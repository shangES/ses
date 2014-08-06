<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>招聘计划管理</title>
<base href="${baseUrl }" />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="skins/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="skins/css/form.css" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css" />
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/default/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/green/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/mac/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/china/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/vista/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/calendar/calendar-blue.css" />


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
var tid='${param.id}';

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








//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.companyid=$("#companyid").val();
	pam.deptid=$("#deptid").val();
	pam.postname=$("#postname").val();
	pam.quotaid=tid;
	pam.rankid=$("#rankid").val();
	pam.myvalid=$("#myvalid").attr("checked")?1:0;
}









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



//一级部门选择回调
function callbackDept1(){
	$("#t_postid").val(null);
	$("#t_postname").val(null);
	
	callbackDept2();
}

//二级部门选择回调
function callbackDept2(){
	$("#t_postid").val(null);
	$("#t_postname").val(null);
	
	callbackPost();
}


//岗位回写
function callbackPost(){
	$("#t_quotaid").val(null);
	$("#t_quotaname").val(null);
}


function callbackDept(){
	$("#postid").val(null);
	$("#postname").val(null);
	$("#quotaid").val(null);
	$("#quotaname").val(null);
}


//公司回调
function callBackCompany(){
	$("#deptid").val(null);
	$("#deptname").val(null);
	
	//部门选择回调
	callbackDept();
}






//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].recruitprogramguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	
	$.post("recruitprogram/delRecruitprogramById.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
}




//审核生效
function auditValid(state){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		if(obj.state=-1&&obj.state!=0&&obj.state!=2&&(obj.interfacecode==null||obj.interfacecode==''))
			array.push(obj.recruitprogramguid);
	}
	if(array.length<=0){
		alert('请选择要生效且不用细分的数据！');
		return;
	}
	
	$.post("recruitprogram/updateRecruitprogramStateById.do",{ids:array.toString(),state:state}, function() {
		mygrid.reload();
    });
}


//细分操作
var auditForm=null;
function audit(id){
	$("#auditWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		buttons: {
			"确定":function(){
				if(auditForm.form()){
					$("#t_runprocess").val(true);
					save();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			//重置表单
			$("#auditForm").clearForm();
			
			loadData(id);
			
			 //加载表单验证
			if(auditForm==null)
				auditForm=$("#auditForm").validate({submitHandler:function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		alert("操作成功!");
			    		mygrid.reload();
			    		$("#auditWindow").dialog("close");
			        });
				}
			});
		}
	});
}



//保存
function save(){
	var id=$("#t_recruitprogramguid").val();
	var remainnum=$("#remainnum").val();
	
	if(remainnum!=null&&remainnum!=''){
		$.getJSON("recruitprogram/getRecruitprogramById.do", {id:id,taskid:null}, function(data) {
			if(data!=null){
				if(remainnum>data.postnum||remainnum<=0){
					alert("细分人数输入有误,请重新输入!");
					$("#remainnum").val(null);
					$("#remainnum").focus();
					return;
				}else
					$('#auditForm').submit();
			}
				
		});
	}
}



