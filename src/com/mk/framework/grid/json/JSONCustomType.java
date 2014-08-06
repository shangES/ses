package com.mk.framework.grid.json;

import java.util.Date;

public class JSONCustomType {
	
	private Object nativeObject;
	
	public final static short TYPE_NATIVE = 1;
	public final static short TYPE_DATE = 2;
	
	public JSONCustomType() {}

	public JSONCustomType(Object nativeObject) {
		this.nativeObject=nativeObject;
	}
	
	public String toString() {
		return toString(nativeObject,TYPE_NATIVE);
	}

	public static String toString(Object value,short type) {
        if (TYPE_DATE==type && value!=null) {
            return "new Date("+ ((Date)value).getTime()+")";
        }
		if (TYPE_NATIVE==type) {
            return String.valueOf(value);
        }
        return JSONObject.quote(String.valueOf(value));
	}
}
