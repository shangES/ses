<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
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
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
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
<script type="text/javascript" src="pages/bpmn/workflow.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//tab页
	loadTab();
	
	//加载布局
	$('#myContent').layout({
        west: {size:"300",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
    
 	loadGrid();
  	//加载树
 	buildRoleTree();
  	
  	
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
        //节点处理
    	var nameValue=data.rolename;
    	
    	if(add){//新增
    		if(selectNode!=null&&selectNode.id!=null)
    			selectNode=selectNode.getParentNode();
    		var newNode = {id:data.roleid, name:nameValue,iconSkin:'role'};
       		zTree.addNodes(selectNode, newNode);
        }else{
        	selectNode.name = nameValue;
   			zTree.updateNode(selectNode);
   			zTree.selectNode(selectNode);
        }
    	add=false;
    	//不可编辑
    	formDisabled(true);
    	//取消状态
    	onCancel();
    });
 	//把表单置为不可编辑
    formDisabled(true);
    
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});



//tab页
var tabIndex=0;
function loadTab(){
	$("#mytab").tabs({
    	select: function(event, ui) {
    		tabIndex=ui.index;
    		$(".gruop_hidden").hide();
    		$("#group"+tabIndex).show();
    		$("#tab"+tabIndex).show();
    		

    		//刷新关系
    		if(tabIndex==1){
    			buildFunctionTree();
    		}else if(tabIndex==2){
    			buildUserTree();
    		}
    	}
    });
}




