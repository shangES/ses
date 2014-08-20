<!--审批流程记录-->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ page session="false"%>
<%@ page import="java.util.Enumeration" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>

<!--这里import页面需要调用的类，不要在页面上调op类或dao类，要用自己的类封装-->
<%@ page import="icbc.cmis.flow.util.*"%>
<%@ include file="../../util/header.jsp" %>

<cmis:muidef def="icbc.cmis.flow.FLOW_UTILE">
<%
//类实例化
icbc.cmis.tags.PropertyResourceReader propertyResourceReader=new icbc.cmis.tags.PropertyResourceReader(pageContext,"icbc.cmis.flow.FLOW_UTILE");
//固定传入的参数，按需get
//entcode,		－客户号
//tradecode		－业务申请号
//tradetype		－申请种类
//flowtype		－流程种类
//ordernum		－当前环节序号
//ordercode		－当前环节代码
//employeecode	－当前柜员
//empareacode	－当前地区
//empareaname	－地区名称
//busitype		－业务性质，0，自营，1，委托

//~~~~使用自己的前缀，与其他片断无重复
String flow_entcode = request.getParameter("entcode");
String flow_tradecode = request.getParameter("tradecode");
String flow_flowtype = request.getParameter("flowtype");

String flow_ordernum = request.getParameter("ordernum");

String flow_employeecode = request.getParameter("employeecode");
String flow_empareacode = request.getParameter("empareacode");

util_content_flow flowhistory = new util_content_flow();
ArrayList flow_ret = flowhistory.queryhistory(flow_entcode,flow_tradecode,flow_flowtype,flow_employeecode,flow_empareacode);

%>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/data_check.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/liball.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/tools.js"></script>
<script Language="JavaScript">
function showquery(str){
	var url = "<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.GB.GB_CreditLimitApprove2Op&opDataUnclear=true&opAction=10001"
			+"&entcode=<%=flow_entcode%>"
			+"&appcode=<%=flow_tradecode%>"
			+"&flownum=" + str
			+"&time="+(new Date);
	var ts = window.showModalDialog(url,window,"dialogWidth:610px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no");


}
</script>
<table width="100%" border="1" cellspacing="" cellpadding="1">
<tr>
<th colspan=9 ><cmis:muiitem item="C000039" exp="审批流程"/></th>
</tr>
<tr>
<td align="center" class="td1"><cmis:muipub item="P000019" exp="序号"/></td>
<td align="center" class="td1"><cmis:muiitem item="C000040" exp="地区"/></td>
<td align="center" class="td1"><cmis:muipub item="P000029" exp="姓名"/></td>
<td align="center" class="td1"><cmis:muiitem item="C000041" exp="环节"/></td>
<td align="center" class="td1"><cmis:muipub item="P000044" exp="意见"/></td>
<td align="center" class="td1"><cmis:muiitem item="C000051" exp="说明审批说明及额度查询"/></td>
<td align="center" class="td1"><cmis:muipub item="P000045" exp="处理时间"/></td>
</tr>
<%if(flow_ret.size()==0){%>
	<tr><td colspan=9><cmis:muiitem item="C000043" exp="没有审批流程记录"/></td></tr>
<%}%>
<%for(int i=0; i<flow_ret.size(); i++) {
	HashMap hmap = (HashMap)flow_ret.get(i);
	String process005 = (String)hmap.get("process005");
	String process006 = (String)hmap.get("process006");
	String process006_name = (String)hmap.get("process006_name");
	String process007 = (String)hmap.get("process007");
	String process007_name = (String)hmap.get("process007_name");
	String process008 = (String)hmap.get("process008");
	String process008_name = (String)hmap.get("process008_name");
	String process009 = (String)hmap.get("process009");
	String process010 = (String)hmap.get("process010");
	String process011 = (String)hmap.get("process011");
	String process011_name = "";
	if(process011.equals("0")) process011_name = propertyResourceReader.getPrivateStr("C000037");//"同意";
	if(process011.equals("1")) process011_name = propertyResourceReader.getPublicStr("P000024");//"否决";
	if(process011.equals("2")) process011_name = propertyResourceReader.getPublicStr("P000025");//"退回";
	if(process011.equals("3")) process011_name = propertyResourceReader.getPrivateStr("C000045");//"保留意见";
	String isprocess021 = (String)hmap.get("isprocess021");
	String process022 = (String)hmap.get("process022");
	String isfirst = (String)hmap.get("isfirst");
	%>
	<tr <%if(i%2==0){%>bgcolor="ffffff"<%}else{%>bgcolor="f1f1f1"<%}%>>
	<td align="center"><%=process005%></td>
	<td align="center"><%=process007_name%></td>
	<td align="center"><%=process008_name%>&nbsp;</td>
	<td align="center"><%=process006_name%></td>
	
	<%if(flow_ordernum.equals(process005)){%>
	<td colspan=5><%if(process009.equals("1")){%>...<cmis:muipub item="P000171" exp="正在处理"/>...<%}else if(process009.equals("2")){%>...<cmis:muiitem item="C000052" exp="处理完毕"/>...<%}else{%>...<cmis:muiitem item="C000044" exp="待处理"/>...<%}%></td>
	<%}else{%>
	<td align="center"><%=process011_name%></td>
	<td align="center"><a href='javascript:showquery("<%=process005%>");'><cmis:muiitem item="C000047" exp="查看"/></a></td>
	<td align="center"><%=process022.substring(0,process022.length()>16?16:process022.length())%></td>	
	<%}%>
	</tr>
<%}%>
</table>
	</cmis:muidef>

