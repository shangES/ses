/**
 * updated by WuQQ,20040219,为档案项目增加if_branch_has_access_permission()方法
 *
 * updated by WuQQ,20040219,修改CheckLength方法提示信息
 * @return
 */

//////////////////////////
/**
 * verify datas
 */
function CheckData(inputValue){
  toNumber(inputValue);
  toExact(inputValue,16,2);
}
/**
 * verify the date
 */
function checkDate(obj,digit)
{
  //var srcIndex = getIndex(window.event.srcElement);
  var srcIndex = 2;

  var timeStyle;
  var date = obj.value;
  var aDate;
  aDate = date;
  if (digit==4){
    timeStyle = "YYYY";
    aDate += "0101";
  }else if (digit==6){
    timeStyle = "YYYYMM";
    aDate += "01";
  }else if (digit==8){
    timeStyle = "YYYYMMDD";
  }
  if(date.length!=digit )
  {
    alert("请先输入"+digit+"位时间"+timeStyle+"!");
    //document.all(srcIndex).focus();
    obj.focus();
    return false;
  }
  if(isNaN(date))
  {
    alert("时间必须是"+digit+"位数字型!");
    obj.focus();
    return false;
  }
  /*
  if (isDateValid2(aDate)){
    alert("输入时间大于系统时间，请重新输入");
    obj.focus();
    return false;
  }
  */
  if (!isDateValid1(aDate)){
    alert("输入时间不正确，请重新输入");
    obj.focus();
    return false;
  }

  return true;
}
/***************************************************************/
function isDateValid1(str){
  if(str.length < 8) return false;
  var sy = str.substring(0,4);
  var iy = sy/1;
  var sm =str.substring(4,6);
  var im = sm/1;
  var sd = str.substring(6,8);
  var id = sd/1;
  var month1 = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
  var month2 = new Array(31,29,31,30,31,30,31,31,30,31,30,31);
  if((iy%4 == 0)||(iy%400 ==0)||(iy%100 == 0)){
    if((id <= month2[im-1])&&(id >=1 ))
      return true;
    else
      return false;
  }
  else{
    if ( im == 0 )
       return true;
    if((id <= month1[im-1])&&(id >= 1))
      return true;
   else
      return false;
  }
}
/**
 * 判断输入年月日是否比当前系统时间大
 */
//输入参数为8位字符窜 exp:20050606
function isDateValid2(str){
  var now = new Date()
  var year = now.getYear()
  var month = now.getMonth()+1;
  var day = now.getDate()
  var today =""+year;
  today += ((month<10)?"0":"")+month;
  today += ((day<10)?"0":"")+day;
  if (str >= today) return true;
  return false;
}
/**
 * test function
 */
function test(){
  alert("This is just a test");
}
/**
 * check length out of limit
 */
 function CheckLength(inputObj,aLength){
  var inputValue = inputObj.value;
  //alert(inputValue);
  var len=0;
  var tempStr;
  var i;
  for (i=0;i<inputValue.length;i++){
    tempStr = inputValue.substring(i,i+1);
    //if ((tempStr>='0' && tempStr<='9')||(tempStr>='a' && tempStr<='z')||(tempStr>='A' && tempStr<='Z')){
    //	len++;
    tempStr = escape(tempStr);
    if(tempStr.substring(0,2) == "%u") {
      len += 2;
    }else{
      len += 1;
    }
  }
  if ((aLength-0)<len){
    alert("光标处字符超长，汉字应该在"+aLength/2+"以内，英文应该在"+aLength+"以内！");
    inputObj.focus();
  }
  return;
}

var objHTTP = new ActiveXObject("Microsoft.XMLHTTP");
  var parser=new ActiveXObject("microsoft.xmldom");
  parser.async="false";
//added by WuQQ,20040310
  function if_branch_has_access_permission(area_code,bank_flag){
    objHTTP.open('GET','/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?xmlOutput=true&operationName=icbc.cmis.FR.util.FR_util&doWhat=getBranchPermission&BankFlag='+bank_flag+'&AreaCode='+area_code+'&time='+new(Date),false);
    objHTTP.Send();
//    alert('objHTTP.Send();');
    var xml = objHTTP.responseText;
    if(!parser.loadXML(xml)) {
      alert(parser.parseError.reason + "\n" +parser.parseError.line + "\n" +parser.parseError.srcText + "\n");
//      alert('loadXML;');
      return;
    }
    var sBranchWithPermission = parser.documentElement.getAttribute("sBranchWithPermission");
    var tmp_area_code;
    for (var i=0;i<sBranchWithPermission.length/8;i++){
      tmp_area_code = sBranchWithPermission.substring(i*8,(i+1)*8);
      if (tmp_area_code==area_code){
         return true;
      }
    }
    return false;
  }
