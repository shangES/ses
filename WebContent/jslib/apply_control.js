/**
   *申请控制
   * @param qycode     客户代码
   * @param areacode   地区代码
   * @param dkfs       具体贷款方式
   * @param ywpz       业务品种
   * @param sqje       申请金额
   * @param currtype   币种
   * @param sqtype     特别授权参数
   * @param checktype  如不满足刚性控制条件，是否需要双人临柜。
   *                   发放准贷证时，传入借据号，需要双人临柜 申请不用双人临柜为0.
   * @return boolean
   * @
   * --	 -1 企业退出标志为'全部'(刚性)
   * --	 -2 该业务品种为停办 (刚性)
   * --	 -3 交叉违纪
   * --	 -4 不符合刚性控制
   * --  -5 停办但是有特别授权
   * --  -6 共享行不能办理该业务品种 
   * --  -7 没有实时汇率
   * --   0 正确
   * --   99 数据库操作失败
   */
  /**
  * 修改于 2003、7、19
  * 修改原因：客户申请的业务如果是停办业务，只要有特别授权也可办理。
  * 修改人：郭芃
  * 修改内容：在调用申请控制存储过程时新增两个传入参数币种和特别授权参数
  */
/**
 * 修改于 2003、7、24
 * 修改原因：原申请控制存储过程对于项目额度申请多币种不能支持。对于非流动资金贷款申请的停办业务控制也存在问题。
 * 修改人： 郭芃
 * 修改内容：对于项目额度申请调用新的申请控制存储过程Proc_AppCtrl_project，对于非流动资金贷款申请调用Proc_AppCtrl_contract
 * 
 */
 
 /**
 * 修改于 2003、10、20
 * 修改原因：停办业务种类调整，引起申请控制存储过程参数变动
 * 修改人：郭芃
 * 修改内容：调整申请控制存储过程参数，对于信用证、信用证修改、进口押汇、出口押汇增加申请控制，
 *           对于刚性控制部分，贸易融资部分暂时不调用 
 * 
 */
 
 /* 修改于 20050608
  * 修改原因：增加共享行业务办理控制。
  * 修改人：郭芃
  * 修改内容：对于共享行办理业务时增加是否被授权的判断。
 */

