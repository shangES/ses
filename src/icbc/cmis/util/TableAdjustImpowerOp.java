/*
 * 
 * 创建日期 2007-6-22
 *
 * @author 王强
 * 
 */
package icbc.cmis.util;
import icbc.cmis.operation.*;
import java.util.*;
import icbc.cmis.base.*;
import icbc.cmis.base.TranFailException;
import icbc.cmis.second.pkg.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.Calendar;
import icbc.cmis.util.tools;
/**
 * Title:业务资料改动权限的授权
 * Description: 业务资料改动权限的授权
 * Company:icbcsdc
 * @author：王强
 * @version 1.0
 */
public class TableAdjustImpowerOp extends CmisOperation {

	TableAdjustImpowerDAO dao = null;
	String baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath");

	/**
	 * @throws Exception
	 */
	public void execute() throws java.lang.Exception {
		/**@todo: implement this icbc.cmis.operation.CmisOperation abstract method*/
		String action = getStringAt("opAction");

		try {
			//授权结果List页面
			if (action.equals("10001")) {
				tableAdjustImpowerList();
			}
			//授权新增页面
			if (action.equals("10002")) {
				tableAdjustImpowerAddPage();
			}

			//授权修改操作
			if (action.equals("10004")) {
				tableAdjustImpowerModify();
			}
			//授权删除操作
			if (action.equals("10005")) {
				tableAdjustImpowerDelete();
			}

			//授权新增页面
			if (action.equals("10006")) {
				tableAdjustImpowerAdd();
			}
			setOperationDataToSession();

		}
		catch (Exception ex) {
			ex.printStackTrace();
			setOperationDataToSession();
			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerOP.execute()", ex.getMessage(), " 业务资料改动权限的授权操作失败");
		}
	}

	/**
	* <b>功能描述:业务资料改动权限的授权</b><br>
	* <p>授权查询List页面</p>
	* @throws TranFailException
	*  
	*/
	private void tableAdjustImpowerList() throws TranFailException {

		String areaCode = (String)this.getStringAt("area_code"); //地区号
		String areaName = (String)this.getStringAt("area_name"); //地区名称

		dao = new TableAdjustImpowerDAO(this);
		Vector list = null; //授权项目列表
		try {
			list = dao.getQueryImpowerList(areaCode);
			this.setFieldValue("query_area_code", areaCode);
			this.setFieldValue("query_area_name", areaName);
			this.setFieldValue("list", list);
			this.setReplyPage("/util/util_TableAdjustImpowerList.jsp");
			this.setOperationDataToSession();
		}
		catch (TranFailException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerOP.tableAdjustImpowerList()", e.getMessage(), "查询授权列表出错");
		}
	}

	/**
		* <b>功能描述:业务资料改动权限的授权</b><br>
		* <p>查询授权新增页面</p>
		* @throws TranFailException
		*  
		*/
	private void tableAdjustImpowerAddPage() throws TranFailException {

		String areaCode = (String)this.getStringAt("query_area_code"); //地区号		
		String areaName = (String)this.getStringAt("query_area_name"); //地区名称
		String matherAreaCode = "";
		String matherAreaName = "";
		String matherSelect = "true"; //上级行是否可选标志位
		dao = new TableAdjustImpowerDAO(this);
		try {
			if (!areaCode.equals("*")) {
				String ret = dao.getPriorBank(areaCode);
				if (ret.length() > 0) {
					matherAreaCode = ret.substring(0, ret.indexOf("|"));
					matherAreaName = ret.substring(ret.indexOf("|") + 1);
					matherSelect = "false";
				}
			}
			this.setFieldValue("mather_area_code", matherAreaCode);
			this.setFieldValue("mather_area_name", matherAreaName);
			this.setFieldValue("matherSelect", matherSelect);
			this.setFieldValue("query_area_code", areaCode);
			this.setFieldValue("query_area_name", areaName);
			this.setReplyPage("/util/util_TableAdjustImpowerNew.jsp");
			this.setOperationDataToSession();
		}
		catch (TranFailException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TranFailException("业务资料改动权限的授权", "TableAdjustImpowerOP.tableAdjustImpowerAddPage()", e.getMessage(), "授权新增错误！");
		}
	}

