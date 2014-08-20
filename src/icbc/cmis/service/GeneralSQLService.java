package icbc.cmis.service;

import icbc.cmis.base.*;
import java.sql.*;
import java.text.NumberFormat;
import java.util.*;
/**
 * Insert the type's description here.
 * Creation date: (2002-1-7 9:44:19)
 * @author: Administrator
 * 			//add by chenj	20040517 处理没有dict_order列的字典
 */
public class GeneralSQLService extends CmisDao {
	private Vector v_tables = new Vector();
	private Vector tmpTable = new Vector();
	private Vector vTable2 = new Vector();
	private Vector vTable3 = new Vector();
	private Vector vTable4 = new Vector();
	//chenj 20070424	
	private Vector vTable10 = new Vector();
	private Vector vTable11 = new Vector();
	private Vector vTable12 = new Vector();
	private Vector vTable13 = new Vector();
	private Vector vTable14 = new Vector();
	/**
	 * GeneralSQLService constructor comment.
	 */
	public GeneralSQLService() {
		super(new icbc.cmis.operation.DummyOperation());
	}
	/**
	 * GeneralSQLService constructor comment.
	 * @param dbResourceName1 java.lang.String
	 */
	public GeneralSQLService(String dbResourceName1) {
		super(dbResourceName1, new icbc.cmis.operation.DummyOperation());
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-4-9 11:22:55)
	 */
	private void initDA200251012(Vector vTable) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		String dMsg = "";
		try {

			String table_id = null;
			String tmpName = null;
			boolean notExist = true;
			for (int i = 0; i < vTable.size(); i++) {
				table_id = ((String[]) vTable.elementAt(i))[0];
				if (table_id.toLowerCase().equals("da200251012")) {
					tmpName = ((String[]) vTable.elementAt(i))[1];
					notExist = false;
					break;
				}
			}
			if (notExist) {
				CmisConstance.da200251012 = null;
				return;
			}
			dMsg =
				"tableName:"
					+ table_id
					+ ",userId:"
					+ tmpName
					+ ",tableType:0[handle the ratio]";
			String tmpVerify = (String) CmisConstance.getPassVerify(tmpName);
			getConnByVerify(tmpName, tmpVerify);
			String sql =
				"select dict_ratio,dict_code,dict_name from "
					+ table_id
					+ " order by dict_order";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			DA200251012Bean da200251012 = new DA200251012Bean();
			while (rs.next()) {
				String ratio = rs.getString(1);
				String key = rs.getString(2);
				String value = rs.getString(3);
				if (ratio == null)
					ratio = "nullratio";
				if (key == null)
					key = "nullkey";
				if (value == null)
					value = "";
				da200251012.addData(key, value, ratio);
			}
			CmisConstance.da200251012 = da200251012;
			rs.close();
			stmt.close();
			closeConnection();
			dMsg = "";

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-4-9 11:22:55)
	 */
	private void initDA200251031(Vector vTable) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		String dMsg = "";
		try {

			String table_id = null;
			String tmpName = null;
			boolean notExist = true;
			for (int i = 0; i < vTable.size(); i++) {
				table_id = ((String[]) vTable.elementAt(i))[0];
				if (table_id.toLowerCase().equals("da200251031")) {
					tmpName = ((String[]) vTable.elementAt(i))[1];
					notExist = false;
					break;
				}
			}
			if (notExist) {
				CmisConstance.da200251031 = null;
				return;
			}
			dMsg =
				"tableName:"
					+ table_id
					+ ",userId:"
					+ tmpName
					+ ",tableType:0[handle the flag]";
			String tmpVerify = (String) CmisConstance.getPassVerify(tmpName);
			getConnByVerify(tmpName, tmpVerify);
			stmt = conn.createStatement();
			String sql =
				"select dict_flag,dict_code,dict_name from "
					+ table_id
					+ " order by dict_order";
			rs = stmt.executeQuery(sql);
			DA200251031Bean da200251031 = new DA200251031Bean();
			while (rs.next()) {
				String flag = rs.getString(1);
				String key = rs.getString(2);
				String value = rs.getString(3);
				if (flag == null)
					flag = "nullareacode";
				if (key == null)
					key = "nullkey";
				if (value == null)
					value = "";
				da200251031.addRow(flag, key, value);
			}
			CmisConstance.da200251031 = da200251031;
			rs.close();
			stmt.close();
			closeConnection();
			dMsg = "";

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}

	}

	/**
	 * Insert the method's description here.
	 * Creation date: (2002-4-9 11:22:55)
	 */
	private void initDA220091006(Vector vTable) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		String dMsg = "";
		try {

			String table_id = null;
			String tmpName = null;
			boolean notExist = true;
			for (int i = 0; i < vTable.size(); i++) {
				table_id = ((String[]) vTable.elementAt(i))[0];
				if (table_id.toLowerCase().equals("da220091006")) {
					tmpName = ((String[]) vTable.elementAt(i))[1];
					notExist = false;
					break;
				}
			}
			if (notExist) {
				CmisConstance.da220091006 = null;
				return;
			}
			dMsg =
				"tableName:"
					+ table_id
					+ ",userId:"
					+ tmpName
					+ ",tableType:1[handle the flag]";
			String tmpVerify = (String) CmisConstance.getPassVerify(tmpName);
			getConnByVerify(tmpName, tmpVerify);
			stmt = conn.createStatement();
			String sql =
				"select dict_type,dict_code,dict_name from "
					+ table_id
					+ " order by dict_order";
			rs = stmt.executeQuery(sql);
			DA220091006Bean da220091006 = new DA220091006Bean();
			while (rs.next()) {
				String flag = rs.getString(1);
				String key = rs.getString(2);
				String value = rs.getString(3);
				if (flag == null)
					flag = "nullareacode";
				if (key == null)
					key = "nullkey";
				if (value == null)
					value = "";
				da220091006.addRow(flag, key, value);
			}
			CmisConstance.da220091006 = da220091006;
			rs.close();
			stmt.close();
			closeConnection();
			dMsg = "";

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}

	}

	private void initMagRateCode(Vector vTable) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		String dMsg = "";
		java.text.DecimalFormat df = new java.text.DecimalFormat();
		df.applyLocalizedPattern("0.000000");
		try {

			String table_id = null;
			String tmpName = null;
			boolean notExist = true;
			for (int i = 0; i < vTable.size(); i++) {
				table_id = ((String[]) vTable.elementAt(i))[0];
				if (table_id.toLowerCase().equals("mag_rate_code")) {
					tmpName = ((String[]) vTable.elementAt(i))[1];
					notExist = false;
					break;
				}
			}
			if (notExist) {
				//            CmisConstance.da220091006 = null;
				return;
			}
			dMsg =
				"tableName:"
					+ table_id
					+ ",userId:"
					+ tmpName
					+ ",tableType:1[handle the flag]";
			String tmpVerify = (String) CmisConstance.getPassVerify(tmpName);
			getConnByVerify(tmpName, tmpVerify);
			stmt = conn.createStatement();
			String sql =
				"select area_code,currtype,ratecode,rate,notes from "
					+ table_id
					+ " order by area_code";
			rs = stmt.executeQuery(sql);
			//SelfDefineTableBean selfDefineTableBean = new SelfDefineTableBean();
			while (rs.next()) {
				String area_code = rs.getString(1);
				String currtype = rs.getString(2);
				String ratecode = rs.getString(3);
				double rate = rs.getDouble(4);
				String notes = rs.getString(5);
				if (area_code == null)
					area_code = "nullareacode";
				if (currtype == null)
					currtype = "";
				if (ratecode == null)
					ratecode = "";
				//            if (rate == null)
				//                rate = "";
				if (notes == null)
					notes = "";
				Vector values = new Vector(4);
				values.add(currtype);
				values.add(ratecode);
				values.add(df.format(rate));
				values.add(notes);
				//selfDefineTableBean.addRow(area_code, values);
			}

			//CmisConstance.getDictParam().put("mag_rate_code",selfDefineTableBean);
			//CmisConstance.da220091006 = da220091006;
			rs.close();
			stmt.close();
			closeConnection();
			dMsg = "";

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-5 20:32:48)
	 * @param v_tables java.util.Vector
	 */
	private void initDictType0(Vector v_tables) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		String dMsg = "";
		String tmpVerify = (String) CmisConstance.getPassVerify("missign");

		try {

			getConnByVerify("missign", tmpVerify);

			for (int i = 0; i < v_tables.size(); i++) {

				String table_id = ((String[]) v_tables.elementAt(i))[0];
				String tmpName = ((String[]) v_tables.elementAt(i))[1];
				dMsg =
					"tableName:"
						+ table_id
						+ ",userId:"
						+ tmpName
						+ ",tableType:0";

				//getConnByVerify("missign",tmpVerify);
				stmt = conn.createStatement();
				DictBean dataBean = new DictBean();
				boolean isNull = true;
				try {
					rs =
						stmt.executeQuery(
							"select dict_code,dict_name from "
								+ tmpName
								+ "."
								+ table_id
								+ " order by DICT_ORDER");
				} catch (SQLException sqle) {
					if (sqle.getErrorCode() == 942) {
						System.out.println(dMsg + " not found in DB");
						if (rs != null) {
							try {
								rs.close();
							} catch (Exception ez) {
							}
						}

						if (stmt != null) {
							try {
								stmt.close();
							} catch (Exception ez) {
							}
						}
						/*
						try{
							closeConnection();
						}catch(Exception ez){}
						*/
						continue;

					}

				}
				while (rs.next()) {
					isNull = false;
					String str1 = rs.getString(1);
					if (str1 == null)
						str1 = "isNullKey";
					String str2 = rs.getString(2);
					if (str2 == null)
						str2 = "";
					dataBean.addData(str1, str2);
				}

				dataBean.setNullMark(isNull);
				if (isNull) {
					System.out.println(table_id + "is null");
				}
				CmisConstance.getDictParam().put(
					table_id.toLowerCase(),
					dataBean);
				rs.close();
				stmt.close();
				//closeConnection();
				dMsg = "";
			}

			closeConnection();

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}

	}

	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-5 20:32:48)
	 * @param v_tables java.util.Vector
	 */
	private void initDictType10(Vector v_tables) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		String dMsg = "";
		String tmpVerify = (String) CmisConstance.getPassVerify("missign");

		try {

			getConnByVerify("missign", tmpVerify);

			for (int i = 0; i < v_tables.size(); i++) {

				String table_id = ((String[]) v_tables.elementAt(i))[0];
				String tmpName = ((String[]) v_tables.elementAt(i))[1];
				dMsg =
					"tableName:"
						+ table_id
						+ ",userId:"
						+ tmpName
						+ ",tableType:10";

				//getConnByVerify("missign",tmpVerify);

				stmt = conn.createStatement();

				boolean isNull = true;

				for (java.util.Iterator itr = CmisConstance.allLang.iterator();
					itr.hasNext();
					) {

					String lang_code = (String) itr.next();
					try {
						rs =
							stmt.executeQuery(
								"select dict_code,dict_name from "
									+ tmpName
									+ "."
									+ table_id
									+ " where lang_code = '"
									+ lang_code
									+ "' order by DICT_ORDER");
					} catch (SQLException sqle) {
						if (sqle.getErrorCode() == 942) {
							System.out.println(dMsg + " not found in DB");
//							if (rs != null) {
//								try {
//									rs.close();
//								} catch (Exception ez) {
//								}
//							}
//
//							if (stmt != null) {
//								try {
//									stmt.close();
//								} catch (Exception ez) {
//								}
//							}
							/*
							try{
								closeConnection();
							}catch(Exception ez){}
							*/
							continue;

						}

					}
					DictBean dataBean = new DictBean();
					while (rs.next()) {
						isNull = false;
						String str1 = rs.getString(1);
						if (str1 == null)
							str1 = "isNullKey";
						String str2 = rs.getString(2);
						if (str2 == null)
							str2 = "";
						dataBean.addData(str1, str2);
					}

					dataBean.setNullMark(isNull);
					if (isNull) {
						System.out.println(table_id + "is null");
					}
					CmisConstance.getDictParam(lang_code).put(
						table_id.toLowerCase(),
						dataBean);
					rs.close();

				}
				stmt.close();
				//closeConnection();
				dMsg = "";
			}

			closeConnection();

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-5 20:42:14)
	 * @param tmpTable java.util.Vector
	 */
	private void initDictType1(Vector tmpTable) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String dMsg = "";
		String tmpVerify = (String) CmisConstance.getPassVerify("missign");
		try {

			getConnByVerify("missign", tmpVerify);

			for (int j = 0; j < tmpTable.size(); j++) {
				String tableName = ((String[]) tmpTable.elementAt(j))[0];
				String tmpName = ((String[]) tmpTable.elementAt(j))[1];
				dMsg =
					"tableName:"
						+ tableName
						+ ",userId:"
						+ tmpName
						+ ",tableType:1";
				//getConnByVerify("missign",tmpVerify);

				//String sqlUTC = " select COLUMN_NAME from user_tab_columns where TABLE_NAME =?";
				String sqlUTC =
					" select COLUMN_NAME from all_tab_columns where TABLE_NAME = ? and owner = ?";
				stmt = conn.prepareStatement(sqlUTC);
				stmt.setString(1, tableName);
				stmt.setString(2, tmpName);
				rs = stmt.executeQuery();
				StringBuffer col = new StringBuffer();
				int count = 0;
				icbc.cmis.base.TableBean tableBean =
					new icbc.cmis.base.TableBean();

				while (rs.next()) {
					count++;
					String colName = rs.getString(1);
					col = col.append(colName + ",");
					tableBean.addColumn(colName.toLowerCase());

				}

				if (count == 0) {
					System.out.println(
						"select COLUMN_NAME from all_tab_columns where TABLE_NAME = '"
							+ tableName
							+ "' and owner = '"
							+ tmpName
							+ "' not fount!!");
					if (rs != null) {
						try {
							rs.close();
						} catch (Exception ez) {
						}
					}

					if (stmt != null) {
						try {
							stmt.close();
						} catch (Exception ez) {
						}
					}
					/*
					try{
						closeConnection();
					}catch(Exception ez){}
					*/
					continue;
				}

				rs.close();
				if (count > 0)
					col = col.deleteCharAt(col.length() - 1);

				String sqlT =
					"select "
						+ new String(col)
						+ " from "
						+ tmpName
						+ "."
						+ tableName;
				stmt.close();
				stmt = conn.prepareStatement(sqlT);

				try {
					rs = stmt.executeQuery();
				} catch (SQLException sqle) {
					if (sqle.getErrorCode() == 942) {
						System.out.println(dMsg + " not found in DB");
						if (rs != null) {
							try {
								rs.close();
							} catch (Exception ez) {
							}
						}

						if (stmt != null) {
							try {
								stmt.close();
							} catch (Exception ez) {
							}
						}
						/*
						try{
							closeConnection();
						}catch(Exception ez){}					
						*/
						continue;
					}

				}

				while (rs.next()) {
					java.util.Vector row = new java.util.Vector();
					for (int h = 1; h <= count; h++) {
						String tmpStr = (String) rs.getString(h);
						row.add(h - 1, tmpStr);
					}
					tableBean.addRow(row);
				}

				CmisConstance.getDictParam().put(
					tableName.toLowerCase(),
					tableBean);
				rs.close();
				stmt.close();
				//closeConnection();
				dMsg = "";

			}

			closeConnection();

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-5 20:42:14)
	 * @param tmpTable java.util.Vector
	 */
	private void initDictType11(Vector tmpTable) throws Exception {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		String dMsg = "";
		String tmpVerify = (String) CmisConstance.getPassVerify("missign");
		try {

			getConnByVerify("missign", tmpVerify);

			for (int j = 0; j < tmpTable.size(); j++) {
				String tableName = ((String[]) tmpTable.elementAt(j))[0];
				String tmpName = ((String[]) tmpTable.elementAt(j))[1];
				dMsg =
					"tableName:"
						+ tableName
						+ ",userId:"
						+ tmpName
						+ ",tableType:11";

				//getConnByVerify("missign",tmpVerify);
				//String sqlUTC = " select COLUMN_NAME from user_tab_columns where TABLE_NAME =?";

				for (java.util.Iterator itr = CmisConstance.allLang.iterator();
					itr.hasNext();
					) {
					String lang_code = (String) itr.next();
					String sqlUTC =
						" select COLUMN_NAME from all_tab_columns where TABLE_NAME = ? and owner = ?";
					stmt = conn.prepareStatement(sqlUTC);
					stmt.setString(1, tableName);
					stmt.setString(2, tmpName);
					rs = stmt.executeQuery();
					StringBuffer col = new StringBuffer();
					int count = 0;
					icbc.cmis.base.TableBean tableBean =
						new icbc.cmis.base.TableBean();

					while (rs.next()) {
						count++;
						String colName = rs.getString(1);
						col = col.append(colName + ",");
						tableBean.addColumn(colName.toLowerCase());

					}

					if (count == 0) {
						System.out.println(
							"select COLUMN_NAME from all_tab_columns where TABLE_NAME = '"
								+ tableName
								+ "' and owner = '"
								+ tmpName
								+ "' not fount!!");
//						if (rs != null) {
//							try {
//								rs.close();
//							} catch (Exception ez) {
//							}
//						}
//
//						if (stmt != null) {
//							try {
//								stmt.close();
//							} catch (Exception ez) {
//							}
//						}
						/*
						try{
							closeConnection();
						}catch(Exception ez){}
						*/
						continue;
					}

					rs.close();
					if (count > 0)
						col = col.deleteCharAt(col.length() - 1);

					String sqlT =
						"select "
							+ new String(col)
							+ " from "
							+ tmpName
							+ "."
							+ tableName
							+ " where lang_code = '"
							+ lang_code
							+ "'";
					stmt.close();
					stmt = conn.prepareStatement(sqlT);

					try {
						rs = stmt.executeQuery();
					} catch (SQLException sqle) {
						if (sqle.getErrorCode() == 942) {
							System.out.println(dMsg + " not found in DB");
							if (rs != null) {
								try {
									rs.close();
								} catch (Exception ez) {
								}
							}

							if (stmt != null) {
								try {
									stmt.close();
								} catch (Exception ez) {
								}
							}
							/*
							try{
								closeConnection();
							}catch(Exception ez){}	
							*/
							continue;
						}

					}

					while (rs.next()) {
						java.util.Vector row = new java.util.Vector();
						for (int h = 1; h <= count; h++) {
							String tmpStr = (String) rs.getString(h);
							row.add(h - 1, tmpStr);
						}
						tableBean.addRow(row);
					}

					CmisConstance.getDictParam(lang_code).put(
						tableName.toLowerCase(),
						tableBean);
					rs.close();
				}
				stmt.close();
				//closeConnection();
				dMsg = "";

			}

			closeConnection();

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-5 20:51:47)
	 * @param vTable java.util.Vector
	 */
	private void initDictType2(Vector vTable) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		String dMsg = "";
		String tmpVerify = (String) CmisConstance.getPassVerify("missign");
		try {

			getConnByVerify("missign", tmpVerify);

			for (int i = 0; i < vTable.size(); i++) {

				String table_id = ((String[]) vTable.elementAt(i))[0];
				String tmpName = ((String[]) vTable.elementAt(i))[1];
				dMsg =
					"tableName:"
						+ table_id
						+ ",userId:"
						+ tmpName
						+ ",tableType:2";

				//getConnByVerify("missign",tmpVerify);
				stmt = conn.createStatement();
				String sql =
					"select area_code,dict_code,dict_name from "
						+ tmpName
						+ "."
						+ table_id
						+ " order by dict_order";
				try {
					rs = stmt.executeQuery(sql);
				} catch (SQLException sqle) {
					//add by chenj	20040517 处理没有dict_order列的字典
					if (sqle.getErrorCode() == 904) {
						try {
							rs =
								stmt.executeQuery(
									"select area_code,dict_code,dict_name from "
										+ tmpName
										+ "."
										+ table_id);
						} catch (SQLException e) {
							if (e.getErrorCode() == 942) {
								System.out.println(dMsg + " not found in DB");
								if (rs != null) {
									try {
										rs.close();
									} catch (Exception ez) {
									}
								}

								if (stmt != null) {
									try {
										stmt.close();
									} catch (Exception ez) {
									}
								}
								/*
								try{
									closeConnection();
								}catch(Exception ez){}
								*/
								continue;

							}
						}
					}
					//add by chenj	end

					else if (sqle.getErrorCode() == 942) {
						System.out.println(dMsg + " not found in DB");
						if (rs != null) {
							try {
								rs.close();
							} catch (Exception ez) {
							}
						}

						if (stmt != null) {
							try {
								stmt.close();
							} catch (Exception ez) {
							}
						}
						/*
						try{
							closeConnection();
						}catch(Exception ez){}
						*/
						continue;

					}

				}

				SelfDefineDictBean selfBean = new SelfDefineDictBean();
				while (rs.next()) {
					String areaCode = rs.getString(1);
					String key = rs.getString(2);
					String value = rs.getString(3);
					if (areaCode == null)
						areaCode = "nullareacode";
					if (key == null)
						key = "nullkey";
					if (value == null)
						value = "";
					selfBean.addRow(areaCode, key, value);
				}
				CmisConstance.getDictParam().put(
					table_id.trim().toLowerCase(),
					selfBean);
				rs.close();
				stmt.close();
				//closeConnection();
				dMsg = "";
			}

			closeConnection();

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-5 20:51:47)
	 * @param vTable java.util.Vector
	 */
	private void initDictType12(Vector vTable) throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		String dMsg = "";
		String tmpVerify = (String) CmisConstance.getPassVerify("missign");
		try {

			getConnByVerify("missign", tmpVerify);

			for (int i = 0; i < vTable.size(); i++) {

				String table_id = ((String[]) vTable.elementAt(i))[0];
				String tmpName = ((String[]) vTable.elementAt(i))[1];
				dMsg =
					"tableName:"
						+ table_id
						+ ",userId:"
						+ tmpName
						+ ",tableType:12";

				//getConnByVerify("missign",tmpVerify);
				stmt = conn.createStatement();

				for (java.util.Iterator itr = CmisConstance.allLang.iterator();
					itr.hasNext();
					) {
					String lang_code = (String) itr.next();

					String sql =
						"select area_code,dict_code,dict_name from "
							+ tmpName
							+ "."
							+ table_id
							+ " where lang_code = '"
							+ lang_code
							+ "' order by dict_order";
					try {
						rs = stmt.executeQuery(sql);
					} catch (SQLException sqle) {
						//add by chenj	20040517 处理没有dict_order列的字典
						if (sqle.getErrorCode() == 904) {
							try {
								rs =
									stmt.executeQuery(
										"select area_code,dict_code,dict_name from "
											+ tmpName
											+ "."
											+ table_id
											+ " where lang_code = '"
											+ lang_code
											+ "'");
							} catch (SQLException e) {
								if (e.getErrorCode() == 942) {
									System.out.println(
										dMsg + " not found in DB");
//									if (rs != null) {
//										try {
//											rs.close();
//										} catch (Exception ez) {
//										}
//									}
//
//									if (stmt != null) {
//										try {
//											stmt.close();
//										} catch (Exception ez) {
//										}
//									}
									/*
									try{
										closeConnection();
									}catch(Exception ez){}
									*/
									continue;

								}
							}
						}
						//add by chenj	end

						else if (sqle.getErrorCode() == 942) {
							System.out.println(dMsg + " not found in DB");
							if (rs != null) {
								try {
									rs.close();
								} catch (Exception ez) {
								}
							}

							if (stmt != null) {
								try {
									stmt.close();
								} catch (Exception ez) {
								}
							}
							/*
							try{
								closeConnection();
							}catch(Exception ez){}
							*/
							continue;

						}

					}

					SelfDefineDictBean selfBean = new SelfDefineDictBean();
					while (rs.next()) {
						String areaCode = rs.getString(1);
						String key = rs.getString(2);
						String value = rs.getString(3);
						if (areaCode == null)
							areaCode = "nullareacode";
						if (key == null)
							key = "nullkey";
						if (value == null)
							value = "";
						selfBean.addRow(areaCode, key, value);
					}
					CmisConstance.getDictParam(lang_code).put(
						table_id.trim().toLowerCase(),
						selfBean);
					rs.close();
				}
				stmt.close();
				//closeConnection();
				dMsg = "";
			}

			closeConnection();

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}
	}

	private void initDictType3(Vector vTable) throws Exception {

		String dMsg = "";
		try {
			for (int i = 0; i < vTable.size(); i++) {

				String table_id = ((String[]) vTable.elementAt(i))[0];
				String tmpName = ((String[]) vTable.elementAt(i))[1];
				dMsg =
					"tableName:"
						+ table_id
						+ ",userId:"
						+ tmpName
						+ ",tableType:2";
				new icbc.cmis.service.ParaDictService().initialDictTables();
			}

		} catch (Exception e) {
			e =
				new Exception(
					"initialization dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			throw e;
		}
	}

	public void initialDictTables() throws java.lang.Exception {
		try {
			initFuncDBMapTable();

			Enumeration e = CmisConstance.getDBUsers();
			for (; e.hasMoreElements();) {
				String sysType = (String) e.nextElement();
				magCacheTable(sysType.trim());
			}
			initDictType0(v_tables);
			initDictType1(tmpTable);
			initDictType2(vTable2);
			initDictType3(vTable3);
			initDA200251031(v_tables);
			initDA220091006(tmpTable);

			//chenj 20070423
			initDictType10(vTable10);
			initDictType11(vTable11);
			initDictType12(vTable12);

			//chenj

			//	initDA200251012(v_tables);
			//initMagRateCode(vTable4);

		} catch (Exception e) {
			System.out.println(
				"initialDictTables error!\n errormessage: " + e.getMessage());
			throw e;
		} finally {
			try {
				closeConnection();
			} catch (Exception ee) {
			}
		}

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-4-15 14:26:49)
	 * @param systemUserName java.lang.String
	 */
	private void magCacheTable(String systemUserName) throws Exception {
		PreparedStatement stmt = null;
		try {
			//		String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
			//	    String userPass = (String)CmisConstance.getParameterSettings().get("dbUserPass");
			//		getConnection(userId,userPass);
			getConnection("missign");
			String sql =
				"select tab_code,tab_type,TAB_OWNER from mag_cache_tables where TAB_OWNER=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, systemUserName.trim().toUpperCase());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				String table_name = rs.getString(1);
				String tableType = rs.getString(2);
				String userName = rs.getString(3);
				String[] tableInfo = { table_name.trim(), userName.trim()};

				if (tableType.equals("0")) {
					v_tables.addElement(tableInfo);
				} else if (tableType.equals("1")) {
					tmpTable.addElement(tableInfo);
				} else if (tableType.equals("2")) {
					vTable2.addElement(tableInfo);
				} else if (tableType.equals("3")) {
					vTable3.addElement(tableInfo);
				} else if (tableType.equals("4")) {
					vTable4.addElement(tableInfo);
				}
				//chenj 20070423
				else if (tableType.equals("10")) {
					vTable10.addElement(tableInfo);
				} else if (tableType.equals("11")) {
					vTable11.addElement(tableInfo);
				} else if (tableType.equals("12")) {
					vTable12.addElement(tableInfo);
				} else if (tableType.equals("13")) {
					vTable13.addElement(tableInfo);
				} else if (tableType.equals("14")) {
					vTable14.addElement(tableInfo);
				}
				//chenj end

			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			try {
				stmt.close();
			} catch (Exception ee) {
			}
			throw e;
		} finally {
			try {
				closeConnection();
			} catch (Exception ee) {
			}
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (2002-4-15 14:26:49)
	 * @param systemUserName java.lang.String
	 */
	private void initFuncDBMapTable() throws Exception {
		Statement stmt = null;
		try {
			CmisConstance.funDbMap = new Hashtable();

			//		String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
			//	    String userPass = (String)CmisConstance.getParameterSettings().get("dbUserPass");
			//		getConnection(userId,userPass);
			getConnection("missign");
			stmt = conn.createStatement();
			String sql = "select func_code,dbpoolname from mag_function";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String funcCode = rs.getString(1);
				String poolName = rs.getString(2);
				if (funcCode == null || funcCode.trim().length() == 0)
					funcCode = "";
				if (poolName == null || poolName.trim().length() == 0)
					poolName = "";
				CmisConstance.funDbMap.put(funcCode.trim(), poolName.trim());
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			try {
				stmt.close();
			} catch (Exception ee) {
			}
			throw e;
		} finally {
			try {
				closeConnection();
			} catch (Exception ee) {
			}
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (2002-4-9 11:22:55)
	 */
	private void updateDA200251012(String tableName, String tmpName)
		throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String tmpVerify = (String) CmisConstance.getPassVerify(tmpName);
			getConnByVerify(tmpName, tmpVerify);
			stmt = conn.createStatement();
			DA200251012Bean dataBean = new DA200251012Bean();
			rs =
				stmt.executeQuery(
					"select dict_ratio,dict_code,dict_name from "
						+ tableName
						+ " order by dict_order");
			while (rs.next()) {
				String ratio = rs.getString(1);
				if (ratio == null)
					ratio = "nullratio";
				String key = rs.getString(2);
				if (key == null)
					key = "nullkey";
				String value = rs.getString(3);
				if (value == null)
					value = "";
				dataBean.addData(key, value, ratio);
			}

			synchronized (this) {
				CmisConstance.da200251012 = dataBean;
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
		} finally {
			closeConnection();
		}

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-4-9 11:22:55)
	 */
	private void updateDA200251031(String tableName, String tmpName)
		throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String tmpVerify = (String) CmisConstance.getPassVerify(tmpName);
			getConnByVerify(tmpName, tmpVerify);
			stmt = conn.createStatement();
			DA200251031Bean dataBean = new DA200251031Bean();
			rs =
				stmt.executeQuery(
					"select dict_flag,dict_code,dict_name from "
						+ tableName
						+ " order by dict_order");
			while (rs.next()) {
				String flag = rs.getString(1);
				if (flag == null)
					flag = "nullareaCode";
				String key = rs.getString(2);
				if (key == null)
					key = "nullkey";
				String value = rs.getString(3);
				if (value == null)
					value = "";
				dataBean.addRow(flag, key, value);
			}
			synchronized (this) {
				CmisConstance.da200251031 = dataBean;
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
		} finally {
			closeConnection();
		}

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-4-9 11:22:55)
	 */
	private void updateDA220091006(String tableName, String tmpName)
		throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String tmpVerify = (String) CmisConstance.getPassVerify(tmpName);
			getConnByVerify(tmpName, tmpVerify);
			stmt = conn.createStatement();
			DA220091006Bean dataBean = new DA220091006Bean();
			rs =
				stmt.executeQuery(
					"select dict_type,dict_code,dict_name from "
						+ tableName
						+ " order by dict_order");
			while (rs.next()) {
				String flag = rs.getString(1);
				if (flag == null)
					flag = "nullareaCode";
				String key = rs.getString(2);
				if (key == null)
					key = "nullkey";
				String value = rs.getString(3);
				if (value == null)
					value = "";
				dataBean.addRow(flag, key, value);
			}
			synchronized (this) {
				CmisConstance.da220091006 = dataBean;
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
		} finally {
			closeConnection();
		}

	}
	public void updateDictTable(
		String tableName,
		String tmpName,
		int tableType)
		throws java.lang.Exception {

		String dMsg = "";
		try {
			if (tableType == 0) {
				dMsg =
					"tableName:"
						+ tableName
						+ ",userId:"
						+ tmpName
						+ ",tableType:0";
				updateDictType0(tableName, tmpName);
				if (tableName.trim().toLowerCase().equals("da200251031")) {
					dMsg =
						"tableName:"
							+ tableName
							+ ",userId:"
							+ tmpName
							+ ",tableType:0[handle flag model]";
					updateDA200251031(tableName, tmpName);
				}
				/*		if(tableName.trim().toLowerCase().equals("da200251012")){
							dMsg = "tableName:"+tableName+",userId:"+tmpName+",tableType:0[handle ratio model]";
							updateDA200251012(tableName,tmpName);
						}
				    */
			} else if (tableType == 1) {
				dMsg =
					"tableName:"
						+ tableName
						+ ",userId:"
						+ tmpName
						+ ",tableType:1";
				updateDictType1(tableName, tmpName);
				if (tableName.trim().toLowerCase().equals("da220091006")) {
					dMsg =
						"tableName:"
							+ tableName
							+ ",userId:"
							+ tmpName
							+ ",tableType:1[handle flag model]";
					updateDA220091006(tableName, tmpName);
				}
			} else if (tableType == 2) {
				dMsg =
					"tableName:"
						+ tableName
						+ ",userId:"
						+ tmpName
						+ ",tableType:2";
				updateDictType2(tableName, tmpName);
			} else if (tableType == 3) {
				dMsg =
					"tableName:"
						+ tableName
						+ ",userId:"
						+ tmpName
						+ ",tableType:3";
				new icbc.cmis.service.ParaDictService().initialDictTables();
			}else if (tableType == 10){
			dMsg =
				"tableName:"
					+ tableName
					+ ",userId:"
					+ tmpName
					+ ",tableType:3";
					
					updateDictType10(tableName,tmpName);				
			}else if (tableType == 11){
			dMsg =
				"tableName:"
					+ tableName
					+ ",userId:"
					+ tmpName
					+ ",tableType:3";
					
					updateDictType11(tableName,tmpName);					
			}else if (tableType == 12){
			dMsg =
				"tableName:"
					+ tableName
					+ ",userId:"
					+ tmpName
					+ ",tableType:3";
					
					updateDictType12(tableName,tmpName);					
			}

		} catch (Exception e) {
			e =
				new Exception(
					"update dict table ["
						+ dMsg
						+ "] failure!\nException Msg:"
						+ e.getMessage());
			throw e;
		} finally {
			try {
				closeConnection();
			} catch (Exception ee) {
			}
		}

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (2002-2-5 21:57:10)
	 * @param tableNa java.lang.String
	 * @param tmpName java.lang.String
	 */
	private void updateDictType0(String tableName, String tmpName)
		throws Exception {

		Statement stmt = null;
		ResultSet rs = null;
		try {
			String tmpVerify = (String) CmisConstance.getPassVerify("missign");
			getConnByVerify("missign", tmpVerify);
			stmt = conn.createStatement();
			DictBean dataBean = new DictBean();
			boolean isNull = true;
			rs =
				stmt.executeQuery(
					"select dict_code,dict_name from "
						+ tmpName
						+ "."
						+ tableName
						+ " order by DICT_ORDER");
			while (rs.next()) {
				isNull = false;
				String str1 = rs.getString(1);
				if (str1 == null)
					str1 = "isNullKey";
				String str2 = rs.getString(2);
				if (str2 == null)
					str2 = "";
				dataBean.addData(str1, str2);
			}
			dataBean.setNullMark(isNull);
			synchronized (this) {
				try {
					CmisConstance.getDictParam().remove(
						tableName.toLowerCase());
				} catch (Exception e) {
				}
				CmisConstance.getDictParam().put(
					tableName.toLowerCase(),
					dataBean);
			}
			rs.close();
			stmt.close();
			closeConnection();
		} catch (Exception e) {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}
	}

	/**
	 * 更新缓存中的字典表数据
	 * @param tableName 字典表名
	 * @param tmpName   数据表空间名
	 * @throws Exception
	 * @author zjfh-shanhy, 2007/05/23
	 */
	private void updateDictType10(String tableName, String tmpName)
		throws Exception {

			Statement stmt = null;
			ResultSet rs = null;

		try {
			

			String tmpVerify = (String) CmisConstance.getPassVerify("missign");
			getConnByVerify("missign", tmpVerify);
			stmt = conn.createStatement();

			for (java.util.Iterator itr = CmisConstance.allLang.iterator();
				itr.hasNext();
				) {

				boolean isNull = true;

				String lang_code = (String) itr.next();
				try {
					rs =
						stmt.executeQuery(
							"select dict_code,dict_name from "
								+ tmpName
								+ "."
								+ tableName
								+ " where lang_code = '"
								+ lang_code
								+ "' order by DICT_ORDER");
				} catch (SQLException sqle) {

					if (rs != null) {
						try {
							rs.close();
						} catch (Exception ez) {
						}
					}

					if (stmt != null) {
						try {
							stmt.close();
						} catch (Exception ez) {
						}
					}
					continue;

				}

				DictBean dataBean = new DictBean();
				while (rs.next()) {
					isNull = false;
					String str1 = rs.getString(1);
					if (str1 == null)
						str1 = "isNullKey";
					String str2 = rs.getString(2);
					if (str2 == null)
						str2 = "";
					dataBean.addData(str1, str2);
				}
				dataBean.setNullMark(isNull);

				synchronized (this) {
					try {
						CmisConstance.getDictParam(lang_code).remove(
							tableName.toLowerCase());
					} catch (Exception e) {
					}
					CmisConstance.getDictParam(lang_code).put(
						tableName.toLowerCase(),
						dataBean);
				}
				rs.close();
				stmt.close();

			}
			
			
		} catch (Exception e) {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ez) {
				}
			}

			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ez) {
				}
			}
			try {
				closeConnection();
			} catch (Exception ez) {
			}
			throw e;
		} finally {
			closeConnection();
		}
	}

