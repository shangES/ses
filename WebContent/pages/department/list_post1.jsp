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
	
	//加载数据
	loadGrid();
	
	//编制
	//loadQuotaGrid();
	
	  //加载表单验证
    $("#myForm").validate();
    $('#myForm').ajaxForm(function(data) {
    	alert("保存成功！");
    	
        //节点处理
    	var nameValue=data.postname;
    	if(add){//新增
    		var newNode = {id:data.postid, name:nameValue,iconSkin:"post",code:"post",state:1,ename:data.companyid};
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
						$("#myForm").clearForm();
						selectNode=treeNode;
						formDisabled(true);
						
						//出现按钮组一
						$('#b1').show();
						$('#b2').hide();
						if(selectNode.code=='dept'){
							$('#new').show();
							$('#edit').hide();
							$('#del').hide();
							$("#recover").hide();
							$("#remove").hide();
							$("#deptid").val(selectNode.id);
						}else if(selectNode.code=='post'){
							if(selectNode.state==1){
								$('#edit').show();
								$('#del').show();
								$('#new').hide();
								$("#recover").hide();
								$("#remove").hide();
							}else if(selectNode.state==0){
								$('#edit').hide();
								$('#new').hide();
								$('#del').hide();
								$("#recover").show();
								$("#remove").show();
							}
							$("#postid").val(selectNode.id);
							loadPostById(selectNode.id);
						}else{
							$('#edit').hide();
							$('#new').hide();
							$('#del').hide();
							$("#recover").hide();
							$("#remove").hide();
							$("#companyid").val(selectNode.id);
						}
					
						//myQuotagrid.reload();
						//loadQuotaGrid();
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
    		$("#tab"+tabIndex).show();
    		
    		//控制tab
    		
			if(selectNode!=null){
				$("#group"+tabIndex).show();
				if(tabIndex==1){
					mygrid.reload();
				}else if(tabIndex==2){
					myQuotagrid.reload();
				}
			}else{
				$("#group"+tabIndex).hide();
			}

    	}
    });
}



//post的保存
var add=false;
function addPost(){
	$('#b1').hide();
	$('#b2').show();
	$("#bzlb1").show();
	$("#bzrs1").show();
	$("#bzlb2").show();
	$("#bzrs2").show();
	$("#myForm").clearForm();
	add=true;
	formDisabled(false);
	
	$("#deptid").val(selectNode.id);
	$("#companyid").val(selectNode.ename);
	$("#state").val(1);
}


//保存岗位
function save(){
	verificationPostname();
}


//修改岗位
function editPost(){
	$("#b1").hide();
	$("#b2").show();
	$("#bzlb1").hide();
	$("#bzrs1").hide();
	$("#bzlb2").hide();
	$("#bzrs2").hide();
	formDisabled(false);
}


//取消
function onCancel(){
	if(selectNode!=null){
		if(selectNode.code=='dept'){
			$('#b1').show();
			$('#b2').hide();
			
			$('#new').show();
			$('#edit').hide();
			$('#del').hide();
			$("#recover").hide();
			$("#remove").hide();
		}else if(selectNode.code=='post'){
			loadPostById(selectNode.id)
			$('#b1').show();
			$('#b2').hide();
			
			$('#del').show();
			$('#edit').show();
			$('#new').hide();
			$("#recover").hide();
			$("#remove").hide();
		}
	}
	formDisabled(true);
}





//验证保存的岗位名是否重复
function verificationPostname(){
	var postid=$("#postid").val();
	var postname=$("#postname").val();
	$.post("department/verificationPostname.do",{postid:postid,deptid:selectNode.id,postname:postname},function(msg){
		if(msg!=''&&msg!=null){
			alert(msg);
			$("#postname").val(null);
		}else{
			var budgettype2=$("#budgettype2").val();
			var budgettype1=$("#budgettype1").val();
			
			//判断不同的编制类别
			if(budgettype1!=null&&budgettype1!=''&&budgettype2!=null&&budgettype2!=''){
				if(budgettype1==budgettype2){
					alert("请选择不同的编制类别!");
					return;
				}
			}
			
			$("#myForm").submit();
		}
	});
}




