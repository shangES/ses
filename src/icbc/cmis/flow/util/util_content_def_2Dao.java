package icbc.cmis.flow.util;

import oracle.jdbc.driver.*;
import icbc.cmis.service.CmisDao;
import icbc.cmis.operation.*;

import java.util.*;
import icbc.cmis.base.TranFailException;

/**
 * 支持低风险业务流程种类的业务定义判断
 * 
 * @author zjfh-zhangyz
 * 
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

public class util_content_def_2Dao extends CmisDao {

	public util_content_def_2Dao(CmisOperation op) {
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
	 * 查询流程定义，支持低风险判断，暂时自营类使用
	 * 
	 * @param entcode
	 *            客户号
	 * @param tradecode
	 *            申请号
	 * @param flowtype
	 *            流程种类
	 * @param empareacode
	 *            柜员地区
	 * @param tradetype
	 *            申请种类
	 * @param LangCode
	 *            语言标志
	 * @return
	 * @throws TranFailException
	 */
	public HashMap getflowdefconten(String entcode, String tradecode,
			String flowtype, String empareacode, String tradetype,
			String langcode) throws TranFailException {
		HashMap hmap = new HashMap();
		java.sql.ResultSet rs = null;
		java.sql.CallableStatement stmt_call = null;

		String defconten = "";
		String notdefconten = "";

		try {
			//已定义的流程
			getConnection("cmis3");
			String sInfo = "";
			String Sqlstate = "";
			stmt_call = conn
					.prepareCall("{ call pack_controlparameter.proc_ywcode_2(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			stmt_call.setString(1, entcode);
			stmt_call.setString(2, tradecode);
			stmt_call.setString(3, "1");
			stmt_call.setString(4, tradetype);
			stmt_call.registerOutParameter(5, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(6, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(7, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(8, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(9, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(10, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(11, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(12, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(13, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(14, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(15, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(16, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(17, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(18, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(19, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(20, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(21, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(22, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(23, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(24, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(25, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(26, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(27, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(28, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(29, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(30, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(31, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(32, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(33, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(34, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(35, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(36, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(37, OracleTypes.VARCHAR);
			stmt_call.execute();
			String slowrisk = killnull(stmt_call.getString(5)); //低风险标志，0为低风险
			String sNewFlowtype = killnull(stmt_call.getString(28));
			sNewFlowtype = "28";
			if (slowrisk.equals("0")) {
				sInfo = "0";//低风险
			} else {
				sNewFlowtype = flowtype;
				sInfo = "1";//非低风险
			}

			stmt_call = conn
					.prepareCall("{ call pack_approvepub.proc_ctrl_spflow(?,?,?,?,?,?,?,?,?)}");
			stmt_call.setString(1, entcode);
			stmt_call.setString(2, tradecode);
			stmt_call.setString(3, sNewFlowtype);
			stmt_call.setString(4, empareacode);
			stmt_call.setString(5, langcode);
			stmt_call.registerOutParameter(6, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(7, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(8, OracleTypes.VARCHAR);
			stmt_call.registerOutParameter(9, OracleTypes.VARCHAR);
			stmt_call.execute();
			defconten = killnull(stmt_call.getString(8));
			notdefconten = killnull(stmt_call.getString(9));
			hmap.put("lowriskinfo", sInfo);
			hmap.put("defconten", defconten);
			hmap.put("notdefconten", notdefconten);
			return hmap;
		} catch (TranFailException te) {
			throw te;
		} catch (Exception e) {
			throw new TranFailException("util_content_def_2Dao",
					"util_content_def2_Dao.getflowdefconten", e.getMessage());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ex) {
				}
			if (stmt_call != null)
				try {
					stmt_call.close();
				} catch (Exception ex) {
				}
			closeConnection();
		}
	}
}