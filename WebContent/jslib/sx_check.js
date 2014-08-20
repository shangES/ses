    function sx_check(enp_code,enp_type,loan_date,curr_type,loan_amount,uniqueID1,uniqueID2,uniqueID3,uniqueID4,v_loantype,v_flowtype){
     /*   enp_code   企业代码
          enp_type  企业类型
         loan_date   放款日期
         curr_type   币种
         loan_amount 金额
         uniqueid1   唯一主键1  表名|客户号|业务编号|相关业务编号|业务申请号|
         uniqueid2   唯一主键2  品种|业务开始时间|业务终止时间|业务所属地区|是否项目融资标记|贷款形式|本次业务发生额以人民币计金额|01|
         uniqueid3   唯一主键3  以人民币计的相关业务总额|本次业务对应的风险量|本次需要控制的风险量|-业务对应的加权担保系数|申请人|审批人|
         uniqueid4   唯一主键4  贷款方式|具体贷款方式|
         v_loantype  da340362020业务种类
         v_flowtype  --流程标记   0 申请 1审批 2 准贷证 3试算

        是否刚性控制 判断条件： v_flowtype=0 and isProve=1  刚性控制
        是否柔性控制 判断条件：  v_flowtype=1 （此时需双人临柜 ）或
                                 v_flowtype=0 and isProve=2  柔性控制
         返回参数：false --为错误或超收信  true --授信控制通过
         
         update by wz 20051226  对于变量用encode()处理

      */
       var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
        var parser=new ActiveXObject("microsoft.xmldom");
        parser.async="false";
        
        var tmp = "&EnpCode="+encode(enp_code)+"&LoanDate="+encode(loan_date)+"&CurrType="+encode(curr_type)+"&LoanAmount="+encode(loan_amount)+"&UniqueID1="+encode(uniqueID1)+"&UniqueID2="+encode(uniqueID2)+"&UniqueID3="+encode(uniqueID3)+"&UniqueID4="+encode(uniqueID4)+ "&LoanType="+encode(v_loantype)+"&time="+(new Date)+"&FlowType="+encode(v_flowtype) + "&Enp_type=" + encode(enp_type);
         objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.util_SxOper&opDataUnclear=true&opAction=sxop60'+tmp,false);
         objHTTP.send();
          var xml = objHTTP.responseText;
           if(!parser.loadXML(xml)) {
              
              return false;
           }
          error = parser.getElementsByTagName("error");
          if(error.length > 0) {
            alert(error.item(0).text);
            return false;
          }
          var out_flag = parser.documentElement.getAttribute("out_flag");
          var out_mesg = parser.documentElement.getAttribute("out_mesg");
          if(out_flag == "1"){  //超授信
            while (out_mesg.indexOf('*')!=-1) {
             var alert_info = out_mesg.substring(0,out_mesg.indexOf('*'));
             if (!confirm(alert_info)) return false;
             out_mesg = out_mesg.substring(out_mesg.indexOf('*')+1);
            }
            if (v_flowtype=="2")
               return false;//准贷证时刚性控制
            
          }else if(out_flag == "2"){ //异常
            alert(out_mesg);
            return false;
          }else if(out_flag == "3"){ //有提示信息
             while (out_mesg.indexOf('*')!=-1) {
             var alert_info = out_mesg.substring(0,out_mesg.indexOf('*'));
             alert(alert_info);
             out_mesg = out_mesg.substring(out_mesg.indexOf('*')+1);
            }
            return true;
          }
          return true;
     }

   function sx_auth(enp_code) {
     var ts = window.showModalDialog(basepath0606+"/util/util_Authorize.jsp?module=icbc.cmis.FG.BankDraftMgrOp&time=" + (new Date),"企业"+enp_code+"申请贷款超授信","dialogWidth:295px;dialogHeight:230px;center:yes;help:no;status:no;resizable:no");
     if(ts != null) {
       return true;
     }
     return false;
   }
