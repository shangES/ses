<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page session="false"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.*"%>
<%@ page import="icbc.cmis.base.*" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ include file="../../util/header.jsp" %>
<cmis:sessionCheck>
<cmis:muidef def="icbc.cmis.flow.BA.flow_BA">
<html>

<%
CMisSessionMgr sm = new CMisSessionMgr(request);
//--------------------------------------------------------\
String LangCode = (String)sm.getSessionData("LangCode");
if(LangCode == null || LangCode.equals("")) LangCode = "zh_CN";
icbc.cmis.tags.muiStr muistr = new icbc.cmis.tags.muiStr("icbc.cmis.flow.BA.flow_BA",LangCode);
//--------------------------------------------------------/

String arraystr = "";
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");

String formflag = (String)context.getValueAt("formflag");	

java.util.Hashtable h_table=CmisConstance.getDictParam();
Vector advicelist = (Vector)context.getValueAt("advicelist"); 
String titlecontent="";
if (formflag.equals("1"))
{
  titlecontent=muistr.getStr("C000033");//"附加条件或限制性条款";
}
if (formflag.equals("2")) 
{
  titlecontent=muistr.getStr("C000091");//"意见说明";
}
if (formflag.equals("3"))
{
  titlecontent=muistr.getStr("C000067");//"辅助审核内容";
}
String documentcontent="";

Vector list = (Vector)context.getValueAt("contentlist"); 

try
  { 
    for (int i=0;i<list.size();i++){
    Hashtable htable = (Hashtable)list.get(i); 
    
    if (formflag.equals("1"))
	{
	  documentcontent=(String)htable.get("process021");
	}
	if (formflag.equals("2"))
	{
	  documentcontent=(String)htable.get("process012");
	}
	if (formflag.equals("3"))
	{
	  documentcontent=(String)htable.get("process020");
	}
   }
  }
  catch (Exception e)
  {
  }
        
%>

<head>
<title>
显示具体内容
</title>
<link rel="stylesheet" href="<%=baseWebPath%>/<cmis:muipub item="CSSPATH"/>/main.css" type="text/css">

<script language="JavaScript" >


function f_cancel() {
  window.returnValue = null;
  window.close();
}

</script></head>
<body leftmargin="0" topmargin="0" >
<cmis:framework align="center" valign="center">
<cmis:tabpage width="0" height="0"> 
<cmis:tabpagebar  title="<%=titlecontent%>" selected="true" />
<cmis:tabpagecontent info="">
  
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <% if (formflag.equals("3")) {%>
  <tr>
  <td class="td1"><cmis:muiitem item="C000067"/><!-- 辅助审核说明 --></td>
  </tr>
  <%}%>
    <tr>
      <td class="td1">
         <TEXTAREA NAME="" ROWS="5" COLS="80" readonly >&nbsp;<%=documentcontent%></TEXTAREA>   
      </td>
    </tr>
  </table>
 
<% if (formflag.equals("3")) {%>

<table width="100%" border="1" cellspacing="" cellpadding="0">

<tr>
<td align="center" class="td1"><cmis:muiitem item="C000001"/></td><!-- 序号 -->
<td align="center" class="td1"><cmis:muiitem item="C000028"/></td><!-- 地区 -->
<td align="center" class="td1"><cmis:muiitem item="C000081"/></td><!-- 辅助审核人 -->
<td align="center" class="td1"><cmis:muiitem item="C000031"/></td><!-- 处理意见 -->
<td align="center" class="td1"><cmis:muiitem item="C000035"/></td><!-- 处理时间 -->

</tr>
<%for(int i=0; i<advicelist.size(); i++) {
	Hashtable htable = (Hashtable)advicelist.get(i);
	String advice006 = (String)htable.get("advice006");
	String advice006_name = "";
	if(advice006.equals("0")) advice006_name = muistr.getStr("C000015");//"未处理";
	if(advice006.equals("1")) advice006_name = muistr.getStr("C000074");//"不同意";
	if(advice006.equals("2")) advice006_name = muistr.getStr("C000075");//"同意";
	
%>
<tr>
    <td align="center"><%=i+1%></td>
	<td><%=(String)htable.get("advice004_name")%></td>
	<td><%=(String)htable.get("advice005_name")%></td>
	<td><%=advice006_name%></td>
	<td><%=(String)htable.get("advice007")%></td>
</tr>
<tr>
	<td align="center" class="td1"><%=muistr.getStr("C000091")%><!-- 意见说明 --></td><td colspan='5'><TEXTAREA NAME="" ROWS="4" COLS="70" readonly ><%=(String)htable.get("advice008")%></TEXTAREA> </td>
	 
</tr>
<%}%>

</table>
<%}%>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
<td  class="td1" align="right"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/close.gif" style="cursor: hand" onClick="f_cancel()"></td>
</tr>
 </table>
  </cmis:tabpagecontent> 
  </cmis:tabpage> </cmis:framework>
</body>
</html></cmis:muidef></cmis:sessionCheck>
