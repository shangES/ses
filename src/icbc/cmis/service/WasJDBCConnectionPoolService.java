package icbc.cmis.service;

import java.util.Hashtable;

import java.sql.*;

//import com.ibm.ejs.dbm.jdbcext.*;
import javax.naming.*;

import icbc.cmis.base.*;
import org.w3c.dom.*;

//import com.ibm.ejs.cm.exception.WorkRolledbackException;
public class WasJDBCConnectionPoolService implements JDBCConnectionPool {

    private Hashtable JDBCResources = new Hashtable();
    private Hashtable ResourceDefines = new Hashtable();

    public static int cons = 0; /**
    * getConnection method comment.
    */
/**
 * WasJDBCConnectionPoolService constructor comment.
 */
public WasJDBCConnectionPoolService() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-1-6 14:40:49)
 */
public void addJDBCResource(DBResource dbr)throws TranFailException
{

	//Get the initial context
//in was3.5
//	Hashtable parms = new Hashtable();
//	parms.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.ibm.ejs.ns.jndi.CNInitialContextFactory");

//in was4.0
//java.util.Properties parms = new java.util.Properties();
//parms.setProperty(javax.naming.Context.INITIAL_CONTEXT_FACTORY,"com.ibm.websphere.naming.WsnInitialContextFactory");


//in was 5.0 no need 20050217 chenj
//	Hashtable parms = new Hashtable();
//	parms.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");


	//parms.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");

	//parms.put(javax.naming.Context.PROVIDER_URL, "iiop:///");
	try
	{
		//javax.naming.Context ctx = new InitialContext(parms);
//		in was 5.0 no need parms 20050217 chenj		
		javax.naming.Context ctx = new InitialContext();

		javax.sql.DataSource ds = null;
		ds = (javax.sql.DataSource) ctx.lookup("jdbc/" + dbr.resourceName);

		JDBCResources.put(dbr.resourceName, ds);
		ResourceDefines.put(dbr.resourceName, dbr);

	}
	catch (Exception e)
	{
		throw new TranFailException("xdtz22117","WasJDBCConnectionPoolService.addJDBCResource(DBResource dbr)","Failed to initialize the JDBCResource[" + dbr.resourceName + "]\nException: " + e.getMessage());
	}
}
/**
 * getConnection method comment.
 */
public java.sql.Connection getConnection(String resourceName) throws TranFailException {

	try{

//		System.out.println("Aply For connection....");
		javax.sql.DataSource  ds = (javax.sql.DataSource)JDBCResources.get( resourceName.trim() );
		DBResource dbr = (DBResource)this.ResourceDefines.get(resourceName.trim());
		Connection con = ds.getConnection(dbr.dbUserName, dbr.dbPassword);

		if( con == null){

			throw new TranFailException("xdtz22112", "WasJDBCConnectionPoolService.getConnection(String)", "Failed to get dbConnection!");
		}
		con.setAutoCommit(false);
//		synchronized(this)
//		{
//			cons++;
//		}

		String enableSqlAnalyser = (String)CmisConstance.getParameterSettings().get("enableSqlAnalyser");
		if(enableSqlAnalyser != null && enableSqlAnalyser.trim().equals("true")){
			return new SQLConnection(con);
		}else{
			return con;
		}
	}catch(Exception e)
	{
		throw new TranFailException("xdtz22112", "WasJDBCConnectionPoolService.getConnection(String)", e.getMessage());
	}
}
public java.sql.Connection getConnection(String resourceName,String userName,String userPass) throws TranFailException {

	try{

//		System.out.println("Aply For connection....");
		javax.sql.DataSource  ds = (javax.sql.DataSource)JDBCResources.get( resourceName.trim() );
		DBResource dbr = (DBResource)this.ResourceDefines.get(resourceName.trim());
		Connection con = ds.getConnection(userName, userPass);

		if( con == null){

			throw new TranFailException("xdtz22112", "WasJDBCConnectionPoolService.getConnection(String resourceName,String userName,String userPass)", "Failed to get dbConnection!");
		}
		con.setAutoCommit(false);
//		synchronized(this)
//		{
//			cons++;
//		}
		String enableSqlAnalyser = (String)CmisConstance.getParameterSettings().get("enableSqlAnalyser");
		if(enableSqlAnalyser != null && enableSqlAnalyser.trim().equals("true")){
			return new SQLConnection(con);
		}else{
			return con;
		}
	}catch(Exception e)
	{
		throw new TranFailException("xdtz22112", "WasJDBCConnectionPoolService.getConnection(String resourceName,String userName,String userPass)", e.getMessage());
	}
}
/**
 * releaseConnection method comment.
 */
public void releaseConnection(String resourceName, java.sql.Connection connection)
{
	try
	{
		if(connection != null){

			try
			{
				String enableSqlAnalyser = (String)CmisConstance.getParameterSettings().get("enableSqlAnalyser");
				if(enableSqlAnalyser != null && enableSqlAnalyser.trim().equals("true")){
					if ((((SQLConnection)connection).getConnection()) instanceof oracle.jdbc.driver.OracleConnection)
					{
						((oracle.jdbc.driver.OracleConnection) (((SQLConnection)connection).getConnection())).close_statements();
					}
				}else{
					if (connection instanceof oracle.jdbc.driver.OracleConnection)
					{
						((oracle.jdbc.driver.OracleConnection) connection).close_statements();
					}
				}
			}
			catch (Exception e)
			{
			}
			connection.rollback();
			connection.close();
//			System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa1");
			connection = null;
//			System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
//			synchronized(this)
//			{
//				cons--;
//			}

		}

		}
//		catch (WorkRolledbackException e){
//			
//			
//		}
		catch (Exception e)
		{
			connection = null;
            // The following line is added to avoid the annoying message regarding with the setAutoCommit()
            // rolledback bug in Oracle 9i jdbc driver
//            if (!(e instanceof com.ibm.ejs.cm.exception.WorkRolledbackException)){
//            				System.out.println(e.getClass().toString());
//			     System.out.println("Failed to release connection!\nException:" + e.getMessage());
//            }
		}

}
}
