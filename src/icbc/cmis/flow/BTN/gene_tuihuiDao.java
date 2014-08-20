package icbc.cmis.flow.BTN;

import java.sql.*;
import java.util.*;
import oracle.jdbc.*;
import icbc.cmis.service.CmisDao;
import icbc.missign.Employee;
import icbc.cmis.base.*;

public class gene_tuihuiDao extends CmisDao {
	public gene_tuihuiDao(icbc.cmis.operation.CmisOperation op) {
		super(op);
	}

	private String killnull(String strin) {
		if (strin == null) {
			return "";
		} else {
			return strin;
		}
	}

	public HashMap directcheck(HashMap hmapinfo) throws TranFailException, SQLException {
		HashMap hresult = new HashMap();
		String sql = "";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.CallableStatement stmt_call = null;
		try {
			this.getConnection("cmis3");
			String str_flag = "";
			String str_msg = "";

			//调用业务变化存储过程
			String sProname = "";
			sql = "select INTERFACE005 from mag_approve_interface where TRADECODE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String)hmapinfo.get("approve_tradetype"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sProname = killnull(rs.getString("INTERFACE005"));
			} else {
				hresult.put("flag", "-1");
				hresult.put("msg", "未定义退回时业务校验接口");
				conn.rollback();
				return hresult;
			}
			if (!sProname.equals("")) {
				stmt_call = conn.prepareCall("{call " + sProname + "(?,?,?,?,?,?,?,?,?,?,?)}");
				stmt_call.setString(1, (String)hmapinfo.get("approve_entcode")); //客户号
				stmt_call.setString(2, (String)hmapinfo.get("approve_tradecode")); //申请号
				stmt_call.setString(3, (String)hmapinfo.get("approve_flowtype")); //流程种类
				stmt_call.setString(4, (String)hmapinfo.get("approve_tradetype")); //申请种类
				stmt_call.setString(5, (String)hmapinfo.get("approve_ordernum")); //序号
				stmt_call.setString(6, (String)hmapinfo.get("approve_ordercode")); //环节代码
				stmt_call.setString(7, (String)hmapinfo.get("empareacode")); //当前地区
				stmt_call.setString(8, (String)hmapinfo.get("employeecode")); //当前柜员
				stmt_call.setString(9, (String)hmapinfo.get("approve_busitype")); //业务大类
				stmt_call.registerOutParameter(10, OracleTypes.VARCHAR); //返回标志,0正确，－1失败
				stmt_call.registerOutParameter(11, OracleTypes.VARCHAR); //返回信息
				stmt_call.execute();
				str_flag = stmt_call.getString(10);
				str_msg = stmt_call.getString(11);
				if (!str_flag.equals("0")) {
					hresult.put("flag", "-1");
					hresult.put("msg", str_msg);
					conn.rollback();
					return hresult;
				}
			} else {
				hresult.put("flag", "-1");
				hresult.put("msg", "未定义退回时业务校验接口");
				conn.rollback();
				return hresult;
			}

			hresult.put("flag", "0");
			hresult.put("msg", str_msg);
			conn.commit();
		} catch (TranFailException e) {
			conn.rollback();
			throw e;
		} catch (Exception e) {
			hresult.put("flag", "-1");
			hresult.put("msg", e.getMessage());
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
		return hresult;
	}

	public HashMap dotuihui(HashMap hmapinfo, String approve_backnum) throws TranFailException, SQLException {
		HashMap hresult = new HashMap();
		String sql = "";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.CallableStatement stmt_call = null;
		try {
			this.getConnection("cmis3");

			//调用公用退回流程存储过程
			stmt_call = conn.prepareCall("{call pack_approve_gene.gene_tuihui(?,?,?,?,?,?,?,?,?,?,?,?)}");
			stmt_call.setString(1, (String)hmapinfo.get("approve_entcode"));
			stmt_call.setString(2, (String)hmapinfo.get("approve_tradecode"));
			stmt_call.setString(3, (String)hmapinfo.get("approve_flowtype"));
			stmt_call.setString(4, (String)hmapinfo.get("approve_tradetype"));
			stmt_call.setString(5, (String)hmapinfo.get("approve_ordernum"));
			stmt_call.setString(6, (String)hmapinfo.get("approve_ordercode"));
			stmt_call.setString(7, (String)hmapinfo.get("empareacode"));
			stmt_call.setString(8, (String)hmapinfo.get("employeecode"));
			stmt_call.setString(9, approve_backnum);
			stmt_call.registerOutParameter(10, OracleTypes.VARCHAR); //返回标志,0正确，－1失败
			stmt_call.registerOutParameter(11, OracleTypes.VARCHAR); //是否返回到了第一人的标志
			stmt_call.registerOutParameter(12, OracleTypes.VARCHAR); //返回信息
			stmt_call.execute();
			String str_flag = stmt_call.getString(10);
			String sFlagFirst = stmt_call.getString(11);
			String str_msg = stmt_call.getString(12);
			if (!str_flag.equals("0")) {
				hresult.put("flag", "-1");
				hresult.put("msg", str_msg);
				conn.rollback();
				return hresult;
			}

			//调用业务变化存储过程
			String sProname = "";
			sql = "select INTERFACE006 from mag_approve_interface where TRADECODE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String)hmapinfo.get("approve_tradetype"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sProname = killnull(rs.getString("INTERFACE006"));
			} else {
				hresult.put("flag", "-1");
				hresult.put("msg", "未定义退回时业务变化接口");
				conn.rollback();
				return hresult;
			}
			if (!sProname.equals("")) {
				stmt_call = conn.prepareCall("{call " + sProname + "(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				stmt_call.setString(1, (String)hmapinfo.get("approve_entcode")); //客户号
				stmt_call.setString(2, (String)hmapinfo.get("approve_tradecode")); //申请号
				stmt_call.setString(3, (String)hmapinfo.get("approve_flowtype")); //流程种类
				stmt_call.setString(4, (String)hmapinfo.get("approve_tradetype")); //申请种类
				stmt_call.setString(5, (String)hmapinfo.get("approve_ordernum")); //序号
				stmt_call.setString(6, (String)hmapinfo.get("approve_ordercode")); //环节代码
				stmt_call.setString(7, (String)hmapinfo.get("empareacode")); //当前地区
				stmt_call.setString(8, (String)hmapinfo.get("employeecode")); //当前柜员
				stmt_call.setString(9, (String)hmapinfo.get("approve_busitype")); //业务大类
				stmt_call.setString(10, approve_backnum); //
				stmt_call.setString(11, sFlagFirst); //
				stmt_call.registerOutParameter(12, OracleTypes.VARCHAR); //返回标志,0正确，－1失败
				stmt_call.registerOutParameter(13, OracleTypes.VARCHAR); //返回信息
				stmt_call.execute();
				String str_flag2 = stmt_call.getString(12);
				String str_msg2 = stmt_call.getString(13);
				if (!str_flag2.equals("0")) {
					hresult.put("flag", "-1");
					hresult.put("msg", str_msg2);
					conn.rollback();
					return hresult;
				}
			} else {
				hresult.put("flag", "-1");
				hresult.put("msg", "未定义退回时业务校验接口");
				conn.rollback();
				return hresult;
			}

			hresult.put("flag", "0");
			hresult.put("msg", str_msg);
			conn.commit();
		} catch (TranFailException e) {
			conn.rollback();
			throw e;
		} catch (Exception e) {
			hresult.put("flag", "-1");
			hresult.put("msg", e.getMessage());
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
		return hresult;
	}

}