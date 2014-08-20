package icbc.cmis.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Insert the type's description here.
 * Creation date: (2002-12-19 18:19:02)
 * @author: Administrator
 */
public class SQLStatement implements java.sql.Statement {
	private java.sql.PreparedStatement statement = null;
	private java.sql.Statement inStatement = null;
	private java.sql.Connection connection  = null;
/**
 * SQLStatement constructor comment.
 */
public SQLStatement(java.sql.Connection conn) {
	super();
	connection = conn;
	
}
	/**
	 * JDBC 2.0
	 *
	 * Adds a SQL command to the current batch of commmands for the statement.
	 * This method is optional.
	 *
	 * @param sql typically this is a static SQL INSERT or UPDATE statement
	 * @exception SQLException if a database access error occurs, or the
	 * driver does not support batch statements
	 */
public void addBatch(String sql) throws java.sql.SQLException {
	if(inStatement == null){
		inStatement = connection.createStatement();
	}
	inStatement.addBatch(sql);
	//throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.addBatch(String sql)]无效");
}
	/**
	 * Cancels this <code>Statement</code> object if both the DBMS and
	 * driver support aborting an SQL statement.
	 * This method can be used by one thread to cancel a statement that
	 * is being executed by another thread.
	 *
	 * @exception SQLException if a database access error occurs
	 */
public void cancel() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.cancel()]无效");
}
/**
 * JDBC 2.0
 *
 * Makes the set of commands in the current batch empty.
 * This method is optional.
 *
 * @exception SQLException if a database access error occurs or the
 * driver does not support batch statements
 */
public void clearBatch() throws java.sql.SQLException
{
	inStatement.clearBatch();
}
/**
 * Clears all the warnings reported on this <code>Statement</code>
 * object. After a call to this method,
 * the method <code>getWarnings</code> will return 
 * null until a new warning is reported for this Statement.  
 *
 * @exception SQLException if a database access error occurs
 */
public void clearWarnings() throws java.sql.SQLException
{
    throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.clearWarnings()]无效");
}
/**
 * Releases this <code>Statement</code> object's database 
 * and JDBC resources immediately instead of waiting for
 * this to happen when it is automatically closed.
 * It is generally good practice to release resources as soon as
 * you are finished with them to avoid tying up database
 * resources.
 * <P><B>Note:</B> A Statement is automatically closed when it is
 * garbage collected. When a Statement is closed, its current
 * ResultSet, if one exists, is also closed.  
 *
 * @exception SQLException if a database access error occurs
 */
public void close() throws java.sql.SQLException
{
	if(statement != null){
    	statement.close();
	}
	if(inStatement != null){
		inStatement.close();
	}
}
	/**
	 * Executes a SQL statement that may return multiple results.
	 * Under some (uncommon) situations a single SQL statement may return
	 * multiple result sets and/or update counts.  Normally you can ignore
	 * this unless you are (1) executing a stored procedure that you know may
	 * return multiple results or (2) you are dynamically executing an
	 * unknown SQL string.  The  methods <code>execute</code>,
	 * <code>getMoreResults</code>, <code>getResultSet</code>,
	 * and <code>getUpdateCount</code> let you navigate through multiple results.
	 *
	 * The <code>execute</code> method executes a SQL statement and indicates the
	 * form of the first result.  You can then use getResultSet or
	 * getUpdateCount to retrieve the result, and getMoreResults to
	 * move to any subsequent result(s).
	 *
	 * @param sql any SQL statement
	 * @return true if the next result is a ResultSet; false if it is
	 * an update count or there are no more results
	 * @exception SQLException if a database access error occurs
	 * @see #getResultSet
	 * @see #getUpdateCount
	 * @see #getMoreResults 
	 */
