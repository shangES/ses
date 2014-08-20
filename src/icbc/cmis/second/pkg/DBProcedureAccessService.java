package icbc.cmis.second.pkg;

import java.sql.*;
import oracle.jdbc.*;
import icbc.cmis.base.*;
import icbc.cmis.operation.CmisOperation;
import java.util.*;
import icbc.cmis.service.CmisDao;

/**
 *	 存储过程访问类定义。
 *
 *    @(#) ProcedureAccessService.java	1.0  
 *    Copyright (c) 2002 ECC All Rights Reserved.
 *   
 *   
 *    @version 1.0 (2002-7-8 9:39:58)
 *    @author   Administrator
 */
public class DBProcedureAccessService extends CmisDao {
	/**
	 * ProcedureAccessService constructor comment.
	 * @param arg1 java.lang.String
	 * @exception java.io.IOException The exception description.
	 */
	public DBProcedureAccessService(CmisOperation arg1)
		throws java.io.IOException {
		super(arg1);
	}
	public int executeProcedure(
		KeyedDataCollection operationData,
		DBProcedureParamsDef procedureParamDef)
		throws Exception {
		if (procedureParamDef == null)
			throw new Exception("请提供存储过程调用的参数信息");

		CallableStatement call = null;

		/*begin 2006-06-03 章慧俊修改：增加bAutoCommit标志的取值，以此判断是否需要进行commit*/
		boolean bAutoCommit = procedureParamDef.getAutoCommit();			
		/*end 2006-06-03*/

		try {			
			if (procedureParamDef.getDBUserName() == null) {
				getConnection();				
			} else if (procedureParamDef.getDBUserPassVerify() != null) {
				getConnByVerify(
					procedureParamDef.getDBUserName(),
					procedureParamDef.getDBUserPassVerify());					
			} else if (procedureParamDef.getDBUserPass() != null) {
				getConnection(
					procedureParamDef.getDBUserName(),
					procedureParamDef.getDBUserPass());					
			} else
				throw new Exception("存储过程的数据库操作用户信息错误");
			
			String procedureName = procedureParamDef.getProcedureName();
			StringBuffer param =
				new StringBuffer("{ call " + procedureName + " (");

			//process the input params        
			java.util.Vector inParam = procedureParamDef.getInParam();
			for (int i = 0; i < inParam.size(); i++)
				param = param.append("?, ");

			//process the results
			java.util.Vector results = procedureParamDef.getOutParam();
			for (int i = 0; i < results.size(); i++)
				param = param.append("?, ");

			//process the resultSets
			java.util.Vector resultSets =
				procedureParamDef.getCursorOutParams();

			for (int i = 0; i < resultSets.size(); i++)
				param = param.append("?, ");

			int len = param.length();

			if (param.charAt(len - 2) == ',')
				param.setCharAt(len - 2, ' ');

			param = param.append(") }");
			CmisConstance.pringMsg("call procedure:"+param.toString());
			call = conn.prepareCall(param.toString());

			int outIdx = inParam.size() + 1;

			//regist the ouput result	
			for (int i = 0; i < results.size(); i++) {
				call.registerOutParameter(outIdx++, OracleTypes.VARCHAR);
			}

			for (int i = 0; i < resultSets.size(); i++) {
				call.registerOutParameter(outIdx++, OracleTypes.CURSOR);
			}

			//now set the procedure input paramaters    
			for (int i = 0; i < inParam.size(); i++) {
				String cName = (String) inParam.elementAt(i);
				String aParam =
					(String) operationData.getValueAt(
						cName);
				if (aParam != null)
					aParam = aParam.trim();
				else
					aParam = "";
				call.setString(1 + i, aParam);
				CmisConstance.pringMsg("param"+(i+1)+",name:"+cName+",value:"+aParam);
			}

			//execute the procedure            
			call.execute();

			String retCode = "-1";

			outIdx = inParam.size() + 1;
			retCode = call.getString(outIdx);

			//retrive the result
			for (int i = 0; i < results.size(); i++) {
				String paramName = (String) results.elementAt(i);
				String value = call.getString(outIdx++);
				try {
					operationData.setValueAt(paramName, value);
				} catch (CTEObjectNotFoundException e) {
					operationData.addElement(paramName, value);
				}
			}

			//retrive the resultSets
			for (int i = 0; i < resultSets.size(); i++) {
				java.util.Vector resultSetParams =
					(java.util.Vector) resultSets.elementAt(i);
				String iCollName =
					(String) procedureParamDef
						.getCursorResultNames()
						.elementAt(
						i);

				IndexedDataCollection iColl = new IndexedDataCollection();
				iColl.setName(iCollName);
				if (operationData.isElementExist(iCollName)) {
					operationData.removeElement(iCollName);
				}
				operationData.addElement(iColl);

				ResultSet rset = null;
				try {
					rset = (ResultSet) call.getObject(outIdx++);

					int idx = 0;
					int maxRow = getOperation().getMaxRowNum();
					while (rset.next()) {
						if (maxRow != -1 && iColl.getSize() >= maxRow) {
							getOperation().setFieldValue(
								iColl.getName() + "IsOverflow",
								"true");
							getOperation().setFieldValue(
								iColl.getName() + "OverflowMsg",
								"交易结果记录数过大,当前返回记录数为 ["
									+ maxRow
									+ "],请调整查询条件缩小数据量.");
							break;
						}
						KeyedDataCollection aKColl = new KeyedDataCollection();
						iColl.addElement(aKColl);

						for (int j = 1; j <= resultSetParams.size(); j++) {
							String dataName =
								(String) resultSetParams.elementAt(j - 1);

							aKColl.addElement(dataName, rset.getString(j));
						}
						idx++;
					}

					//remove the empty IndexedCollection's element[0]

					if (idx == 0) {
						try {
							iColl.removeElement(0);
						} catch (Exception ee) {
						}
					}

					rset.close();
				} catch (SQLException esql) {
					continue;
				}

			}

			/*begin 2006-06-03 章慧俊修改：添加对自动commit标志和存储过程返回标志retCode的判断*/
			if(bAutoCommit){ //当bAutoCommit标志是true时,表示是自动进行数据库提交
			  if(retCode.equals("0")){  //当返回标志retCode是0时,表示存储过程执行成功,则进行数据库提交
				  daoCommit();
			  }else{                //当返回标志retCode是其它值时,表示存储过程执行失败,则进行数据库回滚
				  daoRollback();           
			  }
		    }
		    /*end 2006-06-03*/
			
			call.close();
			closeConnection();

			return Integer.valueOf(retCode).intValue();
		} catch (Exception e) {
			System.out.println(
				"Exception from DBProcedureAccessService.executeProcedure [ "
					+ procedureParamDef.getProcedureName()
					+ "] exception: "
					+ e);
			/*begin 2006-06-07 章慧俊修改：添加捕捉异常时对自动commit标志的判断，即是否需要rollback*/
			try {
			  if (call != null){
			     if(bAutoCommit){ //当bAutoCommit标志是true时,表示是自动进行数据库提交，需要判断是否做rollback
					 daoRollback();           
			     }
			  }									 
			} catch (Exception ee) {
			}			
			/*end 2006-06-07*/		
			try {
				if (call != null)
					call.close();
			} catch (Exception ee) {
			}
			if (conn != null)
				closeConnection();
			System.out.println(e);
			throw e;
		}

	}
	
