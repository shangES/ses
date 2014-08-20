<!--审批流程记录-->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ include file="../../util/header.jsp"%>
<%@ taglib uri="cmisTags" prefix="cmis" %>

<!--这里import页面需要调用的类，不要在页面上调op类或dao类，要用自己的类封装-->
<%@ page import="icbc.cmis.flow.util.*"%>

<cmis:muidef def="icbc.cmis.flow.BA.flow_BA">
<%
String ass_entcode = request.getParameter("entcode");
String ass_tradecode = request.getParameter("tradecode");
String ass_ordernum = request.getParameter("ordernum");
String ass_employeecode = request.getParameter("employeecode");
String ass_empareacode = request.getParameter("empareacode");

util_content_doassist assinfo = new util_content_doassist();
HashMap ass_hmap = assinfo.queryass(ass_entcode,ass_tradecode,ass_ordernum,ass_employeecode,ass_empareacode);

String ass_process007 = (String)ass_hmap.get("process007");
String ass_process007_name = (String)ass_hmap.get("process007_name");
String ass_process008 = (String)ass_hmap.get("process008");
String ass_process008_name = (String)ass_hmap.get("process008_name");
String ass_process020 = (String)ass_hmap.get("process020");
String ass_advice006 = (String)ass_hmap.get("advice006");
String ass_advice008 = (String)ass_hmap.get("advice008");
if (ass_advice006==null) ass_advice006="";
%>
<script src="<%=baseWebPath%>/jslib/data_check.js"></script>
<script src="<%=baseWebPath%>/jslib/liball.js"></script>
<script src="<%=baseWebPath%>/flow/jslib/checkSyntax.js"></script>
<script Language="JavaScript">

function ass_save(){

	if(isEmpty(form1.ass_advice008.value)){
		//alert("请输入意见说明");
		alert(<cmis:muiitem item="C000000" mark="\""/>+<cmis:muiitem item="C000091" mark="\""/>)
		return;
	}
	form1.operationName.value = "icbc.cmis.flow.util.util_content_doassistOp";
	form1.opAction.value = "asssave";
	form1.opDataUnclear.value = "true";
	form1.submit();
	
}

</script>

<table width="100%" border="1" cellspacing="" cellpadding="3">
<tr>
<td class="td1" width="100"><cmis:muiitem item="C000092"/><!-- 柜员地区 --></td><td><%=ass_process007_name%></td>
<td class="td1"><cmis:muiitem item="C000093"/><!-- 业务柜员名称 --></td><td><%=ass_process008_name%></td>
</tr>
<tr>
<td class="td1"><cmis:muiitem item="C000067"/><!-- 辅助审核内容 --></td>
<td colspan=3><%=(String)ass_hmap.get("process020")%></td>
</tr>
<tr>
<td class="td1"><cmis:muiitem item="C000042"/><!-- 本人意见 --></td>
<td colspan=3>
<INPUT type="radio" name="ass_advice006" value="1" <%if(ass_advice006.equals("1")){%>checked<%}%> ><cmis:muiitem item="C000074"/><!-- 不同意 -->&nbsp;&nbsp;&nbsp;
<INPUT type="radio" name="ass_advice006" value="2" <%if(ass_advice006.equals("2")){%>checked<%}%> ><cmis:muiitem item="C000075"/><!-- 同意 -->
</td>
</tr>
<tr>
<td class="td1"><cmis:muiitem item="C000091"/><!-- 意见说明 --></td>
<td colspan=3><TEXTAREA rows="7" cols="80" name="ass_advice008" onblur="javascript:checkAreaLength(this,1000);javascript:checkSyntax(this);"><%=ass_advice008%></TEXTAREA></td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
<tr>
<td class=td1 align="right"  valign="bottom" height=35>
<IMG border="0" src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/confirm.gif" class="hand" onclick="javascript:ass_save();">
</td>
</tr>
</table>
</cmis:muidef>