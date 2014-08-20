package icbc.cmis.util;
import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;
import javax.servlet.*;
import javax.servlet.http.*;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class QueryManagingEnp extends QueryNormalEnp {

  public QueryManagingEnp() {
  }

//  public String getWhere(Connection conn,Employee employee, Hashtable paras) {
//	String where = "";
//	//	int eclass = Integer.valueOf(employee.getEmployeeClass()).intValue();
//		String ecode = employee.getEmployeeCode();
//	//	int bankFlag = Integer.valueOf(employee.getBankFlag()).intValue();
//	//	String area = employee.getMdbSID();
//		where += (" and TA200011001 in ((select ta200012001 from ta200012 where ta200012003 = '" + ecode + "') union all (select ta200012001 from ta200012 where ta200012006 = '" + ecode + "'))");
//	    
//	    //输入已完成
//	    where += (" and TA200011059 = '1' and TA200011083 = 1");
//	if(where.length() > 4 ) where = where.substring(4);
//	return where;
//
//  }

	public String getWhere(Connection conn,Employee employee, Hashtable paras) {
		String where = "";
	    StringBuffer buf= new StringBuffer();
		String ecode = employee.getEmployeeCode();
	//	where += (" and TA200011001 in ((select ta200012001 from ta200012 where ta200012003 = '" + ecode + "') union all (select ta200012001 from ta200012 where ta200012006 = '" + ecode + "'))");
		buf.append(" and TA200011001 in ((select ta200012001 from ta200012 where ta200012003 = '");
		buf.append(ecode);
		buf.append("') union all (select ta200012001 from ta200012 where ta200012006 = '");
		buf.append(ecode);
		buf.append("'))");		
		buf.append(" and TA200011059 in ('1','5') and TA200011083 = 1");
		//输入已完成
		//where += (" and TA200011059 = '1' and TA200011083 = 1");
		
//	if(where.length() > 4 ) where = where.substring(4);
//	return where;
	   if(buf.length() > 4 ) where = buf.substring(4);
	   return where;

	}
}