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
 * date: 2003-01-14
 *
 * updated by WuQQ,20030327
 * 操作员（考虑到评估小组成员都是操作员，所以这里的操作员权限针对评估小组成员而言）可以查询所参与过的评估项目，而不仅仅是本地区所管辖的企业
 * 如果是操作员不需要对ta20001L001进行判断,
 *
 * updated by WuQQ,20030313
 * 操作员（考虑到评估小组成员都是操作员，所以这里的操作员权限针对评估小组成员而言）可以查询所参与过的评估项目，而不仅仅是所管辖的企业
 * 应王冬要求20030313修改
 *
 * updated by WuQQ,20030221
 * 当传入的地区代码不是支行时，应该查询该行所辖所有客户
 */

public class QueryEnpForTraceAndQuery extends QueryNormalEnp {

	public QueryEnpForTraceAndQuery() {

	}

	public String getWhere(Connection conn,Employee employee, Hashtable paras) {
		 String where = "";

		 String areaCode = employee.getMdbSID();
		 if(paras.containsKey("areaCode")){
				areaCode = (String)paras.get("areaCode");
			}

		 String employeeCode = employee.getEmployeeCode();
		 String employeeClass = (String)employee.getEmployeeClass();

		 //非房开、建安和学校、医院企业
		 where = " ta200011010 not in ('8','9','11')";
		 //where ="ta200011014 not in ('L8510','L8520','L8530','L8540','L8550','L8560','L8590','M8910','M8920','M8930','M8940','M8950','M8990') ";

		 //要建立信贷关系
		 where += " and ta200011059='1' ";
		 //输入已完成  --待定
		 //where += " and ta200011083='1'";
		 switch (Integer.parseInt(employeeClass)) {
				//updated by WuQQ,20030327
				//如果是非操作员,则进行所属地区的判断
				case 5://管理员
				case 6://领导
				case 7://综合员
				 //要是所辖客户
				//updated by WuQQ,20030221
				//where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + areaCode + "')";
				where += " and TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (";
				where += " select area_code from mag_area start with area_code = '" + areaCode + "' connect by prior area_code =  belong_bank))";
				break;

				//操作员要是管户或托管,此需求修改为：
				//操作员（考虑到评估小组成员都是操作员，所以这里的操作员权限针对评估小组成员而言）可以查询所参与过的评估项目，而不仅仅是所管辖的企业
				//应王冬要求20030313修改
				case 8:
				 //where +=" and ta200011001 in (select ta200012001 from ta200012  where ta200012005='1' and ( ta200012006='"+ employeeCode +"' or ( ta200012003='"+employeeCode +"' and ta200012006 is null )))";
				 where += " and ta200011001 in "
									+" ("
									+"(select distinct TA350017001 from TA350017 where TA350017002 in (select  project_code from mag_group_350 where employee_code='"+employeeCode+"' ))"
									+" union"
									//updated by WuQQ,20030327
									//+" (select ta200012001 from ta200012  where ta200012005='1' and ( ta200012006='"+ employeeCode +"' or ( ta200012003='"+employeeCode +"' and ta200012006 is null )))"
									+" select ta200012001 from ta200012 where ta200012005='1' and  ta200012006='"+ employeeCode +"' "
									+" union"
									+" select ta200012001 from ta200012 where ta200012003='"+ employeeCode +"' and ta200012006 is null"
									+" )";
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
											//99 代表评级完成
											//where += " and ta200011001 in (select TA350012001 from TA350012 where TA350012031='99' ) ";
											where += " and ta200011001 in (select TA350012001 from TA350012) ";
			}else if (ifProcessCompleted!=null && ifProcessCompleted.equals("no")){
											//where += " and ta200011001 in (select TA300003001 from ta300003 where TA300003001=ta200011001 and TA300003016<>'05') ";
											where += " and ta200011001 in (select TA350012001 from TA350012 where TA350012031<>'99' ) ";
			}
			return where;
	}
}