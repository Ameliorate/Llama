package com.ame.llamanet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * @author Amelorate
 * Opens a server socket and allows clients to connect.
 */
public class ServerSideController implements Runnable {
	public ServerSideController(int port, boolean startThread) {
		this(port, new SorterControllerImplementation(false), startThread);
	}

	public ServerSideController(int port, SorterController controller, boolean startThread) {
		this.controller = controller;
		try {
			serverSocket = new ServerSocket(port);
		}
		catch (IOException e) {
			throw new AssertionError(e);
		}

		if (startThread) {
			checkThread = new Thread(this, "ServerSideController Checker");
			checkThread.start();
		}
		try {
			serverSocket.setSoTimeout(1);
		}
		catch (SocketException e) {
			throw new AssertionError(e);
		}
	}

	@Override
	public void run() {
		//noinspection InfiniteLoopStatement
		while (true) {
			controller.checkOnce();
			try {
				Socket newClientSocket = serverSocket.accept();
				if (newClientSocket != null) {
					Connection newClientConnection = new ConnectionImplementation(newClientSocket);
					controller.registerConnectionToBeChecked(newClientConnection);
					newClientSocket = null;
				}
			}
			catch (SocketTimeoutException ignored) {}
			catch (IOException e) {
				throw new AssertionError(e);
			}
		}
	}

	private ServerSocket serverSocket;

	/**
	 * The thread where all of the connections are checked and accepted.
	 */
	public Thread checkThread;

	/**
	 * The internal controller handling packet sorting and connection checking.
	 */
	public SorterController controller;

}
