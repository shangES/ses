// 进口代付台账新增修改校验
function checkspecial(pinfo){
  var pri=pinfo.split("|");
  if(pri[0]=='24'&&pri[1]=='1'){
    if(!applycheck1())return false;
  }else if(pri[0]=='25'&&pri[1]=='1'){
    if(!applycheck2())return false;
  }
  if(!check1()) return false;
  else return true;  
}

//判断总行授权，是否可以修改删除
function zhAuthorize(oper){
  if(oper=='updateTa')
    if(!patchCheck(form1.ta270051002.value,form1.ta270051001.value,form1.Apply_kind.value,'02',form1.ta270051004.value)){
      return false;
    }else return true;
  else if(oper=='deleteTa')
    if(!patchCheck(form1.ta270051002.value,form1.ta270051001.value,form1.Apply_kind.value,'03',form1.ta270051004.value)){
      return false;
    }else return true;
  else return true;
}

//流程判断
function checkflow(){
  return true;
}

//删除台账条件之一
function deletetz(){
  if(form1.ta270051039.value=='2'){
    alert("代付状态为代付确认，不能删除");
    return false;
  }else return true;
}

//-----------------------信用证项下进口代付台账校验---------------------
//ta270051024：代付到期日
//ta270051021: 约定代付日期
//ta270051025: 付款期限
//ta270051018: 代付金额
function applycheck1(){
  //代付到期日-约定代付日期+付款期限<=参数A
  if(calculatedays(form1.ta270051021.value,form1.ta270051024.value)+form1.ta270051016.value/1>form1.controlA.value){
    alert("代付到期日－约定代付日期+付款期限<="+form1.controlA.value+"天");
    form1.ta270051021.focus();
    return false;
  }
  //代付到期日-约定代付日期<=参数B
  else if(calculatedays(form1.ta270051021.value,form1.ta270051024.value)>form1.controlB.value){
    alert("代付到期日－约定代付日期<="+form1.controlB.value+"天");
    form1.ta270051021.focus();
    return false;
  }
  else if(form1.ta270051021.value>=form1.ta270051024.value){
    alert("代付到期日应大于约定代付日期");
    form1.ta270051024.focus();
    return false;
  }
  else if(form1.ta270051005.value>form1.cmisdate.value){
    alert("申请日期不能大于系统日期("+form1.cmisdate.value+")");
    form1.ta270051005.focus();
    return false;
  }
  //else if(form1.ta270051021.value<form1.cmisdate.value){
  //  alert("约定代付日期不能小于系统日期("+form1.cmisdate.value+")");
  //  form1.ta270051021.focus();
  //  return false;
  //}
  else if(trimNum(form1.ta270051018.value)/1<=0){
    alert("代付金额应大于0");
    form1.ta270051018.focus();
    return false;
  }
  else if(trimNum(form1.ta270051012.value)/1<0){
    alert("保证金金额不能小于0");
    form1.ta270051012.focus();
    return false;
  }
  else if(trimNum(form1.ta270051018.value)/1>trimNum(form1.ta270051015.value)/1){
    alert("代付金额不能大于结算金额");
    form1.ta270051018.focus();
    return false;
  }
  else if(!checkExpDate(form1.ta270051041.value)){
    alert("到期日标识格式不正确，请重新输入");
    form1.ta270051041.focus();
    return false;
  }else if(trimNum(form1.ta270051029.value)/1<0){
    alert("国外利息点数不能小于0");
    form1.ta270051029.focus();
    return false;
  }else if(trimNum(form1.ta270051030.value)/1<0){
    alert("我行利息点数不能小于0");
    form1.ta270051030.focus();
    return false;
  }
  if(!isEmpty(form1.ta270051033.value)){
    if(trimNum(form1.ta270051033.value)/1<0){
      alert("国外代付执行利率不能小于0");
      form1.ta270051033.focus();
      return false;
    }
  }
  if(!isEmpty(form1.ta270051034.value)){
    if(trimNum(form1.ta270051034.value)/1<0){
      alert("我行代付执行利率不能小于0");
      form1.ta270051034.focus();
      return false;
    }
  }
  
  if(form1.ta270051009.value=='20'){
    if(isEmpty(form1.ta270051042.value)){
      alert("请选择担保形式");
      form1.ta270051042.focus();
      return false;
    }else return true;
  }else if(form1.ta270051009.value!='20'){
    if(!isEmpty(form1.ta270051042.value)){
      alert("担保方式非保证，不用输入担保形式");
      form1.ta270051042.focus();
      return false;
    }else return true;
  }
  else return true;
}


