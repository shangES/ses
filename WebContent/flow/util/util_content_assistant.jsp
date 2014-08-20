<!--发送辅助审核，查看辅助审核-->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ page session="false"%>
<%@ page import="java.util.Enumeration" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>

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
CMisSessionMgr sm = new CMisSessionMgr(request);
//--------------------------------------------------------\
String LangCode = (String)sm.getSessionData("LangCode");
if(LangCode == null || LangCode.equals("")) LangCode = "zh_CN";
icbc.cmis.tags.muiStr muistr = new icbc.cmis.tags.muiStr("icbc.cmis.flow.BA.flow_BA",LangCode);
//--------------------------------------------------------/

String assistant_entcode = request.getParameter("entcode");
String assistant_tradecode = request.getParameter("tradecode");
String assistant_tradetype = request.getParameter("tradetype");
String assistant_flowtype = request.getParameter("flowtype");
String assistant_ordernum = request.getParameter("ordernum");
String assistant_ordercode = request.getParameter("ordercode");
String assistant_employeecode = request.getParameter("employeecode");
String assistant_empareacode = request.getParameter("empareacode");
%>
<script src="<%=baseWebPath%>/flow/<cmis:muipub item="JSPATH"/>/checkSyntax.js"></script>
<script language="JavaScript">
var choosedasslist="";

