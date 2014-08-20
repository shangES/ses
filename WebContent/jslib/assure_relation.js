/*调用说明：担保合同公用模块一些公用校验的js函数*/

/**
 * 担保合同
 * @create date 2005-8-24 10:03
 * @create by zheng ze zhou
 * @注：包括担保校验等函数
 * @modify date 2005-8-31 15:23
 * @modify by zheng ze zhou
 * @注：增加了传入参数returnLink
 * @modify date 2005-9-16 14:44
 * @modify by zheng ze zhou
 * @注：增加了传入调用者的表单名字，以保存表单数据；去掉了引用工作区模式
 * @modify date 2005-12-14 10:22
 * @modify by zheng ze zhou
 * @注：get方法上传contractID时，加上encode方法，防止海外分行编码问题
 * @modify date 2006-1-6 10:36
 * @modify by zheng ze zhou
 * @注：从tools.js复制encode函数到本文件防止调用者报找不到encode函数的错误
 */

/****************************************  一些公用的变量  ***************************************************/
var PRENAME  = '_assure_'; //和OP里的统一前缀是一样的，防止此公用模块与调用者重名
var DIVNAME  = 'divAssureHidden'; //调用者表单的统一命名
var ASSUREOP = 'icbc.cmis.tfms.AA.AssureAssociationOP'; //公用担保接口对应的OP类名
var ASSUREQUERY = 'icbc.cmis.tfms.AA.AssureAssociationQuery'; //公用担保接口公用查询类名

/****************************************************************************************************************
                              以下是担保合同模块的公用校验函数
*****************************************************************************************************************/
/******************************************************
 *功能描述：在新增、修改担保合同的页面调用此函数提供担保合同的维护功能；担保合同金额校验
 *参数说明：customerCode = 客户号                                                    
 *参数说明：customerName = 客户名（用于页面显示）                                    
 *参数说明：contractID   = 业务台帐或申请表中的合同号（指ta200211002或ta200212002的值）
 *参数说明：pesudoID     = 业务伪主键（未生成业务合同号时代替）                          
 *参数说明：baseID       = 修改类业务对应的基础业务（信用证修改对应的信用证号）            
 *参数说明：employeeCode = 业务经办人（送当前session柜员）                           
 *参数说明：areaCode     = 业务所在行（送当前session所在行）                             
 *参数说明：magKind      = 该类业务对应的magKind字典项值                                  
 *参数说明：assureKind   = 被担保业务种类（da200211010）                               
 *参数说明：workdate     = 业务发生时间（送cmisdate, yyyymmdd）                         
 *参数说明：applyStage   = 业务所处阶段（0 申请 1 审批 2 准贷证 3 台帐）               
 *参数说明：actionMode   = 0 查询模式 1 可写模式 2 校验模式 3 引用工作区
 *参数说明：returnLink   = 在查询或可写模式调用接口后返回的url
 *参数说明：formName     = 调用者页面表单的名称，一般为form1，用于保存表单数据，没有时传空 
 *作者：zhengzezhou
 *创建日期：2005-8-25 16:01
 *****************************************************/
