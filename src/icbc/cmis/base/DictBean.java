package icbc.cmis.base;

import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-8 13:21:14)
 * @author: Administrator
 */
public class DictBean  implements java.io.Serializable{
	private Vector keys = new Vector();
	private Hashtable dict = new Hashtable();
	private int count = 0;
	private boolean isNull = true;
/**
 * DictBean constructor comment.
 */
public DictBean() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-8 13:25:23)
 * @param key java.lang.String
 * @param value java.lang.String
 */
public void addData(String key, String value) {
	keys.add(count,key);
	dict.put(key,value);
	count++;
	
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-8 13:24:20)
 * @return java.util.Vector
 */
public Vector getKeys() {
	return keys;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTags() {
	StringBuffer buffer = new StringBuffer();
	for(int i = 0;i<keys.size();i++)
	  {
	   String key = (String)keys.elementAt(i);
	   String value = (String)getValue(key);
	   buffer =  buffer.append("<option value=" + key + ">" + value + "</option>\n" ); 
	  }
	return new String(buffer);
}

/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTagsWithCode() {
	StringBuffer buffer = new StringBuffer();
	for(int i = 0;i<keys.size();i++)
	  {
	   String key = (String)keys.elementAt(i);
	   String value = (String)getValue(key);
	   buffer =  buffer.append("<option value=" + key + ">" + key +" "+ value + "</option>\n" ); 
	  }
	return new String(buffer);
}

/**
 * Insert the method's description here.
 * Creation date: (2003-8-4 12:48:52)
 * @return java.lang.String
 */
public String getOptionTags(Vector ntNeed ) {
	StringBuffer buffer = new StringBuffer();
	for(int i = 0;i<keys.size();i++)
	  {
	   String key = (String)keys.elementAt(i);
	   if(ntNeed.contains(key))
	   	  continue;
	   String value = (String)getValue(key);
	   buffer =  buffer.append("<option value=" + key + ">" + value + "</option>\n" ); 
	  }
	return new String(buffer);
}

/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTags(String tfKey) {
	StringBuffer buffer = new StringBuffer();
	for(int i = 0;i<keys.size();i++)
	  {
	   String key = (String)keys.elementAt(i);
	   String value = (String)getValue(key);
	   if(tfKey != null && key.trim().equals(tfKey.trim()))
	   	buffer =  buffer.append("<option value=" + key + "  selected>" + value + "</option>\n" );
	   	else
	   			buffer =  buffer.append("<option value=" + key + ">" + value + "</option>\n" );
	  }
	return new String(buffer);
}

/**
 * Insert the method's description here.
 * Creation date: (2003-7-25 12:48:52)
 * @return java.lang.String
 */
public String getOptionTags(String tfKey,Vector ntNeed) {
	
	StringBuffer buffer = new StringBuffer();
	for(int i = 0;i<keys.size();i++)
	  {
	   String key = (String)keys.elementAt(i);
	   if(ntNeed.contains(key))
	   	  continue;
	   String value = (String)getValue(key);
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
public String getOptionTagsEmty() {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" ); 
	buffer = buffer.append(getOptionTags());
	return new String(buffer);
}


/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTagsEmtyWithCode() {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" ); 
	buffer = buffer.append(getOptionTagsWithCode());
	return new String(buffer);
}


/**
 * Insert the method's description here.
 * Creation date: (2003-8-4 12:48:52)
 * @return java.lang.String
 */
public String getOptionTagsEmty(Vector ntNeed) {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" ); 
	buffer = buffer.append(getOptionTags(ntNeed));
	return new String(buffer);
}



/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 12:48:52)
 * @return java.lang.String
 */
public String getOptionTagsEmty(String tfKey) {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" ); 
	buffer =  buffer.append(getOptionTags(tfKey));
	return new String(buffer);
}

/**
 * Insert the method's description here.
 * Creation date: (2003-7-25 12:48:52)
 * @return java.lang.String
 */
public String getOptionTagsEmty(String tfKey,Vector ntNeed) {
	StringBuffer buffer = new StringBuffer();
	buffer =  buffer.append("<option value=\"\"> </option>\n" ); 
	buffer =  buffer.append(getOptionTags(tfKey,ntNeed));
	return new String(buffer);
}

/**
 * Insert the method's description here.
 * Creation date: (2002-1-8 13:23:24)
 * @return java.lang.String
 * @param key java.lang.String
 */
public String getValue(String key) {
	if(key == null || key.trim().length() == 0) return "";
	String tmpStr = (String)dict.get(key.trim());
	if(tmpStr == null) tmpStr = "";
	return tmpStr;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 17:56:59)
 * @param tf boolean
 */
public boolean  isNull() {
	return isNull;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-9 17:56:59)
 * @param tf boolean
 */
public void setNullMark(boolean tf) {
	isNull = tf;
}
}
