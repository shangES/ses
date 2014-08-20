package icbc.cmis.util;

import icbc.missign.Employee;
import java.sql.*;
import java.util.Hashtable;

/**
 * 接受客户新增中的查询参数，查询客户。
 * 主要实现，客户状态(5全部)、输入完成标志的条件查询(2全部)
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: icbc</p>
 * @author yxt
 * @version 1.1
 */

public class QueryNewCustomerManger extends QueryNormalAllEnp {

  public QueryNewCustomerManger() {
  }

  /**
   * 返回查询条件
   * <p>modify: 12/9/2002 客户状态增加被整合客户4,全部改为5</p>
   * @param conn 数据库链接
   * @param employee 当前柜员
   * @param paras 查询参数
   * @return where子句
   */
  public String getWhere(Connection conn, Employee employee, Hashtable paras) {
      String where = "and " + super.getWhere(conn,employee,paras);

      /*//国家代码DA200011078
      String TA200011078 = (String)paras.get("TA200011078");
      if(TA200011078 != null && TA200011078.length() > 0) {
        where += (" and TA200011078 = '" + TA200011078 + "'");
      }
      //一般客户、重点客户DA200011079
      String TA200011079 = (String)paras.get("TA200011079");
      if(TA200011079 != null && TA200011079.length() > 0) {
        where += (" and TA200011079 = " + TA200011079);
      }
      //主机行业DA200011080
      String TA200011080 = (String)paras.get("TA200011080");
      if(TA200011080 != null && TA200011080.length() > 0) {
        where += (" and TA200011080 = " + TA200011080);
      }*/
      //客户状态 1建立信贷关系 2新增 3销户(无业务) 4整合 5销户(已结束)
      String TA200011059 = (String)paras.get("TA200011059");
      if(TA200011059 != null && TA200011059.length() > 0 && !TA200011059.equals("6")) {
        where += (" and TA200011059 = '" + TA200011059 + "'");
      }
      //完成标志 0 未完成 1 完成
      String TA200011083 = (String)paras.get("TA200011083");
      if(TA200011083 != null && TA200011083.length() > 0 && !TA200011083.equals("2")) {
        where += (" and TA200011083 = " + TA200011083);
      }

    if(where.length() > 4 ) where = where.substring(4);
    return where;
  }
}