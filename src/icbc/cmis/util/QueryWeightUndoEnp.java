//查询企业规模不是小型的,且没有进行过评价人权重设置的企业
package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;

public class QueryWeightUndoEnp extends QueryNormalEnp {

  public QueryWeightUndoEnp() {
  }

  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
//     String where = super.getWhere(conn,employee,paras);
     String area_code = employee.getMdbSID();
     String where = "ta200011063='"+area_code+"' and ta200011010 not in ('9','11') and ta200011016 not in ('1060','2030','2060','3030','4030','5013','5014','5024','8011','8012','8013') and ta200011083='1' and ta200011001 not in (select distinct ma300004001 from ma300004)";

    return where;
  }
}