	public String executeFunction(
		KeyedDataCollection operationData,
		DBProcedureParamsDef procedureParamDef)
		throws Exception {
		if (procedureParamDef == null)
			throw new Exception("请提供存储过程调用的参数信息");

		CallableStatement call = null;

		try {
			if (procedureParamDef.getDBUserName() == null) {
				getConnection();
			} else if (procedureParamDef.getDBUserPassVerify() != null) {
				getConnByVerify(
					procedureParamDef.getDBUserName(),
					procedureParamDef.getDBUserPassVerify());
			} else if (procedureParamDef.getDBUserPass() != null) {
				getConnection(
					procedureParamDef.getDBUserName(),
					procedureParamDef.getDBUserPass());
			} else
				throw new Exception("存储过程的数据库操作用户信息错误");

			String procedureName = procedureParamDef.getProcedureName();
			StringBuffer param =
				new StringBuffer("{ ? = call " + procedureName + " (");

			//process the input params        
			java.util.Vector inParam = procedureParamDef.getInParam();
			for (int i = 0; i < inParam.size(); i++)
				param = param.append("?, ");

			//process the results
			java.util.Vector results = procedureParamDef.getOutParam();
			for (int i = 1; i < results.size(); i++)
				param = param.append("?, ");

			//process the resultSets
			java.util.Vector resultSets =
				procedureParamDef.getCursorOutParams();

			for (int i = 0; i < resultSets.size(); i++)
				param = param.append("?, ");

			int len = param.length();

			if (param.charAt(len - 2) == ',')
				param.setCharAt(len - 2, ' ');

			param = param.append(") }");
			CmisConstance.pringMsg("?=call procedure:"+param.toString());
			call = conn.prepareCall(param.toString());

			int outIdx = inParam.size() + 2;
			
			call.registerOutParameter(1, OracleTypes.VARCHAR);

			//regist the ouput result	
			for (int i = 1; i < results.size(); i++) {
				call.registerOutParameter(outIdx++, OracleTypes.VARCHAR);
			}

			for (int i = 0; i < resultSets.size(); i++) {
				call.registerOutParameter(outIdx++, OracleTypes.CURSOR);
			}

			//now set the procedure input paramaters    
			for (int i = 0; i < inParam.size(); i++) {
				String cName = (String) inParam.elementAt(i);
				String aParam =
					(String) operationData.getValueAt(
						cName);
				if (aParam != null)
					aParam = aParam.trim();
				else
					aParam = "";
				call.setString(2 + i, aParam);
				CmisConstance.pringMsg("param"+(i+2)+",name:"+cName+",value:"+aParam);
			}

			//execute the procedure            
			call.execute();

			String retCode = "-1";

			outIdx = inParam.size() + 2;
			retCode = call.getString(1);

			//retrive the result
			for (int i = 1; i < results.size(); i++) {
				String paramName = (String) results.elementAt(i);
				String value = call.getString(outIdx++);
				try {
					operationData.setValueAt(paramName, value);
				} catch (CTEObjectNotFoundException e) {
					operationData.addElement(paramName, value);
				}
			}

			//retrive the resultSets
			for (int i = 0; i < resultSets.size(); i++) {
				java.util.Vector resultSetParams =
					(java.util.Vector) resultSets.elementAt(i);
				String iCollName =
					(String) procedureParamDef
						.getCursorResultNames()
						.elementAt(
						i);

				IndexedDataCollection iColl = new IndexedDataCollection();
				iColl.setName(iCollName);
				if (operationData.isElementExist(iCollName)) {
					operationData.removeElement(iCollName);
				}
				operationData.addElement(iColl);

				ResultSet rset = null;
				try {
					rset = (ResultSet) call.getObject(outIdx++);

					int idx = 0;
					int maxRow = getOperation().getMaxRowNum();
					while (rset.next()) {
						if (maxRow != -1 && iColl.getSize() >= maxRow) {
							getOperation().setFieldValue(
								iColl.getName() + "IsOverflow",
								"true");
							getOperation().setFieldValue(
								iColl.getName() + "OverflowMsg",
								"交易结果记录数过大,当前返回记录数为 ["
									+ maxRow
									+ "],请调整查询条件缩小数据量.");
							break;
						}
						KeyedDataCollection aKColl = new KeyedDataCollection();
						iColl.addElement(aKColl);

						for (int j = 1; j <= resultSetParams.size(); j++) {
							String dataName =
								(String) resultSetParams.elementAt(j - 1);

							aKColl.addElement(dataName, rset.getString(j));
						}
						idx++;
					}

					//remove the empty IndexedCollection's element[0]

					if (idx == 0) {
						try {
							iColl.removeElement(0);
						} catch (Exception ee) {
						}
					}

					rset.close();
				} catch (SQLException esql) {
					continue;
				}

			}

			call.close();
			closeConnection();

			return retCode;
		} catch (Exception e) {
			System.out.println(
				"Exception from DBProcedureAccessService.executeFunction [ "
					+ procedureParamDef.getProcedureName()
					+ "] exception: "
					+ e);
			try {
				if (call != null)
					call.close();
			} catch (Exception ee) {
			}
			if (conn != null)
				closeConnection();
			System.out.println(e);
			throw e;
		}

	}

	
	public void executeQuery(
		DBSQLParamsDef sqlDef,
		KeyedDataCollection operationData,
		IndexedDataCollection icoll)
		throws Exception {
		PreparedStatement stmt = null;
		boolean isPreStmt = false;
		if (sqlDef.getInColumns() != null
			&& !sqlDef.getInColumns().isEmpty()) {
			isPreStmt = true;
		}
		try {

			String strselect = "select ";

			for (int i = 0; i < sqlDef.getVColumns().size(); i++) {
				String str0 = (String) sqlDef.getVColumns().elementAt(i);
				strselect += str0 + ",";
			}
			strselect = strselect.substring(0, strselect.length() - 1);
			if (sqlDef.getConditions() == null
				|| sqlDef.getConditions().trim().length() == 0)
				strselect = strselect + " from " + sqlDef.getTableNames();
			else
				strselect =
					strselect
						+ " from "
						+ sqlDef.getTableNames()
						+ " "
						+ sqlDef.getConditions();
			if (sqlDef.getDBUserName() == null
				|| sqlDef.getDBUserName().trim().length() == 0) {
				getConnection();
			} else if (
				sqlDef.getDBUserPassVerify() != null
					&& sqlDef.getDBUserPassVerify().trim().length() != 0) {
				getConnByVerify(
					sqlDef.getDBUserName(),
					sqlDef.getDBUserPassVerify());
			} else if (
				sqlDef.getDBUserPass() != null
					&& sqlDef.getDBUserPass().trim().length() != 0) {
				getConnection(sqlDef.getDBUserName(), sqlDef.getDBUserPass());
			}
			ResultSet rs = null;
			stmt = conn.prepareStatement(strselect);
			CmisConstance.pringMsg("sqlStr:"+strselect);
			if (isPreStmt) {

				for (int i = 0; i < sqlDef.getInColumns().size(); i++) {
					String cName = (String)sqlDef.getInColumns().elementAt(i);
					String aParam =
						(String) operationData.getValueAt(
							(String) (cName));
					if (aParam != null)
						aParam = aParam.trim();
					else
						aParam = "";
					stmt.setString(1 + i, aParam);
					CmisConstance.pringMsg("param"+(i+1)+",name:"+cName+",value:"+aParam);
				}
			}
			rs = stmt.executeQuery();

			int maxRow = getOperation().getMaxRowNum();
			while (rs.next()) {
				if (maxRow != -1 && icoll.getSize() >= maxRow) {
					getOperation().setFieldValue(
						icoll.getName() + "IsOverflow",
						"true");
					getOperation().setFieldValue(
						icoll.getName() + "OverflowMsg",
						"交易结果记录数过大,当前返回记录数为 [" + maxRow + "],请调整查询条件缩小数据量.");
					break;
				}
				KeyedDataCollection kcoll = new KeyedDataCollection();
				kcoll.setName("kcollqueryresult");
				for (int i = 0; i < sqlDef.getVColumns().size(); i++) {
					String str1 = (String) sqlDef.getVColumns().elementAt(i);
					DataElement dfield = new DataElement();
					str1 = str1.trim();
					int mark = str1.indexOf(" ");
					while (mark != -1) {
						str1 = str1.substring(mark, str1.length());
						str1 = str1.trim();
						mark = str1.indexOf(" ");
					}

					int index = str1.indexOf(".");
					if (index != -1) {
						str1 = str1.replace('.', '_');
					}

					dfield.setName(str1);
					String str2 = rs.getString(i + 1);
					if (str2 == null)
						str2 = "";
					dfield.setValue(str2);
					kcoll.addElement(dfield);
				}
				icoll.addElement(kcoll);
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			try {
				stmt.close();
			} catch (Exception ee) {
			}
			try {
				closeConnection();
			} catch (Exception ee) {
			}
			throw e;
		}
		return;
	}
}
