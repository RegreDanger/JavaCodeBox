package util.gui.swing;

import java.awt.Color;

import util.GeneralUtil;

/**
 * A class for color enhanced, supports hexadecimal and more, extends the class Color.
 */
public class ColorEnhanced extends Color {
	private static final long serialVersionUID = 1919807017829184269L;
	/** The blue value.*/
	private int blue;
	/** The color. */
	private Color color;
	/** The green value.*/
	private int green;
	/** The hexCode of the color.*/
	private HexCode hexCode;
	
	/** The opacity/alpha value*/
	private int opacity;
	
	/** The red value.*/
	private int red;
	
	/**
	 * Create a color from the specified hexadecimal code, must be follow the pattern: #RRGGBB or #RRGGBBAA.
	 * 
	 * @param hexCode the hexadecimal code.
	 */
	public ColorEnhanced(HexCode hexCode) {
		super(0);
		this.hexCode = hexCode;
		int rgb[] = hexCode.toRGBA();
		red = rgb[0];
		green = rgb[1];
		blue = rgb[2];
		opacity = rgb[3];
		color = new Color(red, green, blue, opacity);
	}
	
	/**
	 * Constructs a color from the specified rgb values, teh values must be between 0 and 255 the opacity is default 255.
	 * 
	 * @param r the red value.
	 * @param g the blue value.
	 * @param b the green value.
	 */
	public ColorEnhanced(int r, int g, int b) {
		super(0);
		red = r;
		green = g;
		blue = b;
		opacity = 255;
		hexCode = HexCode.rgbToHex(r, g, b);
		color = new Color(r, g, b);
	}
	
	/**
	 * Constructs a color from the specified rgba values, the values must be between 0 and 255, a is the opacity.
	 * 
	 * @param r the red value.
	 * @param g the blue value.
	 * @param b the green value.
	 * @param a the opacity value.
	 */
	public ColorEnhanced(int r, int g, int b, int a) {
		super(0);
		red = r;
		green = g;
		blue = b;
		opacity = a;
		hexCode = HexCode.rgbaToHex(r, g, b, a);
		color = new Color(r, g, b, a);
	}
	
	/**
	 * Gets the blue value.
	 */
	@Override
	public int getBlue() {
		return blue;
	}
	
	/**
	 * Gets the green value.
	 */
	@Override
	public int getGreen() {
		return green;
	}
	
	/**
	 * @return the hexadecimalCode.
	 */
	public String getHexCode() {
		return this.hexCode.toString();
	}
	
	/**
	 * Gets the red value.
	 */
	@Override
	public int getRed() {
		return red;
	}
	
	/**
	 * Gets the value rgb from the color.
	 */
	@Override
	public int getRGB() {
		return color.getRGB();
	}
	
	/**
	 * Gets a array from the values rgba.
	 * 
	 * @return the array with the values, 0 for r, 1 for g, 2 for b, 3 for a.
	 */
	public int[] getRGBAArray() {
		return new int[] {red, green, blue, opacity};
	}
	
	/**
	 * Set a new color by the hexadecimal code, must be follow the pattern: #RRGGBB or #RRGGBBAA.
	 * 
	 * @param newHexCode the hexadecimal code.
	 */
	public void setColorByHex(HexCode newHexCode) {
		if (hexCode.equals(newHexCode)) {
			return;
		}
		this.hexCode = newHexCode;
		int[] rgba = newHexCode.toRGBA();
		red = rgba[0];
		green = rgba[1];
		blue = rgba[2];
		opacity = rgba[3];
		color = new Color(red, green, blue, opacity);
	}
	
	/**
	 * set the color from the specified rgb values, the values must be between 0 and 255.
	 * 
	 * @param r the red value.
	 * @param g the green value
	 * @param b the blue value.
	 */
	public void setColorByRGB(int r, int g, int b) {
		validateRGB(r, g, b);
		red = r;
		green = g;
		blue = b;
		hexCode = HexCode.rgbToHex(r, g, b);
		color = new Color(r, g, b);
	}
	
	/**
	 * set the color from the specified rgba values, the values must be between 0 and 255, a is the opacity.
	 * 
	 * @param r the red value.
	 * @param g the green value.
	 * @param b the blue value.
	 * @param a the alpha/opacity value.
	 */
	public void setColorByRGBA(int r, int g, int b, int a) {
		validateRGBA(r, g, b, a);
		red = r;
		green = g;	
		blue = b;
		opacity = a;
		hexCode = HexCode.rgbaToHex(r, g, b, a);
		color = new Color(r, g, b, a);		
	}
	
	/**
	 * Valiidates the values RGB
	 * 
	 * @param r the red value.
	 * @param g the green value.
	 * @param b the blue value.
	 * @exception IllegañArgumentException if something value is less than 0 or greater than 255.
	 */
	protected static void validateRGB(int r, int g, int b) {
		boolean[] toCheck = new boolean[] { (r < 0 || r > 255), (g < 0 || g > 255), (b < 0 || b > 255) };
		if (GeneralUtil.casesToEqual(toCheck)) {
			String badComponentString = "";
			int badComponent = GeneralUtil.binarySearch(new Boolean[] { toCheck[0], toCheck[1], toCheck[2] }, Boolean.TRUE);
			if (badComponent == 0) {
				badComponentString = "Red - r";
			}
			if (badComponent == 1) {
				badComponentString = "Green - g";
			}
			if (badComponent == 2) {
				badComponentString = "Blue - b";
			}
			throw new IllegalArgumentException("Color parameter outside of expected range: " + badComponentString);
		}
	}
	
	/**
	 * Validates the values RGB
	 * 
	 * @param r the red value.
	 * @param g the green value.
	 * @param b the blue value.
	 * @param a the alpha/opacity value.
	 * @exception IllegañArgumentException if something value is less than 0 or greater than 255.
	 */
	protected static void validateRGBA(int r, int g , int b, int a) {
		validateRGB(r, g, b);
		if(a < 0 || a > 255) {
			throw new IllegalArgumentException("Color parameter outside of expected range:" + "Opacity - a");
		}
	}
}
