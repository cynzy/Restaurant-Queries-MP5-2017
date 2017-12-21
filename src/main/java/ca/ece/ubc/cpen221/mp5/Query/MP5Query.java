package ca.ece.ubc.cpen221.mp5.Query;


import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * MP5Query - represents the given query structure of MP5
 *
 * Representation Invariants:
 *
 * - queryString is formatted according to the grammar given
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

public class MP5Query {

    private String queryString;
    private List<String> categories;
    private List<String> locations;
    private List<String> names;
    private List<Integer> rating;
    private List<Integer> price;


    public MP5Query(String queryString)  {
        this.queryString = queryString;
        this.categories = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.names = new ArrayList<>();
        this.rating = new ArrayList<>();
        this.price = new ArrayList<>();

        setLists();
    }

    //modifies categories, locations, names, rating, price
    //by passing the reference to MP5QueryListenerGenerateList
    private void setLists() {

        CharStream stream = new ANTLRInputStream(queryString);
        MP5QueryLexer lexer = new MP5QueryLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        MP5QueryParser parser = new MP5QueryParser(tokens);

        ParseTree tree = parser.query();
        ParseTreeWalker walker = new ParseTreeWalker();
        MP5QueryParserListener listener = new MP5QueryListenerGenerateList(this.categories, this.locations, this.names, this.rating, this.price);
        walker.walk(listener, tree);


    }


    public List<String> getCategories() {
        return categories;
    }

    public List<String> getLocations() {
        return locations;
    }

    public List<String> getNames() {
        return names;
    }

    public List<Integer> getRating() {
        return rating;
    }

    public List<Integer> getPrice() {
        return price;
    }
}
