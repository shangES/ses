<!--扩展（修改调查资料，保证金特别授权）-->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ page session="false"%>
<%@ page import="java.util.Enumeration" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>

<!--这里import页面需要调用的类，不要在页面上调op类或dao类，要用自己的类封装-->
<%@ page import="icbc.cmis.flow.util.*"%>
<%@ include file="../../util/header.jsp" %>


<%
String appextra_entcode = request.getParameter("entcode");
String appextra_tradecode = request.getParameter("tradecode");

String appextra_tradetype = request.getParameter("tradetype");
String appextra_employeecode = request.getParameter("employeecode");
String appextra_empareacode = request.getParameter("empareacode");


util_content_appextra c_appextra = new util_content_appextra();
String btnurl = c_appextra.spellbtn(appextra_entcode,appextra_tradecode,appextra_tradetype);


%>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/data_check.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/liball.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/tools.js"></script>
<script Language="JavaScript">
var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
var parser=new ActiveXObject("microsoft.xmldom");
parser.async="false";

<%if(!btnurl.equals("")){%>
function applymodify(){
	var url = "<%=baseWebPath+btnurl%>&time="+new Date();
	var ts=window.showModalDialog(encode(url),window,"dialogWidth:750px;dialogHeight:550px;center:yes;help:no;status:no;resizable:no");
}
<%}%>

function usebailgrant(){
	//去使用特别授权
	var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_appextraOp&opDataUnclear=true&opAction=querygrant&entcode=<%=appextra_entcode%>&tradecode=<%=appextra_tradecode%>&tradetype=<%=appextra_tradetype%>&employeecode=<%=appextra_employeecode%>&empareacode=<%=appextra_empareacode%>&time="+new Date();
	var ts=window.showModalDialog(encode(url),window,"dialogWidth:600px;dialogHeight:500px;center:yes;help:no;status:no;resizable:no");
	if(ts!=null){
		if(ts[0]==""){
			alert("没有选择特别授权");
			return;
		}
		//写入mag_special_grant_use表
		document.all.grantnum.value=ts[0];
		document.all.money.value=ts[1];
	    var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_appextraOp&opDataUnclear=true&opAction=savegrant&entcode=<%=appextra_entcode%>&tradecode=<%=appextra_tradecode%>&grantnum="+ts[0]+"&money="+ts[1];
		url+= "&newtime=" + new Date();
		objHTTP.Open('GET',encodeURL(url),false);
		objHTTP.Send();
		
		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		info = parser.getElementsByTagName("info");
		if(info.length > 0){
		    alert("使用保证金特别授权成功");
		    return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
		
	} else {
		alert("没有选择特别授权");
		return;
	}
}

function showsth() {
	var ts = window.showModalDialog("<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.GA.GA_InterrelatedInfoOp&opAction=EnterPage&opDataUnclear=true&CustomerId=<%=appextra_entcode%>&time=" + (new Date), window,"dialogWidth:650px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no;z-lock:yes;moveable:no;copyhistory:yes");
	return;
}
</script>

<input type="hidden" name="money" value="">
<input type="hidden" name="grantnum" value="">
<input type="hidden" name="entcode" value="<%=appextra_entcode%>">
<input type="hidden" name="applyno" value="<%=appextra_tradecode%>">
<input type="hidden" name="magkind" value="<%=appextra_tradetype%>">
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
<td class=td1 align="right"  valign="bottom" height=35>

<%if(!btnurl.equals("")){%>
<IMG border="0" src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/applymodify.gif" class="hand" onclick="javascript:applymodify();">
<%}%>

<%if(appextra_tradetype.equals("04")||appextra_tradetype.equals("06")||appextra_tradetype.equals("15")||appextra_tradetype.equals("16")||appextra_tradetype.equals("20")){%>
<IMG border="0" src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/bailgrant.gif" class="hand" onclick="javascript:usebailgrant();">
<%}%>

<IMG border="0" src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/xiangguanxinxi.gif" class="hand" onclick="javascript:showsth();">

</td></tr>
</table>


