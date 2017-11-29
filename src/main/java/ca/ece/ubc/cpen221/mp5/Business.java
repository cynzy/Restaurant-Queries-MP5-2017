package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.HashSet;
import java.util.Set;

public class Business {

	protected boolean open;
	protected String url;
	protected final String businessID;
	protected final String name;
	protected String photoUrl;
	protected Set<String> categories;
	private Set<Review> reviewSet;
	protected Location location;
	protected double rating;
	protected int reviewCount;
	private final int price;

	public Business(JsonObject business) {

		this.reviewSet = new HashSet<Review>();
		this.businessID = business.getString("business_id");
		this.url = business.getString("url");
		this.open = business.getBoolean("open");
		this.name = business.getString("name");
		this.photoUrl = business.getString("photo_url");
		this.reviewCount = business.getInt("review_count");
		this.price = business.getInt("price");
		this.rating = business.getInt("stars");


		//parsing location
		Location location = new Location();
		//make a set of strings from JSON array of neighborhoods
		JsonArray array = business.get("neighborhoods").asJsonArray();
		Set<String> neighbourhoods = new HashSet<>();
		for (int index = 0; index < array.size(); index++){
			neighbourhoods.add(array.getString(index));
		}
		location.setNeighbourhoods(neighbourhoods);

		//make a set of strings from JSON array of schools
		array = business.get("schools").asJsonArray();
		Set<String> schools = new HashSet<>();
		for (int index = 0; index < array.size(); index++){
			schools.add(array.getString(index));
		}
		location.setSchools(schools);

		//set address, city and state
		location.setAddress(business.getString("full_address"));
		location.setCity(business.getString("city"));
		location.setState(business.getString("state"));

		this.location = location;

		//parsing categories
		//make a set of strings from JSON array of categories
		array = business.get("categories").asJsonArray();
		Set<String> categories = new HashSet<>();
		for (int index = 0; index < array.size(); index++){
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

	public void addReview(Review review) {

		if (!this.businessID.equals(review.getBusinessID())) {
			throw new IllegalArgumentException("business ID's do not match");
		}
		this.reviewSet.add(review);
	}

	public void removeReview(Review review) {
		this.reviewSet.remove(review);
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
	public boolean equals( Object o ) {
		
		if( o instanceof Business ) {
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
