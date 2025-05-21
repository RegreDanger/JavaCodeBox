package util.menu;

public interface Optionable {
	
	/**
	 * @return the name of the option.
	 * @author regrettabledanger10
	 */
	public abstract String getName();
	
	/**
	 * @return the ID/Number of the option.
	 * @author regrettabledanger10
	 */
	public abstract short ID();
	
	/**
	 * The action to execute.
	 * @param c the main menu, this will be used to recursive methods to return at the main menu of the options.
	 * @author regrettabledanger10
	 */
	public abstract void runOption(MenuEnhanced c);
	
}
