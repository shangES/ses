package icbc.cmis.util;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.base.TranFailException;
import java.util.*;
import icbc.cmis.second.pkg.*;
import icbc.cmis.base.*;
/**
 * Title:
 * Description: common sql query for general use
 * Copyright:   Copyright (c) 2001
 * Company:
 * @author      wu
 * @version 1.0
 *
 * update date: 2003-06-08
 * update content:修改绑定变量
 * updated by WuQQ
 *
 * update date: 2002-09-12
 * update content: remove print message
 * updated by WuQQ
 */

public class CommonSql extends CmisOperation {

	public CommonSql() {
		super();
	}

	public void execute(){
	}

	/**
	 * Common date query 非绑定变量方式查询
	 * 创建日期：(2002-5-17 15:16:30)
	 * @param String sql
	 * @return ArrayList query result
	 * @exception java.lang.Exception 异常说明。
	 */
	public java.util.ArrayList getDate(String sql) throws java.lang.Exception {
		icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
		icbc.cmis.operation.SqlTool tool = new icbc.cmis.operation.SqlTool(this);
		java.sql.ResultSet rs = null;
		java.util.ArrayList aArrayList = null;
		try {
			tool.getConn();
			rs = tool.executeQuery(sql);
			if (rs != null){
				aArrayList = new java.util.ArrayList();
				//int iColumnCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					aArrayList.add(rs.getString(1));
				}
			}
			rs.close();
			tool.closeconn();
		}catch (Exception e) {
			tool.closeconn();
			throw new TranFailException("xdtzutil???", "icbc.cmis.util.CommonSql.getDate()", "查询时间失败！",e.getMessage());
		}
		return aArrayList;
	}
/**
	 * Common date query 绑定变量方式查询
	 * 创建日期：(2002-5-17 15:16:30)
	 * @param String sql
	 * @return ArrayList query result
	 * @exception java.lang.Exception 异常说明。
	 */
	public java.util.ArrayList getDate(String sql,java.util.Vector vValueData) throws java.lang.Exception {
		icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
		icbc.cmis.operation.SqlTool tool = new icbc.cmis.operation.SqlTool(this);
		java.sql.ResultSet rs = null;
		java.util.ArrayList aArrayList = null;
		try {
			tool.getConn();
			rs = tool.executeQuery(sql,vValueData);
			if (rs != null){
				aArrayList = new java.util.ArrayList();
				//int iColumnCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					aArrayList.add(rs.getString(1));
				}
			}
			rs.close();
			tool.closeconn();
		}catch (Exception e) {
			tool.closeconn();
			throw new TranFailException("xdtzutil???", "icbc.cmis.util.CommonSql.getDate()", "查询时间失败！",e.getMessage());
		}
		return aArrayList;
	}
	/**
	 * 非绑定变量方式查询
	 * 创建日期：(2002-1-15 15:16:30)
	 * @param String sql
	 * @return int query count
	 * @exception java.lang.Exception 异常说明。
	 */
	public  int checkData(String sql) throws java.lang.Exception {
		icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
		icbc.cmis.operation.SqlTool tool = new icbc.cmis.operation.SqlTool(this);
		java.sql.ResultSet rs = null;
		int dataAmount = 0;
		try {
			tool.getConn();
			rs = tool.executeQuery(sql);
			if (rs != null){
				while (rs.next()) {
					dataAmount = rs.getInt(1);
				}
			}
			rs.close();
			tool.closeconn();
		}catch (Exception e) {
			tool.closeconn();
			throw new TranFailException("xdtzutil???", "icbc.cmis.util.CommonSql.checkAmount()","查询记录总数失败！",e.getMessage());
		}
		return dataAmount;
	}
