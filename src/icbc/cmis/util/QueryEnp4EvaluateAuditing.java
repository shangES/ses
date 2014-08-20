package icbc.cmis.util;
import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class QueryEnp4EvaluateAuditing extends QueryNormalEnp {

  public QueryEnp4EvaluateAuditing() {

  }

  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
     String where = "";
     String tt=null;

     String areaCode = employee.getMdbSID();

     if(paras.containsKey("areaCode")){
        areaCode = (String)paras.get("areaCode");
      }

     String employeeCode = employee.getEmployeeCode();
     String employeeClass = (String)employee.getEmployeeClass();

     where =" ta200011059='1' and ta200011063 in " +
            " (select area_code from mag_area where bank_flag = '4' start with area_code = '"+ areaCode+"' connect by prior area_code = belong_bank and belong_bank <> area_code) " +
            " and ta200011001 in ( select ta300005001 from ta300005 where ta300005008='"+employeeCode +"' and ta300005017 in (1,8)) ";

     return where;
  }
}