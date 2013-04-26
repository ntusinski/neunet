package pl.agh.neunet.structure;


public class NetworkConnection {
	private Neuron inputNeuron;

	private Neuron outputNeuron;

	private double weight;

	public NetworkConnection(Neuron inputNeuron, Neuron outputNeuron) {
		this.inputNeuron = inputNeuron;
		this.outputNeuron = outputNeuron;
	}

	public Neuron getInputNeuron() {
		return inputNeuron;
	}

	public void setInputNeuron(Neuron inputNeuron) {
		this.inputNeuron = inputNeuron;
	}

	public Neuron getOutputNeuron() {
		return outputNeuron;
	}

	public void setOutputNeuron(Neuron outputNeuron) {
		this.outputNeuron = outputNeuron;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
