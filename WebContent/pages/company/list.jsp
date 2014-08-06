<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>公司管理</title>
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
    buildCompanyTree();
  	
  	//加载职务
	loadMyJobGrid();
  	
	//加载职级
	loadMyRankGrid();
	
	//加载职级
	loadMyBudgetypeGrid();
    
    //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
        //节点处理
    	var nameValue=data.companyname;
    	
    	if(add){//新增
    		var newNode = {id:data.companyid, name:nameValue,iconSkin:"company",code:"company",marker:data.companycode,ename:1,isaudit:true};
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


//tab页
var tabIndex=0;
function loadTab(){
	$("#mytab").tabs({
    	select: function(event, ui) {
    		tabIndex=ui.index;
    		$(".gruop_hidden").hide();
    		$("#tab"+tabIndex).show();
    		
    		
    		//控制tab
			if(selectNode!=null){
				if(selectNode.isaudit)
					$("#group"+tabIndex).show();
				else
					$("#group"+tabIndex).hide();
				if(tabIndex==1){
					myjobgrid.reload();
				}else if(tabIndex==2){
					myrankgrid.reload();
				}else{
					myBudgetypegrid.reload();
				}
			}else{
				$("#group"+tabIndex).hide();
			}
    		
    	}
    });
}

//树
var zTree;
//选中节点
var selectNode=null;
function buildCompanyTree(){
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
			
		$("#myForm").clearForm();
		selectNode=treeNode;
		formDisabled(true);
		
		
		//出现按钮组一
		$('#b1').show();
		$('#b2').hide();
		
		//只能新增
		if(treeNode.code!='company'){
			if(${add==true})
				$('#new').show();
			$('#edit').hide();
			$('#del').hide();
			$("#recover").hide();
			$("#remove").hide();
		}else if(treeNode.code=='company'){
			//有效的
			if(treeNode.ename==1){
				if(${edit==true})
					$('#edit').show();
				if(${add==true})
					$('#new').show();
				if(${del==true})
					$('#del').show();
				$("#recover").hide();
				$("#remove").hide();
			}else{
				$('#edit').hide();
				$('#new').hide();
				$('#del').hide();
				if(${revert==true})
					$("#recover").show();
				if(${valid==true})
					$("#remove").show();
			}

			//取数据
			loadData(treeNode.id);
			
		}else{
			$('#edit').hide();
			$('#new').hide();
			$('#del').hide();
			$("#recover").hide();
			$("#remove").hide();
		}
			
	}}};
	var myvalid=$("#myvalid").attr("checked")?1:0;
    $.getJSON("company/buildCompanyTree.do",{valid:myvalid}, function(tdata) {
    	zTree = $.fn.zTree.init($("#tree"),setting, tdata);
    });
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
	$.post("company/orderCompany.do",{id:selectNode.id,targetid:targetNode.id,moveType:moveType}, function() {
		
		
    });
}





