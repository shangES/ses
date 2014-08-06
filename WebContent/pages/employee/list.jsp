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
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
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
    
    
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});




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
		var obj=cords[i];
		var emp={};
		emp.employeeid=obj.employeeid;
		emp.postionguid=obj.postionguid;
		emp.state=obj.state;
		emp.isstaff=obj.isstaff;
		emp.name=obj.name;
		array.push(emp);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	//参数
	$.ajax({  
        url: "employee/delEmployeeById.do",  
        contentType: "application/json; charset=utf-8",  
        type: "POST",  
        dataType: "json",  
        data: JSON.stringify({list:array}),
        success: function(result) { 
        	mygrid.reload();
        }
    });
}









//兼任
function onPluralism(id){
	$("#pluralism").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:700,
		height:550,
		buttons: {
			"关闭": function() {
				// 关闭
				var content = document.getElementById('myPluralism');
				if (content != null && content.contentWindow!=null&& content.contentWindow.getPageState != null){
					if(content.contentWindow.getPageState())
						mygrid.reload();
				}
				$(this).dialog("close");
			}
		},
		open:function(){
			var url='employee.do?page=list_position&id='+id;
			var htm='<iframe id="myPluralism" name="myPluralism" width="100%" height="450px" frameborder="0" src="'+url+'" scrolling="no"></iframe>';
			$("#pluralism").html(htm);
		},
		close:function(){
			$("#pluralism").html(null);
		}
	});
}




//取消兼任
var canclePluralismForm=null;
function onCanclePluralism(pid){
	$("#canclePluralism").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		buttons: {
			"确定":function(){
				if(canclePluralismForm.form()){
					var enddate=$("#enddate").val();
					$.post("employee/endPositionById.do",{postionguid:pid,enddate:enddate}, function() {
						alert("取消兼任成功!");
						$("#canclePluralism").dialog("close");
						mygrid.reload();
				  	});
				}
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//校验
			if(canclePluralismForm==null)
				canclePluralismForm=$("#canclePluralismForm").validate();
			
		}
	});
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



