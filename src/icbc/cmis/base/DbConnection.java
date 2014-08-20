package icbc.cmis.base;

/**
 * 
 *  @(#)DbConnection.java	1.0 10/18/99
 *  Copyright (c) 1999 Nantian R&D. All Rights Reserved.
 * 
 * 
 *   @version 1.0 10/18/99
 *   @author  ZhongMingChang
 *  
 */

import java.sql.Connection;

public class DbConnection
{

    private boolean inUse = true;
    static int inst;
    private int serial;

    private Connection connection;
    private int idleTimeOut = 300000;
    private long lastUsed = System.currentTimeMillis();
    private String userName = null;
    private String userPass = null;
    /**
    * Insert the method's description here.
    * Creation date: (2001-12-31 11:10:41)
    * @param userName java.lang.String
    */
	public DbConnection()
	{
		connection = null;
		inUse = true;
		serial = 0;
	}
	public DbConnection(Connection connection)
	{

		inUse = true;
		serial = 0;
		this.connection = connection;
		serial = inst++;
	}
public DbConnection(Connection connection, int idleTimeOut)
	{
		this.connection = connection;
		inUse = true;
		serial = 0;

		serial = inst++;
		this.idleTimeOut = idleTimeOut;
	}
	public Connection getConnection()
	{
		return connection;
	}
public String  getUserName() {
	return userName;
}/**
 * Insert the method's description here.
 * Creation date: (2001-12-31 11:10:41)
 * @param userName java.lang.String
 */
public String getUserPass() {
	return userPass;
}/**
 * Insert the method's description here.
 * Creation date: (2001-12-31 12:32:59)
 * @return boolean
 */
public synchronized boolean isInUse()
{
	if( inUse )
	{
		if( ( System.currentTimeMillis() - lastUsed ) > idleTimeOut )
		{
			System.out.println("Conncetion has been hold for [" + (idleTimeOut/1000) + "]sec.");
			inUse = false;
		}
	}

	return inUse;
	
}
public boolean isTimeout() {
	if( ( System.currentTimeMillis() - lastUsed ) > idleTimeOut )
		{
			//inUse = true;
			setInUse(true);
			return true;
		}else
		{
			return false;
		}
}
public synchronized void setConnection(Connection con)
{
	connection = con;
	
}/**
 * Insert the method's description here.
 * Creation date: (2001-12-31 11:10:41)
 * @param userName java.lang.String
 */
public synchronized void setInUse(boolean flag)
{
	inUse = flag;
	if( inUse )
		lastUsed = System.currentTimeMillis();
	else
		lastUsed = 0;
	
}
public void setUserName(String userName1) {
	userName = userName1;
}/**
 * Insert the method's description here.
 * Creation date: (2001-12-31 11:10:41)
 * @param userName java.lang.String
 */
public void setUserPass(String userPass1) {
	userPass = userPass1;
}
}
