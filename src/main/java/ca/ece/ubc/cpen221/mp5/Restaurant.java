package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Set;

public class Restaurant extends Business {

	private Location location;
	private int rating;
	private int reviewCount;
	private final int price;
	private Set<YelpReview> reviewSet;
	
	public Restaurant(String businessID, Boolean open, String url, String name, String photoUrl, Set<String> categories,
			Location location, int stars, int reviewCount, int price, Set<YelpReview> reviewSet) {
		
		super(businessID, open, url, name, photoUrl, categories);
		this.location = location;
		this.rating = stars;
		this.reviewCount = reviewCount;
		this.price = price;
		this.reviewSet = new HashSet<YelpReview>();
		this.reviewSet.addAll(reviewSet);
	}

	public void addReview( YelpReview review ) {
		this.reviewSet.add(review);
		this.reviewCount = this.reviewSet.size();
		int sumRating = 0;
		for( YelpReview r: this.reviewSet) {
			sumRating += r.getStars();
		}
		this.rating = (int) Math.round(((double)sumRating)/this.reviewCount);
	}
	
	public void setLocation( Location newLocation ) {
		this.location = newLocation;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	public int getReviewCount() {
		return this.reviewCount;
	}
	
	public int getPrice() {
		return this.price;
	}
	
	public Set<YelpReview> getReviewSet(){
		Set<YelpReview> copy = new HashSet<YelpReview>();
		copy.addAll(this.reviewSet);
		return copy;
	}

}
