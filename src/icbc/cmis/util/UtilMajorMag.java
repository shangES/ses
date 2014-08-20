package icbc.cmis.util;

import icbc.cmis.operation.*;
import icbc.cmis.base.*;
import java.util.*;
import java.sql.*;
import icbc.missign.*;
import icbc.cmis.util.Decode;

/**
 * Title: 业务功能裁减
 * Description: 包括对业务功能的增删改
 * Modify History:
 *   20020730  cmis，bmis，vmis都会调用本模块，在请求中增加application参数区分不同的应用；
 *   20030423  广林在每次的增删改操作中增加了setDictUpdatetMark 方法，能由线程及时更新修改的菜单内容；
 *
 *	 20060228 1．增加是否有效的修改功能。
				a.当总模版设置成无效时，需要同时更新各行的相同功能编号的有效性。
				b.当总模版设置成有效时，其余各行的模板维持原状。
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author  金智伟 完成于2002-04-27
 * 			 夏春香 修改于2006-02-28
 * @version 1.0
 */

public class UtilMajorMag extends CmisOperation {

	public UtilMajorMag() {
		super();
	}

	public void execute() throws java.lang.Exception {
		/**@todo: implement this icbc.cmis.operation.CmisOperation abstract method*/
		try {
			String action = getStringAt("opAction");

			if (action.equals("displayMajorFunc")) {
				displayMajorFunc();
			}
			else if (action.equals("enterSelectPage")) { //进入新增选择页面
				enterSelectPage();
			}
			else if (action.equals("querySelectModule")) { //查询供用户选择的模块
				querySelectModule();
			}
			else if (action.equals("MovePageSelectFunc")) { //选择页面翻页
				xmlizeSelectFunc();
			}
			else if (action.equals("enterAddSetPage")) { //进入新增模块权限设置页面
				enterAddSetPage();
			}
			else if (action.equals("addFunc")) { //新增模块
				addFunc();
			}
			else if (action.equals("deleteFunc")) { //删除模块
				deleteFunc();
			}
			else if (action.equals("enterUpdatePage")) { //进入修改页面
				enterUpdatePage();
			}
			else if (action.equals("updateFunc")) {
				updateFunc();
			}
		}
		catch (TranFailException te) {
			this.setOperationDataToSession();
			throw te;
		}
		catch (Exception e) {
			this.setOperationDataToSession();
			throw new TranFailException("100534", "UtilMajorMag.execute()", e.getMessage(), genMsg.getErrMsgByLang((String)getSessionData("LangCode"), "100534"));//业务功能裁减模块错误
		}
	} 

	/**
	 * 根据业务代码，查询该业务下的所有模块、子模块的权限
	 * @throws Exception
	 */
	private void displayMajorFunc() throws Exception {
		try {
			String major_code = getStringAt("major_code");
			String sAppName = getStringAt("application"); //区别应用（cmis，bmis，vmis）的名字
		String area_code = "00000000"; //总行
			String menu = "";
			if (major_code != null && !major_code.equals("")) {
				MenuDAO3 dao3 = new MenuDAO3(this);
				menu = dao3.getMenuXML(major_code, area_code);
			}
			try {
				//this.removeDataField("empClassVector");
				this.removeDataField("str_menu");
			}
			catch (Exception e) {}
			//this.addDataField("empClassVector",empClassVector);
			this.addDataField("str_menu", menu);
			this.setOperationDataToSession();
			setReplyPage("/M/MA/MA_MajorFuncMag.jsp");
			//display from yxt
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("100535", "UtilMajorMag.displayMajorFunc()", e.getMessage(), genMsg.getErrMsgByLang((String)getSessionData("LangCode"), "100535"));
		}
	}

	/**
	 * 进入用户选择新增页面
	 * @throws Exception
	 */
	private void enterSelectPage() throws Exception {
		this.setOperationDataToSession();
		this.setReplyPage("/M/MA/MA_MajorFuncSelectList.jsp");
	}

