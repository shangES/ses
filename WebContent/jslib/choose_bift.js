var webBasePath = "/icbc/bift";

// choose_bift.js

function formatDate(indate)//copy from simple_date.js
{
	if (indate.length!=8)
		return indate;
	var y1=indate.substr(0,4);
	var m1=indate.substr(4,2);
	var d1=indate.substr(6,2);
	return y1+"年"+m1+"月"+d1+"日";

}
/** 检查交割日期(8位)是否节假日
  * 常用方法举例:util_check_settle_date('20040229')
  */
function util_check_settle_date(thisday)
{
	var workday=util_get_next_workdate(thisday);
	if (workday==null)//取下一工作日出错
		return false;
	if (thisday!=workday)//如果指定日期是节假日
		return confirm("交割日"+formatDate(thisday)+"是节假日，下一工作日为:"+formatDate(workday)+"\n是否继续？");
	else//如果指定日期不是节假日
		return true;
}
/** 返回指定日期(8位)后下一个工作日,null表示出错
  * 常用方法举例:get_next_workdate('20040229')
  */
function util_get_next_workdate(thisday) {
  //组织查询条件
  var qstr = "method=getNextWorkdate&thisday=" + thisday;

  //查询
  objHTTP.Open('GET',webBasePath+'/servlet/icbc.cmis.bift.util.CUtilServlet?'+qstr,false);
  objHTTP.Send();

  //查询出错处理
  var xml = objHTTP.responseText;
  //alert(xml);
  if(!parser.loadXML(xml)) {
//    alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
    return null;
  }
  error = parser.getElementsByTagName("error");
  if(error.length > 0) {
    alert(error.item(0).text);
    return null;
  }
  //查询正确，分析结果
  //提取参数值
  var para_value = parser.documentElement.getAttribute("value");

  return para_value;
}

/** 授权
  * 常用方法举例:var ts=util_authorize("反向记账","修改客户信息"+form1.ta390050001.value)
  * alert(ts);//授权柜员号
  */
function util_authorize(module,info) {
  return window.showModalDialog(webBasePath+"/bift/util/util_Authorize.jsp",new Array(module,info),"dialogWidth:295px;dialogHeight:190px;center:yes;help:no;status:no;resizable:yes");
}

/** 选择机构
  * 常用方法举例:var ts=util_choose_area(s_start_area_code)//如：util_choose_area(<%=sm.getSessionData("AreaCode")%>)
  * s_start_area_code://从s_start_area_code指定的机构开始选择(一般取得本柜员所在机构即可)
  * alert(ts.substring(1,1));//机构级别
  * alert(ts.substring(1,9));//机构号
  * alert(ts.substring(10,ts.length));//机构名
  */
function util_choose_area(s_start_area_code,s_range)
{
  if (s_range==null)
  	s_range="01110";
  //总行,一级行，准一级，二级行，支行
  //这里允许选择一级行、准一级行、二级行

  return window.showModalDialog(webBasePath+"/util/util_ChooseAreaNominit.jsp?area="+s_start_area_code+"&range="+s_range+"&time=" + (new Date)+"&opDataUnclear=true",window,"dialogWidth:380px;dialogHeight:280px;center:yes;help:no;status:no;resizable:no");
}

/** 选择债券
  * 常用方法举例:var ts=util_choose_bond()
  *              var ts=util_choose_bond(bondtype)//指定债券类2型（ta390001006）
  *	alert(ts[1]);//对应ta390001001
  *	alert(ts[2]);//对应ta390001002
  *	alert(ts[3]);//对应ta390001003
  *	alert(ts[4]);//对应ta390001004
  *	alert(ts[5]);//对应ta390001005
  * ...
  *	alert(ts[48]);//对应ta390001048
  */
function util_choose_bond(type,status)
{
  if (type==null)
  	type="";
  if (status==null)
    status="01";
  var ret=["请不要取ts[0]"];
  var ts=window.showModalDialog(webBasePath+"/bift/util/util_Query.jsp?ta390001006="+type+"&ta390001048="+status+"&queryType=icbc.cmis.bift.util.CChooseBondDAO&QueryPage=/bift/util/bift_util_choose_bond_input.jsp&width=700&height=400&hasDetailLink=new"+"&opDataUnclear=true",window,"dialogWidth:788px;dialogHeight:526px;center:yes;help:no;status:no;resizable:yes");
  if (ts==null)
  	return ts;
  else 
    return ret.concat(ts);
}

/** 选择债券
  * 常用方法举例:var ts=util_choose_bond()
  *              var ts=util_choose_bond(bondtype)//指定债券类2型（ta390001006）
  *	alert(ts[1]);//对应ta390001001
  *	alert(ts[2]);//对应ta390001002
  *	alert(ts[3]);//对应ta390001003
  *	alert(ts[4]);//对应ta390001004
  *	alert(ts[5]);//对应ta390001005
  * ...
  *	alert(ts[48]);//对应ta390001048
  */
function util_choose_all_bond(type)
{
  if (type==null)
  	type="";
    return util_choose_bond(type,'');
}

 /** 选择柜员
   * 常用方法举例:var ts=util_choose_employee(area,major,eClass)
   * area：机构号，多个机构用逗号分隔(如”12020010,12020020”)
   * major：专业，多个专业用逗号分隔(如”099,210”)对应字典表名（mag_major ）
   * eClass：可选柜员角色，多个角色用逗号分隔（如”5,6”）对应字典表名（mag_employee_class ）
   *		 债券角色（请以最新文档为准）：6-领导 7-帐务管理员 8-风险管理员 9-债券结算员 10-交易员
   * eCode：多个柜员用逗号分隔(如”120200199,120200200”)
   * includeSelf：是否包含字段eCode中的柜员，true|false<br>
   * multiSelect：多选， true|false<br>
   * alert(ts[0][1]);//柜员号
   * alert(ts[0][2]);//姓名
   * alert(ts[0][3]);//专业号
   * alert(ts[0][4]);//专业名称
   * alert(ts[0][5]);//角色
   * alert(ts[0][6]);//角色名称
   */
