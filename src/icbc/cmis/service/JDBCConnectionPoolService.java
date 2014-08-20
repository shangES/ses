package icbc.cmis.service;

import java.util.Hashtable;

import icbc.cmis.base.*;
import java.sql.*;



import icbc.cmis.base.*;public class JDBCConnectionPoolService implements JDBCConnectionPool {

	//public static JDBCConnectionPoolService JDBCPool = null;

	private Hashtable JDBCResources = new Hashtable();
public JDBCConnectionPoolService() { 
// Default constructor
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-6 14:40:49)
 */
public void addJDBCResource(DBResource dbResource) {
	
	ConnectionManager cm = new ConnectionManager(dbResource.driverName, dbResource.dbURL, dbResource.dbUserName, dbResource.dbPassword);

	cm.setIdleTimeOut( dbResource.idleTimeOut );
	cm.setConnectionTimeOut( dbResource.connectionTimeOut );
	cm.setMaxDBCon( dbResource.maxConnection );
	this.JDBCResources.put(dbResource.resourceName, cm );

}
public Connection getConnection(String resourceName) throws TranFailException
{
	
	ConnectionManager cm = (ConnectionManager)JDBCResources.get(resourceName);
	if( cm!=null)
	{

		try{
			Connection connection  = cm.getConnection();
			String enableSqlAnalyser = (String)CmisConstance.getParameterSettings().get("enableSqlAnalyser");
			if(enableSqlAnalyser != null && enableSqlAnalyser.trim().equals("true")){
				return new SQLConnection(connection);
			}else{
				return connection;
			}	
		}catch(Exception e)
		{
			throw new TranFailException("xdtz22112", "JDBCConnectionConnectionPoolService.getConnection(String)", e.getMessage());
			
		}


	}
	else
		throw new TranFailException("xdtz22112", "JDBCConnectionConnectionPoolService.getConnection(String)", "JDBC Resource[" + resourceName +"] not found!");
}
public Connection getConnection(String resourceName,String userName,String userPass) throws TranFailException
{
	
	ConnectionManager cm = (ConnectionManager)JDBCResources.get(resourceName);
	if( cm!=null)
	{

		try{
			Connection connection  = cm.getConnection(userName,userPass);
			String enableSqlAnalyser = (String)CmisConstance.getParameterSettings().get("enableSqlAnalyser");
			if(enableSqlAnalyser != null && enableSqlAnalyser.trim().equals("true")){
				return new SQLConnection(connection);
			}else{
				return connection;
			}
		}catch(Exception e)
		{
			throw new TranFailException("xdtz22112", "JDBCConnectionConnectionPoolService.getConnection(String)", e.getMessage());
			
		}


	}
	else
		throw new TranFailException("xdtz22112", "JDBCConnectionConnectionPoolService.getConnection(String)", "JDBC Resource[" + resourceName +"] not found!");
}
/**
 * Insert the method's description here.
 * Creation date: (2001-4-29 11:35:51)
 * @return com.zmc.base.DataBase.ConnectionManager
 * @param resourceId java.lang.String
 */
public ConnectionManager getConnectionManager(String resourceId) {

	ConnectionManager cm = (ConnectionManager)JDBCResources.get(resourceId);	
	return cm;
}
public void releaseConnection(String resourceName, Connection connection) {

	try {
		String enableSqlAnalyser = (String)CmisConstance.getParameterSettings().get("enableSqlAnalyser");
		if(enableSqlAnalyser != null && enableSqlAnalyser.trim().equals("true")){
			if ((((SQLConnection)connection).getConnection()) instanceof oracle.jdbc.driver.OracleConnection)
				{
					((oracle.jdbc.driver.OracleConnection) (((SQLConnection)connection).getConnection())).close();//.close_statements();
				}
			}else{
				if (connection instanceof oracle.jdbc.driver.OracleConnection)
				{
					((oracle.jdbc.driver.OracleConnection) connection).close();//.close_statements();
				}
			}
	}
	catch (Exception e) {
	}
	ConnectionManager cm = (ConnectionManager) JDBCResources.get(resourceName);
	cm.releaseConnection(((SQLConnection)connection).getConnection());

}
}
