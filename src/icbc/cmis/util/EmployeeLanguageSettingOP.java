/*
 * 创建日期 2007-5-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package icbc.cmis.util;

import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.base.*;
import java.util.*;
import java.sql.ResultSet;
import icbc.cmis.second.pkg.*;
import oracle.jdbc.driver.*;
import icbc.cmis.util.*;
/**
 * @author zjfh-liny
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class EmployeeLanguageSettingOP extends CmisOperation {
	private String webBasePath = (String) CmisConstance.getParameterSettings().get("webBasePath");
	private EmployeeLanguageSettingDao lang_dao = new EmployeeLanguageSettingDao(this);
	/* （非 Javadoc）
	 * @see icbc.cmis.operation.CmisOperation#execute()
	 */
	public void execute() throws Exception, TranFailException {
		// TODO 自动生成方法存根
		String action = getStringAt("opAction");
		if(action.toLowerCase().equals("query")){
		   query_language(); 
		   }
		if(action.toLowerCase().equals("setting")){
		   setting_language();
		   }  
	}
	
	private void query_language() throws Exception{
	    try{
	    
	    String employee_code = (String)this.getSessionData("EmployeeCode");
	    String employee_language = "";
		IndexedDataCollection langList = new IndexedDataCollection();
		
		employee_language = lang_dao.defaultLanguage(employee_code);
		langList = lang_dao.langAvailableList();
		
		langList.setName("querylist");
		if (isElementExist("querylist")) {
			removeDataField("querylist");
			}
		this.addIndexedDataCollection(langList);
		this.setFieldValue("languageName",employee_language);
		this.setReplyPage(webBasePath + "util/EmployeeLanguageSetting.jsp");
		setOperationDataToSession();
		}catch(Exception e){}
	}
	
	private void setting_language() throws Exception{
		String employee_code = (String)this.getSessionData("EmployeeCode");
		String newLanguage = (String)getStringAt("langList");
		this.setFieldValue("employee_code",employee_code);
		this.setFieldValue("new_language",newLanguage);
		try{
			
		DBProcedureParamsDef def = null;
		def = new DBProcedureParamsDef("pack_language_setting.proc_employee_langsetting");
		
		def.addInParam("employee_code");
		def.addInParam("new_language");
		def.addOutParam("ret_flag");
		def.addOutParam("ret_msg"); 
		def.setDBUserName("missign");
		def.setDBUserPassVerify( CmisConstance.getPassVerify("missign") );
		
		DBProcedureAccessService dbProcService =
			new DBProcedureAccessService(this);
		int returncode =
			dbProcService.executeProcedure(this.getOperationData(), def);
		
		dbProcService = null;
		
		String langCode = (String)this.getSessionData("LangCode");
		String e = this.getStringAt("ret_msg");
		if (returncode != 0) {

				throw new icbc.cmis.base.MuiTranFailException(
					"099993",
					"EmployeeLanguageSettingOP.setting_language() pack_language_setting.proc_employee_langsetting",
					langCode);
			} else {
				this.setFieldValue("okTitle", genMsg.getErrMsgByLang(langCode,"099997"));
				this.setFieldValue("okMsg", genMsg.getErrMsgByLang(langCode,"099997"));
				this.setFieldValue(
					"okReturn",
					"/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.EmployeeLanguageSettingOP&opAction=query");
				this.setReplyPage("/icbc/cmis/ok.jsp");
			}
		}catch(Exception ee){} 
	}

}
