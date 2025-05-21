package util.filelib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import util.exceptions.GetValueException;
import util.exceptions.NoFileMetadataException;

/**
 * 
 * Class for management of files, path and more.
 * 
 * @author regrettabledanger10
 */
public class FileIOEnhanced {	
	//The constants
	private static final String NAME_FOLDER_LOGS = "Logs";
	private static final String SEPARATOR = "________________________________________";
	
	//The vars
	private static ArrayList<String> fileList = new ArrayList<String>();
	
	/**
	 * 
	 * Write the first lines in the log file.
	 * 
	 * @param lines the lines, each index of the Array is a line.
	 * @param printWriter the print.
	 */
	private static void writeFirstPartLog(String[] lines, PrintWriter printWriter) {
		if (lines != null) {
			for (String line : lines) {
				printWriter.println(line);
			}
			printWriter.println();
		}
	}
	
	/**
	 * 
	 * Write the StackTrace in the log file.
	 * 
	 * @param e the Exception.
	 * @param printWriter the print.
	 */
	private static void writeStackTrace(Exception e, PrintWriter printWriter) {
		e.printStackTrace(printWriter);
			printWriter.println("\nMessage: " + e.getMessage());
			StackTraceElement[] stackTrace = e.getStackTrace();
			if (stackTrace.length > 0) {
				StackTraceElement element = stackTrace[0];
				printWriter.println("Exception generated in the class: " + element.getClassName());
				printWriter.println("Line: " + element.getLineNumber());
			}
	}
	
