package ca.ece.ubc.cpen221.mp5;

import java.util.Set;

public interface Database extends MP5Db<Object> {

	public Set<Business> getBusinessSet();
	
	public Set<User> getUserSet();
	
	public Set<Review> getReviewSet();
	
}
