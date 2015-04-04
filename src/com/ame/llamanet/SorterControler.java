package com.ame.llamanet;

import org.json.simple.JSONObject;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

/**
 * @author Amelorate
 * Controls various aspects of packet sorters.
 */
public class SorterControler {
	public SorterControler() {
		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage("com.ame.llamanet", ClasspathHelper.contextClassLoader()))
				.setScanners(new MethodAnnotationsScanner()));

		Set<Method> methods = reflections.getMethodsAnnotatedWith(Sorter.class);

		for (Method method : methods) {
			sorters.put(method.getName(), method);
		}
	}

	private HashMap<String, Method> sorters = new HashMap<>();
}
