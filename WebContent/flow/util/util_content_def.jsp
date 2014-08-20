<!--流程定义-->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>

<!--这里import页面需要调用的类，不要在页面上调op类或dao类，要用自己的类封装-->
<%@ page import="icbc.cmis.flow.util.*"%>
<%@ taglib uri="cmisTags" prefix="cmis" %>
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
//empareaname	－地区名称
//busitype		－业务性质，0，自营，1，委托
CMisSessionMgr sm = new CMisSessionMgr(request);
//--------------------------------------------------------\
String LangCode = (String)sm.getSessionData("LangCode");
if(LangCode == null || LangCode.equals("")) LangCode = "zh_CN";
icbc.cmis.tags.muiStr muistr = new icbc.cmis.tags.muiStr("icbc.cmis.flow.BA.flow_BA",LangCode);
//--------------------------------------------------------/

String def_entcode = request.getParameter("entcode");
String def_tradecode = request.getParameter("tradecode");
String def_flowtype = request.getParameter("flowtype");
String def_empareacode = request.getParameter("empareacode");
String def_busitype = request.getParameter("busitype");

util_content_def queryflowdefconten = new util_content_def();
ArrayList flowdefconten = queryflowdefconten.getflowdefconten(def_entcode, def_tradecode, def_flowtype, def_empareacode, def_busitype);

String defconten = "";
String notdefconten = "";
if (flowdefconten.size() == 0) {
	defconten = muistr.getStr("C000053");
	notdefconten = muistr.getStr("C000053");
}
if (flowdefconten.size() != 0) {
	for (int i = 0; i < flowdefconten.size(); i++) {
		HashMap hmap = (HashMap)flowdefconten.get(i);

		defconten = (String)hmap.get("defconten");
		notdefconten = (String)hmap.get("notdefconten");
	}
}

%>
<table width="100%" border="1" cellspacing="0" cellpadding="1">
	<tr>
	<th colspan=2><cmis:muiitem item="C000045"/><!--准备发送--></th>
	</tr>
    <tr align="center"> 
        <td  class="td1" width="180"><cmis:muiitem item="C000054"/><!-- 当前流程定义的环节 --></td>  
        <td>&nbsp;<%=defconten%></td>  
    </tr>
    <tr align="center">
        <td  class="td1" width="180"><cmis:muiitem item="C000055"/><!--缺少的环节--></td>  
	    <td>&nbsp;<%=notdefconten%></td>  
	</tr>
</table>
</cmis:muidef>