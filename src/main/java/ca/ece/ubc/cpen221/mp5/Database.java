package ca.ece.ubc.cpen221.mp5;

import ca.ece.ubc.cpen221.mp5.Query.MP5Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

public class Database implements MP5Db<Object> {

	private Set<Business> businessSet;
	private Set<User> userSet;
	private Set<Review> reviewSet;

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

		for (int i = 0; i < k; i++) {
			clusterSet.add(new Cluster(360 * Math.random() - 180, 180 * Math.random() - 90));
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
			for( Cluster c: clusterSet ) {
				centroidChange = c.adjustCentroid();
			}
			
			if( !centroidChange ) {
				break;
			}
		}
		
		List<Set<Business>> kMeansClusters = new ArrayList<Set<Business>>();
		
		for( Cluster c: clusterSet) {
			kMeansClusters.add(c.getBusinessSet());
		}


		return null;
	}

	@Override
	public ToDoubleBiFunction<MP5Db<Object>, String> getPredictorFunction(String user) {
		// TODO Auto-generated method stub
		return null;
	}

}
