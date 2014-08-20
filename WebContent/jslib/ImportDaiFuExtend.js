
var _status="";

var _status1="";
function query(){
if(isEmpty(form1.Apply_customerCode.value)){
        alert("请选择客户");
        return;
      }
      else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
      }
      else if(isEmpty(form1.Apply_baseID.value)){
        alert("请选择代付业务号。");
        return;
        
      }
      else if(isEmpty(form1.Apply_contractID.value)){
        alert("请选择调查资料。");
        return;
      }
      else{
        clearSessionData();  
        combineParameters();
        form1.Isquery.value='1';
        form1.oper.value="showQueryView";
        form1.submit();
      }
}

   
   
function add(){
	
   if(isEmpty(form1.Apply_customerCode.value)){
        alert("请选择客户");
        return;
      }
      else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
      }else if(isEmpty(form1.Apply_baseID.value)){
        alert("请选择代付业务号。");
        
        return;
      }else if(_status!=2&&_status!=3){
      	  alert("代付状态不是代付确认、代付付款不能新增！");
      	  return;
      }
      else if(_status1==1)
      {
        alert("该代付有正在申请中的代付展期申请或存在未确认的代付展期台账，不能新增！");
        return;
      }
      else{
        if(form1.addflag.value==1){
        alert("该代付有后续业务不能新增！");
        return;
      }
        clearSessionData();  
        combineParameters();
        form1.Isquery.value='0';
        form1.Apply_contractID.value ="";
        form1.oper.value="showInsertView";
        form1.submit();
        
        }
}

function chooseContractID(){
   
    
     if(isEmpty(form1.Apply_customerCode.value)){
        alert("请选择客户");
        return;
      }
       else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
      }
      else{
        var ts;        
        ts = window.showModalDialog(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.tfms.CA_util.RFApplyExtendQuery&enpcode="+form1.Apply_customerCode.value+"&LCCode="+encode(form1.LCCode.value)+"&area="+form1.Apply_sponsorBank.value+"&jiesuantype="+form1.ta270050003.value+"&baseID="+form1.Apply_baseID.value+"&height=400&width=300&hasDetailLink=true&time="+new Date(),window,"dialogWidth:330px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no");
              
        if(ts!=null){
          form1.Apply_contractID.value = ts[2];
        }
      }
     
   }
   
   function f_changelccode(){
    if(form1.ta270050003.value!="1")
    {
     alert("非信用证方式不能选择信用证号！")
     form1.Apply_baseID.value="";
     return ;
    }
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
	    if(ts[8]==""||ts[8]==0){
	       alert("该信用证未开立或已结清，不能做后续业务！");
	       return;
	     }else
	     form1.LCCode.value = ts[1];
	   }
     }
    
  }
  
  function chooseBaseContractID(){
     if(isEmpty(form1.Apply_customerCode.value)){
        alert("请选择客户");
        return;
      }
       else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
      }
      else if(isEmpty(form1.ta270050003.value)){
      	  alert("请选择结算方式！");
      	  return;
      }else if(form1.ta270050003.value=="1"&&isEmpty(form1.LCCode.value)){
      	   alert("请选择信用证号！");
      	   return;
      }
      else{
        var ts = window.showModalDialog(encodeURL(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TC.util.QueryRFNO_Extend&ask=true&customerCode="+form1.Apply_customerCode.value+"&areacode="+form1.Apply_sponsorBank.value+"&xyzcode="+form1.LCCode.value+"&jiesuantype="+form1.ta270050003.value+"&domain=international&zhuxiaoflag=0&width=500&height=410&hasDetailLink=true"),window,"dialogWidth:640px;dialogHeight:458px;center:yes;help:no;status:no;resizable:no");
	
	   if (ts != null){
	     form1.addflag.value=ts[8];
	     form1.Apply_baseID.value = ts[1];
	     form1.Apply_contractID.value="";
	     _status=ts[7];
	     _status1=ts[8];
	   }
      }
  }
  
  function changeCheckOut(){
    form1.LCCode.value="";
    form1.Apply_baseID.value ="";
    
  }