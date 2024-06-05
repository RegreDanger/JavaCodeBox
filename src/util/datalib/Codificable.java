package util.datalib;

public interface Codificable {
	
	String[] code = { "-1-", "-2-", "-3-", "-4-", "-5-", "-6-", "-7-", "-8-", "-9-", "-11-", "-12-",
			"-13-", "-14-", "-15-", "-16-", "-17-", "-18-", "-19-", "-21-", "-22-", "-23-", "-24-", "-25-", "-26-",
			"-27-", "-28-", "-29-", "-30-", "-31-" };
	String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o",
				"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", " ", ":" };
	String[] decode = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "11", "12", "13", "14", "15", "16",
			"17", "18", "19", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" };
	String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
	String[] decodeN = { "=", ".=.", ".===", "=.==", ".=.=.", "..===.", "..===..=", "..=..==.",
			".=..=...=", "..=...=..=." };
	String[] codeN = { "=_", "_.=._", "_.===_", "_=.==_", "_.=.=._", "_..===._", "_..===..=_",
			"_..=..==._", "_.=..=...=_", "_..=...=..=._" };
	
	public String getCharacter(short position);
	
	public String getCharacterN(short position);
	
	public String getCharacterDecoded(short position);
	
	public String getCharacterNDecoded(short position);
	
	public String getLetter(short position);
	
	public String getNumber(short position);
	
	public short getNumbersLength();
	
	public short getLettersLength();
	
	public short getCodeLength();
	
	public short getCodeNLength();
	
	public short getDecodeLength();
	
	public short getDecodeNLength();
}
