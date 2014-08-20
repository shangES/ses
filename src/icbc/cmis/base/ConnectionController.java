package icbc.cmis.base;

/**
 * 
 *  @(#)ConnectionControler.java	1.0 10/18/99
 *  Copyright (c) 1999 Nantian R&D. All Rights Reserved.
 * 
 * 
 *   @version 1.0 10/18/99
 *   @author  ZhongMingChang
 *  
 */

 import java.util.*;
 import java.sql.*;
 
public class ConnectionController {
	static Vector connectionManagers = null; //new Vector(10);
	public static Vector resourceNames = null; //new Vector(10);

	static int resCount = 0;
/**
 * ConnectionControler constructor comment.
 */
public ConnectionController() {
	super();
}
/**
 * ConnectionControler constructor comment.
 */
public static void addConnectionManager(DBResource dbResource) {

	if( connectionManagers ==  null)
		connectionManagers = new Vector(10);
	if( resourceNames == null)
		resourceNames = new Vector(10);

	if( dbResource.resourceName == null || dbResource.resourceName.length() == 0)
	{
		resCount++;
		dbResource.resourceName = "Resource" + resCount;
	}
	//first find out if it's still have the same resource
	for( int i=0; i< resourceNames.size(); i++)
	{
		String rn = (String)resourceNames.elementAt(i);
		if( rn.compareTo( dbResource.resourceName ) == 0)
		{
			return;
		}
	}
	
	ConnectionManager cm = new ConnectionManager(dbResource.driverName, dbResource.dbURL, dbResource.dbUserName, dbResource.dbPassword);
	connectionManagers.addElement( cm );
	resourceNames.addElement(dbResource.resourceName);
	
}
/**
 * ConnectionControler constructor comment.
 */
public static void addConnectionManager(String driverName, String dbURL, String dbUserName, String dbPassword, String resourceName) {

	if( connectionManagers ==  null)
		connectionManagers = new Vector(10);
	if( resourceNames == null)
		resourceNames = new Vector(10);

	
	if( resourceName == null || resourceName.length() == 0)
	{
		resCount++;
		resourceName = "Resource" + resCount;
	}
	//first find out if it's still have the same resource
	for( int i=0; i< resourceNames.size(); i++)
	{
		String rn = (String)resourceNames.elementAt(i);
		if( rn.compareTo( resourceName ) == 0)
		{
			return;
		}
	}
	
	ConnectionManager cm = new ConnectionManager(driverName, dbURL, dbUserName, dbPassword);
	connectionManagers.addElement( cm );
	resourceNames.addElement(resourceName);

}
/**
 * ConnectionControler constructor comment.
 */
public static Connection getConnection() throws SQLException
{
	ConnectionManager cm = (ConnectionManager) connectionManagers.elementAt(0);
	Connection connection = cm.getConnection();
//	System.out.println("In using connections: " + cm.inUseConnections);
	return connection;
}
/**
 * ConnectionControler constructor comment.
 */
public static Connection getConnection(String resourceName)throws SQLException
{
	int i;
	for( i=0; i< resourceNames.size(); i++)
	{
		String rn = (String)resourceNames.elementAt(i);
		if( rn.compareTo( resourceName ) == 0)
		{
			ConnectionManager cm = (ConnectionManager) connectionManagers.elementAt(i);

			Connection connection = cm.getConnection();

//			System.out.println("In using connections: " + cm.inUseConnections);
			return connection;
		}
	}
	return null;
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  03/23/2000
 *  
 * 
 * @return com.zmc.base.DataBase.ConnectionManager
 * @param resourceName java.lang.String
 */
public static ConnectionManager getConnectionManager( String resourceName) {
	int i;
	for( i=0; i< resourceNames.size(); i++)
	{
		String rn = (String)resourceNames.elementAt(i);
		if( rn.compareTo( resourceName ) == 0)
		{
			ConnectionManager cm = (ConnectionManager) connectionManagers.elementAt(i);
			return cm;
		}
	}
	return null;
}
/**
 * ConnectionControler constructor comment.
 */
public static synchronized void releaseConnection(Connection connection)
{
	ConnectionManager cm = (ConnectionManager) connectionManagers.elementAt(0);
	cm.releaseConnection(connection);
//	System.out.println("In using connections: " + cm.inUseConnections);

}
/**
 * ConnectionControler constructor comment.
 */
public static synchronized void releaseConnection(Connection connection, String resourceName)
{
	int i;
	for( i=0; i< resourceNames.size(); i++)
	{
		String rn = (String)resourceNames.elementAt(i);
		if( rn.compareTo( resourceName ) == 0)
		{
			ConnectionManager cm = (ConnectionManager) connectionManagers.elementAt(i);
			cm.releaseConnection(connection);
//			System.out.println("In using connections: " + cm.inUseConnections);
			break;
		}
	}
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  03/02/2000
 *  
 * 
 * @param aName java.lang.String
 */
public static void removeConnectionManager(String aName) {

	int i;
	for( i=0; i< resourceNames.size(); i++)
	{
		String rn = (String)resourceNames.elementAt(i);
		if( rn.compareTo( aName ) == 0)
		{
			resourceNames.removeElementAt(i);
			connectionManagers.removeElementAt(i);
			return;
		}
	}
}
}
