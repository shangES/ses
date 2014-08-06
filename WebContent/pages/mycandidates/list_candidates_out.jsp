<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>面试人员列表</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link href="skins/css/frame.css" type="text/css" rel="stylesheet">
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

<script type="text/javascript">

function _autoHeight(){
	
} 

$(document).ready(function () {
	
    //列表加载
    loadGrid();
    
    
  //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd'
    });
  
    //日期
    $(".timepicker").datetimepicker({
		dateFormat:'yy-mm-dd',
		timeFormat: 'hh:mm:ss',
		beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
	});
  
  	
    
    
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});



//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.name=$("#name").val();
	var today=getCurentDateYMD();
	if($('#today').attr('checked')){
		pam.today=1;
		pam.stoday=today+" 01:00:00";
		pam.etoday=today+" 23:59:59";
	}
	
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
</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'mycandidatesguid'},
				{name : 'recruitpostguid'},
				{name : 'resumeguid'},
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
				{name : 'employeemobile'},
				{name : 'employeeemail'}
				
				
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'name' , header: "应聘者" , width :330 ,headAlign:'center',align:'left',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='&nbsp;';
				htm+=value+'('+record.mobile+','+record.email+')';
				htm+='&nbsp;';
				htm+='&nbsp;';
				return htm;
			}},
			{id: 'maininterviewerguidname' , header: "面试官" , width :330 ,headAlign:'center',align:'left',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='&nbsp;';
				    htm+=value+'(';
				if(record.employeemobile != null && record.employeemobile != "null"){
					htm+=record.employeemobile;
				}
				    htm+=',';
				if(record.employeeemail != null && record.employeeemail != "null"){
				    htm+=record.employeeemail;
				}
				    htm+=')';
				htm+='&nbsp;';
				htm+='&nbsp;';
				return htm;
			}},
			{id: 'modiuser', header: "招聘专员", width :80 ,headAlign:'center',align:'left'},
			{id: 'auditiondate', header: "面试时间", width :150 ,headAlign:'center',align:'center'},
			{id: 'auditionaddress' , header: "面试地址", width :250 ,headAlign:'center',align:'left'}
		];
	var gridOption={
		id : grid_demo_id,
		loadURL :'mycandidates/searchCandidatesPerson.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
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
		loadResponseHandler:function(response,requestParameter){
			var obj = jQuery.parseJSON(response.text);
			var page=obj.pageInfo;
			var hg=(page.pageSize+1)*33+50;
			
			mygrid.setSize('99.9%',hg);
			pageResize(hg);
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}


function pageResize(height){
	$("#myTable").height(height);
	//计算高度
	_autoHeight();
}

</script>

</head>
<body style="overflow:auto;">



<!--Header-->
<div id="Header">
	<div class="header-main">
	</div>
	
	<div class="header-bar">
		<ul style="line-height:30px">
			<li>
				<div class="chout">
					<a href="index.do" style="color:#fff;"><img src="skins/images/shield.png"/>系统首页</a>
					&nbsp;
					&nbsp;
		         	<a href="swf/业务数据模板.xls" style="color:#fff;"><img src="skins/images/new.png"/>数据模板</a>
					&nbsp;
					&nbsp;
					<a href="swf/人事手册.rar" style="color:#fff;"><img src="skins/images/book_open.png"/>人事手册</a>
					&nbsp;
					&nbsp;
					<a href="swf/招聘手册.rar" style="color:#fff;"><img src="skins/images/book_open.png"/>招聘手册</a>
					&nbsp;
					&nbsp;
					<a href="http://125.210.208.60:9080/bookweb/book/home.do" style="color:#fff;"><img src="skins/images/weather_sun.png"/>阳光书屋</a>
					&nbsp;
					&nbsp;
					<a href="address.do" style="color:#fff;"><img src="skins/images/group.png"/>通讯录</a>
					&nbsp;
					&nbsp;
					
					<!-- &nbsp;
					&nbsp;
					<a href="j_spring_security_logout" style="color:#fff;"><img src="skins/images/cross.png"/>注销</a> -->
		         </div>
			</li>
			<li style="padding-top:10px;color:#fff;">
				${companyname }/${deptname }：${username } 
				&nbsp;
				&nbsp;
			</li>
		</ul>
	</div>
</div>



<!--Wrapper-->
<div id="Wrapper">
	<!--Content-->
	<div id="Content">
		<div class="sort">
			<div class="sort-title">
				<h3>面试人员列表</h3>
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
							<span id="b4">
								<div id="checkbox" style="float:left;">
									<label for="today">
										<input id="today" type="checkbox" class="checkboxstyle" onclick="mygrid.reload();" checked="true"/> 只显示当天的面试
									</label>
									&nbsp;
									&nbsp;
								</div>
							</span>
							<a class="btn" href="javascript:searchGrid();"><i class="icon icon-search"></i><span>搜索</span></a>
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
</div>






<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="" id="searchForm" class="form">
		<ul>
			<li>
				<span>姓名：</span>
				<input id="name" name="name" class="inputstyle"/>
			</li>
		</ul>
	</form>
</div>



</body>
</html>