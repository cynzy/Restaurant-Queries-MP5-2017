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

	/**
	 * Constructs a Location object
	 *
	 * @param void
	 * 
	 */
	public Location() {
		this.city = new String();
		this.coordinates = new Coordinates(0, 0);
		this.address = new String();
		this.neighbourhood = new HashSet<String>();
		this.school = new HashSet<String>();
		this.state = new String();
	}

	/**
	 * sets the coordinates of this location. requires: this object is not null, x
	 * is between -180 and 180, and y is between -90 and 90
	 *
	 * @param x
	 *            longitude of this location
	 * @param y
	 *            the latitude of this location
	 * 
	 * @return void
	 * 
	 */
	public void setCoordinates(double x, double y) {
		this.coordinates = new Coordinates(x, y);
	}

	/**
	 * sets the state of this location. requires: this object is not null and state
	 * is not null and is in proper format.
	 *
	 * @param state
	 *            the state set for this location
	 * 
	 * @return void
	 * 
	 */
	public void setState(String state) {
		this.state = state;
	}

	public void removeNeighbourhood(String neighbourhood) {
		this.neighbourhood.remove(neighbourhood);
	}

	/**
	 * sets the set of neighourhoods of this location. requires: this object is not
	 * null, neighbourhoods is not null, and no Strings in neighbourhoods are null.
	 *
	 * @param neighbouhoods
	 *            the set of neighbourhoods set for this location
	 * 
	 * @return void
	 * 
	 */
	public void setNeighbourhoods(Set<String> neighbourhoods) {
		this.neighbourhood.addAll(neighbourhoods);
	}

	public void removeSchool(String school) {
		this.school.remove(school);
	}

	/**
	 * sets the set of schools of this location. requires: this object is not
	 * null, schools is not null, and no Strings in schools are null.
	 *
	 * @param schools
	 *            the set of schools set for this location
	 * 
	 * @return void
	 * 
	 */
	public void setSchools(Set<String> schools) {
		this.school.addAll(schools);
	}

	/**
	 * sets the address of this location. requires: this object is not null and
	 * address is not null and is in proper format.
	 *
	 * @param address
	 *            the address set for this location
	 * 
	 * @return void
	 * 
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * sets the city of this location. requires: this object is not null and city is
	 * not null and is in proper format.
	 *
	 * @param city
	 *            the city set for this location
	 * 
	 * @return void
	 * 
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * returns the longitude latitude pair of this location. requires: this object
	 * is not null
	 *
	 * @param void
	 * 
	 * @return The coordinates of this location
	 * 
	 */
	public Coordinates getCoordinates() {
		Coordinates copy = new Coordinates(this.coordinates.getlongitude(), this.coordinates.getlatitude());

		return copy;
	}

	/**
	 * returns the set of neighbourhoods this location is in. requires: this object
	 * is not null and neighbourhood is not empty
	 *
	 * @param void
	 * 
	 * @return The set of neighbourhoods this location is found in
	 * 
	 */
	public Set<String> getNeighbourhoods() {
		Set<String> copy = new HashSet<String>();
		copy.addAll(this.neighbourhood);

		return copy;
	}

	/**
	 * returns the city this location is in. requires: this object is not null and
	 * city is not empty
	 *
	 * @param void
	 * 
	 * @return The city this location is found in
	 * 
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * returns the set of schools that are near this location. requires: this object
	 * is not null and school is not empty
	 *
	 * @param void
	 * 
	 * @return The set of schools near this location
	 * 
	 */
	public Set<String> getSchool() {
		Set<String> copy = new HashSet<String>();
		copy.addAll(this.school);

		return copy;
	}

	/**
	 * returns the address of this location. requires: this object is not null and
	 * address is not empty
	 *
	 * @param void
	 * 
	 * @return The address of this location
	 * 
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * returns the state this location is in. requires: this object is not null and
	 * state is not empty
	 *
	 * @param void
	 * 
	 * @return The state this location is found in
	 * 
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * Compares the equality of this Location object and another object. Returns
	 * true only if o is a Location object with the same coordinates. Requires: this
	 * Location object and o are not null.
	 *
	 * @param o
	 *            Object to be compared with this location
	 * 
	 * @return true if the objects equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Location) {
			Location other = (Location) o;

			return this.getCoordinates().equals(other.getCoordinates());
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
		return this.neighbourhood.hashCode();
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
		return this.address + ", " + this.city + ", " + this.state + ", " + "|| Coordinates:"
				+ this.coordinates.toString();
	}

}
