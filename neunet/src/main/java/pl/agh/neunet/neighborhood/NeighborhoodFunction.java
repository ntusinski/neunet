package pl.agh.neunet.neighborhood;

import java.util.List;

import pl.agh.neunet.structure.Neuron;

public interface NeighborhoodFunction {
	void initialize(List<Neuron> neurons);

	Neuron getWinnerNeuron(List<Neuron> neurons);

	List<Neuron> getNeighboringNeurons(List<Neuron> neurons);
}