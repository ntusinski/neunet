package pl.agh.neunet.util.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
	public static Properties createDefaultProperties() {
		Properties prop = new LinkedProperties();

		prop.setProperty("hiddenLayers", "1");
		prop.setProperty("layersNeurons", "3;8;2");
		prop.setProperty("activationFunctions", "LINEAR;LINEAR");
		prop.setProperty("customWeights", "false");
		prop.setProperty("customWeightsLowerValue", "0.0");
		prop.setProperty("customWeightsUpperValue", "0.0");
		prop.setProperty("inputWeightsFilepath", "inputWeights.csv");
		prop.setProperty("outputWeightsFilepath", "outputWeights.csv");

		prop.setProperty("kohonen.enable", "true");
		prop.setProperty("kohonen.epochsNumbers", "500;1000;1500;2000");
		prop.setProperty("kohonen.learningRates", "0.06;0.03;0.015;0.0075");
		prop.setProperty("kohonen.neighborhoodFunction", "NEIGHBORHOOD_1D");
		prop.setProperty("kohonen.learningFile", "learning.csv");

		prop.setProperty("grossberg.enable", "true");
		prop.setProperty("grossberg.epochsNumbers", "2000;12000;32000;72000;152000");
		prop.setProperty("grossberg.learningRates", "0.0;0.15;0.075;0.0185;0.00117");

		try {
			prop.storeToXML(new FileOutputStream("neunet.properties"), null);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return prop;
	}

	public static Properties loadProperties(String propertiesPath) {
		Properties prop = new LinkedProperties();

		try {
			prop.loadFromXML(new FileInputStream(propertiesPath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return prop;
	}
}
