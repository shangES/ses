function gene_foujue(){
	if(form2.approve_isfirst.value=='1'){
		alert("当前是发起环节，不能进行否决操作");
		return;
	}
	if(!confirm("是否确认否决当前业务？")){
		return;
	}
	
	//通用的能否否决校验
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	var url = form2.baseWebPath.value + "/servlet/icbc.cmis.servlets.CmisReqServlet";
	var parameter= "operationName=icbc.cmis.flow.BTN.gene_foujueOp&opDataUnclear=true&opAction=10000";
	parameter += "&employeecode="      + form2.employeecode.value;
	parameter += "&empareacode="       + form2.empareacode.value;
	parameter += "&approve_entcode="   + form2.approve_entcode.value;
	parameter += "&approve_tradecode=" + form2.approve_tradecode.value;
	parameter += "&approve_tradetype=" + form2.approve_tradetype.value;
	parameter += "&approve_flowtype="  + form2.approve_flowtype.value;
	parameter += "&approve_ordernum="  + form2.approve_ordernum.value;
	parameter += "&approve_ordercode=" + form2.approve_ordercode.value;
	parameter += "&approve_busitype="  + form2.approve_busitype.value;
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
	
	//否决
	form2.operationName.value = "icbc.cmis.flow.BTN.gene_foujueOp";
	form2.opAction.value = "10001";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();
	
}