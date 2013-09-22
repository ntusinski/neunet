package pl.agh.neunet.trainer;

import java.util.ArrayList;
import java.util.List;

import pl.agh.neunet.structure.NetworkLayer;
import pl.agh.neunet.structure.Neuron;
import pl.agh.neunet.util.csv.CsvReader;
import pl.agh.neunet.util.properties.NetworkProperties;

public class NeuralNetworkTrainer {
    private List<NetworkLayer> layers = new ArrayList<NetworkLayer>();

    public NeuralNetworkTrainer(List<NetworkLayer> layers) {
        this.layers = layers;
    }

    public void learn() {
        List<List<Double>> learningInputData = new ArrayList<List<Double>>();
        List<List<Double>> learningOutputData = new ArrayList<List<Double>>();
        List<Double> line;

        if (NetworkProperties.isKohonen()) {
            CsvReader reader = new CsvReader(NetworkProperties.getKohonenLearningFilePath());
            while ((line = reader.readNextLine()) != null) {
                learningInputData.add(line);
                if (NetworkProperties.isGrossberg()) {
                    learningOutputData.add(reader.readNextLine());
                }
            }
        }

        if (NetworkProperties.isKohonen()) {
            new KohonenTrainer().learn(layers.get(0).getNeurons(), layers.get(1).getNeurons(), NetworkProperties.getKohonenEpochsNumbers(),
                    NetworkProperties.getKohonenLearningRates(), NetworkProperties.getKohonenNeighborhoodFunction(), learningInputData);
            double[][] results = getKohonenLearningResults();

            System.out.println("Kohonen neurons weights:");
            for (int i = 0; i < results.length; i++) {
                System.out.print("< ");
                for (int j = 0; j < results[i].length; j++) {
                    System.out.print(results[i][j] + ", ");
                }
                System.out.println(">");
            }
        }

        if (NetworkProperties.isGrossberg()) {
            new GrossbergTrainer().learn(layers.get(0).getNeurons(), layers.get(1).getNeurons(), NetworkProperties.getGrossbergEpochsNumbers(),
                    NetworkProperties.getGrossbergLearningRates(), NetworkProperties.getKohonenNeighborhoodFunction(), learningInputData, learningOutputData);
        }
    }

    public double[][] getKohonenLearningResults() {
        List<Neuron> kohonenLayerNeurons = layers.get(1).getNeurons();
        double[][] results = new double[kohonenLayerNeurons.size()][];

        for (int i = 0; i < results.length; i++) {
            results[i] = new double[kohonenLayerNeurons.get(i).getBackConnections().size()];
        }
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < results[i].length; j++) {
                results[i][j] = kohonenLayerNeurons.get(i).getBackConnections().get(j).getWeight();
            }
        }

        return results;
    }
}
