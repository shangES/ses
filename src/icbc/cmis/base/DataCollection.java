package icbc.cmis.base;

/**
 * 
 *   @(#) *.java	1.0 05/13/2000
 *   Copyright (c) 2000 ZhongMC. All Rights Reserved.
 *  
 *  
 *   @version 1.0.0 05/13/2000
 *   @author  ZhongMingChang
 *   
 */
public class DataCollection extends DataElement {
/**
 * DataCollection constructor comment.
 */
public DataCollection() {
	super();
}
/**
 * DataCollection constructor comment.
 */
public DataCollection(String name) {
	super(name);
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @return java.lang.String
 * @param dataName java.lang.String
 */
public Object getValueAt( String dataName ) throws CTEObjectNotFoundException,CTEInvalidRequestException{
	//return null;
	throw new CTEInvalidRequestException("CTEInvalidRequestException",
		"DataCollection. getValueAt( String dataName )",
		"this method may be not realizable!");
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  05/13/2000
 *  
 * 
 * @param name java.lang.String
 * @param value java.lang.Object
 */
public void setValueAt( String name, Object value)throws CTEInvalidRequestException, CTEObjectNotFoundException {
	throw new CTEInvalidRequestException("CTEInvalidRequestException",
		"DataCollection. setValueAt( String name, Object value)",
		"this method may be not realizable!");
}
}