function util_choose_employee(area,major,eClass,eCode,includeSelf,multiSelect)
{
  if (eCode==null)
  	eCode="none";
  if (includeSelf==null)
  	includeSelf=true;
  if (multiSelect==null)
  	multiSelect=false;
  	
  var qstr = webBasePath+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=ChooseEmployee&eCodeWsA342d=" + eCode + "&areaWsA342d=" + area + "&majorWsA342d=" + major + "&eClassWsA342d=" + eClass + "&includeSelfWsA342d=" + includeSelf + "&multiSelectWsA342d=" + multiSelect + "&time=" + new(Date)+"&opDataUnclear=true";
  return window.showModalDialog(qstr,window,"dialogWidth:370px;dialogHeight:350px;center:yes;help:no;status:no;resizable:yes");
}

 /** 新选择柜员（在所有下属机构中选择柜员）
   * 常用方法举例:var ts=util_choose_employee_new(area,major,eClass)
   * area：根机构号(如”00000001”)，该机构所有下属机构的柜员均允许选择。
   * major：专业，多个专业用逗号分隔(如”099,210”)对应字典表名（mag_major ）
   * eClass：可选柜员角色，多个角色用逗号分隔（如”5,6”）对应字典表名（mag_employee_class ）
   *		 债券角色（请以最新文档为准）：6-领导 7-帐务管理员 8-风险管理员 9-债券结算员 10-交易员
   * eCode：多个柜员用逗号分隔(如”120200199,120200200”)
   * includeSelf：是否包含字段eCode中的柜员，true|false<br>
   * multiSelect：多选， true|false<br>
   * alert(ts[0][1]);//柜员号
   * alert(ts[0][2]);//姓名
   * alert(ts[0][3]);//专业号
   * alert(ts[0][4]);//专业名称
   * alert(ts[0][5]);//角色
   * alert(ts[0][6]);//角色名称
   * alert(ts[0][7]);//所属机构号
   * alert(ts[0][8]);//所属机构名
   */
function util_choose_employee_new(area,major,eClass,eCode,includeSelf,multiSelect)
{
  area='00000000';
  if (eCode==null)
  	eCode="none";
  if (includeSelf==null)
  	includeSelf=true;
  if (multiSelect==null)
  	multiSelect=false;
  	  
  var qstr = webBasePath+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.ChooseBelongEmp&eCodeWsA342d=" + eCode + "&areaWsA342d=" + area + "&majorWsA342d=" + major + "&eClassWsA342d=" + eClass + "&includeSelfWsA342d=" + includeSelf + "&multiSelectWsA342d=" + multiSelect + "&time=" + new(Date)+"&opDataUnclear=true";
  return window.showModalDialog(qstr,window,"dialogWidth:370px;dialogHeight:350px;center:yes;help:no;status:no;resizable:yes");
}


/** 选择客户
  * 常用方法举例:var ts=util_choose_client()//选择状态正常的全部客户
  * 常用方法举例:var ts=util_choose_client('02')//选择状态正常的代理客户
  *	alert(ts[1]);//对应ta390050001
  *	alert(ts[2]);//对应ta390050002
  *	alert(ts[3]);//对应ta390050003
  *	alert(ts[4]);//对应ta390050004
  *	alert(ts[5]);//对应ta390050005
  *	alert(ts[6]);//对应ta390050006
  *	alert(ts[7]);//对应ta390050007
  *	alert(ts[15]);//对应ta390050015
  */
function util_choose_client(ta390050005,ta390050028)
{
  var ret=["请不要取ts[0]"];
  var para="";
  if (ta390050005==null)
  	ta390050005="";
  if (ta390050028==null)
    ta390050028="01";
  para=para+"&ta390050005="+ta390050005+"&ta390050028="+ta390050028;
    
  var ts=window.showModalDialog(webBasePath+"/bift/util/util_Query.jsp?queryType=icbc.cmis.bift.util.CChooseClientDAO&QueryPage=/bift/util/bift_util_choose_client_input.jsp"+para+"&width=700&height=480&hasDetailLink=new"+"&opDataUnclear=true",window,"dialogWidth:800px;dialogHeight:570px;center:yes;help:no;status:no;resizable:yes");
  if (ts==null)
  	return ts;
  else 
    return ret.concat(ts);
}

/** 央票新增、修改交易行为是转与分行时选择客户
  * 如果不同的major在选客户时还有其它要求，在这里添加
  * by zly 2004.08
  */
function util_choose_client_major(ta390050005,ta390050028,major)
{
  var ret=["请不要取ts[0]"];
  var para="";
  if (ta390050005==null)
  	ta390050005="";
  if (ta390050028==null)
    ta390050028="01";
  //默认情况下取央票  
  if (major==null)
    major="914";  
  para=para+"&ta390050005="+ta390050005+"&ta390050028="+ta390050028+"&major="+major;
    
  var ts=window.showModalDialog(webBasePath+"/bift/util/util_Query.jsp?queryType=icbc.cmis.bift.util.CChooseClientMajorDAO&QueryPage=/bift/util/bift_util_choose_client_input.jsp"+para+"&width=700&height=480&hasDetailLink=new"+"&opDataUnclear=true",window,"dialogWidth:800px;dialogHeight:570px;center:yes;help:no;status:no;resizable:yes");
  if (ts==null)
  	return ts;
  else 
    return ret.concat(ts);
}

