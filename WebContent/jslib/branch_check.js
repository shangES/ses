    function branch_check(area_code,deal_date){

       var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
        var parser=new ActiveXObject("microsoft.xmldom");
        parser.async="false";

        var tmp = "area_code="+area_code+"&deal_date="+deal_date+"&time="+(new Date);
         objHTTP.open('GET','/icbc/cmis/servlet/icbc.cmis.util.util_VBranchCheck?'+tmp,false);
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
          var out_flag = parser.documentElement.getAttribute("out_flag");
          var out_mesg = parser.documentElement.getAttribute("out_mesg");
//           alert( out_mesg );
          if(out_flag == "1"){  //超授信
/*
            while (out_mesg.indexOf('*')!=-1) {
             var alert_info = out_mesg.substring(0,out_mesg.indexOf('*'))+"尚未进行当月借据分类处理，是否继续发送?";
             if (!confirm(alert_info)) return false;
             out_mesg = out_mesg.substring(out_mesg.indexOf('*')+1);
            }
*/
             var alert_info = "尚有"+out_mesg+"尚未进行当月借据分类处理，是否继续发送?";
             if (!confirm(alert_info)) return false;
          }
            return true;
     }