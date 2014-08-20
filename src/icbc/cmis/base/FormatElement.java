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
public class FormatElement
{
	private Decorator decorator = null; // new Decorator();
	private String dataName = null;
	private String classType = "";

	protected static int tabCount = 0;
/**
 * FormatElement constructor comment.
 */
public FormatElement() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param decorator com.zmc.chanel.base.Decorator
 */
public void addDecorator(Decorator decorator)
{
	this.decorator = decorator;
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
public String[] extract(String data) throws CTEInvalidRequestException
{
	String[] tmp;
	if (getDecorator() == null)
	{
		tmp = new String[2];
		tmp[0] = data;
		tmp[1] = "";
	}
	else
		tmp = getDecorator().extract(data);
		
	return tmp;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String
 * @param collection com.zmc.chanel.base.KeyedDataCollection
 */
public String format(DataElement dataElement) throws CTEUncausedException,CTEObjectExistingException,CTEObjectNotFoundException,CTEInvalidRequestException{
	//return null;
	throw new CTEInvalidRequestException("CTEInvalidRequestException",
		"FormatElement.format(DataElement dataElement)",
		"this method may be not realizable!");
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String
 */
public String getClassType() {
	return classType;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String
 */
public String getDataName( ) {
	return dataName;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return com.zmc.chanel.base.Decorator
 */
public Decorator getDecorator() {
	return decorator;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param classType java.lang.String
 */
public void setClassType(String classType)
{
	this.classType = classType;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param dataName java.lang.String
 */
public void setDataName(String dataName)
{
	this.dataName = dataName;
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

	String tmp = "";
	
	for( int i=0; i<tabCount; i++)
		tmp = tmp + "\t";
	
	tmp = tmp + "<FormatElement classType = \"" + this.getClassType() + "\" dataName=\"" + this.dataName + "\">\n";

	tabCount++;
	if( getDecorator()!= null)
	{
		for( int i=0; i<tabCount; i++)
			tmp = tmp + "\t";
		tmp = tmp + this.decorator.toString();
	}

	tabCount--;
	
	for( int i=0; i<tabCount; i++)
		tmp = tmp + "\t";
	tmp = tmp  + "</FormatElement>\n";	
	return tmp;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param dataSource java.lang.String
 * @param dataElement com.zmc.chanel.base.DataElement
 */
public DataElement unformat(String dataSource, DataElement dataElement) throws CTEUncausedException, CTEObjectExistingException,CTEObjectNotFoundException, CTEInvalidRequestException{
	//return null;
	throw new CTEInvalidRequestException("CTEInvalidRequestException",
		"FormatElement.format(DataElement dataElement)",
		"this method may be not realizable!");
}
}