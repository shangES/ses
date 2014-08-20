package icbc.cmis.base;

import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-2-5 21:37:12)
 * @author: Administrator
 */
public class ParaBean {
	private Hashtable hTable = new Hashtable();
/**
 * SelfDefineDictBean constructor comment.
 */
public ParaBean() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-2-5 21:41:12)
 * @param areaCode java.lang.String
 * @param key java.lang.String
 * @param value java.lang.String
 */
public void addRow(String paraType, String key, String value) {
	DictBean bean = (DictBean)hTable.get(paraType.trim());
	if(bean == null){
		bean = new DictBean();
		bean.setNullMark(false);
		bean.addData(key.trim(),value.trim());
		hTable.put(paraType.trim(),bean);
	}else{
		bean.addData(key.trim(),value.trim());
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTags(String paraType) {
	DictBean bean = (DictBean)hTable.get(paraType.trim());
	if(bean == null) return "";
	StringBuffer buffer = new StringBuffer();
	Vector keys =(Vector) bean.getKeys();
	for(int i = 0;i<keys.size();i++)
	  {
	   String key = (String)keys.elementAt(i);
	   String value = (String)bean.getValue(key);
	   buffer =  buffer.append("<option value=" + key + ">" + value + "</option>\n" ); 
	  }
	return new String(buffer);
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTags(String paraType,String tfKey) {
	DictBean bean = (DictBean)hTable.get(paraType.trim());
	if(bean == null) return "";
	
	StringBuffer buffer = new StringBuffer();
	Vector keys = (Vector)bean.getKeys();
	for(int i = 0;i<keys.size();i++)
	  {
	   String key = (String)keys.elementAt(i);
	   String value = (String)bean.getValue(key);
	   if(tfKey != null && key.trim().equals(tfKey.trim()))
	   	buffer =  buffer.append("<option value=" + key + "  selected>" + value + "</option>\n" );
	   	else
	   			buffer =  buffer.append("<option value=" + key + ">" + value + "</option>\n" );
	  }
	return new String(buffer);
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTagsEmty(String paraType) {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" ); 
	buffer = buffer.append(getOptionTags(paraType));
	return new String(buffer);
}

/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTagsEmty(String paraType, String tfkey) {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" ); 
	buffer = buffer.append(getOptionTags(paraType, tfkey));
	return new String(buffer);
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-8 13:24:20)
 * @return java.util.Vector
 */
public Enumeration getParaTypes() {
	return hTable.keys();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-8 13:23:24)
 * @return java.lang.String
 * @param key java.lang.String
 */
public String getValue(String paraType,String key) {
	if(paraType == null || paraType.trim().length() == 0) return null;
	DictBean bean = (DictBean)hTable.get(paraType.trim());
	String tmpStr = (String)bean.getValue(key.trim());
	if(tmpStr == null) tmpStr = "";
	return tmpStr;
	
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-8 13:23:24)
 * @return java.lang.String
 * @param key java.lang.String
 */
public DictBean getValues(String paraType) {
	if(paraType == null || paraType.trim().length() == 0) return null;
	return (DictBean)hTable.get(paraType.trim());
	
}
/**
 * Insert the method's description here.
 * Creation date: (2002-2-7 10:31:41)
 * @return boolean
 * @param paraType java.lang.String
 */
public boolean isNull(String paraType) {
	DictBean bean = (DictBean)hTable.get(paraType.trim());
	if(bean  == null) return true;
	return bean.isNull();
}
}
