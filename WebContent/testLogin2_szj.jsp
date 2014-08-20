<%@ page contentType="text/html; charset=GB2312" %>
<%@ page import="icbc.cmis.base.*" %>
<%@ page import="icbc.missign.*" %>
<%@ taglib uri="cmisTags" prefix="cmis" %>

<%
CMisSessionMgr sm = new CMisSessionMgr(request,true);
LoginDAO dao = new LoginDAO(new icbc.cmis.operation.DummyOperation());
String empCode = null;
//Employee employee = dao.getEmployee("000055555");
try{
  empCode = request.getParameter("newEmpCode");
}catch(Exception e){
  empCode = "888800135";
}

if(empCode == null || empCode.equals(""))
  empCode = "888800135";
Employee employee = dao.getEmployee(empCode);
//Employee employee = dao.getEmployee("060301003");
//employee.setEmployeeClass("8");
//employee.setEmployeeClassName("op");
//employee.setEmployeeMajor("240");
//employee.setEmployeeMajorName("flow");

sm.addSessionData("Employee",employee);
sm.updateSessionData("AreaCode", employee.getMdbSID());
sm.updateSessionData("AreaName", employee.getAreaName());
sm.updateSessionData("BankFlag", employee.getBankFlag());
sm.updateSessionData("EmployeeCode", employee.getEmployeeCode());
sm.updateSessionData("EmployeeName", employee.getEmployeeName());
sm.updateSessionData("Login", "YES");
sm.updateSessionData("EmployeeClass", employee.getEmployeeClass());
sm.updateSessionData("EmployeeClassName", employee.getEmployeeClassName());
//sm.updateSessionData("Major", employee.getEmployeeMajor());
sm.updateSessionData("Major", "099");
sm.updateSessionData("MajorName", employee.getEmployeeMajorName());
sm.updateSessionData("Login", "YES");
sm.updateSessionData("LangCode", employee.getLangCode());
sm.sessionCommit();
String who = employee.getEmployeeCode() + employee.getEmployeeName();
%>
<html>
<head>
<title>
welcome
</title>
<link rel="stylesheet" href="/icbc/cmis/css/main.css" type="text/css">
</head>
<script language="javascript">
function choose() {
  var choose = "&eCodeWsA342d=120401002&areaWsA342d=12020000&eClassWsA342d=1,5,8,11&majorWsA342d=210,801,220&includeSelfWsA342d=false&multiSelectWsA342d=false&time=" + (new Date);
alert(choose);
  var ts = window.showModalDialog("/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.ChooseBelongEmp&OpDataUnclear=true"+choose,window,"dialogWidth:328px;dialogHeight:320px;center:yes;help:no;status:no;resizable:no");
  //var ts = window.open("/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.ChooseBelongEmp&OpDataUnclear=true"+choose,"window","","center:yes;help:no;status:no;resizable:no");
  
  if(ts != null) {
    var tt = "";
    for(i=0;i<ts.length;i++){
      tt = ts[i][1] +'|'+ ts[i][2] +'|'+ ts[i][3] +'|'+ ts[i][4] +'|'+ ts[i][5] +'|'+ ts[i][6];
      alert(tt);
    }
  } else {
    alert('没选择！');
  }
}


function choose2() {
  var choose = "&eCodeWsA342d=120401002&areaWsA342d=12020000&eClassWsA342d=1,5,8,11&majorWsA342d=210,801,220&includeSelfWsA342d=false&multiSelectWsA342d=false&time=" + (new Date);
alert(choose);
  var ts = window.showModalDialog("/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.ChooseBelongEmp&OpDataUnclear=true"+choose,window,"dialogWidth:328px;dialogHeight:320px;center:yes;help:no;status:no;resizable:no");
  //var ts = window.open("/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.util.ChooseEmployee&OpDataUnclear=true"+choose,"window","","center:yes;help:no;status:no;resizable:no");
  
  if(ts != null) {
    var tt = "";
    for(i=0;i<ts.length;i++){
      tt = ts[i][1] +'|'+ ts[i][2] +'|'+ ts[i][3] +'|'+ ts[i][4] +'|'+ ts[i][5] +'|'+ ts[i][6];
      alert(tt);
    }
  } else {
    alert('没选择！');
  }
}

function daikuanlian() {
  var choose = "&act=preNew&transferFlag=0&billValue=1000&billNo=1232123456&loanFormat=30&delFlag=false&returnUrl=asasddasasd&enpName=qqqqqq&customer=120290000020808&calltypeflag=3&time=" + (new Date);

  var ts = window.showModalDialog("/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.FG.LoanChainOp&OpDataUnclear=true"+choose,window,"dialogWidth:800px;dialogHeight:600px;center:yes;help:no;status:no;resizable:no");
  if(ts != null) {
    var tt = "";
    for(i=0;i<ts.length;i++){
      tt = ts[i][1] +'|'+ ts[i][2] +'|'+ ts[i][3] +'|'+ ts[i][4] +'|'+ ts[i][5] +'|'+ ts[i][6];
      alert(tt);
    }
  } else {
    alert('没选择！');
  }
}



function area() {
  var ts = window.showModalDialog("/icbc/cmis/util/util_ChooseArea.jsp?area=00000000&range=11101&enableChangeForArea=false",window,"dialogWidth:370px;dialogHeight:230px;center:yes;help:no;status:no;resizable:no");
  if(ts != null) {
    alert(ts);
    alert(ts.slice(10));
  } else {
    alert('没选择！');
  }
}
function jsp() {
  var ts = window.showModalDialog("Jsp1.jsp",window,"dialogWidth:328px;dialogHeight:320px;center:yes;help:no;status:no;resizable:no");
}
function authorize() {
  var ts = window.showModalDialog("/icbc/cmis/util/util_Authorize.jsp","审批","dialogWidth:295px;dialogHeight:230px;center:yes;help:no;status:no;resizable:no");
  if(ts != null) {
    alert('ok');
  } else {
    alert('fail');
  }
}
function ChooseEnp() {
  var ts = window.showModalDialog("/icbc/cmis/util/util_ChooseEnp.jsp?queryType=QueryAssurerWJFL&assuretype=credit&time=" + (new Date),window,"dialogWidth:630px;dialogHeight:360px;center:yes;help:no;status:no;resizable:no");
  if(ts != null) {
    alert(ts[0]);
    alert(ts[1]);
    alert(ts[2]);
  } else {
    alert('fail');
  }
}

</script>
<body>
<form name="form1" action="testLogin2_szj.jsp">
<input type="text" name="newEmpCode" >
<input type="submit" name="Submit" value="Change Emp Code">
</form>
<cmis:tabpage>
  <cmis:tabpagebar title="Startup" url="index.jsp" selected="false" />
  <cmis:tabpagebar title="Login" selected="true" />
  <cmis:tabpagecontent info="<%=who%>">
  <br>  
  <a href="/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BA.BA_approvelistOp&opAction=getmylist&ordercode=01&busitype=27&runproc=0">流程调查</a><br>
  <br>
  <a href="/icbc/cmis/servlet/icbc.cmis.servlets.CmisReqServlet?operationName=icbc.cmis.flow.BA.BA_approvelistOp&opAction=getmylist&ordercode=012&busitype=27&runproc=0">流程审查</a><br>
  
  
  </cmis:tabpagecontent>
</cmis:tabpage>
</body>
</html>
