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

public class QueryEnp4ScaleQuery extends QueryNormalEnp {

  public QueryEnp4ScaleQuery() {

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

//modify by yhua on 2003/08/08
//     where =" ta200011063 in  (select area_code from mag_area where bank_flag = '4' start with area_code = '"+ areaCode+"' connect by prior area_code = belong_bank and belong_bank <> area_code) ";

     where =" ta200011001 in (select ta20001l001 from ta20001l where ta20001l002 in  (select area_code from mag_area where bank_flag = '4' start with area_code = '"+ areaCode+"' connect by prior area_code = belong_bank and belong_bank <> area_code)) ";

     return where;
  }
}