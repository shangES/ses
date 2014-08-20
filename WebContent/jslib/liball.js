/*update by chenzhaofang on 2006.12.31*/
/************************* create by yhua on 2002/04/06 **********************/
/*六位日期合法性检验*/
function isDateYYYYMM(theStr) {
	  if (theStr.length!=6)   return false;
	  var y = theStr.substring(0,4);
	  var m = theStr.substring(4,6);
	  if (isInt(m)==false || isInt(y) ==false) {return(false);}
	  if (y<1900 ||y>3000)  {return(false);}
	  if ( m>12 )  {return(false);}
	  return(true);
}
/*比较大小*/
function isFstBigthanSec(firstS, secondS) {
	  if( firstS >secondS )
		  return(true);
	  else
		  return false;
}

/**********************create by gj 200704 评级  判断评价内容能否修改或删除*********/
function mod_able(customercode,pj_date,pj_kind,mk_code,key_mk,key,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=mod_able&customercode="+customercode+"&pj_date="+pj_date+"&customer_kind="+pj_kind+"&mk_code="+mk_code+"&key_mk="+key_mk+"&key="+key+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
       var node = nodes.item(0);
       var able_str=node.getAttribute("able_str"); 
      return able_str;
}

/**********************create by wz 20041110 小企业评级  根据客户号取客户类型*********/
function get_customer_kind(customercode,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=check_customercode&customercode="+customercode+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
       var node = nodes.item(0);
       var customer_kind=node.getAttribute("customer_kind");       
      return customer_kind;
/***  customer_kind：一般 10；房开 20；外贸 50；金融 01； 教医 40； 小企业:60  建安：30**********/   
}
/*取历史表客户类型*/
function get_ls_kind(customercode,pjdate,jldate,baseWebPath){
     var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=get_ls_kind&customercode="+customercode+"&pjdate="+pjdate+"&jldate="+jldate+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
       var node = nodes.item(0);
       var customer_kind=node.getAttribute("customer_kind");       
      return customer_kind;
/***  customer_kind：一般 10；房开 20；外贸 50；金融 01； 教医 40； 小企业:60  建安：30**********/      
}

//update by wz 20050414 
/** biaozhun:  10:一般,房开,建安,外贸 30:教医 01:金融 60:小企业 70:政府    **/
function check_customer_new(customercode,biaozhun,baseWebPath){
     var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=check_customer_new&customercode="+customercode+"&biaozhun="+biaozhun+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
       var node = nodes.item(0);
       var ret=node.getAttribute("ret");       
      return ret;
/**** 第一位：客户类型：一般 10；房开 20；外贸 50；金融 01； 教医 40； 小企业:60  建安：30      
/***  第二位：是否正确标志： 0:正确  1：错误
/***  从第三位开始：信息 example : 该客户为"+kind_name+"，不能在该页面操作**/      
}

/* add by chenzhaofang 20061009 
 * ret:  评价类型(小类)流程中
**/      
function get_customer_kind_child(customercode,pjdate,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser  = new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=get_customer_kind_child&customercode="+customercode+"&pjdate="+pjdate+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
      var node = nodes.item(0);
      var ret=node.getAttribute("customer_kind");       
      return ret;
}

/* add by chenzhaofang 20061128 
 * ret:  评价类型(小类)包含历史表和流程中
**/      
function get_customer_kind_all(customercode,pjdate,querytype,querydate,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser  = new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=get_customer_kind_all&customercode="+customercode+"&pjdate="+pjdate+"&query_type="+querytype+"&query_date="+querydate+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
      var node = nodes.item(0);
      var ret=node.getAttribute("customer_kind");       
      return ret;
}

/* add by chenzhaofang 20070426 
 * ret:  评级客户类型，客户档案和参数表中的
**/      
function get_customer_type(customercode,pjdate,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser  = new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=get_customer_type&customercode="+customercode+"&pjdate="+pjdate+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
      var node = nodes.item(0);
      var ret=node.getAttribute("customer_kind");       
      return ret;
}

/* add by chenzhaofang 20061016 
 * ret:  判断评级专管员1-是，0-否
**/      
function check_administrator(customercode,employeecode,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser  = new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=check_administrator&customercode="+customercode+"&employeecode="+employeecode+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
      var node = nodes.item(0);
      var ret=node.getAttribute("ret");       
      return ret;
}

