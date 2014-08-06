package com.mk.framework.grid.util;
/**
 * 
 * @author zhangyu
 * @date 2007-9-4
 */
public class ConvertTools {
	public static int convertBoolean(Boolean value) {
		if (value.booleanValue())
			return 1;
		else
			return 0;
	}

	public static Boolean convertInt(int value) {
		if (value == 0)
			return Boolean.FALSE;
		else
			return Boolean.TRUE;
	}
}
