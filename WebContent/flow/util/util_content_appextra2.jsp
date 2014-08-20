<!--扩展（相关信息）-->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ page session="false"%>
<%@ page import="java.util.Enumeration" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>

<%@ include file="../../util/header.jsp" %>

<%
String appextra_entcode2 = request.getParameter("entcode");
%>
<script Language="JavaScript">
function showsth() {
	var ts = window.showModalDialog("<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.GA.GA_InterrelatedInfoOp&opAction=EnterPage&opDataUnclear=true&CustomerId=<%=appextra_entcode2%>&time=" + (new Date), window,"dialogWidth:650px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no;z-lock:yes;moveable:no;copyhistory:yes");
	return;
}
</script>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
<td class=td1 align="right"  valign="bottom" height=35>
<IMG border="0" src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/xiangguanxinxi.gif" class="hand" onclick="javascript:showsth();">
</td></tr>
</table>


