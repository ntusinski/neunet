package pl.agh.neunet.util.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    public static Properties createDefaultProperties() {
        Properties prop = new LinkedProperties();

        prop.setProperty("hiddenLayers", "1");
        prop.setProperty("layersNeurons", "3;9;1");
        prop.setProperty("activationFunctions", "LINEAR;LINEAR");

        prop.setProperty("customWeights", "false");
        prop.setProperty("customWeightsLowerValue", "0.0");
        prop.setProperty("customWeightsUpperValue", "1.0");

        prop.setProperty("inputWeightsFilepath", "inputWeights.csv");
        prop.setProperty("outputWeightsFilepath", "outputWeights.csv");

        prop.setProperty("conscience.initConscience", "0.8");
        prop.setProperty("conscience.pmin", "0.06");

        prop.setProperty("kohonen.enable", "false");
        prop.setProperty("grossberg.enable", "false");
        prop.setProperty("bp.enable", "true");

        prop.setProperty("kohonen.neighborhoodFunction", "CONSCIENCE");
        prop.setProperty("test.neighborhoodFunction", "CONTEST");

        prop.setProperty("kohonen.epochsNumbers", "8000;8000;8000;8000");
        prop.setProperty("grossberg.epochsNumbers", "2000;4000;8000;16000;32000");
        prop.setProperty("bp.epochsNumbers", "8000;8000;8000;8000");

        prop.setProperty("kohonen.learningRates", "0.06;0.03;0.015;0.0075");
        prop.setProperty("grossberg.learningRates", "0.3;0.15;0.075;0.0185;0.00117");
        prop.setProperty("bp.learningRates", "0.06;0.03;0.015;0.0075");
        
        prop.setProperty("kohonen.learningFile", "learning.csv");
        prop.setProperty("bp.learningFile", "learning.csv");

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
