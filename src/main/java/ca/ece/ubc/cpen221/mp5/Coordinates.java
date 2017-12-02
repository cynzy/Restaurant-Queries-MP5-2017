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

	public double getlongitude() {
		return this.longitude;
	}

	public double getlatitude() {
		return this.latitude;
	}

	public double getDistance(Coordinates other) {
		double deltaLat = Math.toRadians(Math.abs(this.latitude - other.getlatitude()));
		double deltaLong = Math.toRadians(Math.abs(this.longitude - other.getlongitude()));

		double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(Math.toRadians(this.latitude))
				* Math.cos(Math.toRadians(other.getlatitude())) * Math.pow(Math.sin(deltaLong / 2), 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return c * EARTH_RADIUS;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Coordinates) {
			Coordinates other = (Coordinates) o;
			return this.latitude == other.getlatitude() && this.longitude == other.getlongitude();
		}

		return false;
	}

	@Override
	public int hashCode() {
		return (int) (this.latitude + this.longitude);

	}

	@Override
	public String toString() {
		return "Latitude:" + this.latitude + ", Longitude:" + this.longitude;
	}

}
