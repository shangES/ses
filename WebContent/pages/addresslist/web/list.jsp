<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>通讯录管理</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="skins/css/style.css"/>
<link rel="stylesheet" type="text/css" href="pages/addresslist/css/web.css"/>

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
<script type="text/javascript" src="pages/addresslist/css/web.js"></script>
<script type="text/javascript" src="skins/js/jquery.layout.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-en.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="plugin/grid/gt_grid_all.js"></script>
<script type="text/javascript" src="plugin/grid/gt_msg_cn.js"></script>


<script type="text/javascript">

var companycode='${param.companycode}';
var deptcode='${param.deptcode}';
var name='${param.name}';


$(document).ready(function() {
	$('body').layout({
		
    });
	
	//加载数据
	loadGrid();
	
	//关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});






//上级窗口掉用
function reloadWebGrid(tcompanycode,tdeptcode,tname){
	$("#detail").removeAttr("src");
	$(".sort").show();
	
	companycode=tcompanycode;
	deptcode=tdeptcode;
	name=tname;
	mygrid.reload();
}





//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.userguid='${userid}';
	
	pam.companycode=companycode;
	pam.deptcode=deptcode;
	pam.name=name;
}





//切换视图
function convertView(url){
	if ($(".sort").css("display")!="none") {
		$(".sort").hide();
		$("#detail").show();
		
		$("#detail").attr("src",url);
	}else{
		$("#detail").removeAttr("src");
		$(".sort").show();
  	}
}






//定位部门
function makerTreePosation(treeid){
	window.parent.makerTreePosation(treeid);
}
</script>



<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=Math.round(($("body").outerHeight()-60)/35);
	
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'addresslistguid'},
				{name : 'employeeid'},
				{name : 'employeecode'},
				{name : 'companyid'},
				{name : 'companycode'},
				{name : 'deptid'},
				{name : 'deptcode'},
				{name : 'postid'},
				{name : 'postcode'},
				{name : 'jobnumber'},
				{name : 'name'},
				{name : 'sex'},
				{name : 'innerphone'},
				{name : 'extphone'},
				{name : 'mobilephone'},
				{name : 'email'},
				{name : 'officeaddress'},
				{name : 'refreshtimestamp'},
				{name : 'memo'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'}
			]
		};
		var colsOption = [
			{id: 'show' , header: "查看", width :40 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				var htm='<a href="javascript:convertView(\'address.do?page=form&id='+record.addresslistguid+'\');"><img src="skins/images/magnifier.png"/></a>';
				return htm;
			}},
			{id: 'postname' , header: "岗位名称", width :120 ,headAlign:'center',align:'left',hidden:true},
			{id: 'jobnumber' , header: "工号", width :110 ,headAlign:'center',align:'left'},
			{id: 'name' , header: "姓名", width :90 ,headAlign:'center',align:'left'},
			{id: 'innerphone' , header: "内网" , width :70 ,headAlign:'center',align:'left'},
			{id: 'extphone' , header: "分机", width :110 ,headAlign:'center',align:'left'},
			{id: 'mobilephone' , header: "手机", width :90 ,headAlign:'center',align:'left'},
			{id: 'mobilephone2' , header: "备用手机", width :90 ,headAlign:'center',align:'left'},
			{id: 'email' , header: "邮箱", width :160 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门", width :160 ,headAlign:'center',align:'left'},
			{id: 'companyname' , header: "公司" , width :200 ,headAlign:'center',align:'left'},
			{id: 'officeaddress' , header: "办公地址", width :220 ,headAlign:'center',align:'left',hidden:true}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'webaddresslist/searchWebAddressList.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		width:'99.8%',
		height:"99.8%",  //"100%", // 330,
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
		skin:'vista',
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			convertView('address.do?page=form&id='+record.addresslistguid);
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

</script>
</head>
<body>

<div class="ui-layout-center sort" style="overflow:hidden;">
	<div id="gridbox"></div>
</div>
<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>

</body>
</html>