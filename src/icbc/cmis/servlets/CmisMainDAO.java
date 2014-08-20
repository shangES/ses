package icbc.cmis.servlets;

import icbc.cmis.service.CmisDao;
import java.util.Vector;
import icbc.missign.Employee;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.TableBean;
import java.sql.*;
import java.util.*;
import oracle.jdbc.*;
import icbc.cmis.base.*;

public class CmisMainDAO extends CmisDao {
  icbc.cmis.tags.PropertyResourceReader propertyResourceReader;
  public CmisMainDAO(icbc.cmis.operation.CmisOperation op) {
    super(op);
  }
  String langCode = "zh_CN";
  /**
   * 查询mprocess表，显示当前用户的待处理记录
   * @param employee 当前柜员
   * @return 待处理记录
   * @throws TranFailException
   */
  public Vector getProcess(Employee employee) throws TranFailException {
    Vector ret = new Vector();
    String sql = "select query_func_name from alert_function where app_code=? order by query_func_name";
    ResultSet rs = null;
    ResultSet rs2 = null;
    ResultSetMetaData md = null;
    PreparedStatement pstmt = null;
    CallableStatement stmt_call = null;
    String callableFunction = null;
    try {
      //String userId = (String)CmisConstance.getParameterSettings().get("dbUserName");
      //String userPass = (String)CmisConstance.getParameterSettings().get("dbUserPass");
      langCode = (String)getOperation().getSessionData("LangCode");
      Hashtable hTable = (Hashtable)CmisConstance.getDictParam();
      TableBean sys_major = (TableBean)hTable.get("mag_sys_major");
      String sysCode = sys_major.getNameByCode("sys_code", "major_code", employee.getEmployeeMajor());
      //System.out.println("sysCode="+sysCode+ " major_code="+employee.getEmployeeMajor());
      //this.getConnection(userId,userPass);
      this.getConnection("missign");
      //System.out.println("missign connection ok");
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, sysCode);
      rs2 = pstmt.executeQuery();
      if (!rs2.next())
        //throw new TranFailException("cmismain003", "icbc.cmis.servlets.CmisMainDAO", "无对应的业务存储过程", "无对应的业务存储过程");
        throw new TranFailException(
          "099993",
          "icbc.cmis.servlets.CmisMainDAO",
          genMsg.getErrMsgByLang((String)getOperation().getSessionData("LangCode"), "099993"),
          genMsg.getErrMsgByLang((String)getOperation().getSessionData("LangCode"), "099993"));
      //System.out.println("query alert_function ok");
      do {
        callableFunction = rs2.getString(1);
        //System.out.println("call "+ callableFunction + " begin");
        stmt_call = conn.prepareCall("{? = call " + callableFunction + "(?,?,?,?)}");
        stmt_call.registerOutParameter(1, OracleTypes.CURSOR);
        stmt_call.setString(2, employee.getEmployeeMajor());
        stmt_call.setString(3, employee.getEmployeeClass());
        stmt_call.setString(4, employee.getMdbSID());
        stmt_call.setString(5, employee.getEmployeeCode());
        stmt_call.execute();

        rs = (ResultSet)stmt_call.getObject(1);
        md = rs.getMetaData();
        int nColumns = md.getColumnCount();
        propertyResourceReader = new icbc.cmis.tags.PropertyResourceReader(langCode, "icbc.cmis.util.ytdk_frames");
        while (rs.next()) {
          //if(rs.getInt(3) == 0) continue;
          String[] ts = new String[nColumns];
          for (int i = 0; i < nColumns; i++) {

            if (rs.getString(i + 1) == null)
              ts[i] = null;
            else
              ts[i] = rs.getString(i + 1).trim();
           if (ts[i] != null){
			if (ts[i].indexOf("C000") != -1) {

			ts[i] = propertyResourceReader.getPrivateStr(ts[i]);
				}
           }
            
          }
          ret.add(ts);
        }
        rs.close();

      }
      while (rs2.next());

    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("cmismain003", "icbc.cmis.servlets.CmisMainDAO", ex.getMessage(), ex.getMessage());
    }
    finally {
      if (rs2 != null)
        try {
          rs2.close();
        }
        catch (Exception ex) {}
      if (pstmt != null)
        try {
          pstmt.close();
        }
        catch (Exception ex) {}
      if (rs != null)
        try {
          rs.close();
        }
        catch (Exception ex) {}
      if (stmt_call != null)
        try {
          stmt_call.close();
        }
        catch (Exception ex) {}
      this.closeConnection();
    }

