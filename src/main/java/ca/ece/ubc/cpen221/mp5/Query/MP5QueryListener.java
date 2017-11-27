// Generated from MP5Query.g4 by ANTLR 4.7
package ca.ece.ubc.cpen221.mp5.Query;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MP5QueryParser}.
 */
public interface MP5QueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(MP5QueryParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(MP5QueryParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(MP5QueryParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(MP5QueryParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(MP5QueryParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(MP5QueryParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(MP5QueryParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(MP5QueryParser.AndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(MP5QueryParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(MP5QueryParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#ineq}.
	 * @param ctx the parse tree
	 */
	void enterIneq(MP5QueryParser.IneqContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#ineq}.
	 * @param ctx the parse tree
	 */
	void exitIneq(MP5QueryParser.IneqContext ctx);
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#in}.
	 * @param ctx the parse tree
	 */
	void enterIn(MP5QueryParser.InContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#in}.
	 * @param ctx the parse tree
	 */
	void exitIn(MP5QueryParser.InContext ctx);
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#category}.
	 * @param ctx the parse tree
	 */
	void enterCategory(MP5QueryParser.CategoryContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#category}.
	 * @param ctx the parse tree
	 */
	void exitCategory(MP5QueryParser.CategoryContext ctx);
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(MP5QueryParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(MP5QueryParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#rating}.
	 * @param ctx the parse tree
	 */
	void enterRating(MP5QueryParser.RatingContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#rating}.
	 * @param ctx the parse tree
	 */
	void exitRating(MP5QueryParser.RatingContext ctx);
	/**
	 * Enter a parse tree produced by {@link MP5QueryParser#price}.
	 * @param ctx the parse tree
	 */
	void enterPrice(MP5QueryParser.PriceContext ctx);
	/**
	 * Exit a parse tree produced by {@link MP5QueryParser#price}.
	 * @param ctx the parse tree
	 */
	void exitPrice(MP5QueryParser.PriceContext ctx);
}