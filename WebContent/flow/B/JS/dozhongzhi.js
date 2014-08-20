function dozhongzhi(){
   if(!confirm("是否确认中止此流程？")){
		return;
	}
	form2.operationName.value = "icbc.cmis.flow.BTN.zhongzhiOp";
	form2.opAction.value = "10001";
	form2.submit();
   

}