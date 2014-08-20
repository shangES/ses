package icbc.cmis.util;

import java.sql.CallableStatement;
import java.util.Iterator;
import java.util.Vector;

import oracle.jdbc.driver.OracleTypes;

import icbc.cmis.base.MuiTranFailException;
import icbc.cmis.base.TranFailException;
import icbc.cmis.operation.CmisOperation;
import icbc.cmis.second.pkg.DBProcedureAccessService;
import icbc.cmis.second.pkg.DBProcedureParamsDef;

/*************************************************************
 * 
 * <b>创建日期: </b> 2005-4-25<br>
 * <b>标题: </b><br>
 * <b>类描述: </b><br>
 * <br>
 * <p>Copyright: Copyright (c)2005</p>
 * <p>Company: </p>
 * 
 * @author  胡晓忠
 * 
 * @version 
 * 
 * @since 
 * 
 * @see 
 * 
 *************************************************************/

public class Util_ChooseRateTypeOp extends CmisOperation {

	public Util_ChooseRateTypeOp() {
	}
	public void execute() throws Exception, TranFailException {
		
		String langCode = (String)getSessionData("LangCode");
		
	
		try {
			String opAction = getStringAt("opAction");
			String MoneyType = getStringAt("MoneyType");
			String Day_Begin = "";
			if (this.isElementExist("Day_Begin"))
				Day_Begin = getStringAt("Day_Begin");
			String Day_End = "";
			if (this.isElementExist("Day_End"))
				Day_End = getStringAt("Day_End");
			String Day_BeginUse = getStringAt("Day_BeginUse");
			String BorrowType = "";
			if (this.isElementExist("BorrowType"))
				BorrowType = getStringAt("BorrowType");
			String RateTypeCode = getStringAt("RateTypeCode");
			String flag = "";
			if (this.isElementExist("flag"))
				flag = getStringAt("flag");

			this.setFieldValue("flag", flag);
			this.setFieldValue("MoneyType", MoneyType);
			this.setFieldValue("Day_Begin", Day_Begin);
			this.setFieldValue("Day_End", Day_End);
			this.setFieldValue("Day_BeginUse", Day_BeginUse);
			this.setFieldValue("BorrowType", BorrowType);
			this.setFieldValue("RateTypeCode", RateTypeCode);

			String rateCode = "";
			if (!RateTypeCode.equals("null") && RateTypeCode.length() >= 2)
				rateCode = RateTypeCode.substring(0, 2);
			//得到利率大类CODE
			//this.setFieldValue("RateTypeCode",rateCode);
			this.setFieldValue("flag", flag);

			if (opAction.equals("20003")) {
				performXml(RateTypeCode);
			} else if (opAction.equals("20002")) {
				String RateTime = getStringAt("RateTime");
				this.performConfirm(rateCode, RateTime);
			} else if (
				opAction.equals(
					"20001")) //if("".equals(RateTypeCode)||RateTypeCode==null){
				this.performRateType(flag, rateCode);
			else
				throw new MuiTranFailException(
					"099993",
					"util_chooseratetypeop.execute()",
					langCode);
		} catch (Exception e) {
			throw new MuiTranFailException(
				"099993",
				"util_chooseratetypeop",
				e.getMessage(),
				langCode);
		} finally {
			this.setOperationDataToSession();
		}

	}
	
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param RateTypeCode
	 * @param rateTime
	 *  
	 */
	private void performConfirm(String rateCode, String RateTime)
		throws Exception {
		try {
			DBProcedureParamsDef def =
				new DBProcedureParamsDef("proc_query_rate");
			if (rateCode.equals("32"))
				this.setFieldValue("Type", rateCode);
			else
				this.setFieldValue("Type", rateCode + RateTime);
			this.setFieldValue("RateTypeCode", rateCode + RateTime);
			Object a = this.getOperationData();
			def.addInParam("MoneyType"); //
			def.addInParam("Type"); //
			def.addInParam("Day_Begin"); //
			def.addInParam("Day_End"); //
			def.addInParam("Day_BeginUse"); //
			def.addInParam("BorrowType"); //
			def.addOutParam("out_Flag");
			def.addOutParam("out_Message");
			def.addOutParam("RateTypeCodeName");
			def.addOutParam("BaseRate");
			def.addOutParam("dict_rate_code");
			def.addOutParam("dict_rate_type");
			//调用存储过程
			DBProcedureAccessService proceSrv =
				new DBProcedureAccessService(this);
			int returnCode =
				proceSrv.executeProcedure(this.getOperationData(), def);
			proceSrv = null;

			

			String xmlPack =
				"DirectOutput<?xml version=\"1.0\" encoding=\"GB2312\"?>";
			xmlPack += "<datas>";
			String ratename = this.getStringAt("RateTypeCodeName");
			String baserate = "";
			baserate = this.getStringAt("BaseRate");
			if (baserate==null) baserate="-1";
			if (!baserate.equals("-1")) {
				baserate = this.getStringAt("BaseRate");
			}
			if ("-1".equals(baserate)) baserate="";
			String rate = this.getStringAt("Type");
			String dict_rate_code = this.getStringAt("dict_rate_code");
			String dict_rate_type = this.getStringAt("dict_rate_type");
			if (ratename == null) {
				ratename = "";
			}
			if (baserate == null) {
				baserate = "";
			}
			if (rate == null) {
				rate = "";
			}
			if (dict_rate_code == null) {
				dict_rate_code = "";
			}
			if (dict_rate_type == null) {
				dict_rate_type = "";
			}
			String flag = this.getStringAt("out_Flag");
			String message = this.getStringAt("out_Message");
			java.text.DecimalFormat format = new java.text.DecimalFormat();
			format.applyLocalizedPattern("##0.######");
            if(!"".equals(baserate))
            baserate = format.format(Double.parseDouble(baserate));
			xmlPack += "<en ratename=\""
				+ ratename
				+ "\"  baserate=\""
				+ baserate
				+ "\"  rate=\""
				+ rate
				+ "\"  dict_rate_code=\""
				+ dict_rate_code
				+ "\"   dict_rate_type=\""
				+ dict_rate_type
				+ "\"  flag=\""
				+ flag
				+ "\"  message=\""
				+ message
				+ "\"/>";
			xmlPack += "</datas>";
			setReplyPage(xmlPack);
			//this.setReplyPage(filename);

			//}
		} catch (TranFailException ee) {
			throw ee;
		} catch (Exception e) {

			throw new TranFailException(
				"xdtz0FFE6234",
				"util_chooseratetypeop.performConfirm()",
				e.getMessage(),
				e.getMessage());
		}

	}
	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * @param RateTypeCode
	 *  
	 */
	private void performXml(String RateTypeCode) throws Exception {
		Vector vec_time = new Vector();
		String xmlPack = "";
		String[] time = null;
		this.setFieldValue("rateTimeVec", "");
		Util_ChooseRateTypeDAO dao = new Util_ChooseRateTypeDAO(this);
		String langCode = (String)getSessionData("LangCode");
		dao.setLangCode(langCode);
		xmlPack = "DirectOutput<?xml version=\"1.0\" encoding=\"GB2312\"?>";
		xmlPack += "<datas>";
		try {
			vec_time = dao.getRateTime(RateTypeCode);
			//if(vec_time!=null)
			//time=(String) vec_time.elementAt(0);
		} catch (Exception e) {
			throw e;
		}
		for (int i = 0; i < vec_time.size(); i++) {
			time = (String[]) vec_time.elementAt(i);
			xmlPack += "<en dict_code=\""
				+ time[0]
				+ "\" dict_name=\""
				+ time[1]
				+ "\"/>";
		}
		xmlPack += "</datas>";
		setReplyPage(xmlPack);
		this.setOperationDataToSession();
	}

	/**
	 * <b>功能描述: </b><br>
	 * <p> </p>
	 * 
	 *  
	 */
	private void performRateType(String flag, String rateCode)
		throws Exception {
			

		try {
			Vector ret_Type = new Vector();
			Vector ret_Time = new Vector();
			Util_ChooseRateTypeDAO dao = new Util_ChooseRateTypeDAO(this);
			String langCode = (String)getSessionData("LangCode");
					dao.setLangCode(langCode);
			ret_Type = dao.getRateType(flag);
			ret_Time = dao.getRateTime(rateCode);
			this.setFieldValue("rateTypeVec", ret_Type);
			this.setFieldValue("rateTimeVec", ret_Time);

			setReplyPage("/icbc/cmis/util/util_chooseratetype.jsp");
		} catch (Exception e) {
			throw e;
		}
	}

}
