package ca.ece.ubc.cpen221.mp5;

public class Review {

	protected final String businessID;
	protected final String reviewID;
	protected final String userID;
	protected final String text;
	protected final String date;
	protected final int rating;

	public Review(String reviewID, String businessID, String userID, String text, String date, int rating) {
		this.businessID = businessID;
		this.userID = userID;
		this.reviewID = reviewID;
		this.text = text;
		this.date = date;
		this.rating = rating;
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
	
}