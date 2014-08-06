<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
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

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.layout.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	
	//tab页
	loadTab();
	
	//加载布局
	$('#myContent').layout({
        west: {size:"300",resizable:false,spacing_open:0,spacing_closed:0}
    });
	$('#myWest').layout({
        north: {size:"28",resizable:false,spacing_open:0,spacing_closed:0}
    });
	
  	//加载树
    buildMyUserTree();
  	
  	
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
        //节点处理
    	var nameValue=data.username;
    	
    	if(add){//新增
    		if(selectNode!=null&&selectNode.code=="user")
    			selectNode=selectNode.getParentNode();
    		var newNode = {id:data.userguid, name:nameValue,iconSkin:"user",code:"user",ename:1};
       		zTree.addNodes(selectNode, newNode);
        }else{
        	selectNode.name = nameValue;
   			zTree.updateNode(selectNode);
   			zTree.selectNode(selectNode);
        }
    	add=false;
    	//不可编辑
    	formDisabled(true);
    	
    	//取消
    	onCancel();
    	
    });
 	//把表单置为不可编辑
    formDisabled(true);
  	
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
    		
    		
    		
    		//崗位樹
			if(tabIndex==1)
    			buildRoleTree();
			else if(tabIndex==2){
				buildCompanyTree();
			}else if(tabIndex == 4){
				buildadepartmentTree();
			}
			else{
				buildaddressTree();
			}
    	}
    });
}




//树
var zTree;
//选中节点
var selectNode=null;
function buildMyUserTree(){
	var setting = {
		async: {
			enable: true,
			url: getUrl
		},
		callback:{
			beforeClick: function(treeId, treeNode){
				$("#myForm").clearForm();
				selectNode=treeNode;
				formDisabled(true);
				//出现按钮组一
				$('#b1').show();
				$('#b2').hide();
				
				
				//只能新增
				if(treeNode.code!='user'){
					//$('#new').show();
					$('#edit').hide();
					$("#recover").hide();
					$("#remove").hide();
					$(".save").hide();
				}else if(treeNode.code=='user'){
					//有效的
					if(treeNode.ename==1){
						$('#edit').show();
						//$('#new').show();
						$("#recover").hide();
						$("#remove").hide();
					}else{
						$('#edit').hide();
						//$('#new').hide();
						$("#recover").show();
						$("#remove").show();
					}
					$(".save").show();
					//取数据
					loadData(treeNode.id);
					
				}else{
					$('#edit').hide();
					//$('#new').hide();
					$("#recover").hide();
					$("#remove").hide();
				}
					
			}
		},
		onAsyncSuccess: onAsyncSuccess,
		onAsyncError: onAsyncError,
		view: {
			fontCss: getDeptFontCss
		}
	};
	$.getJSON("system/buildMyDepartmentTree.do",function(tdata) {
		zTree = $.fn.zTree.init($("#tree"),setting, tdata);
		
		//邦定定位事件
    	$("#myMarker").bind("keyup",markTreeNode);
    	$("#myMarker").bind("blur",markSelectTreeNode);
    });
}


//异步数据
function getUrl(treeId, treeNode) {
	return "system/asyncUserTree.do?deptid=" + treeNode.id;
}
function onAsyncSuccess(event, treeId, treeNode, msg) {
	if(!msg || msg.length == 0) {return;}
	zTree.updateNode(treeNode);
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
}

//======================
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

//==========================================






//取数据
function loadData(tid) {
	$("#myForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("system/getUserById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#' + key);
			        if(el) 
			            el.val(data[key]);
			    }
				
				//崗位樹
				if(tabIndex==1)
	    			buildRoleTree();
	    		else if(tabIndex==2)
	    			buildCompanyTree();
	    		else if(tabIndex==4)
	    			buildadepartmentTree();
	    		else{
	    			buildaddressTree();
	    		}
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
	
	$('#isadmin').val(0);
	$('#state').val(1);
	
	//所属组织
	var srcNode=selectNode;
	if(srcNode!=null&&srcNode.code=="user")
		srcNode=srcNode.getParentNode();
	$("#orgguid").val(srcNode.id);
	$("#orgname").val(srcNode.name);
}




//编辑
function editNode(){
	$('#b1').hide();
	$('#b2').show();
	formDisabled(false,'#myPassword');
}







//取消
function onCancel(){
	formDisabled(true,'#myPassword');
	
	//取数据
	if(selectNode!=null){
		if(selectNode.code=='user'){
			loadData(selectNode.id);
			$('#b1').show();
			$('#b2').hide();
			
			$('#edit').show();
			//$('#new').show();
			$("#recover").hide();
			$("#remove").hide();
		}else{
			$('#b1').show();
			$('#b2').hide();
			
			//$('#new').show();
			$('#edit').hide();
			$("#recover").hide();
			$("#remove").hide();
		}
	}
		
}


