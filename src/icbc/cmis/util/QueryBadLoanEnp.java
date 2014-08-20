package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;
import icbc.cmis.util.*;

/*************************************************************
 *
 * <b>创建日期: </b> 2004-11-08<br>
 * <b>标题: </b><br>
 * <b>类描述:查询客户代码通用模式对话框的条件类 </b><br>
 * <br>
 * <p>Copyright: Copyright (c)2004</p>
 * <p>Company: </p>
 *
 * @author 金小瓯
 *
 * @version 1.0.0
 *
 * @since
 *
 * @see
 *
 *************************************************************/

public class QueryBadLoanEnp extends QueryNormalEnp {//查询已经完成不良贷款客户基本情况的客户


	  public QueryBadLoanEnp() {
	  }

	  public String getWhere(Connection conn,Employee employee, Hashtable paras) {

		 String where = " ta200011011 in ( select distinct ta390001001 from ta390001 ) ";

		return where;
	  }

}