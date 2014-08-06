<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>我的推荐</title>
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

function _autoHeight(){
	
} 

$(document).ready(function() {
	//列表加载
    loadGrid();
	
    
  //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
  
  
  //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});




//导出
function expGrid(){
	mygrid.exportGrid('xls');
}







//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.userguid="${param.userguid}";
}






//返回
function back(){
	window.parent.convertView('');
}


</script>


<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'recommendguid'},
				{name : 'employeeid'},
				{name : 'name'},
				{name : 'sex'},
				{name : 'mobile'},
				{name : 'situation'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			//{id: 'username' , header: "推荐人" , width :80 ,headAlign:'center',align:'left'},
			{id: 'candidatesstatename' , header: "状态" , width :80 ,headAlign:'center',align:'center'},			
			{id: 'name' , header: "姓名" , width :80 ,headAlign:'center',align:'left'},
			{id: 'birthdayname' , header: "年龄" , width :50,headAlign:'center',align:'left',number:true},
			{id: 'culturename' , header: "学历" , width :100 ,headAlign:'center',align:'center'},
			{id: 'workagename' , header: "工作年限" , width :100 ,headAlign:'center',align:'center'},
			{id: 'mobile' , header: "手机号码" , width :90 ,headAlign:'center',align:'left'},
			{id: 'moditimestamp' , header: "推荐时间" , width :150 ,headAlign:'center',align:'center'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'resume/searchRecommend.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'resume/searchRecommend.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '我的推荐.xls',
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
		skin:getGridSkin()
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
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
					<h3>我的推荐</h3>
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
								<a class="btn" href="resume.do?page=recommend/form_out"><i class="icon icon-hand-left" ></i><span>内部推荐</span></a>
								<a class="btn" href="recruitpost.do?page=internal_list"><i class="icon icon-hand-left" ></i><span>内部竞聘</span></a>
								<a class="btn" href="recruitpost.do?page=myRecruitpost_out_jp"><i class="icon icon-user" ></i><span>我的竞聘</span></a>
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
		</div>
	</div>
	<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>
</div>







</body>
</html>
