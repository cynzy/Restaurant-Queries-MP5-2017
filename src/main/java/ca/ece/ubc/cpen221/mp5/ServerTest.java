package ca.ece.ubc.cpen221.mp5;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

public class ServerTest {

	@Test
	public void serverTest1() throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Request:");
		String request = scanner.nextLine();
		YelpDB database = new YelpDB("data/restaurants.json", "data/users.json", "data/reviews.json");
		String reply = null;
		try {
			final YelpDBServer server = new YelpDBServer(4949,database);
			Thread serverThread = new Thread(()->
			{
				try {
					server.serve();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			});
			serverThread.start();
			
			YelpDBClient client = new YelpDBClient("localhost", 4949);
			client.sendRequest(request);
			reply = client.getReply();
			System.out.println(reply);
			
			client.close();
			
		}
		catch(IOException e) {
			fail();
		}
		scanner.close();
		System.out.println("Done");
	}
}
