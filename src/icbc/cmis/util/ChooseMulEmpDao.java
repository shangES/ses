package icbc.cmis.util;
import java.sql.*;
import java.util.*;
import oracle.jdbc.OracleTypes;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.service.CmisDao;
import icbc.cmis.util.*;
import java.io.*;
import icbc.cmis.base.*;

public class ChooseMulEmpDao extends CmisDao {
	public ChooseMulEmpDao(CmisOperation op) {
		super(op);
	}
	public IndexedDataCollection eventQueryDao(
		String employee_code,
		String employee_name,
		String area_code,
		String sub_bank,
		String major_code,
		String class_code,
		String noneed_code)
		throws TranFailException {
		IndexedDataCollection iResult = new IndexedDataCollection();
		String sql = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String totalp = "";
		int j = 1;
		try {

			this.getConnection();
			//连接数据库
			sql =
				(String)"select distinct(a.employee_code),a.employee_name,c.area_code,c.area_name from "
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
			if (!employee_code.equals("")){
				sql += "and a.employee_code=? ";			
			}
			
			if (!noneed_code.equals("")) {
			    sql += "and a.employee_code not in ('";
			String tmp = "";
			int q1 = 0, q2 = -1;
			q1 = noneed_code.indexOf("|");
			while (q1 != -1) {
				tmp = noneed_code.substring(q2 + 1, q1);
				sql += tmp + "','";
				q2 = q1;
				q1 = noneed_code.indexOf("|", q1 + 1);
			}
			sql += noneed_code.substring(q2 + 1, noneed_code.length())
				+ "') ";
		    }
		   
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
			stmt = conn.prepareStatement(sql);
			if (!employee_code.equals(""))
				stmt.setString(j++, employee_code);
			if (!class_code.equals(""))
				stmt.setString(j++, class_code);
			if (sub_bank.equals("1")){
				stmt.setString(j++, area_code);
				stmt.setString(j++, area_code);
			}
			else stmt.setString(j++, area_code);
			rs = stmt.executeQuery();
			while (rs.next()) {
				KeyedDataCollection coll = new KeyedDataCollection();
				DataElement name1 = new DataElement();
				DataElement name2 = new DataElement();
				DataElement name3 = new DataElement();
				DataElement name4 = new DataElement();

				name1.setName("employee_code");
				name1.setValue(rs.getString(1));
				coll.addElement(name1);
				name2.setName("employee_name");
				name2.setValue(rs.getString(2));
				coll.addElement(name2);
				name3.setName("area_code");
				name3.setValue(rs.getString(3));
				coll.addElement(name3);
				name4.setName("area_name");
				name4.setValue(rs.getString(4));
				coll.addElement(name4);
				iResult.addElement(coll);
			}
		} catch (Exception ex) {
			throw new TranFailException("099999",
			//出错信息
			"ChooseMulEmpDao.eventQueryDao()", ex.getMessage() + sql, genMsg.getErrMsg("099999"));
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
}