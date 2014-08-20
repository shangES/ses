<!--待本行处理-->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ page import="icbc.cmis.flow.util.*"%>
<%@ include file="../../util/header.jsp" %>
<cmis:sessionCheck>
<cmis:muidef def="icbc.cmis.flow.FLOW_UTILE">
<html>

<%
CMisSessionMgr sm = new CMisSessionMgr(request);
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");

Vector list = (Vector)context.getValueAt("list"); 
String areacode=(String)sm.getSessionData("AreaCode");
String employeecode=(String)sm.getSessionData("EmployeeCode");
%>

<head>
<title>
<cmis:muiitem item="C000015" exp="待环节处理"/>
</title>
<link rel="stylesheet" href="<%=baseWebPath%>/<cmis:muipub item="CSSPATH"/>/main.css" type="text/css">
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/data_check.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/liball.js"></script>
<script language="JavaScript" src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/highlight.js"></script>
<script language="JavaScript" >
function cleannext(){
	form1.nextemployeecode.value = "";
	form1.nextemployeename.value = "";
}

function choosenextemployeecode(){
	ts = window.showModalDialog("<%=baseWebPath%>/util/util_ChooseEmp.jsp?queryType=QueryNormalEnp&ask=false&time=" + (new Date) + "&area_code=<%=areacode%>&sub_bank=0&major_code=280&class_code=",window,"dialogWidth:600px;dialogHeight:400px;center:yes;help:no;status:no;resizable:no");
	if(ts != null){
		form1.nextemployeecode.value = ts[0];
		form1.nextemployeename.value = ts[1];
	} 
}

//选择退回
function tuihui(){
	var j=0;
	var fsinfo="|";
	form=document.all.form1;
	var hasselect=false;

	//判断有没有选择
	for (var i=0;i<form.cs.length;i++){
		if(form.cs[i].checked){
			fsinfo=fsinfo+form.cs[i].value+"|";
			j++;
			hasselect=true;
		}
	}

	if(hasselect==true){
		//给予提示
		if(confirm(<cmis:muiitem item="C000001" mark="\"" exp="确定要进行退回吗?"/>)){
			form1.fsinfo.value=j+fsinfo;
			form1.opAction.value="60003";
			form1.submit();	
		}
	}else{
		alert(<cmis:muiitem item="C000002" mark="\"" exp="请选择你要退回的数据!"/>);
	}

}

//选择确定
function fenfa(){
	if(isEmpty(form1.nextemployeecode.value)){
		alert(<cmis:muiitem item="C000003" mark="\"" exp="请选择下一处理人"/>);
		return;
	}

	var j=0;
	var fsinfo="|";

	var hasselect=false;

	//判断有没有选择
	for (var i=0;i<form1.cs.length;i++){
		if(form1.cs[i].checked){
			fsinfo=fsinfo+form1.cs[i].value+"|";
			j++;
			hasselect=true;
		}
	}
	if(hasselect==true){
		//给予提示
		if(confirm(<cmis:muiitem item="C000004" mark="\"" exp="确定要进行分发吗?"/>)){
			form1.fsinfo.value=j+fsinfo;
			form1.opAction.value="60002";
			form1.submit();	
		}
	}else{
		alert(<cmis:muiitem item="C000005" mark="\"" exp="请选择你要分发的数据!"/>);
	}

}
</script></head>
<body >
<form name="form1" method="post" action="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet">
<input type=hidden name="operationName" value="icbc.cmis.flow.util.unit_content_nopersonbanklist">
<input type=hidden name="opAction" value="">
<input type=hidden name="fsinfo" value="">


<cmis:framework align="center">
<cmis:tabpage width="750">
<cmis:tabpagebar titleid="C000016" selected="true"/><!--待本环节处理-->
<cmis:tabpagecontent info="" align="center"> 
<div id="showframe">
<table width="100%" border="1" cellspacing="" cellpadding="1">

