package ca.ece.ubc.cpen221.mp5;

import javax.json.JsonObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class YelpUser extends User {

	private Map<PossibleReactions, Integer> votes;
	private Set<YelpReview> reviewSet;


	public YelpUser(JsonObject yelpUser) {
		super(yelpUser);
		this.reviewSet = new HashSet<>();

		//parsing votes
		//convert JSON Object into map of reactions to integer
		Map<PossibleReactions, Integer> votes = new HashMap<>();
		JsonObject votesJson = yelpUser.get("votes").asJsonObject();
		votes.put(PossibleReactions.FUNNY, votesJson.getInt("funny"));
		votes.put(PossibleReactions.COOL, votesJson.getInt("cool"));
		votes.put(PossibleReactions.USEFUL, votesJson.getInt("useful"));

		this.votes = votes;

	}
	
	public void addReview( YelpReview review ) {
		if( !this.userID.equals(review.userID)) {
			throw new IllegalArgumentException( "user ID's do not match" );
		}
		
		this.reviewSet.add(review);
		this.reviewCount = this.reviewSet.size();
		int ratingSum = 0;
		for( YelpReview r: this.reviewSet) {
			ratingSum += r.getStars();
		}
		
		this.averageRating = ((double)ratingSum)/this.reviewCount;
	}
	
	public boolean wroteReview( YelpReview review ) {
		return this.reviewSet.contains(review);
	}
	
	public Set<YelpReview> getReviewList() {
		Set<YelpReview> copy = new HashSet<YelpReview>();
		copy.addAll(reviewSet);
		return copy;
	}

	public void setReviewSet(Set<YelpReview> reviewSet) {
		this.reviewSet = reviewSet;
	}
	
	public void addVote( PossibleReactions reaction ) {
		this.votes.put(reaction, this.votes.get(reaction) );
	}
	
	public int getNumVotes( PossibleReactions reaction ) {
		return votes.get(reaction);
	}
	
	public Map<PossibleReactions,Integer> getVotes(){
		Map<PossibleReactions, Integer> copy = new HashMap<PossibleReactions, Integer>();
		copy.putAll(this.votes);
		return copy;
	}
	
}