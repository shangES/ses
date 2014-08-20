/*
//updated by ChenJ 20030526 for prepareStmt

*/
package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.service.SQLStatement;
import icbc.cmis.base.TranFailException;
import java.sql.*;
import java.util.*;
import java.util.Hashtable;

public class TableAdjustViewDAO extends CmisDao {
  //final static int QUERY_LIMIT = 200;

  public TableAdjustViewDAO(icbc.cmis.operation.CmisOperation op) {
    super(op);
  }

  public int getCount(String tableCode, Vector colomnsInfo) throws TranFailException {
    String sql = "select count(*) from " + tableCode;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String where = "";
    for (int i = 0; i < colomnsInfo.size(); i++) {
      Hashtable ht = (Hashtable)colomnsInfo.get(i);
      String isPrimary = (String)ht.get("isPrimary");
      if (isPrimary.equals("1"))
        where += "and " + (String)ht.get("col") + "=? ";
    }
    sql = sql + " where " + where.substring(3);
    try {
      this.getConnection();
      pstmt = conn.prepareStatement(sql);
      for (int i = 0,j=1; i < colomnsInfo.size(); i++) {
        Hashtable ht = (Hashtable)colomnsInfo.get(i);
        String isPrimary = (String)ht.get("isPrimary");
        if (isPrimary.equals("1")) {
          pstmt.setString(j, (String)ht.get("primaryValue"));
          j++;
        }
      }
      rs = pstmt.executeQuery();

      rs.next();
      return rs.getInt(1);
    }
    catch (Exception e) {
      throw new TranFailException("getCount", "icbc.cmis.util.TableAdjustViewDAO", e.getMessage(), e.getMessage());
    }
    finally {
      if (rs != null)
        try {
          rs.close();
        }
        catch (Exception ex) {}
      if (pstmt != null)
        try {
          pstmt.close();
        }
        catch (Exception ex) {}
      this.closeConnection();
    }
  }

  public Vector getData(String tableCode, Vector colomnsInfo, int begin, int end) throws TranFailException {
    Vector ret = new Vector();
    int i;
    String sql = "";
    String cols = "";
    String where = "";
    for (i = 0; i < colomnsInfo.size(); i++) {
      Hashtable ht = (Hashtable)colomnsInfo.get(i);
      cols += "," + (String)ht.get("col");
      String isPrimary = (String)ht.get("isPrimary");
      if (isPrimary.equals("1"))
        where += "and " + (String)ht.get("col") + "=? ";
    }
    sql =
      "select "
        + cols.substring(1)
        + " from "
        + tableCode
        + " where "
        + where.substring(3)
        + " order by "
        + (String) ((Hashtable)colomnsInfo.get(0)).get("col");

    sql = "SELECT * FROM ( SELECT ROWNUM rnum,a.* FROM (" + sql + " ) a  WHERE ROWNUM <= ? ) WHERE rnum >= ?";

    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      this.getConnection();
      pstmt = conn.prepareStatement(sql);
      int j = 1;
      for (i = 0; i < colomnsInfo.size(); i++) {
        Hashtable ht = (Hashtable)colomnsInfo.get(i);
        String isPrimary = (String)ht.get("isPrimary");
        if (isPrimary.equals("1")) {
          pstmt.setString(j, (String)ht.get("primaryValue"));
          j++;
        }
      }
      pstmt.setInt(j, end);
      pstmt.setInt(j + 1, begin);
      rs = pstmt.executeQuery();

      while (rs.next()) {
        String[] str = new String[colomnsInfo.size() + 1];
        for (i = 0; i < colomnsInfo.size() + 1; i++) {
          if (rs.getString(i + 1) != null)
            str[i] = rs.getString(i + 1);
          else
            str[i] = "";
        }
        ret.add(str);
      }
    }
    catch (Exception e) {
      throw new TranFailException("getData", "icbc.cmis.util.TableAdjustViewDAO", e.getMessage(), e.getMessage());
    }
    finally {
      if (rs != null)
        try {
          rs.close();
        }
        catch (Exception ex) {}
      if (pstmt != null)
        try {
          pstmt.close();
        }
        catch (Exception ex) {}
      this.closeConnection();
    }

    return ret;
  }
}
