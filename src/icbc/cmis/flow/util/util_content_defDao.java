package icbc.cmis.flow.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.operation.*;
import java.util.*;
import java.sql.*;
import icbc.cmis.base.TranFailException;

/**
 * Title: Description: 流程定义 Copyright: Copyright (c) 2005 Company:icbcsdc
 * 
 * @author：郑期彬
 * @version 1.0
 */
public class util_content_defDao extends CmisDao {

	public util_content_defDao(CmisOperation op) {
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
	 * 查询流程已定义，缺少环节
	 * 
	 * @param entcode
	 *            客户号
	 * @param tradecode
	 *            申请号
	 * @param flowtype
	 *            流程种类
	 * @param empareacode
	 *            地区号
	 * @param busitype
	 *            业务性质
	 * @return
	 * @throws TranFailException
	 */
	public ArrayList getflowdefconten(String entcode, String tradecode,
			String flowtype, String empareacode, String busitype)
			throws TranFailException {
		ArrayList vret = new ArrayList();
		java.sql.ResultSet rsdefconten = null; //已定义的流程结果集
		java.sql.ResultSet rsnotdefconten = null; //未定义的流程结果集
		java.sql.PreparedStatement pstmt = null;
		Vector isdef = new Vector();
		Vector isnotdef = new Vector();
		Vector resultDeflist = new Vector();
		String defconten = "";
		String notdefconten = "";
		String queryStrdefconten = "";
		String queryStrnotdefconten = "";
		String process007 = "";
		String process008 = "";
		String repeatflag = ""; //流程环节是否存在标志0不存在1存在
		String process006 = ""; //流程环节名称
		int processnum = 0; //未经过此次
		String step002 = ""; //流程环节代码
		String role_name = ""; //流程环节名称
		int step003 = 0; //经过此次
		int step004 = 0; //经过此次 用来和step003比较大小

		try {
			//已定义的流程
			getConnection("cmis3");
			String Sqlstate = "";
			if (busitype.equals("1")) { //委托贷款有其特别的地方
				Sqlstate = "{ CALL pack_approvewt.proc_ctrl_spflowwt(?,?,?,?,?,?,?,?)}";
			} else {
				Sqlstate = "{ CALL pack_flow_unit.proc_ctrl_spflow(?,?,?,?,?,?,?,?)}";
			}

			CallableStatement call = null;
			Hashtable ht;
			StringBuffer param = new StringBuffer(Sqlstate);

			call = conn.prepareCall(param.toString());

			String outType = "";
			call.setString(1, entcode);
			call.setString(2, tradecode);
			call.setString(3, flowtype);
			call.setString(4, empareacode);
			call.registerOutParameter(5, Types.VARCHAR);
			call.registerOutParameter(6, Types.VARCHAR);
			call.registerOutParameter(7, Types.VARCHAR);
			call.registerOutParameter(8, Types.VARCHAR);

			call.execute();
			Vector result = new Vector();
			ResultSet rset = null;

			defconten = call.getString(7);
			notdefconten = call.getString(8);
			if (defconten == null || defconten.equals("null")) {
				defconten = "";
			}
			if (notdefconten == null || notdefconten.equals("null")) {
				notdefconten = "";
			}
			HashMap hmap = new HashMap();
			hmap.put("defconten", defconten);
			hmap.put("notdefconten", notdefconten);
			vret.add(hmap);
			return vret;
		} catch (TranFailException te) {
			throw te;
		} catch (Exception e) {
			throw new TranFailException("util_content_defDao",
					"util_content_defDao.getflowdefconten", e.getMessage());
		} finally {
			if (rsdefconten != null)
				try {
					rsdefconten.close();
				} catch (Exception ex) {
				}
			if (rsnotdefconten != null)
				try {
					rsnotdefconten.close();
				} catch (Exception ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (Exception ex) {
				}
			closeConnection();
		}

	}

}