<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page session="false"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ include file="../../../util/header.jsp"%>

<cmis:sessionCheck>

<%
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");
ArrayList alist = (ArrayList)context.getValueAt("alist");

String ordercode = (String)context.getValueAt("ordercode");
String busitype = (String)context.getValueAt("busitype");
String runproc = (String)context.getValueAt("runproc");
String spflag = (String)context.getValueAt("spflag");

int total_list = Integer.parseInt((String)context.getValueAt("max_num"));
String total_page = (total_list%15==0 ? total_list/15 : (total_list-total_list%15)/15+1)+"";
String now_page_us = (String)context.getValueAt("now_page_us");

if(Integer.parseInt(now_page_us)>Integer.parseInt(total_page)&&Integer.parseInt(total_page)!=0){
	now_page_us = total_page;
}

int pegelist = 15;
int listbegin = Integer.parseInt(now_page_us)*pegelist - pegelist + 1;
int listend = listbegin + pegelist - 1;
if(listend>total_list)
	listend = total_list;

String page_info=" 【第 "+ now_page_us +" 页/共 "+ total_page +" 页( "+ total_list +" 条)】 ";

%>

<html>
<head>
<script src="<%=baseWebPath%>/jslib/data_check.js"></script>
<script src="<%=baseWebPath%>/jslib/liball.js"></script>
<script src="<%=baseWebPath%>/jslib/tools.js"></script>
<script language="JavaScript" src="<%=baseWebPath%>/jslib/highlight.js"></script>
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




<title>待本行处理审批业务列表(委贷)</title>
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
<cmis:tabpagebar title="待本人处理" url="javascript:tome();" onclick="" selected="false" />
<cmis:tabpagebar title="待本环节处理" url="" onclick="" selected="true" />
<cmis:tabpagecontent info="<%=page_info%>" align="center" valign="top">

<div id="showframe">

<table width="100%" border="1" cellspacing="0" cellpadding="1">
	<tr>
		<td class="td1" align="center">序号</td>
		<td class="td1" align="center">客户代码</td>
		<td class="td1" align="center">客户全称</td>
		<td class="td1" align="center">申请号</td>
		<td class="td1" align="center">流程种类</td>
		<td class="td1" align="center">申请种类</td>
		<td class="td1" align="center">处理状态</td>
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
		if(process009.equals("0")) process009_name = "待处理";
		if(process009.equals("1")) process009_name = "正在处理";
		if(process009.equals("2")) process009_name = "已发送";
		if(process009.equals("-1")) process009_name = "申请";
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
				src="<%=baseWebPath%>/images/first_page.gif" width="48" height="24" border="0"></a><a 
				href="javascript:qotherpage('2');"><img 
				src="<%=baseWebPath%>/images/pre_page.gif" width="48" height="24" border="0"></a><%}if(!now_page_us.equals(total_page)){%><a 
				href="javascript:qotherpage('3');"><img 
				src="<%=baseWebPath%>/images/next_page.gif" width="48" height="24" border="0"></a><a 
				href="javascript:qotherpage('4');"><img 
				src="<%=baseWebPath%>/images/last_page.gif" width="48" height="24" border="0"></a>
			<%}%>
		<%}%>
		<a href="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.servlets.CmisMain"><IMG 
 		 border="0" src="<%=baseWebPath%>/images/home.gif"  class="hand" width="48" height="24"></a>
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

<jsp:include page="/util/footer.jsp" flush="true" />
</body>
</html>

</cmis:sessionCheck>

