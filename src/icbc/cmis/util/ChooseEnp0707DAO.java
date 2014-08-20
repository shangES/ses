package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.CmisConstance;

import java.io.IOException;
import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import icbc.missign.Employee;


/**
 * 主机客户合并明细查询模块 200707新增
 * @author ZhangDM
 */
public class ChooseEnp0707DAO extends CmisDao {
	
	public ChooseEnp0707DAO(icbc.cmis.operation.CmisOperation op) {
		super(op);
	}

	public String[] genSQL(Employee employee,String TA200011001) throws TranFailException {	
		String sql[] = {"", ""};
		String areaCode = employee.getMdbSID();			
		sql[0]="select count(*) FROM  ta20001c a,ta200011 b " +			   "where a.TA20001c001 = b.ta200011001 " +			   "and a.TA20001C002 in (select area_code from mag_area start with area_code ='"+areaCode+"' connect by prior area_code = belong_bank) and (a.ta20001c001='"+TA200011001+"' or a.ta20001c005='"+TA200011001+"')";
		sql[1]="select * from (select (select ta200011001 from ta200011 " +			   "where ta200011001='"+TA200011001+"' or ta200011005='"+TA200011001+"') v_ta20001c001,TA200011003, TA200011005, TA200011037 ,rownum rnum " +			   "FROM  ta20001c a,ta200011 b " +			   "where b.ta200011001='"+TA200011001+"' and  a.TA20001c001 = b.ta200011001 " +			   "and (a.TA20001C002 in (select area_code from mag_area start with area_code ='"+areaCode+"' connect by prior area_code = belong_bank) " +			   "or a.TA20001C006 in (select area_code from mag_area start with area_code ='"+areaCode+"' connect by prior area_code = belong_bank)) and ";
		return sql;
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
				"icbc.cmis.util.ChooseEnp0707DAO",
				ex.getMessage() + sql,
				"查询记录条数错误！");
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
		return ret;
	}

	public Vector query(String sql, int begin, int end)throws TranFailException {
		Vector ret = new Vector();		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			this.getConnection();			
			stmt = conn.createStatement();			
			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				String[] row ={rs.getString(1),
						       rs.getString(2),
						       rs.getString(3),
						       rs.getString(4),
						       rs.getString(5)};				
				ret.add(row);
				i++;
			}
		} catch (TranFailException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TranFailException(
				"cmisutil215",
				"icbc.cmis.util.ChooseEnp0707DAO",
				ex.getMessage() + sql,
				"产生客户列表错误！");
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
		return ret;
	}	
}