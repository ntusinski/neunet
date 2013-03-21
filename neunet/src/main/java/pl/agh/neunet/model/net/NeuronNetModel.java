package pl.agh.neunet.model.net;

import java.util.Map;

import pl.agh.neunet.action.ActionType;
import pl.agh.neunet.viewmodel.impl.NeuronNetVM;

public class NeuronNetModel implements ActionTaker {
	public void takeAction(ActionType type, Map<String, Object> arguments) {
		if (type == null) {
			throw new IllegalArgumentException();
		}

		switch (type.getGroup()) {
		case PROPERTY_FILE:
			new PropertyFileManager().takeAction(type, arguments);
			break;
		default:
			throw new IllegalArgumentException();
		}

		NeuronNetVM.getInstance().modelChanged();
	}
}
