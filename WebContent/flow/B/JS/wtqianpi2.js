function wtqianpi2(){
	//意见输入
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

	//调用特别授权
	var ts = window.showModalDialog(form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.UK.UK_SpecialGrantListOp&opDataUnclear=true&opAction=query&kh_code="+form2.approve_entcode.value+"&yw_code="+form2.approve_tradecode.value+"&yw_type="+form2.approve_tradetype.value+"&newtime="+new Date(),window,"dialogWidth:700px;dialogHeight:400px;center:yes;help:no;status:no;resizable:no");
	
	if(ts==null){
		alert("没有选择特别授权");
		return;	
	}
	
	var usegrant = ts;
	//写特别授权
	xmlurl = form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BTN.wtqianpiOp&opDataUnclear=true&opAction=60001";
	xmlurl += "&grant_ent=" + form2.approve_entcode.value;
	xmlurl += "&grant_app=" + form2.approve_tradecode.value;
	xmlurl += "&grant_type=" + form2.approve_tradetype.value;
	xmlurl += "&grantnum=" + usegrant;
	xmlurl += "&newtime=" + new Date();
	objHTTP.Open('GET',encodeURL(xmlurl),false);
	objHTTP.Send();
	if(!parser.loadXML(objHTTP.responseText)){
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return;
	}
	error = parser.getElementsByTagName("error");
	if(error.length > 0) {
		alert("写特别授权使用记录出错："+error.item(0).text);
		return;
	}
	
	//作签批
	form2.qptype.value = "1";
	form2.operationName.value = "icbc.cmis.flow.BTN.wtqianpiOp";
	form2.opAction.value = "10002";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();
}