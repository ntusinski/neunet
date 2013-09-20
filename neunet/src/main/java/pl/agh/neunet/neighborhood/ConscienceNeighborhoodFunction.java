package pl.agh.neunet.neighborhood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.agh.neunet.structure.Neuron;
import pl.agh.neunet.util.properties.NetworkProperties;

public class ConscienceNeighborhoodFunction extends ContestNeighborhoodFunction {
	private Map<Neuron, Double> consciences = new HashMap<Neuron, Double>();

	@Override
	public void initialize(List<Neuron> neurons) {
		for (Neuron neuron : neurons) {
			consciences.put(neuron, NetworkProperties.getInitConscience());
		}
	}

	@Override
	public Neuron getWinnerNeuron(List<Neuron> neurons) {
		List<Neuron> activeNeurons = new ArrayList<Neuron>();
		for (Neuron neuron : neurons) {
			if (consciences.get(neuron) > NetworkProperties.getPmin()) {
				activeNeurons.add(neuron);
			}
		}
		Neuron winner = super.getWinnerNeuron(activeNeurons);
		for (Neuron neuron : neurons) {
			Double conscience = consciences.get(neuron);
			conscience = winner == neuron ? conscience - NetworkProperties.getPmin()
					: (conscience + 1.0 / ((double) neurons.size())) % 1.0;
			consciences.put(neuron, conscience);
		}
		return winner;
	}
}
