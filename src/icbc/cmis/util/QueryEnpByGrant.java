package icbc.cmis.util;
import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;
import icbc.cmis.base.CmisConstance;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 *
 * updated by WuQQ,20030319
 * 修改getWhere()
 * 授权对象种类,0=客户，1=地区(主键),查询客户应该  grant001 = '0'
 *
 */

public class QueryEnpByGrant extends QueryNormalEnp {

  public QueryEnpByGrant() {

  }

  public String getWhere(Connection conn,Employee employee,Hashtable paras) {
      String where = "";
      //根据行级别加where
      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
      String ecode = employee.getEmployeeCode();
      int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
      String sysdate = CmisConstance.getWorkDate("yyyyMMdd");
      String area = employee.getMdbSID();
      switch (bankFlag) {
        case 0:
          break;
        case 4:
          //updated by WuQQ,授权对象种类,0=客户，1=地区(主键),查询客户应该  grant001 = '0'
          //where += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "' union all select grant002 from mag_grant where grant001 = '1' and grant004 = '"+ ecode +"' and grant003 = '"+ area +"' and grant008 <= '"+sysdate+"' and grant009 >= '" + sysdate + "')");
          where += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "' union all select grant002 from mag_grant where grant001 = '0' and grant004 = '"+ ecode +"' and grant003 = '"+ area +"' and grant008 <= '"+sysdate+"' and grant009 >= '" + sysdate + "')");
          //根据柜员加where
          if(eclass == 8) {
            //where += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = '" + ecode + "' union all select ta200012001 from ta200012 where ta200012006 = '" + ecode + "' union all select grant002 from mag_grant where grant001 = '1' and grant004 = '"+ ecode +"' and grant003 = '"+ area +"' and grant008 <= '"+sysdate+"' and grant009 >= '" + sysdate + "')");
            where += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = '" + ecode + "' union all select ta200012001 from ta200012 where ta200012006 = '" + ecode + "' union all select grant002 from mag_grant where grant001 = '0' and grant004 = '"+ ecode +"' and grant003 = '"+ area +"' and grant008 <= '"+sysdate+"' and grant009 >= '" + sysdate + "')");
          }
          break;
        default:
          //where += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code)  union all select grant002 from mag_grant where grant001 = '1' and grant004 = '"+ ecode +"' and grant003 = '"+ area +"' and grant008 <= '"+sysdate+"' and grant009 >= '" + sysdate + "')");
          where += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code)  union all select grant002 from mag_grant where grant001 = '0' and grant004 = '"+ ecode +"' and grant003 = '"+ area +"' and grant008 <= '"+sysdate+"' and grant009 >= '" + sysdate + "')");
          break;
      }
    if(where.length() > 4 ) where = where.substring(4);
    return where;
  }

}