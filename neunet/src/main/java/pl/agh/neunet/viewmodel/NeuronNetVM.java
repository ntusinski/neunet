package pl.agh.neunet.viewmodel;

import java.util.Map;

import pl.agh.neunet.action.ActionType;

public interface NeuronNetVM {
	void registerModelObserver(ModelObserver observer);

	void requestAction(ActionType type, Map<String, Object> arguments);

	void modelChanged();
}
