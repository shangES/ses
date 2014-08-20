package icbc.cmis.base;


import icbc.cmis.operation.CmisOperation;
import java.util.*;


  

 
 import org.w3c.dom.*;
import com.ibm.xml.dom.*;public class XMLFullTagFormat extends XMLTagFormat {

/**
 * XMLFullTagFormat constructor comment.
 */
public XMLFullTagFormat() {
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

		String endTag = "</" + tagValue + ">";

		int e= source.indexOf(endTag);
		int b = source.indexOf(">");
		if( e == -1 || b == -1 )
		{
			astring[0] = source;
			astring[1] = "";
			
		}
		else
		{
			
			astring[0] = (source.substring(0, e+endTag.length())).trim();
			astring[1] = (source.substring(e+endTag.length(), source.length())).trim();
		}
	}
	return astring;
}
/**
 * format method comment.
 */
public String format(DataElement arg1){
	String retStr = "<" + getTagValue();

	if( arg1.getValue() != null)
		retStr = retStr + ">" + CmisOperation.getHostArea((String)arg1.getValue()) + "</" + getTagValue() + ">";
	else
		retStr = retStr + ">" + "</" + getTagValue() + ">";
	return retStr;
}

/**
 * unformat method comment.
 */
public Boolean isConstant() {
	return new Boolean(false);
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

	tmp = tmp + "<XMLFullTagFormat";
	tmp = tmp + " tagName=\"" + getTagValue() + "\"";
	tmp = tmp + " dataName=\"" + getDataName() + "\"";
	tmp = tmp + "/>\n";
	
	return tmp;
}
/**
 * unformat method comment.
 */
public DataElement unformat(String arg1, DataElement arg2) throws CTEInvalidRequestException
{

	String endTag = "</" + tagValue + ">";
	int e = arg1.indexOf(endTag);
	int b = arg1.indexOf(">");

	if (e == -1 || b == -1)
	{
		throw new CTEInvalidRequestException("", "",
			"Failed to unformat data!\nTagname=" + tagValue + "\nInput=" + arg1); 
	}
	arg2.setValue(arg1.substring(b + 1, e));
	return arg2;
}

}