//删除
function del(){
	if(selectNode==null)
		return;
	if(!confirm('确认要删除选中的用户吗？')){
		return;
	}
	$.post("system/delUserById.do",{userguid:selectNode.id}, function() {
		zTree.removeNode(selectNode);
		$("#myForm").clearForm();
		
		$('#b1').hide();
		$('#b2').hide();
		
		formDisabled(true);
  });
}






//保存
function save(){
	$('#myForm').submit();
}



//检查登陆名
function checklogname(){
	var userguid=$("#userguid").val();
	var loginname=$("#loginname").val();
	if(loginname!=null&&loginname!=''&&loginname!='null')
	$.post("system/getUserByLoginName.do",{name:loginname}, function(data) {
		if(data==null||data=='')return;
		
		if((userguid==null||userguid=='')||(userguid!=data.userguid)){
			alert("登陆名已经存在,请从新输入！");
			$("#loginname").val(null);
		}
	});
}



//导出
function expGrid(){
	window.open("system/exportUser.do");
}

</script>







<script type="text/javascript">
//崗位树
var roleTree;
function buildRoleTree(){
	var userguid= $("#userguid").val();
	if(userguid==null||userguid=='')
		return;
	
	$("#roleTree").html(null);
    $.getJSON("system/buildRoleCheckTree.do",{userguid:userguid}, function(tdata) {
    	//配置项
    	var setting = {check: {
			enable: true
		}};
    	roleTree = $.fn.zTree.init($("#roleTree"),setting, tdata);
    });
}

