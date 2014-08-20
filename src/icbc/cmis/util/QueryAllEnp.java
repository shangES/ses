package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;

public class QueryAllEnp extends QueryNormalAllEnp {

  public QueryAllEnp() {
  }

  public String getWhere(Connection conn,Employee employee,Hashtable paras) {
    String where = super.getWhere(conn,employee,paras);
    if(where.length() > 0 ) where += " and";
    where += " TA200011059 in (1,2) and TA200011083 = 1";
    return where;
  }
}
