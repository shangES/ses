// common_control.js

/****************************************************************************************************************
                              以下是公用校验函数
*****************************************************************************************************************/
/******************************************************
 *功能描述：统一了贷款链，业务附属台账，担保的控制功能
 *参数说明：customercode = 客户号                                                    
 *参数说明：areacode = 地区号                                    
 *参数说明：tradecode   = 业务台帐或申请表中的合同号
 *参数说明：formercode     = 前一业务号 当业务品种为展期时，送借据号，当业务品种为信用证修改时，为信用证号，当业务品种为保函修改时，为保函号                      
 *参数说明：opflag       =   0－申请 1－正式        
 *参数说明：yewukind = 业务类型                
                    

 *作者：huxiaozhong
 *创建日期：2005-11-29 
 *****************************************************/

var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
var parser=new ActiveXObject("microsoft.xmldom");
parser.async="false";


function control_check(customercode,areacode,tradecode,formercode,opflag,yewukind) {

   var act = basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.Util_ControlOp&xmlOutput=true&opDataUnclear=true&opAction=20001&customercode="+customercode+"&areacode="+areacode+"&tradecode="+encode(tradecode)+"&formercode="+formercode+"&opflag="+opflag+"&yewukind="+yewukind+"&time="+new Date();
     

        objHTTP.Open('GET',act,false);
        objHTTP.Send();
        //查询出错处理
        if(!parser.loadXML(objHTTP.responseText)) {
          alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
          return false;
        }
        //如果session超时和交易失败，显示错误信息
        rootElement = parser.documentElement;
        if (rootElement.tagName == "error") {
          errorCode = "";
          errorMsg = "";
          for (i=0; i<rootElement.childNodes.length; i++) {
            if (rootElement.childNodes.item(i).tagName == "errorCode") {
              errorCode = rootElement.childNodes.item(i).text;
            }
            else if (rootElement.childNodes.item(i).tagName == "errorDispMsg") {
              errorMsg = rootElement.childNodes.item(i).text;
            }
          }
          alert(" 错误代码：" + errorCode + "\n错误描述：" + errorMsg);
          return false;
        }
          var node = rootElement.childNodes.item(0);
          var result=node.getAttribute("result");
          if (result=="true")
          return true;
          else{
          alert(node.getAttribute("message"));
          return false;
          }
}