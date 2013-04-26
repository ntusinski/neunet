package pl.agh.neunet.random;

import java.util.Random;

public class RandomDouble {
	private double min;
	private double max;

	private Random random = new Random();

	public RandomDouble(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public double nextDouble() {
		return random.nextDouble() * (max - min) + min;
	}
}
