package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.Set;

/*
 * Location - Represents the geographical location of an object
 * 
 * Representation Invariants:
 * 
 * - all instance fields are not null
 * 
 * - this.coordinates has to match the coordinates of the address
 * 
 * - this.coordinates has to be one of the coordinates that are associated with this.state, this.city, this.neighbourhood, this.school, and this.address
 * 
 * - this.city has to be a city in this.state
 * 
 * - this.neighbourhood has to be a neighbourhood in this.city
 * 
 * - this.school has to be a school in this.city
 * 
 * - this.address has to be an address within this.neighbourhood
 * 
 * Abstraction Function:
 * 
 * - this.coordinates represents the longitude and latitude values of the location of this object
 * 
 * - this.state represents the state the object is in
 * 
 * - this.neighbourhood represents a set of neighbourhood the object is located in
 * 
 * - this.school represents a set of schools the object is located in/near
 * 
 * - this.address represents the street address of the object
 * 
 * - this.city represents the city the object is located in
 * 
 */
public class Location {

	private Coordinates coordinates;
	private String state;
	private Set<String> neighbourhood;
	private Set<String> school;
	private String address;
	private String city;

	public Location() {
		this.city = new String();
		this.coordinates = new Coordinates(0, 0);
		this.address = new String();
		this.neighbourhood = new HashSet<String>();
		this.school = new HashSet<String>();
		this.state = new String();
	}

	public void setCoordinates(double x, double y) {
		this.coordinates = new Coordinates(x, y);
	}

	public void setState(String state) {
		this.state = state;
	}

	public void removeNeighbourhood(String neighbourhood) {
		this.neighbourhood.remove(neighbourhood);
	}

	public void setNeighbourhoods(Set<String> neighbourhoods) {
		this.neighbourhood.addAll(neighbourhoods);
	}
	
	public void removeSchool( String school ) {
		this.school.remove(school);
	}

	public void setSchools(Set<String> schools) {
		this.school.addAll(schools);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Coordinates getCoordinates() {
		Coordinates copy = new Coordinates(this.coordinates.getlongitude(), this.coordinates.getlatitude());

		return copy;
	}

	public Set<String> getNeighbourhoods() {
		Set<String> copy = new HashSet<String>();
		copy.addAll(this.neighbourhood);

		return copy;
	}

	public String getCity() {
		return this.city;
	}

	public Set<String> getSchool() {
		Set<String> copy = new HashSet<String>();
		copy.addAll(this.school);

		return copy;
	}

	public String getAddress() {
		return this.address;
	}

	public String getState() {
		return this.state;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Location) {
			Location other = (Location) o;

			return this.getCoordinates().equals(other.getCoordinates());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return this.neighbourhood.hashCode();
	}

	@Override
	public String toString() {
		return this.address + ", " + this.city + ", " + this.state + ", " + "|| Coordinates:"
				+ this.coordinates.toString();
	}

}
