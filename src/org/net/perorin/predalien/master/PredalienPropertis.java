package org.net.perorin.predalien.master;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PredalienPropertis {

	private static Properties properties = null;

	private static void initialize() {
		properties = new Properties();
		try {
			InputStream istream = PredalienPropertis.class.getResource("Predalien.properties").openStream();
			properties.load(istream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getPropertiesAsInteger(String key) {
		if (properties == null) {
			initialize();
		}
		try {
			return Integer.parseInt(properties.getProperty(key));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static String getPropertisAsString(String key) {
		if (properties == null) {
			initialize();
		}
		return properties.getProperty(key);
	}

	public static long getPropertiesAsLong(String key) {
		if (properties == null) {
			initialize();
		}
		try {
			return Long.parseLong(properties.getProperty(key));
		} catch (NumberFormatException e) {
			return 0L;
		}
	}

}
