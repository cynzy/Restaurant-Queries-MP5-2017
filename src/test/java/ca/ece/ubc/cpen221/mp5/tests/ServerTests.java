package ca.ece.ubc.cpen221.mp5.tests;

import ca.ece.ubc.cpen221.mp5.*;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.*;

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
	public void testQuery1() throws IOException, InterruptedException {
		String request1 = "QUERY category(Chinese)";
		String request2 = "QUERY category(Chinese) && price <= 3";
		String request3 = "QUERY category(Chinese) && price <= 3 && rating > 2";
		String request4 = "QUERY category(Korean) && price >= 2 && in(UC Campus Area)";
		
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

		client1Thread.start();
		client2Thread.start();
		client3Thread.start();
		client4Thread.start();

		client1Thread.join();
		client2Thread.join();
		client3Thread.join();
		client4Thread.join();

		String reply1 = client1.getReply();
		String reply2 = client2.getReply();
		String reply3 = client3.getReply();
		String reply4 = client4.getReply();

		client1.close();
		client2.close();
		client3.close();
		client4.close();


	}


	@Test
	public void testQuery2() throws IOException, InterruptedException {
		String request1 = "QUERY price <= 2 && name(Restaurant)";
		String request2 = "QUERY price = 2 && in(UC Campus Area)";

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


	}

}
