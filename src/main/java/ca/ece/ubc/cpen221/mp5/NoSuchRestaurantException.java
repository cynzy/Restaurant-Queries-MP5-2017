package ca.ece.ubc.cpen221.mp5;

public class NoSuchRestaurantException extends Exception {

	public NoSuchRestaurantException() {
		super( "no such restaurant" );
	}
}
