<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page session="false"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ include file="../../../util/header.jsp"%>


<cmis:sessionCheck>
<cmis:muidef def="icbc.cmis.flow.FLOW_B_BA">

<%
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");
ArrayList alist = (ArrayList)context.getValueAt("alist");
//类实例化
icbc.cmis.tags.PropertyResourceReader propertyResourceReader=new icbc.cmis.tags.PropertyResourceReader(pageContext,"icbc.cmis.flow.FLOW_B_BA");

int total_list = Integer.parseInt((String)context.getValueAt("max_num"));
String total_page = (total_list%15==0 ? 1 : (total_list-total_list%15)/15+1)+"";
String now_page_me = (String)context.getValueAt("now_page_me");

int pegelist = 15;
int listbegin = Integer.parseInt(now_page_me)*pegelist - pegelist + 1;
int listend = listbegin + pegelist - 1;
if(listend>total_list)
	listend = total_list;

String page_info=propertyResourceReader.getPrivateStr("C000003",now_page_me+"|"+total_page+"|"+total_list );
 //" 【第 "+ now_page_me +" 页/共 "+ total_page +" 页( "+ total_list +" 条)】 ";

%>

<html>
<head>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/data_check.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/liball.js"></script>
<script language="JavaScript" src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/highlight.js"></script>
<script Language="JavaScript">

//翻页
function qotherpage(todo){
	if(todo=="1")
		form1.now_page_me.value="1";
	else if(todo=="2")
		form1.now_page_me.value="<%=Integer.parseInt(now_page_me)-1%>";
	else if(todo=="3")
		form1.now_page_me.value="<%=Integer.parseInt(now_page_me)+1%>";
	else if(todo=="4")
		form1.now_page_me.value="<%=total_page%>";
	form1.opAction.value="qotherpageme";
	document.all.showframe.style.display="none";
	form1.submit();
}


function doass(str1,str2,str3,str4,str5,str6,str7,str8){
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


<title><cmis:muiitem item="C000001" exp="待本人辅助"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" href="<%=baseWebPath%>/<cmis:muipub item="CSSPATH"/>/main.css" type="text/css">
</head>

<body>
<form name="form1" method="post" action="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet">
<input type="hidden" name="operationName" value="icbc.cmis.flow.BA.BA_NoassistantAuditingOp">
<input type="hidden" name="opAction" value="">
<input type="hidden" name="opDataUnclear" value="true">
<input type="hidden" name="now_page_me" value="<%=now_page_me%>">

<cmis:framework align="center">
<cmis:tabpage width="750">
<cmis:tabpagebar titleid="C000002" url="" onclick="" selected="true" /><!--辅助审批-->
<cmis:tabpagecontent info="<%=page_info%>" align="center" valign="top">

<div id="showframe">

<table width="100%" border="1" cellspacing="0" cellpadding="1">
	<tr>
		<td class="td1" align="center"><cmis:muipub item="P000019" exp="序号"/></td>
		<td class="td1" align="center"><cmis:muipub item="P000004" exp="客户代码"/></td>
		<td class="td1" align="center"><cmis:muipub item="P000074" exp="客户全称"/></td>
		<td class="td1" align="center"><cmis:muipub item="P000027" exp="申请号"/></td>
		<td class="td1" align="center"><cmis:muipub item="P000058" exp="流程种类"/></td>
		<td class="td1" align="center"><cmis:muipub item="P000170" exp="申请种类"/></td>
		<td class="td1" align="center"><cmis:muipub item="P000067" exp="处理状态"/></td>
	</tr>
	<% for(int i=listbegin; i<=listend; i++) {
		HashMap hmap = (HashMap)alist.get(i-1);
		String rnum = (String)hmap.get("rnum");//序号
		String advice001 = (String)hmap.get("advice001");//客户代码
		String advice001_name = (String)hmap.get("advice001_name");//客户全称
		String advice002 = (String)hmap.get("advice002");//申请号
		String advice003 = (String)hmap.get("advice003");//环节序号
		String process003 = (String)hmap.get("process003");//流程代码
		String process003_name = (String)hmap.get("process003_name");//流程名称
		String process004 = (String)hmap.get("process004");//申请代码
		String process004_name = (String)hmap.get("process004_name");//申请名称
		String advice006 = (String)hmap.get("advice006");//状态
		String advice006_name = "";
		if(advice006.equals("0")) {
			advice006_name = propertyResourceReader.getPrivateStr("C000004");//"未处理";
		} else {
			advice006_name = propertyResourceReader.getPrivateStr("C000005");//"已处理";
		}
		%>
	<tr <%if(i%2==0){%>bgcolor="ffffff"<%}else{%>bgcolor="f1f1f1"<%}%> onMouseOver=HighLightMouseOver() 
		onclick = "javascript:doass('<%=advice001%>','<%=advice001_name%>','<%=advice002%>','<%=process004%>','<%=process004_name%>','<%=process003%>','<%=process003_name%>','<%=advice003%>');">
		<td><%=rnum%></td>
		<td><%=advice001%></td>
		<td><%=advice001_name%></td>
		<td><%=advice002%></td>
		<td><%=process003_name%></td>
		<td><%=process004_name%></td>
		<td><%=advice006_name%></td>
	</tr>
	<% } %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td class=td1 align="right"  valign="bottom" height=35>
		<%if(!total_page.equals("0")) {%>
			<%if(!now_page_me.equals("1")){%><a 
				href="javascript:qotherpage('1');"><img 
				src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/first_page.gif" height="24" border="0"></a><a 
				href="javascript:qotherpage('2');"><img 
				src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/pre_page.gif" height="24" border="0"></a><%}if(!now_page_me.equals(total_page)){%><a 
				href="javascript:qotherpage('3');"><img 
				src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/next_page.gif" height="24" border="0"></a><a 
				href="javascript:qotherpage('4');"><img 
				src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/last_page.gif" height="24" border="0"></a>
			<%}%>
		<%}%>
		<a href="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.servlets.CmisMain"><IMG 
 		 border="0" src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/home.gif"  class="hand"  height="24"></a>
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
<!--input type="hidden" name="opDataUnclear" value="true"-->
<input type="hidden" name="approve_entcode" value="">
<input type="hidden" name="approve_entname" value="">
<input type="hidden" name="approve_tradecode" value="">
<input type="hidden" name="approve_tradetype" value="">
<input type="hidden" name="approve_tradetypename" value="">
<input type="hidden" name="approve_flowtype" value="">
<input type="hidden" name="approve_flowtypename" value="">
<input type="hidden" name="approve_ordernum" value="">
<input type="hidden" name="approve_ordercode" value="f">
<input type="hidden" name="approve_busitype" value="0">
<input type="hidden" name="approve_spflag" value="1">
<input type="hidden" name="approve_returnurl" value="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BA.BA_NoassistantAuditingOp&opAction=getlist">
</form>


<jsp:include page="/util/footer.jsp" flush="true" />
</body>
</html>
	</cmis:muidef>

</cmis:sessionCheck>
