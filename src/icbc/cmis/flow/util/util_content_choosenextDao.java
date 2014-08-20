/*
 * 创建日期 2006-3-3
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

package icbc.cmis.flow.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.operation.*;
import java.util.*;
import icbc.cmis.base.TranFailException;

/**
 * Title: Description: 查询下一环节列表,下一处理地区列表 Copyright: Copyright (c) 2005
 * Company:icbcsdc
 * 
 * @author：郑期彬
 * @version 1.0
 */
public class util_content_choosenextDao extends CmisDao {

	public util_content_choosenextDao(CmisOperation op) {
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
	 * 取得环节列表
	 * 
	 * @param flowtype
	 * @param busi_type
	 * @param lang_code
	 * @return
	 * @throws TranFailException
	 */
	public ArrayList getflowlist(String flowtype, String busi_type,
			String lang_code) throws TranFailException {
		ArrayList alist = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			getConnection("cmis3");
			sql = " select role_code,role_name"
					+ " from imag_role_major_new "
					+ " where lang_code = ? "
					+ "and  role_type= (select kind_type from imag_kind where kind_code=? and lang_code='"+lang_code+"') "
					+ "and role_flag2='1' order by role_code ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, lang_code);
			pstmt.setString(2, flowtype);
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
					;
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, areacode);
			pstmt.setString(2, areacode);
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
}