package icbc.missign;

import java.net.*;
import icbc.cmis.operation.*;
import icbc.cmis.base.CmisConstance;
/**
 * 处理用户登录切换请求
 * @author Yanbo
 * @version 1.0
 */

public class SwitchLogin extends CmisOperation {

	public SwitchLogin() {
	}
	/**
	 * 处理用户登录请求
	 * 用户在login.jsp中输入柜员号、口令。
	 * 查询指定柜员号的用户信息并判断该用户是否有效；
	 * 有效，则转到指定的应用程序主页面；
	 * 无效，则转到登录失败页面。
	 *
	 * login.jsp必需提供以下信息：
	 * <li>柜员号accountCode
	 * <li>柜员口令passwd
	 * @throws Exception
	 */
	public void execute() throws java.lang.Exception {
		try {
			boolean authorized = false;
			String referringHost = (String)this.getSessionData("ReferringHost");
			String localHost = java.net.InetAddress.getLocalHost().getHostAddress();
			String baseWebPath = (String) CmisConstance.getParameterSettings().get("webBasePath");

			String accountCode = this.getStringAt("accountCode");
			String isSwitch = this.getStringAt("isSwitch");
            String selSysCode = this.getStringAt("selSysCode");
			String outAccountCode = this.getStringAt("outAccountCode");
            
            //取得到授权的引用的IP地址
            java.util.Enumeration e = CmisConstance.getAppEntryURLs();
    		
		    for(;e.hasMoreElements();){
			  String authorizedURL = (String)e.nextElement();
		
			//String fromAppEntry = this.getStringAt("FromAppCode");
			//String authorizedURL = (String) CmisConstance.getParameterSettings().get(fromAppEntry);
			  String authorizedHost = new URL(authorizedURL).getHost();
			  //比较是否得到授权
              if(authorizedHost.equals(referringHost) || referringHost.equals(localHost)){
			  	 authorized = true;
			  	 break;
			  }
		    }
			if(authorized){
			  LoginDAO dao = new LoginDAO(this);
			  Employee employee = dao.getEmployee(accountCode);
			  employee.setBranch(dao.getBranchDetail(outAccountCode));
			  updateSessionData("Employee",employee);
			  updateSessionData("AreaCode", employee.getMdbSID());
			  updateSessionData("AreaName", employee.getAreaName());
			  updateSessionData("BankFlag", employee.getBankFlag());
			  updateSessionData("EmployeeCode", employee.getEmployeeCode());
			  updateSessionData("EmployeeName", employee.getEmployeeName());
			  updateSessionData("ZhujiFlag", employee.getZhujiFlag());
			  updateSessionData("WorldFlag", employee.getWorldFlag());
              updateSessionData("isSwitch", isSwitch);
              updateSessionData("selSysCode", selSysCode);
			  updateSessionData("Login", "YES");
			  updateSessionData("LangCode", employee.getLangCode());
			  updateSessionData("ZoneCode", employee.getZoneCode());

			
			  setReplyPage(baseWebPath+"/logined.jsp");
			
			}
			else{
			  setReplyPage(baseWebPath+"/login.jsp");
			}
		}
		catch (Exception ex) {
			throw ex;
		}
	}
}