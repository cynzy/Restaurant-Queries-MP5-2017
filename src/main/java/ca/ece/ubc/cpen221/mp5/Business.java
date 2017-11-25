package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Set;

public class Business {

	protected boolean open;
	protected String url;
	protected String businessID;
	protected String name;
	protected String photoUrl;
	protected Set<String> categories;

	public Business(String businessID) {
		this.businessID = businessID;
		this.url = new String();
		this.open = true;
		this.name = new String();
		this.photoUrl = new String();
		this.categories = new HashSet<String>();
	}
	
	public void setOpen( boolean open ) {
		this.open = open;
	}
	
	public void setUrl( String url ) {
		this.url = url;
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public void setPhotoUrl( String photoUrl ) {
		this.photoUrl = photoUrl;
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
