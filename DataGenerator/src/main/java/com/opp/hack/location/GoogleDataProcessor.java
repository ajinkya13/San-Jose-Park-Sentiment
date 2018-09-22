package com.opp.hack.location;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voldy.models.LatLongDAO;

public class GoogleDataProcessor {

	static String GOOGLE_API_BASE = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
	// ChIJGQRKQP0zjoARCKj_usz5LrI
	static String APIKEY = "&key=AIzaSyBuAkMANnqqlaDvHm0U94roaGacEityh2Q";

	static ObjectMapper objectMapper = new ObjectMapper();

	public static void getGoogleReviews(LatLongDAO _latLongDao) {
		List<ParkAmenity> amenities = _latLongDao.getParkData();
		StringBuilder sb = null;
		for (ParkAmenity amenity : amenities) {
			String place_id = amenity.getPlace_id();
			if (place_id != null) {
				String googlePlacesQuery = GOOGLE_API_BASE + place_id + APIKEY;
				RestTemplate restTemplate = new RestTemplate();

				ResponseEntity<String> response = restTemplate.getForEntity(googlePlacesQuery, String.class);
				if (response.getStatusCode() == HttpStatus.OK) {
					String responseBody = response.getBody();
					JsonNode node = null;
					try {
						node = objectMapper.readValue(responseBody, JsonNode.class);

						JsonNode resultNode = node.get("result");
						if(resultNode!=null && resultNode.get("reviews")!=null){
							if(resultNode.get("reviews").isArray()){
								sb =new StringBuilder();
								for (final JsonNode reviewNode : resultNode.get("reviews")) {
									if(reviewNode.get("text")!=null){
										sb.append(reviewNode.get("text")).append("|$@@$|");
									}
								}
							}
						}
						

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
			if(sb!=null){
				amenity.setReviewsGoogle(sb.toString());
				_latLongDao.saveOrUpdate(amenity,"Google");
				sb =null;
			}
			
			
			

		}
	}

}
