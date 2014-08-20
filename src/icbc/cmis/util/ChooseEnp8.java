package icbc.cmis.util;
import icbc.cmis.operation.*;
import icbc.cmis.base.*;
import icbc.cmis.util.*;
import java.lang.*;
import java.sql.*;
import java.util.*;
import icbc.cmis.second.pkg.*;

public class ChooseEnp8 extends CmisOperation {
  public ChooseEnp8() {
	super();
  }
  public void execute() throws Exception {
	try {
	  String opAction = this.getStringAt("opAction");
	  if (opAction.equals("20001")) { //20001为查询
		query();
	  }
	}catch (Exception e) {
	  this.setOperationDataToSession();
	  throw e;
	}
  }
		 public void query() throws TranFailException {
					  try {
						  String customercode = this.getStringAt("customercode"); //客户代码
						  String AreaCode = (String)this.getSessionData("AreaCode"); // 地区代码
						  String EmployeeCode = (String)this.getSessionData("EmployeeCode"); //柜员代码
						  ChooseEnp8DAO dao = new ChooseEnp8DAO(this);
						  Hashtable ht_detail = new Hashtable();
						 
						  String v_return = dao.query(customercode,EmployeeCode,AreaCode);
						  
						  String xmlPack =
							  "DirectOutput<?xml version=\"1.0\" encoding=\"GB2312\"?>";

//						  String glkh ="";
//					  if (v_return.size() == 0) { 
//							 glkh = "0";  //是否有查询权限
//						  } else {
//							 glkh="1";
//					   }
//						  xmlPack += "<Content result='" + v_return + "'  ></Content>";
						  xmlPack += "<Content result='" +icbc.cmis.util.Func_XMLfiltrate.validXml(v_return)+ "'  ></Content>";
						  setReplyPage(xmlPack);
//						  setReplyPage(icbc.cmis.util.Func_XMLfiltrate.validXml(xmlPack));
						  setOperationDataToSession();
					  } catch (TranFailException ex) {
						  throw ex;
					  } catch (Exception e) {
//						  throw new TranFailException("ChooseEnp8",
//						  //错误编码，使用者看
//						  "ChooseEnp8.query()", //出错位置,开发者看
//						  "查询失败！" + e.toString() //错误内容，开发者看
//						  );
							throw new MuiTranFailException("099995", "ChooseEnp8.query()",(String)this.getSessionData("LangCode"));
					  }
				  }
  }
 