/** 选择客户
  * 常用方法举例:var ts=util_choose_all_client()//选择全部客户
  * 常用方法举例:var ts=util_choose_all_client('02')//选择全部代理客户
  *	alert(ts[1]);//对应ta390050001
  *	alert(ts[2]);//对应ta390050002
  *	alert(ts[3]);//对应ta390050003
  *	alert(ts[4]);//对应ta390050004
  *	alert(ts[5]);//对应ta390050005
  *	alert(ts[6]);//对应ta390050006
  *	alert(ts[7]);//对应ta390050007
  *	alert(ts[15]);//对应ta390050015
  */
function util_choose_all_client(ta390050005)
{
  if (ta390050005==null)
  	ta390050005="";
  return util_choose_client(ta390050005,'');
}

var objHTTP = new ActiveXObject("Microsoft.XMLHTTP")  
var parser=new ActiveXObject("microsoft.xmldom")
parser.async="false"

function util_get_genpara(paratype,paracode) {
  //组织查询条件
  var qstr = "method=getGenPara&paratype=" + paratype + "&paracode=" + paracode;

  //查询
  objHTTP.Open('GET',webBasePath+'/servlet/icbc.cmis.bift.util.CUtilServlet?'+qstr,false);
  objHTTP.Send();

  //查询出错处理
  var xml = objHTTP.responseText;
  //alert(xml);
  if(!parser.loadXML(xml)) {
//    alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
    return null;
  }
  error = parser.getElementsByTagName("error");
  if(error.length > 0) {
    alert(error.item(0).text);
    return null;
  }
  //查询正确，分析结果
  //提取参数值
  var para_value = parser.documentElement.getAttribute("value");

  return para_value;
}

/** 选择企业
  * 常用方法举例:var ts=util_choose_enp(s_start_area_code)
  * //alert(ts[0]);//企业代码
  * //alert(ts[1]);//企业全称
  * //alert(ts[2]);//企业简称
  * //alert(ts[3]);//行业代码
  * //alert(ts[4]);//行业名称
  */
function util_choose_enp()
{

  return window.showModalDialog(webBasePath+"/util/util_ChooseEnpBift.jsp?queryType=QueryAssurerWJFL&time=" + (new Date)+"&opDataUnclear=true",window,"dialogWidth:690px;dialogHeight:360px;center:yes;help:no;status:no;resizable:no");
}

/** 选择金融机构(客户)
  * 常用方法举例:var ts=util_choose_bank()
  * //alert(ts[0]);//金融机构代码
  * //alert(ts[1]);//金融机构简称
  * //alert(ts[2]);//所属机构代码
  * //alert(ts[3]);//金融机构全称
  * //alert(ts[4]);//所属省份代码
  * //alert(ts[5]);//所属机构名称
  * //alert(ts[6]);//金融省份名称
  * //alert(ts[7]);//行业代码
  */
function util_choose_bank()
{

  return window.showModalDialog(webBasePath+"/util/util_ChooseBank.jsp?ask=true&queryType=QueryGroup&time="+new Date()+"&opDataUnclear=true",window,"dialogWidth:650px;dialogHeight:400px;center:yes;help:no;status:no;resizable:no");
}
/** 选择客户
  * 常用方法举例:var ts=util_choose_client()
  * 常用方法举例:var ts=util_choose_client('02')//选择代理客户
  *	alert(ts[1]);//对应ta390050001
  *	alert(ts[2]);//对应ta390050002
  *	alert(ts[3]);//对应ta390050003
  *	alert(ts[4]);//对应ta390050004
  *	alert(ts[5]);//对应ta390050005
  *	alert(ts[6]);//对应ta390050006
  *	alert(ts[7]);//对应ta390050007
  */
function util_choose_client_new(ta390050005)
{
  var ret=["请不要取ts[0]"];
  var para="";
  if (ta390050005!=null)
  	para=para+"&ta390050005="+ta390050005;
  var ts=window.showModalDialog(webBasePath+"/bift/util/util_Query.jsp?queryType=icbc.cmis.bift.util.CChooseClientDAO"+para+"&width=700&height=432&hasDetailLink=new&isTotal=true"+"&opDataUnclear=true",window,"dialogWidth:760px;dialogHeight:526px;center:yes;help:no;status:no;resizable:yes");
  if (ts==null)
  	return ts;
  else 
    return ret.concat(ts);
}

function roundn(val,decimal) {
	var tmp,part,smalllen;
	if (decimal<=0||decimal==null)//处理整数位的四舍五入和异常情况
		return Math.round(val);
		
	tmp=new String(val);
	part=tmp.split('.');

	if (part.length!=2)
		return val;//多于一个'.'或者是整数时

	smalllen=part[1].length;
	if (smalllen>decimal)
		smalllen=decimal;
	if (part[1].charAt(decimal)<5)
	{//四舍
		part[1]=part[1].substr(0,decimal);
	}
	else
	{//五入
		part[1]=part[1].substr(0,decimal);
		var aa=new String(part[1]);
		part[1]=new String(parseInt(part[1],10)+1);
	}
	
	if (part[1].length<smalllen) //小数位前补0
		part[1]=zero.substr(0,smalllen-part[1].length)+part[1];
	else if (part[1].length>smalllen)//处理小数进位
	{
		if (part[0].charAt(0)=='-')
			part[0]=parseInt(part[0],10)-1;
		else
			part[0]=parseInt(part[0],10)+1;
		part[1]="0";
	}
	
	return parseFloat(part[0]+'.'+part[1]);
}

