package com.voldy.models;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class TestJoinDAO {
  
  @Autowired
  private SessionFactory _sessionFactory;
  
  private Session getSession() {
    return _sessionFactory.getCurrentSession();
  }

  public void query1(String age) {
    
   /* List l =getSession().createQuery("from Criminal as criminal INNER JOIN CriminalType as criminalType"
    		+ "INNER JOIN Crime as crime INNER JOIN Commits as commits WHERE"
    		+ " criminal.crime_id=criminalType.crime_id and "
    		+ " criminalType.crime_id = crime.crime_id and "
    		+ " crime.crime_id = commits.crime_id and "
    		+ "criminal.age > "+age).list();*/
	  
	  List l =getSession().createQuery("from Criminal where "
	    		+ " age > "+age).list();
    System.out.println();
  }
  
  


}

