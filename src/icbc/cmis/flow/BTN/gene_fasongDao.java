package icbc.cmis.flow.BTN;

import java.sql.*;
import java.util.*;
import oracle.jdbc.*;
import icbc.cmis.service.CmisDao;
import icbc.missign.Employee;
import icbc.cmis.base.*;

public class gene_fasongDao extends CmisDao {
	public gene_fasongDao(icbc.cmis.operation.CmisOperation op) {
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
			sql = "select INTERFACE003 from mag_approve_interface where TRADECODE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String)hmapinfo.get("approve_tradetype"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sProname = killnull(rs.getString("INTERFACE003"));
			} else {
				hresult.put("flag", "-1");
				hresult.put("msg", "未定义发送时业务校验接口");
				conn.rollback();
				return hresult;
			}
			if (!sProname.equals("")) {
				stmt_call = conn.prepareCall("{call " + sProname + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				stmt_call.setString(1, (String)hmapinfo.get("approve_entcode")); //客户号
				stmt_call.setString(2, (String)hmapinfo.get("approve_tradecode")); //申请号
				stmt_call.setString(3, (String)hmapinfo.get("approve_flowtype")); //流程种类
				stmt_call.setString(4, (String)hmapinfo.get("approve_tradetype")); //申请种类
				stmt_call.setString(5, (String)hmapinfo.get("approve_ordernum")); //序号
				stmt_call.setString(6, (String)hmapinfo.get("approve_ordercode")); //环节代码
				stmt_call.setString(7, (String)hmapinfo.get("empareacode")); //当前地区
				stmt_call.setString(8, (String)hmapinfo.get("employeecode")); //当前柜员
				stmt_call.setString(9, (String)hmapinfo.get("nextflow")); //下一环节
				stmt_call.setString(10, (String)hmapinfo.get("nextareacode")); //下一地区
				stmt_call.setString(11, (String)hmapinfo.get("nextemployeecode")); //下一柜员
				stmt_call.setString(12, (String)hmapinfo.get("approve_busitype")); //业务大类
				stmt_call.registerOutParameter(13, OracleTypes.VARCHAR); //返回标志,0正确，－1失败
				stmt_call.registerOutParameter(14, OracleTypes.VARCHAR); //返回信息
				stmt_call.execute();
				str_flag = stmt_call.getString(13);
				str_msg = stmt_call.getString(14);
				if (!str_flag.equals("0")) {
					hresult.put("flag", "-1");
					hresult.put("msg", str_msg);
					conn.rollback();
					return hresult;
				}
			} else {
				hresult.put("flag", "-1");
				hresult.put("msg", "未定义发送时业务校验接口");
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

	public HashMap dofasong(HashMap hmapinfo) throws TranFailException, SQLException {
		HashMap hresult = new HashMap();
		String sql = "";
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.CallableStatement stmt_call = null;
		try {
			this.getConnection("cmis3");

			//调用公用发送流程存储过程
			stmt_call = conn.prepareCall("{call pack_approve_gene.gene_fasong(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			stmt_call.setString(1, (String)hmapinfo.get("approve_entcode"));
			stmt_call.setString(2, (String)hmapinfo.get("approve_tradecode"));
			stmt_call.setString(3, (String)hmapinfo.get("approve_flowtype"));
			stmt_call.setString(4, (String)hmapinfo.get("approve_tradetype"));
			stmt_call.setString(5, (String)hmapinfo.get("approve_ordernum"));
			stmt_call.setString(6, (String)hmapinfo.get("approve_ordercode"));
			stmt_call.setString(7, (String)hmapinfo.get("nextflow"));
			stmt_call.setString(8, (String)hmapinfo.get("nextflowname"));
			stmt_call.setString(9, (String)hmapinfo.get("empareacode"));
			stmt_call.setString(10, (String)hmapinfo.get("nextareacode"));
			stmt_call.setString(11, (String)hmapinfo.get("employeecode"));
			stmt_call.setString(12, (String)hmapinfo.get("nextemployeecode"));
			stmt_call.setString(13, (String)hmapinfo.get("nextemployeename"));
			stmt_call.setString(14, "0");
			stmt_call.registerOutParameter(15, OracleTypes.VARCHAR); //返回标志,0正确，－1失败
			stmt_call.registerOutParameter(16, OracleTypes.VARCHAR); //返回信息
			stmt_call.execute();
			String str_flag = stmt_call.getString(15);
			String str_msg = stmt_call.getString(16);
			if (!str_flag.equals("0")) {
				hresult.put("flag", "-1");
				hresult.put("msg", str_msg);
				conn.rollback();
				return hresult;
			}

			//调用业务变化存储过程
			String sProname = "";
			sql = "select INTERFACE004 from mag_approve_interface where TRADECODE = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String)hmapinfo.get("approve_tradetype"));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sProname = killnull(rs.getString("INTERFACE004"));
			} else {
				hresult.put("flag", "-1");
				hresult.put("msg", "未定义发送时业务变化接口");
				conn.rollback();
				return hresult;
			}
			if (!sProname.equals("")) {
				stmt_call = conn.prepareCall("{call " + sProname + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				stmt_call.setString(1, (String)hmapinfo.get("approve_entcode")); //客户号
				stmt_call.setString(2, (String)hmapinfo.get("approve_tradecode")); //申请号
				stmt_call.setString(3, (String)hmapinfo.get("approve_flowtype")); //流程种类
				stmt_call.setString(4, (String)hmapinfo.get("approve_tradetype")); //申请种类
				stmt_call.setString(5, (String)hmapinfo.get("approve_ordernum")); //序号
				stmt_call.setString(6, (String)hmapinfo.get("approve_ordercode")); //环节代码
				stmt_call.setString(7, (String)hmapinfo.get("empareacode")); //当前地区
				stmt_call.setString(8, (String)hmapinfo.get("employeecode")); //当前柜员
				stmt_call.setString(9, (String)hmapinfo.get("nextflow")); //下一环节
				stmt_call.setString(10, (String)hmapinfo.get("nextareacode")); //下一地区
				stmt_call.setString(11, (String)hmapinfo.get("nextemployeecode")); //下一柜员
				stmt_call.setString(12, (String)hmapinfo.get("approve_busitype")); //业务大类
				stmt_call.registerOutParameter(13, OracleTypes.VARCHAR); //返回标志,0正确，－1失败
				stmt_call.registerOutParameter(14, OracleTypes.VARCHAR); //返回信息
				stmt_call.execute();
				String str_flag2 = stmt_call.getString(13);
				String str_msg2 = stmt_call.getString(14);
				if (!str_flag2.equals("0")) {
					hresult.put("flag", "-1");
					hresult.put("msg", str_msg2);
					conn.rollback();
					return hresult;
				}
			} else {
				hresult.put("flag", "-1");
				hresult.put("msg", "未定义发送时业务校验接口");
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