	/**
	 * 选择出同一层次的并且还没有选上的功能模块，供用户选择新增
	 * @throws Exception
	 */
	private void querySelectModule() throws Exception {
		SqlTool tool = new SqlTool(this);
		try {
			String sMajorCode = this.getStringAt("major_code");
			String sModuleId = this.getStringAt("ModuleId");
			String sPModuleId = this.getStringAt("ParentModuleId");
			//String sQryModName = Decode.decode(this.getStringAt("ModName").trim());//这个动作已经在框架中做了
			String sQryModName = this.getStringAt("ModName").trim();
			String sQryModId = this.getStringAt("ModCode").trim();
		String area_code = "00000000"; //总行

			String application = (String)CmisConstance.getParameterSettings().get("application");
			StringTokenizer token = new StringTokenizer(application, "|");

			//查询改专业下，状态正常，满足查询条件的模块名。（父节点，兄弟节点都不选）			 
			Vector vValue = new Vector();
			vValue.add(sMajorCode);
			vValue.add(sPModuleId);
			vValue.add(sPModuleId);
			String sql =
				"select func_code, func_name, func_sub_node from mag_function where func_code not in (select app_module_code from mag_application_new,mag_area where app_major_code = ? and (app_module_code = ? or app_pmodule_code = ?) and mag_area.area_code = '"
					+ area_code
					+ "' and mag_application_new.area_code = mag_area.area_code";
			sql += " and application in (";
			String subSql = "";
			while (token.hasMoreTokens()) {
				subSql += ",'" + token.nextToken() + "'";
			}
			sql += subSql.substring(1) + ")) and func_status = '0'";

			if (sQryModName.length() > 0) {
				//              sql += " and func_name like '%" + sQryModName + "%'";
				vValue.add("%" + sQryModName + "%");
				sql += " and func_name like ? ";
			}
			if (sQryModId.length() > 0) {
				//              sql += " and func_code like '%" + sQryModId + "%'";
				vValue.add("%" + sQryModId + "%");
				sql += " and func_code like ? ";
			}

			tool = new SqlTool(this);
			tool.getConn("missign");
			ResultSet rs = tool.executeQuery(sql, vValue);
			Vector funcVector = new Vector();
			while (rs.next()) {
				String sCode = rs.getString(1);
				String sName = rs.getString(2);
				String sHasChild = rs.getString(3);
				String sValue = sCode + "###" + sName + "@@@" + sHasChild;
				funcVector.add(sValue);
			}
			this.setFieldValue("AvailFuncVector", funcVector);

			this.setOperationDataToSession();
			this.xmlizeSelectFunc();
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("100536", "UtilMajorMag.enterSelectPage()", e.getMessage(), genMsg.getErrMsgByLang((String)getSessionData("LangCode"), "100536"));//查询供选择新增的业务功能模块错误
		}
		finally {
			try {
				tool.closeconn();
			}
			catch (Exception e) {}
		}
	}

	/**
	 * 把待选择的功能模块以xml格式返回给页面
	 * @throws Exception
	 */
	private void xmlizeSelectFunc() throws Exception {
		try {
			java.util.Vector aVector = (java.util.Vector)this.getObjectAt("AvailFuncVector");
			int beginPos = Integer.parseInt(getStringAt("beginPos"));
			int page_size = 15;
			int currPos = beginPos + page_size;
			if (currPos > aVector.size())
				currPos = aVector.size();
			int pages = aVector.size() % page_size == 0 ? aVector.size() / page_size : aVector.size() / page_size + 1;
			int itemamount = aVector.size();

			String xmlPack = "<?xml version=\"1.0\" encoding=\"GB2312\"?>";
			xmlPack += "<Content pages=\"" + pages + "\" itemamount=\"" + itemamount + "\">";
			for (int i = beginPos; i < currPos; i++) {
				String sValue = (String)aVector.get(i);
				int idx1 = sValue.indexOf("###");
				int idx2 = sValue.indexOf("@@@");
				String sCode = sValue.substring(0, idx1);
				String sName = sValue.substring(idx1 + 3, idx2);
				sName = validXml(sName);
				String sHasChild = sValue.substring(idx2 + 3);
				xmlPack += "<CodeName code=\"" + sCode + "\" name=\"" + sName + "\" haschild=\"" + sHasChild + "\"/>";
			}
			xmlPack += "</Content>";

			this.setOperationDataToSession();
			this.setReplyPage("DirectOutput" + xmlPack);
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("100537", "UtilMajorMag.xmlizeSelectFunc()", e.getMessage(), genMsg.getErrMsgByLang((String)getSessionData("LangCode"), "100537"));//供选择的新增模块列表生成翻页数据失败
		}
	}

