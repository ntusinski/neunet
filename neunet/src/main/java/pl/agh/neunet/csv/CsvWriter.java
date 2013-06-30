package pl.agh.neunet.csv;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class CsvWriter {
	private PrintWriter writer;

	public CsvWriter(String filename) {
		try {
			writer = new PrintWriter(filename);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public void writeNextLine(List<Double> weights) {
		StringBuilder sb = new StringBuilder();

		sb.append(weights.get(0));
		for (int i = 1; i < weights.size(); i++) {
			sb.append(";" + weights.get(i));
		}
		writer.println(sb.toString());
	}

	public void close() {
		writer.close();
	}
}
