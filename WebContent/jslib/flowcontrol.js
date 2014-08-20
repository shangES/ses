//流程审批控制信息
/*
str1:客户代码
str2:业务号
str3:环节
str4:申请种类
*/
function flowinfo(str1,str2,str3,str4) {
  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
  var parser=new ActiveXObject("microsoft.xmldom");
  parser.async="false";
  var url=basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.CA.CA_ApproveOp&opDataUnclear=true&opAction=checkapply";
  url += "&str1=" + str1;
  url += "&str2=" + str2;
  url += "&str3=" + str3;
  url += "&str4=" + str4;
  url+= "&newtime=" + new Date();
  objHTTP.Open('GET',url,false);
  objHTTP.Send();
  if(!parser.loadXML(objHTTP.responseText)){
	alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
  }
  error = parser.getElementsByTagName("error");//错误，不能进入
  if(error.length > 0) {
	alert(error.item(0).text);
	return false;
  }
  hint = parser.getElementsByTagName("hint");//只给提示
  if(hint.length > 0) {
	//alert(hint.item(0).text);
	if(window.confirm(hint.item(0).text))return true;
	else return false;
  }
  return true;
}

