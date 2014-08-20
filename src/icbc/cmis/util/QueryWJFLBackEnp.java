package icbc.cmis.util;

import icbc.missign.Employee;
import java.util.*;
import icbc.cmis.base.*;
import java.sql.*;

//public class QueryWJFLBackEnp extends icbc.cmis.operation.CmisOperation{

public class QueryWJFLBackEnp extends QueryNormalEnp {

  public QueryWJFLBackEnp() {
	super();
  }

  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
//     String where = super.getWhere(conn,employee,paras);
        String qf_date="";
        try{
          qf_date = (String)paras.get("qf_date");
        }catch(Exception ee){}
//        employee_code = (String)this.getSessionData("EmployeeCode");
        String employee_code = employee.getEmployeeCode();
        if (qf_date==null) qf_date="";
        String where = "ta200011001 in (select distinct ta260461001 from ta260461 where ta260461002='"+qf_date+"' and ta260461018='14' and ta260461022='4' and ta260461028='"+employee_code+"') and ta200011083='1'";

    return where;
  }
}
