package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;

public class YelpDBServer {

	public static final int YELPDB_PORT = 4949;

	private ServerSocket serversocket;
	private YelpDB database;

	public YelpDBServer(int port, YelpDB database) throws IOException {
		this.serversocket = new ServerSocket(port);
		this.database = database;
	}

	/**
	 * Run the server, listening for connections and handling them.
	 * 
	 * @throws IOException
	 *             if the main server socket is broken
	 */
	public void serve() throws IOException {
		while (true) {
			// block until a client connects
			final Socket socket = serversocket.accept();
			// create a new thread to handle that client
			Thread handler = new Thread(new Runnable() {
				public void run() {
					try {
						try {
							handle(socket);
						} finally {
							socket.close();
						}
					} catch (IOException ioe) {
						// this exception wouldn't terminate serve(),
						// since we're now on a different thread, but
						// we still need to handle it
						ioe.printStackTrace();
					}
				}
			});
			// start the thread
			handler.start();
		}
	}

	/**
	 * Handle one client connection. Returns when client disconnects.
	 * 
	 * @param socket
	 *            socket where client is connected
	 * @throws IOException
	 *             if connection encounters an error
	 */
	private void handle(Socket socket) throws IOException {
		System.err.println("client connected");

		// get the socket's input stream, and wrap converters around it
		// that convert it from a byte stream to a character stream,
		// and that buffer it so that we can read a line at a time
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		// similarly, wrap character=>bytestream converter around the
		// socket output stream, and wrap a PrintWriter around that so
		// that we have more convenient ways to write Java primitive
		// types to it.
		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

		try {
			// each request is a single line containing a number
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				System.err.println("request: " + line);

				String request = new String();
				try {
					request = line.substring(0, line.indexOf(' '));
				} catch (StringIndexOutOfBoundsException e) {
					System.err.println("reply: ERR: ILLEGAL_REQUEST");
					out.print("ERR: ILLEGAL_REQUEST\n");
				}
				if (request.equals("GETRESTAURANT")) {

					try {
						String output = getRestaurant(line);
						System.err.println("reply: " + output);
						out.println(output);
					} catch (NoSuchRestaurantException e) {
						System.err.println("reply: ERR: NO_SUCH_RESTAURANT");
						out.print("ERR: NO_SUCH_RESTAURANT\n");
					}
				}

				else if (request.equals("ADDUSER")) {

					try {
						String output = addUser(line);
						System.err.println("reply: " + output);
						out.println(output);
					} catch (JsonParsingException e) {
						System.err.println("reply: ERR: INVALID_USER_STRING");
						out.print("ERR: INVALID_USER_STRING\n");
					} catch (NullPointerException e) {
						System.err.println("reply: ERR: INVALID_USER_STRING");
						out.print("ERR: INVALID_USER_STRING\n");
					}
				}

				else if (request.equals("ADDRESTAURANT")) {

					try {
						String output = addRestaurant(line);
						System.err.println("reply: " + output);
						out.println(output);
					} catch (JsonParsingException e) {
						System.err.println("reply: ERR: INVALID_RESTAURANT_STRING");
						out.print("ERR: INVALID_RESTAURANT_STRING\n");
					} catch (NullPointerException e) {
						System.err.println("reply: ERR: INVALID_USER_STRING");
						out.print("ERR: INVALID_USER_STRING\n");
					}
				}

				else if (request.equals("ADDREVIEW")) {
					try {
						String output = addReview(line);
						System.err.println("reply: " + output);
						out.println(output);
					} catch (JsonParsingException e) {
						System.err.println("reply: ERR: INVALID_REVIEW_STRING");
						out.print("ERR: INVALID_REVIEW_STRING\n");
					} catch (NoSuchRestaurantException e) {
						System.err.println("reply: ERR: NO_SUCH_RESTAURANT");
						out.print("ERR: NO_SUCH_RESTAURANT\n");
					} catch (NoSuchUserException e) {
						System.err.println("reply: ERR: NO_SUCH_USER");
						out.print("ERR: NO_SUCH_USER\n");
					} catch (NullPointerException e) {
						System.err.println("reply: ERR: INVALID_USER_STRING");
						out.print("ERR: INVALID_USER_STRING\n");
					}
				}

				else if (request.equals("QUERY")) {

				}

				else {
					System.err.println("reply: ERR: ILLEGAL_REQUEST");
					out.print("ERR: ILLEGAL_REQUEST\n");
				}

				// important! our PrintWriter is auto-flushing, but if it were
				// not:
				// out.flush();
			}
		} finally {
			out.close();
			in.close();
		}
	}

	private String addRestaurant(String line) {
		String businessID = "";
		for (int i = 0; i < 10; i++) {
			businessID = businessID + (int) Math.random() * 10;
		}

		JsonReader jsonReader = Json.createReader(new StringReader(line.substring(line.indexOf(' '), line.length())));
		JsonObject restaurantInputJson = jsonReader.readObject();

		JsonObjectBuilder j;
		j = javax.json.Json.createObjectBuilder();
		JsonArrayBuilder array = Json.createArrayBuilder();

		j.add("open", true);
		j.add("url", restaurantInputJson.getString("url"));
		j.add("longitude", restaurantInputJson.getJsonNumber("longitude"));
		j.add("neighbourhoods", restaurantInputJson.get("neighborhoods").asJsonArray());
		j.add("business_id", businessID);
		j.add("name", restaurantInputJson.getString("name"));
		j.add("categories", restaurantInputJson.get("categories").asJsonArray());
		j.add("state", restaurantInputJson.getString("state"));
		j.add("type", "business");
		j.add("stars", 0);
		j.add("city", restaurantInputJson.getString("city"));
		j.add("full_address", restaurantInputJson.getString("full_address"));
		j.add("review_count", 0);
		j.add("photo_url", restaurantInputJson.getString("photo_url"));
		j.add("schools", restaurantInputJson.get("schools").asJsonArray());
		j.add("latitude", restaurantInputJson.getJsonNumber("latitude"));
		j.add("price", restaurantInputJson.getInt("price"));

		JsonObject business = j.build();
		Restaurant restaurant = new Restaurant(business);
		this.database.addRestaurant(restaurant);

		return business.toString();

	}

	private String getRestaurant(String line) throws NoSuchRestaurantException {
		String businessID = line.substring(line.indexOf(' ') + 1, line.length());
		Business restaurant = this.database.getBusinessSet().stream()
				.filter(business -> business.getBusinessID().equals(businessID)).reduce(null, (x, y) -> y);

		if (restaurant == null) {
			throw new NoSuchRestaurantException();
		}

		JsonObjectBuilder j;
	
		j = javax.json.Json.createObjectBuilder();
		j.add("open", restaurant.isOpen());
		j.add("url", restaurant.getUrl());
		j.add("longitude", restaurant.getLocation().getCoordinates().getlongitude());
		
		JsonArrayBuilder array = Json.createArrayBuilder();
		for( String s: restaurant.getLocation().getNeighbourhoods()) {
			array.add(s);
		}
		
		j.add("neighbourhoods", array.build());
		j.add("business_id", restaurant.getBusinessID());
		j.add("name", restaurant.getName());
		
		JsonArrayBuilder array2 = Json.createArrayBuilder();
		for( String s: restaurant.getCategories()) {
			array2.add(s);
		}
		
		j.add("categories", array2.build());
		j.add("state", restaurant.getLocation().getState());
		j.add("type", "business");
		j.add("stars", restaurant.getRating());
		j.add("city", restaurant.getLocation().getCity());
		j.add("full_address", restaurant.getLocation().getAddress());
		j.add("review_count", restaurant.getReviewCount());
		j.add("photo_url", restaurant.getPhotoUrl());
		
		JsonArrayBuilder array3 = Json.createArrayBuilder();
		for( String s: restaurant.getLocation().getSchool()) {
			array3.add(s);
		}
		
		j.add("schools", array3.build());
		j.add("latitude", restaurant.getLocation().getCoordinates().getlatitude());
		j.add("price", restaurant.getPrice());

	
		return j.build().toString();
	}

	private String addUser(String line) {
		JsonReader jsonReader = Json
				.createReader(new StringReader(line.substring(line.indexOf(' ') + 1, line.length())));
		JsonObject userInputJson = jsonReader.readObject();

		String userID = "";
		for (int i = 0; i < 10; i++) {
			userID = userID + (int) Math.random() * 10;
		}

		JsonObject votes = javax.json.Json.createObjectBuilder().add("cool", 0).add("useful", 0).add("funny", 0)
				.build();

		JsonObjectBuilder j;
		j = javax.json.Json.createObjectBuilder();

		j.add("url", "http://www.yelp.com/user_details?userid=" + userID);
		j.add("votes", votes.toString());
		j.add("review_count", 0);
		j.add("type", "user");
		j.add("user_id", userID);
		j.add("name", userInputJson.getString("name"));
		j.add("average_stars", 0);

		JsonObject user = j.build();
		YelpUser yelpUser = new YelpUser(user);
		this.database.addUser(yelpUser);

		return user.toString();

	}

	private String addReview(String line) throws NoSuchRestaurantException, NoSuchUserException {
		JsonReader jsonReader = Json
				.createReader(new StringReader(line.substring(line.indexOf(' ') + 2, line.length())));
		JsonObject reviewInputJson = jsonReader.readObject();

		String reviewID = "";
		for (int i = 0; i < 10; i++) {
			reviewID = reviewID + (int) Math.random() * 10;
		}

		JsonObject votes = javax.json.Json.createObjectBuilder().add("cool", 0).add("useful", 0).add("funny", 0)
				.build();

		JsonObjectBuilder j;
		j = javax.json.Json.createObjectBuilder();

		j.add("type", "review");
		j.add("business_id", reviewInputJson.getString("business_id"));
		j.add("votes", votes.toString());
		j.add("review_id", reviewID);
		j.add("text", reviewInputJson.getString("text"));
		j.add("stars", reviewInputJson.getString("stars"));
		j.add("user_id", reviewInputJson.getString("user_id"));
		j.add("date", reviewInputJson.get("date"));

		JsonObject review = j.build();
		YelpReview yelpReview = new YelpReview(review);

		if (this.database.getBusinessSet().stream().filter(b -> b.getBusinessID().equals(yelpReview.getBusinessID()))
				.collect(Collectors.toList()).isEmpty()) {
			throw new NoSuchRestaurantException();
		}

		if (this.database.getUserSet().stream().filter(u -> u.getUserID().equals(yelpReview.getUserID()))
				.collect(Collectors.toList()).isEmpty()) {
			throw new NoSuchUserException();
		}

		this.database.addReview(yelpReview);

		return review.toString();
	}

	/**
	 * Start a FibonacciServerMulti running on the default port.
	 */
	public static void main(String[] args) {
		try {
			YelpDB database = new YelpDB("data/restaurants.json", "data/users.json", "data/reviews.json");
			YelpDBServer server = new YelpDBServer(YELPDB_PORT, database);
			server.serve();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
