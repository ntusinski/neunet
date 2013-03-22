package pl.agh.neunet.viewmodel.config;

import pl.agh.neunet.viewmodel.neuron.Neuron;

public class NeuronNetSetting {
	private final int layers;

	private final int[] perLayerNumber;

	private Neuron[][] neurons;

	public NeuronNetSetting(int layersNumber, int[] neuronsInLayerNumber,
			Neuron[][] neuronsInLayer) {
		this.layers = layersNumber;
		this.perLayerNumber = neuronsInLayerNumber;
		this.neurons = neuronsInLayer;
	}

	public int getLayersNumber() {
		return layers;
	}

	public int getNeuronsNumber(int layer) {
		return perLayerNumber[layer];
	}

	public Neuron[] getNeurons(int layer) {
		return neurons[layer];
	}
}
