package ca.ece.ubc.cpen221.mp5;

import javax.json.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;

public class YelpDB extends Database {

	private Set<Restaurant> restaurantSet;
	private Set<YelpUser> userSet;
	private Set<YelpReview> reviewSet;

	public YelpDB(String restaurantsJson, String usersJson, String reviewsJson) throws IOException {
		this.restaurantSet = new HashSet<>();
		this.userSet = new HashSet<>();
		this.reviewSet = new HashSet<>();

		parseRestaurants(restaurantsJson);
		parseUsers(usersJson);
		parseReviews(reviewsJson);

	}

	@Override
	public Set<Object> getMatches(String queryString) {
		return super.getMatches(queryString);
	}

	@Override
	public String kMeansClusters_json(int k) {
		Map<Restaurant, Cluster> clusteringMap = new HashMap<Restaurant, Cluster>();
		Set<Cluster> clusterSet = new HashSet<Cluster>();
		List<Cluster> emptyClusterList = new ArrayList<Cluster>();

		List<Coordinates> coordinateList = this.restaurantSet.stream().map(business -> business.getLocation())
				.map(location -> location.getCoordinates()).collect(Collectors.toList());

		double maxLong = coordinateList.stream().map(coordinates -> coordinates.getlongitude())
				.reduce((double) Integer.MIN_VALUE, (x, y) -> Double.max(x, y));
		double maxLat = coordinateList.stream().map(coordinates -> coordinates.getlatitude())
				.reduce((double) Integer.MIN_VALUE, (x, y) -> Double.max(x, y));
		double minLong = coordinateList.stream().map(coordinates -> coordinates.getlongitude())
				.reduce((double) Integer.MAX_VALUE, (x, y) -> Double.min(x, y));
		double minLat = coordinateList.stream().map(coordinates -> coordinates.getlatitude())
				.reduce((double) Integer.MAX_VALUE, (x, y) -> Double.min(x, y));

		do {

			clusterSet.clear();
			for (int i = 0; i < k; i++) {
				clusterSet.add(new Cluster((maxLong - minLong) * Math.random() + minLong,
						(maxLat - minLat) * Math.random() + minLat));
			}

			reAssignClusters(clusterSet, clusteringMap);

			emptyClusterList = clusterSet.stream().filter(cluster -> cluster.isEmpty()).collect(Collectors.toList());

		} while (emptyClusterList.isEmpty());

		List<Boolean> nonFinishedClustersList = new ArrayList<Boolean>();
		do {

			for (Cluster c : clusterSet) {
				c.adjustCentroid();
			}

			nonFinishedClustersList = clusterSet.stream().map(cluster -> cluster.isFinished())
					.filter(isFinished -> false).collect(Collectors.toList());
		
		} while (nonFinishedClustersList.isEmpty());

		int index = 0;
		JsonObjectBuilder j;
		JsonArrayBuilder array = Json.createArrayBuilder();
		for (Cluster c : clusterSet) {
			System.out.println("cluster: " + index);

			for (Business b : c.getBusinessSet()) {
				System.out.println(b.getName());
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

	public void reAssignClusters(Set<Cluster> clusterSet, Map<Restaurant, Cluster> clusteringMap) {

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
			}

			if (!closestCluster.equals(clusteringMap.get(b))) {

				clusteringMap.get(b).removeBusiness(b);
				closestCluster.addBusiness(b);
				clusteringMap.put(b, closestCluster);
			}
		}
	}

	@Override
	public ToDoubleBiFunction<MP5Db<Object>, String> getPredictorFunction(String user) {
		Map<String, Business> idMap = new HashMap<String, Business>();
		for (Business b : this.restaurantSet) {
			idMap.put(b.getBusinessID(), b);
		}

		User thisUser = this.userSet.stream().filter(listUser -> listUser.getUserID().equals(user)).reduce(null,
				(x, y) -> y);

		List<Double> priceList = thisUser.getReviewSet().stream().map(review -> review.getBusinessID())
				.map(businessID -> idMap.get(businessID)).map(business -> (double) business.getPrice())
				.collect(Collectors.toList());

		double sumPrice = priceList.stream().reduce(0.0, (x, y) -> x + y);

		double priceListSize = priceList.stream().reduce(0.0, (x, y) -> x + 1);

		double meanPrice = sumPrice / priceListSize;

		double meanRating = thisUser.getAverageRating();

		double s_xx = priceList.stream().reduce(0.0, (x, y) -> x + Math.pow(y - meanPrice, 2));

		List<Double> ratingList = thisUser.getReviewSet().stream().map(review -> (double) review.getStars())
				.collect(Collectors.toList());

		double s_yy = ratingList.stream().reduce(0.0, (x, y) -> x + Math.pow(y - meanRating, 2));

		double s_xy = 0;
		for (int i = 0; i < ratingList.size(); i++) {
			s_xy += Math.pow(ratingList.get(i) - meanRating, 2) * Math.pow(priceList.get(i) - meanPrice, 2);
		}

		return new YelpPredictorFunction(s_xx, s_yy, s_xy, meanPrice, meanRating);
	}

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

	private void parseReviews(String reviewsJson) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(reviewsJson));
		String line;
		Iterator<Restaurant> iterator;
		Iterator<YelpUser> iterator2;
		while ((line = bufferedReader.readLine()) != null) {
			JsonReader jsonReader = Json.createReader(new StringReader(line));
			JsonObject reviews = jsonReader.readObject();
			YelpReview review = new YelpReview(reviews);
			this.reviewSet.add(review);

			// adds review to corresponding YelpUser and Restaurant
			iterator = this.restaurantSet.iterator();
			while (iterator.hasNext()) {
				Restaurant current = iterator.next();
				if (current.getBusinessID().equals(review.getBusinessID())) {
					current.addReview(review);
					break;
				}
			}
			iterator2 = this.userSet.iterator();
			while (iterator2.hasNext()) {
				YelpUser current = iterator2.next();
				if (current.getUserID().equals(review.getUserID())) {
					current.addReview(review);
					break;
				}
			}

		}
		System.out.println(reviewSet.size());
		bufferedReader.close();
	}

	public Set<Restaurant> getRestaurantSet() {

		Set<Restaurant> copy = new HashSet<Restaurant>();
		copy.addAll(this.restaurantSet);

		return copy;
	}

}
