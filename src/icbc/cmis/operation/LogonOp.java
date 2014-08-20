package icbc.cmis.operation;


import java.util.*;import icbc.cmis.base.*;/**
 * Insert the type's description here.
 * Creation date: (2001-12-28 14:08:27)
 * @author: Administrator
 */
public class LogonOp extends CmisOperation{

/**
 * LogonOp constructor comment.
 */
public LogonOp() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (2001-12-28 14:12:28)
 */
public void execute()throws Exception
{
	try
		{
			String ar[] = (String[])getObjectAt("myField1");
			for(int i = 0;i<ar.length;i++){
				System.out.println("array:"+ar[i]);
			}
			KeyedDataCollection k1 = (KeyedDataCollection)getOperationData().getElement("myKcoll");
			System.out.println(":"+(String)k1.getValueAt("fieldData1"));
			System.out.println(":"+(String)k1.getValueAt("fieldData2"));
			System.out.println("myKcoll.fieldData1:"+getStringAt("myKcoll.fieldData1"));
			System.out.println("myKcoll.fieldData2:"+getStringAt("myKcoll.fieldData2"));
			addSessionData("myKcoll",k1);
			KeyedDataCollection k2 = new KeyedDataCollection();
			k2.addElement("sss","xxx");
			addSessionData("myKcoll1",k2);

			String accountCode= (String) getStringAt("accountCode");
			String areaCode= (String) getStringAt("areaCode");
			String passwd= (String) getStringAt("passwd");
			String major= (String) getStringAt("major");

			MyExampleSqlSrv srv = new MyExampleSqlSrv(this);
			Hashtable table = srv.getUserInfo(accountCode,areaCode);
			if (table == null)
			{
				throw new TranFailException("xdtz22126","LogonOp.execute()",
					"地区号：" + areaCode + " 柜员号：" + accountCode + " 该账号并没有在系统中登记！请于您的管理员联系。");
			}
			if (((String)table.get("5")).equals("0"))
			{
				throw new TranFailException("xdtz22126","LogonOp.execute()",
					"地区号：" + areaCode + " 柜员号：" + accountCode + " 该账号没有激活！请于您的管理员联系。");
			}
			if (((String)table.get("4")).equals("1"))
			{
				throw new TranFailException("xdtz22126","LogonOp.execute()",
					"地区号：" + areaCode + " 柜员号：" + accountCode + " 该账号已作废！请于您的管理员联系。");
			}
			if (!((String)table.get("2")).equals(passwd))
			{
				throw new TranFailException("xdtz22126","LogonOp.execute()",
					"地区号：" + areaCode + " 柜员号：" + accountCode + " 口令错误，请返回主页重试。");
			}
			String EmployeeName= (String)table.get("1");
			String majorName= srv.getMajor(major);

			if (majorName == null)
			{
				throw new TranFailException("xdtz22126","LogonOp.execute()","系统中没有定义" + major + " 专业！请于您的管理员联系。");
			}

			Hashtable t = srv.getAuth(accountCode,areaCode,major);
			if (t == null)
			{
				throw new TranFailException("xdtz22126","LogonOp.execute()",
					"地区号："
						+ areaCode
						+ " 柜员号："
						+ accountCode
						+ " 没有"
						+ major
						+ " 专业的授权！请于您的管理员联系。");
			}
			String EmployeeClass= (String)t.get("1");
			String EmployeeClassName= (String)t.get("2");
			if (EmployeeClassName.equals(""))
			{

				throw new TranFailException("xdtz22126","LogonOp.execute()",
					"地区号："
						+ areaCode
						+ " 柜员号："
						+ accountCode
						+ "专业："
						+ majorName
						+ " 的柜员级别错误！请于您的管理员联系。");
			}

			Hashtable ta = srv.getAreaInfo(areaCode);

			if (ta == null)
			{
				throw new Exception("取 " + areaCode + " 的地区名称时出错！请于您的管理员联系。");
			}
			String flag = (String)ta.get("2");
			if(flag == null){
				System.out.println("*********BankFlag******:"+flag);
				flag = "2";

			}
			addSessionData("AreaCode", areaCode);
			addSessionData("AreaName",ta.get("1"));
			addSessionData("BankFlag", flag);
			addSessionData("EmployeeCode", accountCode);
			addSessionData("EmployeeName", EmployeeName);
			addSessionData("EmployeeClass", EmployeeClass);
			addSessionData("EmployeeClassName", EmployeeClassName);
			addSessionData("Major", major);
			addSessionData("MajorName", majorName);
			addSessionData("Login", "YES");
			setReplyPage("/icbc/cmis/frames.jsp");
			generalMenu();
		//	addDataField("queryTime","200201");
		//	addDataField("GWRetCode","0");
		//	addDataField("TranResult","0");
		//	byte[] id = sendToMQ("logonFmtReq");
		//	receiveFromMQ("logonFmtRep",id);
		//	System.out.println("operationName"+getStringAt("operationName"));
		}
		catch(TranFailException e){
			setErrorCode(e.getErrorCode());
			setErrorDispMsg(e.getDisplayMessage());
			setErrorLocation(e.getErrorLocation());
			setErrorMessage(e.getErrorMsg());
			setReplyPage("/icbc/cmis/error.jsp");
		}
		catch (Exception e)
		{
			setErrorCode("xdtz22126");
			setErrorDispMsg("交易平台错误：客户登录失败");
			setErrorLocation("LogonOp.execute()");
			setReplyPage("/icbc/cmis/error.jsp");
			setErrorMessage(e.getMessage());
			setReplyPage("/icbc/cmis/error.jsp");
		}


}