//-----------------------非信用证项下进口代付台账校验--------------------
//ta270051024：代付到期日
//ta270051021: 约定代付日期
//ta270051018: 代付金额
function applycheck2(){
  //代付到期日-约定代付日期<=参数E
  if(calculatedays(form1.ta270051021.value,form1.ta270051024.value)>form1.controlE.value){
    alert("代付到期日－约定代付日期<="+form1.controlE.value+"天");
    return false;
  }
  else if(form1.ta270051021.value>=form1.ta270051024.value){
    alert("代付到期日应大于约定代付日期");
    form1.ta270051024.focus();
    return false;
  }
  else if(form1.ta270051005.value>form1.cmisdate.value){
    alert("申请日期不能大于系统日期("+form1.cmisdate.value+")");
    form1.ta270051005.focus();
    return false;
  }
  //else if(form1.ta270051021.value<form1.cmisdate.value){
  //  alert("约定代付日期不能小于系统日期("+form1.cmisdate.value+")");
  //  form1.ta270051021.focus();
  //  return false;
  //}
  else if(trimNum(form1.ta270051018.value)/1<=0){
    alert("代付金额应大于0");
    form1.ta270051018.focus();
    return false;
  }
  else if(trimNum(form1.ta270051012.value)/1<0){
    alert("保证金金额不能小于0");
    form1.ta270051012.focus();
    return false;
  }
  else if(trimNum(form1.ta270051015.value)/1<=0){
    alert("结算金额应大于0");
    form1.ta270051015.focus();
    return false;
  }
  else if(trimNum(form1.ta270051018.value)/1>trimNum(form1.ta270051015.value)/1){
    alert("代付金额不能大于结算金额");
    form1.ta270051018.focus();
    return false;
  }
  else if(trimNum(form1.ta270051029.value)/1<0){
    alert("国外利息点数不能小于0");
    form1.ta270051029.focus();
    return false;
  }else if(trimNum(form1.ta270051030.value)/1<0){
    alert("我行利息点数不能小于0");
    form1.ta270051030.focus();
    return false;
  }
  if(!isEmpty(form1.ta270051033.value)){
    if(trimNum(form1.ta270051033.value)/1<0){
      alert("国外代付执行利率不能小于0");
      form1.ta270051033.focus();
      return false;
    }
  }
  if(!isEmpty(form1.ta270051034.value)){
    if(trimNum(form1.ta270051034.value)/1<0){
      alert("我行代付执行利率不能小于0");
      form1.ta270051034.focus();
      return false;
    }
  }
  if(form1.ta270051006.value=='2'){
    if(isEmpty(form1.ta270051013.value)){
      alert("请输入相关业务编号");
      form1.ta270051013.focus();
      return false;
    }
    if(isEmpty(form1.ta270051041.value)){
      alert("请输入到期日标识");
      form1.ta270051041.focus();
      return false;
    }else{
      if(!checkExpDate(form1.ta270051041.value)){
        alert("到期日标识格式不正确，请重新输入");
        form1.ta270051041.focus();
        return false;
      }
    }
    if(isEmpty(form1.ta270051016.value)){
      alert("请输入相关业务付款期限");
      form1.ta270051016.focus();
      return false;
    }
  }else if(form1.ta270051006.value=='3'){
    if(!isEmpty(form1.ta270051041.value)){
      alert("结算方式为T/T汇款,不需输入到期日标识");
      form1.ta270051041.focus();
      return false;
    }
  }
  
  if(!isEmpty(form1.ta270051016.value)){
    if(form1.ta270051016.value/1<0){
      alert("相关业务付款期限不能小于0");
      form1.ta270051016.focus();
      return false;
    }
  }
  
  if(form1.ta270051009.value=='20'){
    if(isEmpty(form1.ta270051042.value)){
      alert("请选择担保形式");
      form1.ta270051042.focus();
      return false;
    }else return true;
  }else if(form1.ta270051009.value!='20'){
    if(!isEmpty(form1.ta270051042.value)){
      alert("担保方式非保证，不用输入担保形式");
      form1.ta270051042.focus();
      return false;
    }else return true;
  }else return true;
}

//改变代付币种
function changecurr1(){
  form1.ta270051014.value=form1.ta270051017.value;
  form1.lab_ta270051035.value="";
  form1.ta270051035.value="";

}
//改变结算币种
function changecurr2(){
  form1.ta270051017.value=form1.ta270051014.value;
  form1.lab_ta270051035.value="";
  form1.ta270051035.value="";

}
//-------------------------进口代付台账通用---------------------------
//变动贷款方式
function changedkfs(){
  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
  var parser=new ActiveXObject("microsoft.xmldom");
  parser.async="false";
  var tmp = "enpcode="+form1.ta270051001.value+"&dkfs="+form1.ta270051009.value+"&oper=querydkfs&time="+new Date();
  objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&xmlOutput=true&opDataUnclear=true&'+tmp,false);
  objHTTP.send();
  var xml = objHTTP.responseText;
  if(!parser.loadXML(xml)) {
    return;
  }
  error = parser.getElementsByTagName("error");
  if(error.length > 0) {
    alert(error.item(0).text);
    return;
  }
  var nodes = parser.documentElement.childNodes;
  var seleobj=document.all.ta270051010;
  seleobj.length = 0;
  seleobj.options[0] = new Option("    ","");
  if(nodes.length > 0){
    for(i = 0; i < nodes.length ; i ++ ) {
      var node = nodes.item(i);
      seleobj.options[i+1] = new Option(node.getAttribute("dict_name"),node.getAttribute("dict_code"));
    }
  } else{
    seleobj.length = 0;
    seleobj.options[0] = new Option("      ","");
  }
}