//保存关系
function saveUserPost(){
	var userguid= $("#userguid").val();
	if(userguid==null||userguid=='')
		return;
	
	var nodes=roleTree.getCheckedNodes(true);
	var array=new Array();
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
	var data={userguid:userguid,list:array};
	$.ajax({  
        url: "system/saveUserRole.do",  
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
//公司树
var companyTree;
function buildCompanyTree(){
	var userguid= $("#userguid").val();
	if(userguid==null||userguid=='')
		return;
	
	$("#companyTree").html(null);
    $.getJSON("system/buildCompanyCheckTree.do",{userguid:userguid}, function(tdata) {
    	//配置项
    	var setting = {check: {
			enable: true,
			chkboxType: { "Y": "", "N": "" }
		}};
    	companyTree = $.fn.zTree.init($("#companyTree"),setting, tdata);
    });
}

//保存关系
function saveUserCompany(){
	var userguid= $("#userguid").val();
	if(userguid==null||userguid=='')
		return;
	
	var nodes=companyTree.getCheckedNodes(true);
	var array=new Array();
	for(var i=0;i<nodes.length;i++){
		var node=nodes[i];
		if(node.id!=null&&node.id!=''){
			
			var obj={};
			obj.userguid=userguid;
			obj.companyid=node.id;
			obj.isdefault=1;
			array.push(obj);
		}
	}
	
	//参数
	var data={userguid:userguid,list:array};
	$.ajax({  
        url: "system/saveUserCompany.do",  
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
//通讯录树
var addressTree;
function buildaddressTree(){
	var userguid= $("#userguid").val();
	if(userguid==null||userguid=='')
		return;
	
	$("#addressTree").html(null);
    $.getJSON("system/buildAddressCompanyCheckTree.do",{userguid:userguid}, function(tdata) {
    	//配置项
    	var setting = {check: {
			enable: true,
			chkboxType: { "Y": "s", "N": "" }
		}};
    	addressTree = $.fn.zTree.init($("#addressTree"),setting, tdata);
    });
}

//保存关系
function saveUserAddressCompany(){
	var userguid= $("#userguid").val();
	if(userguid==null||userguid=='')
		return;
	
	
	var array=new Array();
	var checknodes=addressTree.getCheckedNodes(true);
	var unchecknodes=addressTree.getCheckedNodes(false);
	
	if(unchecknodes.length==0){
		var obj={};
		obj.userguid=userguid;
		array.push(obj);
	}else{
		for(var i=0;i<checknodes.length;i++){
			var node=checknodes[i];
			if(node.id!=null&&node.id!=''){
				var obj={};
				obj.userguid=userguid;
				obj.companyid=node.id;
				array.push(obj);
			}
		}
	}
	
	
	
	//参数
	var data={userguid:userguid,list:array};
	$.ajax({  
        url: "system/saveUserAddressCompany.do",  
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
//部门树
var departmentTree;
function buildadepartmentTree(){
	var userguid= $("#userguid").val();
	if(userguid==null||userguid=='')
		return;
	
	$("#departmentTree").html(null);
    $.getJSON("system/buildUserDepartmentCheckTree.do",{userguid:userguid}, function(tdata) {
    	//配置项
    	var setting = {check: {
			enable: true,
			chkboxType: { "Y": "s", "N": "" }
		}};
    	departmentTree = $.fn.zTree.init($("#departmentTree"),setting, tdata);
    });
}

//保存关系
function saveUserdepartment(){
	var userguid= $("#userguid").val();
	if(userguid==null||userguid=='')
		return;
	
	
	var array=new Array();
	var checknodes=departmentTree.getCheckedNodes(true);
	var unchecknodes=departmentTree.getCheckedNodes(false);
	
	if(unchecknodes.length==0){
		var obj={};
		obj.userguid=userguid;
		array.push(obj);
	}else{
		for(var i=0;i<checknodes.length;i++){
			var node=checknodes[i];
			if(node.id!=null&&node.id!=''){
				var obj={};
				obj.userguid=userguid;
				obj.deptguid=node.id;
				array.push(obj);
			}
		}
	}
	
	
	
	//参数
	var data={userguid:userguid,list:array};
	$.ajax({  
        url: "system/saveUserDepartment.do",  
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



</head>

<body>


<div class="table">
			<div class="table-bar">
				<div class="table-title">
					表格名称
				</div>
				<div class="table-ctrl">
					<!-- 基本信息-->
					<span id="group0" class="gruop_hidden">
					   	<span id="b1">
					   		<a class="btn" href="javascript:expGrid();"><i class="icon icon-download"></i><span>导出</span></a>
							<!-- <a id="new" class="btn" href="javascript:addNode();" style="display:none;"><i class="icon icon-plus"></i><span>新增</span></a> -->
							<a id="edit" class="btn" href="javascript:editNode();" style="display:none;"><i class="icon icon-pencil"></i><span>修改</span></a>
							<a id="recover" class="btn" href="javascript:recoverUser(1);"  style="display:none;"><i class="icon icon-repeat"></i><span>恢复</span></a>
							<a id="remove"  class="btn" href="javascript:del();"  style="display:none;"><i class="icon icon-remove"></i><span>删除</span></a>
				   		</span>
				   		<span id="b2" style="display:none;">
							<a class="btn"  href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
							<a class="btn"  href="javascript:onCancel();"><i class="icon icon-share"></i><span>取消</span></a>
				   		</span>
				   	</span>
				   	<!-- 角色赋权-->
				   	<span id="group1" class="gruop_hidden" style="display:none;">
				   		<a class="btn save"  href="javascript:saveUserPost();"><i class="icon icon-check"></i><span>保存</span></a>
				   	</span>
				   	<!-- 公司赋权-->
				   	<span id="group2" class="gruop_hidden" style="display:none;">
						<a class="btn save"  href="javascript:saveUserCompany();"><i class="icon icon-check"></i><span>保存</span></a>
				   	</span>
				   	<span id="group4"class="gruop_hidden" style="display:none;">
						<a class="btn save"  href="javascript:saveUserdepartment();"><i class="icon icon-check"></i><span>保存</span></a>
				   	</span>
				   	<!-- 通讯录赋权-->
				   	<span id="group3" class="gruop_hidden" style="display:none;">
						<a class="btn save"  href="javascript:saveUserAddressCompany();"><i class="icon icon-check"></i><span>保存</span></a>
				   	</span>
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
					<div class="ui-layout-center" style="border:0px;">
						<ul id="tree" class="ztree"></ul>
					</div>
				</div>
				<div class="ui-layout-center">
					<div id="mytab">
						<ul>
							<li><a href="#tab0">基本信息</a></li>
							<li><a href="#tab1">角色赋权</a></li>
							<li><a href="#tab2">公司赋权</a></li>
							<li><a href="#tab3">通讯录赋权</a></li>
							<li><a href="#tab4">部门赋权</a></li>
						</ul>
						<div id="tab0">
							<form action="system/saveOrUpdateUser.do" method="post" id="myForm" class="form">
								<input id="userguid" name="userguid" type="hidden" value=""/>
								<input id="state" name="state" type="hidden" value="1"/>
								<input id="isadmin" name="isadmin" type="hidden" value="0"/>
								
								
								<fieldset>
									<legend>基本信息</legend>
									<ul>
										<li>
										    <span><em class="red">* </em>用户名：</span>
										    <input id="employeeid" name="employeeid" type="hidden" value=""/>
										    <input id="username" name="username" class="inputstyle"/>
										</li>
									</ul>
									<ul>
										<li>
										    <span><em class="red">* </em>登录名：</span>
										    <input id="loginname" name="loginname" class="{required:true,maxlength:20} inputstyle" onblur="checklogname();"/>
										</li>
									</ul>
									<ul id="myPassword">
										<li>
										    <span><em class="red">* </em>密码：</span>
										    <input id="password" name="password" type="password" class="{required:true,maxlength:5} inputstyle"/>
										</li>
									</ul>
									<ul>
										<li>
										    <span>备注：</span>
										    <textarea id="memo" name="memo"  rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
										</li>
									</ul>
								</fieldset>
					       </form>
						</div>
						<div id="tab1" style="display:none;">
							<ul id="roleTree" class="ztree"></ul>
						</div>
						<div id="tab2" style="display:none;">
							<ul id="companyTree" class="ztree"></ul>
						</div>
						<div id="tab3" style="display:none;">
							<ul id="addressTree" class="ztree"></ul>
						</div>
						<div id="tab4" style="display:none;">
							<ul id="departmentTree" class="ztree"></ul>
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>
