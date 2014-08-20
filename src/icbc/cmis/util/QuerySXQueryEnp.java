//查询已有授信纪录的企业
package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;

public class QuerySXQueryEnp extends QueryNormalEnp {

  public QuerySXQueryEnp() {
  }

  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
     String where = super.getWhere(conn,employee,paras);
     where += " and ta200011001 in (select TA330131002 from TA330131 )";

    return where;
  }
}