function func_apply_control(enpcode,areacode,jtdkfs,ywpz1,ywpz2,sqje,currtype,checktype,sqtype,dkfs,dkxs){
   var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
   var parser=new ActiveXObject("microsoft.xmldom");
   parser.async="false";

   var tmp = "enpcode="+enpcode+"&areacode="+areacode+"&currtype="+currtype+"&dkfs="+dkfs+"&ywpz1="+ywpz1+"&ywpz2="+ywpz2+"&sqje="+sqje+"&sqtype="+sqtype+"&jtdkfs="+jtdkfs+"&dkxs="+dkxs+"&time="+(new Date);

   objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=appctrl&'+tmp,false);
   objHTTP.send();
    xml = objHTTP.responseText;
     if(!parser.loadXML(xml)) {
        return;
     }
    error = parser.getElementsByTagName("error");
    if(error.length > 0) {
      alert(error.item(0).text);
      return;
    }
    var out_flag = parser.documentElement.getAttribute("result");
    var out_message = parser.documentElement.getAttribute("message");
    if(out_flag!="0"){
       while (out_message.indexOf('*')!=-1) {
          out_message = out_message.replace("*","\r\n");
       }
    }
    
    if(out_flag == "0"){
      return true;
    }
    else if(out_flag == "-1"||out_flag == "-8"){
      alert(out_message);
      return false;
    }
    else if(out_flag == "-2"){
      if(checktype == "0" || checktype == "1"){
        alert(out_message);
        return false;
      }
      else{
        var tmp = "enpcode="+enpcode+"&due="+checktype+"&tbyw="+ywpz1+"&currtype="+currtype+"&time="+(new Date);

        objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=checkusespecial&'+tmp,false);
        objHTTP.send();
        xml = objHTTP.responseText;
        if(!parser.loadXML(xml)) {
          return;
        }
        error = parser.getElementsByTagName("error");
        if(error.length > 0) {
          alert(error.item(0).text);
          return;
        }
        var return_count = parser.documentElement.getAttribute("return");
        return_count = return_count/1;
        if(return_count==0){
          alert(out_message);
          return false;
        }
        else{
          return true;
        }
      }
    }
    else if(out_flag == "-3"){
      if(confirm(out_message))
        return true;
      else
        return false;
    }
    else if(out_flag == "-4"){
      if(checktype != "0"){
        if(confirm(out_message)){
          var ts = window.showModalDialog(basepath0606+"/util/util_Authorize.jsp?module=企业"+enpcode+"的贷款不满足刚性控制条件"+"&time=" + (new Date),'不满足刚性控制条件',"dialogWidth:295px;dialogHeight:230px;center:yes;help:no;status:no;resizable:no");
          if(ts != null) {
            return true;
          }
          return false;
        }
        else
          return false;
      }
      else{
        if(confirm(out_message))
          return true;
        else
          return false;
      }
    }
    else if(out_flag == "99"){
      alert(out_message);
      return false;
    }
    else if(out_flag == "-5"){
      if(checktype == "0"|| checktype == "1"){
        return true;
      }
      else{
        var tmp = "enpcode="+enpcode+"&due="+checktype+"&tbyw="+ywpz1+"&currtype="+currtype+"&time="+(new Date);

        objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=checkusespecial&'+tmp,false);
        objHTTP.send();
        xml = objHTTP.responseText;
        if(!parser.loadXML(xml)) {
          return;
        }
        error = parser.getElementsByTagName("error");
        if(error.length > 0) {
          alert(error.item(0).text);
          return;
        }
        var return_count = parser.documentElement.getAttribute("return");
        return_count = return_count/1;
        if(return_count==0){
          alert(out_message);
          return false;
        }
        else{
          return true;
        }
      }
    }
    else if(out_flag == "-6"){
      if(checktype == "0"){
        alert(out_message);
        return false;
      }
    }
    else if(out_flag == "-7"){
      alert(out_message);
      return false;
    }
    return true;
}

function func_apply_control_project(enpcode,areacode,dkfs,ywpz1,ywpz2,sqje,currtype,checktype,sqtype,jtdkfs,dkxs){
   var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
   var parser=new ActiveXObject("microsoft.xmldom");
   parser.async="false";

   var tmp = "enpcode="+enpcode+"&areacode="+areacode+"&currtype="+currtype+"&dkfs="+dkfs+"&ywpz1="+ywpz1+"&ywpz2="+ywpz2+"&sqje="+sqje+"&sqtype="+sqtype+"&jtdkfs="+jtdkfs+"&dkxs="+dkxs+"&time="+(new Date);

   objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=appctrlproject&'+tmp,false);
   objHTTP.send();
    xml = objHTTP.responseText;
     if(!parser.loadXML(xml)) {
        return;
     }
    error = parser.getElementsByTagName("error");
    if(error.length > 0) {
      alert(error.item(0).text);
      return;
    }
    var out_flag = parser.documentElement.getAttribute("result");
    var out_message = parser.documentElement.getAttribute("message");
    if(out_flag!="0"){
       while (out_message.indexOf('*')!=-1) {
          out_message = out_message.replace("*","\r\n");
       }
    }
    if(out_flag == "0"){
      return true;
    }
    else if(out_flag == "-1"||out_flag == "-8"){
      alert(out_message);
      return false;
    }
    else if(out_flag == "-2"){
      if(checktype == "0"){
        alert(out_message);
        return false;
      }
      else{
        var tmp = "enpcode="+enpcode+"&due="+checktype+"&tbyw="+ywpz1+"&currtype="+currtype+"&time="+(new Date);

        objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=checkusespecial&'+tmp,false);
        objHTTP.send();
        xml = objHTTP.responseText;
        if(!parser.loadXML(xml)) {
          return;
        }
        error = parser.getElementsByTagName("error");
        if(error.length > 0) {
          alert(error.item(0).text);
          return;
        }
        var return_count = parser.documentElement.getAttribute("return");
        return_count = return_count/1;
        if(return_count==0){
          alert(out_message);
          return false;
        }
        else{
          return true;
        }
      }
    }
    else if(out_flag == "-3"){
      if(confirm(out_message))
        return true;
      else
        return false;
    }
    else if(out_flag == "-4"){
      if(checktype != "0"){
        if(confirm(out_message)){
          var ts = window.showModalDialog("/icbc/cmis/util/util_Authorize.jsp?module=企业"+enpcode+"的贷款不满足刚性控制条件"+"&time=" + (new Date),'不满足刚性控制条件',"dialogWidth:295px;dialogHeight:230px;center:yes;help:no;status:no;resizable:no");
          if(ts != null) {
            return true;
          }
          return false;
        }
        else
          return false;
      }
      else{
        if(confirm(out_message))
          return true;
        else
          return false;
      }
    }
    else if(out_flag == "99"){
      alert(out_message);
      return false;
    }
    else if(out_flag == "-5"){
      if(checktype == "0"){
        return true;
      }
      else{
        var tmp = "enpcode="+enpcode+"&due="+checktype+"&tbyw="+ywpz1+"&currtype="+currtype+"&time="+(new Date);

        objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=checkusespecial&'+tmp,false);
        objHTTP.send();
        xml = objHTTP.responseText;
        if(!parser.loadXML(xml)) {
          return;
        }
        error = parser.getElementsByTagName("error");
        if(error.length > 0) {
          alert(error.item(0).text);
          return;
        }
        var return_count = parser.documentElement.getAttribute("return");
        return_count = return_count/1;
        if(return_count==0){
          alert(out_message);
          return false;
        }
        else{
          return true;
        }
      }
    }
    else if(out_flag == "-6"){
      alert(out_message);
      return false;
    }
    else if(out_flag == "-7"){
      alert(out_message);
      return false;
    }
    return true;
}