//公司回调
function callBackCompany(){
	$("#deptid").val(null);
	$("#deptname").val(null);
	
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
	
	
	var ws=$("#workstate").val();
	if(ws!=null&&ws!='')
		pam.workstate="="+ws;
	pam.secrecy=$("#secrecy").val();
	pam.companyid=$("#companyid").val();
	pam.deptid=$("#deptid").val();
	pam.postid=$("#postid").val();
	pam.startdate=$("#startdate").val();
	pam.isstaff=$("#isstaff").val();
	
	
	pam.name=$("#name").val();
	pam.sex=$("#sex").val();
	pam.jobnumber=$("#jobnumber").val();
	
	pam.birthday_s=$("#birthday_s").val();
	pam.birthday_e=$("#birthday_e").val();
	
	//pam.classification=$("#classification").val();
	pam.employtype=$("#employtype").val();
	pam.joindate_s=$("#joindate_s").val();
	pam.joindate_e=$("#joindate_e").val();
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
				{name : 'state'},
				{name : 'isstaff'},
				{name : 'isstaffname'},
				{name : 'domicilplacename'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'isstaff' , header: " " , width :50 ,headAlign:'center',align:'center',hidden:${!position},renderer:function(value ,record,colObj,grid,colNo,rowNo){
				var htm='';
				if(value==1){
					if(record.workstate!=-1){
						htm+='<a href="javascript:onPluralism(\''+record.employeeid+'\');" title="设置兼任">';
						htm+='兼任';
						htm+='</a>';
					}
				}else if(value==-1){
					htm+='<a href="javascript:convertView(\'employee.do?page=tab&id='+record.employeeid+'&postionguid='+record.postionguid+'\');"  title="异动生效">';
					htm+='生效';
					htm+='</a>';
				}else{
					if(record.workstate!=-1){
						htm+='<a href="javascript:onCanclePluralism(\''+record.postionguid+'\');" title="取消兼任">';
						htm+='取消';
						htm+='</a>';
					}
				}
				return htm;
			}},
			{id: 'isstaffname' , header: "编制" , width :60 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				if(record.isstaff==0){
					return '<font color="#C8C8C8">'+value+'</font>';
				}else if(record.isstaff==1){
					return '<font color="green">'+value+'</font>';
				}else if(record.isstaff==-1){
					return '<font color="#ff0000">'+value+'</font>';
				}
				return value;
			}},
			{id: 'secrecy' , header: "保密" , width :40,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				var htm="";
				if(value==2)
					htm+='<img src="skins/images/lock.gif"/>';
				return htm;
			}},
			{id: 'workstatename' , header: "状态" , width :60 ,headAlign:'center',align:'center'},
			{id: 'companyname' , header: "公司名称" , width :200 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :150 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称" , width :120 ,headAlign:'center',align:'left'},
			
			{id: 'dorder' , header: "排序号" , width :80 ,headAlign:'center',align:'left'},
			{id: 'jobnumber' , header: "工号" , width :100 ,headAlign:'center'},
			{id: 'name' , header: "姓名" , width :80 ,headAlign:'center',align:'left'},
			{id: 'email' , header: "公司邮箱" , width :150 ,headAlign:'center'},
			{id: 'shortphone' , header: "手机内网号码" , width :120 ,headAlign:'center'},
			{id: 'classificationname' , header: "员工类别" , width :80 ,headAlign:'center'},
			{id: 'employtypename' , header: "用工性质" , width :80 ,headAlign:'center'},
			{id: 'jobname' , header: "职务" , width :100 ,headAlign:'center'},
			{id: 'rankname' , header: "职级" , width :100 ,headAlign:'center',align:'left'},
			{id: 'quotaname' , header: "编制类别" , width :120 ,headAlign:'center'},
			{id: 'cardnumber', header: "身份证号码", width :180 ,headAlign:'center'},
			{id: 'birthday' , header: "出生日期" , width :90 ,headAlign:'center',align:'center'},
			{id: 'sexname' , header: "性别" , width :60 ,headAlign:'center',align:'center'},
			{id: 'birthdayname' , header: "年龄" , width :50 ,headAlign:'center',align:'center'},
			{id: 'culturename' , header: "学历情况" , width :100 ,headAlign:'center'},
			{id: 'nationname' , header: "民族" , width :100 ,headAlign:'center'},
			{id: 'mobile' , header: "手机" , width :120 ,headAlign:'center'},
			{id: 'addressmobile' , header: "通讯录手机" , width :120 ,headAlign:'center'},
			{id: 'domicilplacename' , header: "户籍类型" , width :200 ,headAlign:'center',align:'left'},
			{id: 'nativeplace' , header: "籍贯" , width :100 ,headAlign:'center'},
			{id: 'politicsname' , header: "政治面貌" , width :100 ,headAlign:'center'},
			{id: 'marriedname' , header: "婚姻状况" , width :100 ,headAlign:'center',align:'center'},
			{id: 'bloodtypename' , header: "血型" , width :100 ,headAlign:'center',align:'center'},
			{id: 'joinworkdate' , header: "参加工作时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'joingroupdate' , header: "加入集团时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'secrecyname' , header: "员工信息保密" , width :100 ,headAlign:'center',align:'center'},
			{id: 'checknumber' , header: "考勤号" , width :80 ,headAlign:'center'},
			{id: 'joindate' , header: "加入公司时间" , width :90 ,headAlign:'center',align:'center'},
			{id: 'startdate' , header: "聘任开始时间" , width :120 ,headAlign:'center',align:'center'},
			{id: 'enddate' , header: "聘任结束时间" , width :120 ,headAlign:'center',align:'center'},
			{id: 'officephone' , header: "办公电话" , width :80 ,headAlign:'center'},
			{id: 'officeaddress' , header: "办公地址" , width :200 ,headAlign:'center'},
			{id: 'residenceplace' , header: "身份证地址" , width :200 ,headAlign:'center'},
			{id: 'homeplace' , header: "家庭住址" , width :200 ,headAlign:'center'},
			{id: 'homephone' , header: "家庭电话" , width :100 ,headAlign:'center'},
			{id: 'privateemail' , header: "私人邮箱" , width :150 ,headAlign:'center'},
			{id: 'social' , header: "社保归属地" , width :150 ,headAlign:'center'},
			{id: 'contactname' , header: "紧急联系人" , width :80 ,headAlign:'center'},
			{id: 'contactrelationshipname' , header: "与紧急联系关系" , width :100 ,headAlign:'center'},
			{id: 'contactphone' , header: "紧急联系人电话" , width :150 ,headAlign:'center'},
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
		saveURL :'employee/saveEmployeeGrid.do',
		loadURL :'employee/searchEmployee.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'employee/searchEmployee.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		exportFileName : '员工信息表.xls',
		width: "99.8%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
		container : 'gridbox', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:true,
		selectRowByCheck:true,
		replaceContainer : true,
		showGridMenu : true,
		allowFreeze	: true ,
		allowHide	: true ,
		allowGroup	: true ,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize | reload  state',
		pageSize:size,
		skin:getGridSkin(),
		customRowAttribute : function(record,rn,grid){
			//生效状态
			if(record.isstaff==-1){
				return 'style="background:#edf4fe;"';
			}
			
			if(record.workstate==-1){
				return 'style="background:#fff6f6;"';
			}else if(record.workstate==0){
				return 'style="background:#ffffec;"';
			}
		},
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('employee.do?page=tab&id='+record.employeeid+"&postionguid="+record.postionguid);
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
		<h3>员工管理</h3>
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
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none'}"><i class="icon icon-search"></i><span>搜索</span></a>
					<a class="btn" href="javascript:convertView('employee.do?page=import');" style="display:${imp?'':'none'}"><i class="icon icon-qrcode"></i><span>导入</span></a>
					<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none'}"><i class="icon icon-list-alt"></i><span>导出</span></a>
					<a class="btn" href="javascript:convertView('employee.do?page=tab');" style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>入职</span></a>
					<a class="btn" href="javascript:convertView('employee.do?page=list_yidong');" style="display:${post?'':'none'}"><i class="icon icon-indent-left"></i><span>异动</span></a>
					<a class="btn" href="javascript:remove();" style="display:${del?'':'none'}"><i class="icon icon-remove"></i><span>删除</span></a>
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
		<ul id="myCompany">
			<li>
                <span>公司名称：</span>
                <input id="companyid" name="companyid" type="hidden" value=""/>
			    <input id="companyname" name="companyname" class="inputselectstyle" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
			    <div class="search-trigger" onclick="chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
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
			    <span>岗位名称：</span>
			    <input id="postid" name="postid" type="hidden" value=""/>
			    <input id="postname" name="postname" class="inputselectstyle" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
			    <div class="search-trigger" onclick="choosePostTree('#postid','#postname',$('#deptid').val());"/>
			</li>
        </ul>
        <ul>
         	<li>
			    <span>聘任时间：</span>
			    <input id="startdate" name="startdate" class="{dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#startdate').focus();"/>
			</li>
        </ul>
        <ul>
			<li>
				<span>员工状态：</span>
                <input id="workstate" name="workstate" type="hidden" value=""/>
			    <input id="workstatename" name="workstatename" class="inputselectstyle" onclick="chooseOptionTree('#workstate','#workstatename','WORKSTATE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#workstate','#workstatename','WORKSTATE');"/>
            </li>
        </ul>
        <ul>
	       <li>
			    <span>编制：</span>
			    <input id="isstaff" name="isstaff" type="hidden" value=""/>
			    <input id="isstaffname" name="isstaffname" class="inputselectstyle" onclick="chooseOptionTree('#isstaff','#isstaffname','ISSTAFF');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#isstaff','#isstaffname','ISSTAFF');"/>
			</li>
       </ul>
		<ul>
			<li>
                <span>姓名：</span>
                <input id="name" name="name" class="inputstyle"/>
            </li>
        </ul>
		<ul>
			<li>
                <span>员工工号：</span>
                <input id="jobnumber" name="jobnumber" class="inputstyle"/>
            </li>
        </ul>
        <ul>
			<li>
			    <span>性别：</span>
			    <input id="sex" name="sex" type="hidden" value=""/>
			    <input id="sexname" name="sexname" class="inputselectstyle" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
			</li>
		</ul>
        <ul>
         	<li>
			    <span>年龄从：</span>
			    <input id="birthday_e" name="birthday_e" class="{digits:true} inputstyle" style="width:97px;"/>
			</li>
            <li>
			    <span style="width:16px;">至&nbsp;</span>
			    <input id="birthday_s" name="birthday_s" class="{digits:true} inputstyle" style="width:97px;"/>
			</li>
        </ul>
        <ul>
         	<li style="display:inline;">
			    <span>入职时间从：</span>
			    <input id="joindate_s" name="joindate_s" class="{dateISO:true} inputselectstyle datepicker" style="width:80px;"/>
			    <div class="date-trigger" onclick="$('#joindate_s').focus();"/>
			</li>
            <li style="display:inline;">
			    <span style="width:16px;">至&nbsp;</span>
			     <input id="joindate_e" name="joindate_e" class="{dateISO:true} inputselectstyle datepicker" style="width:80px;"/>
			    <div class="date-trigger" onclick="$('#joindate_e').focus();"/>
			</li>
        </ul>
        <ul>
			<li>
				<span>员工信息保密：</span>
			    <input id="secrecy" name="secrecy" type="hidden" value=""/>
			    <input id="secrecyname" name="secrecyname" class="inputselectstyle" onclick="chooseOptionTree('#secrecy','#secrecyname','SECRETLEVEL');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#secrecy','#secrecyname','SECRETLEVEL');"/>
            </li>
        </ul>
	   <ul>
			<li>
			    <span>用工性质：</span>
			    <input id="employtype" name="employtype" type="hidden" value=""/>
			    <input id="employtypename" name="employtypename" class="inputselectstyle" onclick="chooseOptionTree('#employtype','#employtypename','EMPLOYTYPE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#employtype','#employtypename','EMPLOYTYPE');"/>
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









<!-- 兼任-->
<div id="pluralism" title="员工兼任信息" style="display:none;overflow:hidden;">
	
</div>

<!-- 取消兼任-->
<div id="canclePluralism" title="取消兼任" style="display:none;overflow:hidden;">
	<form action="" id="canclePluralismForm" class="form">
        <ul>
         	<li>
			    <span><em class="red">* </em>结束时间：</span>
			    <input id="enddate" name="enddate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#enddate').focus();"/>
			</li>
        </ul>
	</form>
</div>






</body>
</html>