//加载数据
function loadData(tid){
	$("#myForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("company/getCompanyById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#myForm #' + key);
			        if(el) 
			            el.val(data[key]);
			    }
				
				//控制tab
				if(selectNode!=null){
					if(selectNode.isaudit)
						$("#group"+tabIndex).show();
					else
						$("#group"+tabIndex).hide();
					if(tabIndex==1){
						myjobgrid.reload();
					}else if(tabIndex==2){
						myrankgrid.reload();
					}else{
						myBudgetypegrid.reload();
					}
				}else{
					$("#group"+tabIndex).hide();
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
	
	$("#myForm #state").val(1);
	$("#modiuser").val("${username}");
	
	//设置上级
	if(selectNode!=null){
		var pcompanyid=selectNode.id;
		if(pcompanyid!=null&&pcompanyid!=''){
			$('#pcompanyid').val(pcompanyid);
		}
	}
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
	if(selectNode!=null){
		if(selectNode.code=='company'){
			loadData(selectNode.id);
			$('#b1').show();
			$('#b2').hide();
			
			$('#del').show();
			$('#edit').show();
			$('#new').show();
			$("#recover").hide();
			$("#remove").hide();
		}else{
			$('#b1').show();
			$('#b2').hide();
			
			$('#new').show();
			$('#edit').hide();
			$('#del').hide();
			$("#recover").hide();
			$("#remove").hide();
		}
	}
	formDisabled(true);
}


//删除
function delNode(){
	$.post("department/getDepartmentByCompanyCode.do",{companycode:selectNode.marker},function(data){
		if(data){
			alert("此公司下还有部门信息,不能删除");
		}else{
			if(!confirm('确认要删除吗？')){
				return;
			}
			$.post("company/delCompanyByCompanyCode.do",{id:selectNode.id}, function() {
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
	});
		
}

//无效
function validCompany(valid){
	if(selectNode==null)
		return;
	if(!confirm('确认要把选中的公司置为无效吗？')){
		return;
	}
	$.post("company/validCompanyById.do",{companyid:selectNode.id,valid:valid}, function() {
		selectNode.iconSkin = "remove";
		selectNode.ename = 0;
		zTree.updateNode(selectNode);
		
		$('#b1').show();
		$('#b2').hide();
		
		$('#del').hide();
		$('#edit').hide();
		$('#new').hide();
		$("#recover").show();
		$("#remove").show();
		formDisabled(true);
  });
}

//还原
function recoverCompany(valid){
	if(selectNode==null)
		return;
	$.post("company/validCompanyById.do",{companyid:selectNode.id,valid:valid}, function() {
		
		selectNode.iconSkin = "company";
		selectNode.ename = 1;
		zTree.updateNode(selectNode);
		
		$('#b1').show();
		$('#b2').hide();
		
		$('#del').show();
		$('#edit').show();
		$('#new').show();
		$("#remove").hide();
		$("#recover").hide();
		formDisabled(true);
  });
}





//导出公司树
function expTree(){
	window.open("company/exportCompany.do");
}


</script>


</head>
<body>



<div class="sort">
	<div class="sort-title">
		<h3>公司管理</h3>
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
						<div style="float:left;">
							<label for="myvalid">
								<input id="myvalid" type="checkbox" class="checkboxstyle" onclick="javascript:buildCompanyTree();" checked="true"/> 只显示当前有效公司
							</label>
							&nbsp;
							&nbsp;
						</div>
						<a id="exp" class="btn" href="javascript:expTree();" style="display:${exp?'':'none'}"><i class="icon icon-download"></i><span>导出</span></a>
					   	<span id="b1" style="display:none;">
							<a id="new" class="btn" href="javascript:addNode();" style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>新增</span></a>
							<a id="edit" class="btn" href="javascript:editNode()" style="display:${edit?'':'none'}"><i class="icon icon-pencil"></i><span>修改</span></a>
							<a id="del"  class="btn"  href="javascript:validCompany(0);" style="display:${valid?'':'none'}"><i class="icon icon-trash"></i><span>失效</span></a>
							<a id="recover" class="btn" href="javascript:recoverCompany(1);"  style="display:${"revert"?'':'none'}"><i class="icon icon-repeat"></i><span>恢复</span></a>
							<a id="remove"  class="btn" href="javascript:delNode();"  style="display:${del?'':'none'}"><i class="icon icon-remove"></i><span>删除</span></a>
				   		</span>
				   		
				   		<span id="b2" style="display:none;">
							<a class="btn"  href="javascript:save();"><i class="icon icon-check"></i><span>保存</span></a>
							<a class="btn"  href="javascript:onCancel();"><i class="icon icon-share"></i><span>取消</span></a>
				   		</span>
				   		
				   	</span>
				   	<span id="group1" class="gruop_hidden" style="display:none;">
				   		<div style="float:left;">
							<label for="myjobvalid">
								<input id="myjobvalid" type="checkbox" class="checkboxstyle" onclick="myjobgrid.reload();" checked="true"/> 只显示有效的数据
							</label>
							&nbsp;
							&nbsp;
						</div>
						<a class="btn" href="javascript:addMyJob();"><i class="icon icon-plus"></i><span>新增</span></a>
						<a class="btn" href="javascript:expJobGrid();"><i class="icon icon-download"></i><span>导出</span></a>
						<a class="btn"  href="javascript:validJob(0);" ><i class="icon icon-trash"></i><span>失效</span></a>
						<a class="btn" href="javascript:validJob(1);"><i class="icon icon-repeat"></i><span>恢复</span></a>
						<a class="btn" href="javascript:removeMyJob();"><i class="icon icon-remove"></i><span>删除</span></a>
				   	</span>
				   	
				   	<span id="group2" class="gruop_hidden" style="display:none;">
						<div style="float:left;">
							<label for="myrankvalid">
								<input id="myrankvalid" type="checkbox" class="checkboxstyle" onclick="myrankgrid.reload();" checked="true"/> 只显示有效的数据
							</label>
							&nbsp;
							&nbsp;
						</div>
						<a class="btn" href="javascript:addMyRank();"><i class="icon icon-plus"></i><span>新增</span></a>
						<a class="btn" href="javascript:expRankGrid();"><i class="icon icon-download"></i><span>导出</span></a>
						<a class="btn" href="javascript:validRank(0);"><i class="icon icon-trash"></i><span>失效</span></a>
						<a class="btn" href="javascript:validRank(1);"><i class="icon icon-repeat"></i><span>恢复</span></a>
						<a class="btn" href="javascript:removeMyRank();"><i class="icon icon-remove"></i><span>删除</span></a>
				   	</span>
				   	
				   	<span id="group3" class="gruop_hidden" style="display:none;">
						<div style="float:left;">
							<label for="myBudgetypevalid">
								<input id="myBudgetypevalid" type="checkbox" class="checkboxstyle" onclick="myBudgetypegrid.reload();" checked="true"/> 只显示有效的数据
							</label>
							&nbsp;
							&nbsp;
						</div>
						<a class="btn" href="javascript:addMyBudgetype();"><i class="icon icon-plus"></i><span>新增</span></a>
						<a class="btn" href="javascript:expBudgetypeGrid();"><i class="icon icon-download"></i><span>导出</span></a>
						<a class="btn" href="javascript:validBudgetype(0);"><i class="icon icon-trash"></i><span>失效</span></a>
						<a class="btn" href="javascript:validBudgetype(1);"><i class="icon icon-repeat"></i><span>恢复</span></a>
						<a class="btn" href="javascript:removeMyBudgetype();"><i class="icon icon-remove"></i><span>删除</span></a>
				   	</span>
				   	
				</div>
			</div>
			<div class="table-wrapper" id="myContent" style="height:550px;">
				<div class="ui-layout-west">
					<ul id="tree" class="ztree"></ul>
				</div>
				<div class="ui-layout-center" style="overflow:hidden;">
					<div id="mytab">
						<ul>
							<li ><a href="#tab0">基本信息</a></li>
							<li style="display:${job?'':'none'}"><a href="#tab1">公司职务</a></li>
							<li style="display:${rank?'':'none'}"><a href="#tab2">公司职级</a></li>
							<li style="display:${budgetype?'':'none'}"><a href="#tab3">编制类型</a></li>
						</ul>
						<div id="tab0" style="overflow:auto;height:508px;">
					        <form action="company/saveOrUpdateCompany.do" method="post" id="myForm" class="form">
								<input id="companyid" name="companyid" type="hidden" value=""/>
								<input id="pcompanyid" name="pcompanyid" type="hidden" value=""/>
								<input id="state" name="state" type="hidden" value="1" />
								<input id="modiuser" name="modiuser" type="hidden" value="${username}" />
								<input id="modimemo" name="modimemo" type="hidden" value="" />
							    <input id="companycode" name="companycode" type="hidden" value="" />
								
								<fieldset>
									<legend>基本信息</legend>
									<ul>
						            	<li>
						            		<span><em class="red">* </em>公司名称：</span>
										    <input id="companyname" name="companyname" class="{required:true,maxlength:40} inputstyle"/>
					                    </li>
						            </ul>
						            <ul>
						            	<li>
						            		<span>公司代码：</span>
										    <input id="interfacecode" name="interfacecode" class="{maxlength:30} inputstyle"/>
					                    </li>
						            </ul>
						             <ul>
										<li>
										    <span><em class="red">* </em>公司类型：</span>
										    <input id="companytype" name="companytype" type="hidden"/>
					    					<input id="companytypename" name="companytypename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#companytype','#companytypename','COMPANYTYPE');"/>
					    					<div class="select-trigger" onclick="chooseOptionTree('#companytype','#companytypename','COMPANYTYPE');"/>
										</li>
						            </ul>
						            <ul>
					            		<li>
										    <span><em class="red">* </em>创立时间：</span>
										    <input id="createdate" name="createdate" class="{required:true,dateISO:true} inputselectstyle datepicker" />
										    <div class="date-trigger"  onclick="$('#createdate').focus();"/>
										</li>
						            </ul>
									<ul>
										<li>
										    <span>注册资金(万元)：</span>
										     <input id="regcapital" name="regcapital"  class="{number:true,maxlength:16} inputstyle"/>
										</li>
									 </ul>
									 <ul>
									 	<li>
										    <span>法人代表：</span>
										    <input id="legalperson" name="legalperson" class="{maxlength:30} inputstyle"/>
										</li>
									 </ul>
						            <ul>
										<li>
										    <span>办公地址：</span>
										    <input id="officeaddress" name="officeaddress" class="{maxlength:100} inputstyle"/>
										</li>
						            </ul>
					             	<ul>
										<li>
										    <span>经营范围：</span>
									     	<textarea id="businessscope" name="businessscope" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
										</li>
						            </ul>
						            <ul>
										<li>
										    <span>公司简介：</span>
									     	<textarea id="description" name="description" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
										</li>
						            </ul>
						            <ul style="display:none;">
										<li>
										    <span>备注：</span>
										     <textarea id="memo" name="memo" rows="3" cols="40" class="{maxlength:500} areastyle" ></textarea>
										</li>
						            </ul>
								</fieldset>
					       </form>
   							</div> 
				        <div id="tab1" style="display:none;">
							<div style="padding-top:5px;">
								<div id="myjobTable" style="margin:0px auto;">
									<div id="jobgridbox" ></div>
				                </div>
			                </div>
					    </div>
					    <div id="tab2" style="display:none;">
					    	<div style="padding-top:5px;">
								<div id="myrankTable" style="margin:0px auto;">
									<div id="rankgridbox" ></div>
				                </div>
			                </div>
					    </div>
					    <div id="tab3" style="display:none;">
					    	<div style="padding-top:5px;">
								<div id="myBudgetypeTable" style="margin:0px auto;">
									<div id="Budgetypegridbox" ></div>
				                </div>
			                </div>
					    </div>
				    </div>
				</div>
				
			</div>
		</div>
	</div>
</div>










<!-- 添加职务信息窗口 -->
<div id="addJobWindow" title="职务信息" style="display:none;">
	<form action="job/saveOrUpdateJob.do" id="addJobForm" class="form" method="post">
		<input type="hidden" id="jobid" name="jobid" value=""/>
		<input type="hidden" id="companyid" name="companyid" value=""/>
		<input type="hidden" id="state" name="state" value="1"/>
		<input type="hidden" id="modiuser" name="modiuser" value=""/>
		<input id="modimemo" name="modimemo" type="hidden" value="" />
		<input id="memo" name="memo" type="hidden" value="" />
		<ul>
			<li>
				<span><em class="red">* </em>排序号：</span>
				<input  id="dorder" name="dorder"  class="{required:true,number:true,maxlength:100} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>职务名称：</span>
				<input id="jobname" name="jobname"  class="{required:true,maxlength:50} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>描述：</span>
				<textarea id="description" name="description" rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>








<!-- 添加职级信息窗口 -->
<div id="addRankWindow" title="职级信息" style="display:none;">
	<form action="rank/saveOrUpdateRank.do" id="addRankForm" class="form" method="post">
		<input type="hidden" id="rankid" name="rankid" value=""/>
		<input type="hidden" id="companyid" name="companyid" value=""/>
		<input type="hidden" id="state" name="state" value="1"/>
		<input type="hidden" id="modiuser" name="modiuser" value="${username}"/>
		<input id="modimemo" name="modimemo" type="hidden" value="" />
		<input id="memo" name="memo" type="hidden" value="" />
		<ul>
			<li>
				<span><em class="red">* </em>排序号：</span>
				<input  id="dorder" name="dorder"  class="{required:true,number:true,maxlength:100} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>代码：</span>
				<input  id="interfacecode" name="interfacecode"  class="{required:true,number:true,maxlength:38} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>职级名称：</span>
				<input id="levelname" name="levelname"  class="{required:true,maxlength:40} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>描述：</span>
				<textarea id="description" name="description" rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>





<!-- 添加编制类型信息窗口 -->
<div id="addBudgetypeWindow" title="编制类型信息" style="display:none;">
	<form action="budgetype/saveOrUpdateBudgetype.do" id="addBudgetypeForm" class="form" method="post">
		<input type="hidden" id="budgettypeid" name="budgettypeid" value=""/>
		<input type="hidden" id="companyid" name="companyid" value=""/>
		<input type="hidden" id="state" name="state" value="1"/>
		<input type="hidden" id="modiuser" name="modiuser" value="${username}"/>
		<input id="modimemo" name="modimemo" type="hidden" value="" />
		<ul>
			<li>
				<span><em class="red">* </em>排序号：</span>
				<input  id="dorder" name="dorder"  class="{required:true,number:true,maxlength:100} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">* </em>编制类型：</span>
				<input id="budgettypename" name="budgettypename"  class="{required:true,maxlength:30} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>备注：</span>
				<textarea id="memo" name="memo" rows="3" cols="40" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>


<script type="text/javascript">
//职务
var myjobgrid=null;
function loadMyJobGrid(){
	var size=getGridSize();
	var wh=$(document.body).outerWidth()-360;
	$("#myjobTable").css({width:wh,height:460});
	
	var grid_demo_id = "myGrid1";
	var dsOption = {
	        fields :[
	            {name : 'jobid'},
	            {name : 'jobname'},
	            {name : 'description'},
	            {name : 'state'},
	            {name : 'dorder'},
	            {name : 'memo'},
	            {name : 'modiuser'},
	            {name : 'moditimestamp'}
	        ]
	    };
	    var colsOption = [
	        {id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center' },
	        {id: 'state', header: "操作", width :60 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
	        	var htm='';
	        	if(selectNode!=null&&selectNode.isaudit){
	        		htm+='<a href="javascript:editMyJob(\''+record.jobid+'\');" class="ui-button ui-button-icon-only" title="修改"><span class="ui-icon ui-icon-pencil"></span>&nbsp;</a>';
					if(value==1)
		        		htm+='<a href="javascript:recoverJob(\''+record.jobid+'\',0);" class="ui-button ui-button-icon-only" title="置为无效"><span class="ui-icon ui-icon-trash"></span>&nbsp;</a>';
		        	else
		        		htm+='<a href="javascript:recoverJob(\''+record.jobid+'\',1);" class="ui-button ui-button-icon-only" title="还原"><span class="ui-icon ui-icon-check"></span>&nbsp;</a>';
	        	}
				return htm;
			}},
	        {id: 'dorder' , header: "排序号" ,headAlign:'center',align:'center', width :50},
	        {id: 'jobname' , header: "职务名称" ,width:120,headAlign:'center',align:'left'},
	        {id: 'description', header: "描述",width:250,headAlign:'center',align:'left'}
	    ];
	
	var gridOption={
		id : grid_demo_id,
		loadURL : 'job/searchJob.do',
		beforeLoad:function(reqParam){
			initJobPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'job/searchJob.do?export=true',
		beforeExport:function(){
			initJobPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			myjobgrid.parameters=pam;
		},
		exportFileName : '职务信息表.xls',
		width:'100%',
		height:"460",  //"100%", // 330,
		container : 'jobgridbox',
		autoLoad:false,
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:false,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				editMyJob(record.jobid);
			}
		}
	};
	myjobgrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(myjobgrid) );
}


//参数设置
var pam=null;
function initJobPagePam(){
	pam={};
	pam.expAll=0;
	if(selectNode!=null)
	pam.comid=selectNode.id;
	pam.valid=$("#myjobvalid").attr("checked")?1:0;
}


//新增
var addJobForm=null;
function addMyJob(){
	$("#addJobWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(addJobForm.form){
					$("#addJobForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addJobForm").clearForm();
			if(selectNode==null)
				return;
			$("#addJobForm input[id=companyid]").val(selectNode.id);
			$("#addJobForm input[id=state]").val(1);
			$("#addJobForm input[id=modiuser]").val("${username}");
			
			formDisabled(false);
			addJobForm=$("#addJobForm").validate({submitHandler: function(form) {
			    	$("#addJobForm").ajaxSubmit(function(data) {
			    		myjobgrid.reload();
			    		$("#addJobWindow").dialog("close");
			        });
				}
			});
		}
	});
}


