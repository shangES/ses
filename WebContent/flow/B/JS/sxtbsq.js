function sxtbsq(){
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
	
	//控制流程是否满足
	xmlurl = form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BC.BC_inputcheckOp&opDataUnclear=true&opAction=10006";
	xmlurl += "&check_ent=" + form2.approve_entcode.value;
	xmlurl += "&check_app=" + form2.approve_tradecode.value;
	xmlurl += "&check_flow=" + form2.approve_flowtype.value;
	xmlurl += "&check_area=" + form2.empareacode.value;
	xmlurl += "&newtime=" + new Date();
	objHTTP.Open('GET',encodeURL(xmlurl),false);
	objHTTP.Send();
	if(!parser.loadXML(objHTTP.responseText)){
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return;
	}
	error = parser.getElementsByTagName("error");
	if(error.length > 0) {
		alert("不能签批："+error.item(0).text);
	return;
	}
	
	
	var EntDate_ApplyNo = form2.approve_tradecode.value;
	var ApplyNumber = form2.approve_ordernum.value;
	var ApplyNumber_length = form2.approve_ordernum.value.length;
	
	if (ApplyNumber_length == 1) {
	 ApplyNumber = "00" + form2.approve_ordernum.value;
	 } else if (ApplyNumber_length == 2) {
	 ApplyNumber = "0" + form2.approve_ordernum.value;
	 } else if (ApplyNumber_length >= 3){
	 ApplyNumber =  form2.approve_ordernum.value;
	 } 

     //2006-07-19添加区分流程和补录的标志  bulu_flag 0代表流程,1代表补录
      if (confirm("使用特别授权.是否确定?")){
     var ts = window.showModalDialog(form2.baseWebPath.value+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.GB.GB_CreditApproveMainOp&OpDataUnclear=true&oper=extragrantquery&time="+new Date() + "&EntCode=" + form2.approve_entcode.value + "&EntDate =" + EntDate_ApplyNo.substring(0, 8) + "&GroupCode =" + form2.approve_entcode.value + "&ApplyNo =" + EntDate_ApplyNo.substring(9, 12) + "&ApplyNumber =" + ApplyNumber + "&bulu_flag=0",window,"dialogWidth:415px;dialogHeight:320px;center:yes;help:no;status:no;resizable:no");
     if(ts!=null){
       form2.usrdef1.value = ts[0][0];//原来流程中的grant900
       form2.oper.value = "useextragrant";
       form2.action= form2.baseWebPath.value+ "/servlet/icbc.cmis.servlets.CmisReqServlet";
       form2.operationName.value = "icbc.cmis.GB.GB_CreditApproveMainOp";
       document.all.mainframe.style.display="none";
	   document.all.waitframe.style.display="";
       form2.submit();
     }
     return true;
  }
  
	//特别授权
	/*form2.qptype.value = "";  
	form2.operationName.value = "icbc.cmis.GB.GB_CreditApproveMainOp";
	form2.oper.value = "extragrantquery";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();*/
}