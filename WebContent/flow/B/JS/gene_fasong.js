function gene_fasong(){
	if(isEmpty(form1.nextflow.value)){
		alert("请选择下一环节");
		return;
	}
	if(isEmpty(form1.nextareacode.value)){
		alert("请选择下一地区");
		return;
	}
	
	if(!confirm("是否确认发送当前业务？")){
		return;
	}

	var tmp_nextflow = form1.nextflow.value.split('|');
	form2.nextflow.value = tmp_nextflow[0];
	form2.nextflowname.value = tmp_nextflow[1];
	var temp_nextemp = form1.nextareacode.value.split('|');
	form2.nextareacode.value = temp_nextemp[0];
	form2.nextareaname.value = temp_nextemp[1];
	
	form2.nextemployeecode.value = form1.nextemployeecode.value;
	form2.nextemployeename.value = form1.nextemployeename.value;
	
	//通用的能否发送校验
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	var url = form2.baseWebPath.value + "/servlet/icbc.cmis.servlets.CmisReqServlet";
	var parameter= "operationName=icbc.cmis.flow.BTN.gene_fasongOp&opDataUnclear=true&opAction=10000";
	parameter += "&employeecode="      + form2.employeecode.value;
	parameter += "&empareacode="       + form2.empareacode.value;
	parameter += "&approve_entcode="   + form2.approve_entcode.value;
	parameter += "&approve_tradecode=" + form2.approve_tradecode.value;
	parameter += "&approve_tradetype=" + form2.approve_tradetype.value;
	parameter += "&approve_flowtype="  + form2.approve_flowtype.value;
	parameter += "&approve_ordernum="  + form2.approve_ordernum.value;
	parameter += "&approve_ordercode=" + form2.approve_ordercode.value;
	parameter += "&approve_busitype="  + form2.approve_busitype.value;
	parameter += "&nextflow="          + form2.nextflow.value;
	parameter += "&nextareacode="      + form2.nextareacode.value;
	parameter += "&nextemployeecode="  + form2.nextemployeecode.value;
	parameter += "&newtime=" + new Date();
	objHTTP.Open('GET',encodeURL(url),false);
	objHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	objHTTP.Send(encode(parameter));
	if(!parser.loadXML(objHTTP.responseText)){
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return;
	}
	error = parser.getElementsByTagName("error");
	if(error.length > 0) {
		alert(error.item(0).text);
		return;
	}
	
	form2.operationName.value = "icbc.cmis.flow.BTN.gene_fasongOp";
	form2.opAction.value = "10001";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();
}