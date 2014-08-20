package icbc.cmis.util;

import icbc.cmis.base.*;
import icbc.cmis.service.CmisDao;
import java.sql.*;
import java.util.*;
import java.io.*;
import oracle.jdbc.driver.*;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TableAdjustDAO extends CmisDao {

	static Integer insertLock = new Integer(0);

	public TableAdjustDAO(icbc.cmis.operation.CmisOperation op) {
		super(op);
	}

	public Hashtable getTableOwner(String tableCode) throws TranFailException {
		//String ret = null;
		String sql = "select tab_owner,tab_type from mag_cache_tables where (tab_code=? or tab_code=?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Hashtable ret = new Hashtable();
		try {
			this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableCode.toUpperCase());
			pstmt.setString(2, tableCode.toLowerCase());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				ret.put("tab_owner", rs.getString(1));
				ret.put("tab_type", rs.getString(2));
			} else
				throw new TranFailException("getTableOwner", "icbc.cmis.util.TableAdjustDAO", "该表在初始化cache中不存在", "该表在初始化cache中不存在");
		} catch (Exception e) {
			throw new TranFailException("getTableOwner", "icbc.cmis.util.TableAdjustDAO", e.getMessage(), e.getMessage());
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
			this.closeConnection();
		}

		return ret;
	}

	/*
	 * 取得type_code类的列表
	 */
	public Vector performQueryTableList(String major, String bankFlag, String type_code) throws TranFailException {
		Vector ret = new Vector();
		String sql =
			"select a.adjust001, b.adjust002, a.adjust006, a.adjust007,b.adjust005,b.adjust007 "
				+ "from mag_adjust_tabs a,mag_adjust_tabs_def b "
				+ "where a.adjust005=? and a.adjust001=b.adjust001 and "
				+ "b.adjust003=? and (a.adjust004 is null or a.adjust004 = '' or "
				+ "a.adjust004 like '%' || ? || '%') order by a.adjust001";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			int bankIndex = Integer.parseInt(bankFlag);
			this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bankFlag);
			pstmt.setString(2, type_code);
			pstmt.setString(3, major);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String[] str = new String[6];
				str[0] = rs.getString(1);
				str[1] = rs.getString(2);
				str[2] = rs.getString(4);
				str[3] = rs.getString(3);
				str[4] = rs.getString(5);
				str[5] = rs.getString(6); //新增的事先校验
				ret.add(str);
			}
		} catch (Exception e) {
			throw new TranFailException("performQueryTableList", "icbc.cmis.util.TableAdjustDAO", e.getMessage(), e.getMessage());
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
			this.closeConnection();
		}

		return ret;
	}

	/*
	 * 取得字段类型
	 */
	private String parseType(String src) {
		if (src.startsWith("VARCHAR2") || src.startsWith("varchar2")) {
			return "str";
		} else if (src.startsWith("DATE") || src.startsWith("date"))
			return "date";
		else if (src.startsWith("NUMBER") || src.startsWith("number"))
			return "num";
		else if (src.startsWith("SELECTOR") || src.startsWith("selector"))
			return "sel";
		else
			return "unknown";
	}

	/*
	 * 取得字段长度
	 */
	private String parseLength(String src) {
		int startPos = src.indexOf("(");
		int endPos = src.indexOf(")");
		return src.substring(startPos + 1, endPos);
	}

	/*
	 * 拼写select段
	 */
	private String toSelectorTags(String keyvalue) {
		StringTokenizer token = new StringTokenizer(keyvalue, "|");
		StringBuffer buf = new StringBuffer();

		while (token.hasMoreTokens()) {
			String ts = token.nextToken();
			int pos = ts.indexOf(",");
			String value = ts.substring(0, pos);
			String key = ts.substring(pos + 1);

			buf.append("<option value='" + key + "'>" + value + "</option>\n");
		}

		return buf.toString();
	}

	/*
	 * 置入hashtable
	 */
	private Hashtable toSelectorHashtable(String keyvalue) {
		StringTokenizer token = new StringTokenizer(keyvalue, "|");
		StringBuffer buf = new StringBuffer();
		Hashtable ht = new Hashtable();
		while (token.hasMoreTokens()) {
			String ts = token.nextToken();
			int pos = ts.indexOf(",");
			String value = ts.substring(0, pos);
			String key = ts.substring(pos + 1);

			ht.put(key, value);
		}

		return ht;
	}

	public Vector performQueryHeadDetail(String tableCode) throws TranFailException {
		Vector ret = new Vector();
		String sql =
			"select adjust002,adjust003,adjust005,adjust006,adjust007,adjust008,adjust009,adjust010,adjust011 "
				+ "from mag_adjust_cols where adjust001 = ? "
				+ "order by adjust004";
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableCode);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Hashtable htable = new Hashtable();

				htable.put("col", rs.getString(1)); //列名
				htable.put("name", rs.getString(2)); //中文名

				String type = parseType(rs.getString(3));
				if (type.equals("unknown"))
					throw new TranFailException("performQueryHeadDetail", "icbc.cmis.util.TableAdjustDAO", "不支持的字段类型", "不支持的字段类型");
				htable.put("type", type); //显示类型

				if (type.equals("date") || type.equals("str") || type.equals("num")) {
					htable.put("length", parseLength(rs.getString(3))); //长度
					htable.put("need", rs.getString(4)); //必输
					htable.put("memo", rs.getString(8) == null ? "" : rs.getString(8)); //说明
					htable.put("readOnly", rs.getString(9)); //只读
				} else {
					htable.put("need", rs.getString(4)); //必输
					String seltype = rs.getString(6);
					htable.put("seltype", seltype); //下拉框类型
					if (seltype.equals("01")) {
						htable.put("dict", rs.getString(7)); //字典名
					} else if (seltype.equals("99")) {
						htable.put("tags", toSelectorTags(rs.getString(7)));
						htable.put("value", toSelectorHashtable(rs.getString(7)));
					} else
						throw new TranFailException("performQueryHeadDetail", "icbc.cmis.util.TableAdjustDAO", "不支持的选择域类型", "不支持的选择域类型");
					htable.put("need", rs.getString(4)); //必输
					htable.put("memo", rs.getString(8) == null ? "" : rs.getString(8)); //说明
					htable.put("readOnly", rs.getString(9)); //只读
				}

				htable.put("isPrimary", rs.getString(5));
				ret.add(htable);
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("performQueryHeadDetail", "icbc.cmis.util.TableAdjustDAO", e.getMessage(), e.getMessage());
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
			this.closeConnection();
		}

		return ret;
	}

	public void insert(
		String tableCode,
		Vector colomnInfo,
		String[] values,
		String checkFunction,
		String localInfo,
		String checkFunction_2)
		throws TranFailException {
		Hashtable ht0 = (Hashtable)colomnInfo.get(0);
		String sql = "insert into " + tableCode + " (" + (String)ht0.get("col");
		for (int i = 1; i < colomnInfo.size(); i++) {
			Hashtable htable = (Hashtable)colomnInfo.get(i);
			sql += "," + (String)htable.get("col");
		}
		sql += ") values( ?";
		for (int i = 1; i < colomnInfo.size(); i++)
			sql += ",?";
		sql += ")";

		PreparedStatement pstmt = null;
		CallableStatement stmt_call = null;
		ResultSet rs = null;
		int n = -1;

		try {
			try {
				this.getConnection();
				//增加事先校验，使用checkFunction_2
				if (checkFunction_2 != null && !checkFunction_2.equals("") && !checkFunction_2.equals("null")) {
					String keyValues = "";
					for (int i = 0; i < colomnInfo.size(); i++) {
						Hashtable htable = (Hashtable)colomnInfo.get(i);
						keyValues += "|" + values[i];
					}
					keyValues = keyValues.substring(1);
					String disposalType = "I";
					stmt_call = conn.prepareCall("{call " + checkFunction_2 + "(?,?,?,?,?)}");

					stmt_call.setString(1, keyValues);
					stmt_call.setString(2, disposalType);
					stmt_call.setString(3, localInfo);
					stmt_call.registerOutParameter(4, OracleTypes.VARCHAR);
					stmt_call.registerOutParameter(5, OracleTypes.VARCHAR);

					stmt_call.execute();
					String ret = stmt_call.getString(4);
					if (!ret.equals("0"))
						throw new TranFailException(
							"insert",
							"icbc.cmis.util.TableAdjustDAO",
							"新增校验失败:" + stmt_call.getString(5),
							"新增校验失败:" + stmt_call.getString(5));
				}

				pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < colomnInfo.size(); i++) {
					pstmt.setString(i + 1, values[i]);
				}
				n = pstmt.executeUpdate();
				if (n != 1)
					throw new TranFailException("insert", "icbc.cmis.util.TableAdjustDAO", "新增参数失败", "新增参数失败");

				//校验函数
				if (checkFunction != null && !checkFunction.equals("") && !checkFunction.equals("null")) {
					String keyValues = "";
					for (int i = 0; i < colomnInfo.size(); i++) {
						Hashtable htable = (Hashtable)colomnInfo.get(i);
						keyValues += "|" + values[i];
					}
					keyValues = keyValues.substring(1);
					String disposalType = "I";
					stmt_call = conn.prepareCall("{call " + checkFunction + "(?,?,?,?,?)}");

					stmt_call.setString(1, keyValues);
					stmt_call.setString(2, disposalType);
					stmt_call.setString(3, localInfo);
					stmt_call.registerOutParameter(4, OracleTypes.VARCHAR);
					stmt_call.registerOutParameter(5, OracleTypes.VARCHAR);

					stmt_call.execute();
					String ret = stmt_call.getString(4);
					if (!ret.equals("0"))
						throw new TranFailException(
							"insert",
							"icbc.cmis.util.TableAdjustDAO",
							"新增校验失败:" + stmt_call.getString(5),
							"新增校验失败:" + stmt_call.getString(5));
				}
				conn.commit();
				pstmt.close();
			} catch (SQLException sqexp) {
				if (sqexp.getErrorCode() == 1) {
					throw new TranFailException("insert", "icbc.cmis.util.ParaAdjustDAO", "该记录已经存在！", "该记录已经存在！");
				} else
					throw sqexp;
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ee) {
			}
			throw new TranFailException("insert", "icbc.cmis.util.TableAdjustDAO", e.getMessage(), e.getMessage());
		} finally {
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
			this.closeConnection();
		}
	}

	public void update(
		String tableCode,
		Vector colomnInfo,
		String[] values,
		String[] values_old,
		String checkFunction,
		String localInfo,
		String checkFunction_2)
		throws TranFailException {
		Hashtable ht0 = (Hashtable)colomnInfo.get(0);

		String sql = "update " + tableCode + " set " + (String)ht0.get("col") + "=?";
		for (int i = 1; i < colomnInfo.size(); i++) {
			Hashtable ht = (Hashtable)colomnInfo.get(i);
			sql += "," + (String)ht.get("col") + "=?";
		}
		if (!values_old[0].equals(""))
			sql += " where " + (String)ht0.get("col") + "=?";
		else
			sql += "where (" + (String)ht0.get("col") + "=? or " + (String)ht0.get("col") + " is null) ";

		for (int i = 1; i < colomnInfo.size(); i++) {
			Hashtable ht = (Hashtable)colomnInfo.get(i);
			if (!values_old[i].equals(""))
				sql += " and " + (String)ht.get("col") + "=?";
			else
				sql += " and (" + (String)ht.get("col") + "=? or " + (String)ht.get("col") + " is null) ";
		}

		java.sql.ResultSet rs = null;
		PreparedStatement pstmt = null;
		CallableStatement stmt_call = null;
		int n = -1;

		int i = 0;
		try {
			this.getConnection();
			//事先校验，使用checkfunction_2
			if (checkFunction_2 != null && !checkFunction_2.equals("") && !checkFunction_2.equals("null")) {
				String keyValues = "";
				for (i = 0; i < colomnInfo.size(); i++) {
					Hashtable htable = (Hashtable)colomnInfo.get(i);
					keyValues += "|" + values_old[i];
				}
				keyValues = keyValues.substring(1);
				String disposalType = "U";
				stmt_call = conn.prepareCall("{call " + checkFunction_2 + "(?,?,?,?,?)}");

				stmt_call.setString(1, keyValues);
				stmt_call.setString(2, disposalType);
				stmt_call.setString(3, localInfo);
				stmt_call.registerOutParameter(4, OracleTypes.VARCHAR);
				stmt_call.registerOutParameter(5, OracleTypes.VARCHAR);

				stmt_call.execute();
				String ret = stmt_call.getString(4);
				if (!ret.equals("0"))
					throw new TranFailException(
						"insert",
						"icbc.cmis.util.TableAdjustDAO",
						"修改校验失败:" + stmt_call.getString(5),
						"修改校验失败:" + stmt_call.getString(5));
			}

			pstmt = conn.prepareStatement(sql);
			for (i = 0; i < colomnInfo.size(); i++) {
				pstmt.setString(i + 1, values[i]);
			}
			for (i = 0; i < colomnInfo.size(); i++) {
				pstmt.setString(i + 1 + colomnInfo.size(), values_old[i]);

			}
			n = pstmt.executeUpdate();
			if (n < 1)
				throw new TranFailException("update", "icbc.cmis.util.TableAdjustDAO", "修改参数失败", "修改参数失败");
			String test = null;

			//校验函数
			if (checkFunction != null && !checkFunction.equals("") && !checkFunction.equals("null")) {
				String keyValues = "";
				for (i = 0; i < colomnInfo.size(); i++) {
					Hashtable htable = (Hashtable)colomnInfo.get(i);
					keyValues += "|" + values[i];
				}
				keyValues = keyValues.substring(1);
				String disposalType = "U";
				stmt_call = conn.prepareCall("{call " + checkFunction + "(?,?,?,?,?)}");

				stmt_call.setString(1, keyValues);
				stmt_call.setString(2, disposalType);
				stmt_call.setString(3, localInfo);
				stmt_call.registerOutParameter(4, OracleTypes.VARCHAR);
				stmt_call.registerOutParameter(5, OracleTypes.VARCHAR);

				stmt_call.execute();
				String ret = stmt_call.getString(4);
				if (!ret.equals("0"))
					throw new TranFailException(
						"insert",
						"icbc.cmis.util.TableAdjustDAO",
						"修改校验失败:" + stmt_call.getString(5),
						"修改校验失败:" + stmt_call.getString(5));
			} //校验结束

			/*--记录操作--log*/
			//流水号
			String icount = "";
			sql = "select lpad(SEQ_EDITLOG.nextval,10,'0') from dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				icount = rs.getString(1);
			}
			//柜员号
			int temp_pos = localInfo.indexOf('|');
			String employee_code = localInfo.substring(0, temp_pos);
			//操作时间
			String work_time = CmisConstance.getWorkDate("yyyyMMddhhmm");
			//表名 = tableCode
			//修改数据
			String keyValues = "";
			for (i = 0; i < colomnInfo.size(); i++) {
				Hashtable htable = (Hashtable)colomnInfo.get(i);
				keyValues += "|" + values_old[i];
			}
			keyValues = keyValues.substring(1);

			sql = "insert into MAG_ADJUST_LOG values(?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, icount);
			pstmt.setString(2, employee_code);
			pstmt.setString(3, work_time);
			pstmt.setString(4, tableCode);
			pstmt.setString(5, keyValues);

			n = pstmt.executeUpdate();
			if (n < 1)
				throw new TranFailException("delete", "icbc.cmis.util.TableAdjustDAO", "写入日志表失败", "写入日志表失败");

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ee) {
			}
			throw new TranFailException("update", "icbc.cmis.util.TableAdjustDAO", e.getMessage(), e.getMessage());
		} finally {
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
			this.closeConnection();
		}

	}

	public void delete(
		String tableCode,
		Vector colomnInfo,
		String[] values,
		String checkFunction,
		String localInfo,
		String checkFunction_2)
		throws TranFailException {
		CallableStatement stmt_call = null;
		java.sql.PreparedStatement pstmt = null;
		java.sql.ResultSet rs = null;
		String ret;
		try {
			this.getConnection();
			//以事先校验function2为优先，如果没定义去使用事后校验function
			//都在delete之前进行校验
			if (checkFunction_2 == null || checkFunction_2.equals("") || checkFunction_2.equals("null")) {
				checkFunction_2 = checkFunction;
			}

			if (checkFunction_2 != null && !checkFunction_2.equals("") && !checkFunction_2.equals("null")) {
				String keyValues = "";
				for (int i = 0; i < colomnInfo.size(); i++) {
					Hashtable htable = (Hashtable)colomnInfo.get(i);
					keyValues += "|" + values[i];
				}
				keyValues = keyValues.substring(1);
				String disposalType = "D";
				stmt_call = conn.prepareCall("{call " + checkFunction_2 + "(?,?,?,?,?)}");
				stmt_call.setString(1, keyValues);
				stmt_call.setString(2, disposalType);
				stmt_call.setString(3, localInfo);
				stmt_call.registerOutParameter(4, OracleTypes.VARCHAR);
				stmt_call.registerOutParameter(5, OracleTypes.VARCHAR);
				stmt_call.execute();
				ret = stmt_call.getString(4);
			} else {
				ret = "0";
			}

			if (ret.equals("0")) {
				Hashtable ht0 = (Hashtable)colomnInfo.get(0);
				String sql = "delete from " + tableCode;
				if (!values[0].equals(""))
					sql += " where " + (String)ht0.get("col") + "=?";
				else
					sql += " where (" + (String)ht0.get("col") + "=? or " + (String)ht0.get("col") + " is null) ";

				for (int i = 1; i < colomnInfo.size(); i++) {
					Hashtable ht = (Hashtable)colomnInfo.get(i);
					if (!values[i].equals(""))
						sql += " and " + (String)ht.get("col") + "=?";
					else
						sql += " and (" + (String)ht.get("col") + "=? or " + (String)ht.get("col") + " is null) ";
				}

				int n = -1;
				pstmt = conn.prepareStatement(sql);

				for (int i = 0; i < colomnInfo.size(); i++) {
					pstmt.setString(i + 1, values[i]);
				}
				n = pstmt.executeUpdate();

				if (n < 1)
					throw new TranFailException("delete", "icbc.cmis.util.TableAdjustDAO", "删除参数失败", "删除参数失败");

				/*--记录操作--log*/
				//流水号
				String icount = "";
				sql = "select lpad(SEQ_EDITLOG.nextval,10,'0') from dual";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					icount = rs.getString(1);
				}
				//柜员号
				int temp_pos = localInfo.indexOf('|');
				String employee_code = localInfo.substring(0, temp_pos);
				//操作时间
				String work_time = CmisConstance.getWorkDate("yyyyMMddhhmm");
				//表名 = tableCode
				//修改数据
				String keyValues = "";
				for (int i = 0; i < colomnInfo.size(); i++) {
					Hashtable htable = (Hashtable)colomnInfo.get(i);
					//if (((String)htable.get("isPrimary")).equals("1"))
					keyValues += "|" + values[i];
				}
				keyValues = keyValues.substring(1);

				sql = "insert into MAG_ADJUST_LOG values(?,?,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, icount);
				pstmt.setString(2, employee_code);
				pstmt.setString(3, work_time);
				pstmt.setString(4, tableCode);
				pstmt.setString(5, keyValues);

				n = pstmt.executeUpdate();
				if (n < 1)
					throw new TranFailException("delete", "icbc.cmis.util.TableAdjustDAO", "写入日志表失败", "写入日志表失败");

				conn.commit();
			} else {
				throw new TranFailException("delete", "icbc.cmis.util.TableAdjustDAO", stmt_call.getString(5), "");
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ee) {
			}
			throw new TranFailException("delete", "icbc.cmis.util.TableAdjustDAO", e.getMessage(), e.getMessage());
		} finally {
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
			this.closeConnection();
		}

	}

	/*
	 * 调用存储过程校验
	 */
	public String runProc(Vector colomnInfo, String[] values, String checkFunction, String localInfo) throws TranFailException {
		CallableStatement stmt_call = null;
		String result = "1";
		String reinfo = "";
		String restring = null;
		try {
			this.getConnection();
			if (checkFunction != null && !checkFunction.equals("") && !checkFunction.equals("null")) {
				//组装主键
				String keyValues = "";
				for (int i = 0; i < colomnInfo.size(); i++) {
					Hashtable htable = (Hashtable)colomnInfo.get(i);
					//if (((String)htable.get("isPrimary")).equals("1"))
					keyValues += "|" + values[i];
				}
				keyValues = keyValues.substring(1);
				String disposalType = "P";
				stmt_call = conn.prepareCall("{call " + checkFunction + "(?,?,?,?,?)}");

				stmt_call.setString(1, keyValues);
				stmt_call.setString(2, disposalType);
				stmt_call.setString(3, localInfo);
				stmt_call.registerOutParameter(4, OracleTypes.VARCHAR);
				stmt_call.registerOutParameter(5, OracleTypes.VARCHAR);

				stmt_call.execute();
				result = stmt_call.getString(4);
				reinfo = stmt_call.getString(5);
				restring = result + "|" + reinfo;
				stmt_call.close();
				this.closeConnection();

			} else {
				result = "1";
				reinfo = "未定义该校验过程";
				restring = result + "|" + reinfo;
				this.closeConnection();
			}
		} catch (Exception e) {
			try {
				if (stmt_call != null)
					stmt_call.close();
			} catch (Exception ee) {
			}
			this.closeConnection();
			result = "1";
			reinfo = "校验的存储过程异常" + e.getMessage();
			restring = result + "|" + reinfo;
		}
		return restring;
	}

}
