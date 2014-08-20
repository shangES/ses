function dozrqianpi(){

	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	var xmlurl = form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BC.BC_inputcheckOp&opDataUnclear=true&opAction=10001";
	xmlurl += "&chk_entcode=" + form2.approve_entcode.value;
	xmlurl += "&chk_tradecode=" + form2.approve_tradecode.value;
	xmlurl += "&chk_ordernum=" + form2.approve_ordernum.value;
	xmlurl += "&chk_isfirst=" + form2.approve_isfirst.value;
	xmlurl += "&newtime=" + new Date();
	objHTTP.Open('GET',encodeURL(xmlurl),false);
	objHTTP.Send();
	if(!parser.loadXML(objHTTP.responseText)){
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return;
	}
	error = parser.getElementsByTagName("error");
	if(error.length > 0) {
		alert(error.item(0).text);
		return;
	}

	if(form1.iamsg2process017.value==""){
		alert("请生成系统提示信息");
		return;
	}
	//if(form1.iamsg2process017.value=="-1"){
	//	alert("机评意见生成异常，暂时不能签批");
	//	return;
	//}


	form2.qptype.value = "2";
	form2.operationName.value = "icbc.cmis.flow.BTN.qianpiOp";
	form2.opAction.value = "10003";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();
}