//modified by yhua on 2004/06/16
function checkDataBalance(){
	if(mainForm.customerType.value=="01"){//银行
	   if(mainForm.TA209990101.value*1 != mainForm.TA209990102.value*1+mainForm.TA209990103.value*1+mainForm.TA209990104.value*1){
			alert("不良贷款应等于次级贷款+可疑贷款+损失贷款");
			mainForm.TA209990101.focus();
		   return false;
	   }
	   if(mainForm.TA209990101.value*1 < mainForm.TA209990102.value*1+mainForm.TA209990103.value*1+mainForm.TA209990104.value*1){
		   alert("不良贷款不应小于其中分项之和！");
		   mainForm.TA209990101.focus();
		   return false;
	   }
	   if(mainForm.TA209990107.value*1 < mainForm.TA209990108.value*1+mainForm.TA209990109.value*1){
		   alert("年初持有国债及金融债券余额不应小于其中分项之和！");
		   mainForm.TA209990107.focus();
		   return false;
	   }
	}else if(mainForm.customerType.value=="06"){//财务公司
	   if(mainForm.TA209990106.value*1 != mainForm.TA209990107.value*1+mainForm.TA209990108.value*1+mainForm.TA209990109.value*1){
		   alert("不良贷款应等于其中分项之和！");
		   mainForm.TA209990106.focus();
		   return false;
	   }
/*
modified by yhua on 2004/06/16
	}else if(mainForm.customerType.value=="05"){//证券
	   if(mainForm.TA209990105.value*1<mainForm.TA209990106.value*1){
	  	   alert("银行存款不应小于客户资金存放！");
		   mainForm.TA209990105.focus();
		   return false;
	   }	
*/
	}   
	return true;
}	
