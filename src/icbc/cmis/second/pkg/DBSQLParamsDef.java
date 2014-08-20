package icbc.cmis.second.pkg;

import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-10-25 15:33:08)
 * @author: Administrator
 */
public class DBSQLParamsDef {
	private String dbUserName = null;
	private String dbUserPass = null;
	private String dbUserPassVerify = null;
	private Vector vColumns = null;
	private String conditions  = null;
	private String tableNames = null;
	private Vector vInColumns = null;
/**
 * ProcedureParamsDef constructor comment.
 */
public DBSQLParamsDef() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public String getConditions() {
	return conditions;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public String getDBUserName() {
	return dbUserName;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public String getDBUserPass() {
	return dbUserPass;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public String getDBUserPassVerify() {
	return dbUserPassVerify;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public Vector getInColumns() {
	return vInColumns;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public String getTableNames() {
	return this.tableNames;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public Vector getVColumns() {
	return this.vColumns;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public void reset() throws Exception{
	try{
		dbUserName = null;
		dbUserPassVerify = null;
		vColumns = null;
		
	}catch(Exception e){
		throw new Exception("初始化存储过程参数出错，错误信息为："+e.getMessage());
	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public void setConditions(String conditions1) {
	conditions = conditions1;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public void setDBUserName(String dbUserName) {
	this.dbUserName = dbUserName;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public void setDBUserPass(String dbUserPass) {
	this.dbUserPass = dbUserPass;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public void setDBUserPassVerify(String dbUserPassVerify) {
	this.dbUserPassVerify = dbUserPassVerify;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public void setInColumns(Vector vInColumns1) {
	this.vInColumns = vInColumns1;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public void setTableNames(String tableNames1) {
	this.tableNames = tableNames1;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public void setVColumns(Vector vColumns1) {
	this.vColumns = vColumns1;
}
}