//导出
function expJobGrid(){
	$("#dialog_exp").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:130,
		buttons: {
			"确定": function() {
				myjobgrid.exportGrid('xls');
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


//得到数据
function loadMyJob(tid){
	$("#addJobForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("job/getJobById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#addJobForm input[id=' + key+'],#addJobForm textarea[id=' + key+']');
			        if(el) 
			            el.val(data[key]);
			    }
			}
		});
}


//修改
function editMyJob(tid){
	$("#addJobWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(addJobForm.form){
					$("#addJobForm").submit();
					myjobgrid.reload();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addJobForm").clearForm();
			loadMyJob(tid);
			formDisabled(!(selectNode!=null&&selectNode.isaudit));
			if(addJobForm==null)
			addJobForm=$("#addJobForm").validate({debug:true,submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		myjobgrid.reload();
			    		$("#addJobWindow").dialog("close");
			        });
				}
			});
		}
	});
	
}


//删除
function removeMyJob(){
	var array=new Array();
	var cords=myjobgrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].jobid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	
	$.post("job/delJobById.do",{ids:array.toString()}, function() {
		myjobgrid.reload();
    });
}

//失效恢复
function validJob(valid){
	var array=new Array();
	var cords=myjobgrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		if(obj.state!=valid)
			array.push(obj.jobid);
	}
	if(array.length<=0){
		alert('请选择要'+(valid==0?'失效':'恢复')+'的数据！');
		return;
	}
	if(!confirm('确认要'+(valid==0?'失效':'恢复')+'选中数据吗？')){
		return;
	}
	$.post("job/validJobById.do",{ids:array.toString(),valid:valid}, function() {
		myjobgrid.reload();
  });
}

