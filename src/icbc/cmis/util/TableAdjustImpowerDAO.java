/*
 * 
 * 创建日期 2007-6-22
 *
 * @author 王强
 * 
 */
package icbc.cmis.util;
import oracle.jdbc.driver.*;
import icbc.cmis.service.CmisDao;
import icbc.cmis.operation.*;
import java.util.*;
import java.io.IOException;
import java.sql.*;

import icbc.cmis.base.TranFailException;
import icbc.cmis.base.*;
/**
 * Title:
 * Description: 业务资料改动权限的授权
 * Company:icbcsdc
 * @author：王强
 * @version 1.0
 */
public class TableAdjustImpowerDAO extends CmisDao {

	/**
	 * 构造函数
	 * @param op
	 */
	public TableAdjustImpowerDAO(CmisOperation op) {
		super(op);
	}

	/**
	 * 得到查询授权List		
	 * @return
	 * @throws TranFailException
	 */
	public Vector getQueryImpowerList(String area_code) throws TranFailException {

		String queryStr = "";
		Vector result = null;
		try {
			result = new Vector();
			while (true) {
				DBTools srv = new DBTools(this.getOperation());
				Vector param = new Vector();
				param.add(area_code);

				String dateCondition = "";
				if(area_code.equals("*")){
					queryStr =
						"select a.pa200049001,'*' ,a.pa200049002,a.pa200049003,a.pa200049004,d.area_name,a.pa200049005,c.employee_name,a.pa200049006,e.area_name"
								+ " from pa200049 a,mag_employee c,mag_area d,mag_area e"
								+ " where  a.pa200049004 = d.area_code and a.pa200049006 = e.area_code and a.pa200049005 = c.employee_code and a.pa200049001=? ";
				}else{
					
					queryStr =
						"select a.pa200049001,b.area_name,a.pa200049002,a.pa200049003,a.pa200049004,d.area_name,a.pa200049005,c.employee_name,a.pa200049006,e.area_name"
								+ " from pa200049 a,mag_area b,mag_employee c,mag_area d,mag_area e"
								+ " where a.pa200049001 = b.area_code and a.pa200049004 = d.area_code and a.pa200049006 = e.area_code and a.pa200049005 = c.employee_code and a.pa200049001=? ";
				}
				

				Vector v_select = new Vector();
				v_select.add("area_code");
				v_select.add("area_name");
				v_select.add("operationType_code");
				v_select.add("control_code");
				v_select.add("mather_area_code");
				v_select.add("mather_area_name");
				v_select.add("input_personnel_code");
				v_select.add("input_personnel_name");
				v_select.add("input_area_code");
				v_select.add("input_area_name");
				Vector v_result = srv.executeSQLResult(queryStr, param, v_select);
				//类结查询条数信息
				for (int i = 0; i < v_result.size(); i++) {
					result.add((Hashtable)v_result.get(i));
				}
				break;
			}
			return result;
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerDAO.getQueryImpowerList()", e.getMessage(), genMsg.getErrMsg("100477"));
		}
	}

	/**
	 * 删除business_modify_control表中相关信息		
	 * @return
	 * @throws TranFailException
	 */
	public boolean busiModiContDel(String areaCode, String operationType, String controlType, String matherAreaCode) throws TranFailException {

		String deleteStr = "";
		try {
			getConnection();
			java.sql.PreparedStatement pstmt = null;

			String dateCondition = "";
			deleteStr = "delete from BUSINESS_MODIFY_CONTROL where businss_type=? ";
			//if (areaCode.equals("*")) {
				deleteStr = deleteStr + " and area_code in (select area_code from mag_area start with belong_bank =? connect by prior area_code = belong_bank)";
		//	}
		//	else {
			//	deleteStr = deleteStr + " and area_code = ?";
		//	}

			if (!controlType.equals("*")) {
				deleteStr = deleteStr + " and control_type = ?";
			}

			pstmt = conn.prepareStatement(deleteStr);
			pstmt.setString(1, operationType);
			if (areaCode.equals("*")) {
				pstmt.setString(2, matherAreaCode);
			}
			else {
				pstmt.setString(2, areaCode);
			}

			if (!controlType.equals("*")) {
				pstmt.setString(3, controlType);
			}

			int rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				conn.commit();			
			}
			else {
				conn.rollback();				
			}
			pstmt.close();
			closeConnection();
			return true;
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {

			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerDAO.queryImpowerDelete()", e.getMessage(), "删除失败！");
		}
	}

