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
import pl.agh.neunet.neighborhood.NeighborhoodFunction;
import pl.agh.neunet.neighborhood.NeighborhoodFunctionType;
import pl.agh.neunet.random.RandomDouble;
import pl.agh.neunet.trainer.GrossbergTrainer;
import pl.agh.neunet.trainer.KohonenTrainer;

public class NeuralNetwork {
	private Properties prop;
	private int layersNumber;
	private ActivationFunction[] activationFunctions;
	private List<NetworkLayer> layers = new ArrayList<NetworkLayer>();
	private Neuron winnerNeuron = null;

	public void configure(Properties prop) {
		this.prop = prop;
		layersNumber = Integer.parseInt(prop.getProperty("hiddenLayers")) + 2;

		parseActivationFunctions();
		createNeurons();
		createConnections();
		createWeights();
	}

	private void parseActivationFunctions() {
		String[] functionsNames = prop.getProperty("activationFunctions").split(";");

		activationFunctions = new ActivationFunction[layersNumber];
		activationFunctions[0] = null;
		for (int i = 0; i < functionsNames.length; i++) {
			activationFunctions[i + 1] = ActivationFunctionType.valueOf(functionsNames[i]).getActivationFunction();
		}
	}

	private void createNeurons() {
		List<String> layersSizesString = Arrays.asList(prop.getProperty("layersNeurons").split(";"));
		List<Integer> layersSizes = new ArrayList<Integer>();

		List<Neuron> neurons;
		Neuron lastNeuron;
		Neuron currNeuron = null;

		for (int i = 0; i < layersNumber; i++) {
			layersSizes.add(Integer.parseInt(layersSizesString.get(i)));
		}

		List<List<Neuron>> neuronArray = new ArrayList<List<Neuron>>();
		int xSize = (int) Math.sqrt(layersSizes.get(1));
		int ySize = 0;
		int currIndex = 0;

		for (int i = 0; i < layersNumber; i++) {
			neurons = new ArrayList<Neuron>();
			for (int j = 0; j < layersSizes.get(i); j++) {
				lastNeuron = currNeuron;
				currNeuron = new Neuron(activationFunctions[i]);
				neurons.add(currNeuron);

				if (i == 1) {
					if (j > 0) {
						currNeuron.addNeighbor1D(lastNeuron);
						lastNeuron.addNeighbor1D(currNeuron);
					}

					try {
						neuronArray.get(ySize);
					} catch (IndexOutOfBoundsException e) {
						neuronArray.add(new ArrayList<Neuron>());
					}
					neuronArray.get(ySize).add(currNeuron);
					currIndex = (currIndex + 1) % xSize;
					if (currIndex == 0) {
						ySize++;
					}
					for (int k = 0; k < neuronArray.size(); k++) {
						for (int l = 0; l < neuronArray.get(k).size(); l++) {
							Neuron n = neuronArray.get(k).get(l);
							if (k > 0) {
								n.addNeighbor2D(neuronArray.get(k - 1).get(l));
							}
							if (l > 0) {
								n.addNeighbor2D(neuronArray.get(k).get(l - 1));
							}
							try {
								neuronArray.get(k + 1).get(l);
								n.addNeighbor2D(neuronArray.get(k + 1).get(l));
							} catch (IndexOutOfBoundsException e) {
							}
							try {
								neuronArray.get(k).get(l + 1);
								n.addNeighbor2D(neuronArray.get(k + 1).get(l + 1));
							} catch (IndexOutOfBoundsException e) {
							}
						}
					}
				}
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
					connection = new NetworkConnection(currLayerNeuron, nextLayerNeuron);
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
		CsvReader reader = new CsvReader(prop.getProperty("inputWeightsFilepath"));
		Iterator<Double> lineIterator;

		for (int i = 1; i < layersNumber; i++) {
			for (Neuron neuron : layers.get(i).getNeurons()) {
				lineIterator = reader.readNextLine().iterator();
				neuron.getBias().setValue(lineIterator.next());
				for (NetworkConnection connection : neuron.getBackConnections()) {
					connection.setWeight(lineIterator.next());
				}
			}
		}
	}

	private void createRandomWeights() {
		double lowerValue = Double.parseDouble(prop.getProperty("customWeightsLowerValue"));
		double upperValue = Double.parseDouble(prop.getProperty("customWeightsUpperValue"));
		RandomDouble randomDouble = new RandomDouble(lowerValue, upperValue);

		for (int i = 1; i < layersNumber; i++) {
			for (Neuron neuron : layers.get(i).getNeurons()) {
				neuron.getBias().setValue(randomDouble.nextDouble());
				for (NetworkConnection connection : neuron.getBackConnections()) {
					connection.setWeight(randomDouble.nextDouble());
				}
			}
		}
		saveCurrentWeightsToFile(false);
	}

	public void saveCurrentWeightsToFile(boolean output) {
		CsvWriter writer = output ? new CsvWriter(prop.getProperty("outputWeightsFilepath")) : new CsvWriter(
				prop.getProperty("inputWeightsFilepath"));
		List<Double> nextLine;

		for (int i = 1; i < layersNumber; i++) {
			for (Neuron neuron : layers.get(i).getNeurons()) {
				nextLine = new ArrayList<Double>();
				nextLine.add(neuron.getBias().getValue());
				for (NetworkConnection connection : neuron.getBackConnections()) {
					nextLine.add(connection.getWeight());
				}
				writer.writeNextLine(nextLine);
			}
		}

		writer.close();
	}

	public void learn() {
		String[] rawEpochsNumbers = prop.getProperty("kohonen.epochsNumbers").split(";");
		String[] rawLearningRates = prop.getProperty("kohonen.learningRates").split(";");
		String[] grossRawEpochNumbers = prop.getProperty("grossberg.epochsNumbers").split(";");
		String[] grossRawLearningRates = prop.getProperty("grossberg.learningRates").split(";");

		List<Integer> epochsNumbers = new ArrayList<Integer>();
		List<Integer> grossEpochsNumbers = new ArrayList<Integer>();
		List<Double> learningRates = new ArrayList<Double>();
		List<Double> grossLearningRates = new ArrayList<Double>();

		NeighborhoodFunction function = NeighborhoodFunctionType.valueOf(
				prop.getProperty("kohonen.neighborhoodFunction")).getNeighborhoodFunction();

		boolean grossberg = Boolean.parseBoolean(prop.getProperty("grossberg.enable"));

		List<List<Double>> learningInputData = new ArrayList<List<Double>>();
		List<List<Double>> learningOutputData = new ArrayList<List<Double>>();
		List<Double> line;
		CsvReader reader = new CsvReader(prop.getProperty("kohonen.learningFile"));

		for (String rawEpochsNumber : rawEpochsNumbers) {
			epochsNumbers.add(Integer.parseInt(rawEpochsNumber));
		}
		for (String rawGrossEpochsNumber : grossRawEpochNumbers) {
			grossEpochsNumbers.add(Integer.parseInt(rawGrossEpochsNumber));
		}
		for (String rawLearningRate : rawLearningRates) {
			learningRates.add(Double.parseDouble(rawLearningRate));
		}
		for (String rawGrossLearningRate : grossRawLearningRates) {
			grossLearningRates.add(Double.parseDouble(rawGrossLearningRate));
		}

		while ((line = reader.readNextLine()) != null) {
			learningInputData.add(line);
			if (grossberg) {
				learningOutputData.add(reader.readNextLine());
			}
		}

		new KohonenTrainer().learn(layers.get(0).getNeurons(), layers.get(1).getNeurons(), epochsNumbers,
				learningRates, function, learningInputData);

		if (grossberg) {
			new GrossbergTrainer().learn(layers.get(0).getNeurons(), layers.get(1).getNeurons(), grossEpochsNumbers,
					grossLearningRates, function, learningOutputData);
		}
	}

	public double[][] getLearningResults() {
		List<Neuron> kohonenLayerNeurons = layers.get(1).getNeurons();
		double[][] results = new double[kohonenLayerNeurons.size()][];

		for (int i = 0; i < results.length; i++) {
			results[i] = new double[kohonenLayerNeurons.get(i).getBackConnections().size()];
		}
		for (int i = 0; i < results.length; i++) {
			for (int j = 0; j < results[i].length; j++) {
				results[i][j] = kohonenLayerNeurons.get(i).getBackConnections().get(j).getWeight();
			}
		}

		return results;
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

		if ("false".equals(prop.get("grossberg.enable"))) {
			for (Neuron outputNeuron : layers.get(layers.size() - 1).getNeurons()) {
				outputVector.add(outputNeuron.getOutputSignal());
			}
		} else {
			outputVector.add(winnerNeuron.getOutputSignal());
		}

		return outputVector;
	}

	public int getInputLayerSize() {
		return layers.get(0).getLayerSize();
	}
}
