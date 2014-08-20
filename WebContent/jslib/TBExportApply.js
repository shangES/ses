// TBExportApply.js

function query(){
  if(trim(form1.Apply_customerCode.value) == ""||form1.Apply_customerCode.value == null){
        alert("请选择客户代码!");
        return;
      }
   if(trim(form1.Apply_contractID.value) == ""||form1.Apply_contractID.value == null){
        alert("请选择出口押汇申请号");
        return;
      }else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
      }
      
      else{
    form1.oper.value="querydetail";
    form1.submit();
  }
}

   
   
function add(){
   if(trim(form1.Apply_customerCode.value) == ""||form1.Apply_customerCode.value == null)  {
     alert("请选择客户代码!");
     return;
   }else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
      }
   form1.Apply_contractID.value = "";  
   form1.oper.value="new";
   form1.submit();
}

function chooseContractID(){
    if(form1.Apply_customerCode.value == ""||form1.Apply_customerCode.value == null){
    alert("请先选择客户代码!");
    form1.Apply_customerCode.focus();
    return;
  }
 if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
    }
  var EntCode = form1.Apply_customerCode.value;
  var ts = window.showModalDialog(encodeURL(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TB.util.queryAppCode&EntCode="+EntCode+"&area="+form1.Apply_sponsorBank.value+"&height=410&hasDetailLink=true&returnType=value"),window,"dialogWidth:630px;dialogHeight:460px;center:yes;help:no;status:no;resizable:yes");
  if (ts != null){
    form1.Apply_contractID.value = ts[1];
  }
}