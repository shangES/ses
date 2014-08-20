/**小企业审批
  author:林植
  date:20041103
*/
var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
var parser=new ActiveXObject("microsoft.xmldom");
parser.async="false";
var basepath='/icbc/cmis';

/**
 function func_check_smallenp(enpcode)
 功能：根据传入企业编号判断是否小企业
 传入参数：enpcode(企业编号)
 返回：true  是小企业
      false  alert(不是小企业) 
       空    出错 
*/
function func_check_smallenp(enpcode)
{
   var tmp = "enpcode="+enpcode+"&time="+(new Date);
   objHTTP.open('GET',basepath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_SmallApprove_toolOp&opDataUnclear=true&opAction=60001&'+tmp,false);
   objHTTP.send();
   xml = objHTTP.responseText;
     if(!parser.loadXML(xml)) {
        return;
     }
   var error = parser.getElementsByTagName("error");
    if(error.length > 0) {
      alert(error.item(0).text);
      return;
    }
    var out_flag = parser.documentElement.getAttribute("smallenp");
    
   if (out_flag == "1" ){
   	return true;
      }
   else
   	{
   	return false;
        } 

}
/**
 功能：判断保险金是否合法
 传入参数：enpcode（企业编号）,applykind（业务编号）， bailrate（保证金比例），
          applyno（申请号）， obj（存放特别授权号的页面对象），grant_seq1（使用过的特别授权号）
 返回：
       true   保险金合法
              违反软性控制原则 confirm(客户信用等级为XXX,保险金比例需要在[..])
              
       false  违反刚性控制原则 alert(客户信用等级为XXX,保险金比例必须大于XX) 
       
        空    出错 
*/
function func_check_bailrate(enpcode,applykind,bailrate,applyno,obj,grant_seq1)
{
   var granttype = "";
   //01，承兑汇票。特别授权种类是A1
   if(applykind == "01")
      granttype = "A1";
   else if(applykind == "02" || applykind == "03")  //02，非融资类保函，03，融资类保函
      granttype = "A2";
   else if(applykind == "04" || applykind == "05")  //04，国内信用证，05，国外信用证
      granttype = "A3";
   
   var tmp = "enpcode="+enpcode+"&applykind="+applykind+"&bailrate="+bailrate+"&time="+(new Date);
   objHTTP.open('GET',basepath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_SmallApprove_toolOp&opDataUnclear=true&opAction=60002&'+tmp,false);
   objHTTP.send();
   xml = objHTTP.responseText;
     if(!parser.loadXML(xml)) {
        return;
     }
   var error = parser.getElementsByTagName("error");
    if(error.length > 0) {
      alert(error.item(0).text);
      return;
    }	
    var out_flag = parser.documentElement.getAttribute("flag");
    var out_msg = parser.documentElement.getAttribute("msg");
    
    switch (out_flag)
    {
      case "0":
        return true;
        break;
      case "1":
        
        tmp = basepath+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&xmlOutput=true&opDataUnclear=true&oper=isjianmian&enpcode="+enpcode+"&granttype="+granttype+"&applyno="+applyno+"&time=" + (new Date);
         objHTTP.Open('GET',tmp,false);

         objHTTP.Send();

         var xml = objHTTP.responseText;
         //alert(xml);

         if(!parser.loadXML(xml)) {
           alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
           return false;
         }
         error = parser.getElementsByTagName("error");
         if(error.length > 0) {
           alert(error.item(0).text);
           return false;
         }
         var count = parser.documentElement.getAttribute("count");
         count = count/1;
        if(count == 0){
          	alert(out_msg);
            return false;
        }
        else{
           var grant_seq = window.showModalDialog(basepath+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=listtbsq&enpcode="+enpcode+"&sqtype="+granttype+"&jjh="+applyno+"&bili="+bailrate+"&grant_seq="+grant_seq1+"&time="+new Date(),window,"dialogWidth:570px;dialogHeight:350px;center:yes;help:no;status:no;resizable:no");
              if(grant_seq == null){
                alert(out_msg);
                return false;
              }
              else{
                obj.value = grant_seq;
                return true;
              }
        
        } 
        break;
      case "2":
        confirm(out_msg);
        return true;
        break;
      default:
        return false;
     }
 
}

/**
  功能：判断保险金是否合法
 传入参数：enpcode 企业编号,applykind业务编号 opcur业务币种 opamount业务金额 
           bailcur保证金币种 bailamount保证金金额，applyno（申请号），
          obj（存放特别授权号的页面对象），grant_seq1（使用过的特别授权号）
 返回：
       true   保险金合法
              违反软性控制原则 confirm(客户信用等级为XXX,保险金比例需要在[..])
              
       false  违反刚性控制原则 alert(客户信用等级为XXX,保险金比例必须大于XX) 
       
        空    出错 
 */

function func_check_bailrate2(enpcode,applykind,opcur,opamount,bailcur,bailamount,applyno,obj,grant_seq1,obj1)
{
   var granttype = "";
   //01，承兑汇票。特别授权种类是A1
   if(applykind == "01")
      granttype = "A1";
   else if(applykind == "02" || applykind == "03")  //02，非融资类保函，03，融资类保函
      granttype = "A2";
   else if(applykind == "04" || applykind == "05")  //04，国内信用证，05，国外信用证
      granttype = "A3";
   
   
   var tmp = "enpcode="+enpcode+"&applykind="+applykind+"&opcur="+opcur+"&opamount="+opamount+"&bailcur="+bailcur+"&bailamount="+bailamount+"&time="+(new Date);
   objHTTP.open('GET',basepath+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_SmallApprove_toolOp&opDataUnclear=true&opAction=60003&'+tmp,false);
   objHTTP.send();
   xml = objHTTP.responseText;
     if(!parser.loadXML(xml)) {
        return false;
     }
   var error = parser.getElementsByTagName("error");
    if(error.length > 0) {
      alert(error.item(0).text);
      return false;
    }	
    var out_flag = parser.documentElement.getAttribute("flag");
    var out_msg = parser.documentElement.getAttribute("msg");
    var out_bailrate = parser.documentElement.getAttribute("bailrate");
    obj1.value = out_bailrate;
    switch (out_flag)
    {
      case "0":
        return true;
        break;
      case "1":
        tmp = basepath+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&xmlOutput=true&opDataUnclear=true&oper=isjianmian&enpcode="+enpcode+"&granttype="+granttype+"&applyno="+applyno+"&time=" + (new Date);
         objHTTP.Open('GET',tmp,false);

         objHTTP.Send();

         var xml = objHTTP.responseText;
         //alert(xml);

         if(!parser.loadXML(xml)) {
           alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
           return false;
         }
         error = parser.getElementsByTagName("error");
         if(error.length > 0) {
           alert(error.item(0).text);
           return false;
         }
         var count = parser.documentElement.getAttribute("count");
         count = count/1;
        if(count == 0){
          	alert(out_msg);
            return false;
        }
        else{
           var grant_seq = window.showModalDialog(basepath+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FE.FE_utilOp&opDataUnclear=true&oper=listtbsq&enpcode="+enpcode+"&sqtype="+granttype+"&jjh="+applyno+"&bili="+out_bailrate+"&grant_seq="+grant_seq1+"&time="+new Date(),window,"dialogWidth:570px;dialogHeight:350px;center:yes;help:no;status:no;resizable:no");
              if(grant_seq == null){
                alert(out_msg);
                return false;
              }
              else{
                obj.value = grant_seq;
                return true;
              }
        
        } 
      case "2":
        confirm(out_msg);
        return true;
        break;
      default:
        return false;
     }
 
}
