package com.ame.llamanet;

import org.junit.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Amelorate
 */
public class ClientSideControllerTest {
	@Test
	public void testConstructor() throws Exception {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(999);
		}
		catch (IOException ignored) {}
		DummySorterController dummySorterController = new DummySorterController();

		ClientSideController clientSideController = new ClientSideController("127.0.0.1", 999, dummySorterController);
		Socket serverSideClientSocket = null;
		try {
			if (serverSocket != null)
				serverSideClientSocket =  serverSocket.accept();	// The if not null sciences a warning.
		}
		catch (IOException ignored) {}

		if (!dummySorterController.log.contains("checkregister"))
			throw new Exception("dummySorterController.log doesn't contain \"checkregister\", which means the connection wasn't registered.");	// Strange how all of the above code is all for this.
	}
}