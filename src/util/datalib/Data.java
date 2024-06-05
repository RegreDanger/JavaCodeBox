package util.datalib;

import java.io.File;
import java.io.Serializable;
import java.security.SecureRandom;
import java.text.DecimalFormat;

public class Data implements Serializable {
	private static final long serialVersionUID = 1787691802177029759L;
	private static final int DATA_SIZE = 1024;
	private static int positionTag = 0;
	private static final String[] TAGS = new String[] {"B", "Kb", "Mb", "Gb", "Tb", "Pb"};
	private static String sizeDataString;
	private byte[] data;
	
	protected Data() {}
	
	public Data(byte[] data) {
		this.data = data;
	}
	
	public void setData(byte[] newDataProcess) {
		if(!newDataProcess.equals(data)) {
			data = newDataProcess;
		}
	}
	
	public byte[] getData() {
		return this.data;
	}
	
	public long getSize() {
		if(data != null) {
			return data.length;
		}
		return 0;
	}
	
	public static String getStringSize(long dataSize) {
		return calculate(dataSize);
	}
	
	public static String getStringSize(byte[][] data) {
		long dataSize = 0;
		for(byte[] row : data) {
			dataSize += row.length;
		}
		return calculate(dataSize);
	}
	
	public static String getStringSize(File[] files) {
		long dataSize = 0;
		for(File file : files) {
			dataSize += file.length();
		}
		return calculate(dataSize);
	}
	
	private static String calculate(double dataSize) {
		if(dataSize >= DATA_SIZE) {
			dataSize = dataSize / DATA_SIZE;
			positionTag++;
			return calculate(dataSize);
		} else {
			DecimalFormat format = new DecimalFormat("0.00");
			sizeDataString = format.format(dataSize) + TAGS[positionTag];
			positionTag = 0;
			return sizeDataString;
		}
	}
	
	public static byte[] generateRandomBytes(int bound) {
		SecureRandom randomizer = new SecureRandom();
		byte[] bytesRandom = new byte[randomizer.nextInt(randomizer.nextInt(bound))];
		randomizer.nextBytes(bytesRandom);
		return bytesRandom;
	}
	
	public static byte[] generateRandomBytes(byte[] toFill) {
		SecureRandom randomizer = new SecureRandom();
		randomizer.nextBytes(toFill);
		return toFill;
	}
}