/* add by chenzhaofang 20070625 
 * ret:  判断主管信贷员1-是，0-否
**/      
function check_admin(customercode,employeecode,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser  = new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=check_admin&customercode="+customercode+"&employeecode="+employeecode+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
      var node = nodes.item(0);
      var ret=node.getAttribute("ret");       
      return ret;
}

/* add by chenzhaofang 20061031 
 * ret:  判断是否做过初评1-是，0-否
**/      
function check_cp(customercode,pjdate,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser  = new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=check_cp&customercode="+customercode+"&pjdate="+pjdate+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
      var node = nodes.item(0);
      var ret=node.getAttribute("ret");       
      return ret;
}

/* add by chenzhaofang 20061110
 * ret:  判断是否有定性定量00两位
**/      
function check_dxdl(customerkind,modulecode,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser  = new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=check_dxdl&customer_kind="+customerkind+"&module_code="+modulecode+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
      var node = nodes.item(0);
      var ret=node.getAttribute("ret");       
      return ret;
}
/* add by chenzhaofang 20061230 
 * ret:  判断能否修改主管、托管信贷员1：否 0：是
**/      
function change_admin(customer_code,employee_code,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser  = new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=change_admin&customer_code="+customer_code+"&employeecode="+employee_code+"&time="+new Date();
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
      var node = nodes.item(0);
      var ret=node.getAttribute("ret");       
      return ret;
}

