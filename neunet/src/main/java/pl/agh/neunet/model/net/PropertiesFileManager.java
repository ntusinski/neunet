package pl.agh.neunet.model.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import pl.agh.neunet.shared.action.ActionGroup;
import pl.agh.neunet.shared.action.ActionType;

public class PropertiesFileManager implements ActionTaker {
	private static final String FILENAME_ARGUMENT_NAME = "filename";

	private static final Logger logger = Logger
			.getLogger(PropertiesFileManager.class);

	private void createDefaultPropertiesFile(String filename) {
		if (filename == null) {
			throw new IllegalArgumentException();
		}

		Properties properties = new Properties();

		properties.setProperty("neurons.layers", "3");
		properties.setProperty("neurons.numbers", "2 4 2");
		properties.setProperty("neurons.weights", "1 1 1 1 1 1 1 1 1 "
				+ "1 1 1 1 1 1 ");

		try {
			properties.storeToXML(new FileOutputStream(filename), null);
		} catch (IOException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	private void loadFromPropertiesFile(String string) {
		// TODO Auto-generated method stub

	}

	public void takeAction(ActionType type, Map<String, Object> arguments) {
		if (type.getGroup() != ActionGroup.PROPERTIES_FILE) {
			throw new IllegalArgumentException();
		}

		switch (type) {
		case CREATE_DEFAULT_PROPERTIES_FILE:
			createDefaultPropertiesFile((String) arguments
					.get(FILENAME_ARGUMENT_NAME));
			break;
		case LOAD_PROPERTIES_FILE:
			loadFromPropertiesFile((String) arguments
					.get(FILENAME_ARGUMENT_NAME));
		default:
			throw new IllegalArgumentException();
		}
	}
}
