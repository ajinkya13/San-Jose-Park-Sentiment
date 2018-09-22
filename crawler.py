# -*- coding: utf-8 -*-
 
from bs4 import BeautifulSoup
import requests
import re
import urlparse
import MySQLdb
import time

def parseHTML(rawData):
    
    soup = BeautifulSoup(rawData, 'html.parser')
    review=""
    for line in soup.find_all('p'):
        data=str(line)
        if data.startswith("<p itemprop="):
            r = re.compile('<p itemprop=\"description\" lang=\"en\">(.*?)</p>')
            m = r.search(data)
            if m:
                review = review+"|$@@$|"+m.group(1)
    
    review = review.replace("</br>", '');
    review = review.replace("'", "''");
    return review.replace("<br>", '');
        

def crawl(url):
    # Get the webpage
    req = requests.get(url)
    result = []

    # Check if successful
    if(req.status_code != 200):
        return []

    return req.text

cnx= {'host': '<host>',
  'username': '<username>',
  'password': '<password>',
  'db': 'test'}

db = MySQLdb.connect(cnx['host'],cnx['username'],cnx['password'], cnx['db'])

# prepare a cursor object using cursor() method
cursor = db.cursor()

# Prepare SQL query to INSERT a record into the database.
sql = "SELECT * FROM parkamenity"
try:
    # Execute the SQL command
    cursor.execute(sql)
    # Fetch all the rows in a list of lists.
    results = cursor.fetchall()
    for row in results:
        gen_id = row[0]
        parkName = row[10]
        # Now print fetched result
        parkName=parkName.replace(" ", "-");
        parkName=parkName.replace("*", "");
        url='https://www.yelp.com/biz/'+parkName.lower()+'-san-jose';
        print(url)
        try:
            rawData = crawl(url)
            fo=open("scrapedData", "a")
            reviews=parseHTML(rawData.encode('utf-8'))
            # Prepare SQL query to UPDATE required records
            sql = "UPDATE parkamenity SET reviewsYelp = '"+str(reviews)+"' WHERE gen_id="+str(gen_id)
            print(sql)
            try:
                # Execute the SQL command
                cursor.execute(sql)
                # Commit your changes in the database
                db.commit()
            except:
                # Rollback in case there is any error
                print("Failed: "+gen_id)
                db.rollback()
        except:
            print("Error: unable to fetch data")
        time.sleep(5)

except:
    print ("Error")

# disconnect from server
db.close()

# rawData = crawl('https://www.yelp.com/biz/guadalupe-river-park-and-gardens-san-jose-3', 1)
# fo=open("scrapedData", "a")
# fo.write(rawData.encode('utf-8'))


