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
public class Decorator extends FormatElement {
/**
 * Decorator constructor comment.
 */
public Decorator() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String[]
 * @param data java.lang.String
 */
public String[] extract(String data) throws CTEInvalidRequestException {

	String[] tmp = new String[2];
	tmp[0] = data;
	tmp[1] = "";
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
	return data;
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
	String tmp = "<Decorator classType = \"" + this.getClassType()+ "\"/>\n";
	return tmp;

}
}