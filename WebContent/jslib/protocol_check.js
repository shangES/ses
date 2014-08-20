function isAlpha(theStr,alpha) {
       if (isEmpty(theStr)) return(false);
       else if (theStr.indexOf(alpha) == -1) return(false);
       return(true);
}
function check_protocl_sub(theStr){
	checkLength(theStr);
	if(theStr.value.length!=21){
		alert('分协议编号必须为21位');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(0,1),'I')){
		alert('分协议编号的首字母必须为I');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(1,2),'M')&&!isAlpha(theStr.value.substring(1,2),'R')&&!isAlpha(theStr.value.substring(1,2),'T')&&!isAlpha(theStr.value.substring(1,2),'S')&&!isAlpha(theStr.value.substring(1,2),'Z')){
		alert('分协议编号的第二个字母必须为M,R,S,Z或T');/*zyh增加S,Z字符 20030425*/
		//theStr.focus();
		return false;
	}
	if(!isAlpha('ABCDEFGHIJKLMNOPQRSTUVWXYZ',theStr.value.substring(20,21))){
		alert('分协议编号的末位必须为字母');	
		//theStr.focus();
		return false;
	}
	if(!isInt(theStr.value.substring(2,20))){
		alert('分协议编号的3-20位必须为数字');
		//theStr.focus();
		return false;
	}
	return(true);
}
function checkje(theStr){
	checkLength(theStr);		
	if((ret=isReal(theStr.value,16,2))!="ok"){
		alert('金额为整数部分16位数字，小数部分2位数字'+ret);
		//theStr.focus();
		return false;
	}
	return true;		
}
function checksz(theStr){
	checkLength(theStr);		
	if(!isInt(theStr.value)){
		alert('输入必须为数字');
		//theStr.focus();
		return false;
	}
	return true;		
}
function check_main_protocl(theStr){
	checkLength(theStr);
	if(theStr.value.length!=10){
		alert('总协议编号必须为10位');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(0,1),'G')){
		alert('总协议编号的首字母必须为G');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(1,2),'M')&&!isAlpha(theStr.value.substring(1,2),'R')&&!isAlpha(theStr.value.substring(1,2),'T')&&!isAlpha(theStr.value.substring(1,2),'S')&&!isAlpha(theStr.value.substring(1,2),'Z')){
		alert('总协议编号的第二个字母必须为M,R,S,Z或T');/*zyh增加S,Z字符 20030425*/
		//theStr.focus();
		return false;
	}
	if(!isInt(theStr.value.substring(2,10))){
		alert('总协议编号的3-10位必须为数字');
		//theStr.focus();
		return false;
	}
	return(true);
}
function check_protocl_spe(theStr){
	checkLength(theStr);
	if(theStr.value.length!=21){
		alert('专项协议编号必须为21位');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(0,1),'S')){
		alert('专项协议编号的首字母必须为S');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(1,2),'M')&&!isAlpha(theStr.value.substring(1,2),'R')&&!isAlpha(theStr.value.substring(1,2),'T')&&!isAlpha(theStr.value.substring(1,2),'S')&&!isAlpha(theStr.value.substring(1,2),'Z')){
		alert('专项协议编号的第二个字母必须为M,R,S,Z或T');    /*zyh增加S,Z字符 20030425*/
		//theStr.focus();
		return false;
	}
	if(!isAlpha('ABCDEFGHIJKLMNOPQRSTUVWXYZ',theStr.value.substring(20,21))){      /*zyh修改20030425*/
		alert('专项协议编号的末位必须为字母');
		//theStr.focus();
		return false;
	}
	if(!isInt(theStr.value.substring(2,20))){
		alert('专项协议编号的3-20位必须为数字');
		//theStr.focus();
		return false;
	}
	return(true);
}
function check_protocl_chi(theStr){
	checkLength(theStr);
	if(theStr.value.length!=21){
		alert('分子协议编号必须为21位');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(0,1),'I')){
		alert('分子协议编号的首字母必须为I');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(1,2),'M')&&!isAlpha(theStr.value.substring(1,2),'R')&&!isAlpha(theStr.value.substring(1,2),'T')&&!isAlpha(theStr.value.substring(1,2),'S')&&!isAlpha(theStr.value.substring(1,2),'Z')){
		alert('分子协议编号的第二位必须为M,R,S,Z或T');/*zyh增加S,Z字符 20030425*/
		//theStr.focus();
		return false;
	}
	if(!isAlpha('ABCDEFGHIJKLMNOPQRSTUVWXYZ',theStr.value.substring(20,21))){
		alert('分子协议编号的末位必须为字母');
		//theStr.focus();
		return false;
	}
	if(!isInt(theStr.value.substring(2,20))){
		alert('分子协议编号的3-20位必须为数字');
		//theStr.focus();
		return false;
	}
	return(true);
}
function check_protocl_all(theStr){
	checkLength(theStr);
	if(theStr.value.length!=21){
		alert('分子协议/专项协议编号必须为21位');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(0,1),'I')&&!isAlpha(theStr.value.substring(0,1),'S')){
		alert('分子协议/专项协议编号的首位必须为I或S');
		//theStr.focus();
		return false;
	}
	if(!isAlpha(theStr.value.substring(1,2),'M')&&!isAlpha(theStr.value.substring(1,2),'R')&&!isAlpha(theStr.value.substring(1,2),'T')&&!isAlpha(theStr.value.substring(1,2),'S')&&!isAlpha(theStr.value.substring(1,2),'Z')){
		alert('分子协议/专项协议编号的第二位必须为M,R,S,Z或T');/*zyh增加S,Z字符 20030425*/
		//theStr.focus();
		return false;
	}
	if(!isAlpha('ABCDEFGHIJKLMNOPQRSTUVWXYZ',theStr.value.substring(20,21))){
		alert('分子协议/专项协议编号的末位必须为字母');
		//theStr.focus();
		return false;
	}
	if(!isInt(theStr.value.substring(2,20))){
		alert('分子协议/专项协议编号的3-20位必须为数字');
		//theStr.focus();
		return false;
	}
	return(true);
}
