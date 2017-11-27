package ca.ece.ubc.cpen221.mp5;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ToDoubleBiFunction<MP5Db<Object>, String> getPredictorFunction(String user) {
		// TODO Auto-generated method stub
		return null;
	}

}
