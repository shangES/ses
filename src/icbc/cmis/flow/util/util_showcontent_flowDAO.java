/*
 * 创建日期 2006-3-2
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.flow.util;
import oracle.jdbc.driver.*;
import icbc.cmis.service.CmisDao;
import icbc.cmis.operation.*;
import java.util.*;
import java.io.IOException;
import java.sql.*;
import icbc.cmis.base.TranFailException;
import icbc.cmis.util.DBTools;
/**
 * Title:
 * Description: 查询审批流程表中具体内容
 * Copyright:    Copyright (c) 2005
 * Company:icbcsdc
 * @author：郑期彬
 * @version 1.0
 */
public class util_showcontent_flowDAO extends CmisDao{
	public util_showcontent_flowDAO(CmisOperation op) {
		super(op);
		}
	
	/**
	 * 功能描述: 
	 * @param formflag 查询项标志  1,意见说明 2,附加条件或限制性条款内容 3.辅助内容
	 * @param  entcode   //客户代码
	 * @param tradecode //业务申请号
	 * @param flowtype  //流程种类
	 * @param xh  //序号
	 * @param step  //环节(主要用来区分是否是调查人)		 
	 * @return
	 * @throws
	 */	
	 public Vector getcontenttxt(String formflag, String entcode, String tradecode, String flowtype, String xh, String step)
		 throws TranFailException{
		 String queryStr="";
		 if (formflag.equals("1"))
		 {
			queryStr=
			" select process012"
					 +  " from mprocess_new   "
					 +  " WHERE process001=?  AND Tprocess002=?  AND Tprocess003=? AND Tprocess005=? ";
		 }
		 if (formflag.equals("3"))
		{
		 queryStr=
		 " select process019"
					+  " from mprocess_new   "
					+  " WHERE process001=?  AND Tprocess002=?  AND Tprocess003=? AND Tprocess005=? ";
		}
		if (formflag.equals("2") || step.equals("调查环节") )
		 {
			queryStr=
			" select process013,process014,process015,process016,process020"
					 +  " from mprocess_new   "
					 +  " WHERE process001=?  AND Tprocess002=?  AND Tprocess003=? AND Tprocess005=? ";
		 }
		 if (formflag.equals("2") || !step.equals("调查环节"))
		{
		 queryStr=
		 " select process020"
					+  " from mprocess_new   "
					+  " WHERE process001=?  AND Tprocess002=?  AND Tprocess003=? AND Tprocess005=? ";
		}
		 try{
			 DBTools srv = new DBTools(this.getOperation());
			 Vector param = new Vector(4);
			 param.add(entcode);
			 param.add(tradecode);
			 param.add(flowtype);
			 param.add(xh);
			 Vector v_result = srv.executeSQLResult(queryStr,param);
			 return v_result;       
		 }
		 catch(TranFailException te){
			 throw te;
		 }
		 catch(Exception e){
			 throw new TranFailException("util_showcontent_flow","util_showcontent_flowDAO.getcontenttxt",e.getMessage());
		 }
	 }
	 
/**
	 * 功能描述: 查询本人意见说明
	 * @param formflag 查询项标志  1,意见说明 2,附加条件或限制性条款内容 
	 * @param  entcode   //客户代码
	 * @param tradecode //业务申请号
	 * @param xh  //序号
	 * @param step  //环节(主要用来区分是否是调查人)		 
	 * @return
	 * @throws
	 */	
	 public Vector getadvicetxt(String formflag, String entcode, String tradecode, String xh, String step)
		 throws TranFailException{
		 String queryStr="";
		 if (formflag.equals("1"))
		 {
			queryStr=
			" select process012"
					 +  " from mprocess_new   "
					 +  " WHERE process001=?  AND Tprocess002=?  AND Tprocess005=? AND Tprocess006=? ";
		 }
		if (formflag.equals("2") || step.equals("调查环节") )
		 {
			queryStr=
			" select process013,process014,process015,process016,process020"
					 +  " from mprocess_new   "
					 +  " WHERE process001=?  AND Tprocess002=?  AND Tprocess005=? AND Tprocess006=? ";
		 }
		 if (formflag.equals("2") || !step.equals("调查环节"))
		{
		 queryStr=
		 " select process020"
					+  " from mprocess_new   "
					+  " WHERE process001=?  AND Tprocess002=?  AND Tprocess005=? AND Tprocess006=? ";
		}
		 try{
			 DBTools srv = new DBTools(this.getOperation());
			 Vector param = new Vector(4);
			 param.add(entcode);
			 param.add(tradecode);
			 param.add(xh);
			 param.add(step);
			 Vector v_result = srv.executeSQLResult(queryStr,param);
			 return v_result;       
		 }
		 catch(TranFailException te){
			 throw te;
		 }
		 catch(Exception e){
			 throw new TranFailException("util_showcontent_flow","util_showcontent_flowDAO.getcontenttxt",e.getMessage());
		 }
	 }
}