public boolean execute(String sql) throws java.sql.SQLException {
	if(statement != null){
		statement.close();
		statement = null;
	}
	Hashtable vValue = new Hashtable(10);
	String tmpSql = SQLAnalyser.getAnalyserSQL(sql,vValue);
	statement = connection.prepareStatement(tmpSql);
	String value = varBind(statement,vValue);
	try{
		return statement.execute();
	}catch(SQLException esc){
		throw new CmisSQLException(esc.getErrorCode(),esc.getSQLState(),esc.getMessage()+"||sql:"+tmpSql+"|value:"+value);
	}
}
public String  varBind(PreparedStatement stmt,Hashtable hTable)throws SQLException{
	Enumeration keys = hTable.keys();
	int i = 0;
	StringBuffer buffer = new StringBuffer();
	for(;keys.hasMoreElements();keys.nextElement()){
		i++;
		String[] tmpArray = (String[])hTable.get(new Integer(i));
		if(tmpArray[0].equals("S")){
			stmt.setString(i,tmpArray[1]);
		}else if(tmpArray[0].equals("D")){
			stmt.setDouble(i,Double.parseDouble(tmpArray[1]));
		}else if(tmpArray[0].equals("I")){
			stmt.setInt(i,Integer.parseInt(tmpArray[1]));
		}else if(tmpArray[0].equals("F")){
			stmt.setFloat(i,Float.parseFloat(tmpArray[1]));
		}else{
			throw new SQLException("SQL查询失败，变量绑定异常：无效的变量类型！"); 
		}
		buffer.append(tmpArray[0]+"."+tmpArray[1]);
		icbc.cmis.base.CmisConstance.pringMsg("param:type，"+tmpArray[0]+";value,"+tmpArray[1])	;
	}
	return new String(buffer);
}
	/**
	 * JDBC 2.0
	 * 
	 * Submits a batch of commands to the database for execution.
	 * This method is optional.
	 *
	 * @return an array of update counts containing one element for each
	 * command in the batch.  The array is ordered according 
	 * to the order in which commands were inserted into the batch.
	 * @exception SQLException if a database access error occurs or the
	 * driver does not support batch statements
	 */
public int[] executeBatch() throws java.sql.SQLException {
	 return inStatement.executeBatch();
	//throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.executeBatch()]无效");
}
	/**
	 * Executes a SQL statement that returns a single ResultSet.
	 *
	 * @param sql typically this is a static SQL SELECT statement
	 * @return a ResultSet that contains the data produced by the
	 * query; never null 
	 * @exception SQLException if a database access error occurs
	 */
public java.sql.ResultSet executeQuery(String sql) throws java.sql.SQLException {
	if(statement != null){
		statement.close();
		statement = null;
	}
	Hashtable vValue = new Hashtable(10);
	String tmpSql = SQLAnalyser.getAnalyserSQL(sql,vValue);
	statement = connection.prepareStatement(tmpSql);
	String value = varBind(statement,vValue);
	try{
		return statement.executeQuery();
	}catch(SQLException sqle){
		throw new CmisSQLException(sqle.getErrorCode(),sqle.getSQLState(),sqle.getMessage()+"||sql:"+tmpSql+"|value:"+value);
	}
}
	/**
	 * Executes an SQL INSERT, UPDATE or DELETE statement. In addition,
	 * SQL statements that return nothing, such as SQL DDL statements,
	 * can be executed.
	 *
	 * @param sql a SQL INSERT, UPDATE or DELETE statement or a SQL
	 * statement that returns nothing
	 * @return either the row count for INSERT, UPDATE or DELETE or 0
	 * for SQL statements that return nothing
	 * @exception SQLException if a database access error occurs
	 */
public int executeUpdate(String sql) throws java.sql.SQLException {
	if(statement != null){
		statement.close();
		statement = null;
	}
	Hashtable vValue = new Hashtable(10);
	String tmpSql = SQLAnalyser.getAnalyserSQL(sql,vValue);
	statement = connection.prepareStatement(tmpSql);
	String value = varBind(statement,vValue);
	try{
		return statement.executeUpdate();
	}catch(SQLException esc){
		throw new CmisSQLException(esc.getErrorCode(),esc.getSQLState(),esc.getMessage()+"||sql:"+tmpSql+"|value:"+value);
	}
}
	/**
	 * JDBC 2.0
	 * 
	 * Returns the <code>Connection</code> object
	 * that produced this <code>Statement</code> object.
	 * @return the connection that produced this statement
	 * @exception SQLException if a database access error occurs
	 */
public java.sql.Connection getConnection() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getConnection()]无效");
}
	/**
	 * JDBC 2.0
	 *
	 * Retrieves the direction for fetching rows from
	 * database tables that is the default for result sets
	 * generated from this <code>Statement</code> object.
	 * If this <code>Statement</code> object has not set
	 * a fetch direction by calling the method <code>setFetchDirection</code>,
	 * the return value is implementation-specific.
	 *
	 * @return the default fetch direction for result sets generated
	 *          from this <code>Statement</code> object
	 * @exception SQLException if a database access error occurs
	 */
