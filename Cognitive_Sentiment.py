# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
import urllib2
import urllib
import sys
import base64
import json
import MySQLdb
import yaml
from collections import defaultdict




cnx= {'host': '<hostname>',
  'username': '<user>',
  'password': '<pass>',
  'db': 'test'}

db = MySQLdb.connect(cnx['host'],cnx['username'],cnx['password'], cnx['db'])

# prepare a cursor object using cursor() method
cursor = db.cursor()

base_url = 'https://westus.api.cognitive.microsoft.com/'
account_key = '<APIKEY>'

headers = {'Content-Type':'application/json', 'Ocp-Apim-Subscription-Key':account_key}

# Prepare SQL query to INSERT a record into the database.
sql = "SELECT * FROM parkamenity"
try:
    # Execute the SQL command
    cursor.execute(sql)
    # Fetch all the rows in a list of lists.
    results = cursor.fetchall()
    for row in results:
        try:
            gen_id = row[0]
            reviewsYelp = row[17]
            reviewsYelp = BeautifulSoup(reviewsYelp).text
                    
            input_texts="{\"documents\":[";
            reviewsYelp=reviewsYelp.split("|$@@$|");
            id=1;
            for review in reviewsYelp:
                if review!='':
                    input_texts=input_texts+"{\"id\":\""+str(id)+"\",\"text\":\""+review.replace("\"", "\\\"")+"\"},"
                    id=id+1
            
            input_texts=input_texts+"]}"
            print(input_texts)
            input_texts=input_texts.encode('utf8')
            
            data = yaml.safe_load(input_texts)
            print(data)
            d = defaultdict(list)

        
            num_detect_langs = 1;
             
            # Detect key phrases.
            batch_keyphrase_url = base_url + 'text/analytics/v2.0/keyPhrases'
            req = urllib2.Request(batch_keyphrase_url, input_texts, headers) 
            response = urllib2.urlopen(req)
            result = response.read()
            obj = json.loads(result)
            key_phrases=''
            for keyphrase_analysis in obj['documents']:
                try:
                    key_phrases=key_phrases+ ', '.join(map(str,keyphrase_analysis['keyPhrases']))
                except:
                    print('Ignored due to encoding issue')
            # Detect sentiment.
            batch_sentiment_url = base_url + 'text/analytics/v2.0/sentiment'
            req = urllib2.Request(batch_sentiment_url, input_texts, headers) 
            response = urllib2.urlopen(req)
            result = response.read()
            obj = json.loads(result)
            score=0
            count=0
            for sentiment_analysis in obj['documents']:
                d[str(sentiment_analysis['id'])].append(str(sentiment_analysis['score']))
                d[str(sentiment_analysis['id'])].append(data["documents"][int(sentiment_analysis['id'])-1]["text"])
                if sentiment_analysis['score']<0.7:
                    score=score+(sentiment_analysis['score']*2)
                    count=count+2
                else:
                    score=score+sentiment_analysis['score']
                    count=count+1
            sentiment=score/count
            
            print(str(sentiment)+" : "+key_phrases)
#             sql = "UPDATE parkamenity SET sentimentFS = '"+str(sentiment)+"' , keyPhrasesFS = '"+key_phrases+"' WHERE gen_id="+str(gen_id)
#             print(sql)
#             try:
#                 # Execute the SQL command
#                 cursor.execute(sql)
#                 # Commit your changes in the database
#                 db.commit()
#             except:
#                 # Rollback in case there is any error
#                 print("Failed: "+str(gen_id))
#                 db.rollback()
            
            
            badReviews=[]
            neutralReviews=[]
            goodReviews=[]
            
            print(d)
            for value in d.values():
                if float(value[0])<0.5:
#                     print(value[1])
                    badReviews.append(value[1])
                else:
                    if (float(value[0])>0.5) and (float(value[0])<0.6):
#                         print(value[1])
                        neutralReviews.append(value[1])
                    else:
#                         print(value[1])
                        goodReviews.append(value[1])
            
            goodReviews=str(goodReviews).replace("'","''")
            badReviews=str(badReviews).replace("'","''")
            neutralReviews=str(neutralReviews).replace("'","''")        
            print(badReviews)
            print(goodReviews)
            print(neutralReviews)
            
            sql = "UPDATE parkamenity SET reviewsGoodGoogle = '"+str(goodReviews).replace("\\","\\\\")+"' , reviewsBadGoogle = '"+str(badReviews).replace("\\","\\\\")+"' , reviewsNeutralGoogle = '"+str(neutralReviews).replace("\\","\\\\")+"' WHERE gen_id="+str(gen_id)
            print(sql)
            try:
                # Execute the SQL command
                cursor.execute(sql)
                # Commit your changes in the database
                db.commit()
            except:
                # Rollback in case there is any error
                print("Failed: "+str(gen_id))
                db.rollback()


        
        except:
            print("Error 1")
except:
    print ("Error")
    
# disconnect from server
db.close()