function func_apply_control_contract(enpcode,areacode,dkfs,ywpz1,ywpz2,sqje,currtype,checktype,sqtype,spno,jtdkfs,dkxs){
   var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
   var parser=new ActiveXObject("microsoft.xmldom");
   parser.async="false";

   var tmp = "enpcode="+enpcode+"&areacode="+areacode+"&currtype="+currtype+"&dkfs="+dkfs+"&ywpz1="+ywpz1+"&ywpz2="+ywpz2+"&sqje="+sqje+"&sqtype="+sqtype+"&spno="+spno+"&jtdkfs="+jtdkfs+"&dkxs="+dkxs+"&time="+(new Date);

   objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=appctrlcontract&'+tmp,false);
   objHTTP.send();
    xml = objHTTP.responseText;
     if(!parser.loadXML(xml)) {
        return;
     }
    error = parser.getElementsByTagName("error");
    if(error.length > 0) {
      alert(error.item(0).text);
      return;
    }
    var out_flag = parser.documentElement.getAttribute("result");
    var out_message = parser.documentElement.getAttribute("message");
    if(out_flag!="0"){
       while (out_message.indexOf('*')!=-1) {
          out_message = out_message.replace("*","\r\n");
       }
    }
    if(out_flag == "0"){
      return true;
    }
    else if(out_flag == "-1"||out_flag == "-8"){
      alert(out_message);
      return false;
    }
    else if(out_flag == "-2"){
      if(checktype == "0"){
        alert(out_message);
        return false;
      }
      else{
        var tmp = "enpcode="+enpcode+"&due="+checktype+"&tbyw="+ywpz1+"&currtype="+currtype+"&time="+(new Date);

        objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=checkusespecial&'+tmp,false);
        objHTTP.send();
        xml = objHTTP.responseText;
        if(!parser.loadXML(xml)) {
          return;
        }
        error = parser.getElementsByTagName("error");
        if(error.length > 0) {
          alert(error.item(0).text);
          return;
        }
        var return_count = parser.documentElement.getAttribute("return");
        return_count = return_count/1;
        if(return_count==0){
          alert(out_message);
          return false;
        }
        else{
          return true;
        }
      }
    }
    else if(out_flag == "-3"){
      if(confirm(out_message))
        return true;
      else
        return false;
    }
    else if(out_flag == "-4"){
      if(checktype != "0"){
        if(confirm(out_message)){
          var ts = window.showModalDialog("/icbc/cmis/util/util_Authorize.jsp?module=企业"+enpcode+"的贷款不满足刚性控制条件"+"&time=" + (new Date),'不满足刚性控制条件',"dialogWidth:295px;dialogHeight:230px;center:yes;help:no;status:no;resizable:no");
          if(ts != null) {
            return true;
          }
          return false;
        }
        else
          return false;
      }
      else{
        if(confirm(out_message))
          return true;
        else
          return false;
      }
    }
    else if(out_flag == "99"){
      alert(out_message);
      return false;
    }
    else if(out_flag == "-5"){
      if(checktype == "0"){
        return true;
      }
      else{
        var tmp = "enpcode="+enpcode+"&due="+checktype+"&tbyw="+ywpz1+"&currtype="+currtype+"&time="+(new Date);

        objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=checkusespecial&'+tmp,false);
        objHTTP.send();
        xml = objHTTP.responseText;
        if(!parser.loadXML(xml)) {
          return;
        }
        error = parser.getElementsByTagName("error");
        if(error.length > 0) {
          alert(error.item(0).text);
          return;
        }
        var return_count = parser.documentElement.getAttribute("return");
        return_count = return_count/1;
        if(return_count==0){
          alert(out_message);
          return false;
        }
        else{
          return true;
        }
      }
    }
    else if(out_flag == "-6"){
      alert(out_message);
      return false;
    }
    else if(out_flag == "-7"){
      alert(out_message);
      return false;
    }
    return true;
}

