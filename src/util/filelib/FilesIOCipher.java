package util.filelib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import util.datalib.Coder;
import util.datalib.Encrypt;

public class FilesIOCipher extends FileIOEnhanced {
	
	/**
	 * Saves an archive with text encrypt in SHA-256.
	 * 
	 * @param lines Array of string to write encrypt, each position of the Array is equivalent to each line.
	 * @param dir   the direction of the file to write lines.
	 * @throws NoSuchAlgorithmException
	 */
	public static void setSaveEncrypt256(String[] lines, String dir) {
		write(dir, out -> {
			for (String line : lines) {
			out.println(Encrypt.getEncryptSHA256(line));
		}
		});
	}
	
	/**
	 *
	 * Save an file with text Encoded.
	 *
	 * @param lines            Array of string, each position of the Array is equivalent to each line.
	 * @param dir              the direction of the file to write the lines.
	 * @param dirRenameEncoded when Encoded the text, hide the file original and generate a file with new extension and 0 lines text.
	 * @author regrettabledanger10.
	 */
	public static void setSaveEncoded(final String[] lines, final String dir, final String dirRenameEncoded) {
		File rename = new File(dirRenameEncoded);
		File origin = new File(dir);
		if (origin.isHidden()) {
			setHiddenFile(origin.toPath(), false);
		}
		write(dir, out -> {
			for (String line : lines) {
				out.println(Base64.getEncoder().encodeToString(Coder.getEncode(line).getBytes()));
			}
		});
		setFileRenameExtension(dir, dirRenameEncoded);
		if (rename.exists()) {
			rename.delete();
			createFile(dirRenameEncoded);
		}
		setHiddenFile(origin.toPath(), true);
	}
	
	/*
	 * Coded = Codificador.getEncode(lines[i]); Coded = Base64.getEncoder().encodeToString(Coded.getBytes()); out.println(Coded);
	 */
	
	/**
	 *
	 * Load each line of the file, decoding everything and hide the original file.
	 *
	 * @param dir              the direction of the file to decode the lines.
	 * @param dirRenameEncoded when decode the text, hide the file original and generate a file with new extension and 0 lines text.
	 * @return an Array String of the lines.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static String[] getLoadEncoded(final String dir, final String dirRenameEncoded) throws FileNotFoundException {
		File origin = new File(dir);
		File rename = new File(dirRenameEncoded);
		setHiddenFile(origin.toPath(), false);
		if (rename.exists()) {
			rename.delete();
			createFile(dirRenameEncoded);
		} else {
			createFile(dirRenameEncoded);
		}
		String[] linesRead = read(dir);
		ArrayList<String> decoded = new ArrayList<String>();
		for (String line : linesRead) {
			decoded.add(Coder.getDecoded(new String(Base64.getDecoder().decode(line))));
		}
		setHiddenFile(origin.toPath(), true);
		return decoded.toArray(new String[0]);
	}
	
	/**
	 *
	 * Save an file with text Encoded
	 *
	 * @param lines Array String, each position of the array is equivalent to each line.
	 * @param dir   the direction of the line to write the lines.
	 * @throws IOException
	 * @author regrettabledanger10.
	 */
	public static void setSaveEncoded(final String[] lines, final String dir) {
		write(dir, out -> {
			for (String line : lines) {
				out.println(Base64.getEncoder().encodeToString(Coder.getEncode(line).getBytes()));
			}
		});
		
	}
	
	/**
	 *
	 * Load the file with text decoded.
	 *
	 * @param dir the direction of the file to decoding the lines.
	 * @return an Array String with the lines.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static String[] getLoadEncoded(final String dir) throws FileNotFoundException {
		String[] linesRead = read(dir);
		ArrayList<String> decoded = new ArrayList<String>();
		for (String line : linesRead) {
			decoded.add(Coder.getDecoded(new String(Base64.getDecoder().decode(line))));
		}
		return decoded.toArray(new String[0]);
	}
	
	/**
	 *
	 * Write lines of text coded without deleting previously entered lines coded.
	 *
	 * @param dir   the direction with the file.
	 * @param lines the Array String of the lines to write, each position of the array is equivalent to each line on the archive.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static void ToRewriteEncoded(final String dir, final String[] lines) throws FileNotFoundException {
		final String[] linesSaved = getLoadEncoded(dir);
		write(dir, out -> {
			for (String savedLine : linesSaved) {
				out.println(Base64.getEncoder().encodeToString(Coder.getEncode(savedLine).getBytes()));
			}
			for (String line : lines) {
				out.println(Base64.getEncoder().encodeToString(Coder.getEncode(line).getBytes()));
			}
		});
	}
	
	/**
	 *
	 * Remove from final of the file to the number of the line, of an file with text coded.
	 *
	 * @param dir  the direction with the file.
	 * @param line the number of the line to end the delete.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static void setDeleteFromFinalEncoded(final String dir, short line) throws FileNotFoundException {
		String[] linesSaved = getLoadEncoded(dir);
		final short finalLine = line <= 0 ? 0 : line--;
		write(dir, out -> {
			for (short currentLine = 0; currentLine < finalLine; currentLine++) {
				out.println(Base64.getEncoder().encodeToString(Coder.getEncode(linesSaved[currentLine]).getBytes()));
			}
		});
	}
	
	/**
	 *
	 * Remove from the start line of the file to the number of the line, of an archive with text coded.
	 *
	 * @param dir  the direction with the file.
	 * @param line the number of the line to end the delete.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static void setDeleteFromStartEncoded(final String dir, short line) throws FileNotFoundException {
		String[] linesSaved = getLoadEncoded(dir);
		final short startLine = (short) (line > linesSaved.length + 1 ? linesSaved.length-1 : line--);
		write(dir, out -> {
			for (short printLine = startLine; printLine < linesSaved.length; printLine++) {
				out.println(Base64.getEncoder().encodeToString(Coder.getEncode(linesSaved[printLine]).getBytes()));
			}
		});
	}
	
	/**
	 *
	 * Remove lines of the file with text encoded, between the initial line number and the final line number.
	 *
	 * @param dir     the direction with the file.
	 * @param initial the number of the line to start to delete.
	 * @param end     the number of the line to end the delete.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static void setDeleteFromToEncoded(final String dir, short initial, short end) throws FileNotFoundException {
		String[] linesSaved = getLoadEncoded(dir);
		final short lineInitial = initial <= 1 ? 0 : initial;
		final short lineEnd = end > linesSaved.length + 1 ? (short) (linesSaved.length-1) : end--;
		write(dir, out -> {
			for(short currentLine = 0; currentLine < linesSaved.length; currentLine++) {
			if(!(currentLine > lineInitial && currentLine < lineEnd)) {
				out.println(Base64.getEncoder().encodeToString(Coder.getEncode(linesSaved[currentLine]).getBytes()));
			}
		}
		});
	}
	
	/**
	 *
	 * Remove the specific line of an file with text encoded.
	 *
	 * @param dir  the direction with the file.
	 * @param line the number of the line to delete.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static void setDeleteSpecificEncoded(final String dir, short line) throws FileNotFoundException {
		String[] linesSaved = getLoadEncoded(dir);
		final short deleteLine = (short) (line <= 1 ? 0 : line - 1);
		write(dir, out -> {
			for (short currentLine = 0; currentLine < linesSaved.length; currentLine++) {
				if (currentLine != deleteLine) {
					out.println(Base64.getEncoder().encodeToString(Coder.getEncode(linesSaved[currentLine]).getBytes()));
				}
			}
		});
	}
}
