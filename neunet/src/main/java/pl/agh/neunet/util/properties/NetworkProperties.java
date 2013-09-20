package pl.agh.neunet.util.properties;

import java.util.Properties;

public class NetworkProperties {
	private static double initConscience;
	private static double pmin;

	public static double getInitConscience() {
		return initConscience;
	}

	public static double getPmin() {
		return pmin;
	}

	public static void setNetworkProperties(Properties prop) {
		initConscience = Double.parseDouble(prop.getProperty("conscience.initConscience"));
		pmin = Double.parseDouble(prop.getProperty("conscience.pmin"));
	}
}
