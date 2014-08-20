package icbc.cmis.base;

import icbc.cmis.base.*;
/**
 * 
 *   @(#) IntConvertor.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public class IntConvertor extends PropertyConvertorSurport {
/**
 * ByteConvertor constructor comment.
 */
public IntConvertor() {
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
