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
<link rel="stylesheet" href="skins/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="skins/css/form.css" type="text/css" media="all" />
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

$(document).ready(function() {
	//加载信息
	loadGrid();
	
	//日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
  	
	//按钮hover
    $('.more a').hover(
		function() {$(this).addClass('ui-state-hover');}, 
		function() {$(this).removeClass('ui-state-hover');}
	);
	
  //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});







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
					mygrid.reload();
					$(this).dialog("close");
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








//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.isaudited=$("#isaudited").attr("checked")?1:'';
	pam.modtime_s=$("#modtime_s").val();
	pam.modtime_e=$("#modtime_e").val();
	pam.workplaceguid=$("#workplaceguid").val();
	pam.categoryguid=$("#categoryguid").val();
	pam.keyword=$("#keyword").val();
	pam.postname=$("#postname").val();
	pam.deptid=$("#deptid").val();
	pam.posttype=4;
}









//导出
function expGrid(){
	$("#dialog_exp").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:130,
		buttons: {
			"确定": function() {
				mygrid.exportGrid('xls');
				$(this).dialog("close");
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},
		close: function() {
		}
	});
}
















//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].recruitpostguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	$.post("recruitment/delRecruitPostById.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
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
			mygrid.reload();
  }
}





//竞聘此职位
function openRecruitWindow(id){
	if(${admin}){
		alert("管理员不可以竞聘岗位!");
		return;
	}
	
	if(!confirm('确认要竞聘此岗位吗？')){
		return;
	}
	
	$.post("recruitment/applyRecruitPostByUserId.do",{id:id}, function(webuserguid) {
		convertView('recruitpost.do?page=competition_resume&recruitpostguid='+id+"&id="+webuserguid);
    });
}




//推荐
function openRecommend(id){
	convertView('resume.do?page=recommend/form_2&recruitpostguid='+id);
}



</script>


<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'recruitpostguid'},
				{name : 'workplaceguid'},
				{name : 'categoryguid'},
				{name : 'deptid'},
				{name : 'functions'},
				{name : 'postname'},
				{name : 'keyword'},
				{name : 'postnum'},
				{name : 'workage'},
				{name : 'language'},
				{name : 'educational'},
				{name : 'validdate'},
				{name : 'traffic'},
				{name : 'collection'},
				{name : 'releasedate'},
				{name : 'pubuser'},
				{name : 'audituser'},
				{name : 'isaudited'},
				{name : 'modtime'},
				{name : 'rmk'},
				{name : 'dorder'},
				{name : 'totlecandidates'},
				{name : 'yesterdaycandidates'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'recruitpostguid' , header: "操作" , width :130,headAlign:'center',align:'center',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='';
				htm+='<a href="javascript:convertView(\'recruitpost.do?page=internal_tab&id='+record.recruitpostguid+'&recruitprogramguid='+record.recruitprogramguid+'\');">';
				htm+='查看';
				htm+='</a>';
				htm+='&nbsp;&nbsp;&nbsp;&nbsp;';
				htm+='<a href="javascript:openRecommend(\''+value+'\')">';
				htm+='推荐';
				htm+='</a>';
				htm+='&nbsp;&nbsp;&nbsp;&nbsp;';
				htm+='<a href="javascript:openRecruitWindow(\''+value+'\')">';
				htm+='竞聘';
				htm+='</a>';
			   return htm;
			  }},
			{id: 'companyname' , header: "公司" , width :180 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门" , width :150 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "职位名称" , width :250 ,headAlign:'center',align:'left'},
			{id: 'functions' , header: "岗位职能" , width :120 ,headAlign:'center',align:'left'},
			{id: 'posttypename' , header: "招聘类别" , width :100 ,headAlign:'center',align:'center'},
			{id: 'postnum' , header: "招聘人数" , width :80 ,headAlign:'center',align:'center',number:true},
			{id: 'workplacename' , header: "工作地点" , width :100 ,headAlign:'center',align:'center'},
			{id: 'workagename' , header: "工作年限" , width :100 ,headAlign:'center',align:'center'},
			{id: 'languagename' , header: "语言要求" , width :100 ,headAlign:'center',align:'center'},
			{id: 'educationalname' , header: "学历要求" , width :100 ,headAlign:'center',align:'center'},
			{id: 'keyword' , header: "关键字" , width :150 ,headAlign:'center'},
			{id: 'pubuser' , header: "发布人" , width :100 ,headAlign:'center'},
			{id: 'releasedate' , header: "发布时间" , width :90,headAlign:'center',align:'center'},
			{id: 'audituser' , header: "审核人" , width :100,headAlign:'center'},
			{id: 'validdate' , header: "失效时间" , width :90,headAlign:'center',align:'center'},
			{id: 'rmk' , header: "备注" , width :300 ,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'recruitment/searchInternalPost.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'recruitment/searchInternalPost.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '内部职位表.xls',
		width: "99.8%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
		container : 'gridbox', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:true,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('recruitpost.do?page=internal_tab&id='+record.recruitpostguid+'&recruitprogramguid='+record.recruitprogramguid);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

</script>
</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>内部职位列表</h3>
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
					<a class="btn" href="javascript:expGrid();"><i class="icon icon-download" ></i><span>导出</span></a>
					<a class="btn" href="javascript:expGrid();"><i class="icon icon-download" ></i><span>内部推荐</span></a>
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
<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>


<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="" id="searchForm" class="form">
		<ul>
			<li>
			    <span>工作地点：</span>
			    <input id="workplaceguid" name="workplaceguid" type="hidden" value=""/>
			    <input id="workplacename" name="workplacename" class="inputselectstyle" onclick="chooseWorkplaceTree('#workplaceguid','#workplacename');"/>
			    <div class="select-trigger" onclick="chooseWorkplaceTree('#workplaceguid','#workplacename');"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>职位类别：</span>
			    <input id="categoryguid" name="categoryguid" type="hidden" value=""/>
			    <input id="categoryname" name="categoryname" class="inputselectstyle" onclick="chooseMyMultipleCategoryTree('#categoryguid','#categoryname');"/>
			    <div class="select-trigger" onclick="chooseMyMultipleCategoryTree('#categoryguid','#categoryname');"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>部门名称：</span>
			    <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseMyDeptTree('#deptid','#deptname');"/>
			    <div class="select-trigger" onclick="chooseMyDeptTree('#deptid','#deptname');"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>职位名称：</span>
			    <input id="postname" name="postname" class="inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
                <span>发布日期从：</span>
                <input id="modtime_s" name="modtime_s" class="inputselectstyle datepicker"/>
               <div class="date-trigger" onclick="$('#modtime_s').focus();"/>
            </li>
        </ul>
        <ul>
			<li>
                <span>至：</span>
				<input id="modtime_e" name="modtime_e" class="inputselectstyle datepicker"/>
				<div class="date-trigger" onclick="$('#modtime_e').focus();"/>
            </li>
        </ul>
	</form>
</div>


<!-- 导出 -->
<div id="dialog_exp" style="display:none;" title="数据导出" >
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				&nbsp;
			  	&nbsp;
				&nbsp;
			  	&nbsp;
				<label for="xls1">
					<input id="xls1" type="radio" name="xls" value="0" checked="true" class="checkboxstyle"/>导出本页
				</label>
			  	&nbsp;
			  	&nbsp;
			  	<label for="xls2">
			  		<input id="xls2" type="radio" name="xls" value="1" class="checkboxstyle"/>全部导出
			  	</label>
			</td>
		</tr>
	</table>
</div>


</body>
</html>
