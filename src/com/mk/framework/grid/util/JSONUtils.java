package com.mk.framework.grid.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mk.framework.grid.json.JSONArray;
import com.mk.framework.grid.json.JSONException;
import com.mk.framework.grid.json.JSONObject;

public class JSONUtils {
	public static JSONObject Bean2JSONObject(Object bean) {
		return Bean2JSONObject(bean, BeanUtils.getCacheGetterMethodInfo(bean.getClass()));
	}

	public static JSONObject Bean2JSONObject(Object bean, Object[] methodInfo) {
		JSONObject jsonObj = null;
		if (bean == null) {
			jsonObj = new JSONObject(bean);
		} else {
			jsonObj = new JSONObject(bean, (String[]) methodInfo[0], (Method[]) methodInfo[1]);
		}
		return jsonObj;
	}

	public static Object JSONObject2Bean(JSONObject jsonObj, Class<?> beanClass) {
		Object[] methodInfo = BeanUtils.getCacheSetterMethodInfo(beanClass);
		return JSONObject2Bean(jsonObj, beanClass, methodInfo);
	}

	public static Object JSONObject2Bean(JSONObject jsonObj, Class<?> beanClass, Object[] methodInfo) {
		Object bean = null;
		try {
			bean = beanClass.newInstance();
		} catch (Exception e1) {
			jsonObj = null;
		}
		if (jsonObj == null)
			return null;

		String[] methodNames = (String[]) methodInfo[0];
		Method[] methods = (Method[]) methodInfo[1];
		Class[] paramTypes = (Class[]) methodInfo[2];
		for (int i = 0; i < methods.length; i++) {
			try {
				Class<?> paramType = paramTypes[i];
				Object[] param = null;
				if (paramType.equals(String.class)) {

					// 烦人的"null"
					String val = jsonObj.getString(methodNames[i]);
					if (val != null && val.equals("null")) {
						val = null;
					}
					param = new Object[] { val };
				} else if (paramType.equals(Integer.class)) {
					param = new Object[] { new Integer(jsonObj.getInt(methodNames[i])) };
				} else if (paramType.equals(Long.class)) {
					param = new Object[] { new Long(jsonObj.getLong(methodNames[i])) };
				} else if (paramType.equals(Double.class)) {
					param = new Object[] { new Double(jsonObj.getDouble(methodNames[i])) };
				} else if (paramType.equals(BigDecimal.class)) {
					param = new Object[] { new BigDecimal(jsonObj.getDouble(methodNames[i])) };
				} else if (paramType.equals(Boolean.class)) {
					param = new Object[] { new Boolean(jsonObj.getBoolean(methodNames[i])) };
				} else if (paramType.equals(Date.class)) {
					param = new Object[] { DateUtil.parse(jsonObj.getString(methodNames[i])) };
				} else if (paramType.equals(Timestamp.class)) {
					param = new Object[] { DateUtil.parseTime(jsonObj.getString(methodNames[i])) };
				} else {
					param = new Object[] { jsonObj.get(methodNames[i]) };
				}

				methods[i].invoke(bean, param);
			} catch (Exception e) {
				LogHandler.error(methodNames[i] + "  " + e.getMessage());
			}
		}
		return bean;
	}

	public static JSONArray BeanList2JSONArray(List<?> list, Class<? extends Object> beanClass) {
		Object[] info = BeanUtils.getCacheGetterMethodInfo(beanClass);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0, end = list.size(); i < end; i++) {
			jsonArray.put(Bean2JSONObject(list.get(i), info));
		}
		return jsonArray;
	}

	public static Map<String, Object> JSONObject2Map(JSONObject obj_JS) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<?> keyItr = obj_JS.keys();
		while (keyItr.hasNext()) {
			String key = (String) keyItr.next();
			Object e;
			try {
				e = obj_JS.get(key);
				if (e instanceof JSONObject) {
					e = JSONObject2Map((JSONObject) e);
				}
				map.put(key, e);
			} catch (JSONException e1) {
			}

		}

		return map;
	}

	/*
	 * public static void main(String[] args) throws JSONException { StudentVO
	 * svo=new StudentVO(); svo.setBirthday("1982-01-01"); svo.setDepartment(new
	 * Integer(11)); svo.setGender("1"); svo.setMemo("testmemo asdasd ");
	 * svo.setName("fins"); svo.setNo(new Integer(11));
	 * 
	 * JSONObject jo=Bean2JSONObject(svo); System.out.println(jo.toString(4));
	 * 
	 * StudentVO svo2 = (StudentVO)JSONObject2Bean(jo,StudentVO.class);
	 * System.out.println(svo2); JSONObject jo2=Bean2JSONObject(svo2);
	 * System.out.println(jo2.toString(4)); }
	 */
}
