package pl.agh.neunet.activation;

import pl.agh.neunet.structure.NetworkConnection;

public class Impulse {
	private NetworkConnection connection;

	private double inputSignal;

	public NetworkConnection getConnection() {
		return connection;
	}

	public void setConnection(NetworkConnection connection) {
		this.connection = connection;
	}

	public double getInputSignal() {
		return inputSignal;
	}

	public void setInputSignal(double inputSignal) {
		this.inputSignal = inputSignal;
	}
}
