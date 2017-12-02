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

    private Map<String,Set<Review>> UserReviewMap;
    private Map<String,Set<Review>> RestaurantReviewMap;

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

		while ((line = bufferedReader.readLine()) != null) {
			JsonReader jsonReader = Json.createReader(new StringReader(line));
			JsonObject reviews = jsonReader.readObject();
			YelpReview review = new YelpReview(reviews);
			this.reviewSet.add(review);

			//parsing UserReviewMap
			if(UserReviewMap.containsKey(review.getUserID())){
			    this.UserReviewMap.get(review.userID).add(review);
            } else {
			    Set<Review> reviewSet = new HashSet<>();
			    reviewSet.add(review);
			    this.UserReviewMap.put(review.userID,reviewSet);
            }

            //parsing RestaurantReviewMap
            if(RestaurantReviewMap.containsKey(review.getBusinessID())){
                this.RestaurantReviewMap.get(review.getBusinessID()).add(review);
            } else {
                Set<Review> reviewSet = new HashSet<>();
                reviewSet.add(review);
                this.RestaurantReviewMap.put(review.getBusinessID(),reviewSet);
            }

		}
		bufferedReader.close();
	}


	@Override
	public Set<Object> getMatches(String queryString) {
		return null;
	}

	@Override
	public List<Set<Business>> kMeansClusters_List(int k) {
		Map<Restaurant, Cluster> clusteringMap = new HashMap<Restaurant, Cluster>();
		Set<Cluster> clusterSet = new HashSet<Cluster>();
		List<Cluster> emptyClusterList;

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

		} while (!emptyClusterList.isEmpty());

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
		for( Cluster c: clusterSet) {
			clusterList.add(c.getBusinessSet());
		}
		
		return clusterList;
		
	}

	public String kMeansClusters_json(int k) {
		Map<Restaurant, Cluster> clusteringMap = new HashMap<Restaurant, Cluster>();
		Set<Cluster> clusterSet = new HashSet<Cluster>();
		List<Cluster> emptyClusterList;

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

		} while (!emptyClusterList.isEmpty());

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

		List<Double> priceList = this.reviewSet.stream().filter(review -> review.getUserID().equals(user))
				.map(review -> review.getBusinessID()).map(businessID -> idMap.get(businessID))
				.map(business -> (double) business.getPrice()).collect(Collectors.toList());

		double sumPrice = priceList.stream().reduce(0.0, (x, y) -> x + y);

		double priceListSize = priceList.stream().reduce(0.0, (x, y) -> x + 1);

		double meanPrice = sumPrice / priceListSize;

		double meanRating = this.userSet.stream().filter(thisuser -> thisuser.getUserID().equals(user))
				.map(thisuser -> thisuser.getAverageRating()).reduce(0.0, (x, y) -> y);

		double s_xx = priceList.stream().reduce(0.0, (x, y) -> x + Math.pow(y - meanPrice, 2));

		List<Double> ratingList = this.reviewSet.stream().filter(review -> review.getUserID().equals(user))
				.map(review -> (double) review.getStars()).collect(Collectors.toList());

		double s_yy = ratingList.stream().reduce(0.0, (x, y) -> x + Math.pow(y - meanRating, 2));

		double s_xy = 0;
		for (int i = 0; i < ratingList.size(); i++) {
			s_xy += Math.pow(ratingList.get(i) - meanRating, 2) * Math.pow(priceList.get(i) - meanPrice, 2);
		}

		return new PredictorFunction(s_xx, s_yy, s_xy, meanPrice, meanRating);
	}

	@Override
	public Set<Business> getBusinessSet() {
		Set<Business> copy = new HashSet<Business>();
		copy.addAll(this.restaurantSet);

		return copy;
	}

	@Override
	public Set<User> getUserSet() {
		Set<User> copy = new HashSet<User>();
		copy.addAll(this.userSet);

		return copy;
	}

	@Override
	public Set<Review> getReviewSet() {
		Set<Review> copy = new HashSet<Review>();
		copy.addAll(this.reviewSet);

		return copy;
	}

}