package util;

import java.util.regex.Pattern;

/**
 * A Enum for patterns.
 */
public enum RegexEnum {
	/** Pattern for: "0, 0, 0" Accept numbers, coma, space.*/
	ONLY_NUMBER_COMA_SPACE("\\d+,\\s\\d+(,\\s\\d+)*$"),
	/** Pattern for hexadecimal default: #RRGGBB*/
	HEX_PATTERN("^#[0-9A-Fa-f]{6}$"),
	/** Pattern for hexadecimal with alpha/opacity: #RRGGBBAA*/
	HEX_OPACITY_PATTERN("^#[0-9A-Fa-f]{8}$");
	
	/** The pattern.*/
	private final String pattern;
	
	/**
	 * Construct the pattern.
	 * 
	 * @param pattern the pattern.
	 */
	RegexEnum(String pattern){
		this.pattern = pattern;
	}
	
	/**
	 * Checks if the input is valid to pattern.
	 * 
	 * @param input the input String to check
	 * @return true if is valid, false otherwise.
	 */
	public boolean isValid(String input) {
		return Pattern.matches(pattern, input);
	}
}
