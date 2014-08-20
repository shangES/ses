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
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
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
<script type="text/javascript">
$(document).ready(function() {
	
	
	//加载布局
	$('#myContent').layout({
        west: {size:"300",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
	loadGrid();
	loadTab();
	
 	
  	//加载树
 	buildRoleTree();
	//tab页
	
  	
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
        //节点处理
   	var nameValue=data.rolename;
    	
   	if(add){//新增
   		if(selectNode!=null&&selectNode.id!=null)
  			{selectNode=selectNode.getParentNode();
    		var newNode = {id:data.roleid, name:nameValue,iconSkin:'role'};
       		zTree.addNodes(selectNode, newNode);
      		}else {
   		}
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







//树
var zTree;
//选中节点
var selectNode=null;
function buildRoleTree(){
	var setting = {callback:{beforeClick: function(treeId, treeNode){
		selectNode=treeNode;
		mygrid.reload();
		formDisabled(true);
		loadData(selectNode.id);
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
    		if(tabIndex==0){
    			if(selectNode==null){
	    			loadData(null);
	    			//loadGrid();
	    		}
	    		else {
	    			loadData(selectNode.id);
	    			//loadGrid();
	    		}
    		}else if(tabIndex==1){
    			buildFunctionTree();
    		}else if(tabIndex==2){
    			buildUserTree();
    		}else if(tabIndex==4){
	    		if(selectNode==null){
	    			loadData(null);
	    		}
	    		else {
	    			loadData(selectNode.id);
	    		}
    		}
    	
    	}
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
		{$.getJSON("system/getRoleById.do", {id:tid}, function(data) {
			for (var key in data) {
		        var el = $('#' + key);
		        if(el) 
		            el.val(data[key]);
		    }
			
			
    		//刷新关系
    		if(tabIndex==0){
				//出现按钮组一
    			
    			$('#b1_Role').show();
    			$('#b2_Role').hide();
    			//控制新增编辑
    			
   				$('#edit_role').show();
   				$('#del_role').show();
    		}else if(tabIndex==1){
    			buildFunctionTree();
    		}else if(tabIndex==2){
    			buildUserTree();
    		}else if(tabIndex==4){
    			//出现按钮组一
    			//alert("SSSS");
    			
    			$('#b1').show();
    			$('#b2').hide();
    			//控制新增编辑
    			
   				$('#edit').show();
   				$('#new').show();
   				$('#del').show();
    		 
    		}
		})}else {
			if(tabIndex==0){
				//出现按钮组一
    			
    			$('#b1_Role').show();
    			$('#b2_Role').hide();
    			//控制新增编辑
    			
   				$('#edit_role').show();
   				$('#del_role').show();
    		}else if(tabIndex==1){
				$("#funTree").html(null);
    			return;
    		}else if(tabIndex==2){
    			$("#userTree").html(null);
    			return;
    		}else if(tabIndex==4){
    			$('#b1').show();
    			$('#b2').hide();
   				$('#new').show();
   				$('#edit').hide();
   				$('#del').hide();
    		 
    		}
		};
	
}







//新增
var add=false;
function addNode(){
	if(selectNode==null){
		alert("请先选择节点");
		return;
	}
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
/* var userguid = $("#userguid").val();
alert("SSSSSSSSS");
alert("|"+userguid+"|"); */
function editRoleNode(){
	
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	if(cords.length>0){
		for(var i=0;i<cords.length;i++){
			var obj=cords[i];
			array.push(obj.userguid);
			//alert("QQ"+obj.userguid+"QQ");
		}
		openRole(array.toString());
	}else{
		alert("请选择要修改的用户");
	}
	//alert("asdasdas");
	//openRole(array);
	formDisabled(false);
}

//岗位授权
var roleTree;
function openRole(userguid){
	$("#roleAudit").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:500,
		buttons: {
			"确定": function() {
				var nodes=roleTree.getCheckedNodes(true);
				var array=new Array();
				if(nodes.length>0){
				for(var i=0;i<nodes.length;i++){
					var node=nodes[i];
					if(node.id!=null&&node.id!=''){
						
						var obj={};
						obj.roleid=node.id
						obj.userguid=userguid;
						array.push(obj);
					}
				}
				
				//参数
				var data={ids:userguid,list:array};
				$.ajax({  
			        url: "system/saveUserRoleBatch.do",  
			        contentType: "application/json; charset=utf-8",  
			        type: "POST",  
			        dataType: "json",  
			        data: JSON.stringify(data),
			        success: function(result) { 
			        	alert('授权成功！');
			        	$("#roleAudit").dialog("close");
			        	mygrid.reload();
			        }
			    });
				}else{
					alert("请选择角色");
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},
		close: function() {
			
		},
		open:function(){
			$("#roleTree").html(null);
		    $.getJSON("system/buildRoleCheckTree.do",{userguid:null}, function(tdata) {
		    	//配置项
		    	var setting = {check: {
					enable: true
				}};
		    	roleTree = $.fn.zTree.init($("#roleTree"),setting, tdata);
		    }); 
		}
	});
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
//删除用户角色
function delRoleNode(){
	
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	if(cords.length>0){
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.userguid);
	}
	if(!confirm('确认要删除吗？')){
		return;
	}
	//参数
	var data={ids:array.toString(),roleid:selectNode.id};
	$.ajax({  
        url: "system/delUserRoleByUserId.do",  
        contentType: "application/json; charset=utf-8",  
        type: "POST",  
        dataType: "json",  
        data: JSON.stringify(data),
        success: function(result) { 
        	alert('删除成功！');
        	//$("#roleAudit").dialog("close");
        	mygrid.reload();
        }
    });
	}else{
		alert("请选择删除数据");
	}
}

//参数设置
var pam=null;
function initPagePam(){
	//alert("VVVVVV");	
	pam={};
	pam.expAll=0;
	//pam.roleid = $("#roleid").val();
	//alert("|"+pam.roleid+"|");
	if(selectNode!=null&&selectNode.id!=null){
		//alert("wrong");	
		pam.roleid=selectNode.id;
	}
}
</script>

<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	//alert("111");
	var size=getGridSize();
	var wh=$(document.body).outerWidth()-360;
	$("#myTable").css({width:wh,height:495});
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
				{name : 'jobnumber'},
				{name : 'dorder'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:true,headAlign:'center',align:'center',filterable:false},
			{id: 'companyname' , header: "公司名称" , width :250 ,headAlign:'center',align:'left'},
			{id: 'deptname' , header: "部门名称" , width :120 ,headAlign:'center',align:'left'},
			{id: 'postname' , header: "岗位名称" , width :120 ,headAlign:'center',align:'left'},
			{id: 'username' , header: "用户名" , width :80 ,headAlign:'center',align:'left'},
			{id: 'loginname' , header: "登陆名" , width :80 ,headAlign:'center',align:'left'},
			{id: 'rolename' , header: "用户角色" , width :200 ,headAlign:'center',align:'left'},
			{id: 'modimemo' , header: "备注" , width :200 ,headAlign:'center',headAlign:'left',align:'left'}
		];
		
		//alert("QQQ");
	var gridOption={
		id : grid_demo_id,
		loadURL :'system/searchUserByRole.do',
		beforeLoad:function(reqParam){
			//alert("asdas");
			initPagePam();
			reqParam['parameters']=pam;
		},
		width:'100%',
		minHeight:"100%",  //"100%", // 330,
		container : 'gridbox', 
		autoLoad:true,
		stripeRows: true,
		remoteFilter:true,
		showIndexColumn:true,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		pageSize:size,
		skin:getGridSkin(),
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
	if(roleid==null||roleid==''){
		return;
	}
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
					   	<span id="b1_Role" style="display:none;">
							<a id="edit_role" class="btn" href="javascript:editRoleNode();" style="display:none;"><i class="icon icon-pencil"></i><span>修改</span></a>
							<a id="del_role"  class="btn"  href="javascript:delRoleNode();" style="display:none;"><i class="icon icon-trash"></i><span>删除</span></a>
				   		</span>
				   		<span id="b2_Role" style="display:none;">
							<a class="btn"  href="javascript:saveRole();"><i class="icon icon-check"></i><span>保存</span></a>
							<a class="btn"  href="javascript:onCancelRole();"><i class="icon icon-share"></i><span>取消</span></a>
				   		</span>
				   		
				   	</span>
				   	<span id="group1" class="gruop_hidden" style="display:none;">
				   		<a class="btn"  href="javascript:savePostFunction();"><i class="icon icon-check"></i><span>保存</span></a>
				   	</span>
				   	
				   	<span id="group2" class="gruop_hidden" style="display:none;">
						<a class="btn"  href="javascript:saveRoleUser();"><i class="icon icon-check"></i><span>保存</span></a>
				   	</span>
					<span id="group4" class="gruop_hidden">
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
				<div class="ui-layout-center">
					<div id="mytab">
						<ul>
							<li><a href="#tab0">用户列表</a></li>
							<li><a href="#tab1">菜单授权</a></li>
							<li><a href="#tab2">用户授权</a></li>
							<li><a href="#tab3">用户授权新实现</a></li>
							<li><a href="#tab4">基本信息</a></li>
						</ul>
						<div id="tab0">
							<div style="padding-top:5px;">
								<div id="myTable" style="margin:0px auto;">
									<div id="gridbox" ></div>
				                </div>
			                </div>
						</div>
						<div id="tab1" style="display:none;overflow:auto;height:508px;"> 
							<ul id="funTree" class="ztree"></ul>
						</div>
						<div id="tab2" style="display:none;overflow:auto;height:508px;"> 
							<ul id="userTree" class="ztree"></ul>
						</div>
						<div id="tab3" style="display:none;overflow:auto;height:508px;" class="table-wrapper">
							<form action="quota/saveOrUpdateQuota.do" method="post" id="myForm1" class="form">
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
						<div id="tab4">
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
					</div>
				</div>
				
			</div>
		</div>
	</div>
</div>
<div id="roleAudit" style="display:none;" title="角色授权" >
<ul id="roleTree" class="ztree"></ul>
</div>

</body>
</html>
