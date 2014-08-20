<!--	签批人签署的放款条件 页面 -->
<%@ page contentType="text/html;charset=GBK" pageEncoding="GBK"%>
<%@ page import="icbc.cmis.base.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="icbc.cmis.flow.util.*"%>
<%@ taglib uri="cmisTags" prefix="cmis" %>
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
String iamsg_entcode = request.getParameter("entcode");
String iamsg_tradecode = request.getParameter("tradecode");
String iamsg_flowtype = request.getParameter("flowtype");
String iamsg_tradetype = request.getParameter("tradetype");
String iamsg_ordercode = request.getParameter("ordercode");
String iamsg_employeecode = request.getParameter("employeecode");
String iamsg_empareacode = request.getParameter("empareacode");
util_content_subscribe subscribe = new util_content_subscribe();

String subscribemsg = subscribe.querysubscribe(iamsg_entcode,iamsg_tradecode,iamsg_empareacode,iamsg_employeecode,iamsg_ordercode,iamsg_flowtype,iamsg_tradetype);
%>
  <table width="100%" border="1" cellspacing="" cellpadding="1">
  <tr><th  class="td1" align="left"><font size="4">签批人签署的放款条件:</font></th></tr>
  <tr>
  <td class="td1">
 <TEXTAREA NAME="process021" ROWS="7" COLS="120"><%=subscribemsg%></TEXTAREA>   
  </td>
  <tr>
  </table>




 
 
