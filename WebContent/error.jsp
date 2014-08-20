<%@ page contentType="text/html;charset=GBK"%>
<%@ page session="false"%>
<%@ page import="icbc.cmis.base.KeyedDataCollection" %>
<%@ page import="icbc.cmis.base.IndexedDataCollection" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>
<%@ include file="util/header.jsp"%>
<%
	//英文变量
	String eng_title ="";                       //页标题
	String eng_tabPageBar = "";                 //tab标题
	String eng_mainMsg = "";                    //操作失败
	String eng_errCode = "";                    //错误编码
	String eng_errDes ="";                      //错误描述
	String eng_errLocation ="";                 //错误位置
	String eng_content="";                      //错误内容
	String eng_itemInfo = "";                   //未知错误	
	
	String com_title ="";                       
	String com_tabPageBar = "";                
	String com_mainMsg = "";                    
	String com_errCode = "";                    
	String com_errDes ="";                      
	String com_errLocation ="";                 
	String com_content="";                      
	String com_itemInfo = "";                  
	String com_images = "";                   
	
	//String tag = "1"                            //标志位1表示取得到session；0取不到session 
	try {     
	  HttpSession session = ((HttpServletRequest)pageContext.getRequest()).getSession(false);
      KeyedDataCollection k = (KeyedDataCollection)session.getAttribute("sessionKCData");
      String ret = (String)k.getValueAt("LangCode");
      icbc.cmis.tags.PropertyResourceReader propertyResourceReader=new icbc.cmis.tags.PropertyResourceReader(ret,"icbc.cmis.ok_util_info");
      com_title = propertyResourceReader.getPrivateStr("C000004");                   
	  com_tabPageBar = propertyResourceReader.getPrivateStr("C000005");     
	  com_mainMsg = propertyResourceReader.getPrivateStr("C000006");        
	  com_errCode =propertyResourceReader.getPrivateStr("C000007");         
	  com_errDes =propertyResourceReader.getPrivateStr("C000008");                
	  com_errLocation =propertyResourceReader.getPrivateStr("C000009");              
	  com_content=propertyResourceReader.getPrivateStr("C000010");           
	  com_itemInfo = propertyResourceReader.getPrivateStr("C000011");
	  com_images = propertyResourceReader.getPublicStr("IMAGEPATH");          
    }
    catch (Exception eee) {
     
	  icbc.cmis.tags.PropertyResourceReader propertyResourceReader=new icbc.cmis.tags.PropertyResourceReader("zh_CN","icbc.cmis.ok_util_info");
      com_title =propertyResourceReader.getPrivateStr("C000004");                   
	  com_tabPageBar = propertyResourceReader.getPrivateStr("C000005");     
	  com_mainMsg = propertyResourceReader.getPrivateStr("C000006");        
	  com_errCode =propertyResourceReader.getPrivateStr("C000007");         
	  com_errDes =propertyResourceReader.getPrivateStr("C000008");                
	  com_errLocation =propertyResourceReader.getPrivateStr("C000009");              
	  com_content=propertyResourceReader.getPrivateStr("C000010");           
	  com_itemInfo = propertyResourceReader.getPrivateStr("C000011"); 
	  com_images = propertyResourceReader.getPublicStr("IMAGEPATH");      
	  
	  propertyResourceReader=new icbc.cmis.tags.PropertyResourceReader("en_US","icbc.cmis.ok_util_info");
	  eng_title =propertyResourceReader.getPrivateStr("C000004");                   
	  eng_tabPageBar = propertyResourceReader.getPrivateStr("C000005");     
	  eng_mainMsg = propertyResourceReader.getPrivateStr("C000006");        
	  eng_errCode =propertyResourceReader.getPrivateStr("C000007");         
	  eng_errDes =propertyResourceReader.getPrivateStr("C000008");                
	  eng_errLocation =propertyResourceReader.getPrivateStr("C000009");              
	  eng_content=propertyResourceReader.getPrivateStr("C000010");           
	  eng_itemInfo = propertyResourceReader.getPrivateStr("C000011");
	   
	  com_title = com_title + "("  + eng_title + ")";
	  com_tabPageBar = com_tabPageBar + "("  + eng_tabPageBar + ")"; 	
	  com_mainMsg = com_mainMsg + "("  + eng_mainMsg + ")"; 	
	  com_errCode = com_errCode + "("  + eng_errCode + ")"; 	
	  com_errDes = com_errDes + "("  + eng_errDes + ")"; 	
	  com_errLocation = com_errLocation + "("  + eng_errLocation + ")"; 		 
	  com_content = com_content + "("  + eng_content + ")"; 	
	  com_itemInfo = com_itemInfo + "("  + eng_itemInfo + ")"; 	 	
    }
	KeyedDataCollection context = (KeyedDataCollection)request.getAttribute("operationData");
	//icbc.cmis.tags.PropertyResourceReader propertyResourceReader=new icbc.cmis.tags.PropertyResourceReader(pageContext,"icbc.cmis.ok_util_info");
   // String itemInfo = propertyResourceReader.getPrivateStr("C000011");
   
