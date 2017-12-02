package ca.ece.ubc.cpen221.mp5;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Cluster - Represents a group of businesses and the location of their centroid. Used to find the k-means clustering of a set of businesses
 * 
 * Representation Invariants:
 * 
 * - this.businessSet has to include all the businesses in the cluser
 * 
 * - businesses in this.businessSet are unique to this cluster for a specific calculation of k-means clustering
 * 
 * - this.centroid is the average longitude and latitude of the values in this.businessSet after adjustCentroid() is called
 * 
 * - this.centroid has to be the closes cluster to the businesses in this.businessSet out of the centroids of all other clusters within a computation of k-means clustering until adjustCentroid() is called
 * 
 * - this.isFinished is true if the centroid remained the same after adjustCentroi() was called. False otherwise
 * 
 * - this.centroid must be unique to this cluster
 * 
 * - all instance fields are not null
 * 
 * Abstraction Function:
 * 
 * - AF(businessSet, centroid) = { Business } such that the coordinates of each Business are closer to centroid than any other centroid
 * 
 */
public class Cluster {

	private Set<Business> businessSet;
	private Coordinates centroid;
	private boolean isFinished;

	/**
	 * Constructs a Cluster object. Required: x is between -180 and 180, and y is
	 * between -90 and 90.
	 *
	 * @param x
	 *            The longitude of the initial centroid
	 * @param y
	 *            The latitude of the initial centroid
	 * 
	 */
	public Cluster(double x, double y) {
		this.businessSet = new HashSet<Business>();
		this.centroid = new Coordinates(x, y);
		this.isFinished = false;
	}

	/**
	 * gets the set of businesses in this cluster and returns it
	 *
	 * @param void
	 * 
	 * @return the set of businesses in this cluster
	 * 
	 */
	public Set<Business> getBusinessSet() {
		Set<Business> copy = new HashSet<Business>();
		copy.addAll(this.businessSet);

		return copy;
	}

	/**
	 * Adds a business to this cluster. Does nothing if this cluster already
	 * contains this business.
	 *
	 * requires: business is not null
	 *
	 * @param business
	 *            The business to be added to this cluster
	 * @return void
	 * 
	 */

	public void addBusiness(Business business) {
		this.businessSet.add(business);
	}

	/**
	 * Removes a business from this cluster. Does nothing if this cluster does not
	 * contain this business.
	 *
	 * requires: business is not null
	 *
	 * @param business
	 *            The business to be removed from this cluster
	 * @return void
	 * 
	 */
	public void removeBusiness(Business business) {
		this.businessSet.remove(business);
	}

	/**
	 * Checks if there are businesses contained in this cluster. If the businessSet
	 * of this cluster is empty, it will return true. False otherwise
	 *
	 * requires: cluster is not null
	 *
	 * @param void
	 * 
	 * @return A boolean indicating whether the cluster is empty or not
	 * 
	 */
	public boolean isEmpty() {
		return this.businessSet.isEmpty();
	}

	/**
	 * Adjusts the centroid of this cluster to be the true centroid. When businesses
	 * are added to this cluster, the centroid does not adjust automatically (for
	 * efficiency purposes). Calling this method will set the center to have the
	 * average longitude and average latitude of the locations of all businesses in
	 * this cluster. Requires: Cluster is not null and not empty.
	 *
	 * @param void
	 * 
	 * @return void
	 * 
	 */
	public void adjustCentroid() {

		List<Coordinates> coordinateList = this.businessSet.stream().map(business -> business.getLocation())
				.map(location -> location.getCoordinates()).collect(Collectors.toList());

		double longAverage = coordinateList.stream().map(coordinate -> coordinate.getlongitude()).reduce(0.0,
				(x, y) -> x + y) / this.businessSet.size();
		double latAverage = coordinateList.stream().map(coordinate -> coordinate.getlatitude()).reduce(0.0,
				(x, y) -> x + y) / this.businessSet.size();

		Coordinates previousCentroid = new Coordinates(this.centroid.getlongitude(), this.centroid.getlatitude());
		this.centroid = new Coordinates(longAverage, latAverage);

		this.isFinished = this.centroid.equals(previousCentroid);
	}

	/**
	 * returns true if the centroid of the cluster did not change after
	 * adjustCentroid() was called. Requires: this object is not null.
	 *
	 * @param void
	 * 
	 * @return true if the centroid of the object did not change after
	 *         adjustCentroid() was called. False otherwise.
	 * 
	 */
	public boolean isFinished() {
		return this.isFinished;
	}

	/**
	 * returns the centroid of this cluster. The centroid is returned as a
	 * Coordinates object. requires: this object is not null
	 *
	 * @param void
	 * 
	 * @return The centroid of this cluster as a pair of coordinates
	 * 
	 */
	public Coordinates getCentroid() {
		Coordinates copy = new Coordinates(this.centroid.getlongitude(), this.centroid.getlatitude());
		return copy;
	}

	/**
	 * Compares the equality of this Cluster object and another object. Returns true
	 * only if o is a Cluster with the same centroid. Requires: this cluster and o
	 * are not null.
	 *
	 * @param o
	 *            Object to be compared with this cluster
	 * 
	 * @return true if the objects equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Cluster) {
			Cluster other = (Cluster) o;
			return this.getCentroid().equals(other.getCentroid());
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
		return this.centroid.hashCode();
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
		return "Centroid:" + this.centroid.toString() + "|| Set:" + this.businessSet.toString();
	}

}
