package pl.agh.neunet;

import org.apache.log4j.Logger;

import pl.agh.neunet.view.MainWindow;

public class Neunet {
	private static final Logger logger = Logger.getLogger(Neunet.class);

	public static void main(String[] args) {
		logger.info("Neunet started execution");
		new MainWindow();
	}
}
