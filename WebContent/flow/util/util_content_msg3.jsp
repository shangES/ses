<!--附件条件或限制条款-->

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
String flow_tradetype = request.getParameter("tradetype");
String flow_flowtype = request.getParameter("flowtype");
%>

<script language="JavaScript">

var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
var parser=new ActiveXObject("microsoft.xmldom");
parser.async="false";


function showadvice(entcode,tradecode,ordernum,ordercode,tradetype,flowtype)         
{
	
	var ts=window.showModalDialog("<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg3&opDataUnclear=true&opAction=20002&msg3_entcode="+entcode+"&msg3_tradecode="+encode(tradecode)+"&msg3_ordernum="+ordernum+"&msg3_ordercode="+ordercode+"&time="+new Date(),window,"dialogWidth:600px;dialogHeight:320px;center:yes;help:no;status:no;resizable:no");
	
	document.all.msg3process021.value=ts;

	if (ts!=null){
	//1

		var length21 = document.all.msg3process021.value.length;
		var t_mid21 = 0;
		if(length21%2==1){
			t_mid21 = (length21+1)/2;
		} else {
			t_mid21 = length21/2;
		}
		
		var msg3process021_1 = document.all.msg3process021.value.substring(0,t_mid21);
		var msg3process021_2 = document.all.msg3process021.value.substring(t_mid21);
		//保存辅助
	
		var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_msg3&opDataUnclear=true&opAction=10001";
		url+="&numflag=1";
		url+="&msg3_entcode="+entcode;
	url+="&msg3_tradecode="+tradecode;
	url+="&msg3_ordernum="+ordernum;
	url+="&msg3_ordercode="+ordercode;
	url+="&msg3_tradetype="+tradetype;
	url+="&msg3_flowtype="+flowtype;
		url+= "&newtime=" + new Date();
		objHTTP.Open('POST',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		objHTTP.Send(encode("msg3process021="+form1.msg3process021.value));
	
		
		if(!parser.loadXML(objHTTP.responseText)){
			alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
			return;
		}
		error = parser.getElementsByTagName("error");
		if(error.length > 0) {
			alert(error.item(0).text);
			return;
		}
	
		alert(<cmis:muiitem item="C000051" mark="\""/>);//"附加条件或限制性条款保存成功"
	}
	
}
</script>

<input type=hidden name="msg3process021" value="">
<input type=hidden name="msg3testaaaa" value="">
<table width="100%" border="1" cellspacing="" cellpadding="4">
<tr>
	<td class="td1" width = "350"><cmis:muiitem item="C000044"/><!--附加条件或限制性条款(请点击输入按钮,在对话框中输入)--></td><td><a href="javascript:showadvice('<%=flow_entcode%>','<%=flow_tradecode%>','<%=flow_ordernum%>','<%=flow_ordercode%>','<%=flow_tradetype%>','<%=flow_flowtype%>');"> <cmis:muiitem item="C000052"/></a></td>
</tr>
</table>
</cmis:muidef>