package pl.agh.neunet.structure;

import java.util.List;

public class NetworkLayer {
	private int layerSize;

	private List<Neuron> neurons;

	public int getLayerSize() {
		return layerSize;
	}

	public void setLayerSize(int layerSize) {
		this.layerSize = layerSize;
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}
}
