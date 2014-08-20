// sx_control.js

	/******************************************************
 	 *功能描述:	授信控制处理
 	 *参数说明：customerid:客户号，contractid:流水号或者是业务编号
 	           operatetype:操作类型，producttype:产品种类
 	           money:实际业务金额，billtype:输入金额币种类型
 	           begindate:业务起始日，enddate:业务终止日
 	           requestServletUrl:请求Servlet的Url
 	           requiredHtmlParams:要求的html参数
 	 *作者：hushou
 	 *创建日期：2004-08-11
 	 *****************************************************/
	function f_GrantControl(customerid,contractid,operatetype,producttype,money,billtype,begindate,enddate,requestServletUrl,requiredHtmlParams){
		if(contractid == ""){
			contractid ="1";
		}
		//拼接xml串
   		//var requestServletUrl = '<%=fullPath%><%=utb.getRequestServletUrl()%>';
		//var requiredHtmlParams = '<%= utb.getRequiredHtmlParams (null,"util_GrantControlOp")%>' ;
		
		var act0 =  requestServletUrl + "?xmlRequest=true";
		var act = act0 + '&' + requiredHtmlParams + '&customerid='+customerid+'&contractid='+contractid+'&operatetype='+operatetype+'&producttype='+producttype+'&money='+money+'&billtype='+billtype+'&begindate='+begindate+'&enddate='+enddate; 
		//处理xml
		if (!DealXMLHTTP(act)) return false;
		//解析返回来的得分最大值和最小值
		var nodes = parser.documentElement.childNodes;
		var node = nodes.item(0).childNodes;  
		
		var returnflag = node.item(0).text;  
		var returnmessage = node.item(1).text; 	
		
		if(returnflag == "1"){//1'授信控制通过但有警告信息
			if(confirm(returnmessage)){
				return true;
			}else{
				return false;
			}
		}else if(returnflag == "-1"){//-1'授信不通过
			alert(returnmessage);
			return false;
		}else if(returnflag == "-99"){//-99'数据库其他原因引起的出错
			alert(returnmessage);
			return false;
		}else if(returnflag == "0"){//'0'授信控制通过
			return true;
		}
		
		return true;
	} 