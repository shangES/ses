/*
//updated by ChenJ 20030526 for prepareStmt


  updated by chenj for wqq 20030808
 * 查询条件有变
 * 
 * updated by chenj for yehua 20030808
 * QueryEnp4ScaleQuery
 * 选客户时包含主管行，也包含共享行客户
 * 
 * updated by chenj for jxo 20031020
 * 。五级分类的查询QueryAssurerWJFL
 * 
*/

package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.CmisConstance;
import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import icbc.missign.Employee;
import icbc.cmis.util.QueryNormalEnp;

public class ChooseEnpBiftDAO extends CmisDao {
	//final static int QUERY_LIMIT = 200;

	public ChooseEnpBiftDAO(icbc.cmis.operation.CmisOperation op) {
		super(op);
	}

	public String[] genSQL(
		String queryType,
		Employee employee,
		String TA200011001,
		Hashtable paras)
		throws TranFailException {
		String sql[] = { "", "" };
		String where = "";
//		String where2 = "";

		if (TA200011001.length() > 0) {
			where += (" TA200011001 = '" + TA200011001 + "' and TA200011014=dict_code(+)");
		}

//		if (queryType.equals("QueryAssurer")) { //查询保证人原郭\u8283
//
//		} 		
//		else { //JXO
//			
//		}


//		if (where2.startsWith("and ")) {
//			where2 = where2.substring(4);
//		}
//		if (where2.startsWith(" and ")) {
//			where2 = where2.substring(5);
//		}
//		if (where.length() > 0) {
//			where2 = where.substring(4) + " and " + where2;
//		}
//

		sql[0] = "select count(*) from ta200011,da200011014 where " + where;
		//    sql[1] = "select * from (select TA200011001, TA200011003, TA200011005, TA200011037,RANK() OVER (ORDER BY ta200011001) rnk FROM ta200011 "
		//           + " where " + where2 + ") WHERE rnk >= ? AND rnk <= ? ";
		//updated by ChenJ 20030526 for prepareStmt
		//		sql[1] = "SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
		//					 + "select TA200011001, TA200011003, TA200011005, TA200011037 FROM ta200011 "
		//					 + " where " + where2 + ") a  WHERE ROWNUM <= ?) WHERE rnum >= ?";
		sql[1] =
			"SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
				+ "select TA200011001, TA200011003,TA200011004,TA200011014,nvl(dict_name,'行业代码有误' ) name014 FROM ta200011,da200011014 "
				+ " where "
				+ where
				+ ") a  WHERE ";
		//ROWNUM <= ?) WHERE rnum >= ?";

		//		System.out.println(sql[0]);
		//		System.out.println(sql[1]);
		return sql;
	}



	public int getRecordCount(String sql) throws TranFailException {
		Statement stmt = null;
		ResultSet rs = null;
		int ret = 0;
		try {
			this.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			//			icbc.cmis.FJ.CommonTools.cmisPrintln(sql);
			rs.next();
			ret = rs.getInt(1);
		} catch (TranFailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TranFailException(
				"cmisutil214",
				"icbc.cmis.util.ChooseEnpBiftDAO",
				ex.getMessage() + sql,
				"查询记录条数错误！");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ex) {
				};
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception ex) {
				};
			this.closeConnection();
		}
		return ret;
	}

	public Vector query(String sql, int begin, int end)
		throws TranFailException {
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
				String[] row =
					{
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6)};
				//        String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),String.valueOf(begin + i)};
				ret.add(row);
				i++;
			}
		} catch (TranFailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TranFailException(
				"cmisutil215",
				"icbc.cmis.util.ChooseEnpBiftDAO",
				ex.getMessage() + sql,
				"产生客户列表错误！");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ex) {
				};
			//			if(pstmt != null) try {pstmt.close();} catch (Exception ex) {};
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception ex) {
				};
			this.closeConnection();
		}
		return ret;
	}
}