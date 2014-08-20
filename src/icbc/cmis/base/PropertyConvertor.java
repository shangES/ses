package icbc.cmis.base;

/**
 * 
 *   @(#) PropertyConvertor.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public interface PropertyConvertor {

	public String getAsText();
	public Object getValue();
	public void setAsText(String text) throws java.lang.IllegalArgumentException;
	public void setValue(Object value);
}
