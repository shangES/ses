<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>修改面试官</title>
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
<script type="text/javascript" src="plugin/timepicker/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript" src="plugin/grid/calendar/calendar.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-en.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="plugin/grid/gt_grid_all.js"></script>
<script type="text/javascript" src="plugin/grid/gt_msg_cn.js"></script>

<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="pages/mycandidates/tree_grid.js"></script>


<script type="text/javascript">
$(document).ready(function() {
	//加载布局
	$('body').layout({
        north: {size:"35",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
	//列表加载
    loadGrid();
    
});



//保存表格
function saveGrid(){
	var arry=new Array();
	mygrid.forEachRow(function(row,record,i,grid){
		arry.push(record);
	});
	mygrid.setInsertedRecords(arry);
	mygrid.save(true);
}







//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.name=$("#resumename").val();
}


//返回
function back(){
	window.parent.convertView('');
}




//搜索
function onMyKeyUp(){
	mygrid.reload();
}



//切换视图
function convertView(url){
	if ($(".sort").css("display")!="none") {
		$(".sort").hide();
		$("#detail").show();
		
		$("#detail").attr("src",url);
		$("#myBody").css({'overflow-y':'auto'});
	}else{
		$("#detail").height(0);
		$("#detail").removeAttr("src");
		$("#myBody").css({'overflow':'hidden'});
		$(".sort").show();
		
		//计算高度
		_autoHeight();
		if(url==null)
			mygrid.reload();
  }
}

</script>

<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'mycandidatesguid'},
				{name : 'recruitpostguid'},
				{name : 'webuserguid'},
				{name : 'candidatesstate'},
				{name : 'userguid'},
				{name : 'progress'},
				{name : 'candidatestime'},
				{name : 'matchuser'},
				{name : 'matchtime'},
				{name : 'recommendpostguid'},
				{name : 'matchmemo'},
				{name : 'holduser'},
				{name : 'holdtime'},
				{name : 'holdmemo'},
				{name : 'auditiontime'},
				{name : 'candidatestype'},
				{name : 'mark'},
				{name : 'keyword'},
				{name : 'name'},
				{name : 'sex'},
				{name : 'birthday'},
				{name : 'mobile'},
				{name : 'email'},
				{name : 'homeplace'},
				{name : 'workage'},
				{name : 'culture'},
				{name : 'photo'},
				{name : 'modtime'},
				{name : 'rmk'},
				{name : 'resumetypename'},
				{name : 'postname'},
				{name : 'companyname'},
				{name : 'deptid'},
				{name : 'deptname'},
				{name : 'workplacename'},
				{name : 'birthdayname'},
				{name : 'workagename'},
				{name : 'culturename'},
				{name : 'candidatesstatename'},
				{name : 'maininterviewerguid'},
				{name : 'maininterviewerguidname'},
				{name : 'auditionaddress'},
				{name : 'auditiondate'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'},
				{name : 'userguid'},
				{name : 'username'},
				{name : 'resumeeamilguid'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center',frozen:true},
			{id: 'name' , header: "应聘者" , width :80 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "申请职位" , width :120 ,headAlign:'center',align:'left'},
			{id: 'auditiondate' , header: "面试时间" , width :150 ,headAlign:'center',align:'center'},
			{id: 'maininterviewerguidname' , header: "面试官" , width :80 ,headAlign:'center',align:'left',editor:{type :"text",onClick:chooseinterviewerGuidTree,onKeyPress:loadGridinterviewerTreeData}},
			{id: 'auditionaddress' , header: "面试地点" , width :250 ,headAlign:'center',headAlign:'left',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		saveURL :'mycandidates/saveAuditionMainInterviewer.do',
		loadURL :'mycandidates/searchMycandidatesByMainInterviewerList.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		width: "99.5%",//"100%", // 700,
		height: "98.5%",  //"100%", // 330,
		container : 'gridbox', 
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:false,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : null,
		pageSize:1000,
		skin:getGridSkin(),
		saveResponseHandler:function(){
			//加載數據
			window.parent.mygrid.reload();
		},
		beforeEdit:function(val,record,colunm,grid){
			$('.gt-editor-text').val(null);
			$('.gt-editor-container').remove();
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}




//时间
function showTime(){
	$(".gt-editor-text").datetimepicker({
		dateFormat:'yy-mm-dd',
		timeFormat: 'hh:mm:ss',
		onClose:function(dateText, inst){
			$(".gt-editor-text").datepicker("destroy") ;
			
			//时间置值
			mygrid.forEachRow(function(row,record,i,grid){
				if(record.auditiondate==null)
					record.auditiondate=dateText;
			});
			mygrid.refresh();
			
		}
	}).show();
}



</script>

</head>
<body id="myBody" style="height:500px;">

<div class="ui-layout-north toolbar sort" style="border:0px;">
	<div class="table-bar" style="padding:0px;">
		<div class="table-title">
			<ul>
				<li style="padding:0px;">
	                <span style="width:75px;">应聘者姓名：</span>
	                <input id="resumename" name="resumename" class="inputstyle" style="width:100px;" onkeyup="onMyKeyUp();"/>
	            </li>
			</ul>
		</div>
		<div class="table-ctrl">
			<a class="btn" href="javascript:saveGrid();"><i class="icon icon-check"></i><span>确定</span></a>
		</div>
	</div>
</div>
<div class="ui-layout-center sort" style="overflow:hidden;border:0px;">
	<div id="gridbox" ></div>
</div>
<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>

</body>
</html>