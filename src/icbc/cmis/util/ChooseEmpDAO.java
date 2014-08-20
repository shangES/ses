package icbc.cmis.util;
import java.sql.*;
import java.util.*;
import oracle.jdbc.OracleTypes;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.service.CmisDao;
import icbc.cmis.util.*;
import java.io.*;
import icbc.cmis.base.*;

public class ChooseEmpDAO extends CmisDao {
	public ChooseEmpDAO(CmisOperation op) {
		super(op);
	}
	public IndexedDataCollection eventQueryDao(
		String employee_code,
		String employee_name,
		String area_code,
		String sub_bank,
		String major_code,
		String class_code,
		String beginPos,
		String subemployee)
		throws TranFailException {
		IndexedDataCollection iResult = new IndexedDataCollection();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String totalp = "";
		int j = 1;
		try {
			totalp =
				allcount(
					employee_code,
					employee_name,
					area_code,
					sub_bank,
					major_code,
					class_code,
					subemployee);

			this.getConnection();
			//连接数据库
			sql =
				(String) "select * from ( SELECT ROWNUM rnum,d.* FROM ("
					+ "select distinct(a.employee_code),a.employee_name,c.area_code,c.area_name from "
					+ "mag_employee a,mag_employee_major b,mag_area c "
					+ "where a.employee_delete_flag='0' and a.employee_code=b.employee_code "
					+ "and b.employee_area=c.area_code ";
			if (!major_code.equals("")) {
				sql += "and b.employee_major in ('";
				String tmp = "";
				int q1 = 0, q2 = -1;
				q1 = major_code.indexOf("|");
				while (q1 != -1) {
					tmp = major_code.substring(q2 + 1, q1);
					sql += tmp + "','";
					q2 = q1;
					q1 = major_code.indexOf("|", q1 + 1);
				}
				sql += major_code.substring(q2 + 1, major_code.length())
					+ "') ";
			}
			if (!employee_code.equals(""))
				sql += "and a.employee_code=? ";
			if (!employee_name.equals("")) {
				String tmp = "%" + employee_name + "%";
				sql += "and a.employee_name like '" + tmp + "' ";
			}
			if (!class_code.equals(""))
				sql += "and employee_class=? ";
			if (sub_bank.equals("1"))
				sql
					+= " and c.area_code in (select area_code from mag_area where area_code = ? or belong_bank=?) ";
			else
				sql += " and c.area_code=? ";
			if(!subemployee.equals(""))
			sql+=" and a.employee_code!=?";
			sql += ") d WHERE ROWNUM <= ?) WHERE rnum >= ?";
			stmt = conn.prepareStatement(sql);
			if (!employee_code.equals(""))
				stmt.setString(j++, employee_code);
			if (!class_code.equals(""))
				stmt.setString(j++, class_code);
			if (sub_bank.equals("1")) {
				stmt.setString(j++, area_code);
				stmt.setString(j++, area_code);
			} else
				stmt.setString(j++, area_code);
			if (!subemployee.equals(""))
						stmt.setString(j++,subemployee);
			stmt.setString(j++, String.valueOf(Integer.parseInt(beginPos) + 9));
			stmt.setString(j, beginPos);
			rs = stmt.executeQuery();
			while (rs.next()) {
				KeyedDataCollection coll = new KeyedDataCollection();
				DataElement name1 = new DataElement();
				DataElement name2 = new DataElement();
				DataElement name3 = new DataElement();
				DataElement name4 = new DataElement();
				DataElement name5 = new DataElement();
				DataElement name6 = new DataElement();

				name1.setName("employee_code");
				name1.setValue(rs.getString(2));
				coll.addElement(name1);
				name2.setName("employee_name");
				name2.setValue(rs.getString(3));
				coll.addElement(name2);
				name3.setName("area_code");
				name3.setValue(rs.getString(4));
				coll.addElement(name3);
				name4.setName("area_name");
				name4.setValue(rs.getString(5));
				coll.addElement(name4);
				name5.setName("allcount");
				name5.setValue(totalp);
				coll.addElement(name5);
				name6.setName("beginPos");
				name6.setValue(beginPos);
				coll.addElement(name6);
				iResult.addElement(coll);
			}
		} catch (Exception ex) {
			throw new TranFailException("099999",
			//错误号
			"icbc.cmis.util.ChooseEmpDAO.eventQueryDao()");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ex) {
				};
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception ex) {
				};
			this.closeConnection();
		}
		return iResult;
	}

	public String allcount(
		String employee_code,
		String employee_name,
		String area_code,
		String sub_bank,
		String major_code,
		String class_code,
		String subemployee)
		throws Exception {
		IndexedDataCollection iResult = new IndexedDataCollection();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int j = 1;
		try {
			this.getConnection();
			//连接数据库
			sql =
				(String) "select count(distinct(a.employee_code)) from mag_employee a,mag_employee_major b,mag_area c "
					+ "where a.employee_delete_flag='0' and a.employee_code=b.employee_code "
					+ "and b.employee_area=c.area_code ";
			if (!major_code.equals("")) {
				sql += "and b.employee_major in ('";
				String tmp = "";
				int q1 = 0, q2 = -1;
				q1 = major_code.indexOf("|");
				while (q1 != -1) {
					tmp = major_code.substring(q2 + 1, q1);
					sql += tmp + "','";
					q2 = q1;
					q1 = major_code.indexOf("|", q1 + 1);
				}
				sql += major_code.substring(q2 + 1, major_code.length())
					+ "') ";
			}
			if (!employee_code.equals(""))
				sql += "and a.employee_code=? ";
			if (!employee_name.equals("")) {
				String tmp = "%" + employee_name + "%";
				sql += "and a.employee_name like '" + tmp + "' ";
			}
			if (!class_code.equals(""))
				sql += "and employee_class=? ";
			if (sub_bank.equals("1"))
				sql
					+= " and c.area_code in (select area_code from mag_area where area_code = ? or belong_bank=?) ";
			else
				sql += " and c.area_code=? ";
			if(!subemployee.equals(""))
			sql+=" and a.employee_code!=?";
			stmt = conn.prepareStatement(sql);
			//if (!major_code.equals(""))
			//	stmt.setString(j++, major_code);
			if (!employee_code.equals(""))
				stmt.setString(j++, employee_code);
			if (!class_code.equals(""))
				stmt.setString(j++, class_code);
			if (sub_bank.equals("1")) {
				stmt.setString(j++, area_code);
				stmt.setString(j++, area_code);
			} else
				stmt.setString(j++, area_code);
			if (!subemployee.equals(""))
			stmt.setString(j++,subemployee);
			rs = stmt.executeQuery();
			if (rs.next()) {
				if (rs.getString(1).equals("")) {
					return "0";
				} else {
					return rs.getString(1);
				}
			} else {
				return "0";
			}
		} catch (Exception ex) {
			throw new TranFailException("099999",
			//错误号
			"icbc.cmis.util.ChooseEmpDAO.allcount()");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception ex) {
				};
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception ex) {
				};
			this.closeConnection();
		}
	}
}