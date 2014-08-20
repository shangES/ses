 var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
 var parser=new ActiveXObject("microsoft.xmldom");
 parser.async="false";
 
 function func_xdyw_sxctrl(Userid,FlowFlag,YWDate,YWType,FeeValue,MoneyKind,EnsureValue,EnsureMoneyType,mainFlag1,mainFlag2,mainFlag4){
	        
	      var tmp = "Userid="+Userid+"&FlowFlag="+FlowFlag+"&YWDate="+YWDate+"&YWType="+YWType+"&FeeValue="+FeeValue+"&MoneyKind="+MoneyKind+"&EnsureValue="+EnsureValue+"&EnsureMoneyType="+EnsureMoneyType+"&mainFlag1="+encode(mainFlag1)+"&mainFlag2="+encode(mainFlag2);
          //alert(tmp);
           //var tmp=   "Userid=040290000000243"+"&FlowFlag=0"+"&YWDate=20180101"+"&YWType=01"+"&FeeValue=9000"+"&MoneyKind=01"+"&EnsureValue=0"+"&EnsureMoneyType=01"+"&mainFlag1=TA200061|040290000000243|04020203-2013(´û¿î)5025ºÅ|04020203-2013(´û¿î)5025ºÅ|"+"&mainFlag2=6060|20210106|20240101|04020203|1|10|";      
          //var tmp=   "Userid=120290000180102"+"&FlowFlag=0"+"&YWDate=20180101"+"&YWType=01"+"&FeeValue=9000"+"&MoneyKind=01"+"&EnsureValue=0"+"&EnsureMoneyType=01"+"&mainFlag1=TA200061|120290000180102|12020206-2004(´û¿î)0009ºÅ|12020206-2004(´û¿î)0009ºÅ|"+"&mainFlag2=6060|20210106|20240101|04020203|1|10|";      
          objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?opAction=20001&operationName=icbc.cmis.util.util_xdyw_ctrlOp&xmlOutput=true&opDataUnclear=true&'+tmp+"&time="+(new Date),false);
          //objHTTP.open('GET','/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?opAction=20001&operationName=icbc.cmis.util.util_xdyw_ctrlOp&xmlOutput=true&opDataUnclear=true',false);
          objHTTP.send();          
          var xml = objHTTP.responseText;    
          //alert("xml="+xml);      
          if(!parser.loadXML(xml)) { 
                 
            return;
          } 
          error = parser.getElementsByTagName("error");
          if(error.length > 0) {
            alert(error.item(0).text);
            return;
          }
          var nodes = parser.documentElement.childNodes;          
          var ts=new Array(7);
          if(nodes.length > 0){            
              var node = nodes.item(0);            
              ts[0]=node.getAttribute("tradetype");
              ts[1]=node.getAttribute("customertype");              
              ts[2]=node.getAttribute("applycode");
              ts[3]=node.getAttribute("mainFlag2");
              ts[4]=node.getAttribute("mainFlag3");
              ts[5]=node.getAttribute("flag");
              ts[6]=node.getAttribute("msg");
              //alert(ts[0]);
              //alert(ts[1]);
              //alert(ts[2]);
              //alert(ts[3]);
              //alert(ts[4]);
              //alert(ts[5]);
              //alert(ts[6]);
              
          }
            //alert(ts);        
	        if (ts[5]=='1'){ 
					alert(ts[6]);
					return false;
				}
					else{
					 var backtip=sx_check(Userid,ts[1],YWDate,MoneyKind,FeeValue,mainFlag1+ts[2]+'|',ts[3],ts[4],mainFlag4,ts[0],FlowFlag);
					 //alert(backtip);
					 return backtip;
					 //return true;
				  }
          
 }



