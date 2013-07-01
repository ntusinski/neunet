package pl.agh.neunet.kohonen;

import java.util.List;

import pl.agh.neunet.structure.Neuron;

public class KohonenLearner {
	private static int caseNumber = 0;
	private static List<Neuron> inputNeurons;
	private static List<List<Double>> learningData;

	public static void learn(List<Neuron> inputNeurons, List<Neuron> kohonenNeurons, List<Integer> epochsNumbers,
			List<Double> learningRates, NeighborhoodFunction function, List<List<Double>> learningData) {
		int epochsNumber;
		double learningRate;
		Neuron winnerNeuron;
		List<Neuron> neighboringNeurons;

		KohonenLearner.inputNeurons = inputNeurons;
		KohonenLearner.learningData = learningData;

		for (int i = 0; i < epochsNumbers.size(); i++) {
			epochsNumber = epochsNumbers.get(i);
			learningRate = learningRates.get(i);
			for (int j = 0; j < epochsNumber; j++) {
				setNextTestCase();
				winnerNeuron = function.getWinnerNeuron(kohonenNeurons);
				neighboringNeurons = function.getNeighboringNeurons(kohonenNeurons);
				winnerNeuron.updateWeightKohonen(learningRate);
				for (Neuron neuron : neighboringNeurons) {
					neuron.updateWeightKohonen(Math.exp(-Math.pow(neuron.calculateDistance(winnerNeuron), 2)));
				}
			}
		}
	}

	private static void setNextTestCase() {
		List<Double> currentCase = learningData.get(caseNumber);

		for (int i = 0; i < inputNeurons.size(); i++) {
			inputNeurons.get(i).setOutputSignal(currentCase.get(i));
		}

		caseNumber++;
		caseNumber %= learningData.size();
	}
}
