package ca.ece.ubc.cpen221.mp5;

public class Review {

	private final String businessID;
	private final String reviewID;
	private final String userID;
	private final String text;
	private final String date;
	private final int stars;

	public Review(String reviewID, String businessID, String userID, String text, String date, int stars) {
		this.businessID = businessID;
		this.userID = userID;
		this.reviewID = reviewID;
		this.text = text;
		this.date = date;
		this.stars = stars;
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
		return this.stars;
	}
	
}