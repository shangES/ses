<!--调查分析意见输入-->
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

KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");

String entcode = (String)context.getValueAt("entcode");	  //－客户号
String tradecode = (String)context.getValueAt("tradecode");	//业务申请号
String ordernum = (String)context.getValueAt("ordernum");	//当前环节序号
String ordercode = (String)context.getValueAt("ordercode");	//当前环节代码
java.util.Hashtable h_table=CmisConstance.getDictParam();
Vector list = (Vector)context.getValueAt("contentlist"); 
      
%>

<head>
<title>调查分析意见输入</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" href="<%=baseWebPath%>/css/main.css" type="text/css">

<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/data_check.js"></script>
<script src="<%=baseWebPath%>/<cmis:muipub item="JSPATH"/>/liball.js"></script>
<script src="<%=baseWebPath%>/flow/<cmis:muipub item="JSPATH"/>/checkSyntax.js"></script>
<script language="JavaScript" >
function save()
{
 var ts=new Array(7);
 ts[0]=form1.process012.value;
 ts[1]=form1.process013.value;
 ts[2]=form1.process014.value;
 ts[3]=form1.process015.value;
 ts[4]=form1.process016.value;
 ts[5]=form1.process027.value;
 ts[6]=form1.process028.value;
   window.returnValue = ts;
   window.close();  
}

function f_cancel() {
  window.returnValue = null;
  window.close();
}

</script></head>
<body leftmargin="0" topmargin="0">
<form name="form1" method="post" action="<%=baseWebPath%>/servlet/icbc.cmis.servlets.CmisReqServlet">
	<input type=hidden name="operationName" value="icbc.cmis.flow.util.util_content_msg2">
	<input type=hidden name="opAction" value="">
<div align="center"> <cmis:tabpage width="0" height="0"> 
<cmis:tabpagebar  titleid="C000082" selected="true" /><!--调查人员分析意见-->
  <cmis:tabpagecontent info="">

	<input type="hidden" name="entcode" value="<%=entcode%>">
	<input type="hidden" name="tradecode" value="<%=tradecode%>">
	<input type="hidden" name="ordernum" value="<%=ordernum%>">
	<input type="hidden" name="ordercode" value="<%=ordercode%>">  
  <table width="100%" border="1" cellspacing="0" cellpadding="1">
    
    <%for (int i=0;i<list.size();i++){
      Hashtable htable = (Hashtable)list.get(i); %>
    <tr> 
        <td  class="td1"><cmis:muiitem item="C000083"/><cmis:muiitem item="C000090"/><!-- 一、生产经营及财务状况分析(最多输入500个汉字) --></td>
        </tr><tr>
        <td><TEXTAREA NAME="process012" ROWS="4" COLS="80" onblur="javascript:checkAreaLength(this,1000);javascript:checkSyntax(this);" ><%=(String)htable.get("process012")%></TEXTAREA></td>  
    </tr> 
    <tr >
        <td  class="td1"><cmis:muiitem item="C000084"/><cmis:muiitem item="C000090"/><!-- 二、客户现金流量分析和预测(最多输入500个汉字) --></td>  
        </tr><tr>
	    <td><TEXTAREA NAME="process013" ROWS="4" COLS="80" onblur="javascript:checkAreaLength(this,1000);javascript:checkSyntax(this);" ><%=(String)htable.get("process013")%></TEXTAREA></td>  
	</tr>
	<tr>
	    <td  class="td1" ><cmis:muiitem item="C000085"/><cmis:muiitem item="C000090"/><!-- 三、担保情况分析(最多输入500个汉字) --></td>
	    </tr><tr>  
	    <td><TEXTAREA NAME="process014" ROWS="4" COLS="80" onblur="javascript:checkAreaLength(this,1000);javascript:checkSyntax(this);" ><%=(String)htable.get("process014")%></TEXTAREA></td>  
	</tr>
	<tr>
	    <td  class="td1"><cmis:muiitem item="C000086"/><cmis:muiitem item="C000090"/><!-- 四、风险分析(最多输入500个汉字) --></td> 
	    </tr><tr> 
	    <td><TEXTAREA NAME="process015" ROWS="4" COLS="80" onblur="javascript:checkAreaLength(this,1000);javascript:checkSyntax(this);" ><%=(String)htable.get("process015")%></TEXTAREA></td>  
	</tr>
	<tr>
	    <td  class="td1"><cmis:muiitem item="C000087"/><cmis:muiitem item="C000090"/><!-- 五、收益分析(最多输入500个汉字) --></td>
	    </tr><tr>  
	    <td><TEXTAREA NAME="process016" ROWS="4" COLS="80" onblur="javascript:checkAreaLength(this,1000);javascript:checkSyntax(this);" ><%=(String)htable.get("process016")%></TEXTAREA></td>  
    </tr>
	<tr>
	    <td  class="td1"><cmis:muiitem item="C000089"/><cmis:muiitem item="C000090"/><!-- 六、其他意见(最多输入500个汉字) --></td>
	    </tr><tr>  
	    <td><TEXTAREA NAME="process028" ROWS="4" COLS="80" onblur="javascript:checkAreaLength(this,1000);javascript:checkSyntax(this);" ><%=(String)htable.get("process028")%></TEXTAREA></td>  
    </tr>
    <tr>
	    <td  class="td1"><cmis:muiitem item="C000088"/><cmis:muiitem item="C000090"/><!-- 七、调查人结论和意见(最多输入500个汉字) --></td> 
	    </tr><tr> 
	    <td><TEXTAREA NAME="process027" ROWS="4" COLS="80" onblur="javascript:checkAreaLength(this,1000);javascript:checkSyntax(this);" ><%=(String)htable.get("process027")%></TEXTAREA></td>  
	</tr>
       <%}%>  
  
        
      
    
    <tr align="right"><td width="100%" COLSPAN="2" class="td1" height="35"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/confirm.gif" style="cursor: hand" onClick="save()"><img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/close.gif" style="cursor: hand" onClick="f_cancel()"></td></tr>
  </table>
  </cmis:tabpagecontent> 
  </cmis:tabpage> </div>
  </form>
</body>
</html></cmis:muidef></cmis:sessionCheck>
