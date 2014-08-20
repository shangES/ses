package icbc.cmis.base;

/**
 * 
 *  @(#)SerialGenerator.java	1.0 11/08/99
 *  Copyright (c) 1999 Nantian R&D. All Rights Reserved.
 * 
 * 
 *   @version 1.0 11/08/99
 *   @author  ZhongMingChang
 *  
 */


 import java.sql.*;
 import java.io.*;
 
public class SerialGenerator {
/**
 * SerialGenerator constructor comment.
 */
public SerialGenerator() {
	super();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  03/02/2000
 *  
 * 
 * @param con java.sql.Connection
 * @param sqlContent java.lang.String
*
*	Procedure for execute a pagraph of SQL Commands 
 */
public static void executSQL(Connection connection, String SQLContent) throws Exception{


	if( SQLContent.length() < 5)
		return;
	

	LineNumberReader li = new LineNumberReader(new java.io.StringReader(SQLContent) );
	Statement statement = connection.createStatement();
	String strSQL = "";
	
	while(true)
	{
		String aline = li.readLine();
		if( aline == null )
			break;
		aline = aline.trim();
		if( aline.length() == 0)
			continue;
		strSQL = strSQL + aline;

		if( aline.indexOf(";", 0) != -1)
		{
			statement.execute( strSQL );
			strSQL = "";
		}
	}
	statement.close();
}
/**
 * SerialGenerator constructor comment.
 */

public static synchronized int getSerial(int i) throws Exception
{
	int j = 0;
	Connection connection = ConnectionController.getConnection();
	Statement statement = null;
	boolean flag = true;
	try
	{
		statement = connection.createStatement();
		String string = "select ID from IDENTITY where Index = ";
		string = string + new Integer(i).toString();
		ResultSet resultSet = statement.executeQuery(string);
		resultSet.next();
		j = resultSet.getInt("ID");
		string = "Update IDENTITY set ID = ID+1 where Index = ";
		string = new StringBuffer(String.valueOf(string)).append(new Integer(i).toString()).toString();
		statement.executeUpdate(string);
	} catch (Exception e1)
	{
		flag = false;
		System.out.println(e1.toString());
	}
	try
	{
		if (flag)
			connection.commit();
		else
			connection.rollback();
		ConnectionController.releaseConnection(connection);
		statement.close();
	} catch (Exception e)
	{
		System.out.println(e);
	}
	return j;
}
/**
 * SerialGenerator constructor comment.
 */

public static synchronized int getSerial(int i, String resource) throws Exception
{
	int j = 0;
	Connection connection = ConnectionController.getConnection(resource);
	Statement statement = null;
	boolean flag = true;
	try
	{
		statement = connection.createStatement();
		String string = "select ID from IDENTITY where Index = ";
		string = string + new Integer(i).toString();
		ResultSet resultSet = statement.executeQuery(string);
		resultSet.next();
		j = resultSet.getInt("ID");
		string = "Update IDENTITY set ID = ID+1 where Index = ";
		string = new StringBuffer(String.valueOf(string)).append(new Integer(i).toString()).toString();
		statement.executeUpdate(string);
	} catch (Exception e1)
	{
		flag = false;
		System.out.println(e1.toString());
	}
	try
	{
		if (flag)
			connection.commit();
		else
			connection.rollback();
		ConnectionController.releaseConnection(connection);
		statement.close();
	} catch (Exception e)
	{
		System.out.println(e);
	}
	return j;
}
/**
 * SerialGenerator constructor comment.
 */

public static synchronized int getSerial(int i, Connection connection)
{
	int j = 0;
//	Connection connection = ConnectionControler.getConnection(resource);
	Statement statement = null;
	boolean flag = true;
	try
	{
		statement = connection.createStatement();
		String string = "select ID from IDENTITY where Index = ";
		string = string + new Integer(i).toString();
		ResultSet resultSet = statement.executeQuery(string);
		resultSet.next();
		j = resultSet.getInt("ID");
		string = "Update IDENTITY set ID = ID+1 where Index = ";
		string = new StringBuffer(String.valueOf(string)).append(new Integer(i).toString()).toString();
		statement.executeUpdate(string);
	} catch (Exception e1)
	{
		flag = false;
		System.out.println(e1.toString());
	}
	try
	{
		if (flag)
			connection.commit();
		else
			connection.rollback();
//		ConnectionControler.releaseConnection(connection);
		statement.close();
	} catch (Exception e)
	{
		System.out.println(e);
	}
	return j;
}
}
