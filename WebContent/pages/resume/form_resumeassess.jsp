<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>评价信息</title>
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
<script type="text/javascript" src="skins/js/public.js"></script>
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
var tid='${param.id}';
var userid='${userid}';

$(document).ready(function() {
	//列表加载
    loadGrid();
    
    //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
});

//取数据
function loadData(rid) {
	if(rid!=null&&rid!=''&&rid!='null')
		$.getJSON("resume/getResumeAssessById.do", {id:rid}, function(data) {
			if(data!=null){
				for (var key in data) {
			        var el = $('#myForm #' + key);
			        if(el)el.val(data[key]);
			    }
			}
		});
}



//导出
function expGrid(){
	mygrid.exportGrid('xls');
}





//删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.assessguid);
	}
	
	
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
 	$.post("resume/delResumeAssessById.do",{ids:array.toString()}, function() {
		mygrid.reload();
  	});
}








//表单
var myForm=null;
var rid=null;
function openForm(rid){
		$("#formWindow").dialog({
			autoOpen: true,
			modal: true,
			resizable:false,
			width:470,
			buttons: {
				"确定":function(){
					if(myForm.form()){
						$("#webuserguid").val(tid);
						$('#myForm').submit();
					}
				},
				"重置":function(){
					//重置表单
					resetMyForm();
				},
				"关闭": function() {
					$(this).dialog("close");
				}
			},
			open:function(){
				//重置表单
				resetMyForm();
				//加载数据
				loadData(rid);
				
				 //加载表单验证
				if(myForm==null)
					myForm=$("#myForm").validate();
					$('#myForm').ajaxForm(function(data) {
			    	alert("操作成功！");
			    	$("#formWindow").dialog("close");
			        mygrid.reload();
			    });
				
			    
				
				
			}
		});
}




//重置表单
function resetMyForm(){
	$("#myForm").clearForm();
}


//参数设置
var pam=null;
function initPagePam(){
	pam={};
	pam.expAll=0;
	pam.webuserguid=tid;
}



//返回
function back(){
	window.parent.convertView('');
}

</script>


<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
					{name : 'assessguid'},
					{name : 'webuserguid'},
					{name : 'assesslevel'},
					{name : 'assessresult'},
					{name : 'modiuser'},
					{name : 'moditimestamp'},
					{name : 'modimemo'}
			]
		};
		var colsOption = [
		      	{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
		    	{id: 'assesslevelname' , header: "评价等级" , width :120,headAlign:'center',align:'center'},
		    	{id: 'assesshierarchyname' , header: "评价体系" , width :120,headAlign:'center',align:'center'},
		    	{id: 'assessresult' , header: "评价结果" , width :300,headAlign:'center',align:'left'},
		    	{id: 'modiuser' , header: "评价人" , width :120,headAlign:'center',align:'left'},
		    	{id: 'moditimestamp' , header: "评价时间" , width :160,headAlign:'center',align:'center'},
		    	{id: 'modimemo' , header: "备注" , width :300,headAlign:'center',align:'left'}
		];
	var gridOption={
		id : grid_demo_id,
		loadURL :'resume/searchResumeAssess.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'resume/searchResumeAssess.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=1;
			mygrid.parameters=pam;
		},
		exportFileName : '评价信息.xls',
		width: "99.8%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
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
		skin:getGridSkin(),
		customRowAttribute : function(record,rn,grid){
		},
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				//修改
				openForm(record.assessguid);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}

</script>
</head>
<body>

<div class="table">
	<div class="table-bar">
		<div class="table-title">
			评价管理
		</div>
		<div class="table-ctrl">
			<span id="group" class="gruop_hidden">
				<a class="btn" href="javascript:expGrid();"><i class="icon icon-download"></i><span>导出</span></a>
				<a class="btn" href="javascript:openForm(null);"><i class="icon icon-plus"></i><span>新增</span></a>
				<a class="btn" href="javascript:remove();"><i class="icon icon-remove"></i><span>删除</span></a>
			</span>
			<a class="btn"  href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<div id="myTable" style="height:460px;margin:5px auto;">
			<div id="gridbox" ></div>
        </div>
	</div>
</div>







<!-- 表单 -->
<div id="formWindow" title="评价信息" style="display:none;">
	<form action="resume/saveOrUpdateResumeAssess.do" method="post" id="myForm" class="form">
		<input id="assessguid" name="assessguid" type="hidden" value=""/>
		<input id="webuserguid" name="webuserguid" type="hidden" value=""/>
		<input id="modiuser" name="modiuser" type="hidden" value=""/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		
		<ul>
			<li>
                <span><em class="red">* </em>评价等级：</span>
                <input id="assesslevel" name="assesslevel" type="hidden" value=""/>
			    <input id="assesslevelname" name="assesslevelname" class="{required:true,maxlength:4} inputselectstyle" onclick="chooseOptionTree('#assesslevel','#assesslevelname','ASSESSLEVEL');"/>
           		 <div class="search-trigger" onclick="chooseOptionTree('#assesslevel','#assesslevelname','ASSESSLEVEL');"/>
            </li>
        </ul>
        <ul>
		<li>
               <span><em class="red">* </em>评价体系：</span>
               <input id="assesshierarchy" name="assesshierarchy" type="hidden" value=""/>
		    <input id="assesshierarchyname" name="assesshierarchyname" class="{required:true,maxlength:4} inputselectstyle" onclick="chooseOptionTree('#assesshierarchy','#assesshierarchyname','ASSESSHIERARCHY');"/>
          		 <div class="search-trigger" onclick="chooseOptionTree('#assesshierarchy','#assesshierarchyname','ASSESSHIERARCHY');"/>
           </li>
        </ul>
		<ul>
			<li>
				<span><em class="red">* </em>评价结果：</span>
				<textarea id="assessresult" name="assessresult" rows="3" cols="40" style="width:250px;" class="{required:true,maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>


</body>
</html>
