package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Set;

public class User {

	protected final String name;
	protected final String url;
	protected final String userID;
	protected int reviewCount;
	protected double averageRating;
	private Set<Review> reviewSet;
	
	public User( String name, String url, String userID, int reviewCount, double averageRating ) {
		
		this.name = name;
		this.url = url;
		this.userID = userID;
		this.reviewCount = reviewCount;
		this.averageRating = averageRating;
		this.reviewSet = new HashSet<Review>();
	}
	
	public void addAllReviews( Set<Review> reviewSet) {
		this.reviewSet.addAll(reviewSet);
	}
	
	public void addReview( Review review ) {
		if( !this.userID.equals(review.userID)) {
			throw new IllegalArgumentException( "user ID's do not match" );
		}
		
		this.reviewSet.add(review);
		this.reviewCount = this.reviewSet.size();
		int ratingSum = 0;
		for( Review r: this.reviewSet) {
			ratingSum += r.getStars();
		}
		
		this.averageRating = ((double)ratingSum)/this.reviewCount;
	}
	
	public boolean wroteReview( Review review ) {
		return this.reviewSet.contains(review);
	}
	
	public double getAverageRating() {
		return this.averageRating;
	}
	
	public int getReviewCount() {
		return this.reviewCount;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public String getUserID() {
		return this.userID;
	}
	
}
