package icbc.cmis.base;

import java.util.*;
/**
 * 
 *   @(#) Context.java	1.0 10/23/2000
 *   Copyright (c) 2000 ZhongMC. All Rights Reserved.
 *  
 *  
 *   @version 1.0 19/23/2000
 *   @author  ZhongMingChang
 *   
 */
public class Context
{
	private static Context rootContext;
	
	private String name;
	private Context parent;
	private Vector childrens = null;
	
	private Context currentChild;
	private KeyedDataCollection dataCollection;

	private Hashtable services;
	private Hashtable notifiers;
/**
 * Context constructor comment.
 */
public Context() {
	super();
}
/**
 * Context constructor comment.
 */
public Context(String name) {
	super();
	this.name = name;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  09/08/2000
 *  
 * 
 * @param aContext com.zmc.chanel.base.Context

 * add a children context to it.
 
 */
public void addContext(Context aContext) {
	if( this.childrens == null)
		childrens = new Vector( 10 );
	childrens.addElement( aContext );
	aContext.setParent( this );
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  10/23/2000
 *  
 * 
 * @param dataCollection com.zmc.chanel.base.KeyedDataCollection
 */
public void addKeyedDataCollection(KeyedDataCollection dataCollection)
{
	this.dataCollection = dataCollection;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  10/23/2000
 *  
 * 
 * @return com.zmc.chanel.base.Context
 * @param name java.lang.String
 */
public Context getChildContextNamed(String name) throws CTEObjectNotFoundException{
	if( this.name.equals( name ))
		return this;

	if(childrens != null )
	{
		for( int i=0; i<childrens.size(); i++)
		{
			Context aContext = (Context)childrens.elementAt(i);
			Context context = aContext.getChildContextNamed(name);
			if( context != null)
				return context;
		}
	}
	//return null;
	throw new CTEObjectNotFoundException("CTEObjectNotFoundException",
		"Context.getChildContextNamed(String name)",
		"Context "+name+" not Found in Context "+rootContext+" and it's children");
	
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  10/23/2000
 *  
 * 
 * @return com.zmc.chanel.base.Context
 * @param name java.lang.String
 */
public static Context getContextNamed(String name) throws CTEContextInitializationException,CTEObjectNotFoundException{
	if( rootContext == null )
		//return null;
		throw new CTEContextInitializationException("CTEContextInitializationException",
			"Context.getContextNamed(String name)",
			"Context-root context not initialization exception,root context is null");

	return rootContext.getChildContextNamed(name);	
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  10/23/2000
 *  
 * 
 * @return com.zmc.chanel.base.KeyedDataCollection
 */
public KeyedDataCollection getKeyedDataCollection() throws CTEInvalidRequestException{
	//return null;
	throw new CTEInvalidRequestException("CTEInvalidRequestException",
		"Context. getKeyedDataCollection()",
		"this method may be not realizable!");
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  09/08/2000
 *  
 * 
 * @param context com.zmc.chanel.base.Context
 */
public void setParent(Context context)
{
	this.parent = context;
}
}
