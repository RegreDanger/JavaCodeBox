package util.datalib;

public class Coder implements Codificable {
	private static Coder instance;
	
	private Coder() {}
	
	public static Coder getInstance() {
		if(instance == null) {
			instance = new Coder();
		}
		return instance;
	}

	@Override
	public String getSimpleCharEncoded(char ch) {
		return CHARACTER_CODING.getValue(ch);
	}

	@Override
	public String getSimpleNumberEncoded(int number) {
		return NUMBER_CODING.getValue(number);
	}

	@Override
	public char getSimpleCharDecoded(String code) {
		return CHARACTER_CODING.getKey(code);
	}

	@Override
	public int getSimpleNumberDecoded(String code) {
		return NUMBER_CODING.getKey(code);
	}
	
	@Override
	public boolean containsChar(char ch) {
		return CHARACTER_CODING.containsKey(ch);
	}
	
	@Override
	public boolean containsNumber(int number) {
		return NUMBER_CODING.containsKey(number);
	}
	
	@Override
	public boolean containsCharCode(String code) {
		return CHARACTER_CODING.containsValue(code);
	}
	
	@Override
	public boolean containsNumberCode(String code) {
		return NUMBER_CODING.containsValue(code);
	}
	
	/**
	 * Encodes a String.
	 *
	 * @param str the String to be encoded.
	 * @return the String encoded.
	 * @author regrettabledanger10.
	 */
	public String getEncoded(String str) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			result.append(getSimpleEncoded(str.charAt(i)));
			if(i < str.length()-1) {
				result.append(SEPARATOR);
			}
		}
		return result.toString();
	}
	
	/**
	 * Decodes a String.
	 *
	 * @param str the String to be decoded.
	 * @return the String decoded.
	 * @author regrettabledanger10.
	 */
	public String getDecoded(String str) {
		StringBuilder result = new StringBuilder();
		String[] splitString = str.split("\\" + SEPARATOR);
		for(int i = 0; i < splitString.length; i++) {
			result.append(getSimpleDecoded(splitString[i]));
		}
		return result.toString();
	}
	
	/**
	 * Encodes a char or number.
	 *
	 * @param ch the char to be encoded.
	 * @return the char encoded in a String or the original value if isn't present in the alphabet.
	 * @author regrettabledanger10.
	 */
	@Override
	public String getSimpleEncoded(char ch) {
		String str = "";
		if(containsChar(Character.toLowerCase(ch))) {
			str = getSimpleCharEncoded(Character.toLowerCase(ch));
		} else if (containsNumber(Integer.valueOf(String.valueOf(ch)))){
			str = getSimpleNumberEncoded(Integer.valueOf(String.valueOf(ch)));
		} else {
			str = String.valueOf(ch);
		}
		return str;
	}
	
	/**
	 * Decodes a char or number.
	 *
	 * @param ch the char to be decoded.
	 * @return the char decoded in a String or the original value if isn't present in the alphabet.
	 * @author regrettabledanger10.
	 */
	@Override
	public String getSimpleDecoded(String code) {
		String str = "";
		if(containsCharCode(code)) {
			str = String.valueOf(getSimpleCharDecoded(code));
		} else if(containsNumberCode(code)) {
			str = String.valueOf(getSimpleNumberDecoded(code));
		} else {
			str = code;
		}
		return str;
	}
	
}
