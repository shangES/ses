function dofenfa(){
	//校验部分待
	if(isEmpty(form1.nextflow.value)){
		alert("请选择下一环节");
		return;
	}
	if(isEmpty(form1.nextareacode.value)){
		alert("请选择下一地区");
		return;
	}
	//对于分发，只能发送到同一环节，同一地区
	if(form1.nextflow.value != form2.approve_ordercode.value){
		alert("分发时，只能发送到当前环节："+form2.approve_ordername.value);
		return;
	}
	if(form1.nextareacode.value != form2.empareacode.value) {
		alert("分发时，只能发送到当前柜员所在行："+form2.empareaname.value);
		return;
	}
	if(isEmpty(form1.nextemployeecode.value)) {
		alert("请选择需要发送的柜员");
		return;
	}
	
	form2.nextflow.value = form1.nextflow.value;
	form2.nextflowname.value = form1.nextflow.value;
	form2.nextareacode.value = form1.nextareacode.value;
	form2.nextareaname.value = form1.nextareaname.value;
	form2.nextemployeecode.value = form1.nextemployeecode.value;
	form2.nextemployeename.value = form1.nextemployeename.value;

	form2.operationName.value = "icbc.cmis.flow.BTN.fenfaOp";
	form2.opAction.value = "10001";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();
}
