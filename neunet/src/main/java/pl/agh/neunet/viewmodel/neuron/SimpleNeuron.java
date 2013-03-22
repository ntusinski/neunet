package pl.agh.neunet.viewmodel.neuron;

import java.awt.Dimension;

public class SimpleNeuron implements Neuron {
	private Dimension location;

	public Dimension getLocation() {
		return location;
	}

	public void setLocation(Dimension location) {
		this.location = location;
	}
}