	/**
	 * 用户选中添加某个模块后，进入权限、行级别设置页面
	 * @throws Exception
	 */
	private void enterAddSetPage() throws Exception {
		
		String sNewModuleId = this.getStringAt("NewModuleId");
		String sMajorCode = this.getStringAt("major_code");
		String webBasePath =(String) CmisConstance.getParameterSettings().get("webBasePath");
		MenuDAO3 dao = new MenuDAO3(this);
		boolean isExist = dao.isExistMoudle(sMajorCode,sNewModuleId);
		this.setOperationDataToSession();
		if(isExist){
			this.setFieldValue("infoTitle", genMsg.getErrMsgByLang((String)getSessionData("LangCode"), "099996"));
			this.setFieldValue(
				"infoReturn","javascript:history.back();");
			this.setFieldValue("infoMsg", genMsg.getErrMsgByLang((String)getSessionData("LangCode"), "100545"));//该功能模块已在当前业务菜单模板当中存在，不能重复新增。
			this.setReplyPage(webBasePath + "/util/util_info.jsp");	
		}else{
			this.setReplyPage("/M/MA/MA_MajorFuncAddSet.jsp");
		}	
	}

	/**
	 * 进入更新页面
	 * @throws Exception
	 */
	private void enterUpdatePage() throws Exception {
		
		this.setOperationDataToSession();
		this.setReplyPage("/M/MA/MA_MajorFuncModify.jsp");

	}

	/**
	 * 在表mag_application_new中增加一条记录，同时根据权限字、行级别修改其父模块的权限和行级别
	 * @throws Exception
	 */
	private void addFunc() throws Exception {
		try {
			String sMajorCode = this.getStringAt("major_code");
			String sModuleId = this.getStringAt("ModuleId");
			String sPModuleId = this.getStringAt("ParentModuleId");
			String sNewModuleId = this.getStringAt("NewModuleId");
			String sModuleHasChild = this.getStringAt("ModuleHasChild");
			String sSiblingChild = this.getStringAt("SiblingChildRadio");
			String sAppName = this.getStringAt("application");
		String sEnaFlag = this.getStringAt("Enable");
		String area_code = "00000000"; //总行
	  
			int PrivCount = Integer.parseInt(this.getStringAt("PrivilegeCount"));
			String sPrivilege = "", sBankClass = "", sEnableFlag = "";
			for (int i = 0; i < PrivCount; i++) {
				String sName = "ChkboxPriv" + (i + 1);
				String sPriv = "";
				try {
					sPriv = this.getStringAt(sName);
					sPrivilege += sPriv;
					this.removeDataField(sName); //防止下一次进入函数时，session中仍然保留这次的值
				}
				catch (TranFailException e) {
					sPrivilege += "0";
				}
			}
			StringBuffer sBuff = new StringBuffer(40);
			sBuff.append(sPrivilege);
			for(int i = 0;i<40 - PrivCount;i++){
				sBuff.append("0");
			}
			sPrivilege = sBuff.toString();
			for (int i = 0; i < 5; i++) {
				String sName = "ChkboxBank" + (i + 1);
				String sBank = "";
				try {
					sBank = this.getStringAt(sName);
					sBankClass += sBank;
					this.removeDataField(sName);
				}
				catch (TranFailException e) {
					sBankClass += "0";
				}
			}

			String sName = "ChkboxEnable";
			String sEnable = "";
			try {
				sEnable = this.getStringAt(sName);
				sEnableFlag += sEnable;
				this.removeDataField(sName);
			}
			catch (TranFailException e) {
				sEnableFlag = "0";
			}
			MenuDAO3 dao = new MenuDAO3(this);
			dao.addNewFunc(sMajorCode, sPModuleId, sModuleId, sNewModuleId, sPrivilege, sBankClass, sModuleHasChild, sSiblingChild, sAppName, sEnableFlag, area_code);
			this.setDictUpdatetMark("UTILMAJORMAGICBCECC["+sMajorCode +"$" + area_code +"]", "3", "cmis3");
			this.setOperationDataToSession();
			this.displayMajorFunc();
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("100538", "UtilMajorMag.addFunc()", e.getMessage(), genMsg.getErrMsgByLang((String)getSessionData("LangCode"), "100538"));//新增模块失败
		}
	}

	/**
	 * 删除模块及其子模块
	 * @throws Exception
	 */
	private void deleteFunc() throws Exception {
		try {
			String sMajorCode = this.getStringAt("major_code");
			String sModuleId = this.getStringAt("ModuleId");
			String sPModuleId = this.getStringAt("ParentModuleId");
		String area_code = "00000000"; //总行
	  
			MenuDAO3 dao = new MenuDAO3(this);
			dao.deleteFunc(sMajorCode, sPModuleId, sModuleId);
			this.setDictUpdatetMark("UTILMAJORMAGICBCECC["+ sMajorCode +"$" + area_code +"]", "3", "cmis3");
			this.setOperationDataToSession();
			this.displayMajorFunc();
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("100539", "UtilMajorMag.deleteFunc()", e.getMessage(), genMsg.getErrMsgByLang((String)getSessionData("LangCode"), "100539"));//删除模块失败
		}
	}