public int getFetchDirection() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getFetchDirection()]无效");
}
	/**
	 * JDBC 2.0
	 *
	 * Retrieves the number of result set rows that is the default 
	 * fetch size for result sets
	 * generated from this <code>Statement</code> object.
	 * If this <code>Statement</code> object has not set
	 * a fetch size by calling the method <code>setFetchSize</code>,
	 * the return value is implementation-specific.
	 * @return the default fetch size for result sets generated
	 *          from this <code>Statement</code> object
	 * @exception SQLException if a database access error occurs
	 */
public int getFetchSize() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement. getFetchSize()]无效");
}
	/**
	 * Returns the maximum number of bytes allowed
	 * for any column value. 
	 * This limit is the maximum number of bytes that can be
	 * returned for any column value.
	 * The limit applies only to BINARY,
	 * VARBINARY, LONGVARBINARY, CHAR, VARCHAR, and LONGVARCHAR
	 * columns.  If the limit is exceeded, the excess data is silently
	 * discarded.
	 *
	 * @return the current max column size limit; zero means unlimited 
	 * @exception SQLException if a database access error occurs
	 */
public int getMaxFieldSize() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getMaxFieldSize()]无效");
}
	/**
	 * Retrieves the maximum number of rows that a
	 * ResultSet can contain.  If the limit is exceeded, the excess
	 * rows are silently dropped.
	 *
	 * @return the current max row limit; zero means unlimited
	 * @exception SQLException if a database access error occurs
	 */
public int getMaxRows() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getMaxRows()]无效");
}
	/**
	 * Moves to a Statement's next result.  It returns true if 
	 * this result is a ResultSet.  This method also implicitly
	 * closes any current ResultSet obtained with getResultSet.
	 *
	 * There are no more results when (!getMoreResults() &&
	 * (getUpdateCount() == -1)
	 *
	 * @return true if the next result is a ResultSet; false if it is
	 * an update count or there are no more results
	 * @exception SQLException if a database access error occurs
	 * @see #execute 
	 */
public boolean getMoreResults() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getMoreResults()]无效");
}
	/**
	 * Retrieves the number of seconds the driver will
	 * wait for a Statement to execute. If the limit is exceeded, a
	 * SQLException is thrown.
	 *
	 * @return the current query timeout limit in seconds; zero means unlimited 
	 * @exception SQLException if a database access error occurs
	 */
public int getQueryTimeout() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getQueryTimeout()]无效");
}
	/**
	 *  Returns the current result as a <code>ResultSet</code> object. 
	 *  This method should be called only once per result.
	 *
	 * @return the current result as a ResultSet; null if the result
	 * is an update count or there are no more results
	 * @exception SQLException if a database access error occurs
	 * @see #execute 
	 */
public java.sql.ResultSet getResultSet() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getResultSet()]无效");
}
	/**
	 * JDBC 2.0
	 *
	 * Retrieves the result set concurrency.
	 */
public int getResultSetConcurrency() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getResultSetConcurrency()]无效");
}
	/**
	 * JDBC 2.0
	 *
	 * Determine the result set type.
	 */
public int getResultSetType() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getResultSetType()]无效");
}
	/**
	 *  Returns the current result as an update count;
	 *  if the result is a ResultSet or there are no more results, -1
	 *  is returned. 
	 *  This method should be called only once per result.
	 * 
	 * @return the current result as an update count; -1 if it is a
	 * ResultSet or there are no more results
	 * @exception SQLException if a database access error occurs
	 * @see #execute 
	 */
public int getUpdateCount() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getUpdateCount()]无效");
}
/**
 * Retrieves the first warning reported by calls on this Statement.
 * Subsequent Statement warnings will be chained to this
 * SQLWarning.
 *
 * <p>The warning chain is automatically cleared each time
 * a statement is (re)executed.
 *
 * <P><B>Note:</B> If you are processing a ResultSet, any
 * warnings associated with ResultSet reads will be chained on the
 * ResultSet object.
 *
 * @return the first SQLWarning or null 
 * @exception SQLException if a database access error occurs
 */
