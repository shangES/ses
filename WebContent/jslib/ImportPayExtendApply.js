//展期申请校验
function checkspecial(pinfo){
  if(!zhanqicheck())return false;
  else if(!dfcheck())return false;
  else return true;
}

//判断总行授权
function zhAuthorize(oper){
  return true;
}

//判断是否进入流程
function checkflow(){
  if(!checkappstat(form1.Apply_customerCode.value,form1.Apply_contractID.value,form1.Apply_kind.value)){
    return false;
  }else return true;
}

//删除条件
function deletetz(){
  return true;
}
//-----------------------展期申请校验---------------------------
//ta270052005:展期金额
//ta270051020:代付余额
//ta270052006:展期起始日
//ta270052007:展期到期日
//ta270051023:代付实际到期日
function zhanqicheck(){
  if(trimNum(form1.ta270052005.value)/1>trimNum(form1.ta270051020.value)/1){
    alert("展期金额不能大于代付余额");
    form1.ta270052005.focus();
    return false;
  }else if(trimNum(form1.ta270052005.value)/1<=0){
    alert("展期金额应大于0");
    form1.ta270052005.focus();
    return false;
  }else if(form1.ta270052006.value>form1.ta270051023.value){
    alert("展期起始日不能大于原代付实际到期日("+form1.ta270051023.value+")");
    form1.ta270052006.focus();
    return false;
  }else if(form1.ta270052006.value<form1.cmisdate.value){
    alert("展期起始日不能小于系统日期("+form1.cmisdate.value+")");
    form1.ta270052006.focus();
    return false;
  }else if(form1.ta270052006.value<form1.ta270051022.value){
    alert("展期起始日不能小于原实际代付日期("+form1.ta270051022.value+")");
    form1.ta270052006.focus();
    return false;
  }else if(form1.ta270052007.value<=form1.ta270051023.value){
    alert("展期后代付到期日应大于原代付实际到期日("+form1.ta270051023.value+")");
    form1.ta270052007.focus();
    return false;
  }else if(form1.ta270052905.value>form1.cmisdate.value){
    alert("申请日期不能大于系统日期("+form1.cmisdate.value+")");
    form1.ta270052905.focus();
    return false;
  }else if(trimNum(form1.ta270052008.value)/1<0){
    alert("展期后国外利息点数不能小于0");
    form1.ta270052008.focus();
    return false;
  }else if(trimNum(form1.ta270052009.value)/1<0){
    alert("展期后我行利息点数不能小于0");
    form1.ta270052009.focus();
    return false;
  }
  else return true;
}


//ta270052007:展期后代付到期日
//ta270051021:代付日
//ta270051016:信用证付款期限
//ta270052006:展期起始日
//pa:参数C(信用证项下)/参数F(非信用证项下)
//pb:参数D/参数G
function dfcheck(){
  var pa,pb;
  if(form1.ta270051006.value=='1'){
    pa=form1.controlC.value;
    pb=form1.controlD.value;
  }else{
    pa=form1.controlF.value;
    pb=form1.controlG.value;
  }
  if(calculatedays(form1.ta270052006.value,form1.ta270052007.value)>pa){
    alert("展期后代付到期日－展期起始日<="+pa+"天");
    form1.ta270052006.focus();
    return false;
  }
  if(form1.ta270051006.value=='1'){
    if(calculatedays(form1.ta270051022.value,form1.ta270052007.value)+form1.ta270051016.value/1>pb){
       alert("展期后代付到期日－代付日＋信用证付款期限<="+pb+"天");
       form1.ta270052007.focus();
       return false;
    }
  }else{
    if(calculatedays(form1.ta270051022.value,form1.ta270052007.value)>pb){
      alert("展期后代付到期日－代付日<="+pb+"天");
      form1.ta270052007.focus();
      return false;
    }
  }
  return true;
}


//选利率种类
function chooserate(){
  if(isEmpty(form1.ta270052006.value)){
    alert("请输入展期起始日期");
    form1.ta270052006.focus();
    return;
  }else return true;

}

//双休日alert
function alertday(fdate){
  isweekday(eval("form1."+fdate).value);
}
