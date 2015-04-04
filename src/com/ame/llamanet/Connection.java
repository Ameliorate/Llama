package com.ame.llamanet;

import org.json.simple.JSONArray;

/**
 * @author Amelorate
 */
public interface Connection {
	/**
	 * Checks the connection for new traffic.
	 */
	void check(SorterControler controler) throws ClosedExeption;

	/**
	 * Closes the socket and preforms some cleanup.
	 */
	void close();

	void send(JSONArray data) throws ClosedExeption;
}
