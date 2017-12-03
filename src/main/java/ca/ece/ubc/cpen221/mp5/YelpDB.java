package ca.ece.ubc.cpen221.mp5;

import javax.json.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;

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
	 * Takes a JSON string of the restuarnats json data file
	 * and parses corresponding key values into specific
	 * instances of the Restaurant datatype
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
	 * Takes a JSON string of the user json data file
	 * and parses corresponding key values into specific
	 * instances of the YelpUser datatype
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
	 * Takes a JSON string of the reviews json data file
	 * and parses corresponding key values into specific
	 * instances of the YelpReview datatype
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

	@Override
	public Set<Object> getMatches(String queryString) {
		return null;
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
		coordinatesList.addAll(restaurantSet.stream().map(business -> business.getLocation())
				.map(location -> location.getCoordinates()).collect(Collectors.toSet()));

		for (int i = 0; i < k; i++) {
			Coordinates coordinates = coordinatesList.get(i);
			clusterSet.add(new Cluster(coordinates.getlongitude(), coordinates.getlatitude()));
		}

		reAssignClusters(clusterSet, clusteringMap);

		List<Boolean> nonFinishedClustersList = new ArrayList<Boolean>();
		do {

			for (Cluster c : clusterSet) {
				c.adjustCentroid();
			}
			reAssignClusters(clusterSet, clusteringMap);
			nonFinishedClustersList = clusterSet.stream().map(cluster -> cluster.isFinished())
					.filter(isFinished -> false).collect(Collectors.toList());

		} while (!nonFinishedClustersList.isEmpty());

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
		coordinatesList.addAll(restaurantSet.stream().map(business -> business.getLocation())
				.map(location -> location.getCoordinates()).collect(Collectors.toSet()));

		for (int i = 0; i < k; i++) {
			Coordinates coordinates = coordinatesList.get(i);
			clusterSet.add(new Cluster(coordinates.getlongitude(), coordinates.getlatitude()));
		}

		reAssignClusters(clusterSet, clusteringMap);

		List<Boolean> nonFinishedClustersList = new ArrayList<Boolean>();
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
		Cluster bullshit = new Cluster(0, 0);
		Cluster fuckthis = new Cluster(0, 0);
		for (Cluster c : clusterSet) {
			if (index == 1) {
				bullshit = c;
			}
			if (index == 3) {
				fuckthis = c;
			}
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

	private void reAssignClusters(Set<Cluster> clusterSet, Map<Restaurant, Cluster> clusteringMap) {

		for (Restaurant b : this.restaurantSet) {
			double minDistance = Integer.MAX_VALUE;
			Cluster closestCluster = new Cluster(0, 0);
			for (Cluster c : clusterSet) {
				double currentDistance = b.getLocation().getCoordinates().getDistance(c.getCentroid());
				if (minDistance > currentDistance) {
					closestCluster = c;
					minDistance = currentDistance;
				}
			}
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

		List<Review> reviewsByUser = this.reviewSet.stream().filter(review -> review.getUserID().equals(user))
				.collect(Collectors.toList());

		List<Double> priceList = reviewsByUser.stream().map(review -> review.getBusinessID())
				.map(businessID -> idMap.get(businessID)).map(business -> (double) business.getPrice())
				.collect(Collectors.toList());

		double sumPrice = priceList.stream().reduce(0.0, (x, y) -> x + y);

		double meanPrice = sumPrice / priceList.size();

		List<Double> ratingList = this.reviewSet.stream().filter(review -> review.getUserID().equals(user))
				.map(review -> (double) review.getStars()).collect(Collectors.toList());

		double sumRating = ratingList.stream().reduce(0.0, (x, y) -> x + y);

		double meanRating = sumRating / ratingList.size();

		double s_xx = priceList.stream().reduce(0.0, (x, y) -> x + Math.pow(y - meanPrice, 2));

		double s_yy = ratingList.stream().reduce(0.0, (x, y) -> x + Math.pow(y - meanRating, 2));

		double s_xy = 0;
		for (int i = 0; i < ratingList.size(); i++) {
			s_xy += (ratingList.get(i) - meanRating) * (priceList.get(i) - meanPrice);
		}

		System.out.println(s_xx + "||" + s_yy + "||" + s_xy + "||" + meanPrice + "||" + meanRating);
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
	public Set<Business> getBusinessSet() {
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
	public Set<User> getUserSet() {
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
	public Set<Review> getReviewSet() {
		Set<Review> copy = new HashSet<Review>();
		copy.addAll(this.reviewSet);

		return copy;
	}

}