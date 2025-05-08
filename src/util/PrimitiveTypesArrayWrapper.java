package util;

public class PrimitiveTypesArrayWrapper {
	
	private PrimitiveTypesArrayWrapper() {}
	
	public static final Boolean[] wrap(boolean[] src) {
		Boolean[] wrapBol = new Boolean[src.length];
		for(int index = 0; index < src.length; index++) {
			wrapBol[index] = src[index];
		}
		return wrapBol;
	}
	
	public static final Byte[] wrap(byte[] src) {
		Byte[] wrapBol = new Byte[src.length];
		for(int index = 0; index < src.length; index++) {
			wrapBol[index] = src[index];
		}
		return wrapBol;
	}
	
	public static final Short[] wrap(short[] src) {
		Short[] wrapBol = new Short[src.length];
		for(int index = 0; index < src.length; index++) {
			wrapBol[index] = src[index];
		}
		return wrapBol;
	}
	
	public static final Integer[] wrap(int[] src) {
		Integer[] wrapBol = new Integer[src.length];
		for(int index = 0; index < src.length; index++) {
			wrapBol[index] = src[index];
		}
		return wrapBol;
	}
	
	public static final Long[] wrap(long[] src) {
		Long[] wrapBol = new Long[src.length];
		for(int index = 0; index < src.length; index++) {
			wrapBol[index] = src[index];
		}
		return wrapBol;
	}
	
	public static final Float[] wrap(float[] src) {
		Float[] wrapBol = new Float[src.length];
		for(int index = 0; index < src.length; index++) {
			wrapBol[index] = src[index];
		}
		return wrapBol;
	}
	
	public static final Double[] wrap(double[] src) {
		Double[] wrapBol = new Double[src.length];
		for(int index = 0; index < src.length; index++) {
			wrapBol[index] = src[index];
		}
		return wrapBol;
	}
	
	public static final Character[] wrap(char[] src) {
		Character[] wrapBol = new Character[src.length];
		for(int index = 0; index < src.length; index++) {
			wrapBol[index] = src[index];
		}
		return wrapBol;
	}
	
}
