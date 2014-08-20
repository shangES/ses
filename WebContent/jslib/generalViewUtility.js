//var basepath0606 = "/icbc/cmis";
var objArchiveHTTP = new ActiveXObject("Microsoft.XMLHTTP");
var parserse=new ActiveXObject("microsoft.xmldom");
parserse.async="false";
var curTaget;


function loadTable(formCode,opAction,info,querypermute){
  objArchiveHTTP.Open('GET',basepath0606 + '/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralViewOp&opDataUnclear=true&opAction=' + opAction + '&formCode=' + formCode + '&primaryInfo=' + encode(info)+ '&time='+new Date(),false);
  objArchiveHTTP.Send();
  //alert('bbb');
  //document.all.code.value=objArchiveHTTP.responseText;
  document.all.datas.innerHTML = objArchiveHTTP.responseText;
  if(querypermute=="1")
  permute();//变更可写方法
  if(opAction!="showQueryView" && document.all.linkageInit.value!=""){
    var linkage = document.all.linkageInit.value;
    var linkageArray = document.all.linkageInit.value.split("|");
    for(linkageArrayIdx = 0; linkageArrayIdx<linkageArray.length; linkageArrayIdx++){
      var pos = linkageArray[linkageArrayIdx].indexOf("#");
      var funcs = linkageArray[linkageArrayIdx].substr(0,pos);
      var vars = linkageArray[linkageArrayIdx].substr(pos+1);
      var varNameArray = vars.split(",");
      var varValArray = new Array(varNameArray.length);
      for(varNameArrayIdx = 0;varNameArrayIdx<varNameArray.length;varNameArrayIdx++){
        varValArray[varNameArrayIdx] = eval("document.all." + varNameArray[varNameArrayIdx]).value;
      }
      eval(funcs);
      for(varNameArrayIdx = 0;varNameArrayIdx<varNameArray.length;varNameArrayIdx++){
        setSelectDefaultValue(eval("document.all." + varNameArray[varNameArrayIdx]),varValArray[varNameArrayIdx]);
      }
    }
  }
}


/*
function ChooseTarget(url,str_field){
  //alert(basepath0606 + url);
  var ts = window.showModalDialog(basepath0606 + url +"time=" + (new Date),window,"dialogWidth:630px;dialogHeight:360px;center:yes;help:no;status:no;resizable:no");
  if(ts != null) {
      var fields = str_field.split(",");
      for(iField = 0 ;iField < fields.length; iField++){
        if(iField == 0){
          var lab_field = "lab_" + fields[iField];
          document.getElementsByName(lab_field)[0].innerHTML = ts[iField];
        }
        document.getElementsByName(fields[iField])[0].value = ts[iField];
      }
  }
  //alert(document.getElementsByName(field).value);
  //alert(eval("document.all." + field).value);
  //alert(form1.ta470001910.value);
}*/
/******************************************************
 *功能描述:显示弹出框 
 *参数说明：str_field用|分隔,针对弹出框返回参数数量及位置指定所需赋值变量，如所需赋值变量少于返回参数数量，
           用空代替即可
 *作者：
 *创建日期：
 *****************************************************/ 
function ChooseTarget(url,str_field){
  //替换url内[]里变量的值
  var priposition=url.indexOf("[");
  var nextposition=url.indexOf("]");
  var tmp;
  var dealurl=url;
  while(priposition!=-1){
    tmp=dealurl.substring(priposition+1,nextposition);
    tmp=eval(tmp);
    dealurl=dealurl.replace(dealurl.substring(priposition,nextposition+1),tmp);
    priposition=dealurl.indexOf("[");
    nextposition=dealurl.indexOf("]");
  }
  
  var ts = window.showModalDialog(basepath0606 + dealurl +"&time=" + (new Date),window,"dialogWidth:630px;dialogHeight:360px;center:yes;help:no;status:no;resizable:no");
  if(ts != null) {
      var fields = str_field.split(",");
      for(iField = 0 ;iField < fields.length; iField++){
        if(fields[iField]!=""){
          if(fields[iField].indexOf("plain") == 0){
            eval("document.all."+fields[iField]).innerHTML = ts[iField];
          } else
            eval("document.all."+fields[iField]).value = ts[iField];
        }
      }
  }
}

function checkNeedParams(){
  //alert(document.getElementById("needParamString").value);
  var s = document.getElementById("needParamString").value;
  //alert(s);
  var rawParamArray = s.split("|");
  for(idx=0;idx<rawParamArray.length;idx++){
    var param = rawParamArray[idx].split(",");
    //alert("document.all." + param[0]);
    var val = eval("document.all." + param[0]).value;
    if(val==""){
      alert("请输入" + param[1]);
      eval("document.all." + param[0]).focus();
      return false;
    }
  }
  return true;
}



