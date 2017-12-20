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
	public void test1() throws IOException, InterruptedException {
		String request1 = "ADDRESTAURANT {\"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"name\": \"Cafe 42\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}\r\n";
		String request2 = "ADDREVIEW {\"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"stars\": 1, \"user_id\": \"9fMogxnnd0m9_FKSi-4AoQ\", \"text\": \"I personally thought the fries were a bit too soggy\", \"date\": \"2007-10-11\"}";
		YelpDB database = new YelpDB("data/restaurants.json", "data/users.json", "data/reviews.json");
		String businessID = "1CBs84C-a-cuA3vncXVSAw";
		String userID = "9fMogxnnd0m9_FKSi-4AoQ";
		int originalRestaurantSize = database.getBusinessSet().size();
		int originalReviewSize = database.getReviewSet().size();
		int originalBusinessReviewCount = database.getBusinessSet().stream()
				.filter(b -> b.getBusinessID().equals(businessID)).map(b -> b.getReviewCount()).reduce(0, (x, y) -> y);
		int originalUserReviewCount = database.getUserSet().stream().filter(u -> u.getUserID().equals(userID))
				.map(u -> u.getReviewCount()).reduce(0, (x, y) -> y);
		
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
			
			client1.getReply();
			client2.getReply();
			
			client1.close();
			client2.close();
			
			assertEquals( originalRestaurantSize +1, database.getBusinessSet().size());
			assertEquals( originalReviewSize +1, database.getReviewSet().size());

		} catch (IOException e) {
			fail();
		}
		
		int newUserReviewCount = database.getUserSet().stream().filter(u -> u.getUserID().equals(userID))
				.map(u -> u.getReviewCount()).reduce(0, (x, y) -> y);
		int newBusinessReviewCount = database.getBusinessSet().stream().filter(b -> b.getBusinessID().equals(businessID))
				.map(b -> b.getReviewCount()).reduce(0, (x, y) -> y);
		
		assertEquals( newUserReviewCount, originalUserReviewCount + 1);
		assertEquals( newBusinessReviewCount, originalBusinessReviewCount +1);
		
	}

	@Test
	public void testErrorHandling() throws IOException, InterruptedException {
		String request1 = "rip";
		String request2 = "ADDUSER rip";
		String request3 = "ADDUSER {\"average_rating\": 3}";
		String request4 = "GETRESTAURANT rip";
		String request5 = "ADDRESTAURANT rip";
		String request6 = "ADDRESTAURANT {\"name\": \"rip\"}";
		String request7 = "ADDREVIEW rip";
		String request8 = "ADDREVIEW {\"text\": \"rip\"}";
		String request9 = "ADDREVIEW {\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"The pizza is terrible, but if you need a place to watch a game or just down some pitchers, this place works.\\n\\nOh, and the pasta is even worse than the pizza.\", \"stars\": 2, \"user_id\": \"rip\", \"date\": \"2006-07-26\"}\r\n";
		String request10 = "ADDREVIEW {\"type\": \"review\", \"business_id\": \"rip\", \"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"The pizza is terrible, but if you need a place to watch a game or just down some pitchers, this place works.\\n\\nOh, and the pasta is even worse than the pizza.\", \"stars\": 2, \"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", \"date\": \"2006-07-26\"}\r\n";
		String request11 = "ADDYELPUSER {\"name\": \"Dvir H.\"";
		YelpDB database = new YelpDB("data/restaurants.json", "data/users.json", "data/reviews.json");

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
		Thread client3Thread = new Thread(() -> {
			try {
				client3.sendRequest(request3);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread client4Thread = new Thread(() -> {
			try {
				client4.sendRequest(request4);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread client5Thread = new Thread(() -> {
			try {
				client5.sendRequest(request5);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread client6Thread = new Thread(() -> {
			try {
				client6.sendRequest(request6);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread client7Thread = new Thread(() -> {
			try {
				client7.sendRequest(request7);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread client8Thread = new Thread(() -> {
			try {
				client8.sendRequest(request8);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread client9Thread = new Thread(() -> {
			try {
				client9.sendRequest(request9);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread client10Thread = new Thread(() -> {
			try {
				client10.sendRequest(request10);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread client11Thread = new Thread(() -> {
			try {
				client11.sendRequest(request11);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		client1Thread.start();
		client2Thread.start();
		client3Thread.start();
		client4Thread.start();
		client5Thread.start();
		client6Thread.start();
		client7Thread.start();
		client8Thread.start();
		client9Thread.start();
		client10Thread.start();
		client11Thread.start();

		client1Thread.join();
		client2Thread.join();
		client3Thread.join();
		client4Thread.join();
		client5Thread.join();
		client6Thread.join();
		client7Thread.join();
		client8Thread.join();
		client9Thread.join();
		client10Thread.join();
		client11Thread.join();

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

		assertTrue(reply1.equals("ERR: ILLEGAL_REQUEST\n"));
		assertTrue(reply2.equals("ERR: INVALID_USER_STRING\\n"));
		assertTrue(reply3.equals("ERR: INVALID_USER_STRING\\n"));
		assertTrue(reply4.equals("ERR: NO_SUCH_RESTAURANT\\n"));
		assertTrue(reply5.equals("ERR: INVALID_RESTAURANT_STRING\\n"));
		assertTrue(reply6.equals("ERR: INVALID_RESTAURANT_STRING\\n"));
		assertTrue(reply7.equals("ERR: INVALID_REVIEW_STRING\\n"));
		assertTrue(reply8.equals("ERR: INVALID_REVIEW_STRING\\n"));
		assertTrue(reply9.equals("ERR: NO_SUCH_USER\n"));
		assertTrue(reply10.equals("ERR: NO_SUCH_RESTAURANT\\n"));
		assertTrue(reply11.equals("ERR: ILLEGAL_REQUEST\n"));

	}
}
