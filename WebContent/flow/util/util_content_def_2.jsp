<!--流程定义，支持低风险业务-->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
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

String def_entcode     = request.getParameter("entcode");
String def_tradecode   = request.getParameter("tradecode");
String def_flowtype    = request.getParameter("flowtype");
String def_empareacode = request.getParameter("empareacode");
String def_tradetype    = request.getParameter("tradetype");

icbc.cmis.flow.util.util_content_def_2 util_Def = new icbc.cmis.flow.util.util_content_def_2();
HashMap hmap = util_Def.getflowdefconten(def_entcode, def_tradecode, def_flowtype, def_empareacode, def_tradetype, LangCode);

String defconten = "";
String notdefconten = "";
String lowriskinfo = "";

defconten = (String)hmap.get("defconten");
notdefconten = (String)hmap.get("notdefconten");
lowriskinfo = (String)hmap.get("lowriskinfo");

if(lowriskinfo.equals("0")) lowriskinfo = muistr.getStr("C000094");//低风险业务标志
else lowriskinfo="";
if(defconten.equals("")) defconten = muistr.getStr("C000053");
if(notdefconten.equals("")) notdefconten = muistr.getStr("C000053");

%>
<table width="100%" border="1" cellspacing="0" cellpadding="1">
	<tr>
	<th colspan=2><cmis:muiitem item="C000045"/><!--准备发送--></th>
	</tr>
    <tr align="center"> 
        <td  class="td1" width="180"><cmis:muiitem item="C000054"/><!-- 当前流程定义的环节 --></td>  
        <td>&nbsp;<%=lowriskinfo%><%=defconten%></td>  
    </tr>
    <tr align="center">
        <td  class="td1" width="180"><cmis:muiitem item="C000055"/><!--缺少的环节--></td>  
	    <td>&nbsp;<%=notdefconten%></td>  
	</tr>
</table>
</cmis:muidef>