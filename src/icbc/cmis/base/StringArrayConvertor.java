package icbc.cmis.base;

import icbc.cmis.base.*;
/**
 * 
 *   @(#) StringArrayConvertor.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public class StringArrayConvertor extends PropertyConvertorSurport {
/**
 * ByteConvertor constructor comment.
 */
public StringArrayConvertor() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/04/2000
 *  
 * 
 */
public String getAsText(){

	try{
	String[] value = (String[])getValue();
	String str = "";
	str = value[0];
	for(int i=1; i<value.length; i++)
		str = str+","+value[i];
	return str;
	}catch(Exception e)
	{
		return "";
	}
	
//	setValue( text );
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

	String[] value = null;

	java.util.StringTokenizer st = new java.util.StringTokenizer(text, ",");
	int length = st.countTokens();
	if( length != 0)
	{
		value = new String[length];
		int i = 0;
		while (st.hasMoreTokens())
		{
			value[i] = st.nextToken();
			i++;
		}
	}

	setValue( value );
}
}
