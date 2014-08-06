<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>简历管理</title>
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

<link rel="stylesheet" type="text/css" href="plugin/swfupload/css/default.css"/>


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
<script type="text/javascript" src="skins/js/jquery.ajaxupload.3.6.js"></script>

<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="pages/resume/recommend/tree.js"></script>

<script type="text/javascript" src="plugin/swfupload/js/swfupload.js"></script>
<script type="text/javascript" src="plugin/swfupload/js/swfupload.swfobject.js"></script>
<script type="text/javascript" src="plugin/swfupload/js/swfupload.queue.js"></script>
<script type="text/javascript" src="plugin/swfupload/js/fileprogress.js"></script>
<script type="text/javascript" src="plugin/swfupload/js/handlers.js"></script>


<script type="text/javascript" src="pages/resume/uploadlist.js"></script>


<script type="text/javascript">
var mark='${param.mark}';

$(document).ready(function () {
	
    //列表加载
    loadGrid();
    
    initManyUpload('${baseUrl }');
    
  //日期
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd'
    });
    
    //关闭等待层
    if(window.parent.hidenLoading)
    	window.parent.hidenLoading();
});


//导入
function impGrid(){
	
}




//导出
function expGrid(){
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	$("#dialog_exp").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:130,
		position:[x,y],
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



//物理删除
function remove(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.webuserguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	$.post("resume/delResumeById.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
}

//逻辑删除
function logicdelete(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.webuserguid);
	}
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
	$.post("resume/updateResumeById.do",{ids:array.toString(),mark:0}, function() {
		mygrid.reload();
    });
}












//搜索
var searchForm=null;
function searchGrid(){
	//计算位置
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	
	$("#search").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:[x,y],
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
	pam.resumetype=$("#resumetype").val();
	pam.culture=$("#culture").val();
	pam.name=$("#name").val();
	pam.workage=$("#workage").val();
	pam.mobile = $("#mobile").val();
	pam.email = $("#email").val();
	pam.assesslevel = $("#assesslevel").val();
	pam.assesshierarchy = $("#assesshierarchy").val();
	pam.homeplace = $("#homeplace").val();
	pam.sex = $("#sex").val();
	pam.birthday_s=$("#birthday_s").val();
	pam.birthday_e=$("#birthday_e").val();
	pam.mark=$("#myvalid").attr("checked")?1:0;
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


</script>


<script type="text/javascript">

//回调
function callbackRecruiter(){
	$("#postname").val(null);
}


//回调
function callbackChooseRecruiter(id){
	$("#recruitpostguid").val(id);
}



//公司回调
function callBackCompany(){
	$("#recommenddeptid").val(null);
	$("#recommenddeptname").val(null);

	
	//部门选择回调
	callbackDept();
}





//部门选择回调
function callbackDept(){
	$("#recommendpostguid").val(null);
	$("#recommendpostname").val(null);
	$("#userguid").val(null);
	$("#username").val(null);
}


//用户回调
function callbackUser(){
	var id=$("#userguid").val();
	if(id!=null&&id!=''&&id!='null'){
		$.getJSON("employee/getPositionByUserId.do", {id:id}, function(data) {
			if(data!=null){
				$("#recommendcompanyid").val(data.companyid);
				$("#recommendcompanyname").val(data.companyname);
				$("#recommenddeptid").val(data.deptid);
				$("#recommenddeptname").val(data.deptname);
			}
		});
	}
}


 // 人才库 推荐
var matchForm=null;
function openMacthWindow(webuserguid,index){
	 var userarray=new Array();
	 userarray.push(webuserguid);
		$.getJSON("mycandidates/checkMyCandidatesByUserGuid.do",{userguid:userarray.toString(),state:2}, function(data) {
			if(data!=null&&data!=""){
				alert(data);
				return;
			}else{
				openMacth(webuserguid,index);
			}
		});
		
}

function openMacth(webuserguid,index){
	//计算位置
	var y=100;
	if(index!=null){
		var cityOffset = $("tr[__gt_ds_index__="+index+"]").offset();
		y=cityOffset.top;
	}
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	
	$("#matchWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:[x,y],
		buttons: {
			"确定":function(){
				if(matchForm.form()){
					$("#matchForm").submit();
				}
			},
			"重置":function(){
				$("#matchForm").clearForm();
			},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			$("#matchForm").clearForm();
			$("#webuserguid").val(webuserguid);
			$("#candidatesstate").val(2);
			$("#readtype").val(1);
			$("#candidatestype").val(2);
			$("#progress").val(0);
			
			//校验
			if(matchForm==null)
				matchForm=$("#matchForm").validate({submitHandler:function(form) {
				    	$(form).ajaxSubmit(function(data) {
				    		alert("操作成功!");
				    		mygrid.reload();
				    		$("#matchWindow").dialog("close");
				        });
					}
				});
		}
	});
}


