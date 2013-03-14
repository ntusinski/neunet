package pl.agh.neunet.neuron;

import java.awt.Dimension;

import org.junit.Assert;
import org.junit.Test;

public class NeuronTest {
	@Test
	public void testLocation() {
		Neuron n = new SimpleNeuron();
		Dimension expected = new Dimension(150, 250);
		Dimension actual;

		n.setLocation(expected);
		actual = n.getLocation();

		Assert.assertEquals(expected, actual);
	}
}
