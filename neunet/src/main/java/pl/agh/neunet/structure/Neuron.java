package pl.agh.neunet.structure;

import java.util.ArrayList;
import java.util.List;

import pl.agh.neunet.activation.ActivationFunction;

public class Neuron {
	private ActivationFunction activationFunction;

	private Bias bias = new Bias();

	private List<NetworkConnection> backConnections = new ArrayList<NetworkConnection>();

	private List<NetworkConnection> frontConnections = new ArrayList<NetworkConnection>();

	private double outputSignal;

	public Neuron(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}

	public Bias getBias() {
		return bias;
	}

	public List<NetworkConnection> getBackConnections() {
		return backConnections;
	}

	public List<NetworkConnection> getFrontConnections() {
		return frontConnections;
	}

	public double getOutputSignal() {
		return outputSignal;
	}

	public void setOutputSignal(double outputSignal) {
		this.outputSignal = outputSignal;
	}

	public void addBackConnection(NetworkConnection connection) {
		backConnections.add(connection);
	}

	public void addFrontConnection(NetworkConnection connection) {
		frontConnections.add(connection);
	}

	public void updateOutputSignal() {
		if (activationFunction != null) {
			outputSignal = activationFunction.getOutputSignal(this);
		}
	}

	public void updateWeightKohonen(double learningRate) {
		double newWeight;

		for (NetworkConnection connection : backConnections) {
			newWeight = connection.getWeight() + learningRate
					* (connection.getInputNeuron().getOutputSignal() - connection.getWeight());
			connection.setWeight(newWeight);
		}
	}

	public double calculateDistance(Neuron neuron) {
		double currentDistance = 0;
		for (int i = 0; i < getBackConnections().size(); i++) {
			currentDistance += Math.pow(getBackConnections().get(i).getWeight()
					- neuron.getBackConnections().get(i).getWeight(), 2);
		}
		return currentDistance;
	}
}
