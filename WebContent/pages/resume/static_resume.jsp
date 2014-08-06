<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<link rel="stylesheet" type="text/css" href="plugin/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/default/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/green/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/mac/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/china/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/skin/vista/skinstyle.css" />
<link rel="stylesheet" type="text/css" href="plugin/grid/calendar/calendar-blue.css"  />


<link rel="stylesheet" type="text/css" href="skins/css/form.css"/>
<link rel="stylesheet" type="text/css" href="skins/css/jquery-ui-1.8.15.custom.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="plugin/tree/zTreeIcons.css"/>
<link rel="stylesheet" type="text/css" href="plugin/grid/calendar/calendar-blue.css"  />



<style>
<!--
.resumeBody {color: #555555;font-size: 12px;border-top:1px solid #ddd;}
.resumeBody strong, .resumeBody h1, .resumeBody h5, .resumeBody h6 {color: #555555;font-size:14px;padding:0px;margin:0px;}
.resumeBody h1 {font-size: 32px;height: 42px;line-height: 1em;}
.resumeBody .headerImg {border: 1px solid #DDDDDD;padding: 5px;}
.resumeBody .ver-line {color: #555555;font-size: 14px;padding: 0 5px;}
.resumeBody h5, .resumeBody h6, .resumeBody strong {font-weight: bold;}
.resumeBody .details dt h5 {color: #315AAA;font-size: 14px;height: 26px;line-height: 26px;text-indent: 20px;}
.resumeBody .summary, .resumeBody .details dd {line-height: 25px;;margin: 10px;padding-left: 10px;}
.resumeBody .certificates, .resumeBody .training, .resumeBody .graduates, .resumeBody .social, .resumeBody .education-background, .resumeBody .project-experience, .resumeBody .work-experience {border-bottom: 1px dotted #CCCCCC;margin-bottom: 10px;padding-bottom: 5px;}
.resumeBody .certificates:last-child, .resumeBody .training:last-child, .resumeBody .graduates:last-child, .resumeBody .education-background:last-child, .resumeBody .project-experience:last-child, .resumeBody .work-experience:last-child {border-bottom: 0 none;}
.resumeBody .training h6 {margin-bottom: 10px;}
.resumeBody .em {}

table.static-table{border:0px;}
table.static-table td{border:0px;}
table.static-table tbody tr:HOVER {
background:none;
}

-->
</style>


<!-- 打印style,切勿修改 -->
<style type="text/css" id="remove">
 .resumeBody .details dt {background: url("skins/images/lookResumebg.jpg") no-repeat scroll 5px 0 transparent;} 
</style>

<script type="text/javascript" src="skins/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.layout.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>

<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="plugin/timepicker/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript" src="plugin/grid/calendar/calendar.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-en.js"></script>
<script type="text/javascript" src="plugin/grid/calendar/calendar-setup.js"></script>
<script type="text/javascript" src="plugin/grid/gt_grid_all.js"></script>
<script type="text/javascript" src="plugin/grid/gt_msg_cn.js"></script>
<script type="text/javascript" src="pages/tree.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="plugin/lodop/LodopFuncs.js"></script>


<script type="text/javascript">

var pageState=false;

$(document).ready(function () {
	

 
});





//返回
function back(){
	window.parent.convertView(pageState?null:'');
}



//评价
function openResumeAssess(id){
	$("#addResumeAssessWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:480,
		position:['center','top'],
		buttons: {
			"确定": function() {
				if(addResumeAssessForm.form){
					$("#addResumeAssessForm").submit();
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			$("#addResumeAssessForm").clearForm();
			$('#addResumeAssessForm input[id=webuserguid]').val(id);
			addResumeAssessForm=$("#addResumeAssessForm").validate({submitHandler: function(form) {
			    	$(form).ajaxSubmit(function(data) {
			    		alert("评价成功！");
			    		$("#addResumeAssessWindow").dialog("close");
			    		pageState=true;
			    		window.parent.callbackPageState(pageState);
			    		back();
			        });
				}
			});
		}
	});
}


//修改简历
function edit(){
	window.location.href='${baseUrl }resume.do?page=form&id='+'${resume.webuserguid}';
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


//人才库 推荐
var matchForm=null;
function openMacthWindow(webuserguid){
	 var userarray=new Array();
	 userarray.push(webuserguid);
		$.getJSON("mycandidates/checkMyCandidatesByUserGuid.do",{userguid:userarray.toString(),state:2}, function(data) {
			if(data!=null&&data!=""){
				alert(data);
				return;
			}else{
				openMacth(webuserguid);
			}
		});
		
}

function openMacth(webuserguid){
	$("#matchWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:470,
		position:['center','top'],
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
			$('#matchForm input[id=webuserguid]').val(webuserguid);
			//$("#webuserguid").val(webuserguid);
			$("#candidatesstate").val(2);
			$("#readtype").val(1);
			$("#candidatestype").val(2);
			$("#progress").val(0);
			
			//校验
			if(matchForm==null)
				matchForm=$("#matchForm").validate({submitHandler:function(form) {
				    	$(form).ajaxSubmit(function(data) {
				    		alert("操作成功!");
				    		//mygrid.reload();
				    		$("#matchWindow").dialog("close");
				    		pageState=true;
				    		window.parent.callbackPageState(pageState);
				    		back();
				        });
					}
				});
		}
	});
}


</script>


<script type="text/javascript">

//双击图片变大
function showPic(dom){
	var cityObj = $(dom);
	var cityOffset = $(dom).offset();
	var img = $("#mypic").attr("src");
	var htm='';
	htm+='<img style="width:100%;height:100%;border:1px solid #CCCCCC;" src="';
	htm+=img;
	htm+='">';
	var mypicwindow=$("#mypicwindow");
	mypicwindow.html(htm);
    mypicwindow.css({"left":cityOffset.left/2+"px"});
    mypicwindow.css({"top":cityOffset.top/2+"px"});
	mypicwindow.show();
}

function hidePic(){
	var mypicwindow=$("#mypicwindow");
	mypicwindow.hide();
}




//CSSRuleList，样式表的规则集合列表
function getStyleSheet(element) {
    return element.sheet || element.styleSheet;
}

//打印简历
function myPreview(){	
	LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  	
	var strBodyStyle="<style>"+document.getElementById("style1").innerHTML+"</style>";
	var link = document.getElementById("remove");
	var sheet = getStyleSheet(link);
	if(sheet&&sheet!=null){
		var cssRules=sheet.cssRules;
		//删除、浏览器判断
		if(cssRules!=null&&cssRules.length > 0){
			if(cssRules)
				sheet.deleteRule(0);
			else
				sheet.removeRule(0);//IE版本
		}
		else{
			$(".details dt").css({"background":"none"});
		}
	}
	
	
	
	$(".details dt").prepend('<img alt="" style="float:left;position:absolute;z-index:-1;margin-left:5px;" src="${baseUrl }skins/images/lookResumebg.jpg"/>');
	var strFormHtml=strBodyStyle+"<body><div align='center' style='border-bottom:1px solid #ddd;'><h1>个人简历</h1></div>"+document.getElementById("form1").innerHTML+"</body>";
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_样式风格");
	//纸张
	LODOP. SET_PRINT_PAGESIZE (1,0, 0,"A4");
	//如果前面剩余空间不足，关联对象顺序打印时就"从新页开始"
	LODOP.SET_PRINT_STYLEA("All","LinkNewPage",true);
	LODOP.ADD_PRINT_HTM("2%","5%","90%","90%",strFormHtml);
	LODOP.PREVIEW();
};

</script>




<!--打印-->
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="install_lodop32.exe"></embed>
</object> 
</head>
<body ondblclick="hidePic();">


<div class="table">
	<div class="table-bar">
		<div class="table-title">
			简历预览
		</div>
		<div class="table-ctrl">
			<a class="btn" id="pingjia2" href="javascript:openResumeAssess('${resume.webuserguid}')"><i class="icon icon-thumbs-up"></i><span>评价</span></a>
			<a class="btn" id="tuijian2" href="javascript:openMacthWindow('${resume.webuserguid}');"><i class="icon icon-thumbs-up"></i><span>推荐</span></a>
		    <a class="btn" href="javascript:edit();"><i class="icon icon-pencil"></i><span>修改</span></a>
		    <a class="btn" href="javascript:myPreview();"><i class="icon icon-print"></i><span>打印</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	
	<div class="table-wrapper" id="form1">
	
		<div class="resumeBody">
								
			<!-- 基本信息 -->
			<div class="summary">
			<c:if test="${resume.photo==null}">
				<img id="mypic" onclick="showPic(this);" style="max-width:200px;height:125px;float:right;" class="headerImg" alt="" src="${baseUrl }skins/images/nopic.jpg">
			</c:if>
			<c:if test="${resume.photo!=null}">
				<img id="mypic" onclick="showPic(this);" style="max-width:200px;height:125px;float:right;" class="headerImg" alt="" src="${resume.photo}">
			</c:if>
		           <h1>${resume.name }</h1>
		           ${resume.sexname } <span class="ver-line">|</span>
		           ${resume.birthday }生 <span class="ver-line">|</span>
		           现居住于${resume.homeplace } <br> 
		           ${resume.workagename }(工作经验) <span class="ver-line">|</span>
		           最高学历${resume.culturename }</span> <br> 
		           ${resume.mobile }(手机) <br>
		           E-mail：<a href="mailto:${resume.email }">${resume.email }</a> <br>
			</div>
			<dl class="details">
				<dt><h5>自我评价</h5></dt>
				<dd>
	               <div class="training">
	                    ${resume.valuation}
	               </div>
		         </dd>
		     </dl>    
		    <dl class="details">
				<dt><h5>求职意向</h5></dt>
				<dd>
	               <div class="training">
	               	   <strong>期望从事职业：</strong>${resume.occupation}<br>
	                   <strong>行业：</strong>${resume.industry}<br>
	                   <strong>期望月薪：</strong>${resume.salary}<br>
	                   <strong>目前状况：</strong>${resume.situation}<br>
	               </div>
		         </dd>     
		    </dl>      
			<dl class="details">
				<dt><h5>工作经历</h5></dt>
				<dd>
					<c:forEach items="${workexperiences }" var="item">
		               <div class="work-experience">
		                   <p>${item.startdate } -- ${item.enddate==null?'至今':item.enddate }</p>
		                   <h6>${item.workunit } <span class="ver-line">|</span> ${item.posation } </h6>
		                   <p>
		                       <strong>职责描述：</strong><br>
		                       ${item.jobdescription }
		                   </p>
		               </div>
		               </c:forEach>
		           </dd>
		                 
				<dt><h5>项目经验</h5></dt>
				<dd>
					<c:forEach items="${projectexperiences }" var="item">
		               <div class="project-experience">
		                   <p>${item.startdate } -- ${item.enddate==null?'至今':item.enddate }</p>
		                   <h6>${item.itemname }</h6>
		                   <p>
		                   <strong>职责描述：</strong><br>
		                    ${item.jobdescription }
		                   </p>
		               </div>
		               </c:forEach>
		         </dd>
		           
		           
				<dt><h5>教育经历</h5></dt>
				<dd>
					<c:forEach items="${educationexperiences }" var="item">
		               <div class="education-background">
		                  <p>${item.startdate } -- ${item.enddate==null?'至今':item.enddate }</p>
		                   <h6>${item.school } <span class="ver-line">|</span> ${item.specialty } <span class="ver-line">|</span> ${item.culturename } </h6>
		                   <p>
		                   <strong>专业描述：</strong><br>
		                    ${item.majordescription }
		                   </p>
		               </div>
		               </c:forEach>
		           </dd>
		            
		            
				<dt><h5>培训经历</h5></dt>
				<dd>
					<c:forEach items="${trainingexperiences }" var="item">
		               <div class="training">
		                   <p>${item.startdate } -- ${item.enddate==null?'至今':item.enddate }</p>
		                   <h6>${item.traininginstitutions }</h6>
		                      <strong>所获证书：</strong>${item.certificate }<br>
		                   <p>
		                   	<strong>培训描述：</strong><br>
		                     		${item.trainingcontent }
		                      </p>
		               </div>
		               </c:forEach>
		          </dd>
			</dl>	
		</div>
	</div>
</div>



<div id="mypicwindow" class="chooseTree" style="background:#f8f8f8;border:1px solid #CCCCCC;max-width:200px; max-height:400px;padding:10px;margin: 0 auto;">
</div>



<!-- 评价信息 -->
<div id="addResumeAssessWindow" title="评价信息" style="display:none;">
	<form action="resume/saveOrUpdateResumeAssess.do" method="post" id="addResumeAssessForm" class="form">
		<input id="assessguid" name="assessguid" type="hidden" value=""/>
		<input id="webuserguid" name="webuserguid" type="hidden" value=""/>
		<input id="modiuser" name="modiuser" type="hidden" value=""/>
		<input id="moditimestamp" name="moditimestamp" type="hidden" value=""/>
		
		<ul>
			<li>
                <span><em class="red">* </em>评价等级：</span>
                <input id="assesslevel" name="assesslevel" type="hidden" value=""/>
			    <input id="assesslevelname" name="assesslevelname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#assesslevel','#assesslevelname','ASSESSLEVEL');"/>
           		 <div class="search-trigger" onclick="chooseOptionTree('#assesslevel','#assesslevelname','ASSESSLEVEL');"/>
            </li>
        </ul>
        <ul>
			<li>
                <span><em class="red">* </em>评价体系：</span>
                <input id="assesshierarchy" name="assesshierarchy" type="hidden" value=""/>
			    <input id="assesshierarchyname" name="assesshierarchyname" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#assesshierarchy','#assesshierarchyname','ASSESSHIERARCHY');"/>
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
		<ul>
			<li>
				<span>备注：</span>
				<textarea id="modimemo" name="modimemo" rows="3" cols="40" style="width:250px;" class="{maxlength:500} areastyle"></textarea>
			</li>
		</ul>
	</form>
</div>

</body>




<!-- 打印样式 -->
<style id="style1">

.table-wrapper{
	padding:5px 10px 10px 10px;
}

table.static-table {
    border:0px;
	width: 100%;
	table-layout: fixed;
	border-top:0px solid #ccc;
	border-right:0px solid #ccc;
}

table.static-table th {
	border-left:0px solid #ccc;
	border-bottom:0px solid #ccc;
	border-right:0px solid #ccc;
	border-top:0px solid #ccc;
	height: 35px;
	font-weight:normal;
	color:#000;
	overflow: hidden;
	white-space: nowrap;
	word-break: keep-all;
	word-wrap: normal;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
}

table.static-table td {
	border-left:0px solid #ccc;
	border-bottom:0px solid #ccc;
	border-right:0px solid #ccc;
	border-top:0px solid #ccc;
	qdisplay: block;
	height: 35px;
	line-height: 35px;
	padding:0px 5px;
	overflow: hidden;
	white-space: nowrap;
	word-break: keep-all;
	word-wrap: normal;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
}

table.static-table tbody tr:HOVER {
	background:#f8f8f8;
}

.resumeBody {color: #555555;font-size: 12px;}
.resumeBody strong, .resumeBody h1, .resumeBody h5, .resumeBody h6 {color: #555555;font-size:14px;padding:0px;margin:0px;}
.resumeBody h1 {font-size: 32px;height: 42px;line-height: 1em;}
.resumeBody .headerImg {border: 1px solid #DDDDDD;padding: 5px;}
.resumeBody .ver-line {color: #555555;font-size: 14px;padding: 0 5px;}
.resumeBody h5, .resumeBody h6, .resumeBody strong {font-weight: bold;}
.resumeBody .details dt h5 {color: #315AAA;font-size: 14px;height: 26px;line-height: 26px;text-indent: 20px;}
.resumeBody .summary, .resumeBody .details dd {line-height: 25px;;margin: 10px;padding-left: 10px;}
.resumeBody .certificates, .resumeBody .training, .resumeBody .graduates, .resumeBody .social, .resumeBody .education-background, .resumeBody .project-experience, .resumeBody .work-experience {border-bottom: 1px dotted #CCCCCC;margin-bottom: 10px;padding-bottom: 5px;}
.resumeBody .certificates:last-child, .resumeBody .training:last-child, .resumeBody .graduates:last-child, .resumeBody .education-background:last-child, .resumeBody .project-experience:last-child, .resumeBody .work-experience:last-child {border-bottom: 0 none;}
.resumeBody .training h6 {margin-bottom: 10px;}
.resumeBody .em {}

.training .work-experience .project-experience .education-background{font-size: 28px;font-family:'KaiTi_GB2312';}
</style> 

</html>
