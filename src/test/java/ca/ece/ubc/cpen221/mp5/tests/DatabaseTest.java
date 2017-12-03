package ca.ece.ubc.cpen221.mp5.tests;

import ca.ece.ubc.cpen221.mp5.Business;
import ca.ece.ubc.cpen221.mp5.MP5Db;
import ca.ece.ubc.cpen221.mp5.PredictorFunction;
import ca.ece.ubc.cpen221.mp5.Review;
import ca.ece.ubc.cpen221.mp5.User;
import ca.ece.ubc.cpen221.mp5.YelpDB;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleBiFunction;

/**
 * Created by Cynzy on 2017-12-02.
 */
public class DatabaseTest {

	// testing YelpDB parsing
	@Test
	public void test0() throws IOException {
		String restaurantJson = "data/restaurantsTest.json";
		String usersJson = "data/users.json";
		String reviewsJson = "data/reviews.json";

		YelpDB db = new YelpDB(restaurantJson, usersJson, reviewsJson);

		String DatatypeTest1 = "data/DatatypeTest1.json";
		BufferedReader bufferedReader = new BufferedReader(new FileReader(DatatypeTest1));
		String line;
		line = bufferedReader.readLine();
		JsonReader jsonReader = Json.createReader(new StringReader(line));
		JsonObject business = jsonReader.readObject();
		Business coffeeLab = new Business(business);

		String DatatypeTest2 = "data/DatatypeTest2.json";
		BufferedReader bufferedReader2 = new BufferedReader(new FileReader(DatatypeTest2));
		line = bufferedReader2.readLine();
		JsonReader jsonReader2 = Json.createReader(new StringReader(line));
		JsonObject readObject2 = jsonReader2.readObject();
		Review review = new Review(readObject2);

		String DatatypeTest3 = "data/DatatypeTest3.json";
		BufferedReader bufferedReader3 = new BufferedReader(new FileReader(DatatypeTest3));
		line = bufferedReader3.readLine();
		JsonReader jsonReader3 = Json.createReader(new StringReader(line));
		JsonObject readObject3 = jsonReader3.readObject();
		User user = new User(readObject3);

		assertTrue(db.getBusinessSet().contains(coffeeLab));
		assertTrue(db.getReviewSet().contains(review));
		assertTrue(db.getUserSet().contains(user));

	}

	// testing YelpDB algorithms
	@Test
	public void test1() throws IOException {
		String restaurantJson = "data/restaurants.json";
		String usersJson = "data/users.json";
		String reviewsJson = "data/reviews.json";

		YelpDB db = new YelpDB(restaurantJson, usersJson, reviewsJson);

		List<Set<Business>> kmeans = db.kMeansClusters_List(45);

		int numClusteredBusinesses = kmeans.stream().map(set -> set.size()).reduce(0, (x, y) -> x + y);
		int numEmptyClusters = kmeans.stream().filter(set -> set.isEmpty()).map(set -> set.size()).reduce(0,
				(x, y) -> x + 1);

		assertTrue( db.kMeansClusters_json(10).charAt(0) == '[');
		assertEquals(db.getBusinessSet().size(), numClusteredBusinesses);
		assertEquals(0, numEmptyClusters);
	}

	@Test( expected = UnsupportedOperationException.class)
	public void test2() throws IOException {
		String restaurantJson = "data/restaurants.json";
		String usersJson = "data/users.json";
		String reviewsJson = "data/reviews.json";

		YelpDB db = new YelpDB(restaurantJson, usersJson, reviewsJson);
		ToDoubleBiFunction<MP5Db<Object>, String> rip = db.getPredictorFunction("_n9N41zBLY8uFLyTdynJ1A");

		rip.applyAsDouble(db, "loBOs5ruFXSNL-ZM29cTrA");
	}
	

	@Test
	public void test3() throws IOException {
		String restaurantJson = "data/restaurants.json";
		String usersJson = "data/users.json";
		String reviewsJson = "data/reviews.json";

		YelpDB db = new YelpDB(restaurantJson, usersJson, reviewsJson);
		ToDoubleBiFunction<MP5Db<Object>, String> rip = db.getPredictorFunction("KH3cBULZ2y4miI8mtlbY9Q");

		assertEquals(5, (int) rip.applyAsDouble(db, "loBOs5ruFXSNL-ZM29cTrA"));
	}
	
	// found collectively with a friend while going through database
	@Test
	public void test4() throws IOException {
		String restaurantJson = "data/restaurants.json";
		String usersJson = "data/users.json";
		String reviewsJson = "data/reviews.json";

		YelpDB db = new YelpDB(restaurantJson, usersJson, reviewsJson);
		ToDoubleBiFunction<MP5Db<Object>, String> rip = db.getPredictorFunction("QScfKdcxsa7t5qfE0Ev0Cw");

		assertTrue( rip.applyAsDouble(db, "G3d-xJF_Rt-P_za2eZ1q-Q") == 1.0);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void test5() throws IOException {
		String restaurantJson = "data/restaurants.json";
		String usersJson = "data/users.json";
		String reviewsJson = "data/reviews.json";

		YelpDB db = new YelpDB(restaurantJson, usersJson, reviewsJson);
		ToDoubleBiFunction<MP5Db<Object>, String> rip = db.getPredictorFunction("QScfKdcxsa7t5qfE0Ev0Cw");
		rip.applyAsDouble(db, "notARealID" );

	}
}
