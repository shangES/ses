//载入页面
function f_init(){
  for(var i=0;i<parent.length;i++){
   if(parent.frames[i].name=="approve_iframeinfo")	       
     hiddenButtons();
  }
  loadTable(form1.formID.value,form1.pageMode.value,form1._primaryInfo.value,form1._querypermute.value);
}

//新增记录
function insertTa(){
  
  
  var items="";
  //取表单内的所有input项 
  items = document.getElementsByTagName("input");
  for(ii=0;ii<items.length;ii++){
    if(items[ii].isAmount=='yes')
      items[ii].value=trimNum(items[ii].value);
    else
      items[ii].value=trim(items[ii].value);
  }
  
  //把数据暂存到session中
 
  if (form1.Apply_kind.value=='24'|| form1.Apply_kind.value=='25'||form1.Apply_kind.value.value=='26'){
    if(!checkNeedParams())
    return;  
  if(!checkspecial(form1.primaryInfo.value))
    return;
  
  }else{
  
  if(!checkForm_Ht())  //合同是否输入完整
  return;
  if(form1.Apply_kind.value=='171'){
  if(checkJJcount())
  return;
  }
  }
  if (form1.Isquery.value=='0')
  form1.opAction.value="add";
  else if (form1.Isquery.value=='1')
  form1.opAction.value="modify";
  submitparameter("form1",form1.formCode.value);
  form1.submit();
}

//修改记录
function modifyTa(){
  if(!checkflow()){//判断是否进入流程
    return;
  }
  if(!zhAuthorize('updateTa'))//判断是否有总行授权
    return;
  form1.isJieju.value="0";  //借据
  form1.operationName.value='icbc.cmis.util.GeneralContentOp';
  form1.oper.value='showInsertView';  //点修改后进入新增页面，因为和新增的功能一样
  form1.submit();
}

//更新记录
function updateTa(){
  if(!checkNeedParams())
    return;
  if(!checkspecial(form1.primaryInfo.value))//判断业务各数据关系校验
    return;
   
  var items="";
  //取表单内的所有input项 
  items = document.getElementsByTagName("input");
  for(ii=0;ii<items.length;ii++){
    if(items[ii].isAmount=='yes')
      items[ii].value=trimNum(items[ii].value);
    else
      items[ii].value=trim(items[ii].value);
  }
    
  //把数据暂存到session中
  submitparameter("form1",form1.formCode.value);
  form1.opAction.value='modify';
  form1.submit();
}

//删除记录
function deleteTa(){  
  if(!checkflow()){//判断是否进入流程
   return;
  }
  if(!zhAuthorize('deleteTa'))//判断总行授权
    return;
  if(!deletetz()){//特殊校验
    return;
  }
  if(!confirm("是否确认要删除该业务记录?"))
    return;

  form1.opAction.value='delete';
  form1.submit();
}

function returnmainAdd(){
  pinfo=form1._primaryInfo.value;
  var pri=pinfo.split("|");
  form1.operationName.value="icbc.cmis.util.GeneralApplyFirstFormOp";
  form1.Apply_kind.value=pri[0];
  form1.Apply_stage.value=pri[1];
  form1.approveAction.value=0;
  form1.submit();
}

