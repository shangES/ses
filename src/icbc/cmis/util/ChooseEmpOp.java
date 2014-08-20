package icbc.cmis.util;

import icbc.cmis.base.*;
import icbc.cmis.operation.*;
import icbc.cmis.service.*;
import java.util.*;
import java.sql.*;
import icbc.cmis.util.*;

public class ChooseEmpOp extends CmisOperation {

	public ChooseEmpOp() {
		super();
	}

	public void execute() throws java.lang.Exception {
		/**@todo: implement this icbc.cmis.operation.CmisOperation abstract method*/
		try {
			String action = getStringAt("opAction");
			if (action.equals("QueryPage")) { //查询柜员
				querySelectModule();
			}
		} catch (TranFailException te) {
			this.setOperationDataToSession();
			throw te;
		} catch (Exception e) {
			this.setOperationDataToSession();
			throw new TranFailException(
				"099999",
				"icbc.cmis.util.ChooseEmpOp.execute()");
		}
	}

	/**
	 * 查询柜员
	 * @throws Exception
	 */
	private void querySelectModule() throws Exception {
		IndexedDataCollection v_return = new IndexedDataCollection();
		try {
			String employee_code = this.getStringAt("employee_code"); //柜员编码
			String employee_name = this.getStringAt("employee_name"); //柜员姓名
			String area_code = this.getStringAt("area_code"); //地区号
			String sub_bank = this.getStringAt("sub_bank"); //是否查询下级行
			String major_code = this.getStringAt("major_code"); //专业号
			String class_code = this.getStringAt("class_code"); //岗位号
			String beginPos = this.getStringAt("beginPos"); //起始位置
			String subemployee = this.getStringAt("subemployee");//除去的柜员
			String a1 = "";
			String a2 = "";
			String a3 = "";
			String a4 = "";
			String a5 = "";
			String a6 = "";
			String a7 = "";
			ChooseEmpDAO dao = new ChooseEmpDAO(this);
			v_return =
				dao.eventQueryDao(
					employee_code,
					employee_name,
					area_code,
					sub_bank,
					major_code,
					class_code,
					beginPos,
					subemployee);
			if (this.isElementExist("query_Result")) {
				this.removeDataField("query_Result");
			}

			String xmlPack = "<?xml version=\"1.0\" encoding=\"GB2312\"?>";

			if (v_return.getSize() > 0) {
				KeyedDataCollection kdresult =
					(KeyedDataCollection) v_return.getElement(0);
				a5 = (String) kdresult.getValueAt("allcount"); //记录条数
				a6 = (String) kdresult.getValueAt("beginPos"); //记录起始位置
				xmlPack += "<Content pages=\""
					+ a6
					+ "\" itemamount=\""
					+ a5
					+ "\">";
				for (int j = 0; j < v_return.getSize(); j++) {
					KeyedDataCollection kdresult1 =
						(KeyedDataCollection) v_return.getElement(j);
					a1 = (String) kdresult1.getValueAt("employee_code"); //柜员编码
					a2 = (String) kdresult1.getValueAt("employee_name"); //柜员姓名
					a3 = (String) kdresult1.getValueAt("area_code"); //地区号
					a4 = (String) kdresult1.getValueAt("area_name"); //地区名称

					xmlPack += "<CodeName "
						+ "a7=\""
						+ String.valueOf(j + 1)
						+ "\" a1=\""
						+ a1
						+ "\" a2=\""
						+ a2
						+ "\" a3=\""
						+ a3
						+ "\" a4=\""
						+ a4
						+ "\"/>";
				}
			} else {
				a5 = "0"; //记录条数
				a6 = "0"; //记录起始位置
				xmlPack += "<Content pages=\""
					+ a6
					+ "\" itemamount=\""
					+ a5
					+ "\">";

				a1 = ""; //柜员编码
				a2 = ""; //柜员姓名
				a3 = ""; //地区号
				a4 = ""; //地区名称
				a7="";

				xmlPack += "<CodeName "
					+ "a7=\""
					+ a7
					+ "\" a1=\""
					+ a1
					+ "\" a2=\""
					+ a2
					+ "\" a3=\""
					+ a3
					+ "\" a4=\""
					+ a4
					+ "\"/>";

			}
			xmlPack += "</Content>";
			this.setOperationDataToSession();
			this.setReplyPage("DirectOutput" + xmlPack);
//			this.setReplyPage("DirectOutput" + icbc.cmis.util.Func_XMLfiltrate.validXml(xmlPack));
		} catch (TranFailException e) {
			this.setOperationDataToSession();
			throw e;
		} catch (Exception e) {
			this.setOperationDataToSession();
			throw new TranFailException(
				"099999",
				"icbc.cmis.util.ChooseEmpOp.querySelectModule()");
		}
	}

}