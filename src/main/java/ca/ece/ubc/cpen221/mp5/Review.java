package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;

/**
 * Review - Represents a review given in the database by a user about a
 * restaurant
 * 
 * Representation Invariants:
 *
 * - this.businessID should be the business ID of a business in the database
 * this Review object is in
 * 
 * - this.UserID should be the user ID of a user in the database this Review
 * object is in
 *
 * - this.reviewID should be unique to this review
 * 
 * - this.date must be no later than today and no earlier than the year 2000
 *
 * - this.rating must be between 1 and 5
 * 
 * Abstraction Function:
 * 
 * - this.businessID represents the business ID of the business that was
 * reviewed
 * 
 * - this.reviewID represents the ID of this review
 * 
 * - this.userID represents the ID of the user that wrote the review
 * 
 * - this.text represents the content of the review (the written text)
 * 
 * - this.date represents the date on which this review was written
 * 
 * - this.rating represents the rating the review gave to the business on this
 * review
 * 
 */
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
	public boolean equals(Object o) {
		if (o instanceof Review) {
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