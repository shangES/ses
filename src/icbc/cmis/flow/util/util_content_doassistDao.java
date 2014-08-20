package icbc.cmis.flow.util;

import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.*;
import icbc.cmis.service.CmisDao;
import icbc.missign.Employee;
import icbc.cmis.base.*;

public class util_content_doassistDao extends CmisDao {
	String langCode="zh_CN";
	icbc.cmis.tags.PropertyResourceReader propertyResourceReader = null;
	public util_content_doassistDao(icbc.cmis.operation.CmisOperation op) {
		super(op);
		try
		{
			langCode=(String)op.getSessionData("LangCode");
		propertyResourceReader=new icbc.cmis.tags.PropertyResourceReader(langCode,"icbc.cmis.flow.FLOW_UTILE");
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
	 * 查询辅助审批信息
	 * 
	 * @param entcode
	 * @param tradecode
	 * @param ordernum
	 * @param employeecode
	 * @param empareacode
	 * @return
	 * @throws TranFailException
	 */
	public HashMap queryass(String entcode, String tradecode, String ordernum,
			String employeecode, String empareacode) throws TranFailException {
		HashMap hmap = new HashMap();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			getConnection("cmis3");
			sql = "select process007, (select area_name from mag_area where area_code = process007) process007_name, "
					+ "process008,(select employee_name from mag_employee where employee_code = process008) process008_name,"
					+ "process020 "
					+ "from mprocess_new "
					+ "where process001 = ? "
					+ "and process002 = ? "
					+ "and process005 = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, entcode);
			pstmt.setString(2, tradecode);
			pstmt.setString(3, ordernum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				hmap.put("process007", killnull(rs.getString("process007")));
				hmap.put("process007_name", killnull(rs
						.getString("process007_name")));
				hmap.put("process008", killnull(rs.getString("process008")));
				hmap.put("process008_name", killnull(rs
						.getString("process008_name")));
				hmap.put("process020", killnull(rs.getString("process020")));
			}

			sql = "select advice006,advice008 from madvice_ass "
					+ "where advice001 = ? " + "and advice002 = ? "
					+ "and advice003 = ? " + "and advice004 = ? "
					+ "and advice005 = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, entcode);
			pstmt.setString(2, tradecode);
			pstmt.setString(3, ordernum);
			pstmt.setString(4, empareacode);
			pstmt.setString(5, employeecode);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				hmap.put("advice006", killnull(rs.getString("advice006")));
				hmap.put("advice008", killnull(rs.getString("advice008")));
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_flowhistoryDao.queryhistory()", e.getMessage(), e
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
		return hmap;
	}

	/**
	 * 保存辅助审批意见
	 * 
	 * @param entcode
	 * @param tradecode
	 * @param ordernum
	 * @param employeecode
	 * @param empareacode
	 * @param advice006
	 * @param advice008
	 * @param sysdate
	 * @throws TranFailException
	 * @throws SQLException
	 */
	public HashMap saveass(String entcode, String tradecode, String ordernum,
			String employeecode, String empareacode, String advice006,
			String advice008, String sysdate) throws TranFailException,
			SQLException {
		HashMap hmap = new HashMap();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			getConnection("cmis3");
			int icount = 0;
			sql = "select count(*) from madvice_ass where advice001 = ? and advice002 = ? and advice003 = ? and advice004 = ? and advice005 = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, entcode);
			pstmt.setString(2, tradecode);
			pstmt.setString(3, ordernum);
			pstmt.setString(4, empareacode);
			pstmt.setString(5, employeecode);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				icount = rs.getInt(1);
			}

			if (icount > 0) {
				sql = "update madvice_ass set advice006=?, advice007=?, advice008=? where advice001 = ? and advice002 = ? and advice003 = ? and advice004 = ? and advice005 = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, advice006);
				pstmt.setString(2, sysdate);
				pstmt.setString(3, advice008);
				pstmt.setString(4, entcode);
				pstmt.setString(5, tradecode);
				pstmt.setString(6, ordernum);
				pstmt.setString(7, empareacode);
				pstmt.setString(8, employeecode);
				pstmt.executeUpdate();
				hmap.put("result", "0");
				hmap.put("info", propertyResourceReader.getPrivateStr("C000064"));//保存完成
				conn.commit();
			} else {
				hmap.put("result", "1");
				hmap.put("info", propertyResourceReader.getPrivateStr("C000065"));//该笔辅助审批已被撤销，您无法完成操作				
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			//hmap.put("result", "1");
			//hmap.put("info", "错误："+e.getMessage());
			throw new TranFailException("icbc.cmis.flow.util",
					"util_flowhistoryDao.saveass()", e.getMessage(), e
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
		return hmap;
	}

}