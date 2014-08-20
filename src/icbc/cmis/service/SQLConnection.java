package icbc.cmis.service;

/**
 * Insert the type's description here.
 * Creation date: (2002-12-19 14:07:01)
 * @author: Administrator
 */
public class SQLConnection implements java.sql.Connection{
	private java.sql.Connection connection = null;
/**
 * SQLConnection constructor comment.
 */
public SQLConnection(java.sql.Connection conn) {
	super();
	connection = conn;
}
	/**
	 * Clears all warnings reported for this <code>Connection</code> object.	
	 * After a call to this method, the method <code>getWarnings</code>
	 * returns null until a new warning is
	 * reported for this Connection.  
	 *
	 * @exception SQLException if a database access error occurs
	 */
public void clearWarnings() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.clearWarnings()]无效");
}
	/**
	 * Releases a Connection's database and JDBC resources
	 * immediately instead of waiting for
	 * them to be automatically released.
	 *
	 * <P><B>Note:</B> A Connection is automatically closed when it is
	 * garbage collected. Certain fatal errors also result in a closed
	 * Connection.
	 *
	 * @exception SQLException if a database access error occurs
	 */
public void close() throws java.sql.SQLException {
	connection.close();
}
	/**
	 * Makes all changes made since the previous
	 * commit/rollback permanent and releases any database locks
	 * currently held by the Connection. This method should be
	 * used only when auto-commit mode has been disabled.
	 *
	 * @exception SQLException if a database access error occurs
	 * @see #setAutoCommit 
	 */
public void commit() throws java.sql.SQLException {
	connection.commit();
}
	/**
	 * Creates a <code>Statement</code> object for sending
	 * SQL statements to the database.
	 * SQL statements without parameters are normally
	 * executed using Statement objects. If the same SQL statement 
	 * is executed many times, it is more efficient to use a 
	 * PreparedStatement
	 *
	 * JDBC 2.0
	 *
	 * Result sets created using the returned Statement will have
	 * forward-only type, and read-only concurrency, by default.
	 *
	 * @return a new Statement object 
	 * @exception SQLException if a database access error occurs
	 */
public java.sql.Statement createStatement() throws java.sql.SQLException {
	
	return new SQLStatement(connection);
}
	/**
	 * JDBC 2.0
	 *
	 * Creates a <code>Statement</code> object that will generate
	 * <code>ResultSet</code> objects with the given type and concurrency.
	 * This method is the same as the <code>createStatement</code> method
	 * above, but it allows the default result set
	 * type and result set concurrency type to be overridden.
	 *
	 * @param resultSetType a result set type; see ResultSet.TYPE_XXX
	 * @param resultSetConcurrency a concurrency type; see ResultSet.CONCUR_XXX
	 * @return a new Statement object 
	 * @exception SQLException if a database access error occurs
	 */
public java.sql.Statement createStatement(int resultSetType, int resultSetConcurrency) throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.createStatement(int resultSetType, int resultSetConcurrency)]无效");
}
	/**
	 * Gets the current auto-commit state.
	 *
	 * @return the current state of auto-commit mode
	 * @exception SQLException if a database access error occurs
	 * @see #setAutoCommit 
	 */
public boolean getAutoCommit() throws java.sql.SQLException {
	return connection.getAutoCommit();
}
	/**
	 * Returns the Connection's current catalog name.
	 *
	 * @return the current catalog name or null
	 * @exception SQLException if a database access error occurs
	 */
public String getCatalog() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求无效[SQLConnection.getCatalog()]");
}
/**
 * Insert the method's description here.
 * Creation date: (2002-12-19 14:52:51)
 * @return java.sql.Connection
 */
public java.sql.Connection getConnection() {
	return connection;
}
	/**
	 * Gets the metadata regarding this connection's database.
	 * A Connection's database is able to provide information
	 * describing its tables, its supported SQL grammar, its stored
	 * procedures, the capabilities of this connection, and so on. This
	 * information is made available through a DatabaseMetaData
	 * object.
	 *
	 * @return a DatabaseMetaData object for this Connection 
	 * @exception SQLException if a database access error occurs
	 */
public java.sql.DatabaseMetaData getMetaData() throws java.sql.SQLException {
	return connection.getMetaData();
}
	/**
	 * Gets this Connection's current transaction isolation level.
	 *
	 * @return the current TRANSACTION_* mode value
	 * @exception SQLException if a database access error occurs
	 */
public int getTransactionIsolation() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.getTransactionIsolation() ]无效");
}
	/**
	 * JDBC 2.0
	 *
	 * Gets the type map object associated with this connection.
	 * Unless the application has added an entry to the type map,
	 * the map returned will be empty.
	 *
	 * @return the <code>java.util.Map</code> object associated 
	 *         with this <code>Connection</code> object
	 */
