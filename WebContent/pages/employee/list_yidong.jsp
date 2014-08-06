<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>员工异动</title>
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
	
    //列表加载
    loadGrid();
    
    
    //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd'
    });
    
});



//选择异动的员工
function onYiDong(){
	$("#yidong").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:750,
		height:550,
		buttons: {
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			var url='employee.do?page=util/selectemployee';
			var htm='<iframe id="myYiDong" name="myYiDong" width="100%" height="450px" frameborder="0" src="'+url+'" scrolling="no" ></iframe>';
			$("#yidong").html(htm);
		},
		close:function(){
			$("#yidong").html(null);
		}
	});
}







//确定异动
var pageState=false;
var myForm=null;
function openForm(){
	var array=new Array();
	$("input[name='mycheckbox']:checked").each(function(){
		array.push($(this).val());
	});
	
	if(array.length<=0){
		alert('请选择要异动的数据！');
		return;
	}
	if(!confirm('确认要异动选中数据吗？')){
		return;
	}
	
	
	$("#formWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		buttons: {
			"确定":function(){
				$('#myForm').submit();
			},
			"重置":function(){
				//重置表单
				resetMyForm(array.toString());
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//重置表单
			resetMyForm(array.toString());
			 //加载表单验证
			if(myForm==null){
			    $("#myForm").validate();
			    $('#myForm').ajaxForm(function(data) {
			    	if(data!=null&&data!=''){
			    		alert(data);
			    		return;
			    	}
			    	alert("【"+array.length+"】条员工数据异动成功！");
			    	$("#formWindow").dialog("close");
			    	pageState=true;
			    });
			}
		}
	});
}

//重置表单
function resetMyForm(ids){
	$("#employeeid").val(ids);
	$("#isstaff").val(-1);
	$("#state").val(1);
	$("#modiuser").val("${username }");
}








//删除
function remove(){
	var array=new Array();
	$("input[name='mycheckbox']:checked").each(function(){
		$(this).parent().parent().parent().remove();
	});
}




//添加到待异动列表中
function add(obj){
	var state=false;
	
	$("input[name='mycheckbox']").each(function(){
		var val=$(this).val();
		if(obj.employeeid==val)
			state=true;
	});
	if(!state)
		mygrid.add(obj);
}



//选中所有
function checkAll(el){
	var state=$(el).attr("checked");
	$("input[name='mycheckbox']").attr("checked",state);
}




//公司回调
function callBackCompany(){
	$("#deptid").val(null);
	$("#deptname").val(null);
	
	
	$("#jobid").val(null);
	$("#jobname").val(null);
	
	//部门选择回调
	callbackDept();
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
	pam.expAll=0;
	pam.userguid='${userid}';
}




//返回
function back(){
	window.parent.convertView(pageState?null:'');
}

