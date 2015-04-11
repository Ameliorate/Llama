package com.ame.llamanet;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Amelorate
 * Allows a single connection to a server to be made.
 */
public class ClientSideController {
	/**
	 * @param startThread Weather or not to start a new thread for checking for new traffic.
	 */
	public ClientSideController(String serverIP, int port, boolean startThread) throws UnknownHostException {
		this(serverIP, port, new SorterControllerImplementation(startThread));
	}

	public ClientSideController(String serverIP, int port, SorterController controller) {
		this.controller = controller;
		Socket socket;
		try {
			socket = new Socket(serverIP, port);
		}
		catch (IOException e) {
			throw new AssertionError(e);	// e probably doesn't happen as it doesn't actually mention what is means.
		}
		ConnectionImplementation connection = new ConnectionImplementation(socket);
		controller.registerConnectionToBeChecked(connection);

		server = connection;
	}

	public Connection server;
	public SorterController controller;
}
