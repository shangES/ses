package icbc.cmis.base;

/**
 * 
 *  @(#)ConnectionManager.java	1.0 10/18/99
 *  Copyright (c) 1999 Nantian R&D. All Rights Reserved.
 * 
 * 
 *   @version 1.0 10/18/99
 *   @author  ZhongMingChang
 *  
 */

 import java.sql.*;
import java.io.PrintStream;
import java.util.Vector;

public class ConnectionManager {

	private String driverName = "COM.ibm.db2.jdbc.app.DB2Driver";
	private String dbURL = "jdbc:db2:CBTFDEMO";
	private String dbUserName = "db2admin";
	private String dbPassword = "yangge";
	private Vector dbConnectionList = new Vector(10);
	private int maxCon = 10;

	private int connectionTimeOut = 180000;			private int idleTimeOut = 300000;			public static int inUseConnections = 0;
/**
 * ConnectionManager constructor comment.
 */
public ConnectionManager() {
	super();
	initialize();
}
/**
 * ConnectionManager constructor comment.
 */
public ConnectionManager(String driverName, String dbURL, String dbUserName, String dbPassword) {

	super();
	this.driverName = driverName;
	this.dbURL = dbURL;
	this.dbUserName = dbUserName;
	this.dbPassword = dbPassword;

	initialize();
}
/**
 * ConnectionManager constructor comment.
 */
public void close()
{
	try
	{
		for (int i = 0; i < dbConnectionList.size(); i++)
		{
			DbConnection dbConnection = (DbConnection) dbConnectionList.elementAt(i);
			Connection connection = dbConnection.getConnection();
			if( !connection.isClosed() )
				connection.close();
		}
		dbConnectionList.removeAllElements();
	} catch (Exception e)
	{
		System.out.println(e.toString());
	}
}
/**
 * ConnectionManager constructor comment.
 */
public void finalize()
{
	close();
}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  03/23/2000
 *  
 * 
 * @return int
 */
public int getActiveConnections() {
	int cons = 0;
	for (int i = 0; i < dbConnectionList.size(); i++)
	{
		DbConnection dbConnection1 = (DbConnection) dbConnectionList.elementAt(i);
		if (dbConnection1.isInUse())
		{
			cons++;
		}
	}
	return cons;

}
/**
 * ConnectionManager constructor comment.
 */
public synchronized Connection getConnection() throws SQLException {
	for (int i = 0; i < dbConnectionList.size(); i++)
	{
		DbConnection dbConnection1 = (DbConnection) dbConnectionList.elementAt(i);
		if (!dbConnection1.isInUse())
		{
			dbConnection1.setInUse(true);
			{
				Connection aConnection = dbConnection1.getConnection();
				if (aConnection.isClosed()) //the connection has been closed so reestablish the connection!!
				{
					aConnection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
					aConnection.setAutoCommit(false);
				}

				//System.out.println("get Connection " + i);
				inUseConnections++;
				return dbConnection1.getConnection();
			}
		}
	}
	if (dbConnectionList.size() >= maxCon)
	{
		int times = 0;
		long begWaitTime = System.currentTimeMillis();
		long timeToWait = connectionTimeOut;
		while (true)
		{

			try
			{
				wait(timeToWait);
			}
			catch (InterruptedException e)
			{
			}

			int j = 0;
			while (j < dbConnectionList.size())
			{
				DbConnection dbConnection2 = (DbConnection) dbConnectionList.elementAt(j);
				if (!dbConnection2.isInUse())
				{
					dbConnection2.setInUse(true);

					//System.out.println("get Connection " + j);

					inUseConnections++;
					
					return dbConnection2.getConnection();
				}
				j++;
			}

			int timeWaited = (int)(System.currentTimeMillis() - begWaitTime);
			timeToWait = connectionTimeOut - timeWaited;
			
			if( timeWaited >= connectionTimeOut )
			{
				System.out.println("Wait for free db connection time out!");
				return null;
			}
		}
	}
	else
	{
		try
		{
			Connection connection = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
			DbConnection dbConnection3 = new DbConnection(connection, this.idleTimeOut);
			dbConnectionList.addElement(dbConnection3);
			connection.setAutoCommit(false);
			//System.out.println("get new Connection " + dbConnectionList.size() );
			inUseConnections++;
			connection.close();
			return connection;
		}
		catch (SQLException e)
		{
			System.out.println(new StringBuffer("[ConnectionManager]:").append(e.toString()).toString());
			throw e;
		}
	}
}
public synchronized Connection getConnection(String userName,String userPass) throws SQLException {
	Vector vOtherUserC = new Vector();
	for (int i = 0; i < dbConnectionList.size(); i++)
	{
		DbConnection dbConnection1 = (DbConnection) dbConnectionList.elementAt(i);
		if (!dbConnection1.isInUse())
		{
			if(dbConnection1.getUserName().equals(userName.trim()) && dbConnection1.getUserPass().trim().equals(userPass.trim())){
				dbConnection1.setInUse(true);
				{
					Connection aConnection = dbConnection1.getConnection();
					if (aConnection.isClosed()) //the connection has been closed so reestablish the connection!!
					{
						aConnection = DriverManager.getConnection(dbURL, dbConnection1.getUserName(), dbConnection1.getUserPass());
						aConnection.setAutoCommit(false);
						dbConnection1.setConnection(aConnection);
					}

					//System.out.println("get Connection " + i);
					inUseConnections++;
					return dbConnection1.getConnection();
				}
			}else{
				vOtherUserC.addElement(dbConnection1);
				
			}
		}
	}
	
	for(int x = 0;x < vOtherUserC.size(); x++){
		DbConnection otherC = (DbConnection)vOtherUserC.elementAt(x);
		if(isCanUsing(otherC,userName,userPass) != null){
			return otherC.getConnection();
		}
	}
	
	if (dbConnectionList.size() >= maxCon)
	{
		int times = 0;
		long begWaitTime = System.currentTimeMillis();
		long timeToWait = connectionTimeOut;
		while (true)
		{

			try
			{
				wait(timeToWait);
			}
			catch (InterruptedException e)
			{
			}

			int j = 0;
			Vector vTmp = new Vector();
			while (j < dbConnectionList.size())
			{
				DbConnection dbConnection2 = (DbConnection) dbConnectionList.elementAt(j);
				if (!dbConnection2.isInUse())
				{
					if(dbConnection2.getUserName().equals(userName.trim()) && dbConnection2.getUserPass().trim().equals(userPass.trim())){
			
						dbConnection2.setInUse(true);

						//System.out.println("get Connection " + j);

						inUseConnections++;
					
						return dbConnection2.getConnection();
					}else{
						vTmp.addElement(dbConnection2);
					}
					
				}
				j++;
			}
			for(int yy = 0; yy < vTmp.size(); yy++){
				DbConnection tmpC = (DbConnection)vTmp.elementAt(yy);
				if(isCanUsing(tmpC,userName,userPass) != null){
					return tmpC.getConnection();
				}
			}

			int timeWaited = (int)(System.currentTimeMillis() - begWaitTime);
			timeToWait = connectionTimeOut - timeWaited;
			
			if( timeWaited >= connectionTimeOut )
			{
				System.out.println("Wait for free db connection time out!");
				return null;
			}
		}
	}
	else
	{
		try
		{
			Connection connection = DriverManager.getConnection(dbURL, userName, userPass);
			DbConnection dbConnection3 = new DbConnection(connection, this.idleTimeOut);
			dbConnection3.setUserName(userName);
			dbConnection3.setUserPass(userPass);
			
			dbConnectionList.addElement(dbConnection3);
			connection.setAutoCommit(false);
			//System.out.println("get new Connection " + dbConnectionList.size() );
			inUseConnections++;
		//	connection.close();
			return connection;
		}
		catch (SQLException e)
		{
			System.out.println(new StringBuffer("[ConnectionManager]:").append(e.toString()).toString());
			throw e;
		}
	}
}/**
 * Insert the method's description here.
 * Creation date: (2001-12-31 13:31:47)
 * @return java.sql.Connection
 * @param dbr com.zmc.base.DataBase.DbConnection
 */
/**
 * 
 *  This method was created by ZhongMingChang.
 *  03/23/2000
 *  
 * 
 * @return int
 */
public int getConnections() {
	return dbConnectionList.size();
}
/**
 * ConnectionManager constructor comment.
 */
public String getDBDriver()
{
	return driverName;
}
/**
 * ConnectionManager constructor comment.
 */
public String getDBPassword()
{
	return dbPassword;
}
/**
 * ConnectionManager constructor comment.
 */
public String getDBURL()
{
	return dbURL;
}
/**
 * ConnectionManager constructor comment.
 */
	public String getDBUserID()
	{
		return dbUserName;
	}
/**
 * ConnectionManager constructor comment.
 */
	public String getMaxCon()
	{
		return new Integer(maxCon).toString();
	}
/**
 * 
 *  This method was created by ZhongMingChang.
 *  03/23/2000
 *  
 * 
 * @return int
 */
public int getMaxDBCon() {
	return maxCon;
}
/**
 * ConnectionManager constructor comment.
 */
public void initialize()
{
	try
	{
		Class aclass = Class.forName(driverName);
		Driver driver = (Driver) aclass.newInstance();
		DriverManager.registerDriver(driver);
	} catch (Exception e)
	{
		System.out.println("Exception from ConnectionManager.initialize():" + e);
	}
}
private Connection isCanUsing(DbConnection otherC,String userName,String userPass) throws SQLException{
	
	Connection con = otherC.getConnection();
	if(!otherC.isInUse()){
		if(otherC.isTimeout() && !con.isClosed()){
			con.close();
		}
		if(con.isClosed()){
			otherC.setUserName(userName.trim());
			otherC.setUserPass(userPass.trim());
			con = DriverManager.getConnection(dbURL, userName, userPass);
			con.setAutoCommit(false);
			otherC.setInUse(true);
			otherC.setConnection(con);
			inUseConnections++;
			return otherC.getConnection();
		}
	}
	return null;
}
/**
 * ConnectionManager constructor comment.
 */
public synchronized void releaseConnection(Connection connection)
{
	for (int i = 0; i < dbConnectionList.size(); i++)
	{
		DbConnection dbConnection = (DbConnection) dbConnectionList.elementAt(i);
		if (dbConnection.getConnection() == connection)
		{
			if( dbConnection.isInUse() )
			{
				inUseConnections--;
				dbConnection.setInUse(false);
				notifyAll();
				return;
			}
			else
			{
				System.out.println("warnning release a not in used connection!");
				return;
			}
		}
	}
	if( connection != null )
		System.out.println("Get a valid connection (can't be found in connections list)!");
}
/**
 * ConnectionManager constructor comment.
 */
public synchronized void reset()
{
	close();
}
public void setConnectionTimeOut(int timeOut)
{
	this.connectionTimeOut = timeOut;
}/**
 * Insert the method's description here.
 * Creation date: (2001-4-29 9:30:04)
 * @param timeOut int
 */
/**
 * ConnectionManager constructor comment.
 */
public void setDBDriver(String string)
{
	driverName = string;
}
/**
 * ConnectionManager constructor comment.
 */
public void setDBPassword(String string)
{
	dbPassword = string;
}
/**
 * ConnectionManager constructor comment.
 */
public void setDBURL(String string)
{
	dbURL = string;
}
/**
 * ConnectionManager constructor comment.
 */
public void setDBUserID(String string)
{
	dbUserName = string;
}
public void setIdleTimeOut(int timeOut)
{
	this.idleTimeOut = timeOut;
}/**
 * ConnectionManager constructor comment.
 */
public void setMaxDBCon(int maxCons)
{
		maxCon = maxCons;
}/**
 * ConnectionManager constructor comment.
 */
/**
 * ConnectionManager constructor comment.
 */
public void setMaxDBCon(String string)
{
		try
	{
		maxCon = new Integer(string).intValue();
	} catch (Exception e)
	{
		System.out.println(new StringBuffer("Exception in set Max DBCon:").append(e.toString()).toString());
	}
}
}
