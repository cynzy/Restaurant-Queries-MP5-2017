package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.HashSet;
import java.util.Set;

/**
 * Business - represents a business to input into a database. Includes
 * information about the business' location, name, ID, etc.
 * 
 * Representation Invariants:
 *
 * - none of the instance fields are null
 *
 * - this.businessID is unique to this business
 *
 * - this.url has a proper web address format, and is unique to this business
 *
 * - this.name is the name of this business and is unique to it
 *
 * - this.photoUrl is in proper web address format and links to a picture set by
 * the business
 * 
 * - this.rating must be the average value of the ratings of all reviews that
 * include this.businessID
 * 
 * - this.rating is between 1 and 5
 * 
 * - this.reviewCount == the size of the set of reviews that contain
 * this.businessID
 * 
 * - this.price is a value between 1 and 5 rating how expensive this business it
 * 
 * - businessID and name are immutable
 * 
 * Abstraction Function:
 * 
 * - this.open represents the current open/close status of the business
 * 
 * - this.url represents the url address of the business
 * 
 * - this.businessID represets the business ID of this business
 * 
 * - this.name represents the name of this business
 * 
 * - this.photourl represents a link to the photo that the business has on the
 * website this database is implemented for
 * 
 * - this.categories includes a list of tags that identify the categories for
 * which the business falls under
 * 
 * - this.location represents the geographical location of this business
 * 
 * - this.rating represents the average rating this business has received
 * through reviews
 * 
 * - this.reviewCount represents the amount of reviews that have been written
 * for this business
 * 
 * - this.price represents a rating of how expensive the products or services
 * that this business produces are
 */
public class Business {

	protected boolean open;
	protected String url;
	protected final String businessID;
	protected final String name;
	protected String photoUrl;
	protected Set<String> categories;
	protected Location location;
	protected double rating;
	protected int reviewCount;
	protected int price;

	/**
	 * Constructs a Business object
	 *
	 * @param business
	 *            a JsonObject representing a file with all the information for this
	 *            object's representation. requires: business is not null
	 * 
	 */
	public Business(JsonObject business) {

		this.businessID = business.getString("business_id");
		this.url = business.getString("url");
		this.open = business.getBoolean("open");
		this.name = business.getString("name");
		this.photoUrl = business.getString("photo_url");
		this.reviewCount = business.getInt("review_count");
		this.price = business.getInt("price");
		this.rating = business.getInt("stars");

		// parsing location
		Location location = new Location();
		// make a set of strings from JSON array of neighborhoods
		JsonArray array = business.get("neighborhoods").asJsonArray();
		Set<String> neighbourhoods = new HashSet<>();
		for (int index = 0; index < array.size(); index++) {
			neighbourhoods.add(array.getString(index));
		}
		location.setNeighbourhoods(neighbourhoods);

		// make a set of strings from JSON array of schools
		array = business.get("schools").asJsonArray();
		Set<String> schools = new HashSet<>();
		for (int index = 0; index < array.size(); index++) {
			schools.add(array.getString(index));
		}
		location.setSchools(schools);

		// set address, coordinates, city and state
		location.setAddress(business.getString("full_address"));
		location.setCoordinates(Double.parseDouble(business.get("longitude").toString()),
				Double.parseDouble(business.get("latitude").toString()));
		location.setCity(business.getString("city"));
		location.setState(business.getString("state"));

		this.location = location;

		// parsing categories
		// make a set of strings from JSON array of categories
		array = business.get("categories").asJsonArray();
		Set<String> categories = new HashSet<>();
		for (int index = 0; index < array.size(); index++) {
			categories.add(array.getString(index));
		}

		this.categories = categories;

	}

	/**
	 * returns the average rating of this business. requires: this object is not
	 * null
	 *
	 * @param void
	 * 
	 * @return The average rating, between 1 and 5, of this business
	 * 
	 */
	public synchronized double getRating() {
		return this.rating;
	}

	/**
	 * Recomputes the average rating and review count of this business when a review
	 * is added to the database.
	 *
	 * @param newRating
	 *            the rating from the new review
	 * 
	 * @return void
	 * 
	 */
	public synchronized void adjustRating(int newRating) {

		this.rating = (this.rating * this.reviewCount + newRating) / (this.reviewCount + 1);
		this.reviewCount++;
	}

	/**
	 * returns the review count of this business. requires: this object is not null
	 *
	 * @param void
	 * 
	 * @return The number of reviews written about this business
	 * 
	 */
	public synchronized int getReviewCount() {
		return this.reviewCount;
	}

	/**
	 * returns the price rating of this business. requires: this object is not null
	 *
	 * @param void
	 * 
	 * @return The rating, between 1 and 5, of how expensive this business is
	 * 
	 */
	public synchronized int getPrice() {
		return this.price;
	}

