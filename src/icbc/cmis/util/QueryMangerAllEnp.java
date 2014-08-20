package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;

public class QueryMangerAllEnp extends QueryNormalEnp {

  public QueryMangerAllEnp() {
  }

  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
    String area = (String)paras.get("area");
    if(area == null) {
      area = employee.getMdbSID();
    }

    String where = " ta200011059 in ('1','4') and TA200011083 = '1' and ta200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "' and ta20001L003 = 1)";
    return where;
  }
}