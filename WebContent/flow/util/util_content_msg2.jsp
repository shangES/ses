<!--调查分析意见输入-->

<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ page session="false"%>
<%@ page import="java.util.Enumeration" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<!--这里import页面需要调用的类，不要在页面上调op类或dao类，要用自己的类封装-->
<%@ page import="icbc.cmis.flow.util.*"%>
<%@ include file="../../util/header.jsp" %>


<cmis:muidef def="icbc.cmis.flow.BA.flow_BA">
<%
//固定传入的参数，按需get
//entcode,		－客户号
//tradecode		－业务申请号
//tradetype		－申请种类
//flowtype		－流程种类
//ordernum		－当前环节序号
//ordercode		－当前环节代码
//employeecode	－当前柜员
//empareacode	－当前地区
//busitype		－业务性质，0，自营，1，委托

//~~~~使用自己的前缀，与其他片断无重复

String flow_entcode = request.getParameter("entcode");
String flow_tradecode = request.getParameter("tradecode");
String flow_ordernum = request.getParameter("ordernum");
String flow_ordercode = request.getParameter("ordercode");
String flow_flowtype = request.getParameter("flowtype");
String flow_tradetype = request.getParameter("tradetype");
String flow_employeecode = request.getParameter("employeecode");
String flow_empareacode = request.getParameter("empareacode");
util_content_msg2 advicehistory = new util_content_msg2();
String firstflag="";  //用来标识是否是发起人
if (flow_ordernum.equals("1"))
{firstflag="1";}
if (!flow_ordernum.equals("1")){
firstflag = advicehistory.queryfirstflag(flow_entcode,flow_tradecode,flow_ordercode,flow_empareacode,flow_employeecode);
}
%>

<script language="JavaScript">

var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
var parser=new ActiveXObject("microsoft.xmldom");
parser.async="false";

function init()
{
}

