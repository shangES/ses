package icbc.cmis.second.pkg;

import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-10-25 15:33:08)
 * @author: Administrator
 */
public class DBProcedureParamsDef {
	private String procedureName = null;
	private String dbUserName = null;
	private String dbUserPass = null;
	private String dbUserPassVerify = null;
	private Vector inParam = new Vector(10);
	private Vector outParam = new Vector(20);
	private Vector vCursorParam = new Vector(2);
	private Vector vIResultName = new Vector(2);
	
	/*begin 2006-06-03 章慧俊增加：添加自动commit标志变量，默认值为不自动*/
	private boolean bAutoCommit = false;
	/*end 2006-06-03*/

/**
 * ProcedureParamsDef constructor comment.
 */
public DBProcedureParamsDef(String procedureName) {
	super();
	this.procedureName = procedureName;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public void addCursorOutParams(Vector  vCursorOutParamNames,String iResultName)throws Exception {
	
	if(vCursorOutParamNames == null || vCursorOutParamNames.isEmpty()){
		throw new Exception("设置存储过程结果集返回参数不合理！");
	}
	if(iResultName == null || iResultName.trim().length() == 0){
		throw new Exception("设置存储过程结果集数据名称不合理！");
	}	
	vCursorParam.add(vCursorOutParamNames);
	vIResultName.add(iResultName);
}
 
public void addInParam(String inParamName)throws Exception{
	
	 if(inParamName == null || inParamName.trim().length() == 0)
	 
	 	throw new Exception("存储过程输入参数设置不合理，参数不能为空");
	 	
	 
	 inParam.add(inParamName.trim());
	 
 }
 
public void addOutParam(String outParamName)throws Exception{
	
	 if(outParamName == null || outParamName.trim().length() == 0)
	 
	 	throw new Exception("存储过程输出参数设置不合理，参数不能为空");
	 	
	
	 outParam.add(outParamName.trim());
	 
 }
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public Vector getCursorOutParams() {
	return vCursorParam;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public Vector getCursorResultNames() {
	return vIResultName;
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
 
public Vector getInParam(){
	
	return inParam;
	 
 }
 
public Vector getOutParam(){
	
	return  outParam;
	 
 }
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public String getProcedureName() {
	return procedureName;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-10-25 15:49:08)
 */
public void reset() throws Exception{
	try{
		dbUserName = null;
		dbUserPassVerify = null;
		procedureName = null;
		if(inParam != null && !inParam.isEmpty()){
			inParam.removeAllElements();
		}
		if(outParam != null && !outParam.isEmpty()){
			outParam.removeAllElements();
		}
		if(vCursorParam != null && !vCursorParam.isEmpty()){
			vCursorParam.removeAllElements();
		}
		if(vIResultName != null && !vIResultName.isEmpty()){
			vIResultName.removeAllElements();
		}
		
	}catch(Exception e){
		throw new Exception("初始化存储过程参数出错，错误信息为："+e.getMessage());
	}
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
 *<b>创建日期: </b> 2006-06-03<br>
 *<b>标题: 给bAutoCommit标志赋值</b><br>
 *<br>给是否自动commit标志bAutoCommit赋值
 *<p>Copyright: Copyright (c)2006</p>
 *<p>Company: ICBC</p>
 *@author 章慧俊
 *-------------------------------------------------------------
 *修改人：
 *修改原因：
 *修改时间：
**/
public void setAutoCommit(boolean bAutoCommit) {
	this.bAutoCommit = bAutoCommit;
}

/**
 *<b>创建日期: </b> 2006-06-03<br>
 *<b>标题: 取得bAutoCommit标志的值</b><br>
 *<br>取得是否自动commit标志bAutoCommit的值
 *<p>Copyright: Copyright (c)2006</p>
 *<p>Company: ICBC</p>
 *@author 章慧俊
 *-------------------------------------------------------------
 *修改人：
 *修改原因：
 *修改时间：
**/
public boolean getAutoCommit() {
	return bAutoCommit;
}

}
