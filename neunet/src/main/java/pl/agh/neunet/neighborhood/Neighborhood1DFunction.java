package pl.agh.neunet.neighborhood;

import java.util.List;

import pl.agh.neunet.structure.NetworkConnection;
import pl.agh.neunet.structure.Neuron;

public class Neighborhood1DFunction implements NeighborhoodFunction {
	public void initialize(List<Neuron> neurons) {
	}

	public Neuron getWinnerNeuron(List<Neuron> neurons) {
		double currentDistance;
		double maxDistance = -1;
		Neuron winnerNeuron = null;

		for (Neuron neuron : neurons) {
			currentDistance = 0;
			for (NetworkConnection connection : neuron.getBackConnections()) {
				currentDistance += Math.pow(connection.getWeight() - connection.getInputNeuron().getOutputSignal(), 2);
			}
			if (maxDistance == -1 || currentDistance < maxDistance) {
				maxDistance = currentDistance;
				winnerNeuron = neuron;
			}
		}
		return winnerNeuron;
	}

	public List<Neuron> getNeighboringNeurons(List<Neuron> neurons) {
		return getWinnerNeuron(neurons).getNeighbors1D();
	}
}
