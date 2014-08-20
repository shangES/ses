package icbc.missign;

import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.TranFailException;
import icbc.cmis.base.SSICTool;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Menu implements Serializable {
  private Collection items = new ArrayList(150);
  private String menuName;

  private String baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath");


  public String getHtml(MenuItemUnit menuTree, Employee employee, boolean isSwitch) throws TranFailException{
    StringBuffer menuBuffer = new StringBuffer(5000);
	String lang_code = employee.getCurrentRole().getLangCode();
    //输出菜单
    menuBuffer.append("<HTML>");
    menuBuffer.append("<HEAD>");
    menuBuffer.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">");
    menuBuffer.append("<LINK rel=\"stylesheet\" type=\"text/css\" href=\"");
    menuBuffer.append(baseWebPath);
    if (lang_code.equals("en_US"))
		menuBuffer.append("/css/navigator_en_US.css\"/>");
    else
		menuBuffer.append("/css/navigator.css\"/>");
    
    menuBuffer.append("<SCRIPT src=\"");
    menuBuffer.append(baseWebPath);
    menuBuffer.append("/toggle.js\"></SCRIPT>");
    menuBuffer.append("</HEAD>");
    menuBuffer.append("<BODY leftmargin=\"0\" topmargin = \"0\" onload=\"showEmployee();\">");
    menuBuffer.append("");
    menuBuffer.append(
      "<DIV xmlns:xsl=\"http://www.w3.org/TR/WD-xsl\" id=\"MenuLayer\" style=\"position:absolute; left:0px; top:0px; width:180px; height:400px; z-index:1\">");
    menuBuffer.append("<TABLE width=\"180\" cellspacing=\"1\" cellpadding=\"0\">");
    menuBuffer.append("<TR><td class=\"title1\" height=\"16\"></td></TR>");
    menuBuffer.append("</TABLE>");

    //显示机构

    menuBuffer.append(employee.getBranchHtml());

    //显示系统
    menuBuffer.append(employee.getSystemsHtml());

    //显示专业,专业级别
    menuBuffer.append(employee.getMajorsHtml());
    menuBuffer.append(employee.getMajorClassHtml());

    menuBuffer.append(menuTree.getSpacer());
    //    menuBuffer.append("<TABLE width=\"180\" cellspacing=\"1\" cellpadding=\"0\">");
    //    menuBuffer.append("<TR><td class=\"title1\" height=\"16\"></td></TR>");
    //    menuBuffer.append("</TABLE>");

    //显示菜单
    menuBuffer.append(menuTree.toMenu(employee.getCurrentRole()));

    menuBuffer.append(menuTree.getSpacer());
//    menuBuffer.append("<TABLE width=\"180\" cellspacing=\"1\" cellpadding=\"0\">");
//    menuBuffer.append("<TR><td class=\"title1\" height=\"16\" ></td></TR>");
//    menuBuffer.append("</TABLE>");
    menuBuffer.append("</DIV>");
    menuBuffer.append("<form name=\"form1\" method=\"post\" action=\"" + baseWebPath + "/servlet/icbc.missign.GetMenu?isSwitch=true\">");
    menuBuffer.append("<input type=\"hidden\" name=\"sysCode\" value=\"\" />");
    menuBuffer.append("<input type=\"hidden\" name=\"major\" value=\"\" />");
    menuBuffer.append("<input type=\"hidden\" name=\"majorClass\" value=\"\" />");
    menuBuffer.append("<input type=\"hidden\" name=\"areaName\" value=\"" + employee.getAreaName() + "\" />");
    menuBuffer.append("<input type=\"hidden\" name=\"eName\" value=\"" + employee.getEmployeeName() + "\" />");

    menuBuffer.append("<input type=\"hidden\" name=\"sysName\" value=\"" + employee.getEmployeeSysName() + "\" />");

    menuBuffer.append("<input type=\"hidden\" name=\"majorName\" value=\"" + employee.getEmployeeMajorName() + "\" />");
    menuBuffer.append("<input type=\"hidden\" name=\"className\" value=\"" + employee.getEmployeeClassName() + "\" />");
    menuBuffer.append("</form>");
    menuBuffer.append("<form name=\"form2\"  target=\"mainFrame\" method=\"post\" action=\"" + baseWebPath + "/servlet/icbc.cmis.servlets.CmisReqServlet\">");
    menuBuffer.append("<input type=\"hidden\" name=\"operationName\" value=\"icbc.cmis.servlets.CmisMain\" />");
    menuBuffer.append("</form>");

    menuBuffer.append("<form name=\"switchForm\"  target=\"_parent\" method=\"post\" action=\"\">");
    menuBuffer.append("</form>");

    //add by yanbo 20040415
    menuBuffer.append(employee.getSysEntryScript(isSwitch));
    //end 20040415

    menuBuffer.append("</BODY>");
    menuBuffer.append("</HTML>");

    return menuBuffer.toString();
  }


}