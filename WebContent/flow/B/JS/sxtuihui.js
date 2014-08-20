function sxtuihui(){
	if(form2.approve_isfirst.value=='1'){
		alert("当前是发起环节，不能进行退回操作");
		return;
	}
	if(!confirm("是否确认进行退回操作？")){
		return;
	}
	
	//意见输入校验
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	var xmlurl = form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BC.BC_inputcheckOp&opDataUnclear=true&opAction=10005";
	xmlurl += "&check_ent=" + form2.approve_entcode.value;
	xmlurl += "&check_app=" + form2.approve_tradecode.value;
	xmlurl += "&check_num=" + form2.approve_ordernum.value;
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
	
	//可否退回校验
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	var xmlurl = form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BC.BC_inputcheckOp&opDataUnclear=true&opAction=10007";
	xmlurl += "&check_ent=" + form2.approve_entcode.value;
	xmlurl += "&check_app=" + form2.approve_tradecode.value;
	xmlurl += "&check_num=" + form2.approve_ordernum.value;
	xmlurl += "&newtime=" + new Date();
	objHTTP.Open('GET',encodeURL(xmlurl),false);
	objHTTP.Send();
	if(!parser.loadXML(objHTTP.responseText)){
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return;
	}
	error = parser.getElementsByTagName("error");
	if(error.length > 0) {
		alert("授信额度已经修改，不能进行退回或否决操作！");
		return;
	}
	
	
	
	//做退回
	form2.operationName.value = "icbc.cmis.flow.BTN.sxtuihuiOp";
	form2.opAction.value = "10001";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();
}