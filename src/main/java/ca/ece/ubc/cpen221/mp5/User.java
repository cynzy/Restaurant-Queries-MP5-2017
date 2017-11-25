package ca.ece.ubc.cpen221.mp5;

public class User {

	protected final String name;
	protected final String url;
	protected final String userID;
	
	public User( String name, String url, String userID ) {
		
		this.name = name;
		this.url = url;
		this.userID = userID;
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
	
}
