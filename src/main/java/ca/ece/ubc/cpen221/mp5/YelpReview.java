package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

/**
 * YelpReview - Represents a review written on the website Yelp.
 * 
 * Representation Invariants:
 *
 * - Same representation invariants as its supertype: Review
 *
 * - this.reactionMap only includes positive values and cannot be modified after
 * being initialized
 *
 * 
 * Abstraction Function:
 * 
 * - Inherits the abstraction function of Review
 *
 * - this.reactionMap represents the reactions to this review which include
 * "FUNNY", "COOL", and "USEFUL"
 *
 */
public class YelpReview extends Review {

	private final Map<PossibleReactions, Integer> reactionMap;

	/**
	 * Constructs a YelpReview object
	 *
	 * @param review
	 *            a JsonObject representing a file with all the information for this
	 *            object's representation. requires: review is not null
	 * 
	 */
	public YelpReview(JsonObject review) {
		super(review);

		// pasing votes
		// convert JSON Object into map of reactions to integer
		Map<PossibleReactions, Integer> votes = new HashMap<>();
		JsonObject votesJson = review.get("votes").asJsonObject();
		votes.put(PossibleReactions.FUNNY, votesJson.getInt("funny"));
		votes.put(PossibleReactions.COOL, votesJson.getInt("cool"));
		votes.put(PossibleReactions.USEFUL, votesJson.getInt("useful"));

		this.reactionMap = votes;

	}

	/**
	 * returns the number of reactions this review got given a specific reaction.
	 *
	 * @param reaction
	 *            an Enum representing one the possible reactions: "FUNNY",
	 *            "USEFUL", and "COOL".
	 * @return the number of times that specific reaction got voted for this review
	 */
	public int getNumReactions(PossibleReactions reaction) {
		return this.reactionMap.get(reaction);
	}

	/**
	 * returns a map of the reactions voted for this review. requires: this object
	 * is not null
	 *
	 * @param void
	 * 
	 * @return A map detailing this review's votes of different reactions
	 * 
	 */
	public Map<PossibleReactions, Integer> getReactionMap() {
		Map<PossibleReactions, Integer> copy = new HashMap<>();
		copy.putAll(this.reactionMap);

		return copy;
	}

}