<tr>
<td align="center" class="td1"><cmis:muipub item="P000019" exp="序号"/></td>
<td align="center" class="td1"><cmis:muipub item="P000004" exp="客户代码"/></td>
<td align="center" class="td1"><cmis:muipub item="P000074" exp="客户全称"/></td>
<td class="td1" align="center"><cmis:muiitem item="C000017" exp="授信时间"/></td>
<td align="center" class="td1"><cmis:muiitem item="C000018" exp="调整种类"/></td>
<td class="td1" align="center"><cmis:muiitem item="C000019" exp="调整序号"/></td>
<td align="center" class="td1"><cmis:muipub item="P000027" exp="申请号"/></td>
<td align="center" class="td1"><cmis:muipub item="P000170" exp="申请种类"/></td>
<td align="center" class="td1"><cmis:muiitem item="C000008" exp="所属环节"/></td>
</tr>
<INPUT TYPE="hidden" NAME="cs" value=''>
<%for(int i=0; i<list.size(); i++) {
	Hashtable htable = (Hashtable)list.get(i);
	
	String process001 = (String)htable.get("process001");
	String ta200011003 = (String)htable.get("ta200011003");
	String process002 = (String)htable.get("process002");
	String process002_time = process002.substring(0,8);//授信时间
	String process002_num = process002.substring(9);//调整序号
	String process003 = (String)htable.get("process003");
    String kind_name = (String)htable.get("kind_name");
	String process004 = (String)htable.get("process004");
	String pa002 = (String)htable.get("pa002");
	String process005 = (String)htable.get("process005");
	String process006 = (String)htable.get("process006");
	String role_name = (String)htable.get("role_name");
	String process007 = (String)htable.get("process007");
	String process008 = (String)htable.get("process008");
	String ta200011040 = (String)htable.get("ta200011040");
	String process023 = (String)htable.get("process023");
	String process024 = (String)htable.get("process024");
%>
<tr <%if(i%2==0){%>bgcolor="ffffff"<%}else{%>bgcolor="f1f1f1"<%}%>>
    <td align="center"><INPUT type="checkbox" name="cs" value='<%=","+process001+","+process002+","+process003+","+process004+","+process005+","+process006+","+process007+","+employeecode%>'></td>
	<td>&nbsp;<%=process001%></td>
	<td>&nbsp;<%=ta200011003%></td>
	<td>&nbsp;<%=process002_time%></td>
    <td>&nbsp;<%=kind_name%></td>
    <td>&nbsp;<%=process002_num%></td>
	<td>&nbsp;<%=process002%></td>
	<td>&nbsp;<%=pa002%></td>
    <td>&nbsp;<%=role_name%></td>
</tr>
<%}%>

</table>
<%if (list.size()!=0){%>
<table width="100%" border="1" cellspacing="" cellpadding="1">
	<tr align="left">
		<td width="140" class="td1"><cmis:muiitem item="C000010" exp="下一处理人代码"/><a href="javascript:cleannext();"><cmis:muiitem item="C000011" exp="清除"/></a></td>
		<td width="208" valign="bottom"><input name="nextemployeecode" size="22" maxlength="15" readonly>
			<a href="javascript:choosenextemployeecode();" title=<cmis:muiitem item="C000012" mark="\"" exp="选择下一处理人代码"/>><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/choose.gif" border="0"></a>
		</td>
		<td width="140" class="td1"><cmis:muiitem item="C000013" exp="下一处理人姓名"/></td>
		<td width="208" valign="bottom"><input type="text"
			name="nextemployeename" size="22" maxlength="60" readonly></td>
	</tr>
</table>
<%}%>

</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="right">
	<tr align="right">
		<td class=td1 align="right"  valign="bottom" height=35>
			<%if (list.size()!=0){%><a href="javascript:fenfa()"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/send.gif"  border="0"></a><!--a 
									   href="javascript:tuihui()"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/untread.gif"  border="0"></a--><%}%><a 
									   href="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.servlets.CmisMain"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/home.gif" alt="主页"  border="0"></a>
		</td>
	</tr>
</table>

</cmis:tabpagecontent>
</cmis:tabpage>
</cmis:framework>
</form>
</body>
<jsp:include page="/util/footer.jsp" flush="true" />
</html>
	</cmis:muidef></cmis:sessionCheck>
	