package pl.agh.neunet.util.properties;

import java.util.Properties;

import pl.agh.neunet.neighborhood.NeighborhoodFunction;
import pl.agh.neunet.neighborhood.NeighborhoodFunctionType;

public class NetworkProperties {
	private static double initConscience;
	private static double pmin;

	private static NeighborhoodFunction kohonenNeighborhoodFunction;
	private static NeighborhoodFunction testNeighborhoodFunction;

	public static double getInitConscience() {
		return initConscience;
	}

	public static double getPmin() {
		return pmin;
	}

	public static NeighborhoodFunction getKohonenNeighborhoodFunction() {
		return kohonenNeighborhoodFunction;
	}

	public static NeighborhoodFunction getTestNeighborhoodFunction() {
		return testNeighborhoodFunction;
	}

	public static void setNetworkProperties(Properties prop) {
		initConscience = Double.parseDouble(prop.getProperty("conscience.initConscience"));
		pmin = Double.parseDouble(prop.getProperty("conscience.pmin"));

		kohonenNeighborhoodFunction = NeighborhoodFunctionType
				.valueOf(prop.getProperty("kohonen.neighborhoodFunction")).getNeighborhoodFunction();
		testNeighborhoodFunction = NeighborhoodFunctionType.valueOf(prop.getProperty("test.neighborhoodFunction"))
				.getNeighborhoodFunction();
	}
}
