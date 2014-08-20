<!--选择下一地区--> 
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%> <%@ page import="java.util.*"%>
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
CMisSessionMgr sm = new CMisSessionMgr(request);
//--------------------------------------------------------\
String LangCode = (String)sm.getSessionData("LangCode");
if(LangCode == null || LangCode.equals("")) LangCode = "zh_CN";
icbc.cmis.tags.muiStr muistr = new icbc.cmis.tags.muiStr("icbc.cmis.flow.BA.flow_BA",LangCode);
//--------------------------------------------------------/

String chsn_AreaCode = request.getParameter("empareacode");
String chsn_entcode = request.getParameter("entcode");
String chsn_flowtype = request.getParameter("flowtype");
String chsn_busitype = request.getParameter("busitype");
String chsn_tradecode = request.getParameter("tradecode");
String chsn_tradetype = request.getParameter("tradetype");

util_content_choosenext c_nextarea = new util_content_choosenext();
util_content_choosenext1 c_nextarea1 = new util_content_choosenext1();
ArrayList chsn_flowlist = c_nextarea.getflowlist(chsn_flowtype,chsn_busitype,LangCode);
ArrayList chsn_arealist = null;

if (null != chsn_tradetype && chsn_tradecode!=null && (chsn_tradetype.substring(0,2)).equals("qt"))
{
chsn_arealist = c_nextarea1.getnextarea(chsn_AreaCode,chsn_busitype,chsn_entcode,chsn_tradecode,chsn_tradetype);
}
else{
 chsn_arealist = c_nextarea.getnextarea(chsn_AreaCode,chsn_busitype,chsn_entcode);
}
CMisSessionMgr smTemp = new CMisSessionMgr(request);//
String s_Major = (String) smTemp.getSessionData("Major"); //当前柜员业务（专业）

%>
<script language="JavaScript">
function choosenextemployeecode()
{
	if(isEmpty(form1.nextareacode.value)){
		alert(<cmis:muiitem item="C000056" mark="\""/>);//"请选择下一处理地区"
		return;
	}
	var temparea = form1.nextareacode.value.split('|');
	var ts = null;

	ts = window.showModalDialog("<%=baseWebPath%>/util/util_ChooseEmp.jsp?queryType=QueryNormalEnp&ask=false&time=" + (new Date) + "&area_code="+temparea[0]+"&sub_bank=0&major_code=<%=s_Major%>&class_code=",window,"dialogWidth:600px;dialogHeight:400px;center:yes;help:no;status:no;resizable:no");
	if(ts != null){
		form1.nextemployeecode.value = ts[0];
		form1.nextemployeename.value = ts[1];
	} 
}
function cleannext(){
	form1.nextemployeecode.value = "";
	form1.nextemployeename.value = "";
}
</script> 
<table width="100%" border="1" cellspacing="" cellpadding="1">
	<tr align="left">
		<td width="140" class="td1"><cmis:muiitem item="C000057"/><!-- 选择下一环节 --></td>
		<td width="208">
		<select name="nextflow">
		<option value=""></option>
		<%
		for(int i=0; i<chsn_flowlist.size(); i++) {		
			HashMap hmap = (HashMap)chsn_flowlist.get(i);%>
		<option value ="<%=(String)hmap.get("role_code")%>"><%=(String)hmap.get("role_name")%></option>
		<%}%>
		</select>
		</td>
		<td width="140" class="td1"><cmis:muiitem item="C000058"/><!-- 下一处理地区 --></td>
		<td width="208">
		<select name="nextareacode" onchange="javascript:cleannext();">
		<option value=""></option>
		<%for(int i=0; i<chsn_arealist.size(); i++) {		
			HashMap hmap = (HashMap)chsn_arealist.get(i);%>
		<option value ="<%=(String)hmap.get("area_code")%>"><%=(String)hmap.get("area_name")%></option>
		<%}%>		
		</select>
		</td>
	</tr>
	<tr align="left">
		<td width="140" class="td1"><cmis:muiitem item="C000059"/><!-- 下一处理人代码 --><a href="javascript:cleannext();"><cmis:muiitem item="C000060"/></a></td>
		<td width="208" valign="bottom"><input name="nextemployeecode" size="22" maxlength="15" readonly>
			<a href="javascript:choosenextemployeecode();" title="选择下一处理人代码"><img src="<%=baseWebPath%>/images/choose.gif" border="0"></a>
		</td>
		<td width="140" class="td1"><cmis:muiitem item="C000061"/><!--下一处理人姓名--></td>
		<td width="208" valign="bottom"><input type="text"
			name="nextemployeename" size="22" maxlength="60" readonly></td>
	</tr>
</table>
</cmis:muidef>