//发送辅助审核
function sendassistant(){
	if(choosedasslist==""){
		alert(<cmis:muiitem item="C000078" mark="\""/>);//"请选择辅助审核人"
		return;
	}
	if(form1.assistant_process020.value==""){
		alert(<cmis:muiitem item="C000077" mark="\""/>);//"请输入辅助审核说明"
		return;
	}
	
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";

	var length20 = form1.assistant_process020.value.length;
	var t_mid = 0;
	
	if(length20%2==1){
		t_mid = (length20+1)/2;
	} else {
		t_mid = length20/2;
	}
	var process020_1 = form1.assistant_process020.value.substring(0,t_mid);
	var process020_2 = form1.assistant_process020.value.substring(t_mid);

	//保存辅助
	var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_assistantOp&opDataUnclear=true&opAction=10002";
	url+="&assistant_entcode=<%=assistant_entcode%>";
	url+="&assistant_tradecode=<%=assistant_tradecode%>";
	url+="&assistant_tradetype=<%=assistant_tradetype%>";
	url+="&assistant_flowtype=<%=assistant_flowtype%>";
	url+="&assistant_ordernum=<%=assistant_ordernum%>";
	url+="&assistant_ordercode=<%=assistant_ordercode%>";
	url+="&assistant_empareacode=<%=assistant_empareacode%>";
	url+="&assistant_employeecode=<%=assistant_employeecode%>";
	//url+="&assistant_assinfo="+form1.assistant_process020.value;
	url+= "&newtime=" + new Date();
	objHTTP.Open('POST',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	objHTTP.Send(encode("assistant_assinfo="+form1.assistant_process020.value+"&assistant_asslist="+choosedasslist));
	
	if(!parser.loadXML(objHTTP.responseText)){
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return;
	}
	error = parser.getElementsByTagName("error");
	if(error.length > 0) {
		alert(error.item(0).text);
		return;
	}

	ass_init();
	cleanlist();
	alert(<cmis:muiitem item="C000079" mark="\""/>);//"发送辅助审批成功"
}

//页面初始化，置列表，置辅助审核说明
function ass_init(){
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	
	document.all.hideass.innerHTML = "<%=muistr.getStr("C000070")%><a href='javascript:hideass()'><%=muistr.getStr("C000072")%></a>";
	document.all.assframe.style.display="none";
	
	var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_assistantOp&opDataUnclear=true&opAction=20001";
	url+="&assistant_entcode=<%=assistant_entcode%>";
	url+="&assistant_tradecode=<%=assistant_tradecode%>";	
	url+="&assistant_ordernum=<%=assistant_ordernum%>";
	url+= "&newtime=" + new Date();
	objHTTP.Open('GET',encodeURL(url),false);
	objHTTP.Send();
	if(!parser.loadXML(objHTTP.responseText)){
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return;
	}
	
	info = parser.getElementsByTagName("info");
	var showhtml = "";
	if(info.length > 0){
		var nodes = parser.documentElement.childNodes;
		var ts=new Array(6);
		if(nodes.length > 0){
			for(i = 0; i < nodes.length ; i ++ ){
				var node = nodes.item(i);
				if(node.nodeName == "myinfo"){
					form1.assistant_process020.value = node.text;
				} else {
					ts[0]=node.getAttribute("rnum");
					ts[1]=node.getAttribute("advice005");
					ts[2]=node.getAttribute("advice005_name");
					ts[3]=node.getAttribute("advice006");
					if(ts[3]=="1") ts[3]="<%=muistr.getStr("C000074")%>";
					if(ts[3]=="2") ts[3]="<%=muistr.getStr("C000075")%>";
					if(ts[3]=="0") ts[3]="<%=muistr.getStr("C000015")%>";
					if(ts[3]=="") ts[3]="&nbsp";
					ts[4]=node.getAttribute("advice007");
					if(ts[4]=="") ts[4]= "&nbsp";
					ts[5]=node.getAttribute("advice008");
					if(ts[5]=="") ts[5]= "&nbsp";
					showhtml += '<tr>'
							  + '<td>' + ts[0] + '</td>'
							  + '<td>' + ts[1] + '</td>'
							  + '<td>' + ts[2] + '</td>'
							  + '<td>' + ts[3] + '</td>'
  							  + '<td>' + ts[5] + '</td>'
							  + '<td>' + ts[4] + '</td>'
							  + '</tr>';
				}
			}
		}
	
	}
	if(showhtml==""){
		document.all.asslist.innerHTML = '<table width="100%" border="1" cellspacing="0" cellpadding="1">'
										+'<tr><td class="td1"><%=muistr.getStr("C000001")%></td><td class="td1"><%=muistr.getStr("C000080")%></td><td class="td1"><%=muistr.getStr("C000081")%></td><td class="td1"><%=muistr.getStr("C000031")%></td><td class="td1" width="40%"><%=muistr.getStr("C000032")%></td><td class="td1"><%=muistr.getStr("C000035")%></td></tr>'
										+ '<tr><td colspan=6><%=muistr.getStr("C000073")%></td></tr>'
										+ '</table>';		
	} else{
		document.all.asslist.innerHTML = '<table width="100%" border="1" cellspacing="0" cellpadding="1">'
										+'<tr><td class="td1"><%=muistr.getStr("C000001")%></td><td class="td1"><%=muistr.getStr("C000080")%></td><td class="td1"><%=muistr.getStr("C000081")%></td><td class="td1"><%=muistr.getStr("C000031")%></td><td class="td1" width="40%"><%=muistr.getStr("C000032")%></td><td class="td1"><%=muistr.getStr("C000035")%></td></tr>'
										+ showhtml
										+ '</table>';
	}
	
}


function cleanlist() {
	tselect = document.all.choosedassemp;
	tselect.selectedIndex = 0;
	for(j = tselect.length - 1 ; j>= 0 ; j--) {
		tselect.remove(j);
	}
	tselect.length = 0;
	choosedasslist = "";
}
function chooseassemp(){ 
	var ts = window.showModalDialog("<%=baseWebPath%>/util/util_ChooseMulEmp.jsp?queryType=QueryNormalEnp&ask=false&time=" + (new Date) + "&area_code=<%=assistant_empareacode%>&sub_bank=0&major_code=210&class_code=&noneed_code=",window,"dialogWidth:610px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no");
	if(ts != null){
		cleanlist();
	
		tselect = document.all.choosedassemp;
		tselect.selectedIndex = 0;
		for(j = tselect.length - 1 ; j>= 0 ; j--) {
			tselect.remove(j);
		}
		tselect.length = 0;
		for(var j=0; j<ts.length;j++){
			var op =new Option(ts[j][0]+' '+ts[j][1],ts[j][0]);
			document.all.choosedassemp.add(op,0);
			choosedasslist = choosedasslist + ts[j][0] + '|';
		}
	}
}
 
function hideass(){
	if(document.all.assframe.style.display==""){
		document.all.assframe.style.display="none";
		document.all.hideass.innerHTML = "<%=muistr.getStr("C000070")%><a href='javascript:hideass()'><%=muistr.getStr("C000072")%></a>";
	} else {
		document.all.assframe.style.display="";
		document.all.hideass.innerHTML = "<%=muistr.getStr("C000070")%><a href='javascript:hideass()'><%=muistr.getStr("C000071")%></a>";
	}
}



</script>

<table width="100%" border="1" cellspacing="" cellpadding="1">
<tr><th><div id="hideass"></div></th></tr>
</table>

<div id="assframe">
<table width="100%" border="1" cellspacing="" cellpadding="0">
<tr>
<td colspan=2>
<div id="asslist"></div>
</td>
</tr>

<tr>
<td class="td1">
<cmis:muiitem item="C000067"/><!-- 辅助审核内容： -->
</td>
<td class="td1">
<cmis:muiitem item="C000068"/><!-- 添加辅助审核： --><a href="javascript:chooseassemp();"><cmis:muiitem item="C000069"/></a><!-- 选择辅助审核人 -->
</td>
</tr>

<tr>
<td rowspan=2>
<TEXTAREA NAME="assistant_process020" ROWS="9" COLS="88" onblur="javascript:checkAreaLength(this,1000);javascript:checkSyntax(this);" ></TEXTAREA>
</td>
<td>
<SELECT name="choosedassemp" style="width:270px" size=6>
</SELECT>
</td>
</tr>

<tr>
<td class="td1" align="right">
<img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/clear.gif" border="0" style="cursor:hand" onClick="javascript:cleanlist();"><img 
	 src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/send.gif" border="0" style="cursor:hand" onClick="javascript:sendassistant();">
</td>
</tr>

</table>
</div>
</cmis:muidef>