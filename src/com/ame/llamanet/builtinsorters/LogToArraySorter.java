package com.ame.llamanet.builtinsorters;

import com.ame.llamanet.ClosedException;
import com.ame.llamanet.Connection;
import com.ame.llamanet.Sorter;
import com.ame.llamanet.SorterController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * @author Amelorate
 * Logs the given string to an array. Not saved to disc.
 */
public class LogToArraySorter {
	@Sorter
	@SuppressWarnings("unused")
	public static void LogToArray(JSONObject packet, Connection connection) {
		String message = null;
		try {
			message = (String) packet.get("message");
		}
		catch (ClassCastException e) {
			if (SorterController.printContentsOfInvalidPackets)
				System.out.println("Invalid packet:\n" + packet);
		}
		if (message != null)
			log.add(message);
	}


	@SuppressWarnings("unchecked")
	public static void send(String message, Connection sending) {
		JSONArray outer = new JSONArray();
		outer.add(getInnerPacket(message));

		try {
			sending.send(outer);
		}
		catch (ClosedException ignored) {}
	}

	@SuppressWarnings("unchecked")
	public static JSONObject getInnerPacket(String message) {
		JSONObject inner = new JSONObject();
		inner.put("message", message);
		inner.put("sorter", "LogToArray");

		return inner;
	}

	public static ArrayList<String> log = new ArrayList<>();
}
