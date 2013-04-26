package pl.agh.neunet.activation;

import java.util.List;

public interface ActivationFunction {
	double getOutputSignal(List<Impulse> impulses, double bias);
}
