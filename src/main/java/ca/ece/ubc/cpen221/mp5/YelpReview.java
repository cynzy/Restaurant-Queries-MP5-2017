package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

public class YelpReview extends Review {

	private Map<PossibleReactions, Integer> reactionMap;

	public YelpReview(JsonObject review){
		super(review);

		//pasing votes
		//convert JSON Object into map of reactions to integer
		Map<PossibleReactions, Integer> votes = new HashMap<>();
		JsonObject votesJson = review.get("votes").asJsonObject();
		votes.put(PossibleReactions.FUNNY, votesJson.getInt("funny"));
		votes.put(PossibleReactions.COOL, votesJson.getInt("cool"));
		votes.put(PossibleReactions.USEFUL, votesJson.getInt("useful"));

		this.reactionMap = votes;

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
