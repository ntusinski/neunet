package pl.agh.neunet.activation;

import pl.agh.neunet.structure.Neuron;

public interface ActivationFunction {
    double getErrorSignal(Neuron neuron);

    double getOutputSignal(Neuron neuron);
}
