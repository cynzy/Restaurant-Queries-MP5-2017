package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

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

}