/**
 * Insert the method's description here.
 * Creation date: (2002-2-5 21:57:10)
 * @param tableNa java.lang.String
 * @param tmpName java.lang.String
 */
private void updateDictType1(String tableName, String tmpName)
	throws Exception {

	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
		String tmpVerify = (String) CmisConstance.getPassVerify("missign");
		getConnByVerify("missign", tmpVerify);
		//			String sqlUTC = " select COLUMN_NAME from user_tab_columns where TABLE_NAME = ?";
		String sqlUTC =
			" select COLUMN_NAME from all_tab_columns where TABLE_NAME = ? and owner = ?";
		stmt = conn.prepareStatement(sqlUTC);
		stmt.setString(1, tableName.toUpperCase());
		stmt.setString(2, tmpName.toUpperCase());
		rs = stmt.executeQuery();

		StringBuffer col = new StringBuffer();
		int count = 0;
		TableBean tableBean = new TableBean();

		while (rs.next()) {
			count++;
			String colName = rs.getString(1);
			col = col.append(colName + ",");
			tableBean.addColumn(colName.toLowerCase());

		}

		rs.close();
		if (count < 1) {
			closeConnection();
			throw new Exception(
				"请确认数据表[ "
					+ tableName
					+ " ]存在或用户[ "
					+ tmpName
					+ " ]有操作该数据表的权限");
		}
		col = col.deleteCharAt(col.length() - 1);
		try {
			stmt.close();
		} catch (Exception e) {
		}
		String sqlT =
			"select " + new String(col) + " from " + tmpName + "." + tableName;
		stmt = conn.prepareStatement(sqlT);
		rs = stmt.executeQuery();
		while (rs.next()) {
			java.util.Vector row = new java.util.Vector();
			for (int h = 1; h <= count; h++) {
				String tmpStr = rs.getString(h);
				if (tmpStr == null)
					tmpStr = "";
				row.add(h - 1, tmpStr);
			}
			tableBean.addRow(row);
		}

		synchronized (this) {
			try {
				CmisConstance.getDictParam().remove(tableName.toLowerCase());
			} catch (Exception e) {
			}

			CmisConstance.getDictParam().put(
				tableName.toLowerCase(),
				tableBean);
		}
		rs.close();
		stmt.close();
		closeConnection();

	} catch (Exception e) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception ez) {
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception ez) {
			}
		}
		try {
			closeConnection();
		} catch (Exception ez) {
		}
		throw e;
	} finally {
		closeConnection();
	}
}

