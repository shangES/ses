package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;

public class MedalQueryNormalEnp extends QueryNormalEnp{

  public MedalQueryNormalEnp() {
  }

  public String getWhere(Connection conn,Employee employee,Hashtable paras) {
      String where = "";
      //根据行级别加where
      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
      String ecode = employee.getEmployeeCode();
      int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
      String area = employee.getMdbSID();

      String operType = employee.getEmployeeClass();
      String seleClient = (String)paras.get("seleClient");
      if(operType.equals("8")&&seleClient!=null&&seleClient.equals("1")){
         where += (" and TA200011055 = '" + ecode + "'");
      }else{

        switch (bankFlag) {
          case 0:
            break;
          case 4:
            where += (" and TA200011063  = '" + area + "'");
          //根据柜员加where
         // if(eclass == 8) {
          //  where += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = '" + ecode + "' or ta200012006 = '" + ecode + "')");
        //  }
            break;
          default:
            where += (" and TA200011063  in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code)");
            break;
        }
      }
    //输入已完成
    where += (" and TA200011059 = '1' and TA200011083 = 1");
    if(where.length() > 4 ) where = where.substring(4);

    return where;
  }
}