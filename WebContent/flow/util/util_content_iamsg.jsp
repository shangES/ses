<!--1.11	显示只读机评意见-->
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
String iamsg_entcode = request.getParameter("entcode");
String iamsg_tradecode = request.getParameter("tradecode");
String iamsg_flowtype = request.getParameter("flowtype");
String iamsg_tradetype = request.getParameter("tradetype");
String iamsg_ordercode = request.getParameter("ordercode");
String iamsg_employeecode = request.getParameter("employeecode");
String iamsg_empareacode = request.getParameter("empareacode");
util_content_iamsg advicehistory = new util_content_iamsg();

ArrayList jipingadvice = advicehistory.queryiamsg(iamsg_entcode,iamsg_tradecode,iamsg_empareacode,iamsg_employeecode,iamsg_ordercode,iamsg_flowtype,iamsg_tradetype);
String o_ret1="";
String o_ret2="";
if(jipingadvice.size()==0)
{
o_ret1="无";
o_ret2="无";
}
if(jipingadvice.size()!=0)
{
for(int i=0; i<jipingadvice.size(); i++) {
	HashMap hmap = (HashMap)jipingadvice.get(i);

	 o_ret1 = (String)hmap.get("o_ret1");
	 o_ret2 = (String)hmap.get("o_ret2");
	}
}
%>
<input type="hidden" name="iamsg2process017" value="0">
<input type="hidden" name="o_stoparea" value="">

<table width="100%" border="1" cellspacing="" cellpadding="1">
<tr><th colspan=2><cmis:muiitem item="C000062"/><!-- 系统提示信息 --></th></tr>
<tr>
<td class=td1 align="center" width="50%"><cmis:muiitem item="C000064"/><!-- 刚性控制信息： --></td>
<td class=td1 align="center" width="50%"><cmis:muiitem item="C000065"/><!-- 提示性信息： --></td>
</table>
<table width="100%" border="0" cellspacing="" cellpadding="0">

<tr>
<td><TEXTAREA NAME="iamsg2gxadvice" ROWS="5" COLS="65"  readonly ><%=o_ret1%></TEXTAREA></td>  
<td><TEXTAREA NAME="iamsg2rxadvice" ROWS="5" COLS="65"  readonly ><%=o_ret2%></TEXTAREA></td> 
</tr>
</table>
</cmis:muidef>