/**
 * 更新缓存中的字典表数据
 * @param tableName 字典表名
 * @param tmpName   数据表空间名
 * @throws Exception
 * @author zjfh-shanhy, 2007/05/23
 */
private void updateDictType11(String tableName, String tmpName)
	throws Exception {

	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
		String tmpVerify = (String) CmisConstance.getPassVerify("missign");
		getConnByVerify("missign", tmpVerify);
		//			String sqlUTC = " select COLUMN_NAME from user_tab_columns where TABLE_NAME = ?";
		String sqlUTC =
			" select COLUMN_NAME from all_tab_columns where TABLE_NAME = ? and owner = ?";
		stmt = conn.prepareStatement(sqlUTC);
		stmt.setString(1, tableName.toUpperCase());
		stmt.setString(2, tmpName.toUpperCase());
		rs = stmt.executeQuery();

		StringBuffer col = new StringBuffer();
		int count = 0;

		while (rs.next()) {
			count++;
			String colName = rs.getString(1);
			col = col.append(colName.toLowerCase() + ",");
		}

		rs.close();
		if (count < 1) {
			closeConnection();
			throw new Exception(
				"请确认数据表[ "
					+ tableName
					+ " ]存在或用户[ "
					+ tmpName
					+ " ]有操作该数据表的权限");
		}
		col = col.deleteCharAt(col.length() - 1);
		try {
			stmt.close();
		} catch (Exception e) {
		}
		
		String sqlT =
			"select " + new String(col) + " from " + tmpName + "." + tableName + " where lang_code = ?";
		stmt = conn.prepareStatement(sqlT);		
		
		for (java.util.Iterator itr = CmisConstance.allLang.iterator();
			itr.hasNext();
			) {
				
			String lang = (String)itr.next();
			
			try{
				stmt.setString(1, lang);
				rs = stmt.executeQuery();					
			}catch(SQLException e){
							
				if(rs != null){
					try{					
						rs.close();
					
					}catch(Exception e1){
				
					}					
				}
				if(stmt != null){			
					try{
						stmt.close();
					
					}catch(Exception e2){
					}						
				}

				continue;			
			}

			
			TableBean tableBean = new TableBean();
			
			//获得数据表字段名
			StringTokenizer token = new StringTokenizer(col.toString(),",");
			while(token.hasMoreTokens()){
				tableBean.addColumn(token.nextToken());				
			}
			
			//提取数据	 
			while (rs.next()) {
				java.util.Vector row = new java.util.Vector();
				for (int h = 1; h <= count; h++) {
					String tmpStr = rs.getString(h);
					if (tmpStr == null)
						tmpStr = "";
					row.add(h - 1, tmpStr);
				}
				tableBean.addRow(row);
			}

			
			synchronized (this) {
				try {
					CmisConstance.getDictParam(lang).remove(tableName.toLowerCase());
				} catch (Exception e) {
				}

				CmisConstance.getDictParam(lang).put(
					tableName.toLowerCase(),
					tableBean);
			}
			
		}
		
		rs.close();
		stmt.close();
		closeConnection();

	} catch (Exception e) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception ez) {
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception ez) {
			}
		}
		try {
			closeConnection();
		} catch (Exception ez) {
		}
		throw e;
	} finally {
		closeConnection();
	}
}

