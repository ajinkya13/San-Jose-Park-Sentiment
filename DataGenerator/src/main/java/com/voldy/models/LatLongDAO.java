package com.voldy.models;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opp.hack.location.ParkAmenity;

@Repository
@Transactional
public class LatLongDAO {
  
  @Autowired
  private SessionFactory _sessionFactory;
  
  private Session getSession() {
    return _sessionFactory.getCurrentSession();
  }

  public void save(ParkAmenity parkAmenity) {
	    getSession().save(parkAmenity);
	  }

public List<ParkAmenity> getParkData() {
	// TODO Auto-generated method stub
	return getSession().createQuery("from ParkAmenity").list();
}

public boolean exists(String parkName) {
	// TODO Auto-generated method stub
	if(parkName.contains("'")){
		parkName = parkName.replace("'", "");
	}
	List l = getSession().createQuery("from ParkAmenity where parkName like '"+parkName+"%'").list();
	if(l.size() <=0){
		return false;
	}
	return true;
}

public void saveOrUpdate(ParkAmenity amenity, String socialNw) {
	// TODO Auto-generated method stub
	String sql  = null;
	if(socialNw.equals("Google")){
		sql = "update test.parkamenity set reviewsGoogle ='"+amenity.getReviewsGoogle().replace("\"", "").replace("'", "")+"' where gen_id ="+amenity.getGen_id();
	}else{
		//sql = "update test.parkamenity set reviewsFS ='"+amenity.getReviewsFS().replace("\"", "").replace("'", "")+"' where gen_id ="+amenity.getGen_id();
	}
	getSession().update(amenity);
}
  
  


}

