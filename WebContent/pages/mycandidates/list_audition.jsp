<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>安排面试</title>
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
    
  
    //日期
    /*$(".timepicker").datetimepicker({
		dateFormat:'yy-mm-dd',
		timeFormat: 'hh:mm:ss',
	});*/
  
  	
    //关闭等待层
    //if(window.parent.hidenLoading)
    	//window.parent.hidenLoading();
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
}


//返回
function back(){
	window.parent.convertView('');
}


//批量查看
function showResume(){
	var array=new Array();
	var array_name=new Array();
	var array_mycandidatesguid = new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].webuserguid);
		array_name.push(cords[i].name);
		array_mycandidatesguid.push(cords[i].mycandidatesguid)
	}
	
	if(array.length<=0){
		alert('请选择要查看的数据！');
		return;
	}
	
	var ids = array.toString();
	var names = array_name.toString();
	var mycandidatesguids = array_mycandidatesguid.toString();
	if(ids!=null&&ids!=""&&ids!="null"){
		var url='${baseUrl }mycandidates.do?page=list_sum&ids='+ids+'&names='+encodeURI(names)+'&mycandidatesguids='+mycandidatesguids;
		convertView(url);
	}
	
}


//是否发送邮件和短信
function checkisEmail(el){
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
				{name : 'resumeeamilguid'},
				{name : 'recommendguid'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center',frozen:true},
			{id: 'name' , header: "应聘者" , width :80 ,headAlign:'center',align:'left',editor:{type :"text"}},
			{id: 'postname' , header: "申请职位" , width :100 ,headAlign:'center',align:'left'},
			{id: 'auditiontime' , header: "面试时间" , width :150 ,headAlign:'center',align:'center',editor: {type :"text",onClick:showTime}},
			{id: 'maininterviewerguidname' , header: "面试官" , width :80 ,headAlign:'center',align:'left',editor:{type :"text",onClick:chooseinterviewerGuidTree,onKeyPress:loadGridinterviewerTreeData}},
			{id: 'auditionaddress' , header: "面试地点" , width :250 ,headAlign:'center',headAlign:'left',align:'left',editor:{type :"text",onClick:chooseAddressGridTree}},
			{id: 'recommendcompanyname' , header: "推荐公司" , width :150,headAlign:'center',align:'left'},
  			{id: 'recommenddeptname' , header: "推荐部门" , width :100,headAlign:'center',align:'left'},
  			{id: 'recommendpostname' , header: "推荐岗位" , width :100,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		saveURL :'mycandidates/saveAuditionGrid.do',
		loadURL :'mycandidates/searchMycandidatesByAudition.do',
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
		beforeSave:function(reqParam){
			var isemail=$("#isemail").attr("checked")?1:0;
			var ismsg=$("#ismsg").attr("checked")?1:0;
			var pam=({isemail:isemail,ismsg:ismsg});
			reqParam['parameters']=pam;
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
		}
	}).show();
}



//地点置值
/*function showResume(webuserguid,recruitpostguid,mycandidatesguid,resumeeamilguid){
	var url='${baseUrl }mycandidates.do?page=form&id='+webuserguid+'&recruitpostguid='+recruitpostguid+'&mycandidatesguid='+mycandidatesguid+'&resumeeamilguid='+resumeeamilguid;
	convertView(url);
}*/


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
						<input id="isemail" name="isemail" type="checkbox" class="checkboxstyle"  onclick="checkisEmail(this)" checked="true"/>是否发送邮件
					</label>
					<label for="ismsg">
						<input id="ismsg" name="ismsg" type="checkbox" class="checkboxstyle" onclick="checkisEmail(this)" checked="true"/>是否发送短信
					</label>
					&nbsp;
					&nbsp;
				</div>
			</span>
			<a class="btn" href="javascript:showResume();"><i class="icon icon-eye-open"></i><span>查看</span></a>
			<a class="btn" href="javascript:saveGrid();"><i class="icon icon-check"></i><span>提交</span></a>
		</div>
	</div>
</div>
<div class="ui-layout-center sort" style="overflow:hidden;border:0px;">
	<div id="gridbox" ></div>
</div>
<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>

</body>
</html>