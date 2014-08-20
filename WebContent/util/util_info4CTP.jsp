<%@ page contentType="text/html;charset=GBK"%>
<%@ page session="false"%>
<%@ page import="icbc.cmis.base.*" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ include file="/includes/head.jsp" %>
 
<html>
<head>
<title>
交易处理提示信息
</title>
<link rel="stylesheet" href="<%=fullPath%>css/main.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<body>
<div align="center">
<cmis:tabpage width="500" height="300">
<cmis:tabpagebar title = "提示" selected="true" />
<cmis:tabpagecontent>
<h2><%= (String)context.getValueAt("infoTitle") %></h2>
<%= (String)context.getValueAt("infoMsg") %>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<%
	String ret = (String)context.getValueAt("infoReturn");
	String url = null;
	String parm = null;
	String opName = null;
	if(ret != null && ret.length() > 0 && ret.indexOf("javascript")==-1) {
		java.util.StringTokenizer tokenizer=new java.util.StringTokenizer(ret,"?");
		if(tokenizer.hasMoreTokens()){
			String temp=tokenizer.nextToken();
			if(temp.indexOf(".jsp") != -1)
				url=fullPath+temp;
			else{
				url=fullPath+utb.getRequestServletUrl();
				opName=temp;
			}
			if(tokenizer.hasMoreTokens()){
				parm=tokenizer.nextToken();
			}
		}
		StringBuffer formStr = new StringBuffer("\n");
		String actionStr = "";
		
		formStr.append("<form name=\"returnForm\" method=\"POST\" action=\""+url+"\">\n");
		if(parm != null && parm.length() > 0){
			java.util.StringTokenizer toke = new java.util.StringTokenizer(parm,"&");
			for(;toke.hasMoreElements();){
				String tmp = (String)toke.nextElement();
				int index = tmp.indexOf("=");
				String tmpName = (String)tmp.substring(0,index);
				String tmpValue = (String)tmp.substring(index+1,tmp.length());
				if(tmpValue == null) tmpValue = "";
				formStr.append("<input type=\"hidden\" name=\""+tmpName+"\" value=\""+tmpValue+"\"/>\n");
			}
		}
		if(opName!=null)
			formStr.append(utb.getRequiredHtmlFields(null,opName));
		formStr.append("</form>");
		out.println(new String(formStr));
%>
	<p align="right"><a><img  border="0" src="<%=fullPath%>images/return.gif" width="48" height="24"  onclick="javascript:returnForm.submit();"></a></p>
<%	
	}else{	
%>
	<p align="right"><img class="hand"  border="0" src="<%=fullPath%>images/return.gif" width="48" height="24"  onClick="<%=ret%>"></p>
<%
	}
%>

</cmis:tabpagecontent>
</cmis:tabpage>
</div>
<jsp:include page="/util/footer.jsp" flush="true" />
</body>
</html>