<!--待本行处理-->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ page import="icbc.cmis.flow.util.*"%>
<%@ include file="../../util/header.jsp" %>
<cmis:sessionCheck>
<html>

<%



CMisSessionMgr sm = new CMisSessionMgr(request);
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");

Vector list = (Vector)context.getValueAt("list"); 
String areacode=(String)sm.getSessionData("AreaCode");
String employeecode=(String)sm.getSessionData("EmployeeCode");
String jspurl = "/flow/util/util_content_choosenext.jsp";

String tempp = jspurl
			+ "?entcode=" + java.net.URLEncoder.encode("*")
			+ "&empareacode=" + java.net.URLEncoder.encode(areacode)
			+ "&flowtype=" + java.net.URLEncoder.encode("sxh01")
			+ "&busitype=" + java.net.URLEncoder.encode("6");
%>

<head>
<title>
待本人处理
</title>
<link rel="stylesheet" href="<%=baseWebPath%>/css/main.css" type="text/css">
<script src="<%=baseWebPath%>/jslib/data_check.js"></script>
<script src="<%=baseWebPath%>/jslib/liball.js"></script>
<script language="JavaScript" src="<%=baseWebPath%>/jslib/highlight.js"></script>
<script language="JavaScript" >
//待本行处理
	function nobanklist(){
		form1.opAction.value="70001";

		form1.submit();
	}
	
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
		if(confirm("确定要进行退回吗?")){
			form1.fsinfo.value=j+fsinfo;
			form1.opAction.value="60003";
			form1.submit();	
		}
	}else{
		alert("请选择你要退回的数据!");
	}

}

//选择确定
function fenfa(){
	if(isEmpty(form1.nextemployeecode.value)){
		alert("请选择下一处理人");
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
		if(confirm("确定要进行分发吗?")){
			form1.fsinfo.value=j+fsinfo;
			form1.opAction.value="70002";
			form1.submit();	
		}
	}else{
		alert("请选择你要分发的数据!");
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
<cmis:tabpagebar title="待本行处理" onclick="nobanklist();" selected="false"/>
<cmis:tabpagebar title="待本人处理" selected="true"/>
<cmis:tabpagecontent info="" align="center"> 
<div id="showframe">
<table width="100%" border="1" cellspacing="" cellpadding="1">

<tr>
<td align="center" class="td1">序号</td>
<td align="center" class="td1">客户代码</td>
<td align="center" class="td1">客户全称</td>
<td align="center" class="td1">检查期</td>
<!--<td align="center" class="td1">调整种类</td>-->
<td align="center" class="td1">检查次数</td>
<td align="center" class="td1">申请号</td>
<td align="center" class="td1">申请种类</td>
<td align="center" class="td1">所属环节</td>
</tr>
<INPUT TYPE="hidden" NAME="cs" value=''>
<%for(int i=0; i<list.size(); i++) {
	Hashtable htable = (Hashtable)list.get(i);
	
	String process001 = (String)htable.get("process001");
	String ta200011003 = (String)htable.get("ta200011003");
	String process002 = (String)htable.get("process002");
	String process002_time = process002.substring(0,6);//授信时间
	String process002_num = process002.substring(7,10);//调整序号
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
   <!-- <td>&nbsp;<%=kind_name%></td>-->
    <td>&nbsp;<%=process002_num%></td>
	<td>&nbsp;<%=process002%></td>
	<td>&nbsp;<%=pa002%></td>
    <td>&nbsp;<%=role_name%></td>
</tr>
<%}%>

</table>
 
<%if (list.size()!=0){%>
<jsp:include page="<%=tempp%>" flush="true" />  
<%}%>

</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0" align="right">
	<tr align="right">
		<td class=td1 align="right"  valign="bottom" height=35>
			<%if (list.size()!=0){%><a href="javascript:fenfa()"><img src="<%=baseWebPath%>/images/send.gif"  border="0"></a><!--a 
									   href="javascript:tuihui()"><img src="<%=baseWebPath%>/images/untread.gif"  border="0"></a--><%}%><a 
									   href="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.servlets.CmisMain"><img src="<%=baseWebPath%>/images/home.gif" alt="主页"  border="0"></a>
		</td>
	</tr>
</table>

</cmis:tabpagecontent>
</cmis:tabpage>
</cmis:framework>
</form>
</body>
<jsp:include page="/util/footer.jsp" flush="true" />
</html></cmis:sessionCheck>
	