package com.ame.llamanet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Amelorate
 * Controls various aspects of packet sorters.
 */
public class SorterController implements Runnable {
	/**
	 * @param startThread Weather or not to start a new thread automatically for checking connections and opening new ones.
	 */
	public SorterController(boolean startThread) {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage("com.ame.llamanet", ClasspathHelper.contextClassLoader()))
				.setScanners(new MethodAnnotationsScanner()));

		Set<Method> methods = reflections.getMethodsAnnotatedWith(Sorter.class);

		for (Method method : methods) {
			sorters.put(method.getName(), method);
		}

		if (startThread) {
			connectionCheckThread = new Thread(this, "Connection Check");
			connectionCheckThread.start();
		}
	}

	/**
	 * Whenever a client connects or disconnects, print it in the console if true.
	 */
	public static boolean printConnectAndDisconnectMessages = false;

	/**
	 * Print the contents of invalid packets to the console if true.
	 */
	public static boolean printContentsOfInvalidPackets = false;

	private Thread connectionCheckThread;
	private HashSet<Connection> connections = new HashSet<>();
	private HashMap<String, Method> sorters = new HashMap<>();

	/**
	 * Registers a connection to be checked regularly.
	 */
	public void registerConnectionToBeChecked(Connection connection) {	// There is never such a thing as a method whose name is too long with auto completion.
		connections.add(connection);
	}

	/**
	 * Sorts an incoming packet.
	 */
	public void sort(Connection connection, JSONArray packet) {
		for (Object contents : packet) {
			try {
				JSONObject inner = (JSONObject) contents;
				String sorter = (String) inner.get("sorter");
				Method sorterMethod = sorters.get(sorter);

				//						\/ I'm not sure this will work.
				sorterMethod.invoke(sorterMethod.getClass(), inner, connection);
			}
			catch (ClassCastException | NullPointerException e) {
				if (printContentsOfInvalidPackets)
					System.out.println("Malformed packet. Full packet text:\n" + packet);
			}
			catch (InvocationTargetException | IllegalAccessException ignored) {}
		}
	}

	public void checkOnce() {
		for (Connection connection : connections) {
			try {
				connection.check(this);
			}
			catch (ClosedException e) {
				connections.remove(connection);
			}
		}
	}

	@Override
	public void run() {
		while (true)
			checkOnce();
	}
}
