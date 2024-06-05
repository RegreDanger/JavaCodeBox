package util.menu;

import java.util.Scanner;

/**
 * 
 * A class for Menu of Menus for console.
 * @author regrettabledanger10
 */
public class MenuTypes extends ConsoleMenu {
	
	private ConsoleMenu[] menus;
	private Scanner superInput = new Scanner(System.in);
	private String superTitle;
	private String superMessage;
	private String superErrorMessage = "Error, the introduced character is not of the type of entry";
	private String superErrorMessageRange = "Error, the value is not equal with something ID";
	private static short idNextSuperMenu;
	private short idSuperMenu;
	private static short i;
	
	/**
	 * Sets an empty object.
	 * @author regrettabledanger10
	 */
	public MenuTypes() {
		this("", "", "", "", new ConsoleMenu[] {});
	}
	
	/**
	 * 
	 * Sets an Menu for the Menus.
	 * The type of entry of the input is 1 (Integer).
	 * 
	 * @param title the title of the Menu, if is null or the length is 0, the title will be like: Menu + ID of the main Menu.
	 * @param message the message or description.
	 * @param errorMessage the message error if the user enter a value other than entry or options, if is null or the length is 0, the error message will be: Error, the introduced character is not of the type of entry or is out of the range.
	 * @param menus the menus.
	 * 
	 * @author regrettabledanger10
	 */
	public MenuTypes(String title, String message, String errorMessage, String errorOutOfRange, ConsoleMenu[] menus) {
		idNextSuperMenu++;
		this.idSuperMenu = idNextSuperMenu;
		if (title != null && title.length() != 0) {
			this.superTitle = title;
		} else {
			this.superTitle = "Main Menu: " + idSuperMenu;
		}
		this.superMessage = message;
		if (errorMessage != null && errorMessage.length() != 0) {
			this.superErrorMessage = errorMessage;
		}
		if (errorOutOfRange != null && errorOutOfRange.length() > 0) {
			this.superErrorMessageRange = errorOutOfRange;
		}
		this.menus = menus;
	}
	
	/**
	 * 
	 * Runs the menu, if the number introduced is not equal with something number of the menu, repeats.
	 * 
	 * @author regrettabledanger10
	 */
	public void runMenu() {
		if(superTitle.contains("Menu: ")) {
			System.out.println(superTitle);
		} else {
			System.out.println("Menu: " + superTitle);
		}
		System.out.println(superMessage);
		for(i = 0; i < menus.length; i++) {
			System.out.println(String.valueOf(i+1) + ": " + menus[i].getTitle());
		}
		try {
			short optionMenu = superInput.nextShort();
			if(optionMenu > menus.length || optionMenu <= 0) {
				System.err.println(superErrorMessageRange);
				runMenu();
			}
			for (i = 0; i < menus.length; i++) {
				if (Integer.parseInt(String.valueOf(i + 1)) == optionMenu) {
					menus[i].runInput();
				}
			}
		} catch (Exception e) {
			System.out.println(superErrorMessage);
			superInput = new Scanner(System.in);
			runMenu();
		}
	}
	
	@Override
	public String toString() {
		String menuString = "";
		for (ConsoleMenu menu : menus) {
			menuString = menuString + menu.toString() + "\n\n";
		}
		return "Title: " + "\"" + superTitle + "\"" + "\nID Main Menu: " + idSuperMenu + "\nOptions amount: " + menus.length + "\nsubMenus: { \n" + menuString + "}"; 
	}
}
