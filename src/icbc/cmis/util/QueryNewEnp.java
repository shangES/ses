package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;

public class QueryNewEnp extends QueryNormalAllEnp{

  public QueryNewEnp() {
  }

  public String getWhere(Connection conn,Employee employee,Hashtable paras) {
      String where = super.getWhere(conn,employee,paras);
      //查询本地区新增的记录
      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
      String ecode = employee.getEmployeeCode();
      int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
      String area = employee.getMdbSID();
      where += (" and TA200011063 = '"+ area +"' and TA200011059 <> '3' and  TA200011083 = 0 ");
    return where;
  }
}