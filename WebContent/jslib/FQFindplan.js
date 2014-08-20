

function findplan(customercode,jjcode){

  	        var act =basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName="
  	         +"icbc.cmis.FQ.FQCentPeriodPayOp&opDataUnclear=true&xmlOutput=true&opAction=20004"
  	         +"&customercode="+customercode+"&jjcode="+jjcode+"&time="+(new Date);         
        objHTTP.Open('GET',act,false);
        objHTTP.Send();
         
        
        //查询出错处理
        if(!parser.loadXML(objHTTP.responseText)) {
          alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
          return false;
        }
        
        //如果session超时和交易失败，显示错误信息
        rootElement = parser.documentElement;
        if (rootElement.tagName == "error") {
	          errorCode = "";
	          errorMsg = "";
	          for (i=0; i<rootElement.childNodes.length; i++) {
	              if (rootElement.childNodes.item(i).tagName == "errorCode") {
	                   errorCode = rootElement.childNodes.item(i).text;
	              }
	              else if (rootElement.childNodes.item(i).tagName == "errorDispMsg") {
	                  errorMsg = rootElement.childNodes.item(i).text;
	              }
	          }
	          alert("错误代码：" + errorCode + "\n错误描述：" + errorMsg);
	          return false;
        }
        //其他错误
        error = parser.getElementsByTagName("error");
        if(error.length > 0) {
           alert(error.item(0).text);
           return false;
        }
     
     var num = parser.documentElement.getAttribute("num");
     
     if(num==0)
     
     return false;
     
     
     else
     
     return true;

}