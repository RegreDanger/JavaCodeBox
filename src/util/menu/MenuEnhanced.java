package util.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * A class that simplifies a creation of Menu by class per option for console.
 * @author regrettabledanger10
 */
public class MenuEnhanced extends ConsoleMenu {
	
	private Scanner input = new Scanner(System.in);
	private Optionable[] options;
	private String title;
	private String message;
	private String errorMessage = "Error, the introduced character is not of the type of entry";
	private String errorMessageRange = "Error, the value is not equal with something ID";
	private static final String ERROR_MESSAGE_ID = "The id are equal, and not different, change it";
	private static final String ERROR_MESSAGE_NAME = "The name of the options are equal and not different, change it";
	private static final byte NEXT_POSITION = 1;
	private static short idNextMenuEnhanced;
	private static short i, j, k;
	private short idMenuEnhanced;
	private short valueShort;
	
	/**
	 * Sets an empty object.
	 * @author regrettabledanger10
	 */
	public MenuEnhanced() {
		this("", "", "", "", new Optionable[] {});
	}
	
	/**
	 * 
	 * Sets an Menu, by the given options, without title.
	 * 
	 * Every option is a one class, and after the creation of the classes,
	 * in the options parameter, an Optionable array should be given with class objects that implement the Optionable Interface.
	 * (it is recommended that one of the constructors of the object be empty).
	 * 
	 * <p>Only in case of recursive function, to initiate the current menu again, in the method runOption, 
	 * use the parameter c and write this instruction: c.runInput(), before the execution of the recursive method, 
	 * if is wrote in a if block, is recommended write the instruction "return;", if is wrote at the end of the method, do not write anything.</p>
	 * 
	 * @param message the message or description.
	 * @param errorMessage the message error if the user enter a value other than entry, 
	 * if this parameter is null or the length is 0, the error message will be: Error, the introduced character is not of the type of entry or is out of range.
	 * @param errorOutOfRange the message error if the user enter a value that is not equal that something ID, 
	 * the error message will be: Error, the value is not equal with something ID.
	 * @param options a Array of the AllOptions class with the instance of the classes of options.
	 * @author regrettabledanger10
	 */
	public MenuEnhanced(String message, String errorMessage, String errorOutOfRange, Optionable[] options) {
		idNextMenuEnhanced++;
		this.idMenuEnhanced = idNextMenuEnhanced;
		this.title = "";
		this.message = message;
		if (errorMessage != null && errorMessage.length() > 0) {
			this.errorMessage = errorMessage;
		}
		if(errorOutOfRange != null && errorOutOfRange.length() > 0) {
			this.errorMessageRange = errorOutOfRange;
		}
		this.options = options;
	}
	
	/**
	 * 
	 * Sets an Menu, by the given options.
	 * 
	 * Every option is a one class, and after the creation of the classes,
	 * in the options parameter, an Optionable array should be given with class objects that implement the Optionable Interface.
	 * (it is recommended that one of the constructors of the object be empty).
	 * 
	 * <p>Only in case of recursive function, to initiate the current menu again, in the method runOption, 
	 * use the parameter c and write this instruction: c.runInput(), before the execution of the recursive method, 
	 * if is wrote in a if block, is recommended write the instruction "return;", if is wrote at the end of the method, do not write anything.</p>
	 * 
	 * @param title the title of the Menu, if is null or the length is 0, the title will be like: Menu Enhanced + ID of the Menu.
	 * @param message the message or description.
	 * @param errorMessage the message error if the user enter a value other than entry, 
	 * if this parameter is null or the length is 0, the error message will be: Error, the introduced character is not of the type of entry or is out of range.
	 * @param errorOutOfRange the message error if the user enter a value that is not equal that something ID, 
	 * the error message will be: Error, the value is not equal with something ID.
	 * @param options a Array of the AllOptions class with the instance of the classes of options.
	 * @author regrettabledanger10
	 */
	public MenuEnhanced(String title, String message, String errorMessage, String errorOutOfRange, Optionable[] options) {
		idNextMenuEnhanced++;
		this.idMenuEnhanced = idNextMenuEnhanced;
		if (title != null && title.length() > 0) {
			this.title = title;
		} else {
			this.title = "Menu Enhanced: " + idMenuEnhanced;
		}
		this.message = message;
		if (errorMessage != null && errorMessage.length() > 0) {
			this.errorMessage = errorMessage;
		}
		if(errorOutOfRange != null && errorOutOfRange.length() > 0) {
			this.errorMessageRange = errorOutOfRange;
		}
		this.options = options;
	}
	
