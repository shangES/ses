/*
 * 创建日期 2005-11-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.tags;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import icbc.cmis.base.*;
import icbc.cmis.util.*;
import icbc.cmis.operation.*;
/**
 * @author Administrator
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class CheckEmployeeAuthTag extends TagSupport {
  Hashtable authHash = new Hashtable();
  private String enpcode = null;
  public int doStartTag() throws JspTagException {
    try {
      HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
      CMisSessionMgr sm = new CMisSessionMgr(request);
      String major = (String)sm.getSessionData("Major");
      //String empClass = (String)sm.getSessionData("EmployeeClass");
      String areaCode = (String)sm.getSessionData("AreaCode");
      String bankFlag = (String)sm.getSessionData("BankFlag");

      DBTools srv = new DBTools(new DummyOperation());
      String querySql =
        "SELECT TABLE_CODE,FIELD_CODE FROM MAG_ENPINFO_EMP_GRANT WHERE AREA_CODE=? AND EMPLOYEE_MAJOR=? AND ENP_CODE=? AND END_DATE >=TO_CHAR(CMISDATE,'yyyymmdd') AND STATE='0'";
      Vector params = new Vector(3);
      params.add(areaCode);
      params.add(major);
      //params.add(empClass);
      params.add(enpcode);
      Vector rawSrc = srv.executeSQLResultNoLimit(querySql, params);
      if (rawSrc.size() == 0) {
        params.set(2, "*");
        rawSrc = srv.executeSQLResultNoLimit(querySql, params);
      }

      if (rawSrc.size() == 0) {
        if (bankFlag.equals("4")) {
          String querySql2 =
            "SELECT TABLE_CODE, FIELD_CODE "
              + "FROM (SELECT DISTINCT TABLE_CODE,FIELD_CODE "
              + "FROM MAG_ENPINFO_EMP_GRANT "
              + "WHERE STATE = '1' AND EMPLOYEE_MAJOR = ? AND "
              + "ENP_CODE = ? AND END_DATE >= TO_CHAR(CMISDATE, 'yyyymmdd') AND "
              + "AREA_CODE IN "
              + "(SELECT AREA_CODE "
              + "FROM MAG_AREA "
              + "WHERE AREA_CODE <> ? "
              + "START WITH AREA_CODE = ? "
              + "CONNECT BY (AREA_CODE = PRIOR BELONG_BANK)))";
          Vector params2 = new Vector(4);
          params2.add(major);
          //params2.add(empClass);
          params2.add(enpcode);
          params2.add(areaCode);
          params2.add(areaCode);
          rawSrc = srv.executeSQLResultNoLimit(querySql2, params2);

          if (rawSrc.size() == 0) {
            params2.set(1, "*");
            rawSrc = srv.executeSQLResultNoLimit(querySql2, params2);
          }
        }
        else {
          String querySql3 =
            "SELECT COUNT(*) FROM MAG_ENPINFO_EMP_GRANT WHERE AREA_CODE=? AND EMPLOYEE_MAJOR=? AND (ENP_CODE=? OR ENP_CODE='*') AND END_DATE >=TO_CHAR(CMISDATE,'yyyymmdd')";
          Vector params3 = new Vector(3);
          params3.add(areaCode);
          params3.add(major);
          //params.add(empClass);
          params3.add(enpcode);
          String count = srv.executeSQL(querySql3, params3);
          if (count.equals("0")) {
            String querySql4 = "SELECT TABLE_CODE, FIELD_CODE FROM MAG_ENPINFO_GRANT_FIELD_DEFINE ";
            Vector params4 = new Vector();
			rawSrc = srv.executeSQLResultNoLimit(querySql4, params4);
          }
        }  
      }
	  authHash = reformatVector(rawSrc, "TABLE_CODE", "FIELD_CODE");
    }
    catch (Exception e) {
      throw new JspTagException(e.getMessage());
    }
    return EVAL_BODY_INCLUDE;
  }

  public void setEnpcode(String enpcode) {
    this.enpcode = enpcode;
  }

  Hashtable getAuthHashtable() {
    return authHash;
  }

  Hashtable reformatVector(Vector input, String key, String value) {
    Hashtable ht = new Hashtable();
    for (int i = 0; i < input.size(); i++) {
      Hashtable tmp = (Hashtable)input.get(i);
      Vector node = (Vector)ht.get(((String)tmp.get(key)).toUpperCase());
      if (node == null) {
        node = new Vector();
        ht.put(((String)tmp.get(key)).toUpperCase(), node);
      }
      node.add(((String)tmp.get(value)).toUpperCase());
    }
    return ht;
  }

}
