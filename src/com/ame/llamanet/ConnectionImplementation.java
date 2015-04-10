package com.ame.llamanet;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Amelorate
 * A connection to another mechine. Doesn't differ between client and server.
 */
public class ConnectionImplementation implements Connection {
	public ConnectionImplementation(Socket connectionSocket) {
		connection = connectionSocket;
		try {
			connectionGet = new DataInputStream(connection.getInputStream());
			connectionOut = new DataOutputStream(connection.getOutputStream());
		}
		catch (IOException e) {
			close();
			System.out.println("[Error] The socket is closed while initialising a ConnectionImplementation. Strange... Tell Somebody about this.");
		}

		if (SorterControllerImplementation.printConnectAndDisconnectMessages)
			System.out.println("Connection " + toString() + " opened.");
	}

	private Socket connection;
	private DataInputStream connectionGet;
	private DataOutputStream connectionOut;

	@Override
	public void check(SorterController controller) throws ClosedException {
		if (connection == null)
			throw new ClosedException();


		while(true) {
			try {
				if (connectionGet.available() > 0) {	// Basically non blocking IO.
					String data = connectionGet.readUTF();
					try {
						JSONArray dataJSON = (JSONArray) JSONValue.parseWithException(data);
						controller.sort(this, dataJSON);
					}
					catch (ClassCastException | ParseException e) {
						if (SorterControllerImplementation.printContentsOfInvalidPackets)
							System.out.println("Malformed packet. Full packet text:\n" + data);
					}
				}
				else
					break;
			}
			catch (IOException e) {
				close();	// I believe this happens when the connection is closed.
				break;
			}
		}
	}

	@Override
	public void close() {
		try {
			connection.close();
		}
		catch (IOException ignored) {}
		connection = null;
		connectionGet = null;
		connectionOut = null;

		if (SorterControllerImplementation.printConnectAndDisconnectMessages)
			System.out.println("Connection " + toString() + " closed.");

	}

	@Override
	public void send(JSONArray data) throws ClosedException {
		if (connection == null)
			throw new ClosedException();
		try {
			connectionOut.writeUTF(data.toString());
		}
		catch (IOException e) {
			close();	// I believe this happens when the connection is closed.
		}
	}
}
