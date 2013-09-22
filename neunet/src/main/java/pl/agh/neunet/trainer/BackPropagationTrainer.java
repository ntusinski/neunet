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
                setInputNeuronSignalsAsInTrainingData(learningInputData.get(caseNumber), layers.get(0).getNeurons());
                calculateOutputSignalsForAllNeurons(layers);
                setOutputNeuronSignalsAsInTrainingData(learningOutputData.get(caseNumber), layers.get(layers.size() - 1).getNeurons());
                calculateInputSignalsForAllNeurons(layers);
            }
        }
    }

    private void setInputNeuronSignalsAsInTrainingData(List<Double> currentCaseData, List<Neuron> inputNeurons) {
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

    private void setOutputNeuronSignalsAsInTrainingData(List<Double> currentCaseData, List<Neuron> outputNeurons) {
        for (int i = 0; i < outputNeurons.size(); i++) {
            outputNeurons.get(i).setInputSignal(currentCaseData.get(i));
        }
    }

    private void calculateInputSignalsForAllNeurons(List<NetworkLayer> layers) {
        for (int i = layers.size() - 2; i >= 0; i--) {
            for (Neuron neuron : layers.get(i).getNeurons()) {
                neuron.updateInputSignal();
            }
        }
    }
}
