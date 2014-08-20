function checkappstat( check_entcode, check_appcode, check_appkind ){
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
    var url = basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BC.BC_inputcheckOp&opDataUnclear=true&opAction=10002";
    url +="&check_ent=" + check_entcode;
    url +="&check_app=" + check_appcode;
    url +="&check_kind=" + check_appkind;
	url +="&newtime=" + new Date();
	objHTTP.Open('GET',encodeURL(url),false);
	objHTTP.Send();
	if(!parser.loadXML(objHTTP.responseText)){
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return false;
	}
	error = parser.getElementsByTagName("error");
	if(error.length > 0) {
		alert(error.item(0).text);
		return false;
	}
	info = parser.getElementsByTagName("info");
	if(info.length > 0){
		var stat =info.item(0).text;
		if(stat == "3"){
			alert("当前业务已经进入审批流程，不能删除或修改");
			return false;
		} else {
			return true;
		}
	}
}