//输入input的时候也要校验
function checkPostname(){
	var postid=$("#postid").val();
	var postname=$("#postname").val();
	
	$.post("department/verificationPostname.do",{postid:postid,deptid:selectNode.id,postname:postname},function(msg){
		if(msg!=''&&msg!=null){
			alert(msg);
			$("#postname").val(null);
		}
	});
}





//通过岗位id加载岗位信息
function loadPostById(postguid){
	$("#myForm").clearForm();
	$.getJSON("department/getPostByPostId.do",{id:postguid},function(data){
		if(data!=null){
			for (var key in data) {
		        var el = $('#' + key);
		        if(el) 
		            el.val(data[key]);
		    }
			
			//控制tab
			if(selectNode!=null){
				$("#group"+tabIndex).show();
				if(tabIndex==1){
					mygrid.reload();
				}else if(tabIndex==2){
					myQuotagrid.reload();
				}
			}else{
				$("#group"+tabIndex).hide();
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
 		$("#myForm").clearForm();
 		
 		$('#b1').show();
		$('#b2').hide();
 		
		$('#del').hide();
		$('#edit').hide();
		$('#new').hide();
 		formDisabled(true);
  	});
}








//岗位信息的单个的失效或者还原生效
function invalidOrReductionPostById(valid){
	if(selectNode==null)
		return;
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
			$('#b1').show();
			$('#b2').hide();
			
			$('#del').hide();
			$('#edit').hide();
			$('#new').hide();
			$("#recover").show();
			$("#remove").show();
			formDisabled(true);
		}else{
			selectNode.iconSkin = "post";
			selectNode.state=1;
			zTree.updateNode(selectNode);
			$('#b1').show();
			$('#b2').hide();
			
			$('#del').show();
			$('#edit').show();
			$('#new').hide();
			$("#remove").hide();
			$("#recover").hide();
			formDisabled(true);
		}
	});
}


//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	if(selectNode!=null){
		pam.postid=selectNode.id;
		pam.isstaff=1;
	}
		
}





//导出部门岗位
function expPostTree(){
	window.open("department/exportPost.do");
}

</script>

