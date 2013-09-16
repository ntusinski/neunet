package pl.agh.neunet.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import pl.agh.neunet.structure.NeuralNetwork;
import pl.agh.neunet.util.PropertiesUtil;

public class Neunet {
	private static final Scanner scan = new Scanner(System.in);

	private NeuralNetwork network;

	public Neunet() {
		Properties prop = PropertiesUtil.createDefaultProperties();
		run(prop);
	}

	public Neunet(String propertiesPath) {
		Properties prop = PropertiesUtil.loadProperties(propertiesPath);
		run(prop);
	}

	private void run(Properties prop) {
		network = new NeuralNetwork();

		network.configure(prop);

		if ("true".equals(prop.getProperty("kohonen.enable"))) {
			learn();
		}
		network.saveCurrentWeightsToFile(true);

		while (true) {
			testNetwork();
		}
	}

	private void learn() {
		double[][] results;

		network.learn();
		results = network.getLearningResults();

		System.out.println("Kohonen neurons weights:");
		for (int i = 0; i < results.length; i++) {
			System.out.print("< ");
			for (int j = 0; j < results[i].length; j++) {
				System.out.print(results[i][j] + ", ");
			}
			System.out.println(">");
		}
	}

	private void testNetwork() {
		List<Double> inputVector = new ArrayList<Double>();

		System.out.println("Enter input vector in size of " + network.getInputLayerSize());
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
