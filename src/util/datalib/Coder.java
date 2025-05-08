package util.datalib;

public class Coder implements Codificable {
	private static String[] splitCode;
	private static String[] splitCodeN;
	private static short i;
	private static short j;
	private static final Coder coder1 = new Coder();
	
	@Override
	public String getCharacter(final short position) {
		// TODO Auto-generated method stub
		return Codificable.code[position];
	}
	
	@Override
	public String getCharacterN(final short position) {
		// TODO Auto-generated method stub
		return Codificable.codeN[position];
	}
	
	@Override
	public String getCharacterDecoded(final short position) {
		// TODO Auto-generated method stub
		return Codificable.decode[position];
	}
	
	@Override
	public String getCharacterNDecoded(final short position) {
		// TODO Auto-generated method stub
		return Codificable.decodeN[position];
	}
	
	@Override
	public String getLetter(final short position) {
		// TODO Auto-generated method stub
		return Codificable.letters[position];
	}
	
	@Override
	public String getNumber(final short position) {
		// TODO Auto-generated method stub
		return Codificable.numbers[position];
	}
	
	@Override
	public short getNumbersLength() {
		// TODO Auto-generated method stub
		return (short) (Codificable.numbers.length);
	}
	
	@Override
	public short getLettersLength() {
		// TODO Auto-generated method stub
		return (short) (Codificable.letters.length);
	}
	
	@Override
	public short getCodeLength() {
		// TODO Auto-generated method stub
		return (short) (Codificable.code.length);
	}
	
	@Override
	public short getCodeNLength() {
		// TODO Auto-generated method stub
		return (short) (Codificable.codeN.length);
	}
	
	@Override
	public short getDecodeLength() {
		// TODO Auto-generated method stub
		return (short) (Codificable.decode.length);
	}
	
	@Override
	public short getDecodeNLength() {
		// TODO Auto-generated method stub
		return (short) (Codificable.decodeN.length);
	}
	
	/**
	 * Encode a String.
	 *
	 * @param str the String to be Encoded.
	 * @return the String Encoded.
	 * @author regrettabledanger10.
	 */
	public static String getStringEncoded(String str) {
		for (i = 0; i < str.length(); i++) {
			for (j = 0; j < coder1.getLettersLength(); j++) {
				str = str.replaceAll(coder1.getLetter(j), coder1.getCharacter(j));
			}
		}
		return str;
	}
	
	/**
	 *
	 * If is the string is coded, Returns the decoded String.
	 *
	 * @param str String to decoded.
	 * @return the String decoded, if the string is not coded, return the same string.
	 * @author regrettabledanger10.
	 */
	public static String getStringDecoded(String str) {
		if (isStringEncoded(str)) {
			splitCode = str.split("-"); // divide the string by obtaining the code and eliminating the symbol
			for (i = 0; i < splitCode.length; i++) {
				for (j = 0; j < coder1.getDecodeLength(); j++) {
					if (splitCode[i].equalsIgnoreCase(coder1.getCharacterDecoded(j))) { // check if the the code in the array is equal to what is in the other array
						splitCode[i] = coder1.getLetter(j); // Replace the code for letters
					}
				}
			}
			str = "";
			for (i = 0; i < splitCode.length; i++) {
				str = str + splitCode[i];
			}
			return str;
		} else {
			return str;
		}
	}
	
	/**
	 * Encode a number.
	 *
	 * @param number the number to be Encoded.
	 * @return the number Encoded.
	 * @author regrettabledanger10.
	 */
	public static String getNumberEncoded(String number) {
		for (i = 0; i < number.length(); i++) {
			for (j = 0; j < coder1.getNumbersLength(); j++) {
				number = number.replaceAll(coder1.getNumber(j), coder1.getCharacterN(j));
			}
		}
		return number;
	}
	
	/**
	 *
	 * If is the String is coded, returns the decoded Number.
	 *
	 * @param number Number to decoded.
	 * @return the Number decoded, if the String is not coded, return the same number.
	 * @author regrettabledanger10.
	 */
	public static String getNumberDecoded(String number) {
		if (isNumberEncoded(number)) {
			splitCodeN = number.split("_");
			for (i = 0; i < splitCodeN.length; i++) {
				for (j = 0; j < coder1.getDecodeNLength(); j++) {
					if (splitCodeN[i].equalsIgnoreCase(coder1.getCharacterNDecoded(j))) {
						splitCodeN[i] = coder1.getNumber(j);
					}
				}
			}
			number = "";
			for (i = 0; i < splitCodeN.length; i++) {
				number = number + splitCodeN[i];
			}
			return number;
		} else {
			return number;
		}
	}
	
