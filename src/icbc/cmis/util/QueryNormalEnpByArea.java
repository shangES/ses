package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;

public class QueryNormalEnpByArea  extends QueryNormalEnp {

  public QueryNormalEnpByArea() {
  }

  public String getWhere(Connection conn,Employee employee,Hashtable paras) {
    String area = (String)paras.get("areacode");
    if(area == null || area.equals("")) {
      area = employee.getMdbSID();
    }
    String where = "";
    //根据行级别加where
    int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
    String ecode = employee.getEmployeeCode();
    int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
    switch (bankFlag) {
      case 0:
        break;
      case 4:
        where += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')");
        //根据柜员加where
        if(eclass == 8) {
          where += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = '" + ecode + "' union all select ta200012001 from ta200012 where ta200012006 = '" + ecode + "')");
        }
        break;
      default:
        where += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))");
      break;
    }
    //输入已完成
    where += (" and TA200011059 = '1' and TA200011083 = 1");
    if(where.length() > 4 ) where = where.substring(4);
    return where;
  }
}