function assure_check(customerCode,customerName,contractID,pesudoID,baseID,employeeCode,areaCode,magKind,assureKind,workdate,applyStage,actionMode,returnLink,formName){
  if(actionMode=='1'||actionMode=='3'||actionMode=='0'){
    if(formName==null||formName=='')return false;
    var objForm = eval('document.all.'+formName);
    objForm.action = basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet'; 
    objForm.operationName.value = 'icbc.cmis.tfms.AA.AssureAssociationOP';
    var objDiv = eval('document.all.'+DIVNAME);
    //判断以下元素是否与调用者重复
    var obj_opAction = eval(formName+'.opAction');
    if(obj_opAction==null) {
      objDiv.innerHTML += '<input type="hidden" name="opAction" value="showList">';
    }else{
      obj_opAction.value = 'showList';
    }
    var obj_opDataUnclear = eval(formName+'.opDataUnclear');
    if(obj_opDataUnclear==null) {
      objDiv.innerHTML += '<input type="hidden" name="opDataUnclear" value="true">';
    }else{
      obj_opDataUnclear.value = 'true';
    }
    var obj_queryType = eval(formName+'.queryType');
    if(obj_queryType==null) {
      objDiv.innerHTML += '<input type="hidden" name="queryType" value="' + ASSUREQUERY + '">';
    }else{
      obj_queryType.value = ASSUREQUERY;
    }
    var obj_customerCode = eval(formName+'.customerCode');
    if(obj_customerCode==null) {
      objDiv.innerHTML += '<input type="hidden" name="customerCode" value="'+customerCode+'">';
    }else{
      obj_customerCode.value = customerCode;
    }
    var obj_customerName = eval(formName+'.customerName');
    if(obj_customerCode==null) {
      objDiv.innerHTML += '<input type="hidden" name="customerName" value="'+customerName+'">';
    }else{
      obj_customerName.value = customerName;
    }
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'contractID" value="'+contractID+'">';
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'pesudoID" value="'+pesudoID+'">';
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'baseID" value="'+baseID+'">';
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'employeeCode" value="'+employeeCode+'">';
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'areaCode" value="'+areaCode+'">';
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'magKind" value="'+magKind+'">';
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'assureKind" value="'+assureKind+'">';
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'workdate" value="'+workdate+'">';
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'applyStage" value="'+applyStage+'">';
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'actionMode" value="'+actionMode+'">';
    objDiv.innerHTML += '<input type="hidden" name="'+PRENAME+'returnLink" value="'+returnLink+'">';
    objDiv.innerHTML += '<input type="hidden" name="hasDetailLink" value="true">';
    objDiv.innerHTML += '<input type="hidden" name="returnType" value="submitOp">';
    objForm.submit();
    ;
  }else if(actionMode=='2'){ //如果是校验模式
	  //define xml object
	  var objHTTP = new ActiveXObject('Microsoft.XMLHTTP');
    var parser=new ActiveXObject('microsoft.xmldom');
    parser.async='false';
    var act = basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName='+ASSUREOP
            + '&opAction=test'
            + '&'+PRENAME+'i_customercode=' + customerCode
            + '&'+PRENAME+'i_areacode=' + areaCode
            + '&'+PRENAME+'i_newcontract=' + encode(contractID)
            + '&'+PRENAME+'i_basecontract=' + encode(baseID)
            + '&'+PRENAME+'i_checktype=' + magKind
            + '&'+PRENAME+'i_applystage=' + applyStage
            + '&xmlOutput=true&opDataUnclear=true&DSF32AS='+new Date();
    //查询
    objHTTP.Open('GET',act,false);
    objHTTP.Send();
    //查询出错处理
    if(!parser.loadXML(objHTTP.responseText)) {
      alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
      return false;
    }
    //如果session超时和交易失败，显示错误信息
    rootElement = parser.documentElement;
    if (rootElement.tagName == "error") {
      errorCode = "";
      errorMsg = "";
      for (i=0; i<rootElement.childNodes.length; i++) {
          if (rootElement.childNodes.item(i).tagName == "errorCode") {
               errorCode = rootElement.childNodes.item(i).text;
          }
          else if (rootElement.childNodes.item(i).tagName == "errorDispMsg") {
              errorMsg = rootElement.childNodes.item(i).text;
          }
      }
      alert(" 错误代码：" + errorCode + "\n错误描述：" + errorMsg);
      return false;
    }
    if(rootElement.tagName=="datas")
    {
       var result=rootElement.getAttribute("message");
       if (result == 'OK') {
  	 	   return true;
		   }
		   else {
         alert('担保控制提示信息：'+result);
         return false;
		   }
    }
  } //校验模式 end
}

/****************************************************************************************************************
                              以下是担保合同页面用到的输入域处理的公用函数
*****************************************************************************************************************/
/******************************************************
 *功能描述:校验所有必输项
 *参数说明：
 *作者：zhengzezhou
 *创建日期：2005-3-23 14:58
 *****************************************************/
function checkRequired(){
  var inputs = document.all.tags("INPUT");
  for(var i=0;i < inputs.length ; i++) {
        if(inputs[i].required == "true"){
            if(isEmpty(inputs[i].value)){
                alert(inputs[i].hint+"不能为空");
                inputs[i].focus();
                return false;
            }
        }
  }
  var selects = document.all.tags("select");
  for(var i=0;i < selects.length ; i++) {
        if(selects[i].required == "true"){
            if(isEmpty(selects[i].value)){
                alert(selects[i].hint+"不能为空");
                selects[i].focus();
                return false;
            }
        }
  }
  var textareas = document.all.tags("textarea");
  for(var i=0;i < textareas.length ; i++) {
        if(textareas[i].required == "true"){
            if(isEmpty(textareas[i].value)){
                alert(textareas[i].hint+"不能为空");
                textareas[i].focus();
                return false;
            }
        }
  }
  return true;
}
/******************************************************
 *功能描述:校验日期输入域格式
 *参数说明：自定义input域属性dateF="true"或者dataType="date"
 *作者：zhengzezhou
 *创建日期：2005-3-23 14:58
 *****************************************************/
