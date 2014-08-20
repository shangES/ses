package icbc.cmis.base;


import java.util.*;

  

 
import org.w3c.dom.*;
import com.ibm.xml.dom.*;public class XMLTagFormat extends FieldFormat {
	String tagValue;


/**
 * XMLTagFormat constructor comment.
 */
public XMLTagFormat() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2000-12-29 21:19:14)
 * @return java.lang.String[]
 * @param source java.lang.String
 */
public String[] extract(String source)throws CTEInvalidRequestException {
	String astring[] = new String[2];
	if (source.length() <= getTagValue().length()+2) {
		astring[0] = source;
		astring[1] = "";
	} 
	else {
		int i= source.indexOf(">");
		if( i == -1 )
		{
			astring[0] = source;
			astring[1] = "";
		}
		else
		{	
			astring[0] = source.substring(0, i+1);
			astring[1] = (source.substring(i+1, source.length())).trim();
		}
	}
	return astring;
}
/**
 * format method comment.
 */
public String format(DataElement arg1) throws CTEInvalidRequestException 
{
	return "<" + getTagValue() + ">";	
}

/**
 * Insert the method's description here.
 * Creation date: (2000-12-29 21:22:47)
 * @return java.lang.String
 */
protected String getTagValue() {
	return tagValue;
}

/**
 * unformat method comment.
 */
public Boolean isConstant() {
	return new Boolean(true);
}

/**
 * toStrings method comment.
 */
public String toString() {
	String tmp = "";
	
	for (int i=0; i<tabCount; i++)
	{
		tmp = tmp + "\t";
	}
	tabCount++;
	
	return tmp + "<XMLTagFormat tagName= " + tagValue + "/>\n";
}
/**
 * unformat method comment.
 */
public DataElement unformat(String arg1, DataElement arg2)throws CTEInvalidRequestException {
	return arg2;
}

/**
 * Insert the method's description here.
 * Creation date: (2000-12-29 22:15:43)
 * @param tagValue java.lang.String
 */
public void setTagName(String tagValue) {
	this.tagValue = tagValue;
}
}