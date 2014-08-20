package icbc.cmis.base;

import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-4-9 11:09:56)
 * @author: Administrator
 */
public class DA220091006Bean extends SelfDefineDictBean {
/**
 * DA200251031Bean constructor comment.
 */
public DA220091006Bean() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-2-5 21:41:12)
 * @param areaCode java.lang.String
 * @param key java.lang.String
 * @param value java.lang.String
 */
public void addRow(String flag, String key, String value) {
	super.addRow(flag,key,value);
}
public Enumeration getFlags() {
	return super.getAreaCodes();
}
public String getOptionTags(String flag) {
	return super.getOptionTags(flag);
	
}
public String getOptionTags(String flag,String tfKey) {
	return super.getOptionTags(flag,tfKey);
}
public String getOptionTagsEmty(String flag) {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" );
	buffer = buffer.append(getOptionTags(flag));
	return new String(buffer);
	
}
public String getOptionTagsEmty(String flag,String tfKey) {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" );
	buffer = buffer.append(getOptionTags(flag,tfKey));
	return new String(buffer);
}
 public String getValue(String flag,String key) {
	return super.getValue(flag,key);
	
}
public DictBean getValues(String flag) {
	return super.getValues(flag);
	
}
public boolean isNull(String flag) {
	return super.isNull(flag);
}
}
