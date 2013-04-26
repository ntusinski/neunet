package pl.agh.neunet.util;

import java.util.Scanner;

public final class ConsoleUtil {
	private static final Scanner scan = new Scanner(System.in);

	public static double nextDouble() {
		while (!scan.hasNext()) {
		}
		return scan.nextDouble();
	}
}
