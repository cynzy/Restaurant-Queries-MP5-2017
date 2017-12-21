package ca.ece.ubc.cpen221.mp5;

import ca.ece.ubc.cpen221.mp5.Query.MP5Query;

import javax.json.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;

/**
 * YelpDB - Represents a database for Yelp Restaurants, Reviews, and Users Parse
 * JSON string into sets of Restaurant, YelpReview, YelpUser dataset
 *
 * Representation Invariants:
 *
 * - this.restaurantSet is not null and only contains Restaurant datatypes
 *
 * - this.userSet is not null and only contains YelpUser datatypes
 *
 * - this.reviewSet is not null and only contains YelpReview datatypes
 *
 * - this.UserReviewMap maps userID to a set of reviews written under that
 * specific userID
 *
 * - this.RestaurantReviewMap maps businessID to a set of reviews written for
 * that specific Restaurant
 *
 * - all datatype sets cannot be modified (for this part for now)
 *
 * Abstraction Function:
 *
 * AF(restaurantsJsonString, usersJsonString, reviewsJsonString) ->
 * Set<Restaurant>, Set<YelpUser>, Set<YelpReview>, Map<String, Set<Review>>,
 * Map<String, Set<Review>>
 *
 *
 */

public class YelpDB implements Database {

	private Set<Restaurant> restaurantSet;
	private Set<YelpUser> userSet;
	private Set<YelpReview> reviewSet;

	private Map<String, Set<Review>> UserReviewMap;
	private Map<String, Set<Review>> RestaurantReviewMap;

	public YelpDB(String restaurantsJson, String usersJson, String reviewsJson) throws IOException {
		this.restaurantSet = new HashSet<>();
		this.userSet = new HashSet<>();
		this.reviewSet = new HashSet<>();

		this.UserReviewMap = new ConcurrentHashMap<>();
		this.RestaurantReviewMap = new ConcurrentHashMap<>();

		parseRestaurants(restaurantsJson);
		parseUsers(usersJson);
		parseReviews(reviewsJson);

	}

