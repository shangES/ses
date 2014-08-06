<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>选择招聘专员</title>
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
<script type="text/javascript" src="pages/resume/recommend/tree.js"></script>

<script type="text/javascript">
$(document).ready(function () {
	//加载布局
	$('body').layout({
        north: {size:"35",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
    //列表加载
    loadGrid();
    
    
});



//部门选择回调
function callbackDept(){
	/*$("#postid").val(null);
	$("#postname").val(null);*/
	
	mygrid.reload();
}


//招聘专员选择回调
function callbackRecruiter(){
	mygrid.reload();
}



//搜索
function onMyKeyUp(){
	mygrid.reload();
}





//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.userguid=$("#userguid").val();
	//pam.deptid=$("#deptid").val();
	//pam.companyid=$("#companyid").val();
	pam.postname=$("#postname").val();
}





</script>
<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=10;
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
				{name : 'dorder'}
			]
		};
		var colsOption = [
			{id: 'recruitpostguid' , header: "选择" , width :40 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				return '<input name="myradio" type="radio" onclick="callbackChooseRecruiter(\''+value+'\',\''+record.postname+'\')" />';
			}},
			{id: 'companyname' , header: "公司名称" , width :200 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :150 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "职位名称" , width :180 ,headAlign:'center',align:'left'},
			{id: 'postnum' , header: "招聘人数" , width :80,headAlign:'center',align:'center'},
			{id: 'workagename' , header: "工作年限" , width :100 ,headAlign:'center',align:'center'},
			{id: 'languagename' , header: "语言要求" , width :100 ,headAlign:'center',align:'center'},
			{id: 'educationalname' , header: "学历要求" , width :100 ,headAlign:'center',align:'center'},
			{id: 'rmk' , header: "备注" , width :200 ,headAlign:'center',headAlign:'left',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'recruitment/searchRecruitPost.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		width: "99.5%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
		container : 'gridbox',
		autoLoad:false,
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:false,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		toolbarContent : 'nav | pagesize  state',
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
		},

	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

//选择回调
function callbackChooseRecruiter(id,postname){
	var userguid=$("#userguid").val();
	var username=$("#username").val();
	window.parent.callbackChooseRecruiter(id,userguid,username,postname);
}

</script>

</head>
<body >
<div class="ui-layout-north" style="border:0px;">
	<div  class="form">
		<ul>
			<li style="padding:0px;display:none" >
			    <span style="width:70px;">部门名称：</span>
			    <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);" style="width:90px;"/>
			    <div class="search-trigger" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
			</li>
			
			<li style="padding:0px;">
                <span style="width:75px;">招聘专员：</span>
                <input id="userguid" name="userguid" type="hidden" value=""/>
			    <input id="username" name="username" class="inputselectstyle" onclick="chooseRecruiterTree('#userguid','#username',callbackRecruiter);"/>
			    <div class="search-trigger" onclick="chooseRecruiterTree('#userguid','#username',callbackRecruiter);" />
            </li>
             <li style="padding:0px">
                 <span style="width: 80px;">职务名称：</span>
                 <input id="postname" name="postname" class="inputstyle" onkeyup="onMyKeyUp();"/>
             </li>
		</ul>
	</div>
</div>
	
<div class="ui-layout-center" style="overflow:hidden;border:0px;">
	<div id="gridbox" ></div>
</div>



</body>
</html>