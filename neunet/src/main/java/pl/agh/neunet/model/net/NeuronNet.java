package pl.agh.neunet.model.net;

import java.util.List;

import pl.agh.neunet.model.neuron.Neuron;

public interface NeuronNet {
	int getNumberOfLayers();

	int getNumberOfNeuronsInLayer(int layerNumber);

	List<Neuron> getNeuronsInLayer(int layerNumber);

	List<Neuron> getAllNeurons();
}
