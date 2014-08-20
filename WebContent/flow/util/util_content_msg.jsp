<!--意见说明-->
<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>

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
String flow_entcode = request.getParameter("entcode");
String flow_tradecode = request.getParameter("tradecode");
String flow_ordernum = request.getParameter("ordernum");
String flow_ordercode = request.getParameter("ordercode");
//util_content_flow flowhistory = new util_content_flow();
//Vector flow_ret = flowhistory.queryhistory(flow_entcode,flow_tradecode);

%>

<script language="JavaScript">
function showadvice(entcode,tradecode,ordernum,ordercode)//formflag:1,意见说明 2附件条件
{

 window.showModalDialog("<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.util_content_msg&opAction="20001"&entcode="+entcode+"&tradecode="+tradecode+"&ordernum="+ordernum+"&ordercode="+ordercode+"&time="+new Date(),window,"dialogWidth:500px;dialogHeight:320px;center:yes;help:no;status:no;resizable:no");
}
</script>

<table width="100%" border="1" cellspacing="" cellpadding="1">
<tr>
<td  class="td1">本人意见</td>
</tr>
<tr>
</tr>

<tr>
	
	<td>意见说明(请点击意见按钮,在对话框中输入)<a href="javascript:showadvice('<%=flow_entcode%>','<%=flow_tradecode%>','<%=flow_ordernum%>','<%=flow_ordercode%>');"> 意见 </a></td>
</tr>

</table>