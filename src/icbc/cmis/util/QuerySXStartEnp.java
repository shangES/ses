//查询已有授信测算纪录的企业
package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;

public class QuerySXStartEnp extends QueryNormalEnp {

  public QuerySXStartEnp() {
  }

  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
     String where = super.getWhere(conn,employee,paras);
     where += " and ta200011001 in (select TA330161002 from TA330161 where ta3301610014 is not null )";

    return where;
  }
}
