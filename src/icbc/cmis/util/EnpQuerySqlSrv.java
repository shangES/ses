package icbc.cmis.util;

import java.util.*;
import java.sql.*;
import icbc.cmis.base.TranFailException;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-7 17:42:19)
 * @author: Administrator
 */
public class EnpQuerySqlSrv extends icbc.cmis.service.CmisDao {
/**
 * EnpQuerySqlSrv constructor comment.
 */
public EnpQuerySqlSrv(icbc.cmis.operation.CmisOperation op) {
  	super(op);
  }

/**
 * EnpQuerySqlSrv constructor comment.
 * @param dbResourceName1 java.lang.String
 */
public EnpQuerySqlSrv(String dbResourceName1,icbc.cmis.operation.CmisOperation op) {
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
		throw new TranFailException("xdtz22125","EnpQuerySqlSrv.getUserInfo(String,String)",e.getMessage());
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
                System.out.println(sql);
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
		throw new TranFailException("xdtz22125","EnpQuerySqlSrv.queryResult(String,Hashtable)",e.getMessage());
	}

}
}
