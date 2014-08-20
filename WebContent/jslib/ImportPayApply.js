//进口代付申请校验
function checkspecial(pinfo){
  var pri=pinfo.split("|");
  if(pri[0]=='24'&&pri[1]=='0'){
    if(!applycheck1())return false;
    else return true;
  }else if(pri[0]=='25'&&pri[1]=='0'){
    if(!applycheck2())return false;
    else return true; 
  }else return true;
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
//-----------------------信用证项下进口代付申请校验---------------------------
//ta270050018：代付到期日
//ta270050017: 约定代付日期
//ta270050014: 付款期限
//ta270050016: 代付金额
function applycheck1(){
  //代付到期日-约定代付日期+付款期限<=参数A
  if(calculatedays(form1.ta270050017.value,form1.ta270050018.value)+form1.ta270050014.value/1>form1.controlA.value){
    alert("代付到期日－约定代付日期+付款期限<="+form1.controlA.value+"天");
    form1.ta270050017.focus();
    return false;
  }
  //代付到期日-约定代付日期<=参数B
  else if(calculatedays(form1.ta270050017.value,form1.ta270050018.value)>form1.controlB.value){
    alert("代付到期日－约定代付日期<="+form1.controlB.value+"天");
    form1.ta270050017.focus();
    return false;
  }
  else if(form1.ta270050017.value>=form1.ta270050018.value){
    alert("代付到期日应大于约定代付日期");
    form1.ta270050018.focus();
    return false;
  }
  else if(form1.ta270050905.value>form1.cmisdate.value){
    alert("申请日期不能大于系统日期("+form1.cmisdate.value+")");
    form1.ta270050905.focus();
    return false;
  }
  else if(form1.ta270050017.value<form1.cmisdate.value){
    alert("约定代付日期不能小于系统日期("+form1.cmisdate.value+")");
    form1.ta270050017.focus();
    return false;
  }
  else if(trimNum(form1.ta270050016.value)/1>trimNum(form1.ta270050013.value)/1){
    alert("代付金额不能大于结算金额");
    form1.ta270050016.focus();
    return false;
  }
  else if(trimNum(form1.ta270050016.value)/1<=0){
    alert("代付金额应大于0");
    form1.ta270050016.focus();
    return false;
  }
  else if(trimNum(form1.ta270050009.value)/1<0){
    alert("保证金金额不能小于0");
    form1.ta270050009.focus();
    return false;
  }
  else if(trimNum(form1.ta270050019.value)/1<0){
    alert("国外利息点数不能小于0");
    form1.ta270050019.focus();
    return false;
  }else if(trimNum(form1.ta270050020.value)/1<0){
    alert("我行利息点数不能小于0");
    form1.ta270050020.focus();
    return false;
  //}else if(form1.ta270050006.value=='20'){
  //  if(isEmpty(form1.ta270050023.value)){
  //    alert("请选择担保形式");
  //    form1.ta270050023.focus();
  //    return false;
  //  }else return true;
  }else if(!checkExpDate(form1.ta270050022.value)){
    alert("到期日标识格式不正确，请重新输入");
    form1.ta270050022.focus();
    return false;
  }
  //}else if(form1.ta270050006.value!='20'){
  //  if(!isEmpty(form1.ta270050023.value)){
  //    alert("担保方式非保证，不用输入担保形式");
  //    form1.ta270050023.focus();
  //    return false;
  //  }else return true;
  //}  else return true;
  if(form1.ta270050006.value=='20'){
    if(isEmpty(form1.ta270050023.value)){
      alert("请选择担保形式");
      form1.ta270050023.focus();
      return false;
    }else return true;
  }else if(form1.ta270050006.value!='20'){
    if(!isEmpty(form1.ta270050023.value)){
      alert("担保方式非保证，不用输入担保形式");
      form1.ta270050023.focus();
      return false;
    }else return true;
  }else return true;
}

//-----------------------非信用证项下进口代付申请校验---------------------------
//ta270050018：代付到期日
//ta270050017: 约定代付日期
//ta270050016: 代付金额
function applycheck2(){
  //代付到期日-约定代付日期<=参数B
  if(calculatedays(form1.ta270050017.value,form1.ta270050018.value)>form1.controlE.value){
    alert("代付到期日－约定代付日期<="+form1.controlE.value+"天");
    form1.ta270050017.focus();
    return false;
  }
  else if(form1.ta270050017.value>=form1.ta270050018.value){
    alert("代付到期日应大于约定代付日期");
    form1.ta270050018.focus();
    return false;
  }
  else if(form1.ta270050905.value>form1.cmisdate.value){
    alert("申请日期不能大于系统日期("+form1.cmisdate.value+")");
    form1.ta270050905.focus();
    return false;
  }
  else if(trimNum(form1.ta270050016.value)/1<=0){
    alert("代付金额应大于0");
    form1.ta270050016.focus();
    return false;
  }
  else if(trimNum(form1.ta270050009.value)/1<0){
    alert("保证金金额不能小于0");
    form1.ta270050009.focus();
    return false;
  }
  else if(trimNum(form1.ta270050013.value)/1<=0){
    alert("结算金额应大于0");
    form1.ta270050013.focus();
    return false;
  }

  else if(form1.ta270050017.value<form1.cmisdate.value){
    alert("约定代付日期不能小于系统日期("+form1.cmisdate.value+")");
    form1.ta270050017.focus();
    return false;
  }
  else if(trimNum(form1.ta270050016.value)/1>trimNum(form1.ta270050013.value)/1){
    alert("代付金额不能大于结算金额");
    form1.ta270050016.focus();
    return false;
  }else if(trimNum(form1.ta270050019.value)/1<0){
    alert("国外利息点数不能小于0");
    form1.ta270050019.focus();
    return false;
  }else if(trimNum(form1.ta270050020.value)/1<0){
    alert("我行利息点数不能小于0");
    form1.ta270050020.focus();
    return false;
  }
  
  if(form1.ta270050003.value=='2'){
    if(isEmpty(form1.ta270050011.value)){
      alert("请输入相关业务编号");
      form1.ta270050011.focus();
      return false;
    }
    if(isEmpty(form1.ta270050022.value)){
      alert("请输入到期日标识");
      form1.ta270050022.focus();
      return false;
    }else{
      if(!checkExpDate(form1.ta270050022.value)){
        alert("到期日标识格式不正确，请重新输入");
        form1.ta270050022.focus();
        return false;
      }
    }
    if(isEmpty(form1.ta270050014.value)){
      alert("请输入相关业务付款期限");
      form1.ta270050014.focus();
      return false;
    }
  }else if(form1.ta270050003.value=='3'){
    if(!isEmpty(form1.ta270050022.value)){
      alert("结算方式为T/T汇款,不需输入到期日标识");
      form1.ta270050022.focus();
      return false;
    }
  }
  
  if(!isEmpty(form1.ta270050014.value)){
    if(form1.ta270050014.value/1<0){
      alert("相关业务付款期限不能小于0");
      form1.ta270050014.focus();
      return false;
    }  
  }
  
  if(form1.ta270050006.value=='20'){
    if(isEmpty(form1.ta270050023.value)){
      alert("请选择担保形式");
      form1.ta270050023.focus();
      return false;
    }else return true;
  }else if(form1.ta270050006.value!='20'){
    if(!isEmpty(form1.ta270050023.value)){
      alert("担保方式非保证，不用输入担保形式");
      form1.ta270050023.focus();
      return false;
    }else return true;
  }else return true;
}
//改变代付币种
function changecurr1(){
  form1.ta270050012.value=form1.ta270050015.value;
  if(!isEmpty(form1.ta270050021.value)){
    form1.lab_ta270050021.value="";
    form1.ta270050021.value="";
    alert("请重新选择利率种类");
    return;
  }
}

//改变结算币种
function changecurr2(){
  form1.ta270050015.value=form1.ta270050012.value;
  if(!isEmpty(form1.ta270050021.value)){
    form1.lab_ta270050021.value="";
    form1.ta270050021.value="";
    alert("请重新选择利率种类");
    return;
  }
}
//---------------------进口代付申请通用-------------------------------------
//选利率种类
function chooserate(){
  var pinfo=form1.primaryInfo.value;
  if(isEmpty(form1.ta270050015.value)){
    var pri=pinfo.split("|");
    if(pri[0]=='24')
      alert("请选择相关业务编号");
    else{
      alert("请选择代付币种");
      form1.ta270050015.focus();
    }
    return;
  }else if(isEmpty(form1.ta270050017.value)){
    alert("请输入约定代付日期");
    form1.ta270050017.focus();
    return;
  }else return true;

}

//变动代付币种
function changecurr(){
  if(!isEmpty(form1.ta270050021.value)){
    form1.lab_ta270050021.value="";
    form1.ta270050021.value="";
    alert("请重新选择利率种类");
    return;
  }
}


//变动贷款方式
function changedkfs(){
  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
  var parser=new ActiveXObject("microsoft.xmldom");
  parser.async="false";
  var tmp = "enpcode="+form1.ta270050001.value+"&dkfs="+form1.ta270050006.value+"&oper=querydkfs&time="+new Date();
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
  var seleobj=document.all.ta270050007;
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
/*
//相关合同
function assure(){  
  var flag;//0不能修改，1可以修改
  if(form1.pageMode.value=='showInsertView'||form1.pageMode.value=='showModifyView'){
    flag='1';
  }else
    flag='0';
  if(form1.pageMode.value!='showQueryView')
    submitparameter("form1",form1.formCode.value);
  var returnLink = basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true';//返回的url
  returnLink+="&oper="+form1.pageMode.value;
  assureTab(flag,returnLink);
}

//业务附属信息
function append(){
  var flag;//0不能修改，1可以修改
  if(form1.pageMode.value=='showInsertView'||form1.pageMode.value=='showModifyView'){
    flag='1';
  }else
    flag='0';
  if(form1.pageMode.value!='showQueryView')
    submitparameter("form1",form1.formCode.value);
  var returnurl=basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true';
  returnurl+="&oper="+form1.pageMode.value;
  appendTab(flag,returnurl,'');
}


//返回首页
function returnmain(){
  pinfo=form1._primaryInfo.value;
  var pri=pinfo.split("|");
  form1.operationName.value="icbc.cmis.util.GeneralApplyFirstFormOp";
  form1.Apply_kind.value=pri[0];
  form1.Apply_stage.value=pri[1];
  form1.submit();
}
*/


