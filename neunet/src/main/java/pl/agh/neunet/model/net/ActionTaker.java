package pl.agh.neunet.model.net;

import java.util.Map;

import pl.agh.neunet.action.ActionType;

public interface ActionTaker {
	void takeAction(ActionType type, Map<String, Object> arguments);
}