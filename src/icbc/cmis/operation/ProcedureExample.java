package icbc.cmis.operation;

import java.util.*;
import icbc.cmis.base.*;
import icbc.cmis.second.pkg.DBProcedureAccessService;
import icbc.cmis.second.pkg.DBProcedureParamsDef;
import icbc.cmis.service.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-10 16:33:34)
 * @author: Administrator
 *
 *	 存储过程访问类定义。使用例子：
 *	   数据库表：
 *	   CREATE TABLE MAG_MAJOR ( 
 *	    MAJOR_CODE  VARCHAR2 (3)  NOT NULL, 
 *	   MAJOR_NAME  VARCHAR2 (20)  NOT NULL
 *	   )
 *	  存储过程：
 *	 create or replace package dbtest as
 *	 	type rec_dbtest is record (
 *	 			out_id	  varchar2(50),
 *	 			out_value  varchar2(50)
 *	 			);	
 *	 	type rec_dbtest1 is record (
 *	 			out_id1	  varchar2(50),
 *	 			out_value1  varchar2(50)
 *	 		);	
 *	 	type ref_dbtest is ref cursor return rec_dbtest;
 *	 	type ref_dbtest1 is ref cursor return rec_dbtest1;		
 *	 	procedure dbtestproc (
 *	 			in_rowNum in varchar2,
 *	 			out_ProcSign	out varchar2,
 *	 			out_rowCount	out varchar2,
 *	 			ret_dbtest 	out ref_dbtest,
 *	 			ret_dbtest1 	out ref_dbtest1
 *	 			);
 *	 end dbtest;
 *	 /
 *	 create or replace package body dbtest as 
 *	 	procedure dbtestproc (
 *	 			in_rowNum in varchar2,
 *	 			out_ProcSign	out varchar2,
 *	 			out_rowCount	out varchar2,
 *	 			ret_dbtest 	out ref_dbtest,
 *	 			ret_dbtest1 	out ref_dbtest1) 
 *	 	as
 *	 	begin
 *	 		out_ProcSign:='0';
 *	 		select count(*) into out_rowCount from mag_major;
 *	 		
 *	 		open ret_dbtest for
 *	 			select major_code,major_name from   Mag_major 
 *	 			where rownum < in_rowNum;
 *	 		open ret_dbtest1 for
 *	 			select major_code,major_name from   Mag_major 
 *	 			where rownum < in_rowNum;
 *	 		commit;
 *	 	exception
 *	 		when NO_DATA_FOUND then
 *	 			  open ret_dbtest for
 *	 			  	   select major_code,major_name from   Mag_major 
 *	 				   where rownum < -1;
 *	 		open ret_dbtest1 for
 *	 			select major_code,major_name from   Mag_major 
 *	 			where rownum < -1;
 *	 			  out_ProcSign:='0';
 *	 		when others then
 *	 			out_ProcSign:='1';
 *	 	end;
 *	 end dbtest;
 *	 /
 *
 */
public class ProcedureExample extends CmisOperation {
/**
 * ProcedureExample constructor comment.
 */
public ProcedureExample() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-10 16:33:34)
 *
 *
 *	 存储过程访问类定义。使用例子：
 *	   数据库表：
 *	   CREATE TABLE MAG_MAJOR ( 
 *	    MAJOR_CODE  VARCHAR2 (3)  NOT NULL, 
 *	   MAJOR_NAME  VARCHAR2 (20)  NOT NULL
 *	   )
 *
 *
 *	  存储过程：
 *	 create or replace package dbtest as
 *	 	type rec_dbtest is record (
 *	 			out_id	  varchar2(50),
 *	 			out_value  varchar2(50)
 *	 			);	
 *	 	type rec_dbtest1 is record (
 *	 			out_id1	  varchar2(50),
 *	 			out_value1  varchar2(50)
 *	 		);	
 *	 	type ref_dbtest is ref cursor return rec_dbtest;
 *	 	type ref_dbtest1 is ref cursor return rec_dbtest1;		
 *	 	procedure dbtestproc (
 *	 			in_rowNum in varchar2,
 *	 			out_ProcSign	out varchar2,
 *	 			out_rowCount	out varchar2,
 *	 			ret_dbtest 	out ref_dbtest,
 *	 			ret_dbtest1 	out ref_dbtest1
 *	 			);
 *	 end dbtest;
 *	 /
 *	 create or replace package body dbtest as 
 *	 	procedure dbtestproc (
 *	 			in_rowNum in varchar2,
 *	 			out_ProcSign	out varchar2,
 *	 			out_rowCount	out varchar2,
 *	 			ret_dbtest 	out ref_dbtest,
 *	 			ret_dbtest1 	out ref_dbtest1) 
 *	 	as
 *	 	begin
 *	 		out_ProcSign:='0';
 *	 		select count(*) into out_rowCount from mag_major;
 *	 		
 *	 		open ret_dbtest for
 *	 			select major_code,major_name from   Mag_major 
 *	 			where rownum < in_rowNum;
 *	 		open ret_dbtest1 for
 *	 			select major_code,major_name from   Mag_major 
 *	 			where rownum < in_rowNum;
 *	 		commit;
 *	 	exception
 *	 		when NO_DATA_FOUND then
 *	 			  open ret_dbtest for
 *	 			  	   select major_code,major_name from   Mag_major 
 *	 				   where rownum < -1;
 *	 		open ret_dbtest1 for
 *	 			select major_code,major_name from   Mag_major 
 *	 			where rownum < -1;
 *	 			  out_ProcSign:='0';
 *	 		when others then
 *	 			out_ProcSign:='1';
 *	 	end;
 *	 end dbtest;
 *	 /
 */
public void execute() throws Exception {

//为调用存储过程准备所需要的数据
	
	setFieldValue("rowNum","10");//存储过程输入场所需参数值
	DBProcedureParamsDef def = new DBProcedureParamsDef("dbtest.dbtestproc");
	//输入场参数
	def.addInParam("rowNum");

	//输出场参数
	def.addOutParam("returnFlag");//存储过程返回码，标识存储过程运行状态
	def.addOutParam("rowCount");

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
	
//指定存储过程用户调用信息,如果使用默认用户cmis3，则不需要以下操作
	
	def.setDBUserName((String)CmisConstance.getParameterSettings().get("dbUserName"));
	def.setDBUserPass((String)CmisConstance.getParameterSettings().get("dbUserPass"));
	 
//调用存储过程
	DBProcedureAccessService proceSrv = new DBProcedureAccessService(this);
	int returnCode = proceSrv.executeProcedure(this.getOperationData(),def);
	proceSrv = null;
		
//取存储过程结果信息
	
	//假设returnCode为0表示存储过程调用成功
	if(returnCode != 0){
		//根据returnCode的不同类型处理存储过程返回的信息returnMessage（如果您的存储过程定义了的话）和业务流程
		setReplyPage("/icbc/cmis/testlogin.jsp");	//可以是其他页面
		return;
	}
	
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
}
}