function initArray(){
	this.length = initArray.arguments.length
	for (var i = 0; i < this.length; i++)
	this[i+1] = initArray.arguments[i]
}
var daysInMonth = new initArray(31,28,31,30,31,30,31,31,30,31,30,31);
function getDays(month, year) {
	//下面的这段代码是判断当前是否是闰年的
	if (month == 2)
		return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28;
	else
		return daysInMonth[month];
}
function add_months(basedate,factor)
{
     //目前只考虑了factor为正的情况
     if (factor<0)
     	return 'error';
     var year=parseInt(basedate.substring(0,4),10);
     var month=parseInt(basedate.substring(4,6),10)+factor;
     var days=basedate.substring(6,8);
     if (month>12)
     {
        year=year+Math.floor(month/12);
        month=month % 12;
        
        if (month==0){//当month+factor是12的整数倍时，会发生month=0的情况
	     	year=year-1;
	     	month="12";
     	}
     }
     
     //考虑大小月调整的情况
     if (days>getDays(month,year))
     	days=getDays(month,year);
     if (month<10)
     	month="0"+month;
     	
     return ""+year+month+days;
}
//根据传入的资金交割日、首次起息日、付息方式，计算上一付息日
//返回的是字符串
function util_get_last_interest_date(settledate,startdate,interesttype)
{
  var result_this;
  var result_last;

  if (settledate==null||startdate==null||interesttype==null)
    return null;

  //如果在首次起息前交割，则返回资金交割日，保证利息为0
  if (settledate<startdate)
  	return settledate;

  //零息券，本期起息日就是资金交割日，保证利息为0
  if (interesttype ==4)
     return settledate;
  	
  //到期付息，本期起息日就是首次到期日
  if (interesttype ==3)
     return startdate;

  //一年付息，本期起息日或者是本年的付息日，或者是去年的付息日
  if (interesttype ==1)
  {
     //取得本年度付息日
     result_this= settledate.substring(0,4)+startdate.substring(startdate,4,8);
     //取得上年度付息日
     result_last= ""+(parseInt(settledate.substring(0,4),10)-1)+startdate.substring(startdate,4,8);
     if (result_this<=settledate)
        return result_this;
     else
        return result_last;
  }
  //半年付息，从本期起息日以半年为周期后推，直到找到最接近settledate的最后一个起息日
  if (interesttype ==2)
  {
     //从首次起息日开始
     result_this=startdate;
     times=times+1;
     //循环后推，直到result_this超过settledate,则result_last即为最后结果
     while (result_this<=settledate)
     {
     	times=times+1;
     	result_last=result_this;
     	result_this=add_months(startdate,6*times);
     }
     return result_last;
  }
}
//根据传入的年份是否闰年
//返回的是或否
function is_leap(year_in)
{
	var year=parseInt(year_in,10);
	return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? true : false;
}
//根据传入的起始日期和结束日期，判断之间是否存在2月29日（死息日）
//返回期间内死息日的天数
function sum_dead_interest_date(startdate,enddate)
{
  var year,dead_interest_date,day_count;
  day_count=0;

  year=startdate.substring(0,4);

  //从起始日期开始到结束日期，检查当年是否存在死息日(并存在与起止日期区间内)
  while (year<=enddate.substring(0,4))
  {
    if (is_leap(year)){
       dead_interest_date=year+'0229';
       if (dead_interest_date>=startdate && dead_interest_date<=enddate)
          day_count=day_count+1;
    }
    year=""+(parseInt(year,10)+1);
  }

  return day_count;
}
/**
 *计算天数
 *输入参数:起始日期，结束日期
 *输出为:起始日期到结束日期包括的天数
 */