//返回查询页
function cancelp(){
  form1.operationName.value="icbc.cmis.util.GeneralContentOp";
  form1.oper.value="showQueryView";
  form1.submit();
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

//相关合同
function assure(){  
  var flag;//0不能修改，1可以修改
  var returnLink;
  if (form1.isJieju.value=='3'){
  alert("请先点确定保存借据，或者点返回取消借据!");
  return;
  }
  if(form1.pageMode.value=='showInsertView'||form1.pageMode.value=='showModifyView'){
    flag='1';
  }else
    flag='0';
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
  
  if (form1.isJieju.value!='2' &&  form1.isJieju.value!='6')
    submitparameter("form1",form1.formCode.value);
  }
  if(form1.isJieju.value=='6'||form1.isJieju.value=='7') 
   form1.pageMode.value='showQueryView';  //如果是查询进来就置查询状态
  //if (form1.isJieju.value=='2'||form1.isJieju.value=='3'||form1.isJieju.value=='4')
  form1.isJieju.value='0'  //返回到合同页面
   returnLink = basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true&isSessionClear=1';//返回的url
  returnLink+="&oper="+form1.pageMode.value;
  
  assureTab(flag,returnLink);
}
//到合同申请页面
function Qyhtsq(){
  form1.isJieju.value="0";  //借据
  form1.operationName.value="icbc.cmis.util.GeneralContentOp";
  form1.submit();

}
//返回到合同申请页面（不点修改时）
function Qyhtsq1(){
  form1.oper.value="showQueryView";
  form1.isJieju.value="0";  //借据
  form1.operationName.value="icbc.cmis.util.GeneralContentOp";
  form1.submit();

}
//查询不点修改进入的列表页面
function Qyjjsq(){
  form1.oper.value="showInsertView";
  form1.isJieju.value="6";  //借据
  form1.operationName.value="icbc.cmis.util.GeneralContentOp";
  form1.submit();
}

//到借据申请页面
function jjsq(){  

  if(!checkForm_Ht())
  return;
  submitparameter("form1",form1.formCode.value);
  form1.isJieju.value="2";  //借据
  form1.operationName.value="icbc.cmis.util.GeneralContentOp";
  form1.submit();
}

//借据新增到借据新增页面
function addjj(){  
  
  form1.isJieju.value="3";  //借据
  form1.operationName.value="icbc.cmis.util.GeneralContentOp";
  form1.submit();
}
//借据单笔新增(保存数据)
function insertjj(){  
  if(!checkForm_Jj())
  return;
  form1.operationName.value="icbc.cmis.util.GeneralViewOp";
  form1.opAction.value="addjjArchive";
  form1.submit();
}



//点击单笔借据进入的页面
function gotoModjj(jjcode,table_name){
   
  if (form1.isJieju.value=="6"){
  form1.isJieju.value="7"
  form1.oper.value="showInsertView";
  };//进入不能修改的借据详细页面
  else
  form1.isJieju.value="4";    
  form1.operationName.value="icbc.cmis.util.GeneralContentOp";
  form1.querytj.value=table_name+'#'+jjcode;
  form1.submit();

}
//单笔修改
function modifyJjInfo(){
  if(!checkForm_Jj())
  return;
  form1.operationName.value="icbc.cmis.util.GeneralViewOp";
  form1.opAction.value="modjjArchive";
  form1.submit();

}
//单笔删除
function deleteJjInfo(){
  if(!candeljj())
  return;
  form1.operationName.value="icbc.cmis.util.GeneralViewOp";
  form1.opAction.value="deljjArchive";
  form1.submit();

}

//借据列表中的按钮点击后返回合同页面
function returnHt(){
    form1.operationName.value="icbc.cmis.util.GeneralContentOp";
    form1.isJieju.value='0';
    form1.submit();
}

//借据中点击返回按钮，返回到借据列表页面
//借据列表中的按钮点击后返回合同页面
function returnJjList(){
    form1.operationName.value="icbc.cmis.util.GeneralContentOp";
    form1.isJieju.value='2';
    form1.submit();
}

//业务附属信息
function append(){
  var flag;//0不能修改，1可以修改
  if(form1.pageMode.value=='showInsertView'||form1.pageMode.value=='showModifyView'){
    flag='1';
  }else
    flag='0';
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
    submitparameter("form1",form1.formCode.value);
  }
  var returnurl=basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.GeneralContentOp&opDataUnclear=true';
  returnurl+="&oper="+form1.pageMode.value;
  appendTab(flag,returnurl,'');
}


function hiddenButtons(){
  document.all.image_area.style.display="none";
}


function showRelation(){
  var ts = window.showModalDialog(basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.GA.GA_InterrelatedInfoOp&opAction=EnterPage&CustomerId="+form1.Apply_customerCode.value+"&opDataUnclear=true&time=" + (new Date), window,"dialogWidth:650px;dialogHeight:450px;center:yes;help:no;status:no;resizable:no;z-lock:yes;moveable:no;copyhistory:yes");
}


function isweekday(str){
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
 }

