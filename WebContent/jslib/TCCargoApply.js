// TCCargoApply.js

function query(){
  if(check()){
    if(isEmpty(form1.Apply_contractID.value)){
      alert(" 请输入申请号！ ");
      form1.Apply_contractID.focus();
      return ;
    }
    
    form1.oper.value="query";    
    document.form1.submit();
  }
}


/*选信用证号*/
function chooseLC_Code()
{
  if(isEmpty(form1.Apply_customerCode.value)){

    alert(" 请输入客户代码！ ");
    form1.Apply_customerCode.focus();
    return ;
  }
  if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return ;
  }else{
  var customerCode = form1.Apply_customerCode.value;		//客户代码 
  var ts = window.showModalDialog(encodeURL(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TC.util.queryAllLC&ask=true&customerCode="+customerCode+"&areacode="+form1.Apply_sponsorBank.value+"&domain=international&zhuxiaoflag=0&height=410&hasDetailLink=true"),window,"dialogWidth:640px;dialogHeight:458px;center:yes;help:no;status:no;resizable:no");

  if (ts != null){
     form1.LC_code.value = ts[1];		//信用证号
     form1.LC_Ctype.value=ts[6];		//信用证币种
     form1.Balance.value=ts[8];		//信用证实际余额
     Lcyxq = ts[12];		//信用证有效期
   }
  }
}
 /*信用证实际余额为空时，表未开立信用证，此时不能申请提货担保*/
function check()
{
  if(isEmpty(form1.Apply_customerCode.value))
  {
    alert(" 请输入客户代码！ ");
    form1.Apply_customerCode.focus();
    return false;
  }
  if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return false;
      }
  if(isEmpty(form1.LC_code.value)){
    alert(" 请输入信用证号！ ");
    form1.LC_code.focus();
    return false;
  }

  if(form1.Balance.value=='null'||isEmpty(form1.Balance.value)){
    alert(" 当前信用证尚未正式开立，不能进行后续业务的处理！ ");
    form1.LC_code.focus();
    return false;
  }
  
  return true;
}  
   
function add(){
   if(check()){ 
   	if(Lcyxq < sys_date){ 
   	  alert(" 当前信用证已经失效，不能进行新增！");
      return ;
    }else{
    	form1.Apply_contractID.value ="";  
      form1.oper.value="addNew";      
      document.form1.submit();
    }
  }
  
}

function chooseContractID(){
    if(isEmpty(form1.Apply_customerCode.value)){

    alert(" 请输入客户代码！ ");
    form1.Apply_customerCode.focus();
    return ;
  }if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return ;
  }
  if(isEmpty(form1.LC_code.value)){

    alert(" 请输入信用证编号！ ");
    form1.LC_code.focus();
    return ;
  } else{
    var customerCode = form1.Apply_customerCode.value;		//客户代码
    var LCID=form1.LC_code.value;
    var ts = window.showModalDialog(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TC.util.queryCargoAppID&ask=true&customerCode="
     +customerCode+"&area="+form1.Apply_sponsorBank.value+"&LC_code="+LCID+"&height=410&hasDetailLink=true",window,"dialogWidth:640px;dialogHeight:458px;center:yes;help:no;status:no;resizable:no");
   if (ts != null){
     form1.Apply_contractID.value = ts[2];			//选择提货担保申请号
   }
  }
}
   
  