	/**
	 * 删除授权		
	 * @return
	 * @throws TranFailException
	 */
	public boolean adjustImpowerDelete(String areaCode, String operationType, String controlType, String matherAreaCode) throws TranFailException {

		String deleteStr = "";

		try {
			getConnection();
			java.sql.PreparedStatement pstmt = null;

			String dateCondition = "";
			deleteStr = "delete from pa200049 a where a.pa200049001=? and  a.pa200049002=? and  a.pa200049003=?  and  a.pa200049004=?";

			deleteStr = deleteStr + dateCondition;
			pstmt = conn.prepareStatement(deleteStr);
			pstmt.setString(1, areaCode);
			pstmt.setString(2, operationType);
			pstmt.setString(3, controlType);
			pstmt.setString(4, matherAreaCode);

			int rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				conn.commit();
				pstmt.close();
				closeConnection();
			}
			else {
				conn.rollback();
				pstmt.close();
				closeConnection();
				throw new TranFailException("CMIS1001", "TableAdjustImpowerDAO.queryImpowerDelete()", "删除失败！");
			}
			return true;
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {

			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerDAO.queryImpowerDelete()", e.getMessage(), genMsg.getErrMsg("100477"));
		}
	}

	/**
	 * 是否存在记录		
	 * @return
	 * @throws TranFailException
	 */
	public int getRecordCount(String areaCode, String operationType, String controlType, String matherAreaCode) throws TranFailException {

		java.sql.PreparedStatement pstmt = null;
		String queryStr = "select count(*) from pa200049 where pa200049002= ? and pa200049004= ? ";
		if (!areaCode.equals("*")) {
			queryStr = queryStr + " and pa200049001 in (?,'*')";
		}

		if (!controlType.equals("*")) {
			queryStr = queryStr + " and pa200049003 in (?,'*')";
		}
		int count = 0;
		try {
			getConnection();

			pstmt = conn.prepareStatement(queryStr);
			pstmt.setString(1, operationType);
			pstmt.setString(2, matherAreaCode);
			int no = 3;
			if (!areaCode.equals("*")) {
				pstmt.setString(no++, areaCode);
			}

			if (!controlType.equals("*")) {
				pstmt.setString(no++, controlType);
			}

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			closeConnection();
			return count;
		}

		catch (TranFailException te) {

			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerDAO.getRecordCount()", e.getMessage(), "查询已存在记录出错");
		}
	}

	/**
	 * 删除已存在的记录		
	 * @return
	 * @throws TranFailException
	 */
	public int deleteExistRecord(String areaCode, String operationType, String controlType, String matherAreaCode) throws TranFailException {

		java.sql.PreparedStatement pstmt = null;
		String queryStr = "delete from pa200049 where pa200049002= ? and pa200049004= ? ";
		if (!areaCode.equals("*")) {
			queryStr = queryStr + " and pa200049001 =?";
		}

		if (!controlType.equals("*")) {
			queryStr = queryStr + " and pa200049003 =?";
		}

		try {
			getConnection();

			pstmt = conn.prepareStatement(queryStr);
			pstmt.setString(1, operationType);
			pstmt.setString(2, matherAreaCode);
			int no = 3;
			if (!areaCode.equals("*")) {
				pstmt.setString(no++, areaCode);
			}

			if (!controlType.equals("*")) {
				pstmt.setString(no++, controlType);
			}

			int rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				conn.commit();				
			}			
			pstmt.close();
			closeConnection();		
			return rowCount;
		}

