package com.ame.llamanet;

import org.json.simple.JSONArray;

/**
 * @author Amelorate
 */
public interface Connection {
	/**
	 * Checks the connection for new traffic.
	 */
	void check(SorterController controler) throws ClosedException;

	/**
	 * Closes the socket and preforms some cleanup.
	 */
	void close();

	void send(JSONArray data) throws ClosedException;
}
