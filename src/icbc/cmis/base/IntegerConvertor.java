package icbc.cmis.base;

import icbc.cmis.base.*;
/**
 * 
 *   @(#) IntegerConvertor.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public class IntegerConvertor extends PropertyConvertorSurport {
/**
 * ByteConvertor constructor comment.
 */
public IntegerConvertor() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 * @param text java.lang.String
 */
public void setAsText(String text)  throws IllegalArgumentException{
	
	Integer value = new Integer(text);
	setValue( value );
}
}
