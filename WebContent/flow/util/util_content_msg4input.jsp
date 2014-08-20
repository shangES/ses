<!--授信用意见输入-->
<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page session="false"%>
<%@ page import="java.util.*"%>
<%@ page import="icbc.cmis.base.*" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ include file="../../util/header.jsp" %>


<cmis:sessionCheck>
<html>

<%
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");
String out_msg = (String)context.getValueAt("out_msg");
String out_flag = (String)context.getValueAt("out_flag");
if(out_msg==null){
	out_msg = "";
}

%>

<html>
<head>
<script src="<%=baseWebPath%>/jslib/data_check.js"></script>
<script src="<%=baseWebPath%>/jslib/tools.js"></script>
<script src="<%=baseWebPath%>/flow/jslib/checkSyntax.js"></script>
<script Language="JavaScript">

function dosave(){
	if(isEmpty(form1.ta340041013.value)){
		alert("请输入意见说明");
		return;
	}
	window.returnValue = encode(form1.ta340041013.value);
	window.close();
}


function docancel(){
	window.returnValue = null;
	window.close();
}
</script>


<title>授信意见输入</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" href="<%=baseWebPath%>/css/main.css" type="text/css">
</head>

<body>
<form name="form1" method="post" action="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet">

<cmis:framework align="center">
<cmis:tabpage width="450">
<cmis:tabpagebar title="意见输入" url="" onclick="" selected="true" />
<cmis:tabpagecontent info="" align="center" valign="top">
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
<td>
<%if(out_flag.equals("0")){%>
<TEXTAREA rows="10" cols="80" name="ta340041013" onblur="javascript:checkAreaLength(this,500);javascript:checkSyntax(this);"><%=out_msg%></TEXTAREA>
<%}else{%>
<%=out_msg%>，请返回！
<%}%>
</td>
</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td class=td1 align="right"  valign="bottom" height=35>
		<%if(out_flag.equals("0")){%><IMG border="0" src="<%=baseWebPath%>/images/confirm.gif" class="hand" onclick="javascript:dosave();"><%}%><IMG 
			 border="0" src="<%=baseWebPath%>/images/cancel.gif" class="hand" onclick="javascript:docancel();">
		</td>
	</tr>
</table>

</cmis:tabpagecontent>
</cmis:tabpage>
</cmis:framework>
</form>

</body>
</html>

</cmis:sessionCheck>

