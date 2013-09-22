package pl.agh.neunet.activation;

import pl.agh.neunet.structure.Neuron;

public interface ActivationFunction {
	double getInputSignal(Neuron neuron);
	double getOutputSignal(Neuron neuron);
}
