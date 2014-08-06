<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>菜单管理</title>
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
	
	
	
  	//加载树
    buildFunctionTree();
    
  //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
        //节点处理
    	var nameValue=data.labelname;
    	
    	if(add){//新增
    		var newNode = {id:data.funid, name:nameValue,iconSkin:'fun'};
       		zTree.addNodes(selectNode, newNode);
        }else{
        	selectNode.name = nameValue;
        	selectNode.iconSkin='fun';
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
    		
    		//崗位樹
			if(tabIndex==1){
				buildRoleTree();
			}
				
    	}
    });
}

//树
var zTree;
//选中节点
var selectNode=null;
function buildFunctionTree(){
	//配置
	var setting = {callback:{beforeClick: function(treeId, treeNode) {
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

    			//加载数据
    			loadData(treeNode.id);
    		}
    	}
  	}};
	
    $.getJSON("system/buildFunctionTree.do", function(tdata) {
    	zTree = $.fn.zTree.init($("#tree"),setting, tdata);
    	
    	$('#b1').hide();
    	$('#b2').hide();
    });
}




//加载数据
function loadData(tid){
	$("#myForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("system/getFunctionById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#' + key);
			        if(key=='funtype')
			        	$('#' + key+data[key]).attr("checked",true);
			        if(el) 
			            el.val(data[key]);
			    }
				
				//崗位樹
				if(tabIndex==1)
	    			buildRoleTree();
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
	
	
	//设置上级
	if(selectNode!=null){
		var funpid=selectNode.id;
		if(funpid!=null&&funpid!=''){
			$('#funpid').val(funpid);
		}
	}
	
	
	$("#funtype1").attr("checked",true);
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
	if(!confirm('确认要删除吗？')){
		return;
	}
	$.post("system/delFunctionById.do",{id:selectNode.id}, function() {
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

</script>

<script type="text/javascript">
//崗位树
var roleTree;
function buildRoleTree(){
	var funid= $("#funid").val();
	if(funid==null||funid=='')
		return;
	
	$("#roleTree").html(null);
    $.getJSON("system/buildFuntionRoleCheckTree.do",{funid:funid}, function(tdata) {
    	//配置项
    	var setting = {check: {
			enable: true
		}};
    	roleTree = $.fn.zTree.init($("#roleTree"),setting, tdata);
    });
}



//保存关系
function saveFunctionRole(){
	var funid= $("#funid").val();
	if(funid==null||funid=='')
		return;
	
	var nodes=roleTree.getCheckedNodes(true);
	var array=new Array();
	for(var i=0;i<nodes.length;i++){
		var node=nodes[i];
		if(node.id!=null&&node.id!=''){
			
			var obj={};
			obj.roleid=node.id
			obj.funid=funid;
			array.push(obj);
		}
	}
	
	//参数
	var data={funid:funid,list:array};
	$.ajax({  
        url: "system/saveFunctionRole.do",  
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

<div class="sort">
	<div class="sort-title">
		<h3>菜单管理</h3>
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
								<a id="edit" class="btn" href="javascript:editNode()" style="display:none;"><i class="icon icon-pencil"></i><span>修改</span></a>
								<a id="del"  class="btn" href="javascript:delNode()"  style="display:none;"><i class="icon icon-remove"></i><span>删除</span></a>
					   		</span>
					   		
					    	<span id="b2" style="display:none;">
					     		<a class="btn"  href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
								<a class="btn"  href="javascript:onCancel();"><i class="icon icon-share"></i><span>取消</span></a>
					   		</span>
				   		</span>
				   		
				   		<span id="group1" class="gruop_hidden" style="display:none;">
				   			<a class="btn"  href="javascript:saveFunctionRole();"><i class="icon icon-check"></i><span>保存</span></a>
				   		</span>
					</div>
				</div>
				<div class="table-wrapper" id="myContent" style="height:550px;">
					<div class="ui-layout-west" style="overflow:auto;">
						<ul id="tree" class="ztree"></ul>
					</div>
					<div class="ui-layout-center" style="overflow:hidden;">
						<div id="mytab">
							<ul>
								<li><a href="#tab0">基本信息</a></li>
								<li><a href="#tab1">角色赋权</a></li>
							</ul>
							 <div id="tab0">
						        <form action="system/saveOrUpdateFunction.do" method="post" id="myForm" class="form">
									<input id="funid" name="funid" type="hidden" value=""/>
									<input id="funpid" name="funpid" type="hidden" value=""/>
									
									<fieldset>
										<legend>基本信息</legend>
										<ul>
							            	<li>
						                        <span>菜单类型：</span>
						                        <label>
						                        	<input type="radio" id="funtype0" name="funtype" value="0" class="checkboxstyle" checked />菜单
						                        </label>
						                        <label>
						                        	<input type="radio" id="funtype1" name="funtype" value="1" class="checkboxstyle" />按钮
						                        </label>
						                    </li>
							            </ul>
										<ul>
											<li>
											    <span><em class="red">* </em>排序号：</span>
											    <input id="orderid" name="orderid" class="{required:true,number:true} inputstyle"/>
											</li>
										 </ul>
							            <ul>
											<li>
											    <span><em class="red">* </em>名称：</span>
											    <input id="labelname" name="labelname" class="{required:true,maxlength:20} inputstyle"/>
											</li>
							            </ul>
							            <ul>
											<li>
											    <span>事件：</span>
											    <input id="javaevent" name="javaevent" class="{maxlength:100} inputstyle"/>
											</li>
							            </ul>
							            <ul>
											<li>
											    <span>备注：</span>
											     <textarea id="rmk" name="rmk" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
											</li>
							            </ul>
										</fieldset>
						       	</form>
					    	 </div> 
			      		 	<div id="tab1" style="display:none;">
								<ul id="roleTree" class="ztree"></ul>
					     	</div>
				  		</div>
				  	</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>