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

public class QueryWJFL2Enp extends QueryNormalEnp {

  public QueryWJFL2Enp() {
  }
  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
     String where = super.getWhere(conn,employee,paras);
     where += " and ta200011001 in (select TA260411001 from TA260411 )";

    return where;
  }
}