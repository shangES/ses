<!--
function initArray(){
	this.length = initArray.arguments.length
	for (var i = 0; i < this.length; i++)
	this[i+1] = initArray.arguments[i]
}
var MonthArray = new initArray("01","02","03","04","05","06","07","08","09","10","11","12");
var DayArray = new initArray("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");
var daysInMonth = new initArray(31,28,31,30,31,30,31,31,30,31,30,31);
function getDays(month, year) {
	//下面的这段代码是判断当前是否是闰年的
	if (month == 2)
		return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28;
	else
		return daysInMonth[month];
}
function ShowDateSelect(name,v_date){
	var i;
	var y1="";
    var m1="";
    var d1="";
	var today=new Date(); 
if(v_date==null || v_date==""){
	y1=today.getYear();
	m1=today.getMonth()+1;
	d1=today.getDate();
}
else{
	if (v_date.length==4)
        {
          y1 = v_date;
        }
    else if(v_date.length == 6)
        {
          y1 = v_date.substr(0,4);
		  if(v_date.substr(4,1)=="0")
			m1 = v_date.substr(5,1);
		  else
		    m1 = v_date.substr(4,2);
        }
    else if(v_date.length == 8)
        {
          y1 = v_date.substr(0,4);		  
		  if(v_date.substr(4,1)=="0")
			m1 = v_date.substr(5,1);
		  else
		    m1 = v_date.substr(4,2);
		  if(v_date.substr(6,1)=="0")
			d1 = v_date.substr(7,1);
		  else
		    d1 = v_date.substr(6,2);
        }
}
    //年
	var year=parseInt(y1);
	document.write("<input size=\"4\" type=\"text\" maxlength=\"4\" name=\""+name+"year\" value="+y1+" onChange=\"chgYear('"+name+"')\" class=tablebody>年");
    //月	
	document.write("<SELECT name=\""+name+"month\" onChange=\"changelocation('"+name+"')\" class=tablebody>");
	var month=parseInt(m1);
	/*if (m1==" "){
          month=today.getMonth()+1;
        }
        else{
          month=parseInt(m1);
        }*/
	for (i=1;i<13;i++){ // generate options
		document.write("<option value=\""+MonthArray[i]+"\"");
		if (month==i){ // current month, selected
			document.write(" selected");
		}
		document.write(">"+MonthArray[i]+"</option>");
	}
	document.write("</SELECT>");
	document.write("月");	
	
	//日
	document.write("<SELECT name=\""+name+"day\" class=tablebody>");
	var date = parseInt(d1); 
	for (i=1;i<=getDays(month,year);i++){
		document.write("<option value=\""+DayArray[i]+"\"");
		//window.alert(DayArray[i]);
		if (i==date){ 
			document.write(" selected");
		}
		document.write(">"+DayArray[i]+"</option>");
	}
	document.write("</SELECT>");
	document.write("日");
}
function changelocation(name){
	this0 = eval("document.all."+name+"year");
	this1 = eval("document.all."+name+"month");
	this2 = eval("document.all."+name+"day");
	var tmpmonth = this1.selectedIndex+1;
	var tmpyear = parseInt(this0.value);
	//window.alert(tmpyear);
	var daycount = getDays(tmpmonth,tmpyear);
	//var daycount = getDays(9,tmpyear);
	//window.alert("tmpyear:"+tmpyear+" "+"tmpmonth:"+tmpmonth+"  daycount"+daycount);
	this2.length = 0; 
    var i;
    this2.options[0] = new Option('01','01');
    for (i=1;i<daycount; i++)
        {
            this2.options[i] = new Option(DayArray[i+1],DayArray[i+1]);  
        }        
}
function chgYear(name){
	//window.alert("年份改变了"+name);
	var i = 0;
	this0 = eval("document.all."+name+"year");
	this1 = eval("document.all."+name+"month");
	this2 = eval("document.all."+name+"day");
	//输入合法性的校验；
	if (this0.value=="")
	{
		window.alert("请输入年份!");
		this0.focus();
		return false;
	}
	if (this0.value.length < 4)
	{
		window.alert("年份输入不正确，请重新输入!");
		this0.focus();
		return false;
	}
	for (i;i<4;i++)
	{
		if (isNaN(this0.value.charAt(i)))
		{
			window.alert("年份输入不正确，请重新输入!");
			this0.focus();
			return false;
		}
	}
	//是否为二月;
	if (this1.value=="02")
	{
		var year = parseInt(this0.value);
		if (((year%4==0)&&(year%100!=0))||(year%400==0))
		{
			this2.options[0] = new Option('01','01');
			for (i=1;i<29; i++)
			{
				this2.options[i] = new Option(DayArray[i+1],DayArray[i+1]);  
			} 
		}
	}
	return true;
}
//获得所选择的日期；
function getSelectDate(name){
	this0 = eval("document.all."+name+"year");
	this1 = eval("document.all."+name+"month");
	this2 = eval("document.all."+name+"day");
	var riqi = this0.value + "/" + this1.value + "/" + this2.value;
	return riqi;
}
/**
 *计算天数
 *输入参数:起始日期，结束日期
 ×输出为:起始日期到结束日期包括的天数
 */
