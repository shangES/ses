   /**
   *判断是否不足成立一年标志
   * @create date 2007-6-12
   * @create by zheng qi bin
   */
 

	/*--
	功能：判断是否不足成立一年标志
	传入参数：
     客户代码，评级时间，评级类型；
	--*/
	
	function Isdlpjflg(khcode,pjdate,pjtype) {
	   //校验柜员代码输入合法性

     if (khcode ==null || khcode =="")  {
	       alert("客户代码不能为空！");
		   return false;
	  }
	  if (pjdate ==null || pjdate =="")  {
	       alert("评级时间不能为空！");
		   return false;
	  }
	  if (pjtype ==null || pjtype =="")  {
	       alert("评级类型不能为空！");
		   return false;
	  }
	  
	  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      parser.async="false";
      var act = basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.khpj.EE.KhpjflagOp&xmlOutput=true&opDataUnclear=true&oper=20001&khcode="+khcode+"&pjdate="+pjdate+"&pjtype="+pjtype+"&time="+new Date();
      //查询
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
    if(rootElement.tagName=="datas")
    {
       var result=rootElement.getAttribute("ret_flag");
       
	   return result;
		 
		  
    }
  }
