package pl.agh.neunet.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import pl.agh.neunet.structure.NeuralNetwork;
import pl.agh.neunet.util.PropertiesUtil;

public class Neunet {
	private static final Scanner scan = new Scanner(System.in);

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

		System.out.println("Enter input vector in size of "
				+ network.getInputLayerSize());
		for (int i = 0; i < network.getInputLayerSize(); i++) {
			inputVector.add(scan.nextDouble());
		}

		System.out.println("Result is: " + network.testNetwork(inputVector));
		System.out.println();
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			new Neunet();
		} else {
			new Neunet(args[0]);
		}
	}
}
