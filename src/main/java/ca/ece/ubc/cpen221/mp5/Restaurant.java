package ca.ece.ubc.cpen221.mp5;

import java.util.Set;

public class Restaurant extends Business {
	
	private Location location;
	private int stars;
	private int reviewCount;
	private int price;;
	
	public Restaurant( String businessID) {
		super(businessID);
		this.location = new Location();
		this.stars = 0;
		this.reviewCount = 0;
		this.price = 0;
	}

}
