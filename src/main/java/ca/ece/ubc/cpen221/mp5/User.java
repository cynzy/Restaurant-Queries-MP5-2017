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
 * - this.reviewCount is equal to the size of the set of reviews that contain this.userID
 * 
 * - this.averageRating is equal to the average rating of reviews that contain this.userID
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
 * - this.averageRating represents the average rating this user gives on their review
 *
 */
public class User {

	protected final String name;
	protected final String url;
	protected final String userID;
	protected int reviewCount;
	protected double averageRating;

	public User(JsonObject user) {

		this.name = user.getString("name");
		this.url = user.getString("url");
		this.userID = user.getString("user_id");
		this.reviewCount = user.getInt("review_count");
		this.averageRating = Double.parseDouble(user.get("average_stars").toString());
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

	@Override
	public boolean equals(Object o) {
		if (o instanceof User) {
			User other = (User) o;

			return this.userID.equals(other.getUserID());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return (int) this.getAverageRating();
	}

	@Override
	public String toString() {
		return this.name;
	}

}
