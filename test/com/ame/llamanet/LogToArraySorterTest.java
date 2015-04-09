package com.ame.llamanet;

import com.ame.llamanet.builtinsorters.LogToArraySorter;
import junit.framework.Assert;
import org.json.simple.JSONObject;
import org.junit.Test;

/**
 * @author Amelorate
 */
public class LogToArraySorterTest {

	@Test
	@SuppressWarnings("unchecked")
	public void testLogToArray() throws Exception {
		JSONObject packet = new JSONObject();
		packet.put("message", "foo");

		LogToArraySorter.LogToArray(packet, new DummyConnection());

		if (!LogToArraySorter.log.contains("foo"))
			throw new Exception("Didn't log foo");
	}

	@Test
	public void testSend() throws Exception {
		DummyConnection connection = new DummyConnection();

		LogToArraySorter.send("foo", connection);

		if (!connection.log.contains("send:[{\"sorter\":\"LogToArray\",\"message\":\"foo\"}]"))
			throw new Exception("Didn't send right packet.");
	}

	@Test
	public void testGetInnerPacket() throws Exception {
		JSONObject inner = LogToArraySorter.getInnerPacket("foo");

		Assert.assertEquals(inner.toString(), "{\"sorter\":\"LogToArray\",\"message\":\"foo\"}");
	}
}