package icbc.cmis.util;

import icbc.cmis.service.CmisDao;
import icbc.cmis.base.TranFailException;
import java.sql.*;
import java.util.Vector;
import java.util.Hashtable;
import icbc.missign.Employee;
import icbc.cmis.util.PagesQuery;
import java.io.Serializable;

public abstract class util_QueryDAO extends CmisDao implements Serializable {
  protected String countSQL = null;
  protected String querySQL = null;

  protected int fieldNumber = 0;
  protected String[] fieldNames;
  protected Hashtable parameters;
  protected int recordCount = -1;
  private int bufferBegin = 0;
  private int bufferEnd = 0;
  private int pageLines = 0;
  private int bufferPages = 0;
  private int bufferLines = 0;
  private int pages = 0;
  private Vector datas;

  public util_QueryDAO() {
    super(new icbc.cmis.operation.DummyOperation());
  }

  public void genParameters(Employee employee, Hashtable paras) throws TranFailException {
    try {
      String dbuser = null;

      dbuser = (String)paras.get("dbuser");

      if (dbuser == null || dbuser.equals(""))
        dbuser = "cmis3";

      this.getConnection(dbuser);
      countSQL = this.getCountSQL(conn, employee, paras);
      querySQL = this.getQuerySQL(conn, employee, paras);
      fieldNumber = this.getFieldNumber();

      fieldNames = this.getFieldNames();
      parameters = paras;
      pageLines = this.getPageLines();
      bufferPages = this.getBufferPages();
      bufferLines = pageLines * bufferPages;
      recordCount = this.getRecordCount(dbuser);
      pages = (recordCount - 1) / pageLines + 1;
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {

      throw new TranFailException("cmisutil300", "icbc.cmis.util.util_QueryDAO", ex.getMessage(), ex.getMessage());
    }
    finally {
      this.closeConnection();
    }

  }

  public abstract String getCountSQL(Connection conn, Employee employee, Hashtable paras) throws Exception;

  public abstract String getQuerySQL(Connection conn, Employee employee, Hashtable paras) throws Exception;

  public abstract String[] getFieldNames();

  public abstract int getFieldNumber();

  public abstract void setParameters(boolean notCountQuery, PreparedStatement pstmt, Hashtable paras, int begin, int end) throws Exception;

  public int getRecordCount(String dbuser) throws TranFailException {
    if (this.recordCount != -1) {
      return this.recordCount;
    }
    PreparedStatement pstmt = null;

    ResultSet rs = null;
    int ret = 0;
    try {
      this.getConnection(dbuser);
      pstmt = conn.prepareStatement(this.countSQL);
      this.setParameters(false, pstmt, parameters, 0, 0);
      rs = pstmt.executeQuery();
      rs.next();
      ret = rs.getInt(1);
      this.recordCount = ret;
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {

      throw new TranFailException("cmisutil301", "icbc.cmis.util.util_QueryDAO", ex.getMessage() + countSQL, ex.getMessage() + countSQL);
    }
    finally {
      if (rs != null)
        try {
          rs.close();
        }
        catch (Exception ex) {};
      if (pstmt != null)
        try {
          pstmt.close();
        }
        catch (Exception ex) {};
      this.closeConnection();
    }
    return ret;
  }

  public String queryPage(int page, String dbuser) throws TranFailException {

    int begin = (page - 1) * this.pageLines + 1;
    int end = (page) * this.pageLines;
    if (end > recordCount) {
      end = recordCount;
    }

    if (begin < bufferBegin || end > bufferEnd) {
      if (begin < bufferBegin) {
        bufferBegin = begin / this.bufferLines * this.bufferLines + 1;
        bufferEnd = bufferBegin + this.bufferLines - 1;
      }
      else {
        bufferEnd = ((end - 1) / this.bufferLines + 1) * this.bufferLines;
        bufferBegin = bufferEnd - this.bufferLines + 1;
      }

      if (bufferBegin > begin) {
        bufferBegin = begin;
      }
      if (bufferEnd < end) {
        bufferEnd = end;
      }
      if (bufferEnd > recordCount) {
        bufferEnd = recordCount;
      }
      if (bufferBegin < 1) {
        bufferBegin = 1;
      }

      datas = this.query(bufferBegin, bufferEnd, dbuser);
    }
    StringBuffer ret = new StringBuffer(2000);
    for (int i = begin - bufferBegin; i <= end - bufferBegin; i++) {
      ret.append((String)datas.get(i));
    }
    return ret.toString();
  }

  public Vector query(int begin, int end, String dbuser) throws TranFailException {
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Vector ret = new Vector(this.bufferPages * this.pageLines);
    try {
      this.getConnection(dbuser);
      pstmt = conn.prepareStatement(this.querySQL);
      this.setParameters(true, pstmt, parameters, begin, end);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        StringBuffer ts = new StringBuffer(100);
        ts.append("<row ");
        for (int i = 1; i <= this.getFieldNumber(); i++) {
          ts.append(" f");
          ts.append(i);


          ts.append("='");


          ts.append((rs.getString(i) == null) ? "" : icbc.cmis.util.Func_XMLfiltrate.validXml(rs.getString(i)));


          ts.append("'");

          
        }
        ts.append(" />");
        ret.add(ts.toString());
      }
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {

      throw new TranFailException("cmisutil302", "icbc.cmis.util.util_QueryDAO", ex.getMessage() + this.querySQL, ex.getMessage() + this.querySQL);
    }
    finally {
      if (rs != null)
        try {
          rs.close();
        }
        catch (Exception ex) {};
      if (pstmt != null)
        try {
          pstmt.close();
        }
        catch (Exception ex) {};
      this.closeConnection();
    }
    return ret;
  }

  public abstract int getBufferPages();

  public abstract int getPageLines();

  public abstract int[] getFieldsWidth();

  public abstract int[] getAskFields();

  public abstract int[] getFieldsAlign();

  public int getPages() {
    return pages;
  }
}