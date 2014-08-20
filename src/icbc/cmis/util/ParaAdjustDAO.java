package icbc.cmis.util;

import icbc.cmis.base.*;
import icbc.cmis.service.CmisDao;
import java.sql.*;
import java.util.*;
import java.io.*;
import oracle.jdbc.driver.*;

/**
 * 通用参数管理
 * @author zjfh-zhangyz
 * 2007-1-18 / 14:38:18
 *
 */
public class ParaAdjustDAO extends CmisDao {

	static Integer insertLock = new Integer(0);

	public ParaAdjustDAO(icbc.cmis.operation.CmisOperation op) {
		super(op);
	}

	public Hashtable getTableOwner(String tableCode) throws TranFailException {
		String sql = "select tab_owner,tab_type from mag_cache_tables where (tab_code=? or tab_code=?)";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Hashtable ret = new Hashtable();
		try {
			this.getConnection("missign");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, tableCode.toUpperCase());
			pstmt.setString(2, tableCode.toLowerCase());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				ret.put("tab_owner", rs.getString(1));
				ret.put("tab_type", rs.getString(2));
			} else
				throw new TranFailException("getTableOwner", "icbc.cmis.util.ParaAdjustDAO", "该表在初始化cache中不存在", "该表在初始化cache中不存在");
		} catch (Exception e) {
			throw new TranFailException("getTableOwner", "icbc.cmis.util.ParaAdjustDAO", e.getMessage(), e.getMessage());
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

	/**
	 * D类字典表分类列表
	 * @return
	 * @throws Exception
	 */
	public IndexedDataCollection getTableTypeD() throws Exception {
		IndexedDataCollection iColl = new IndexedDataCollection();
		KeyedDataCollection kColl = new KeyedDataCollection();
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pstmt = null;
		String sql = null;

		try {
			getConnection("cmis3");
			sql = "select TYPE001, TYPE002 from cmis3.MAG_ADJUST_TABS_DEFTYPE where TYPE001 like 'D%'";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				kColl = new KeyedDataCollection();
				kColl.addElement("1", rs.getString(1));
				kColl.addElement("2", rs.getString(2));
				iColl.addElement(kColl);
			}
			pstmt.close();
			closeConnection();
			return iColl;
		} catch (Exception e) {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ee) {
			}
			closeConnection();
			throw new TranFailException("getTableTypeD", "icbc.cmis.util.ParaAdjustDAO", e.getMessage(), sql);
		}

	}

	public Vector performQueryTableList(String major, String bankFlag) throws TranFailException {
		Vector ret = new Vector();
		String sql =
			"select a.adjust001, b.adjust002, a.adjust006, a.adjust007, b.adjust003, b.adjust005, b.adjust007 "
				+ "from mag_adjust_tabs a,mag_adjust_tabs_def b "
				+ "where a.adjust005=? and a.adjust001=b.adjust001 and "
				+ "b.adjust003 like 'D%' and (a.adjust004 is null or a.adjust004 = '' or "
				+ "a.adjust004 like '%' || ? || '%') "
				+ "order by b.adjust003, a.adjust001";

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			int bankIndex = Integer.parseInt(bankFlag);
			this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bankFlag);
			pstmt.setString(2, major);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				String[] str = new String[7];
				str[0] = rs.getString(1);
				str[1] = rs.getString(2);
				str[2] = rs.getString(4);
				str[3] = rs.getString(3);
				str[4] = rs.getString(5); //字典表类型
				str[5] = rs.getString(6); //校验，用于I和U后的校验，以及D前的校验，以及P的校验
				str[6] = rs.getString(7); //校验2，用于I和U前的校验
				ret.add(str);
			}
		} catch (Exception e) {
			throw new TranFailException("performQueryTableList", "icbc.cmis.util.ParaAdjustDAO", e.getMessage(), e.getMessage());
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

	private String parseLength(String src) {
		int startPos = src.indexOf("(");
		int endPos = src.indexOf(")");
		return src.substring(startPos + 1, endPos);
	}

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
					throw new TranFailException("performQueryHeadDetail", "icbc.cmis.util.ParaAdjustDAO", "不支持的字段类型", "不支持的字段类型");
				htable.put("type", type); //显示类型

				if (type.equals("date") || type.equals("str") || type.equals("num")) {
					htable.put("length", parseLength(rs.getString(3))); //长度
					htable.put("need", rs.getString(4)); //必输
					htable.put("memo", rs.getString(8) == null ? "" : rs.getString(8));
					//说明
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
						throw new TranFailException("performQueryHeadDetail", "icbc.cmis.util.ParaAdjustDAO", "不支持的选择域类型", "不支持的选择域类型");
					htable.put("need", rs.getString(4)); //必输
					htable.put("memo", rs.getString(8) == null ? "" : rs.getString(8));
					//说明
					htable.put("readOnly", rs.getString(9)); //只读
				}
				htable.put("isPrimary", rs.getString(5));
				ret.add(htable);
			}
		} catch (TranFailException e) {
			throw e;
		} catch (Exception e) {
			throw new TranFailException("performQueryHeadDetail", "icbc.cmis.util.ParaAdjustDAO", e.getMessage(), e.getMessage());
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

				//做insert
				pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < colomnInfo.size(); i++) {
					pstmt.setString(i + 1, values[i]);
				}
				n = pstmt.executeUpdate();
				if (n != 1) {
					throw new TranFailException("insert", "icbc.cmis.util.ParaAdjustDAO", "新增参数失败", "新增参数失败");
				}

				//运行事后校验
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
			throw new TranFailException("insert", "icbc.cmis.util.ParaAdjustDAO", e.getMessage(), e.getMessage());
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
		//return n;
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

		PreparedStatement pstmt = null;
		CallableStatement stmt_call = null;
		int n = -1;

		int i = 0;

		try {
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

				//运行update
				pstmt = conn.prepareStatement(sql);
				for (i = 0; i < colomnInfo.size(); i++) {
					pstmt.setString(i + 1, values[i]);
				}
				for (i = 0; i < colomnInfo.size(); i++) {
					pstmt.setString(i + 1 + colomnInfo.size(), values_old[i]);
				}
				n = pstmt.executeUpdate();
				if (n < 1) {
					throw new TranFailException("update", "icbc.cmis.util.ParaAdjustDAO", "修改参数失败", "修改参数失败");
				}

				//运行事后校验
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

				conn.commit();

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
			throw new TranFailException("update", "icbc.cmis.util.ParaAdjustDAO", e.getMessage(), e.getMessage());
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
		PreparedStatement pstmt = null;
		CallableStatement stmt_call = null;
		String ret;
		int n = -1;

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
				pstmt = conn.prepareStatement(sql);
				for (int i = 0; i < colomnInfo.size(); i++) {
					pstmt.setString(i + 1, values[i]);
				}
				n = pstmt.executeUpdate();
				if (n < 1)
					throw new TranFailException("delete", "icbc.cmis.util.ParaAdjustDAO", "删除参数失败", "删除参数失败");
				conn.commit();
			} else {
				throw new TranFailException("delete", "icbc.cmis.util.TableAdjustDAO", stmt_call.getString(5), "");
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ee) {
			}
			throw new TranFailException("delete", "icbc.cmis.util.ParaAdjustDAO", e.getMessage(), e.getMessage());
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
}
