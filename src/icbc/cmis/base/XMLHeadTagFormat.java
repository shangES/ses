package icbc.cmis.base;

/**
 * Insert the type's description here.
 * Creation date: (2001-3-20 8:34:30)
 * @author: Administrator
 */




import java.util.*;

 
  
 
import org.w3c.dom.*;
import com.ibm.xml.dom.*;public class XMLHeadTagFormat extends XMLTagFormat {
/**
 * XMLHeadTagFormat constructor comment.
 */
public XMLHeadTagFormat() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2000-12-29 21:19:14)
 * @return java.lang.String[]
 * @param source java.lang.String
 */
public String[] extract(String source) {
	String astring[] = new String[2];

	int b= source.indexOf("?>");
	if( b == -1)
	{
		astring[0] = source;
		astring[1] = "";
		
	}
	else
	{
		astring[0] = source.substring(0, b+2);
		astring[1] = (source.substring(b+2, source.length())).trim();
	}
	return astring;
}
/**
 * format method comment.
 */
public String format(DataElement arg1) throws CTEInvalidRequestException {
	
	String retStr = "<?xml";

	retStr = retStr + " version=\"" + getVersion() + "\"";
	retStr = retStr + " encoding=\"" + getEncoding() + "\"";
	retStr = retStr + " standalone=\"" + getStandalone() + "\"";
	
	return retStr + "?>";
}
	String encoding = "";	String standalone = "";	String version = "";/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 16:31:23)
 * @return java.lang.String
 */
public String getEncoding() {
	return encoding;
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 16:32:17)
 * @return java.lang.String
 */  
public String getStandalone() {
	return standalone;
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 16:30:22)
 * @return java.lang.String
 */  
public String getVersion() {
	return version;
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 16:33:37)
 * @param encoding java.lang.String
 */  
public void setEncoding(String encoding) 
{
	this.encoding = encoding;
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 16:34:14)
 * @param standalone java.lang.String
 */  
public void setStandalone(String standalone) 
{
	this.standalone = standalone;
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-31 16:32:55)
 * @param version java.lang.String
 */  
public void setVersion(String version) 
{
	this.version = version;
}/**
 * 此处插入方法说明。
 * 创建日期：(2001-12-30 15:15:23)
 * @return java.lang.String
 */  
public String toString()
{
	String tmp = "";

	for (int i = 0; i < tabCount; i++)
	{
		tmp = tmp + "\t";
	}

	tmp = tmp + "<XMLHeadTagFormat";
	tmp = tmp + " version=\"" + getVersion() + "\"";
	tmp = tmp + " encoding=\"" + getEncoding() + "\"";
	tmp = tmp + " standalone=\"" + getStandalone() + "\"";
	tmp = tmp + "/>\n";
	
	return tmp;
}
}