package util;

public class PrimitiveTypesArrayWrapper {
	
	private PrimitiveTypesArrayWrapper() {}
	
	public static final Boolean[] wrap(boolean[] src) {
		Boolean[] wrapBol = new Boolean[src.length];
		for(int i = 0; i < src.length; i++) {
			wrapBol[i] = src[i];
		}
		return wrapBol;
	}
	
	public static final Byte[] wrap(byte[] src) {
		Byte[] wrapByte = new Byte[src.length];
		for(int i = 0; i < src.length; i++) {
			wrapByte[i] = src[i];
		}
		return wrapByte;
	}
	
	public static final Short[] wrap(short[] src) {
		Short[] wrapShort = new Short[src.length];
		for(int i = 0; i < src.length; i++) {
			wrapShort[i] = src[i];
		}
		return wrapShort;
	}
	
	public static final Integer[] wrap(int[] src) {
		Integer[] wrapInteger = new Integer[src.length];
		for(int i = 0; i < src.length; i++) {
			wrapInteger[i] = src[i];
		}
		return wrapInteger;
	}
	
	public static final Long[] wrap(long[] src) {
		Long[] wrapLong = new Long[src.length];
		for(int i = 0; i < src.length; i++) {
			wrapLong[i] = src[i];
		}
		return wrapLong;
	}
	
	public static final Float[] wrap(float[] src) {
		Float[] wrapFloat = new Float[src.length];
		for(int i = 0; i < src.length; i++) {
			wrapFloat[i] = src[i];
		}
		return wrapFloat;
	}
	
	public static final Double[] wrap(double[] src) {
		Double[] wrapDouble = new Double[src.length];
		for(int i = 0; i < src.length; i++) {
			wrapDouble[i] = src[i];
		}
		return wrapDouble;
	}
	
	public static final Character[] wrap(char[] src) {
		Character[] wrapChar = new Character[src.length];
		for(int i = 0; i < src.length; i++) {
			wrapChar[i] = src[i];
		}
		return wrapChar;
	}
	
}
