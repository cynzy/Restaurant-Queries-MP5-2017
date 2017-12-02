package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		this.votes.put(reaction, this.votes.get(reaction));
	}

	public int getNumVotes(PossibleReactions reaction) {
		return votes.get(reaction);
	}

	public Map<PossibleReactions, Integer> getVotes() {
		Map<PossibleReactions, Integer> copy = new HashMap<PossibleReactions, Integer>();
		copy.putAll(this.votes);
		return copy;
	}

}