<script type="text/javascript">
//人员信息
var mygrid=null;
function loadGrid(){
	var size=20;
	var wh=$(document.body).outerWidth()-360;
	$("#myTable").css({width:wh,height:460});
	
	var grid_demo_id = "myGrid1";
	var dsOption = {
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
    		loadURL :'employee/searchEmployee.do',
    		beforeLoad:function(reqParam){
    			initPagePam();
    			reqParam['parameters']=pam;
    		},
    		width:'100%',
    		height:"460", //"100%", // 330
    		container : 'gridbox',
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
    		skin:getGridSkin()
    	};
    	mygrid=new Sigma.Grid( gridOption );
    	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

</script>





<script type="text/javascript">
//编制
//公司回调
function callBackCompany(){
	$("#quotadeptid").val(null);
	$("#quotadeptname").val(null);
	
	//部门选择回调
	callbackDept();
}



//部门选择回调
function callbackDept(){
	/* $("#postid").val(null);
	$("#postname").val(null); */
}



//岗位回调
function callbackpost(){
	var postid=$("#postid").val();
	var quotaid=$("#quotaquotaid").val();
	var state=$("#quotastate").val();
	var budgettype=$("#quotabudgettype").val();
	$.post("quota/checkQuotaByPostIdAndBudgettype.do",{quotaid:quotaid,postid:postid,budgettype:budgettype,state:state}, function(data) {
		if(data!=null&&data!=""){
			alert(data);
			$("#postid").val(null);
			$("#postname").val(null);
		}
    });
	
}






//验证此岗位下编制是否被占
function callbackBudgettype(){
	var postid=$("#postid").val();
	var quotaid=$("#quotaquotaid").val();
	var state=$("#quotastate").val();
	var budgettype=$("#quotabudgettype").val();
	$.post("quota/checkQuotaByPostIdAndBudgettype.do",{quotaid:quotaid,postid:postid,budgettype:budgettype,state:state}, function(data) {
		if(data!=null&&data!=""){
			alert(data);
			$("#quotabudgettype").val(null);
			$("#quotabudgettypename").val(null);
		}
    });
}








//编制
var myQuotagrid=null;
function loadQuotaGrid(){
	var size=20;
	var wh=$(document.body).outerWidth()-360;
	$("#gridQuotabox").css({width:wh,height:460});
	
	var grid_demo_id = "myGrid2";
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
			{id: 'quotaid' , header: "操作" , width :60 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				var htm='';
	        		htm+='<a href="javascript:lockQuota(\''+record.quotaid+'\',\''+record.vacancynumber+'\');" title="锁定" style="display:${lock?'':'none'}">';
	        		htm+='锁定';
	        		htm+='</a>';
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
			initQuotaPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'quota/searchQuota.do?export=true',
		beforeExport:function(){
			initQuotaPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			myQuotagrid.parameters=pam;
		},
		exportFileName : '编制信息表.xls',
		width:'99.8%',
		height:"460",
		minHeight:"300",  //"100%", // 330,
		container : 'gridQuotabox', 
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
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('quota.do?page=tab&id='+record.quotaid);
			}
		}
	};
	myQuotagrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(myQuotagrid) );
}