	/**
	 * Takes a JSON string of the restuarnats json data file and parses
	 * corresponding key values into specific instances of the Restaurant datatype
	 *
	 * Modifies this.restaurantSet
	 *
	 * @param restaurantsJson
	 *            JSON formatted string
	 * @return void
	 */
	private void parseRestaurants(String restaurantsJson) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(restaurantsJson));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			JsonReader jsonReader = Json.createReader(new StringReader(line));
			JsonObject restaurants = jsonReader.readObject();
			Restaurant restaurant = new Restaurant(restaurants);
			this.restaurantSet.add(restaurant);
		}
		bufferedReader.close();
	}

	/**
	 * Takes a JSON string of the user json data file and parses corresponding key
	 * values into specific instances of the YelpUser datatype
	 *
	 * Modifies this.userSet
	 *
	 * @param userJson
	 *            JSON formatted string
	 * @return void
	 */
	private void parseUsers(String userJson) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(userJson));
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			JsonReader jsonReader = Json.createReader(new StringReader(line));
			JsonObject users = jsonReader.readObject();
			YelpUser user = new YelpUser(users);
			this.userSet.add(user);
		}
		bufferedReader.close();
	}

	/**
	 * Takes a JSON string of the reviews json data file and parses corresponding
	 * key values into specific instances of the YelpReview datatype
	 *
	 * Modifies this.reviewsSet
	 *
	 * @param reviewsJson
	 *            JSON formatted string
	 * @return void
	 */
	private void parseReviews(String reviewsJson) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(reviewsJson));
		String line;

		while ((line = bufferedReader.readLine()) != null) {
			JsonReader jsonReader = Json.createReader(new StringReader(line));
			JsonObject reviews = jsonReader.readObject();
			YelpReview review = new YelpReview(reviews);
			this.reviewSet.add(review);

			// parsing UserReviewMap
			if (UserReviewMap.containsKey(review.getUserID())) {
				this.UserReviewMap.get(review.userID).add(review);
			} else {
				Set<Review> reviewSet = new HashSet<>();
				reviewSet.add(review);
				this.UserReviewMap.put(review.userID, reviewSet);
			}

			// parsing RestaurantReviewMap
			if (RestaurantReviewMap.containsKey(review.getBusinessID())) {
				this.RestaurantReviewMap.get(review.getBusinessID()).add(review);
			} else {
				Set<Review> reviewSet = new HashSet<>();
				reviewSet.add(review);
				this.RestaurantReviewMap.put(review.getBusinessID(), reviewSet);
			}

		}
		bufferedReader.close();
	}

	/**
	 * Perform a structured query and return the set of objects that matches the
	 * query
	 * 
	 * @param queryString
	 * @return the set of objects that matches the query
	 */
	@Override
	public Set<Object> getMatches(String queryString) {

			MP5Query query = new MP5Query(queryString);

			List<String> categories = query.getCategories();
			List<String> locations = query.getLocations();
			List<String> names = query.getNames();
			List<Integer> rating = query.getRating();
			List<Integer> price = query.getPrice();
			Set<Restaurant> restaurantQuerySet = this.restaurantSet;

			if (!categories.isEmpty()) {
				for (String category : categories) {
					restaurantQuerySet = restaurantQuerySet.stream()
							.filter(Restaurant -> Restaurant.getCategories().contains(category))
							.collect(Collectors.toSet());
				}
			}

			if (!locations.isEmpty()) {
				for (String location : locations) {
					restaurantQuerySet = restaurantQuerySet.stream()
							.filter(Restaurant -> Restaurant.getLocation().getNeighbourhoods().contains(location))
							.collect(Collectors.toSet());
				}

			}

			if (!names.isEmpty()) {
				for (String name : names) {
					restaurantQuerySet = restaurantQuerySet.stream()
							.filter(Restaurant -> Restaurant.getName().contains(name)).collect(Collectors.toSet());
				}
			}

			if (!rating.isEmpty()) {
				restaurantQuerySet = restaurantQuerySet.stream()
						.filter(Restaurant -> (Restaurant.getPrice() >= price.get(0)))
						.filter(Restaurant -> (Restaurant.getPrice() <= price.get(price.size() - 1)))
						.collect(Collectors.toSet());
			}

			if (!price.isEmpty()) {
				restaurantQuerySet = restaurantQuerySet.stream()
						.filter(Restaurant -> (Restaurant.getPrice() >= price.get(0)))
						.filter(Restaurant -> (Restaurant.getPrice() <= price.get(price.size() - 1)))
						.collect(Collectors.toSet());
			}

			return (Set<Object>) (Object) restaurantQuerySet;

	}

	/**
	 * Cluster objects into k clusters using k-means clustering
	 *
	 * @param k
	 *            number of clusters to create (0 < k <= number of objects)
	 * @return a List of Set of Businesses that are in one cluster
	 */
	@Override
	public List<Set<Business>> kMeansClusters_List(int k) {
		Map<Restaurant, Cluster> clusteringMap = new HashMap<Restaurant, Cluster>();
		Set<Cluster> clusterSet = new HashSet<Cluster>();
		List<Coordinates> coordinatesList = new ArrayList<Coordinates>();

		// creates centroids arbitrarily
		coordinatesList.addAll(restaurantSet.stream().map(business -> business.getLocation())
				.map(location -> location.getCoordinates()).collect(Collectors.toSet()));

		for (int i = 0; i < k; i++) {
			Coordinates coordinates = coordinatesList.get(i);
			clusterSet.add(new Cluster(coordinates.getlongitude(), coordinates.getlatitude()));
		}

		// reassigns the clusters
		reAssignClusters(clusterSet, clusteringMap);
		List<Boolean> nonFinishedClustersList = new ArrayList<Boolean>();

		// loops through the process of assigning businesses to the closest cluster and
		// adjusting their centroids until all businesses are not closer to a different
		// centroid than they are to their own
		do {

			for (Cluster c : clusterSet) {
				c.adjustCentroid();
			}
			reAssignClusters(clusterSet, clusteringMap);
			// check how many clusters did not adjust
			nonFinishedClustersList = clusterSet.stream().map(cluster -> cluster.isFinished())
					.filter(isFinished -> false).collect(Collectors.toList());

		} while (!nonFinishedClustersList.isEmpty());

		// assigns clusters to a list of sets
		List<Set<Business>> clusterList = new ArrayList<Set<Business>>();
		for (Cluster c : clusterSet) {
			clusterList.add(c.getBusinessSet());
		}

		return clusterList;

	}

	/**
	 * Cluster objects into k clusters using k-means clustering
	 * 
	 * @param k
	 *            number of clusters to create (0 < k <= number of objects)
	 * @return a String, in JSON format, that represents the clusters
	 */
	public String kMeansClusters_json(int k) {
		Map<Restaurant, Cluster> clusteringMap = new HashMap<Restaurant, Cluster>();
		Set<Cluster> clusterSet = new HashSet<Cluster>();
		List<Coordinates> coordinatesList = new ArrayList<Coordinates>();

		// assigns centroids arbitrarily
		coordinatesList.addAll(restaurantSet.stream().map(business -> business.getLocation())
				.map(location -> location.getCoordinates()).collect(Collectors.toSet()));

		for (int i = 0; i < k; i++) {
			Coordinates coordinates = coordinatesList.get(i);
			clusterSet.add(new Cluster(coordinates.getlongitude(), coordinates.getlatitude()));
		}

		reAssignClusters(clusterSet, clusteringMap);

		List<Boolean> nonFinishedClustersList = new ArrayList<Boolean>();

		// loops through the process of assigning businesses to the closest cluster and
		// adjusting their centroids until all businesses are not closer to a different
		// centroid than they are to their own
		do {
			for (Cluster c : clusterSet) {
				c.adjustCentroid();
			}
			reAssignClusters(clusterSet, clusteringMap);
			nonFinishedClustersList = clusterSet.stream().map(cluster -> cluster.isFinished())
					.filter(isFinished -> false).collect(Collectors.toList());
		} while (!nonFinishedClustersList.isEmpty());

		// parsing final clusters into JSON formatted string
		int index = 0;
		JsonObjectBuilder j;
		JsonArrayBuilder array = Json.createArrayBuilder();

		for (Cluster c : clusterSet) {
			for (Business b : c.getBusinessSet()) {

				j = javax.json.Json.createObjectBuilder();
				j.add("x", b.getLocation().getCoordinates().getlatitude());
				j.add("y", b.getLocation().getCoordinates().getlongitude());
				j.add("name", b.getName());
				j.add("cluster", index);
				j.add("weight", 1.0);
				array.add(j.build());
			}
			index++;
		}

		String json = array.build().toString();
		return json;
	}

	/**
	 * Adjusts the clusters by assigning each business to the cluster with the
	 * closest centroid. Helper function for k-means clustering methods.
	 *
	 * @param clusterSet
	 *            the Set of clusters in k means clustering
	 * @param clusteringMap
	 *            the map detailing which cluster each business is in
	 */
	private void reAssignClusters(Set<Cluster> clusterSet, Map<Restaurant, Cluster> clusteringMap) {

		//loops through all restaurants
		for (Restaurant b : this.restaurantSet) {
			double minDistance = Integer.MAX_VALUE;
			//loops through all clusters and assigns a restaurant to its closest cluster
			Cluster closestCluster = new Cluster(0, 0);
			for (Cluster c : clusterSet) {
				double currentDistance = b.getLocation().getCoordinates().getDistance(c.getCentroid());
				if (minDistance > currentDistance) {
					closestCluster = c;
					minDistance = currentDistance;
				}
			}
			
			//checks if the restaurant has not yet been assigned to a cluster
			if (!clusteringMap.containsKey(b)) {
				clusteringMap.put(b, closestCluster);
				closestCluster.addBusiness(b);
			}

			if (!closestCluster.equals(clusteringMap.get(b))) {

				clusteringMap.get(b).removeBusiness(b);
				closestCluster.addBusiness(b);
				clusteringMap.put(b, closestCluster);
			}
		}
	}

	/**
	 * 
	 * @param user
	 *            represents a user_id in the database
	 * @return a function that predicts the user's ratings for objects (of type T)
	 *         in the database of type MP5Db<T>. The function that is returned takes
	 *         two arguments: one is the database and other other is a String that
	 *         represents the id of an object of type T.
	 */
	@Override
	public ToDoubleBiFunction<MP5Db<Object>, String> getPredictorFunction(String user) {
		Map<String, Business> idMap = new HashMap<String, Business>();
		for (Business b : this.restaurantSet) {
			idMap.put(b.getBusinessID(), b);
		}

		//finds a list of reviews by the specified user
		List<Review> reviewsByUser = this.reviewSet.stream().filter(review -> review.getUserID().equals(user))
				.collect(Collectors.toList());

		// finds a list of prices of the restaurants reviewed by the user
		List<Double> priceList = reviewsByUser.stream().map(review -> review.getBusinessID())
				.map(businessID -> idMap.get(businessID)).map(business -> (double) business.getPrice())
				.collect(Collectors.toList());

		double sumPrice = priceList.stream().reduce(0.0, (x, y) -> x + y);

		double meanPrice = sumPrice / priceList.size();

		// finds a list of all ratings by the user
		List<Double> ratingList = this.reviewSet.stream().filter(review -> review.getUserID().equals(user))
				.map(review -> (double) review.getStars()).collect(Collectors.toList());

		double sumRating = ratingList.stream().reduce(0.0, (x, y) -> x + y);

		double meanRating = sumRating / ratingList.size();

		//statistical analysis variables
		double s_xx = priceList.stream().reduce(0.0, (x, y) -> x + Math.pow(y - meanPrice, 2));

		double s_yy = ratingList.stream().reduce(0.0, (x, y) -> x + Math.pow(y - meanRating, 2));

		double s_xy = 0;
		for (int i = 0; i < ratingList.size(); i++) {
			s_xy += (ratingList.get(i) - meanRating) * (priceList.get(i) - meanPrice);
		}

		return new PredictorFunction(s_xx, s_yy, s_xy, meanPrice, meanRating);
	}

	/**
	 * returns the set of businesses in this database. requires: this object is not
	 * null and businesses in the set are not null and have no null instance fields
	 *
	 * @param void
	 * 
	 * @return The set of businesses in this database
	 * 
	 */
	@Override
	public synchronized Set<Business> getBusinessSet() {
		Set<Business> copy = new HashSet<Business>();
		copy.addAll(this.restaurantSet);

		return copy;
	}

	/**
	 * returns the set of users in this database. requires: this object is not null
	 * and users in the set are not null and have no null instance fields
	 *
	 * @param void
	 * 
	 * @return The set of users in this database
	 * 
	 */
	@Override
	public synchronized Set<User> getUserSet() {
		Set<User> copy = new HashSet<User>();
		copy.addAll(this.userSet);

		return copy;
	}

	/**
	 * returns the set of reviews in this database. requires: this object is not
	 * null and reviews in the set are not null and have no null instance fields
	 *
	 * @param void
	 * 
	 * @return The set of reviews in this database
	 * 
	 */
	@Override
	public synchronized Set<Review> getReviewSet() {
		Set<Review> copy = new HashSet<Review>();
		copy.addAll(this.reviewSet);

		return copy;
	}

	/**
	 * Adds a user to this database. requires: this database is not null and user is
	 * not null
	 * 
	 * @param user
	 *            the user to be added to this database
	 * 
	 * @return void
	 * 
	 */
	public synchronized void addUser(YelpUser user) {
		this.userSet.add(user);
	}

	/**
	 * Adds a restaurant to this database. requires: this database is not null and
	 * restaurant is not null
	 * 
	 * @param restaurant
	 *            the restaurant to be added to this database
	 * 
	 * @return void
	 * 
	 */
	public synchronized void addRestaurant(Restaurant restaurant) {
		this.restaurantSet.add(restaurant);
	}

	/**
	 * Adds a review to this database. requires: this database is not null and
	 * review is not null
	 * 
	 * @param review
	 *            the review to be added to this database
	 * 
	 * @return void
	 * 
	 */
	public synchronized void addReview(YelpReview review) {
		this.reviewSet.add(review);

		YelpUser user = this.userSet.stream().filter(u -> u.getUserID().equals(review.getUserID())).reduce(null,
				(x, y) -> y);
		Restaurant restaurant = this.restaurantSet.stream()
				.filter(r -> r.getBusinessID().equals(review.getBusinessID())).reduce(null, (x, y) -> y);

		user.adjustRating(review.getStars());
		restaurant.adjustRating(review.getStars());

	}

}