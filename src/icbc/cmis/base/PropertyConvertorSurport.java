package icbc.cmis.base;

/**
 * 
 *   @(#) PropertyConvertorSurport.java	1.0 05/04/2000
 *   Copyright (c) 1999 EChannel R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/04/2000
 *   @author  ZhongMingChang
 *   
 */
public class PropertyConvertorSurport implements PropertyConvertor {

	private Object value;
	
/**
 * PropertyConvertorSurport constructor comment.
 */
public PropertyConvertorSurport() {
	super();
}
/**
 * getAsText method comment.
 */
public String getAsText() {
	if( value instanceof String)
	{
		return (String)value;
	}
	return "" + value;
}
/**
 * getValue method comment.
 */
public Object getValue() {
	return value;
}
/**
 * setAsText method comment.
 */
public void setAsText(String text) throws IllegalArgumentException {
	if (value instanceof String) {
	    setValue(text);
	    return;
	}
	throw new java.lang.IllegalArgumentException(text);
	
}
/**
 * setValue method comment.
 */
public void setValue(Object value) {
	this.value = value;	
}
}
