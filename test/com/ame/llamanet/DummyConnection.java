package com.ame.llamanet;

import org.json.simple.JSONArray;

import java.util.ArrayList;

/**
 * @author Amelorate
 * Just logs all method calls and their args. Used for unit testing.
 */
public class DummyConnection implements Connection {
	@Override
	public void check(SorterController controller) throws ClosedException {
		if (closeOnNextCheck) {
			log.add("checkAndClosed");
			closeOnNextCheck = false;
			throw new ClosedException();
		}
		else
			log.add("checked");
	}

	@Override
	public void close() {
		log.add("closed");
	}

	@Override
	public void send(JSONArray data) throws ClosedException {
		if (closeOnNextSend) {
			log.add("sendAndClosed");
			closeOnNextSend = false;
			throw new ClosedException();
		}
		else
			log.add("send:" + data);
	}

	@Override
	public String toString() {
		return identifier;
	}

	/**
	 * Makes the connection throw a ClosedException on the next check call.
	 */
	public void closeOnNextCheck() {
		closeOnNextCheck = true;
	}

	/**
	 * Makes the connection throw a ClosedException on the next send call.
	 */
	public void closeOnNextSend() {
		closeOnNextSend = true;
	}

	private boolean closeOnNextSend = false;
	private boolean closeOnNextCheck = false;

	/**
	 * What to return when toString is called.
	 */
	public String identifier;

	/**
	 * Logs method calls and their arguments here.
	 */
	public ArrayList<String> log = new ArrayList<>();
}
