package icbc.cmis.base;

import icbc.cmis.base.*;
/**
 * 
 *   @(#) FloatConvertor.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public class FloatConvertor extends PropertyConvertorSurport {
/**
 * ByteConvertor constructor comment.
 */
public FloatConvertor() {
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
public void setAsText(String text) throws IllegalArgumentException {
	try {
		Float value = new Float(text);
		setValue(value);
	} catch (Exception e) {
		setValue(new Float(0.0));
	}
}
}
