package ca.ece.ubc.cpen221.mp5;

import java.io.IOException;

public class TestStuff {
	
	public static void main( String[] args) throws IOException {
		String restaurantJson = "data/restaurantsTest.json";
        String usersJson = "data/users.json";
        String reviewsJson = "data/reviews.json";

        YelpDB db = new YelpDB(restaurantJson,usersJson,reviewsJson);
        System.out.println("finished parsing data \n");

        System.out.println(db.kMeansClusters_json(4));
	}

}
