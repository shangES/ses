/*
//updated by ChenJ 20030526 for prepareStmt
//20060221，应项目标准值要求临时修改排序方式
*/
package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.service.SQLStatement;
import icbc.cmis.base.TranFailException;
import java.sql.*;
import java.util.*;
import java.util.Hashtable;

public class ParaAdjustViewDAO extends CmisDao {
	//final static int QUERY_LIMIT = 200;

	public ParaAdjustViewDAO(icbc.cmis.operation.CmisOperation op) {
		super(op);
	}

	public int getCount(String tableCode) throws TranFailException {
		String sql = "select count(*) from " + tableCode;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			this.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			throw new TranFailException("getCount", "icbc.cmis.util.ParaAdjustViewDAO", e.getMessage(), e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			this.closeConnection();
		}
	}

	public Vector getData(String tableCode, Vector colomnsInfo, int begin, int end) throws TranFailException {
		Vector ret = new Vector();
		int i;
		Hashtable ht0 = (Hashtable)colomnsInfo.get(0);
		String sql = "select " + (String)ht0.get("col");
		for (i = 1; i < colomnsInfo.size(); i++) {
			Hashtable ht = (Hashtable)colomnsInfo.get(i);
			sql += "," + (String)ht.get("col");
		}
		
		//--20060221，应项目标准值要求临时修改排序方式，待其模块完成后撤除
		if (tableCode.substring(0, 2).toLowerCase().equals("sd")) {
			sql += " from " + tableCode + " order by 1,2,3,4";
		} else {
			sql += " from " + tableCode + " order by " + (String)ht0.get("col");
		}//--
		
		
		//sql += " from " + tableCode + " order by " + (String)ht0.get("col");

		sql = "SELECT * FROM ( SELECT ROWNUM rnum,a.* FROM (" + sql + " ) a  WHERE ROWNUM <= ? ) WHERE rnum >= ?";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, end);
			pstmt.setInt(2, begin);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String[] str = new String[colomnsInfo.size() + 1];
				for (i = 0; i < colomnsInfo.size() + 1; i++) {
					if (rs.getString(i + 1) != null)
						str[i] = rs.getString(i + 1);
					else
						str[i] = "";
				}
				ret.add(str);
			}
		} catch (Exception e) {
			throw new TranFailException("getData", "icbc.cmis.util.ParaAdjustViewDAO", e.getMessage(), e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			this.closeConnection();
		}

		return ret;
	}
}
