package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import icbc.missign.Employee;
import icbc.cmis.util.QueryNormalEnp;

public class ChooseEnpDAO extends CmisDao {
  final static int QUERY_LIMIT = 200;

  public ChooseEnpDAO(icbc.cmis.operation.CmisOperation op) {
  super(op);
  }

  public Vector getEnterprises(String queryType,Employee employee,String TA200011001,String TA200011003,String TA200011005,String TA200011010,String TA200011011,String TA200011012,String TA200011014,String TA200011016,String TA200011031, Hashtable paras) throws TranFailException {
    Vector ret = new Vector();
    String sql = null;
    Statement stmt = null;
    ResultSet rs = null;
    try {
      this.getConnection();
      stmt = conn.createStatement();
      String where = "";
      if(TA200011001.length() > 0) {
        where += (" and TA200011001 like '%" + TA200011001 + "%'");
      }
      if(TA200011003.length() > 0) {
        where += (" and TA200011003 like '%" + TA200011003 + "%'");
      }
      if(TA200011005.length() > 0) {
        where += (" and TA200011005 like '" + TA200011005 + "%'");
      }
      if(TA200011010.length() > 0) {
        where += (" and TA200011010 = '" + TA200011010 + "'");
      }
      if(TA200011011.length() > 0) {
        where += (" and TA200011011 = '" + TA200011011 + "'");
      }
      if(TA200011012.length() > 0) {
        where += (" and TA200011012 = '" + TA200011012 + "'");
      }
      if(TA200011014.length() > 0) {
        where += (" and TA200011014 like '" + TA200011014.substring(0,1) + "%'");
      }
      if(TA200011016.length() > 0) {
        where += (" and TA200011016 = '" + TA200011016 + "'");
      }
      if(TA200011031.length() > 0) {
        where += (" and TA200011031 = '" + TA200011031 + "'");
      }


      //根据情况加where条件
      QueryNormalEnp qwhere = (QueryNormalEnp)Class.forName("icbc.cmis.util." + queryType).newInstance();
      String where2 = qwhere.getWhere(this.conn,employee,paras);

      if(where.length() > 4) where = where.substring(4);
      //查询
      if(where.length() == 0) {
        sql = "select count(*) from ta200011 where " + where2 + "";
      } else {
        sql = "select count(*) from ta200011 where " + where + " and  " + where2 ;
      }
      rs = stmt.executeQuery(sql);
      rs.next();
      if(rs.getInt(1) > QUERY_LIMIT ) {
        ret.add(" 请缩小范围查询(当前返回前 " + QUERY_LIMIT + " 条记录)");
      } else {
        ret.add("");
      }
      if(where.length() == 0) {
        sql = "select TA200011001, TA200011003, TA200011005, TA200011037 from ta200011 "
            + " where rownum <= " + QUERY_LIMIT + " and " + where2 + " order by TA200011001";
      } else {
        sql = "select TA200011001, TA200011003, TA200011005, TA200011037 from ta200011 "
            + " where rownum <= " + QUERY_LIMIT + " and " + where + " and " + where2 + " order by TA200011001";
      }
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)};
        ret.add(row);
      }
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("cmisutil212","icbc.cmis.util.ChooseEnpDAO",ex.getMessage() + sql,"产生客户列表错误！");
    }
    finally {
      if(rs != null) try {rs.close();} catch (Exception ex) {};
      if(stmt != null) try {stmt.close();} catch (Exception ex) {};
      this.closeConnection();
    }
    return ret;
  }

}