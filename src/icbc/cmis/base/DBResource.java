package icbc.cmis.base;

/**
 * 
 *   @(#) *.java1.0 05/13/2000
 *   Copyright (c) 1999 Nantian R&D. All Rights Reserved.
 *  
 *  
 *   @version 1.0 05/13/2000
 *   @author  ZhongMingChang
 *   
 */
public class DBResource {
	public String resourceName = "";
	public String driverName = "";
	public String dbURL = "";
	public String dbUserName = "";
	public String dbPassword = "";
	public int maxConnection = 10;
	
	public int connectionTimeOut = 180000;		public int idleTimeOut = 300000;
/**
 * DBResource constructor comment.
 */
public DBResource() {
	super();
}
/**
 * DBResource constructor comment.
 */
public DBResource(String resourceName, String driverName,String dbURL,String dbUserName, String dbPassword)
{
	super();
	this.resourceName = resourceName;
	this.driverName = driverName;
	this.dbURL = dbURL;
	this.dbUserName = dbUserName;

	this.dbPassword = dbPassword;
	
}
}
