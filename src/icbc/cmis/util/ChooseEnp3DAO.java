/*
//updated by ChenJ 20030526 for prepareStmt

//updatee by chenj 修改根据柜员代码检查机构客户查询权限的函数，考虑了输入未完成的客户 20040809
*/
package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.genMsg;

import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import icbc.missign.Employee;

public class ChooseEnp3DAO extends CmisDao {
	//final static int QUERY_LIMIT = 200;
	public String LangCode ="";
	public ChooseEnp3DAO(icbc.cmis.operation.CmisOperation op) {
	super(op);
	}

	public void setLangCode(String langCode){
		LangCode = langCode;
	}
	public String[] genSQL(String queryType,Employee employee,String TA200011001,String TA200011003,String TA200011005,String TA200011010,String TA200011011,String TA200011012,String TA200011014,String TA200011016,String TA200011031, Hashtable paras) throws TranFailException {
		String sql[] = {"",""};
		String where = "";
		String where2 = "";
		String langCode = employee.getLangCode();	     //得到语言类型	

		if(TA200011001.length()==15) {
			where += (" and TA200011001 = '" + TA200011001 + "'");
		}
		else if(TA200011001.length() > 0){
			where += (" and TA200011001 like '%" + TA200011001 + "%'");		
		}
		if(TA200011003.length() > 0) {
			where += (" and TA200011003 like '%" + TA200011003 + "%'");
		}
		if(TA200011005.length() > 0) {
			where += (" and TA200011005 like '" + TA200011005 + "%'");
		}
		if(TA200011010.length() > 0) {
			where += (" and TA200011010 = '" + TA200011010 + "'");
		}
		if(TA200011011.length() > 0) {
			where += (" and TA200011011 = '" + TA200011011 + "'");
		}
		if(TA200011012.length() > 0) {
			where += (" and TA200011012 = '" + TA200011012 + "'");
		}
		if(TA200011014.length() > 0) {
			where += (" and TA200011014 like '" + TA200011014.substring(0,1) + "%'");
		}
		if(TA200011016.length() > 0) {
			where += (" and TA200011016 = '" + TA200011016 + "'");
		}
		if(TA200011031.length() > 0) {
			where += (" and TA200011031 = '" + TA200011031 + "'");
		}
		//根据情况加where条件
		try {
			QueryNormalEnp qwhere = (QueryNormalEnp)Class.forName("icbc.cmis.util." + queryType).newInstance();
			where2 = qwhere.getWhere(this.conn,employee,paras);
			if (where2.startsWith("and ")) {
				where2 = where2.substring(4);
			}
			if (where2.startsWith(" and ")) {
				where2 = where2.substring(5);
			}
		}
		catch (Exception ex) {
			throw new TranFailException("cmisutil213","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage(),genMsg.getErrMsgByLang(langCode,"099993"));
		}

		if(where.length() > 0) {
			if(where2.length() > 0){
			where2 = where.substring(4) + " and " + where2;
			}else{
			where2 = where.substring(4);	
			}
		}

		//add by chenj for jxo 根据柜员代码检查是否有权查询机构客户

//		if(queryType.equals("QueryNormalEnp")||queryType.equals("QueryAssurer")){
//			
//		}
//		else{
//			int eclass1 = Integer.valueOf(employee.getEmployeeClass()).intValue();
//			if(eclass1!=8){
//			where2 += this.canQueryUnit(employee.getEmployeeCode(),employee.getMdbSID());
//			}
//		}
		int eclass1 = Integer.valueOf(employee.getEmployeeClass()).intValue();
		
		if(eclass1!=8 ){
			where2 += this.canQueryUnit(employee.getEmployeeCode(),employee.getMdbSID());
			if (where2.startsWith("and ")) {
				where2 = where2.substring(4);
			}
			if (where2.startsWith(" and ")) {
				where2 = where2.substring(5);
			}
		}
		//add end

		sql[0] = "select count(*) from ta200011 where " + where2;
//    sql[1] = "select * from (select TA200011001, TA200011003, TA200011005, TA200011037,RANK() OVER (ORDER BY ta200011001) rnk FROM ta200011 "
//           + " where " + where2 + ") WHERE rnk >= ? AND rnk <= ? ";
//updated by ChenJ 20030526 for prepareStmt
//		sql[1] = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
//					 + "select TA200011001, TA200011003, TA200011005, TA200011037 FROM ta200011 "
//					 + " where " + where2 + ") a  WHERE ROWNUM <= ?) WHERE rnum >= ?";
		//LXM 增加客户状态显示项
		sql[1] = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
					 + "select TA200011001,replace(TA200011003,'&',chr(38)||'amp;'), TA200011005,TA200011037,ta200011059 FROM ta200011 "
					 + " where " + where2 + ") a  WHERE ";//ROWNUM <= ?) WHERE rnum >= ?";

//		System.out.println(sql[0]);
//		System.out.println(sql[1]);
		return sql;
	}

	public int getRecordCount (String sql) throws TranFailException {		
		
		Statement stmt = null;
		ResultSet rs = null;
		int ret = 0;
		try {
			this.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			ret = rs.getInt(1);
		}
		catch (TranFailException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new TranFailException("cmisutil214","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage() + sql,genMsg.getErrMsgByLang(LangCode,"099993"));
		}
		finally {
			if(rs != null) try {rs.close();} catch (Exception ex) {};
			if(stmt != null) try {stmt.close();} catch (Exception ex) {};
			this.closeConnection();
		}
		return ret;
	}

	public Vector query(String sql,int begin, int end) throws TranFailException  {		
		
		Vector ret = new Vector();
		//PreparedStatement pstmt = null;
                Statement stmt = null;
		ResultSet rs = null;
		try {
			this.getConnection();

//			pstmt = conn.prepareStatement(sql);
			stmt = conn.createStatement();
//			pstmt.setInt(2,begin);
//			pstmt.setInt(1,end);
			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)};//LXM add 20060228
//        String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),String.valueOf(begin + i)};
				ret.add(row);
				i++;
			}
		}
		catch (TranFailException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new TranFailException("cmisutil215","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage() + sql,genMsg.getErrMsgByLang(LangCode,"099993"));
		}
		finally {
			if(rs != null) try {rs.close();} catch (Exception ex) {};
//			if(pstmt != null) try {pstmt.close();} catch (Exception ex) {};
			if(stmt != null) try {stmt.close();} catch (Exception ex) {};
			this.closeConnection();
		}
		return ret;
	}


/**
 * add by chenj for jxo 根据柜员代码检查是否有权查询机构客户
 * @param employee_code java.lang.String
 * @param area_code java.lang.String
 */
   
