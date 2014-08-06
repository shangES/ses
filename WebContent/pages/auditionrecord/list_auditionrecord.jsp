<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>面试管理</title>
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
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
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
var pageState=false;


$(document).ready(function () {
	
    //列表加载
    loadGrid();
    
});




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



//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.auditionrecordguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	$.post("audition/delAuditionRecordByAuditionRecordId.do",{ids:array.toString()}, function() {
		mygrid.reload();
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












//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.mycandidatesguid='${param.mycandidatesguid}';
}




//批量审核
function audit(valid){
	var prompt=(valid==1?"审核发布":"取消发布");
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.auditionrecordguid);
	}
	if(array.length<=0){
		alert("请选择要"+prompt+"的数据！");
		return;
	}
	if(!confirm("确认要"+prompt+"选中数据吗？")){
		return;
	}
   	$.post("audition/auditAuditionRecordById.do",{ids:array.toString(),state:valid}, function() {
		mygrid.reload();
    });
	
}


//新增或修改
var addAuditionResultForm=null;
function editAuditionResult(id){
	$("#addAuditionResultWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		buttons: {
			"确定": function() {
				if(addAuditionResultForm.form){
					$("#addAuditionResultForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addAuditionResultForm").clearForm();
			loadAuditionResult(id);
			$("#auditionrecordguid").val(id);
			$("#isrelease").val(2);
			$("#modiuser").val("${username}");
			addAuditionResultForm=$("#addAuditionResultForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		mygrid.reload();
			    		$("#addAuditionResultWindow").dialog("close");
			    		pageState=true;
			        });
				}
			});
		}
	});
}



//数据加载
function loadAuditionResult(id){
	if(id!=null&&id!=''&&id!='null')
		$.getJSON("audition/getAuditionResultByAuditionResultId.do", {auditionrecordguid:id}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#addAuditionResultForm #' + key);
			        if(el) 
			            el.val(data[key]);
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








//返回
function back(){
	window.parent.convertView(pageState?null:'');
}

</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'auditionrecordguid'},
				{name : 'mycandidatesguid'},
				{name : 'maininterviewerguidname'},
				{name : 'auditiondate'},
				{name : 'auditionaddress'},
				{name : 'modiuser'},
				{name : 'moditimestamp'},
				{name : 'modimemo'},
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'auditionrecordguid' , header: "操作" , width :60 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				if(record.resulttype==null&&getCurentDateYMDHMS()>record.auditiondate)
        			return '<a href="javascript:editAuditionResult(\''+record.auditionrecordguid+'\');" title="填写面试结果">结果</a>';
        		return '';
			}},
			{id: 'isrelease' , header: "状态" , width :60 ,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				if(value==1){
					return '<em class="red">发布<em>';
				}
				return '未发布';
			}},
			{id: 'auditiondate', header: "面试时间", width :150 ,headAlign:'center',align:'center'},
			{id: 'auditionaddress' , header: "面试地址", width :250 ,headAlign:'center',align:'left'},
			{id: 'maininterviewerguidname' , header: "面试官", width :100 ,headAlign:'center',align:'left'},
			{id: 'username' , header: "面试官(抄送)", width :150 ,headAlign:'center',align:'left'},
			{id: 'resulttypename' , header: "结果" , width :90 ,headAlign:'center',align:'left'},
			{id: 'resultcontent' , header: "评语" , width :250 ,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'audition/searchAuditionRecord.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'audition/searchAuditionRecord.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '面试管理列表.xls',
		width:'99.8%',
		height:"542",  //"100%", // 330,
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
		skin:getGridSkin()
		
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

</script>

</head>
<body>
<div class="sort-cont sort-table">
	<div class="table">
		<div class="table-bar">
			<div class="table-title">
				表格名称
			</div>
			<div class="table-ctrl">
				<a class="btn"  href="javascript:audit(1);" ><i class="icon icon-ok-circle"></i><span>发布</span></a>
				<a class="btn" href="javascript:audit(2);"  ><i class="icon icon-ban-circle"></i><span>取消发布</span></a>
				<a class="btn" href="javascript:remove();"><i class="icon icon-remove"></i><span>删除</span></a>
				<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
			</div>
		</div>
		<div class="table-wrapper">
			<div id="myTable" style="height:550px;margin:5px auto;">
				<div id="gridbox" ></div>
               </div>
		</div>
	</div>
</div>
<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>



<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="" id="searchForm" class="form">
		
	</form>
</div>




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


<!-- 面试结果窗口 -->
<div id="addAuditionResultWindow" title="面试结果信息" style="display:none;">
	<form action="audition/saveOrUpateAuditionResult.do" id="addAuditionResultForm" class="form" method="post">
		<input type="hidden" id="auditionrecordguid" name="auditionrecordguid" value=""/>
		<input type="hidden" id="isrelease" name="isrelease" value=2 />
		<input type="hidden" id="moditimestamp" name="moditimestamp" />
		<input id="modiuser" name="modiuser" type="hidden" value="${username}" />
		<input id="modimemo" name="modimemo" type="hidden" value="" />
		<ul>
			<li>
			    <span><em class="red">* </em>面试结果：</span>
				<input id="resulttype" name="resulttype" type="hidden" value=""/>
			    <input id="resulttypename" name="resulttypename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#resulttype','#resulttypename','RESULTTYPE');"/>
 			    <div class="search-trigger" onclick="chooseOptionTree('#resulttype','#resulttypename','RESULTTYPE');" />
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>评语：</span>
				<textarea id="resultcontent" name="resultcontent"  rows="5" cols="40" class="{required:true,maxlength:100} areastyle"/></textarea>
			</li>
		</ul>
	</form>
</div>

</body>
</html>