</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
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
				{name : 'isstaffname'},
				{name : 'domicilplacename'}
			]
		};
		var colsOption = [
			{id: '选择' , width :40,headAlign:'center',align:'center',sortable:false,resizable:false,hdRenderer:function(header,colObj,grid){
				return '<input type="checkbox" onclick="checkAll(this);"/>';
			},renderer:function(value ,record,colObj,grid,colNo,rowNo){
				return '<input type="checkbox" name="mycheckbox" value="'+record.employeeid+'"/>';
			}},
			{id: 'workstatename' , header: "状态" , width :60 ,headAlign:'center',align:'center'},
			{id: 'isstaffname' , header: "编制" , width :60 ,headAlign:'center',align:'center'},
			{id: 'companyname' , header: "公司名称" , width :200 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :150 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称" , width :120 ,headAlign:'center',align:'left'},
			{id: 'jobnumber' , header: "工号" , width :100 ,headAlign:'center'},
			{id: 'name' , header: "姓名" , width :80 ,headAlign:'center',align:'left'},
			{id: 'sexname' , header: "性别" , width :60 ,headAlign:'center',align:'center'},
			{id: 'birthday' , header: "出生日期" , width :90 ,headAlign:'center',align:'center'},
			{id: 'birthdayname' , header: "年龄" , width :50 ,headAlign:'center',align:'center'},
			{id: 'cardnumber', header: "身份证号码", width :180 ,headAlign:'center'},
			{id: 'culturename' , header: "学历情况" , width :100 ,headAlign:'center'},
			{id: 'nationname' , header: "民族" , width :100 ,headAlign:'center'},
			{id: 'residenceplace' , header: "身份证地址" , width :200 ,headAlign:'center'},
			{id: 'mobile' , header: "手机" , width :120 ,headAlign:'center'},
			{id: 'homeplace' , header: "家庭住址" , width :200 ,headAlign:'center'},
			{id: 'homephone' , header: "家庭电话" , width :100 ,headAlign:'center'},
			{id: 'bloodtypename' , header: "血型" , width :100 ,headAlign:'center',align:'center'},
			{id: 'domicilplacename' , header: "户籍类型" , width :200 ,headAlign:'center',align:'left'},
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
			{id: 'joindate' , header: "加入公司时间" , width :90 ,headAlign:'center',align:'center'},
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
		loadURL :'',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		width: "99.8%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
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
		customRowAttribute : function(record,rn,grid){
			if(record.workstate==-1){
				return 'style="background:#fff6f6;"';
			}else if(record.workstate==0){
				return 'style="background:#ffffec;"';
			}
		},
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
		},
		loadResponseHandler:function(response,requestParameter){
			//弹出异动
		    onYiDong();
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
		<h3>员工异动</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
	
	<div class="sort-cont sort-table">
		<div class="table">
			<div class="table-bar">
				<div class="table-title">
					异动列表
				</div>
				<div class="table-ctrl">
					<a class="btn" href="javascript:onYiDong();"><i class="icon icon-ok-circle"></i><span>选择</span></a>
					<a class="btn" href="javascript:openForm();"><i class="icon icon-bookmark"></i><span>确定异动</span></a>
					<a class="btn" href="javascript:remove();"><i class="icon icon-trash"></i><span>删除</span></a>
					<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
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






<!-- 选择异动的员工-->
<div id="yidong" title="选择异动的员工" style="display:none;overflow:hidden;">
	
</div>









<!-- 员工异动信息 -->
<div id="formWindow" title="员工异动信息" style="display:none;">
	<form action="employee/saveOrUpdateManyPosition.do" method="post" id="myForm" class="form">
		<input id="employeeid" name="employeeid" type="hidden" value=""/>
		<input id="postionguid" name="postionguid" type="hidden" value=""/>
		<input id="isstaff" name="isstaff" type="hidden" value="-1"/>
		<input id="state" name="state" type="hidden" value="1"/>
		<input id="enddate" name="enddate" type="hidden" value=""/>
		<input id="modiuser" name="modiuser" type="hidden" value="${userid }"/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		<input id="modimemo" name="modimemo" type="hidden" value=""/>
		<ul id="myCompany">
			<li>
                <span><em class="red">* </em>公司名称：</span>
                <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="companyname" name="companyname" class="{required:true} inputselectstyle" onclick="chooseAllCompanyTree('#companyid','#companyname',callBackCompany);"/>
			    <div class="search-trigger" onclick="chooseAllCompanyTree('#companyid','#companyname',callBackCompany);"/>
            </li>
        </ul>
        <ul>
			<li>
			    <span>部门名称：</span>
			    <input id="deptid" name="deptid" type="hidden" value=""/>
			    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
			    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>职务名称：</span>
			    <input id="jobid" name="jobid" type="hidden" value=""/>
			    <input id="jobname" name="jobname" class="inputselectstyle" onclick="chooseJobTree('#jobid','#jobname',$('#companyid').val());"/>
			    <div class="search-trigger" onclick="chooseJobTree('#jobid','#jobname',$('#companyid').val());"/>
			</li>
        </ul>
        <ul>
			<li>
				<span>岗位名称：</span>
                <input id="postid" name="postid" type="hidden" value=""/>
			    <input id="postname" name="postname" class="inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
			    <div class="search-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
            </li>
        </ul>
         <ul id="myQuota">
			<li>
			    <span>岗位编制：</span>
			    <input id="quotaid" name="quotaid" type="hidden" value=""/>
			    <input id="quotaname" name="quotaname" class="inputselectstyle" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val());"/>
			    <div class="search-trigger" onclick="chooseQuotaTree('#quotaid','#quotaname',$('#postid').val());"/>
			</li>
			<li>
			    <span>职级：</span>
			     <input id="rankid" name="rankid" type="hidden" value=""/>
			    <input id="rankname" name="rankname" class="inputselectstyle" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());"/>
			    <div class="select-trigger" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());" />
			</li>
        </ul>
        <ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="startdate" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#startdate').focus();"/>
			</li>
		</ul>
        <ul>
			<li>
			    <span>备注：</span>
			    <textarea id="memo" name="memo"  rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>




</body>
</html>