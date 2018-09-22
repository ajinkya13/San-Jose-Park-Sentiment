package com.opp.hack.location;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voldy.models.LatLongDAO;

public class LatitudeLongitudeDataCreation {

	static String CSV_PATH = "/Users/amoghrao/Downloads/Database226-Assignment5-master 2/src/main/resources/ParkAmeText.csv";
	static String GOOGLE_MAP_API = "http://maps.googleapis.com/maps/api/geocode/json?";
	static String ADDRESS_SEARCH = "address=";
	static ObjectMapper objectMapper = new ObjectMapper();

	public static void saveData(LatLongDAO _latLongDao) {
		List<ParkAmenity> parkAmenities = getAmenityList();

		
		for (ParkAmenity parkAmenity : parkAmenities) {
			RestTemplate restTemplate = new RestTemplate();

			String googleMapResourceUrl = GOOGLE_MAP_API + ADDRESS_SEARCH + parkAmenity.getParkName() + " San Jose";
			ResponseEntity<String> response = restTemplate.getForEntity(googleMapResourceUrl , String.class);
			
			if(response.getStatusCode() == HttpStatus.OK){
				
				 try {
					 
					
					JsonNode node = objectMapper.readValue(response.getBody(), JsonNode.class);
					
					JsonNode resultsNode = node.get("results").get(0);
					
					if(resultsNode.get("address_components")!=null){
						JsonNode array = resultsNode.get("address_components");
					    
						//City
					    JsonNode jsonNode0 = array.get(0);
					    String city = jsonNode0.get("long_name").asText();
					    parkAmenity.setCity(city);
					    
					    //County
					    
					    JsonNode jsonNode1 = array.get(1);
					    String county = jsonNode1.get("long_name").asText();
					    parkAmenity.setCounty(county);
					    //State
					    
					    JsonNode jsonNode2 = array.get(2);
					    String state = jsonNode2.get("long_name").asText();
					    parkAmenity.setState(state);
					    //Country
					    
					    JsonNode jsonNode3 = array.get(3);
					    String country = jsonNode3.get("long_name").asText();
					    parkAmenity.setCountry(country);
					    
					    
					   String formatted_address = resultsNode.get("formatted_address").asText();
					   parkAmenity.setFormatted_address(formatted_address);
					   String place_id = resultsNode.get("place_id").asText();
					   parkAmenity.setPlace_id(place_id);
					   
					   if(resultsNode.get("geometry").get("bounds")!=null){
						   JsonNode neLocations = resultsNode.get("geometry").get("bounds").get("northeast");
						   JsonNode swLocations = resultsNode.get("geometry").get("bounds").get("southwest");
						   String neLatitude = neLocations.get("lat").asText();
						   parkAmenity.setNeLatitude(neLatitude);
						   String neLongitude = neLocations.get("lng").asText();
						   parkAmenity.setNeLongitude(neLongitude);
						   
						   String swLatitude = swLocations.get("lat").asText();
						   parkAmenity.setSwLatitude(swLatitude);
						   String swLongitude = swLocations.get("lng").asText();
						   parkAmenity.setSwLongitude(swLongitude);
					   }
					   
					   JsonNode locations = resultsNode.get("geometry").get("location");
					   String latitude = locations.get("lat").asText();
					   parkAmenity.setLatitude(latitude);
					   String longitude = locations.get("lng").asText();
					   parkAmenity.setLongitude(longitude);
					   
					   System.out.println("lat:"+parkAmenity.getLatitude()+" longitude:"+parkAmenity.getLongitude());
					   
					   
					   _latLongDao.save(parkAmenity);
					   try {
						Thread.sleep(150);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					
				   
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println(response.getBody());
			}
			
		}
		
		
		System.out.println();

	}

	private static List<ParkAmenity> getAmenityList() {
		List<ParkAmenity> resultList = new ArrayList<ParkAmenity>();

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\t";

		try {
			br = new BufferedReader(new FileReader(CSV_PATH));
			while ((line = br.readLine()) != null) {

				ParkAmenity parkAmenity = new ParkAmenity();
				// use comma as separator
				String[] park = line.split(cvsSplitBy);
				if (park[0] != null && park[0].equals("#")) {
					continue;
				}

				//System.out.println("Parkname [name= " + park[1] + " , acreage=" + park[2] + "]");

				parkAmenity.setParkName(park[1]);
				parkAmenity.setAcreage(park[2]);

				resultList.add(parkAmenity);

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return resultList;
	}

}
