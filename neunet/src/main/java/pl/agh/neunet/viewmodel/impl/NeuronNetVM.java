package pl.agh.neunet.viewmodel.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.agh.neunet.model.net.NeuronNetModel;
import pl.agh.neunet.shared.action.ActionType;
import pl.agh.neunet.viewmodel.ModelObserver;

public class NeuronNetVM implements pl.agh.neunet.viewmodel.NeuronNetVM {
	private final List<ModelObserver> observers = new ArrayList<ModelObserver>();

	private NeuronNetVM() {
	}

	public void registerModelObserver(ModelObserver observer) {
		observers.add(observer);
	}

	public void requestAction(ActionType type, Map<String, Object> arguments) {
		new NeuronNetModel().takeAction(type, arguments);
	}

	public void modelChanged() {
		for (ModelObserver observer : observers) {
			observer.refreshView();
		}
	}

	public static NeuronNetVM getInstance() {
		return NeuronNetVMHolder.INSTANCE;
	}

	private static class NeuronNetVMHolder {
		public static final NeuronNetVM INSTANCE = new NeuronNetVM();
	}
}