//选择利率种类
function chooserate(){
  var pinfo=form1.primaryInfo.value;
  if(isEmpty(form1.ta270051017.value)){
    var pri=pinfo.split("|");
    if(pri[0]=='24')
      alert("请选择相关业务编号");
    else{
      alert("请选择代付币种");
      form1.ta270051017.focus();
    }
    return;
  }else if(isEmpty(form1.ta270051021.value)){
    alert("请输入约定代付日期");
    form1.ta270051021.focus();
    return;
  }else return true;
}

//改变代付币种
function changecurr(){
  if(!isEmpty(form1.ta270051017.value)){
    form1.lab_ta270051035.value="";
    form1.ta270051035.value="";
    alert("请重新选择利率种类");
    return;
  }
}

//当台账状态不是未确认时的数据校验
function check1(){
  if(form1.ta270051039.value!='0'){
    if(isEmpty(form1.ta270051027.value)){
      alert("请输入代付实际业务编号");
      form1.ta270051027.focus();
      return false;
    }else if(isEmpty(form1.ta270051019.value)){
      alert("请输入实际代付金额");
      form1.ta270051019.focus();
      return false; 
    }else if(trimNum(form1.ta270051019.value)/1<=0){
      alert("实际代付金额应大于0");
      form1.ta270051019.focus();
      return false;
    }else if(trimNum(form1.ta270051019.value)/1>form1.ta270051015.value){
      alert("实际代付金额不能大于结算金额");
      form1.ta270051019.focus();
      return false;
    }else if(isEmpty(form1.ta270051020.value)){
      alert("请输入代付余额");
      form1.ta270051020.focus();
      return false;
    }else if(trimNum(form1.ta270051020.value)/1<0){
      alert("代付余额不能小于0");
      form1.ta270051020.focus();
      return false;
    }else if(trimNum(form1.ta270051020.value)/1>trimNum(form1.ta270051019.value)/1){
      alert("代付余额不能大于实际代付金额");
      form1.ta270051020.focus();
      return false;
    }
    else if(isEmpty(form1.ta270051022.value)){
      alert("请输入实际代付日期");
      form1.ta270051022.focus();
      return false;
    }else if(isEmpty(form1.ta270051023.value)){
      alert("请输入代付实际到期日");
      form1.ta270051023.focus();
      return false
    }else if(isEmpty(form1.ta270051036.value)){
      alert("请输入银行复核日期");
      form1.ta270051036.focus();
      return false;
    }else if(isEmpty(form1.ta270051028.value)){
      alert("请输入代付行");
      form1.ta270051028.focus();
      return false;
    //}else if(form1.ta270051022.value<form1.cmisdate.value){
    //  alert("实际代付日期不能小于系统日期("+form1.cmisdate.value+")");
    //  form1.ta270051022.focus();
    //  return false;
    }else if(form1.ta270051023.value<form1.ta270051022.value){
      alert("代付实际到期日不能小于实际代付日期");
      form1.ta270051023.focus();
      return false;
    }
  }
  if(form1.ta270051006.value=='1'){
    if(calculatedays(form1.ta270051022.value,form1.ta270051023.value)+form1.ta270051016.value/1>form1.controlA.value){
      alert("代付实际到期日－实际代付日期+付款期限<="+form1.controlA.value+"天");
      form1.ta270051023.focus();
      return false;
    }
    //代付到期日-约定代付日期<=参数B
    else if(calculatedays(form1.ta270051022.value,form1.ta270051023.value)>form1.controlB.value){
      alert("代付实际到期日－实际代付日期<="+form1.controlB.value+"天");
      form1.ta270051023.focus();
      return false;
    }
  }else{
    if(calculatedays(form1.ta270051022.value,form1.ta270051023.value)>form1.controlE.value){
      alert("代付实际到期日－实际代付日期<="+form1.controlE.value+"天");
      form1.ta270051023.focus();
      return false;
    }
  }
  return true;
}


//检查到期日标识是否为RGF或者YYYY.MM.DD格式的日期。
function checkExpDate(vexp){
	var v_temp;
	var v_YYYYMMDD;

	if(isEmpty(vexp)){return false;}
	v_temp=vexp;
	if(v_temp=="RGF"){return true;}
	if(v_temp.length!=10){return false;}
	if(v_temp.indexOf('.')!=4){return false;}
	if(v_temp.lastIndexOf('.')!=7){return false;}
	v_YYYYMMDD=v_temp.substring(0,4)+v_temp.substring(5,7)+v_temp.substring(8,10);
	if(isDateValid1(v_YYYYMMDD)){return true;}
	return false;
}

//双休日alert
function alertday(fdate){
  isweekday(eval("form1."+fdate).value);
}