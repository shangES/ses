package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.SSICTool;
import icbc.missign.Employee;
import java.sql.*;
import java.util.Vector;
import icbc.cmis.util.AuthorizationException;

public class AuthorizationDAO extends CmisDao {

  public AuthorizationDAO(icbc.cmis.operation.CmisOperation op) {
    super(op);
  }

  public String authorize(Employee employee, String ecode2, String passwd, String module, String info) throws TranFailException, AuthorizationException {
    String ecode1 = employee.getEmployeeCode();
    String ename1 = employee.getEmployeeName();
    String area = employee.getMdbSID();
    String major = employee.getEmployeeMajor();
    String majorName = employee.getEmployeeMajorName();

    String insideEmpCode2 = null; //ecode2对应的area地区的内部柜员号
    String xml = "<ok>ok</ok>";
    String sql = null;
    Statement stmt = null;
    ResultSet rs = null;
    java.sql.PreparedStatement pstmt = null;
    if (employee.getOutsideEmpCode().equals(ecode2)) {
      return "<error>柜员不能是自己!</error>";
    }

    try {
      this.getConnection();

      //取得该外部柜员下的指点地区的内部柜员
      sql = "select inside_employee_code from missign.mag_employee_comparison where outside_employee_code = ? and area_code = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, ecode2);
      pstmt.setString(2, area);
      rs = pstmt.executeQuery();
      if (!rs.next()) {
        throw new AuthorizationException("<error>柜员:" + ecode2 + "在本地区没有担任任何角色!</error>");
      }
      insideEmpCode2 = rs.getString(1);
      pstmt.close();


      //取得柜员信息
      sql = "select employee_passwd,employee_name from mag_employee where employee_code = ? ";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, insideEmpCode2);

      rs = pstmt.executeQuery();
      if (!rs.next()) {
        throw new AuthorizationException("<error>无此柜员:" + ecode2 + "!</error>");
      }

      String passwd2 = rs.getString(1);
      String ename2 = rs.getString(2);
      pstmt.close();

      sql =
        "select employee_class from mag_employee_major where employee_code = ? and employee_major in ('210', ? ) and employee_area = ? and employee_class in ('5','6')";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, insideEmpCode2);
      pstmt.setString(2, employee.getEmployeeMajor());
      pstmt.setString(3, area);

      rs = pstmt.executeQuery();

      if (!rs.next()) {
        throw new AuthorizationException("<error>外部柜员" + ecode2  + ename2 + "在本地区对应的内部柜员"+insideEmpCode2 +"没有在本地区担任信贷或" + employee.getEmployeeMajorName() + "中的专业管理员或领导角色！</error>");
      }

      //int eclass = rs.getInt(1);
      //if(eclass != 5 && eclass != 6) {throw new AuthorizationException("<error>柜员必须为专业管理员或领导</error>");}
      if (!SSICTool.isSSICEnabled()) {
        if (!Encode.checkPassword(ecode2, passwd, passwd2)) {
          throw new AuthorizationException("<error>柜员" + ecode2 + "口令错误!</error>");
        }
      }
      pstmt.close();
      sql = "insert into mag_audit values( ? , ? , ? , ? , ? ,to_char(cmisdate,'yyyymmddhhmiss'),?,?)";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, area);
      pstmt.setString(2, ecode1);
      pstmt.setString(3, ename1);
      pstmt.setString(4, ecode2);
      pstmt.setString(5, ename2);
      pstmt.setString(6, module);
      pstmt.setString(7, info);

      int rows = pstmt.executeUpdate();
      if (rows == 0) {
        throw new AuthorizationException("<error>插入授权表(mag_aduit)错误!插入0条记录。</error>");
      }

      conn.commit();
    }
    catch (AuthorizationException ex) {
      throw ex;
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("cmisutil215", "icbc.cmis.util.AuthorizationDAO", ex.getMessage(), ex.getMessage());
    }
    finally {
      if (rs != null)
        try {
          rs.close();
        }
        catch (Exception ex) {};
      if (pstmt != null)
        try {
          pstmt.close();
        }
        catch (Exception ex) {};
      this.closeConnection();
    }
    return xml;
  }
}