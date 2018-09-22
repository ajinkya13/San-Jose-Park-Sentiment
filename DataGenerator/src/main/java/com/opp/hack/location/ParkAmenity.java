package com.opp.hack.location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="parkamenity")
public class ParkAmenity {
	
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long gen_id;
	
	public ParkAmenity() { }

	private String parkName;
	private String acreage;
	
	//google apis
	
	private String latitude;
	private String longitude;
	
	private String neLatitude;
	private String neLongitude;
	
	private String swLatitude;
	private String swLongitude;
	
	
	private String city;
	private String county;
	private String state;
	private String country;
	
	private String formatted_address;
	private String place_id;
	private String reviewsYelp;
	private String reviewsFS;
	private String reviewsGoogle;
	
	private String sentimentYelp;
	private String sentimentFS;
	private String sentimentGoogle;
	
	private String keyPhrasesYelp;
	private String keyPhrasesFS;
	private String keyPhrasesGoogle;
	
	private String weightedSentiment;
	
	private String reviewsGoodYelp;
	private String reviewsBadYelp;
	private String reviewsNeutralYelp;
	
	private String reviewsGoodGoogle;
	private String reviewsBadGoogle;
	private String reviewsNeutralGoogle;
	
	
	
	
	
	
	public String getReviewsGoodYelp() {
		return reviewsGoodYelp;
	}
	public void setReviewsGoodYelp(String reviewsGoodYelp) {
		this.reviewsGoodYelp = reviewsGoodYelp;
	}
	public String getReviewsBadYelp() {
		return reviewsBadYelp;
	}
	public void setReviewsBadYelp(String reviewsBadYelp) {
		this.reviewsBadYelp = reviewsBadYelp;
	}
	public String getReviewsNeutralYelp() {
		return reviewsNeutralYelp;
	}
	public void setReviewsNeutralYelp(String reviewsNeutralYelp) {
		this.reviewsNeutralYelp = reviewsNeutralYelp;
	}
	public String getReviewsGoodGoogle() {
		return reviewsGoodGoogle;
	}
	public void setReviewsGoodGoogle(String reviewsGoodGoogle) {
		this.reviewsGoodGoogle = reviewsGoodGoogle;
	}
	public String getReviewsBadGoogle() {
		return reviewsBadGoogle;
	}
	public void setReviewsBadGoogle(String reviewsBadGoogle) {
		this.reviewsBadGoogle = reviewsBadGoogle;
	}
	public String getReviewsNeutralGoogle() {
		return reviewsNeutralGoogle;
	}
	public void setReviewsNeutralGoogle(String reviewsNeutralGoogle) {
		this.reviewsNeutralGoogle = reviewsNeutralGoogle;
	}
	public String getWeightedSentiment() {
		return weightedSentiment;
	}
	public void setWeightedSentiment(String weightedSentiment) {
		this.weightedSentiment = weightedSentiment;
	}
	public String getSentimentYelp() {
		return sentimentYelp;
	}
	public void setSentimentYelp(String sentimentYelp) {
		this.sentimentYelp = sentimentYelp;
	}
	public String getSentimentFS() {
		return sentimentFS;
	}
	public void setSentimentFS(String sentimentFS) {
		this.sentimentFS = sentimentFS;
	}
	public String getSentimentGoogle() {
		return sentimentGoogle;
	}
	public void setSentimentGoogle(String sentimentGoogle) {
		this.sentimentGoogle = sentimentGoogle;
	}
	public String getKeyPhrasesYelp() {
		return keyPhrasesYelp;
	}
	public void setKeyPhrasesYelp(String keyPhrasesYelp) {
		this.keyPhrasesYelp = keyPhrasesYelp;
	}
	public String getKeyPhrasesFS() {
		return keyPhrasesFS;
	}
	public void setKeyPhrasesFS(String keyPhrasesFS) {
		this.keyPhrasesFS = keyPhrasesFS;
	}
	public String getKeyPhrasesGoogle() {
		return keyPhrasesGoogle;
	}
	public void setKeyPhrasesGoogle(String keyPhrasesGoogle) {
		this.keyPhrasesGoogle = keyPhrasesGoogle;
	}
	public String getReviewsGoogle() {
		return reviewsGoogle;
	}
	public void setReviewsGoogle(String reviewsGoogle) {
		this.reviewsGoogle = reviewsGoogle;
	}
	public String getReviewsFS() {
		return reviewsFS;
	}
	public void setReviewsFS(String reviewsFS) {
		this.reviewsFS = reviewsFS;
	}
	public long getGen_id() {
		return gen_id;
	}
	public void setGen_id(long gen_id) {
		this.gen_id = gen_id;
	}
	public String getReviewsYelp() {
		return reviewsYelp;
	}
	public void setReviewsYelp(String reviewsYelp) {
		this.reviewsYelp = reviewsYelp;
	}
	public String getNeLatitude() {
		return neLatitude;
	}
	public void setNeLatitude(String neLatitude) {
		this.neLatitude = neLatitude;
	}
	public String getNeLongitude() {
		return neLongitude;
	}
	public void setNeLongitude(String neLongitude) {
		this.neLongitude = neLongitude;
	}
	public String getSwLatitude() {
		return swLatitude;
	}
	public void setSwLatitude(String swLatitude) {
		this.swLatitude = swLatitude;
	}
	public String getSwLongitude() {
		return swLongitude;
	}
	public void setSwLongitude(String swLongitude) {
		this.swLongitude = swLongitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFormatted_address() {
		return formatted_address;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	public String getPlace_id() {
		return place_id;
	}
	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getAcreage() {
		return acreage;
	}
	public void setAcreage(String acreage) {
		this.acreage = acreage;
	}
	
	
}
