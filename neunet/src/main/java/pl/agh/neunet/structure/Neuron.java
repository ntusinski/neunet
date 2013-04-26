package pl.agh.neunet.structure;

import java.util.List;

import pl.agh.neunet.activation.ActivationFunction;

public class Neuron {
	private List<NetworkConnection> backConnections;

	private List<NetworkConnection> frontConnections;

	private ActivationFunction activationFunction;

	public List<NetworkConnection> getBackConnections() {
		return backConnections;
	}

	public void setBackConnections(List<NetworkConnection> backConnections) {
		this.backConnections = backConnections;
	}

	public List<NetworkConnection> getFrontConnections() {
		return frontConnections;
	}

	public void setFrontConnections(List<NetworkConnection> frontConnections) {
		this.frontConnections = frontConnections;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

	public void setActivationFunction(ActivationFunction activationFunction) {
		this.activationFunction = activationFunction;
	}
}
