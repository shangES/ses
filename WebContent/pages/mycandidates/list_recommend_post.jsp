<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>推荐信息</title>
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
//覆盖计算高度
function _autoHeight(){}

$(document).ready(function() {
	//加载布局
	$('body').layout({
        north: {size:"35",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
	 //列表加载
    loadGrid();
	 
    //window.parent.parent.goCenter();
  
});



//保存表格
function saveGrid(){
	mygrid.save(true);
}







//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
}


//返回
function back(){
	window.parent.convertView('');
}




//是否发送邮件和短信
function checkisEmail(){
	var state=$(el).attr("checked");
	$(el).val(state?1:0);
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
				{name : 'recommendguid'},
				{name : 'state'},
				{name : 'recommendcompanyid'},
				{name : 'recommenddeptid'},
				{name : 'recommendcompanyname'},
				{name : 'recommenddeptname'},
				{name : 'username'},
				{name : 'recommendpostguid'},
				{name : 'recommendpostname'},
				{name : 'userguid'},
				{name : 'auditiontime'},
				{name : 'moditimestamp'},
				{name : 'modimemo'}
			]
		};
		var colsOption = [
			//{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center',frozen:true,sortable:false},
			{id: 'recommendcompanyname' , header: "推荐公司" , width :180 ,headAlign:'center',align:'left',sortable:false},
			{id: 'recommenddeptname' , header: "推荐部门" , width :150 ,headAlign:'center',align:'left',sortable:false},
			{id: 'username' , header: "推荐到" , width :100 ,headAlign:'center',align:'left',editor:{type :"text",onClick:chooseUserGuidTree,onKeyPress:loadGridUserTreeData},sortable:false},
			{id: 'recommendpostname' , header: "推荐岗位" , width :100 ,headAlign:'center',align:'left',editor:{type :"text",onClick:choosePostGuidTree,onKeyPress:loadGridPostTreeData},sortable:false},
			{id: 'modimemo' , header: "备注" , width :250 ,headAlign:'center',headAlign:'left',align:'left',editor:{type :"text"},sortable:false}
		];
		
	var gridOption={
		id : grid_demo_id,
		saveURL :'mycandidates/saveRcommendGrid.do',
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
		onComplete:function(grid){
			grid.add();
			grid.add();
			grid.add();
			grid.add();
			grid.add();
		},
		beforeSave:function(reqParam){
			var isemail=$("#isemail").attr("checked")?1:0;
			var ismsg=$("#ismsg").attr("checked")?1:0;
			var pam=({isemail:isemail,ismsg:ismsg});
			reqParam['parameters']=pam;
		},
		saveResponseHandler:function(){
			//加載數據
			window.parent.mygrid.reload();
		},
		defaultRecord:{state:1,mycandidatesguid:'${param.mycandidatesguid}',moditimestamp:getCurentDateYMDHMS},
		beforeEdit:function(val,record,colunm,grid){
			$('.gt-editor-text').val(null);
			$('.gt-editor-container').remove();
		}
		
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}








</script>

</head>
<body id="myBody" style="height:500px;">

<div class="ui-layout-north toolbar sort" style="border:0px;">
	<div class="table-bar" style="padding:0px;">
		<div class="table-title">
			表格名称
		</div>
		<div class="table-ctrl">
			<span id="b4">
				<div id="checkbox" style="float:left;">
					<label for="isemail">
						<input id="isemail" name="isemail" type="checkbox" class="checkboxstyle"  onclick="checkisEmail" checked="true"/>是否发送邮件
					</label>
					<label for="ismsg">
						<input id="ismsg" name="ismsg" type="checkbox" class="checkboxstyle" onclick="checkisEmail" checked="true"/>是否发送短信
					</label>
					&nbsp;
					&nbsp;
				</div>
			</span>
			<a class="btn" href="javascript:mygrid.add();"><i class="icon icon-plus"></i><span>添加</span></a>
			<a class="btn" href="javascript:saveGrid();"><i class="icon icon-check"></i><span>保存</span></a>
		</div>
	</div>
</div>
<div class="ui-layout-center sort" style="overflow:hidden;border:0px;">
	<div id="gridbox" ></div>
</div>
<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>

</body>
</html>