package pl.agh.neunet.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.agh.neunet.structure.NetworkConnection;
import pl.agh.neunet.structure.NetworkLayer;
import pl.agh.neunet.structure.Neuron;
import pl.agh.neunet.util.properties.NetworkProperties;

public class NeuralNetworkTester {
    private List<NetworkLayer> layers = new ArrayList<NetworkLayer>();

    public NeuralNetworkTester(List<NetworkLayer> layers) {
        this.layers = layers;
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
