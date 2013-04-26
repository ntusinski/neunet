package pl.agh.neunet.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import pl.agh.neunet.structure.NeuralNetwork;
import pl.agh.neunet.util.ConsoleUtil;
import pl.agh.neunet.util.PropertiesUtil;

public class Neunet {
	public Neunet() {
		Properties prop = PropertiesUtil.createDefaultProperties();
		run(prop);
	}

	public Neunet(String propertiesPath) {
		Properties prop = PropertiesUtil.loadProperties(propertiesPath);
		run(prop);
	}

	private void run(Properties prop) {
		NeuralNetwork network = new NeuralNetwork();

		network.configure(prop);
		while (true) {
			testNetwork(network);
		}
	}

	private void testNetwork(NeuralNetwork network) {
		List<Double> inputVector = new ArrayList<Double>();

		for (int i = 0; i < network.getInputLayerSize(); i++) {
			inputVector.add(ConsoleUtil.nextDouble());
		}

		System.out.println(network.testNetwork(inputVector));
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			new Neunet();
		} else {
			new Neunet(args[0]);
		}
	}
}
