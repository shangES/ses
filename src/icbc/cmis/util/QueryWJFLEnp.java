package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.*;
import icbc.cmis.base.*;

public class QueryWJFLEnp extends QueryNormalEnp {

  public QueryWJFLEnp() {
  }

  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
      String where = super.getWhere(conn,employee,paras);
      String area_code = employee.getMdbSID();
      String qf_date = "999999";
      try{
         qf_date = (String)paras.get("qf_date");
      }catch (Exception ex) {}
      if (qf_date==null) qf_date = "";
//      where += " and ta200011001 in (select ta200301001 from ta200301 a where ta200301001=ta200011001 and ta200301040='"+area_code+"' and ta200301010>0 and ta200301007=(select max(ta200301007) from ta200301 where ta200301001=a.ta200301001 and ta200301003=a.ta200301003 and ta200301005 <= '"+qf_date+"'))";
      where += " and ta200011001 in (select ta200361002 from ta200361 a where ta200361001='"+qf_date+"' and ta200361118='"+area_code+"' and ta200361008>0 and (ta200361008-ta200361017)>0 and ta200361002=ta200011001 )";

    return where;
  }
}