//取数据
function loadData(id) {
	if(id!=null&&id!=''&&id!='null')
		$.getJSON("recruitprogram/getRecruitprogramById.do", {id:id}, function(data) {
			if(data!=null){
				for(var key in data){
					if($('#auditForm #t_'+key)){
						$('#auditForm #t_'+key).val(data[key]);
					}
				}
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







//校验所输入的数字要小于招聘人数
function checkRemainNum(){
	var id=$("#t_recruitprogramguid").val();
	var remainnum=$("#remainnum").val();
	
	if(remainnum!=null&&remainnum!=''){
		$.getJSON("recruitprogram/getRecruitprogramById.do", {id:id,taskid:null}, function(data) {
			if(data!=null){
				if(remainnum>data.postnum||remainnum<=0){
					alert("细分人数输入有误,请重新输入!");
					$("#remainnum").val(null);
					$("#remainnum").focus();
					return;
				}
			}
		});
	}
}



//取消
function goout(id,interfacecode){
	if(!confirm('确认要取消细分数据吗？')){
		return;
	}
	
	$.getJSON("recruitprogram/cancelRecruitprogramByIdAndInterfacecode.do", {id:id,interfacecode:interfacecode}, function() {
		alert("操作成功!");
		mygrid.reload();
	});
}



</script>


<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'recruitprogramguid'},
				{name : 'recruitprogramcode'},
				{name : 'quotaid'},
				{name : 'companyid'},
				{name : 'deptid'},
				{name : 'postid'},
				{name : 'rankid'},
				{name : 'applydate'},
				{name : 'hillockdate'},
				{name : 'postnum'},
				{name : 'state'},
				{name : 'processinstanceid'},
				{name : 'memo'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'taskid' , header: "操作" , width :80 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				var htm='&nbsp;';
				if(record.state==2&&record.assignee=='${userid}'){
					htm+='<a href="javascript:convertView(\'recruitprogram.do?page=tab&id='+record.recruitprogramguid+'&taskid='+record.taskid+'\')">审核</a>';
					htm+='&nbsp;';
					htm+='&nbsp;';
					//return '<a href="javascript:convertView(\'recruitprogram.do?page=tab&id='+record.recruitprogramguid+'&taskid='+record.taskid+'\')">审核</a>';
				}else if(record.state==-1&&record.interfacecode!=null&&record.interfacecode!=''){
					htm+='<a href="javascript:audit(\''+record.recruitprogramguid+'\')">';
					htm+='细分';
					htm+='</a>';
					htm+='&nbsp;';
					htm+='&nbsp;';
				}else if(record.state==1&&record.interfacecode!=null&&record.interfacecode!=''&&record.interfacecode.length!=32&&record.modiuser!=null&&record.modiuser!=''){
					htm+='<a href="javascript:goout(\''+record.recruitprogramguid+'\',\''+record.interfacecode+'\')">';
					htm+='取消';
					htm+='</a>';
					htm+='&nbsp;';
					htm+='&nbsp;';
				}
				return htm;
			}},
			{id: 'statename' , header: "状态" , width :60 ,headAlign:'center',align:'left'},
			{id: 'recruitprogramcode' , header: "计划编号" , width :120,headAlign:'center',align:'left'},
			{id: 'companyname' , header: "公司名称" , width :180,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :150,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称" , width :120,headAlign:'center',align:'left'},
			{id: 'rankname' , header: "职级" , width :100,headAlign:'center',align:'center'},
			{id: 'applydate' , header: "申请时间" , width :120,headAlign:'center',align:'center'},
			{id: 'quotaname' , header: "编制类型" , width :120,headAlign:'center',align:'center'},
			{id: 'quotacode' , header: "编制编号" , width :120,headAlign:'center',align:'left'},
			{id: 'employednumber' , header: "占编人数" , width :60,headAlign:'center',align:'left',number:true},
			{id: 'quotanumber' , header: "编制人数" , width :60,headAlign:'center',align:'left',number:true},
			{id: 'interviewnumber' , header: "面试通过人数" , width :100,headAlign:'center',align:'left',number:true},
			{id: 'vacancynumber' , header: "缺编人数" , width :60,headAlign:'center',align:'left',number:true},
			{id: 'postnum' , header: "招聘人数/可细分人数" , width :150,headAlign:'center',align:'left',number:true},
			{id: 'hillockdate' , header: "计划到岗时间" , width :120,headAlign:'center',align:'center'},
			{id: 'memo' , header: "备注" , width :250 ,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'recruitprogram/searchRecruitprogram.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'recruitprogram/searchRecruitprogram.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '招聘计划管理.xls',
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
		},
		customRowAttribute : function(record,rn,grid){
			//if(record.state==1&&record.interfacecode!=null&&record.interfacecode!=''&&record.interfacecode.length!=32&&record.modiuser!=null&&record.modiuser!=''){
				//return 'style="font-weight:bold;background:#FFFACD;"';
			//}
			if(record.employednumber>(record.quotanumber+record.interviewnumber)){
				//return 'style="font-weight:bold;background:#F08080;"';
				return 'style="font-weight:bold;background:#FFFACD;"';
			}
		},
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('recruitprogram.do?page=tab&id='+record.recruitprogramguid+'&taskid='+record.taskid+'&interfacecode='+record.interfacecode);
			}
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
<body>

<div class="sort">
	<div class="sort-title">
		<h3>招聘计划管理</h3>
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
					<div style="float:left;">
						<label for="myvalid">
							<input id="myvalid" type="checkbox" class="checkboxstyle" onclick="mygrid.reload();" checked="true"/> 不显示失效的信息
						</label>
						&nbsp;
						&nbsp;
					</div>
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none'}"><i class="icon icon-search"></i><span>搜索</span></a>
			   		<a class="btn" href="javascript:convertView('recruitprogram.do?page=import');" style="display:${imp?'':'none'}"><i class="icon icon-qrcode"></i><span>导入</span></a>
					<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none'}"><i class="icon icon-download"></i><span>导出</span></a>
					<a class="btn"  href="javascript:auditValid(3);" style="display:${valid?'':'none'}"><i class="icon icon-stop"></i><span>生效</span></a>
					<a class="btn" href="javascript:convertView('recruitprogram.do?page=tab');" style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>新增</span></a>
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
	<iframe id="detail" name="detail" width="100%" height="100%"frameborder="0" src="" scrolling="no" style="display: none;"></iframe>


<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display: none;">
	<form action="" id="searchForm" class="form">
		<ul>
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
			    <input id="deptname" name="deptname" class="inputselectstyle" onclick="chooseOneCompanyMultipleDeptTree('#deptid','#deptname',$('#companyid').val());"/>
			    <div class="search-trigger" onclick="chooseOneCompanyMultipleDeptTree('#deptid','#deptname',$('#companyid').val());"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>岗位名称：</span>
				<input id="postname" name="postname" class="inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>职级：</span>
			     <input id="rankid" name="position.rankid" type="hidden" value=""/>
			    <input id="rankname" name="position.rankname" class="inputselectstyle" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());"/>
			    <div class="select-trigger" onclick="chooseRankTree('#rankid','#rankname',$('#companyid').val());" />
			</li>
		</ul>
	</form>
</div>


<!-- 导出 -->
<div id="dialog_exp" style="display: none;" title="数据导出">
	<table width="100%" height="100%" border="0" cellpadding="0"
		cellspacing="0">
		<tr>
			<td>&nbsp; &nbsp; &nbsp; &nbsp; <label for="xls1"> 
				<input id="xls1" type="radio" name="xls" value="0" checked="true" class="checkboxstyle" />导出本页
			</label> &nbsp; &nbsp; 
			<label for="xls2"> 
				<input id="xls2" type="radio" name="xls" value="1" class="checkboxstyle" />全部导出
			</label>
			</td>
		</tr>
	</table>
</div>



<!-- 审核 -->
<div id="auditWindow"  title="OA审核" style="display:none;">
	<form action="recruitprogram/UpdateRecruitProgram.do" id="auditForm"  class="form" method="post">
		<input type="hidden" id="t_recruitprogramguid" name="recruitprogramguid" value=""/>
		<input type="hidden" id="t_interfacecode" name="interfacecode" value=""/>
		<input type="hidden" id="t_recruitprogramcode" name="recruitprogramcode" value=""/>
		<input type="hidden" id="t_applydate" name="applydate" value=""/>
		<input type="hidden" id="t_state" name="state" value="" />
		<input id="t_modiuser" name="modiuser" type="hidden" value="${username}"/>
		<input id="t_moditimestamp" name="moditimestamp" type="hidden" value=""/>
		<input type="hidden" id="t_runprocess" name="runprocess" value="true" />
		<input type="hidden" id="t_processinstanceid" name="processinstanceid" value=""/>
		
		
		<!-- 流程信息 -->
		<input id="t_variables_commit" name="variables['rate']" type="hidden" value="2" />
		<input id="t_variables_commitNum" name="variables['commitNum']" type="hidden" value=1 />
		
		<ul>
			<li>
                <span><em class="red">* </em>公司名称：</span>
                <input id="t_companyid" name="companyid" type="hidden" value=""/>
			    <input id="t_companyname" name="companyname" class="{required:true} inputstyle disabled" readonly="true" />
            </li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>一级部门名称：</span>
			    <input id="t_deptid" name="deptid" type="hidden" value=""/>
			    <input id="t_deptname" name="deptname" class="{required:true} inputselectstyle "  onclick="chooseOneCompanyDeptTree('#t_deptid','#t_deptname',$('#t_companyid').val(),callbackDept1);"/>
			    <div class="search-trigger" onclick="chooseOneCompanyDeptTree('#t_deptid','#t_deptname',$('#t_companyid').val(),callbackDept1);"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span>二级部门名称：</span>
			    <input id="t_pdeptid" name="pdeptid" type="hidden" value=""/>
			    <input id="t_pdeptname" name="pdeptname" class="inputselectstyle" onclick="choosePDeptTree('#t_pdeptid','#t_pdeptname',$('#t_deptid').val(),callbackDept2);"/>
			    <div class="search-trigger" onclick="choosePDeptTree('#t_pdeptid','#t_pdeptname',$('#t_deptid').val(),callbackDept2);"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>岗位：</span>
				<input id="t_postid" name="postid" type="hidden" value=""/>
			    <input id="t_postname" name="postname" class="{required:true} inputselectstyle" onclick="choosePostTree('#t_postid','#t_postname',$('#t_pdeptid').val()==''?$('#t_deptid').val():$('#t_pdeptid').val(),callbackPost);"/>
			    <div class="select-trigger" onclick="choosePostTree('#t_postid','#t_postname',$('#t_pdeptid').val()==''?$('#t_deptid').val():$('#t_pdeptid').val(),callbackPost);"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>编制类型：</span>
				<input id="t_quotaid" name="quotaid" type="hidden"/>
				<input id="t_quotaname" name="quotaname" class="{required:true} inputselectstyle"  <div class="search-trigger" onclick="chooseQuotaTree('#t_quotaid','#t_quotaname',$('#t_postid').val());"/>
		    	<div class="search-trigger" onclick="chooseQuotaTree('#t_quotaid','#t_quotaname',$('#t_postid').val());"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>职级：</span>
				<input id="t_rankid" name="rankid" type="hidden" value=""/>
				<input id="t_rankname" name="rankname" class="{required:true} inputselectstyle"  onclick="chooseRankTree('#t_rankid','#t_rankname',$('#t_companyid').val());"/>
				<div class="search-trigger" onclick="chooseRankTree('#t_rankid','#t_rankname',$('#t_companyid').val());"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>计划到岗时间：</span>
				<input id="t_hillockdate" name="hillockdate" class="{required:true,dateISO:true}  inputselectstyle datepicker"/>
		    	<div class="date-trigger" onclick="$('#t_hillockdate').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>细分人数：</span>
				<input id="remainnum" name="remainnum" class="{required:true,number:true,maxlength:38}  inputstyle"  onblur="checkRemainNum();"/>
			</li>
		</ul>
	</form>
</div>



</body>
</html>