/**
	 * 绑定变量方式查询
	 * 创建日期：(2002-1-15 15:16:30)
	 * @param String sql
	 * @return int query count
	 * @exception java.lang.Exception 异常说明。
	 */
	public  int checkData(String sql,java.util.Vector vValueData) throws java.lang.Exception {
		icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
		icbc.cmis.operation.SqlTool tool = new icbc.cmis.operation.SqlTool(this);
		java.sql.ResultSet rs = null;
		int dataAmount = 0;
		try {
			tool.getConn();
			rs = tool.executeQuery(sql,vValueData);
			if (rs != null){
				while (rs.next()) {
					dataAmount = rs.getInt(1);
				}
			}
			rs.close();
			tool.closeconn();
		}catch (Exception e) {
			tool.closeconn();
			throw new TranFailException("xdtzutil???", "icbc.cmis.util.CommonSql.checkAmount()","查询记录总数失败！",e.getMessage());
		}
		return dataAmount;
	}
	/**
	 * 查询单记录(一个字段)
	 * 创建日期：(2002-1-15 15:16:30)
	 * @param String sql
	 * @return String query result
	 * @exception java.lang.Exception 异常说明。
	 */
	public   java.lang.String getSingleResult(String sql) throws java.lang.Exception {
		icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
		icbc.cmis.operation.SqlTool tool = new icbc.cmis.operation.SqlTool(this);
		java.sql.ResultSet rs = null;
		//java.util.ArrayList aArrayList = null;
		String aStr = null;

		try {
			tool.getConn();
			rs = tool.executeQuery(sql);
			if (rs != null){
				//aArrayList = new java.util.ArrayList();
				//int iColumnCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					//aArrayList.add("first element");
					//for(int i=1;i<iColumnCount+1;i++){
						//aArrayList.add(rs.getString(i));
					//}
					aStr = rs.getString(1);
				}
			}
			rs.close();
			tool.closeconn();
		}catch (Exception e) {
			tool.closeconn();
			throw new TranFailException("xdtz0FFK804", "icbc.cmis.util.CommonSql.getResult()", "查询记录失败！",e.getMessage());
		}
		return aStr;
	}
	/**
	 * 查询单记录(一个字段)
	 * 创建日期：(2002-1-15 15:16:30)
	 * @param String sql
	 * @return String query result
	 * @exception java.lang.Exception 异常说明。
	 */
	public   java.lang.String getSingleResult(String sql,java.util.Vector vValueData) throws java.lang.Exception {
		icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
		icbc.cmis.operation.SqlTool tool = new icbc.cmis.operation.SqlTool(this);
		java.sql.ResultSet rs = null;
		//java.util.ArrayList aArrayList = null;
		String aStr = null;

		try {
			tool.getConn();
			rs = tool.executeQuery(sql,vValueData);
			if (rs != null){
				//aArrayList = new java.util.ArrayList();
				//int iColumnCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					//aArrayList.add("first element");
					//for(int i=1;i<iColumnCount+1;i++){
						//aArrayList.add(rs.getString(i));
					//}
					aStr = rs.getString(1);
				}
			}
			rs.close();
			tool.closeconn();
		}catch (Exception e) {
			tool.closeconn();
			throw new TranFailException("xdtz0FFK804", "icbc.cmis.util.CommonSql.getResult()", "查询记录失败！",e.getMessage());
		}
		return aStr;
	}
	/**
	 * 查询单记录(多字段)
	 * 创建日期：(2002-1-15 15:16:30)
	 * @param String sql
	 * @return ArrayList query result
	 * @exception java.lang.Exception 异常说明。
	 */
	public  java.util.ArrayList getSingleListResult(String sql) throws java.lang.Exception {
		icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
		icbc.cmis.operation.SqlTool tool = new icbc.cmis.operation.SqlTool(this);
		java.sql.ResultSet rs = null;
		java.util.ArrayList aArrayList = null;
		try {
			tool.getConn();
			rs = tool.executeQuery(sql);
			if (rs != null){
				aArrayList = new java.util.ArrayList();
				int iColumnCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					//aArrayList.add("first element");
					for(int i=1;i<iColumnCount+1;i++){
						aArrayList.add(rs.getString(i));
					}
				}
			}
			rs.close();
			tool.closeconn();
		}catch (Exception e) {
			tool.closeconn();
			throw new TranFailException("xdtzUtil000", "icbc.cmis.util.CommonSql.getSingleResult()", "查询记录失败！",e.getMessage());
		}
		return aArrayList;
	}
