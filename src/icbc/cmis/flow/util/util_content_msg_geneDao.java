package icbc.cmis.flow.util;

import java.sql.*;
import java.util.*;
import oracle.jdbc.*;
import icbc.cmis.service.CmisDao;
import icbc.missign.Employee;
import icbc.cmis.base.*;

public class util_content_msg_geneDao extends CmisDao {
	public util_content_msg_geneDao(icbc.cmis.operation.CmisOperation op) {
		super(op);

	}

	private String killnull(String strin) {
		if (strin == null) {
			return "";
		} else {
			return strin;
		}
	}

	public HashMap savemsg(
		String msg_g_entcode,
		String msg_g_tradecode,
		String msg_g_flowtype,
		String msg_g_tradetype,
		String msg_g_ordernum,
		String msg_g_ordercode,
		String msg_g_empareacode,
		String msg_g_employeecode,
		String msg_g_msginput)
		throws TranFailException, SQLException {
		HashMap hmapresult = new HashMap();
		String sql = "";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.CallableStatement stmt_call = null;
		try {
			getConnection("cmis3");
			stmt_call = conn.prepareCall("{call pack_approve_msg.update_msg(?,?,?,?,?,?,?,?,?,?,?)}");
			stmt_call.setString(1, msg_g_entcode);
			stmt_call.setString(2, msg_g_tradecode);
			stmt_call.setString(3, msg_g_flowtype);
			stmt_call.setString(4, msg_g_tradetype);
			stmt_call.setString(5, msg_g_ordernum);
			stmt_call.setString(6, msg_g_ordercode);
			stmt_call.setString(7, msg_g_empareacode);
			stmt_call.setString(8, msg_g_employeecode);
			stmt_call.setString(9, msg_g_msginput);
			stmt_call.registerOutParameter(10, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(11, OracleTypes.VARCHAR);
			stmt_call.execute();
			String sresult = stmt_call.getString(10);
			String sreinfo = stmt_call.getString(11);
			if (!sresult.equals("0")) {
				//出错返回
				conn.rollback();
				hmapresult.put("result", "-1");
				hmapresult.put("reinfo", sreinfo);
				return hmapresult;
			}

			//置申请资料状态
			String sProcedureName = "";
			sql = "select INTERFACE002 from mag_approve_interface where TRADECODE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msg_g_tradetype);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sProcedureName = killnull(rs.getString("INTERFACE002"));
			}

			if (sProcedureName.equals("")) {
				//不能调用
				conn.rollback();
				hmapresult.put("result", "-1");
				hmapresult.put("reinfo", genMsg.getErrMsg("099993"));//"未定义置申请书状态过程");
				return hmapresult;
			}

			stmt_call = conn.prepareCall("{call " + sProcedureName + "(?,?,?,?,?,?)}");
			stmt_call.setString(1, msg_g_entcode);
			stmt_call.setString(2, msg_g_tradecode);
			stmt_call.setString(3, "3");
			stmt_call.setString(4, msg_g_tradetype);
			stmt_call.registerOutParameter(5, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(6, OracleTypes.VARCHAR);
			stmt_call.execute();
			sresult = stmt_call.getString(5);
			sreinfo = stmt_call.getString(6);
			if (!sresult.equals("0")) {
				//出错返回
				conn.rollback();
				hmapresult.put("result", "-1");
				hmapresult.put("reinfo", sreinfo);
				return hmapresult;
			}

			//成功
			conn.commit();
			hmapresult.put("result", "0");
			hmapresult.put("reinfo", "ok");

		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			conn.rollback();
			hmapresult.put("result", "-1");
			hmapresult.put("reinfo", e.getMessage());
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
				} catch (Exception ex) {
				}
			closeConnection();
		}
		return hmapresult;
	}

}