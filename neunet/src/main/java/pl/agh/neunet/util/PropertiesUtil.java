package pl.agh.neunet.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
	public static Properties createDefaultProperties() {
		Properties prop = new Properties();

		prop.setProperty("hiddenLayers", "1");
		prop.setProperty("layersNeurons", "3;8;1");
		prop.setProperty("activationFunctions", "LINEAR;LINEAR");
		prop.setProperty("customWeights", "false");
		prop.setProperty("customWeightsLowerValue", "0.0");
		prop.setProperty("customWeightsUpperValue", "1.0");
		prop.setProperty("inputWeightsFilepath", "inputWeights.csv");
		prop.setProperty("outputWeightsFilepath", "outputWeights.csv");

		prop.setProperty("kohonen.enable", "true");
		prop.setProperty("kohonen.epochsNumbers", "8000;8000;8000;8000");
		prop.setProperty("kohonen.learningRates", "0.06;0.03;0.015;0.0075");
		prop.setProperty("kohonen.neighborhoodFunction", "CONTEST");
		prop.setProperty("kohonen.learningFile", "learning.csv");

		prop.setProperty("grossberg.enable", "true");
		prop.setProperty("grossberg.learningRates", "0.01;0.005;0.0025;0.00125");

		try {
			prop.storeToXML(new FileOutputStream("neunet.properties"), null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return prop;
	}

	public static Properties loadProperties(String propertiesPath) {
		Properties prop = new Properties();

		try {
			prop.loadFromXML(new FileInputStream(propertiesPath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return prop;
	}
}
