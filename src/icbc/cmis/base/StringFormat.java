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
public class StringFormat extends FieldFormat
{
/**
 * StringFormat constructor comment.
 */
public StringFormat() {
	super();
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
public String format(DataElement dataElement) {

	String tmp;
	if( dataElement.getValue() instanceof String)
		tmp = (String)dataElement.getValue();
	else
		tmp = dataElement.getValue().toString();

	if( getDecorator() != null)
		return getDecorator().format(tmp);
	else
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
public DataElement unformat(String dataSource, DataElement dataElement)
{
	dataElement.setValue( dataSource );
	return dataElement;
}
}