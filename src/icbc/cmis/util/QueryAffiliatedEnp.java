package icbc.cmis.util;

import icbc.missign.*;
import java.sql.*;
import java.util.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 *
 * update date:2002-08-22
 * update content:客户代码加 ''
 * update by WQQ
 *
 * update date:2002-08-29
 * update content:modify sEnpList，子客户查询自身失败
 * update by WQQ
 *
 * update date:2002-08-08
 * update content:modify where  TA200173002 in ()(因为主客户可能有两个关联号)
 * update by WQQ
 */

public class QueryAffiliatedEnp extends QueryNormalEnp {

  public QueryAffiliatedEnp() {
  }
  public String getWhere(Connection conn,Employee employee,Hashtable paras) {
    String TA200173003 = (String)paras.get("TA200173003");
    String where = "";
		String sEnpList = "'"+TA200173003 +"'";
    try{
			icbc.cmis.FK.CommonSqlTool sqlTool = new icbc.cmis.FK.CommonSqlTool(new icbc.cmis.operation.DummyOperation());
			sEnpList =  sEnpList + sqlTool.getAffiliatedEnp(TA200173003);
    }catch (Exception e){}

		//根据 TA200173002是否一致 判断是否为关联客户
    //where = " ta200011001 in (select TA200173003 from TA200173 where TA200173002 in (select distinct TA200173002 from TA200173 where TA200173003='"+TA200173003+"' and TA200173001='60'))";
		where = " ta200011001 in ("+sEnpList+")";
    //System.out.println(where);
    return where;
  }
}