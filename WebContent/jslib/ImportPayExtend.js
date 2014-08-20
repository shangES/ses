//展期新增修改校验
function checkspecial(pinfo){
  if(!zhanqicheck())return false;
  else if(!dfcheck())return false;
  else return true;
}

//判断总行授权
function zhAuthorize(oper){
  if(oper=='updateTa')
    if(!patchCheck(form1.ta270053002.value,form1.ta270053001.value,form1.Apply_kind.value,'02',form1.ta270053004.value))
      return false;
    else return true;
  else if(oper=='deleteTa')
    if(!patchCheck(form1.ta270053002.value,form1.ta270053001.value,form1.Apply_kind.value,'03',form1.ta270053004.value))
      return false;
    else return true;
  else return true;
}

//流程控制
function checkflow(){
  return true;
}

//删除判断条件之一
function deletetz(){
  if(form1.ta270053022.value=='1'){
    alert("代付展期状态为已确认，不能删除");
    return false;
  }else return true;
}
//-----------------------展期台账校验---------------------------
//ta270053009:展期金额
//ta270051020:代付余额
//ta270053011:展期起始日
//ta270053012:展期到期日
//ta270051023:代付实际到期日
function zhanqicheck(){
  if(trimNum(form1.ta270053009.value)/1>trimNum(form1.ta270051020.value)/1){
    alert("展期金额不能大于代付余额");
    form1.ta270053009.focus();
    return false;
  }else if(trimNum(form1.ta270053009.value)/1<=0){
    alert("展期金额应大于0");
    form1.ta270053009.focus();
    return false;
  }else if(form1.ta270053011.value>form1.ta270051023.value){
    alert("展期起始日不能大于代付实际到期日("+form1.ta270051023.value+")");
    form1.ta270053011.focus();
    return false;
  //}else if(form1.ta270053011.value<form1.cmisdate.value){
  //  alert("展期起始日不能小于系统日期("+form1.cmisdate.value+")");
  //  form1.ta270053011.focus();
  //  return false;
  }else if(form1.ta270053011.value<form1.ta270051022.value){
    alert("展期起始日不能小于原实际代付日期("+form1.ta270051022.value+")");
    form1.ta270053011.focus();
    return false;
  }else if(form1.ta270053012.value<=form1.ta270051023.value){
    alert("展期后代付到期日应该大于代付实际到期日("+form1.ta270051023.value+")");
    form1.ta270053012.focus();
    return false;
  }else if(form1.ta270053005.value>form1.cmisdate.value){
    alert("展期申请日不能大于系统日期("+form1.cmisdate.value+")");
    form1.ta270053005.focus();
    return false;
  }else if(trimNum(form1.ta270053013.value)/1<0){
    alert("展期后国外利息点数不能小于0");
    form1.ta270053013.focus();
    return false;
  }else if(trimNum(form1.ta270053014.value)/1<0){
    alert("展期后我行利息点数不能小于0");
    form1.ta270053014.focus();
    return false;
  }
  if(!isEmpty(form1.ta270053015.value)){
    if(trimNum(form1.ta270053015.value)/1<=0){
      alert("展期后国外利率应大于0");
      form1.ta270053015.focus();
      return false;
    }
  }
  if(!isEmpty(form1.ta270053016.value)){
    if(trimNum(form1.ta270053016.value)/1<=0){
      alert("展期后我行利率应大于0");
      form1.ta270053016.focus();
      return false;
    }
  }
  if(!isEmpty(form1.ta270053017.value)){
    if(trimNum(form1.ta270053017.value)/1<=0){
      alert("展期后国外利息应大于0");
      form1.ta270053017.focus();
      return false;
    }
  } 
  
  //如果展期台账为已确认时，实际展期金额为必输
  if(form1.ta270053022.value=='1'){
    if(isEmpty(form1.ta270053010.value)){
      alert("请输入实际展期金额");
      form1.ta270053010.focus();
      return false;
    }
  }
  if(!isEmpty(form1.ta270053010.value)){
    if(trimNum(form1.ta270053010.value)/1<=0){
      alert("实际展期金额应大于0");
      form1.ta270053010.focus();
      return false;
    }
    if(trimNum(form1.ta270053010.value)/1>trimNum(form1.ta270051020.value)/1){
      alert("实际展期金额不能大于代付余额");
      form1.ta270053010.focus();
      return false;
    }
  }
  return true;
}


//ta270053012:展期后代付到期日
//ta270051021:代付日
//ta270051016:信用证付款期限
//ta270053011:展期起始日
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
  if(calculatedays(form1.ta270053011.value,form1.ta270053012.value)>pa){
    alert("展期后代付到期日－展期起始日<="+pa+"天");
    form1.ta270053011.focus();
    return false;
  }
  if(form1.ta270051006.value=='1'){
    if(calculatedays(form1.ta270051022.value,form1.ta270053012.value)+form1.ta270051016.value/1>pb){
      alert("展期后代付到期日－代付日＋信用证付款期限<="+pb+"天");
      form1.ta270053012.focus();
      return false;
    }
  }else{
     if(calculatedays(form1.ta270051022.value,form1.ta270053012.value)>pb){    
       alert("展期后代付到期日－代付日<="+pb+"天");
       form1.ta270053012.focus();
       return false;
     }
  }
  return true;
}


//选利率种类
function chooserate(){
  if(isEmpty(form1.ta270053011.value)){
    alert("请输入展期起始日期");
    form1.ta270053011.focus();
    return;
  }else return true;

}

//返回路径
function return2(){
  window.location=form1.return2Url.value;
}

//双休日alert
function alertday(fdate){
  isweekday(eval("form1."+fdate).value);
}

//
function alertstatus(){
  if(form1.ta270053022.value=='0'||form1.ta270053022.value=='2')
    alert("展期状态修改为未确认或者已拒绝，请及时修改代付台账中的代付实际到期日");
    //return false;
  
}