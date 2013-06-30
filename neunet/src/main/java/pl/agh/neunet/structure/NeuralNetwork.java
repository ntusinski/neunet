package pl.agh.neunet.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import pl.agh.neunet.activation.ActivationFunction;
import pl.agh.neunet.activation.ActivationFunctionType;
import pl.agh.neunet.csv.CsvReader;
import pl.agh.neunet.csv.CsvWriter;
import pl.agh.neunet.random.RandomDouble;

public class NeuralNetwork {
	private Properties prop;
	private int layersNumber;
	private ActivationFunction[] activationFunctions;
	private List<NetworkLayer> layers = new ArrayList<NetworkLayer>();

	public void configure(Properties prop) {
		this.prop = prop;
		layersNumber = Integer.parseInt(prop.getProperty("hiddenLayers")) + 2;

		parseActivationFunctions();
		createNeurons();
		createConnections();
		createWeights();
	}

	private void parseActivationFunctions() {
		String[] functionsNames = prop.getProperty("activationFunctions")
				.split(";");

		activationFunctions = new ActivationFunction[layersNumber];
		activationFunctions[0] = null;
		for (int i = 0; i < functionsNames.length; i++) {
			activationFunctions[i + 1] = ActivationFunctionType.valueOf(
					functionsNames[i]).getActivationFunction();
		}
	}

	private void createNeurons() {
		List<String> layersSizesString = Arrays.asList(prop.getProperty(
				"layersNeurons").split(";"));
		List<Integer> layersSizes = new ArrayList<Integer>();

		List<Neuron> neurons;

		for (int i = 0; i < layersNumber; i++) {
			layersSizes.add(Integer.parseInt(layersSizesString.get(i)));
		}

		for (int i = 0; i < layersNumber; i++) {
			neurons = new ArrayList<Neuron>();
			for (int j = 0; j < layersSizes.get(i); j++) {
				neurons.add(new Neuron(activationFunctions[i]));
			}
			layers.add(new NetworkLayer(layersSizes.get(i), neurons));
		}
	}

	private void createConnections() {
		NetworkLayer currentLayer;
		NetworkLayer nextLayer;

		NetworkConnection connection;

		for (Neuron inputLayerNeuron : layers.get(0).getNeurons()) {
			connection = new NetworkConnection(null, inputLayerNeuron);

			inputLayerNeuron.addBackConnection(connection);
		}

		for (int i = 0; i < layers.size() - 1; i++) {
			currentLayer = layers.get(i);
			nextLayer = layers.get(i + 1);
			for (Neuron currLayerNeuron : currentLayer.getNeurons()) {
				for (Neuron nextLayerNeuron : nextLayer.getNeurons()) {
					connection = new NetworkConnection(currLayerNeuron,
							nextLayerNeuron);
					currLayerNeuron.addFrontConnection(connection);
					nextLayerNeuron.addBackConnection(connection);
				}
			}
		}
	}

	private void createWeights() {
		for (Neuron neuron : layers.get(0).getNeurons()) {
			for (NetworkConnection connection : neuron.getBackConnections())
				connection.setWeight(1.0);
		}

		if (Boolean.parseBoolean(prop.getProperty("customWeights"))) {
			createWeightsFromFile();
		} else {
			createRandomWeights();
		}
	}

	private void createWeightsFromFile() {
		CsvReader reader = new CsvReader(
				prop.getProperty("inputWeightsFilepath"));
		Iterator<Double> lineIterator;
		Double weight;
		Double bias;

		for (int i = 0; i < layersNumber; i++) {
			for (Neuron neuron : layers.get(i).getNeurons()) {
				lineIterator = reader.readNextLine().iterator();
				if (i > 0) {
					bias = lineIterator.next();
					neuron.getBias().setValue(bias);
				}
				if (i < layersNumber - 1) {
					for (NetworkConnection connection : neuron
							.getFrontConnections()) {
						weight = lineIterator.next();
						connection.setWeight(weight);
					}
				}
			}
		}

	}

	private void createRandomWeights() {
		double lowerValue = Double.parseDouble(prop
				.getProperty("customWeightsLowerValue"));
		double upperValue = Double.parseDouble(prop
				.getProperty("customWeightsUpperValue"));
		RandomDouble randomDouble = new RandomDouble(lowerValue, upperValue);

		for (int i = 0; i < layersNumber; i++) {
			for (Neuron neuron : layers.get(i).getNeurons()) {
				if (i > 0) {
					neuron.getBias().setValue(randomDouble.nextDouble());
				}
				if (i < layersNumber - 1) {
					for (NetworkConnection connection : neuron
							.getFrontConnections()) {
						connection.setWeight(randomDouble.nextDouble());
					}
				}
			}
		}
		saveCurrentWeightsToFile(false);
	}

	public void saveCurrentWeightsToFile(boolean output) {
		CsvWriter writer = output ? new CsvWriter(
				prop.getProperty("outputWeightsFilepath")) : new CsvWriter(
				prop.getProperty("inputWeightsFilepath"));
		List<Double> nextLine;

		for (int i = 0; i < layersNumber; i++) {
			for (Neuron neuron : layers.get(i).getNeurons()) {
				nextLine = new ArrayList<Double>();
				if (i > 0) {
					nextLine.add(neuron.getBias().getValue());
				}
				if (i < layersNumber - 1) {
					for (NetworkConnection connection : neuron
							.getFrontConnections()) {
						nextLine.add(connection.getWeight());
					}
				}
				writer.writeNextLine(nextLine);
			}
		}

		writer.close();
	}

	public List<Double> testNetwork(List<Double> inputVector) {
		NetworkLayer inputLayer = layers.get(0);
		Neuron inputLayerNeuron;
		List<Double> outputVector = new ArrayList<Double>();

		for (int i = 0; i < getInputLayerSize(); i++) {
			inputLayerNeuron = inputLayer.getNeurons().get(i);
			inputLayerNeuron.setOutputSignal(inputVector.get(i));
		}

		for (int i = 1; i < layers.size(); i++) {
			for (Neuron neuron : layers.get(i).getNeurons()) {
				neuron.updateOutputSignal();
			}
		}

		for (Neuron outputNeuron : layers.get(layers.size() - 1).getNeurons()) {
			outputVector.add(outputNeuron.getOutputSignal());
		}

		return outputVector;
	}

	public int getInputLayerSize() {
		return layers.get(0).getLayerSize();
	}
}
