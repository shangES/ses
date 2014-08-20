package icbc.cmis.util;

import icbc.cmis.operation.*;
import icbc.cmis.base.*;
import java.util.*;
import java.sql.*;
import icbc.missign.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class UtilEmpFuncMag extends CmisOperation {

  private SqlTool sqltool= null;
private String baseWebPath = (String) CmisConstance.getParameterSettings().get("webBasePath");
  private void init() throws Exception{
    sqltool = new SqlTool(this);
  }

  public UtilEmpFuncMag() {
  }
  public void execute() throws java.lang.Exception {
    /**@todo: implement this icbc.cmis.operation.CmisOperation abstract method*/
    try{

      String action = getStringAt("act");
      if(action.equals("queryEmpMajor")){
        queryEmpMajor();
      }
      else if(action.equals("displayFunc")){
        displayFunc();
      }
      else if(action.equals("updateFunc")){
        updateFunc();
      }
    }
    catch(Exception e){
      try{
        sqltool.closeconn();
      }
      catch(Exception e2){}
      throw e;
    }


  }
  private void queryEmpMajor() throws Exception{
    String login_area_code = (String)this.getSessionData("AreaCode");
    String emp_code = this.getStringAt("emp_code");
    String sql1 = "select count(*) from mag_employee_major,MAG_MAJOR, MAG_EMPLOYEE_CLASS where employee_code ='"+emp_code+"'and employee_area ='"+login_area_code+"'and EMPLOYEE_MAJOR = MAJOR_CODE and EMPLOYEE_CLASS = CLASS_CODE";

    String sql = "select EMPLOYEE_MAJOR,MAJOR_NAME, EMPLOYEE_CLASS, CLASS_NAME from mag_employee_major,MAG_MAJOR, MAG_EMPLOYEE_CLASS where employee_code ='"+emp_code+"'and employee_area ='"+login_area_code+"'and EMPLOYEE_MAJOR = MAJOR_CODE and EMPLOYEE_CLASS = CLASS_CODE";
    init();
    sqltool.getConn();
    ResultSet rs1 =sqltool.executeQuery(sql1);
    rs1.next();
    int row_num = rs1.getInt(1);
    if(row_num == 0){
      sqltool.closeconn();
      rs1.close();
        throw new TranFailException("xdtzutil999","UtilEmpFuncMag.queryEmpMajor()","此人在该地区没有业务授权！");
    }
    rs1.close();
    ResultSet rs=null;
    rs = sqltool.executeQuery(sql);
      Vector majors = new Vector();
      while(rs.next()) {
        String[] major = {rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)};
        majors.add(major);
      }
    rs.close();
    sqltool.closeconn();
    try{
      this.removeDataField("emp_majorUEFM");
    }
    catch(Exception e){}
    this.addDataField("emp_majorUEFM",majors);
    this.setOperationDataToSession();
    this.setReplyPage(baseWebPath + "/util/util_EmployeeFuncDisplayMajor.jsp");
  }
  private void displayFunc() throws Exception{
    String emp_code= getStringAt("emp_code");
    String emp_major= getStringAt("emp_major");
    LoginDAO dao = new LoginDAO(this);
    Employee employee = dao.getEmployee(emp_code);
    String menu="";
    if(emp_major != null && !emp_major.equals("")) {
      MenuDAO2 dao2 = new MenuDAO2(this);
//      menu = dao2.getMenuXML(employee,emp_major);


    }
    try{
      this.removeDataField("str_menu");
    }
    catch(Exception e){

    }
    this.addDataField("str_menu",menu);
    this.setOperationDataToSession();

    setReplyPage(baseWebPath + "/util/util_EmployeeFuncDisplay.jsp");
    //display from yxt
/*      out.println("<HTML>");
      out.println("<HEAD>");
      out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">");
      out.println("<LINK rel=\"stylesheet\" type=\"text/css\" href=\baseWebPath + "/css/navigator.css\"/>");
      out.println("<SCRIPT src=\baseWebPath + "/toggle.js\"></SCRIPT>");
      out.println("</HEAD>");
      out.println("<BODY leftmargin=\"0\" topmargin = \"0\" onload=\"showEmployee();\">");
      out.println("");
      out.println("<DIV xmlns:xsl=\"http://www.w3.org/TR/WD-xsl\" id=\"MenuLayer\" style=\"position:absolute; left:0px; top:0px; width:180px; height:400px; z-index:1\">");
      out.println("<TABLE width=\"180\" cellspacing=\"1\" cellpadding=\"0\">");
      out.println("<TR><td class=\"title1\" height=\"16\"></td></TR>");
      out.println("</TABLE>");
      out.println("    <font color=\"#FFFFFF\"><b>业务</b></font><select name=\"smajor\" style=\"width:110px\">");
      out.println(options.toString());
      out.println("    </select><img src=\baseWebPath + "/images/go.gif\" STYLE=\"cursor:hand\" width=\"20\" height=\"20\" onclick=\"changeMajor();\" />");
      out.println("<TABLE width=\"180\" cellspacing=\"1\" cellpadding=\"0\">");
      out.println("<TR><td class=\"title1\" height=\"16\"></td></TR>");
      out.println("</TABLE>");
      out.println("<TABLE width=\"180\" cellspacing=\"1\" cellpadding=\"0\">");
      out.println(menu);
      out.println("</TR>");
      out.println("<TR TITLE=\"签退\" Class=\"Navigator\" ID=\"240\" AncestorID=\"0\" Depth=\"0\">");
      out.println("<TD STYLE=\"cursor:hand\"  class=\"title\">");
      out.println("<DIV noWrap=\"true\" STYLE=\"padding-left:0em;\">");
      out.print(  "<img src=\baseWebPath + "/images/dot.gif\">");
      out.println("<a href =\"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=LogoutOp\" target=\"_top\")\">签退</A>");
      out.println("</DIV>");
      out.println("</TD>");
      out.println("</TR>");
      out.println("</TABLE>");
      out.println("<TABLE width=\"180\" cellspacing=\"1\" cellpadding=\"0\">");
      out.println("<TR><td class=\"title1\" height=\"16\" ></td></TR>");
      out.println("</TABLE>");
      out.println("</DIV>");
      out.println("<form name=\"form1\" method=\"post\" action=\"/servlet/icbc.missign.GetMenu\">");
      out.println("<input type=\"hidden\" name=\"major\" value=\"\" />");
      out.println("<input type=\"hidden\" name=\"eName\" value=\"" + employee.getEmployeeName() + "\" />");
      out.println("<input type=\"hidden\" name=\"majorName\" value=\"" + employee.getEmployeeMajorName() + "\" />");
      out.println("<input type=\"hidden\" name=\"className\" value=\"" + employee.getEmployeeClassName() + "\" />");
      out.println("</form>");
      out.println("<form name=\"form2\"  target=\"mainFrame\" method=\"post\" action=\"\">");
      out.println("</form>");
      out.println("</BODY>");
      out.println("</HTML>");*/
    //display end

  }
  private void updateFunc() throws Exception{
    try{
      String forbid_id = getStringAt("forbid_id");
      String emp_code  = getStringAt("emp_code");
      String emp_major = getStringAt("emp_major");
      String area_code = (String)this.getSessionData("AreaCode");
      String sql = "update mag_employee_major set mag_func='"+forbid_id+"' where employee_code='"+emp_code+"' and employee_major='"+emp_major+"' and employee_area='"+area_code+"'";

      init();
      sqltool.getConn();
      if(sqltool.executeUpdate(sql)==1){
        sqltool.commit();
        sqltool.closeconn();
        this.setFieldValue("okTitle","操作成功！");
        this.setFieldValue("okMsg","柜员功能修改成功！");
        this.setFieldValue("okReturn",baseWebPath + "/util/util_EmployeeFuncMag.jsp");
        this.setReplyPage(baseWebPath + "/ok.jsp");
      }
      else{
        sqltool.closeconn();
        throw new TranFailException("xdtzutil998","UtilEmpFuncMag.updateFunc()","柜员功能修改失败！");
      }
    }
    catch(Exception e){
      try{
        sqltool.closeconn();
      }
      catch(Exception e1){}
      throw e;
    }

  }
}