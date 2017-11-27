package ca.ece.ubc.cpen221.mp5.Query;


import ca.ece.ubc.cpen221.mp5.Location;
import ca.ece.ubc.cpen221.mp5.Restaurant;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cynzy on 2017-11-24.
 */
public class MP5Query {

    private String queryString;
    private List<String> categories;
    private List<Location> locations;
    private List<String> names;
    private List<Integer> rating;
    private List<Integer> price;
    private List<Restaurant> restaurantsList;


    public MP5Query(String queryString) throws IOException {
        this.queryString = queryString;
        this.categories = new ArrayList<>();
        this.locations = new ArrayList<>();
        this.names = new ArrayList<>();
        this.rating = new ArrayList<>();
        this.price = new ArrayList<>();
        this.restaurantsList = new ArrayList<>();

        setLists();
        findRestaurants();
    }

    //modifies categories, locations, names, rating, price
    //by passing the reference to MP5QueryListenerGenerateList
    private void setLists() throws IOException {

        CharStream stream = new ANTLRInputStream(queryString);
        MP5QueryLexer lexer = new MP5QueryLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        MP5QueryParser parser = new MP5QueryParser(tokens);

        ParseTree tree = parser.query();
        System.out.println(tree.toStringTree(parser));
        ParseTreeWalker walker = new ParseTreeWalker();
        MP5QueryBaseListener listener = new MP5QueryListenerGenerateList(this.categories,this.locations,this.names,this.rating,this.price);
        walker.walk(listener, tree);


    }

    //modifies list of restaurants by searching through the database
    //with given query requests
    private void findRestaurants() {

    }


}
