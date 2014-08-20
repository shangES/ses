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
 *
 * update date: 2003-05-12
 * update content:change sql where,查询地区从ta200011
 * updated by WuQQ
 *
 * update date: 2002-09-26
 * update content:change sql where,查询地区从ta20001L判断
 * updated by WuQQ
 *
 * update date: 2002-07-30
 * update content:change sql where,query sub_bank
 * updated by WuQQ
 */

public class QueryEnp4EvaluateForTraceAndQuery extends QueryNormalEnp {

	public QueryEnp4EvaluateForTraceAndQuery() {

	}

	public String getWhere(Connection conn,Employee employee, Hashtable paras) {
		 String where = "";
		 String tt=null;

		 String areaCode = employee.getMdbSID();
		 if(paras.containsKey("areaCode")){
				areaCode = (String)paras.get("areaCode");
			}

		 String employeeCode = employee.getEmployeeCode();
		 String employeeClass = (String)employee.getEmployeeClass();

		 //where ="ta200011014 not in ('L8510','L8520','L8530','L8540','L8550','L8560','L8590','M8910','M8920','M8930','M8940','M8950','M8990') and ta200011063='"+areaCode+"'  and ta200011059='1' ";
		 //where ="ta200011014 not in ('L8510','L8520','L8530','L8540','L8550','L8560','L8590','M8910','M8920','M8930','M8940','M8950','M8990') ";
		 //where += " and ta200011063  in (select area_code from mag_area start with area_code = '"+areaCode+"' connect by prior area_code =  belong_bank )";
		 //非主管行不能评级，所以只要查询 ta200011063即可，不考虑多头开户情况
		 //where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + areaCode + "')";
		 where += " ta200011063='"+areaCode+"' and ta200011059='1' ";
		 switch (Integer.parseInt(employeeClass)) {
				case 5://管理员
				case 6://领导
				 break;
				case 7:
				//操作员要是管户或托管
				case 8:
					//where +=" and ta200011001 in (select ta200012001 from ta200012  where ta200012005='1' and ( ta200012006='"+ employeeCode +"' or ( ta200012003='"+employeeCode +"' and ta200012006 is null )))";
					where +=" and ta200011001 in (select ta200012001 from ta200012"+
					" where ta200012005='1'"+
					" and ta200012006='"+ employeeCode +"'"+
					" UNION ALL"+
					" select ta200012001 from ta200012"+
					" where ta200012005='1'"+
					" AND ta200012003='"+ employeeCode +"' and ta200012006 is null)";
				 break;
				default :
					 break;
			}

			//评级流程和评级查询使用
			String ifProcessCompleted = null;
			if(paras.containsKey("ifProcessCompleted")){
							ifProcessCompleted = (String)paras.get("ifProcessCompleted");
			}
			//try{
							//ifProcessCompleted = (String)paras.get("ifProcessCompleted");
			//}catch (Exception e){}
			if (ifProcessCompleted!=null && ifProcessCompleted.equals("yes")){
											where += " and ta200011001 in (select TA300003001 from th300003 ) ";
											//where += " and ta200011001 in (select TA300003001 from ta300003 where TA300003001=ta200011001 and TA300003016='05') ";
			}else if (ifProcessCompleted!=null && ifProcessCompleted.equals("no")){
											//where += " and ta200011001 in (select TA300003001 from ta300003 where TA300003001=ta200011001 and TA300003016<>'05') ";
											where += " and ta200011001 in (select TA300003001 from ta300003 ) ";
			}
			return where;
	}
}