function calculatedays(startdate,enddate){
/*	var oneday=24*60*60*1000;  //计算一天的毫秒数=24小时×60分钟×60秒×1000
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
	return sedate;*/
	
	date1 = new Date();
	date2 = new Date();
	diff  = new Date();
	
	date1temp = new Date(startdate.substr(4,2) + "/" + startdate.substr(6,2) + "/" + startdate.substr(0,4) + " " + "01:00:00 AM");
	date1.setTime(date1temp.getTime());
	
	date2temp = new Date(enddate.substr(4,2) + "/" + enddate.substr(6,2) + "/" + enddate.substr(0,4) + " " + "01:00:00 AM");
	date2.setTime(date2temp.getTime());

	// sets difference date to difference of first date and second date
	diff.setTime(Math.abs(date1.getTime() - date2.getTime()));
	timediff = diff.getTime();

	days = Math.floor(timediff / (1000 * 60 * 60 * 24)); 
	
	return days;	
}
//根据传入的起始日期和结束日期，判断其中的有效计息天数
//返回天数
function interest_days_between(startdate,enddate)
{
  return calculatedays(startdate,enddate)-sum_dead_interest_date(startdate,enddate);
}
//根据传入的资金交割日、首次起息日、付息方式、债券面值、实际利率、年限、到期起始日、付息标志，计算应计利息
//返回的是应计利息金额
function compute_interest_old(settledate,startdate,interesttype,base_amount,rate,year,enddate,payflag)
{
  var result_this,result_last,last_interest_date,next_interest_date;
  var times,factor;

  //初始赋值
  factor=1;
  if (year==null)
  	year=0;
  if (enddate==null)
  	enddate="20991231";
  if (payflag==null)
  	payflag=0;
  
  if (settledate==null||startdate==null||interesttype==null||base_amount==null||rate==null)
    return null;

  //如果到期付息，而年限未知则返回null
  if (interesttype==3 && (year==0 || enddate=='20991231'))
    return null;

  //如果在首次起息前交割，则返回0
  if (settledate<startdate)
  	return 0;

  //如果资金交割日晚于到期起始日，则将资金交割日设置为到期起始日
  if (settledate >enddate)
     settledate=enddate;

  //零息券，本期起息日就是资金交割日，利息为0
  if (interesttype ==4)
     return 0;
  	
  //到期付息，本期起息日就是首次到期日
  if (interesttype ==3)
  {
     if (payflag==0)
            factor=roundn(1.0*interest_days_between(startdate,settledate)/interest_days_between(startdate,enddate),14);
     return roundn(roundn(1.0*base_amount*roundn(1.0*rate*year,14),14)*factor,14);
  }

  //一年付息，本期起息日或者是本年的付息日，或者是去年的付息日
  if (interesttype ==1)
  {
     //取得本年度付息日
     result_this= settledate.substring(0,4)+startdate.substring(startdate,4,8);
     //取得上年度付息日
     result_last= ""+(parseInt(settledate.substring(0,4),10)-1)+startdate.substring(startdate,4,8);
     if (result_this<=settledate)
        last_interest_date = result_this;
     else
        last_interest_date = result_last;
     //上一付息日计算完毕,返回应计利息
     if (payflag==0)
	factor=roundn(1.0*interest_days_between(last_interest_date,settledate)/365,14);
     return roundn(roundn(1.0*base_amount*rate,14)*factor,14);
  }
  //半年付息，从本期起息日以半年为周期后推，直到找到最接近settledate的最后一个起息日
  if (interesttype ==2)
  {
     //从首次起息日开始
     result_this=startdate;
     times=0;
     //循环后推，直到result_this超过settledate,则result_last即为最后结果
     while (result_this<=settledate)
     {
     	times=times+1;
     	result_last=result_this;
     	result_this=add_months(startdate,6*times);
     }
     last_interest_date= result_last;
     next_interest_date= result_this;
     //上一付息日与下一付息日计算完毕，返回应计利息
     if (payflag==0)
	factor=roundn(1.0*interest_days_between(last_interest_date,settledate)/interest_days_between(last_interest_date,next_interest_date),14);
     return roundn(roundn(1.0*base_amount*roundn(1.0*rate/2,14),14)*factor,14);
  }
}

