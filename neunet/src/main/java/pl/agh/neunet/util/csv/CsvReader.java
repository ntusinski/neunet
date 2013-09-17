package pl.agh.neunet.util.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
	private BufferedReader br;

	public CsvReader(String filename) {
		try {
			br = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Double> readNextLine() {
		try {
			String line = br.readLine();

			if (line == null) {
				return null;
			}

			String[] splitLine = line.split(";");
			List<Double> nextLine = new ArrayList<Double>();

			for (int i = 0; i < splitLine.length; i++) {
				nextLine.add(Double.parseDouble(splitLine[i]));
			}
			return nextLine;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