	/**
	 * 
	 * Runs the menu with the options of the array of the AllOptions;
	 * if the ID of the class is equal to that of another class, it gives Error(Exception), 
	 * if the option name is equal to that of another class, it gives Error(Exception),
	 * If the number introduced by user is different from any ID of class, prints the errorOutOfRange message and repeat,
	 * and if the user introduces something different from the integer entry type, prints the errorMessage and repeat.
	 * @author regrettabledanger10
	 */
	@Override
	public void runInput() {
		if (title.contains("Menu: ")) {
			System.out.println(title);
		} else {
			System.out.println("Menu: " + title);
		}
		System.out.println(message);
		for (j = 0; j < options.length; j++) {
			for (k = 0; k < options.length - 1; k++) {
				if (options[k].ID() == options[k + NEXT_POSITION].ID()) {
					throw new Error(ERROR_MESSAGE_ID);
				} else if (options[k].getName().equals(options[k + NEXT_POSITION].getName())) {
					throw new Error(ERROR_MESSAGE_NAME);
				}
				if (options[k].ID() > options[k + NEXT_POSITION].ID()) {
					Optionable NewValue = options[k];
					options[k] = options[k + NEXT_POSITION];
					options[k + NEXT_POSITION] = NewValue;
					
				}
			}
		}
		for (Optionable option : options) {
			System.out.println(option.ID() + ": " + option.getName());
		}
		try {
			boolean outOfRange = false;
			valueShort = input.nextShort();
			for (Optionable current : options) {
				if (valueShort == current.ID()) {
					outOfRange = false;
					current.runOption(this);
					break;
				} else {
					outOfRange = true;
				}
			}
			if (outOfRange) {
				System.err.println(errorMessageRange);
				runInput();
			}
		} catch (InputMismatchException e) {
			System.out.println(errorMessage);
			input = new Scanner(System.in);
			runInput();
		}
	}
	
	/**
	 * @return the ID of the Menu.
	 * @author regrettabledanger10
	 */
	protected short getIDMenuEnhanced() {
		return idMenuEnhanced;
	}
	
	/**
	 * @return the title.
	 * @author regrettabledanger10
	 */
	protected String getTitleEnhanced() {
		return title;
	}
	
	protected String getMessageEnhanced() {
		return message;
	}
	
	/**
	 * @return the errorMessage.
	 * @author regrettabledanger10
	 */
	protected String getErrorMessageEnhanced() {
		return errorMessage;
	}
	
	/**
	 * @return the errorMessageRange
	 */
	protected String getErrorMessageRangeEnhanced() {
		return errorMessageRange;
	}
	
	/**
	 * @return the optionsEnhanced.
	 * @author regrettabledanger10
	 */
	protected Optionable[] getOptionsEnhanced() {
		return options;
	}
	
	@Override
	public String toString() {
		StringBuilder optStr = new StringBuilder();
		optStr.append('[');
		for (i = 0; i < options.length; i++) {
			optStr.append(options[i].getName());
			if(i == options.length-1) {
				optStr.append(']').toString();
				break;
			}
			optStr.append(", ");
		}
		return "Title: " + "\"" + getTitleEnhanced() + "\"" + "\n" + "Options amount: " 
				+ getOptionsEnhanced().length + "\n" + "Options: "
				+ optStr + "\n" + "ID: " + getIDMenuEnhanced();
	}
	
}
