package icbc.cmis.base;

/**
 * 
 *   @(#) *.java	1.0 05/13/2000
 *   Copyright (c) 2000 ZhongMC. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/13/2000
 *   @author  ZhongMingChang
 *   
 */
public class Delimiter extends Decorator {

	private String delimChar = "|";
/**
 * Delimiter constructor comment.
 */
public Delimiter() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String[]
 * @param aString java.lang.String
 */
public String[] extract(String aString) {

	String[] tmp = new String[2];

	int idx = aString.indexOf(this.delimChar);
	if(idx != -1)
	{
		tmp[0] = aString.substring(0, idx);
		tmp[1] = aString.substring(idx + delimChar.length());
	}
	else
	{
		tmp[0] = aString;
		tmp[1] = "";
	}
	return tmp;	
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String
 * @param data java.lang.String
 */
public String format( String data) {
	return data + this.delimChar;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param delimChar java.lang.String
 */
public void setDelimChar(String delimChar)
{
	this.delimChar = delimChar;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String
 */
public String toString() {
	String tmp = "<Decorator classType = \"" + this.getClassType()+ "\" delimChar= \"" +this.delimChar + "\"/>\n";
	return tmp;

}
}