<%@ page contentType="text/html;charset=GBK"%>
<%@ page session="false"%>
<%@ page import="icbc.cmis.base.*" %>
<%@ page import="java.util.Vector" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ include file="util/header.jsp"%>
<%
KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");
Vector paras = new Vector();
if (context.isElementExist("okParameters")) {
  paras = (Vector)context.getValueAt("okParameters");
}
String otherParameters = "";
if(!paras.isEmpty()) {
  for (int i = 0; i < paras.size(); i++) {
    String[] row = (String[])paras.get(i);
    otherParameters += ("<input type='hidden' name='" + row[0] + "' value='" + row[1] + "'>");
  }
}

%>
<cmis:sessionCheck>
<cmis:muidef def="icbc.cmis.ok_util_info">
<html>
<head>
<title>
ok
</title>
<link rel="stylesheet" href="<%=baseWebPath%>/<cmis:muipub item="CSSPATH"/>/main.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<script language="javascript">
var flag = true;
function f_submit() {
  if(flag) {
    flag = false;
    form1.submit();
  }
}
</script>
<body>
<div align="center">
<cmis:tabpage width = "500" height="300">
<cmis:tabpagebar titleid = "C000001" selected="true" />
<cmis:tabpagecontent>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
    <tr>
      <td width="18%" valign="top"><br>
        <img src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/ok.gif" width="80" height="80"></td>
      <td width="82%">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
          <tr>
            <td valign="top"><br>

              <h2><%= (String)context.getValueAt("okTitle") %></h2>
				<%= (String)context.getValueAt("okMsg") %></td>
  </tr>
  <tr>
  <td align="right" valign="bottom">
<%
  String ret = (String)context.getValueAt("okReturn");
  if(ret != null && ret.length() > 0) {
  //  String baseWebPath = null;
 //   try{
  //    baseWebPath = (String)CmisConstance.getParameterSettings().get("webBasePath");
 //   }catch(Exception e){
 //     baseWebPath = null;
 //   }
    if(baseWebPath != null && baseWebPath.trim().length() >1){
      baseWebPath = baseWebPath.trim();
      ret = ret.trim();
      if(ret.startsWith("/servlet")){
        ret = baseWebPath + ret;
      }
    }
		StringBuffer formStr = new StringBuffer("\n");
		String actionStr = "";
		int idx = ret.indexOf("?");
		if(idx != -1){
			formStr.append("<form name=\"form1\" method=\"POST\" action=\""+ret.substring(0,idx)+"\">\n");
			String tmpStr = ret.substring(idx+1,ret.length());
			if(tmpStr != null){
				java.util.StringTokenizer toke = new java.util.StringTokenizer(tmpStr,"&");
				for(;toke.hasMoreElements();){
					String tmp = (String)toke.nextElement();
					int index = tmp.indexOf("=");
					String tmpName = (String)tmp.substring(0,index);
					String tmpValue = (String)tmp.substring(index+1,tmp.length());
					if(tmpValue == null) tmpValue = "";
					formStr.append("<input type=\"hidden\" name=\""+tmpName+"\" value=\""+tmpValue+"\"/>\n");
				}
			}
      formStr.append(otherParameters);
			formStr.append("</form>");
		}else{
			formStr.append("<form name=\"form1\" method=\"POST\" action=\""+ret+"\">\n");
      formStr.append(otherParameters);
			formStr.append("</form>");
		}
	out.println(new String(formStr));
%>
<a href="javascript:f_submit();"><img  border="0" src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/return.gif"  height="24"></a>
<%
  } else {
%>
<a><img  border="0" src="<%=baseWebPath%>/<cmis:muipub item="IMAGEPATH"/>/return.gif"  height="24"  onClick="javascript:history.back();"></a>
<%
  }
%>
&nbsp;&nbsp;&nbsp;<br>
<br>
            </td>
  </tr>
</table>
</td>
  </tr>
</table>
</cmis:tabpagecontent>
</cmis:tabpage>
</div>

<jsp:include page="/util/footer.jsp" flush="true" />
</body>
</html>
</cmis:muidef>
</cmis:sessionCheck>