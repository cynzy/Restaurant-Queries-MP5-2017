package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;
import java.util.HashSet;
import java.util.Set;

/**
 * User - Represents a user in the website or app that the database was
 * developed for
 * 
 * Representation Invariants:
 *
 * - this.name is unique to this user
 *
 * - this.url is in proper web address format and links to the user's profile
 * 
 * - this.userID is unique to this user
 * 
 * - this.reviewCount is equal to the size of the set of reviews that contain
 * this.userID
 * 
 * - this.averageRating is equal to the average rating of reviews that contain
 * this.userID
 * 
 * - this.averageRating is a number between 1 and 5
 * 
 * - no instance fields are null
 * 
 * Abstraction Function:
 * 
 * - this.name represents the username of this user
 * 
 * - this.url represents a link to the user's profile
 * 
 * - this.userID represents the userID of this user
 * 
 * - this.reviewCount represents the amount of reviews this user has wrote
 * 
 * - this.averageRating represents the average rating this user gives on their
 * review
 *
 */
public class User {

	protected final String name;
	protected final String url;
	protected final String userID;
	protected int reviewCount;
	protected double averageRating;

	/**
	 * Constructs a User object
	 *
	 * @param user
	 *            a JsonObject representing a file with all the information for this
	 *            object's representation. requires: user is not null
	 * 
	 */
	public User(JsonObject user) {

		this.name = user.getString("name");
		this.url = user.getString("url");
		this.userID = user.getString("user_id");
		this.reviewCount = user.getInt("review_count");
		this.averageRating = Double.parseDouble(user.get("average_stars").toString());
	}

	/**
	 * returns the average rating voted on by this user. requires: this object is
	 * not null
	 *
	 * @param void
	 * 
	 * @return The average rating voted on by this user
	 * 
	 */
	public synchronized double getAverageRating() {
		return this.averageRating;
	}
	
	public synchronized void adjustRating( int newRating ) {
		this.averageRating = (this.averageRating * this.reviewCount + newRating)/(this.reviewCount + 1);
		this.reviewCount++;
	}

	/**
	 * returns the number of reviews written by this user. requires: this object is
	 * not null
	 *
	 * @param void
	 * 
	 * @return The number of reviews written by this user
	 * 
	 */
	public synchronized int getReviewCount() {
		return this.reviewCount;
	}

	/**
	 * returns the username of this user. requires: this object is not null and name
	 * is in the proper format
	 *
	 * @param void
	 * 
	 * @return The username of this user
	 * 
	 */
	public synchronized String getName() {
		return this.name;
	}

	/**
	 * returns the url address of this user's profile. requires: this object is not
	 * null and url is in the proper format
	 *
	 * @param void
	 * 
	 * @return The url address of this user's profile
	 * 
	 */
	public synchronized String getUrl() {
		return this.url;
	}

	/**
	 * returns the user ID of this user. requires: this object is not
	 * null and user ID is in the proper format
	 *
	 * @param void
	 * 
	 * @return The user ID of this user
	 * 
	 */
	public synchronized String getUserID() {
		return this.userID;
	}

	/**
	 * Compares the equality of this User object and another object. Returns
	 * true only if o is a User with the same userID. Requires: this
	 * user and o are not null.
	 *
	 * @param o
	 *            Object to be compared with this user
	 * 
	 * @return true if the objects equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			User other = (User) o;

			return this.userID.equals(other.getUserID());
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
		return (int) this.getAverageRating();
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
		return this.name;
	}

}