/* add by chenzhaofang 20070702 
 * ret:判断财务报表是否被锁定1-是，0-否
**/      
function report_locked(customercode,pjdate,reporttype,baseWebPath){
      var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser  = new ActiveXObject("microsoft.xmldom");
      tmp = "opAction=report_locked&customercode="+customercode+"&pjdate="+pjdate+"&report_type="+reporttype+"&time="+new Date();                 
      objHTTP.open('GET',baseWebPath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.eval.EA.EA_mfevl_999_Op&xmlOutput=true&opDataUnclear=true&'+tmp,false);          
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
      var node = nodes.item(0);
      var ret=node.getAttribute("ret");       
      return ret;
}

/*****************************************************************************/
/*去空格*/
function trim(str){
    var returnstr="";
    if(str == "")
       return "";
    var i = 0;
    for(i = 0;i<str.length;i++)
    {
        if(str.charAt(i) == ' '){
            continue;
         }
        break;
    }
    str = str.substring(i,str.length);
    if(str =="")
       return "";
    for(i=str.length-1;i>=0;i--)
    {
        if(str.charAt(i)==' '){
            continue;
         }
         break;
     }
     returnstr = str.substring(0,i+1);
     return returnstr;
}
/*一个数是否在某个区间内*/
function isBetween (val,lo,hi){
	  if ((val<lo) || (val > hi)) { return(false);}
	  else {return(true);}
}
/*判断是否有效日期格式八位*/
function isDate(theStr) {
	 if (theStr.length!=8)   return false;
	  var y = theStr.substring(0,4);
	  var m = theStr.substring(4,6); if(m/1==0||m/1>12) return false;
	  var d = theStr.substring(6,8);
	  var maxDays = 31;
	  if (isInt(m)==false || isInt(d)==false || isInt(y) ==false) {
		  return(false);}
	  else if (y<1900 ||y>3000)  {return(false);}
	  else if (m==4 || m==6 || m==9 || m==11) maxDays = 30;
	  else if (m==2) {
			  if (y%4>0) maxDays= 28;
			  else if (y % 100 ==0 && y % 400 >0) maxDays = 28;
			  else  maxDays = 29;
			}
	 if (isBetween(d,1,maxDays) == false) { return(false);}
	 else {return(true);}
}
/*查验时间*/
function isTime(theStr) {
  var colonDex = theStr.indexOf(':');
  if ((colonDex < 1) || (colonDex > 2)) {return(false);}
  else {
	 var hh = theStr.substring(0,colonDex);
	 var ss = theStr.substring(colonDex+1,theStr.length);
	 if ((hh.length<1) || (hh.length>2) || (!isInt(hh))) {return(false);}
	 else if ((ss.length<1) || (ss.length>2) || (!isInt(ss))) {return(false);}
	 else if ((!isBetween(hh,0,23)) || (!isBetween(ss,0,59))) {return(false);}
	 else {return(true);}
  }
}
/*数字0-9*/
function isDigit(theNum) {
   var theMask = '0123456789';
   if (isEmpty(theNum)) return(false);
   else if (theMask.indexOf(theNum) == -1) return(false);
   return(true);
}
/*判断为空*/
function isEmpty(str) {
   str = trim(str);
   if ((str==null) || (str.length == 0)) return true;
   else return(false);
}
/*判断为整数*/
function isInt(theStr) {
   var flag = true;
   theStr = trim(theStr);
   if (isEmpty(theStr))  flag=true;
   else
	{ if (theStr.substring(0,1)=='-')   //负数
		  theStr = theStr.substring(1);
	  for (var i=0;i<theStr.length;i++){
	   if (isDigit(theStr.substring(i,i+1))==false) {
		  flag = false;
		  break;
		  }
	   }
	 }
	 return flag;
 }
 
 /*数字整数位小数位长度限制*/
 function isReal(theStr,intLen,decLen) { //(输入值,整数部分长度,小数部分长度)
	 theStr = trim(theStr);
	 //test if chars are acceptable
	 if(theStr.indexOf(" ")>0){
	   return "数字中间不允许出现空格";
	 }
	 if(isNaN(theStr*1))
	   return "数字输入不合法";
	 
	 var v_ret=0;
	 var ret_mesg="ok";
	 
	 if (theStr.substring(0,1)=='-')   //负数
		  theStr = theStr.substring(1);
//xgl begin 20030407
	  if(theStr.substring(0,1)=='.'){
		theStr = '0'+theStr;
	 }
	  //xgl end

	 if (!isEmpty(theStr)) {
	 var dot1st = theStr.indexOf('.');
	 var dot2nd = theStr.lastIndexOf('.');

	 if (dot1st == -1) {
		 if (!isInt(theStr)) v_ret=1;
		 else if (theStr.length>intLen) v_ret=2;
		 else v_ret=0;
	 }
	 else if (dot1st != dot2nd) v_ret=1;
	 else if (dot1st==0) v_ret=1;
	 else {
		  var intPart = theStr.substring(0,dot1st);
		  var decPart = theStr.substring(dot2nd+1);
		  if (intPart.length > intLen) v_ret=2;
		  else if (decPart.length > decLen) v_ret=3;
		  else if (!isInt(intPart) || !isInt(decPart)) v_ret=1;
		  else if ( isEmpty(decPart)) v_ret=1;
		  else v_ret=0;
	 }
	}
	 if (v_ret==0) ret_mesg="ok";
	 else if (v_ret==1) ret_mesg="数字输入不合法";
	 else if (v_ret==2) ret_mesg="整数部分超长(最多"+intLen+"位)";
	 else if (v_ret==3) ret_mesg="小数部分超长(最多"+decLen+"位)";
	 else  ret_mesg="检查出错";

	 return ret_mesg;
 }
 /*贷款借据*/
 function check_jjj(para_code) {
  var local_code = para_code.substr(0,8);
  var de_code = para_code.substr(8,1);
  var verify_code = para_code.substr(9,1);
  if (para_code.length > 10) {
    return true;
  }
  if (de_code != '-') {
    return "第9位应为分隔符 '-'！";
  }
  total = 0;
  for (i=0;i<8 ;i++ ) {
    switch(i+1) {
      case 1:
        w = 3;break;
      case 2:
        w = 7;break;
      case 3:
        w = 9;break;
      case 4:
        w = 10;break;
      case 5:
        w = 5;break;
      case 6:
        w = 8;break;
      case 7:
        w = 4;break;
      case 8:
        w = 2;break;
      default:
        w = 0;break;
    }
    s = local_code.substr(i,1);
    if(s >= '0' && s <= '9') {
      n = s*1;
    } else if (s >='A'&& s <='Z') {
      n = s.charCodeAt(0) - 55;
    } else {
      return "技监局编码主体部分输入不正确!";
    }
    total = total + w * n;
  }
  goal = total % 11;
  switch (goal) {
    case 0:
      rst = '0'; break;
    case 1:
      rst = 'X'; break;
    default:
      rst = (11 - goal) + ""; break;
  }
  if(rst==verify_code) {
    return true;
  } else {
    return "技监局编码的校验码部分不正确。根据当前主体部分,校验码应为" + rst;
  }
}

/*去逗号*/
var space = "                                                                ";
var zero = "000000000000000000000000000000000000000000000000000000000000000";
function trimNum(str){
    var returnstr="";
    str = "" + str;
    for(i = 0;i<str.length;i++)
    {
        if(str.charAt(i) != ' ' && str.charAt(i) != ',')
        {
            returnstr += str.charAt(i);
         }
    }
     return returnstr;
}

/*只能输入数字*/
function inputNumber() {
	if (event.keyCode < 45 || event.keyCode > 57 || event.keyCode == 47) event.returnValue = false;
}
/*格式化整数部分和小数部分*/
function exact(val,len,decimal) {
	var nav = 0;

	if(decimal == null) decimal = 2;
	var scale = Math.pow(10, decimal);
	var t = Math.round(parseFloat(val) * scale);
	if(t < 0){ nav = 1; t = 0 - t; }
	var tStr = "" + t;
	if(tStr == "NaN") return "";

	if(tStr.length <= decimal) {
	  tStr = zero.substr(0,decimal - tStr.length + 1) + tStr;
	}

  var str = "";
  if(decimal > 0) {
  	str = "." + tStr.substring(tStr.length - decimal,tStr.length);
  }

	for(var i=tStr.length-decimal-1, j=0 ; i>=0; i--) {
		if(++j > 3) {
			str = "," + str;
			j = 1;
		}
		str = tStr.substring(i, i+1) + str;
	}
	if(nav == 1) str = "-" + str;
	str = space.substr(0,len - str.length + 1) + str;
	return str;
}

function toExact(ob,len,dec) {
	var val = trimNum(ob.value);
	if((ret = isReal(val,len - dec,dec)) != "ok") { alert(ret); ob.focus(); return false; }
	ob.value = exact(val,ob.size,dec);
	return true;
}

function toExact2(ob) {
	var val = trimNum(ob.value);
  var len = ob.dataLen;
  var dec = ob.decLen;
	if((ret = isReal(val,len - dec,dec)) != "ok") {event.cancelBubble=true ;ob.focus(); alert(ret);  return false; }
	ob.value = exact(val,ob.size,dec);
}

function allExact() {
  var allInputs = document.all.H7D4F6Z;
  if(allInputs == null) return;
  if(allInputs.length == null) {
    toExact2(allInputs);
  } else {
    for (var i = 0; i < allInputs.length; i++) {
      toExact2(allInputs(i));
    }

  }
}

function toNumber(ob) {
	var ts = trimNum(ob.value);
	ob.value = ts;
	ob.select();
}

function toNumber2(ob) {
	var ts = trimNum(ob.value);
	ob.value = ts;
}

function allToNumber() {
  var allInputs = document.all.H7D4F6Z;
  if(allInputs == null) return;
  if(allInputs.length == null) {
    toNumber2(allInputs);
  } else {
    for (var i = 0; i < allInputs.length; i++) {
      toNumber2(allInputs(i));
    }

  }
}

function isDateValid1(str){
  if(str.length < 8) return false;
  var sy = str.substring(0,4);
  var iy = sy/1;
  var sm =str.substring(4,6);
  var im = sm/1;
  var sd = str.substring(6,8);
  var id = sd/1;
  var month1 = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
  var month2 = new Array(31,29,31,30,31,30,31,31,30,31,30,31);
  if((iy%4 == 0)||(iy%400 ==0)||(iy%100 == 0)){
    if((id <= month2[im-1])&&(id >=1 ))
   	  return true;
    else
      return false;
  }
  else{
    if((id <= month1[im-1])&&(id >= 1))
   	  return true;
   else
      return false;
  }
}
/***************************************************************/


/*******************判断输入年月日是否比当前系统时间大**********/
/*                                                             */
/*     输入参数为8位字符窜 exp:20050606                        */
function isDateValid2(str){
  var now = new Date()
  var year = now.getYear()
  var month = now.getMonth()+1;
  var day = now.getDate()
  var today =""+year;
  today += ((month<10)?"0":"")+month;
  today += ((day<10)?"0":"")+day;

  if (str >= today) return true;
  return false;
}
/***************************************************************/

/*********************判断字符串是否为数字*********************/
function isNumberCode(str){
  var len;
  len = str.length;
  for(var i=0;i<len;i++)
  {
      if((str.charAt(i) < '0') || (str.charAt(i) > '9'))
      {
        return false;
      }
  }
  return true;
}


/*******************判断8位、6位或者4位日期**********************add by wbn***********/
function isDateValid(str){
  var sy = str.substring(0,4);
  var iy = sy/1;
  var sm = str.substring(4,6);
  var im = sm/1;
  var sd = str.substring(6,8);
  var id = sd/1;
  if(str.length == 8){
     if(isDate(str)){
   	return true;
     }else{
        return false;
     }
  }else if(str.length == 6){
    if (iy<1900 ||iy>3000 ||im==0 ||im>12){
       return false;
    }else{
       return true;
    }
  }else if(str.length == 4){
    if (iy<1900 ||iy>3000){
       return false;
    }else{
       return true;
    }
  }else return false;
}

//将日期转换为加"/"
function toDateShow(ob,len) {
       var val = ob.value;
       if (isEmpty(val)) return true;
       if (len==6) val=val+"01";
       if (!isDate(val)) {
         alert("日期输入不合法");
         ob.focus();
         return false;
       }
        if (len==6)
           ob.value = val.substring(0,4)+"/"+val.substring(4,6);
	else
            ob.value = val.substring(0,4)+"/"+val.substring(4,6)+"/"+val.substring(6,8);
}

function toDateEdit(ob,len) {
       var val = ob.value;
       if (len==6 && val.length!=7 ) return true;
       if (len==8 && val.length!=10 ) return true;
       ob.value = trimDate(val,len);
       ob.select();
}

function trimDate(str,len) {
       if (len==8 && str.length!=10) return str;
       if (len==6 && str.length!=7) return str;
       if (len==6)
         return str.substring(0,4)+str.substring(5,7);
       else
         return str.substring(0,4)+str.substring(5,7)+str.substring(8,10);
}
/**************判断报表日期是否合法，输入年月,00月是合法的,added by lzq*/
function isReportDateValid(str){
  if(isNaN(str)){
    return false;
  }
  if(!isNumberCode(str)){
    return false;
  }
  var sy = str.substring(0,4);
  var iy = sy/1;
  var sm = str.substring(4,6);
  var im = sm/1;
  if(str.length == 6){
    if (iy<1900 ||iy>3000 ||im>12){
       return false;
    }else{
       return true;
    }
  }else return false;
}

function checkLength(v){
	var s = v.value;
	var l = v.maxLength;
	var len = 0;
	  for(i=0;i<s.length;i++){
	    var c = s.substr(i,1);
	    var ts = escape(c);
	    if(ts.substring(0,2) == "%u") {
	     len+=2;
	    } else {
	     len+=1;
	    }
	  }
	  if(l>=len){
	    return;
	  }
	  else{
	     alert("该输入项插入值过长！最多"+l+"个字符");
	    v.focus();
	  }
}


function checkAreaLength(v,l){
	var s= v.value;
	var temlen=0;
	var len = 0;
	for(i=0;i<s.length;i++){
	    var c = s.substr(i,1);
	    var ts = escape(c);

	    if(ts.substring(0,2) == "%u"){
	     len+=2;
	     len+=temlen;
	     temlen=0;
	    }
	    else if(ts.substring(0,3) == "%0D"){
	    	temlen+=1;
	    }
	    else if(ts.substring(0,3) == "%0A"){
	        temlen+=1;
	    }
	    else if(ts.substring(0,3) == "%20"){
	    	temlen+=1;
	    }
	     else{
	     len+=1;
	     len+=temlen;
	     temlen=0;
	    }
	  }
	 if(len>l){
	   alert("插入值过长！最多"+l+"个字符");
	    v.focus();
	 }
}

