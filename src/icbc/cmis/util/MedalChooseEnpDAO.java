package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.CmisConstance;
import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import icbc.missign.Employee;
import icbc.cmis.util.QueryNormalEnp;

/*
 * 
 * update by wz 20060220 对于选客户公共模块中的seleClient参数作一下调整
 //update by wz 20060720  可以选择到授信审批行是当前柜员所在行的集团
 */ 

public class MedalChooseEnpDAO extends CmisDao {
	//final static int QUERY_LIMIT = 200;

	public MedalChooseEnpDAO(icbc.cmis.operation.CmisOperation op) {
		super(op);
	}

	public String[] genSQL(
		String queryType,
		Employee employee,
		String TA200011001,
		String TA200011003,
		String TA200011005,
		String TA200011010,
		String TA200011011,
		String TA200011012,
		String TA200011014,
		String TA200011016,
		String TA200011031,
		String generalType,
		Hashtable paras)
		throws TranFailException {
		String sql[] = { "", "" };
		String where = "";
		String where2 = "";

		if (TA200011001.length() > 0) {
			where += (" and TA200011001 = '" + TA200011001 + "'");
		}
		if (TA200011003.length() > 0) {
			where += (" and TA200011003 like '%" + TA200011003 + "%'");
		}
		if (TA200011005.length() > 0) {
			where += (" and TA200011005 like '" + TA200011005 + "%'");
		}
		if (TA200011010.length() > 0) {
			where += (" and TA200011010 = '" + TA200011010 + "'");
		}
		if (TA200011011.length() > 0) {
			where += (" and TA200011011 = '" + TA200011011 + "'");
		}
		if (TA200011012.length() > 0) {
			where += (" and TA200011012 = '" + TA200011012 + "'");
		}
		if (TA200011014.length() > 0) {
			where
				+= (" and TA200011014 like '"
					+ TA200011014.substring(0, 1)
					+ "%'");
		}
		if (TA200011016.length() > 0) {
			where += (" and TA200011016 = '" + TA200011016 + "'");
		}
		if (TA200011031.length() > 0) {
			where += (" and TA200011031 = '" + TA200011031 + "'");
		}
		if (generalType.equals("0")) {
			where += (" and (TA200011090 = 10 or TA200011090 is null)"); // me
		}

		//根据情况加where条件

		if (queryType.equals("QueryNormalEnp")) {
			int eclass =
				Integer.valueOf(employee.getEmployeeClass()).intValue();
			String ecode = employee.getEmployeeCode();
			int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
			String area = employee.getMdbSID();
			switch (bankFlag) {
				case 0 :
					break;
				case 4 :
					where2
						+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '"
							+ area
							+ "')");
					//根据柜员加where
					if (eclass == 8) {
						where2
							+= (" and TA200011001 in ((select ta200012001 from ta200012 where ta200012003 = '"
								+ ecode
								+ "') union all (select ta200012001 from ta200012 where ta200012006 = '"
								+ ecode
								+ "'))");
					}
					break;
				default :
					where2
						+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '"
							+ area
							+ "' connect by belong_bank = prior area_code))");
					break;
			}
			//输入已完成
			where2 += (" and TA200011059 = '1'");
			if (where2.length() > 4) {
				where2 = where2.substring(4);

				//                  where2 = genQueryNormal(employee,paras);
			}
		} else if (queryType.equals("QueryNormalAllEnp")) {
			//根据行级别加where
			int eclass =
				Integer.valueOf(employee.getEmployeeClass()).intValue();
			String ecode = employee.getEmployeeCode();
			int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
			String area = employee.getMdbSID();
			switch (bankFlag) {
				case 0 :
					break;
				case 4 :
					where2
						+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '"
							+ area
							+ "')");
					//根据柜员加where2
					if (eclass == 8) {
						where2
							+= (" and TA200011001 in ((select ta200012001 from ta200012 where ta200012003 = '"
								+ ecode
								+ "') union all ( select ta200012001 from ta200012 where ta200012006 = '"
								+ ecode
								+ "'))");
					}
					break;
				default :
					where2
						+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '"
							+ area
							+ "' connect by belong_bank = prior area_code))");
					break;
			}
			if (where2.length() > 4) {
				where2 = where2.substring(4);
			}
		} else if (queryType.equals("QueryNormalEnpByArea")) {
			String area = (String) paras.get("areacode");
			if (area == null || area.equals("")) {
				area = employee.getMdbSID();
			}
			//根据行级别加where
			int eclass =
				Integer.valueOf(employee.getEmployeeClass()).intValue();
			String ecode = employee.getEmployeeCode();
			int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
			switch (bankFlag) {
				case 0 :
					break;
				case 4 :
					where2
						+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '"
							+ area
							+ "')");
					//根据柜员加where
					if (eclass == 8) {
						where2
							+= (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = '"
								+ ecode
								+ "' union all select ta200012001 from ta200012 where ta200012006 = '"
								+ ecode
								+ "')");
					}
					break;
				default :
					where2
						+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '"
							+ area
							+ "' connect by belong_bank = prior area_code))");
					break;
			}
			//输入已完成
			where2 += (" and TA200011059 = '1' and TA200011083 = 1");
			if (where2.length() > 4) {
				where2 = where2.substring(4);
			}
		} else if (queryType.equals("QueryMangerEnp")) {
			String area = (String) paras.get("area");
			if (area == null) {
				area = employee.getMdbSID();
			}
			where2 =
				" ta200011059 = '1' and TA200011083 = '1' and ta200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '"
					+ area
					+ "' and ta20001L003 = 1)";
		} else if (queryType.equals("QueryMangerAllEnp")) {
			String area = (String) paras.get("area");
			if (area == null) {
				area = employee.getMdbSID();
			}
			where2 =
				" ta200011059 in ('1','4') and TA200011083 = '1' and ta200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '"
					+ area
					+ "' and ta20001L003 = 1)";
		} else if (queryType.equals("QueryNewCustomerManger")) {
			String area = (String) paras.get("area");
			if (area == null) {
				area = employee.getMdbSID();
			}
			where2 =
				" ta200011059 in ('1','4') and TA200011083 = '1' and ta200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '"
					+ area
					+ "' and ta20001L003 = 1)";
			//客户状态 1建立信贷关系 2新增 3销户 4整合
			String TA200011059 = (String) paras.get("TA200011059");
			if (TA200011059 != null
				&& TA200011059.length() > 0
				&& !TA200011059.equals("5")) {
				where2 += (" and TA200011059 = " + TA200011059);
			}
			//完成标志 0 未完成 1 完成
			String TA200011083 = (String) paras.get("TA200011083");
			if (TA200011083 != null
				&& TA200011083.length() > 0
				&& !TA200011083.equals("2")) {
				where2 += (" and TA200011083 = " + TA200011083);
			}
			if (where2.length() > 4) {
				where2 = where2.substring(4);
			}
		} else if (queryType.equals("QueryNewEnp")) {
			int eclass =
				Integer.valueOf(employee.getEmployeeClass()).intValue();
			String ecode = employee.getEmployeeCode();
			int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
			String area = employee.getMdbSID();
			switch (bankFlag) {
				case 0 :
					break;
				case 4 :
					where2
						+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '"
							+ area
							+ "')");
					//根据柜员加where
					if (eclass == 8) {
						where2
							+= (" and TA200011001 in ((select ta200012001 from ta200012 where ta200012003 = '"
								+ ecode
								+ "') union all ( select ta200012001 from ta200012 where ta200012006 = '"
								+ ecode
								+ "'))");
					}
					break;
				default :
					where2
						+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '"
							+ area
							+ "' connect by belong_bank = prior area_code))");
					break;
			}
			if (where2.length() > 4) {
				where2 = where2.substring(4);
			}
			where2
				+= (" and TA200011063 = '"
					+ area
					+ "' and TA200011059 <> '3' and  TA200011083 = 0 ");
		} else if (queryType.equals("QueryAreaEnp")) {
			String area = (String) paras.get("area");
			if (area == null) {
				area = employee.getMdbSID();
			}
			where2 =
				" ta200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '"
					+ area
					+ "')";
		} else if (queryType.equals("MedalQueryNormalEnp")) {
			int eclass =
				Integer.valueOf(employee.getEmployeeClass()).intValue();
			String ecode = employee.getEmployeeCode();
			int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
			String area = employee.getMdbSID();

			String operType = employee.getEmployeeClass();
			String seleClient = (String) paras.get("seleClient");
			/*
			 * 对于选客户公共模块中的seleClient参数作一下调整
			    seleClient=0:
			           选择辖内+共享
			    seleClient=1:
			           选择授信专管员是该柜员
			    seleClient=2:       
			           选择辖内.如果该客户所属集团的授信审批行为本行的，则操作员可以选择到该客户(update by wz 20060606)
			           
			 * 
			 * update by wz 20060220
			 */
			if (seleClient != null && seleClient.equals("1")) {
				where2 =
					" and ta200011001 in (select ta200012001 from ta200012 where ta200012003 = '"
						+ ecode
						+ "' and TA200012005='3')";
			} else if ((seleClient != null && seleClient.equals("0"))) {
				switch (bankFlag) {
					case 0 :
						break;
					case 4 :
						where2
							+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '"
								+ area
								+ "')");
						break;
					default :
						where2
							+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '"
								+ area
								+ "' connect by belong_bank = prior area_code))");
						break;
				}
			} else {
				switch (bankFlag) {
					case 0 :
						break;
					case 4 :
						where2
							+= (" and ( TA200011063  = '"
								+ area
								+ "'  or ( ta200011001 in (select ta340002001 from ta340002 where ta340002002 in(select ta340001001 from ta340001 where ta340001012='"
								+ area
								+ "'))) ) ");
						break;
					default :
						where2
							+= (" and ( TA200011063  in (select area_code from mag_area start with area_code = '"
								+ area
								+ "' connect by belong_bank = prior area_code ) or ( ta200011001 in (select ta340002001 from ta340002 where ta340002002 in(select ta340001001 from ta340001 where ta340001012='"
								+ area
								+ "'))) )");
						break;
				}
			}

			//where2 = " and ( ( "+where2+" ) or (ta200011001 in (select ta200012001 from ta200012 where ta200012003 = '" + ecode + "' and TA200012005='3') ) )";
			//where2 += (" and TA200011059 = '1' and TA200011083 = 1");
			where2 += (" and TA200011059 = '1' and TA200011083 = 1");
			if (where2.length() > 4) {
				where2 = where2.substring(4);

			}
		} else { //JXO
			where2 = genQueryNormal(employee, paras);
		}

		if (where2.startsWith("and ")) {
			where2 = where2.substring(4);
		}
		if (where2.startsWith(" and ")) {
			where2 = where2.substring(5);
		}
		if (where.length() > 0) {
			where2 = where.substring(4) + " and " + where2;
		}

		sql[0] = "select count(*) from ta200011 where " + where2;
		sql[1] =
			"SELECT * FROM ( SELECT a.*, ROWNUM rnum FROM ("
				+ "select TA200011001, TA200011003, TA200011005, TA200011037, TA200011090 FROM ta200011 "
				+ " where "
				+ where2
				+ ") a  WHERE ";
		return sql;
	}

	private String genQueryNormal(Employee employee, Hashtable paras)
		throws TranFailException {
		String where = "";
		//根据行级别加where
		int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
		String ecode = employee.getEmployeeCode();
		int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
		String area = employee.getMdbSID();
		switch (bankFlag) {
			case 0 :
				break;
			case 4 :
				where
					+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '"
						+ area
						+ "')");
				//根据柜员加where
				if (eclass == 8) {
					where
						+= (" and TA200011001 in ((select ta200012001 from ta200012 where ta200012003 = '"
							+ ecode
							+ "') union all (select ta200012001 from ta200012 where ta200012006 = '"
							+ ecode
							+ "'))");
				}
				break;
			default :
				where
					+= (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '"
						+ area
						+ "' connect by belong_bank = prior area_code))");
				break;
		}
		//输入已完成
		where += (" and TA200011059 = '1' and TA200011083 = 1");
		if (where.length() > 4) {
			where = where.substring(4);
		}
		return where;
	}

	public int getRecordCount(String sql) throws TranFailException {
		Statement stmt = null;
		ResultSet rs = null;
		int ret = 0;
		try {
			this.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			ret = rs.getInt(1);
		} catch (TranFailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TranFailException(
				"cmisutil214",
				"icbc.cmis.util.ChooseEnp3DAO",
				ex.getMessage() + sql,
				"查询记录条数错误！");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			};
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ex) {
				}
			};
			this.closeConnection();
		}
		return ret;
	}

	public Vector query(String sql, int begin, int end)
		throws TranFailException {
		Vector ret = new Vector();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			this.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			int i = 0;
			while (rs.next()) {
				String[] row =
					{
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6)};
				ret.add(row);
				i++;
			}
		} catch (TranFailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TranFailException(
				"cmisutil215",
				"icbc.cmis.util.ChooseEnp3DAO",
				ex.getMessage() + sql,
				"产生客户列表错误！");
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception ex) {
				}
			};
			if (stmt != null) {
				try {
					stmt.close();
				} catch (Exception ex) {
				}
			};
			this.closeConnection();
		}
		return ret;
	}

	public String[] comSQL(
		String queryType,
		Employee employee,
		String TA340003001,
		String TA340003002,
		String TA340003003,
		String areaType,
		String area_code,
		String bank_flag,
		String generalType,
		Hashtable paras)
		throws TranFailException {
		String sql[] = { "", "" };
		String where = "";
		String where2 = "";

		String area = employee.getMdbSID();
		if (TA340003001.length() > 0) {
			where += ("and TA340003001 like '%" + TA340003001 + "%'");
		}
		if (TA340003002.length() > 0) {
			where += (" and TA340003002 like '%" + TA340003002 + "%'");
		}
		if (TA340003003.length() > 0) {
			where += (" and TA340003003 like '" + TA340003003 + "%'");
		}
		//lxm改于20050908
		//判断是查询授信集团还是查询所有集团
		if (generalType.equals("0")) {
			//查询授信集团
			where += " and TA340003009 = '1'";
		}

		//查询授信集团，地区为授信关联管理行
		//update by wz 20060720  可以选择到授信审批行是当前柜员所在行的集团
		if (generalType.equals("0")) {
			if (areaType.equals("1")) {
				if (area_code.length() == 0) {
					where
						+= "and TA340003004 in (select area_code from mag_area start with area_code='00000000' connect by belong_bank = prior area_code)";
				} else {
					where
						+= "and TA340003004 in (select area_code from mag_area start with area_code='"
						+ area_code
						+ "' connect by belong_bank = prior area_code)";
				}
			} else {
				where
					+= "and (TA340003004 in (select area_code from mag_area start with area_code='"
					+ area
					+ "' connect by belong_bank = prior area_code) or  TA340003012 ='"+area+"' )";
			}

		}
		//查询全部集团，地区为关联管理行
		else {

			if (areaType.equals("1")) {
				if (area_code.length() == 0) {
					where
						+= "and TA340003008 in (select area_code from mag_area start with area_code='00000000' connect by belong_bank = prior area_code)";
				} else {
					where
						+= "and TA340003008 in (select area_code from mag_area start with area_code='"
						+ area_code
						+ "' connect by belong_bank = prior area_code)";
				}
			} else {
				where
					+= "and TA340003008 in (select area_code from mag_area start with area_code='"
					+ area
					+ "' connect by belong_bank = prior area_code)";
			}

		}

		if (where.length() > 0) {
			where2 = where.substring(4);
		}
		//lxm改于20050908
		//增加两个字段的查询：TA340003007 集团关联标志
		sql[0] = "select count(*) from TA340003 where " + where2;
		sql[1] =
			"select * from (select TA340003001,TA340003002,TA340003003,TA340003004,TA340003007,rownum rnum from TA340003 WHERE "
				+ where2
				+ "and ";
		return sql;
	}
}