function checkDateFormat(){
  var inputs = document.all.tags("INPUT");
  for(var i=0;i < inputs.length ; i++) {
        if(!isEmpty(inputs[i].value) && (inputs[i].dateF == "true" || inputs[i].dataType == "date")){
            var date = trimDate(inputs[i].value,8);
            if(!isDate(date)){
                alert(inputs[i].hint+"日期格式不对");
                inputs[i].focus();
                return false;
            }
        }
  }
  return true;
}
/******************************************************
 *功能描述:校验金额输入域格式
 *参数说明：自定义input域属性amountF="true"或者dataType="amount"
 *作者：zhengzezhou
 *创建日期：2005-3-23 14:58
 *****************************************************/
function checkAmountFormat(){
  var inputs = document.all.tags("INPUT");
  for(var i=0;i < inputs.length ; i++) {
        if(inputs[i].amountF == "true" || inputs[i].dataType == "amount"){
            var amount = trimNum(inputs[i].value);
            var mesg = isReal(amount,16,2);
            if(mesg !='ok'){
                alert(inputs[i].hint+"金额格式不对："+mesg);
                inputs[i].focus();
                return false;
            }
        }
  }
  return true;
}

/*调用说明：担保合同模块一些公用的trim的js函数*/
/******************************************************
 *功能描述:将所有被标记出来的金额输入域的值trim
 *参数说明：自定义input域属性amountF="true"或者dataType="amount"
 amountF="true"标记为金额输入域，amountF="false"不是金额输入域，默认
 <form name="form1" method="post" action="">
  <input type="text" name="amount1" amountF="true">
</form>
提交表单时要将金额输入域的value格式化掉，以去掉其中的逗号，以前都是手工写（有几个金额字段就要写几个trimNumber语句），现在仿照canModifyAll的形式将其自动化，只需如下动作
1 在金额输入域标记amountF自定义标记为true
例： <input type="text" name="某金额" value="123，456.12" disabled canModify=true amountF=true >
2 在submit()函数前加上trimNumAll()函数

 *作者：zheng ze zhou
 *创建日期：2004-7-20 9:08
 *****************************************************/
function trimNumAll(){
  var inputs = document.all.tags("INPUT");
  for(var i=0;i < inputs.length ; i++) {
    if(inputs[i].amountF=="true" || inputs[i].dataType == "amount"){
      inputs[i].value = delsep(inputs[i].value,',');
      inputs[i].value = trim(inputs[i].value);
    }
  }
}

/******************************************************
 *功能描述:将所有被标记出来的日期输入域的值trim
 *参数说明：自定义input域属性dateF="true"或者dataType="date"
 dateF="true"标记为日期输入域，dateF="false"不是日期输入域，默认
 <form name="form1" method="post" action="">
  <input type="text" name="date1" dateF="true">
</form>
提交表单时要将日期输入域的value格式化掉，以去掉其中的'/'，以前都是手工写（有几个日期字段就要写几个trim语句），现在只需如下动作
1 在日期输入域标记dateF自定义标记为true
例： <input type="text" name="某日期" value="2005/02/13" disabled canModify=true dateF=true >
2 在submit()函数前加上trimDateAll()函数

 *作者：zheng ze zhou
 *创建日期：2004-7-20 9:08
 *****************************************************/
function trimDateAll(){
  var inputs = document.all.tags("INPUT");
  for(var i=0;i < inputs.length ; i++) {
    if(inputs[i].dateF=="true" || inputs[i].dataType == "date"){
      inputs[i].value = trimDate(inputs[i].value,8);
    }
  }
}

/******************************************************
 *功能描述:提交表单前，将所有输入域trim的动作
 *参数说明：
 *作者：
 *创建日期：2005-3-23 14:59
 *****************************************************/
function trimFormAll(){
  trimNumAll();
  trimDateAll();
}

/******************************************************
 *功能描述:页面载入后，格式化显示所有输入域的动作
 *参数说明：自定义input域属性amountF="true"或者dataType="amount"
 *参数说明：自定义input域属性dateF="true"或者dataType="date"
 *作者：
 *创建日期：2005-3-23 14:59
 *****************************************************/
function formatShowAll(){
  var inputs = document.all.tags("INPUT");
  for(var i=0;i < inputs.length ; i++) {
    if(inputs[i].dateF=="true" || inputs[i].dataType == "date"){
      toDateShow(inputs[i],8);
    }
    if(inputs[i].amountF=="true" || inputs[i].dataType == "amount"){
      if(!isEmpty(inputs[i].value)){
        toExact2(inputs[i]);
      }
    }
  }
}