function showfxadvice(entcode,tradecode,ordernum,ordercode,tradetype,flowtype,firstflag)
{
var ts=window.showModalDialog("<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg2Op&opDataUnclear=true&opAction=20002&msg2_entcode="+entcode+"&msg2_tradecode="+encode(tradecode)+"&msg2_ordernum="+ordernum+"&msg2_ordercode="+ordercode+"&firstflag="+firstflag+"&time="+new Date(),window,"dialogWidth:600px;dialogHeight:700px;center:yes;help:no;status:no;resizable:no");

if(ts!=null){
	if (firstflag=="1")
	{
		document.all.msg2_process012.value=ts[0];
		document.all.msg2_process013.value=ts[1];
		document.all.msg2_process014.value=ts[2];
		document.all.msg2_process015.value=ts[3];
		document.all.msg2_process016.value=ts[4];
		document.all.msg2_process027.value=ts[5];
		document.all.msg2_process028.value=ts[6];
		var length12 = document.all.msg2_process012.value.length;
		var t_mid12 = 0;
		
		if(length12%2==1){
			t_mid12 = (length12+1)/2;
		} else {
			t_mid12 = length12/2;
		}
		var process012_1 = document.all.msg2_process012.value.substring(0,t_mid12);
		var process012_2 = document.all.msg2_process012.value.substring(t_mid12);
		
		var length13 = document.all.msg2_process013.value.length;
		var t_mid13 = 0;
		
		if(length13%2==1){
			t_mid13 = (length13+1)/2;
		} else {
			t_mid13 = length13/2;
		}
		var process013_1 = document.all.msg2_process013.value.substring(0,t_mid13);
		var process013_2 = document.all.msg2_process013.value.substring(t_mid13);
		
		var length14 = document.all.msg2_process014.value.length;
		var t_mid14 = 0;
		
		if(length14%2==1){
			t_mid14 = (length14+1)/2;
		} else {
			t_mid14 = length14/2;
		}
		var process014_1 = document.all.msg2_process014.value.substring(0,t_mid14);
		var process014_2 = document.all.msg2_process014.value.substring(t_mid14);
		
		var length15 = document.all.msg2_process015.value.length;
		var t_mid15 = 0;
		
		if(length15%2==1){
			t_mid15 = (length15+1)/2;
		} else {
			t_mid15 = length15/2;
		}
		var process015_1 = document.all.msg2_process015.value.substring(0,t_mid15);
		var process015_2 = document.all.msg2_process015.value.substring(t_mid15);
		
		var length16 = document.all.msg2_process016.value.length;
		var t_mid16 = 0;
		
		if(length12%2==1){
			t_mid16 = (length16+1)/2;
		} else {
			t_mid16 = length16/2;
		}
		var process016_1 = document.all.msg2_process016.value.substring(0,t_mid16);
		var process016_2 = document.all.msg2_process016.value.substring(t_mid16);
		
		var length27 = document.all.msg2_process027.value.length;
		var t_mid27 = 0;
		
		if(length27%2==1){
			t_mid27 = (length27+1)/2;
		} else {
			t_mid27 = length27/2;
		}
		var process027_1 = document.all.msg2_process027.value.substring(0,t_mid27);
		var process027_2 = document.all.msg2_process027.value.substring(t_mid27);
		
		var length28 = document.all.msg2_process028.value.length;
		var t_mid28 = 0;
		
		if(length28%2==1){
			t_mid28 = (length28+1)/2;
		} else {
			t_mid28 = length28/2;
		}
		var process028_1 = document.all.msg2_process028.value.substring(0,t_mid28);
		var process028_2 = document.all.msg2_process028.value.substring(t_mid28);
	   if (length12>1000)
		{
		alert(<cmis:muiitem item="C000047" mark="\""/>);
		}
		if (length13>1000)
		{
		alert(<cmis:muiitem item="C000047" mark="\""/>);
		}
		if (length14>1000)
		{
		alert(<cmis:muiitem item="C000047" mark="\""/>);
		}
		if (length15>1000)
		{
		alert(<cmis:muiitem item="C000047" mark="\""/>);
		}
		if (length16>1000)
		{
		alert(<cmis:muiitem item="C000047" mark="\""/>);
		}
		if (length27>1000)
		{
		alert(<cmis:muiitem item="C000047" mark="\""/>);
		}
		if (length28>1000)
		{
		alert(<cmis:muiitem item="C000047" mark="\""/>);
		}
		//1
		var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg2Op&opDataUnclear=true&opAction=10001";
		url+="&numflag=1";
		url+="&msg2_entcode="+entcode;
	url+="&msg2_tradecode="+tradecode;
	url+="&msg2_ordernum="+ordernum;
	url+="&msg2_ordercode="+ordercode;
	url+="&msg2_tradetype="+tradetype;
	url+="&msg2_flowtype="+flowtype;
		url+= "&newtime=" + new Date();
		objHTTP.Open('POST',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		objHTTP.Send(encode("msg2_process012="+form1.msg2_process012.value+"&msg2_process013=&msg2_process014=&msg2_process015=&msg2_process027=&msg2_process028=&msg2_process016="));

		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
		
		//2
	
		var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg2Op&opDataUnclear=true&opAction=10001";
		url+="&numflag=2";
		url+="&msg2_entcode="+entcode;
	url+="&msg2_tradecode="+tradecode;
	url+="&msg2_ordernum="+ordernum;
	url+="&msg2_ordercode="+ordercode;
	url+="&msg2_tradetype="+tradetype;
	url+="&msg2_flowtype="+flowtype;
		url+= "&newtime=" + new Date();
		objHTTP.Open('POST',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		objHTTP.Send(encode("msg2_process013="+form1.msg2_process013.value+"&msg2_process012=&msg2_process014=&msg2_process015=&msg2_process027=&msg2_process028=&msg2_process016="));
		
		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
		
		//3
		var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg2Op&opDataUnclear=true&opAction=10001";
		url+="&numflag=3";
		url+="&msg2_entcode="+entcode;
	url+="&msg2_tradecode="+tradecode;
	url+="&msg2_ordernum="+ordernum;
	url+="&msg2_ordercode="+ordercode;
	url+="&msg2_tradetype="+tradetype;
	url+="&msg2_flowtype="+flowtype;
		url+= "&newtime=" + new Date();
		objHTTP.Open('POST',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		objHTTP.Send(encode("msg2_process014="+form1.msg2_process014.value+"&&msg2_process013=&msg2_process012=&msg2_process015=&msg2_process027=&msg2_process028=&msg2_process016="));

		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
		
		//4
		 var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg2Op&opDataUnclear=true&opAction=10001";
		url+="&numflag=4";
		url+="&msg2_entcode="+entcode;
	url+="&msg2_tradecode="+tradecode;
	url+="&msg2_ordernum="+ordernum;
	url+="&msg2_ordercode="+ordercode;
	url+="&msg2_tradetype="+tradetype;
	url+="&msg2_flowtype="+flowtype;
		url+= "&newtime=" + new Date();
		objHTTP.Open('POST',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		objHTTP.Send(encode("msg2_process015="+form1.msg2_process015.value+"&&msg2_process013=&msg2_process014=&msg2_process012=&msg2_process027=&msg2_process028=&msg2_process016="));

		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
		
		//5
		var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg2Op&opDataUnclear=true&opAction=10001";
		url+="&numflag=5";
		url+="&msg2_entcode="+entcode;
	url+="&msg2_tradecode="+tradecode;
	url+="&msg2_ordernum="+ordernum;
	url+="&msg2_ordercode="+ordercode;
	url+="&msg2_tradetype="+tradetype;
	url+="&msg2_flowtype="+flowtype;
		url+= "&newtime=" + new Date();
		objHTTP.Open('POST',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		objHTTP.Send(encode("msg2_process016="+form1.msg2_process016.value+"&&msg2_process013=&msg2_process014=&msg2_process015=&msg2_process027=&msg2_process028=&msg2_process012="));

		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
		
		//6
		var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg2Op&opDataUnclear=true&opAction=10001";
		url+="&numflag=6";
		url+="&msg2_entcode="+entcode;
	url+="&msg2_tradecode="+tradecode;
	url+="&msg2_ordernum="+ordernum;
	url+="&msg2_ordercode="+ordercode;
	url+="&msg2_tradetype="+tradetype;
	url+="&msg2_flowtype="+flowtype;
		url+= "&newtime=" + new Date();
		objHTTP.Open('POST',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		objHTTP.Send(encode("msg2_process027="+form1.msg2_process027.value+"&&msg2_process013=&msg2_process014=&msg2_process015=&msg2_process012=&msg2_process028=&msg2_process016="));

		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
		
		//7
		 var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg2Op&opDataUnclear=true&opAction=10001";
		url+="&numflag=7";
		url+="&msg2_entcode="+entcode;
	url+="&msg2_tradecode="+tradecode;
	url+="&msg2_ordernum="+ordernum;
	url+="&msg2_ordercode="+ordercode;
	url+="&msg2_tradetype="+tradetype;
	url+="&msg2_flowtype="+flowtype;
		url+= "&newtime=" + new Date();
		objHTTP.Open('POST',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		objHTTP.Send(encode("msg2_process028="+form1.msg2_process028.value+"&&msg2_process013=&msg2_process014=&msg2_process015=&msg2_process027=&msg2_process012=&msg2_process016="));

		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
	}	
	if (firstflag=="0")
	{
		document.all.msgprocess012.value=ts;
		var length12 = document.all.msgprocess012.value.length;
		var t_mid12 = 0;
		
		if(length12%2==1){
			t_mid12 = (length12+1)/2;
		} else {
			t_mid12 = length12/2;
		}
		var msgprocess012_1 = document.all.msgprocess012.value.substring(0,t_mid12);
		var msgprocess012_2 = document.all.msgprocess012.value.substring(t_mid12);
		//1
		 var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg2Op&opDataUnclear=true&opAction=10001";
		url+="&numflag=1";
		url+="&msg2_entcode="+entcode;
	url+="&msg2_tradecode="+tradecode;
	url+="&msg2_ordernum="+ordernum;
	url+="&msg2_ordercode="+ordercode;
	url+="&msg2_tradetype="+tradetype;
	url+="&msg2_flowtype="+flowtype;
		url+= "&newtime=" + new Date();
		objHTTP.Open('POST',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		objHTTP.Send(encode("msgprocess012="+form1.msgprocess012.value));
		
		
		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
		
	}


	alert(<cmis:muiitem item="C000050" mark="\""/>);//"调查分析意见保存成功"

	}
}
</script>

<input type=hidden name="msg2_process013" value="">
<input type=hidden name="msg2_process014" value="">
<input type=hidden name="msg2_process015" value="">
<input type=hidden name="msg2_process016" value="">
<input type=hidden name="msg2_process012" value="">
<input type=hidden name="msg2_process027" value="">
<input type=hidden name="msg2_process028" value="">
<input type=hidden name="msgprocess012" value="">
<input type=hidden name="msg2testaaaa" value="<%=firstflag%>">
<table width="100%" border="1" cellspacing="" cellpadding="4">
<%if(firstflag.equals("1")){%>
	<tr>	
	<td class="td1" width = "350"><cmis:muiitem item="C000048"/><!--调查分析意见输入(请点击意见按钮,在对话框中输入)--></td><td><a href="javascript:showfxadvice('<%=flow_entcode%>','<%=flow_tradecode%>','<%=flow_ordernum%>','<%=flow_ordercode%>','<%=flow_tradetype%>','<%=flow_flowtype%>','<%=firstflag%>');"> <cmis:muiitem item="C000049"/> </a></td>
	</tr>
<%}%>
<%if(firstflag.equals("0")){%>
	<tr>
	<td class="td1" width = "350"><cmis:muiitem item="C000043"/><!--意见说明(请点击意见按钮,在对话框中输入)--></td><td><a href="javascript:showfxadvice('<%=flow_entcode%>','<%=flow_tradecode%>','<%=flow_ordernum%>','<%=flow_ordercode%>','<%=flow_tradetype%>','<%=flow_flowtype%>','<%=firstflag%>');"> <cmis:muiitem item="C000049"/> </a></td>
	</tr>
<%}%>

</table>
</cmis:muidef>