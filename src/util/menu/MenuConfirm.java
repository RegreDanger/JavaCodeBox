package util.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * A class that simplifies the creation of Menu Confirm for console.
 * @author regrettabledanger10
 */
public class MenuConfirm extends ConsoleMenu {
	private Scanner input = new Scanner(System.in);
	private String title;
	private String[] options;
	private String message;
	private String errorMessage = "Error, the introduced character is not of the type of entry";
	private String errorMessageRange = "Error, the value is out of range";
	private static final String[] twoOptions = new String[] {"Yes.", "No."};
	private static final String[] twoOptionsEs = new String[] {"Sí.", "No."};
	private static short idNextMenuConfirm;
	private short i;
	private short idMenuConfirm;
	private short valueShort;
	
	/**
	 * Sets an empty object.
	 * @author regrettabledanger10}
	 */
	public MenuConfirm() {
		this("", "", "", false);
	}
	
	/**
	 * 
	 * Sets an Menu with the only option of yes and no.
	 * 
	 * @param title the title of the Menu, if is null or the length is 0, the title will be like: Menu Confirm + ID of the Menu.
	 * @param message the message or description.
	 * @param errorOutOfRange the error message if the user introduces other number than 1 and 2 (1 for yes or 2 for no).
	 * @param errorMessage the message error if the user enter a value other than entry, if this parameter is null or the length is 0, the error message will be: Error, the introduced character is not of the type of entry or is out of range.
	 * @param spanish if is true, set the Yes and no into a Spanish version: Sí and no
	 * @author regrettabledanger10
	 */
	public MenuConfirm(String title, String message, String errorOutOfRange, String errorMessage, boolean spanish) {
		idNextMenuConfirm++;
		this.idMenuConfirm = idNextMenuConfirm;
		if (title != null && title.length() > 0) {
			this.title = title;
		} else {
			this.title = "Menu Confirm: " + idMenuConfirm;
		}
		this.message = message;
		if(errorOutOfRange != null && errorOutOfRange.length() > 0) {
			this.errorMessageRange = errorOutOfRange;
		}
		if (errorMessage != null && errorMessage.length() > 0) {
			this.errorMessage = errorMessage;
		}
		if(spanish) {
			this.options = twoOptions;
		} else {
			this.options = twoOptionsEs;
		}
	}
	
	/**
	 * 
	 * Sets an Menu with the only option of yes and no and without title.
	 * 
	 * @param message the message or description.
	 * @param errorOutOfRange the error message if the user introduces other number than 1 and 2 (1 for yes or 2 for no).
	 * @param errorMessage the message error if the user enter a value other than entry, if this parameter is null or the length is 0, the error message will be: Error, the introduced character is not of the type of entry or is out of range.
	 * @param spanish if is true, set the Yes and no into a Spanish version: Sí and no
	 * @author regrettabledanger10
	 */
	public MenuConfirm(String message, String errorOutOfRange, String errorMessage, boolean spanish) {
		idNextMenuConfirm++;
		this.idMenuConfirm = idNextMenuConfirm;
		this.title = "";
		this.message = message;
		if(errorOutOfRange != null && errorOutOfRange.length() > 0) {
			this.errorMessageRange = errorOutOfRange;
		}
		if (errorMessage != null && errorMessage.length() > 0) {
			this.errorMessage = errorMessage;
		}
		if(spanish) {
			this.options = twoOptionsEs;
		} else {
			this.options = twoOptions;
		}
	}
	
	/**
	 * 
	 * Runs the menu with the given options, store the value in the variable, and the result are obtained by the method: getResults();
	 * If the number introduced by user is different from 1 or 2, (1 for yes, 2 for no), prints the errorOutOfRange message and repeat.
	 * and if the user introduces something different from the integer entry type, prints the errorMessage and repeat.
	 * @author regrettabledanger10
	 */
	public void runInput() {
		System.out.println(message);
		for(i = 0; i < options.length; i++) {
			System.out.println(String.valueOf(i+1) + ": " + options[i]);
		}
		try {
			valueShort = input.nextShort();
			if(valueShort > options.length || valueShort < 1) {
				System.out.println(errorMessageRange);
				runInput();
			}
		} catch (InputMismatchException e) {
			System.out.println(errorMessage);
			input = new Scanner(System.in);
			runInput();
		}
	}
	
	/**
	 * @return the result of the input.
	 * @author regrettabledanger10
	 */
	public byte getResultConfirm() {
		return (byte) (valueShort);
	}
	

	/**
	 * @return the ID of the Menu.
	 * @author regrettabledanger10
	 */
	protected short getIDMenuConfirm() {
		return idMenuConfirm;
	}
	
	protected String getTitleConfirm() {
		return title;
	}
	
	protected String getMessageConfirm() {
		return message;
	}
	
	/**
	 * @return the errorMessage.
	 * @author regrettabledanger10
	 */
	protected String getErrorMessageConfirm() {
		return errorMessage;
	}
	
	/**
	 * @return the errorMessageRange
	 */
	protected String getErrorMessageRangeConfirm() {
		return errorMessageRange;
	}
	
	/**
	 * @return the optionsEnhanced.
	 * @author regrettabledanger10
	 */
	protected String[] getOptionsConfirm() {
		return options;
	}
	
}
