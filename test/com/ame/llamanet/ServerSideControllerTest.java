package com.ame.llamanet;

import com.ame.llamanet.builtinsorters.LogToArraySorter;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author Amelorate
 */
public class ServerSideControllerTest {
	@Test
	public void testEverything() throws Exception {		// Here is just seems easier to test everything by sending a packet.
		ServerSideController serverSideController = new ServerSideController(1000, true);
		ClientSideController clientSideController = new ClientSideController("127.0.0.1", 1000, true);
		LogToArraySorter.send("foobar", clientSideController.server);

		try {
			Thread.sleep(100);		// We wait a bit to let the other threads to catch up. 1/10th of a second should be enough.
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		if (!LogToArraySorter.log.contains("foobar"))
			throw new Exception("Log does not contain foobar; something doesn't work in the server side controller or sorter controller.");

		LogToArraySorter.log = new ArrayList<>();
		SorterControllerImplementation serverSorterController = (SorterControllerImplementation) serverSideController.controller;

		for (Connection connection : serverSorterController.connections) {
			LogToArraySorter.send("testbar", connection);
		}

		try {
			Thread.sleep(100);
		}
		catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}

		if (!LogToArraySorter.log.contains("testbar"))
			throw new Exception("Log does not contain testbar; something doesn't work in the client side controller or sorter controller");
	}
}