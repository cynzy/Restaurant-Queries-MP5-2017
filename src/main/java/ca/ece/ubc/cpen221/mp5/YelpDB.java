package ca.ece.ubc.cpen221.mp5;


import javax.json.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.function.ToDoubleBiFunction;

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

		for (int i = 0; i < k; i++) {
			clusterSet.add(new Cluster(360 * Math.random() - 180, 180 * Math.random() - 90));
		}

		while (true) {
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
				if (clusteringMap.containsKey(b)) {
					clusteringMap.get(b).removeBusiness(b);
				}
				closestCluster.addBusiness(b);
				clusteringMap.put(b, closestCluster);
			}

			boolean centroidChange = false;
			for (Cluster c : clusterSet) {
				centroidChange = c.adjustCentroid();
			}

			if (!centroidChange) {
				break;
			}
		}

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


	@Override
	public ToDoubleBiFunction<MP5Db<Object>, String> getPredictorFunction(String user) {
		return super.getPredictorFunction(user);
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

	private void parseUsers (String userJson) throws IOException {
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

	private void parseReviews (String reviewsJson) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(reviewsJson));
		String line;
		Iterator<Restaurant> iterator;
		Iterator<YelpUser> iterator2;
		while ((line = bufferedReader.readLine()) != null) {
			JsonReader jsonReader = Json.createReader(new StringReader(line));
			JsonObject reviews = jsonReader.readObject();
			YelpReview review = new YelpReview(reviews);
			this.reviewSet.add(review);

			//adds review to corresponding YelpUser and Restaurant
			iterator = this.restaurantSet.iterator();
			while (iterator.hasNext()){
				Restaurant current = iterator.next();
				if (current.getBusinessID().equals(review.getBusinessID())){
					current.addReview(review);
					break;
				}
			}
			iterator2 = this.userSet.iterator();
			while (iterator2.hasNext()){
				YelpUser current = iterator2.next();
				if (current.getUserID().equals(review.getUserID())){
					current.addReview(review);
					break;
				}
			}

		}
		System.out.println(reviewSet.size());
		bufferedReader.close();
	}


}
