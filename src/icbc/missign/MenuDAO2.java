package icbc.missign;

import icbc.cmis.service.CmisDao;
import java.lang.StringBuffer;
import java.sql.*;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Enumeration;
import icbc.cmis.base.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class MenuDAO2 extends CmisDao {

  private Vector forbidFuncId=null;

  public MenuDAO2(icbc.cmis.operation.CmisOperation op) {
  super(op);
  }
   public void menuItem(String[] row, int level, StringBuffer menu) {
        String id = row[0];
        String name = row[1];
        String pid = row[2];
        String baseWebPath = (String) CmisConstance.getParameterSettings().get("webBasePath");
        int child = Integer.valueOf(row[3]).intValue();

        menu.append("<TR TITLE=\"");
        menu.append(name);
        menu.append("\" Class=\"Navigator");
        menu.append("\" IDD=\"");
        menu.append(id);
        menu.append("\" AncestorID=\"");
        menu.append(pid);
        menu.append("\" Depth=\"");
        menu.append(level);
        if(child > 0) {
          menu.append("\" Expanded=\"no");
        }
        menu.append("\"><TD colspan=\"2\"  class=\"title\">");
        menu.append("<DIV noWrap=\"true\" STYLE=\"padding-left:");
        menu.append(level);
        menu.append("em;\">");
        menu.append("<img src=\"" + baseWebPath + "/images/open.gif\">");
        menu.append("<INPUT TYPE=\"checkbox\" NAME=\"id_check\"");
        menu.append("onclick=\"javascript:check_item_chkeck('");
        menu.append(id);
        menu.append("');\" value=\"");
        menu.append(id);
        menu.append("\" ID=\"");
        menu.append(id);
        menu.append("\" AncestorID=\"");
        menu.append(pid);
        menu.append("\"");
        if(isForbid(id)){
          menu.append("/>");
        }
        else{
          menu.append(" CHECKED />");
        }
        if(child == 0) {
          menu.append("<A STYLE=\"cursor:hand\"");
          menu.append(">");


          menu.append(name);

        }else{

          menu.append(name);
        }
        menu.append("</DIV></TD></TR>");
  }

  /**
   * 产生一层菜单
   * @param pstmt
   * @param level
   * @param pcode
   * @param menu
   * @throws TranFailException
   */
  public void getOneLevel(PreparedStatement pstmt,int level, String pcode,StringBuffer menu)  throws TranFailException {
    ResultSet rs = null;
    Vector rows = new Vector();

    try {
      pstmt.setString(2,pcode);
      rs = pstmt.executeQuery();
      while(rs.next()) {
        String[] row = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)};
        rows.add(row);
      }
      try { if(rs != null) rs.close(); } catch (Exception ex) {}
      if(!rows.isEmpty()) {
        for (int i = 0; i < rows.size(); i++) {
          String[] row = (String[])rows.get(i);
          menuItem(row,level,menu);
          getOneLevel(pstmt,level + 1,row[0],menu);
        }
      }
    } catch(TranFailException ex) {
      throw ex;
    } catch(Exception ex) {
      throw new TranFailException("menu003","icbc.missign.MenuDAO2.getOneLevel",ex.getMessage(),ex.getMessage());
    }

  }
  public String getMenuXML(Employee employee, String major, String mClass ,String area_code) throws TranFailException {
    Statement stmt = null;
    ResultSet rs = null;
    String sql = null;
    PreparedStatement pstmt = null;
    StringBuffer menu = new StringBuffer(5000);
    forbidFuncId = new Vector();
    String application = (String)CmisConstance.getParameterSettings().get("application");
	StringTokenizer token = new StringTokenizer(application,"|");
    try {
      String langCode = (String)getOperation().getSessionData("LangCode");
      //建立连接
	  this.getConnection("missign");
      stmt = conn.createStatement();
      //取指定柜员、指定专业的功能定义、专业名称、柜员级别、级别名称等信息
      sql = "select EMPLOYEE_FUNC,MAJOR_NAME,EMPLOYEE_CLASS, CLASS_NAME,CAN_SCAN from mag_employee_major,mag_major,MAG_EMPLOYEE_CLASS where employee_area = '" + employee.getMdbSID()
          + "' and employee_code = '" + employee.getEmployeeCode() + "' and employee_major = '" + major + "' and employee_class = '"+ mClass +
         "' and EMPLOYEE_MAJOR = mag_major.MAJOR_CODE and EMPLOYEE_CLASS = CLASS_CODE and MAG_EMPLOYEE_CLASS.MAJOR_CODE = EMPLOYEE_MAJOR and mag_major.lang_code='"+langCode+"' and MAG_EMPLOYEE_CLASS.lang_code='"+langCode+"'";
      rs = stmt.executeQuery(sql);
      if(!rs.next()) {
        throw new MuiTranFailException ("099993","icbc.missign.MenuDAO2.getMenuXML",(String)getOperation().getSessionData("LangCode"));//"取用户功能定义出错！"
      }
      //组织禁用模块列表
      String func = rs.getString(1);
      String forbid = "";
      if(func != null && func.length() > 0) {
        StringTokenizer stk = new StringTokenizer(func,",");
        while (stk.hasMoreTokens()) {
          String ts = stk.nextToken();
          forbid += ",'" + ts + "'";
          forbidFuncId.addElement(ts);
        }
        if (forbid.length() > 0) {
          forbid = " and APP_MODULE_CODE not in (" + forbid.substring(1) + ") ";
        }
      }
      //设定柜员信息
      int eClass = rs.getInt(3);
      employee.setCurrentRole(major,Integer.parseInt(rs.getString(3)));
      employee.setCanScan(rs.getString(5));

      //组织菜单查询语句
      //                 1             2            3              4
      sql = "select APP_MODULE_CODE, FUNC_NAME, APP_PMODULE_CODE, FUNC_SUB_NODE from MAG_APPLICATION_NEW, MAG_FUNCTION where lang_code='"+langCode+"'and APPLICATION in ("; 
      String subSql = "";
      while (token.hasMoreTokens()) {
		subSql += ",'" + token.nextToken() + "'";	
	  }
 	  sql += subSql.substring(1) + ") and APP_MAJOR_CODE = ? and APP_PMODULE_CODE = ? and "; 
      if (employee.getCanScan().equals("0")) {
        sql += "substr(APP_PRIVILEGE,?,1) = '1'";
      }
      else {//APP_CLASS的第4位为扫描权限
        sql += "(substr(APP_PRIVILEGE,?,1) = '1' or substr(APP_PRIVILEGE,4,1) = '1')";
      }
      sql += " and substr(APP_CLASS,? + 1,1) = '1' and APP_MODULE_CODE = FUNC_CODE and AREA_CODE = ? order by APP_ORDER";

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(3,rs.getString(3));
      pstmt.setString(4,employee.getBankFlag());
	  pstmt.setString(5,area_code);
      pstmt.setString(1,major);
      getOneLevel(pstmt,0,"00000",menu);
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("menu002","icbc.missign.MenuDAO2.getMenuXML",ex.getMessage(),ex.getMessage());
    }
    finally {
      try { if(rs != null) rs.close(); } catch (Exception ex) {}
      try { if(stmt != null) rs.close(); } catch (Exception ex) {}
      try { if(pstmt != null) rs.close(); } catch (Exception ex) {}
      this.closeConnection();
    }
    return menu.toString();
  }

  public String getMenuXML(Employee employee, String major ,String area_code) throws TranFailException {

    ResultSet rs = null;
    String sql = null;
    PreparedStatement pstmt = null;
    StringBuffer menu = new StringBuffer(5000);
    forbidFuncId = new Vector();
    String application = (String)CmisConstance.getParameterSettings().get("application");
    StringTokenizer token = new StringTokenizer(application,"|");

    try {
    	String langCode=(String)getOperation().getSessionData("LangCode");
      //建立连接
     this.getConnection("missign");
      //取指定柜员、指定专业的功能定义、专业名称、柜员级别、级别名称等信息
      sql = "select EMPLOYEE_FUNC,MAJOR_NAME,EMPLOYEE_CLASS, CLASS_NAME, CAN_SCAN from mag_employee_major,mag_major,MAG_EMPLOYEE_CLASS " +      	"where mag_major.lang_code='"+langCode+"' and MAG_EMPLOYEE_CLASS.lang_code='"+langCode+"'and employee_area = ?"
          + " and employee_code = ? and employee_major = ? and EMPLOYEE_MAJOR = mag_major.MAJOR_CODE and EMPLOYEE_CLASS = CLASS_CODE ";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, employee.getMdbSID());
      pstmt.setString(2, employee.getEmployeeCode());
      pstmt.setString(3, major);
      rs = pstmt.executeQuery();
      if(!rs.next()) {
        throw new MuiTranFailException("100543","icbc.missign.MenuDAO.getMenuXML",(String)getOperation().getSessionData("LangCode"));
      }
      //组织禁用模块列表
      String func = rs.getString(1);
      String forbid = "";
      if(func != null && func.length() > 0) {
        StringTokenizer stk = new StringTokenizer(func,",");
        while (stk.hasMoreTokens()) {
          String ts = stk.nextToken();
          forbid += ",'" + ts + "'";
          forbidFuncId.addElement(ts);
        }
        if (forbid.length() > 0) {
          forbid = " and APP_MODULE_CODE not in (" + forbid.substring(1) + ") ";
        }
      }
      //设定柜员信息
      int eClass = rs.getInt(3);
      employee.setCurrentRole(major,Integer.parseInt(rs.getString(3)));
      employee.setCanScan(rs.getString(5));

      //组织菜单查询语句
      //                 1             2            3              4
      sql = "select APP_MODULE_CODE, FUNC_NAME, APP_PMODULE_CODE, FUNC_SUB_NODE from MAG_APPLICATION_NEW, MAG_FUNCTION where lang_code='"+langCode+"'and APPLICATION in (";
      String subSql = "";
      while (token.hasMoreTokens()) {
        subSql += ",'" + token.nextToken() + "'";   
      }
      sql += subSql.substring(1) + ") and APP_MAJOR_CODE = ? and APP_PMODULE_CODE = ? and ";//substr(APP_PRIVILEGE,?,1) = '1' and substr(APP_CLASS,? + 1,1) = '1' and APP_MODULE_CODE = FUNC_CODE order by APP_ORDER";
     if (employee.getCanScan().equals("0")) {
        sql += "substr(APP_PRIVILEGE,?,1) = '1'";
      }
      else {//APP_CLASS的第4位为扫描权限
        sql += "(substr(APP_PRIVILEGE,?,1) = '1' or substr(APP_PRIVILEGE,4,1) = '1')";
      }
      sql += " and substr(APP_CLASS,? + 1,1) = '1' and APP_MODULE_CODE = FUNC_CODE and AREA_CODE = ? order by APP_ORDER";

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(3,rs.getString(3));
      pstmt.setString(4,employee.getBankFlag());
	  pstmt.setString(5,area_code);
      pstmt.setString(1,major);
      getOneLevel(pstmt,0,"00000",menu);
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("menu002","icbc.missign.MenuDAO.getMenuXML",ex.getMessage(),ex.getMessage());
    }
    finally {
      try { if(rs != null) rs.close(); } catch (Exception ex) {}
      try { if(pstmt != null) rs.close(); } catch (Exception ex) {}
      this.closeConnection();
    }
    return menu.toString();
  }

  private boolean isForbid(String id){
    Enumeration ids=(Enumeration)forbidFuncId.elements();
    while(ids.hasMoreElements()){

      String forbidId =(String)ids.nextElement();
      if(id.equals(forbidId)) return true;

    }
    return false;
  }

}