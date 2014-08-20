package icbc.cmis.util;
import icbc.missign.Employee;
import icbc.cmis.util.QueryNormalEnp;
import java.sql.Connection;
import java.util.Hashtable;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author: WuQQ
 * @version 1.0
 * date: 2002-12-09
 */

public class QueryEnpForGroup extends QueryNormalEnp {

	public QueryEnpForGroup() {

	}

	public String getWhere(Connection conn,Employee employee, Hashtable paras) {
		 String where = "";

		 /*
		 String areaCode = employee.getMdbSID();
		 if(paras.containsKey("areaCode")){
				areaCode = (String)paras.get("areaCode");
			}
		 String employeeClass = (String)employee.getEmployeeClass();
		 */
		 String employeeCode = employee.getEmployeeCode();
		//评估小组成员查询自己参与过评估的客户（mag_group_290所标志）
		where = " ta200011001 in (select distinct TA350017001 from TA350017 where TA350017002 in (select distinct project_code from mag_group_350 where employee_code='"+employeeCode+"' )) ";
		return where;
	}
}