//恢复
function recoverJob(id,valid){
	var array=new Array();
	array.push(id);
	
	if(!confirm('确认要'+(valid==0?'失效':'恢复')+'选中数据吗？')){
		return;
	}
	
	$.post("job/validJobById.do",{ids:array.toString(),valid:valid}, function() {
		myjobgrid.reload();
  });
}
</script>

























<script type="text/javascript">
//职级
var myrankgrid=null;
function loadMyRankGrid(){
	var size=getGridSize();
	var wh=$(document.body).outerWidth()-360;
	$("#myrankTable").css({width:wh,height:460});
	
	var grid_demo_id = "myGrid2";
	var dsOption = {
	        fields :[
	            {name : 'rankid'},
	            {name : 'levelname'},
	            {name : 'description'},
	            {name : 'state'},
	            {name : 'dorder'},
	            {name : 'memo'},
	            {name : 'modiuser'},
	            {name : 'moditimestamp'},
	            {name : 'interfacecode'}
	        ]
	    };
	    var colsOption = [
	        {id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center' },
	        {id: 'state', header: "操作", width :60 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
	        	var htm='';
	        	if(selectNode!=null&&selectNode.isaudit){
		        	htm+='<a href="javascript:editMyRank(\''+record.rankid+'\');" class="ui-button ui-button-icon-only" title="修改"><span class="ui-icon ui-icon-pencil"></span>&nbsp;</a>';
					if(value==1)
		        		htm+='<a href="javascript:recoverRank(\''+record.rankid+'\',0);" class="ui-button ui-button-icon-only" title="置为无效"><span class="ui-icon ui-icon-trash"></span>&nbsp;</a>';
		        	else
		        		htm+='<a href="javascript:recoverRank(\''+record.rankid+'\',1);" class="ui-button ui-button-icon-only" title="还原"><span class="ui-icon ui-icon-check"></span>&nbsp;</a>';
	        	}
				return htm;
			}},
	        {id: 'dorder' , header: "排序号" ,headAlign:'center',align:'center', width :50},
	        {id: 'levelname' , header: "职级名称" ,width:120,headAlign:'center',align:'left'},
	        {id: 'description', header: "描述",width:250,headAlign:'center',align:'left'}
	    ];
	
	var gridOption={
		id : grid_demo_id,
		loadURL : 'rank/searchRank.do',
		beforeLoad:function(reqParam){
			initRankPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'rank/searchRank.do?export=true',
		beforeExport:function(){
			initRankPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			myrankgrid.parameters=pam;
		},
		exportFileName : '职级信息表.xls',
		width:'100%',
		height:"460",  //"100%", // 330,
		container : 'rankgridbox',
		autoLoad:false,
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:false,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				editMyRank(record.rankid);
			}
		}
	};
	myrankgrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(myrankgrid) );
}

