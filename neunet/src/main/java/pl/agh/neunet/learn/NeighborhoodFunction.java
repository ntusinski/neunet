package pl.agh.neunet.learn;

import java.util.List;

import pl.agh.neunet.structure.Neuron;

public interface NeighborhoodFunction {
	Neuron getWinnerNeuron(List<Neuron> neurons);

	List<Neuron> getNeighboringNeurons(List<Neuron> neurons);
}
