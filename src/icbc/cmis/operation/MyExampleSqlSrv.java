package icbc.cmis.operation;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.TranFailException;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-7 17:42:19)
 * @author: Administrator
 */
public class MyExampleSqlSrv extends icbc.cmis.service.CmisDao {
/**
 * MyExampleSqlSrv constructor comment.
 */
public MyExampleSqlSrv(icbc.cmis.operation.CmisOperation op) {
	super(op);
}
/**
 * MyExampleSqlSrv constructor comment.
 * @param dbResourceName1 java.lang.String
 */
public MyExampleSqlSrv(String dbResourceName1,icbc.cmis.operation.CmisOperation op) {
	super(dbResourceName1,op);
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 19:00:47)
 * @return java.util.Hashtable
 * @param areaCode java.lang.String
 */
public Hashtable getAreaInfo(String areaCode) throws TranFailException{
	Hashtable table = null;
	Statement stmt = null;
	try{
		getConnection();
		stmt = conn.createStatement();
		String sql = "select area_name,bank_flag from mag_area where area_code = '" + areaCode + "'";
		ResultSet rs= stmt.executeQuery(sql);
		if(rs.next()){
			table = new Hashtable();
			table.put("1",rs.getString(1));
			table.put("2",rs.getString(2));
		}
		stmt.close();
		closeConnection();
		return table;

	}catch(TranFailException e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw e;
	}
	catch(Exception e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw new TranFailException("xdtz22125","MyExampleSqlSrv.getUserInfo(String,String)",e.getMessage());
	}
}


/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 18:52:25)
 * @return java.util.Hashtable
 * @param acctCode java.lang.String
 * @param areaCode java.lang.String
 * @param major java.lang.String
 */
public Hashtable getAuth(String acctCode, String areaCode, String major)throws TranFailException{
	Hashtable table = null;
	Statement stmt = null;
	try{
		getConnection();
		stmt = conn.createStatement();
		String sql= "select EMPLOYEE_CLASS, CLASS_NAME from MAG_EMPLOYEE_MAJOR, MAG_EMPLOYEE_CLASS "
					+ "WHERE EMPLOYEE_AREA = '"
					+ areaCode
					+ "' and EMPLOYEE_CODE = '"
					+ acctCode
					+ "' and EMPLOYEE_MAJOR = '"
					+ major
					+ "' and EMPLOYEE_CLASS = CLASS_CODE(+)";
	ResultSet rs= stmt.executeQuery(sql);
	if(rs.next()){
		table = new Hashtable();
		table.put("1",rs.getString(1));
		table.put("2",rs.getString(2));
	}
	stmt.close();
	closeConnection();
	return table;

	}catch(TranFailException e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw e;
	}
	catch(Exception e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw new TranFailException("xdtz22125","MyExampleSqlSrv.getUserInfo(String,String)",e.getMessage());
	}

}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 20:19:41)
 * @return java.lang.String
 * @param branch java.lang.String
 */
public Vector getBranchArea(String branch)throws TranFailException {
	Vector  banchArea = new Vector();
	Statement stmt = null;
	try{
		getConnection();
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select AREA_CODE from mag_area where BELONG_BANK = '" + branch + "' or SECOND_BANK = '" + branch + "'");
	    while(rs.next())
			banchArea.add(rs.getString(1));

		stmt.close();
		closeConnection();
		return banchArea;

	}catch(TranFailException e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw e;
	}
	catch(Exception e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw new TranFailException("xdtz22125","MyExampleSqlSrv.getUserInfo(String,String)",e.getMessage());
	}

}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 19:33:16)
 * @return java.lang.String
 * @param areaCode java.lang.String
 * @param employCode java.lang.String
 * @param mayor java.lang.String
 */
public String getEmployeeFun(String areaCode, String employCode, String major)throws TranFailException{
	String  emFun = null;
	Statement stmt = null;
	try{
		getConnection();
		stmt = conn.createStatement();
		String  sql = "select employee_func from mag_employee_major where employee_area = '" + areaCode
		 			 + "' and employee_code = '" + employCode + "' and employee_major = '" + major + "'";
	  ResultSet rs = stmt.executeQuery(sql);
	  if(rs.next())
		emFun = rs.getString(1);

		stmt.close();
		closeConnection();
		return emFun;

	}catch(TranFailException e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw e;
	}
	catch(Exception e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw new TranFailException("xdtz22125","MyExampleSqlSrv.getUserInfo(String,String)",e.getMessage());
	}

}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 18:13:47)
 * @return java.lang.String
 */