//树
var zTree;
//选中节点
var selectNode=null;
function buildRoleTree(){
	var setting = {callback:{beforeClick: function(treeId, treeNode){
		$("#myForm").clearForm();
		selectNode=treeNode;
		formDisabled(true);
		//出现按钮组一
		$('#b1').show();
		$('#b2').hide();
		//控制新增编辑
		if(treeNode.id==null){
			$('#new').show();
			$('#edit').hide();
			$('#del').hide();
		}else{
			$('#edit').show();
			$('#new').show();
			$('#del').show();

			//取数据
			loadData(treeNode.id);
		}
		mygrid.reload();
	}},
	view: {
		fontCss: getDeptFontCss
	}
	};
	$.getJSON("system/buildRoleTree.do",function(tdata) {
		zTree = $.fn.zTree.init($("#tree"),setting, tdata);
		
		//邦定定位事件
    	$("#myMarker").bind("keyup",markTreeNode);
    	$("#myMarker").bind("blur",markSelectTreeNode);
    });
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

function getDeptFontCss(treeId, treeNode) {
	return (!!treeNode.highlight) ? {color:"#ff0000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
}

//取数据
function loadData(tid) {
	$("#myForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("system/getRoleById.do", {id:tid}, function(data) {
			for (var key in data) {
		        var el = $('#' + key);
		        if(el) 
		            el.val(data[key]);
		    }
			
			
    		//刷新关系
    		if(tabIndex==1){
    			buildFunctionTree();
    		}else if(tabIndex==2){
    			buildUserTree();
    		}
		});
}







//新增
var add=false;
function addNode(){
	$('#b1').hide();
	$('#b2').show();
	$("#myForm").clearForm();
	formDisabled(false);
	add=true;
	
	$('#state').val(1);
}




//编辑
function editNode(){
	$('#b1').hide();
	$('#b2').show();
	formDisabled(false);
}




//保存
function save(){
	$('#myForm').submit();
}




//取消
function onCancel(){
	//取数据
	if(selectNode!=null)
		loadData(selectNode.id);
	
	formDisabled(true);
	$('#b1').show();
	$('#b2').hide();
	
	$('#del').show();
	$('#edit').show();
	$('#new').show();
}


//删除
function delNode(){
	if(selectNode==null)
		return;
	if(!confirm('确认要删除吗？')){
		return;
	}
	$.post("system/delRoleById.do",{id:selectNode.id}, function() {
		zTree.removeNode(selectNode);
		$("#myForm").clearForm();
			
 		$('#b1').show();
		$('#b2').hide();
		
		$('#del').hide();
		$('#edit').hide();
		$('#new').hide();
 		formDisabled(true);
    });
}

//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	
	pam.roleid = $("#roleid").val();
	
	
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
				{name : 'userguid'},
				{name : 'employeeid'},
				{name : 'loginname'},
				{name : 'password'},
				{name : 'isadmin'},
				{name : 'state'},
				{name : 'memo'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'},
				{name : 'companyname'},
				{name : 'deptname'},
				{name : 'postname'},
				{name : 'username'},
				{name : 'rolename'},
				{name : 'jobnumber'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},              
			{id: 'companyname' , header: "公司名称" , width :250 ,headAlign:'center',align:'left'},              
			{id: 'companyname' , header: "公司名称" , width :250 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :120 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称" , width :120 ,headAlign:'center',align:'left'},
			{id: 'username' , header: "用户名" , width :80 ,headAlign:'center',align:'left'},
			{id: 'loginname' , header: "登陆名" , width :80 ,headAlign:'center',align:'left'},
			{id: 'rolename' , header: "用户角色" , width :200 ,headAlign:'center',align:'left'},
			{id: 'modimemo' , header: "备注" , width :200 ,headAlign:'center',headAlign:'left',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'system/searchUserByRole.do',
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

<script type="text/javascript">
//菜单树
var funTree;
function buildFunctionTree(){
	var roleid= $("#roleid").val();
	if(roleid==null||roleid=='')
		return;
	
	$("#funTree").html(null);
    $.getJSON("system/buildRoleRightTree.do",{roleid:roleid}, function(tdata) {
    	//配置项
    	var setting = {check: {
			enable: true
		}};
    	funTree = $.fn.zTree.init($("#funTree"),setting, tdata);
    });
}

//保存关系
function savePostFunction(){
	var roleid= $("#roleid").val();
	if(roleid==null||roleid=='')
		return;
	
	var nodes=funTree.getCheckedNodes(true);
	
	var array=new Array();
	for(var i=0;i<nodes.length;i++){
		var node=nodes[i];
		if(node.id!=null&&node.id!=''){
			var obj={};
			obj.roleid=roleid;
			obj.funid=node.id;
			array.push(obj);
		}
	}
	
	//参数
	var data={roleid:roleid,list:array};
	$.ajax({  
        url: "system/saveRoleFunction.do",  
        contentType: "application/json; charset=utf-8",  
        type: "POST",  
        dataType: "json",  
        data: JSON.stringify(data),
        success: function(result) { 
        	alert('授权成功！');
        }
    });
}

</script>

<script type="text/javascript">
//用户树
var userTree;
function buildUserTree(){
	var roleid= $("#roleid").val();
	if(roleid==null||roleid=='')
		return;
	
	$("#userTree").html(null);
    $.getJSON("system/buildRoleUserTree.do",{roleid:roleid}, function(tdata) {
    	//配置项
    	var setting = {
    			async: {
    				enable: true,
    				url: getUrl
    			},
    			check: {
		    		enable: true
				},
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError
    		};
    	userTree = $.fn.zTree.init($("#userTree"),setting, tdata);
    });
}



//异步数据
function getUrl(treeId, treeNode) {
	return "system/asyncRoleUserTree.do?deptid=" + treeNode.id+"&roleid="+selectNode.id;
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
	if(!msg || msg.length == 0) {return;}
	zTree.updateNode(treeNode);
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
}



//保存关系
function saveRoleUser(){
	var roleid= $("#roleid").val();
	if(roleid==null||roleid=='')
		return;
	
	var array=new Array();
	var checknodes=userTree.getCheckedNodes(true);
	var unchecknodes=userTree.getCheckedNodes(false);
	//打勾的节点
	for(var i=0;i<checknodes.length;i++){
		var node=checknodes[i];
		if(node.id!=null&&node.id!=''&&node.code=='user'){
			var obj={};
			obj.roleid=roleid;
			obj.userguid=node.id;
			obj.checked=node.checked;
			array.push(obj);
		}
	}
	
	//没有打勾的节点
	for(var i=0;i<unchecknodes.length;i++){
		var node=unchecknodes[i];
		if(node.id!=null&&node.id!=''&&node.code=='user'){
			var obj={};
			obj.roleid=roleid;
			obj.userguid=node.id;
			obj.checked=node.checked;
			array.push(obj);
		}
	}
	
	//参数
	var data={roleid:roleid,list:array};
	$.ajax({  
        url: "system/saveRoleUser.do",  
        contentType: "application/json; charset=utf-8",  
        type: "POST",  
        dataType: "json",  
        data: JSON.stringify(data),
        success: function(result) { 
        	alert('授权成功！');
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
//岗位回调
function callbackpost(){
	var postid=$("#postid").val();
	var quotaid=$("#quotaid").val();
	var state=$("#state").val();
	var budgettype=$("#budgettype").val();
	$.post("quota/checkQuotaByPostIdAndBudgettype.do",{quotaid:quotaid,postid:postid,budgettype:budgettype,state:state}, function(data) {
		if(data!=null&&data!=""){
			alert(data);
			$("#postid").val(null);
			$("#postname").val(null);
		}
    });
	
}


</script>


</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>角色管理</h3>
	</div>
	
	<div class="sort-cont sort-table">
		<div class="table">
			<div class="table-bar">
				<div class="table-title">
					表格名称
				</div>
				<div class="table-ctrl">
					<span id="group0" class="gruop_hidden">
					   	<span id="b1" style="display:none;">
							<a id="new" class="btn" href="javascript:addNode();" style="display:none;"><i class="icon icon-plus"></i><span>新增</span></a>
							<a id="edit" class="btn" href="javascript:editNode();" style="display:none;"><i class="icon icon-pencil"></i><span>修改</span></a>
							<a id="del"  class="btn"  href="javascript:delNode();" style="display:none;"><i class="icon icon-trash"></i><span>删除</span></a>
				   		</span>
				   		<span id="b2" style="display:none;">
							<a class="btn"  href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
							<a class="btn"  href="javascript:onCancel();"><i class="icon icon-share"></i><span>取消</span></a>
				   		</span>
				   		
				   	</span>
				   	<span id="group1" class="gruop_hidden" style="display:none;">
				   		<a class="btn"  href="javascript:savePostFunction();"><i class="icon icon-check"></i><span>保存</span></a>
				   	</span>
				   	
				   	<span id="group2" class="gruop_hidden" style="display:none;">
						<a class="btn"  href="javascript:saveRoleUser();"><i class="icon icon-check"></i><span>保存</span></a>
				   	</span>
				</div>
			</div>
			<div class="table-wrapper" id="myContent" style="height:550px;">
				<div class="ui-layout-west" id="myWest" style="overflow:auto;">
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
					<div id="mytab">
						<ul>
							<li><a href="#tab0">基本信息</a></li>
							<li><a href="#tab1">菜单授权</a></li>
							<li><a href="#tab2">用户授权</a></li>
							<li><a href="#tab3">用户授权新实现</a></li>
							<li><a href="#tab4">用户列表</a></li>
						</ul>
						<div id="tab0">
							<form action="system/saveOrUpdateRole.do" method="post" id="myForm" class="form">
								<input id="roleid" name="roleid" type="hidden" value=""/>
								<input id="state" name="state" type="hidden" value="1"/>
								
								<fieldset>
									<legend>基本信息</legend>
									<ul>
										<li>
										    <span><em class="red">* </em>排序号：</span>
										    <input id="dorder" name="dorder" class="{required:true,number:true} inputstyle"/>
										</li>
									</ul>
									<ul>
										<li>
										    <span><em class="red">* </em>角色名称：</span>
										    <input id="rolename" name="rolename" class="{required:true,maxlength:20} inputstyle"/>
										</li>
									</ul>
									<ul>
						                <li>
						                    <span>描述：</span>
						                    <textarea id="description" name="description"  rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
						                </li>
						            </ul>
						   		</fieldset>
					       </form>
						</div>
						<div id="tab1" style="display:none;overflow:auto;height:508px;"> 
							<ul id="funTree" class="ztree"></ul>
						</div>
						<div id="tab2" style="display:none;overflow:auto;height:508px;"> 
							<ul id="userTree" class="ztree"></ul>
						</div>
						<div id="tab3" style="display:none;overflow:auto;height:508px;" class="table-wrapper">
							<form action="quota/saveOrUpdateQuota.do" method="post" id="myForm" class="form">
								<input id="quotaid" name="quotaid" type="hidden" value="" />
								<input id="quotacode" name="quotacode" type="hidden" value="" />
								<input id="state" name="state" type="hidden" value="1" />
								<input id="modiuser" name="modiuser" type="hidden" value="" />
								<input id="modimemo" name="modimemo" type="hidden" value="" />
								<input id="moditimestamp" name="moditimestamp" type="hidden" value="" />
								<input type="hidden" id="runprocess" name="runprocess" value="true" />
								
								<!-- 流程信息 -->
								<input id="variables_commit" name="variables['rate']" type="hidden" value="2" />
								<input id="variables_commitNum" name="variables['commitNum']" type="hidden" value="1" />
								<ul>
									<li>
						                <span><em class="red">* </em>公司名称：</span>
						                <input id="companyid" name="companyid" type="hidden" value=""/>
									    <input id="companyname" name="companyname" class="{required:true} inputselectstyle" onclick="$('#companyname').attr('readonly',false);chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);" />
									    <div class="search-trigger" onclick="$('#companyname').attr('readonly',false);chooseMyHasCompanyTree('#companyid','#companyname',callBackCompany);"/>
						            </li>
									<li>
									    <span><em class="red">* </em>部门名称：</span>
									    <input id="deptid" name="deptid" type="hidden" value=""/>
									    <input id="deptname" name="deptname" class="{required:true} inputselectstyle" onclick="$('#deptname').attr('readonly',false);chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
									    <div class="search-trigger" onclick="$('#deptname').attr('readonly',false);chooseOneCompanyDeptTree('#deptid','#deptname',$('#companyid').val(),callbackDept);"/>
									</li>
									<li>
									    <span><em class="red">* </em>岗位名称：</span>
									    <input id="postid" name="postid" type="hidden" value=""/>
									    <input id="postname" name="postname" class="inputselectstyle" onclick="$('#postname').attr('readonly',false);choosePostTree('#postid','#postname',$('#deptid').val(),callbackpost);"/>
									    <div class="select-trigger" onclick="$('#postname').attr('readonly',false);choosePostTree('#postid','#postname',$('#deptid').val());" />
									</li>
								</ul>
  							</form>
						</div>
						<div id="tab4" class="ui-layout-center" style="display:none;overflow:auto;height:508px;"> 
							<div id="gridbox"></div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</div>


</body>
</html>