/**
 * Insert the method's description here.
 * Creation date: (2002-2-5 21:57:10)
 * @param tableNa java.lang.String
 * @param tmpName java.lang.String
 */
private void updateDictType2(String tableName, String tmpName)
	throws Exception {

	Statement stmt = null;
	ResultSet rs = null;
	try {
		String tmpVerify = (String) CmisConstance.getPassVerify("missign");
		getConnByVerify("missign", tmpVerify);
		stmt = conn.createStatement();
		SelfDefineDictBean dataBean = new SelfDefineDictBean();
		rs =
			stmt.executeQuery(
				"select area_code,dict_code,dict_name from "
					+ tmpName
					+ "."
					+ tableName
					+ " order by dict_order");
		while (rs.next()) {
			String areaCode = rs.getString(1);
			if (areaCode == null)
				areaCode = "nullareaCode";
			String key = rs.getString(2);
			if (key == null)
				key = "nullkey";
			String value = rs.getString(3);
			if (value == null)
				value = "";
			dataBean.addRow(areaCode, key, value);
		}
		synchronized (this) {
			try {
				CmisConstance.getDictParam().remove(tableName.toLowerCase());
			} catch (Exception e) {
			}
			CmisConstance.getDictParam().put(tableName.toLowerCase(), dataBean);
		}
		rs.close();
		stmt.close();
		closeConnection();
	} catch (Exception e) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception ez) {
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception ez) {
			}
		}
		try {
			closeConnection();
		} catch (Exception ez) {
		}
		throw e;
	} finally {
		closeConnection();
	}
}

