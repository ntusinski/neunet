package pl.agh.neunet.structure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import pl.agh.neunet.activation.ActivationFunction;
import pl.agh.neunet.activation.ActivationFunctionType;
import pl.agh.neunet.random.RandomDouble;
import pl.agh.neunet.reader.CsvReader;
import pl.agh.neunet.reader.CsvWriter;

public class NeuralNetwork {
	private Properties prop;

	private ActivationFunction activationFunction;
	private double bias;
	private List<NetworkLayer> layers = new ArrayList<NetworkLayer>();

	public void configure(Properties prop) {
		activationFunction = ActivationFunctionType.valueOf(
				prop.getProperty("activationFunction")).getActivationFunction();
		createNeurons();
		createConnections();
		createWeights();
	}

	private void createNeurons() {
		int layersNumber = Integer.parseInt(prop.getProperty("hiddenLayers")) + 2;

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
				neurons.add(new Neuron(activationFunction, bias));
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
		if (Boolean.parseBoolean(prop.getProperty("customWeights"))) {
			createWeightsFromFile();
		} else {
			createRandomWeights();
		}
	}

	private void createWeightsFromFile() {
		CsvReader reader = new CsvReader(prop.getProperty("weightsFilepath"));
		Iterator<Double> weightsIterator;

		bias = reader.readNextLine().get(0);

		weightsIterator = reader.readNextLine().iterator();
		for (Neuron inputLayerNeuron : layers.get(0).getNeurons()) {
			inputLayerNeuron.getBackConnections().get(0)
					.setWeight(weightsIterator.next());
		}

		for (int i = 0; i < layers.size() - 1; i++) {
			weightsIterator = reader.readNextLine().iterator();
			for (Neuron neuron : layers.get(i).getNeurons()) {
				for (NetworkConnection connection : neuron
						.getFrontConnections())
					connection.setWeight(weightsIterator.next());
			}
		}
	}

	private void createRandomWeights() {
		CsvWriter writer = new CsvWriter(prop.getProperty("weightsFilepath"));
		RandomDouble randomDouble = new RandomDouble(0.0, 1.0);
		double current;
		List<Double> nextLine;

		current = randomDouble.nextDouble();
		bias = current;
		nextLine = new ArrayList<Double>();
		nextLine.add(current);
		writer.writeNextLine(nextLine);

		nextLine = new ArrayList<Double>();
		for (Neuron inputLayerNeuron : layers.get(0).getNeurons()) {
			current = randomDouble.nextDouble();
			inputLayerNeuron.getBackConnections().get(0).setWeight(current);
			nextLine.add(current);
		}
		writer.writeNextLine(nextLine);

		for (int i = 0; i < layers.size() - 1; i++) {
			nextLine = new ArrayList<Double>();
			for (Neuron neuron : layers.get(i).getNeurons()) {
				for (NetworkConnection connection : neuron
						.getFrontConnections()) {
					current = randomDouble.nextDouble();
					connection.setWeight(current);
					nextLine.add(current);
				}
			}
			writer.writeNextLine(nextLine);
		}
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
