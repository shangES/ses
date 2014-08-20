function dotbsqqianpi(){
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

	//去使用特别授权
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";

	var url = form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BC.BC_UseGrantOp&opDataUnclear=true&opAction=querygrant"+"&entcode="+form2.approve_entcode.value+"&tradecode="+encode(form2.approve_tradecode.value)+"&stoparea="+form1.o_stoparea.value+"&tradetype="+form2.approve_tradetype.value+"&employeecode="+form2.employeecode.value+"&empareacode="+form2.empareacode.value+"&time="+new Date();
	var ts=window.showModalDialog(url,window,"dialogWidth:600px;dialogHeight:500px;center:yes;help:no;status:no;resizable:no");
	if(ts!=null){
		//写入mag_special_grant_use表
		if(ts[0]=="" && ts[1]==""){
			alert("没有选择特别授权");
			return;
		}		
		
		if(ts[0]!=""){
			var xmlurl = form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BC.BC_UseGrantOp&opDataUnclear=true&opAction=savegrant&grantnum="+ts[0]+"&entcode="+form2.approve_entcode.value+"&tradecode="+form2.approve_tradecode.value+"&money="+ts[2];
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
		}		
		if(ts[1]!=""){
			xmlurl = form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BC.BC_UseGrantOp&opDataUnclear=true&opAction=savegrant&grantnum="+ts[1]+"&entcode="+form2.approve_entcode.value+"&tradecode="+form2.approve_tradecode.value+"&money="+ts[3];
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
		}
	} else {
		alert("没有选择特别授权");
		return;
	}

	form2.qptype.value = "1";
	form2.operationName.value = "icbc.cmis.flow.BTN.qianpiOp";
	form2.opAction.value = "10002";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();
}