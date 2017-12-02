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
 * - this.text represents the content of the review (the written text) and
 * cannot be edited after being initialized
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

	/**
	 * Constructs a Review object
	 *
	 * @param review
	 *            a JsonObject representing a file with all the information for this
	 *            object's representation. requires: review is not null
	 * 
	 */
	public Review(JsonObject review) {
		this.businessID = review.getString("business_id");
		this.userID = review.getString("user_id");
		this.reviewID = review.getString("review_id");
		this.text = review.getString("text");
		this.date = review.getString("date");
		this.rating = review.getInt("stars");
	}

	/**
	 * returns the business ID of the business this review is written about.
	 * requires: this object is not null and business ID is in the proper format
	 *
	 * @param void
	 * 
	 * @return The business ID of the business this review is written about
	 * 
	 */
	public String getBusinessID() {
		return this.businessID;
	}

	/**
	 * returns the review ID of this review. requires: this object is not null and
	 * review ID is in the proper format
	 *
	 * @param void
	 * 
	 * @return The review ID of this review
	 * 
	 */
	public String getReviewID() {
		return this.reviewID;
	}

	/**
	 * returns the user ID of the user who wrote this review. requires: this object
	 * is not null and user ID is in the proper format
	 *
	 * @param void
	 * 
	 * @return The user ID of the user who wrote this review
	 * 
	 */
	public String getUserID() {
		return this.userID;
	}

	/**
	 * returns the written text which composes this review. requires: this object is
	 * not null
	 *
	 * @param void
	 * 
	 * @return The written text which composes this review
	 * 
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * returns the date on which this review was written. requires: this object is
	 * not null and date is in the proper format
	 *
	 * @param void
	 * 
	 * @return The date on which this review was written
	 * 
	 */
	public String getDate() {
		return this.date;
	}

	/**
	 * returns the rating of this review. requires: this object is not null
	 *
	 * @param void
	 * 
	 * @return The rating given to the restaurant by the user in this review
	 * 
	 */
	public int getStars() {
		return this.rating;
	}

	/**
	 * Compares the equality of this Review object and another object. Returns true
	 * only if o is a Review with the same reviewID. Requires: this review and o are
	 * not null.
	 *
	 * @param o
	 *            Object to be compared with this review
	 * 
	 * @return true if the objects equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Review) {
			Review other = (Review) o;

			return this.reviewID.equals(other.getReviewID());
		}

		return false;
	}

	/**
	 * Computes a hash code for this object
	 *
	 * @param void
	 * 
	 * @return This object's hash code
	 */
	@Override
	public int hashCode() {
		return this.businessID.hashCode();
	}

	/**
	 * Returns a string representation of this object
	 *
	 * @param void
	 * 
	 * @return A string representation of the object
	 */
	@Override
	public String toString() {
		return this.text;
	}

}