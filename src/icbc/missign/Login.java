package icbc.missign;

import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.*;
import icbc.cmis.base.CmisConstance;
import icbc.cmis.base.SSICTool;
import java.util.Vector;
/**
 * 处理用户登录请求
 * @author 叶晓挺
 * @version 1.0
 */

public class Login extends CmisOperation {

	public Login() {
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
			String baseWebPath = (String) CmisConstance.getParameterSettings().get("webBasePath");
			String enabledConfig = (String) CmisConstance.getParameterSettings().get("enableIDAndCryptoConfig");
            //boolean enabledConfig = false;
            boolean configedCrypto = true;
            String pwdCrypto = null;
			//统一认证入口
			if (SSICTool.isSSICEnabled()) {
		      //获取统一认证登陆时用的柜员号
              String accountCode = SSICTool.getSSICEmployeeCode(this);
              Vector branch = null;
              LoginDAO dao = new LoginDAO(this);
              //判断外部柜员号是否存在，主要目的是为了判明使用的登陆柜员的情况，包括是否停用、是否是内部柜员
              String outSideEmpCode = dao.isEmployeeExisted(accountCode);
              if(outSideEmpCode.equalsIgnoreCase("true")){            
              }
              else if(outSideEmpCode.equalsIgnoreCase("false"))
                throw new TranFailException("execute","Login.execute()","该柜员可能为内部柜员，还未挂入外部柜员！，不能登录！","该柜员可能为内部柜员，还未挂入外部柜员！不能登录！");
              else if(outSideEmpCode.equalsIgnoreCase("stop")){
                //如果与外部柜员＝内部柜员的柜员号被停用，则取其他可以用来登陆的柜员
                branch = dao.getBranchDetail(accountCode);
                if(branch.size()==0)
                  throw new TranFailException("execute","Login.execute()","该柜员已经被停用，不能登录！","该柜员已经被停用，不能登录！");
              }
              else
                throw new TranFailException("execute","Login.execute()","该柜员为内部柜员，请用该柜员对应的外部柜员"+outSideEmpCode+"登录系统！","该柜员为内部柜员，请用该柜员对应的外部柜员"+outSideEmpCode+"登录系统！");
              
              if(branch == null){
                branch = dao.getBranchDetail(accountCode);
              }
              
              //取机构集合中的第一个柜员作为登陆用的内部柜员
              Employee employee = null;
              if(branch.size()>0){
                 String[] branch1 = (String[])branch.get(0);                
                 employee = dao.getEmployee(branch1[0]);
              }
              else
			    throw new TranFailException("execute","Login.execute()","该柜员未被授予任何业务，不能登录！","该柜员未被授予任何业务，不能登录！");
              
              //设置柜员相关信息
              employee.setBranch(branch);
              employee.setOutsideEmpCode(accountCode);
              
              updateSessionData("Employee", employee);
              updateSessionData("AreaCode", employee.getMdbSID());
              updateSessionData("AreaName", employee.getAreaName());
              updateSessionData("BankFlag", employee.getBankFlag());
              updateSessionData("EmployeeCode", employee.getEmployeeCode());
              updateSessionData("EmployeeName", employee.getEmployeeName());
			  updateSessionData("ZhujiFlag", employee.getZhujiFlag());
			  updateSessionData("WorldFlag", employee.getWorldFlag());
			  updateSessionData("LangCode", employee.getLangCode());
			  updateSessionData("ZoneCode", employee.getZoneCode());
                //update by yanbo 20040429 for 一人多机构
                //获取该柜员所有的机构
//                Vector branch = dao.getBranchDetail(accountCode);
//                employee.setBranch(branch);
                
                //updateSessionData("insideEmpDefault",branch);
              updateSessionData("Login", "YES");

              setReplyPage(baseWebPath+"/logined.jsp");
             
              
              return;
            }
            
            //非统一认证入口
		    String accountCode = this.getStringAt("accountCode");
			
            String passwd = this.getStringAt("passwd");
            Vector branch = null;
			LoginDAO dao = new LoginDAO(this);
//		判断外部柜员号是否存在，主要目的是为了判明使用的登陆柜员的情况，包括是否停用、是否是内部柜员
            String outSideEmpCode = dao.isEmployeeExisted(accountCode);
			if(outSideEmpCode.equalsIgnoreCase("false"))
              throw new TranFailException("execute","Login.execute()","该柜员可能为内部柜员，还未挂入外部柜员！，不能登录！","该柜员可能为内部柜员，还未挂入外部柜员！不能登录！");
            else if(outSideEmpCode.equalsIgnoreCase("stop")){
//		如果与外部柜员＝内部柜员的柜员号被停用，则取其他可以用来登陆的柜员
                branch = dao.getBranchDetail(accountCode);
                if(branch.size()==0)
                  throw new TranFailException("execute","Login.execute()","该柜员已经被停用，不能登录！","该柜员已经被停用，不能登录！");  
            }
            else if(!outSideEmpCode.equalsIgnoreCase("true"))
              throw new TranFailException("execute","Login.execute()","该柜员为内部柜员，请用该柜员对应的外部柜员"+outSideEmpCode+"登录系统！","该柜员为内部柜员，请用该柜员对应的外部柜员"+outSideEmpCode+"登录系统！");
            
            Employee employee = null;//dao.getEmployee(accountCode);
			
            //设置统一认证密文(目的是为了在统一认证移行时，使用统一认证的密码校验)
//            if(enabledConfig.equals("true")){
//              pwdCrypto = SSICTool.genSSICpass(passwd);
//              configedCrypto = dao.configPwdCrypto(accountCode, pwdCrypto);
//              
//            }
//		取机构集合中的第一个柜员作为登陆用的内部柜员
            if(branch == null){
              branch = dao.getBranchDetail(accountCode);
            }
            if(branch.size()>0){
              String[] branch1 = (String[])branch.get(0);
              //if(!branch1[0].equals(accountCode)){
                employee = dao.getEmployee(branch1[0]);
				if(!employee.isValid(passwd)) {
					throw new TranFailException("cmisLogin001", "icbc.missign.Login", employee.getInvaildInfo(), employee.getInvaildInfo());			
				}
				employee.setBranch(branch);
            
				employee.setOutsideEmpCode(accountCode);
              //}
				//设置统一认证密文(目的是为了在统一认证移行时，使用统一认证的密码校验)
				if(enabledConfig.equals("true")){
					pwdCrypto = SSICTool.genSSICpass(passwd);
					configedCrypto = dao.configPwdCrypto(employee.getEmployeeCode(), pwdCrypto);
              
				}
            }
			else
			  throw new TranFailException("execute","Login.execute()","该柜员已经被停用，不能登录！","该柜员已经被停用，不能登录！");
            
            
              
            //updateSessionData("insideEmpDefault",branch);
            
            //设置柜员信息
            updateSessionData("Employee",employee);
			updateSessionData("AreaCode", employee.getMdbSID());
			updateSessionData("AreaName", employee.getAreaName());
			updateSessionData("BankFlag", employee.getBankFlag());
			updateSessionData("EmployeeCode", employee.getEmployeeCode());
			updateSessionData("EmployeeName", employee.getEmployeeName());
			updateSessionData("ZhujiFlag", employee.getZhujiFlag());
			updateSessionData("WorldFlag", employee.getWorldFlag());
			updateSessionData("LangCode", employee.getLangCode());
			updateSessionData("ZoneCode", employee.getZoneCode());
//            Vector branch = dao.getBranchDetail(accountCode);
//            employee.setBranch(branch);
//            employee.setOutsideEmpCode(employee.getOutsideEmpCode());
            //updateSessionData("insideEmpDefault",branch);
			updateSessionData("Login", "YES");

			if(!configedCrypto){
              this.addSessionData("pwdCrypto",pwdCrypto);
              setReplyPage(baseWebPath+"/util/util_IDAndCryptoConfig.jsp");
            }
            else {
				setReplyPage(baseWebPath+"/logined.jsp");
			}
		}
		catch (Exception ex) {
			throw ex;
		}
	}
}