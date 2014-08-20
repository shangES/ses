<%@ page contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page session="false"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.*"%>
<%@ page import="icbc.cmis.base.*" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ include file="../../util/header.jsp" %>
<cmis:sessionCheck>
<cmis:muidef def="icbc.cmis.flow.BA.flow_BA">
<html>

<%
String arraystr = "";
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");

String formflag = (String)context.getValueAt("formflag");	
java.util.Hashtable h_table=CmisConstance.getDictParam();
String titlecontent="";

Vector list = (Vector)context.getValueAt("contentlist"); 
      
%>

<head>
<title>
显示具体内容
</title>
<link rel="stylesheet" href="<%=baseWebPath%>/<cmis:muipub item="CSSPATH"/>/main.css" type="text/css">

<script language="JavaScript" >


function f_cancel() {
  window.returnValue = null;
  window.close();
}

</script></head>
<body leftmargin="0" topmargin="0" >

<cmis:framework align="center" valign="center">
 <cmis:tabpage width="0" height="0"> 
<cmis:tabpagebar  titleid="C000082" selected="true" /><!-- 调查人员分析意见 -->
  <cmis:tabpagecontent info="">
  <table width="100%" border="1" cellspacing="0" cellpadding="0">
   
      
    <%for (int i=0;i<list.size();i++){
      Hashtable htable = (Hashtable)list.get(i); %>
    <tr align="left"> 
        <td  class="td1"><cmis:muiitem item="C000083"/></td><!-- 一、生产经营及财务状况分析 -->
    </tr> <tr>
        <td><TEXTAREA NAME="" ROWS="4" COLS="80" readonly ><%=(String)htable.get("process012")%></TEXTAREA></td>  
    </tr> 
    <tr align="left">
        <td  class="td1"><cmis:muiitem item="C000084"/></td><!-- 二、客户现金流量分析和预测 -->
    </tr><tr>
	    <td><TEXTAREA NAME="" ROWS="4" COLS="80" readonly ><%=(String)htable.get("process013")%></TEXTAREA></td>  
	</tr>
	<tr align="left">
	    <td  class="td1"><cmis:muiitem item="C000085"/></td><!-- 三、担保情况分析 -->
	</tr><tr>
	    <td><TEXTAREA NAME="" ROWS="4" COLS="80" readonly ><%=(String)htable.get("process014")%></TEXTAREA></td>  
	</tr>
	<tr align="left">
	    <td  class="td1"><cmis:muiitem item="C000086"/></td><!-- 四、风险分析 -->
	</tr><tr>
	    <td><TEXTAREA NAME="" ROWS="4" COLS="80" readonly ><%=(String)htable.get("process015")%></TEXTAREA></td>  
	</tr>
	<tr align="left">
	    <td  class="td1"><cmis:muiitem item="C000087"/></td><!-- 五、收益分析 -->
	</tr><tr>
	    <td><TEXTAREA NAME="" ROWS="4" COLS="80" readonly ><%=(String)htable.get("process016")%></TEXTAREA></td>  
    </tr>
    <tr align="left">
	    <td  class="td1"><cmis:muiitem item="C000089"/></td><!-- 六、其他意见 -->
	</tr><tr>
	    <td><TEXTAREA NAME="" ROWS="4" COLS="80" readonly ><%=(String)htable.get("process028")%></TEXTAREA></td>  
    </tr>
    <tr align="left">
	    <td  class="td1"><cmis:muiitem item="C000088"/></td><!-- 七、调查人结论和意见 -->
	</tr><tr>
	    <td><TEXTAREA NAME="" ROWS="4" COLS="80" readonly ><%=(String)htable.get("process027")%></TEXTAREA></td>  
    </tr>
       <%}%>  
  
        
      
    
    <tr align="right"><td width="100%" COLSPAN="2" class="td1"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/close.gif" style="cursor: hand" onClick="f_cancel()"></td></tr>
  </table>
  </cmis:tabpagecontent> 
  </cmis:tabpage> </cmis:framework>
</body>
</html></cmis:muidef></cmis:sessionCheck>
