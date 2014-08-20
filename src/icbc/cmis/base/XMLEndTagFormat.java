package icbc.cmis.base;


import java.util.Enumeration;

import java.util.*;public class XMLEndTagFormat extends Decorator {

	String tagValue = "";
	
	
/**
 * XMLEndTagFormat constructor comment.
 */
public XMLEndTagFormat() {
	super();
}
/**
 * addDecoration method comment.
 */
public String addDecoration(String arg1) {
	return arg1 + "</"  + tagValue + ">\n";
}
public String[] extract(String string1) throws CTEInvalidRequestException {
	String astring[] = new String[2];
	if (string1.length() <= tagValue.length() + 2)
	{
		astring[0] = string1;
		astring[1] = "";
		return astring;
	}

	String endTag = "</" + tagValue + ">";

	int i = string1.indexOf(endTag);

	if (i == -1)
	{

		String string2 = string1;

		if (string1.length() > 15)
			string2 = 
				new StringBuffer(String.valueOf(string1.substring(0, 15)))
					.append("...")
					.toString(); 

		throw new CTEInvalidRequestException("CTEInvalidRequestException",
	        "XMLEndTagFormat.extract(String)",
			"Extract" + string2 + " with tagValue " + tagValue); 

	}

	astring[0] = (string1.substring(0, i + endTag.length())).trim();

	if ((i + endTag.length()) < string1.length())
		astring[1] = (string1.substring(i + endTag.length())).trim();

	else
		astring[1] = "";

	return astring;

}

/**
 * removeDecoration method comment.
 */
public String removeDecoration(String string1) throws CTEInvalidRequestException {
	String endTag = "</" + tagValue + ">";
	int i = string1.indexOf(endTag);
	if( i == -1)
		return string1;
	else
		return string1.substring(0, i);
}
/**
 * toStrings method comment.
 */
public String toString() {
	tabCount--;

	String tmp = "";
	
	for (int i=0; i<tabCount; i++)
	{
		tmp = tmp + "\t";
	}
	
	return tmp + "<XMLEndTagFormat tagName= \"" + tagValue + "\"/>\n";
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-28 18:52:41)
 * @return java.lang.String
 * @param str java.lang.String
 */

public String format(DataElement element) {
	return "</" + tagValue + ">";
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-28 18:52:41)
 * @return java.lang.String
 * @param str java.lang.String
 */  

public String format(String str) {
	return str + "</" + tagValue + ">";
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-30 18:02:56)
 * @param tagValue java.lang.String
 */  
public void setTagName(String tagValue) 
{
	this.tagValue = tagValue;
}
}