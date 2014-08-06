package com.mk.framework.utils;

import java.io.Serializable;
import java.net.InetAddress;

import com.mk.framework.grid.util.StringUtils;

/**
 * Created by IntelliJ IDEA. Date: 11-1-7 Time: 下午2:51
 */
public class UUIDGenerator {
	/**
	 * Description:生成形如‘2c9081261d50d1c4011d50d1c4320000’的32位uuid串
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		UUIDGenerator uuidGenerator = new UUIDGenerator();
		// System.out.println(uuidGenerator.generate().toString());
	}

	private static final int IP;

	public static int IptoInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	static {
		int ipadd;
		try {
			ipadd = IptoInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}
	private static short counter = (short) 0;
	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	public UUIDGenerator() {
	}

	/**
	 * Unique across JVMs on this machine (unless they load this class in the
	 * same quater second - very unlikely)
	 */
	protected int getJVM() {
		return JVM;
	}

	/**
	 * Unique in a millisecond for this JVM instance (unless there are >
	 * Short.MAX_VALUE instances created in a millisecond)
	 */
	protected short getCount() {
		synchronized (UUIDGenerator.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}

	/**
	 * Unique in a local network
	 */
	protected int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 */
	protected short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	private final static String sep = "";

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	public Serializable generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep).append(format(getJVM())).append(sep).append(format(getHiTime())).append(sep).append(format(getLoTime())).append(sep)
				.append(format(getCount())).toString();
	}

	public static String randomUUID() {
		return new UUIDGenerator().generate().toString();
	}

	public static String format0000_ID(String pid, String maxid, int step) {
		StringBuffer id = new StringBuffer();
		int count = 0;
		if (StringUtils.isEmpty(pid)) {
			count = (maxid == null ? 1 : Integer.parseInt(maxid) + step);
		} else {
			if (maxid == null)
				count = 1;
			else {
				String tmpId = maxid.substring(maxid.length() - 5, maxid.length());
				count = Integer.parseInt(tmpId) + step;
			}
		}
		if (count <= 0)
			id.append("00001");
		else if (count >= 0 && count <= 9)
			id.append("0000").append(count);
		else if (count > 9 && count <= 99)
			id.append("000").append(count);
		else if(count > 99 && count <= 999)
			id.append("00").append(count);
		else if(count > 999 && count <= 9999)
			id.append("0").append(count);
		else if(count > 9999 && count<=99999)
			id.append(count);
		if (pid != null && !pid.equals(""))
			return pid + id.toString();
		return id.toString();
	}
}
