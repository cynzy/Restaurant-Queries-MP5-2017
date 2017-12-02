package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;
import java.util.HashSet;
import java.util.Set;

public class User {

	protected final String name;
	protected final String url;
	protected final String userID;
	protected int reviewCount;
	protected double averageRating;
	private Set<Review> reviewSet;
	
	public User(JsonObject user) {
		
		this.name = user.getString("name");
		this.url = user.getString("url");
		this.userID = user.getString("user_id");
		this.reviewCount = user.getInt("review_count");
		this.averageRating = Double.parseDouble(user.get("average_stars").toString());
		this.reviewSet = new HashSet<>();
	}
	
	public void addReview( Review review ) {
		if( !this.userID.equals(review.userID)) {
			throw new IllegalArgumentException( "user ID's do not match" );
		}
		
		this.reviewSet.add(review);
		this.reviewCount = this.reviewSet.size();
		int ratingSum = 0;
		for( Review r: this.reviewSet) {
			ratingSum += r.getStars();
		}
		
		this.averageRating = ((double)ratingSum)/this.reviewCount;
	}
	
	public Set<Review> getReviewSet(){
		Set<Review> copy = new HashSet<Review>();
		copy.addAll(this.reviewSet);
		
		return copy;
	}
	
	public boolean wroteReview( Review review ) {
		return this.reviewSet.contains(review);
	}
	
	public double getAverageRating() {
		return this.averageRating;
	}
	
	public int getReviewCount() {
		return this.reviewCount;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public String getUserID() {
		return this.userID;
	}
	
	@Override
	public boolean equals( Object o ) {
		if( o instanceof User ) {
			User other = (User) o;
			
			return this.userID.equals(other.getUserID());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) this.getAverageRating();
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
