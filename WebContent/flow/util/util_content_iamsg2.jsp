<!--1.11	重新生成机评意见 -->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Vector" %>

<!--这里import页面需要调用的类，不要在页面上调op类或dao类，要用自己的类封装-->
<%@ page import="icbc.cmis.flow.util.*"%>
<%@ include file="../../util/header.jsp" %>

<%@ taglib uri="cmisTags" prefix="cmis" %>
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

String iamsg2_entcode = request.getParameter("entcode");
String iamsg2_tradecode = request.getParameter("tradecode");
String iamsg2_ordernum = request.getParameter("ordernum");
String iamsg2_flowtype = request.getParameter("flowtype");
String iamsg2_tradetype = request.getParameter("tradetype");
String iamsg2_ordercode = request.getParameter("ordercode");
String iamsg2_employeecode = request.getParameter("employeecode");
String iamsg2_empareacode = request.getParameter("empareacode");
%>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/data_check.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/liball.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/tools.js"></script>
<script language="JavaScript">

function secondiamsg() {
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util.util_content_iamsg2&opDataUnclear=true&opAction=20001&entcode=<%=iamsg2_entcode%>&tradecode=<%=iamsg2_tradecode%>&tradetype=<%=iamsg2_tradetype%>&ordercode=<%=iamsg2_ordercode%>&employeecode=<%=iamsg2_employeecode%>&empareacode=<%=iamsg2_empareacode%>&ordernum=<%=iamsg2_ordernum%>&flowtype=<%=iamsg2_flowtype%>";
	url+= "&newtime=" + new Date();
	objHTTP.Open('GET',encodeURL(url),false);
	objHTTP.Send();
	
	if(!parser.loadXML(objHTTP.responseText)){
		//alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
	    return;
	}
	error = parser.getElementsByTagName("error");
	if(error.length > 0) {
		alert(error.item(0).text);
		return;
	}	

	info = parser.getElementsByTagName("info");
	if(info.length > 0){
		var nodes = parser.documentElement.childNodes;
		var ts=new Array(6);
		if(nodes.length > 0){
			for(i = 0; i < nodes.length ; i ++ ) {
				var node = nodes.item(i);
				ts[0]=node.getAttribute("iamsg2process017");//得到机评结果
				ts[1]=node.getAttribute("o_stoparea");//停办设置行
				ts[2]=node.getAttribute("o_ret1");//得到刚性机评意见
				ts[3]=node.getAttribute("o_ret2");//得到软性机评意见
				document.all.iamsg2process017.value=ts[0];
				document.all.o_stoparea.value=ts[1];
				document.all.iamsg2gxadvice.value =ts[2];
				document.all.iamsg2rxadvice.value =ts[3];
							
			}
		}
	}
	
}

</script>


<input type="hidden" name="iamsg2process017" value="">
<input type="hidden" name="o_stoparea" value="">

<table width="100%" border="1" cellspacing="" cellpadding="1">
<tr><th colspan=2><cmis:muiitem item="C000062"/><!-- 系统提示信息 --><a href="javascript:secondiamsg();"><cmis:muiitem item="C000063"/><!-- 生成 --></a></th></tr>
<tr>
<td class="td1" align="center" width="50%"><cmis:muiitem item="C000064"/><!-- 刚性控制信息： --></td>
<td class="td1" align="center" width="50%"><cmis:muiitem item="C000065"/><!-- 提示性信息： --></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="" cellpadding="0">
<tr>
<td><TEXTAREA NAME="iamsg2gxadvice" ROWS="5" COLS="65"  readonly ><cmis:muiitem item="C000066"/></TEXTAREA></td><!-- 请点击生成 -->
<td><TEXTAREA NAME="iamsg2rxadvice" ROWS="5" COLS="65"  readonly ><cmis:muiitem item="C000066"/></TEXTAREA></td><!-- 请点击生成 -->
</tr>
</table>

</cmis:muidef>