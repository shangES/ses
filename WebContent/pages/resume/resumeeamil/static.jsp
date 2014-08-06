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

<link rel="stylesheet" type="text/css" href="skins/css/style.css"/>


<style>
<!--

table.static-table{border:0px;}
table.static-table td{border:0px;}
table.static-table tbody tr:HOVER {
background:none;
}

-->
</style>
<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.layout.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>

<script type="text/javascript">
var tid='${param.id}';
var mycandidatesguid='${mycandidatesguid}'

$(document).ready(function () {
	

	//取数据
	loadData();
    
	
});



//取数据
function loadData() {
	if(tid!=null&&tid!=''&&tid!='null'){
		$.getJSON("resume/getResumeEamilById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#'+key)){
						$('#'+key).html(data[key]);
					}
				}
				
				//附件信息
				var resumeeamilfiles=data.resumeeamilfiles;
				if(resumeeamilfiles!=null&&resumeeamilfiles.length>0){
					var htm='';
					for(var i=0;i<resumeeamilfiles.length;i++){
						var file=resumeeamilfiles[i];
						//htm+='<a href="'+file.resumeeamilfileguid+'">';
						//htm+=file.filename;
						//htm+='</a><br/>';
						htm+='<a href="resume/DownLoadresumeeamilfile.do?id='+file.resumeeamilfileguid+'">';
						htm+=file.filename;
						htm+='</a><br/>';
					}
					$("#files").html(htm);
				}
			}
			//计算高度
			_autoHeight();
		});
	}
}



//返回
function back(){
	window.parent.convertView('');
}

</script>
</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>简历邮箱管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
	<div class="sort-cont sort-table">

		<div class="table">
			<div class="table-bar">
				<div class="table-title">
					邮箱信息
				</div>
				<div class="table-ctrl">
					<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
				</div>
			</div>
			<div class="table-wrapper" >
			
				<div style="border-top:1px solid #ddd;">
					<table align="center" border="0" cellpadding="0" class="static-table">
						<tbody>
							<tr>
								<td align="right" width="80">
									发件人：</td>
								<td valign="top" id="personal">
								</td>
							</tr>
							<tr>
								<td align="right">
									电子邮箱：
								</td>
								<td valign="top" id="email">
								</td>
							</tr>
							<tr>
								<td align="right">
									主题：
								</td>
								<td valign="top" id="subject">
								</td>
							</tr>
							<tr>
								<td align="right">
									投递时间：
								</td>
								<td valign="top" id="modtime">
								</td>
							</tr>
							<tr>
								<td align="right">
									附件信息：
								</td>
								<td valign="top" id="files">
								
								</td>
							</tr>
						</tbody>
					</table>
				</div>
					
					
				<div style="border-top:1px solid #ddd;padding:20px;">
	               <div id="content">
	                  
	               </div>
				</div>
			</div>
		</div>
		
		
	</div>
</div>



</body>
</html>
