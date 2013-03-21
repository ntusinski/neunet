package pl.agh.neunet.model.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import pl.agh.neunet.action.ActionGroup;
import pl.agh.neunet.action.ActionType;

public class PropertyFileManager implements ActionTaker {
	private static final String FILENAME_ARGUMENT_NAME = "filename";

	private static final Logger logger = Logger
			.getLogger(PropertyFileManager.class);

	private void createDefaultPropertyFile(String filename) {
		Properties properties = new Properties();

		properties.setProperty("neurons.layers", "3");

		try {
			properties.storeToXML(new FileOutputStream(filename), null);
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	public void takeAction(ActionType type, Map<String, Object> arguments) {
		if (type.getGroup() != ActionGroup.PROPERTY_FILE) {
			throw new IllegalArgumentException();
		}

		switch (type) {
		case CREATE_DEFAULT_PROPERTY_FILE:
			if (arguments.get(FILENAME_ARGUMENT_NAME) == null) {
				throw new IllegalArgumentException();
			}
			createDefaultPropertyFile((String) arguments
					.get(FILENAME_ARGUMENT_NAME));
			break;
		default:
			throw new IllegalArgumentException();
		}
	}
}