//参数设置
var pam=null;
function initRankPagePam(){
	pam={};
	pam.expAll=0;
	if(selectNode!=null)
	pam.comid=selectNode.id;
	pam.valid=$("#myrankvalid").attr("checked")?1:0;
}

//导出
function expRankGrid(){
	$("#dialog_exp").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:130,
		buttons: {
			"确定": function() {
				myrankgrid.exportGrid('xls');
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



//新增
var addRankForm=null;
function addMyRank(){
	$("#addRankWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(addRankForm.form){
					$("#addRankForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addRankForm").clearForm();
			if(selectNode==null)
				return;
			$("#addRankForm input[id=companyid]").val(selectNode.id);
			$("#addRankForm input[id=state]").val(1);
			$("#addRankForm input[id=modiuser]").val("${username}");
			formDisabled(false);
			if(addRankForm==null)
				addRankForm=$("#addRankForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		myrankgrid.reload();
			    		$("#addRankWindow").dialog("close");
			        });
				}
			});
		}
	});
}

//得到数据
function loadMyRank(tid){
	$("#addRankForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("rank/getRankById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
					var el = $('#addRankForm input[id=' + key+'],#addRankForm textarea[id=' + key+']');
			        if(el) 
			            el.val(data[key]);
			    }
			}
		});
}


