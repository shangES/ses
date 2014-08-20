function doblyjfasong(){
	if(form2.approve_isfirst.value=='1'){
		alert("当前是发起环节，不能进行保留意见发送操作");
		return;
	}
	if(form1.iamsg2process017.value=="1"){
		//alert("机评意见为有控制，不能进行保留意见发送");
		//return;
		if(!confirm("系统提示信息为有刚性控制，是否继续")){
			return;
		}
	}
	if(form1.iamsg2process017.value=="-1"){
		if(!confirm("系统提示信息异常，是否继续")){
			return;
		}
	}
	
	

	//校验部分待
	if(isEmpty(form1.nextflow.value)){
		alert("请选择下一环节");
		return;
	}
	if(isEmpty(form1.nextareacode.value)){
		alert("请选择下一地区");
		return;
	}
	
	//校验意见输入
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
	
	//影像校验
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	var xmlurl = form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BC.BC_inputcheckOp&opDataUnclear=true&opAction=10004";
	xmlurl += "&check_ent=" + form2.approve_entcode.value;
	xmlurl += "&check_app=" + form2.approve_tradecode.value;
	xmlurl += "&newtime=" + new Date();
	objHTTP.Open('GET',encodeURL(xmlurl),false);
	objHTTP.Send();
	if(!parser.loadXML(objHTTP.responseText)){
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return;
	}
	imgstr = parser.getElementsByTagName("imgstr");
	if(imgstr.length > 0) {
		if(!confirm("当前业务存在未扫描或未结卷影像，是否继续？\n未扫描或未结卷影像流水号："+imgstr(0).text)){
			return;
		}
	}	
	error = parser.getElementsByTagName("error");
	if(error.length > 0) {
		alert(error.item(0).text);
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

	form2.operationName.value = "icbc.cmis.flow.BTN.fasongOp";
	form2.opAction.value = "10002";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();
}