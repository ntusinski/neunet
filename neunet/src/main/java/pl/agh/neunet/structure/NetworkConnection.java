package pl.agh.neunet.structure;

import java.util.List;

public class NetworkConnection {
	private Neuron inputNeuron;

	private List<Neuron> outputNeurons;

	private double weight;

	public NetworkConnection(Neuron inputNeuron, List<Neuron> outputNeurons) {
		this.inputNeuron = inputNeuron;
		this.outputNeurons = outputNeurons;
	}

	public Neuron getInputNeuron() {
		return inputNeuron;
	}

	public void setInputNeuron(Neuron inputNeuron) {
		this.inputNeuron = inputNeuron;
	}

	public List<Neuron> getOutputNeurons() {
		return outputNeurons;
	}

	public void setOutputNeurons(List<Neuron> outputNeurons) {
		this.outputNeurons = outputNeurons;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