//简历单个导入
function impHTML(){
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 470/2;
	$("#impHTML").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:500,
		height:150,
		position:[x,y],
		buttons: {
			"确定": function() {
				$("#uploadFile").submit();
				$(this).dialog("close");
		},
			"关闭": function() {
				$(this).dialog("close");
			}
		},
		open:function(){
			$("#uploadFile").resetForm();
			//加载表单验证
		    $("#uploadFile").validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						mygrid.reload();
					});
				}
			});
		}
	});
}

//发送短信
function sendMsg(){
	var array=new Array();
	var namearray=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		array.push(cords[i].mobile);
		namearray.push(cords[i].name+'('+cords[i].mobile+')');
	}
	if(array.length<=0){
		alert('请选择要发送的数据！');
		return;
	}
	if(!confirm('确认要发送短信吗？')){
		return;
	}
	
	//回调发送短信的页面
	window.parent.parent.showNote(namearray.toString(),array.toString());
}



//回车搜索
function formSubmit(){
	mygrid.reload();
	$("#search").dialog("close");
}


</script>




<script type="text/javascript">	
var mygrid=null;
function loadGrid(){
	var size=getGridSize();
	var grid_demo_id = "myGrid1";
	var dsOption= {
			fields :[
				{name : 'webuserguid'},
				{name : 'resumetype'},
				{name : 'name'},
				{name : 'sex'},
				{name : 'birthday'},
				{name : 'mobile'},
				{name : 'email'},
				{name : 'homeplace'},
				{name : 'workage'},
				{name : 'culture'},
				{name : 'photo'},
				{name : 'recommenduserguid'},
				{name : 'modtime'},
				{name : 'rmk'}
			]
		};
		var colsOption = [
			{id: '选择' ,isCheckColumn : true, editable:false,headAlign:'center',align:'center'},
			{id: 'candidatesstate' , header: "操作" , width :70,headAlign:'center',align:'center',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='&nbsp;';
				if(!record.isrecommend){
					htm+='<a href="javascript:openMacthWindow(\''+record.webuserguid+'\','+rowNo+')">';
					htm+='推荐';
					htm+='</a>';
				}
			   return htm;
			  }},
			{id: 'mark' , header: "人才库" , width :50 ,headAlign:'center',align:'center',renderer:function(value,record,colObj,grid,colNo,rowNo){
				var htm='';
				if(value==1){
					htm+='<img src="skins/images/vcard.png" title="已经纳入人才库"/>';
				}
				return htm;
			}},
			{id: 'name' , header: "姓名" , width :80 ,headAlign:'center',align:'left'},
			{id: 'sexname' , header: "性别" , width :60 ,headAlign:'center',align:'center'},
			{id: 'birthday' , header: "出生日期" , width :90 ,headAlign:'center',align:'center'},
			{id: 'birthdayname' , header: "年龄" , width :50 ,headAlign:'center',align:'center',number:true},
			{id: 'mobile' , header: "手机" , width :120 ,headAlign:'center',number:true},
			{id: 'email' , header: "电子邮件" , width :150 ,headAlign:'center'},
			{id: 'workagename' , header: "工作年限" , width :100 ,headAlign:'center',align:'center'},
			{id: 'culturename' , header: "最高学历" , width :100 ,headAlign:'center',align:'center'},
			{id: 'homeplace' , header: "现居住地" , width :180 ,headAlign:'center',align:'left'},
			{id: 'modimemo' , header: "备注" , width :200 ,headAlign:'center',headAlign:'left',align:'left'}
		];
	var gridOption={
		id : grid_demo_id,
		loadURL :'resume/searchResume.do',
		beforeLoad:function(reqParam){
			initPagePam();
			reqParam['parameters']=pam;
		},
		exportURL : 'resume/searchResume.do?export=true',
		beforeExport:function(){
			initPagePam();
			pam.expAll=$('input[name="xls"]:checked').val();
			mygrid.parameters=pam;
		},
		exportFileName : '简历信息表.xls',
		width: "99.8%",//"100%", // 700,
		height: "100%",  //"100%", // 330,
		container : 'gridbox', 
		pageSizeList : [size,size*2,size*4,size*6,size*8,size*10],
		stripeRows: false,
		remoteFilter:true,
		showIndexColumn:true,
		selectRowByCheck:true,
		replaceContainer : true,
		showGridMenu : true,
		allowFreeze	: true ,
		allowHide	: true ,
		allowGroup	: true ,
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
		customRowAttribute : function(record,rn,grid){
			if(record.resumetype==2){
				return 'style="background:#edf4fe;"';
			}else if(record.resumetype==3){
				return 'style="background:#ffffec;"';
			}
			
		},
		onDblClickCell:function(value, record , cell, row, colNO, rowNO,columnObj,grid){
			if(colNO!=0){
				convertView('resume.do?page=tab&id='+record.webuserguid);
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
		<h3>简历管理</h3>
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
						<label for="myvalid">
							<input id="myvalid" type="checkbox" class="checkboxstyle" onclick="mygrid.reload();" checked="true"/> 只显示人才的信息
						</label>
						&nbsp;
						&nbsp;
					</div>
					<a class="btn" href="javascript:searchGrid();" style="display:${search?'':'none'}"><i class="icon icon-search"></i><span>搜索</span></a>
					  <!--<a class="btn" href="javascript:impHTMLList();" style="display:${exp?'':'none'}"><i class="icon icon icon-qrcode"></i><span>批量导入</span></a>-->
					<!--  <a class="btn" href="javascript:convertView('resume.do?page=import');" style="display:${imp?'':'none'}"><i class="icon icon-qrcode"></i><span>导入</span></a>-->
					<a class="btn" href="javascript:expGrid();" style="display:${exp?'':'none'}"><i class="icon icon-list-alt"></i><span>导出</span></a>
					<!-- <a class="btn" href="javascript:convertView('resume.do?page=tab');" style="display:${add?'':'none'}"><i class="icon icon-plus"></i><span>新增</span></a>-->
					<!--  <a class="btn" href="javascript:remove();"><i class="icon icon-remove" style="display:${del?'':'none'}"></i><span>物理删除</span></a>-->
					<a class="btn" href="javascript:sendMsg();"><i class="icon icon-envelope"></i><span>发送短信</span></a>
					<a class="btn" href="javascript:logicdelete();" style="display:${logicdel?'':'none'}"><i class="icon icon-remove"></i><span>删除</span></a>
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
	<form action="javascript:void(0);" id="searchForm" class="form" onkeydown="javascript:if(event.keyCode==13)formSubmit();">
		<ul>
			<li>
				<span>来源渠道：</span>
				<input id="resumetype" name="resumetype" type="hidden" value=""/>
				<input id="resumetypename" name="resumetypename" class="inputselectstyle" onclick="chooseOptionTree('#resumetype','#resumetypename','RESUMETYPE');"/>
				<div class="select-trigger" onclick="chooseOptionTree('#resumetype','#resumetypename','RESUMETYPE');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>姓名：</span>
				<input id="name" name="name" class="{maxlength:25} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				 <span>性别：</span>
				 <input id="sex" name="sex" type="hidden" value=""/>
				 <input id="sexname" name="sexname" class="inputselectstyle" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
			     <div class="select-trigger" onclick="chooseOptionTree('#sex','#sexname','SEX');"/>
			</li>
		</ul>
		<ul>
         	<li>
			    <span>年龄从：</span>
			    <input id="birthday_e" name="birthday_e" class="{digits:true} inputstyle" style="width:97px;"/>
			</li>
            <li>
			    <span style="width:16px;">至&nbsp;</span>
			    <input id="birthday_s" name="birthday_s" class="{digits:true} inputstyle" style="width:97px;"/>
			</li>
        </ul>
		<ul>
			<li>
				 <span>现居住地：</span>
				 <input id="homeplace" name="homeplace" class="{maxlength:50} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
                <span>评价等级：</span>
                <input id="assesslevel" name="assesslevel" type="hidden" value=""/>
			    <input id="assesslevelname" name="assesslevelname" class="{maxlength:4} inputselectstyle" onclick="chooseOptionTree('#assesslevel','#assesslevelname','ASSESSLEVEL');"/>
           		 <div class="search-trigger" onclick="chooseOptionTree('#assesslevel','#assesslevelname','ASSESSLEVEL');"/>
            </li>
        </ul>
        <ul>
			<li>
                <span>评价体系：</span>
                <input id="assesshierarchy" name="assesshierarchy" type="hidden" value=""/>
			    <input id="assesshierarchyname" name="assesshierarchyname" class="{maxlength:4} inputselectstyle" onclick="chooseOptionTree('#assesshierarchy','#assesshierarchyname','ASSESSHIERARCHY');"/>
           		 <div class="search-trigger" onclick="chooseOptionTree('#assesshierarchy','#assesshierarchyname','ASSESSHIERARCHY');"/>
            </li>
        </ul>
		<ul>
			<li>
				<span>电子邮件：</span>
				<input id="email" name="email" class="{maxlength:50,email:true} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>手机：</span>
				<input id="mobile" name="mobile" class="{maxlength:50} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>最高学历：</span>
				<input id="culture" name="culture" type="hidden" value=""/>
				<input id="culturename" name="culturename" class="inputselectstyle" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
				<div class="select-trigger" onclick="chooseOptionTree('#culture','#culturename','CULTURE');"/>
			</li>
		</ul>
		<ul>
			<li>
				<span>工作年限：</span>
				<input id="workage" name="workage" type="hidden" value=""/>
				<input id="workagename" name="workagename" class="inputselectstyle" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
				<div class="select-trigger" onclick="chooseOptionTree('#workage','#workagename','WORKAGE');"/>
			</li>
		</ul>
		<ul>
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






<!-- 人才推荐-->
<div id="matchWindow" title="推荐信息" style="display:none;">
	<form action="mycandidates/saveMyCandidateAndRecommend.do" method="post" id="matchForm" class="form">
		<input id="webuserguid" name="webuserguid" type="hidden" value=""/>
		<input id="candidatesstate" name="candidatesstate" type="hidden" value=""/>
		<input id="readtype" name="readtype" type="hidden" value=""/>
		<input id="candidatestype" name="candidatestype" type="hidden" value=""/>
		<input id="progress" name="progress" type="hidden" value=""/>
		<input id="candidatestime" name="candidatestime" type="hidden" value=""/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		
		<ul>
			<li>
			    <span><em class="red">* </em>推荐公司：</span>
			    <input id="recommendcompanyid" name="recommendcompanyid" type="hidden" value=""/>
			    <input id="recommendcompanyname" name="recommendcompanyname" class="{required:true} inputstyle disabled" readonly="true"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>推荐部门：</span>
			    <input id="recommenddeptid" name="recommenddeptid" type="hidden" value=""/>
			    <input id="recommenddeptname" name="recommenddeptname" class="{required:true} inputstyle disabled" readonly="true"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>推荐到：</span>
				<input id="userguid" name="userguid" type="hidden" value=""/>
				<input id="username" name="username" class="{required:true} inputselectstyle" onclick="chooseinterviewerTree('#userguid','#username',$('#recommenddeptid').val(),callbackUser);"/>
				<div class="select-trigger" onclick="chooseinterviewerTree('#userguid','#username',$('#recommenddeptid').val(),callbackUser);"/>
			</li>
		</ul>
		<ul>
			<li>
				<span><em class="red">*</em>推荐岗位：</span>
				<input id="recommendpostguid" name="recommendpostguid" type="hidden" value=""/>
				<input id="recommendpostname" name="recommendpostname" class="{required:true} inputselectstyle" onclick="chooseMyPostTree('#recommendpostguid','#recommendpostname',$('#recommenddeptid').val());"/>
				<div class="select-trigger" onclick="chooseMyPostTree('#recommendpostguid','#recommendpostname',$('#recommenddeptid').val());"/>
			</li>
		</ul>
		<!--  
		<ul>
          	<li>
               <span><em class="red">* </em>招聘专员：</span>
               <input id="userid" name="userid" type="hidden" value=""/>
			   <input id="useridname" name="useridname" class="{required:true} inputselectstyle" onclick="chooseRecruiterTree('#userid','#useridname',callbackRecruiter);"/>
			   <div class="search-trigger" onclick="chooseRecruiterTree('#userid','#useridname',callbackRecruiter);" />
            </li>
           
          	<li>
		      <span><em class="red">* </em>推荐职务：</span>
		      <input id="recruitpostguid" name="recruitpostguid" type="hidden" value=""/>
			  <input id="postname" name="postname" class="{required:true} inputselectstyle" onclick="choosePostNameTree('#postname',$('#userid').val(),callbackChooseRecruiter);"/>
		      <div class="search-trigger" onclick="choosePostNameTree('#postname',$('#userid').val(),callbackChooseRecruiter);" />
		   </li>
        </ul>
        -->
		<ul>
			<li>
				<span>备注：</span>
				<textarea id="modimemo" name="modimemo" rows="3" cols="40" style="width:250px;" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>


<!-- 选择导入HTML -->
<div id="impHTML" style="display:none;" title="选择HTML简历">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<form id="uploadFile" action="resume/resumeUpload.do" method="post"  style="text-align: center;" enctype="multipart/form-data">
					<input type="hidden" id="table" name="table" value="tmp"/>
				     <input id="filepath" type="file" name="filepath" style="width:100%;height:25px;line-height:25px;padding-left:5px;" size="65"/>
			   	</form>
			</td>
		</tr>
	</table>
</div>


<!-- 批量上传窗口 -->
<div id="uploadManyWindow" style="display:none;background:#eee;" title="选择上传文件（注：仅限于html文件）" >
	<div style="width:100%;">
		<div class="toolbar" style="height:30px;">
			<span class="title">
				<span id="spanButtonPlaceholder" style="width:100px;height:20px;"></span>
			</span>
			<span class="more">
				<input id="btnUpload" type="button" value="上传文件" class="btns" />
				<input id="btnCancel" type="button" value="全部取消" disabled="disabled" class="btns" />
			</span>
		</div>
		<div style="height:250px;overflow-x:hidden;overflow-y:auto;background:#fff;">
			<form id="form1" action="uploadFileList.do" method="post" enctype="multipart/form-data">
				<table id="idFileList" class="upload-table" border="0" cellpadding="0" cellspacing="0">
					<thead>
						<tr class="uploadTitle">
							<th>文件名</th>
							<th width="80px">文件大小</th>
							<th width="150px">状态</th>
							<th width="35px">&nbsp;</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</form>
		</div>
		<div class="toolbar">
			等待上传 <span id="idFileListCount">0</span> 个 ，成功上传 <span id="idFileListSuccessUploadCount">0</span> 个
		</div>
		<div id="divSWFUploadUI" style="visibility: hidden;"></div>
		<noscript style="display: block; margin: 10px 25px; padding: 10px 15px;">
			很抱歉，相片上传界面无法载入，请将浏览器设置成支持JavaScript。
		</noscript>
		<div id="divLoadingContent" class="content" style="display: none;">
			上传界面正在载入，请稍后...
		</div>
		<div id="divLongLoading" class="content" style="display: none;">
			上传界面载入失败，请确保浏览器已经开启对JavaScript的支持，并且已经安装可以工作的Flash插件版本。
		</div>
		<div id="divAlternateContent" class="content" style="display: none;">
			很抱歉，相片上传界面无法载入，请安装或者升级您的Flash插件。
			请访问： <a href="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" target="_blank">Adobe网站</a> 获取最新的Flash插件。
		</div>
	</div>
</div>



</body>
</html>