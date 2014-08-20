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
 * Title: Description: 查询审批流程表中具体内容 Copyright: Copyright (c) 2005
 * Company:icbcsdc
 * 
 * @author：郑期彬
 * @version 1.0
 */
public class util_content_msg2Dao extends CmisDao {
	public util_content_msg2Dao(CmisOperation op) {
		super(op);
	}

	private String killnull(String strin) {
		if (strin == null) {
			return "";
		} else {
			return strin;
		}
	}

	/**
	 * 功能描述: 查询本人分析意见
	 * 
	 * @param entcode
	 *            //客户代码
	 * @param tradecode
	 *            //业务申请号
	 * @param xh
	 *            //序号
	 * @param step
	 *            //环节(主要用来区分是否是调查人)
	 * @return
	 * @throws
	 */
	public Vector getadvicetxt(String entcode, String tradecode, String xh,
			String step) throws TranFailException {
		String queryStr = "";

		queryStr = " select process013,process014,process015,process016,process012,process027,process028"
				+ " from mprocess_new   "
				+ " WHERE process001=?  AND process002=?  AND process005=? AND process006=? ";

		try {
			DBTools srv = new DBTools(this.getOperation());
			Vector param = new Vector(4);
			param.add(entcode);
			param.add(tradecode);
			param.add(xh);
			param.add(step);
			Vector v_result = srv.executeSQLResult(queryStr, param);

			if (v_result.size() == 0) {
				Hashtable hmap = new Hashtable();
				hmap.put("process013", "");
				hmap.put("process014", "");
				hmap.put("process015", "");
				hmap.put("process016", "");
				hmap.put("process012", "");
				hmap.put("process027", "");
				hmap.put("process028", "");
				v_result.add(hmap);
			}

			return v_result;
		} catch (TranFailException te) {
			throw te;
		} catch (Exception e) {
			throw new TranFailException("util_content_msg2Dao",
					"util_content_msg2Dao.getadvicetxt", e.getMessage());
		}
	}

	/**
	 * 功能描述: 查询本人意见说明
	 * 
	 * @param entcode
	 *            //客户代码
	 * @param tradecode
	 *            //业务申请号
	 * @param xh
	 *            //序号
	 * @param step
	 *            //环节(主要用来区分是否是调查人)
	 * @return
	 * @throws
	 */
	public Vector getselfadvicetxt(String entcode, String tradecode, String xh,
			String step) throws TranFailException {
		String queryStr = "";

		queryStr = " select process012"
				+ " from mprocess_new   "
				+ " WHERE process001=?  AND process002=?  AND process005=? AND process006=? ";

		try {
			DBTools srv = new DBTools(this.getOperation());
			Vector param = new Vector(4);
			param.add(entcode);
			param.add(tradecode);
			param.add(xh);
			param.add(step);
			Vector v_result = srv.executeSQLResult(queryStr, param);
			return v_result;
		} catch (TranFailException te) {
			throw te;
		} catch (Exception e) {
			throw new TranFailException("util_content_msg2Dao",
					"util_content_msg2Dao.getselfadvicetxt", e.getMessage());
		}
	}

	/**
	 * 得到该业务是否是发起人flag
	 * 
	 * @param entcode
	 * @param tradecode
	 * @return
	 * @throws TranFailException
	 */
	public String queryfirstflag(String entcode, String tradecode,
			String ordercode, String empareacode, String employeecode)
			throws TranFailException {
		String vret = "";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {

			//得到流程信息列表
			getConnection("cmis3");
			sql = "select count(*) " + "from mprocess_new "
					+ "where process001 = ? " + "and process002 = ? "
					+ "and process005 = '1'" + "and process006 = ? "
					+ "and process007 = ? " + "and process008 = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, entcode);
			pstmt.setString(2, tradecode);
			pstmt.setString(3, ordercode);
			pstmt.setString(4, empareacode);
			pstmt.setString(5, employeecode);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				vret = rs.getString(1);
			}

		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_msg2Dao.queryfirstflag()", e.getMessage(), e
							.getMessage());
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
			closeConnection();
		}
		return vret;
	}
}