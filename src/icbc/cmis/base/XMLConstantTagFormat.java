package icbc.cmis.base;


import java.util.*;
import org.w3c.dom.Node;  



public class XMLConstantTagFormat extends XMLTagFormat {
	private String value = "";
	private String format = "";
/**
 * XMLConstantTagFormat constructor comment.
 */
public XMLConstantTagFormat() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2000-12-29 21:19:14)
 * @return java.lang.String[]
 * @param source java.lang.String
 */
public String[] extract(String source){
	String astring[] = new String[2];
	if (source.length() <= getTagValue().length()+2) {
		astring[0] = source;
		astring[1] = "";
	} else {
		int i= source.indexOf("</" + this.getTagValue() + ">");
		if( i == -1 )
		{
			astring[0] = source;
			astring[1] = "";
			
		}
		else
		{
			int len = ("</" + getTagValue() +">").length();
			astring[0] = source.substring(0, i+len);
			astring[1] = (source.substring(i+len, source.length())).trim();
		}
	}
	return astring;
}
/**
 * formatField method comment.
 */
public String format(DataElement arg1){


	return  "<" + getTagValue() + ">" + getRealValue() + "</" + getTagValue() + ">";
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 16:59:48)
 * @return java.lang.String
 */
public String getValue() {
	return value;
}

/**
 * toStrings method comment.
 */
public String toString() {
	
	String tmp = "";

	for (int i = 0; i < tabCount; i++)
	{
		tmp = tmp + "\t";
	}

	tmp = tmp + "<XMLConstantTagFormat";
	tmp = tmp + " value=\"" + getValue() + "\"";
	if (getFormat() != "") {
		tmp = tmp + " format=\"" + getFormat() + "\"";
	}
	tmp = tmp + "/>\n";
	
	return tmp;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 16:55:50)
 * @return java.lang.String
 */
public String getFormat() {
	return format;
}/**
 * Insert the method's description here.
 * Creation date: (2001-3-7 19:29:38)
 * @return java.lang.String
 */  
private String getRealValue()
{
/*
	java.util.Calendar calendar = java.util.Calendar.getInstance();
	java.util.Date date = calendar.getTime();
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format);
*/
	if (value.equals("Time")) //get the current time
	{
		//return formatter.format(date);
		return CmisConstance.getWorkDate(format);
	} else if (value.equals("TimeMillis")) //get the current TimeMillis
	{
		return String.valueOf(System.currentTimeMillis());
	} else if (value.equals("Date")) //get the current date
	{
	    //return formatter.format(date);
	    return CmisConstance.getWorkDate(format);
	} else if (value.equals("DateTime")) //get the current Data Time
	{
	  //  return formatter.format(date);
	    return CmisConstance.getWorkDate(format);
	}
	return value;
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 16:53:24)
 * @param format java.lang.String
 */  
public void setFormat(String format) 
{
	this.format = format;
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 16:54:09)
 * @param value java.lang.String
 */  
public void setValue(String value) 
{
	this.value = value;
}
}