/**
 * 更新缓存中的字典表数据
 * @param tableName 字典表名
 * @param tmpName   数据表空间名
 * @throws Exception
 * @author zjfh-shanhy, 2007/05/23
 */
private void updateDictType12(String tableName, String tmpName)
	throws Exception {

	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
		String tmpVerify = (String) CmisConstance.getPassVerify("missign");
		getConnByVerify("missign", tmpVerify);
		
		String sql = "select area_code,dict_code,dict_name from "
		+ tmpName
		+ "."
		+ tableName
		+ " where lang_code = ?" + " order by dict_order";
		
		stmt = conn.prepareStatement(sql);
		
		
		for (java.util.Iterator itr = CmisConstance.allLang.iterator();
					itr.hasNext();) {
						
			String lang = (String)itr.next();
			try{
				stmt.setString(1, lang);
				rs = stmt.executeQuery();					
			}catch(SQLException sqle){
				if(rs != null){
					try{
						rs.close();
					}catch(Exception e1){
						
					}
				}
				
				if(stmt != null){
					try{
						stmt.close();
					}catch(Exception e1){
						
					}					
				}
				continue;
			}

			
			SelfDefineDictBean dataBean = new SelfDefineDictBean();	
			
			while (rs.next()) {
				String areaCode = rs.getString(1);
				if (areaCode == null)
					areaCode = "nullareaCode";
				String key = rs.getString(2);
				if (key == null)
					key = "nullkey";
				String value = rs.getString(3);
				if (value == null)
					value = "";
				dataBean.addRow(areaCode, key, value);
			}	
					
			synchronized (this) {
					try {
						CmisConstance.getDictParam().remove(tableName.toLowerCase());
					} catch (Exception e) {
					}
					CmisConstance.getDictParam().put(tableName.toLowerCase(), dataBean);
				}			
		}	
	
	
		rs.close();
		stmt.close();
		closeConnection();
	} catch (Exception e) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception ez) {
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception ez) {
			}
		}
		try {
			closeConnection();
		} catch (Exception ez) {
		}
		throw e;
	} finally {
		closeConnection();
	}
}
}