/**
	 * 查询单记录(多字段)
	 * 创建日期：(2002-1-15 15:16:30)
	 * @param String sql
	 * @return ArrayList query result
	 * @exception java.lang.Exception 异常说明。
	 */
	public  java.util.ArrayList getSingleListResult(String sql,java.util.Vector vValueData) throws java.lang.Exception {
		icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
		icbc.cmis.operation.SqlTool tool = new icbc.cmis.operation.SqlTool(this);
		java.sql.ResultSet rs = null;
		java.util.ArrayList aArrayList = null;
		try {
			tool.getConn();
			rs = tool.executeQuery(sql,vValueData);
			if (rs != null){
				aArrayList = new java.util.ArrayList();
				int iColumnCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					//aArrayList.add("first element");
					for(int i=1;i<iColumnCount+1;i++){
						aArrayList.add(rs.getString(i));
					}
				}
			}
			rs.close();
			tool.closeconn();
		}catch (Exception e) {
			tool.closeconn();
			throw new TranFailException("xdtzUtil000", "icbc.cmis.util.CommonSql.getSingleResult()", "查询记录失败！",e.getMessage());
		}
		return aArrayList;
	}
		/**
	 * 查询多记录(多字段)
	 * 创建日期：(2002-1-15 15:16:30)
	 * @param String sql
	 * @return ArrayList query result
	 * @exception java.lang.Exception 异常说明。
	 */
	public   java.util.ArrayList getMultiListResult(String sql) throws java.lang.Exception {
		icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
		icbc.cmis.operation.SqlTool tool = new icbc.cmis.operation.SqlTool(this);
		java.sql.ResultSet rs = null;
		java.util.ArrayList aArrayList = null;
		java.util.ArrayList bArrayList = new ArrayList();

		try {
			tool.getConn();
			rs = tool.executeQuery(sql);
			if (rs != null) {
				int iColumnCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					aArrayList = new ArrayList();
					for (int j=0;j<iColumnCount;j++){
						aArrayList.add(rs.getString(j+1));
					}
					bArrayList.add(aArrayList);
				}
			}
			rs.close();
			tool.closeconn();
		}catch (Exception e) {
			tool.closeconn();
			throw new TranFailException("xdtzUtil000", "icbc.cmis.util.CommonSql.getMultiListResult()", "查询记录失败！",e.getMessage());
		}
		return bArrayList;
	}
		/**
	 * 查询多记录(多字段)绑定变量方式查询
	 * 创建日期：(2002-1-15 15:16:30)
	 * @param String sql
	 * @return ArrayList query result
	 * @exception java.lang.Exception 异常说明。
	 */
	public   java.util.ArrayList getMultiListResult(String sql,java.util.Vector vValueData) throws java.lang.Exception {
		icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
		icbc.cmis.operation.SqlTool tool = new icbc.cmis.operation.SqlTool(this);
		java.sql.ResultSet rs = null;
		java.util.ArrayList aArrayList = null;
		java.util.ArrayList bArrayList = new ArrayList();

		try {
			tool.getConn();
			rs = tool.executeQuery(sql,vValueData);
			if (rs != null) {
				int iColumnCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					aArrayList = new ArrayList();
					for (int j=0;j<iColumnCount;j++){
						aArrayList.add(rs.getString(j+1));
					}
					bArrayList.add(aArrayList);
				}
			}
			rs.close();
			tool.closeconn();
		}catch (Exception e) {
			tool.closeconn();
			throw new TranFailException("xdtzUtil000", "icbc.cmis.util.CommonSql.getMultiListResult()", "查询记录失败！",e.getMessage());
		}
		return bArrayList;
	}
	/**
	 * 此处插入方法说明。
	 * 创建日期：(2003-01-06 15:16:30)
	 * @param
	 * @return
	 * @exception java.lang.Exception 异常说明。
	 */

	public String delTable(ArrayList inDataValue,ArrayList inDataName,String procName) throws java.lang.Exception {
	String sRet = "OK";
	String sErrMessageFlag = "sErrMessage";
	try {

		DBProcedureParamsDef def = new DBProcedureParamsDef(procName);
		//输入场参数
		for (int i=0;i<inDataValue.size();i++){
			this.setFieldValue((String)inDataName.get(i),inDataValue.get(i));
			def.addInParam((String)inDataName.get(i));
		}
		//输出场参数
		def.addOutParam(sErrMessageFlag);

		/*
		//为调用存储过程准备所需要的数据
		//setFieldValue("rowNum","10");//存储过程输入场所需参数值
		//输入场参数
		//def.addInParam("rowNum");

		//输出场参数
		//def.addOutParam("returnFlag");//存储过程返回码，标识存储过程运行状态
		//def.addOutParam("rowCount");

		//定义存储过程结果集（cursor）返回参数
		//结果集１
		Vector vCursor1 = new Vector(2);
		vCursor1.add("id1");//结果集数据字段
		vCursor1.add("value1");//结果集数据字段
		def.addCursorOutParams(vCursor1,"iResultSet1");//指定结果集存储在当前交易集合中的一个以iResultSet1命名的IndexedDataCollection中
		//结果集２
		Vector vCursor2 = new Vector(2);
		vCursor2.add("id2");//结果集数据字段
		vCursor2.add("value2");//结果集数据字段
		def.addCursorOutParams(vCursor2,"iResultSet2");//指定结果集存储在当前交易集合中的一个以iResultSet２命名的IndexedDataCollection中
		*/

		//调用存储过程
			DBProcedureAccessService proceSrv = new DBProcedureAccessService(this);
			int returnCode = proceSrv.executeProcedure(this.getOperationData(),def);
			proceSrv = null;

		//取存储过程结果信息

		//假设returnCode为0表示存储过程调用成功
		if(returnCode != 0){
			//根据returnCode的不同类型处理存储过程返回的信息returnMessage（如果您的存储过程定义了的话）和业务流程
			//setReplyPage("/icbc/cmis/testlogin.jsp");	//可以是其他页面
			//return;
			sRet = this.getStringAt(sErrMessageFlag);
			return sRet;
		}

		/*
		System.out.println(getStringAt("returnFlag"));
		System.out.println(getStringAt("rowCount"));

		//取结果集信息
			//结果集１
			IndexedDataCollection iResult1 = getIndexedDataCollection("iResultSet1");

			for(int i = 0;i<iResult1.getSize();i++){	//遍历结果集中的每条记录
				KeyedDataCollection aKColl = (KeyedDataCollection)iResult1.getElement(i);
				System.out.println((String)aKColl.getValueAt("id1"));
				System.out.println((String)aKColl.getValueAt("value1"));
			}

			//结果集２
			IndexedDataCollection iResult2 = getIndexedDataCollection("iResultSet2");

			for(int i = 0;i<iResult2.getSize();i++){	//遍历结果集中的每条记录
				KeyedDataCollection aKColl = (KeyedDataCollection)iResult2.getElement(i);
				System.out.println((String)aKColl.getValueAt("id2"));
				System.out.println((String)aKColl.getValueAt("value2"));
			}
			setReplyPage("/icbc/cmis/testlogin.jsp");
			*/
		return sRet;
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			//handler your exception
			throw e;
		}
	}
	/**
	 * 调用增删改的存储过程
	 * 创建日期：(2003-01-06 15:16:30)
	 * @param 入口参数值,入口参数名称,调用的存储过程名称
	 * @return  正确操作后返回"OK",否则返回错误信息
	 * @exception java.lang.Exception 异常说明。
	 */

	public String callUpdateProc(ArrayList inDataName,ArrayList inDataValue,String procName) throws java.lang.Exception {
	String sRet = "OK";
	String sErrMessageFlag = "sErrMessage";
	KeyedDataCollection data = new KeyedDataCollection();
	try {

		DBProcedureParamsDef def = new DBProcedureParamsDef(procName);
		//输入场参数
		for (int i=0;i<inDataValue.size();i++){
			data.addElement((String)inDataName.get(i),(String)inDataValue.get(i));
			def.addInParam((String)inDataName.get(i));
		}
		//输出场参数
		def.addOutParam("sRet");
		def.addOutParam(sErrMessageFlag);

		/*
		//为调用存储过程准备所需要的数据
		//setFieldValue("rowNum","10");//存储过程输入场所需参数值
		//输入场参数
		//def.addInParam("rowNum");

		//输出场参数
		//def.addOutParam("returnFlag");//存储过程返回码，标识存储过程运行状态
		//def.addOutParam("rowCount");

		//定义存储过程结果集（cursor）返回参数
		//结果集１
		Vector vCursor1 = new Vector(2);
		vCursor1.add("id1");//结果集数据字段
		vCursor1.add("value1");//结果集数据字段
		def.addCursorOutParams(vCursor1,"iResultSet1");//指定结果集存储在当前交易集合中的一个以iResultSet1命名的IndexedDataCollection中
		//结果集２
		Vector vCursor2 = new Vector(2);
		vCursor2.add("id2");//结果集数据字段
		vCursor2.add("value2");//结果集数据字段
		def.addCursorOutParams(vCursor2,"iResultSet2");//指定结果集存储在当前交易集合中的一个以iResultSet２命名的IndexedDataCollection中
		*/

		//调用存储过程
			DBProcedureAccessService proceSrv = new DBProcedureAccessService(this);
			//updated by WuQQ
			//int returnCode = proceSrv.executeProcedure(this.getOperationData(),def);
			int returnCode = proceSrv.executeProcedure(data,def);
			proceSrv = null;

		//取存储过程结果信息

		//假设returnCode为0表示存储过程调用成功
		if(returnCode != 0){
			//根据returnCode的不同类型处理存储过程返回的信息returnMessage（如果您的存储过程定义了的话）和业务流程
			//setReplyPage("/icbc/cmis/testlogin.jsp");	//可以是其他页面
			//return;
			//updated by WuQQ
			//sRet = this.getStringAt(sErrMessageFlag);
			sRet = (String)data.getValueAt(sErrMessageFlag);
			return sRet;
		}

		/*
		System.out.println(getStringAt("returnFlag"));
		System.out.println(getStringAt("rowCount"));

		//取结果集信息
			//结果集１
			IndexedDataCollection iResult1 = getIndexedDataCollection("iResultSet1");

			for(int i = 0;i<iResult1.getSize();i++){	//遍历结果集中的每条记录
				KeyedDataCollection aKColl = (KeyedDataCollection)iResult1.getElement(i);
				System.out.println((String)aKColl.getValueAt("id1"));
				System.out.println((String)aKColl.getValueAt("value1"));
			}

			//结果集２
			IndexedDataCollection iResult2 = getIndexedDataCollection("iResultSet2");

			for(int i = 0;i<iResult2.getSize();i++){	//遍历结果集中的每条记录
				KeyedDataCollection aKColl = (KeyedDataCollection)iResult2.getElement(i);
				System.out.println((String)aKColl.getValueAt("id2"));
				System.out.println((String)aKColl.getValueAt("value2"));
			}
			setReplyPage("/icbc/cmis/testlogin.jsp");
			*/
		return sRet;
		//} catch (TranFailException e) {
			//throw e;
		} catch (Exception e) {
			//handler your exception
			throw new TranFailException("CommonSql000", "icbc.cmis.util.CommonSql.callUpdateProc() ", e.toString(),"查询项目评估号失败！");
		}
	}
	/**
	 * 调用增删改的存储过程
	 * 创建日期：(2003-01-06 15:16:30)
	 * @param 入口参数值,入口参数名称,调用的存储过程名称
	 * @return  正确操作后返回"OK",否则返回错误信息
	 * @exception java.lang.Exception 异常说明。
	 */

	public String callInsertProc(ArrayList inDataName,ArrayList inDataValue,String procName) throws java.lang.Exception {
	String sRet = "OK";
	String sErrMessageFlag = "sErrMessage";
	KeyedDataCollection data = new KeyedDataCollection();
	try {

		DBProcedureParamsDef def = new DBProcedureParamsDef(procName);
		//输入场参数
		for (int i=0;i<inDataValue.size();i++){
			data.addElement((String)inDataName.get(i),(String)inDataValue.get(i));
			def.addInParam((String)inDataName.get(i));
		}
		//输出场参数
		def.addOutParam("sRet");
		def.addOutParam(sErrMessageFlag);

		/*
		//为调用存储过程准备所需要的数据
		//setFieldValue("rowNum","10");//存储过程输入场所需参数值
		//输入场参数
		//def.addInParam("rowNum");

		//输出场参数
		//def.addOutParam("returnFlag");//存储过程返回码，标识存储过程运行状态
		//def.addOutParam("rowCount");

		//定义存储过程结果集（cursor）返回参数
		//结果集１
		Vector vCursor1 = new Vector(2);
		vCursor1.add("id1");//结果集数据字段
		vCursor1.add("value1");//结果集数据字段
		def.addCursorOutParams(vCursor1,"iResultSet1");//指定结果集存储在当前交易集合中的一个以iResultSet1命名的IndexedDataCollection中
		//结果集２
		Vector vCursor2 = new Vector(2);
		vCursor2.add("id2");//结果集数据字段
		vCursor2.add("value2");//结果集数据字段
		def.addCursorOutParams(vCursor2,"iResultSet2");//指定结果集存储在当前交易集合中的一个以iResultSet２命名的IndexedDataCollection中
		*/

		//调用存储过程
			DBProcedureAccessService proceSrv = new DBProcedureAccessService(this);
			//updated by WuQQ
			//int returnCode = proceSrv.executeProcedure(this.getOperationData(),def);
			int returnCode = proceSrv.executeProcedure(data,def);
			proceSrv = null;

		//取存储过程结果信息
		sErrMessageFlag = (String)data.getValueAt(sErrMessageFlag);
		//假设returnCode为0表示存储过程调用成功
		if(returnCode != 0){
			//根据returnCode的不同类型处理存储过程返回的信息returnMessage（如果您的存储过程定义了的话）和业务流程
			//setReplyPage("/icbc/cmis/testlogin.jsp");	//可以是其他页面
			//return;
			//updated by WuQQ
			//sRet = this.getStringAt(sErrMessageFlag);

			sRet = sErrMessageFlag;
			return sRet;
		}

		/*
		System.out.println(getStringAt("returnFlag"));
		System.out.println(getStringAt("rowCount"));

		//取结果集信息
			//结果集１
			IndexedDataCollection iResult1 = getIndexedDataCollection("iResultSet1");

			for(int i = 0;i<iResult1.getSize();i++){	//遍历结果集中的每条记录
				KeyedDataCollection aKColl = (KeyedDataCollection)iResult1.getElement(i);
				System.out.println((String)aKColl.getValueAt("id1"));
				System.out.println((String)aKColl.getValueAt("value1"));
			}

			//结果集２
			IndexedDataCollection iResult2 = getIndexedDataCollection("iResultSet2");

			for(int i = 0;i<iResult2.getSize();i++){	//遍历结果集中的每条记录
				KeyedDataCollection aKColl = (KeyedDataCollection)iResult2.getElement(i);
				System.out.println((String)aKColl.getValueAt("id2"));
				System.out.println((String)aKColl.getValueAt("value2"));
			}
			setReplyPage("/icbc/cmis/testlogin.jsp");
			*/
		//正确处理，返回"OK"+提示信息
		return (sRet+sErrMessageFlag);
		//} catch (TranFailException e) {
			//throw e;
		} catch (Exception e) {
			//handler your exception
			throw new TranFailException("CommonSql000", "icbc.cmis.util.CommonSql.callUpdateProc() ", e.toString(),"查询项目评估号失败！");
		}
	}
	/**
	 * 调用增删改的存储过程
	 * 创建日期：(2004-02-03 15:16:30)
	 * @param 入口参数值,入口参数名称,调用的存储过程名称
	 *        outDataName 中存放存储过程out参数
	 * @return  正确操作后返回"OK",否则返回错误信息
	 * @exception java.lang.Exception 异常说明。
	 */

	public ArrayList getProcResult(ArrayList inDataName,ArrayList inDataValue,ArrayList outDataName,String procName) throws java.lang.Exception {
//	String sRet = "OK";
//	String sErrMessageFlag = "sErrMessage";
//	String sRetValue = "sRetValue";
	KeyedDataCollection data = new KeyedDataCollection();
	int i=0;
	try {

		DBProcedureParamsDef def = new DBProcedureParamsDef(procName);
		//输入场参数
		for (i=0;i<inDataValue.size();i++){
			data.addElement((String)inDataName.get(i),(String)inDataValue.get(i));
			def.addInParam((String)inDataName.get(i));
		}
		//输出场参数
		for (i=0;i<outDataName.size();i++){
			def.addOutParam((String)outDataName.get(i));
		}
		//调用存储过程
			DBProcedureAccessService proceSrv = new DBProcedureAccessService(this);
			int returnCode = proceSrv.executeProcedure(data,def);
			proceSrv = null;

		//取存储过程结果信息
		ArrayList ret = new ArrayList(outDataName.size());
		//假设returnCode为0表示存储过程调用成功
		//无论调用是否成功，都将调用结果返回上级方法，上级方法根据ret中第一个数据是否为0判断调用是否成功。
//		if(returnCode != 0){
//			sRet = (String)data.getValueAt(sErrMessageFlag);
			for (i=0;i<outDataName.size();i++){
				ret.add(data.getValueAt((String)outDataName.get(i)));
			}
			return ret;
//		}
//		return ;
		} catch (Exception e) {
			//handler your exception
			throw new TranFailException("CommonSql000", "icbc.cmis.util.CommonSql.callUpdateProc() ", e.toString(),"查询项目评估号失败！");
		}
	}
}