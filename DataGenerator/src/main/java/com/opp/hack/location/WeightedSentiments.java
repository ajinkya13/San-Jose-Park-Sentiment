package com.opp.hack.location;

import java.util.List;

import com.voldy.models.LatLongDAO;

public class WeightedSentiments {
	static String DEL = "|$@@$|";
	public static void calculateSentiment(LatLongDAO _latLongDao) {
		
		List<ParkAmenity> amenities = _latLongDao.getParkData();
		for (ParkAmenity amenity : amenities) {
			double multiplyYelp = 0;
			double multiplyGoogle = 0;
			double multiplyFS = 0;
			
			int yelpReviewSize = 0;
			int googleReviewSize = 0;
			int fsReviewSize = 0;
			
			int division = 0;
			boolean divideByZero = Boolean.FALSE;
			if(amenity.getReviewsYelp()!=null && amenity.getReviewsYelp().contains(DEL)){
				yelpReviewSize = amenity.getReviewsYelp().split(DEL).length;
				if(amenity.getSentimentYelp()!=null){
					double yelpSentiment = Double.parseDouble(amenity.getSentimentYelp());
					multiplyYelp = yelpReviewSize * yelpSentiment;
					division = yelpReviewSize;
					divideByZero = Boolean.TRUE;
				}
				
			}
			
			if(amenity.getReviewsGoogle()!=null && amenity.getReviewsGoogle().contains(DEL)){
				googleReviewSize = amenity.getReviewsGoogle().split(DEL).length;
				if(amenity.getSentimentGoogle()!=null){
					double googleSentiment = Double.parseDouble(amenity.getSentimentGoogle());
					multiplyGoogle = googleReviewSize * googleSentiment;
					division = division + googleReviewSize;
					divideByZero = Boolean.TRUE;
				}
			}
			
			if(amenity.getReviewsFS()!=null && amenity.getReviewsFS().contains(DEL)){
				fsReviewSize = amenity.getReviewsFS().split(DEL).length;
				if(amenity.getSentimentFS()!=null){
					double fsSentiment = Double.parseDouble(amenity.getSentimentFS());
					multiplyFS = fsReviewSize * fsSentiment;
					division = division + fsReviewSize;
					divideByZero = Boolean.TRUE;
				}
			}
			
			if(divideByZero){
				double weightedSentiment = (multiplyYelp+multiplyGoogle+multiplyFS)/division;
				amenity.setWeightedSentiment(String.valueOf(weightedSentiment));
				
				_latLongDao.saveOrUpdate(amenity, "");
				System.out.println();
			}
			
			
			
		}
	}

}