public java.util.Map getTypeMap() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.getTypeMap()]无效");
}
	/**
	 * Returns the first warning reported by calls on this Connection.
	 *
	 * <P><B>Note:</B> Subsequent warnings will be chained to this
	 * SQLWarning.
	 *
	 * @return the first SQLWarning or null 
	 * @exception SQLException if a database access error occurs
	 */
public java.sql.SQLWarning getWarnings() throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.getWarnings()]无效");
}
	/**
	 * Tests to see if a Connection is closed.
	 *
	 * @return true if the connection is closed; false if it's still open
	 * @exception SQLException if a database access error occurs
	 */
public boolean isClosed() throws java.sql.SQLException {
	return connection.isClosed();
}
	/**
	 * Tests to see if the connection is in read-only mode.
	 *
	 * @return true if connection is read-only and false otherwise
	 * @exception SQLException if a database access error occurs
	 */
public boolean isReadOnly() throws java.sql.SQLException {
	return connection.isReadOnly();
}
	/**
	 * Converts the given SQL statement into the system's native SQL grammar.
	 * A driver may convert the JDBC sql grammar into its system's
	 * native SQL grammar prior to sending it; this method returns the
	 * native form of the statement that the driver would have sent.
	 *
	 * @param sql a SQL statement that may contain one or more '?'
	 * parameter placeholders
	 * @return the native form of this statement
	 * @exception SQLException if a database access error occurs
	 */
public String nativeSQL(String sql) throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.nativeSQL(String sql) ]无效");
}
	/**
	 * Creates a <code>CallableStatement</code> object for calling
	 * database stored procedures.
	 * The CallableStatement provides
	 * methods for setting up its IN and OUT parameters, and
	 * methods for executing the call to a stored procedure.
	 *
	 * <P><B>Note:</B> This method is optimized for handling stored
	 * procedure call statements. Some drivers may send the call
	 * statement to the database when the method <code>prepareCall</code>
	 * is done; others
	 * may wait until the CallableStatement is executed. This has no
	 * direct effect on users; however, it does affect which method
	 * throws certain SQLExceptions.
	 *
	 * JDBC 2.0
	 *
	 * Result sets created using the returned CallableStatement will have
	 * forward-only type and read-only concurrency, by default.
	 *
	 * @param sql a SQL statement that may contain one or more '?'
	 * parameter placeholders. Typically this  statement is a JDBC
	 * function call escape string.
	 * @return a new CallableStatement object containing the
	 * pre-compiled SQL statement 
	 * @exception SQLException if a database access error occurs
	 */
public java.sql.CallableStatement prepareCall(String sql) throws java.sql.SQLException {
	return connection.prepareCall(sql);
}
	/**
	 * JDBC 2.0
	 *
	 * Creates a <code>CallableStatement</code> object that will generate
	 * <code>ResultSet</code> objects with the given type and concurrency.
	 * This method is the same as the <code>prepareCall</code> method
	 * above, but it allows the default result set
	 * type and result set concurrency type to be overridden.
	 *
	 * @param resultSetType a result set type; see ResultSet.TYPE_XXX
	 * @param resultSetConcurrency a concurrency type; see ResultSet.CONCUR_XXX
	 * @return a new CallableStatement object containing the
	 * pre-compiled SQL statement 
	 * @exception SQLException if a database access error occurs
	 */
public java.sql.CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.prepareCall(String sql, int resultSetType, int resultSetConcurrency)]无效");
}
	/**
	 * Creates a <code>PreparedStatement</code> object for sending
	 * parameterized SQL statements to the database.
	 * 
	 * A SQL statement with or without IN parameters can be
	 * pre-compiled and stored in a PreparedStatement object. This
	 * object can then be used to efficiently execute this statement
	 * multiple times.
	 *
	 * <P><B>Note:</B> This method is optimized for handling
	 * parametric SQL statements that benefit from precompilation. If
	 * the driver supports precompilation,
	 * the method <code>prepareStatement</code> will send
	 * the statement to the database for precompilation. Some drivers
	 * may not support precompilation. In this case, the statement may
	 * not be sent to the database until the <code>PreparedStatement</code> is
	 * executed.  This has no direct effect on users; however, it does
	 * affect which method throws certain SQLExceptions.
	 *
	 * JDBC 2.0
	 *
	 * Result sets created using the returned PreparedStatement will have
	 * forward-only type and read-only concurrency, by default.
	 *
	 * @param sql a SQL statement that may contain one or more '?' IN
	 * parameter placeholders
	 * @return a new PreparedStatement object containing the
	 * pre-compiled statement 
	 * @exception SQLException if a database access error occurs
	 */
