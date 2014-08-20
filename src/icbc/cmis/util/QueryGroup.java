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
 * update date:2003-04-15
 * update content:除去打印信息 system.out.print()
 * update by WuQQ
 *
 * update date:2002-08-22
 * update content:除去多余end
 * update by WuQQ
 *
 * update date: 2002-08-20
 * update content：查询客户所属地区改由ta20001l中取值
 * updated by WuQQ
 */

public class QueryGroup extends QueryNormalEnp {

	public QueryGroup() {
	}
	public String getWhere(Connection conn,Employee employee,Hashtable paras) {
		String where = "";
		//根据 TA200173001 判断是否为集团客户
		//‘1’代表主客户，‘60’代表集团客户
		where = "ta200011001 in (select ta200173003 from ta200173 where  TA200173004='1' and TA200173001='60')";

	 String area = (String)paras.get("area");
	 String bankflag = (String)paras.get("bankFlag");
	 if(!bankflag.equals("0")) where += " and ";
	 if(bankflag.equals("4"))
		 //当所选地区是支行，则所选客户应该在用户所选的地区中
		 where += " TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 = '" + area + "')";
		 //where += " TA200011063  =  '"+area+"' ";
	 else if(bankflag.equals("3")||bankflag.equals("2"))
		 //如果所选地区是二级行或准一级行，所选客户应该在所选地区的下级行
		 where += " TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
		 //where += " TA200011063 in (select area_code from mag_area where belong_bank='"+area+"')";
	 //如果所选地区是一级行，所选客户应该在所选地区的所有支行
	 else if(bankflag.equals("1"))
		 where += " TA200011001 in (select ta20001L001 from ta20001L where ta20001L002 in (select area_code from mag_area start with area_code = '" + area + "' connect by belong_bank = prior area_code))";
		 //where += " TA200011063 in (select area_code from mag_area where belong_bank in (select area_code from mag_area where belong_bank ='"+area+"'))";
	 //如果所选地区是总行的，不作地区控制

		//客户状态为建立信贷关系，且输入标志是完成的
		where += " and ta200011059 = '1'";
		where += " and ta200011083='1'";

		//System.out.println(where);
		return where;
	}
}