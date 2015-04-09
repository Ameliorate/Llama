package com.ame.llamanet;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

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

	public void closeOnNextCheck() {
		closeOnNextCheck = true;
	}

	public void closeOnNextSend() {
		closeOnNextSend = true;
	}

	private boolean closeOnNextSend = false;
	private boolean closeOnNextCheck = false;

	public ArrayList<String> log = new ArrayList<>();
}
