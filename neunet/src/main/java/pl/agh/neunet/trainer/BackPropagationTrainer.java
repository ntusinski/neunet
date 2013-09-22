package pl.agh.neunet.trainer;

import java.util.List;
import java.util.Random;

import pl.agh.neunet.structure.NetworkLayer;
import pl.agh.neunet.structure.Neuron;

public class BackPropagationTrainer {
    public void learn(List<NetworkLayer> layers, List<List<Double>> learningInputData, List<List<Double>> learningOutputData, List<Integer> epochsLengths,
            List<Double> learningRates) {
        Random random = new Random();
        int epochsNumber = epochsLengths.size();

        for (int epoch = 0; epoch < epochsNumber; epoch++) {
            int epochLength = epochsLengths.get(epoch);
            for (int epochItNumber = 0; epochItNumber < epochLength; epochItNumber++) {
                int caseNumber = Math.abs(random.nextInt()) % learningInputData.size();
                setInputNeuronWeightsAsInTrainingData(learningInputData.get(caseNumber), layers.get(0).getNeurons());
                calculateOutputSignalsForAllNeurons(layers);
            }
        }
    }

    private void setInputNeuronWeightsAsInTrainingData(List<Double> currentCaseData, List<Neuron> inputNeurons) {
        for (int i = 0; i < inputNeurons.size(); i++) {
            inputNeurons.get(i).setOutputSignal(currentCaseData.get(i));
        }
    }

    private void calculateOutputSignalsForAllNeurons(List<NetworkLayer> layers) {
        for (int i = 1; i < layers.size(); i++) {
            for (Neuron neuron : layers.get(i).getNeurons()) {
                neuron.updateOutputSignal();
            }
        }
    }
}
