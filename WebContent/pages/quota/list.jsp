<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>编制管理</title>
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
$(document).ready(function() {
	
	//加载布局
	$('#myContent').layout({
        west: {size:"300",resizable:false,spacing_open:5,spacing_closed:5}
    });
	
	$('#myWest').layout({
        north: {size:"28",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
	//加载信息
	loadGrid();
	
	//树的展示
	buildDepartmentTree();
	
	
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





//树
var zTree;
//选中节点
var selectNode=null;
function buildDepartmentTree(){
	//配置
	var setting = {
				callback:{
					beforeClick: function(treeId, treeNode) {
						$("#myForm").clearForm();
						selectNode=treeNode;
						
						mygrid.reload();
					}
				},
				view: {
					fontCss: getDeptFontCss
				}
			};

	
    $.getJSON("department/buildMyDepartmentTree.do",{valid:1}, function(tdata) {
    	zTree = $.fn.zTree.init($("#tree"),setting, tdata);
    	
    	//邦定定位事件
    	$("#myMarker").bind("keyup",markTreeNode);
    	$("#myMarker").bind("blur",markSelectTreeNode);
    });
}



//节点搜索定位
function markSelectTreeNode(){
	var value="";
	value = $.trim($("#myMarker").val());
	if (value != ""){
		nodeList = zTree.getNodesByParamFuzzy("name", value);
		if(nodeList.length<=0)
			return;
		for( var i=0, l=nodeList.length; i<l; i++) {
			var node=nodeList[i];
			if(i==0){
				zTree.selectNode(node);
			}
		}
	}
}


//节点搜索变色
function markTreeNode(e) {
	if(!nodeState){
		nodeState=true;
		var value="";
		value = $.trim($("#myMarker").val());
		
		changeNodesColor(false);
		if (value === ""){
			nodeState=false;
			return;
		}
		nodeList = zTree.getNodesByParamFuzzy("name", value);
		changeNodesColor(true);
		nodeState=false;
	}
}
function changeNodesColor(highlight) {
	if(nodeList.length<=0)
		return;
	for( var i=0, l=nodeList.length; i<l; i++) {
		var node=nodeList[i];
		node.highlight = highlight;
		zTree.updateNode(nodeList[i]);
	}
}
function getDeptFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#ff0000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

//===============






//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	
	pam.companyid=$("#companyid").val();
	pam.deptid=$("#deptid").val();
	pam.postname=$("#postname").val();
	
	
	if(selectNode!=null){
		if(selectNode.code=='dept'){
			pam.deptcode=selectNode.deptcode;
		}else if(selectNode.code='company'){
			pam.companycode=selectNode.companycode;
		}
	}
	
}






//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.quotaid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
   	$.post("quota/delQuotaByQuotaid.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
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


//锁定人数
var addQuotaOperateForm=null;
function lockQuota(id,vacancynumber){
	$("#addQuotaOperateWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(addQuotaOperateForm.form){
					var operatenum=$("#operatenum").val();
					if(operatenum>vacancynumber){
						alert("锁定人数过多,请重新输入!");
						 $("#operatenum").focus();
						 return;
					}else{
						$("#addQuotaOperateForm").submit();
					}
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addQuotaOperateForm").clearForm();
			$("#quotaid").val(id);
			$("#operatestate").val(3);
			$("#modiuser").val("${username}");
			addQuotaOperateForm=$("#addQuotaOperateForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		mygrid.reload();
			    		$("#addQuotaOperateWindow").dialog("close");
			        });
				}
			});
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
	//var size=14;
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'quotacode'},
				{name : 'companyname'},
				{name : 'deptname'},
				{name : 'postname'},
				{name : 'budgettypename'},
				{name : 'budgetnumber'},
				{name : 'employednumber'},
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
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'quotaid' , header: "操作" , width :90 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				var htm='';
	        		htm+='<a href="javascript:lockQuota(\''+record.quotaid+'\',\''+record.vacancynumber+'\');" title="锁定" style="display:${lock?'':'none'}">';
	        		htm+='锁定';
	        		htm+='</a>';
	        		htm+='&nbsp;';
					htm+='&nbsp;';
					if(record.state==2&&record.assignee=='${userid}'){
						htm+='<a href="javascript:convertView(\'quota.do?page=tab&id='+record.quotaid+'&taskid='+record.taskid+'\')">审核</a>';
						htm+='&nbsp;';
						htm+='&nbsp;';
						//return '<a href="javascript:convertView(\'recruitprogram.do?page=tab&id='+record.recruitprogramguid+'&taskid='+record.taskid+'\')">审核</a>';
					}
        		return htm;
			}},
			{id: 'quotacode' , header: "编号", width :100 ,headAlign:'center',align:'center'},
			{id: 'companyname' , header: "公司名称" , width :250 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称", width :300 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称", width :140 ,headAlign:'center',align:'left'},
			{id: 'budgettypename' , header: "编制类别" ,headAlign:'center',align:'center', width :100},
      		{id: 'budgetnumber' , header: "编制人数",headAlign:'center',align:'center', width :80,number:true},
      		{id: 'employednumber' , header: "在岗人数",headAlign:'center',align:'center', width :80,number:true},
      		{id: 'vacancynumber' , header: "缺编人数",headAlign:'center',align:'center', width :80,number:true},
      		{id: 'operatenum' , header: "锁定人数",headAlign:'center',align:'center', width :80,number:true},
      		{id: 'postnumber' , header: "计划招聘人数",headAlign:'center',align:'center', width :80,number:true},
      		{id: 'budgetdate' , header: "编制时间" ,headAlign:'center', align:'center', width :100},
			{id: 'memo' , header: "备注", width :300 ,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'quota/searchQuota.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'quota/searchQuota.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		exportFileName : '编制信息表.xls',
		width:'99.8%',
		minHeight:"300",  //"100%", // 330,
		container : 'gridbox', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		autoLoad:true,
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
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('quota.do?page=tab&id='+record.quotaid+'&taskid='+record.taskid);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}


