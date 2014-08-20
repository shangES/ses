package icbc.cmis.flow.util;

/*
 * 创建日期 2006-3-7
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

import oracle.jdbc.driver.*;
import icbc.cmis.service.CmisDao;
import icbc.cmis.operation.*;

import java.util.*;
import java.io.IOException;
import java.sql.*;
import icbc.cmis.base.TranFailException;
import icbc.cmis.util.DBTools;

/**
 * Title: Description: 待处理查询 Copyright: Copyright (c) 2005 Company:icbcsdc
 * 
 * @author：郑期彬
 * @version 1.0
 */
public class unit_content_nopersonbanklistDao extends CmisDao {

	String langCode="zh_CN";
	public unit_content_nopersonbanklistDao(CmisOperation op) {
		super(op);
		try
		{
			langCode=(String)op.getSessionData("LangCode");
		}
		catch (Exception ex)
		{
			langCode="zh_CN";
		}

	}

	private String killnull(String strin) {
		if (strin == null) {
			return "";
		} else {
			return strin;
		}
	}

	/**
	 * 取可以发送的地区
	 * 
	 * @param areacode
	 *            地区号
	 * @param busi_type
	 *            业务性质，0，自营，1，委托
	 * @param entcode
	 *            客户代码
	 * @return
	 * @throws TranFailException
	 */
	public ArrayList getnextarea(String areacode, String busi_type,
			String entcode) throws TranFailException {
		ArrayList alist = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			getConnection("cmis3");

			//取本级行以及直属上级行以及定义的可以发送的行
			sql = "select area_code,area_name "
					+ "from mag_area "
					+ "where area_code = ? or area_code=(select belong_bank from mag_area where area_code=?) "
					+ "union "
					+ "select area_code,area_name "
					+ "from mag_area "
					+ "where area_code in (select report_area_code from mag_report_area where busi_type=? and (enp_code='*' or enp_code = ?) and apply_area_code = ?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, areacode);
			pstmt.setString(2, areacode);
			pstmt.setString(3, busi_type);
			pstmt.setString(4, entcode);
			pstmt.setString(5, areacode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				HashMap hmap = new HashMap();
				hmap.put("area_code", killnull(rs.getString("area_code")) + "|"
						+ killnull(rs.getString("area_name")));
				hmap.put("area_name", killnull(rs.getString("area_name")));
				alist.add(hmap);
			}
		} catch (TranFailException te) {
			throw te;
		} catch (Exception e) {
			throw new TranFailException("util_content_choosenextDao",
					"util_content_choosenextDao.getnextarea", e.getMessage());
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
		return alist;
	}

