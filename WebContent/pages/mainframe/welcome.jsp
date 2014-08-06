<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<!DOCTYPE html>
<head>
<title>华数人力资源管理系统</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<link rel="stylesheet" type="text/css" href="skins/css/frame.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/style.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>

<style type="text/css">
.column { float: left; padding-bottom: 10px;display:inline;width:50%;}
.portlet { margin: 0 5px 10px 5px;border:1px solid #ddd; }
.portlet-header {padding-left:10px; border:0px;height:30px;line-height:30px;}
.portlet-header .ui-icon { float: right;cursor: pointer; }
.portlet-content { padding: 0.2em; }
.ui-sortable-placeholder { border: 1px dotted black; visibility: visible !important; height: 300px !important; }
.ui-sortable-placeholder * { visibility: hidden; }

table.static-table {width: 100%;table-layout: fixed;border-top:1px solid #E8E8E8;border-right:1px solid #E8E8E8;}
table.static-table tr:HOVER {background:#f8f8f8;}
table.static-table th {background:#f8f8f8;border-left:1px solid #E8E8E8;border-bottom:1px solid #E8E8E8;height: 30px;font-weight:normal;overflow: hidden;white-space: nowrap;word-break: keep-all;word-wrap: normal;text-overflow: ellipsis;-o-text-overflow: ellipsis;}
table.static-table td {border-left:1px solid #E8E8E8;border-bottom:1px solid #E8E8E8;qdisplay: block;height: 25px;line-height: 25px;padding:0px 5px;overflow: hidden;white-space: nowrap;word-break: keep-all;word-wrap: normal;text-overflow: ellipsis;-o-text-overflow: ellipsis;}
table.static-table .index {background:#f8f8f8;}
</style>

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>

<script type="text/javascript" src="pages/mainframe/welcome.js"></script>

<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="skins/js/AnyChart.js"></script>

<script type="text/javascript">
var userid="${userid}";
var companyid="${companyid}";
$(document).ready(function () {
	
    pageReload();
	
	$(".portlet").addClass("ui-corner-all")
		.find(".portlet-header")
		.addClass( "ui-widget-header ui-corner-all" )
		.prepend( "<span class='ui-icon ui-icon-carat-1-s'></span>")
		.end();

	
	$(".portlet-header .ui-icon").click(function() {
		$(this).toggleClass("ui-icon-carat-1-s").toggleClass("ui-icon-carat-1-n");
		$(this).parents(".portlet:first").find(".portlet-content").toggle();
	});
	
	  //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd'
    });
  
	
	 //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});



function pageReload(){
	if(${recruitprogram==true}){
		//招聘计划审批
		$("#recruitprogram").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getRecruitprogramTodo();
	}
	
	if(${affirm==true}){
		//待认定的简历
		$("#affirm").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getAffirmMyCandidatesTodo();
	}
	
	if(${interview==true}){
		//待安排的面试
		$("#interview").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getInterviewTodo();
	}
	
	if(${audition==true}){
		//面试人员列表
		$("#audition").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getAuditionTodo();
	}
	
	if(${result==true}){
		//面试结果的反馈
		$("#result").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getAffirmAuditionResultsTodo();
	}
	
	if(${examination==true}){
		//待安排的体检
		$("#examination").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getExaminationTodo();
	}
	
	if(${entryOk==true}){
		//待入职的应聘者
		$("#entryOk").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getEntryOkTodo();
	}
	
	
	if(${entry==true}){
		//待确定入职的员工
		$("#entry").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getEntryTodo();
	}
	
	//异动待生效的员工
	if(${employeeposation==true}){
		$("#employeePosation").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getEmployeePosationTodo();
	}
	
	
	//待转正的员工
	if(${employeezhuzheng==true}){
		$("#employeezhuzheng").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getEmployeeZhuZhengTodo();
	}
		
	
	//合同将要到期的员工
	if(${employeecontract==true}){
		$("#employeeContract").html('<img src="skins/images/ajax-loader-small.gif"/>');
		getEmployeeContractTodo();
	}
	
	// 我的应聘状态统计图
	if(${mycandidateschart==true}){
		$("#mycandidatesstate").html('<img src="skins/images/ajax-loader-small.gif"/>');
		loadMyCandidatesChart();
	}
	
	
	//来源渠道统计表
	if(${mycandidatesTypechart==true}){
		$("#mycandidatestype").html('<img src="skins/images/ajax-loader-small.gif"/>');
		loadMyCandidatesTypeChart();
	}
	
	

	//体系编制情况统计图表
	if(${bzqkchart==true}){
		loadBZQKChart(null);
	}
	
	
	//当日投递情况
	if(${deliver==true}){
		getDeliverTodo();
	}
}


//切换视图
function convertView(url){
	if ($(".sort").css("display")!="none") {
		$(".sort").hide();
		$("#detail").show();
		
		$("#detail").attr("src",url);
	}else{
		$("#detail").height(0);
		$("#detail").removeAttr("src");
		$(".sort").show();
		
		//计算高度
		_autoHeight();
		if(url==null)
			pageReload();
  }
}




</script>


</head>
<body>
<div class="sort">
	<div class="sort-title">
		<h3>首页</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
	
	<div class="sort-cont sort-table">
		<div class="table" style="padding-top:10px;">
			<div class="table-wrapper">
			    <div id="mycandidatesstate">
					
				</div>
				
				<div id="myBZQKChart">
				<c:if test="${bzqkchart==true}">
					<div class="portlet">
						<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>
							<select onchange = "changeChart(this)" style="height:80%;text-algin:center;font-weight:bold;">
								<option value="1" id="bztjt">体系编制情况统计图</option>
								<option value="2" id="requirt">招聘计划统计图</option>
								</select>
							</div>
						<div id="BZQKContent"></div>
					</div>
					</c:if>
				</div>
				
				<div class="column" id="column1">
					<div id="deliver">
						
					</div>
				
					<div id="recruitprogram">
					
					</div>
					
					<div id="affirm">
					
					</div>
					
					<div id="interview">
					
					</div>
					<div id="audition">
					
					</div>
					
					<div id="employeePosation">
					
					</div>
				
					<div id="employeezhuzheng">
					
					</div>
				
				</div>
				<div class="column" id="column2">
					 <div id="mycandidatestype">
					
					</div>
					
					<div id="result">
					
					</div>
					
					<div id="examination">
					
					</div>
					
					
					<div id="entryOk">
					
					</div>
					
					<div id="entry">
					
					</div>
							
					<div id="employeeContract">
					
					</div>
				    
				</div>
				
			</div>
		</div>
	</div>
</div>
<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>

</body>
</html>