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

String msg4_entcode = request.getParameter("entcode");
String msg4_tradecode = request.getParameter("tradecode");
String msg4_ordernum = request.getParameter("ordernum");
String msg4_ordercode = request.getParameter("ordercode");
String msg4_tradetype = request.getParameter("tradetype");
String msg4_flowtype = request.getParameter("flowtype");
String msg4_employeecode = request.getParameter("employeecode");
String msg4_empareacode = request.getParameter("empareacode");

%>

<script src="<%=baseWebPath%>/flow/jslib/checkappstat.js"></script>
<script language="JavaScript">

var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
var parser=new ActiveXObject("microsoft.xmldom");
parser.async="false";


function showadvice(){
	var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg4&opDataUnclear=true&opAction=10001"
			+"&msg4_entcode=<%=msg4_entcode%>"
			+"&msg4_tradecode=<%=msg4_tradecode%>"
			+"&msg4_ordernum=<%=msg4_ordernum%>"
			+"&msg4_tradetype=<%=msg4_tradetype%>"
			+"&msg4_flowtype=<%=msg4_flowtype%>"
			+"&msg4_ordercode=<%=msg4_ordercode%>"
			+"&msg4_employeecode=<%=msg4_employeecode%>"
			+"&msg4_empareacode=<%=msg4_empareacode%>"
			+"&time="+(new Date);
	var ts = window.showModalDialog(url,window,"dialogWidth:610px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no");
	
	if(ts!=null){
		var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
		var parser=new ActiveXObject("microsoft.xmldom");
		parser.async="false";
		
		var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet";
		objHTTP.Open('POST',encodeURL(url),false);
		objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		var parameter = "operationName=icbc.cmis.flow.util.util_content_msg4&opDataUnclear=true&opAction=20001"
					  +"&msg4_entcode=<%=msg4_entcode%>"
					  +"&msg4_tradecode=<%=msg4_tradecode%>"
					  +"&msg4_ordernum=<%=msg4_ordernum%>"
					  +"&ta340041013="+ts
					  +"&msg4_tradetype=<%=msg4_tradetype%>"
					  +"&msg4_flowtype=<%=msg4_flowtype%>"
					  +"&msg4_employeecode=<%=msg4_employeecode%>"
					  +"&msg4_empareacode=<%=msg4_empareacode%>"
					  +"&time=" + (new Date);
		objHTTP.Send(encodeURL(parameter));
		
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