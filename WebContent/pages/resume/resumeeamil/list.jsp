<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>邮箱管理</title>
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

$(document).ready(function () {
	
    //列表加载
    loadGrid();
    
    
  //日期
   $(".datepicker").datepicker({
       dateFormat: 'yy-mm-dd'
   });
  
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
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
		array.push(obj.resumeeamilguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
 	$.post("resume/delResumeEamilById.do",{ids:array.toString()}, function() {
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
	pam.readtype=$("#readtype").attr("checked")?0:1;
	pam.modtime_s=$("#modtime_s").val();
	pam.modtime_e=$("#modtime_e").val();
	pam.personal=$("#personal").val();
	pam.email=$("#email").val();
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



//标记已读
function markEamil(readtype){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		if(obj.readtype!=readtype)
			array.push(obj.resumeeamilguid);
	}
	if(array.length<=0){
		alert("请选择相应数据！");
		return;
	}
	if(!confirm("确认要操作选中数据吗？")){
		return;
	}
   	$.post("resume/updateByReadtype.do",{ids:array.toString(),readtype:readtype}, function() {
		mygrid.reload();
    });
}







//同步员工
function refresh(){
	$.post("resume/refreshResumeEamil.do",function() {
		alert("邮箱同步成功！");
		mygrid.reload();
    });
}
</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'resumeeamilguid'},
				{name : 'readtype'},
				{name : 'userguid'},
				{name : 'email'},
				{name : 'subject'},
				{name : 'content'},
				{name : 'modtime'},
				{name : 'rmk'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id:'readtype', header: "状态" , width :50,headAlign:'center',align:'center',renderer:function(value ,record,colObj,grid,colNo,rowNo){
				if(value==0){
					return '<img src="skins/images/email.png"/>';
				}
				return '';
			}},
			{id: 'personal' , header: "发件人" , width :150,headAlign:'center',align:'left'},
			//{id: 'email' , header: "邮箱" , width :250,headAlign:'center',align:'left'},
			{id: 'subject' , header: "主题" , width :400,headAlign:'center',align:'left'},
			{id: 'content' , header: "内容" , width :300,headAlign:'center',align:'left',hidden:true},
			{id: 'filename' , header: "附件名称" , width :200,headAlign:'center',align:'left',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='';
				var files=record.resumeeamilfiles;
				if(files!=null)
				for(var i=0;i<files.length;i++){
					var obj=files[i];
					htm+='<a href="resume/downloadResumeEmail.do?id='+obj.resumeeamilfileguid+'">';
					htm+=obj.filename;
					htm+='</a>';
					htm+='&nbsp;&nbsp;';
				}
				return htm;
			}},
			{id: 'modtime' , header: "投递时间" , width :150,headAlign:'center',align:'center'},
			{id: 'rmk' , header: "备注" , width :300,headAlign:'center',align:'left'}
		];
		
	var gridOption={
		id : grid_demo_id,
		loadURL :'resume/searchResumeEamil.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'resume/searchResumeEamil.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		}, 
		exportFileName : '邮箱管理信息表.xls',
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
		loadResponseHandler:function(response,requestParameter){
			var obj = jQuery.parseJSON(response.text);
			var page=obj.pageInfo;
			var hg=(page.pageSize+1)*33+50;
			
			mygrid.setSize('99.9%',hg);
			pageResize(hg);
		},
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('resumeemail.do?page=static&id='+record.resumeeamilguid);
			}
		}
	};
	mygrid=new Sigma.Grid( gridOption );
	Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );
}


function pageResize(height){
	$("#myTable").height(height);
	//计算高度
	_autoHeight();
}
</script>

</head>
<body>

<div class="sort">
	<div class="sort-title">
		<h3>邮箱管理</h3>
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
					<div style="float:left;">
						<label for="readtype">
							<input id="readtype" type="checkbox" class="checkboxstyle" onclick="mygrid.reload()" checked="true"/>只显示未读邮件
						</label>
						&nbsp;
						&nbsp;
					</div>
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none'}"><i class="icon icon-search"></i><span>搜索</span></a>
					<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none'}"><i class="icon icon-download" ></i><span>导出</span></a>
					
					<a class="btn" href="javascript:refresh();" style="display:${refresh?'':'none'}"><i class="icon icon-refresh"></i><span>邮箱同步</span></a>
					<a class="btn" href="javascript:markEamil(1);" style="display:${mark?'':'none'}"><i class="icon icon-map-marker"></i><span>标记为已读</span></a>
					
					<!-- <a class="btn" href="javascript:convertView('resumeemail.do?page=tab');" ><i class="icon icon-plus"></i><span>新增</span></a> -->
					<a class="btn" href="javascript:remove();" style="display:${del?'':'none'}"><i class="icon icon-remove" ></i><span>删除</span></a>
				</div>
			</div>
			<div class="table-wrapper">
				<div id="myTable" style="height:550px;margin:5px auto;">
					<div id="gridbox" ></div>
                </div>
			</div>
		</div>
	</div>
</div>
<iframe id="detail" name="detail" width="100%" height="100%" frameborder="0" src="" scrolling="no" style="display:none;"></iframe>



<!-- 搜索 -->
<div id="search" title="搜索条件设置" style="display:none;">
	<form action="" id="searchForm" class="form">
		<ul>
			<li>
				<span>发件人：</span>
				<input id="personal" name="personal" class="inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>邮箱：</span>
				<input id="email" name="email" class="inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
                <span>投递日期从：</span>
                <input id="modtime_s" name="modtime_s" class="inputselectstyle datepicker"/>
               <div class="date-trigger" onclick="$('#modtime_s').focus();"/>
            </li>
        </ul>
        <ul>
			<li>
                <span>至：</span>
				<input id="modtime_e" name="modtime_e" class="inputselectstyle datepicker"/>
				<div class="date-trigger" onclick="$('#modtime_e').focus();"/>
            </li>
        </ul>
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






</body>
</html>