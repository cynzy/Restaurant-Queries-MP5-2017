package ca.ece.ubc.cpen221.mp5;

/*
 * Coordinates - represents a pair of longitude and latitude values to indicate the location of an object
 * 
 * Representation Invariants:
 * 
 * - no instance fields are null
 * 
 * - this.longitude is between -180 and 180 degrees
 * 
 * - this.latitude is between -90 and 90 degrees
 * 
 * Abstraction Function:
 * 
 * AF(longitude,latitude) = (longitude,latitude) such that -180 <= longitude <= 180 and -90 <= latitude <= 90
 * 
 */
public class Coordinates {

	private double longitude;
	private double latitude;
	private static final int EARTH_RADIUS = 6371000;

	/**
	 * Constructs a Coordinates object
	 * 
	 * requires: longitude is between -180 and 180 requires: latitude is between -90
	 * and 90
	 *
	 * @param longitude
	 *            The longitude of the coordinates
	 * @param latitude
	 *            The latitude of the coordinates
	 * 
	 */
	public Coordinates(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	/**
	 * Changes the longitude of this Coordinates object
	 *
	 * @param newlongitude
	 *            the new longitude for this Coordinates object to have
	 * 
	 * @return void
	 * 
	 */
	public void setlongitude(double newlongitude) {
		this.longitude = newlongitude;
	}

	/**
	 * Changes the latitude of this Coordinates object
	 *
	 * @param newlatitude
	 *            the new latitude for this Coordinates object to have
	 * 
	 * @return void
	 * 
	 */
	public void setlatitude(double newlatitude) {
		this.latitude = newlatitude;
	}

	/**
	 * returns the longitude of these coordinates. requires: this object is not null
	 *
	 * @param void
	 * 
	 * @return The longitude of these coordinates
	 * 
	 */
	public double getlongitude() {
		return this.longitude;
	}

	/**
	 * returns the latitude of these coordinates. requires: this object is not null
	 *
	 * @param void
	 * 
	 * @return The latitude of these coordinates
	 * 
	 */
	public double getlatitude() {
		return this.latitude;
	}

	/**
	 * computes the distance between these coordinates and another pair of
	 * coordinates. The distance is calculated using the Haversine formula.
	 * Required: this object is not null.
	 *
	 * @param other
	 *            the other set of coordinates used to calculate the distance
	 * 
	 * @return The distance between the two coordinates as comupted by the Haversine
	 *         Formula
	 * 
	 */
	public double getDistance(Coordinates other) {
		double deltaLat = Math.toRadians(this.latitude - other.getlatitude());
		double deltaLong = Math.toRadians(this.longitude - other.getlongitude());

		double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(Math.toRadians(this.latitude))
				* Math.cos(Math.toRadians(other.getlatitude())) * Math.pow(Math.sin(deltaLong / 2), 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return c * EARTH_RADIUS;
	}

	/**
	 * Compares the equality of this Coordinates object and another object. Returns
	 * true only if o is a Coordinates object with the same longitude and latitude.
	 * Requires: this coordinates object and o are not null.
	 *
	 * @param o
	 *            Object to be compared with this Coordinates object
	 * 
	 * @return true if the objects equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coordinates) {
			Coordinates other = (Coordinates) o;
			return this.latitude == other.getlatitude() && this.longitude == other.getlongitude();
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
		return (int) (this.latitude + this.longitude);

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
		return "Latitude:" + this.latitude + ", Longitude:" + this.longitude;
	}

}
