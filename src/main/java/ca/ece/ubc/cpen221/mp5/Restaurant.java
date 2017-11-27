package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Set;

public class Restaurant extends Business {
	
	private Set<YelpReview> reviewSet;
	
	public Restaurant(String businessID, Boolean open, String url, String name, String photoUrl, Set<String> categories,
			Location location, int stars, int reviewCount, int price, Set<YelpReview> reviewSet) {
		
		super(businessID, open, url, name, photoUrl, categories, location, reviewCount, price, stars);
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
	
	
	public Set<YelpReview> getReviewSet(){
		Set<YelpReview> copy = new HashSet<YelpReview>();
		copy.addAll(this.reviewSet);
		return copy;
	}

}
