package pl.agh.neunet.activation;

import pl.agh.neunet.structure.NetworkConnection;
import pl.agh.neunet.structure.Neuron;

public class StepActivationFunction implements ActivationFunction {
    public double getErrorSignal(Neuron neuron) {
        double value = 0.0;
        for (NetworkConnection connection : neuron.getFrontConnections()) {
            value += connection.getOutputNeuron().getErrorSignal() * connection.getWeight();
        }
        return value;
    }

    public double getOutputSignal(Neuron neuron) {
        double value = 0.0;
        for (NetworkConnection connection : neuron.getBackConnections()) {
            value += connection.getInputNeuron().getOutputSignal() * connection.getWeight();
        }
        value -= neuron.getBias().getValue();
        value = value > 0 ? 1 : 0;
        return value;
    }
}
