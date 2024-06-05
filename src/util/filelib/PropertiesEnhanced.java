package util.filelib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * A class that handles properties..
 *
 * @author regrettabledanger10
 */
public class PropertiesEnhanced extends Properties {
	private static final long serialVersionUID = 3009095207177895044L;
	private String path;
	private FileInputStream FIS;
	private FileOutputStream FOS;

	protected PropertiesEnhanced() {}
	
	public PropertiesEnhanced(InputStream inStream, String path) {
		super();
		isProperties(path);
		this.path = path;
		try {
			load(inStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a Properties, use this if the properties is inside of the jar.
	 * 
	 * @param path the path.
	 * @param mainClazz the main class to get the resource Properties.
	 */
	public PropertiesEnhanced(String path, Class<?> mainClazz) {
		super();
		isProperties(path);
		this.path = path;
		try {
			load(mainClazz.getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PropertiesEnhanced(String path) throws IOException {
		super();
		isProperties(path);
		this.path = path;
		try {
			FIS = new FileInputStream(path);
		} catch(FileNotFoundException e) {
			new File(path).createNewFile();
			FIS = new FileInputStream(path);
		}
		load(FIS);
	}
	
	private void isProperties(String path) {
		if(!path.endsWith(".properties")) {
			throw new UnsupportedOperationException("The file is not properties!");
		}
	}
	
	public boolean containsAll(String[] keys) {
		for(String key : keys) {
			if(!containsKey(key)) {
				return false;
			}
		}
		return true;
	}

	public void setPath(String newPath) {
		if(!newPath.endsWith(".properties")) {
			throw new UnsupportedOperationException("The file is not properties!");
		}
		path = newPath;
	}

	public String getPath() {
		return path;
	}
	
	public static Map<String, String> getKVProperties(Properties[] properties){
		Map<String, String> map = new HashMap<>();
		for(Properties current : properties) {
			for(String propertyName : current.stringPropertyNames()) {
				String propertyValue = current.getProperty(propertyName);
				map.put(propertyName, propertyValue);
			}
		}
		return map;
	}
	
	/**
	 * Writes the hashMap in the file properties, the first argument is the key and the second argument is the value.
	 * <p>The map should not contain null values or keys.
	 *
	 * @param map
	 * @throws IOException
	 */
	@Override
	public synchronized void putAll(Map<?, ?> map) {
		if(map.containsKey(null) || map.containsValue(null)) {
			throw new UnsupportedOperationException("Something key or value are null");
		}
		super.putAll(map);
		try {
			save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Something it's wrong! See the log in the folder Logs");
			FileIOEnhanced.createLog(e, null);
		}
	}

	public void overrideProperties(Properties newProperties) throws IOException {
		clear();
		super.putAll(newProperties);
		save();
	}

	public void mergeProperties(Properties otherProperties) throws IOException {
		super.putAll(otherProperties);
		save();
	}

	public void save() throws IOException {
		FOS = new FileOutputStream(path);
		store(FOS, null);
		FOS.close();
	}

	public Properties getPropertiesByCategory(String category) {
		Properties categoryProperties = new Properties();
		for(String propertyName : stringPropertyNames()) {
			if(propertyName.startsWith(category + ".")) {
				String categoryPropertyName = propertyName.substring(category.length()+1);
				String propertyValue = getProperty(propertyName);
				categoryProperties.setProperty(categoryPropertyName, propertyValue);
			}
		}
		return categoryProperties;
	}

}
