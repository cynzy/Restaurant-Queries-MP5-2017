package ca.ece.ubc.cpen221.mp5;

import java.util.List;
import java.util.Set;

/**
 * Database - represents a database that stores information for websites which
 * include users reviewing businesses online.
 * 
 * Has methods for getting the sets of businesses, users, and reviews in the
 * database, as well as an alternative implementation to statistical learning
 * through kMean clustering. Instead of returning a String in Json format, this
 * implementation returns a list of sets representing each cluster.
 *
 */
public interface Database extends MP5Db<Object> {

	/**
	 * returns the set of businesses in this database. requires: this object is not
	 * null and businesses in the set are not null and have no null instance fields
	 *
	 * @param void
	 * 
	 * @return The set of businesses in this database
	 * 
	 */
	public Set<Business> getBusinessSet();

	/**
	 * returns the set of users in this database. requires: this object is not null
	 * and users in the set are not null and have no null instance fields
	 *
	 * @param void
	 * 
	 * @return The set of users in this database
	 * 
	 */
	public Set<User> getUserSet();

	/**
	 * returns the set of reviews in this database. requires: this object is not
	 * null and reviews in the set are not null and have no null instance fields
	 *
	 * @param void
	 * 
	 * @return The set of reviews in this database
	 * 
	 */
	public Set<Review> getReviewSet();

	/**
	 * Cluster objects into k clusters using k-means clustering
	 * 
	 * @param k
	 *            number of clusters to create (0 < k <= number of objects)
	 * @return a list of sets. Each set represents on cluster of businesses
	 */
	public List<Set<Business>> kMeansClusters_List(int k);

}
