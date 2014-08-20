// TGAlterApply.js


function query(){
if(isEmpty(form1.Apply_customerCode.value)){
		alert("请你输入客户号！");
		form1.Apply_customerCode.focus();
		return ;
	}
	 if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return ;
      }
	if(isEmpty(form1.Apply_baseID.value)){
		alert("请你输入保函编号！");
		form1.Apply_baseID.focus();
		return ;
	}
	if(isEmpty(form1.Apply_contractID.value)){
		alert("请你输入保函变更号！");
		form1.Apply_contractID.focus();
		return ;
	}
	form1.oper.value = "queryDB";
	form1.submit();
}

   
   
function add(){
   if(isEmpty(form1.Apply_customerCode.value)){
		alert("请你输入客户号！");
		form1.Apply_customerCode.focus();
		return ;
	}
	 if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return ;
      }
	if(isEmpty(form1.Apply_baseID.value)){
		alert("请你输入保函编号！");
		form1.Apply_baseID.focus();
		return ;
	}
	if(isEmpty(form1.lgbgType.value)){
		alert("请你输入保函变更种类！");
		form1.lgbgType.focus();
		return ;
	}
	form1.Apply_contractID.value ="";
	form1.oper.value = "showNew";
	form1.submit();
}

function chooseContractID(){
     if(isEmpty(form1.Apply_customerCode.value)){
		alert("请你输入客户号！");
		form1.Apply_customerCode.focus();
		return ;
	} 
	if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return ;
      }	
	var ts=window.showModalDialog(encodeURL(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TG.util.queryLGNO&ask=true&customerCode="+form1.Apply_customerCode.value+"&areacode="+form1.Apply_sponsorBank.value+"&domain=international&kailiflag=true&height=410&hasDetailLink=true"),window,"dialogWidth:640px;dialogHeight:458px;center:yes;help:no;status:no;resizable:no");
	if(ts!=null){
 		form1.Apply_baseID.value=ts[1];
		form1.ISEELGCode.value=ts[2];
        form1.LGCurrencyType.value=ts[5];
        form1.LGFormerAmt.value=ts[4];
		form1.Apply_contractID.value = ""; //防止误选
 	}
   }
   
 //选保函变更编号
function f_SelectLGBG() {
	if(isEmpty(form1.Apply_customerCode.value)){
		alert("请你输入客户号！");
		form1.Apply_customerCode.focus();
		return ;
	}
	if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return ;
      }
	if(isEmpty(form1.Apply_baseID.value)){
		alert("请你输入保函编号！");
		form1.Apply_baseID.focus();
		return ;
	}
	 
	if(isEmpty(form1.lgbgType.value)){
		alert("请你输入保函变更种类！");
		form1.lgbgType.focus();
		return ;
	}
	var ts=window.showModalDialog(encodeURL(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TG.util.queryLGAlterApplyNum&ask=true&customerCode="+form1.Apply_customerCode.value+"&LGCode="+form1.Apply_baseID.value+"&lgbgType="+form1.lgbgType.value+"&height=410&hasDetailLink=true"),window,"dialogWidth:640px;dialogHeight:458px;center:yes;help:no;status:no;resizable:no");
	if(ts!=null){
 		form1.Apply_contractID.value=ts[1];
 	}
}