//参数设置
var pam=null;
function initQuotaPagePam(){
	pam={};
	pam.expAll=0;
	pam.companyid=$("#companyid").val();
	pam.deptid=$("#deptid").val();
	pam.postname=$("#postname").val();
	pam.postidofquota=$("#postid").val();
	if(selectNode!=null){
		if(selectNode.code=='dept'){
			pam.deptcode=selectNode.deptcode;
		}else if(selectNode.code='company'){
			pam.companycode=selectNode.companycode;
		}
	}
	
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
						<span id="group0" class="gruop_hidden">
							<div style="float:left;">
								<label for="myrankvalid">
									<input id="myrankvalid" type="checkbox" class="checkboxstyle" onclick="buildDepartmentQuotaTree();" checked="true"/> 只显示有效的岗位
								</label>
							&nbsp;
							&nbsp;
							</div>
							<a class="btn" id="exp" href="javascript:expPostTree();" style="display:${exp?'':'none'}"><i class="icon icon-download"></i><span>导出</span></a>
							<span id="b1" style="display:none;">
						   		<a class="btn" id="new" href="javascript:addPost();" style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>新增</span></a>
						   		<a class="btn" id="edit" href="javascript:editPost();" style="display:${edit?'':'none'}"><i class="icon icon-repeat"></i><span>修改</span></a>
						   		<a class="btn" id="del" href="javascript:invalidOrReductionPostById(0);" style="display:${valid?'':'none'}"><i class="icon icon-trash"></i><span>失效</span></a>
						   		<a class="btn" id="recover" href="javascript:invalidOrReductionPostById(1);" style="display:${revert?'':'none'}"><i class="icon icon-repeat"></i><span>还原</span></a>
					   			<a class="btn" id="remove"  href="javascript:delPostById();"><i class="icon icon-remove" style="display:${del?'':'none'}"></i><span>删除</span></a>
						   	</span>
						   	<span id="b2" style="display:none;">
						   		<a class="btn"  href="javascript:save();" ><i class="icon icon-check"></i><span>保存</span></a>
								<a class="btn"  href="javascript:onCancel();"><i class="icon icon-share"></i><span>取消</span></a>
						   	</span>
			   			</span>
			   		
		   				<span id="group1" class="gruop_hidden" style="display:none;">
				   			
				   		</span>
				</div>
			</div>
		<div class="table-wrapper" id="myContent" style="height:750px;">
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
								<li ><a href="#tab0">基本信息</a></li>
								<li ><a href="#tab1">在岗员工</a></li>
								<!--  <li ><a href="#tab2">编制信息</a></li>-->
							</ul>
						<div id="tab0">
							<form action="department/saveOrUpdatePost.do" method="post" id="myForm" class="form">
							<fieldset>
							<legend>岗位基本信息</legend>
							<input type="hidden" id="postid" name="postid" value=""/>
							<input type="hidden" id="companyid" name="companyid" value=""/>
							<input type="hidden" id="deptid" name="deptid" value=""/>
							<input type="hidden" id="state" name="state" value="1"/>
							<input type="hidden" id="modiuser" name="modiuser" value=""/>
							<input type="hidden" id="modimemo" name="modimemo" value=""/>
							<input type="hidden" id="moditimestamp" name="moditimestamp" value=""/>
							<!--  <input type="hidden" id="interfacecode" name="interfacecode" value=""/>-->
							<input type="hidden" id="postcode" name="postcode" value=""/>
							<ul>
								<li>
									<span><em class="red">* </em>岗位名称：</span>
									<input id="postname" name="postname" class="{required:true,maxlength:30} inputstyle"   onblur="checkPostname();"/>
								</li>
							</ul>
							<ul id="bzlb1" style="display:none">
								<li>
									<span>编制类别(正式)：</span>
									<input id="budgettype1" name="budgettype1" type="hidden" value=""/>
									<input id="budgettypename1" name="budgettypename1" class="inputselectstyle" onclick="chooseBudgettypeTree('#budgettype1','#budgettypename1',$('#companyid').val(),callbackBudgettype);"/>
									<div class="search-trigger" onclick="chooseBudgettypeTree('#budgettype1','#budgettypename1',$('#companyid').val(),callbackBudgettype);"/>
								</li>
							</ul>
							<ul id="bzrs1" style="display:none">
								<li>
									<span>编制人数(正式)：</span>
									<input id="budgetnumber1" name="budgetnumber1" class="{number:true,maxlength:40} inputstyle" value=""/>
								</li>
							</ul>
							<ul id="bzlb2" style="display:none">
								<li>
									<span>编制类别(劳务)：</span>
									<input id="budgettype2" name="budgettype2" type="hidden" value=""/>
									<input id="budgettypename2" name="budgettypename2" class="inputselectstyle" onclick="chooseBudgettypeTree('#budgettype2','#budgettypename2',$('#companyid').val(),callbackBudgettype);"/>
									<div class="search-trigger" onclick="chooseBudgettypeTree('#budgettype2','#budgettypename2',$('#companyid').val(),callbackBudgettype);"/>
								</li>
							</ul>
							<ul id="bzrs2" style="display:none">
								<li>
									<span>编制人数(劳务)：</span>
									<input id="budgetnumber2" name="budgetnumber2" class="{number:true,maxlength:40} inputstyle" value=""/>
								</li>
							</ul>
							<ul>
				            	<li>
				            		<span>代码：</span>
								    <input id="interfacecode" name="interfacecode" class="{maxlength:30} inputstyle"/>
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
					    <div id="tab1" style="display:none;">
				        	<div style="padding-top:5px;">
								<div id="myTable" style="margin:0px auto;">
									<div id="gridbox" ></div>
				                </div>
			                </div>
					    </div>
					    
					    <!--  <div id="tab2" style="display:none;">
				        	<div style="padding-top:5px;">
								<div id="myQuota" style="margin:0px auto;">
									<div id="gridQuotabox" ></div>
				                </div>
			                </div>
					    </div>-->
					</div>	
				</div>			
			</div>
		</div>
	</div>
</div>















</body>
</html>