/******************************************************
 *功能描述:将所有输入域置disabled的动作
 *参数说明：
 *作者：zheng ze zhou
 *创建日期：2005-3-23 14:59
 *****************************************************/
function disableAll(){

  var inputs = document.all.tags("INPUT");
  for(var i=0;i < inputs.length ; i++) {
    inputs[i].disabled = true;
  }

   
  var selects = document.all.tags("select");
  for(var i=0;i < selects.length ; i++) {
        selects[i].disabled = true;
  }
  
  var textareas = document.all.tags("textarea");
  for(var i=0;i < textareas.length ; i++) {
        textareas[i].disabled = true;
  }
}

/******************************************************
 *功能描述:将所有输入域取消disabled的动作
 *参数说明：
 *作者：zheng ze zhou
 *创建日期：2005-3-23 14:59
 *****************************************************/
function ableAll(){
  var inputs = document.all.tags("INPUT");
  for(var i=0;i < inputs.length ; i++) {
        inputs[i].disabled = false;
  }
  var selects = document.all.tags("select");
  for(var i=0;i < selects.length ; i++) {
        selects[i].disabled = false;
  }
  var textareas = document.all.tags("textarea");
  for(var i=0;i < textareas.length ; i++) {
        textareas[i].disabled = false;
  }
}

/******************************************************
 *功能描述:将所有输入域的按照canModify动作设置
 *参数说明：
 canModify="true"设置为可以编辑，canModify="false"为不可以编辑
 <form name="form1" method="post" action="">
  <input type="text" name="a" canModify="true">
  <input type="text" name="b" canModify="false">
  <input type="text" name="c" canModify="true">
  <input type="text" name="d" canModify="false">
</form>
 *作者：
 *创建日期：
 *****************************************************/
function canModifyAll(){
  var inputs = document.all.tags("INPUT");
  for(var i=0;i < inputs.length ; i++) {
    if(inputs[i].canModify=="true")
        inputs[i].disabled = false;
      if(inputs[i].canModify=="false")
        inputs[i].disabled = true;
  }
  var selects = document.all.tags("select");
  for(var i=0;i < selects.length ; i++) {
    if(selects[i].canModify=="true")
          selects[i].disabled = false;
        if(selects[i].canModify=="false")
          selects[i].disabled = true;
  }
  var textareas = document.all.tags("textarea");
  for(var i=0;i < textareas.length ; i++) {
    if(textareas[i].canModify=="true")
          textareas[i].disabled = false;
        if(textareas[i].canModify=="false")
          textareas[i].disabled = true;
  }
}


/****************************************************************************************************************
                              以下是担保合同页面用到的摘录自其他js的实用函数
*****************************************************************************************************************/
/*调用说明：一些公用金额输入校验的js函数*/
/******************************************************
 *功能描述: 检验是否只输入正的数值（0～9 和.）
 *参数说明：
 *作者：严波
 *创建日期：
 *****************************************************/
function inputPositiveNum(){
  if (event.keyCode < 46 || event.keyCode > 57 || event.keyCode == 47) event.returnValue = false;
}

/******************************************************
 *功能描述: 把一个正数格式化成金额表达式(###,###,###.##)
 *参数说明：
 *作者：严波
 *创建日期：
 *****************************************************/
function toExactPositive(ob,len,dec) {
  var val = trimNum(ob.value);
  if((ret = isReal(val,len - dec,dec)) != "ok") { alert(ret); ob.focus(); return false; }
  if(val<0){
    alert("金额数字不能小于零");
    ob.focus();
      return false;
  }
  ob.value = exact(val,ob.size,dec);
  return true;
}
/******************************************************
 *功能描述: 在字符串str中查找字符sep,将去掉所有str串中的sep字符
 *参数说明：str 源字符串 sep 被过滤的字符 
 *作者：
 *创建日期：
 *****************************************************/
function delsep(str,sep){
  while(str.search(sep)!=-1)
    str=str.replace(sep,"");  
  return str;
}

/******************************************************
 *功能描述: 只对汉字进行unicode编码；
            为了反止tools.js没被调用者包含，故包含供本文件使用
 *参数说明：str 源字符串 返回 编码后字符串
 *作者：
 *创建日期：
 *****************************************************/

//只对汉字进行unicode编码
function encode(s) {
  if(s == null ) return s;
  if(s.length == 0) return s;

  var ret = "";
  for(i=0;i<s.length;i++) {
    var c = s.substr(i,1);
    var ts = escape(c);
    if(ts.substring(0,2) == "%u") {
      ret = ret + ts.replace("%u","@@");
    } else {
      ret = ret + c;
    }
  }
  return ret;
}
