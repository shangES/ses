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


KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");
CMisSessionMgr sm = new CMisSessionMgr(request);
java.util.Hashtable h_table=CmisConstance.getDictParam((String)sm.getSessionData("LangCode"));
Vector list = (Vector)context.getValueAt("list"); 
DictBean bean = null;
DictBean bean1 = null;    
String areacode=(String)sm.getSessionData("AreaCode");
String employeecode=(String)sm.getSessionData("EmployeeCode");
String flowtype = (String)context.getValueAt("flowtype");         
String busitype = (String)context.getValueAt("busitype");
String jspurl = "/flow/util/util_content_choosenext.jsp";
String tempp = jspurl
			+ "?entcode=" + java.net.URLEncoder.encode("*")
			+ "&empareacode=" + java.net.URLEncoder.encode(areacode)
			+ "&flowtype=" + java.net.URLEncoder.encode(flowtype)
			+ "&busitype=" + java.net.URLEncoder.encode(busitype);
			System.out.println(tempp);
String s_Major = (String) sm.getSessionData("Major"); //当前柜员业务（专业）
%>

<head>
<title>
<cmis:muiitem item="C000006" exp="待本行处理"/>
</title>
<link rel="stylesheet" href="<%=baseWebPath%>/<cmis:muipub item="CSSPATH"/>/main.css" type="text/css">
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/data_check.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/liball.js"></script>
<script language="JavaScript" src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/highlight.js"></script>
<script language="JavaScript" >

//待本人处理

	function nopersonlist(){
		form1.opAction.value="20001";
		form1.submit();
	}
	
function cleannext(){
	form1.nextemployeecode.value = "";
	form1.nextemployeename.value = "";
}

function choosenextemployeecode()
{
/*
	<%if(busitype.equals("0")){%>
	ts = window.showModalDialog("<%=baseWebPath%>/util/util_ChooseEmp.jsp?queryType=QueryNormalEnp&ask=false&time=" + (new Date) + "&area_code=<%=areacode%>&sub_bank=0&major_code=210&class_code=",window,"dialogWidth:600px;dialogHeight:400px;center:yes;help:no;status:no;resizable:no");
	<%}%>
	
	<%if(busitype.equals("1")){%>
	ts = window.showModalDialog("<%=baseWebPath%>/util/util_ChooseEmp.jsp?queryType=QueryNormalEnp&ask=false&time=" + (new Date) + "&area_code=<%=areacode%>&sub_bank=0&major_code=370&class_code=",window,"dialogWidth:600px;dialogHeight:400px;center:yes;help:no;status:no;resizable:no");
	<%}%>	

	<%if(busitype.equals("2")){%>
	ts = window.showModalDialog("<%=baseWebPath%>/util/util_ChooseEmp.jsp?queryType=QueryNormalEnp&ask=false&time=" + (new Date) + "&area_code=<%=areacode%>&sub_bank=0&major_code=280&class_code=",window,"dialogWidth:600px;dialogHeight:400px;center:yes;help:no;status:no;resizable:no");
	<%}%>
*/

	ts = window.showModalDialog("<%=baseWebPath%>/util/util_ChooseEmp.jsp?queryType=QueryNormalEnp&ask=false&time=" + (new Date) + "&area_code=<%=areacode%>&sub_bank=0&major_code=<%=s_Major%>&class_code=",window,"dialogWidth:600px;dialogHeight:400px;center:yes;help:no;status:no;resizable:no");
	if(ts != null){
		form1.nextemployeecode.value = ts[0];
		form1.nextemployeename.value = ts[1];
	} 
}

//选择确定
function tuihui()
{


var j=0;
var fsinfo="|";
	form=document.all.form1;
	var hasselect=false;

//判断有没有选择
	for (var i=0;i<form.cs.length;i++)
	{
		if(form.cs[i].checked)
		{
		fsinfo=fsinfo+form.cs[i].value+"|";
		j++;
		hasselect=true;
		}
		  
	}

	 if (j>1){
    alert(<cmis:muiitem item="C000066" mark="\"" exp="当前只能对一笔记录进行退回"/>);
    return;
    }
	if(hasselect==true && j==1)
	{
	
		//给予提示
		if(confirm(<cmis:muiitem item="C000001" mark="\"" exp="确定要进行退回吗?"/>))
		{
		 form1.fsinfo.value=j+fsinfo;
	  	   form1.opAction.value="40001";
	       form1.submit();	
		}
	}
	else
	{
	alert(<cmis:muiitem item="C000002" mark="\"" exp="请选择你要退回的数据!"/>);
	}

}

