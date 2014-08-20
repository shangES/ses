<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page session="false"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ include file="../../../util/header.jsp"%>

<cmis:muidef def="icbc.cmis.flow.BA.flow_BA">
<%
//--------------------------------------------------------\
String LangCode = "zh_CN";
icbc.cmis.tags.muiStr muistr = new icbc.cmis.tags.muiStr("icbc.cmis.flow.BA.flow_BA",LangCode);
//--------------------------------------------------------/

KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");
ArrayList alist = (ArrayList)context.getValueAt("alist");

String ordercode = (String)context.getValueAt("ordercode");
String busitype = (String)context.getValueAt("busitype");
String runproc = (String)context.getValueAt("runproc");
String spflag = (String)context.getValueAt("spflag");

int total_list = Integer.parseInt((String)context.getValueAt("max_num"));
String total_page = "";
if (total_list==0)
	total_page="1";
else
	total_page = (total_list%15==0 ? total_list/15 : (total_list-total_list%15)/15+1)+"";
	
String now_page_us = (String)context.getValueAt("now_page_us");

if(Integer.parseInt(now_page_us)>Integer.parseInt(total_page)&&Integer.parseInt(total_page)!=0){
	now_page_us = total_page;
}

int pegelist = 15;
int listbegin = Integer.parseInt(now_page_us)*pegelist - pegelist + 1;
int listend = listbegin + pegelist - 1;
if(listend>total_list)
	listend = total_list;

String page_info = now_page_us +"|"+ total_page +"|"+ total_list;

%>

<html>
<head>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/data_check.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/liball.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/tools.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/highlight.js"></script>
<script Language="JavaScript">
//翻页
function qotherpage(todo){
	if(todo=="1")
		form1.now_page_us.value="1";
	else if(todo=="2")
		form1.now_page_us.value="<%=Integer.parseInt(now_page_us)-1%>";
	else if(todo=="3")
		form1.now_page_us.value="<%=Integer.parseInt(now_page_us)+1%>";
	else if(todo=="4")
		form1.now_page_us.value="<%=total_page%>";
	form1.opAction.value="qotherpageus";
	document.all.showframe.style.display="none";
	form1.submit();
}

function tome(){
	form1.opAction.value = "getmylist";
	document.all.showframe.style.display="none";
	form1.submit();
}

//去作审批喽～
function doapprove(str1,str2,str3,str4,str5,str6,str7,str8){
<%if(ordercode.equals("1")&&busitype.equals("0")) {%>
	//添加校验
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	
	var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BA.BA_approvelistOp&opDataUnclear=true&opAction=checkapply";
	url += "&chk_entcode=" + str1;
	url += "&chk_tradecode=" + str3;
	url += "&chk_tradetype=" + str4;
	url += "&chk_ordercode=<%=ordercode%>";
	url+= "&newtime=" + new Date();
	objHTTP.Open('GET',encodeURL(url),false);
	objHTTP.Send();
	if(!parser.loadXML(objHTTP.responseText)){
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return;
	}
	error = parser.getElementsByTagName("error");//错误，不能进入
	if(error.length > 0) {
		alert(error.item(0).text);
		return;
	}
	hint = parser.getElementsByTagName("hint");//只给提示
	if(hint.length > 0) {
		if(confirm(hint.item(0).text + "\n"+ <cmis:muiitem item="C000014" mark="\""/> + "?")){
		} else {
			return;
		}	
		//alert(hint.item(0).text);
	}
<%}%>

	form2.approve_entcode.value = str1;
	form2.approve_entname.value = str2;
	form2.approve_tradecode.value = str3;
	form2.approve_tradetype.value = str4;
	form2.approve_tradetypename.value = str5;
	form2.approve_flowtype.value = str6;
	form2.approve_flowtypename.value = str7;
	form2.approve_ordernum.value = str8;
	document.all.showframe.style.display="none";
	form2.submit();
}

</script>




<title>待本行处理审批业务列表\flow\B\BA\BA_approvelist_us.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" href="<%=baseWebPath%>/css/main.css" type="text/css">
</head>

<body>
<form name="form1" method="post" action="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet">
<input type="hidden" name="operationName" value="icbc.cmis.flow.BA.BA_approvelistOp">
<input type="hidden" name="opAction" value="">
<input type="hidden" name="opDataUnclear" value="true">

<input type="hidden" name="now_page_us" value="<%=now_page_us%>">

<cmis:framework align="center">
<cmis:tabpage width="750">
<cmis:tabpagebar titleid="C000008" url="javascript:tome();" onclick="" selected="false" /><!-- 待本人处理 -->
<cmis:tabpagebar titleid="C000009" url="" onclick="" selected="true" /><!-- 待本环节处理 -->
<cmis:tabpagecontent infoid="PAGEDEF" align="center" valign="top" para="<%=page_info%>">

<div id="showframe">

