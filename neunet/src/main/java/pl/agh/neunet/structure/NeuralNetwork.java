package pl.agh.neunet.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import pl.agh.neunet.trainer.GrossbergTrainer;
import pl.agh.neunet.trainer.KohonenTrainer;
import pl.agh.neunet.util.csv.CsvReader;
import pl.agh.neunet.util.csv.CsvWriter;
import pl.agh.neunet.util.properties.NetworkProperties;
import pl.agh.neunet.util.random.RandomDouble;

public class NeuralNetwork {
    private List<NetworkLayer> layers = new ArrayList<NetworkLayer>();

    public void createStructure() {
        createNeurons();
        createConnections();
        createWeights();
    }

    private void createNeurons() {
        List<Neuron> neurons;
        Neuron lastNeuron;
        Neuron currNeuron = null;

        List<List<Neuron>> neuronArray = new ArrayList<List<Neuron>>();
        int xSize = (int) Math.sqrt(NetworkProperties.getLayersSizes().get(1));
        int ySize = 0;
        int currIndex = 0;

        for (int i = 0; i < NetworkProperties.getLayersNumber(); i++) {
            neurons = new ArrayList<Neuron>();
            for (int j = 0; j < NetworkProperties.getLayersSizes().get(i); j++) {
                lastNeuron = currNeuron;
                currNeuron = new Neuron(NetworkProperties.getActivationFunctions()[i]);
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
            layers.add(new NetworkLayer(NetworkProperties.getLayersSizes().get(i), neurons));
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

        if (NetworkProperties.isCustomWeights()) {
            createWeightsFromFile();
        } else {
            createRandomWeights();
        }
    }

    private void createWeightsFromFile() {
        CsvReader reader = new CsvReader(NetworkProperties.getInputWeightsFilePath());
        Iterator<Double> lineIterator;

        for (int i = 1; i < NetworkProperties.getLayersNumber(); i++) {
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
        RandomDouble randomDouble = new RandomDouble(NetworkProperties.getCustomWeightsLowerValue(), NetworkProperties.getCustomWeightsUpperValue());

        for (int i = 1; i < NetworkProperties.getLayersNumber(); i++) {
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
        CsvWriter writer = output ? new CsvWriter(NetworkProperties.getOutputWeightsFilePath()) : new CsvWriter(NetworkProperties.getInputWeightsFilePath());
        List<Double> nextLine;

        for (int i = 1; i < NetworkProperties.getLayersNumber(); i++) {
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
        List<List<Double>> learningInputData = new ArrayList<List<Double>>();
        List<List<Double>> learningOutputData = new ArrayList<List<Double>>();
        List<Double> line;

        if (NetworkProperties.isKohonen()) {
            CsvReader reader = new CsvReader(NetworkProperties.getKohonenLearningFilePath());
            while ((line = reader.readNextLine()) != null) {
                learningInputData.add(line);
                if (NetworkProperties.isGrossberg()) {
                    learningOutputData.add(reader.readNextLine());
                }
            }
        }

        if (NetworkProperties.isKohonen()) {
            new KohonenTrainer().learn(layers.get(0).getNeurons(), layers.get(1).getNeurons(), NetworkProperties.getKohonenEpochsNumbers(),
                    NetworkProperties.getKohonenLearningRates(), NetworkProperties.getKohonenNeighborhoodFunction(), learningInputData);
            double[][] results = getKohonenLearningResults();

            System.out.println("Kohonen neurons weights:");
            for (int i = 0; i < results.length; i++) {
                System.out.print("< ");
                for (int j = 0; j < results[i].length; j++) {
                    System.out.print(results[i][j] + ", ");
                }
                System.out.println(">");
            }
        }

        if (NetworkProperties.isGrossberg()) {
            new GrossbergTrainer().learn(layers.get(0).getNeurons(), layers.get(1).getNeurons(), NetworkProperties.getGrossbergEpochsNumbers(),
                    NetworkProperties.getGrossbergLearningRates(), NetworkProperties.getKohonenNeighborhoodFunction(), learningInputData, learningOutputData);
        }
    }

    public double[][] getKohonenLearningResults() {
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

    public void testNetwork() {
        Scanner scan = new Scanner(System.in);
        List<Double> inputData = new ArrayList<Double>();

        System.out.println("Enter input vector in size of " + layers.get(0).getNeurons().size());
        for (int i = 0; i < layers.get(0).getNeurons().size(); i++) {
            inputData.add(scan.nextDouble());
        }

        for (int i = 0; i < layers.get(0).getNeurons().size(); i++) {
            Neuron inputLayerNeuron = layers.get(0).getNeurons().get(i);
            inputLayerNeuron.setOutputSignal(inputData.get(i));
        }

        List<Double> outputData = NetworkProperties.isGrossberg() ? testNetworkGrossberg(inputData) : testNetworkWithDefaultSettings();
        System.out.println("Result is: " + outputData);
        System.out.println();
    }

    private List<Double> testNetworkWithDefaultSettings() {
        for (int i = 1; i < layers.size(); i++) {
            for (Neuron neuron : layers.get(i).getNeurons()) {
                neuron.updateOutputSignal();
            }
        }

        List<Double> outputData = new ArrayList<Double>();
        for (Neuron neuron : layers.get(layers.size() - 1).getNeurons()) {
            neuron.updateOutputSignal();
            outputData.add(neuron.getOutputSignal());
        }
        return outputData;
    }

    public List<Double> testNetworkGrossberg(List<Double> inputData) {
        Neuron winnerNeuron = NetworkProperties.getTestNeighborhoodFunction().getWinnerNeuron(layers.get(1).getNeurons());

        System.out.print("Back connections: ");
        for (NetworkConnection conn : winnerNeuron.getBackConnections()) {
            System.out.print(conn.getWeight() + " ");
        }
        System.out.println();

        for (Neuron neuron : layers.get(1).getNeurons()) {
            if (winnerNeuron == neuron) {
                neuron.updateOutputSignal();
            } else {
                neuron.setOutputSignal(0.0);
            }
        }

        List<Double> outputData = new ArrayList<Double>();
        for (Neuron neuron : layers.get(2).getNeurons()) {
            neuron.getBias().setValue(0.0);
            neuron.updateOutputSignal();
            outputData.add(neuron.getOutputSignal());
        }

        return outputData;
    }
}
