package icbc.cmis.servlets;

import icbc.cmis.operation.*;
import icbc.missign.Employee;
import icbc.missign.MenuDAO;
import icbc.cmis.base.*;
//import icbc.cmis.servlets.CmisMainDAO2;
import java.util.Vector;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author yxt
 * @version 1.0
 */

public class CmisMain extends CmisOperation {
  String opAction = null;

  public CmisMain() {}
  public void execute() throws java.lang.Exception {
    try {
      try {
        opAction = this.getStringAt("opAction");
      }
      catch (Exception e) {
        opAction = "query";
      }
      if (opAction.equals("query")) {

        Employee employee = (Employee)this.getSessionData("Employee");
        String customerCode = "";
        try {
          customerCode = (String)this.getSessionData("CustomerCodeInSession");
        }
        catch (Exception ex) {}
        String customerName = "";
        try {
          customerName = (String)this.getSessionData("CustomerNameInSession");
        }
        catch (Exception ex) {}

        try {
          this.removeDataField("showNotice");
        }
        catch (Exception ex) {}
        //updated by WuQQ,20030409
        String ifAutoQuery = "true";
        try {
          //如果是main.jsp页面发起的查询请求，ifAotuQuery 位 false
          ifAutoQuery = (String)this.getStringAt("ifAutoQuery");
        }
        catch (Exception ex) {}

        try {
          //如果是main.jsp页面发起的待处理业务查询请求，才进行查询。
          //登陆后以及返回主页都不进行待处理业务查询
          if (ifAutoQuery.equals("false")) {
            CmisMainDAO dao = new CmisMainDAO(this);
            Vector process = dao.getProcess(employee);
            this.setFieldValue("Process", process); //查询待处理业务
            this.setFieldValue("batchDate", dao.getBatchDate(employee)); //查询批量日期
          }

        }
        catch (TranFailException ex) {
          throw ex;
        }

        employee = (Employee)this.getSessionData("Employee");
        //this.removeAllSessionData();
        updateSessionData("Employee", employee);
		updateSessionData("AreaCode", employee.getMdbSID());
		updateSessionData("AreaName", employee.getAreaName());
		updateSessionData("BankFlag", employee.getBankFlag());
		updateSessionData("EmployeeCode", employee.getEmployeeCode());
		updateSessionData("EmployeeName", employee.getEmployeeName());
		updateSessionData("EmployeeClass", employee.getEmployeeClass());
		updateSessionData("EmployeeClassName", employee.getEmployeeClassName());
		updateSessionData("Major", employee.getEmployeeMajor());
		updateSessionData("MajorName", employee.getEmployeeMajorName());
		updateSessionData("ZhujiFlag", employee.getZhujiFlag());
		updateSessionData("WorldFlag", employee.getWorldFlag());
		updateSessionData("LangCode", employee.getLangCode());
		updateSessionData("ZoneCode", employee.getZoneCode());
		updateSessionData("Login", "YES");
		updateSessionData("CustomerCodeInSession", customerCode);
		updateSessionData("CustomerNameInSession", customerName);

        setReplyPage("/main.jsp");
      }
//      else if (opAction.equals("check")) {
//        String area_code = (String)this.getSessionData("AreaCode");
//        String major = this.getStringAt("majorcode");
//        String bankFlag = (String)this.getSessionData("BankFlag");
//        String cls = (String)this.getSessionData("EmployeeClass");
//        String func = this.getStringAt("func");
//        MenuDAO menuDao = new MenuDAO(this);
//        String menuArea = menuDao.getMenuArea(area_code, major);
//        CmisMainDAO mainDao = new CmisMainDAO(this);
//        int checker = mainDao.checkFunc(func, menuArea, bankFlag, major, cls);
//
//        setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>" + checker + "</info>");
//      }
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("cmismain001", "icbc.cmis.servlets.CmisMain", ex.getMessage());
    }
  }
}