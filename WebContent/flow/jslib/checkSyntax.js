function checkSyntax(target){
	if(target.value.indexOf("<")  >=0 ||
		target.value.indexOf(">") >=0 ||
		target.value.indexOf("&") >=0 ||
		target.value.indexOf("\"")>=0 ||
		target.value.indexOf("'") >=0 ||
		target.value.indexOf("%") >=0 ||
		target.value.indexOf("+") >=0){
		alert("输入域中不能包含特殊字符：\n< 、> 、& 、\" 、'、%、+");
		target.focus();
		return false;
	}
	return true;
}