/**
 * 查询该企业该申请是否存在未扫描的必扫影像资料
 * @param enpcode 客户代码
 * @param applycode  申请书号
 * @param opcode   业务种类
 * @param assuretype 担保方式，四位，第一位表示信用，第二位表示保证
 * 第三位表示抵押，第四位表示质押
 *
 * 如果返回0，则表示不存在未扫描的必扫影像资料
 * 返回1，则表示存在未扫描的必扫影像资料
 */
function imagecontrol(enpcode,applycode,opcode,assuretype){
   var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
   var parser=new ActiveXObject("microsoft.xmldom");
   parser.async="false";

   var tmp = "enpcode="+enpcode+"&applycode="+encode(applycode)+"&assuretype="+assuretype+"&opcode="+opcode+"&time="+(new Date);

   objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=queryimage&'+tmp,false);
   objHTTP.send();
    xml = objHTTP.responseText;
     if(!parser.loadXML(xml)) {
        return;
     }
    error = parser.getElementsByTagName("error");
    if(error.length > 0) {
      alert(error.item(0).text);
      return;
    }
    var out_flag = parser.documentElement.getAttribute("result");

    if(out_flag == "0"){
      return true;
    }
    else{
      alert("该企业的这笔申请还有未扫描的影像资料，请扫描。");
      return false;
    }
}

/**
 * 查询该企业该业务是否存在未扫描的必扫影像资料
 * @param enpcode 客户代码
 * @param applycode  业务书号
 * @param opcode   业务种类
 *
 *
 * 如果返回0，则表示不存在未扫描的必扫影像资料
 * 返回1，则表示存在未扫描的必扫影像资料
 */
function imageaftercontrol(enpcode,applycode,opcode){
   var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
   var parser=new ActiveXObject("microsoft.xmldom");
   parser.async="false";

   var tmp = "enpcode="+enpcode+"&applycode="+encode(applycode)+"&opcode="+opcode+"&time="+(new Date);

   objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=queryimage1&'+tmp,false);
   objHTTP.send();
    xml = objHTTP.responseText;
     if(!parser.loadXML(xml)) {
        return;
     }
    error = parser.getElementsByTagName("error");
    if(error.length > 0) {
      alert(error.item(0).text);
      return;
    }
    var out_flag = parser.documentElement.getAttribute("result");

    if(out_flag == "0"){
      return true;
    }
    else{
      alert("该企业的这笔业务还有未扫描的影像资料，请扫描。");
      return false;
    }
}



