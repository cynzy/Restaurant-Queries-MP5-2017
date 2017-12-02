package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;

public class Review {

	protected final String businessID;
	protected final String reviewID;
	protected final String userID;
	protected final String text;
	protected final String date;
	protected final int rating;

	public Review(JsonObject review) {
		this.businessID = review.getString("business_id");
		this.userID = review.getString("user_id");
		this.reviewID = review.getString("review_id");
		this.text = review.getString("text");
		this.date = review.getString("date");
		this.rating = review.getInt("stars");
	}

	public String getBusinessID() {
		return this.businessID;
	}
	
	public String getReviewID() {
		return this.reviewID;
	}
	
	public String getUserID() {
		return this.userID;
	}
	
	public String getText() {
		return this.text;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public int getStars() {
		return this.rating;
	}
	
	@Override
	public boolean equals( Object o ) {
		if( o instanceof Review ) {
			Review other = (Review) o;
			
			return this.reviewID.equals(other.getReviewID());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.businessID.hashCode();
	}
	
	@Override
	public String toString() {
		return this.reviewID;
	}
	
}