	/**
	 * 
	 * Creates a log with the info of the exception.
	 * </p>Every log is stored in the folder Logs in the actual path.
	 * </p>The log contains: The lines of linesBeginning, the StackTrace of the Exception, the message of the Exception, the class where is generated the Exception,
	 * 					 the line where is the Exception, the Date and Hour, the version of Java, the version of JVM, the name of the Device and the IP Adress<p>
	 * 
	 * @param e the Exception.
	 * @param linesBeginning the lines of write at the begin of the log, each index is a line.
	 */
	public static void createLog(Exception e, String[] linesBeginning) {
		if(!new File(NAME_FOLDER_LOGS).exists()) {
			createFolder("", NAME_FOLDER_LOGS);
		}
		final String pathFile = "Logs/log " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replaceAll(":", ".") + ".txt";
		write(pathFile, out -> {
			writeFirstPartLog(linesBeginning, out);
			writeStackTrace(e, out);
			out.println(SEPARATOR);
			out.println("Date & hour: " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			out.println("Version of Java: " + System.getProperty("java.version"));
			out.println("Version of JVM: " + System.getProperty("java.vm.version"));
			try {
				out.println("IP Address: " + java.net.InetAddress.getLocalHost().getHostAddress());
				out.println("Name of the device: " + InetAddress.getLocalHost().getHostName());
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			out.println(SEPARATOR);
			out.println();
		});
	}
	
	/**
	 * 
	 * Checks the metadata of the Jar.
	 * </p>A folder must be created with name "metadata_nameOfClass" (name of Class with the main method) and a metadata.properties file should be created in the folder with the corresponding key-value.<p>
	 * </p>Example:<p>
	 * <code>public class check {
	 * </p> //Lines of code<p>
	 * </p>}<p></code>
	 * 
	 * </p> So, the folder will be named: <code>metadata_check</code><p>
	 * 
	 * @param classMain the Main class of the Jar.
	 * @return true if the value of the key NameJar equals with the name of the Jar, false otherwise.
	 * @throws NoFileMetadataException if the metadata file doesn't exists.
	 * @throws IOException if an error occurred when reading from theinput stream.
	 * @throws GetValueException if the value for the key NameJar is null.
	 * @apiNote use only in Jar testings.
	 * @author regrettabledanger10.
	 */
	public static boolean checkMetadata(Class<?> classMain) throws NoFileMetadataException, IOException, GetValueException {
		try {
			InputStream metadataStream = classMain.getResourceAsStream("/" + "metadata_" + classMain.getSimpleName() + "/metadata.properties");
            if (metadataStream != null) {
                Properties metadata = new Properties();
                metadata.load(metadataStream);
                String nameJar = metadata.getProperty("NameJar");
                if (nameJar != null) {
                    String[] files = getFilesFromFolder(getDir(classMain, true), ".jar");
                    for (String file : files) {
                        if (nameJar.equals(file)) {
                            return true;
                        }
                    }
                    return false;
                } else {
                    throw new GetValueException("The value for the key NameJar doesn't exist or is written incorrectly");
                }
            } else {
                throw new NoFileMetadataException("The metadata file doesn't exist or has been removed");
            }
		} catch (NoFileMetadataException | GetValueException e) {
            createLog(e, null);
            throw e;
        } catch (IOException e) {
            createLog(e, null);
            throw e;
        }
	}
	
	/**
	 *
	 * Creates a file.
	 *
	 * @param path the path with the file to create.
	 * @return the file.
	 * @author regrettabledanger10.
	 */
	public static File createFile(final String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (final IOException e) {
			}
		}
		return file;
	}
	
	/**
	 * 
	 * Creates a new folder, if exist, rename the folder to FolderName_2, and if continue exist, the number increases in 1.
	 * 
	 * @param path        the path to create the folder, if is null or "" is actual path.
	 * @param folderName the name of the folder to create;
	 * @author regrettabledanger10.
	 */
	public static void createFolder(String path, String folderName) {
		if (path == null || path.isBlank()) {
			path = folderName;
		} else {
			folderName = !folderName.contains("\\") ? "\\" + folderName : folderName;
			path = path + folderName;
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		} else {
			short num = 1;
			String originalDir = path;
			file = new File(path);
			do {
				path = originalDir + "_" + num;
				file = new File(path);
			} while (file.exists());
			file.mkdirs();
		}
	}
	
	/**
	 * 
	 * Crates a new folder on the location of the JAR, if exist, rename the folder to FolderName_2, and if continue exist, the number increases in 1
	 * 
	 * @param classPathMAIN main class to get the path of the Jar.
	 * @param folderName the name of the folder.
	 * @param formatJar if is true, returns the path without %20 for Jar.
	 */
	public static void createFolderInJarLocation(Class<?> classPathMAIN, String folderName, boolean formatJar) {
		String path = classPathMAIN.getProtectionDomain().getCodeSource().getLocation().getPath();
		path = formatJar ? path.replace("%20", " ") : path;
		folderName = !folderName.startsWith("\\") && !(path.endsWith("\\")) ? "\\" + folderName : folderName;
		createFolder(path, folderName);
	}
	
	/**
	 * 
	 * Get all files of something folder
	 * 
	 * @param path the path of the folder.
	 * @return a Array String with the files.
	 * @author regrettabledanger10.
	 */
	public static String[] getAllFiles(final String path) {
		File pathectory = new File(path);
		String[] files = pathectory.list();
		return files;
	}
	
	/**
	 * 
	 * Gets all files from the folder and its subfolders.
	 * 
	 * @param path the path of the folder and its subfolders.
	 * @return a Array String of the paths of files.
	 */
	public static String[] getAllFilesSubfolders(final String path) {
		fileList.clear();
		return search(path);
	}
	
	/**
	 * Gets the path of the class.
	 * 
	 * @param classPath the class to be located.
	 * @param formatJar if is true, returns the path without %20 for Jar.
	 * @return the pathection of the class
	 * @throws IOException 
	 */
	public static String getDir(Class<?> classPath, boolean formatJar) {
		String path = "";
		try {
			path = new File(classPath.getProtectionDomain().getCodeSource().getLocation().getPath()).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formatJar ? path.replaceAll("%20", " ") : path;
	}
	
	/**
	 * 
	 * Constructs a path with the folder in the Jar location.
	 * 
	 * @param classPathMain main class to get the path of the Jar.
	 * @param folderName the name of the folder.
	 * @param formatJar if is true, returns the path without %20 for Jar.
	 * @return the path with the folder in the Jar location.
	 * @throws IOException
	 */
	public static String getDirFolderInJarLocation(Class<?> classPathMain, String folderName, boolean formatJar) {
		String path = getDir(classPathMain, formatJar);
		folderName = !(folderName.startsWith("\\") || folderName.startsWith("/")) && !(path.endsWith("\\") || path.endsWith("/")) ? "\\" + folderName : folderName;
		return path + folderName;
	}
	
	/**
	 * 
	 * Get files files of something folder by the extension.
	 * 
	 * @param path the path of the folder.
	 * @param extension the extention for filter.
	 * @return a Array String of the files by extension.
	 * @author regrettabledanger10.
	 */
	public static String[] getFilesFromFolder(final String path, final String extension) {
		File pathectory = new File(path);
		ArrayList<String> filesFilter = new ArrayList<String>();
		String[] files = pathectory.list();
		for (String file : files) {
			if (file.contains(extension)) {
				filesFilter.add(file);
			}
		}
		filesFilter.trimToSize();
		return filesFilter.toArray(new String[0]);
	}
	
	/**
	 * 
	 * Get files of something folder filter by the extensions.
	 * 
	 * @param path the path of the folder.
	 * @param extensions the extensions for filter.
	 * @return and Array String two-dimensional; the first dimension contains the extensions and the second contains the files.
	 * @author regrettabledanger10.
	 */
	public static String[][] getFilesFromFolder(final String path, final String... extensions) {
		String[] Allfiles = getAllFiles(path);
		String[][] filesPerExtensions = new String[extensions.length][];
		String[] toArrayString = new String[0];
		ArrayList<String> listFilesExtension;
		for (short positionFirstDimension = 0; positionFirstDimension < extensions.length; positionFirstDimension++) {
			listFilesExtension = new ArrayList<String>(Allfiles.length);
			for (String file : Allfiles) {
				if (file.endsWith(extensions[positionFirstDimension])) {
					listFilesExtension.add(extensions[positionFirstDimension] + ": " + file);
				}
			}
			listFilesExtension.trimToSize();
			toArrayString = new String[listFilesExtension.size()];
			listFilesExtension.toArray(toArrayString);
			filesPerExtensions[positionFirstDimension] = toArrayString;
		}
		return filesPerExtensions;
	}
	
	/**
	 * 
	 * Gets the name of the jar from where the class is executed.
	 * 
	 * @param classPathMain the main class.
	 * @param extension if is true, return the name of the jar with the extensions, if is false, return the name without the extension.
	 * @return the name of the jar from where the class is executed, or null is jar doesn't exist or doesn't equals the metadata with the Jar.
	 * @author regrettabledanger10
	 */
	public static String getNameJar(Class<?> classPathMain, boolean extension) {
		try {
			if(checkMetadata(classPathMain)) {
				Properties metadata = new Properties();
				metadata.load(classPathMain.getResourceAsStream("/" + "metadata_" + classPathMain.getSimpleName() + "/metadata.properties"));
				return extension ? metadata.getProperty("NameJar") : metadata.getProperty("NameJar").replaceAll(".jar", "");
				
			} else {
				return null;
			}
		} catch (NoFileMetadataException | IOException | GetValueException e) {
			// TODO Auto-generated catch block
			createLog(e, new String[] {"Cannot get name Jar"});
			System.err.print("Error displaying name, check the logs in the folder logs");
			System.exit(-1);
		}
		return null;
	}
	
	/**
	 * 
	 * Get all subfolders from a folder
	 * 
	 * @param path the path of the folder.
	 * @return a Array String with the paths of subfolders.
	 */
	public static String[] getSubfolders(String path) {
		File folder = new File(path);
		String[] subpathectories = folder.list(new FilenameFilter() {
			@Override
			public boolean accept(File path, String name) {
				// TODO Auto-generated method stub
				return new File(path, name).isDirectory();
			}
		});
		return subpathectories;
	}
	
	/**
	 *
	 * Read the file line by line.
	 *
	 * @param path the path with the file.
	 * @return an Array String of the text, each position of the Array is equivalent to each line.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static String[] read(final String path) throws FileNotFoundException {
		File fileToRead = new File(path);
		ArrayList<String> lines = new ArrayList<String>();
		Scanner reader = new Scanner(fileToRead);
		while (reader.hasNextLine()) {
			lines.add(reader.nextLine());
		}
		reader.close();
		return lines.toArray(new String[0]);
	}
	
	/**
	 *
	 * Write lines of text without deleting previously entered lines.
	 *
	 * @param path   the path with the file.
	 * @param lines the Array String of the lines to write, each position of the array is equivalent to each line on the archive.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static void rewrite(final String path, final String[] lines) throws FileNotFoundException {
		String[] saveLines = read(path);
		write(path, out -> {
			for (short saveLine = 0; saveLine < saveLines.length; saveLine++) {
				out.println(saveLines[saveLine]);
			}
			for (short line = 0; line < lines.length; line++) {
				out.println(lines[line]);
			}
		});
	}
	
	/**
	 * 
	 * For search in the subfolders, a recursive search.
	 * 
	 * @param path the path of the folder or subfolders.
	 * @return a list of the paths the files.
	 */
	private static String[] search(String path) {
        File pathectory = new File(path);
		if (pathectory.exists()) {
			File[] files = pathectory.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isDirectory()) {
						search(file.getAbsolutePath());
					} else {
						fileList.add(file.getAbsolutePath());
					}
				}
			}
		}
		return fileList.toArray(new String[0]);
	}
	
	/**
	 * 
	 * Search the file in the project by given file name.
	 * 
	 * @param path the path of the project.
	 * @param fileName the name of the file.
	 * @return the path of the file, null if doesn't exist or not found.
	 */
	protected static String searchProject(String path, String fileName) {
		File projectDirectory = new File(path).getParentFile();
		String[] paths = getAllFilesSubfolders(projectDirectory.getAbsolutePath());
		if (paths != null) {
			for (String pathFile : paths) {
				if (pathFile.contains(fileName)) {
					return pathFile;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Search the file in the Jar by given file name.
	 * 
	 * @param classSearchMain the main class to checks the metadata.
	 * @param path the path of Jar.
	 * @param fileName the name of the file to search.
	 * @return the path of the file, null if doesn't exist or not found.
	 * @throws IOException
	 */
	protected static String searchInJar(Class<?> classSearchMain, String path, String fileName) throws IOException {
		InputStream metadataStream = classSearchMain.getResourceAsStream("/" + "metadata_" + classSearchMain.getName() + "/metadata.properties");
		Properties metadata = new Properties();
		metadata.load(metadataStream);
		JarFile jarFile = new JarFile(new File(path + "//" + metadata.getProperty("NameJar")));
		Enumeration<JarEntry> entries = jarFile.entries();
		jarFile.close();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (!entry.isDirectory()) {
				if (entry.getName().contains(fileName)) {
					return entry.getName();
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Search for a file in Jar Application or Proyect Folder.
	 * </p>To search in the Jar Application, checks the metadata.<p>
	 * 
	 * @param classSearchMain the main class to execute the search.
	 * @param fileName the name of the file to search, must be included the extension (and it's optional the folder for more precision).
	 * @param searchJar is is true, search in the Jar Application, if is false, search in the proyect.
	 * @return the path of the file (at the first coincidence), null if the file doesn't exist or did not find it.
	 * @author regrettabledanger10.
	 * @throws IOException 
	 * @throws GetValueException 
	 * @throws NoFileMetadataException 
	 */
	public static String searchInJarOrProyect(Class<?> classSearchMain, String fileName, boolean searchJar)
			throws IOException, NoFileMetadataException, GetValueException {
		String path = getDir(classSearchMain, true);
		if (searchJar && checkMetadata(classSearchMain)) {
			return searchInJar(classSearchMain, path, fileName);
		} else {
			return searchProject(path, fileName);
		}
	}
	
	/**
	 *
	 * Remove from final of the file to the number of the line.
	 *
	 * @param path  the path with the file.
	 * @param line the number of the line to end the delete.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static void setDeleteFromFinal(final String path, short line) throws FileNotFoundException {
		String[] linesSaved = read(path);
		final short finalLine = line <= 0 ? 0 : line--;
		write(path, out -> {
			for (short currentLine = 0; currentLine < finalLine; currentLine++) {
				out.println(linesSaved[currentLine]);
			}
		});
		
	}
	
	/**
	 *
	 * Remove from the start line of the file to the number of the line.
	 *
	 * @param path  the path with the file.
	 * @param line the number of the line to end the delete.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static void setDeleteFromStart(final String path, short line) throws FileNotFoundException {
		String[] linesSaved = read(path);
		final short startLine = (short) (line > linesSaved.length + 1 ? linesSaved.length-1 : line--);
		write(path, out -> {
			for (short printLine = startLine; printLine < linesSaved.length; printLine++) {
				out.println(linesSaved[printLine]);
			}
		});
	}
	
	/**
	 *
	 * Remove lines of the file, between the initial line number and the final line number.
	 *
	 * @param path     the path with the file.
	 * @param initial the number of the line to start to delete.
	 * @param end     the number of the line to end the delete.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static void setDeleteFromTo(final String path, short initial, short end) throws FileNotFoundException {
		String[] linesSaved = read(path);
		final short lineInitial = initial <= 1 ? 0 : initial;
		final short lineEnd = end > linesSaved.length + 1 ? (short) (linesSaved.length-1) : end--;
		write(path, out -> {
			for(short currentLine = 0; currentLine < linesSaved.length; currentLine++) {
			if(!(currentLine > lineInitial && currentLine < lineEnd)) {
				out.println(linesSaved[currentLine]);
			}
		}
		});
		
	}
	
	/**
	 *
	 * Remove the specific line of text of an file.
	 *
	 * @param path  the path with the file.
	 * @param line the number of the line to delete.
	 * @author regrettabledanger10.
	 * @throws FileNotFoundException 
	 */
	public static void setDeleteSpecific(final String path, short line) throws FileNotFoundException {
		String[] linesSaved = read(path);
		final short deleteLine = (short) (line <= 1 ? 0 : line - 1);
		write(path, out -> {
			for (short currentLine = 0; currentLine < linesSaved.length; currentLine++) {
				if (currentLine != deleteLine) {
					out.println(linesSaved[currentLine]);
				}
			}
		});
	}
	
	/**
	 *
	 * Rename a file extension, to create new files.
	 *
	 * @param pathOldExten the path of the file original with the extension.
	 * @param pathNewExten the path of the file with new extension.
	 * @return an file with new extension.
	 * @author regrettabledanger10.
	 */
	public static File setFileRenameExtension(final String pathOldExten, final String pathNewExten) {
		File file = new File(pathOldExten);
		File rename = new File(pathNewExten);
		file.renameTo(rename);
		return rename;
	}
	
	/**
	 *
	 * Hidden an File or not.
	 *
	 * @param pathFile the pathection with the file to hide.
	 * @param hidden  if is true, the file is hidden, if is false, the file is visible.
	 * @author regrettabledanger10.
	 */
	public static void setHiddenFile(final Path pathFile, final boolean hidden) {
		if (hidden) {
			try {
				Files.setAttribute(pathFile, "dos:hidden", true);
			} catch (final IOException | UnsupportedOperationException e) {
			}
		} else {
			try {
				Files.setAttribute(pathFile, "dos:hidden", false);
			} catch (final IOException | UnsupportedOperationException e) {
			}
		}
	}
	

	
	/**
	 * 
	 * To start the write in a file and close automatically.
	 * 
	 * @param path the path of the file to writer.
	 * @param action the action to perform with the code to write in the file.
	 */
	public static void write(String path, Consumer<PrintWriter> action) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(path, true))) {
	        action.accept(writer);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 *
	 * Writes in a file, and close automatically.
	 *
	 * @param path   the path with the file.
	 * @param lines an Array String, each position of the array is equivalent a each line wrote in the file.
	 * @author regrettabledanger10.
	 */
	public static void write(final String path, final String[] lines) {
		write(path, out -> {
			for (short line = 0; line < lines.length; line++) {
			out.println(lines[line]);
		}
		});
		
	}
}
