var scrolling=false;
var width = 162;
var widths = 162;
var step = 5;
var interval = 10;
var tid;

function getkindofagreement(id){
	var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
	var parser=new ActiveXObject("microsoft.xmldom");
	parser.async="false";
	var qstr = "number=" + id + "&time=" + new(Date);
	objHTTP.Open('GET','/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=CShowGoodsOp&opDataUnclear=true&'+qstr,false);
	objHTTP.Send();
	var xml = objHTTP.responseText;
	if(!parser.loadXML(xml)) {
		alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
		return;
	}
	error = parser.getElementsByTagName("error");
	if(error.length > 0) {
  		alert(error.item(0).text);
  		return;
	}
	var flag = parser.documentElement.getAttribute("agreementkind");
	return flag;
}
function toggle(id)
{	
	var thisRow = document.all.item(id);
	if (thisRow)
	{
		if (thisRow.getAttribute("Expanded") == 'yes')
		{
			thisRow.setAttribute("Expanded", "no");
			thisRow.children(0).children(0).children(0).src = "/icbc/cmis/images/bs.gif";

			var allRows = document.all.tags("TR");
			for (var i=0; i < allRows.length; i++)
			{
				var row = allRows[i];
				if (row.getAttribute("AncestorID") == id)
				{
					if (row.getAttribute("Expanded") == 'yes') {
						toggle(row.getAttribute("id"));
					}
					row.className = 'Navigator-Hidden';
				}
			}
			thisRow.className = 'Navigator';
		}
		else
		{
			thisRow.setAttribute("Expanded", "yes");
			thisRow.children(0).children(0).children(0).src = "/icbc/cmis/images/bo.gif";

			var allRows = document.all.tags("TR");
			var depth = parseInt(thisRow.getAttribute("Depth"));
			for (var i=0; i < allRows.length; i++)
			{
				var row = allRows[i];
				if (row.getAttribute("AncestorID") == id &&
				parseInt(row.getAttribute("Depth")) == depth + 1 )
				{
				row.className = 'Navigator';
				}
			}
		}
		goto1(id);
	}
}

function toggle1(id)
{	
	var thisRow = document.all.item(id);
	if (thisRow)
	{
		if (thisRow.getAttribute("Expanded") == 'yes')
		{
			thisRow.setAttribute("Expanded", "no");
			thisRow.children(0).children(0).children(0).src = "/icbc/cmis/images/bs.gif";

			var allRows = document.all.tags("TR");
			for (var i=0; i < allRows.length; i++)
			{
				var row = allRows[i];
				if (row.getAttribute("AncestorID") == id)
				{
					if (row.getAttribute("Expanded") == 'yes') {
						toggle(row.getAttribute("id"));
					}
					row.className = 'Navigator-Hidden';
				}
			}
			thisRow.className = 'Navigator';
		}
		else
		{
			thisRow.setAttribute("Expanded", "yes");
			thisRow.children(0).children(0).children(0).src = "/icbc/cmis/images/bo.gif";

			var allRows = document.all.tags("TR");
			var depth = parseInt(thisRow.getAttribute("Depth"));
			for (var i=0; i < allRows.length; i++)
			{
				var row = allRows[i];
				if (row.getAttribute("AncestorID") == id &&
				parseInt(row.getAttribute("Depth")) == depth + 1 )
				{
				row.className = 'Navigator';
				}
			}
		}
		goto4(id);
	}
}

function Hiding()
{
	if(widths > 0) {
		widths = widths - step;
		if (widths < 0) {widths = 0;}
		document.all.MenuLayer.style.left = widths - width;
	}
	else
	{
		clearInterval(tid);
		parent.document.all.frameset2.cols = "0,*";
    document.body.style.display = 'none';
	}
}

function goto(id){	
	var URL = "icbc.cmis.OJ.OJ_OperationLevelAgreement1Op";
	var thisRow = document.all.item(id);
	info.number.value=id;
	if (thisRow.getAttribute("Depth")=="0"){
		info.kind.value="1";
	}else if ((thisRow.getAttribute("Depth")=="1")){
		info.kind.value="2";
	}else{
		info.kind.value="0";
	}
	info.action = "/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=" + URL+"&operflag=detail&kind=0";
	info.submit();	
}