//根据传入的资金交割日、首次起息日、付息方式、债券面值、实际利率、年限、到期起始日、付息标志、计息类型、发行价格、workdate，计算应计利息
//返回的是应计利息金额
//应计利息根据workdate判断是否采用新算法，付息金额计算方法不变
function compute_interest(settledate, startdate, interesttype, base_amount, rate, year, enddate, payflag, interestmode, payprice, workdate) {
  var result_this, result_last, last_interest_date, next_interest_date;
  var times, factor;
  var genparadate;

      /*
      alert("资金交割日(" + settledate + ")\n" +
            "首次起息日(" + startdate + ")\n" +
            "付息方式(" + interesttype + ")\n" +
            "债券面值(" + base_amount + ")\n" +
            "实际利率(" + rate + ")\n" +
            "年限(" + year + ")\n" +
            "到期起始日(" + enddate + ")\n" +
            "付息标志(" + payflag + ")\n" +
            "计息类型(" + interestmode + ")\n" +
            "发行价格(" + payprice + ")\n" +
            "workdate(" + workdate + ")"
      );
      */

  //初始赋值
  factor = 1;
  if (year == null)
    year = 0;
  if (enddate == null)
    enddate = "20991231";
  if (payflag == null)
    payflag = 0;

  if (settledate == null || startdate == null || interesttype == null || base_amount == null || rate == null)
    return null;
  if (interestmode == null || payprice == null || workdate == null)
    return null;

  if (payflag == 1)
    return compute_interest_old(settledate, startdate, interesttype, base_amount, rate, year, enddate, payflag);
  genparadate = util_get_genpara('77', '01');
  if (genparadate == null)
    //参数不存在
    genparadate = "20991231";
  if (settledate < genparadate)
    //不采用新算法
    return compute_interest_old(settledate, startdate, interesttype, base_amount, rate, year, enddate, payflag);

  //如果到期付息，而年限未知则返回null
  if (interesttype == 3 && (year == 0 || enddate == '20991231'))
    return null;

  //如果在首次起息前交割，则返回0
  if (settledate < startdate)
    return 0;

  //如果资金交割日晚于到期起始日，则将资金交割日设置为到期起始日
  if (settledate > enddate)
    settledate = enddate;

  //算法调整
  //零息券，本期起息日就是资金交割日，利息为0
  if (interestmode == 4)
    if (payflag == 0) {

      /*
      alert("资金交割日(" + settledate + ")\n" +
            "首次起息日(" + startdate + ")\n" +
            "付息方式(" + interesttype + ")\n" +
            "债券面值(" + base_amount + ")\n" +
            "实际利率(" + rate + ")\n" +
            "年限(" + year + ")\n" +
            "到期起始日(" + enddate + ")\n" +
            "付息标志(" + payflag + ")\n" +
            "计息类型(" + interestmode + ")\n" +
            "发行价格(" + payprice + ")\n" +
            "workdate(" + workdate + ")\n" +
            "零息债券\n" +
            "P = " + payprice + "\n" +
            "t = " + interest_days_between(startdate, settledate) + "\n" +
            "T = " + interest_days_between(startdate, enddate) + "\n" +
            "100 - P = " + (100 * 10000 - payprice * 10000) / 10000 + "\n" +
            "(100 - P) * t * 债券面值 / 100 = " + roundn((100 * 10000 - payprice * 10000) / 10000 * interest_days_between(startdate, settledate) * base_amount * 10 / 1000, 16) + "\n" +
            "((100 - P) * t * 债券面值 / 100) / T = " + roundn(roundn((100 * 10000 - payprice * 10000) / 10000 * interest_days_between(startdate, settledate) * base_amount * 10 / 1000, 16) / interest_days_between(startdate, enddate), 16)
      );
      */

      return roundn(roundn((100 * 10000 - payprice * 10000) / 10000 * (interest_days_between(startdate, settledate) + sum_dead_interest_date(startdate, settledate)) * base_amount * 10 / 1000, 16) / (interest_days_between(startdate, enddate) + sum_dead_interest_date(startdate, enddate)), 16);
    } else
      return roundn((100 * 10000 - payprice * 10000) / 10000 * base_amount * 10 / 1000, 16);

  //到期付息，本期起息日就是首次到期日
  if (interesttype == 3) {
    //计算上一理论付息日
    //取得本年度付息日
    result_this = settledate.substring(0, 4) + enddate.substring(4, 8);
    //取得上年度付息日
    result_last = "" + (parseInt(settledate.substring(0, 4), 10) - 1) + enddate.substring(4, 8);
    if (result_this <= settledate)
      last_interest_date = result_this;
    else
      last_interest_date = result_last;
    //起息日或上一理论付息日取较大值
    if (startdate > last_interest_date)
      last_interest_date = startdate;
    if (payflag == 0) {

      /*
      alert("资金交割日(" + settledate + ")\n" +
            "首次起息日(" + startdate + ")\n" +
            "付息方式(" + interesttype + ")\n" +
            "债券面值(" + base_amount + ")\n" +
            "实际利率(" + rate + ")\n" +
            "年限(" + year + ")\n" +
            "到期起始日(" + enddate + ")\n" +
            "付息标志(" + payflag + ")\n" +
            "计息类型(" + interestmode + ")\n" +
            "发行价格(" + payprice + ")\n" +
            "workdate(" + workdate + ")\n" +
            "到期一次还本付息债券\n" +
            "起息日或上一理论付息日 = " + last_interest_date + "\n" +
            "K = " + (interest_days_between(startdate, settledate) - interest_days_between(startdate, settledate) % 365) / 365 + "\n" +
            "C * 债券面值 / 100 = " + base_amount * rate + "\n" +
            "K * C * 债券面值 / 100 = " + roundn(((interest_days_between(startdate, settledate) - interest_days_between(startdate, settledate) % 365) / 365) * base_amount * rate, 16) + "\n" +
            "t = " + interest_days_between(last_interest_date, settledate) + "\n" +
            "(C * 债券面值 / 100) * t = " + roundn(interest_days_between(last_interest_date, settledate) * base_amount * rate, 16) + "\n" +
            "(C * 债券面值 / 100) * t / 365 = " + roundn(roundn(interest_days_between(last_interest_date, settledate) * base_amount * rate, 16) / 365, 16) + "\n" +
            "(K * C * 债券面值 / 100) + ((C * 债券面值 / 100) * t / 365) = " + (roundn(((interest_days_between(startdate, settledate) - interest_days_between(startdate, settledate) % 365) / 365) * base_amount * rate, 16) + roundn(roundn(interest_days_between(last_interest_date, settledate) * base_amount * rate, 16) / 365, 16))
      );
      */

      return roundn((((interest_days_between(startdate, settledate) + sum_dead_interest_date(startdate, settledate)) - (interest_days_between(startdate, settledate) + sum_dead_interest_date(startdate, settledate)) % 365) / 365) * base_amount * rate, 16) + roundn(roundn((interest_days_between(last_interest_date, settledate) + sum_dead_interest_date(last_interest_date, settledate)) * base_amount * rate, 16) / 365, 16);
    } else
      return roundn(base_amount * roundn(rate * year, 16), 16);
  }

  //一年付息，本期起息日或者是本年的付息日，或者是去年的付息日
  if (interesttype == 1) {
    //取得本年度付息日
    result_this = settledate.substring(0, 4) + startdate.substring(4, 8);
    //取得上年度付息日
    result_last = "" + (parseInt(settledate.substring(0, 4), 10) - 1) + startdate.substring(4, 8);
    if (result_this <= settledate)
      last_interest_date = result_this;
    else
      last_interest_date = result_last;
    if (startdate > last_interest_date)
      last_interest_date = startdate;
    if (payflag == 0) {

      /*
      alert("资金交割日(" + settledate + ")\n" +
            "首次起息日(" + startdate + ")\n" +
            "付息方式(" + interesttype + ")\n" +
            "债券面值(" + base_amount + ")\n" +
            "实际利率(" + rate + ")\n" +
            "年限(" + year + ")\n" +
            "到期起始日(" + enddate + ")\n" +
            "付息标志(" + payflag + ")\n" +
            "计息类型(" + interestmode + ")\n" +
            "发行价格(" + payprice + ")\n" +
            "workdate(" + workdate + ")\n" +
            "一年付息一次\n" +
            "起息日或上一付息日 = " + last_interest_date + "\n" +
            "C * 债券面值 / 100 = " + base_amount * rate + "\n" +
            "t = " + interest_days_between(last_interest_date, settledate) + "\n" +
            "(C * 债券面值 / 100) * t / 365 = " + roundn(roundn(base_amount * rate, 16) * interest_days_between(last_interest_date, settledate) / 365, 16) + "\n" +
            roundn(roundn(base_amount * rate, 16) * (interest_days_between(last_interest_date, settledate) + sum_dead_interest_date(last_interest_date, settledate)) / 365, 16)
      );
      */

      return roundn(roundn(base_amount * rate, 16) * (interest_days_between(last_interest_date, settledate) + sum_dead_interest_date(last_interest_date, settledate)) / 365, 16);
    } else
      return roundn(base_amount * rate, 16);
  }

  //半年付息，从本期起息日以半年为周期后推，直到找到最接近settledate的最后一个起息日
  if (interesttype == 2) {
    //从首次起息日开始
    result_this = startdate;
    times = 0;
    //循环后推，直到result_this超过settledate则result_last即为最后结果
    while (result_this <= settledate) {
      times = times + 1;
      result_last = result_this;
      result_this = add_months(startdate, 6 * times);
    };
    last_interest_date = result_last;
    next_interest_date = result_this;
    if (startdate > last_interest_date)
      last_interest_date = startdate;
    if (payflag == 0) {

      /*
      alert("资金交割日(" + settledate + ")\n" +
            "首次起息日(" + startdate + ")\n" +
            "付息方式(" + interesttype + ")\n" +
            "债券面值(" + base_amount + ")\n" +
            "实际利率(" + rate + ")\n" +
            "年限(" + year + ")\n" +
            "到期起始日(" + enddate + ")\n" +
            "付息标志(" + payflag + ")\n" +
            "计息类型(" + interestmode + ")\n" +
            "发行价格(" + payprice + ")\n" +
            "workdate(" + workdate + ")\n" +
            "半年付息一次\n" +
            "起息日或上一付息日 = " + last_interest_date + "\n" +
            "C * 债券面值 / 100 = " + base_amount * rate + "\n" +
            "t = " + interest_days_between(last_interest_date, settledate) + "\n" +
            "(C * 债券面值 / 100) * t / 365 = " + roundn(roundn(base_amount * rate, 16) * interest_days_between(last_interest_date, settledate) / 365, 16)
      );
      */

      return roundn(roundn(base_amount * rate, 16) * (interest_days_between(last_interest_date, settledate) + sum_dead_interest_date(last_interest_date, settledate)) / 365, 16);
    } else
      return roundn(roundn(base_amount * rate, 16) / 2, 16);
  }
}

