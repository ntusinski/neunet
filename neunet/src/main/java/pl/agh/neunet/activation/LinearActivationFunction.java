package pl.agh.neunet.activation;

import java.util.List;

public class LinearActivationFunction implements ActivationFunction {
	public double getOutputSignal(List<Impulse> impulses, double bias) {
		double value = 0.0;
		for (Impulse impulse : impulses) {
			value += impulse.getInputSignal()
					* impulse.getConnection().getWeight();
		}
		value += bias;
		return value;
	}
}
