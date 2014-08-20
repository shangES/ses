package icbc.cmis.service;

/**
 * Insert the type's description here.
 * Creation date: (2001-3-29 13:04:12)
 * @author: Administrator
 */

import java.sql.*;



 
import icbc.cmis.base.*;

public interface JDBCConnectionPool {


public Connection getConnection(String resourceName) throws TranFailException;
public void releaseConnection(String resourceName, Connection connection);
public Connection getConnection(String resourceName,String userName,String userPass) throws TranFailException;
}