	public String canQueryUnit(String employee_code,String area_code) throws TranFailException {		
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String retSql = null;	
		try {
			this.getConnection();
			String sql = "select substr(query_flag,0,2) from mag_query_jg where employee_code = ? and area_code = ? and rownum = 1 ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,employee_code);
			pstmt.setString(2,area_code);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				String falg = rs.getString(1);
				if(falg.equals("00")){//都无权查询
					retSql = " and (ta200011010 is null or ta200011010 not in ('15','16'))";
				}
				else if(falg.equals("11")){//第一位表示政府类16，第二位表示军队类15，0表示无权，1表示有权。
					retSql = " ";	
				}
				else if(falg.equals("10")){//可以查政府类 16
					retSql = " and (ta200011010 is null or ta200011010 not in ('15'))";
				}
				else if(falg.equals("01")){//可以查军队类 15
					retSql = " and (ta200011010 is null or ta200011010 not in ('16'))";
				}
			}
			else{//都无权查询
				retSql = " and (ta200011010 is null or ta200011010 not in ('15','16'))";
			}
			
			return retSql;

		}
		catch (TranFailException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new TranFailException("cmisutil217","icbc.cmis.util.ChooseEnp3DAO",ex.getMessage(),genMsg.getErrMsgByLang(LangCode,"099993"));
		}
		finally {
			if(rs != null) try {rs.close();} catch (Exception ex) {};
			if(pstmt != null) try {pstmt.close();} catch (Exception ex) {};
			this.closeConnection();
		}
	}
	
}