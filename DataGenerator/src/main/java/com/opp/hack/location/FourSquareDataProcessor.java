package com.opp.hack.location;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voldy.models.LatLongDAO;

public class FourSquareDataProcessor {
	
	static String FORESQUARE_URL_1 = "https://api.foursquare.com/v2/venues/explore?";
			//+ "ll=37.2679809,-121.8053685"
	static String FORESQUARE_URL_2 = "&oauth_token=EOTMLK5DRIZW3SGZNWO0BZMIIRFICQ3IBPLNVIQ5TB4J44YP&v=20160924";
	static ObjectMapper objectMapper = new ObjectMapper();
	public static void getFourSquareData(LatLongDAO _latLongDao) {
		// TODO Auto-generated method stub
		
		Map<String,String> parkReviewsMap = new HashMap<String,String>();
		List<ParkAmenity> amenities= _latLongDao.getParkData();
		
		for(ParkAmenity amenity:amenities){
			String parentParkName= amenity.getParkName();
			if(parentParkName!=null && parentParkName.contains("*"))
				parentParkName = amenity.getParkName().replace("*", "");
			
			String lat =amenity.getLatitude();
			String lg = amenity.getLongitude();
			String latLongQuery = "ll="+lat+","+lg;
			String fsQuery = FORESQUARE_URL_1 + latLongQuery + FORESQUARE_URL_2;
			
			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<String> response = restTemplate.getForEntity(fsQuery , String.class);
			
			if(response.getStatusCode() == HttpStatus.OK){
		
				String responseBody = response.getBody();
				try {
					JsonNode node = objectMapper.readValue(response.getBody(), JsonNode.class);
					JsonNode responseNode = node.get("response");
					JsonNode groupsNode = responseNode.get("groups");
					
					
					if (groupsNode.isArray())
					    for (final JsonNode groupNode : groupsNode) {
					    	
					    	JsonNode items = groupNode.get("items");
					    
					    	if (items.isArray())
							    for (final JsonNode item : items) {
							    	String parkName = item.get("venue").get("name").asText();
							    	JsonNode tipsNode = item.get("tips");
							    	
							    	StringBuilder sb = new StringBuilder();
							    	if(tipsNode!=null && tipsNode.isArray())
							    		for (final JsonNode tip : tipsNode) {
							    			sb.append(tip.get("text").asText());
							    			sb.append("|$@@$|");
							    			
							    			
							    			System.out.println();
							    		}
							    	//&& _latLongDao.exists(parkName)
							    	if(parkName!=null ){
							    		parkReviewsMap.put(parkName, sb.toString());
							    	}
							}
					  
					    	
					}
					
				}
					catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println();
				
			}
			
			
			System.out.println();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		for(ParkAmenity amenity:amenities){
			String parkNameKey =amenity.getParkName();
			if(amenity.getParkName()!=null && amenity.getParkName().contains("*"))
				parkNameKey = amenity.getParkName().replace("*", "");
			String review = parkReviewsMap.get(parkNameKey);
			amenity.setReviewsFS(review);
			_latLongDao.saveOrUpdate(amenity,"FourSquare");
			System.out.println();
			
		}
		
		
		
		System.out.println();
		
	}

	
	
}
