package com.mk.framework.grid.util;


public class BytesUtils {

	public static final int BYTE_LEN = 1;
	public static final int SHORT_LEN = 2;
	public static final int INT_LEN = 4;
	public static final int FLOAT_LEN = 4;
	public static final int LONG_LEN = 8;
	public static final int DOUBLE_LEN = 8;
  
	
	public static byte[] shortToBytes(short num) {
		byte[] bytes=new byte[SHORT_LEN];
		int startIndex=0;
		bytes[startIndex] = (byte) (num & 0xff);
		bytes[startIndex + 1] = (byte) ((num >> 8) & 0xff);
		return bytes;
	}
		
	public static byte[] intToBytes(int num ) {
		byte[] bytes=new byte[INT_LEN];
		int startIndex=0;
		bytes[startIndex + 0] = (byte) (num & 0xff);
		bytes[startIndex + 1] = (byte) ((num >> 8) & 0xff);
		bytes[startIndex + 2] = (byte) ((num >> 16) & 0xff);
		bytes[startIndex + 3] = (byte) ((num >> 24) & 0xff);
		return bytes;
	}
	

	public static byte[] floatToBytes(float fnum) {
		return intToBytes(Float.floatToIntBits(fnum));
	}

	
	public static byte[] longToBytes(long lnum) {
		byte[] bytes=new byte[LONG_LEN];
		int startIndex=0;
		for (int i = 0; i < 8; i++)
			bytes[startIndex + i] = (byte) ((lnum >> (i * 8)) & 0xff);
		return bytes;
	}
	

	public static byte[] doubleToBytes(double dnum) {
		return longToBytes(Double.doubleToLongBits(dnum));
	}

}