//调用存储过程计算应计利息
//根据传入的资金交割日、债券序号、债券面值、实际利率、付息标志，计算应计利息
function util_get_interest(settledate, bond_serial, base_amount, rate, payflag) {

  /*
  alert("资金交割日(" + settledate + ")\n" +
        "债券序号(" + bond_serial + ")\n" +
        "债券面值(" + base_amount + ")\n" +
        "实际利率(" + rate + ")");
        */

  //参数不允许为空
  if (settledate == null || bond_serial == null || base_amount == null || rate == null || payflag == null)
    return null;

  //组织查询条件
  var qstr = "method=getInterest&settledate=" + settledate + "&bond_serial=" + bond_serial + "&base_amount=" + base_amount + "&rate=" + rate + "&payflag=" + payflag;

  //查询
  objHTTP.Open('GET',webBasePath+'/servlet/icbc.cmis.bift.util.CUtilServlet?'+qstr,false);
  objHTTP.Send();

  //查询出错处理
  var xml = objHTTP.responseText;
  //alert(xml);
  if(!parser.loadXML(xml)) {
//    alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
    return null;
  }
  error = parser.getElementsByTagName("error");
  if(error.length > 0) {
    alert(error.item(0).text);
    return null;
  }
  //查询正确，分析结果
  //提取参数值
  var para_value = parser.documentElement.getAttribute("value");

  return para_value;
}

//主机地区号选择
function util_choose_area_compare(s_start_area_code, s_range) {
  if (s_range == null)
    s_range = "1111";
  //总行、一级行、准一级行、二级行
  //这里允许选择总行、一级行、准一级行、二级行

  return window.showModalDialog(webBasePath+"/bift/util/bift_util_choose_area_compare.jsp?area="+s_start_area_code+"&range="+s_range+"&time=" + (new Date)+"&opDataUnclear=true",window,"dialogWidth:380px;dialogHeight:280px;center:yes;help:no;status:no;resizable:no");
}

/**选择下一级审批人
  *Danny 20041205
  *@param flowtype 审批类型
  *@param level 审批环节
  *@return ts[0][1]审批人编号
  *        ts[0][2]审批人姓名
  */
function util_choose_checkman(flowtype,level)
{
  var qstr = webBasePath+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.bift.flow.core.util.ChooseCheckList&scene=checkman&flowtype="+flowtype+"&level="+level+"&opDataUnclear=true";
  return window.showModalDialog(qstr,window,"dialogWidth:370px;dialogHeight:350px;center:yes;help:no;status:no;resizable:yes");
}

