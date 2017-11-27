package ca.ece.ubc.cpen221.mp5;

import java.awt.Point;

public class Location {
	
	private Coordinates coordinates;
	private String state;
	private String neighbourhood;
	private String school;
	private String address;
	private String city;
	
	public Location() {
		this.city = new String();
		this.coordinates = new Coordinates( 0,0);
		this.address = new String();
		this.neighbourhood = new String();
		this.school = new String();
		this.state = new String();
	}
	
	public void setCoordinates( double x, double y) {
		this.coordinates = new Coordinates(x, y);
	}
	
	public void setState( String state ) {
		this.state = state;
	}
	
	public void setNeighbourhood( String neighbourhood ) {
		this.neighbourhood = neighbourhood;
	}
	
	public void setSchool( String school ) {
		this.school = school;
	}
	
	public void setAddress( String address ) {
		this.address = address;
	}
	
	public void setCity( String city ) {
		this.city = city;
	}
	
	public Coordinates getCoordinates() {
		Coordinates copy = new Coordinates(this.coordinates.getX(), this.coordinates.getY());
		
		return copy;
	}
	
	public String getNeighbourhood() {
		return this.neighbourhood;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getSchool() {
		return this.school;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public String getState() {
		return this.state;
	}

}
