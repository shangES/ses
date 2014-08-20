<!--附件条件或限制条款-->
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
//输入非调查人为附加条件或限制性条款内容
String arraystr = "";
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");
	
String entcode = (String)context.getValueAt("entcode");	  //－客户号
String tradecode = (String)context.getValueAt("tradecode");	//业务申请号
String ordernum = (String)context.getValueAt("ordernum");	//当前环节序号
String ordercode = (String)context.getValueAt("ordercode");	//当前环节代码
java.util.Hashtable h_table=CmisConstance.getDictParam();

String documentcontent="";
Vector list = (Vector)context.getValueAt("contentlist"); 

try
  { 
    for (int i=0;i<list.size();i++){
    Hashtable htable = (Hashtable)list.get(i); 
    
	documentcontent=documentcontent=(String)htable.get("process021");

   }
  }
  catch (Exception e)
  {
  }
        
%>

<head>
<title>附加条件或限制性条款</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" href="<%=baseWebPath%>/css/main.css" type="text/css">

<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/data_check.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/liball.js"></script>
<script src="<%=baseWebPath%>/flow/<cmis:muipub item="JSPATH"/>/checkSyntax.js"></script>
<script language="JavaScript" >
function save()
{
  window.returnValue = form1.process021.value;
  window.close();  
}

function f_cancel() {
  window.returnValue = null;
  window.close();
}

</script></head>
<body leftmargin="0" topmargin="0">
<form name="form1" method="post" action="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet">
	<input type=hidden name="operationName" value="icbc.cmis.flow.util.util_content_msg3">
	<input type=hidden name="opAction" value="">
<div align="center"> <cmis:tabpage width="0" height="0"> 
<cmis:tabpagebar  titleid="C000033" selected="true" />
  <cmis:tabpagecontent info="">
	<input type="hidden" name="entcode" value="<%=entcode%>">
	<input type="hidden" name="tradecode" value="<%=tradecode%>">
	<input type="hidden" name="ordernum" value="<%=ordernum%>">
	<input type="hidden" name="ordercode" value="<%=ordercode%>">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
	    <td  class="td1"><cmis:muiitem item="C000033"/><cmis:muiitem item="C000090"/></td> <!-- 附加条件或限制性条款(最多输入500个汉字) -->
	    </tr>
    <tr>
      <td class="td1">       
         <TEXTAREA NAME="process021" ROWS="5" COLS="80" onblur="javascript:checkAreaLength(this,1000);javascript:checkSyntax(this);"><%=documentcontent%></TEXTAREA>     
        <div align="right" class="td1"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/confirm.gif" style="cursor: hand" onClick="save()"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/close.gif" style="cursor: hand" onClick="f_cancel()"></div>
      </td>
    </tr>
  </table>
  </cmis:tabpagecontent> 
  </cmis:tabpage> </div>
  </form>
</body>
</html></cmis:muidef></cmis:sessionCheck>