	/**
	 * 功能描述: 查询下一环节列表
	 * 
	 * @param flowtype
	 *            //流程种类
	 * @return
	 * @throws
	 */
	public ArrayList getflowlist(String flowtype, String busi_type)
			throws TranFailException {
		ArrayList alist = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			getConnection("cmis3");
			sql = " select role_code,role_name"
					+ " from imag_role_major_new   "
					+ " where role_type= (select kind_type from imag_kind where kind_code=? and lang_code='"+langCode+"') and lang_code='"+langCode+"' and role_flag='1' order by role_code ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, flowtype);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				HashMap hmap = new HashMap();
				hmap.put("role_code", killnull(rs.getString("role_code")) + "|"
						+ killnull(rs.getString("role_name")));
				hmap.put("role_name", killnull(rs.getString("role_name")));
				alist.add(hmap);
			}
		} catch (TranFailException te) {
			throw te;
		} catch (Exception e) {
			throw new TranFailException("util_content_choosenextDao",
					"util_content_choosenextDao.getflowlist", e.getMessage());
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
		return alist;
	}

	/**
	 * 查询代本人分发
	 * 
	 * @param areacode
	 *            地区号
	 * @param employeecode
	 *            柜员号
	 * @param busitype
	 *            业务大类
	 * @return
	 * @throws TranFailException
	 */
	public Vector getnopersonlist(String areacode, String employeecode,
			String busitype) throws TranFailException {
		String queryStr = "";

		queryStr = "select process001,(select ta200011003 from ta200011 where ta200011001 = process001) as ta200011003,process002,"
				+ "process003,(select kind_name from imag_kind where kind_code = process003 and lang_code='"+langCode+"') as kind_name, "
				+ "process004,(select pa002 from ipa200021 where pa001 = process004 and lang_code='"+langCode+"') as pa002, "
				+ "process005,process006,(select role_name from imag_role_major_new where role_code = process006  and lang_code='"+langCode+"' "
				+ "and role_type = (select kind_type from imag_kind where kind_code = process003  and lang_code='"+langCode+"')  )  as role_name,process007,process008 ,(select decode(ta200011040,'00',ta200011070,ta200011040) from ta200011 where ta200011001 = process001) as ta200011040,"
				+ "process023,process024 "
				+ " from mprocess_new "
				+ " where process006='7' and process007 = ? and process004 in (select pa001 from ipa200021 where pa004=? and lang_code='"+langCode+"') and process008 = ? and ( process009 = '0' or process009 = '1' )";

		try {
			DBTools srv = new DBTools(this.getOperation());
			Vector param = new Vector(3);
			param.add(areacode);
			param.add(busitype);
			param.add(employeecode);
			Vector out = new Vector();
			out.add("process001");
			out.add("ta200011003");
			out.add("process002");
			out.add("process003");
			out.add("kind_name");
			out.add("process004");
			out.add("pa002");
			out.add("process005");
			out.add("process006");
			out.add("role_name");
			out.add("process007");
			out.add("process008");
			out.add("ta200011040");
			out.add("process023");
			out.add("process024");
			Vector v_result = srv.executeSQLResult(queryStr, param, out);
			return v_result;
		} catch (TranFailException te) {
			throw te;
		} catch (Exception e) {
			throw new TranFailException("unit_content_nopersonbanklistDao",
					"unit_content_nopersonbanklistDao.getnopersonlist", e
							.getMessage());
		}
	}

	/**
	 * 待本环节分发
	 * 
	 * @param areacode
	 *            地区号
	 * @param busitype
	 *            业务大类
	 * @return
	 * @throws TranFailException
	 */
	public Vector getnobanklist(String areacode, String busitype)
			throws TranFailException {
		String queryStr = "";

		queryStr = "select process001,(select ta200011003 from ta200011 where ta200011001 = process001) as ta200011003,process002,"
				+ "process003,(select kind_name from imag_kind where kind_code = process003  and lang_code='"+langCode+"') as kind_name, "
				+ "process004,(select pa002 from ipa200021 where pa001 = process004 and lang_code='"+langCode+"') as pa002, "
				+ "process005,process006,(select role_name from imag_role_major_new where role_code = process006  and lang_code='"+langCode+"'"
				+ "and role_type = (select kind_type from imag_kind where kind_code = process003  and lang_code='"+langCode+"')  )  as role_name,process007,process008 ,(select decode(ta200011040,'00',ta200011070,ta200011040) from ta200011 where ta200011001 = process001) as ta200011040,"
				+ "process023,process024"
				+ " from mprocess_new "
				//
				+ " where   process007 = ? and process004 in (select pa001 from ipa200021 where pa004=? and lang_code='"+langCode+"') and (process008 is null or process008='') and ( process009 = '0' or process009 = '1' )";
		try {
			DBTools srv = new DBTools(this.getOperation());
			Vector param = new Vector(2);
			param.add(areacode);
			param.add(busitype);
			Vector out = new Vector();
			out.add("process001");
			out.add("ta200011003");
			out.add("process002");
			out.add("process003");
			out.add("kind_name");
			out.add("process004");
			out.add("pa002");
			out.add("process005");
			out.add("process006");
			out.add("role_name");
			out.add("process007");
			out.add("process008");
			out.add("ta200011040");
			out.add("process023");
			out.add("process024");
			Vector v_result = srv.executeSQLResult(queryStr, param, out);
			return v_result;
		} catch (TranFailException te) {
			throw te;
		} catch (Exception e) {
			throw new TranFailException("unit_content_nopersonbanklistDao",
					"unit_content_nopersonbanklistDao.getnobanklist", e
							.getMessage());
		}
	}
}