package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;

public class QueryGroupEnp extends QueryNormalEnp{

  public QueryGroupEnp() {
  }
  public String getWhere(Connection conn,Employee employee,Hashtable paras) {
      String where = "";
      //根据行级别加where
      int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
      String ecode = employee.getEmployeeCode();
      int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
      String area = employee.getMdbSID();
      /*此处不需要限制如下条件
      switch (bankFlag) {
        case 0:
          break;
        case 4:
          where += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')");
          //根据柜员加where
          if(eclass == 8) {
            where += (" and TA200011001 in (select ta200012001 from ta200012 where ta200012003 = '" + ecode + "' or ta200012006 = '" + ecode + "')");
          }
          break;
        default:
          where += (" and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))");
          break;
      }
      */
      
    //输入已完成
    where += (" and TA200011059 = '1' and TA200011083 = 1");
    switch (eclass) {
        case 5:
		  where += (" and (ta200011001 in ( select ta350015001 from ta350015,ta350012 a where  ta350015001 = a.ta350012001");
		  where += (" and ta350015002 = a.ta350012002");
		  where += (" and ta350015010 = '"+ area + "' ");
		  where += (" and a.ta350012031 = '09'");
		  where += (" and ta350015023 =");
		  where += (" (select max (ta350015023)");
		  where += (" from ta350015");
		  where += (" where ta350015001 = a.ta350012001");
		  where += (" and ta350015002 = a.ta350012002");
		  where += (" )) or ");
		  where += (" ta200011001 in (select ta350012001 from ta350012 b,mag_group_350");
		  where += (" where b.ta350012001 = custom_id");
		  where += (" and b.ta350012002 = project_code");
		  where += (" and b.ta350012031 in ('10','11', '12', '13', '14', '15', '16', '21', '22', '23', '24', '25', '99')");
		  where += (" and  '"+ area + "' in  ");
		  where += (" (select area_code");
		  where += (" from mag_group_350");
		  where += (" where b.ta350012001 = custom_id");
		  where += (" and b.ta350012002 = project_code");
		  where += (" ))");		  
		  where += (" )");
          break;
        case 6:
          where += (" and ta200011001 in (select ta350012001 from ta350012 a,mag_group_350");
		  where += (" where a.ta350012001 = custom_id");
		  where += (" and a.ta350012002 = project_code");
		  where += (" and a.ta350012031 in ('10', '11','12', '13', '14', '15', '16', '21', '22', '23', '24', '25', '99')");
		  where += (" and  '"+ area + "' in  ");
		  where += (" (select area_code");
		  where += (" from mag_group_350");
		  where += (" where a.ta350012001 = custom_id");
		  where += (" and a.ta350012002 = project_code");
		  where += (" ))");
          break;
        case 8:
          where += (" and TA200011001 in (select custom_id from mag_group_350 where employee_code = '"+ ecode + "')");
          break;
      }
    if(where.length() > 4 ) where = where.substring(4);
    return where;
  }
}
