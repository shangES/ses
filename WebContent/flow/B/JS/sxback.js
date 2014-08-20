function sxback(){
	if(form2.approve_isfirst.value=='1'){
		alert("当前是发起环节，不能进行还原操作");
		return;
	}

	if(!confirm("将当前审批业务还原成未处理状态，是否继续？")){
		return;
	}
	
	//还原处理
	form2.operationName.value = "icbc.cmis.flow.BTN.sxbackOp";
	form2.opAction.value = "10001";
	document.all.mainframe.style.display="none";
	document.all.waitframe.style.display="";
	form2.submit();
}