	/**
		* <b>功能描述:业务资料改动权限的授权</b><br>
		* <p>查询授权新增页面</p>
		* @throws TranFailException
		*  
		*/
	private void tableAdjustImpowerAdd() throws TranFailException {

		String areaCode = (String)this.getStringAt("area_code"); //地区号
		String operationType = (String)this.getStringAt("operationType"); //业务品种
		String controlType = (String)this.getStringAt("controlType"); //控制类型
		String matherAreaCode = (String)this.getStringAt("mather_area_code"); //被授权的上级行
		String input_personnel_code = (String)this.getSessionData("EmployeeCode"); //this.getStringAt("input_personnel_code"); //录入柜员
		String input_area_code = (String)this.getStringAt("input_area_code"); //录入地区

		dao = new TableAdjustImpowerDAO(this);
		try {
			int count = 0;
			count = dao.getRecordCount(areaCode, operationType, controlType, matherAreaCode);
			if (count == 0) {
				if (dao.adjustImpowerAdd(areaCode, operationType, controlType, matherAreaCode, input_personnel_code, input_area_code)) {
					String returnURL =
						baseWebPath
							+ "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.TableAdjustImpowerOp&opAction=10001"
							+ "&area_code="
							+ areaCode;
					this.setFieldValue("okTitle", "业务资料改动权限的授权新增操作");
					this.setFieldValue("okMsg", "业务资料改动权限的授权新增操作成功！");
					this.setFieldValue("okReturn", returnURL);
					this.setOperationDataToSession();
					this.setReplyPage("/ok.jsp");
					this.setOperationDataToSession();
				}

			}
			else {
				if (!areaCode.equals("*") && !controlType.equals("*")) {
					this.setFieldValue("infoTitle", "业务资料改动权限的授权新增操作");
					this.setFieldValue("infoMsg", "权限已存在，不用新增！");
					this.setFieldValue("infoReturn", "javascript:window.history.back();");
					this.setOperationDataToSession();
					this.setReplyPage("/util/util_info.jsp");
					this.setOperationDataToSession();
				}
				else {
					dao.deleteExistRecord(areaCode, operationType, controlType, matherAreaCode) ;
					if (dao.adjustImpowerAdd(areaCode, operationType, controlType, matherAreaCode, input_personnel_code, input_area_code)) {
						String returnURL =
							baseWebPath
								+ "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.TableAdjustImpowerOp&opAction=10001"
								+ "&area_code="
								+ areaCode;
						this.setFieldValue("okTitle", "业务资料改动权限的授权新增操作");
						this.setFieldValue("okMsg", "业务资料改动权限的授权新增操作成功！");
						this.setFieldValue("okReturn", returnURL);
						this.setOperationDataToSession();
						this.setReplyPage("/ok.jsp");
						this.setOperationDataToSession();
					}

				}
			}
		}
		catch (TranFailException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TranFailException("业务资料改动权限的授权", "AA_FinancingOpTrunImpowerOp.financingOpTrunImpowerAdd()", e.getMessage(), genMsg.getErrMsg("100469"));
		}
	}