function pageResize(height){
	$("#myContent").height(height);
	//计算高度
	_autoHeight();
}
</script>



</head>

<body>

<div class="sort">
	<div class="sort-title">
		<h3>部门编制管理</h3>
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
					<!--  <a class="btn"  ><i class="icon icon-picture"></i><span>总体情况</span></a>-->
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none'}"><i class="icon icon-search"></i><span>搜索</span></a>
			   		<a class="btn" href="javascript:convertView('quota.do?page=import');" style="display:${imp?'':'none'}"><i class="icon icon-qrcode"></i><span>导入</span></a>
				   	<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none'}"><i class="icon icon-list-alt"></i><span>导出</span></a>
			   		<a  class="btn" href="javascript:convertView('quota.do?page=tab');" style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>新增</span></a>
			   		<!--  <a  class="btn" href="javascript:lockQuota();" ><i class="icon icon-lock" ></i><span>锁定</span></a>-->
			   		<!--  <a  class="btn" ><i class="icon icon-repeat"></i><span>解锁</span></a>-->
					<a  class="btn" href="javascript:remove();" style="display:${del?'':'none'}" ><i class="icon icon-remove" ></i><span>删除</span></a>
				</div>
			</div>
			<div class="table-wrapper" id="myContent" style="height:550px;">
				<div class="ui-layout-west" id="myWest">
					<div class="ui-layout-north">
						<table width="100%" height="100%" border="0">
							<tr>
								<td style="padding-left:1px;">
									<input class="myMarker" id="myMarker"/>
								</td>
							</tr>
						</table>
					</div>
					<div class="ui-layout-center" style="border-right:0px;">
						<ul id="tree" class="ztree"></ul>
					</div>
				</div>
				<div class="ui-layout-center" style="overflow:hidden;">
					<div id="gridbox"></div>
				</div>				
			</div>
		</div>
	</div>
</div>

<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>


<!-- 导出窗口 -->
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


<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
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
	</form>
</div>


<!-- 编制锁定窗口 -->
<div id="addQuotaOperateWindow" title="编制锁定" style="display:none;">
	<form action="quota/saveOrUpdateQuotaOperate.do" id="addQuotaOperateForm" class="form" method="post">
		<input type="hidden" id="quotaoperateguid" name="quotaoperateguid" value=""/>
		<input type="hidden" id="quotaid" name="quotaid" value=""/>
		<input type="hidden" id="operatestate" name="operatestate" value="3"/>
		<input id="modiuser" name="modiuser" type="hidden" value="${username}" />
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		
	    <ul>
			<li>
				<span><em class="red">* </em>锁定人数：</span>
				<input id="operatenum" name="operatenum" class="{required:true,number:true,maxlength:4} inputstyle" />
			</li>
		</ul>
		<ul>
			<li>
			    <span>备注：</span>
			    <textarea id="modimemo" name="modimemo" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
			</li>
        </ul>
	</form>
</div>


</body>
</html>