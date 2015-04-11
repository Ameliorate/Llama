package com.ame.llamanet;

import org.json.simple.JSONArray;

import java.util.ArrayList;

/**
 * @author Amelorate
 * Logs all attempts to use this. Useful in unit testing
 */
public class DummySorterController implements SorterController {
	@Override
	public void registerConnectionToBeChecked(Connection connection) {
		log.add("checkregister:" + connection);
	}

	@Override
	public void sort(Connection connection, JSONArray packet) {
		log.add("sort:" + connection + "," + connection);
	}

	@Override
	public void checkOnce() {
		log.add("checkonce");
	}

	public ArrayList<String> log = new ArrayList<>();
}
