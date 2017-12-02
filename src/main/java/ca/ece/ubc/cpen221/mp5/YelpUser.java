package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

/**
 * YelpUser - Represents a user on the website Yelp
 * 
 * Representation Invariants:
 *
 * - Same representation invariants as its supertype: User
 *
 * - this.reactionMap only includes positive values
 * 
 * Abstraction Function:
 * 
 * - Inherits the abstraction function of User
 *
 * - this.votes represents the reactions this user voted on for other reviews
 * including "FUNNY", "COOL", and "USEFUL"
 *
 */
public class YelpUser extends User {

	private Map<PossibleReactions, Integer> votes;

	public YelpUser(JsonObject yelpUser) {
		super(yelpUser);

		// parsing votes
		// convert JSON Object into map of reactions to integer
		Map<PossibleReactions, Integer> votes = new HashMap<>();
		JsonObject votesJson = yelpUser.get("votes").asJsonObject();
		votes.put(PossibleReactions.FUNNY, votesJson.getInt("funny"));
		votes.put(PossibleReactions.COOL, votesJson.getInt("cool"));
		votes.put(PossibleReactions.USEFUL, votesJson.getInt("useful"));

		this.votes = votes;

	}

	public void addVote(PossibleReactions reaction) {
		this.votes.put(reaction, this.votes.get(reaction) + 1);
	}
	
	public int getNumVotes(PossibleReactions reaction) {
		return votes.get(reaction);
	}

	/**
	 * returns a map of this YelpUser's votes for reviews. requires: this object is not
	 * null
	 *
	 * @param void
	 * 
	 * @return A map detailing this user's votes of different reactions on reviews
	 * 
	 */
	public Map<PossibleReactions, Integer> getVotes() {
		Map<PossibleReactions, Integer> copy = new HashMap<PossibleReactions, Integer>();
		copy.putAll(this.votes);
		return copy;
	}

}