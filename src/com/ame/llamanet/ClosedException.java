package com.ame.llamanet;

/**
 * @author Amelorate
 * Indicates that the connection has been closed.
 */
public class ClosedException extends Exception {
	public ClosedException() {
		super();
	}

	public ClosedException(String message) {
		super(message);
	}
}
