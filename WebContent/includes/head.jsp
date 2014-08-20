<%@ page import="com.icbc.cte.base.*" %>
<%@ taglib uri="CTPTLD" prefix="ctp" %>
<%
String sysid = "0";
String path=CTEConstance.getAppFullWebPathBeforeLogon(sysid);
boolean isTimeout = false;
%>
<jsp:useBean id="utb" scope="page" class="com.icbc.cte.cs.html.CTEJspContextServices">
<%
   try{
		utb.initialize(request);
   }catch(Exception ex){
   System.out.println("*****utb init error");
   isTimeout = true;
		//response.sendRedirect("../sesserror.jsp");
		//return;
   }
%>
</jsp:useBean>

<%
	String sessionId = null;
	///////////////////////////////////////////////////////////////////
	/*
	 * 修改原因：由于Context类的定义存在icbc.cmis.base.Context和
	 *			 com.icbc.cte.base.Context两种定义，为了防止在页面中产生
	 			 二意性，故在此进行完整的声明。
	 * 修改日期：2004-07-08
	 * 修改人：杨光润
	 */
	//Context sessionCtx = null;
	//Context context = null;
	com.icbc.cte.base.Context sessionCtx = null;
	com.icbc.cte.base.Context context=null;
	///////////////////////////////////////////////////////////////////
	String basePath = null;
	String exPathInfo = null;
	String fullPath = null;
	String internation = null;
if(!isTimeout){
	sessionId = utb.getSessionId();
	sessionCtx = utb.getSessionContext();
 	if(sessionId == null || sessionCtx == null){
		isTimeout = true;
	}
}
if(!isTimeout){
	context = utb.getContext();
	basePath = utb.getBaseWebPath();

	exPathInfo = utb.getExtraPathInfo();
	fullPath = utb.getAppFullWebPath(sessionId);
	internation = (String)sessionCtx.getValueAt("Language");
}
%>
<%
if(isTimeout){
%>
 <form NAME="timeoutfmt" METHOD="post" action="<%=path%>sesserror.jsp"  target="_top">
 </form>
<%
out.println("<script>");
out.println("timeoutfmt.submit();");
out.println("</script>");
return;
}
%>
