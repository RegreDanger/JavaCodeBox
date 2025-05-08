package util.menu;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * A class base that simplifies a creation of Menu for console.
 * @author regrettabledanger10
 */
public class ConsoleMenu {
	
	private Scanner input = new Scanner(System.in);
	private String[] options;
	private String title;
	private String message;
	private String errorMessage = "Error, the introduced character is not of the type of entry";
	private static short idNextMenu;
	private static short i;
	private short idMenu;
	private short valueShort;
	
	/**
	 * Sets an empty object.
	 * @author regrettabledanger10}
	 */
	public ConsoleMenu() {
		this("", "", "", new String[] {});
	}
	
	/**
	 * 
	 * Sets an Menu with the type input of a short, and without title, the results are obtained by the method: getResults();
	 * 
	 * @param message the message or description.
	 * @param errorMessage the message error if the user enter a value other than entry, if is null or the length is 0, the error message will be: Error, the introduced character is not of the type of entry.
	 * @param options the options of an string.
	 * @author regrettabledanger10
	 */
	public ConsoleMenu(String message, String errorMessage, String[] options) {
		idNextMenu++;
		this.title = "";
		this.message = message;
		if (errorMessage != null && errorMessage.length() > 0) {
				this.errorMessage = errorMessage;
		}
		this.options = options;
	}
	
	/**
	 * 
	 * Sets an Menu with the type input of a short, the results are obtained by the method: getResults();
	 * 
	 * @param title the title of the Menu, if is null or the length is 0, the title will be like: Menu + ID of the Menu.
	 * @param message the message or description.
	 * @param errorMessage the message error if the user enter a value other than entry, if is null or the length is 0, the error message will be: Error, the introduced character is not of the type of entry.
	 * @param options the options of an string.
	 * @author regrettabledanger10
	 */
	public ConsoleMenu(String title, String message, String errorMessage, String[] options) {
		idNextMenu++;
		this.idMenu = idNextMenu;
		if (title != null && title.length() > 0) {
			this.title = title;
		} else {
			this.title = "Menu: " + idMenu;
		}
		this.message = message;
		if (errorMessage != null && errorMessage.length() > 0) {
				this.errorMessage = errorMessage;
		}
		this.options = options;
	}
	
	/**
	 * 
	 * Runs the menu, and store the values in the variables, the results are obtained by the method: getResults();
	 * if the user introduces something different from the entry type, prints the errorMessage and repeat.
	 * @author regrettabledanger10
	 */
	public void runInput() {
		if (title.contains("Menu: ")) {
			System.out.println(title);
		} else {
			System.out.println("Menu: " + title);
		}
		System.out.println(message);
		for (i = 0; i < options.length; i++) {
			System.out.println(String.valueOf(i + 1) + ": " + options[i]);
		}
		try {
			valueShort = input.nextShort();
		} catch (InputMismatchException e) {
			System.out.println(errorMessage);
			input = new Scanner(System.in);
			runInput();
		}
	}
	
	/**
	 * @return the result of the input, give the number but subtracting a 1.
	 * @author regrettabledanger10
	 */
	public short getResults() {
		return (short) (valueShort - 1);
	}
	
	/**
	 * @return the ID of the Menu.
	 * @author regrettabledanger10
	 */
	protected short getIDMenu() {
		return idMenu;
	}
	
	/**
	 * @return the title.
	 * @author regrettabledanger10
	 */
	protected String getTitle() {
		return title;
	}
	
	/**
	 * @return the message.
	 * @author regrettabledanger10
	 */
	protected String getMessage() {
		return message;
	}
	
	/**
	 * @return the errorMessage.
	 * @author regrettabledanger10
	 */
	protected String getErrorMessage() {
		return errorMessage;
	}
		
	/**
	 * @return the options.
	 * @author regrettabledanger10
	 */
	protected String[] getOptions() {
		return options;
	}

	@Override
	public String toString() {
		return "Title: " + "\"" + getTitle() + "\"" + "\n"
				+ "Options amount: " + getOptions().length + "\n" + "Options Name: " + Arrays.toString(getOptions())
				+ "\n" + "ID Menu: " + getIDMenu();
	}
}
