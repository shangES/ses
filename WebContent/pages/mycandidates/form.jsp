<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>


<script type="text/javascript">
var tid='${param.id}';
var recruitpostguid='${param.recruitpostguid}';
var mycandidatesguid='${param.mycandidatesguid}';
var auditionrecordguid='${param.auditionrecordguid}';
var candidatesstate='${param.candidatesstate}';
var resumeeamilguid='${param.resumeeamilguid}';
var auditiontime='${param.auditiontime}';
var recommendguid='${param.recommendguid}';
var recommend='${param.recommend}';
var arrangement='${param.arrangement}';
var advise='${param.advise}';
var entry='${param.entry}';
var filter='${param.filter}';

var pageState=false;

$(document).ready(function() {
	
	//tab页
	loadTab();
	
	
	//加载数据
	$("#detail").attr("src","resume/getResumeStaticById.do?id="+tid+"&mycandidatesguid="+mycandidatesguid+"&candidatesstate="+candidatesstate+"&auditionrecordguid="+auditionrecordguid+"&auditiontime="+auditiontime+"&recommend="+recommend+"&arrangement="+arrangement+"&advise="+advise+"&entry="+entry+"&recommendguid="+recommendguid+"&filter="+filter);

	//加载tab
	ShowTab(mycandidatesguid);
	
	
	//是否顯示郵箱
	if(resumeeamilguid!=null&&resumeeamilguid!=''&&resumeeamilguid!='null'){
		$("#resumeeamil").show();
	}
});




//tab页
var tabIndex=0;
function loadTab(){
	$("#mytab").tabs({
    	select: function(event, ui) {
    		tabIndex=ui.index;
    		//刷新
    		if(tabIndex==0){
    			$("#detail").attr("src","resume/getResumeStaticById.do?id="+tid+"&mycandidatesguid="+mycandidatesguid+"&candidatesstate="+candidatesstate+"&auditionrecordguid="+auditionrecordguid+"&auditiontime="+auditiontime+"&recommend="+recommend+"&arrangement="+arrangement+"&advise="+advise+"&entry="+entry+"&recommendguid="+recommendguid+"&filter="+filter);
    		}else if(tabIndex==1){
    			$("#detail").attr("src","resumeemail.do?page=static_theme&id="+resumeeamilguid);
    		}else if(tabIndex==2){
    			$("#detail").attr("src","mycandidates.do?page=form_history&mycandidatesguid="+mycandidatesguid);
    		}
    		
    		/*else if(tabIndex==2){
    			$("#detail").attr("src","resume.do?page=form_resumeassess&id="+tid);
    		}else if(tabIndex==3){
    			$("#detail").attr("src","auditionrecord.do?page=list_auditionrecord&mycandidatesguid="+mycandidatesguid);
    		}else if(tabIndex==4){
    			$("#detail").attr("src","examinationrecord.do?page=list&id="+mycandidatesguid);
    		}*/
    		
    	}
    });
	
	//$("#detail").attr("src","mycandidates.do?page=form_mycandidates&id="+mycandidatesguid);
	//$("#detail").attr("src","recruitpost.do?page=form_recruitpost&id="+recruitpostguid);
}



//切换视图
function convertView(url){
	//2013年9月10日修改的	
	window.parent.convertView(pageState?null:'');
	//window.parent.convertView('');
}





//页面保存状态
function callbackPageState(state){
	pageState=state;
}



//显示标签页
function ShowTab(mycandidatesguid){
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.getJSON("mycandidates/getMyCandidatesById.do", {id:mycandidatesguid}, function(data) {
			  if(data!=''&&data!=null){	
				//刷新已读
				if(!data.isreadtype){
					var array=new Array();
					array.push(mycandidatesguid);
					$.post("mycandidates/updateByReadtype.do",{ids:array.toString(),readtype:1}, function() {
						pageState=true;
				    });  
				}else{
					pageState=false;
				}
				  
				//面试
				/*if(data.candidatesstate>=5)
					$("#candidatesstate5").show();		
				//体检
				if(data.candidatesstate>=11)
					$("#candidatesstate11").show();*/
			}		
		});	
	}
	
}

</script>


</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>简历管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
	
	<div class="sort-cont sort-table">
		<div id="mytab">
			<ul>
				<li><a href="#tab0">华数简历</a></li>
				<li><a href="#tab0" id="resumeeamil" style="display:none;">原始简历</a></li>
				<li><a href="#tab0" id="mycandidateshistory">操作历史</a></li>
				<li><a href="#tab0" style="display:none;">简历评级</a></li>
				<li><a href="#tab0" id="candidatesstate5" style="display:none;">面试记录</a></li>
				<li><a href="#tab0" id="candidatesstate11" style="display:none;">体检记录</a></li>
			</ul>
			<div id="tab0">
				<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" scrolling="no" src="" ></iframe>
			</div>
		</div>
	</div>
</div>


</body>
</html>