package ca.ece.ubc.cpen221.mp5.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.Restaurant;
import ca.ece.ubc.cpen221.mp5.YelpDB;
import ca.ece.ubc.cpen221.mp5.YelpDBClient;
import ca.ece.ubc.cpen221.mp5.YelpDBServer;
import ca.ece.ubc.cpen221.mp5.YelpUser;

public class ServerTests {

	@Test
	public void test0() throws IOException, InterruptedException {

		String request1 = "GETRESTAURANT h_we4E3zofRTf4G0JTEF0A";
		String request2 = "ADDUSER {\"name\": \"Dvir H.\"}";
		YelpDB database = new YelpDB("data/restaurants.json", "data/users.json", "data/reviews.json");
		int originalSize = database.getUserSet().size();
		try {
			final YelpDBServer server = new YelpDBServer(YelpDBServer.YELPDB_PORT, database);
			Thread serverThread = new Thread(() -> {
				try {
					server.serve();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			serverThread.start();

			YelpDBClient client1 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client2 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);

			Thread client1Thread = new Thread(() -> {
				try {
					client1.sendRequest(request1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			Thread client2Thread = new Thread(() -> {
				try {
					client2.sendRequest(request2);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			client1Thread.start();
			client2Thread.start();

			client1Thread.join();
			client2Thread.join();

			String reply1 = client1.getReply();
			String reply2 = client2.getReply();

			client1.close();
			client2.close();
			JsonReader jsonReader1 = Json.createReader(new StringReader(reply1));
			JsonReader jsonReader2 = Json.createReader(new StringReader(reply2));

			JsonObject restaurant = jsonReader1.readObject();
			JsonObject user = jsonReader2.readObject();

			Restaurant test = new Restaurant(restaurant);
			Restaurant realRestaurant = (Restaurant) database.getBusinessSet().stream()
					.filter(b -> b.getName().equals("Fondue Fred")).reduce(null, (x, y) -> y);
			YelpUser userTest = new YelpUser(user);

			assertTrue(test.equals(realRestaurant));
			assertTrue(userTest.getName().equals("Dvir H."));

		} catch (IOException e) {
			fail();
		}

		assertEquals(originalSize + 1, database.getUserSet().size());
	}

	@Test
	public void testErrorHandling() throws IOException {
		String request1 = "rip";
		String request2 = "ADDUSER rip";
		String request3 = "ADDUSER {\"average_rating\": 3}";
		String request4 = "GETRESTAURANT rip";
		String request5 = "ADDRESTAURANT rip";
		String request6 = "ADDRESTAURANT {\"name\": \"rip\"}";
		String request7 = "ADDREVIEW rip";
		String request8 = "ADDREVIEW {\"text\": \"rip\"}";
		String request9 = "ADDREVIEW {\"text\": \"rip\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"date\": \"2006-07-26\", \"stars\": 3, \"user_id\": \"rip\"}";
		String request10 = "ADDREVIEW {\"text\": \"rip\", \"business_id\": \"rip\", \"date\": \"2006-07-26\", \"stars\": 3, \"user_id\": \"wr3JF-LruJ9LBwQTuw7aUg\"}";
		String request11 = "ADDYELPUSER {\"name\": \"Dvir H.\"";
		YelpDB database = new YelpDB("data/restaurants.json", "data/users.json", "data/reviews.json");
		
		try {
			final YelpDBServer server = new YelpDBServer(YelpDBServer.YELPDB_PORT, database);
			Thread serverThread = new Thread(() -> {
				try {
					server.serve();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			serverThread.start();
			
			YelpDBClient client1 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client2 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client3 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client4 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client5 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client6 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client7 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client8 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client9 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client10 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);
			YelpDBClient client11 = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);

			client1.sendRequest(request1);
			client2.sendRequest(request2);
			client3.sendRequest(request3);
			client4.sendRequest(request4);
			client5.sendRequest(request5);
			client6.sendRequest(request6);
			client7.sendRequest(request7);
			client8.sendRequest(request8);
			client9.sendRequest(request9);
			client10.sendRequest(request10);
			client11.sendRequest(request11);
			
			String reply1 = client1.getReply();
			String reply2 = client2.getReply();
			String reply3 = client3.getReply();
			String reply4 = client4.getReply();
			String reply5 = client5.getReply();
			String reply6 = client6.getReply();
			String reply7 = client7.getReply();
			String reply8 = client8.getReply();
			String reply9 = client9.getReply();
			String reply10 = client10.getReply();
			String reply11 = client11.getReply();
			
			client1.close();
			client2.close();
			client3.close();
			client4.close();
			client5.close();
			client6.close();
			client7.close();
			client8.close();
			client9.close();
			client10.close();
			client11.close();
			
			assertTrue( reply1.equals("ERR: ILLEGAL_REQUEST\n"));
			assertTrue( reply2.equals("ERR: INVALID_USER_STRING\\n"));
			assertTrue( reply3.equals("ERR: INVALID_USER_STRING\\n"));
			assertTrue( reply4.equals("ERR: NO_SUCH_RESTAURANT\\n"));
			assertTrue( reply5.equals("ERR: INVALID_RESTAURANT_STRING\\n"));
			assertTrue( reply6.equals("ERR: INVALID_RESTAURANT_STRING\\n"));
			assertTrue( reply7.equals("ERR: INVALID_REVIEW_STRING\\n"));
			assertTrue( reply8.equals("ERR: INVALID_REVIEW_STRING\\n"));
			assertTrue( reply9.equals("ERR: NO_SUCH_USER\n"));
			assertTrue( reply10.equals("ERR: NO_SUCH_RESTAURANT\\n"));
			assertTrue( reply11.equals("ERR: ILLEGAL_REQUEST\n"));
			
		} catch( IOException e) {
			fail();
		}
			
	}

}
