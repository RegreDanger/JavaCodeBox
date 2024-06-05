package util.filelib;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import util.datalib.Encrypt;

public class FileIOSerializer extends FileIOEnhanced implements Serializable {
	private static final long serialVersionUID = 316634624988267887L;
	private Object[] objs = null;
	private Object obj = null;
	private String pathFile = "";
	private short index;
	
	public FileIOSerializer(String pathFile, Object obj) {
		this.pathFile = pathFile;
		this.obj = obj;
	}
	
	public FileIOSerializer(String pathFile, Object[] objs, short index) {
		this.pathFile = pathFile;
		this.objs = objs;
		if (index < 0) {
			throw new UnsupportedOperationException(
					"The index must be 0 or higher than 0 and less than the length of the array");
		}
		this.index = index;
	}
	
	public void setPathFile(String pathFile) {
		if (pathFile == null) {
			return;
		}
		this.pathFile = pathFile;
	}
	
	public String getPathFile() {
		return pathFile;
	}
	
	public void setObject(Object obj) {
		if (objs != null) {
			throw new UnsupportedOperationException("The object cannot be modified when use the constructor of array");
		}
		this.obj = obj;
	}
	
	public Object getObject() {
		return obj;
	}
	
	public void setArray(Object[] objs) {
		if (obj != null) {
			throw new UnsupportedOperationException("The array cannot be modified when use the constructor of object");
		}
		this.objs = objs;
	}
	
	public Object[] getArray() {
		return objs;
	}
	
	public void setIndex(short index) {
		if (index < 0 || index > objs.length) {
			return;
		}
		this.index = index;
	}
	
	public short getIndex() {
		return index;
	}
	
	public Map<Short, Map<String, Object>> mappingObjects() {
		if (objs != null) {
			Map<Short, Map<String, Object>> objectMap = new HashMap<>();
			for (short index = 0; index < objs.length; index++) {
				Map<String, Object> innerMap = new HashMap<>();
				innerMap.put(objs[index].getClass().getSimpleName(), objs[index]);
				objectMap.put(index, innerMap);
			}
			return objectMap;
		}
		return null;
	}
	
	public void serialize() {
		try (FileOutputStream streamOut = new FileOutputStream(pathFile);
				ObjectOutputStream objOut = new ObjectOutputStream(streamOut)) {
			byte[] encryptedData = Encrypt.encryptData(objs != null ? objs : obj);
            objOut.writeObject(encryptedData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object deserialize() {
		try (FileInputStream streamIn = new FileInputStream(pathFile);
				ObjectInputStream objIn = new ObjectInputStream(streamIn);) {
            byte[] encryptedData = (byte[]) objIn.readObject();
			if (obj != null) {
				return (Object) Encrypt.decryptData(encryptedData);
			}
			if (objs != null) {
				Object[] objects = (Object[]) Encrypt.decryptData(encryptedData);
					return objects[index];
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString() {
		if(objs != null) {
			String contain = "";
			for(Map.Entry<Short, Map<String, Object>> entry : mappingObjects().entrySet()) {
				short index = entry.getKey();
				Map<String, Object> innerMap = entry.getValue();
				for(Map.Entry<String, Object> innerEntry : innerMap.entrySet()) {
					String classString = innerEntry.getKey();
					Object obj = innerEntry.getValue();
					contain = contain.concat("Index Array: " + index + "\nClass: " + classString + "\nObject/inforation:\n" + obj.toString() + "\n\n");
				}
			}
			return contain;
		}
		if(obj != null) {
			return obj.toString();
		}
		return null;
	}
	
}