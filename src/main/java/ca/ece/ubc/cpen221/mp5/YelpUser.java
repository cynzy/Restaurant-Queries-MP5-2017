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

	/**
	 * Constructs a YelpUser object
	 *
	 * @param yelpUser
	 *            a JsonObject representing a file with all the information for this
	 *            object's representation. requires: yelpuser is not null
	 * 
	 */
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

	/**
	 * Increments the number of votes for a specified reaction by 1.
	 *
	 * @param reaction
	 *            an Enum representing one the possible reactions: "FUNNY",
	 *            "USEFUL", and "COOL".
	 * @return the number of times that this user voted for that specific reaction
	 */
	public synchronized void addVote(PossibleReactions reaction) {
		this.votes.put(reaction, this.votes.get(reaction) + 1);
	}

	/**
	 * returns the number of votes this user's reviews got in total given a specific
	 * reaction.
	 *
	 * @param reaction
	 *            an Enum representing one the possible reactions: "FUNNY",
	 *            "USEFUL", and "COOL".
	 * @return the number of times that specific reaction got voted by the user
	 */
	public synchronized int getNumVotes(PossibleReactions reaction) {
		return votes.get(reaction);
	}

	/**
	 * returns a map of this YelpUser's votes for reviews. requires: this object is
	 * not null
	 *
	 * @param void
	 * 
	 * @return A map detailing this user's votes of different reactions on reviews
	 * 
	 */
	public synchronized Map<PossibleReactions, Integer> getVotes() {
		Map<PossibleReactions, Integer> copy = new HashMap<PossibleReactions, Integer>();
		copy.putAll(this.votes);
		return copy;
	}

}