function clearSessionData(){
  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
  url = basepath0606 + "/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralViewOp&opAction=clearTempData&time=" + (new Date);
  objHTTP.Open('GET',encodeURL(url),false);
  objHTTP.Send();
}

//other tools
function setSelectDefaultValue(selector,val){
  var valIndex;
  for(valIndex=0;valIndex<selector.length;valIndex++){
    if(selector.options[valIndex].value==val){
      selector.selectedIndex = valIndex;
      break;
    }
  }
}

function checkSyntax(obj){
  if(obj.value.indexOf(">")>=0 ||
     obj.value.indexOf("<")>=0 ||
     obj.value.indexOf("\"")>=0 ||
     obj.value.indexOf("\'")>=0){
    alert("不允许输入<、>、\"、\'，请尝试用全角字符或者其他类似的字符替换");
    obj.focus();
    return;
  }
  checkLength(obj);
}

function checkDate(obj){
  if(obj.value=="")
    return;
  if(obj.size > obj.value.length){
    alert("日期长度输入错误");
    obj.focus();
    return;
  }
  //var s = v.value;
  if(!isDateValid(obj.value)){
    alert("日期格式输入错误");
    obj.focus();
  }
}

function checkNum(obj,len,decLen,min_val,max_val){
  obj.value = trim(obj.value);
  if(obj.value=="")
    return;
  var youxiao = obj.value.replace(".","");
  youxiao = youxiao.replace("+","");
  if(youxiao.length>len){
    alert("超过了最大的有效数字长度"+len+"位");
    obj.focus();
    return;
  }
  if(decLen != 0){
    var zero = "0000000000";
    var pointPos = obj.value.indexOf(".");
    if(pointPos==-1){
      obj.value = obj.value + "." + zero.substr(0,decLen);
    }
    else{
      var inputDecLen = obj.value.length - (pointPos+1);
      if(inputDecLen < decLen)
        obj.value = obj.value + zero.substr(0,decLen-inputDecLen);
    }
    var msg = isReal(obj.value,len - decLen,decLen);
    if(msg!="ok"){
      alert(msg);
      obj.focus();
    //return;
    }
    var val = parseFloat(obj.value);
    //alert(val + " " + min_val + " " + max_val);
    //alert(min_val != "")
    //alert(val < min_val)
    if("" + min_val != ""){
      
      if(val < min_val){
        alert("输入值小于最小值"+ min_val);
        obj.focus();
      }
    }
    if("" + max_val != ""){
      if(val > max_val){
        alert("输入值大于最大值"+ max_val);
        obj.focus();
      }
    }
  }
  else{
    //alert("int");
    if(!isInt(obj.value)){
      alert("数字输入不合法");
      obj.focus();
    }
    var val = parseInt(obj.value);
    //alert(val + " " + min_val + " " + max_val);
    if("" + min_val != ""){
      if(val < min_val){
        alert("输入值小于最小值"+ min_val);
        obj.focus();
      }
    }
    if("" + max_val != ""){
      if(val > max_val){
        alert("输入值大于最大值"+ max_val);
        obj.focus();
      }
    }
  }
}




/******************************************************
 *功能描述:组装页面参数
 *参数说明：formname--表单名
 *作者：
 *创建日期：
 *****************************************************/ 
function combineparameters(formname) {
  var items="";
  var itemsize=0;
  var allparameters="";
  //取表单内的所有input项
  items = document.forms[formname].getElementsByTagName("input");
  itemsize = items.length;
  for(i=0;i<itemsize;i++){
    if(items[i].disabled!=true && items[i].isAutoGen == "true")
    allparameters+="&"+items[i].name+"="+encode2(items[i].value);
  }
  //取表单内的所有select项
  items = document.forms[formname].getElementsByTagName("select");
  itemsize=items.length;
  for(i=0;i<itemsize;i++){
    if(items[i].disabled!=true&& items[i].isAutoGen == "true")
    allparameters+="&"+items[i].name+"="+encode2(items[i].value);
  }
  
  return allparameters;
}



function submitparameter(formname,formCode){
  var inputfile=combineparameters(formname);
  var act = basepath0606 + "/servlet/icbc.cmis.servlets.CmisReqServlet";
  //alert(act);
  //document.all.code.value=document.all.code.value + act;
  objArchiveHTTP.Open('GET',act,false);
  objArchiveHTTP.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  //alert(encodeURL("operationName=icbc.cmis.util.GeneralViewOp&opAction=saveTempData&inputfile="+inputfile));
  objArchiveHTTP.Send("operationName=icbc.cmis.util.GeneralViewOp&opAction=saveTempData&formCode="+formCode+"&inputfile="+inputfile);
  //var ret = objArchiveHTTP.responseText;
  //alert(objArchiveHTTP.responseText);
}