package ca.ece.ubc.cpen221.mp5;

import ca.ece.ubc.cpen221.mp5.Query.MP5Query;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.util.*;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;

public class Database implements MP5Db<Object> {

	private Set<Business> businessSet;
	private Set<User> userSet;
	private Set<Review> reviewSet;

	public Database() {
		this.businessSet = new HashSet<>();
		this.userSet = new HashSet<>();
		this.reviewSet = new HashSet<>();
	}

	@Override
	public Set<Object> getMatches(String queryString) {
		try {
			MP5Query query = new MP5Query(queryString);
			Set<Object> set = new HashSet<>(query.getRestaurantsSet());
			return set;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String kMeansClusters_json(int k) {
		Map<Business, Cluster> clusteringMap = new HashMap<Business, Cluster>();
		Set<Cluster> clusterSet = new HashSet<Cluster>();

		List<Coordinates> coordinateList = this.businessSet.stream().map(business -> business.getLocation())
				.map(location -> location.getCoordinates()).collect(Collectors.toList());

		double maxLong = coordinateList.stream().map( coordinates -> coordinates.getlongitude()).reduce(Double.MIN_VALUE, (x,y) -> Double.max(x, y));
		double maxLat = coordinateList.stream().map( coordinates -> coordinates.getlatitude()).reduce(Double.MIN_VALUE, (x,y) -> Double.max(x, y));
		double minLong = coordinateList.stream().map( coordinates -> coordinates.getlongitude()).reduce(Double.MAX_VALUE, (x,y) -> Double.min(x, y));
		double minLat = coordinateList.stream().map( coordinates -> coordinates.getlatitude()).reduce(Double.MAX_VALUE, (x,y) -> Double.min(x, y));

		for (int i = 0; i < k; i++) {
			clusterSet.add(new Cluster(( maxLong - minLong )* Math.random() + minLong, (maxLat - minLat) * Math.random() + minLat));
		}

		while (true) {
			for (Business b : this.businessSet) {
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
			//	centroidChange = c.adjustCentroid();
			}

			if (!centroidChange) {
				break;
			}
		}

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


	public List<Set<Business>> kMeansClusters(int k) {
		Map<Business, Cluster> clusteringMap = new HashMap<Business, Cluster>();
		Set<Cluster> clusterSet = new HashSet<Cluster>();

		List<Coordinates> coordinateList = this.businessSet.stream().map(business -> business.getLocation())
				.map(location -> location.getCoordinates()).collect(Collectors.toList());
		
		double maxLong = coordinateList.stream().map( coordinates -> coordinates.getlongitude()).reduce(Double.MIN_VALUE, (x,y) -> Double.max(x, y));
		double maxLat = coordinateList.stream().map( coordinates -> coordinates.getlatitude()).reduce(Double.MIN_VALUE, (x,y) -> Double.max(x, y));
		double minLong = coordinateList.stream().map( coordinates -> coordinates.getlongitude()).reduce(Double.MAX_VALUE, (x,y) -> Double.min(x, y));
		double minLat = coordinateList.stream().map( coordinates -> coordinates.getlatitude()).reduce(Double.MAX_VALUE, (x,y) -> Double.min(x, y));
		
		for (int i = 0; i < k; i++) {
			clusterSet.add(new Cluster(( maxLong - minLong )* Math.random() + minLong, (maxLat - minLat) * Math.random() + minLat));
		}

		while (true) {
			for (Business b : this.businessSet) {
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
			//	centroidChange = c.adjustCentroid();
			}

			if (!centroidChange) {
				break;
			}
		}

		List<Set<Business>> kMeansClusters = new ArrayList<Set<Business>>();

		for (Cluster c : clusterSet) {
			kMeansClusters.add(c.getBusinessSet());
		}

		return kMeansClusters;
	}

	@Override
	public ToDoubleBiFunction<MP5Db<Object>, String> getPredictorFunction(String user) {

		Map<String, Business> idMap = new HashMap<String, Business>();
		for (Business b : this.businessSet) {
			idMap.put(b.getBusinessID(), b);
		}

		User thisUser = this.userSet.stream().filter(listUser -> listUser.getUserID().equals(user)).reduce(null,
				(x, y) -> y);

		double sumPrice = thisUser.getReviewSet().stream().map(review -> review.getBusinessID())
				.map(businessID -> idMap.get(businessID)).map(business -> business.getPrice())
				.reduce(0, (x, y) -> x + y);
		double priceListSize = thisUser.getReviewSet().stream().map(review -> review.getBusinessID())
				.map(businessID -> idMap.get(businessID)).map(business -> business.getPrice())
				.reduce(0, (x, y) -> x + 1);

		double meanPrice = ((double) sumPrice) / priceListSize;
		double meanRating = thisUser.getAverageRating();

		double s_xx = thisUser.getReviewSet().stream().map(review -> review.getBusinessID())
				.map(businessID -> idMap.get(businessID)).map(business -> (double) business.getPrice())
				.reduce(0.0, (x, y) -> x + Math.pow(y - meanPrice, 2));

		double s_yy = thisUser.getReviewSet().stream().map(review -> (double) review.getStars()).reduce(0.0,
				(x, y) -> x + Math.pow(y - meanRating, 2));

		return null;
	}

	public Set<Business> getBusinessSet() {

		Set<Business> copy = new HashSet<Business>();
		copy.addAll(this.businessSet);

		return copy;
	}



}
