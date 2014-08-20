package icbc.cmis.base;

/**
 * 
 *  @(#)DataAccessCtrol.java	1.0 10/18/99
 *  Copyright (c) 1999 Nantian R&D. All Rights Reserved.
 * 
 * 
 *   @version 1.0 11/08/99
 *   @author  ZhongMingChang
 *  
 */


import java.sql.*;
import java.io.PrintStream;
import java.util.Vector;

public class DataAccessCtrol
{
	private static String driverName;
	private static String dbURL;
	private static String dbUserName;
	private static String dbPassword;
	private static Vector dbConnectionList;
	private static int maxCon;

	static 
	{
		driverName = "COM.ibm.db2.jdbc.app.DB2Driver";
		dbURL = "jdbc:db2:CBTFDEMO";
		dbUserName = "db2admin";
		dbPassword = "cbtf";
		dbConnectionList = new Vector(10);
		maxCon = 10;
	}

	public DataAccessCtrol()
	{
		initialize();
	}
	public static void close()
	{
		try
		{
			for (int i = 0; i < dbConnectionList.size(); i++)
			{
				DbConnection dbConnection = (DbConnection)dbConnectionList.elementAt(i);
				Connection connection = dbConnection.getConnection();
				connection.close();
			}
			dbConnectionList.removeAllElements();
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}
/**
 * This method was created by ZhongMingChang.
 */
public void finalize() {
	close();
}
	public static synchronized Connection getConnection()
	{
		for (int i = 0; i < dbConnectionList.size(); i++)
		{
			DbConnection dbConnection1 = (DbConnection)dbConnectionList.elementAt(i);
			if (!dbConnection1.isInUse())
			{
				dbConnection1.setInUse(true);
				return dbConnection1.getConnection();
			}
		}
		if (dbConnectionList.size() > maxCon)
		{
			System.out.println(maxCon + " !!");
			while (true)
			{
				int j = 0;
				while (j < dbConnectionList.size())
				{
					DbConnection dbConnection2 = (DbConnection)dbConnectionList.elementAt(j);
					if (!dbConnection2.isInUse())
					{
						dbConnection2.setInUse(true);
						return dbConnection2.getConnection();
					}
					j++;
				}
			}
		}
		try
		{
			Connection connection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
			DbConnection dbConnection3 = new DbConnection(connection);
			dbConnectionList.addElement(dbConnection3);
			connection.setAutoCommit(false);
			return connection;
		}
		catch (SQLException e)
		{
			System.out.println(new StringBuffer("[DataAccessCtrl]:").append(e.toString()).toString());
		}
		return null;
	}
	public static String getDBDriver()
	{
		return driverName;
	}
	public static String getDBPassword()
	{
		return dbPassword;
	}
	public static String getDBURL()
	{
		return dbURL;
	}
	public static String getDBUserID()
	{
		return dbUserName;
	}
	public static String getMaxCon()
	{
		return new Integer(maxCon).toString();
	}
	public static void initialize()
	{
		try
		{
			Class aclass = Class.forName(driverName);
			Driver driver = (Driver)aclass.newInstance();
			DriverManager.registerDriver(driver);
		}
		catch (Throwable throwable)
		{
			System.out.println(new StringBuffer("e0:").append(throwable.toString()).toString());
		}
	}
	void newMethod()
	{
	}
	public static synchronized void releaseConnection(Connection connection)
	{
		for (int i = 0; i < dbConnectionList.size(); i++)
		{
			DbConnection dbConnection = (DbConnection)dbConnectionList.elementAt(i);
			if (dbConnection.getConnection() == connection)
				dbConnection.setInUse(false);
		}
	}
	public static void reset()
	{
		close();
	}
	public static void setDBDriver(String string)
	{
		driverName = string;
	}
	public static void setDBPassword(String string)
	{
		dbPassword = string;
	}
	public static void setDBURL(String string)
	{
		dbURL = string;
	}
	public static void setDBUserID(String string)
	{
		dbUserName = string;
	}
	public static void setMaxDBCon(String string)
	{
		try
		{
			maxCon = new Integer(string).intValue();
		}
		catch (Exception e)
		{
			System.out.println(new StringBuffer("Exception in set Max DBCon:").append(e.toString()).toString());
		}
	}
}