    return ret;
  }
  /**
   * 查询mprocess表，显示当前用户的待处理记录
   * @param employee 当前柜员
   * @return 待处理记录
   * @throws TranFailException
   */
  public String getBatchDate(Employee employee) throws TranFailException {
    StringBuffer ret = new StringBuffer();
    String sql = "";
    Statement stmt = null;
    ResultSet rs = null;
    CallableStatement stmt_call = null;
    try {
      this.getConnection();
      stmt_call = conn.prepareCall("{? = call get_batch_date(?)}");
      stmt_call.registerOutParameter(1, OracleTypes.VARCHAR);
      stmt_call.setString(2, employee.getMdbSID().substring(0, 4));
      stmt_call.execute();
      String batchDate = stmt_call.getString(1);
      if (batchDate != null) {
        ret.append(batchDate);
        ret.insert(4, '-');
        ret.insert(7, '-');
      }
      /*
            sql = "select max(dated) from mag_batch where flag='1' and area = '"+ employee.getMdbSID().substring(0,4)+"'";
            this.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
              if(rs.getString(1) != null) {
                ret.append(rs.getString(1));
                ret.insert(4,'-');
                ret.insert(7,'-');
              }
            }*/

    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("cmismain004", "icbc.cmis.servlets.CmisMainDAO", ex.getMessage(), ex.getMessage());
    }
    finally {
      if (rs != null)
        try {
          rs.close();
        }
        catch (Exception ex) {}
      if (stmt != null)
        try {
          stmt.close();
        }
        catch (Exception ex) {}
      this.closeConnection();
    }
    return ret.toString();
  }

  public int checkFunc(String func, String menuAreaCode, String bankFlag, String major, String cls, String employeeCode, String areaCode)
    throws TranFailException {
    String sql =
      "select count(*) from mag_application_new where app_major_code = ? and area_code = ? and app_module_code = ? and substr(app_class,(?+1),1)='1' and substr(app_privilege,?,1)='1' and rownum=1";
    ResultSet rs = null;
    PreparedStatement pstmt = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs2 = null;
    String sql2 =
      "select employee_func,employee_spfunc from mag_employee_major where employee_code=? and employee_major=? and employee_area=? and employee_class=?";
    try {
      this.getConnection("missign");
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, major);
      pstmt.setString(2, menuAreaCode);
      pstmt.setString(3, func);
      pstmt.setInt(4, Integer.parseInt(bankFlag));
      pstmt.setInt(5, Integer.parseInt(cls));

      rs = pstmt.executeQuery();

      pstmt2 = conn.prepareStatement(sql2);
      pstmt2.setString(1, employeeCode);
      pstmt2.setString(2, major);
      pstmt2.setString(3, areaCode);
      pstmt2.setString(4, cls);

      rs2 = pstmt2.executeQuery();
      rs2.next();
      String employee_func = rs2.getString(1) == null ? "" : rs2.getString(1);
      String employee_spfunc = rs2.getString(2) == null ? "" : rs2.getString(2);
      if (rs.next()) {
        int res = rs.getInt(1);
        if (res > 0) {
          if (employee_func.indexOf(func) >= 0)
            return 0;
          else
            return 1;
        }
        else {
          if (employee_spfunc.indexOf(func) >= 0)
            return 1;
          else
            return 0;
        }
        //return res;
      }
      else {
        return -1;
      }

    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("cmismain003", "icbc.cmis.servlets.CmisMainDAO", ex.getMessage(), ex.getMessage());
    }
    finally {
      if (pstmt != null)
        try {
          pstmt.close();
        }
        catch (Exception ex) {}
      if (pstmt2 != null)
        try {
          pstmt2.close();
        }
        catch (Exception ex) {}
      if (rs != null)
        try {
          rs.close();
        }
        catch (Exception ex) {}
      if (rs2 != null)
        try {
          rs2.close();
        }
        catch (Exception ex) {}
      this.closeConnection();
    }
  }
}