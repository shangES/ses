// FECreditApply.js

function query(){
 if(isEmpty(form1.Apply_customerCode.value)){
        alert("请选择客户");
        return ;
      }
      else if(isEmpty(form1.Apply_contractID.value)){
        alert("请选择调查资料");
        return ;
      }else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return ;
      }
      else{
        form1.oper.value="detail";
        form1.submit();
      }
}

   
   
function add(){
   if(isEmpty(form1.Apply_customerCode.value)){
    alert("请选择客户");
    return ;
   }else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return ;
    }
   else{
    form1.Apply_contractID.value ="";
    form1.oper.value="add";
    form1.submit();
   
    }
}

function chooseContractID(){
     if(isEmpty(form1.Apply_customerCode.value)){
        alert("请选择客户");
        return ;
      }else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return ;
      }
      else{
        var ts = window.showModalDialog(basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=FECreditApplyOp&OpDataUnclear=true&ta200061001="+form1.Apply_customerCode.value+"&area="+form1.Apply_sponsorBank.value+"&oper=query&time="+new Date(),window,"dialogWidth:500px;dialogHeight:320px;center:yes;help:no;status:no;resizable:no");
        if(ts!=null){
          form1.Apply_contractID.value = ts[0][0];
        }
      }
   }