public String getMajor(String major)throws TranFailException {
	String  ma = null;
	Statement stmt = null;
	try{
		getConnection();
		stmt = conn.createStatement();
		String sql= "select major_name from mag_major where major_code = '" + major + "'";
		ResultSet rs= stmt.executeQuery(sql);
		if(rs.next())
			ma = rs.getString(1);

		stmt.close();
		closeConnection();
		return ma;

	}catch(TranFailException e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw e;
	}
	catch(Exception e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw new TranFailException("xdtz22125","MyExampleSqlSrv.getUserInfo(String,String)",e.getMessage());
	}

}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 19:42:49)
 * @return java.util.Hashtable
 * @param major java.lang.String
 * @param pCode java.lang.String
 * @param bankFlag java.lang.String
 */
public Vector getMenuInfo(String major, int pCode, String bankFlag,String mployeeClass) throws TranFailException{
	Vector v = null;
	Statement stmt = null;
	try{
		getConnection();
		stmt = conn.createStatement();
		String sql = "select app_module_code,app_name,app_subnode,app_address from MAG_APPLICATION where app_major_code = '" + major
				  + "' and app_pmodule_code = '" + String.valueOf(pCode) + "' and substr(APP_PRIVILEGE,"+ mployeeClass +",1) = '1'  and substr(APP_CLASS,"+ bankFlag +" + 1 ,1) = '1' ";
		ResultSet rs = stmt.executeQuery(sql);
		v = new Vector();
		while(rs.next()){
			Hashtable table = new Hashtable();
			String tmp4 = (String)rs.getString(4);
			if(tmp4 == null) tmp4 = "";
			String tmp3 = (String)rs.getString(3);
			if(tmp3 == null) tmp3 = "";
			String tmp2 = (String)rs.getString(2);
			if(tmp2 == null) tmp2 = "";
			String tmp1 = (String)rs.getString(1);
			if(tmp1 == null) tmp1 = "";
			table.put("1",tmp1);
			table.put("2",tmp2);
			table.put("3",tmp3);
			table.put("4",tmp4);
			v.add(table);
		}
		stmt.close();
		closeConnection();
		return v;
	}catch(TranFailException e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw e;
	}
	catch(Exception e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw new TranFailException("xdtz22125","MyExampleSqlSrv.getUserInfo(String,String)",e.getMessage());

	}
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 17:47:02)
 * @return java.util.Hashtable
 * @param acctCode java.lang.String
 * @param areaCode java.lang.String
 */
public Hashtable getUserInfo(String acctCode, String areaCode)throws TranFailException {

	Statement stmt = null;
	Hashtable table = null;

	try{

		getConnection();

		String sql= "select employee_name,employee_passwd,employee_useful,employee_delete_flag,employee_enable_flag from mag_employee"
					+ " where employee_code = '"
					+ acctCode
					+ "' and mdb_sid = '"
					+ areaCode
					+ "'";
		stmt = conn.createStatement();
		ResultSet rs= stmt.executeQuery(sql);
		if(rs.next()){
			table = new Hashtable();
			table.put("1",rs.getString(1));
			table.put("2",rs.getString(2));
			table.put("3",rs.getString(3));
			table.put("4",rs.getString(4));
			table.put("5",rs.getString(5));
		}

		stmt.close();

	}catch(TranFailException e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw e;
	}
	catch(Exception e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw new TranFailException("xdtz22125","MyExampleSqlSrv.getUserInfo(String,String)",e.getMessage());

	}
	closeConnection();
	return table;
}
/**
 * Insert the method's description here.
 * Creation date: (2002-1-7 18:52:25)
 * @return java.util.Hashtable
 * @param acctCode java.lang.String
 * @param areaCode java.lang.String
 * @param major java.lang.String
 */
public Vector queryResult(String swhere,Hashtable h_tmp)throws TranFailException{
	Vector vTable = new Vector();
	Statement stmt = null;
	try{
		getConnection();
		stmt = conn.createStatement();
		String select= (String) h_tmp.get("select");
		String from= (String) h_tmp.get("from");
		String sql= "select " + select + " from " + from +" where "+swhere;
		ResultSet rs= stmt.executeQuery(sql);
		while(rs.next()){
			Hashtable hTable = new Hashtable();
			hTable.put("1",rs.getString(1));
			hTable.put("2",rs.getString(2));
			hTable.put("3",rs.getString(3));
			vTable.add(hTable);
		}
		stmt.close();
		closeConnection();
		return vTable;

	}catch(TranFailException e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw e;
	}
	catch(Exception e){
		try{
			if(stmt != null) stmt.close();
		}catch(Exception e1){}
		closeConnection();
		throw new TranFailException("xdtz22125","MyExampleSqlSrv.getUserInfo(String,String)",e.getMessage());
	}

}
}
