package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;
import java.util.HashSet;
import java.util.Set;

public class Restaurant extends Business {
	
	private Set<YelpReview> reviewSet;
	
	public Restaurant(JsonObject restaurant) {
		super(restaurant);
		this.reviewSet = new HashSet<>();
	}

	public void addReview( YelpReview review ) {
		this.reviewSet.add(review);
		this.reviewCount = this.reviewSet.size();
		int sumRating = 0;
		for( YelpReview r: this.reviewSet) {
			sumRating += r.getStars();
		}
		this.rating = (int) Math.round(((double)sumRating)/this.reviewCount);
	}

	public Set<YelpReview> getReviewSet(){
		Set<YelpReview> copy = new HashSet<>();
		copy.addAll(this.reviewSet);
		return copy;
	}
}
