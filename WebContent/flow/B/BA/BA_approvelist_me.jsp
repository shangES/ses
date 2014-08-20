<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page session="false"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="cmisTags" prefix="cmis" %>

<cmis:muidef def="icbc.cmis.flow.BA.flow_BA">
<%
//--------------------------------------------------------\
String LangCode = "zh_CN";
if(LangCode == null || LangCode.equals("")) LangCode = "zh_CN";
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
	
String now_page_me = (String)context.getValueAt("now_page_me");

if(Integer.parseInt(now_page_me)>Integer.parseInt(total_page)&&Integer.parseInt(total_page)!=0){
	now_page_me = total_page;
}

int pegelist = 15;
int listbegin = Integer.parseInt(now_page_me)*pegelist - pegelist + 1;
int listend = listbegin + pegelist - 1;
if(listend>total_list)
	listend = total_list;

String page_info = now_page_me +"|"+ total_page +"|"+ total_list;

%>

<html>
<head>
<% 
  		  String baseWebPath = "/hrmweb";
%>
<script language="javascript" src="<%=baseWebPath%>/jslib/webbase.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/data_check.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/liball.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/tools.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/highlight.js"></script>

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

//切换至待本行处理页面
function tous(){
	form1.opAction.value = "getourlist";
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




<title>待本人处理审批业务列表\flow\B\BA\BA_approvelist_me.jsp</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=baseWebPath%>/css/main.css" type="text/css">
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/skins/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/skins/css/jquery-ui-1.8.15.custom.css"/>

<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/plugin/tree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/plugin/tree/zTreeIcons.css"/>
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/plugin/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/plugin/grid/skin/default/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/plugin/grid/skin/green/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/plugin/grid/skin/mac/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/plugin/grid/skin/china/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/plugin/grid/skin/vista/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="<%=baseWebPath%>/plugin/grid/calendar/calendar-blue.css"  />


<script type="text/javascript" src="<%=baseWebPath%>/skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/skins/js/public.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/skins/js/jquery.layout.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/plugin/form/lib/jquery.metadata.js"></script>

<script type="text/javascript" src="<%=baseWebPath%>/plugin/grid/calendar/calendar.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/plugin/grid/calendar/calendar-en.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/plugin/grid/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/plugin/grid/gt_grid_all.js"></script>
<script type="text/javascript" src="<%=baseWebPath%>/plugin/grid/gt_msg_cn.js"></script>

</head>

<body>
<div class="sort">
	
	<div class="sort-cont sort-table">
		<div id="mytab">
			<ul>
				<li><a href="#tab0">用户树</a></li>
				<li><a href="#tab0">用户列表</a></li>
			</ul>
			<div id="tab0">
				11111111111111111111
			</div>
		</div>
	</div>
</div>
</body>
</html>
</cmis:muidef>