//修改
function editMyRank(tid){
	$("#addRankWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(addRankForm.form){
					$("#addRankForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addRankForm").clearForm();
			loadMyRank(tid);
			formDisabled(!(selectNode!=null&&selectNode.isaudit));
			if(addRankForm==null)
				addRankForm=$("#addRankForm").validate({debug:true,submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		myrankgrid.reload();
			    		$("#addRankWindow").dialog("close");
			        });
				}
			});
		}
	});
	
}


//删除
function removeMyRank(){
	var array=new Array();
	var cords=myrankgrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].rankid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	
	$.post("rank/delRankById.do",{ids:array.toString()}, function() {
		myrankgrid.reload();
    });
}

//失效恢复
function validRank(valid){
	var array=new Array();
	var cords=myrankgrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		if(obj.state!=valid)
			array.push(obj.rankid);
	}
	if(array.length<=0){
		alert('请选择要'+(valid==0?'失效':'恢复')+'的数据！');
		return;
	}
	if(!confirm('确认要'+(valid==0?'失效':'恢复')+'选中数据吗？')){
		return;
	}
	$.post("rank/validRankById.do",{ids:array.toString(),valid:valid}, function() {
		myrankgrid.reload();
  });
}

//恢复
function recoverRank(id,valid){
	var array=new Array();
	array.push(id);
	
	if(!confirm('确认要'+(valid==0?'失效':'恢复')+'选中数据吗？')){
		return;
	}
	
	$.post("rank/validRankById.do",{ids:array.toString(),valid:valid}, function() {
		myrankgrid.reload();
  });
}
</script>