function goto1(id){	
	var URL = "icbc.cmis.OJ.OJ_OperationLevelAgreement2Op";
	var thisRow = document.all.item(id);
	info.number.value=id;
	if (thisRow.getAttribute("Depth")=="0"){
		info.kind.value="1";
	}else if ((thisRow.getAttribute("Depth")=="1")){
		info.kind.value="2";
	}else{
		info.kind.value="0";
	}
	info.action = "/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=" + URL+"&operflag=detail&kind=0";
	info.submit();	
}

function goto2(id){	
	var URL = "icbc.cmis.OJ.OJ_OperationLevelAgreement3Op";
	var thisRow = document.all.item(id);
	info.number.value=id;
	if (thisRow.getAttribute("Depth")=="0"){
		info.kind.value="1";
	}else if ((thisRow.getAttribute("Depth")=="1")){
		info.kind.value="2";
	}else{
		info.kind.value="0";
	}
	info.action = "/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=" + URL+"&operflag=detail&kind=0";
	info.submit();	
}

function goto3(id){	
	var URL = "icbc.cmis.OJ.OJ_OperationLevelAgreement1Op";
	var thisRow = document.all.item(id);
	info.number.value=id;
	if (thisRow.getAttribute("Depth")=="0"){
		info.kind.value="1";
	}else if ((thisRow.getAttribute("Depth")=="1")){
		info.kind.value="2";
	}else{
		info.kind.value="0";
	}
	info.action = "/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=" + URL+"&operflag=detail&kind=2";
	info.submit();	
}

function goto4(id){	
	var URL = "icbc.cmis.OJ.OJ_OperationLevelAgreement2Op";
	var thisRow = document.all.item(id);
	info.number.value=id;
	if (thisRow.getAttribute("Depth")=="0"){
		info.kind.value="1";
	}else if ((thisRow.getAttribute("Depth")=="1")){
		info.kind.value="2";
	}else{
		info.kind.value="0";
	}
	info.action = "/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=" + URL+"&operflag=project&kind=2";
	info.submit();	
}

function goto5(id){
	var flag = parseInt(getkindofagreement(id),10)+2;
	var URL = "icbc.cmis.OJ.OJ_OperationLevelAgreement"+flag+"Op";
	var thisRow = document.all.item(id);
	info.number.value=id;
	if (thisRow.getAttribute("Depth")=="0"){
		info.kind.value="1";
	}else if ((thisRow.getAttribute("Depth")=="1")){
		info.kind.value="2";
	}else{
		info.kind.value="0";
	}
	info.action = "/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=" + URL+"&operflag=detail&kind=2";
	info.submit();	
}

function goto6(id){
	var URL = "icbc.cmis.OJ.OJ_OperationLevelAgreement6Op";
	info.number.value=id;
	info.action = "/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=" + URL+"&operflag=detail&kind=11111";
	info.submit();	
}

function goto7(id){
	var URL = "icbc.cmis.OJ.OJ_OperationLevelAgreement5Op";
	info.number.value=id;
	info.action = "/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=" + URL+"&operflag=detail&kind=11111";
	info.submit();	
}

function changeMajor() {
  form1.major.value = document.all.smajor.value;
  //alert(form1.major.value);
  form1.submit();
}

function changeMajorClass() {
  form1.major.value = document.all.smajor.value;
  form1.majorClass.value = document.all.smajorClass.value;
  //alert(form1.major.value);
  form1.submit();
}

function showEmployee() {
	if (form1.majorName.value != null && form1.majorName.value != "") {
		str = form1.areaName.value + " " + form1.majorName.value + " " + form1.className.value + " " + form1.eName.value;
		var tobj = parent.frames('topFrame').employee;
		if(tobj != null) tobj.innerText = str;
	}
	form2.submit();
}