//选择确定
function fenfa()
{
if(isEmpty(form1.nextemployeecode.value)){
		alert(<cmis:muiitem item="C000003" mark="\"" exp="请选择下一处理人"/>);
		return;
	}

var j=0;
var fsinfo="|";

	var hasselect=false;

//判断有没有选择
	for (var i=0;i<form1.cs.length;i++)
	{

		if(form1.cs[i].checked)
		{
		fsinfo=fsinfo+form1.cs[i].value+"|";
		j++;
		hasselect=true;
		}
		  
	}

	if(hasselect==true)
	{
	
		//给予提示
		if(confirm(<cmis:muiitem item="C000004" mark="\"" exp="确定要进行分发吗?"/>))
		{
		 form1.fsinfo.value=j+fsinfo;
	  	   form1.opAction.value="20003";
	       form1.submit();	
		}
	}
	else
	{
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
	
	<cmis:tabpagebar titleid="C000006" selected="true"/><!--待本行处理-->
	<cmis:tabpagebar titleid="C000007" onclick="nopersonlist();" selected="false"/><!--待本人处理-->
	
	<cmis:tabpagecontent info="" align="center"> 
	<div id="showframe">
<table width="100%" border="1" cellspacing="" cellpadding="1">

<tr>
<td align="center" class="td1"><cmis:muipub item="P000019" exp="序号"/></td>
<td align="center" class="td1"><cmis:muipub item="P000004" exp="客户代码"/></td>
<td align="center" class="td1"><cmis:muipub item="P000074" exp="客户全称"/></td>
<td align="center" class="td1"><cmis:muipub item="P000027" exp="申请号"/></td>
<td align="center" class="td1"><cmis:muipub item="P000056" exp="业务种类"/></td>
<td align="center" class="td1"><cmis:muipub item="P000170" exp="申请种类"/></td>
<td align="center" class="td1"><cmis:muiitem item="C000008" exp="所属环节"/></td>
<td align="center" class="td1"><cmis:muipub item="P000321" exp="信用等级"/></td>
<td align="center" class="td1"><cmis:muiitem item="C000009" exp="业务币种"/></td>
<td align="center" class="td1"><cmis:muipub item="P000248" exp="业务金额"/></td>

</tr>
<INPUT TYPE="hidden" NAME="cs" value=''>
<%for(int i=0; i<list.size(); i++) {
	Hashtable htable = (Hashtable)list.get(i);
	
	String process001 = (String)htable.get("process001");
	String ta200011003 = (String)htable.get("ta200011003");
	String process002 = (String)htable.get("process002");
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
	<td>&nbsp;<%=process002%></td>
    <td>&nbsp;<%=kind_name%></td>
    <td>&nbsp;<%=pa002%></td>
	<td>&nbsp;<%=role_name%></td>
	
	<td>&nbsp;<%bean=(DictBean)h_table.get("ida200011040");
 	 			out.println(bean.getValue((String)htable.get("ta200011040")));	
 	 			%></td>
    <td>&nbsp;<%bean1=(DictBean)h_table.get("ida200251007");
 	 			out.println(bean1.getValue((String)htable.get("process023")));	
 	 			%></td>
    <td>&nbsp;<%=process024%></td>
	
</tr>
<%}%>

</table>
<%if (list.size()!=0){%>

	
	<%if(!busitype.equals("2") && !busitype.equals("4") && !busitype.equals("6")) {%>

	<jsp:include page="<%=tempp%>" flush="true" /> 
	<%}%>	

<table width="100%"  border="1" cellspacing="" cellpadding="1">
<tr> 
        <td  class="td1"><cmis:muiitem item="C000014" exp="分发意见(最多输入500个汉字)"/></td>
        <tr/>
    <tr>
      <td class="td1">       
         <TEXTAREA NAME="process012" ROWS="5" COLS="120" onblur="javascript:checkAreaLength(this,1000);"></TEXTAREA>     
       
      </td>
    </tr>
  </table>
    <%}%>
</div>
<table   border="1" cellspacing="" cellpadding="1" align="right">
<tr align="right">
<%if (list.size()!=0){%><td><a href="javascript:fenfa()"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/send.gif"  border="0"></a></td><%}%><%if (list.size()!=0){%><td><a href="javascript:tuihui()"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/untread.gif"  border="0"></a></td><%}%><td><a href="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.servlets.CmisMain"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/home.gif" alt="主页"  border="0"></a></td>
	   </tr> 
</table>

</cmis:tabpagecontent>
</cmis:tabpage>
</cmis:framework>
<font color="FFFFFF"><%=busitype%></font>
</form>
</body>
<jsp:include page="/util/footer.jsp" flush="true" />
</html>
	</cmis:muidef></cmis:sessionCheck>
	