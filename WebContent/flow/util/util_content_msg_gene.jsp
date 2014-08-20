<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ page session="false"%>
<%@ page import="java.util.Enumeration" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>

<%@ page import="icbc.cmis.flow.util.*"%>
<%@ include file="../../util/header.jsp" %>

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
//empareaname	－当前地区名称
//busitype		－业务性质，0，自营，1，委托

//~~~~使用自己的前缀，与其他片断无重复

String msg_g_entcode = request.getParameter("entcode");
String msg_g_tradecode = request.getParameter("tradecode");
String msg_g_ordernum = request.getParameter("ordernum");
String msg_g_ordercode = request.getParameter("ordercode");
String msg_g_tradetype = request.getParameter("tradetype");
String msg_g_flowtype = request.getParameter("flowtype");
String msg_g_employeecode = request.getParameter("employeecode");
String msg_g_empareacode = request.getParameter("empareacode");
%>

<script src="<%=baseWebPath%>/flow/jslib/checkSyntax.js"></script>
<script language="JavaScript">
//只对汉字以及特殊字符进行unicode编码
function encodex(s) {
	if(s == null ) return s;
	if(s.length == 0) return s;
	var ret = "";
	for(i=0;i<s.length;i++) {
		var c = s.substr(i,1);
		var ts = escape(c);
		var tstemp = ts.substr(1,1);
		if(tstemp<='F' && tstemp >='A'){
			ret = ret + "@@00" + ts.substr(1,2);
		} else if(ts.substr(0,2) == "%u") {
			ret = ret + ts.replace("%u","@@");
		} else {
			ret = ret + c;
		}
	}
	return ret;
}

function showadvice(){
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";

	
	var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg_geneOp&opDataUnclear=true&opAction=10001"
			+"&msg_g_entcode=<%=msg_g_entcode%>"
			+"&msg_g_tradecode=<%=msg_g_tradecode%>"
			+"&msg_g_ordernum=<%=msg_g_ordernum%>"
			+"&msg_g_employeecode=<%=msg_g_employeecode%>"
			+"&msg_g_flag=1"
			+"&time="+(new Date);
	var ts = window.showModalDialog(url,window,"dialogWidth:610px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no");
	
	if(ts!=null){
		var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
		var parser=new ActiveXObject("microsoft.xmldom");
		parser.async="false";
		
		var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet";
		objHTTP.Open('POST',encodeURL(url),false);
		objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		var parameter = "operationName=icbc.cmis.flow.util.util_content_msg_geneOp&opDataUnclear=true&opAction=20001"
					  +"&msg_g_entcode=<%=msg_g_entcode%>"
					  +"&msg_g_tradecode=<%=msg_g_tradecode%>"
					  +"&msg_g_ordernum=<%=msg_g_ordernum%>"
					  +"&msg_g_msginput="+ts
					  +"&msg_g_ordercode=<%=msg_g_ordercode%>"
					  +"&msg_g_tradetype=<%=msg_g_tradetype%>"
					  +"&msg_g_flowtype=<%=msg_g_flowtype%>"
					  +"&msg_g_employeecode=<%=msg_g_employeecode%>"
					  +"&msg_g_empareacode=<%=msg_g_empareacode%>"
					  +"&time=" + (new Date);
		objHTTP.Send(encodex(parameter));
		
		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
		alert("保存意见说明成功");	
	}
	
}
</script>




<table width="100%" border="1" cellspacing="" cellpadding="1">
<tr>
	<th colspan=2>本人意见</th>
</tr>
<tr>
	<td class="td1" width = "350">意见说明(请点击意见按钮,在对话框中输入)</td><td><a href="javascript:showadvice();"> 输入</a></td>
</tr>
</table>

