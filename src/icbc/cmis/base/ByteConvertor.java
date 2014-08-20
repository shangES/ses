package icbc.cmis.base;

import icbc.cmis.base.*;
/**
 * 
 *   @(#) ByteConvertor.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public class ByteConvertor extends PropertyConvertorSurport {
/**
 * ByteConvertor constructor comment.
 */
public ByteConvertor() {
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
	
	Byte abyte = new Byte(text);
	setValue( abyte );
}
}
