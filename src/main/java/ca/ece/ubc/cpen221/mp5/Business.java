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
 * - this.rating must be the average value of the ratings of all reviews that include this.businessID
 * 
 * - this.rating is between 1 and 5
 * 
 * - this.reviewCount == the size of the set of reviews that contain this.businessID
 * 
 * - this.price is a value between 1 and 5 rating how expensive this business it
 
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
 * - this.rating represents the average rating this business has received through reviews
 * 
 * - this.reviewCount represents the amount of reviews that have been written for this business
 * 
 * - this.price represents a rating of how expensive the products or services that this business produces are
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

	public double getRating() {
		return this.rating;
	}

	public int getReviewCount() {
		return this.reviewCount;
	}

	public int getPrice() {
		return this.price;
	}

	public void setUrl(String newUrl) {
		this.url = newUrl;
	}

	public void addCategory(String newCategory) {
		this.categories.add(newCategory);
	}

	public void removeCategory(String category) {
		this.categories.remove(category);
	}

	public void setOpen(Boolean newOpen) {
		this.open = newOpen;
	}

	public void setPhotoUrl(String newPhotoUrl) {
		this.photoUrl = newPhotoUrl;
	}

	public Boolean isOpen() {
		return this.open;
	}

	public String getBusinessID() {
		return this.businessID;
	}

	public String getName() {
		return this.name;
	}

	public String getUrl() {
		return this.url;
	}

	public String getPhotoUrl() {
		return this.photoUrl;
	}

	public Set<String> getCategories() {
		Set<String> copy = new HashSet<String>();
		copy.addAll(this.categories);

		return copy;
	}

	public void setLocation(Location newLocation) {
		this.location = newLocation;
	}

	public Location getLocation() {
		return this.location;
	}

	public boolean containsCategory(String category) {
		return this.categories.contains(category);
	}

	@Override
	public boolean equals(Object o) {

		if (o instanceof Business) {
			Business other = (Business) o;
			return this.businessID.equals(other.getBusinessID());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.businessID.hashCode();
	}

	@Override
	public String toString() {
		return this.name;
	}
}
