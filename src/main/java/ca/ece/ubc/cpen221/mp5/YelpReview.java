package ca.ece.ubc.cpen221.mp5;

import java.util.HashMap;
import java.util.Map;

public class YelpReview extends Review {

	private Map<PossibleReactions, Integer> reactionMap;


	public YelpReview(String reviewID, String businessID, String userID, String text, String date, int stars, Map<PossibleReactions, Integer> reactionMap) {
		super(reviewID, businessID, userID, text, date, stars);
		
		this.reactionMap = reactionMap;

	}

	public void addReaction( PossibleReactions reaction ) {
		reactionMap.put(reaction, reactionMap.get(reaction) + 1);
	}
	
	public int getNumReactions( PossibleReactions reaction ) {
		return this.reactionMap.get(reaction);
	}
	
	public Map<PossibleReactions, Integer> getReactionMap(){
		Map<PossibleReactions, Integer> copy = new HashMap<>();
		copy.putAll(this.reactionMap);
		
		return copy;
	}

}
