package ca.ece.ubc.cpen221.mp5;

import java.util.HashMap;
import java.util.Map;

public class YelpReview extends Review {

	private Map<PossibleReactions, Integer> reactionMap;
	private int rating;

	public YelpReview(String reviewID, String businessID, String userID, String text, String date, int stars,
			Map<PossibleReactions, Integer> reactionsMap) {
		super(reviewID, businessID, userID, text, date);
		
		this.rating = stars;
		
		this.reactionMap = new HashMap<PossibleReactions, Integer>();
		this.reactionMap.putAll(reactionsMap);
	}
	
	public void addReaction( PossibleReactions reaction ) {
		reactionMap.put(reaction, reactionMap.get(reaction) + 1);
	}
	
	public int getNumReactions( PossibleReactions reaction ) {
		return this.reactionMap.get(reaction);
	}
	
	public Map<PossibleReactions, Integer> getReactionMap(){
		Map<PossibleReactions, Integer> copy = new HashMap<PossibleReactions, Integer>();
		copy.putAll(this.reactionMap);
		
		return copy;
	}
	
	public int getStars() {
		return this.rating;
	}

}
