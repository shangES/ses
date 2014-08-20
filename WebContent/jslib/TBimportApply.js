// TBimportApply.js

function query(){
 	if(trim(form1.Apply_customerCode.value) == ""||form1.Apply_customerCode.value == null){
       alert("请选择客户!");
       return;
     }
     if(trim(form1.Apply_contractID.value) == ""||form1.Apply_contractID.value == null){
       alert("请选择合同申请号!");
       return;
     }else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
      }else{
     	if(form1.Apply_kind.value=='17'&&form1.Apply_sub.value=='1'){
      	if(form1.TA270030028.value==""){
      		alert("请先选择合同号");
      		return;
      	}else{
      		form1.oper.value="query";       
		       form1.NQ.value="query";
		       form1.submit();
      	}
      	
      }else {
      //modify by zap
       //form1.oper.value="query";       
       //form1.NQ.value="query";
       //form1.submit();
       //end by zap
       // form1.Apply_contractID.value ="";
        clearSessionData();  
        combineParameters();
        form1.oper.value="showQueryView";
            form1.Isquery.value="1";
         form1._querypermute.value="1";
        //form1.NQ.value="showQueryView";
        form1.submit();
     }
    }
}

   
   
function add(){
//add by zap
 combineParameters();//组装primayInfo
 //end by zap
  if(trim(form1.Apply_customerCode.value)== ""||form1.Apply_customerCode.value == null)  {
       alert("请选择客户");
       return;
     }else if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
      }else{
      if(form1.Apply_kind.value=='17'&&form1.Apply_sub.value=='1'){
      	if(form1.TA270030028.value==""){
      		alert("请先选择合同号");
      		return;
      	}else{
      		form1.Apply_contractID.value = "";
      		form1.oper.value="prepare"; 
      		form1.NQ.value="new";      
          form1.submit();
      	}
      }else {
      //update by zap
       //form1.Apply_contractID.value = "";
       //form1.oper.value="prepare";    
      // form1.NQ.value="new";   
      // form1.submit();
       clearSessionData();  
       form1.Isquery.value="0";
       form1.Apply_contractID.value ="";
       form1.oper.value="showInsertView";
       form1.submit();
       //end by zap
    }
  }
}

function chooseContractID(){
  if(form1.Apply_kind.value=='17'&&form1.Apply_sub.value==''){
    if (form1.Apply_customerCode.value==null||trim(form1.Apply_customerCode.value)=="")
     {
       alert("请选择客户!");
       return ;
     }
     if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
      }
   var ts = window.showModalDialog(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TB.operation.TBChooseLoanOp&TA270019001="+form1.Apply_customerCode.value+"&area="+form1.Apply_sponsorBank.value+"&height=400&width=600&hasDetailLink=true&date="+new Date(),window,"dialogWidth:650px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no");

     if(ts != null) {
       form1.Apply_contractID.value = ts[3];
       form1.TA270019016.value = ts[4];
     }
     }else{
      if (isEmpty(form1.Apply_customerCode.value)|| isEmpty(form1.TA270030028.value))
     {
       alert("请选择客户或者正式合同号!");
       return ;
     }
      
     var ts = window.showModalDialog(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TB.operation.TBChooseApplyLoanOp&TA270030001="+form1.Apply_customerCode.value+"&TA270030028="+form1.TA270030028.value+"&area="+form1.Apply_sponsorBank.value+"&height=400&width=300&hasDetailLink=true&date="+new Date(),window,"dialogWidth:330px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no");

     if(ts != null) {
       form1.Apply_contractID.value = ts[1];
       
     }
     }
   }
   
   function loanQuery(){
     
   if (form1.Apply_customerCode.value==null||trim(form1.Apply_customerCode.value)=="")
     {
       alert("请选择客户!");
       return ;
     }
     if(isEmpty(form1.Apply_sponsorBank.value)){
        alert("请选择业务所属行");
        return;
      }
   var ts = window.showModalDialog(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TB.operation.TBChooseIMLoanOp&TA270030001="+form1.Apply_customerCode.value+"&area="+form1.Apply_sponsorBank.value+"&height=400&width=600&hasDetailLink=true&date="+new Date(),window,"dialogWidth:650px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no");

     if(ts != null) {
       form1.TA270030028.value = ts[2];
       form1.TA270019023.value = ts[2];
       form1.Apply_contractID.value="";
     }
     
   }