<table width="100%" border="1" cellspacing="0" cellpadding="1">
	<tr>
		<td class="td1" align="center"><cmis:muiitem item="C000001"/><!--序号--></td>
		<td class="td1" align="center"><cmis:muiitem item="C000002"/><!--客户代码--></td>
		<td class="td1" align="center"><cmis:muiitem item="C000003"/><!--客户全称--></td>
		<td class="td1" align="center"><cmis:muiitem item="C000004"/><!--申请号--></td>
		<td class="td1" align="center"><cmis:muiitem item="C000005"/><!--流程种类--></td>
		<td class="td1" align="center"><cmis:muiitem item="C000006"/><!--申请种类--></td>
		<td class="td1" align="center"><cmis:muiitem item="C000007"/><!--处理状态--></td>
	</tr>
	<% for(int i=listbegin; i<=listend; i++) {
		HashMap hmap = (HashMap)alist.get(i-1);
		String rnum = (String)hmap.get("rnum");//序号
		String process001 = (String)hmap.get("process001");//客户代码
		String process001_name = (String)hmap.get("process001_name");//客户全称
		String process002 = (String)hmap.get("process002");//申请号
		String process003 = (String)hmap.get("process003");//流程代码
		String process003_name = (String)hmap.get("process003_name");//流程名称
		String process004 = (String)hmap.get("process004");//申请代码
		String process004_name = (String)hmap.get("process004_name");//申请名称
		String process005 = (String)hmap.get("process005");//环节序号
		String process009 = (String)hmap.get("process009");//状态
		String process009_name = "";
		if(process009.equals("0")) process009_name = muistr.getStr("C000010");//"待处理";
		if(process009.equals("1")) process009_name = muistr.getStr("C000011");//"正在处理";
		if(process009.equals("2")) process009_name = muistr.getStr("C000012");//"已发送";
		if(process009.equals("-1")) process009_name = muistr.getStr("C000013");//"申请";
		%>
	<tr <%if(i%2==0){%>bgcolor="ffffff"<%}else{%>bgcolor="f1f1f1"<%}%> onMouseOver=HighLightMouseOver() 
		onclick = "javascript:doapprove('<%=process001%>','<%=process001_name%>','<%=process002%>','<%=process004%>','<%=process004_name%>','<%=process003%>','<%=process003_name%>','<%=process005%>');">
		<td><%=rnum%></td>
		<td><%=process001%></td>
		<td><%=process001_name%></td>
		<td><%=process002%></td>
		<td><%=process003_name%></td>
		<td><%=process004_name%></td>
		<td><%=process009_name%></td>
	</tr>
	<% } %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td class=td1 align="right"  valign="bottom" height=35>
		<%if(!total_page.equals("0")) {%>
			<%if(!now_page_us.equals("1")){%><a 
				href="javascript:qotherpage('1');"><img 
				src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/first_page.gif" height="24" border="0"></a><a 
				href="javascript:qotherpage('2');"><img 
				src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/pre_page.gif" height="24" border="0"></a><%}if(!now_page_us.equals(total_page)){%><a 
				href="javascript:qotherpage('3');"><img 
				src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/next_page.gif" height="24" border="0"></a><a 
				href="javascript:qotherpage('4');"><img 
				src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/last_page.gif" height="24" border="0"></a>
			<%}%>
		<%}%>
		<a href="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.servlets.CmisMain"><IMG 
 		 border="0" src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/home.gif"  class="hand" height="24"></a>
		</td>
	</tr>
</table>

</div>

</cmis:tabpagecontent>
</cmis:tabpage>
</cmis:framework>
</form>

<form name="form2" method="post" action="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet">
<input type="hidden" name="operationName" value="icbc.cmis.flow.BA.BA_approvedoOp">
<input type="hidden" name="opAction" value="showinfo">
<input type="hidden" name="opDataUnclear" value="true">
<input type="hidden" name="approve_entcode" value="">
<input type="hidden" name="approve_entname" value="">
<input type="hidden" name="approve_tradecode" value="">
<input type="hidden" name="approve_tradetype" value="">
<input type="hidden" name="approve_tradetypename" value="">
<input type="hidden" name="approve_flowtype" value="">
<input type="hidden" name="approve_flowtypename" value="">
<input type="hidden" name="approve_ordernum" value="">
<input type="hidden" name="approve_ordercode" value="<%=ordercode%>">
<input type="hidden" name="approve_busitype" value="<%=busitype%>">
<input type="hidden" name="approve_spflag" value="<%=spflag%>">
<input type="hidden" name="approve_returnurl" value="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BA.BA_approvelistOp&opDataUnclear=false&opAction=getourlist&ordercode=<%=ordercode%>&busitype=<%=busitype%>&runproc=<%=runproc%>&now_page_us=<%=now_page_us%>&spflag=<%=spflag%>">
</form>

</body>
</html>
</cmis:muidef>

