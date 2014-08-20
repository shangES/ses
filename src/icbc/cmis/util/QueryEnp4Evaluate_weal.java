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

public class QueryEnp4Evaluate_weal extends QueryNormalEnp {

  public QueryEnp4Evaluate_weal() {

  }

  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
     String where = "";
     String tt=null;

     String areaCode = employee.getMdbSID();
     String employeeCode = employee.getEmployeeCode();
     String employeeClass = (String)employee.getEmployeeClass();

     switch (Integer.parseInt(employeeClass)) {
        case 7:
        case 8:
         where =" ta200011063='"+areaCode+"' and ta200011010 in ('9','11')  and ta200011059='1' and ta200011001 in (select ta200012001 from ta200012  where ta200012005='1' and ta200012006='"+ employeeCode +"' union all select ta200012001 from ta200012  where ta200012005='1' and ta200012003='"+employeeCode +"' and ta200012006 is null )";
         break;
        default ://众多领导及管理员，可以查询到本行所有企业
         where =" ta200011010 in ('9','11') and ta200011063='"+areaCode+"'  and ta200011059='1' ";
           break;
      }

			//评级流程和评级查询使用
		  String ifProcessCompleted = null;
			try{
				ifProcessCompleted = (String)paras.get("ifProcessCompleted");
			}catch (Exception e){}
			if (ifProcessCompleted!=null && ifProcessCompleted.equals("yes")){
					where += " and ta200011001 in (select TA300003001 from ta300003 where TA300003001=ta200011001 and TA300003016='05') ";
			}else if (ifProcessCompleted!=null && ifProcessCompleted.equals("no")){
					where += " and ta200011001 in (select TA300003001 from ta300003 where TA300003001=ta200011001 and TA300003016<>'05') ";
			}
      return where;
  }
}