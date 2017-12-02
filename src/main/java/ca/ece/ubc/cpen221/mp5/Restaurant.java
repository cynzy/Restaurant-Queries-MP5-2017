package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;
import java.util.HashSet;
import java.util.Set;

/**
 * Restaurant - represents a restaurant to input into a database. Includes
 * information about the business' location, name, ID, etc.
 * 
 * Representation Invariants:
 *
 *- Same representation invariants as its supertype: Business
 *
 *- this.Categories must include the tag "Restaurants"
 * 
 * Abstraction Function:
 * 
 * - Inherits the abstraction function of Business
 *
 */
public class Restaurant extends Business {
	
	public Restaurant(JsonObject restaurant) {
		super(restaurant);
	}
}