	/**
	* <b>功能描述:业务资料改动权限的授权</b><br>
	* <p>查询授权修改操作</p>
	* @throws TranFailException
	*  
	*/
	private void tableAdjustImpowerModify() throws TranFailException {
		String oldAreaCode = ""; //地区号
		String oldOoperationType = ""; //业务品种
		String oldControlType = ""; //控制类型
		String oldMatherAreaCode = ""; //被授权的上级行
		String newAreaCode = ""; //地区号
		String newOoperationType = ""; //业务品种
		String newControlType = ""; //控制类型
		String newMatherAreaCode = ""; //被授权的上级行

		String newValues = (String)this.getStringAt("newValues");
		String oldValues = (String)this.getStringAt("oldValues");
		String employeeCode = (String)this.getSessionData("EmployeeCode");
		String employeeArea = (String)this.getSessionData("AreaCode");

		dao = new TableAdjustImpowerDAO(this);

		//解析新数据
		StringTokenizer st = new StringTokenizer(newValues, "|");
		if (st.hasMoreTokens()) {
			newAreaCode = ((String)st.nextElement()).trim();
			newOoperationType = ((String)st.nextElement()).trim();
			newControlType = ((String)st.nextElement()).trim();
			newMatherAreaCode = ((String)st.nextElement()).trim();
		}

		//解析老数据
		st = new StringTokenizer(oldValues, "|");
		if (st.hasMoreTokens()) {
			oldAreaCode = ((String)st.nextElement()).trim();
			oldOoperationType = ((String)st.nextElement()).trim();
			oldControlType = ((String)st.nextElement()).trim();
			oldMatherAreaCode = ((String)st.nextElement()).trim();
		}

		try {
			if (oldOoperationType.equals(newOoperationType) && oldControlType.equals(newControlType)) {
				this.setFieldValue("infoTitle", "业务资料改动权限的授权修改操作");
				this.setFieldValue("infoMsg", "资料没变化，无需修改！");
				this.setFieldValue("infoReturn", "javascript:window.history.back();");
				this.setOperationDataToSession();
				this.setReplyPage("/util/util_info.jsp");
				this.setOperationDataToSession();
				return;
			}
			//如果只是控制类型从具体变成*,则不需要清楚business_control_modify表中的记录
			if (!(oldOoperationType.equals(newOoperationType) && newControlType.equals("*"))) {
				dao.busiModiContDel(oldAreaCode, oldOoperationType, oldControlType, oldMatherAreaCode);
			}

			//更新数据			
			if (dao
				.adjustImpowerUpdate(
					newAreaCode,
					newOoperationType,
					newControlType,
					oldOoperationType,
					oldControlType,
					newMatherAreaCode,
					employeeCode,
					employeeArea)) {
				String returnURL =
					baseWebPath
						+ "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.TableAdjustImpowerOp&opAction=10001"
						+ "&area_code="
						+ oldAreaCode;
				this.setFieldValue("okTitle", "业务资料改动权限的授权修改操作");
				this.setFieldValue("okMsg", "业务资料改动权限的授权修改操作成功！");
				this.setFieldValue("okReturn", returnURL);
				this.setOperationDataToSession();
				this.setReplyPage("/ok.jsp");
				this.setOperationDataToSession();
			}
		}
		catch (TranFailException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TranFailException("业务资料改动权限的授权", "AA_FinancingOpTrunImpowerOp.financingOpQueryImpowerModify()", e.getMessage(), genMsg.getErrMsg("100469"));
		}
	}

	/**
		* <b>功能描述:业务资料改动权限的授权</b><br>
		* <p>授权查询删除操作</p>
		* @throws TranFailException
		*  
		*/
	private void tableAdjustImpowerDelete() throws TranFailException {

		String areaCode = ""; //地区号
		String operationType = ""; //业务品种
		String controlType = ""; //控制类型
		String matherAreaCode = ""; //被授权的上级行

		String res = (String)this.getStringAt("oldValues");
		StringTokenizer st = new StringTokenizer(res, "|");
		if (st.hasMoreTokens()) {
			areaCode = ((String)st.nextElement()).trim();
			operationType = ((String)st.nextElement()).trim();
			controlType = ((String)st.nextElement()).trim();
			matherAreaCode = ((String)st.nextElement()).trim();
		}
		dao = new TableAdjustImpowerDAO(this);
		Vector list = null; //授权项目列表
		try {
			if (dao.busiModiContDel(areaCode, operationType, controlType, matherAreaCode)) {
				if (dao.adjustImpowerDelete(areaCode, operationType, controlType, matherAreaCode)) {
					String returnURL =
						baseWebPath
							+ "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.TableAdjustImpowerOp&opAction=10001"
							+ "&area_code="
							+ areaCode;
					this.setFieldValue("okTitle", "业务资料改动权限的授权删除操作");
					this.setFieldValue("okMsg", "业务资料改动权限的授权删除操作成功！");
					this.setFieldValue("okReturn", returnURL);
					this.setOperationDataToSession();
					this.setReplyPage("/ok.jsp");
					this.setOperationDataToSession();
				}
			}

		}
		catch (TranFailException e) {
			throw e;
		}
		catch (Exception e) {

			throw new TranFailException("业务资料改动权限的授权", "AA_FinancingOpTrunImpowerOp.financingOpTrunImpowerAddFistWeb()", e.getMessage(), genMsg.getErrMsg("100469"));
		}
	}
}