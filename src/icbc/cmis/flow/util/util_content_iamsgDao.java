/*
 * 创建日期 2006-3-6
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
import icbc.cmis.util.DBTools;
import java.sql.PreparedStatement;

/**
 * Title: Description: 查询机评意见 Copyright: Copyright (c) 2005 Company:icbcsdc
 * 
 * @author：郑期彬
 * @version 1.0
 */
public class util_content_iamsgDao extends CmisDao {
	public util_content_iamsgDao(icbc.cmis.operation.CmisOperation op) {
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
	 * 功能描述: 查询本人意见说明
	 * 
	 * @param entcode
	 *            //客户代码
	 * @param tradecode
	 *            //业务申请号
	 * @param flowtype
	 *            //流程种类
	 * @return
	 * @throws 这个是只读机评，直接查询流程号为1的记录，现在表结构有变更，需要修改
	 * 
	 *  
	 */
	public ArrayList getiamsg(String entcode, String tradecode,
			String empareacode, String employeecode, String ordercode,
			String flowtype, String tradetype) throws TranFailException {
		ArrayList vret = new ArrayList();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String process017 = "";
		String process018 = "";
		String process019 = "";
		String process025 = "";
		String process026 = "";
		String s_out_flag = "";
		String s_out_mess = "";
		String o_ret1 = "";
		String o_ret2 = "";
		String queryStr = "";

		try {
			//		查询机评意见并且
			getConnection("cmis3");
			queryStr = "select process017,process018,process019,process025,process026 from mprocess_new where process001=? and process002=?  "
					+ " and process017 is not null order by process005 desc ";

			/*
			 * queryStr= "select
			 * process017,process018,process019,process025,process026 from
			 * mprocess_new where process001=? and process002=? " + " and
			 * process006 =? " + " and process007 =?" + " and process008 =?";
			 */

			pstmt = conn.prepareStatement(queryStr);
			pstmt.setString(1, entcode);
			pstmt.setString(2, tradecode);
			//pstmt.setString(3, entcode);
			//pstmt.setString(4, tradecode);
			//pstmt.setString(5, entcode);
			//pstmt.setString(6, tradecode);
			//pstmt.setString(7, flowtype);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				process017 = rs.getString("process017");
				process018 = rs.getString("process018");
				process019 = rs.getString("process019");
				process025 = rs.getString("process025");
				process026 = rs.getString("process026");

			} else {
				process017 = "";
				process018 = "";
				process019 = "";
				process025 = "";
				process026 = "";
			}

			//组合机评意见
			String Sqlstate = "{ CALL pack_explaincode.proc_analyze(?,?,?,?,?,?,?,?)}";
			/*
			 * //Vector inParam; Vector outParamName; inParam.add(process018);
			 * inParam.add(process019); inParam.add(process025);
			 * inParam.add(process026); OutParam.add("o_flag");
			 * OutParam.add("o_msg"); OutParam.add("o_ret1");
			 * OutParam.add("o_ret2");
			 */

			CallableStatement call = null;
			Hashtable ht;
			try {

				StringBuffer param = new StringBuffer(Sqlstate);

				call = conn.prepareCall(param.toString());

				String outType = "";
				call.setString(1, process018);
				call.setString(2, process019);
				call.setString(3, process025);
				call.setString(4, process026);
				call.registerOutParameter(5, Types.VARCHAR);
				call.registerOutParameter(6, Types.VARCHAR);
				call.registerOutParameter(7, OracleTypes.CURSOR);
				call.registerOutParameter(8, OracleTypes.CURSOR);

				call.execute();
				Vector result = new Vector();
				ResultSet rset = null;

				s_out_flag = call.getString(5);
				s_out_mess = call.getString(6);
				ResultSet rs1 = (ResultSet) call.getObject(7);
				ResultSet rs2 = (ResultSet) call.getObject(8);

				IndexedDataCollection aCursor = new IndexedDataCollection();
				int i = 1;
				while (rs1.next()) {
					KeyedDataCollection aRecord = new KeyedDataCollection();
					if (!(rs1.getString(1)).equals("null")) {
						o_ret1 = o_ret1 + i + "." + rs1.getString(1) + "\n";
						i = i + 1;
					}

				}
				IndexedDataCollection bCursor = new IndexedDataCollection();
				int j = 1;
				while (rs2.next()) {
					KeyedDataCollection aRecord = new KeyedDataCollection();
					if (!(rs2.getString(1)).equals("null")) {
						o_ret2 = o_ret2 + j + "." + rs2.getString(1) + "\n";
						j = j + 1;
					}
				}

				if (!s_out_flag.equals("0")) {
					HashMap hmap = new HashMap();
					hmap.put("process017", "");
					hmap.put("o_ret1", "机评意见解析错误");
					hmap.put("o_ret2", "");
					vret.add(hmap);
					return vret;
					//throw new TranFailException("util_content_iamsgDao",
					// "util_content_iamsgDao.getiamsg", s_out_mess);

				}
				HashMap hmap = new HashMap();
				hmap.put("process017", process017);
				hmap.put("o_ret1", o_ret1);
				hmap.put("o_ret2", o_ret2);
				vret.add(hmap);
				return vret;
			}

			catch (Exception e) {
				throw new TranFailException("util_content_iamsgDao",
						"util_content_iamsgDao.getiamsg", e.getMessage());
			}
		}

		catch (TranFailException te) {
			throw te;
		} catch (Exception e) {
			throw new TranFailException("util_content_iamsgDao",
					"util_content_iamsgDao.getiamsg", e.getMessage());
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

	}

}

