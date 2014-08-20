<%@ page contentType="text/html;charset=GBK"%>
<%@ page session="false"%>
<%@ page import="icbc.cmis.base.*" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");
%>
<html>
<head>
<title>
ok
</title>
<link rel="stylesheet" href="<%=baseWebPath%>/css/main.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<body>
<div align="center">
<cmis:tabpage width = "300" height="200">
<cmis:tabpagebar title = "成功" selected="true" />
<cmis:tabpagecontent>
<h2><%= (String)context.getValueAt("okTitle") %></h2>
<%= (String)context.getValueAt("okMsg") %>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p align="right"><a><img  border="0" src="<%=baseWebPath%>/images/return.gif" width="48" height="24"  onclick="
<%
  String ret = (String)context.getValueAt("okReturn");
  if(ret != null && ret.length() > 0) {
//    String baseWebPath = null;
//    try{
//      baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath");
//    }catch(Exception e){
//      baseWebPath = null;
//    }
    if(baseWebPath != null && baseWebPath.trim().length() >1){
      baseWebPath = baseWebPath.trim();
      ret = ret.trim();
      if(ret.startsWith(baseWebPath)){
        ret = ret.substring(baseWebPath.length());
      }
      if(ret.startsWith("/servlet")){
        ret = baseWebPath + ret;
      }
    }
    out.print(ret);
  } else {
    out.print("javascript:history.back();");
  }
%>"></a></p>
</cmis:tabpagecontent>
</cmis:tabpage>
</div>
<jsp:include page="/util/footer.jsp" flush="true" />
</body>
</html>