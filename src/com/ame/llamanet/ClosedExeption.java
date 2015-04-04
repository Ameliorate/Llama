package com.ame.llamanet;

/**
 * @author Amelorate
 * Indicates that the connection has been closed.
 */
public class ClosedExeption extends Exception {
	public ClosedExeption() {
		super();
	}

	public ClosedExeption(String message) {
		super(message);
	}
}
