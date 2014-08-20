/*
 * 创建日期 2006-3-7
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.flow.util;

import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.*;
import icbc.cmis.service.CmisDao;
import icbc.missign.Employee;
import icbc.cmis.base.*;

/**
 * Title: Description: 查询辅助审核内容 Copyright: Copyright (c) 2005 Company:icbcsdc
 * 
 * @author：郑期彬
 * @version 1.0
 */
public class util_content_assistantDao extends CmisDao {

	public util_content_assistantDao(icbc.cmis.operation.CmisOperation op) {
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
	 * 查询辅助审核内容
	 * 
	 * @param entcode
	 * @param tradecode
	 * @param ordernum
	 * @return
	 * @throws TranFailException
	 */
	public ArrayList getassistant(String entcode, String tradecode,
			String ordernum) throws TranFailException {
		ArrayList vret = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			getConnection("cmis3");
			sql = "select rownum rnum,advice005,(select employee_name from mag_employee where employee_code = advice005) advice005_name,advice006,advice007,advice008 "
					+ "from madvice_ass "
					+ "where advice001=? and advice002=? and advice003=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, entcode);
			pstmt.setString(2, tradecode);
			pstmt.setString(3, ordernum);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				HashMap hmap = new HashMap();
				hmap.put("rnum", killnull(rs.getString("rnum")));
				hmap.put("advice005", killnull(rs.getString("advice005")));
				hmap.put("advice005_name", killnull(rs
						.getString("advice005_name")));
				hmap.put("advice006", killnull(rs.getString("advice006")));
				hmap.put("advice007", killnull(rs.getString("advice007")));
				hmap.put("advice008", killnull(rs.getString("advice008")));
				vret.add(hmap);
			}

		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_assistantDao.getassistant()", e.getMessage(),
					e.getMessage());
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

	/**
	 * 取得辅助审批说明
	 * 
	 * @param entcode
	 * @param tradecode
	 * @param ordernum
	 * @return
	 * @throws TranFailException
	 */
	public String getmyassinfo(String entcode, String tradecode, String ordernum)
			throws TranFailException {
		String strout = "";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		try {
			getConnection("cmis3");
			sql = "select process020 from mprocess_new where process001=? and process002=? and process005=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, entcode);
			pstmt.setString(2, tradecode);
			pstmt.setString(3, ordernum);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				strout = killnull(rs.getString("process020"));
			}

		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("icbc.cmis.flow.util",
					"util_content_assistantDao.getmyassinfo()", e.getMessage(),
					e.getMessage());
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
		return strout;
	}

	/**
	 * 保存辅助审批信息
	 * 
	 * @param entcode
	 * @param tradecode
	 * @param tradetype
	 * @param flowtype
	 * @param ordernum
	 * @param ordercode
	 * @param empareacode
	 * @param employeecode
	 * @param assinfo
	 * @param asslist
	 * @throws TranFailException
	 * @throws SQLException
	 */
	public HashMap savemyassinfo(String entcode, String tradecode,
			String tradetype, String flowtype, String ordernum,
			String ordercode, String empareacode, String employeecode,
			String assinfo, String asslist) throws TranFailException,
			SQLException {
		HashMap hmap = new HashMap();
		CallableStatement stmt_call = null;
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;
		String result = "";
		String reinfo = "";
		String result2 = "";
		String reinfo2 = "";
		try {
			getConnection("cmis3");

			stmt_call = conn
					.prepareCall("{call pack_approvedeal.checkstat(?,?,?,?,?,?)}");
			stmt_call.setString(1, entcode);
			stmt_call.setString(2, tradecode);
			stmt_call.setString(3, ordernum);
			stmt_call.setString(4, employeecode);
			stmt_call.registerOutParameter(5, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(6, OracleTypes.VARCHAR);
			stmt_call.execute();
			result = stmt_call.getString(5);
			reinfo = stmt_call.getString(6);

			if (result.equals("2") || result.equals("0")) {
				//写发送
				StringTokenizer token = new StringTokenizer(asslist, "|");
				HashMap hlist = new HashMap();
				int i = 0;
				while (token.hasMoreTokens()) {
					String temp = token.nextToken();
					hlist.put(i + "", temp);
					i++;
				}
				for (i = 0; i < hlist.size(); i++) {
					int icount = 0;
					sql = "select count(*) "
							+ "from madvice_ass "
							+ "where advice001=? and advice002=? and advice003=? and advice004=? and advice005=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, entcode);
					pstmt.setString(2, tradecode);
					pstmt.setString(3, ordernum);
					pstmt.setString(4, empareacode);
					pstmt.setString(5, (String) hlist.get(i + ""));
					rs = pstmt.executeQuery();
					if (rs.next()) {
						icount = rs.getInt(1);
					}
					if (icount == 0) {
						sql = "insert into madvice_ass(advice001,advice002,advice003,advice004,advice005,advice006) "
								+ "values(?,?,?,?,?,?)";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, entcode);
						pstmt.setString(2, tradecode);
						pstmt.setString(3, ordernum);
						pstmt.setString(4, empareacode);
						pstmt.setString(5, (String) hlist.get(i + ""));
						pstmt.setString(6, "0");
						pstmt.executeUpdate();
					}
				}
			}
			if (result.equals("2")) {
				//插入记录
				sql = "insert into mprocess_new(process001,process002,process003,process004,process005,process006,process007,process008,process009,process010,process020) "
						+ "values(?,?,?,?,?,?,?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, entcode);
				pstmt.setString(2, tradecode);
				pstmt.setString(3, flowtype);
				pstmt.setString(4, tradetype);
				pstmt.setString(5, ordernum);
				pstmt.setString(6, ordercode);
				pstmt.setString(7, empareacode);
				pstmt.setString(8, employeecode);
				pstmt.setString(9, "1");
				pstmt.setString(10, "1");
				pstmt.setString(11, assinfo);
				pstmt.executeUpdate();

				//置申请书状态
				stmt_call = conn
						.prepareCall("{call pack_flow_parameter.proc_update_state(?,?,?,?,?,?)}");
				stmt_call.setString(1, entcode);
				stmt_call.setString(2, tradecode);
				stmt_call.setString(3, "3");
				stmt_call.setString(4, tradetype);
				stmt_call.registerOutParameter(5, OracleTypes.VARCHAR);
				stmt_call.registerOutParameter(6, OracleTypes.VARCHAR);
				stmt_call.execute();
				result2 = stmt_call.getString(5);
				reinfo2 = stmt_call.getString(6);
				if (!result2.equals("0")) {
					hmap.put("result", "-1");
					hmap.put("reinfo", reinfo);
					return hmap;
				}
			} else if (result.equals("0")) {
				sql = "update mprocess_new set process008=?,process009=?,process010=?,process020=? "
						+ "where process001=? and process002=? and process005=? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, employeecode);
				pstmt.setString(2, "1");
				pstmt.setString(3, "1");
				pstmt.setString(4, assinfo);
				pstmt.setString(5, entcode);
				pstmt.setString(6, tradecode);
				pstmt.setString(7, ordernum);
				pstmt.executeUpdate();
			} else {
				hmap.put("result", "-1");
				hmap.put("reinfo", reinfo);
				return hmap;
			}
			hmap.put("result", "0");
			hmap.put("reinfo", "");
			conn.commit();
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			conn.rollback();
			hmap.put("result", "-1");
			hmap.put("reinfo", e.getMessage());
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
			if (stmt_call != null)
				try {
					stmt_call.close();
				} catch (Exception ee) {
				}
			closeConnection();
		}
		return hmap;
	}

}