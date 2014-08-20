<%@ page contentType="text/html;charset=GBK"%>
<%@ page session="false"%>
<%@ page import="icbc.cmis.base.*" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ include file="header.jsp" %>
<jsp:useBean id="utb2" scope="page" class="icbc.cmis.presentation.JspContextServices">
	<%utb2.initialize(request,response); %>
</jsp:useBean>
<%
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");
%>
<cmis:sessionCheck>
<cmis:muidef def="icbc.cmis.ok_util_info">
<html>
<head>
<title>
<cmis:muiitem item = "C000003"/>
</title>
<link rel="stylesheet" href="<%=baseWebPath%>/<cmis:muipub item="CSSPATH"/>/main.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>

<%
String ret1 = (String)context.getValueAt("infoReturn");
out.print(utb2.getFormHtml(ret1,"returnForm"));
%>
<body>
<div align="center">
<cmis:tabpage width="500" height="300">
<cmis:tabpagebar titleid = "C000002" selected="true" />
<cmis:tabpagecontent>
<h2><%= (String)context.getValueAt("infoTitle") %></h2>
<%= (String)context.getValueAt("infoMsg") %>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p align="right"><a><img  border="0" src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/return.gif"  height="24"  onclick="javascript:returnForm.submit();"></a></p>
</cmis:tabpagecontent>
</cmis:tabpage>
</div>
<jsp:include page="/util/footer.jsp" flush="true" />
</body>
</html>
</cmis:muidef>
</cmis:sessionCheck>