<script type="text/javascript">
//编制类型
var myBudgetypegrid=null;
function loadMyBudgetypeGrid(){
	var size=getGridSize();
	var wh=$(document.body).outerWidth()-360;
	$("#myBudgetypeTable").css({width:wh,height:460});
	
	var grid_demo_id = "myGrid3";
	var dsOption = {
	        fields :[
	            {name : 'budgettypeid'},
	            {name : 'budgettypename'},
	            {name : 'state'},
	            {name : 'dorder'},
	            {name : 'memo'},
	            {name : 'modiuser'},
	            {name : 'moditimestamp'}
	        ]
	    };
	    var colsOption = [
	        {id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center' },
	        {id: 'state', header: "操作", width :80 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
	        	var htm='';
	        	if(selectNode!=null&&selectNode.isaudit){
		        	htm+='<a href="javascript:editMyBudgetype(\''+record.budgettypeid+'\');" class="ui-button ui-button-icon-only" title="修改"><span class="ui-icon ui-icon-pencil"></span>&nbsp;</a>';
					if(value==1)
		        		htm+='<a href="javascript:recoverBudgetype(\''+record.budgettypeid+'\',0);" class="ui-button ui-button-icon-only" title="置为无效"><span class="ui-icon ui-icon-trash"></span>&nbsp;</a>';
		        	else
		        		htm+='<a href="javascript:recoverBudgetype(\''+record.budgettypeid+'\',1);" class="ui-button ui-button-icon-only" title="还原"><span class="ui-icon ui-icon-check"></span>&nbsp;</a>';
	        	}
				return htm;
			}},
	        {id: 'dorder' , header: "排序号" ,headAlign:'center',align:'center', width :50},
	        {id: 'budgettypename' , header: "编制类型名称" ,width:150,headAlign:'center',align:'left'},
	       	{id: 'memo', header: "备注",width:250,headAlign:'center',align:'left'}
	    ];
	
	var gridOption={
		id : grid_demo_id,
		loadURL : 'budgetype/searchBudgetype.do',
		beforeLoad:function(reqParam){
			initBudgetypePagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'budgetype/searchBudgetype.do?export=true',
		beforeExport:function(){
			initBudgetypePagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		exportFileName : '编制类型信息表.xls',
		width:'100%',
		height:"460",  //"100%", // 330,
		container : 'Budgetypegridbox',
		autoLoad:false,
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:false,
		selectRowByCheck:true,
		replaceContainer : true,
		dataset : dsOption ,
		columns : colsOption,
		toolbarContent : 'nav | pagesize  state',
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		pageSize:size,
		skin:getGridSkin(),
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				editMyBudgetype(record.budgettypeid);
			}
		}
	};
	myBudgetypegrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(myBudgetypegrid) );
}

