// deleteRequisition.js

// 根据传入的企业代码,新的业务代码,原先的业务代码，业务号删除业务对照表中的相关记录
function DeleteSubmit(sEnpCode,newsOpCode,oldsOpCode,sReqCode) {
     var objHTTP = new ActiveXObject("Microsoft.XMLHTTP")
      var parser=new ActiveXObject("microsoft.xmldom")
      parser.async="false"
      var act = basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.IA.IA_DelRequisitionOp&opDataUnclear=true&xmlOutput=true&opAction=40001&sEnpCode="+sEnpCode+"&newsOpCode="+newsOpCode+"&oldsOpCode="+oldsOpCode+"&sReqCode="+sReqCode;
      objHTTP.Open('GET',act,false);
      objHTTP.Send();
      //查询出错处理
      if(!parser.loadXML(objHTTP.responseText)) {
        alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
        return 0;
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
          return 0;
      }
      var returnInt=  rootElement.getAttribute("rertunInt");
      return  returnInt;
}
