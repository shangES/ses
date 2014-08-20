package icbc.cmis.util;

import icbc.cmis.operation.*;
import icbc.cmis.base.*;
import java.util.*;
import java.io.*;

/**
 * 调帐管理，类似于通用参数维护。
 * @author zjfh-zhangyz
 * 2007-1-19 / 15:18:16
 *
 */
public class TableAdjustOp extends CmisOperation {

	public TableAdjustOp() {
	}

	public void execute() throws java.lang.Exception {
		try {
			String action = getStringAt("opAction");
			if (action.equals("login")) {
				String type_code = (String)getStringAt("type_select");
				performLogin(type_code);
			}
			//业务调帐授权
			else if (action.equals("firstR")) {
				try {
					this.removeDataField("type_select");
				} catch (Exception ee) {
				}
				this.addDataField("type_select", "R0");
				performLogin("R0");
			}
			//业务帐务调整
			else if (action.equals("firstT")) {
				try {
					this.removeDataField("type_select");
				} catch (Exception ee) {
				}
				this.addDataField("type_select", "T0");
				performLogin("T0");
			} else if (action.equals("Query")) {
				performQuery();
			} else if (action.equals("QueryCondition")) {
				performQueryCondition();
			} else if (action.equals("insert")) {
				performInsert();
			} else if (action.equals("modify")) {
				performModify();
			} else if (action.equals("delete")) {
				performDelete();
			} else if (action.equals("reloadTable")) {
				performReloadTable();
			}
			//调用存储过程
			else if (action.equals("runProc")) {
				performRunProc();
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw e;
		}

	}

	private void performQueryCondition() throws Exception {
		String paraTable = null;
		String tableCode = null;
		String tableName = null;
		String needReload = null;
		String restrict = null;
		String checkFunction = null;
		String checkFunction_2 = null;

		Vector head = null;
		try {
			paraTable = getStringAt("paraTable");
			String table = paraTable;
			int pos = table.indexOf("|");
			tableCode = table.substring(0, pos);

			table = table.substring(pos + 1);
			pos = table.indexOf("|");
			tableName = table.substring(0, pos);

			table = table.substring(pos + 1);
			pos = table.indexOf("|");
			needReload = table.substring(0, pos);

			table = table.substring(pos + 1);
			pos = table.indexOf("|");
			restrict = table.substring(0, pos);

			table = table.substring(pos + 1);
			pos = table.indexOf("|");
			checkFunction = table.substring(0, pos);

			checkFunction_2 = table.substring(pos + 1);

			this.updateSessionData("paraTableCode", tableCode);
			this.updateSessionData("paraTableName", tableName);
			this.updateSessionData("needReload", needReload);
			this.updateSessionData("restrict", restrict);
			this.updateSessionData("checkFunction", checkFunction);
			this.updateSessionData("checkFunction_2", checkFunction_2);

			TableAdjustDAO dao = new TableAdjustDAO(this);

			head = dao.performQueryHeadDetail(tableCode);

			this.updateSessionData("colomnInfo", head);
			this.setFieldValue("paraTable", paraTable);
			this.setOperationDataToSession();
			setReplyPage("/icbc/cmis/util/util_TableAdjustFillCondition.jsp");
		} catch (Exception e) {
			throw e;
		}
	}

	private void performQuery() throws Exception {
		Vector head = null;

		try {
			head = (Vector)this.getSessionData("colomnInfo");
			for (int i = 0; i < head.size(); i++) {
				Hashtable htable = (Hashtable)head.get(i);
				if (((String)htable.get("isPrimary")).equals("1")) {
					String name = (String)htable.get("col");
					String value = this.getStringAt(name);
					htable.put("primaryValue", value);
				}
			}

			String page = null;
			try {
				page = this.getStringAt("page");
			} catch (Exception e) {
			}

			if (page != null)
				this.setFieldValue("showPage", page);

			this.setOperationDataToSession();
			setReplyPage("/icbc/cmis/util/util_TableAdjustDetailList.jsp");
		} catch (Exception e) {
			throw e;
		}
	}

	private void performLogin(String type_code) throws Exception {
		Vector vec = null;
		String major = (String)this.getSessionData("Major");
		String bankFlag = (String)this.getSessionData("BankFlag");

		try {
			TableAdjustDAO dao = new TableAdjustDAO(this);
			vec = dao.performQueryTableList(major, bankFlag, type_code);
			try {
				this.removeSessionData("paraTableCode");
			} catch (Exception e) {
			}

			try {
				this.removeSessionData("paraTableName");
			} catch (Exception e) {
			}

			try {
				this.removeSessionData("needReload");
			} catch (Exception e) {
			}

			try {
				this.removeSessionData("isUpdated");
			} catch (Exception e) {
			}

			try {
				this.removeSessionData("restrict");
			} catch (Exception e) {
			}

			try {
				this.removeSessionData("colomnInfo");
			} catch (Exception e) {
			}
			try {
				this.removeSessionData("checkFunction");
			} catch (Exception e) {
			}

			this.setFieldValue("paraTableList", vec);
			this.setOperationDataToSession();
			setReplyPage("/icbc/cmis/util/util_TableAdjustLogin.jsp");
		} catch (Exception e) {
			throw e;
		}
	}

	private void performInsert() throws Exception {
		Vector vec = null;
		Vector colomnInfo = null;
		String tableCode = null;
		String checkFunction = null;
		String localInfo = null;
		String checkFunction_2 = null;
		int n = 1;
		try {
			colomnInfo = (Vector)this.getSessionData("colomnInfo");
			tableCode = (String)this.getSessionData("paraTableCode");
			checkFunction = (String)this.getSessionData("checkFunction");
			String[] values = new String[colomnInfo.size()];
			for (int i = 0; i < values.length; i++) {
				Hashtable htable = (Hashtable)colomnInfo.get(i);
				values[i] = this.getStringAt((String)htable.get("col"));
			}

			localInfo =
				(String)this.getSessionData("EmployeeCode")
					+ "|"
					+ (String)this.getSessionData("AreaCode")
					+ "|"
					+ (String)this.getSessionData("Major");
			TableAdjustDAO dao = new TableAdjustDAO(this);
			dao.insert(tableCode, colomnInfo, values, checkFunction, localInfo, checkFunction_2);

			this.updateSessionData("isUpdated", "true");
			this.setOperationDataToSession();
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>新增参数成功</info>");
		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>新增参数失败：" + e.getMessage() + "</error>");
		}
	}

	private void performModify() throws Exception {

		Vector colomnInfo = null;
		//Vector colomnType = null;
		String tableCode = null;
		String checkFunction = null;
		String checkFunction_2 = null;
		String page = "1";
		String oldKey = null;
		String localInfo = null;
		int n = 1;
		try {
			colomnInfo = (Vector)this.getSessionData("colomnInfo");
			tableCode = (String)this.getSessionData("paraTableCode");
			checkFunction = (String)this.getSessionData("checkFunction");
			String[] values = new String[colomnInfo.size()];
			String[] values_old = new String[colomnInfo.size()];
			for (int i = 0; i < values.length; i++) {
				Hashtable htable = (Hashtable)colomnInfo.get(i);
				values[i] = this.getStringAt((String)htable.get("col"));
				values_old[i] = this.getStringAt((String)htable.get("col") + "_old");
			}
			localInfo =
				(String)this.getSessionData("EmployeeCode")
					+ "|"
					+ (String)this.getSessionData("AreaCode")
					+ "|"
					+ (String)this.getSessionData("Major");
			TableAdjustDAO dao = new TableAdjustDAO(this);
			dao.update(tableCode, colomnInfo, values, values_old, checkFunction, localInfo, checkFunction_2);

			this.updateSessionData("isUpdated", "true");
			this.setOperationDataToSession();
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>修改参数成功</info>");
		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>修改参数失败：" + e.getMessage() + "</error>");
		}
	}

	private void performDelete() throws Exception {

		Vector colomnInfo = null;
		String tableCode = null;
		String page = "1";
		String checkFunction = null;
		String checkFunction_2 = null;
		String localInfo = null;

		int n = 1;
		try {
			colomnInfo = (Vector)this.getSessionData("colomnInfo");
			tableCode = (String)this.getSessionData("paraTableCode");
			String[] values = new String[colomnInfo.size()];

			checkFunction = (String)this.getSessionData("checkFunction");

			for (int i = 0; i < values.length; i++) {
				Hashtable htable = (Hashtable)colomnInfo.get(i);
				values[i] = this.getStringAt((String)htable.get("col"));

			}

			localInfo =
				(String)this.getSessionData("EmployeeCode")
					+ "|"
					+ (String)this.getSessionData("AreaCode")
					+ "|"
					+ (String)this.getSessionData("Major");

			TableAdjustDAO dao = new TableAdjustDAO(this);
			dao.delete(tableCode, colomnInfo, values, checkFunction, localInfo, checkFunction_2);

			this.updateSessionData("isUpdated", "true");
			this.setOperationDataToSession();
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>删除参数成功</info>");
		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>删除参数失败：" + e.getMessage() + "</error>");
		}
	}

	private void performReloadTable() throws Exception {

		Vector colomnInfo = null;
		String tableCode = null;
		String tableName = null;
		String page = "1";

		int n = 1;
		try {
			tableCode = (String)this.getSessionData("paraTableCode");
			tableName = (String)this.getSessionData("paraTableName");

			TableAdjustDAO dao = new TableAdjustDAO(this);
			Hashtable ht = dao.getTableOwner(tableCode);
			String tab_type = (String)ht.get("tab_type");
			String tab_owner = (String)ht.get("tab_owner");
			this.setDictUpdatetMark(tableCode, tab_type, tab_owner.toLowerCase());

			this.updateSessionData("isUpdated", "false");
			this.setOperationDataToSession();

			setReplyPage(
				"DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>系统已经准备重新初始化" + tableName + ",初始化结果稍候将实时反映</info>");
		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>重新初始化：" + e.getMessage() + "</error>");
		}
	}

	/*
	 * 功能描述：直接调用存储过程校验
	 */
	private void performRunProc() throws Exception {
		Vector colomnInfo = null;
		String tableCode = null;
		String checkFunction = null;
		String page = "1";
		String oldKey = null;
		String localInfo = null;
		String restring;
		String result;
		String reinfo;
		int n = 1;
		try {
			colomnInfo = (Vector)this.getSessionData("colomnInfo");
			tableCode = (String)this.getSessionData("paraTableCode");
			checkFunction = (String)this.getSessionData("checkFunction");
			String[] values = new String[colomnInfo.size()];
			for (int i = 0; i < values.length; i++) {
				Hashtable htable = (Hashtable)colomnInfo.get(i);
				values[i] = this.getStringAt((String)htable.get("col"));
			}
			localInfo =
				(String)this.getSessionData("EmployeeCode")
					+ "|"
					+ (String)this.getSessionData("AreaCode")
					+ "|"
					+ (String)this.getSessionData("Major");
			TableAdjustDAO dao = new TableAdjustDAO(this);
			restring = dao.runProc(colomnInfo, values, checkFunction, localInfo);
			result = restring.substring(0, 1);
			reinfo = restring.substring(2);

			this.updateSessionData("isUpdated", "true");
			this.setOperationDataToSession();

			if (result.equals("0")) {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>校验完成</info>");
			} else {
				setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>校验失败：" + reinfo + "</info>");
			}

		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>校验失败：" + e.getMessage() + "</error>");
		}
	}

}
