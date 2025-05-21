package util.gui.swing;

import util.RegexEnum;

public class HexCode {
	String hexCode = "";
	
	/**
	 * Constructs a HexCode by String.
	 * 
	 * @param hexCode the hexadecimal code.
	 * @throws IllegalArgumentException if syntax are invalid.
	 */
	public HexCode(String hexCode) {
		if(validateHexCode(hexCode)){
			throw new IllegalArgumentException("The format is incorrect, must be like: \"#FF0000\" or \"#FF000000\"");
		}
		this.hexCode = hexCode;
	}
	
	public void mixColor(HexCode secondHexCode) {
		int[] firstVals = toRGBA();
		int[] secondVals = secondHexCode.toRGBA();
		int rMixed = (firstVals[0] + secondVals[0]) / 2;
		int gMixed = (firstVals[1] + secondVals[1]) / 2;
		int bMixed = (firstVals[2] + secondVals[2]) / 2;
		int aMixed = (firstVals[3] + secondVals[3]) / 2;
		if(aMixed == 255) {
			hexCode = rgbToHex(rMixed, gMixed, bMixed).toString();
			return;
		}
		hexCode = rgbaToHex(rMixed, gMixed, bMixed, aMixed).toString();
	}
	
	/**
	 * Converts the hexadecimal code to a rgb values, must be follow the pattern: #RRGGBB or #RRGGBBAA..
	 * 
	 * @param hexCode the hexadecimal code.
	 * @return an array with the rgb values.
	 */
	public int[] toRGBA() {
		String hexCodeTrim = hexCode.substring(1);
		int[] rgba = new int[4];
		rgba[0] = Integer.parseInt(hexCodeTrim.substring(0, 2), 16);
		rgba[1] = Integer.parseInt(hexCodeTrim.substring(2, 4), 16);
		rgba[2] = Integer.parseInt(hexCodeTrim.substring(4, 6), 16);
		if(hexCodeTrim.length() == 8) {
			rgba[3] = Integer.parseInt(hexCodeTrim.substring(6, 8), 16);
		} else {
			rgba[3] = 255;
		}
		return rgba;
	}
	
	@Override
	public String toString() {
		return hexCode;
	}
	
	/**
	 * Converts the rgba values into a hexadecimal code, follow the pattern: #RRGGBBAA.
	 * 
	 * @param r the red value.
	 * @param g the green value.
	 * @param b the blue value.
	 * @param a the alpha/opacity value.
	 * @return the hexadecimal code.
	 */
	public static HexCode rgbaToHex(int r, int g, int b, int a) {
		ColorEnhanced.validateRGBA(r, g, b, a);
		return new HexCode(String.format("#%02x%02x%02x%02x", r, g, b, a).toUpperCase());
	}
	
	/**
	 * Converts the rgb values into a hexadecimal code, follow the pattern: #RRGGBB.
	 * 
	 * @param r the red value.
	 * @param g the green value.
	 * @param b the blue value.
	 * @return the hexadecimal code.
	 */
	public static HexCode rgbToHex(int r, int g, int b) {
		ColorEnhanced.validateRGB(r, g, b);
		return new HexCode(String.format("#%02x%02x%02x", r, g, b).toUpperCase());
	}
	
	/**
	 * Validates the hexadecimal code.
	 * 
	 * @param hexCode the hexadecimal code.
	 * @exception IllegalArgumentException if the format is incorrect.
	 */
	public static boolean validateHexCode(String hexCode) {
		return (hexCode != null && (!RegexEnum.HEX_PATTERN.isValid(hexCode) && !RegexEnum.HEX_OPACITY_PATTERN.isValid(hexCode)));
	}
	
}
