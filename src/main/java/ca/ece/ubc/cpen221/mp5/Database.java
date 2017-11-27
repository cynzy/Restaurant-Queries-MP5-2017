package ca.ece.ubc.cpen221.mp5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

public class Database implements MP5Db<Object> {

	private Set<Business> businessSet;
	private Set<User> userSet;
	private Set<Review> reviewSet;

	@Override
	public Set<Object> getMatches(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String kMeansClusters_json(int k) {
		Map<Business, Cluster> clusteringMap = new HashMap<Business, Cluster>();
		Set<Cluster> clusterSet = new HashSet<Cluster>();

		for (int i = 0; i < k; i++) {
			clusterSet.add(new Cluster(360 * Math.random() - 180, 180 * Math.random() - 90));
		}

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
			closestCluster.addBusiness(b);
			clusteringMap.put(b, closestCluster);
		}

		return null;
	}

	@Override
	public ToDoubleBiFunction<MP5Db<Object>, String> getPredictorFunction(String user) {
		// TODO Auto-generated method stub
		return null;
	}

}