/**
*选择下级审批人
*传入参数：major 0申请人 1审批人 2最高审批人
*         area 机构代码
*return ts[0][1]审批人编号
*       ts[0][2]审批人姓名
*/
function util_choose_namelist(major,area)
{
  var qstr = webBasePath+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.bift.flow.core.util.ChooseCheckList&scene=h&checkmajor="+major+"&area_code="+area+"&emp_code=0,1&opDataUnclear=true";
  return window.showModalDialog(qstr,window,"dialogWidth:370px;dialogHeight:350px;center:yes;help:no;status:no;resizable:yes");
}

/**选择审批柜员
  *王音 20041214
  *@param area_code 机构编码
  *@return ts[0][1]柜员编号
  *        ts[0][2]柜员姓名
  *        ts[0][3]所属机构
  *        ts[0][4]审批角色
*/
function util_choose_checkemployee(scene,area_code,checkmajor,emp_code)
{
  var qstr = webBasePath+"/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.bift.flow.core.util.ChooseCheckList&scene="+scene+"&area_code="+area_code+"&checkmajor="+checkmajor+"&emp_code="+emp_code+"&opDataUnclear=true";
  return window.showModalDialog(qstr,window,"dialogWidth:370px;dialogHeight:350px;center:yes;help:no;status:no;resizable:yes");
}

// 买断式回购选择审批单
function util_choose_open_approval(empFlag,busiType,oldsn)
{
  var ret=["请不要取ts[0]"];
  if (empFlag==null)
  	empFlag="";
  	
  if (busiType==null)
  	busiType="";
  	
  if (oldsn==null)
  	oldsn="";
  	
  var ts=window.showModalDialog(webBasePath+"/bift/util/util_Query.jsp?ta391032008="+ busiType + "&empFlag=" + empFlag + "&oldsn=" + oldsn + "&queryType=icbc.cmis.bift.sa.COpenChooseApprovalDAO&QueryPage=/bift/s/sa/bift_choose_open_approval_input.jsp &width=700&height=480&hasDetailLink=new"+"&opDataUnclear=true",window,"dialogWidth:800px;dialogHeight:570px;center:yes;help:no;status:no;resizable:yes");
  if (ts==null)
  	return ts;
  else 
    return ret.concat(ts);
}


// 现券央票买卖选择审批单
function util_choose_cashbill_approval(kemu,empFlag,oldsn)
{
  var ret=["请不要取ts[0]"];
  if (kemu==null)
  	kemu="";

  if (empFlag==null)
  	empFlag="";
  	
  if (oldsn==null)
  	oldsn="";
  	
  var ts=window.showModalDialog(webBasePath+"/bift/util/util_Query.jsp?kemu="+kemu+"&empFlag="+empFlag+"&oldsn="+oldsn+"&queryType=icbc.cmis.bift.ca.CCashBillChooseApprovalDAO&QueryPage=/bift/c/ca/bift_choose_cashbill_approval_input.jsp &width=700&height=480&hasDetailLink=new"+"&opDataUnclear=true",window,"dialogWidth:800px;dialogHeight:570px;center:yes;help:no;status:no;resizable:yes");
  if (ts==null)
  	return ts;
  else 
    return ret.concat(ts);
}


// 公开市场现券投标买卖选择审批单
function util_choose_cashbid_approval(empFlag)
{
  var ret=["请不要取ts[0]"];

  if (empFlag==null)
  	empFlag="";
  	
  var ts=window.showModalDialog(webBasePath+"/bift/util/util_Query.jsp?empFlag="+empFlag+"&queryType=icbc.cmis.bift.ca.CCashBidChooseApprovalDAO&QueryPage=/bift/c/ca/bift_choose_cashbid_approval_input.jsp &width=700&height=480&hasDetailLink=new"+"&opDataUnclear=true",window,"dialogWidth:800px;dialogHeight:570px;center:yes;help:no;status:no;resizable:yes");
  if (ts==null)
  	return ts;
  else 
    return ret.concat(ts);
}

// 公开市场央票投标选择审批单
function util_choose_billbid_approval(empFlag)
{
  var ret=["请不要取ts[0]"];

  if (empFlag==null)
  	empFlag="";
  	
  var ts=window.showModalDialog(webBasePath+"/bift/util/util_Query.jsp?empFlag="+empFlag+"&queryType=icbc.cmis.bift.ca.CBillBidChooseApprovalDAO&QueryPage=/bift/c/ca/bift_choose_billbid_approval_input.jsp &width=700&height=480&hasDetailLink=new"+"&opDataUnclear=true",window,"dialogWidth:800px;dialogHeight:570px;center:yes;help:no;status:no;resizable:yes");
  if (ts==null)
  	return ts;
  else 
    return ret.concat(ts);
}

/** 新增、根据客户属性选择客户
  * 常用方法举例:var ts=util_choose_client_type()//选择全部客户
  * 常用方法举例:var ts=util_choose_client_type('01')//选择所有商业银行客户
  * by lexy 2004.12.07
*/
function util_choose_client_type(ta390050007)
{
  var ret=["请不要取ts[0]"];
  var para="";
  if (ta390050007==null)
  	ta390050007="";
  
  para=para+"&ta390050007="+ta390050007;
    
  var ts=window.showModalDialog(webBasePath+"/bift/util/util_Query.jsp?queryType=icbc.cmis.bift.util.CChooseClientDAO&QueryPage=/bift/util/bift_util_choose_client_input.jsp"+para+"&width=700&height=480&hasDetailLink=new"+"&opDataUnclear=true",window,"dialogWidth:800px;dialogHeight:570px;center:yes;help:no;status:no;resizable:yes");
  if (ts==null)
  	return ts;
  else 
    return ret.concat(ts);
}
