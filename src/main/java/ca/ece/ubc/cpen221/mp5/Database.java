package ca.ece.ubc.cpen221.mp5;

import java.util.List;
import java.util.Set;

public interface Database extends MP5Db<Object> {

	public Set<Business> getBusinessSet();
	
	public Set<User> getUserSet();
	
	public Set<Review> getReviewSet();

	public List<Set<Business>> kMeansClusters_List(int k);
	
}
