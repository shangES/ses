/**
   *柜员校验
   * @create date 2004-4-24 15:34
   * @create by zheng ze zhou
   * @注：此js库最初用于签批人管理模块
   */
  /**
  * 修改日期：2004-4-27 9:09
  * 修改原因：增加了查询判断审批人是否有签批权
  * 修改人：郑泽洲
  * 修改内容：增加了查询判断审批人是否有签批权
  */
  /**
  * 修改日期：2004-7-21 15:51
  * 修改原因：以物抵债项目新增函数
  * 修改人：郑泽洲
  * 修改内容：增加了周星江提供的函数
  */
  /**
  * 修改日期：2005-2-21 15:58
  * 修改原因：新增函数，用于补录权限控制
  * 修改人：郑泽洲
  * 修改内容：补录权限查询函数
  */

 //var webBasePath = "/icbc/cmis";

	/*--
	功能：检查前台输入的柜员代码
	参数：obj 页面上的一个输入框，此输入框同时显示柜员代码和名字
	            obj2 页面上的一个输入框（hidden域），此输入框保存柜员代码
	--*/
	function checkEmployeeCode(obj,obj2) {
	   //校验柜员代码输入合法性
	   if (obj.readOnly == true) return false;
	   var  code = obj.value;
     if (code.length != 9)  {
	       alert("请输入9位柜员号！");
		   return false;
	  }
	  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      parser.async="false";
      var act = basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.MA.MA_EmployeeCodeVerifyOp&xmlOutput=true&opDataUnclear=true&oper=checkEmployee&employeecode="+code+"&time="+new Date();
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
       var result=rootElement.getAttribute("employeename");
       if (result == '0') {
			  alert("该柜员不存在！");
			  obj.focus();
			  return false;
		   }
		   else {
			  if (!confirm("请确认您所输入的柜员是:"+code+" "+result+"?")) {
          obj.value = "";
				  return false;
			  }
			  else {
			 	  obj.value = code+" "+result;
				  obj2.value = code;
			  }
		  }
    }
  }

	/*--
	功能：判断审批人是否有签批权
	参数：employeecode 传入该柜员代码
	输出：true 有权 false 无权
	--*/
	function isEmployeeSign(employeecode) {
	   //校验柜员代码输入合法性
	   var  code = employeecode;
     if (code.length != 9)  {
	       alert("请输入9位柜员号！");
		   return false;
	  }
	  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      parser.async="false";
      var act = basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.MA.MA_EmployeeCodeVerifyOp&xmlOutput=true&opDataUnclear=true&oper=isEmployeeSign&employeecode="+code+"&time="+new Date();
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
       var result=rootElement.getAttribute("employeename");
       if (result == '1') {
			  return true;
		   }
		   else {
			  return false;
		  }
    }
  }

	/*--
	功能：检查前台输入的柜员代码
	参数：obj 页面上的一个输入框，此输入框同时显示柜员代码
	备注：另一命为obj+"name"的输入框显示名字
	--*/
	function checkEmployee(obj) {
	   //校验柜员代码输入合法性
	   var  code = obj.value;
     if (code.length != 9)  {
	       alert("请输入9位柜员号！");
		   obj.value = "";
		   return false;
	  }
	  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      parser.async="false";
      var act = basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.MA.MA_EmployeeCodeVerifyOp&xmlOutput=true&opDataUnclear=true&oper=checkEmployee&employeecode="+code+"&time="+new Date();
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
      var objName;
      var result=rootElement.getAttribute("employeename");
      if (result == '0') {
			  alert("该柜员不存在！");
			  obj.focus();
			  return false;
		  }
		  else {
			  if (!confirm("请确认您所输入的柜员是:"+code+" "+result+"?")) {
				  objName = eval("form1."+obj.name+"name");
				  obj.value = "";
				  objName.value = "";
				  return false;
			  }
			  else {
			  	objName = eval("form1."+obj.name+"name");
				  objName.value = result;
			  }
		  }
    }
  }

/******************************************************
 *功能描述:检查柜员代码合法的方法
 *参数说明：var EmployeeCode 柜员代码
 *作者：Zhouxj
 *创建日期：2004/07/02
 调用方法举例：
 alert(checkEmployee2("120200206"));
 *****************************************************/
function checkEmployee2(EmployeeCode) {

	   var  code = EmployeeCode;
      if (code.length != 9)  {
		   return false;
	  }
	  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      parser.async="false";
      var act = basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.MA.MA_EmployeeCodeVerifyOp&xmlOutput=true&opDataUnclear=true&oper=checkEmployee&employeecode="+code+"&time="+new Date();
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
      var objName;
      var result=rootElement.getAttribute("employeename");
      if (result == '0') {
			  return false;
		  }
		  else {
			  return true;
		  }
    }
}

/******************************************************
 *功能描述:用柜员号取柜员名称的方法
 *参数说明：var EmployeeCode 柜员代码
 * 返回柜员名称
 *作者：Zhouxj
 *创建日期：2004/07/02
  调用方法举例：
alert(getEmployeeName("120200206"));
 *****************************************************/
function getEmployeeName(EmployeeCode) {

	   var  code = EmployeeCode;
     if (code.length != 9)  {
		   return "柜员号必须是9位";
	  }
	  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      parser.async="false";
      var act = basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.MA.MA_EmployeeCodeVerifyOp&xmlOutput=true&opDataUnclear=true&oper=checkEmployee&employeecode="+code+"&time="+new Date();
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
    if(rootElement.tagName=="datas"){
		var objName;
		var result=rootElement.getAttribute("employeename");
			if (result == '0') {
				return "该柜员不存在！";
			}
			else {
				return result+"";
			}
	}
}

/******************************************************
 *功能描述:补录权限查询的方法
 *参数说明：
 *    areacode 地区代码 
 *    customercode 客户代码
 *    bussinesstype 业务类型
 *    controltype 控制类型
 *    bussinesspk 业务主键
 * 返回是否成功标志
 *作者：zhengzezhou 
 *创建日期： 2005-2-21 16:03
  调用方法举例：
 *****************************************************/
function patchCheck(areacode,customercode,bussinesstype,controltype,bussinesspk) {

	  var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
      var parser=new ActiveXObject("microsoft.xmldom");
      parser.async="false";
      var tmp = "&areacode="+areacode+"&customercode="+customercode+"&bussinesstype="+bussinesstype+"&controltype="+controltype+"&bussinesspk="+bussinesspk;
      var act = basepath0606+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.MA.MA_PatchGrantOp&xmlOutput=true&opDataUnclear=true&opAction=patchCheck"+tmp+"&time="+new Date();
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
    if(rootElement.tagName=="Content"){
		var objName;
		var result=rootElement.getAttribute("result");
			if (result == 'OK') {
				return true;
			}
			else {
				alert(result);
				return false;
			}
	  }
	  return true;
}