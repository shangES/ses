<!--
function initArray(){
	this.length = initArray.arguments.length
	for (var i = 0; i < this.length; i++)
	this[i+1] = initArray.arguments[i]
}
var MonthArray = new initArray("01","02","03","04","05","06","07","08","09","10","11","12"," ");
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
	//var today=new Date();
        var y1=" ";
        var m1=" ";
        var d1=" ";
	if (v_date.length == 4)
        {
          y1 = v_date;
        }
        else if(v_date.length == 6)
        {
          y1 = v_date.substr(0,4);
          m1 = v_date.substr(4,5);
        }
        else if(v_date.length == 8)
        {
          y1 = v_date.substr(0,4);
          m1 = v_date.substr(4,2);
          d1 = v_date.substr(6,2);
        }


        //window.alert("年"+y1+"月"+m1+"日"+d1+"日");
        //年
	document.write("<input size=\"4\" type=\"text\" maxlength=\"4\" name=\""+name+"year\" value=\""+y1+"\" onChange=\"chgYear('"+name+"')\">年");
        //月
        if (y1==" "){
	    document.write("<SELECT name=\""+name+"month\" onChange=\"changelocation('"+name+"')\" disabled>");
        }else{
            document.write("<SELECT name=\""+name+"month\" onChange=\"changelocation('"+name+"')\">");
        }
	//window.alert("<SELECT name=\""+name+"month\" onChange=\"changelocation('"+name+"')\">");
	var month=0;
	if (m1==" "){
          month=13;
        }
        else{
          month=parseInt(m1);
        }
	var i;
	for(i=1;i<14;i++){ // generate options
	    document.write("<option value=\""+MonthArray[i]+"\"");
            if (month==i){ // current month, selected
		document.write(" selected");
	    }
	    document.write(">"+MonthArray[i]+"</option>");
	}
	document.write("</SELECT>");
	document.write("月");
	//日
        if (m1==" "){
            document.write("<SELECT name=\""+name+"day\" disabled>");
        }else{
            document.write("<SELECT name=\""+name+"day\">");
        }
	var date = 0;
        if (d1!=" "){
          date=parseInt(d1);
        }
	for (i=1;i<32;i++){
		document.write("<option value=\""+DayArray[i]+"\"");
		//window.alert(DayArray[i]);
		if (i==date){
			document.write(" selected");
		}
		document.write(">"+DayArray[i]+"</option>");

	}
        document.write("<option value=\" \"");
        if(d1==" "){
                      document.write(" selected");
        }
        document.write("> </option>");
	document.write("</SELECT>");
	document.write("日");

	//设置时间的input框，名字为传入的值name
	//document.write("<input type=\"hidden\" name=\""+name+"\" value=\""+jintian+"\">");
	//document.write("<input type=\"hidden\" name=\""+name+"\" value=\""+jintian+"\">");
}
function changelocation(name){
	//var haha=name;
	this0 = eval("document.all."+name+"year");
	this1 = eval("document.all."+name+"month");
	this2 = eval("document.all."+name+"day");
        if (this1.value==" ")
        {
          this2.value = " ";
          this2.disabled = true;
          return false;
        }
        else
        {
          this2.disabled = false;
        }

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
    this2.options[i] = new Option(" "," ");
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
		//window.alert("请输入年份!");
		//this0.focus();
		this1.value=" ";
                this2.value=" ";
                this1.disabled=true;
                this2.disabled=true;
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
        this1.disabled=false;
        //this2.disabled=false;
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
        var riqi;
        if (this2.value==" "){
          if (this1.value==" ")
            riqi = this0.value;
          else
            riqi = this0.value + this1.value;
        }
        else
          riqi = this0.value + this1.value + this2.value;

        return riqi;
}
//-->