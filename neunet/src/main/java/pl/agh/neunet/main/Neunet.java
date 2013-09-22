package pl.agh.neunet.main;

import java.util.Properties;

import pl.agh.neunet.util.properties.NetworkProperties;
import pl.agh.neunet.util.properties.PropertiesUtil;

public class Neunet {
    public Neunet() {
        Properties prop = PropertiesUtil.createDefaultProperties();
        NetworkProperties.setNetworkProperties(prop);
        run(prop);
    }

    public Neunet(String propertiesPath) {
        Properties prop = PropertiesUtil.loadProperties(propertiesPath);
        NetworkProperties.setNetworkProperties(prop);
        run(prop);
    }

    private void run(Properties prop) {
        NeuralNetwork network = new NeuralNetwork();

        network.createStructure();
        network.learn();
        network.saveCurrentWeightsToFile(true);

        while (true) {
            network.testNetwork();
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            new Neunet();
        } else {
            new Neunet(args[0]);
        }
    }
}
