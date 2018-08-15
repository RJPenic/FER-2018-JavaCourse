package hr.fer.zemris.java.webserver;

import java.util.Scanner;

/**
 * Program which starts and shuts down the server when user requests such
 * behavior
 * 
 * @author Rafael Josip Penić
 *
 */
public class MainProgram {

	/**
	 * Main method
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer("server.properties");
		server.start();

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter anything if you wish to terminate the server.");
		sc.nextLine();
		sc.close();

		System.out.println("Stopping server...");
		server.stop();
	}
}