//参数设置
var pam=null;
function initBudgetypePagePam(){
	pam={};
	pam.expAll=0;
	if(selectNode!=null)
	pam.comid=selectNode.id;
	pam.valid=$("#myBudgetypevalid").attr("checked")?1:0;
}


//导出
function expBudgetypeGrid(){
	$("#dialog_exp").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:130,
		buttons: {
			"确定": function() {
				myBudgetypegrid.exportGrid('xls');
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



//新增
var addBudgetypeForm=null;
function addMyBudgetype(){
	$("#addBudgetypeWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(addBudgetypeForm.form){
					$("#addBudgetypeForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addBudgetypeForm").clearForm();
			if(selectNode==null)
				return;
			
			$("#addBudgetypeForm input[id=companyid]").val(selectNode.id);
			$("#addBudgetypeForm input[id=state]").val(1);
			$("#addBudgetypeForm input[id=modiuser]").val("${username}");
			
			formDisabled(false);
			if(addBudgetypeForm==null)
				addBudgetypeForm=$("#addBudgetypeForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		myBudgetypegrid.reload();
			    		$("#addBudgetypeWindow").dialog("close");
			        });
				}
			});
		}
	});
}

//得到数据
function loadMyBudgetype(tid){
	$("#addBudgetypeForm").clearForm();
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("budgetype/getBudgetypeById.do", {id:tid}, function(data) {
			if(data!=null){
				for (var key in data) {
					var el = $('#addBudgetypeForm input[id=' + key+'],#addBudgetypeForm textarea[id=' + key+']');
			        if(el)
			            el.val(data[key]);
			    }
			}
		});
}


//修改
function editMyBudgetype(tid){
	$("#addBudgetypeWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(addBudgetypeForm.form){
					$("#addBudgetypeForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addBudgetypeForm").clearForm();
			loadMyBudgetype(tid);
			
			formDisabled(!(selectNode!=null&&selectNode.isaudit));
			if(addBudgetypeForm==null)
				addBudgetypeForm=$("#addBudgetypeForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		myBudgetypegrid.reload();
			    		$("#addBudgetypeWindow").dialog("close");
			        });
				}
			});
		}
	});
	
}


//删除
function removeMyBudgetype(){
	var array=new Array();
	var cords=myBudgetypegrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].budgettypeid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	
	$.post("budgetype/delBudgetypeById.do",{ids:array.toString()}, function() {
		myBudgetypegrid.reload();
    });
}

//失效恢复
function validBudgetype(valid){
	var array=new Array();
	var cords=myBudgetypegrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		if(obj.state!=valid)
			array.push(obj.budgettypeid);
	}
	if(array.length<=0){
		alert('请选择要'+(valid==0?'失效':'恢复')+'的数据！');
		return;
	}
	if(!confirm('确认要'+(valid==0?'失效':'恢复')+'选中数据吗？')){
		return;
	}
	$.post("budgetype/validBudgetypeById.do",{ids:array.toString(),valid:valid}, function() {
		myBudgetypegrid.reload();
  });
}

//恢复
function recoverBudgetype(id,valid){
	var array=new Array();
	array.push(id);
	
	if(!confirm('确认要'+(valid==0?'失效':'恢复')+'选中数据吗？')){
		return;
	}
	
	$.post("budgetype/validBudgetypeById.do",{ids:array.toString(),valid:valid}, function() {
		myBudgetypegrid.reload();
  });
}
</script>


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


</body>
</html>