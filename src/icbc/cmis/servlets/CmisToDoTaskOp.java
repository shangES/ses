package icbc.cmis.servlets;

import icbc.cmis.operation.*;
import icbc.missign.*;
import icbc.cmis.base.*;
//import icbc.cmis.servlets.CmisMainDAO2;
import java.util.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author yxt
 * @version 1.0
 */

public class CmisToDoTaskOp extends CmisOperation {
  String opAction = null;

  public CmisToDoTaskOp() {}
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
        this.removeAllSessionData();
        addSessionData("Employee", employee);
        addSessionData("AreaCode", employee.getMdbSID());
        addSessionData("AreaName", employee.getAreaName());
        addSessionData("BankFlag", employee.getBankFlag());
        addSessionData("EmployeeCode", employee.getEmployeeCode());
        addSessionData("EmployeeName", employee.getEmployeeName());
        addSessionData("EmployeeClass", employee.getEmployeeClass());
        addSessionData("EmployeeClassName", employee.getEmployeeClassName());
        addSessionData("Major", employee.getEmployeeMajor());
        addSessionData("MajorName", employee.getEmployeeMajorName());
        addSessionData("ZhujiFlag", employee.getZhujiFlag());
        addSessionData("WorldFlag", employee.getWorldFlag());
        addSessionData("Login", "YES");
        addSessionData("CustomerCodeInSession", customerCode);
        addSessionData("CustomerNameInSession", customerName);
		updateSessionData("LangCode", employee.getLangCode());
        setReplyPage("/main.jsp");					
      }
      else if (opAction.equals("check")) {
        try {
          String area_code = (String)this.getSessionData("AreaCode");
          String employee_code = (String)this.getSessionData("EmployeeCode");
          String major = this.getStringAt("majorcode");
          String bankFlag = (String)this.getSessionData("BankFlag");
          String cls = (String)this.getSessionData("EmployeeClass");
          String func = this.getStringAt("func");
          MenuDAO menuDao = new MenuDAO(this);
          String menuArea = menuDao.getMenuArea(area_code, major);
          CmisMainDAO mainDao = new CmisMainDAO(this);
          int checker = mainDao.checkFunc(func, menuArea, bankFlag, major, cls, employee_code, area_code);

          setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>" + checker + "</info>");
        }
        catch (Exception e) {
		  setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>-1</info>");
        }
      }
	  else if (opAction.equals("changeLang")) {
		try {
			String oriLang = (String)this.getStringAt("oriLang");
			String tarLang = null;
			if(oriLang.equals("en_US"))
			  tarLang = "zh_CN";
			else
			  tarLang = "en_US";
			Employee employee = (Employee)this.getSessionData("Employee");
			employee.setLangCode(tarLang);
			Collection collection = employee.getRoles();
			Iterator iterator = collection.iterator();
			while(iterator.hasNext()){
			  Role role = (Role)iterator.next();
			  role.setLangCode(tarLang);
			}
			this.updateSessionData("LangCode",tarLang);
			
			LoginDAO dao = new LoginDAO(this);
			dao.setEmployeeRole(employee);
			
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>1</info>");
		}
		catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>-1</info>");
		}
	  }
    }
    catch (TranFailException ex) {
      throw ex;
    }
    catch (Exception ex) {
      throw new TranFailException("cmismain001", "icbc.cmis.servlets.CmisMain", ex.getMessage());
    }
  }
}