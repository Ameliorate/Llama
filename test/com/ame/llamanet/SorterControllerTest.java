package com.ame.llamanet;

import com.ame.llamanet.builtinsorters.LogToArraySorter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * @author Amelorate
 */
public class SorterControllerTest {

	@org.junit.Before
	public void setUp() throws Exception {
		controller = new SorterControllerImplementation(false);
		controller.registerConnectionToBeChecked(dummyConnection);
	}

	@org.junit.Test
	public void testRegisterConnectionToBeChecked() throws Exception {
		DummyConnection dummy = new DummyConnection();
		controller.registerConnectionToBeChecked(dummy);
		dummy.log = new ArrayList<>();

		if (!controller.connections.contains(dummy))
			throw new Exception("Connection pool doesn't contain dummy when it should.");
	}

	@org.junit.Test
	@SuppressWarnings("unchecked")
	public void testSort() throws Exception {
		JSONArray outer = new JSONArray();
		JSONObject inner1 = LogToArraySorter.getInnerPacket("boo");
		JSONObject inner2 = LogToArraySorter.getInnerPacket("fah");
		outer.add(inner1);
		outer.add(inner2);

		controller.sort(new DummyConnection(), outer);

		if (!LogToArraySorter.log.contains("boo"))
			throw new Exception("The packet sorter doesn't sort any packets or the LogToArraySorter is nonfunctional.");
		if (!LogToArraySorter.log.contains("fah"))
			throw new Exception("The packet sorter sorts only the first packet in the list.");
	}

	@org.junit.Test
	public void testCheckOnce() throws Exception {
		dummyConnection.log = new ArrayList<>();
		controller.checkOnce();

		if (!dummyConnection.log.contains("checked"))
			throw new Exception("checkOnce doesn't check all the connections.");

		dummyConnection.closeOnNextCheck();
		controller.checkOnce();

		if (controller.connections.contains(dummyConnection))
			throw new Exception("checkOnce doesn't delete when connection throws closed exception.");
	}

	private DummyConnection dummyConnection = new DummyConnection();
	private SorterControllerImplementation controller;
}