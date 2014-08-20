package icbc.cmis.util;

import icbc.cmis.operation.*;
import icbc.cmis.base.*;
import java.util.*;
import java.io.*;

/**
 * 通用参数管理
 * @author zjfh-zhangyz
 * ????-?-?? / ??:??:?? 建立
 * 2007-1-18 / 14:34:17 增加字段，mag_adjust_tabs_def.adjust008，用于定义I和U时的事先校验
 *
 */
public class ParaAdjustOp extends CmisOperation {

	public ParaAdjustOp() {
	}

	public void execute() throws java.lang.Exception {
		try {
			String action = getStringAt("opAction");
			if (action.equals("firstD")) {
				performLogin();
			} else if (action.equals("Query")) {
				performQuery();
			} else if (action.equals("insert")) {
				performInsert();
			} else if (action.equals("modify")) {
				performModify();
			} else if (action.equals("delete")) {
				performDelete();
			} else if (action.equals("filter")) {
				performFilter();
			} else if (action.equals("reloadTable")) {
				performReloadTable();
			} else if (action.equals("runProc")) {
				performRunProc();
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw e;
		}

	}

	private void performQuery() throws Exception {
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

			String page = null;
			try {
				page = this.getStringAt("page");
			} catch (Exception e) {
			}

			this.updateSessionData("paraTableCode", tableCode);
			this.updateSessionData("paraTableName", tableName);
			this.updateSessionData("needReload", needReload);
			this.updateSessionData("restrict", restrict);
			this.updateSessionData("checkFunction", checkFunction);
			this.updateSessionData("checkFunction_2", checkFunction_2);

			ParaAdjustDAO dao = new ParaAdjustDAO(this);

			head = dao.performQueryHeadDetail(tableCode);
			this.updateSessionData("colomnInfo", head);
			if (page != null)
				this.setFieldValue("showPage", page);
			this.setFieldValue("paraTable", paraTable);
			this.setOperationDataToSession();
			setReplyPage("/icbc/cmis/util/util_ParaAdjustDetailList.jsp");
		} catch (Exception e) {
			throw e;
		}
	}

	private void performLogin() throws Exception {
		Vector vec = null;
		IndexedDataCollection table_type = null;
		String major = (String)this.getSessionData("Major");
		String bankFlag = (String)this.getSessionData("BankFlag");
		try {
			ParaAdjustDAO dao = new ParaAdjustDAO(this);
			vec = dao.performQueryTableList(major, bankFlag);
			table_type = dao.getTableTypeD();
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

			this.setFieldValue("paraTableList", vec);

			table_type.setName("table_type");
			try {
				this.removeDataField("table_type");
			} catch (Exception ee) {
			}
			this.addIndexedDataCollection(table_type);

			this.setOperationDataToSession();
			setReplyPage("/icbc/cmis/util/util_ParaAdjustLogin.jsp");
		} catch (Exception e) {
			throw e;
		}
	}

	private void performInsert() throws Exception {
		Vector vec = null;
		Vector colomnInfo = null;
		String tableCode = null;
		String checkFunction = null;
		String checkFunction_2 = null;
		String localInfo = null;
		int n = 1;
		try {
			colomnInfo = (Vector)this.getSessionData("colomnInfo");
			tableCode = (String)this.getSessionData("paraTableCode");
			checkFunction = (String)this.getSessionData("checkFunction");
			checkFunction_2 = (String)this.getSessionData("checkFunction_2");
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
			ParaAdjustDAO dao = new ParaAdjustDAO(this);
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
		String tableCode = null;
		String page = "1";
		String oldKey = null;
		int n = 1;
		String checkFunction = null;
		String checkFunction_2 = null;
		String localInfo = null;
		try {
			colomnInfo = (Vector)this.getSessionData("colomnInfo");
			tableCode = (String)this.getSessionData("paraTableCode");
			checkFunction = (String)this.getSessionData("checkFunction");
			checkFunction_2 = (String)this.getSessionData("checkFunction_2");
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
			ParaAdjustDAO dao = new ParaAdjustDAO(this);
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

			for (int i = 0; i < values.length; i++) {
				Hashtable htable = (Hashtable)colomnInfo.get(i);
				values[i] = this.getStringAt((String)htable.get("col"));

			}

			checkFunction = (String)this.getSessionData("checkFunction");
			checkFunction_2 = (String)this.getSessionData("checkFunction_2");
			localInfo =
				(String)this.getSessionData("EmployeeCode")
					+ "|"
					+ (String)this.getSessionData("AreaCode")
					+ "|"
					+ (String)this.getSessionData("Major");

			ParaAdjustDAO dao = new ParaAdjustDAO(this);
			dao.delete(tableCode, colomnInfo, values, checkFunction, localInfo, checkFunction_2);

			this.updateSessionData("isUpdated", "true");
			this.setOperationDataToSession();

			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><info>删除参数成功</info>");
		} catch (Exception e) {
			setReplyPage("DirectOutput<?xml version=\"1.0\" encoding=\"GBK\"?><error>删除参数失败：" + e.getMessage() + "</error>");
		}
	}

	private void performFilter() throws Exception {
		String paraTable = null;
		String tableCode = null;
		String tableName = null;
		String needReload = null;
		String restrict = null;

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

			this.updateSessionData("paraTableCode", tableCode);
			this.updateSessionData("paraTableName", tableName);
			this.updateSessionData("needReload", needReload);
			this.updateSessionData("restrict", restrict);

			TableAdjustDAO dao = new TableAdjustDAO(this);
			head = dao.performQueryHeadDetail(tableCode);
			this.updateSessionData("colomnInfo", head);
			this.setFieldValue("paraTable", paraTable);
			this.setOperationDataToSession();

		} catch (Exception e) {
			throw e;
		}

	}

	private void performReloadTable() throws Exception {

		Vector colomnInfo = null;
		String tableCode = null;
		String tableName = null;
		String page = "1";

		int n = 1;
		try {
			tableCode = ((String)this.getSessionData("paraTableCode")).toUpperCase();
			tableName = (String)this.getSessionData("paraTableName");

			ParaAdjustDAO dao = new ParaAdjustDAO(this);
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
