<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>岗位管理</title>
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
<script type="text/javascript" src="pages/department/post.js"></script>

<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="pages/treedrag.js"></script>
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
	
	//树的展示
	buildDepartmentQuotaTree();
	
	formDisabled(true,"#postForm");
	
	  //加载表单验证
    $("#postForm").validate();
    $('#postForm').ajaxForm(function(data) {
        //节点处理
    	var nameValue=data.postname;
    	
    	if(add){//新增
    		var newNode = {id:data.postid, name:nameValue,iconSkin:"post",code:"post",state:1,ename:data.companyid};
       		zTree.addNodes(selectNode, newNode);
    		$("#b1").hide();
    		$("#b2").hide();
    		$("#b3").hide();
    		$("#b0").show();
    		formDisabled(true,"#postForm");
        }else{
    		$("#b0").hide();
    		$("#b3").hide();
    		$("#b2").hide();
    		$("#b1").show();
    		formDisabled(true,"#postForm");
        	selectNode.name = nameValue;
   			zTree.updateNode(selectNode);
   			zTree.selectNode(selectNode);
        }
    	add=false;
    });
	
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});


//树
var zTree;
//选中节点
var selectNode=null;
function buildDepartmentQuotaTree(){
	//配置
	var setting = {
			edit: {
				drag: {
					autoExpandTrigger: true,
					prev: dropPrev,
					inner: dropInner,
					next: dropNext
				},
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			callback:{
				beforeDrag: beforeDrag,
				beforeDrop: beforeDrop,
				beforeDragOpen: beforeDragOpen,
				onDrag: onDrag,
				onDrop: onDrop,
				beforeClick: function(treeId, treeNode) {
						selectNode=treeNode;
						formDisabled(true);
						if(selectNode.code=='dept'){
							$("#b2").hide();
							$("#b3").hide();
							$("#b1").hide();
							$("#b0").show();
						}else if(selectNode.code=='post'){
							if(selectNode.state==1){
								$("#b0").hide();
								$("#b2").hide();
								$("#b3").hide();
								$("#b1").show();
							}else if(selectNode.state==0){
								$("#b0").hide();
								$("#b2").hide();
								$("#b1").hide();
								$("#b3").show();
							}
							loadPostById(selectNode.id);
						}else{
							$("#b2").hide();
							$("#b3").hide();
							$("#b1").hide();
							$("#b0").hide();
						}
					}
				},
				view: {
					fontCss: getDeptFontCss
				}
			};
	
	var myvalid=$("#myrankvalid").attr("checked")?1:0;
    $.getJSON("department/buildMyPostTree.do",{valid:myvalid}, function(tdata) {
    	zTree = $.fn.zTree.init($("#tree"),setting, tdata);
    });
    
	//邦定定位事件
	$("#myMarker").bind("keyup",markTreeNode);
	$("#myMarker").bind("blur",markSelectTreeNode);
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

//节点排序
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	if(treeNodes.lenght>0){
		alert("请选择1个节点进行排序！");
		return;
	}
	if(targetNode==null	)
		return;
	selectNode=treeNodes[0];
	$.post("department/orderPost.do",{id:selectNode.id,targetid:targetNode.id,moveType:moveType}, function() {
		
		
    });
}
//========================================




	
	
	
	
//tab页
var tabIndex=0;
function loadTab(){
	$("#mytab").tabs({
    	select: function(event, ui) {
    		tabIndex=ui.index;
    		$(".gruop_hidden").hide();
    		$("#group"+tabIndex).show();
    		$("#tab"+tabIndex).show();

    	}
    });
}



//post的保存
var add=false;
function addPost(){
	$("#b0").hide();
	$("#b3").hide();
	$("#b1").hide();
	$("#b2").show();
	$("#postForm").clearForm();
	add=true;
	formDisabled(false,"#postForm");
	
	$("#postForm #deptid").val(selectNode.id);
	$("#postForm #companyid").val(selectNode.ename);
	$("#postForm #state").val(1);
}


//保存岗位
function save(){
	verificationPostname();
}


//修改岗位
function editPost(){
	$("#b0").hide();
	$("#b3").hide();
	$("#b1").hide();
	$("#b2").show();
	formDisabled(false,"#postForm");
}


//取消
function onCancel(){
	if(selectNode.code=='dept'){
		$("#b1").hide();
		$("#b3").hide();
		$("#b2").hide();
		$("#b0").show();
	}else if(selectNode.code=='post'){
		$("#b0").hide();
		$("#b3").hide();
		$("#b2").hide();
		$("#b1").show();
	}
	formDisabled(true,"#postForm");
}



//验证保存的岗位名是否重复
function verificationPostname(){
	var postid=$("#postForm #postid").val();
	var postname=$("#postForm #postname").val();
	$.post("department/verificationPostname.do",{postid:postid,deptid:selectNode.id,postname:postname},function(msg){
		if(msg!=''&&msg!=null){
			alert(msg);
			$("#postForm #postname").val(null);
		}else{
			$("#postForm").submit();
		}
	});
}




//输入input的时候也要校验
function checkPostname(){
	var postid=$("#postForm #postid").val();
	var postname=$("#postForm #postname").val();
	
	$.post("department/verificationPostname.do",{postid:postid,deptid:selectNode.id,postname:postname},function(msg){
		if(msg!=''&&msg!=null){
			alert(msg);
			$("#postForm #postname").val(null);
		}
	});
}





//通过岗位id加载岗位信息
var postguid;
function loadPostById(postguid){
	$.getJSON("department/getPostByPostId.do",{id:postguid},function(data){
		for(var key in data){
			if($('#postForm #'+key)){
				$('#postForm #'+key).val(data[key]);
			}
		}
	});
}








//删除岗位列表的数据
function delPostById(){
	var array=new Array();
	array.push(selectNode.id);
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
 	$.post("department/delPostById.do",{ids:array.toString()}, function() {
 		zTree.removeNode(selectNode);
 		$("#b0").hide();
 		$("#b3").hide();
 		$("#b1").hide();
 		$("#b2").hide();
  	});
}








//岗位信息的单个的失效或者还原生效
function invalidOrReductionPostById(valid){
	var array=new Array();
	array.push(selectNode.id);
	if(!confirm('确认要'+(valid==0?'失效':'恢复')+'选中数据吗？')){
		return;
	}
	
	$.post("department/invalidOrReductionPostById.do",{ids:array.toString(),state:valid}, function() {
		if(valid==0){
			selectNode.iconSkin = "remove";
			selectNode.state=0;
			zTree.updateNode(selectNode);
			$("#b1").hide();
			$("#b3").show();
		}else{
			selectNode.iconSkin = "post";
			selectNode.state=1;
			zTree.updateNode(selectNode);
			$("#b3").hide();
			$("#b1").show();
		}
	});
}







//导出部门岗位
function expPostTree(){
	window.open("department/exportPost.do");
}

</script>
</head>
<body>
<div class="sort">
	<div class="sort-title">
		<h3>岗位管理</h3>
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
					<span id="b4">
						<div style="float:left;">
							<label for="myrankvalid">
								<input id="myrankvalid" type="checkbox" class="checkboxstyle" onclick="buildDepartmentQuotaTree();" checked="true"/> 只显示有效的岗位
							</label>
							&nbsp;
							&nbsp;
						</div>
					</span>
					<span id="b5">
						<a class="btn post" href="javascript:expPostTree();" style="display:${exp?'':'none'}"><i class="icon icon-download"></i><span>导出</span></a>
					</span>
					<span id="b0" style="display:none;">
				   		<a class="btn post" href="javascript:addPost();" style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>新增</span></a>
					</span>
				   	<span id="b1" style="display:none;">
				   		<a class="btn post" href="javascript:editPost();" style="display:${edit?'':'none'}"><i class="icon icon-repeat"></i><span>修改</span></a>
				   		<a class="btn post" href="javascript:invalidOrReductionPostById(0);" style="display:${valid?'':'none'}"><i class="icon icon-trash"></i><span>失效</span></a>
			   		</span>
			   		<span id="b2" style="display:none;">
						<a class="btn"  href="javascript:save();" ><i class="icon icon-check"></i><span>保存</span></a>
						<a class="btn"  href="javascript:onCancel();"><i class="icon icon-share"></i><span>取消</span></a>
				   	</span>
				   	<span id="b3" style="display:none;">
				   		<a class="btn post" href="javascript:invalidOrReductionPostById(1);" style="display:${revert?'':'none'}"><i class="icon icon-repeat"></i><span>还原</span></a>
				   		<a class="btn post" href="javascript:delPostById();"><i class="icon icon-remove" style="display:${del?'':'none'}"></i><span>删除</span></a>
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
				<div class="ui-layout-center" style="overflow:hidden;">
				<div id="mytab">
						<ul>
							<li><a href="#tab0">基本信息</a></li>
						</ul>
					<div id="tab0">
						<form action="department/saveOrUpdatePost.do" method="post" id="postForm" class="form">
						<fieldset>
						<legend>基本信息</legend>
						<input type="hidden" id="postid" name="postid" value=""/>
						<input type="hidden" id="companyid" name="companyid" value=""/>
						<input type="hidden" id="deptid" name="deptid" value=""/>
						<input type="hidden" id="state" name="state" value="1"/>
						<input type="hidden" id="modiuser" name="modiuser" value=""/>
						<input type="hidden" id="modimemo" name="modimemo" value=""/>
						<input type="hidden" id="moditimestamp" name="moditimestamp" value=""/>
						<input type="hidden" id="interfacecode" name="interfacecode" value=""/>
						<input type="hidden" id="postcode" name="postcode" value=""/>
						<ul>
							<li>
								<span><em class="red">* </em>岗位名称：</span>
								<input id="postname" name="postname" class="{required:true,maxlength:30} inputstyle"   onblur="checkPostname();"/>
							</li>
						</ul>
						<ul>
							<li>
								<span>职务：</span>
								<input id="jobid" name="jobid" type="hidden" class="inputstyle"/>
								<input id="jobidname" name="jobidname" class="inputselectstyle" onclick="chooseJobTree('#jobid','#jobidname',selectNode.ename);"/>
								<div class="search-trigger" onclick="chooseJobTree('#jobid','#jobidname',selectNode.ename);"/>
							</li>
						</ul>
						<ul>
							<li>
								<span>描述：</span>
								<textarea id="postduty" name="postduty" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
							</li>
						</ul>
						<ul>
							<li>
								<span>备注：</span>
								<textarea id="memo" name="memo" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
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

</body>
</html>