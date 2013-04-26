package pl.agh.neunet.structure;

public class NetworkConnection {
	private Neuron fromNeuron;

	private Neuron toNeuron;

	private double weight;

	public Neuron getFromNeuron() {
		return fromNeuron;
	}

	public void setFromNeuron(Neuron fromNeuron) {
		this.fromNeuron = fromNeuron;
	}

	public Neuron getToNeuron() {
		return toNeuron;
	}

	public void setToNeuron(Neuron toNeuron) {
		this.toNeuron = toNeuron;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
