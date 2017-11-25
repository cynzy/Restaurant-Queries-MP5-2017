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

	public Business(String businessID, boolean open, String url, String name, String photoUrl, Set<String> categories) {
		this.businessID = businessID;
		this.url = url;
		this.open = open;
		this.name = name;
		this.photoUrl = photoUrl;
		this.categories = categories;
	}
	
	public void SetCategories( Set<String> categories ) {
		this.categories.addAll(categories);
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