%>
<html>
<head>
<title>
<%=com_title%>
</title>
<link rel="stylesheet" href="<%=baseWebPath%>/<cmis:muipub item="CSSPATH"/>/main.css" type="text/css">
</head>
<script language="javaScript">
	function showMsg(){
	 if(document.all.errorDetialMsg.style.display==""){
	 	document.all.errorDetialMsg.style.display="none";
	 	document.all.btn.innerHTML=">>>>>>>...";
	 }else{
	 	document.all.errorDetialMsg.style.display="";
	 	document.all.btn.innerHTML="<<<<<<<...";
	 }
	}
</script>
<body>
<div align="center">
<cmis:tabpage width = "500" height="300">
<cmis:tabpagebar title = "<%=com_tabPageBar%>" selected="true" />
<cmis:tabpagecontent>
<table width='100%' border='0' cellspacing='0' cellpadding='0'>
  <tr>
      <td width='20%'><img src='<%=baseWebPath%>/<%=com_images%>/stop.gif' width='80' height='80'></td>
      <td width='80%'>
        <h2><br>
          <%=com_mainMsg%></h2>

<%
  String errorCode = "";
  String errorLocat = "";
  String errorMsg  = "";
  String errorDispMsg  = "";
  if(context != null){
 	 try{
  		errorCode = (String)context.getValueAt("tranErrorCode");
		errorLocat = (String)context.getValueAt("tranErrorLocation");
		errorMsg = (String)context.getValueAt("tranErrorMsg");
		errorDispMsg = (String)context.getValueAt("tranErrorDispMsg");
  	}catch(Exception e){
  		errorCode = (String)request.getAttribute("tranErrorCode");
		errorDispMsg = (String)request.getAttribute("tranErrorDispMsg");
		errorLocat = (String)request.getAttribute("tranErrorLocation");
		errorMsg = (String)request.getAttribute("tranErrorMsg");
  	}

  }else{
  	errorCode = (String)request.getAttribute("tranErrorCode");
	errorDispMsg = (String)request.getAttribute("tranErrorDispMsg");
	errorLocat = (String)request.getAttribute("tranErrorLocation");
	errorMsg = (String)request.getAttribute("tranErrorMsg");

  }
  if(errorCode ==null) errorCode=com_itemInfo;
  if(errorDispMsg ==null) errorDispMsg=com_itemInfo;
  if(errorLocat ==null) errorLocat=com_itemInfo;
  if(errorMsg ==null) errorMsg=com_itemInfo;
errorMsg = errorMsg.trim();


%>
         <%=com_errCode%><%=errorCode%><br>
         <%=com_errDes%><%=errorDispMsg%>
          </td>
          </tr>
          <tr><td></td>
          <br>
          <td align="right">
          <a href="#"><label id="btn" onclick="showMsg()">>>>>>>...</label></a>
          </td>
          </tr>
          <tr><td></td>
          <td>

          <label id="errorDetialMsg" style="display:none">
          	<%=com_errLocation%><%=errorLocat%><BR>
          	<%=com_content%><%=errorMsg%>
          </label>
    </td>
  </tr>
  <tr>
    <td colspan="2">
        <div align="right">
          <p><br>
            <br>
            <br>
            <br>
            <br>
          </p>
        <p>
<% if (errorCode != null && errorCode.equals("xdtz22118")) {%>
<a href="<%=baseWebPath%>/login.jsp" target="_top"><img  border="0" src="<%=baseWebPath%>/<%=com_images%>/login.gif" height="24" border="0"></a>&nbsp;&nbsp;&nbsp;<br><br></p>
<% } else { %>
<a><img  border="0" src="<%=baseWebPath%>/<%=com_images%>/return.gif" height="24"  onclick="javascript:history.back();"></a>&nbsp;&nbsp;&nbsp;<br><br></p>
<% } %>
      </div>
    </td>
  </tr>
</table>
</cmis:tabpagecontent>
</cmis:tabpage>
</div>
<jsp:include page="/util/footer.jsp" flush="true" />
</body>
</html>