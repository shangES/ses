package icbc.cmis.base;

import icbc.cmis.operation.*;
import java.util.*;
import java.sql.*;
/**
 * Title:20060222
 * Description:获取错误信息描述
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author chenj
 * @version 1.0
 */

public class genMsg {


  private static Hashtable msgTable = null;
  
  public genMsg() {
    super();
  }

  public static void initializeMsgList() throws Exception {

	msgTable = new java.util.Hashtable();

	for(Iterator i = CmisConstance.allLang.iterator(); i.hasNext();){
		String lang_code = (String)i.next();
		Hashtable tmpTable = new Hashtable();
		
		//msgTable.put(lang_code,new Hashtable());
		  String sql = "select errcode,message from system_errmsg where language = '" + lang_code +"'";
		  SqlTool sqlTool = null;
		  try{	
			  sqlTool = new SqlTool(new DummyOperation());
			  sqlTool.getConn("missign");
			  ResultSet rs = sqlTool.executeQuery(sql);
			  while(rs.next()){
				tmpTable.put(rs.getString(1),rs.getString(2));
			  }
			  sqlTool.closeconn();
		  }
		  catch(Exception e){
			  try{
				  if(sqlTool!=null)
			  sqlTool.closeconn();
			  }catch(Exception ee){}
			  throw new TranFailException("icbc.cmis.base.genMsg","genMsg.initializeMsgList","init msgTab error!!!",e.getMessage());
		  }
		msgTable.put(lang_code,tmpTable);
	}
  }

  /**
   * 根据编码获取系统相关信息
   * Creation date: (2006-2-22)
   * @return java.lang.String
   */ 
  public static String getErrMsg(String errCode) {
	try {
		
		String msg = (String)((Hashtable)msgTable.get("zh_CN")).get(errCode.trim());
		/*	if(msg == null || msg.trim().length() == 0){
				msg = (String)((DictBean)dict_tables.get("errTable")).get(errorCode);
			}
		*/
		if (msg == null || msg.trim().length() == 0) {
			msg  =  "该信息提示代码: " + errCode + ",未在CM2002中进行登记!";
		}
		return msg;
	} catch (Exception e) {
			return "该信息提示代码: " + errCode + ",未在CM2002中进行登记!";
	}
  }

/**
 * 根据编码获取系统相关信息
 * Creation date: (2006-2-22)
 * @return java.lang.String
 */ 
public static String getErrMsgByLang(String langCode, String errCode) {
  try {
		
	  String msg = (String)((Hashtable)msgTable.get(langCode)).get(errCode.trim());
	  /*	if(msg == null || msg.trim().length() == 0){
			  msg = (String)((DictBean)dict_tables.get("errTable")).get(errorCode);
		  }
	  */
	  if ((msg == null || msg.trim().length() == 0)&&(langCode=="zh_CN")) {
		  msg  =  "该信息提示代码: " + errCode + ",未在CM2002中进行登记!";
	  }
	if ((msg == null || msg.trim().length() == 0)&&(langCode=="en_US")) {
			  msg  =  "prompt code: " + errCode + ",unregistered in CM2002!";
		  }
	  return msg;
  } catch (Exception e) {
		  if(langCode=="zh_CN"){
		  return "该信息提示代码: " + errCode + ",未在CM2002中进行登记!";
		  }else
	          {
			  return "prompt code: " + errCode + ",unregistered in CM2002!";
			  }
		  
  }
}
  
  /**
	* 根据编码获取系统相关信息,并用参数替换通配符号
	* Creation date: (2006-2-22)
	* @return java.lang.String
	*/ 
  public static String getErrMsg(String errCode,String parastring) {
	try{
		String msg = (String)((Hashtable)msgTable.get("zh_CN")).get(errCode.trim());
		if (msg == null || msg.trim().length() == 0) {
			return "code: " + errCode + ",unknow code!";
		}
		
		StringTokenizer token1 = new StringTokenizer(msg,"$");
		StringTokenizer token2 = new StringTokenizer(parastring,"|");
		StringBuffer msB = new StringBuffer(10);

		while (token1.hasMoreTokens()) {
			msB.append(token1.nextToken());
				if (token2.hasMoreTokens()){
					msB.append(token2.nextToken());
				}
		}
		return msB.toString();
	}
	catch (Exception e) {
		return getErrMsg(errCode);
	}


  }
  
/**
  * 根据编码获取系统相关信息,并用参数替换通配符号
  * Creation date: (2006-2-22)
  * @return java.lang.String
  */ 
public static String getErrMsgByLang(String langCode, String errCode,String parastring) {
  try{
	  String msg = (String)((Hashtable)msgTable.get(langCode)).get(errCode.trim());
	  if (msg == null || msg.trim().length() == 0) {
		  return "code: " + errCode + ",unknow code!";
	  }
		
	  StringTokenizer token1 = new StringTokenizer(msg,"$");
	  StringTokenizer token2 = new StringTokenizer(parastring,"|");
	  StringBuffer msB = new StringBuffer(10);

	  while (token1.hasMoreTokens()) {
		  msB.append(token1.nextToken());
			  if (token2.hasMoreTokens()){
				  msB.append(token2.nextToken());
			  }
	  }
	  return msB.toString();
  }
  catch (Exception e) {
	  return getErrMsg(errCode);
  }


}  
  
  
  /**
	* 根据编码获取系统相关信息,是否在提示信息当中有信息代码
	* Creation date: (2006-2-22)
	* @return java.lang.String
	*/
    
  public static String getErrMsgCode(String errCode,String parastring) {
	return errCode+getErrMsg(errCode,parastring);
  }
  
  public static String getNo() throws TranFailException {
	String strNo = null;
	try{
	    //getTransNoDAO dao = new getTransNoDAO(new icbc.cmis.operation.DummyOperation());	
		//strNo = dao.getNo();
	} 

    catch(Exception ex) {
      throw new TranFailException("cmisGetTranNo","",ex.getMessage(),ex.getMessage());
    }
	return strNo;	

  }


}