	/**
	 * sets the url of this business to the new url. requires: this object is not
	 * null and newUrl is not null and in proper format
	 *
	 * @param newUrl
	 *            the url to be set for this business
	 * 
	 * @return void
	 * 
	 */
	public synchronized void setUrl(String newUrl) {
		this.url = newUrl;
	}

	/**
	 * Adds a category tag to this business. If the category is already within this
	 * business' set of categories, the method does nothing. Requires: this object
	 * is not null and category is not null and in proper format
	 *
	 * @param newCategory
	 *            category to be added to this business' set of categories
	 * 
	 * @return void
	 */
	public synchronized void addCategory(String newCategory) {
		this.categories.add(newCategory);
	}

	/**
	 * Removes a category tag from this business. If the category already does not
	 * exist for this business, this methid does nothing. Requires: this object is
	 * not null and category is not null and in proper format
	 *
	 * @param newCategory
	 *            category to be removed from this business' set of categories
	 * 
	 * @return void
	 */
	public synchronized void removeCategory(String category) {
		this.categories.remove(category);
	}

	/**
	 * sets the open/close status of this business. requires: this object is not
	 * null and newOpem is not null
	 *
	 * @param newOpen
	 *            the open/close status to be set for this business
	 * 
	 * @return void
	 * 
	 */
	public synchronized void setOpen(Boolean newOpen) {
		this.open = newOpen;
	}

	/**
	 * sets the photo url of this business to the new photo url. requires: this
	 * object is not null and newPhotoUrl is not null and in proper format
	 *
	 * @param newPhotoUrl
	 *            the photo url to be set for this business
	 * 
	 * @return void
	 * 
	 */
	public synchronized void setPhotoUrl(String newPhotoUrl) {
		this.photoUrl = newPhotoUrl;
	}

	/**
	 * Checks whether this business is open of closed. Returns true only if this
	 * business is open. requires: this object is not null
	 *
	 * @param void
	 * 
	 * @return True if this business is open. False otherwise.
	 */
	public synchronized Boolean isOpen() {
		return this.open;
	}

	/**
	 * returns the business ID of this business. requires: this object is not null
	 * and in proper format
	 *
	 * @param void
	 * 
	 * @return The business ID of this business
	 * 
	 */
	public synchronized String getBusinessID() {
		return this.businessID;
	}

	/**
	 * returns the name of this business. requires: this object is not null
	 *
	 * @param void
	 * 
	 * @return The name of this business
	 * 
	 */
	public synchronized String getName() {
		return this.name;
	}

	/**
	 * returns the url of this business. requires: this object is not null and url
	 * is in proper format
	 *
	 * @param void
	 * 
	 * @return The url address of this business
	 * 
	 */
	public synchronized String getUrl() {
		return this.url;
	}

	/**
	 * returns the photo url of this business. requires: this object is not null and
	 * photourl is in proper format
	 *
	 * @param void
	 * 
	 * @return The url address of this business' photo
	 * 
	 */
	public synchronized String getPhotoUrl() {
		return this.photoUrl;
	}

	/**
	 * returns the set of category tags of this business. requires: this object is
	 * not null and categories are in proper format
	 *
	 * @param void
	 * 
	 * @return The set of categories of this business
	 * 
	 */
	public synchronized Set<String> getCategories() {
		Set<String> copy = new HashSet<String>();
		copy.addAll(this.categories);

		return copy;
	}

	/**
	 * sets the location of this business to a new location. requires: this object
	 * is not null and newLocation is not null and has no null instance fields
	 *
	 * @param newLocation
	 *            the location to be set for this business
	 * 
	 * @return void
	 * 
	 */
	public synchronized void setLocation(Location newLocation) {
		this.location = newLocation;
	}

	/**
	 * returns the location of this business. requires: this object is not null and
	 * location has no null fields
	 *
	 * @param void
	 * 
	 * @return The location of this business
	 * 
	 */
	public synchronized Location getLocation() {
		return this.location;
	}

	/**
	 * Checks whether the category describes this business. Returns true only if
	 * this category is in this business' set of categories. requires: this object
	 * is not null and category is not null and in proper format.
	 *
	 * @param category
	 *            the String for which this method checks inclusion in the category
	 *            set
	 * 
	 * @return True if the category is in this business' category set. False
	 *         otherwise.
	 * 
	 */
	public synchronized boolean containsCategory(String category) {
		return this.categories.contains(category);
	}

	/**
	 * Compares the equality of this Business object and another object. Returns
	 * true only if o is a Business with the same business ID. Requires: this
	 * business and o are not null.
	 *
	 * @param o
	 *            Object to be compared with this business
	 * 
	 * @return true if the objects equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {

		if (o instanceof Business) {
			Business other = (Business) o;
			return this.name.equals(other.getName());
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
		return this.name;
	}
}
