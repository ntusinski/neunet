package pl.agh.neunet.learn;

import java.util.List;

import pl.agh.neunet.structure.NetworkConnection;
import pl.agh.neunet.structure.Neuron;

public class Learner {
	private static int caseNumber = 0;
	private static List<Neuron> inputNeurons;
	private static List<List<Double>> learningInputData;
	private static List<List<Double>> learningOutputData;

	public static Neuron learn(List<Neuron> inputNeurons, List<Neuron> kohonenNeurons, List<Integer> epochsNumbers,
			List<Double> learningRates, NeighborhoodFunction function, List<List<Double>> learningInputData,
			List<List<Double>> learningOutputData, boolean grossberg, List<Double> grossLearningRates) {
		int epochsNumber;
		double learningRate;
		double grossLearningRate;

		Neuron winnerNeuron = null;
		List<Neuron> neighboringNeurons;

		Learner.inputNeurons = inputNeurons;
		Learner.learningInputData = learningInputData;
		Learner.learningOutputData = learningOutputData;

		for (int i = 0; i < epochsNumbers.size(); i++) {
			epochsNumber = epochsNumbers.get(i);
			learningRate = learningRates.get(i);
			grossLearningRate = learningRates.get(i);
			for (int j = 0; j < epochsNumber; j++) {
				setNextTestCase();
				winnerNeuron = function.getWinnerNeuron(kohonenNeurons);
				neighboringNeurons = function.getNeighboringNeurons(kohonenNeurons);
				winnerNeuron.updateWeightKohonen(learningRate);
				for (Neuron neuron : neighboringNeurons) {
					neuron.updateWeightKohonen(Math.exp(-Math.pow(neuron.calculateDistance(winnerNeuron), 2)));
				}
				if (grossberg) {
					setGrossbergWeights(winnerNeuron, grossLearningRate);
				}
				caseNumber++;
				caseNumber %= learningInputData.size();
			}
		}

		return winnerNeuron;
	}

	private static void setGrossbergWeights(Neuron winnerNeuron, double grossLearningRate) {
		List<Double> currentCase = learningOutputData.get(caseNumber);
		NetworkConnection conn;

		for (int i = 0; i < winnerNeuron.getFrontConnections().size(); i++) {
			conn = winnerNeuron.getFrontConnections().get(i);
			conn.setWeight(conn.getWeight() + grossLearningRate
					* (currentCase.get(i) - conn.getOutputNeuron().getOutputSignal()));
		}
	}

	private static void setNextTestCase() {
		List<Double> currentCase = learningInputData.get(caseNumber);

		for (int i = 0; i < inputNeurons.size(); i++) {
			inputNeurons.get(i).setOutputSignal(currentCase.get(i));
		}
	}
}
