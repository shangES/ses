function check_protocl_subspe(theStr){
/*	theStr			校验的专项子协议编号
	返回参数：false --为校验不通过  true --为校验通过
*/	
	checkLength(theStr);
	if(theStr.value.length!=21){
		alert('专项子协议编号必须为21位');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(0,1),'S')){
		alert('专项子协议编号的首字母必须为S');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(1,2),'M')&&!isAlpha(theStr.value.substring(1,2),'R')&&!isAlpha(theStr.value.substring(1,2),'T')&&!isAlpha(theStr.value.substring(1,2),'S')&&!isAlpha(theStr.value.substring(1,2),'Z')){
		alert('专项子协议编号的第二个字母必须为M,R,S,Z或T');    /*zyh增加S,Z字符 20030425*/
		//theStr.focus();
		return false;
	}
	if(!isAlpha('ABCDEFGHIJKLMNOPQRSTUVWXYZ',theStr.value.substring(20,21))){      /*zyh修改20030425*/
		alert('专项子协议编号的末位必须为字母');
		//theStr.focus();
		return false;
	}
	if(!isInt(theStr.value.substring(2,20))){
		alert('专项子协议编号的3-20位必须为数字');
		//theStr.focus();
		return false;
	}
	return(true);
}

function tranauth_check(agree_type,agree_no,employee_id,modify_date){
/*	agree_type   		协议类型
	agree_no	 	协议序号
	employee_id		柜员号（修改协议）
	modify_date		修改日期
	返回参数：false --为无权修改  true --为有权修改
*/
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
        
	var tmp = "&TA450401001="+agree_type+"&TA450401002="+agree_no+"&TA450401004="+employee_id+"&InDate="+modify_date+"&time="+(new Date);
	objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.OU.OU_tranAuthQuery&opDataUnclear=true'+tmp,false);
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
	if(out_flag == "1"){//异常
		alert(out_mesg);
		return false;
	}else if(out_flag == "0"){//有权修改
		return true;
	}else{//无权修改
		//alert(out_mesg);
		return false;
	}
}

function project_agreement_check(TA220011001,TA220011002){
/*	TA220011001   		企业代码
	TA220011002	 	项目代码
	返回参数：0 --为项目无对应协议  1 --为项目有对应协议  2 --出错
*/	
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser = new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	var ret = 2;

    if(TA220011001 == ""){
		alert("企业代码不能为空！");
		return ret;
    }
    if(TA220011002 == ""){
		alert("项目代码不能为空！");
		return ret;
    }

	var tmp = "TA220011001="+TA220011001+"&TA220011002="+TA220011002;
	objHTTP.open('GET',basepath0606+'/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.OG.TransLoanExecOp&xmlOutput=true&opDataUnclear=true&act=checkagreement&'+tmp,false);
	objHTTP.send();
	var xml = objHTTP.responseText;
	if(!parser.loadXML(xml)) { 
		alert(parser.parseError.reason + "\n" + parser.parseError.line + "\n" + parser.parseError.srcText + "\n");    
		return ret;
	}

	if(parser.documentElement.tagName == "error") {
		var errorCode = parser.getElementsByTagName("errorCode").item(0).childNodes.item(0).text;
		var errorDispMsg = parser.getElementsByTagName("errorDispMsg").item(0).childNodes.item(0).text;		
		alert("出错代码:"+errorCode+"\n"+errorDispMsg);
		return ret;
	}
	var nodes = parser.documentElement.childNodes;
	if(nodes.length > 0){
		var node = nodes.item(0);    
		var flag = node.getAttribute("flag");
		if (flag == "0")
			ret = 0;
		else if (flag == "1")
			ret = 1;
		else
			ret = 2;
	}else{
		ret = 2;
	}
	return ret;
}