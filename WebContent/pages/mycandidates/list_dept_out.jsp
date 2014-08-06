<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<!DOCTYPE html>
<html>
<head>
<title>简历管理</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<META HTTP-EQUIV="refresh" CONTENT="120"/> <!--设定自动刷新时间-->

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>


<style type="text/css">
html,body
{
	margin: 0px; padding: 0px; border-style: none; border-bottom: 2px solid #e0e0e0;width:100%;height:100%;
}
table,div
{
	font-size:12px;
}
td {
	height:20px;
	line-height:20px;
	text-align:left;
}
.td_title
{
	background-color:#c8c8c8;
	border:1px solid #CFCFCF;
	border-left:0px;
	font-weight: bold;
}
.td_content
{
}
.bold_text
{
	font-weight:bold;
}
.normal_text
{
	font-weight:normal;
}
</style>



<script type="text/javascript">
var userid='${userid}';
$(document).ready(function() {
	//显示数据
	loadMyDptCandidates();
	
	
});




var isshow=false;
function isiframe(data){
	if(data!=null&&data!=''){
		isshow=true;
	}else{
		isshow=false;
	}
	if(window.parent.showIframe)
		window.parent.showIframe(isshow);
}





function loadMyDptCandidates(){
	var pam={parameters:{userid:userid}};
	$.ajax({
		url: "mycandidates/getMyDptCandidatesByUserGuid.do",
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",
		beforeSend:null,
		data: JSON.stringify(pam),
		success: function(data) {
			isiframe(data);
			var htm='';
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var dataObj=data[i];
					htm+='<tr style="cursor:pointer;" onclick="checkinfo(\''+dataObj.webuserguid+'\',\''+dataObj.recruitpostguid+'\',\''+dataObj.mycandidatesguid+'\',\''+dataObj.resumeeamilguid+'\',\''+dataObj.recommendguid+'\')"  bgColor="#f6f8f7" onmouseover="ch_color(this);" onmouseout="ch_color_out(this);" bordercolorlight="#ffffff" bordercolordark="#ffffff" class="normal_text" id="content_line_0">';
					htm+='<td class="td_content" >'+dataObj.candidatesstatename+'</td>';
					htm+='<td class="td_content">'+dataObj.name+'</td>';
					htm+='<td class="td_content">'+dataObj.recommenddeptname+'</td>';
					htm+='<td class="td_content">'+dataObj.recommendpostname+'</td>';
					//htm+='<td class="stylecenter">'+dataObj[i].mobile+'</td>';
					if(dataObj.birthdayname==null){
						htm+='<td class="td_content">';
						htm+='&nbsp;';
						htm+='</td>';
					}else{
						htm+='<td class="td_content">'+dataObj.birthdayname+'</td>';
					}
					if(dataObj.culturename==null){
						htm+='<td class="td_content">';
						htm+='&nbsp;';
						htm+='</td>';
					}else{
						htm+='<td class="td_content">'+dataObj.culturename+'</td>';
					}
					if(dataObj.workagename==null){
						htm+='<td class="td_content">';
						htm+='&nbsp;';
						htm+='</td>';
					}else{
						htm+='<td class="td_content">'+dataObj.workagename+'</td>';
					}
					htm+='<td class="td_content">'+dataObj.postname+'</td>';
					//htm+='<td class="stylecenter">'+dataObj[i].candidatestypename+'</td></tr>';
				}
				
				$("#dept").html(htm);
			}
			
		}
		
	});
	
	
}

//简历详细信息
function checkinfo(webuserguid,recruitpostguid,mycandidatesguid,resumeeamilguid,recommendguid){
	window.open('resume/getResumeStaticById.do?id='+webuserguid+'&recruitpostguid='+recruitpostguid+'&mycandidatesguid='+mycandidatesguid+'&resumeeamilguid='+resumeeamilguid+'&recommendguid='+recommendguid+'&isclose='+true);
}




function ch_color(obj){
	obj.style.color = 'red';
}

function ch_color_out(obj){
	obj.style.color = "";
}



</script>
</head>
<body>



<form method="post" name="listform" id="listform">
<table width="653px" border="0" cellspacing="1" cellpadding="1" id="tab_list">
	<thead>
		<tr>
			<td style="width:90px" class="td_title">状态</td>
			<td width="80" class="td_title">姓名</td>
			<td width="180" class="td_title">推荐部门</td>
			<td width="150" class="td_title">推荐岗位</td>
			<!--<td width="90" class="td_title">手机</td>-->
		    <td width="70" class="td_title">年龄</td>
		    <td width="100" class="td_title">学历</td>
		    <td width="180" class="td_title">工作年限</td>
		    <td width="180" class="td_title">职位名称</td>
		    <!--  <td width="120" class="td_title">来源渠道</td>-->
		</tr>
	</thead>
	<tbody id="dept">
		
	</tbody>
</table>
</form>


</body>
</html>

