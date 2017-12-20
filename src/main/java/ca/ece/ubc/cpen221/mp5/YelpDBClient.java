package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class YelpDBClient {

	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;

	public YelpDBClient(String hostname, int port) throws UnknownHostException, IOException {
		this.socket = new Socket(hostname, port);
		this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	public void close() throws IOException {
		this.input.close();
		this.output.close();
		this.socket.close();
	}

	/**
	 * Send a request to the server. Requires this is "open".
	 * 
	 * @param x
	 *            to find Fibonacci(x)
	 * @throws IOException
	 *             if network or server failure
	 */
	public void sendRequest(String x) throws IOException {
		this.output.print(x + "\n");
		this.output.flush(); // important! make sure x actually gets sent
	}

	/**
	 * Get a reply from the next request that was submitted. Requires this is
	 * "open".
	 * 
	 * @return the requested Fibonacci number
	 * @throws IOException
	 *             if network or server failure
	 */
	public String getReply() throws IOException {
		String reply = this.input.readLine();
		if (reply == null) {
			throw new IOException("connection terminated unexpectedly");
		}

		return new String(reply);

	}

	/**
	 * Use a FibonacciServer to find the first N Fibonacci numbers.
	 */
	public static void main(String[] args) {
		try {
			YelpDBClient client = new YelpDBClient("localhost", YelpDBServer.YELPDB_PORT);

			// send the requests to find the first N Fibonacci numbers

			while (true) {
				Scanner reader = new Scanner(System.in);

				System.out.println("Enter your request (if you wish to quit, enter \"quit\": ");
				String x = reader.nextLine();
				
				if( x.equals("quit")) {
					break;
				}

				client.sendRequest(x);
				System.out.println("request sent");

				String y = client.getReply();
				System.out.println("server replied");
				reader.close();
			}
			client.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
