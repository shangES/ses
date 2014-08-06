<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>第三方机构管理</title>
<base href="${baseUrl }"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>    
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" href="skins/css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="skins/css/form.css" type="text/css" media="all" />
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css" />
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/gt_grid.css" />

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<!-- <script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script> -->
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

<script type="text/javascript" src="pages/treedrag.js"></script>
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
    	var nameValue=data.thirdpartnername;
        
    	buildFunctionTree();
    	//新增
    	if(add){
    		var newNode = {id:data.thirdpartnerguid, name:nameValue,iconSkin:''};
       		zTree.addNodes(selectNode, newNode);
        }else{
        	zTree.removeNode(selectNode);
        	var newNode = {id:data.thirdpartnerguid, name:nameValue,iconSkin:''};
        	selectNode.name = nameValue;
        	selectNode.iconSkin='';
   			zTree.addNodes(selectNode, newNode);
   			zTree.selectNode(selectNode);
        }
    	add=false;
    	
    	formDisabled(true);
    	//取消状态
    	onCancel();
    });
    
    formDisabled(false);
  
  
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
    		
    		/* //机构用户树
    		if(tabIndex==1)
				mygrid.reload(); */
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
	
    $.getJSON("thirdpartner/buildThirdPartnerWebUserCheckTree.do", function(tdata) {
    	zTree = $.fn.zTree.init($("#tree"),setting, tdata);
    	
    	$('#b1').hide();
    	$('#b2').hide();
    });
}

//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	if(selectNode!=null)
	pam.thirdpartnerguid=selectNode.id;
}



//加载数据
function loadData(tid){
	$("#myForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("recruitment/getWebUserById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#' + key);
			        if(key=='funtype')
			        	$('#' + key+data[key]).attr("checked",true);
			        if(el) 
			            el.val(data[key]);
			    }
			}
		});
}


//新增
var add=false;
function addNode(state){
	$('#b1').hide();
	$('#b2').show();
	$("#myForm").clearForm();
	formDisabled(false);
	add=true;
	staues=state;
	
}


//编辑
function editNode(state){
	staues=state;
	$('#b1').hide();
	$('#b2').show();
	formDisabled(false);
}


//保存
var staues=false;
function save(){
	$("#valid").val(1);
	$("#isadmin").val(0);
	if(staues){
		$("#thirdpartnerguid").val(selectNode.id);
	}
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
	$.post("recruitment/delWebUserById.do",{ids:selectNode.id}, function() {
		zTree.removeNode(selectNode);
		$("#myForm").clearForm();
			
 		$('#b1').show();
		$('#b2').hide();
		
		$('#del').hide();
		$('#edit').hide();
		$('#new').hide();
 		formDisabled(false);
    });
}

//通过选项列表的id加载数据
function loadThirdAssess(thirdpartyassessguid){
	formDisabled(false);
	$.getJSON("thirdpartner/getThirdPartyAssessById.do",{id:thirdpartyassessguid},function(data){
		for(var key in data){
			if($('#optionListForm input[id='+key+']')){
				$('#optionListForm input[id='+key+']').val(data[key]);
			}
			if($('#optionListForm textarea[id='+key+']')){
				$('#optionListForm textarea[id='+key+']').val(data[key]);
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
		
/* 		//计算高度
		_autoHeight();
		if(url==null)
			mygrid.reload(); */
  }
}

//判断登录名是否存在
function checkLoginName(){
	var email=$("#email").val();
	var userguid=$("#userguid").val();
	if (email!=null&&email!=""){
		$.getJSON("recruitment/checkWebUserByEmail.do",{userguid:userguid,name:email}, function(data) {
			if(data!=null&&data!=""){
				alert(data);
				$("#email").val(null);
			}
		});
	}
}
</script>


</head>
<body>


<div class="sort">
	<div class="sort-title">
		<h3>第三方机构管理</h3>
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
	      			
			        	<span id="group0" class="gruop_hidden">
					        <span id="b1" style="display:none;">
					        	<a id="new" class="btn" href="javascript:addNode(true);" style="display:none;"><i class="icon icon-plus"></i><span>新增</span></a>
								<a id="edit" class="btn" href="javascript:editNode(false)" style="display:none;"><i class="icon icon-pencil"></i><span>修改</span></a>
								<a id="del"  class="btn" href="javascript:delNode()"  style="display:none;"><i class="icon icon-remove"></i><span>删除</span></a>
					   		</span>
					   		
					    	<span id="b2" style="display:none;">
					     		<a class="btn"  href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
								<a class="btn"  href="javascript:onCancel();"><i class="icon icon-share"></i><span>取消</span></a>
					   		</span>
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
							</ul>
							 <div id="tab0">
						        <form action="recruitment/saveOrUpdateWebUser.do" method="post" id="myForm" class="form">
									<input id="thirdpartnerguid" name="thirdpartnerguid" type="hidden" value=""/> 
									<input id="userguid" name="userguid" type="hidden" value=""/>
									<input type="hidden" id="valid" name="valid" value="\"/>
									<input type="hidden" id="isadmin" name="isadmin" value=""/>
									<input type="hidden" id="code" name="code" value=""/>
									<fieldset>
										<legend>基本信息</legend>
										<ul>
											<li>
											    <span><em class="red">* </em>用户名：</span>
												<input id="username" name="username" class="{required:true,maxlength:20} inputstyle"  onblur="checkLoginName();"/>
											</li>
										 </ul>
							            <ul>
											<li id="loginnamePanel">
											    <span><em class="red">* </em>登录名(邮箱注册)：</span>
												<input id="email" name="email" class="{required:true,maxlength:50,email:true} inputstyle"  onblur="checkLoginName();"/>
											</li>
							            </ul>
							            <ul>
											<li id="myPassword">
												<span><em class="red">* </em>密码：</span>
												<input type="password" id="password" name="password" class="{required:true,maxlength:10} inputstyle"  />
											</li>
							            </ul>
							            <ul>
											<li id="myPassword">
												<span><em class="red">* </em>联系电话：</span>
												<input id="mobile" name="mobile" class="{required:true,maxlength:20} inputstyle"  />
											</li>
							            </ul>
							             <ul>
											<li id="myPassword">
												<span>备注：</span>
												<textarea id="rmk" name="rmk" rows="5" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
											</li>
							            </ul>
										</fieldset>
						       	</form>
					    	 </div> 
			      		 	<div id="tab1" style="display:none;">
							<div style="padding-top:5px;">
								<div id="myTable" style="margin:0px auto;">
									<div id="gridbox" ></div>
				                </div>
			                </div>
					    </div>
				  		</div>
				  	</div>
				</div>
			</div>
		</div>
</div>

</body>
</html>