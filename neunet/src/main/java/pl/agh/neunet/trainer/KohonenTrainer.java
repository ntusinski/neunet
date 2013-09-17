package pl.agh.neunet.trainer;

import java.util.List;

import pl.agh.neunet.neighborhood.NeighborhoodFunction;
import pl.agh.neunet.structure.Neuron;

public class KohonenTrainer {
	public void learn(List<Neuron> inputNeurons, List<Neuron> kohonenNeurons, List<Integer> epochsLengths,
			List<Double> learningRates, NeighborhoodFunction function, List<List<Double>> learningData) {
		int caseNumber = 0;
		int epochsNumber = epochsLengths.size();
		for (int epoch = 0; epoch < epochsNumber; epoch++) {
			int epochLength = epochsLengths.get(epoch);
			double learningRate = learningRates.get(epoch);
			for (int epochItNumber = 0; epochItNumber < epochLength; epochItNumber++) {
				setInputNeuronWeightsAsInTrainingData(learningData.get(caseNumber), inputNeurons);
				setKohonenWeights(learningData.get(caseNumber), kohonenNeurons, function, learningRate);
				caseNumber++;
				caseNumber %= learningData.size();
			}
		}
	}

	private void setKohonenWeights(List<Double> currentCaseData, List<Neuron> kohonenNeurons,
			NeighborhoodFunction function, double learningRate) {
		Neuron winnerNeuron = function.getWinnerNeuron(kohonenNeurons);
		List<Neuron> neighboringNeurons = function.getNeighboringNeurons(kohonenNeurons);
		winnerNeuron.updateWeightKohonen(learningRate);
		for (Neuron neuron : neighboringNeurons) {
			neuron.updateWeightKohonen(Math.exp(-Math.pow(neuron.calculateDistance(winnerNeuron), 2)));
		}
	}

	private void setInputNeuronWeightsAsInTrainingData(List<Double> currentCaseData, List<Neuron> inputNeurons) {
		for (int i = 0; i < inputNeurons.size(); i++) {
			inputNeurons.get(i).setOutputSignal(currentCaseData.get(i));
		}
	}
}
