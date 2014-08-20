var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";

function xgxx(){
      var ts = window.showModalDialog(basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.GA.GA_InterrelatedInfoOp&opAction=EnterPage&CustomerId="+form1.Apply_customerCode.value+"&opDataUnclear=true&time=" + (new Date), window,"dialogWidth:650px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no;z-lock:yes;moveable:no;copyhistory:yes");
     
    }

//判断是否可以选择开证件币种
function changekzbz(){
 if(form1.ta270019010.value=="1"){
 alert("相关结算类型为信用证，不能选择开证币种！");
 return false;
 }
}
function return2(){
  window.location=form1.return2Url.value;
}
function scanpre()
{
 
  form1.operationName.value="icbc.cmis.IA.IAImagePreDefOp";
  form1.doWhat.value="OpClassfy";
  form1.qydm.value = form1.Apply_customerCode.value;
  
  form1.spzt.value  ="0";
  
  form1.ywbh.value =form1.Apply_contractID.value;
  if(form1.ta270019010.value=='1'){
   if(form1.ta270019011.value=='0')//即期  
     form1.ywzl.value = "2020";
   else
     form1.ywzl.value = "8045"; 
  }
  if(form1.ta270019010.value=='2'){
     form1.ywzl.value = "8046";
  } 
   if(form1.ta270019011.value=='3'){
     form1.ywzl.value = "8047";
  }
  form1.dbfs.value=  form1.ta270019006.value;
        
    form1.OnlyAdd.value = "0";
    form1.returnUrl.value=basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true&isSessionClear=1&oper="+form1.pageMode.value;
 
    form1.submit();
}

//判断是否可以修改或删除
function checkflow(){	
	var applyState = form1.ta270019016.value;//申请书状态
	var ywfqh  = form1.ta270019033.value;//业务发起行
	var curDq = form1.dqdm.value;
	form1._querypermute.value = "1";
	if(applyState=='1'||applyState=='2'||applyState=='4'){
		if(ywfqh==curDq){
			return true;
		}else{
			form1._querypermute.value = "0";
			alert("本申请的业务发起行不是本行，不能删除和修改！");
			return false;
		}
	}else{
		form1._querypermute.value = "0";
		alert("本申请不在申请、否决或退回调查状态，不能删除和修改！");
		return false;
	}
	return true;	
}

 function f_v_image()//影像扫描
    {
      form1.enpcode.value =form1.Apply_customerCode.value;
      form1.applycode.value=form1.Apply_contractID.value;
      form1.oper.value = "query";
      form1.operationName.value = "icbc.cmis.ID.Image_display";
      form1.returnUrl.value=basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true&isSessionClear=1&oper="+form1.pageMode.value;

      form1.submit();
    }
    
    //贷款定价信息
    function goDJ(){
   if(form1.pageMode.value=="showInsertView"){
     alert("请在查询模式下点击查询贷款定价信息！");
     return;
   }else{
     
    form1.opDataUnclear.value="true";
    form1.Dj_ReturnUrl.value=basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true&isSessionClear=1&oper="+form1.pageMode.value;//返回的url
    form1.operationName.value="icbc.cmis.FE.FE_LPIInterfaceOp";
    form1.opAction.value='20003';    
    form1.submit();
   }
 }
function zhAuthorize(str){//判断总行授权
	return true;
}

function deletetz(){//特殊校验
	return true;
}

function checkMoney(){
	var totalDueMoney = form1.totalDueMoney.value;
	if(trim(totalDueMoney)=="" || totalDueMoney==null||totalDueMoney=="null"){
		totalDueMoney = 0;
	}
	if(trimNum(form1.ta270019015.value)/1<trimNum(totalDueMoney)/1){
		alert("合同申请押汇金额小于借据金额总额，当前借据金额总额为："+totalDueMoney);
		form1.ta270019015.focus();
		return false;
	}
	return true;
}
//校验申请日期
function checkApplyDate()
{
 if (form1.ta270019004.value >form1.cmisdate.value)
   {
   alert("申请日期不得大于系统日期:"+form1.cmisdate.value);
   form1.ta270019004.value="";
   return false;
 }
 return true;
}
//根据贷款方式获得具体贷款方式
 function changejtdkfs()
 {
  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
  var parser=new ActiveXObject("microsoft.xmldom");
  parser.async="false";
  var tmp = "enpcode="+form1.ta270019001.value+"&dkfs="+form1.ta270019006.value+"&oper=querydkfs&time="+new Date();
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
  var seleobj=document.all.ta270019007;
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

 //根据具体贷款方式获得贷款风险度 
 function changedkfs(){
  if(isEmpty(form1.ta270019007.value)){
    alert("具体贷款方式为空");
    form1.ta270019007.focus();
  }
  else{

        var tmp = "enpcode="+form1.ta270019001.value+"&dkfs="+form1.ta270019007.value+"&oper=creditventure&time="+new Date();
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
          form1.ta270019020.value = parser.documentElement.getAttribute("creditveture");
          document.all.plain_ta270019020.innerHTML=parser.documentElement.getAttribute("creditveture");
  }
}
//根据相关结算类型,初始化各
function changexyzzl(){
  if  (form1.ta270019010.value=="1"){
  	form1.ta270019034.readOnly =true;
  	form1.ta270019034.value="";
  	form1.ta270019035.readOnly =true;
  	form1.ta270019035.value="";
  	form1.ta270019003.readOnly = true;
  	form1.ta270019030.value = "8045";
  	form1.ta270019009.value="0123002";
  }
  if(form1.ta270019010.value=="2") {
  	form1.ta270019034.readOnly =false;
	form1.ta270019035.readOnly=false;
  	form1.ta270019024.value='8046';
  	form1.ta270019003.readOnly = false;
  	form1.ta270019003.value="";
  	document.all.plain_ta270019012.innerHTML="人民币";
  	form1.ta270019012.value="01";
  	form1.ta270019013.value = "";
    document.all.plain_ta270019013.innerHTML="";
    form1.ta270019009.value="0123002";    
  }
  if(form1.ta270019010.value=="3"){
   	form1.ta270019034.readOnly =false;
	form1.ta270019035.readOnly=false;
  	form1.ta270019024.value='8047';
  	form1.ta270019003.readOnly = true;
  	form1.ta270019003.value="";
  	document.all.plain_ta270019012.innerHTML="人民币";
  	form1.ta270019012.value="01";
  	form1.ta270019013.value = "";
    document.all.plain_ta270019013.innerHTML="";
    form1.ta270019009.value="0123008";   
  } 
   var tmp = "dkkmCode="+form1.ta270019009.value+"&opAction=creditventure&time="+new Date();
   objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.TE.JK_RFQueryOp&xmlOutput=true&opDataUnclear=true&'+tmp,false);
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
   var ta270019009Show = parser.documentElement.getAttribute("creditveture"); 
   if(isEmpty(ta270019009Show)){
   	document.all.plain_ta270019009.innerHTML=form1.ta270019009.value;
   }else{
   	document.all.plain_ta270019009.innerHTML=ta270019009Show;
   }
}


//选择相关业务编号
function ChooseLC()
{
	if(trim(form1.ta270019010.value)=="" || form1.ta270019010.value==null){
      	alert("请先选择相关结算类型!");
      	form1.ta270019010.focus();
      	return;
      }
       if(form1.ta270019010.value=='2'){
      	alert("选择相关结算类型为进口代收，不能选择相关业务编号,请手工输入ISEE进口代收唯一标识！");
      	form1.ta270019003.readOnly=false;
      	return;
      }
	 if(form1.ta270019010.value=='3'){
      	alert("选择相关结算类型为TT融资，不能选择相关业务编号！");
      	form1.ta270019003.readOnly=true;
      	return;
      }
        var customerCode = form1.ta270019001.value;
        var ts = window.showModalDialog(basepath0606+"/util/util_Query.jsp?queryType=icbc.cmis.TC.util.queryAllLC&customerCode="+form1.ta270019001.value+"&areacode="+form1.ta270019018.value+"&height=410&hasDetailLink=true&ask=true&domain=international&zhuxiaoflag=0",window,"dialogWidth:645px;dialogHeight:465px;center:yes;help:no;status:no;resizable:no");
        if (ts != null){        
        
        
        if (ts[8]==null||ts[8]=='')
        {
          alert("当前信用证尚未正式开立,不能进行后续业务的处理.");
          form1.ta270019003.value ="";
          return ;
        }
        
        else if (ts[11]=='1')
        {
          alert("当前信用证是项目项下的，不能使用！");
          form1.ta270019003.value ="";
          return ;
        }
        else {
        
          document.all.plain_ta270019012.innerHTML=ts[3];
          form1.ta270019003.value = ts[1];
          form1.ta270019013.value = ts[4];
          document.all.plain_ta270019013.innerHTML=ts[4];
          form1.ta270019012.value = ts[6];
          //form1.TA270019012SHOW.value=ts[3];
          var bar='';
          if(ts[7]==1){
            //bar='<select name="ta270019011" disabled><option value=0 selected>即期 </option><option value=1>远期 </option>';
            form1.ta270019011.value='1';//远期
            document.all.plain_ta270019011.innerHTML='远期';          
          		form1.ywzl.value = "8045";
            	form1.ta270019024.value='8045';         
          }
          else{
          // bar='<select name="TA270019011" disabled><option value=0 >即期 </option><option value=1 selected>远期 </option>'; 
          	form1.ta270019011.value='0';//
          	document.all.plain_ta270019011.innerHTML='即期';
          	form1.ywzl.value = "2020";
           	form1.ta270019024.value='2020';  
           }
                
          //form1.TA270019012SHOW.value=ts[6];
        }
      }
      
  }
  
  /*********************************************************************************/
  /*选择利率种类*/
    function chooserate(){
      
      if(isEmpty(form1.ta270019014.value)){
        alert("请先选择申请押汇币种！");
        form1.ta270019014.focus();
        return;
      } 
      if(isEmpty(form1.ta270019004.value)){
        alert("请先输入申请日期！");
        form1.ta270019004.focus();
        return;
      }      
      var   MoneyType= form1.ta270019014.value;              //--传入币种
      var   BorrowType= "10";                           //--贷款分类
      var   RateTypeCode= form1.ta270019026.value;     //--利率种类
      var   Day_Begin= "";        //--起始日
      var   Day_End= "";          //--到期日
      var   Day_BeginUse= form1.ta270019004.value;     //--启用日
      var   flag= "0";                                  //--申请标志(0，申请 1，正式)
	　var ts = window.showModalDialog(basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.Util_ChooseRateTypeOp&opAction=20001&opDataUnclear=true&MoneyType="+MoneyType+"&RateTypeCode="+RateTypeCode+"&Day_BeginUse="+Day_BeginUse+"&flag="+flag+"&time="+(new Date()),window,"dialogWidth:630px;dialogHeight:360px;center:yes;help:no;status:yes;resizable:no");
	  
	  if(ts!=null){
	    form1.lab_ta270019026.value = ts[0]; //利率种类名称
	    form1.ta270019027.value = ts[1]; //基准利率
	    document.all.plain_ta270019027.innerHTML=ts[1];
	    form1.ta270019026.value = ts[2]; //利率种类
	    form1.ta270019032.value = ts[3];//利率代码
	    if(ts!=null&&ts[2]=="32"){
   	    form1.ta270019028.value = "1";//协议固定利率只能是浮动利率
   	    changefdlx1();
       }
	  }
  }
  
   
  /*变化浮动类型响应*/
   function changefdlx(){
      if(form1.ta270019028.value=="0"){
        form1.ta270019029.value = "";
        ds.style.display = "none";
        dc.style.display = "";
      }
      else{
        form1.ta270019029.value = "";
        ds.style.display = "";
        dc.style.display = "none";
      }
    }
/*************************************************************************/
function changefdlx1(){
      if(form1.ta270019028.value=='0'){
        document.all.div_140030.innerHTML='正常利率浮动档次(%)<font color="#FF0000">*</font>';
        form1.ta270019029.value="";}
       else{
        document.all.div_140030.innerHTML='正常利率浮动值<font color="#FF0000">*</font>';
         form1.ta270019029.value="";}
}
    function changeyqlv(){
      if(form1.ta270019030.value=='0'){
        document.all.div_150030.innerHTML='逾期利率浮动档次(%)<font color="#FF0000">*</font>';
         form1.ta270019031.value="";}
       else{
        document.all.div_150030.innerHTML='逾期利率浮动值<font color="#FF0000">*</font>';
         form1.ta270019031.value="";}
    }   
/************************************************************/
  
  function chooseDDCode()//获取到单号,借据申请中
  {
  if(form1.ta270019010.value=="1"){ 
	  	var ts = window.showModalDialog(encodeURL(basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.TC.operation.LCDocOp&opDataUnclear=true&doflag=queryDocSeq&LCCode="+form1.ta270019003.value+"&customerCode="+form1.ta270016001.value+"&time="+(new Date)),window,"dialogWidth:400px;dialogHeight:300px;center:yes;help:no;status:no;resizable:no");
	    if (ts != null){
	      	form1.ta270016017.value = ts[1];
	     	form1.ta270016006.value = ts[2];
	     	document.all.plain_ta270016006.innerHTML=ts[2];	
	     	form1.ta270016007.value=ts[3];
	     	document.all.plain_ta270016007.innerHTML=ts[3];	     	
	    }
  }else{
		alert("相关结算类型为非信用证，不能选择来单号！");
	   form1.ta270016017.value = '*';
	   form1.ta270016007.value='0';
	   form1.ta270016005.value='01';
	   form1.ta270016006.value='0';
	   document.all.plain_ta270016005.innerHTML='人民币';
	   document.all.plain_ta270016006.innerHTML='0';
	   form1.ta270016007.value='0';	
	   document.all.plain_ta270016007.innerHTML='0';   
  }
 }
  
  function checkJJcount(){
  if (form1.jjcount.value=='0'){
  alert("借据申请中,必须至少录入一条借据才能保存!");
  return true;
  
   }
  }

//得到贷款资产风险度
function changedkfx()
{
  if (trim(form1.ta270019004.value)==""||form1.ta270019004.value==null||isDate(form1.ta270019004.value)==false)
  {
    alert ("必须填上正确申请日期YYYYmmdd!");
    form1.ta270019004.focus();
    return false;
  }
  else if (checkApplyDate()==false) return false;
         var tmp = "enpcode="+form1.ta270019001.value+"&sqsj="+form1.ta270019004.value+"&oper=capitalventure&time="+new Date();
         objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&xmlOutput=true&opDataUnclear=true&'+tmp,false);
         objHTTP.send();
         var xml = objHTTP.responseText;
        if(!parser.loadXML(xml))
        {
          return false;
        }
        error = parser.getElementsByTagName("error");
       if(error.length > 0)
       {
           alert(error.item(0).text);
         	return;
       }
       
      form1.ta270019021.value = parser.documentElement.getAttribute("capitalventure");
      document.all.plain_ta270019021.innerHTML=parser.documentElement.getAttribute("capitalventure");

}
//判断长度
function checkLength_szj(v,length){
 var s = v;
 var l = length;
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
     return true;
   }
   else{
	var aaa=Math.floor(l/2);
	alert("相关业务编号插入值过长！最多"+l+"个字符或"+aaa+"个汉字。");
	return false;
   }
}
  /*检查页面表单的各个字段的输入*/
  function checkForm_Ht()
  {
    
    if (trim(form1.ta270019004.value)=="" || form1.ta270019004.value==null|| isDate(form1.ta270019004.value)==false)
  	{
	    alert ("必须填上正确申请日期YYYYmmdd!");
	    form1.ta270019004.focus();
	    return false;
    }
    if(!checkApplyDate()){
    	form1.ta270019004.focus();
    	return false;
    }
    if (trim(form1.ta270019010.value)=="" || form1.ta270019010.value==null )
   	{
	     alert ("必须选择相关结算类型!");
	     form1.ta270019010.focus();
	     return false;
   	}
   	if(form1.ta270019010.value!="1"){
   		
		   	 if (isEmpty(form1.ta270019034.value)||isDate(form1.ta270019034.value)==false)
		    {
		        alert("请输入正确的合同生效日yyyyMMdd!");
		        form1.ta270019034.focus();
		        return false;
		    }
		   if (isEmpty(form1.ta270019035.value)||isDate(form1.ta270019035.value)==false)
		  {
		        alert("请输入正确的合同到期日yyyyMMdd!");
		        form1.ta270019035.focus();
		        return false;
		   }
		   if(form1.ta270019035.value < form1.cmisdate.value)
   			 {
               alert("合同到期日必须大于当前时间!"+form1.cmisdate.value);
               form1.ta270019035.focus();
               return false;
   			}
		  if (dateBetween(form1.ta270019035.value,form1.ta270019034.value)<0)
		   {
		        alert("合同到期日不得迟于合同生效日!");
		        form1.ta270019035.focus();
		        return false;
		  }
   	}
   	if(form1.ta270019010.value=="1"){
   		 	 if(!isEmpty(form1.ta270019034.value))
		    {
		        alert("相关结算类型为信用证，不能输入合同生效日！");		        
		        form1.ta270019034.value="";
		        form1.ta270019034.readOnly=true;
		        return false;
		    }
		   if (!isEmpty(form1.ta270019035.value))
		  {
		        alert("相关结算类型为信用证，不能输入合同到期日！");
		        form1.ta270019035.value="";
		        form1.ta270019035.readOnly=true;
		        return false;
		   }  	
   	}
    if (trim(form1.ta270019006.value)=="" || form1.ta270019006.value==null )
    {
        alert ("必须选择贷款方式!");
        form1.ta270019006.focus();
        return false;
    }

 	if (trim(form1.ta270019007.value)==""||form1.ta270019007.value==null)
  	{
    	alert("请填写具体贷款方式!");
    	form1.ta270019007.focus();
    	return false;
  	}
    
    if(isEmpty(form1.lab_ta270019026.value)){
	    alert("请选择利率种类！");    
	    form1.lab_ta270019026.focus();
	    return false;
    }
	    if(isEmpty(form1.ta270019028.value)){
	    alert("请选择正常利率浮动类型！");
	    form1.ta270019028.focus();
	    return false;
	    }
	    if(form1.ta270019026.value=="32"){
	    	if(form1.ta270019028.value=="0"){
	    		alert("利率种类为协议固定利率，只能选择浮动利率");
	    		form1.ta270019028.focus();
	    		return false;
	    	}
	    }
	    if(form1.ta270019028.value=="0"){
	    	 if(isEmpty(form1.ta270019029.value)){
		    alert("请选择正常利率浮动档次！");
		    form1.ta270019029.focus();
		    return false;
		    }
	    }else{
		    if(isEmpty(form1.ta270019029.value)){
		    alert("请选择正常利率浮动值！");
		    form1.ta270019029.focus();
		    return false;
		    }
	    }
	
	    if(isEmpty(form1.ta270019030.value)){
	    alert("请选择逾期利率浮动类型！");
	    form1.ta270019030.focus();
	    return false;
	    }
	    if(form1.ta270019030.value=="0"){
	     	if(isEmpty(form1.ta270019031.value)){
		    alert("请选择逾期利率浮动档次！");
		    form1.ta270019031.focus();
		    return false;
		    }		    
	    }else{
		    if(isEmpty(form1.ta270019031.value)){
		    alert("请选择逾期利率浮动值！");
		    form1.ta270019031.focus();
		    return false;
		    }
    	}
        if(form1.ta270019028.value=="1"){
		   if(trimNum(form1.ta270019029.value)<-10||trimNum(form1.ta270019029.value)>10){
		    alert("浮动利率的范围是-10至10,输入有误");
		    return false;
		   }
		 }
		 
		 if(form1.ta270019028.value=="0"){
		   if(trimNum(form1.ta270019029.value)<=-100||trimNum(form1.ta270019029.value)>=900){
		    alert("正常利率浮动档次不能小于等于-100,大于等于900,输入有误");
		    return false;
		   }
		 }
		 
		 if(form1.ta270019030.value==0){
			 if(form1.ta270019031.value<=-100||form1.ta270019031.value>=900){
			   alert("当逾期利率浮动类型为浮动档次时,逾期浮动值不能小于等于-100,大于等于900。");
			   form1.ta270019031.focus();
			   return false;
			 }
		 }
		 else
		 {
		   if(form1.ta270019031.value<=-100||form1.ta270019031.value>=100){
		   alert("当逾期利率浮动类型为浮动利率时,逾期浮动值不能小于等于-100,大于等于100。");
		   form1.ta270019031.focus();
		   return false;
		 }
 	 	}
    
	    //code add by zhengzezhou 2004-6-10 页面把信用证种类 即期 远期 也作为输入项
	      if ((trim(form1.ta270019011.value)==""||form1.ta270019011.value==null)&&trim(form1.ta270019010.value)=='1')
	      {
	        alert("请选择信用证期限!");
	        form1.ta270019011.focus();
	        return false;
	      }
	    if (trim(form1.ta270019008.value)=="" || form1.ta270019008.value==null )
    {
	    alert ("必须选择贷款性质!");
	    form1.ta270019008.focus();
	    return false;
    }
		if(form1.ta270019010.value=="1"){
	      if (trim(form1.ta270019003.value)=="" || form1.ta270019003.value==null )
	      {
	        alert ("必须选择相关业务编号!");
	        form1.ta270019003.focus();
	        return false;
	      }
	     }
	     if(form1.ta270019010.value=="2"){
	      if (trim(form1.ta270019003.value)=="" || form1.ta270019003.value==null )
	      {
	        alert ("相关业务编号必须输入ISEE进口代收唯一标识!");
	        form1.ta270019003.focus();
	        return false;
	      }
	     }
	     if(form1.ta270019010.value=="2"){
	     
	     	if (trim(form1.ta270019003.value)!="" && form1.ta270019003.value!=null )
	    	{
		    	if(!checkLength_szj(form1.ta270019003.value,30)){
		    		form1.ta270019003.focus();
		    		return false;
		    	}
	    	}
	     } 
	     if(form1.ta270019010.value=="1"){
	      if (trim(form1.ta270019013.value)==""||form1.ta270019013.value==null)
	      {
	        alert("请填写开证金额!");
	        form1.ta270019013.focus();
	        return false;
	      }
	      else  if  ((msg=isReal(trimNum(form1.ta270019013.value),14,2))!="ok")
	      {
	        alert ("填写申请开证金额出错:"+msg);
	        form1.ta270019013.focus();
	        return false;
	      }
	      }
	      if (trim(form1.ta270019015.value)=="" || form1.ta270019015.value==null )
	      {
	        alert("必须填写申请押汇金额!");
	        form1.ta270019015.focus();
	        return false;
	      }
	      else  if  ((msg=isReal(trimNum(form1.ta270019015.value),14,2))!="ok")
	      {
	        alert ("填写申请押汇金额出错:"+msg);
	        form1.ta270019015.focus();
	        return false;
	      }
	      if(!checkMoney()){
  			return false; 
  		  }
    	 if(trim(form1.ta270019015.value)!="" && form1.ta270019015.value!=null){
	      	
	      }	
    
    //code modify by zhengzezhou 2004-6-10 解决了在未输入“申请押汇金额”时，提交表单，系统提示“必须填写押汇金额”
      if (trim(form1.ta270019013.value)!="" && form1.ta270019013.value!=null )
      {
        if  ((msg=isReal(trimNum(form1.ta270019013.value),14,2))!="ok")
        {
          alert ("填写开证金额出错:"+msg);
          form1.ta270019013.focus();
          return false;
        }
      }
      if (form1.ta270019006.value=="20" && isEmpty(form1.ta270019019.value)){
        alert ("保证贷款方式下,请选择担保形式.")
            form1.ta270019019.focus();
        return false;
      }
      if (form1.ta270019006.value!="20" && !isEmpty(form1.ta270019019.value)){
        alert ("当贷款方式不是保证时,不必选择担保形式.")
            form1.ta270019019.focus();
        return false;
      }
      if (form1.oper.value=="store" || form1.oper.value=="new"){
        //刚性控制
        //if(!call_apply_control()) return false;
      }
      //if(chechstat()==false) return; 判断是否进入流程，暂时没实现
	    form1.ta270019013.value=trimNum(form1.ta270019013.value);
	    form1.ta270019015.value=trimNum(form1.ta270019015.value);        
	    
	    form1.ta270019029.value=trimNum(form1.ta270019029.value);
	    form1.ta270019031.value=trimNum(form1.ta270019031.value);
	                  
      return true;

  }
  //初始化方法
function permute(){

if(form1.isJieju.value=='0'){	
	if(form1.ta270019028.value=='0'){
        document.all.div_140030.innerHTML='正常利率浮动档次(%)<font color="#FF0000">*</font>';
    }else{
        document.all.div_140030.innerHTML='正常利率浮动值<font color="#FF0000">*</font>';
    }
	if(form1.ta270019030.value=='0'){
        document.all.div_150030.innerHTML='逾期利率浮动档次(%)<font color="#FF0000">*</font>';
    }else{
        document.all.div_150030.innerHTML='逾期利率浮动值<font color="#FF0000">*</font>';        
    }
    if(form1.ta270019010.value=='2'){
    	form1.ta270019003.readOnly=false;
    }
    if(form1.ta270019010.value=='1'){
    	form1.ta270019034.readOnly=true;
    	form1.ta270019035.readOnly=true;
    }           
}
if(form1.isJieju.value=='7'){
	if(form1.ta270016010.value=='0'){
        document.all.div_110010.innerHTML='正常利率浮动档次(%)<font color="#FF0000">*</font>';        
    }
     else{
        document.all.div_110010.innerHTML='正常利率浮动值<font color="#FF0000">*</font>';        
     }
     if(form1.ta270016019.value=='0'){
        document.all.div_150010.innerHTML='逾期利率浮动档次(%)<font color="#FF0000">*</font>';
     }
     else{
        document.all.div_150010.innerHTML='逾期利率浮动值<font color="#FF0000">*</font>';
     }
}
if(form1.isJieju.value=='4'){
	if(form1.ta270016010.value=='0'){
        document.all.div_110010.innerHTML='正常利率浮动档次(%)<font color="#FF0000">*</font>';        
    }
     else{
        document.all.div_110010.innerHTML='正常利率浮动值<font color="#FF0000">*</font>';        
     }
     if(form1.ta270016019.value=='0'){
        document.all.div_150010.innerHTML='逾期利率浮动档次(%)<font color="#FF0000">*</font>';
     }
     else{
        document.all.div_150010.innerHTML='逾期利率浮动值<font color="#FF0000">*</font>';
     }
}

}

/***************************以下是借据的JS**************************************/

/*日期比较大小，小于零表示str1<str2*/
function dateBetween(str1,str2)
{
  var x = str1.substring(0,4)/1;
  var y = str1.substring(4,6)/1-1;
  var z = str1.substring(6,8);
  var date1 = new Date(x,y,z);
  var x = str2.substring(0,4)/1;
  var y = str2.substring(4,6)/1-1;
  var z = str2.substring(6,8);
  var date2 = new Date(x,y,z);
  return (date1-date2)/(1000*3600*24);
}

 /*选择利率种类*/
    function chooserate2(){
      
      if(isEmpty(form1.ta270019014.value)){
        alert("请先选择申请押汇币种！");
        return;
      } 
      if(isEmpty(form1.ta270019004.value)){
        alert("请先输入申请日期！");
        return;
      }
     
      if(form1.ta270016013.value=="0"){
      	alert("计息周期为不计息，不能选择利率种类！");
      	form1.ta270016013.focus();
      	return;
      }      
      var   MoneyType= form1.ta270019014.value;              //--传入币种
      var   BorrowType= "10";                           //--贷款分类
      var   RateTypeCode=form1.ta270016014.value;     //--利率种类
      var   Day_Begin=form1.ta270016008.value;        //--起始日
      var   Day_End= form1.ta270016009.value;          //--到期日
      var   Day_BeginUse=form1.ta270016008.value;     //--启用日
      var   flag= "1";                                  //--申请标志(0，申请 1，正式)
	　var ts = window.showModalDialog(basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.Util_ChooseRateTypeOp&opAction=20001&opDataUnclear=true&MoneyType="+MoneyType+"&RateTypeCode="+RateTypeCode+"&Day_BeginUse="+Day_BeginUse+"&flag="+flag,window,"dialogWidth:630px;dialogHeight:360px;center:yes;help:no;status:yes;resizable:no");
	  
	  if(ts!=null){
	    form1.lab_ta270016014.value = ts[0]; //利率种类名称
	    form1.ta270016011.value = ts[1]; //基准利率
        document.all.plain_ta270016011.innerHTML=ts[1];
	    form1.ta270016014.value = ts[2]; //利率种类
	    //form1.ta200251054.value = ts[3];//利率代码
	    if(ts!=null&&ts[2]=="32"){
   	    form1.ta270016010.value = "1";//协议固定利率只能是浮动利率
   	    changefdlx2();
       }
	  }
  }
  
//选择正常浮动类型
 function changefdlx2(){
      if(form1.ta270016013.value=="0"&&form1.ta270016010.value=="1"){
        alert("不计息时，正常浮动类型只能选择浮动档次！");
        form1.ta270016010.value="0";
        return;
      }
      if(form1.ta270016010.value=='0'){
        document.all.div_110010.innerHTML='正常利率浮动档次(%)<font color="#FF0000">*</font>';
        form1.ta270016012.value="";
        }
       else{
        document.all.div_110010.innerHTML='正常利率浮动值<font color="#FF0000">*</font>';
        form1.ta270016012.value="";
        }
    }
    
//选择愈期浮动类型
 function changeyqlv2(){
      if(form1.ta270016013.value=="0"&&form1.ta270016019.value=="1"){
        alert("不计息时，逾期浮动类型只能选择浮动档次！");
        form1.ta270016019.value="0";
        return;
      }
      if(form1.ta270016019.value=='0'){
        document.all.div_150010.innerHTML='逾期利率浮动档次(%)<font color="#FF0000">*</font>';
        form1.ta270016018.value="";
        }
       else{
        document.all.div_150010.innerHTML='逾期利率浮动值<font color="#FF0000">*</font>';
        form1.ta270016018.value="";
        }
 }  
   
//获得计息周期
function changeta200071012(){
      if(form1.ta270016013.value=="0"){
       // document.all.rateChoose.style.display="none";
       	form1.jxzqFlag.value="0";
        form1.ta270016010.value="0";
        form1.ta270016010.readOnly=true;       
        form1.ta270016012.readOnly=true;
       // form1.ta270026014Name.value="贷款利率零利率";
        form1.lab_ta270016014.value="贷款利率零利率";
        form1.ta270016011.value="0";
        document.all.plain_ta270016011.innerHTML='0';
        form1.ta270016014.value="30000";
        if(form1.ta270016019.value!="0"){
        form1.ta270016019.value="0";
        changefdlx2();
        }
        form1.ta270016012.value="0";
        form1.ta270016018.value="0";
        form1.ta270016018.readOnly=true;
      }
      else{
      	if(form1.jxzqFlag.value=="0"){
      	form1.jxzqFlag.value="1";
        form1.ta270016010.value="";
        form1.ta270016010.disabled=false;
        form1.ta270016012.value="";
        form1.ta270016012.readOnly=false;
        //form1.ta270026014Name.value="";
        form1.lab_ta270016014.value="";
        form1.ta270016011.value="";
        document.all.plain_ta270016011.innerHTML='';
        form1.ta270016014.value="";
        //form1.ta200071020.value = "";
        form1.ta270016018.readOnly=false;
       } 
      }
    }

/*
借据为空判断
*/
    function checkForm_Jj()
    {
    
    if (form1.isJieju.value=='3'){
      if (trimNum(form1.ta270016004.value)>(form1.ta270019015.value-form1.sumDueMoney.value) )
 {
 alert ("您的借据押汇金额总额已经超过合同押汇金额.该合同的借据押汇余额为"+(form1.ta270019015.value-form1.sumDueMoney.value));
 
 return false;
 
 }
 }else{
 
 if (trimNum(form1.ta270016004.value)>((form1.ta270019015.value-form1.sumDueMoney.value)*1+(form1.ta270019015_all.value)*1) ){
 
 alert ("您的借据押汇金额总额已经超过合同押汇金额.该合同的借据押汇余额为"+((form1.ta270019015.value-form1.sumDueMoney.value)*1+(form1.ta270019015_all.value)*1));
 
 return false;
 
 }
 
 
 }
 
      
      if (isEmpty(form1.ta270016004.value))
      {
        alert("押汇申请金额不能为空!");
        form1.ta270016004.focus();
        return false;
      }
      
      if(trimNum(form1.ta270016004.value)<=0){
        alert("押汇申请金额不能小于等于0!");
        form1.ta270016004.focus();
        return false;
      }
 //     if(form1.isMo.value=='1'){
 //       if (checkDueBillAmout()==false) return false;
  //    }
      if(isEmpty(form1.ta270016013.value)){
        alert("计息周期不能为空！");
        return false;
      }
      
 //     if (isEmpty(form1.ta270016017.value))
 //     {
  //      alert("押汇到单编号不能为空!");        
  //      return false;
  //    }
      if (isEmpty(form1.ta270016006.value))
      {
        alert("押汇单据金额不能为空，请先选择到单编号!");
        //form1.ta270016006.focus();
        return false;
      }
      if (isEmpty(form1.ta270016008.value)||isDate(form1.ta270016008.value)==false)
      {
        alert("请输入正确的押汇日yyyyMMdd!");
        form1.ta270016008.focus();
        return false;
      }
     if(form1.ta270016008.value < form1.cmisdate.value)
      {
        alert("押汇日必须大于当前时间!");
        return false;
     }
      if (isEmpty(form1.ta270016009.value)||isDate(form1.ta270016009.value)==false)
      {
        alert("请输入正确的押汇归还日yyyyMMdd!");
        form1.ta270016009.focus();
        return false;
      }
      if (dateBetween(form1.ta270016009.value,form1.ta270016008.value)<0)
      {
        alert("押汇日不得迟于押汇归还日!");
        form1.ta270016009.focus();
        return false;
      }
      
 if (dateBetween(form1.ta270016009.value,form1.ta270016008.value)>form1.controlC.value)
   {
  alert("押汇归还日不得迟于押汇日"+form1.controlC.value+"天!");
   form1.ta270016009.focus();
   return false;
 }
 if (isEmpty(form1.ta270016011.value))
 {
   alert("押汇利率不能为空!");
   form1.ta270016011.focus();
   return false;
 }
  if (isEmpty(form1.ta270016019.value))
 {
   alert("逾期浮动类型不能为空!");
   form1.ta270016019.focus();
   return false;
 }
 if(form1.ta270016019.value=="0"){
 	if (isEmpty(form1.ta270016018.value))
	 {
	   alert("逾期浮动档次不能为空!");
	   form1.ta270016018.focus();
	   return false;
	 }
 }else{
	 if (isEmpty(form1.ta270016018.value))
	 {
	   alert("逾期浮动值不能为空!");
	   form1.ta270016018.focus();
	   return false;
	 }
 }
 form1.ta270016018.value = trimNum(form1.ta270016018.value);
 
 if(form1.ta270016019.value==0){
 if(form1.ta270016018.value<=-100||form1.ta270016018.value>=900){
   alert("当逾期利率浮动类型为浮动档次时,逾期浮动值不能小于等于-100,大于等于900。");
   form1.ta270016018.focus();
   return false;
 }
 }else{
   if(form1.ta270016018.value<=-100||form1.ta270016018.value>=100){
   alert("当逾期利率浮动类型为浮动利率时,逾期浮动值不能小于等于-100,大于等于100。");
   form1.ta270016018.focus();
   return false;
 }
 }
  if (isEmpty(form1.ta270016010.value))
 {
   alert("浮动类型不能为空!");
   form1.ta270016010.focus();
   return false;
 }
 if(form1.ta270016010.value=="0"){
 	if (isEmpty(form1.ta270016012.value))
	 {
	   alert("浮动档次不能为空!");
	   form1.ta270016012.focus();
	   return false;
	 }
 }else{
	 if (isEmpty(form1.ta270016012.value))
	 {
	   alert("浮动值不能为空!");
	   form1.ta270016012.focus();
	   return false;
	 }
 }

 
 var a = 0;
 form1.ta270016012.value = trimNum(form1.ta270016012.value);
 a = form1.ta270016012.value/1;
 if(form1.ta270016010.value=="1"){
   if(a<-10||a>10){
    alert("浮动利率的范围是-10至10,输入有误");
    return false;
   }
 }
 
 if(form1.ta270016010.value=="0"){
   if(a<=-100||a>=900){
    alert("正常利率浮动档次不能小于等于-100,大于等于900,输入有误");
    return false;
   }
 }
 if (form1.ta270019010.value=="1"){
   if (isEmpty(form1.ta270016007.value))
   {
     alert("在信用证结算方式下,必须输入正确的押汇到单到期日标识!");
     form1.ta270016007.focus();
     return false;
   }
   if (isEmpty(form1.ta270016017.value))
   {
     alert("在信用证结算方式下,必须选择押汇到单编号!");

     return false;
   }
 }
 //code add by zzz 2004-5-8
   if (form1.ta270016011.value <0) {
     alert("押汇利率不能小于0！");
     return false;
   }
   if(form1.ta270016014.value=='32'){
     if(form1.ta270016010.value=='0'){
      alert("当利率种类是协议固定利率,只能选择浮动利率!");
      return false;
     }
     trimNum(form1.ta270016012.value);
     if(form1.ta270016012.value<=0)
     {
       alert("浮动值不能小于等于0！");
       return false;
     }
  }
      return true;
}

    /*
       调用借据明细试算接口
    */  
    function calTerm(){
      if(isEmpty(form1.ta270016011.value)){
        alert("请选择利率种类！");
        return;
      }
      if(isEmpty(form1.ta270016004.value)){
        alert("请输入押汇申请金额！");
        return;
      }
      if(!isDate(form1.ta270016008.value)){
        alert("请输入正确的放款日期！");
        return;
      }
      if(!isDate(form1.ta270016009.value)){
        alert("请输入正确的到期日期！");
        return;
      }
      var rate =0;
      if(form1.ta270016010.value==0){
      	  rate=Math.round(trimNum(form1.ta270016011.value)*(1+trimNum(form1.ta270016012.value)/100)*1000000)/1000000;
      }else{
      	  rate=trimNum(form1.ta270016011.value)/1+trimNum(form1.ta270016012.value)/1;
      }     
      var ts = window.showModalDialog(basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FG.FG_TrialDuebillAmorDetailOp&opAction=50001&currType="+form1.ta270019014.value+"&amout="+trimNum(form1.ta270016004.value)+"&opDataUnclear=true&grantDate="+form1.ta270016008.value+"&matuDate="+form1.ta270016009.value+"&rate="+rate,window,"dialogWidth:630px;dialogHeight:360px;center:yes;help:no;status:no;resizable:no");
    }

function candeljj(){
	return true;
}
//添加借据附属信息
function append1() {
  var flag;//0不能修改，1可以修改
  if(form1.isJieju.value == '7'){
    flag='0';
  }else
    flag='1';
  if(form1.pageMode.value!='showQueryView'){
    var items="";
    //取表单内的所有input项 
    items = document.getElementsByTagName("input");
    for(ii=0;ii<items.length;ii++){
      if(items[ii].isAmount=='yes')
        items[ii].value=trimNum(items[ii].value);
      else
        items[ii].value=trim(items[ii].value);    
    }
    
    submitparameter("form1","17");
  }
  var returnurl=basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true';
  returnurl+="&oper="+form1.pageMode.value;
  appendTab2(flag,returnurl,form1.ta270016003.value);

}


  function appendTab2(actionMode,returnUrl,loanCode){
    form1.opAction.value = "60001";
    form1.opDataUnclear.value="true";
    form1.operationName.value="icbc.cmis.FG.FG_DirectionOp";
    form1.url.value=returnUrl;
    form1.pretend.value=form1.Apply_pesudoID.value;
    form1.flag.value=actionMode;
    
    form1.magkind.value="01";
    form1.step.value=form1.Apply_stage.value;
    form1.areacode.value=form1.dqdm.value;
    form1.enpcode.value= form1.Apply_customerCode.value;
    form1.enpname.value=form1.Apply_customerName.value;
    if(form1.Apply_contractID.value =="")
    form1.tradecode.value='待定';
    else
    form1.tradecode.value=form1.Apply_contractID.value;
    if(loanCode!=""){
    form1.tradecode.value=form1.tradecode.value+'-'+loanCode;
    form1.pretend.value=form1.pretend.value+'-'+loanCode;
    }
    form1.submit();  
  }	








  