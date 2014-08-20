package icbc.cmis.base;

import org.w3c.dom.Node;
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
public class DataElement extends Object implements java.io.Serializable
{
	String name;
	Object value;

	protected static int tabCount = 0;
/**
 * DataElement constructor comment.
 */
public DataElement() {
	super();
}
/**
 * DataElement constructor comment.
 */
public DataElement(String name) {
	super();
	this.name = name;
}
/**
 * DataElement constructor comment.
 */
public DataElement(String name, Object value) {
	super();
	this.name = name;
	this.value = value;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String
 */
public String getName() {
	return name;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.Object
 */
public Object getValue() {
	return value;
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-24 15:39:29)
 * @return java.lang.Object
 */
public Object initializeFrom(Node node) throws CTEInvalidRequestException{
	//return null;
	throw new CTEInvalidRequestException("CTEInvalidRequestException",
		"DataCollection.initializeFrom(Node node)",
		"this method may be not realizable!");
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param name java.lang.String
 */
public void setName(String name)
{
	this.name = name;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param value java.lang.Object
 */
public void setValue( Object value) {
	this.value = value;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 */
public String toString()
{
	try
	{
		if(getName() == null) setName("");
		if(getValue() == null) setValue("");
		String tmp = "";
		for (int i = 0; i < tabCount; i++)
			tmp = tmp + "\t";
		return tmp + getName() + " = " + getValue().toString() + "\n";
	}
	catch (Exception e)
	{
		System.out.println(e);
		return "null";		
	}
}
}