public java.sql.SQLWarning getWarnings() throws java.sql.SQLException
{
   throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.getWarnings()]无效");
}
/**
 * Defines the SQL cursor name that will be used by
 * subsequent Statement <code>execute</code> methods. This name can then be
 * used in SQL positioned update/delete statements to identify the
 * current row in the ResultSet generated by this statement.  If
 * the database doesn't support positioned update/delete, this
 * method is a noop.  To insure that a cursor has the proper isolation
 * level to support updates, the cursor's SELECT statement should be
 * of the form 'select for update ...'. If the 'for update' phrase is 
 * omitted, positioned updates may fail.
 *
 * <P><B>Note:</B> By definition, positioned update/delete
 * execution must be done by a different Statement than the one
 * which generated the ResultSet being used for positioning. Also,
 * cursor names must be unique within a connection.
 *
 * @param name the new cursor name, which must be unique within
 *             a connection
 * @exception SQLException if a database access error occurs
 */
public void setCursorName(String name) throws java.sql.SQLException
{
   throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.setCursorName(String name)]无效");
}
/**
 * Sets escape processing on or off.
 * If escape scanning is on (the default), the driver will do
 * escape substitution before sending the SQL to the database.
 *
 * Note: Since prepared statements have usually been parsed prior
 * to making this call, disabling escape processing for prepared
 * statements will have no effect.
 *
 * @param enable true to enable; false to disable
 * @exception SQLException if a database access error occurs
 */
public void setEscapeProcessing(boolean enable) throws java.sql.SQLException
{
   throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.setEscapeProcessing(boolean enable)]无效");
}
/**
 * JDBC 2.0
 *
 * Gives the driver a hint as to the direction in which
 * the rows in a result set
 * will be processed. The hint applies only to result sets created 
 * using this Statement object.  The default value is 
 * ResultSet.FETCH_FORWARD.
 * <p>Note that this method sets the default fetch direction for 
 * result sets generated by this <code>Statement</code> object.
 * Each result set has its own methods for getting and setting
 * its own fetch direction.
 * @param direction the initial direction for processing rows
 * @exception SQLException if a database access error occurs
 * or the given direction
 * is not one of ResultSet.FETCH_FORWARD, ResultSet.FETCH_REVERSE, or
 * ResultSet.FETCH_UNKNOWN
 */
public void setFetchDirection(int direction) throws java.sql.SQLException
{
    throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.setFetchDirection(int direction)]无效");
}
/**
 * JDBC 2.0
 *
 * Gives the JDBC driver a hint as to the number of rows that should 
 * be fetched from the database when more rows are needed.  The number 
 * of rows specified affects only result sets created using this 
 * statement. If the value specified is zero, then the hint is ignored.
 * The default value is zero.
 *
 * @param rows the number of rows to fetch
 * @exception SQLException if a database access error occurs, or the
 * condition 0 <= rows <= this.getMaxRows() is not satisfied.
 */
public void setFetchSize(int rows) throws java.sql.SQLException
{
    throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.setFetchSize(int rows)]无效");
}
/**
 * Sets the limit for the maximum number of bytes in a column to
 * the given number of bytes.  This is the maximum number of bytes 
 * that can be returned for any column value.  This limit applies
 * only to BINARY, VARBINARY, LONGVARBINARY, CHAR, VARCHAR, and
 * LONGVARCHAR fields.  If the limit is exceeded, the excess data
 * is silently discarded. For maximum portability, use values
 * greater than 256.
 *
 * @param max the new max column size limit; zero means unlimited 
 * @exception SQLException if a database access error occurs
 */
public void setMaxFieldSize(int max) throws java.sql.SQLException
{
    throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.setMaxFieldSize(int max)]无效");
}
/**
 * Sets the limit for the maximum number of rows that any
 * ResultSet can contain to the given number.
 * If the limit is exceeded, the excess
 * rows are silently dropped.
 *
 * @param max the new max rows limit; zero means unlimited 
 * @exception SQLException if a database access error occurs
 */
public void setMaxRows(int max) throws java.sql.SQLException
{
   throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.setMaxRows(int max)]无效");
}
/**
 * Sets the number of seconds the driver will
 * wait for a Statement to execute to the given number of seconds.
 * If the limit is exceeded, a SQLException is thrown.
 *
 * @param seconds the new query timeout limit in seconds; zero means 
 * unlimited 
 * @exception SQLException if a database access error occurs
 */
public void setQueryTimeout(int seconds) throws java.sql.SQLException
{
   throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLStatement.setQueryTimeout(int seconds)]无效");
}
}
