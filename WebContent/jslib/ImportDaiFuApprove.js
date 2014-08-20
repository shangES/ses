// ImportDaiFuApprove.js

// ImportDaiFu.js

function query(){
    
    if(isEmpty(form1.Apply_customerCode.value)){
        alert("请选择客户");
        return;
   }
   if(isEmpty(form1.Apply_sponsorBank.value)){
	    alert("请选择业务所属行");
	    return;
   }
   if(form1.Apply_kind.value=="24"){
	    if(isEmpty(form1.LCCode.value)){
	    alert("请选择信用证号。");
	    form1.LCCode.focus();
	    return;
      }
   }else{
        if(isEmpty(form1.ta270050003.value)){
        alert("请选择结算方式。");
        form1.ta270050003.focus();
        return;
      }
   }
   if(isEmpty(form1.Apply_contractID.value)){
	    alert("请选择代付业务号！");
	    return;
   }
    clearSessionData();  
    combineParameters();   
    form1.Isquery.value='1';
    form1.oper.value="showQueryView";   
    form1.submit();
     
}

   
   
function add(){
  
   if(isEmpty(form1.Apply_customerCode.value)){
        alert("请选择客户");
        return;
   }
   if(isEmpty(form1.Apply_sponsorBank.value)){
	    alert("请选择业务所属行");
	    return;
   }
   if(form1.Apply_kind.value=="24"){
	    if(isEmpty(form1.LCCode.value)){
	    alert("请选择信用证号。");
	    form1.LCCode.focus();
	    return;
      }
   }else{
        if(isEmpty(form1.ta270050003.value)){
        alert("请选择结算方式。");
        form1.ta270050003.focus();
        return;
      }
   }
   if(form1.Apply_kind.value=="24"){
   if(form1.LCRemain.value==0){
        alert("信用证已结清，不能新增！");
        return;
   }  
   }
    if(!patchCheck(form1.Apply_sponsorBank.value,form1.Apply_customerCode.value,form1.Apply_kind.value,'01','')){
	  return;
    }
    clearSessionData();  
    combineParameters();
    form1.Isquery.value='0';
    form1.Apply_contractID.value ="";
    form1.oper.value="showInsertView";
    form1.submit();
        
        
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
        if(form1.Apply_kind.value=="24"){
        if(isEmpty(form1.LCCode.value)){
        alert("请选择信用证号。");
        form1.LCCode.focus();
        return;
      }
        ts = window.showModalDialog(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TC.util.queryRFNO&customerCode="+form1.Apply_customerCode.value+"&xyzcode="+encode(form1.LCCode.value)+"&areacode="+form1.Apply_sponsorBank.value+"&applykind="+form1.Apply_kind.value+"&height=400&width=500&hasDetailLink=true&time="+new Date(),window,"dialogWidth:500px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no");
       
       }
        else{
        if(isEmpty(form1.ta270050003.value)){
        alert("请选择结算方式。");
        form1.ta270050003.focus();
        return;
      }
        ts=window.showModalDialog(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TC.util.queryRFNO&customerCode="+form1.Apply_customerCode.value+"&jiesuantype="+form1.ta270050003.value+"&areacode="+form1.Apply_sponsorBank.value+"&applykind="+form1.Apply_kind.value+"&height=400&width=500&hasDetailLink=true&time="+new Date(),window,"dialogWidth:500px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no");
        
        }
        if(ts!=null){
          form1.Apply_contractID.value = ts[1];
        }
      }
     
   }
   
   function f_changelccode(){
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
	     if(ts[8]==""){
	       alert("该信用证未开立，不能做后续业务！");
	       return;
	     }else{
	     form1.LCRemain.value=ts[8];
	     form1.LCCode.value = ts[1];
	     form1.Apply_contractID.value="";
	     }
	   }
     }
    
  }
  
  function changeCheckOut(){
    if(form1.ta270050003.value==1){
    alert("非信用证项下进口代付不能选择信用证方式！");
    form1.ta270050003.value="";
    return;
    }
    form1.Apply_contractID.value="";
  }