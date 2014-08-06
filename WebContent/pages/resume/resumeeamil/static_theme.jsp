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
<link rel="stylesheet" type="text/css" href="plugin/lodop/PrintSample10.css"/>

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
<script type="text/javascript" src="plugin/lodop/LodopFuncs.js"></script>
<script type="text/javascript">
var tid='${param.id}';
var formopen='${param.formOpen}';

$(document).ready(function () {

	if(formopen){
		$("#back").hide();
		$("#isclose").show();
	}

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


function close1(){
	window.parent.close();
}


//打印简历
function print(){	
	LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  	
	var strBodyStyle="<style>"+document.getElementById("style1").innerHTML+"</style>";
	var strFormHtml=strBodyStyle+"<body>"+document.getElementById("content").innerHTML+"</body>";
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_样式风格");	
	LODOP.ADD_PRINT_TEXT(35,310,260,20,"个人简历");
	
	LODOP.ADD_PRINT_HTM("0%","0%","100%","100%",strFormHtml);
	LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT","Full-Width");
	LODOP.PREVIEW();
};

</script>

<!--打印-->
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="install_lodop32.exe"></embed>
</object> 
</head>
<body>


		<div class="table">
			<div class="table-bar">
				<div class="table-title">
					邮箱信息
				</div>
				<div class="table-ctrl">
					<a class="btn" href="javascript:print();"><i class="icon icon-print"></i><span>打印</span></a>
					<a class="btn" id="back" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
					<a class="btn" style="display:none" id="isclose"  href="javascript:close1();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>				</div>
			</div>
			<div class="table-wrapper" id="wrapper">
			
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
					
					
					
									
				<div style="border-top:1px solid #ddd;padding:2px;height: auto;">
	               <div id="content" style="height: auto;float: left;">
	                  
	               </div>
				</div>
			</div>
		</div>



</body>


<!-- 打印样式 -->
<style id="style1">

.table-wrapper{
	padding:5px 10px 10px 10px;
}


table.static-table {
    border:0px;
	width: 100%;
	table-layout: fixed;
	border-top:1px solid #ccc;
	border-right:0px solid #ccc;
}

table.static-table th {
	border-left:0px solid #ccc;
	border-bottom:0px solid #ccc;
	border-right:0px solid #ccc;
	border-top:0px solid #ccc;
	height: 35px;
	font-weight:normal;
	color:#000;
	overflow: hidden;
	white-space: nowrap;
	word-break: keep-all;
	word-wrap: normal;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
}

table.static-table td {
	border-left:0px solid #ccc;
	border-bottom:0px solid #ccc;
	border-right:0px solid #ccc;
	border-top:0px solid #ccc;
	qdisplay: block;
	height: 35px;
	line-height: 35px;
	padding:0px 5px;
	overflow: hidden;
	white-space: nowrap;
	word-break: keep-all;
	word-wrap: normal;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
}

table.static-table tbody tr:HOVER {
	background:#f8f8f8;
}




.resumeBody {color: #555555;font-size: 12px;border-top:1px solid #ddd;}
.resumeBody strong, .resumeBody h1, .resumeBody h5, .resumeBody h6 {color: #555555;font-size:14px;padding:0px;margin:0px;}
.resumeBody h1 {font-size: 32px;height: 42px;line-height: 1em;}
.resumeBody .headerImg {border: 1px solid #DDDDDD;padding: 5px;}
.resumeBody .ver-line {color: #555555;font-size: 14px;padding: 0 5px;}
.resumeBody h5, .resumeBody h6, .resumeBody strong {font-weight: bold;}
.resumeBody .details dt {background: url("skins/images/lookResumebg.jpg") no-repeat scroll 5px 0 transparent;}
.resumeBody .details dt h5 {color: #315AAA;font-size: 14px;height: 26px;line-height: 26px;text-indent: 20px;}
.resumeBody .summary, .resumeBody .details dd {line-height: 25px;;margin: 10px;padding-left: 10px;}
.resumeBody .certificates, .resumeBody .training, .resumeBody .graduates, .resumeBody .social, .resumeBody .education-background, .resumeBody .project-experience, .resumeBody .work-experience {border-bottom: 1px dotted #CCCCCC;margin-bottom: 10px;padding-bottom: 5px;}
.resumeBody .certificates:last-child, .resumeBody .training:last-child, .resumeBody .graduates:last-child, .resumeBody .education-background:last-child, .resumeBody .project-experience:last-child, .resumeBody .work-experience:last-child {border-bottom: 0 none;}
.resumeBody .training h6 {margin-bottom: 10px;}
.resumeBody .em {}

</style> 


</html>
