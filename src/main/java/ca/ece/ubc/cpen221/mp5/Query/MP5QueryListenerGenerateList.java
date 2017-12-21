package ca.ece.ubc.cpen221.mp5.Query;

import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * MP5QueryListenerGenerateList - serves as a listener to parse the content of each node
 *
 * - listener walks into every exit node of Category, In, Name, Price, Rating
 *
 * Representation Invariants:
 *
 * - at least one token cannot be empty; at least one of the lists in this class has at least one element
 *
 * - each element of rating and price must be distinct and has to be 0 <= element <= 5
 *
 * Abstraction Function:
 *
 * - categories represent the categories of the restaurants the client is looking for
 *
 * - locations represent the neighborhoods the restaurants resides the client is looking for
 *
 * - names represent the restaurants that contain the names the client is looking for
 *
 * - rating represent the rating of the restaurants the client is looking for
 *
 * - pricing represent the pricing of the restaurants the client is looking for
 *
 */
public class MP5QueryListenerGenerateList extends MP5QueryParserBaseListener {

    private List<String> categories;
    private List<String> locations;
    private List<String> names;
    private List<Double> rating;
    private List<Integer> price;

    public MP5QueryListenerGenerateList(List<String> categories, List<String> locations, List<String> names, List<Double> rating, List<Integer> price) {
        this.categories = categories;
        this.locations = locations;
        this.names = names;
        this.rating = rating;
        this.price = price;
    }

    @Override
    public void exitCategory(MP5QueryParser.CategoryContext ctx) {
        TerminalNode token = ctx.STR();
        this.categories.add(token.getText());
    }

    @Override
    public void exitIn(MP5QueryParser.InContext ctx) {
        TerminalNode token = ctx.STR();
        Set<String> neighborhoods = new HashSet<>();
        neighborhoods.add(token.getText());
        this.locations.addAll(neighborhoods);
    }

    @Override
    public void exitName(MP5QueryParser.NameContext ctx) {
        TerminalNode token = ctx.STR();
        this.names.add(token.getText());
    }

    @Override
    public void exitPrice (MP5QueryParser.PriceContext ctx) {
        TerminalNode token = ctx.NUM();
        MP5QueryParser.IneqContext ineq = ctx.ineq();
        String cond = ineq.getText();
        int price = Integer.parseInt(token.getText());


        int maxPrice = 5;
        if (cond.equals("<")){
            for (int i = 1; i < price; i++){
                this.price.add(i);
            }
        } else if (cond.equals("<=")){
            for (int i = 1; i <= price; i++){
                this.price.add(i);
            }
        } else if (cond.equals("=")){
            this.price.add(price);
        } else if (cond.equals(">")){
            for (int i = maxPrice; maxPrice > price; i--){
                this.price.add(i);
            }
        } else if (cond.equals(">=")){
            for (int i = maxPrice; maxPrice >= price; i--){
                this.price.add(i);
            }
        } else {
            throw ctx.exception;
        }


    }

    @Override
    public void exitRating(MP5QueryParser.RatingContext ctx) {
        TerminalNode token = ctx.NUM();
        MP5QueryParser.IneqContext ineq = ctx.ineq();
        String cond = ineq.getText();
        double rating = Double.parseDouble(token.getText());

        int maxRating = 5;
        if (cond.equals("<")){
            this.rating.add(0.0);
            this.rating.add(rating - 0.01);
        } else if (cond.equals("<=")){
            this.rating.add(0.0);
            this.rating.add(rating - 0.0);
        } else if (cond.equals("=")){
            this.rating.add(rating-0.01);
            this.rating.add(rating+0.01);
        } else if (cond.equals(">")){
            this.rating.add(rating - 0.0);
            this.rating.add(5.0);
        } else if (cond.equals(">=")){
            this.rating.add(rating -0.01);
            this.rating.add(5.0);
        }


    }
}
