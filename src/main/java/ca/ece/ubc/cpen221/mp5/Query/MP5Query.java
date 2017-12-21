package ca.ece.ubc.cpen221.mp5.Query;


import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cynzy on 2017-11-24.
 */
public class MP5Query {

    private String queryString;
    private List<String> categories;
    private List<String> locations;
    private List<String> names;
    private List<Integer> rating;
    private List<Integer> price;


    public MP5Query(String queryString) throws RecognitionException {
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
    private void setLists() throws RecognitionException {

        CharStream stream = new ANTLRInputStream(queryString);
        MP5QueryLexer lexer = new MP5QueryLexer(stream);
        lexer.removeErrorListener(ConsoleErrorListener.INSTANCE);
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
