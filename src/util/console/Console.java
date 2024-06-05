package util.console;

import java.util.Scanner;

public class Console {
	protected static Scanner carrier;
	
	private Console() {}
	
	public static String readLine() {
		carrier = new Scanner(System.in);
		return carrier.nextLine();
	}
	
}