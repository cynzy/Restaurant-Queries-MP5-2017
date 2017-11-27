package ca.ece.ubc.cpen221.mp5;

public class Coordinates {

	private double longitude;
	private double latitude;
	private static final int EARTH_RADIUS = 6371000;

	public Coordinates(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public void setlongitude(double newlongitude) {
		this.longitude = newlongitude;
	}

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
		
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		
		return c * EARTH_RADIUS;
	}
	
	

}
