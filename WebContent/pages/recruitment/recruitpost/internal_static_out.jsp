<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>竞聘职位管理</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="skins/css/style.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css"/>
<style type="text/css">
table.static-table {
	width: 100%;
	border-top:1px solid #c8c8c8;
	border-right:1px solid #c8c8c8;
}

table.static-table th {
	border-left:1px solid #c8c8c8;
	border-bottom:1px solid #c8c8c8;
	padding:5px 10px;
	line-height:25px;
	text-align:center;
}
table.static-table td {
	border-left:1px solid #c8c8c8;
	border-bottom:1px solid #c8c8c8;
	padding:8px 10px;
}

table.static-table .label{
	background:#f8f8f8;
}
</style>

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.ajaxupload.3.6.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="pages/tree.js"></script>

<script type="text/javascript">
var tid='${param.id}';

$(document).ready(function() {
  	//加載數據
    loadData(tid);
    
});




//取数据
function loadData(tid) {
	if(tid!=null&&tid!=''&&tid!='null'){
		$.getJSON("recruitment/getRecruitPostById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					//正文
					if(key=='postcontent'){
						var content=data[key];
						if(content!=null){
							$("#postcontent").html(content.postcontent);
						}
					}else if($('#myForm #'+key)){
						$('#myForm #'+key).text(data[key]);
					}
				}
				//计算高度
				_autoHeight();
			}
		});
	}
}



//竞聘此职位
function openRecruitWindow(){
	if(${admin}){
		alert("管理员不可以竞聘岗位!");
		return;
	}
	
	if(!confirm('确认要竞聘此岗位吗？')){
		return;
	}
	
	$.post("recruitment/applyRecruitPostByUserId.do",{id:tid}, function(webuserguid) {
		//convertView('recruitpost.do?page=competition_resume&recruitpostguid='+tid+"&id="+webuserguid);
		window.location.href='${baseUrl }recruitpost.do?page=competition_resume_out&id='+webuserguid+"&recruitpostguid="+tid;
	});
	
}



//推荐
function openRecommend(){
	window.location.href='${baseUrl }resume.do?page=recommend/form_2_out&recruitpostguid='+tid;
	
	//window.parent.convertView('resume.do?page=recommend/form_2&recruitpostguid='+tid);
}






//返回
function back(){
	window.parent.convertView('');
}










</script>




</head>

<body>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			竞聘职位信息
		</div>
		<div class="table-ctrl">
			<a class="btn" href="javascript:openRecommend();"><i class="icon icon-hand-left"></i><span>立即推荐</span></a>
			<a class="btn" href="javascript:openRecruitWindow();"><i class="icon icon-check"></i><span>立即竞聘</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
			<div class="content" style="padding:10px 20px;" id="myForm">
				<div style="font-size:16px;font-weight:bold;line-height:60px;" id="postname"></div>
				<table cellpadding="0" cellspacing="0" border="0" class="static-table">
						<tbody>
							<tr>
								<td width="100px;" class="label">
									工作地点:
								</td>
								<td>
									<span id="workplacename"></span>
								</td>
								<td class="label">
									职位类别:
								</td>
								<td>
									<span id="categoryname"></span>
								</td>
								<td class="label">
									学历:
								</td>
								<td>
									<span id="educationalname"></span>
								</td>
							</tr>
							<tr>
								<td class="label">
									工作年限:
								</td>
								<td>
									<span id="workagename"></span>
								</td>
								<td class="label">
									人数:
								</td>
								<td>
									<span id="postnum"></span>
								</td>
								<td class="label">
									失效时间:
								</td>
								<td >
									<span id="validdate"></span>
								</td>
							</tr>
						</tbody>
					</table>
					<br/>
					<div style="line-height:35px;font-size:14px;text-align:left;">
						<p id="postcontent">
						
						</p>
					</div>
			</div>
	</div>
</div>

</body>
</html>





