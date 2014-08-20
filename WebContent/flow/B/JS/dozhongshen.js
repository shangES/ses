function dozhongshen(){
	if(!confirm("是否确认进行终审操作？")){
		return;
	}
	
	form2.operationName.value = "icbc.cmis.flow.BTN.zhongshenOp";
	form2.opAction.value = "10001";
	form2.submit();
   
}