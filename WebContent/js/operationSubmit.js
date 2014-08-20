//**************************************************************
//  StruComboBox相关Js处理
//	Creation Date: (2004-05-22)	
//	@Author: ECC - renfl
//	@Version: 1.0
//**************************************************************

var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
var parser=new ActiveXObject("microsoft.xmldom");
parser.async = "false";

function DealOperHTTP(act){
    act = act + "&now=" + new Date();
	objHTTP.Open('Get', act, false);
	objHTTP.Send();
	// 查询交易调用是否出错处理
	if (parser.documentElement.tagName == "error") {
	  alert(parser.documentElement.text);
	  return false;
	}else{
	  if(objHTTP.responseText.indexOf('ok')==-1){
	    alert("删除机构出错！");
	    return false; 
	  }
	}
	return true;
}

function DealXMLHTTP(act) {
  act = act + "&now=" + new Date();
	objHTTP.Open('Get', act, false);
	objHTTP.Send();
	// 查询响应XML加载出错处理 
	if(!parser.loadXML(objHTTP.responseText)) {
		alert(parser.parseError.reason + "\n" + parser.parseError.line + "\n" + parser.parseError.srcText + "\n");
	  return false;
	}
	// 查询交易调用是否出错处理
	if (parser.documentElement.tagName == "error") {
	  alert(parser.documentElement.text);
	  return false;
	}
	return true;
}	