	/**
	 * 更新一个功能模块的权限字，同时更改它的父子模块的权限字
	 * @throws Exception
	 */
	private void updateFunc() throws Exception {
		try {
			String sMajorCode = this.getStringAt("major_code");
			String sModuleId = this.getStringAt("ModuleId");
			String sPModuleId = this.getStringAt("ParentModuleId");
			String sPrePriv = this.getStringAt("PrePrivilege");
			String sPreBank = this.getStringAt("PreBankClass");
			String sPreAppOrder = this.getStringAt("PreAppOrder");
			String sAppOrder = this.getStringAt("AppOrder");
			String sEnaFlag = this.getStringAt("Enable");
			String area_code = "00000000"; //总行
			int PrivCount = Integer.parseInt(this.getStringAt("PrivilegeCount"));
			String sPrivilege = "", sBankClass = "", sEnableFlag = "";
			for (int i = 0; i < PrivCount; i++) {
				String sName = "ChkboxPriv" + (i + 1);
				String sPriv = "";
				try {
					sPriv = this.getStringAt(sName);
					sPrivilege += sPriv;
					this.removeDataField(sName);
				}
				catch (TranFailException e) {
					sPrivilege += "0";
				}
			}
			StringBuffer sBuff = new StringBuffer(40);
			sBuff.append(sPrivilege);
				for(int i = 0;i<40 - PrivCount;i++){
					sBuff.append("0");
			}
			sPrivilege = sBuff.toString();
			for (int i = 0; i < 5; i++) {
				String sName = "ChkboxBank" + (i + 1);
				String sBank = "";
				try {
					sBank = this.getStringAt(sName);
					sBankClass += sBank;
					this.removeDataField(sName);
				}
				catch (TranFailException e) {
					sBankClass += "0";
				}
			}
			String sName = "ChkboxEnable";
			String sEnable = "";
			try {
				sEnable = this.getStringAt(sName);
				sEnableFlag += sEnable;
				this.removeDataField(sName);
			}
			catch (TranFailException e) {
				sEnableFlag = "0";
			}
			if (!sPrivilege.equals(sPrePriv) || !sBankClass.equals(sPreBank) || !sEnableFlag.equals(sEnaFlag) || !sAppOrder.equals(sPreAppOrder)) { //模块的权限和行级别发生变化
				MenuDAO3 dao = new MenuDAO3(this);
				dao.updateFunc(sMajorCode, sPModuleId, sModuleId, sPrivilege, sBankClass, sAppOrder, sEnableFlag, area_code);
				this.setDictUpdatetMark("UTILMAJORMAGICBCECC["+sMajorCode +"$" + area_code +"]", "3", "cmis3");
			}
			this.displayMajorFunc();
		}
		catch (TranFailException te) {
			throw te;
		}
		catch (Exception e) {
			throw new TranFailException("100527", "UtilMajorMag.updateFunc()", e.getMessage(), genMsg.getErrMsgByLang((String)getSessionData("LangCode"),"100527"));
		}
	}
	/**
	 *  
	 * Description  : 过滤XML输出的特殊字符	 
	 * @return
	 */

	public static String validXml(String xml) {
		String ret = xml;

		final String[] FROM = { "&", "<", ">", "'", "\"" };
		final String[] TO = { "&amp;", "&lt;", "&gt;", "’", "&quot;" };

		for (int i = 0; i < FROM.length; i++) {
			ret = replaceAll(ret, FROM[i], TO[i]);
		}
		return ret;
	}

	/**
	 *  
	 * Description  : 替换 (注意:区分大小写)
	 * CreationDate : 2007-6-20 15:05:15
	 * @param src
	 * @param replaceEx
	 * @param relacement
	 * @return
	 */
	public static String replaceAll(String src, String replaceEx, String relacement) {
		String ret = "";

		int srcLen = src.length();
		int repLen = replaceEx.length();
		int start = 0;
		int i;
		for (i = 0; i < srcLen - repLen + 1;) {
			if (src.substring(i, i + repLen).equals(replaceEx)) {
				ret += src.substring(start, i) + relacement;

				i += repLen;
				start = i;
			}
			else {
				i++;
			}
		}
		if (i > start) {
			ret += src.substring(start, i);
		}
		return ret;
	}		

}