package pl.agh.neunet.trainer;

import java.util.List;

import pl.agh.neunet.neighborhood.NeighborhoodFunction;
import pl.agh.neunet.structure.NetworkConnection;
import pl.agh.neunet.structure.Neuron;

public class GrossbergTrainer {
	public void learn(List<Neuron> inputNeurons, List<Neuron> kohonenNeurons, List<Integer> epochsLengths,
			List<Double> learningRates, NeighborhoodFunction function, List<List<Double>> learningInputData,
			List<List<Double>> learningOutputData) {
		int caseNumber = 0;
		int epochsNumber = epochsLengths.size();
		function.initialize(kohonenNeurons);
		for (int epoch = 0; epoch < epochsNumber; epoch++) {
			int epochLength = epochsLengths.get(epoch);
			double learningRate = learningRates.get(epoch);
			for (int epochItNumber = 0; epochItNumber < epochLength; epochItNumber++) {
				setInputNeuronWeightsAsInTrainingData(learningInputData.get(caseNumber), inputNeurons);
				setGrossbergWeights(learningOutputData.get(caseNumber), kohonenNeurons, function, learningRate);
				caseNumber++;
				caseNumber %= learningInputData.size();
			}
		}
	}

	private void setGrossbergWeights(List<Double> currentCaseData, List<Neuron> kohonenNeurons,
			NeighborhoodFunction function, double learningRate) {
		Neuron winnerNeuron = function.getWinnerNeuron(kohonenNeurons);
		for (int i = 0; i < winnerNeuron.getFrontConnections().size(); i++) {
			NetworkConnection conn = winnerNeuron.getFrontConnections().get(i);
			conn.setWeight(conn.getWeight() + learningRate
					* (currentCaseData.get(i) - conn.getOutputNeuron().getOutputSignal()));
		}
	}

	private void setInputNeuronWeightsAsInTrainingData(List<Double> currentCaseData, List<Neuron> inputNeurons) {
		for (int i = 0; i < inputNeurons.size(); i++) {
			inputNeurons.get(i).setOutputSignal(currentCaseData.get(i));
		}
	}
}