	/**
	 * Encode a String with letters & numbers.
	 *
	 * @param str the String to be Encoded.
	 * @return the String Encoded or the same String if not coded if is not mixed.
	 * @author regrettabledanger10.
	 */
	public static String getMixedEncoded(String str) {
		if (isMixed(str)) {
			str = getNumberEncoded(str);
			str = getStringEncoded(str);
		}
		return str;
	}
	
	/**
	 *
	 * If is the string is Mixed coded, returns the decoded String.
	 *
	 * @param str String to decoded.
	 * @return the String decoded, if the string is not coded mixed, return the same string.
	 * @author regrettabledanger10.
	 */
	public static String getMixedDecoded(String str) {
		if (isMixedEncoded(str)) {
			str = getStringDecoded(str);
			str = getNumberDecoded(str);
		}
		return str;
	}
	
	/**
	 *
	 * Get the Encode automatically of a String
	 *
	 * @param str the String to Encode.
	 * @return a Encode of the String.
	 * @author regrettabledanger10.
	 */
	public static String getEncode(final String str) {
		if (isMixed(str)) {
			return getMixedEncoded(str);
		} else if (isNumber(str)) {
			return getNumberEncoded(str);
		} else if (isWord(str)) {
			return getStringEncoded(str);
		}
		return null;
	}
	
	/**
	 *
	 * Get the decode automatically of a String.
	 *
	 * @param str the String to decode.
	 * @return a decoded String.
	 * @author regrettabledanger10.
	 */
	public static String getDecoded(final String str) {
		if (isMixedEncoded(str)) {
			return getMixedDecoded(str);
		} else if (isNumberEncoded(str)) {
			return getNumberDecoded(str);
		} else if (isStringEncoded(str)) {
			return getStringDecoded(str);
		}
		return null;
	}
	
	/**
	 *
	 * Checks if the String is Encoded String.
	 *
	 * @param str String to be check.
	 * @return true if the String is Encoded, false otherwise.
	 * @author regrettabledanger10.
	 */
	public static boolean isStringEncoded(final String str) {
		for (i = 0; i < coder1.getCodeLength(); i++) {
			if (str.contains(coder1.getCharacter(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the String is Encoded Number.
	 *
	 * @param str String to be check.
	 * @return true if the String is Encoded numeric, false otherwise.
	 * @author regrettabledanger10.
	 */
	public static boolean isNumberEncoded(final String str) {
		for (i = 0; i < coder1.getCodeNLength(); i++) {
			if (str.contains(coder1.getCharacterN(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *
	 * Checks if the String is Encoded Mixed.
	 *
	 * @param str String to be check.
	 * @return true if the String is Encoded Mixed, false otherwise.
	 * @author regrettabledanger10.
	 */
	public static boolean isMixedEncoded(final String str) {
		for (i = 0; i < coder1.getCodeLength(); i++) {
			for (j = 0; j < coder1.getCodeNLength(); j++) {
				if (str.contains(coder1.getCharacter(i)) && str.contains(coder1.getCharacterN(j))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 *
	 * Checks if the String contains numbers and letters.
	 *
	 * @param str String to be check.
	 * @return true if contains numbers and letters, false otherwise.
	 * @author regrettabledanger10.
	 */
	public static boolean isMixed(final String str) {
		for (i = 0; i < str.length() - 1; i++) {
			if (!Character.isDigit(str.charAt(i)) && Character.isDigit(str.charAt(i + 1))
					|| Character.isDigit(str.charAt(i)) && !Character.isDigit(str.charAt(i + 1))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 *
	 * Checks if the String is a words.
	 *
	 * @param str the String to be check.
	 * @return true if all is a letter, false otherwise.
	 * @author regrettabledanger10.
	 */
	public static boolean isWord(final String str) {
		for (i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 *
	 * Checks if the String is a number.
	 *
	 * @param Number the String to be check.
	 * @return true if all is a number, false otherwise.
	 * @author regrettabledanger10.
	 */
	public static boolean isNumber(final String Number) {
		for (i = 0; i < Number.length(); i++) {
			if (!Character.isDigit(Number.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
