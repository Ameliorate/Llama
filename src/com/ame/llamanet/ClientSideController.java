package com.ame.llamanet;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Amelorate
 * Allows a single connection to a server to be made.
 */
public class ClientSideController {
	public ClientSideController(String serverIP, int port) throws UnknownHostException {
		this(serverIP, port, new SorterControllerImplementation(true));
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
	}

	public SorterController controller;
}
