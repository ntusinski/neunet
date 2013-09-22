package pl.agh.neunet.structure;

import java.util.ArrayList;
import java.util.List;

import pl.agh.neunet.activation.ActivationFunction;

public class Neuron {
    private ActivationFunction activationFunction;

    private Bias bias = new Bias();

    private List<NetworkConnection> backConnections = new ArrayList<NetworkConnection>();
    private List<NetworkConnection> frontConnections = new ArrayList<NetworkConnection>();

    private double outputSignal;
    private double errorSignal;

    private List<Neuron> neighbors1D = new ArrayList<Neuron>();
    private List<Neuron> neighbors2D = new ArrayList<Neuron>();

    public Neuron(ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public Bias getBias() {
        return bias;
    }

    public List<NetworkConnection> getBackConnections() {
        return backConnections;
    }

    public List<NetworkConnection> getFrontConnections() {
        return frontConnections;
    }

    public double getOutputSignal() {
        return outputSignal;
    }

    public void setOutputSignal(double outputSignal) {
        this.outputSignal = outputSignal;
    }

    public double getErrorSignal() {
        return errorSignal;
    }

    public void setErrorSignal(double inputSignal) {
        this.errorSignal = inputSignal;
    }

    public void addBackConnection(NetworkConnection connection) {
        backConnections.add(connection);
    }

    public void addFrontConnection(NetworkConnection connection) {
        frontConnections.add(connection);
    }

    public void updateOutputSignal() {
        if (activationFunction != null) {
            outputSignal = activationFunction.getOutputSignal(this);
        }
    }

    public void updateErrorSignal() {
        if (activationFunction != null) {
            errorSignal = activationFunction.getErrorSignal(this);
        }
    }

    public void updateWeightKohonen(double learningRate) {
        double newWeight;

        for (NetworkConnection connection : backConnections) {
            newWeight = connection.getWeight() + learningRate * (connection.getInputNeuron().getOutputSignal() - connection.getWeight());
            connection.setWeight(newWeight);
        }
    }

    public double calculateDistance(Neuron neuron) {
        double currentDistance = 0;
        for (int i = 0; i < getBackConnections().size(); i++) {
            currentDistance += Math.pow(getBackConnections().get(i).getWeight() - neuron.getBackConnections().get(i).getWeight(), 2);
        }
        return currentDistance;
    }

    public List<Neuron> getNeighbors1D() {
        return neighbors1D;
    }

    public void addNeighbor1D(Neuron neighbor) {
        neighbors1D.add(neighbor);
    }

    public List<Neuron> getNeighbors2D() {
        return neighbors2D;
    }

    public void addNeighbor2D(Neuron neighbor) {
        neighbors2D.add(neighbor);
    }
}
