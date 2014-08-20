package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.MuiTranFailException;
import icbc.cmis.base.TranFailException;
import java.util.Vector;
import java.sql.*;
/**
 * <p>Title: 产生柜员列表</p>
 * <p>Description: 根据传入的参数，返回柜员列表</p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ChooseEmployeeDAO extends CmisDao {

  public ChooseEmployeeDAO(icbc.cmis.operation.CmisOperation op) {
  super(op);
  }

  /**
   * 根据传入的参数，在mag_employee_major表中选择柜员，返回柜员列表H
   * @param eCode 柜员号a
   * @param area 地区号，多个地区用逗号分隔?
   * @param major 专业，多个专业用逗号分隔?
   * @param eClasses 可选柜员角色，多个角色用逗号分隔?
   * @param includeSelf 是否包含自己，true|false
   * @return Vector柜员列表?
   * @param boolean exludeInsideCode 是否排除内部柜员 
   * @throws TranFailException
   */
  public Vector getEmployee(String eCode,String area,String major,String eClasses,
                            boolean includeSelf,String distinctIgnor,boolean exludeInsideCode) throws TranFailException{
    String where = "";
    if(major.length() > 0) {
      StringBufferR majors = new StringBufferR("'"+major+"'");
      majors.replace(",","','");
      where = " and employee_major in (" + majors.toString() + ") ";
    }
    if(area.length() > 0) {
      StringBufferR areas = new StringBufferR("'"+area+"'");
      areas.replace(",","','");
      where += (" and b.mdb_sid in (" + areas.toString() + ") ");
    }
    if(!includeSelf) {
      StringBufferR eCodes = new StringBufferR("'"+eCode+"'");
      eCodes.replace(",","','");
      where += (" and b.employee_code not in (" + eCodes.toString() + ") ");
    }
    if(eClasses.length() > 0) {
      where += (" and employee_class in (" + eClasses + ") ");
    }
    if(where.length() > 0) {
      where = where.substring(4) + " and ";
    }
    //查询mag_employee表t
    String sql = "select distinct b.employee_code||' '||rpad(rtrim(b.employee_name),9,'.')||'..'";
    String select = ",b.employee_code,b.employee_name";

    if (distinctIgnor.charAt(0) == '1') {//ignor major
      sql = sql + "||'.............'";
      select = select + ",null,null";
    }
    else {
      sql = sql + "||rpad(major_name,19,'.')";
      select = select + ",employee_major,major_name";
    }

    if (distinctIgnor.charAt(1) == '1') {//ignor class
      select = select + ",null,null";
    }
    else {
      sql = sql + "||class_name";
      select = select + ",employee_class,class_name";
    }
    sql = sql + select + " from mag_employee_major a,mag_employee_class,mag_major,mag_employee b where "
              + where + " b.employee_delete_flag(+) = 0  and "
              + " employee_class = class_code(+) and employee_major = mag_employee_class.major_code(+) and employee_major = mag_major.major_code(+) ";
              
              
     //20070623--------
     
     sql+=" and mag_employee_class.lang_code='"+(String)super.getOperation().getSessionData("LangCode")+"' ";
     sql+=" and  mag_major.lang_code = mag_employee_class.lang_code  ";
     
     //---------
     
    //修改原因：新增一个是否排除内部柜员的参数
    //修改日期:	2004-5-20 
    //修改人：杨光润
    if(exludeInsideCode)
    	sql=sql+" and (b.employee_code not in ( select inside_employee_code from missign.mag_employee_comparison )) ";
    sql=sql+" and a.employee_code(+) = b.employee_code order by b.employee_code ";
    Statement stmt = null;
    ResultSet rs = null;
    Vector retVector = new Vector();
    try {
      this.getConnection();
      stmt = conn.createStatement();
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)};
        retVector.add(row);
      }
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
//      throw new TranFailException("cmisutil200","icbc.cmis.util.ChooseEmployeeDAO",ex.getMessage() + sql,"产生柜员列表错误！");
			throw new MuiTranFailException("099995", "ChooseEmployeeDAO.getEmployee()",(String)this.getOperation().getSessionData("LangCode"));
    }
    finally {
      if(rs != null) try {rs.close();} catch (Exception ex) {};
      if(stmt != null) try {stmt.close();} catch (Exception ex) {};
      this.closeConnection();
    }
    return retVector;

  }
}