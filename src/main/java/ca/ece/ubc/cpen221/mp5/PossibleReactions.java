package ca.ece.ubc.cpen221.mp5;

/*
 * PossibleReactions - possible user reactions in a YelpReview
 *
 * Representation Invariants:
 *
 * - there are only three possible reactions: cool, funny, or useful
 *
 * Abstraction Function:
 *
 * AF("cool") = PossibleReactions.COOL
 * AF("funny") = PossibleReactions.FUNNY
 * AF("useful") = PossibleReactions.USEFUL
 *
 */

public enum PossibleReactions {
	COOL, FUNNY, USEFUL
}
