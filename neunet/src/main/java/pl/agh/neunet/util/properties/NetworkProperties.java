package pl.agh.neunet.util.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import pl.agh.neunet.activation.ActivationFunction;
import pl.agh.neunet.activation.ActivationFunctionType;
import pl.agh.neunet.neighborhood.NeighborhoodFunction;
import pl.agh.neunet.neighborhood.NeighborhoodFunctionType;

public class NetworkProperties {
    private static boolean kohonen;
    private static boolean grossberg;

    private static int layersNumber;

    private static double initConscience;
    private static double pmin;
    private static double customWeightsLowerValue;
    private static double customWeightsUpperValue;

    private static String kohonenLearningFilePath;

    private static NeighborhoodFunction kohonenNeighborhoodFunction;
    private static NeighborhoodFunction testNeighborhoodFunction;

    private static ActivationFunction[] activationFunctions;

    private static List<Integer> kohonenEpochsNumbers = new ArrayList<Integer>();
    private static List<Integer> grossbergEpochsNumbers = new ArrayList<Integer>();

    private static List<Double> kohonenLearningRates = new ArrayList<Double>();
    private static List<Double> grossbergLearningRates = new ArrayList<Double>();

    public static void setNetworkProperties(Properties prop) {
        kohonen = Boolean.parseBoolean(prop.getProperty("kohonen.enable"));
        grossberg = Boolean.parseBoolean(prop.getProperty("grossberg.enable"));

        layersNumber = Integer.parseInt(prop.getProperty("hiddenLayers")) + 2;

        initConscience = Double.parseDouble(prop.getProperty("conscience.initConscience"));
        pmin = Double.parseDouble(prop.getProperty("conscience.pmin"));
        customWeightsLowerValue = Double.parseDouble(prop.getProperty("customWeightsLowerValue"));
        customWeightsUpperValue = Double.parseDouble(prop.getProperty("customWeightsUpperValue"));

        kohonenLearningFilePath = prop.getProperty("kohonen.learningFile");

        kohonenNeighborhoodFunction = NeighborhoodFunctionType.valueOf(prop.getProperty("kohonen.neighborhoodFunction")).getNeighborhoodFunction();
        testNeighborhoodFunction = NeighborhoodFunctionType.valueOf(prop.getProperty("test.neighborhoodFunction")).getNeighborhoodFunction();

        parseActivationFunctions(prop);
        parseEpochsNumbersAndLearningRates(prop);
    }

    private static void parseActivationFunctions(Properties prop) {
        String[] functionsNames = prop.getProperty("activationFunctions").split(";");

        activationFunctions = new ActivationFunction[functionsNames.length + 1];
        activationFunctions[0] = null;
        for (int i = 0; i < functionsNames.length; i++) {
            activationFunctions[i + 1] = ActivationFunctionType.valueOf(functionsNames[i]).getActivationFunction();
        }
    }

    private static void parseEpochsNumbersAndLearningRates(Properties prop) {
        String[] rawKohonenEpochsNumbers = prop.getProperty("kohonen.epochsNumbers").split(";");
        String[] rawGrossbergEpochNumbers = prop.getProperty("grossberg.epochsNumbers").split(";");

        String[] rawKohonenLearningRates = prop.getProperty("kohonen.learningRates").split(";");
        String[] rawGrossbergLearningRates = prop.getProperty("grossberg.learningRates").split(";");

        for (String rawEpochsNumber : rawKohonenEpochsNumbers) {
            kohonenEpochsNumbers.add(Integer.parseInt(rawEpochsNumber));
        }
        for (String rawGrossEpochsNumber : rawGrossbergEpochNumbers) {
            grossbergEpochsNumbers.add(Integer.parseInt(rawGrossEpochsNumber));
        }
        for (String rawLearningRate : rawKohonenLearningRates) {
            kohonenLearningRates.add(Double.parseDouble(rawLearningRate));
        }
        for (String rawGrossLearningRate : rawGrossbergLearningRates) {
            grossbergLearningRates.add(Double.parseDouble(rawGrossLearningRate));
        }
    }

    public static boolean isKohonen() {
        return kohonen;
    }

    public static boolean isGrossberg() {
        return grossberg;
    }

    public static int getLayersNumber() {
        return layersNumber;
    }

    public static double getInitConscience() {
        return initConscience;
    }

    public static double getPmin() {
        return pmin;
    }

    public static double getCustomWeightsLowerValue() {
        return customWeightsLowerValue;
    }

    public static double getCustomWeightsUpperValue() {
        return customWeightsUpperValue;
    }

    public static String getKohonenLearningFilePath() {
        return kohonenLearningFilePath;
    }

    public static NeighborhoodFunction getKohonenNeighborhoodFunction() {
        return kohonenNeighborhoodFunction;
    }

    public static NeighborhoodFunction getTestNeighborhoodFunction() {
        return testNeighborhoodFunction;
    }

    public static ActivationFunction[] getActivationFunctions() {
        return activationFunctions;
    }

    public static List<Integer> getKohonenEpochsNumbers() {
        return kohonenEpochsNumbers;
    }

    public static List<Integer> getGrossbergEpochsNumbers() {
        return grossbergEpochsNumbers;
    }

    public static List<Double> getKohonenLearningRates() {
        return kohonenLearningRates;
    }

    public static List<Double> getGrossbergLearningRates() {
        return grossbergLearningRates;
    }
}
