package ca.ece.ubc.cpen221.mp5;

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

	public Business(String businessID, boolean open, String url, String name, String photoUrl, Set<String> categories) {
		this.businessID = businessID;
		this.url = url;
		this.open = open;
		this.name = name;
		this.photoUrl = photoUrl;
		this.categories = new HashSet<String>();
		this.categories.addAll(categories);
		this.reviewSet = new HashSet<Review>();
	}
	
	public void addAllReviews( Set<Review> reviewSet) {
		this.reviewSet.addAll(reviewSet);
	}
	
	public void addReview(Review review) {

		if (!this.businessID.equals(review.getBusinessID())) {
			throw new IllegalArgumentException("business ID's do not match");
		}
		this.reviewSet.add(review);
	}
	
	public void removeReview( Review review ) {
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
}
