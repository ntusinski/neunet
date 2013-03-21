package pl.agh.neunet.viewmodel.impl;

import java.util.ArrayList;
import java.util.List;

import pl.agh.neunet.viewmodel.ModelObserver;

public class NeuronNetVM implements pl.agh.neunet.viewmodel.NeuronNetVM {
	private final List<ModelObserver> observers = new ArrayList<ModelObserver>();

	private NeuronNetVM() {
	}

	public NeuronNetVM getInstance() {
		return NeuronNetVMHolder.INSTANCE;
	}

	public void registerModelObserver(ModelObserver observer) {
		observers.add(observer);
	}

	public void modelChanged() {
		for (ModelObserver observer : observers) {
			observer.refreshView();
		}
	}

	private static class NeuronNetVMHolder {
		public static final NeuronNetVM INSTANCE = new NeuronNetVM();
	}
}