  public void generalMenu() throws Exception {

	StringBuffer buffer = new StringBuffer();
	String major =  (String)getSessionData("Major");
	String employeeCode =  (String)getSessionData("EmployeeCode");
	String areaCode =  (String)getSessionData("AreaCode");
	String bankFlag =  (String)getSessionData("BankFlag");
	String employeeClass =  (String)getSessionData("EmployeeClass");
	String func = "";
	try {
		MyExampleSqlSrv srv = new MyExampleSqlSrv(this);
		func = srv.getEmployeeFun(areaCode,employeeCode,major);
	  //response.setContentType("text/xml; charset=GBK");
	 	buffer.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	  	buffer.append("<!DOCTYPE Navi SYSTEM \"/icbc/cmis/navigator.dtd\">");
	  	buffer.append("<?xml-stylesheet type=\"text/xsl\" href=\"/icbc/cmis/navigator.xsl\" ?>");
	  	buffer.append("<Navigation>");

	 	subMenu(buffer,major,0,0,func,bankFlag,employeeClass);
	 	buffer.append("</Navigation>");
	} catch (Exception e) {
	 	//response.setContentType("text/html; charset=GBK");
	 	//out.println("<html><body>取人员专业表出错：<br>"+ sql + "<br>" + e.getMessage() + "</body></html>");
	 	buffer.append("取人员专业表出错：<br>" + e.getMessage());
		throw e;

	}
	String menuStr = new String(buffer);
//	System.out.println(menuStr);
	int index = menuStr.indexOf("/>");
	if(index != -1) index = menuStr.indexOf("/>",index+2);
	menuStr = menuStr.substring(0,index+2)+"</Navigation>";
	//System.out.println(menuStr);
	addDataField("menuStr",menuStr);
  }                                                              private void subMenu(StringBuffer buffer, String major, int pCode, int layer, String func, String bankFlag, String employeeClass)
	{
	  try {
		  MyExampleSqlSrv srv = new MyExampleSqlSrv(this);
		  Vector vTable = srv.getMenuInfo(major,pCode,bankFlag,employeeClass);
		  if(vTable == null) return;
		  int num = 0;
		  for(int i = 0;i<vTable.size();i++){
			  Hashtable hTable = (Hashtable)vTable.elementAt(i);
			  int intF = Integer.valueOf((String)hTable.get("1")).intValue();
			  if (func.charAt(intF) == '0')
		    {
			  String url = (String)hTable.get("4");
			  if (url == null) { url = "#"; }
			  buffer.append("<Navigator ID='" + (String)hTable.get("1") +
				  		"' AncestorID='" + String.valueOf(pCode) +
				  		"' Layer='" + String.valueOf(layer) +
				  		"' Title='" + (String)hTable.get("2") +
				  		"' Childs='" + (String)hTable.get("3") +
				  		"' Url='" + url +
				  		"' Image='/icbc/cmis/images/dot.gif' />"
						);
			  subMenu(buffer,major,intF,layer + 1,func,bankFlag,employeeClass);
		    }
		  }
		//if (rs!=null) rs.close();
	  } catch (Exception e) {
	  buffer.append("生成菜单出错：" + e.getMessage());
	  }

	}}