function calculatedays(startdate,enddate){
	var oneday=24*60*60*1000;  //计算一天的毫秒数=24小时×60分钟×60秒×1000
	if(parseInt(startdate)>parseInt(enddate)){
		var tmp=startdate;
		startdate=enddate;
		enddate=tmp;		
	}
	//起始时间
	var sy="";
	var sm="";
	var sd="";
	sy = startdate.substr(0,4);		  
	if(startdate.substr(4,1)=="0")
	  sm = startdate.substr(5,1);
	else
	  sm = startdate.substr(4,2);
	if(startdate.substr(6,1)=="0")
	  sd = startdate.substr(7,1);
	else
	  sd = startdate.substr(6,2);
	//结束时间
	var ey="";
	var em="";
	var ed="";
	ey = enddate.substr(0,4);		  
	if(enddate.substr(4,1)=="0")
	  em = enddate.substr(5,1);
	else
	  em = enddate.substr(4,2);
	if(enddate.substr(6,1)=="0")
	  ed = enddate.substr(7,1);
	else
	  ed = enddate.substr(6,2);
	//设置开始时间
	var theSDate = new Date();
	theSDate.setFullYear(parseInt(sy));
	theSDate.setMonth(parseInt(sm)-1);
	theSDate.setDate(parseInt(sd));
	var sdate=Date.parse(theSDate.toGMTString());	
	//设置结束时间
	var theEDate = new Date();
	theEDate.setFullYear(parseInt(ey));
	theEDate.setMonth(parseInt(em)-1);
	theEDate.setDate(parseInt(ed));
	var edate=Date.parse(theEDate.toGMTString());
	
	var sedate=(edate-sdate)/oneday;
	return sedate;
}
function compareDate6month(startdate,enddate){
	if(parseInt(enddate) < parseInt(startdate)){
		alert("开始时间必须小于等于结束时间");
	}
	//起始时间
	var sy="";
	var sm="";
	var sd="";
	sy = startdate.substr(0,4);		  
	if(startdate.substr(4,1)=="0")
	  sm = startdate.substr(5,1);
	else
	  sm = startdate.substr(4,2);
	if(startdate.substr(6,1)=="0")
	  sd = startdate.substr(7,1);
	else
	  sd = startdate.substr(6,2);
	//结束时间
	var ey="";
	var em="";
	var ed="";
	ey = enddate.substr(0,4);		  
	if(enddate.substr(4,1)=="0")
	  em = enddate.substr(5,1);
	else
	  em = enddate.substr(4,2);
	if(enddate.substr(6,1)=="0")
	  ed = enddate.substr(7,1);
	else
	  ed = enddate.substr(6,2);

	var interMonth = (ey*1 - sy*1)*12+em*1 - sm*1;

	if(interMonth > 6)
		return false;
	else if(interMonth < 6)
		return true;
	else{
		if(ed*1>sd*1) return false;
		else return true;
	}
}
function isDate(theStr) {
      var sy = theStr.substring(0,4);
      var iy = sy/1;
      var sm = theStr.substring(4,6);
      var im = sm/1;
      var sd = theStr.substring(6,8);
      var id = sd/1;
      if (theStr.length!=8)   return false;
	  if(im==0 ||im>12)  return false;
         var month1 = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
         var month2 = new Array(31,29,31,30,31,30,31,31,30,31,30,31);
         if((iy%4 == 0)&&(iy%400 ==0)&&(iy%100 == 0)){
             if((id <= month1[im-1])&&(id >=1 ))
   	         return true;
             else
                 return false;
         }else{
            if((id <= month2[im-1])&&(id >= 1))
                return true;
             else
                return false;
         }
}
function isDateValid(str){
  var sy = str.substring(0,4);
  var iy = sy/1;
  var sm = str.substring(4,6); 
  var im = sm/1;
  var sd = str.substring(6,8);
  var id = sd/1;
  if(str.length == 8){
     if(isDate(str)){
   	return true;
     }else{
        return false;
     }
  }
  else if(str.length == 6){
    if (iy<1900 ||iy>3000 ||im==0 ||im>12){
       return false;
    }else{
       return true;
    }
  }else if(str.length==4){
    if (iy<1900 ||iy>3000){
       return false;
    }else{
       return true;
    }
  }
  else return false;
}
//将日期转换为加"/"
function toDateShow(ob,len) {
       var val = ob.value;
       if (isEmpty(val)) return true;
       if (!isDate(val)) {
         alert("日期输入不合法");
         ob.focus();
         return false;
       }
       ob.value = val.substring(0,4)+"年"+val.substring(4,6)+"月"+val.substring(6,8)+"日";
}
function toDateEdit(ob,len) {
       var val = ob.value;
       if (len==6 && val.length!=8 ) return true;
       if (len==8 && val.length!=11 ) return true;
       ob.value = trimDate(val); 
       ob.select();
}
function trimDate(val){
	while(val.search('年')!=-1)
		val=val.replace('年',"/");
	while(val.search('月')!=-1)
		val=val.replace('月',"/");
	while(val.search('日')!=-1)
		val=val.replace('日',"");
	return val;
}
function isEmpty(str) {
       if ((str==null) || (str.length == 0)) return true;
       else return(false);
}
//-->