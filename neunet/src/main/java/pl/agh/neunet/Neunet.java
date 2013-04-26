package pl.agh.neunet;

public class Neunet {
	public static void main(String[] args) {
		if (args.length != 1) {
			createDefaultPropertiesFile();
			throw new IllegalArgumentException(
					"You must specify 1 parameter -- "
							+ "path to properties file");
		}
		loadPropertiesFile(args[0]);
	}

	private static void loadPropertiesFile(String string) {
		// TODO Auto-generated method stub

	}

	private static void createDefaultPropertiesFile() {
		// TODO Auto-generated method stub

	}
}