		catch (TranFailException te) {

			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerDAO.deleteExistRecord()", e.getMessage(), "删除已存在记录出错");
		}
	}

	/**
	 * 保存授权新增记录		
	 * @return
	 * @throws TranFailException
	 */
	public boolean adjustImpowerAdd(
		String areaCode,
		String operationType,
		String controlType,
		String matherAreaCode,
		String input_personnel_code,
		String input_area_code)
		throws TranFailException {

		java.sql.PreparedStatement pstmt = null;
		String inStr = "";
		int rowCount = 0;
		try {
			getConnection();

			inStr =
				"insert into pa200049(pa200049001,pa200049002,pa200049003,pa200049004,pa200049005,pa200049006,pa200049007)"
					+ " values(?,?,?,?,?,?,to_char(sysdate,'yyyymmdd'))";
			pstmt = conn.prepareStatement(inStr);
			pstmt.setString(1, areaCode);
			pstmt.setString(2, operationType);
			pstmt.setString(3, controlType);
			pstmt.setString(4, matherAreaCode);
			pstmt.setString(5, input_personnel_code);
			pstmt.setString(6, input_area_code);

			rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				conn.commit();
				pstmt.close();
				closeConnection();
			}
			else {
				conn.rollback();
				pstmt.close();
				closeConnection();
				throw new TranFailException("CMIS1001", "TableAdjustImpowerDAO.queryImpowerAdd()", "新增失败！");
			}
			return true;
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerDAO.getRecordCount()", e.getMessage(), "新增失败！");
		}
	}

	/**
	 * 修改授权记录		
	 * @return
	 * @throws TranFailException
	 */
	public boolean adjustImpowerUpdate(
		String areaCode,
		String newOperationType,
		String newControlType,
		String oldOperationType,
		String oldControlType,
		String matherAreaCode,
		String input_personnel_code,
		String input_area_code)
		throws TranFailException {

		java.sql.PreparedStatement pstmt = null;
		String inStr = "";
		int rowCount = 0;
		try {
			getConnection();

			inStr =
				"update pa200049 set pa200049002=?,pa200049003=?,pa200049005=?,pa200049006=?,pa200049007=to_char(sysdate,'yyyymmdd') "
					+ " where pa200049001=? and pa200049004=? and pa200049002=? and pa200049003=?";
			pstmt = conn.prepareStatement(inStr);
			pstmt.setString(1, newOperationType);
			pstmt.setString(2, newControlType);
			pstmt.setString(3, input_personnel_code);
			pstmt.setString(4, input_area_code);
			pstmt.setString(5, areaCode);
			pstmt.setString(6, matherAreaCode);
			pstmt.setString(7, oldOperationType);
			pstmt.setString(8, oldControlType);

			rowCount = pstmt.executeUpdate();
			if (rowCount != 0) {
				conn.commit();
				pstmt.close();
				closeConnection();
			}
			else {
				conn.rollback();
				pstmt.close();
				closeConnection();
				throw new TranFailException("CMIS1001", "TableAdjustImpowerDAO.queryImpowerAdd()", "修改失败！");
			}
			return true;
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerDAO.getRecordCount()", e.getMessage(), "修改失败！");
		}
	}

	// 获得上级行信息
	public String getPriorBank(String areaCode) throws TranFailException {
		String ret = "";
		java.sql.PreparedStatement pstmt = null;

		try {
			//获得数据库连接
			getConnection();
			String query = "select a.area_code,a.area_name from mag_area a,mag_area b where a.area_code = b.belong_bank and b.area_code = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, areaCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				ret = rs.getString("area_code") + "|" + rs.getString("area_name");
			}
			rs.close();
			pstmt.close();
			closeConnection();
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerDAO.getPriorBank()", e.getMessage(), "查找当前授权行的上级行出错");
		}
		return ret;
	}
}