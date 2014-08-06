package com.mk.framework.grid.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FilterUtil {

	public static void convertFH(String filter, Object obj) throws Exception {
		String[] attrs = filter.split("\\|");
		if (attrs.length == 3 && !attrs[2].equals("null")) {
			String column = attrs[0];
			String fh = attrs[1];
			String value = attrs[2];

			Class ownerClass = obj.getClass();
			Field[] fieds = ownerClass.getDeclaredFields();
			Field fied = null;
			Object[] args = null;

			for (int i = 0; i < fieds.length; i++) {
				fied = fieds[i];
				if (fied.getName().equals(column)) {
					args = new Object[] { value };
					// 执行set方法
					invokeMethod(obj, "set" + StringUtils.toLowerCaseInitial(fied.getName(), false), args);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
		Class ownerClass = owner.getClass();
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}

}
