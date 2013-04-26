package pl.agh.neunet.structure;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
	private List<NetworkConnection> backConnections = new ArrayList<NetworkConnection>();

	private NetworkConnection frontConnection;

	public List<NetworkConnection> getBackConnections() {
		return backConnections;
	}

	public NetworkConnection getFrontConnection() {
		return frontConnection;
	}

	public void setFrontConnection(NetworkConnection frontConnection) {
		this.frontConnection = frontConnection;
	}

	public void addBackConnection(NetworkConnection connection) {
		backConnections.add(connection);
	}
}
