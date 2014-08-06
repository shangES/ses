<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>员工管理</title>
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

<script type="text/javascript">
$(document).ready(function () {
	//加载布局
	$('body').layout({
        north: {size:"35",resizable:false,spacing_open:0,spacing_closed:0}
    });
    
	
    //列表加载
    loadGrid();
    
    
    //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd'
    });
    
});




//添加到待异动列表中
function add(rownum){
	var obj=mygrid.getRecord(rownum);
	window.parent.add(obj);
}




//部门选择回调
function callbackDept(){
	$("#postid").val(null);
	$("#postname").val(null);
	
	mygrid.reload();
}

function callbackPost(){
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
	pam.userguid='${userid}';
	
	pam.workstate="<>-1";
	pam.isstaff="1";
	//pam.companyid=$("#companyid").val();
	pam.deptid=$("#deptid").val();
	
	pam.postid=$("#postid").val();
	
	pam.name=$("#name").val();
	pam.jobnumber=$("#jobnumber").val();
	
}

</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=10;
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'employeeid'},
				{name : 'name'},
				{name : 'sex'},
				{name : 'birthday'},
				{name : 'cardnumber'},
				{name : 'culture'},
				{name : 'nation'},
				{name : 'mobile'},
				{name : 'residenceplace'},
				{name : 'homephone'},
				{name : 'homeplace'},
				{name : 'bloodtype'},
				{name : 'domicilplace'},
				{name : 'nativeplace'},
				{name : 'politics'},
				{name : 'married'},
				{name : 'privateemail'},
				{name : 'photo'},
				{name : 'joinworkdate'},
				{name : 'joingroupdate'},
				{name : 'workstate'},
				{name : 'jobnumber'},
				{name : 'secrecy'},
				{name : 'dorder'},
				{name : 'classification'},
				{name : 'employtype'},
				{name : 'checknumber'},
				{name : 'joindate'},
				{name : 'startdate'},
				{name : 'enddate'},
				{name : 'officephone'},
				{name : 'email'},
				{name : 'shortphone'},
				{name : 'officeaddress'},
				{name : 'studymonth'},
				{name : 'officialdateplan'},
				{name : 'officialdate'},
				{name : 'officialmemo'},
				{name : 'resignationdate'},
				{name : 'resignationreason'},
				{name : 'interfacecode'},
				{name : 'contactphone'},
				{name : 'contactrelationship'},
				{name : 'contactname'},
				{name : 'memo'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'},
				{name : 'secrecyname'},
				{name : 'sexname'},
				{name : 'culturename'},
				{name : 'nationname'},
				{name : 'bloodtypename'},
				{name : 'politicsname'},
				{name : 'marriedname'},
				{name : 'resignationreasonname'},
				{name : 'classificationname'},
				{name : 'employtypename'},
				{name : 'contactrelationshipname'},
				{name : 'isstaff'},
				{name : 'isstaffname'}
			]
		};
		var colsOption = [
			{id: 'state' , header: "操作" , width :40 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				var htm='<a href="javascript:add('+rowNo+');" class="ui-button ui-button-icon-only" title="添加到待异动列表中"><span class="ui-icon ui-icon-plusthick"></span>&nbsp;</a>';
				return htm;
			}},
			{id: 'workstatename' , header: "状态" , width :60 ,headAlign:'center',align:'center'},
			{id: 'companyname' , header: "公司名称" , width :200 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :100 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称" , width :100 ,headAlign:'center',align:'left'},
			
			{id: 'jobnumber' , header: "工号" , width :100 ,headAlign:'center'},
			{id: 'name' , header: "姓名" , width :80 ,headAlign:'center',align:'left'},
			{id: 'sexname' , header: "性别" , width :60 ,headAlign:'center',align:'center'},
			{id: 'birthday' , header: "出生日期" , width :90 ,headAlign:'center',align:'center'},
			{id: 'birthdayname' , header: "年龄" , width :50 ,headAlign:'center',align:'center'},
			{id: 'cardnumber', header: "身份证号码", width :180 ,headAlign:'center'},
			{id: 'culturename' , header: "文化程度" , width :100 ,headAlign:'center'},
			{id: 'nationname' , header: "民族" , width :100 ,headAlign:'center'},
			{id: 'residenceplace' , header: "户口所在地" , width :200 ,headAlign:'center'},
			{id: 'mobile' , header: "手机" , width :120 ,headAlign:'center'},
			{id: 'homeplace' , header: "家庭住址" , width :200 ,headAlign:'center'},
			{id: 'homephone' , header: "家庭电话" , width :100 ,headAlign:'center'},
			{id: 'bloodtypename' , header: "血型" , width :100 ,headAlign:'center',align:'center'},
			{id: 'domicilplace' , header: "户籍信息" , width :200 ,headAlign:'center',align:'left'},
			{id: 'nativeplace' , header: "籍贯" , width :100 ,headAlign:'center'},
			{id: 'politicsname' , header: "政治面貌" , width :100 ,headAlign:'center'},
			{id: 'marriedname' , header: "婚姻状况" , width :100 ,headAlign:'center',align:'center'},
			{id: 'privateemail' , header: "私人邮箱" , width :150 ,headAlign:'center'},
			{id: 'contactname' , header: "紧急联系人" , width :80 ,headAlign:'center'},
			{id: 'contactrelationshipname' , header: "与紧急联系关系" , width :100 ,headAlign:'center'},
			{id: 'contactphone' , header: "紧急联系人电话" , width :150 ,headAlign:'center'},
			
			{id: 'joinworkdate' , header: "参加工作时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'joingroupdate' , header: "加入集团时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'secrecyname' , header: "员工信息保密" , width :100 ,headAlign:'center',align:'center'},
			{id: 'classificationname' , header: "员工类别" , width :80 ,headAlign:'center'},
			{id: 'employtypename' , header: "用工性质" , width :80 ,headAlign:'center'},
			{id: 'jobname' , header: "职务" , width :150 ,headAlign:'center'},
			{id: 'quotaname' , header: "编制类别" , width :150 ,headAlign:'center'},
			{id: 'checknumber' , header: "考勤号" , width :80 ,headAlign:'center'},
			{id: 'joindate' , header: "入职时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'startdate' , header: "岗位聘任开始时间" , width :120 ,headAlign:'center',align:'center'},
			{id: 'enddate' , header: "岗位聘任结束时间" , width :120 ,headAlign:'center',align:'center'},
			
			{id: 'officephone' , header: "办公电话" , width :80 ,headAlign:'center'},
			{id: 'email' , header: "公司邮箱" , width :150 ,headAlign:'center'},
			{id: 'shortphone' , header: "手机内网号码" , width :120 ,headAlign:'center'},
			{id: 'officeaddress' , header: "办公地址" , width :200 ,headAlign:'center'},
			{id: 'studymonth' , header: "试用期（月）" , width :80 ,headAlign:'center',align:'center'},
			{id: 'officialdateplan' , header: "计划转正时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'officialdate' , header: "转正时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'officialmemo' , header: "转正备注" , width :200 ,headAlign:'center'},
			{id: 'resignationdate' , header: "离职时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'resignationreasonname' , header: "离职原因" , width :100 ,headAlign:'center'},
			{id: 'modimemo' , header: "备注" , width :200 ,headAlign:'center',headAlign:'left',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'employee/searchEmployee.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		width: "99.5%",//"100%", // 700,
		height: "98.5%",  //"100%", // 330,
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
		customRowAttribute : function(record,rn,grid){
			if(record.workstate==-1){
				return 'style="background:#fff6f6;"';
			}else if(record.workstate==0){
				return 'style="background:#ffffec;"';
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

</script>

</head>
<body style="width:100%;height:450px;">
<div class="ui-layout-north" style="border:0px;">
	<div  class="form">
		<ul>
			<li style="padding:0px;">
			    <span style="width:70px;">部门名称：</span>
			    <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);" style="width:90px;"/>
			    <div class="search-trigger" onclick="chooseMyDeptTree('#deptid','#deptname',callbackDept);"/>
			</li>
			
			<li style="padding:0px;">
			    <span style="width:70px;">岗位名称：</span>
			    <input id="postid" name="postid" type="hidden" value=""/>
			    <input id="postname" name="postname" class="inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val(),callbackPost);" style="width:90px;"/>
			    <div class="search-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val(),callbackPost);" />
			</li>
			
			<li style="padding:0px;">
                <span style="width:50px;">姓名：</span>
                <input id="name" name="name" class="inputstyle" style="width:100px;" onkeyup="onMyKeyUp();"/>
            </li>
			<li style="padding:0px;">
                <span style="width:75px;">员工工号：</span>
                <input id="jobnumber" name="jobnumber" class="inputstyle" style="width:100px;" onkeyup="onMyKeyUp();"/>
            </li>
		</ul>
	</div>
</div>
	
<div class="ui-layout-center" style="overflow:hidden;border:0px;">
	<div id="gridbox" ></div>
</div>



</body>
</html>