public java.sql.PreparedStatement prepareStatement(String sql) throws java.sql.SQLException {
	return connection.prepareStatement(sql);
}
	/**
	 * JDBC 2.0
	 *
	 * Creates a <code>PreparedStatement</code> object that will generate
	 * <code>ResultSet</code> objects with the given type and concurrency.
	 * This method is the same as the <code>prepareStatement</code> method
	 * above, but it allows the default result set
	 * type and result set concurrency type to be overridden.
	 *
	 * @param resultSetType a result set type; see ResultSet.TYPE_XXX
	 * @param resultSetConcurrency a concurrency type; see ResultSet.CONCUR_XXX
	 * @return a new PreparedStatement object containing the
	 * pre-compiled SQL statement 
	 * @exception SQLException if a database access error occurs
	 */
public java.sql.PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws java.sql.SQLException {
	return connection.prepareStatement(sql,resultSetType,resultSetConcurrency);
	//throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.prepareStatement(String sql, int resultSetType, int resultSetConcurrency)]无效");
}
	/**
	 * Drops all changes made since the previous
	 * commit/rollback and releases any database locks currently held
	 * by this Connection. This method should be used only when auto-
	 * commit has been disabled.
	 *
	 * @exception SQLException if a database access error occurs
	 * @see #setAutoCommit 
	 */
public void rollback() throws java.sql.SQLException {
	connection.rollback();
	}
	/**
	 * Sets this connection's auto-commit mode.
	 * If a connection is in auto-commit mode, then all its SQL
	 * statements will be executed and committed as individual
	 * transactions.  Otherwise, its SQL statements are grouped into
	 * transactions that are terminated by a call to either
	 * the method <code>commit</code> or the method <code>rollback</code>.
	 * By default, new connections are in auto-commit
	 * mode.
	 *
	 * The commit occurs when the statement completes or the next
	 * execute occurs, whichever comes first. In the case of
	 * statements returning a ResultSet, the statement completes when
	 * the last row of the ResultSet has been retrieved or the
	 * ResultSet has been closed. In advanced cases, a single
	 * statement may return multiple results as well as output
	 * parameter values. In these cases the commit occurs when all results and
	 * output parameter values have been retrieved.
	 *
	 * @param autoCommit true enables auto-commit; false disables
	 * auto-commit.  
	 * @exception SQLException if a database access error occurs
	 */
public void setAutoCommit(boolean autoCommit) throws java.sql.SQLException {
	connection.setAutoCommit(autoCommit);
}
	/**
	 * Sets a catalog name in order to select 	
	 * a subspace of this Connection's database in which to work.
	 * If the driver does not support catalogs, it will
	 * silently ignore this request.
	 *
	 * @exception SQLException if a database access error occurs
	 */
public void setCatalog(String catalog) throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.setCatalog(String catalog)]无效");
}
	/**
	 * Puts this connection in read-only mode as a hint to enable 
	 * database optimizations.
	 *
	 * <P><B>Note:</B> This method cannot be called while in the
	 * middle of a transaction.
	 *
	 * @param readOnly true enables read-only mode; false disables
	 * read-only mode.  
	 * @exception SQLException if a database access error occurs
	 */
public void setReadOnly(boolean readOnly) throws java.sql.SQLException {
	connection.setReadOnly(readOnly);
}
	/**
	 * Attempts to change the transaction
	 * isolation level to the one given.
	 * The constants defined in the interface <code>Connection</code>
	 * are the possible transaction isolation levels.
	 *
	 * <P><B>Note:</B> This method cannot be called while
	 * in the middle of a transaction.
	 *
	 * @param level one of the TRANSACTION_* isolation values with the
	 * exception of TRANSACTION_NONE; some databases may not support
	 * other values
	 * @exception SQLException if a database access error occurs
	 * @see DatabaseMetaData#supportsTransactionIsolationLevel 
	 */
public void setTransactionIsolation(int level) throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.setTransactionIsolation(int level)]无效");
}
	/**
	 * JDBC 2.0
	 *
	 * Installs the given type map as the type map for
	 * this connection.  The type map will be used for the
	 * custom mapping of SQL structured types and distinct types.
	 *
	 * @param the <code>java.util.Map</code> object to install
	 *        as the replacement for this <code>Connection</code>
	 *        object's default type map
	 */
public void setTypeMap(java.util.Map map) throws java.sql.SQLException {
	throw new java.sql.SQLException("系统错误，JDBC操作请求[SQLConnection.setTypeMap(java.util.Map map)]无效");
}
}
