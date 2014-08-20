function sxfasong(){
	if(isEmpty(form1.nextflow.value)){
		alert("请选择下一环节");
		return;
	}
	if(isEmpty(form1.nextareacode.value)){
		alert("请选择下一地区");
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
	
	
	
	
	
	//发送
	var tmp_nextflow = form1.nextflow.value.split('|');
	form2.nextflow.value = tmp_nextflow[0];
	form2.nextflowname.value = tmp_nextflow[1];
	//alert(form2.nextflow.value);
	
	var temp_nextemp = form1.nextareacode.value.split('|');
	form2.nextareacode.value = temp_nextemp[0];
	form2.nextareaname.value = temp_nextemp[1];
	//alert(form2.nextareacode.value);
	
	form2.nextemployeecode.value = form1.nextemployeecode.value;
	form2.nextemployeename.value = form1.nextemployeename.value;

	form2.operationName.value = "icbc.cmis.flow.BTN.sxfasongOp";
	form2.opAction.value = "10001";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();
}