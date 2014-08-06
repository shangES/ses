<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>工龄报表</title>
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
<link rel="stylesheet" type="text/css" href="plugin/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/default/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/green/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/mac/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/china/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/vista/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/calendar/calendar-blue.css"  />


<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.layout.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>

<script type="text/javascript" src="plugin/grid/calendar/calendar.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-en.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="plugin/grid/gt_grid_all.js"></script>
<script type="text/javascript" src="plugin/grid/gt_msg_cn.js"></script>

<script type="text/javascript" src="pages/tree.js"></script>

<script type="text/javascript">
$(document).ready(function () {
	
    //列表加载
    loadGrid();
    searchGrid();
    
    //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd'
    });
    
    
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});




//导出
function expGrid(){
	mygrid.exportGrid('xls');
}

//打印
function printGrid(){
	mygrid.printGrid();
}




//搜索
var searchForm=null;
function searchGrid(){
	$("#search").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		buttons: {
			"确定":function(){
				if(searchForm.form()){
					$(this).dialog("close");
		    		mygrid.loadURL="report/searchPCD0111.do";
		    		mygrid.reload();
				}
			},
			"重置":function(){
				$("#searchForm").clearForm();
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//校验
			if(searchForm==null)
				searchForm=$("#searchForm").validate();
		}
	});
}



//部门选择回调
function callbackDept(){
	$("#postid").val(null);
	$("#postname").val(null);
}







//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.userguid='${userid}';
	pam.companyid=$("#companyid").val();
	pam.deptid=$("#deptid").val();
	pam.sex=$("#sex").val();
	pam.culture=$("#culture").val();
	pam.politics=$("#politics").val();
	pam.workstate=$("#workstate").val();
	pam.classification=$("#classification").val();
	pam.employtype=$("#employtype").val();
}





</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'COMPANYNAME'},
				{name : 'DEPTNAME'},
				{name : 'POSTNAME'},
				{name : 'JOBNUMBER'},
				{name : 'NAME'},
				{name : 'JOINDATE'},
				{name : 'JOINGROUPDATE'},
				{name : 'GROUPWORKAGE'},
				{name : 'JOINWORKDATE'},
				{name : 'WORKAGE'}
			]
		};
		var colsOption = [
			{id: 'COMPANYNAME' , header: "公司名称" , width :200 ,headAlign:'center',align:'left'},
			{id: 'DEPTNAME' , header: "部门名称" , width :150 ,headAlign:'center',align:'left'},
			{id: 'POSTNAME' , header: "职位" , width :120 ,headAlign:'center',align:'left'},
			{id: 'JOBNUMBER' , header: "工号" , width :120 ,headAlign:'center',align:'left'},
			{id: 'NAME' , header: "姓名" , width :80 ,headAlign:'center',align:'left'},
			{id: 'JOINDATE' , header: "入职时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'JOINGROUPDATE' , header: "加入公司时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'GROUPWORKAGE' , header: "集团工龄" , width :90 ,headAlign:'center',align:'center'},
			{id: 'WORKAGE' , header: "工作工龄" , width :90 ,headAlign:'center',align:'center'},
			{id: 'JOINWORKDATE' , header: "参加工作时间" , width :90 ,headAlign:'center',align:'center'}
		];
	var gridOption={
		id : grid_demo_id,
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'report/searchPCD0111.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		exportFileName : '工龄报表.xls',
		width: "99.8%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
		container : 'gridbox', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:true,
		selectRowByCheck:true,
		replaceContainer : true,
		showGridMenu : true,
		allowFreeze	: true ,
		allowHide	: true ,
		allowGroup	: true ,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSize:size,
		skin:getGridSkin()
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

</script>

</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>工龄报表</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
	
	<div class="sort-cont sort-table">
		<div class="table">
			<div class="table-bar">
				<div class="table-title">
					表格名称
				</div>
				<div class="table-ctrl">
					<a class="btn" href="javascript:searchGrid();"><i class="icon icon-search"></i><span>搜索</span></a>
					<a class="btn" href="javascript:expGrid();"><i class="icon icon-list-alt"></i><span>导出</span></a>
					<a class="btn" href="javascript:printGrid();"><i class="icon icon-print"></i><span>打印</span></a>
				</div>
			</div>
			<div class="table-wrapper">
				<div id="myTable" style="height:550px;margin:5px auto;">
					<div id="gridbox" ></div>
                </div>
			</div>
		</div>
	</div>
</div>



<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="" id="searchForm" class="form">
		<ul>
			<li>
                <span>部门名称：</span>
                <input id="companyid" name="companyid" type="hidden" value=""/>
                <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseMyMultipleDeptTree('#deptid','#deptname');"/>
			    <div class="search-trigger" onclick="chooseMyMultipleDeptTree('#deptid','#deptname');"/>
            </li>
        </ul>
        <ul>
			<li>
				<span>员工状态：</span>
                <input id="workstate" name="workstate" type="hidden" value=""/>
			    <input id="workstatename" name="workstatename" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#workstate','#workstatename','WORKSTATE');"/>
			    <div class="select-trigger" onclick="chooseMyMultipleOptionTree('#workstate','#workstatename','WORKSTATE');"/>
            </li>
        </ul>
        <ul>
			<li>
			    <span>员工类别：</span>
			    <input id="classification" name="classification" type="hidden" value=""/>
			    <input id="classificationname" name="classificationname" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#classification','#classificationname','CLASSIFICATION');"/>
			    <div class="select-trigger" onclick="chooseMyMultipleOptionTree('#classification','#classificationname','CLASSIFICATION');"/>
			</li>
		</ul>
	   <ul>
			<li>
			    <span>用工性质：</span>
			    <input id="employtype" name="employtype" type="hidden" value=""/>
			    <input id="employtypename" name="employtypename" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#employtype','#employtypename','EMPLOYTYPE');"/>
			    <div class="select-trigger" onclick="chooseMyMultipleOptionTree('#employtype','#employtypename','EMPLOYTYPE');"/>
			</li>
        </ul>
        <ul>
			<li>
			    <span>性别：</span>
			    <input id="sex" name="sex" type="hidden" value=""/>
			    <input id="sexname" name="sexname" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#sex','#sexname','SEX');"/>
			    <div class="select-trigger" onclick="chooseMyMultipleOptionTree('#sex','#sexname','SEX');"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>文化程度：</span>
			    <input id="culture" name="culture" type="hidden" value=""/>
			    <input id="culturename" name="culturename" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#culture','#culturename','CULTURE');"/>
			    <div class="select-trigger" onclick="chooseMyMultipleOptionTree('#culture','#culturename','CULTURE');"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>政治面貌：</span>
			    <input id="politics" name="politics" type="hidden" value=""/>
			    <input id="politicsname" name="politicsname" class="inputselectstyle" onclick="chooseMyMultipleOptionTree('#politics','#politicsname','POLITICS');"/>
			    <div class="select-trigger" onclick="chooseMyMultipleOptionTree('#politics','#politicsname','POLITICS');"/>
			</li>
		</ul>
        
	</form>
</div>









</body>
</html>