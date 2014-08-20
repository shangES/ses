   function trim(str)
   {
    var returnstr="";
    if(str == "")
       return "";
    var i = 0;
    for(i = 0;i<str.length;i++)
    {
        if(str.charAt(i) == ' ')
        {
            continue;
         }
        break;
    }
    str = str.substring(i,str.length);
    if(str =="")
       return "";
    for(i=str.length-1;i>=0;i--)
    {
        if(str.charAt(i)==' ')
        {
            continue;
         }
         break;
     }
     returnstr = str.substring(0,i+1);
     return returnstr;
  }

   function isBetween (val,lo,hi){
      if ((val<lo) || (val > hi)) { return(false);}
      else {return(true);}
   }

   function isDate(theStr) {
         theStr=trim(theStr);
         if (theStr.length!=8)   return false;
         //防止非数字式的"-,+"号
		if(isNaN(theStr)){
  			return false;
		}
		//防止数字式的".,+,-"号
		if(theStr.indexOf(".")!=-1 || theStr.indexOf("-")!=-1 || theStr.indexOf("+")!=-1){
  			return false;
		}
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

    function isDigit(theNum) {
       var theMask = '0123456789';
       if (isEmpty(theNum)) return(false);
       else if (theMask.indexOf(theNum) == -1) return(false);
       return(true);
    }

    function isEmpty(str) {
       str = trim(str);
       if ((str==null) || (str.length == 0)) return true;
       else return(false);
    }

    function isInt(theStr) {
       var flag = true;
       theStr = trim(theStr);
       if(theStr.indexOf(" ")>0){
     	 return false;
       }
       if(isNaN(theStr*1))
         return false;
       
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

function inputNumber() {
	if (event.keyCode < 45 || event.keyCode > 57 || event.keyCode == 47) event.returnValue = false;
}

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
	if (ob.readOnly == false ) ob.value = ts;
	ob.select();
}

function toNumber2(ob) {
	var ts = trimNum(ob.value);
	if (ob.readOnly == false ) ob.value = ts;
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
  str=trim(str);
  if(str.length < 8) return false;
           //防止非数字式的"-,+"号
		if(isNaN(str)){
  			return false;
		}
		//防止数字式的".,+,-"号
		if(str.indexOf(".")!=-1 || str.indexOf("-")!=-1 || str.indexOf("+")!=-1){
  			return false;
		}
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
      str=trim(str);
         //防止非数字式的"-,+"号
		if(isNaN(str)){
  			return false;
		}
		//防止数字式的".,+,-"号
		if(str.indexOf(".")!=-1 || str.indexOf("-")!=-1 || str.indexOf("+")!=-1){
  			return false;
		}
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
  str=trim(str);
  if(str*1 + "" == "NaN")
    return false;
  //防止非数字式的"-,+"号
		if(isNaN(str)){
  			return false;
		}
		//防止数字式的".,+,-"号
		if(str.indexOf(".")!=-1 || str.indexOf("-")!=-1 || str.indexOf("+")!=-1){
  			return false;
		}
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
 if (v==null) 
  v=window.event.srcElement;
 if ((new String(v.tagName)).toLowerCase()=="textarea")
 	return checkAreaLength(v,v.maxlength);

 var s = v.value;
 var l = v.maxLength;
 var len = 0;
   for(i=0;i<s.length;i++){
     var c = s.substr(i,1);
     var ts = escape(c);
     if(ts.substring(0,2) == "%u") {
      len+=2;
     } else {
      	if(ts.substring(0,3) == "%D7"){
      	     len+=2;
     	}      	
	else{
 	     len+=1;
	}
     }
   }
   if(l>=len){
     return;
   }
   else{
	var aaa=Math.floor(l/2);
	alert("该输入项插入值过长！最多"+l+"个字符或"+aaa+"个汉字。");

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
     else if(ts.substring(0,3) == "%D7"){
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
	var aaa=Math.floor(l/2);
	alert("该输入项插入值过长！最多"+l+"个字符或"+aaa+"个汉字。");  	
    
    v.focus();
    return false;
  }
  return true;
}

function verifyAccount(arg1){
  //帐号不能为空
  if (isNaN(arg1)) {
    alert("帐号为空，请输入！");
    return false;
  }
  //小于17位的帐号视为老帐号，不校验
  if (arg1.length < 17) return true;
  //新帐号必需为19位
  if (arg1.length < 19){
    alert("请输入19位帐号!");
    return false;
  }
  var num_so;
  var num_ob;
  num_so=arg1.substr(0,1)*11+arg1.substr(1,1)*13+arg1.substr(2,1)*17+arg1.substr(3,1)*19+
    arg1.substr(4,1)*23+arg1.substr(5,1)*29+arg1.substr(6,1)*31+arg1.substr(7,1)*37+
    arg1.substr(8,1)*41+arg1.substr(9,1)*43+arg1.substr(10,1)*47+arg1.substr(11,1)*53+
    arg1.substr(12,1)*59+arg1.substr(13,1)*61+arg1.substr(14,1)*67+arg1.substr(15,1)*71+
    arg1.substr(16,1)*73;
  num_ob=97-num_so%97;
  if (arg1.substr(17,2)!=num_ob) {
      if(num_ob < 10) {
        num_ob = "0" + num_ob;
      }
    	alert("帐号错误，根据帐号的前17位校验码应为：" + num_ob);
	    form2.accountNumber.focus();
    	return false;
  }
  return true;
}



  function checkday(str){
    if(str.length>0&&isDate(str)){
      var x= str.substring(0,4)/1;
      var y= str.substring(4,6)/1-1;
      var z= str.substring(6,8)/1;
      var date1=new Date(x,y,z);
      if(date1.getDay()== '0'){
        alert("这天是星期天");
      }
      else if(date1.getDay()=='6'){
        alert("这天是星期六");
      }
    }
    else{
      alert("日期输入有误");
    }
 }

  function checkdate(obj){
      var str = obj.name;
	  var values = obj.value;
	   var str1 = "";
	  if(!isDate(values)){
	     if(str == "ta200271017")
		    str1 = "保证有效起始日输入不合法，请修改";
		 else if(str == "ta200271018")
		    str1 = "保证有效到期日输入不合法，请修改";
		 else if(str == "ta200281014")
		    str1 = "公证日期输入不合法，请修改";
		 else if(str == "ta200281015")
		    str1 = "抵质押合同起始日输入不合法，请修改";
		 else if(str == "ta200281016")
		    str1 = "抵质押合同到期日输入不合法，请修改";
		 else if(str == "ta200281022")
		    str1 = "评估时间输入不合法，请修改";
		 else if(str == "ta200271032")
		    str1 = "保证合同实际有效到期日输入不合法，请修改";
		 else if(str == "ta200281032")
		    str1 = "抵质押合同实际到期日输入不合法，请修改";
		 alert(str1);
		 obj.focus();
		 return false;
	  }
  }

function checkExpDate(vexp){
//检查到期日标识是否为RGF或者YYYY.MM.DD格式的日期。
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

/*控制输入数字只能为正数*/
function inputNumber2() {
  if (event.keyCode < 46 || event.keyCode > 57 || event.keyCode == 47) event.returnValue = false;
}

/*  判断该输入框输入的是否包含全角字符
*/
function checkSBCcaseChar(obj){
	var str = obj.value;
	var arr=str.match(/[^\x00-\xff]/ig);
	if(arr != null){
  	   alert("该输入框输入不合法，请输入半角字符");
  	   obj.focus();
  	   return false;
  	} else {
  	   return true;
  	}
} 
 //Danny 20041202
    function isWorkTime(theStr) {
      if(theStr.length != 4)
      	return false;
      var h = theStr.substring(0,2);
	  var m = theStr.substring(2,4);
	  //alert(h);
	  //alert(m);
	  if (!isBetween(h,0,23) || !isBetween(m,0,59))
		return false;
	  else 
	    return true;	
    }

//Danny 20041203 新增
//判断用户输入是否为非负的整数(一般用于判断天数的输入)
function inputInteger() {
	if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;	
}