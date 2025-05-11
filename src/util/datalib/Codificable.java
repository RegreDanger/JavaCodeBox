package util.datalib;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import util.datastructures.BidirectionalMap;

public interface Codificable {
	String SEPARATOR = "|";
	
	BidirectionalMap<Character, String> CHARACTER_CODING = new BidirectionalMap<>(Stream.of(
			new Object[][] {
				{ 'a', "-" }, { 'b', "-=" },
				{ 'c', "-==" }, { 'd', "--==" },
				{ 'e', "--===" }, { 'f', "---===" },
				{ 'g', "---====" }, { 'h', "----====" },
				{ 'i', "----=====" }, { 'j', "_" },
				{ 'k', "_-" }, { 'l', "_-=" },
				{ 'm', "_-==" }, { 'n', "_--==" },
				{ 'ñ', "_--===" }, { 'o', "_---===" },
				{ 'p', "_---====" }, { 'q', "_----====" },
				{ 'r', "_----=====" }, { 's', "__" },
				{ 't', "__-" }, { 'u', "__-=" },
				{ 'v', "__-==" }, { 'w', "__--==" },
				{ 'x', "__--===" }, { 'y', "__---===" },
				{ 'z', "__---====" }, { ' ', "__----====" },
				{ ':', "__----=====" }
			}
			).collect(Collectors.toMap(c -> (char) c[0], i -> (String) i[1])));
	
	BidirectionalMap<Integer, String> NUMBER_CODING = new BidirectionalMap<>(Stream.of(
			new Object[][] {
				{1, "="},
				{2, ".=."},
				{3, ".==="},
				{4, "=.=="},
				{5, ".=.=."},
				{6, "..===."},
				{7, "..===..="},
				{8, "..=..==."},
				{9, ".=..=...="},
				{0, "..=...=..=."}
			}
			).collect(Collectors.toMap(i -> (int) i[0], c -> (String) c[1])));
	
	
	public String getSimpleCharEncoded(char ch);
	
	public String getSimpleNumberEncoded(int number);
	
	public char getSimpleCharDecoded(String code);
	
	public int getSimpleNumberDecoded(String code);
	
	public boolean containsChar(char ch);
	
	public boolean containsNumber(int number);
	
	public boolean containsCharCode(String code);
	
	public boolean containsNumberCode(String code);
	
	public String getSimpleEncoded(char ch);
	
	public String getSimpleDecoded(String code);
	
}
