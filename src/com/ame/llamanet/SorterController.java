package com.ame.llamanet;

import org.json.simple.JSONArray;

/**
 * @author Amelorate
 */
public interface SorterController {
	/**
	 * Register a connection to be checked regularly for new traffic.
	 */
	void registerConnectionToBeChecked(Connection connection);

	/**
	 * Sort an incoming packet
	 * @param connection The connection sending the packet.
	 */
	void sort(Connection connection, JSONArray packet);

	/**
	 * Check all of the connections once.
	 */
	void checkOnce();
}
