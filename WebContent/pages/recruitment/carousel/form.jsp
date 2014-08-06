<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>轮播图片管理</title>
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
<script type="text/javascript" src="skins/js/public.js"></script>
<script type="text/javascript" src="skins/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript" src="skins/js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="plugin/tree/jquery.ztree.all.min.js"></script>
<script type="text/javascript" src="skins/js/jquery.ajaxupload.3.6.js"></script>
<script type="text/javascript" src="plugin/form/jquery.form.js"></script>
<script type="text/javascript" src="plugin/form/jquery.validate.js"></script>
<script type="text/javascript" src="plugin/form/lib/jquery.metadata.js"></script>
<script type="text/javascript" src="plugin/ckeditor/ckeditor.js"></script>


<script type="text/javascript">
var tid='${param.id}';
var pageState=false;

$(document).ready(function() {
    //加载表单验证
    $("#myForm").validate({submitHandler:function(form) {
	    	$(form).ajaxSubmit(function(data) {
				alert("操作成功！");
	    		
	    		//重新加载
	    		loadData(data.carouselguid,false);
	    		
	    		pageState=true;
	        });
		}
	});
    
    
  	
    
  	//加載數據
    loadData(tid,true);
    
    
});




//取数据
function loadData(tid,state) {
	if(tid!=null&&tid!=''&&tid!='null')
		$.getJSON("recruitment/getCarouselById.do", {id:tid}, function(data) {
			if(data!=null){
				for(var key in data){
					//正文
					if(key=='carouselcontent'){
						var content=data[key];
						if(content!=null){
							$("#carouselcontent").val(content.carouselcontent);
						}
					}else if($('#myForm #'+key)){
						$('#myForm #'+key).val(data[key]);
					}
					
					//头像
					if(data.titlepic!=null){
						$("#upload_button").attr("src", data.titlepic);
						$("#clearPhoto").show();
					}
				}
				
				//判定下状态
				initPageState();
				//渲染编辑器
				if(state)
					renderHtmlBox();
			}
		});
	else{
		//控制状态
		initPageState();
		
		//渲染编辑器
		if(state)
			renderHtmlBox();
	}
}








//页面状态
function initPageState(){
	//上传照片
	initPhoto();
	
	if(!${edit==true}){
		//编辑器只读
		CKEDITOR.config.readOnly = true; 
		
		formDisabled(true);
		$("#save").hide();
		$("#audit").hide();
		$("#cancel").hide();
		return;
	}
	
	
	//审核权限
	if(${audit==true}){
		//判断是否审核
		var isaudited=$("#isaudited").val();
		//已经审核
		if(isaudited==0){
			$("#cancel").show();
			$("#save").hide();
			$("#audit").hide();
			formDisabled(true);
			
			//编辑器只读
			CKEDITOR.config.readOnly = true; 
			
	   	}else{
	   		$("#cancel").hide();
			$("#save").show();
			$("#audit").show();
			formDisabled(false);
			
			//编辑器只读
			CKEDITOR.config.readOnly = false; 
	   	}
	}
}




//失效与恢复
function valid(state){
	$("#valid").val(state);
 	var isaudited=$("#isaudited").val();
	save(isaudited);
}










//保存
function save(isaudit){
	var content=CKEDITOR.instances.carouselcontent.getData();
	if(content!=null&&content!=''&&content.length>67){
		//審核人去掉
		if(isaudit==1){
			$("#audituser").val(null);
		}else{
			$("#audituser").val('${userid}');
		}
		$("#isaudited").val(isaudit);
		$("#carouselcontent").val(content);
		$("#myForm").submit();
	}else
		alert("轮播图文不能为空！");
}






//返回
function back(){
	window.parent.convertView(pageState?null:'');
}











</script>

<script type="text/javascript">
//上传照片
function initPhoto(){
	new AjaxUpload('#upload_button', {
    	id:'upFile',
    	action: 'uploadFile.do',
    	name:'file1',
    	autoSubmit:true,
    	responseType: 'json',
    	data: {table:'j_carousel_photo'},
  	onSubmit: function(file, ext){
  		if (ext && /^(jpg|png|jpeg|gif)$/i.test(ext)) {  
  			
             } else {
                 alert('只能上传jpg|png|jpeg|gif图片!');
                 return false;
             }
  	},  
  	onComplete: function(filename,obj) {
  		$("#upload_button").attr("src",obj.filepath);
  		$("#clearPhoto").show();
  		$("#titlepic").val(obj.filepath);
  	}
 	});
}

//清空照片
function clearPhoto(){
	$("#upload_button").attr("src","skins/images/nopic.jpg");
	$("#clearPhoto").hide();
	$("#titlepic").val("");
}





//渲染编辑器
function renderHtmlBox(){
	CKEDITOR.replace('carouselcontent',{
		height:500,
		fullPage : true,
		filebrowserFlashUploadUrl : '${baseUrl}uploadCkeditFile.do?directory=ckedit',
		filebrowserImageUploadUrl : '${baseUrl}uploadCkeditFile.do?directory=ckedit'
	});
}

</script>




</head>

<body>
<div class="sort">
	<div class="sort-title">
		<h3>轮播图片管理</h3>
		<div class="title-ctrl">
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',false);"><i class="icon icon-chevron-up"></i></a>
			<a class="btn-ctrl" href="javascript:chevronUpDown('.sort-cont',true);"><i class="icon icon-chevron-down"></i></a>
		</div>
	</div>
<div class="table">
	<div class="table-bar">
		<div class="table-title">
			轮播图片信息
		</div>
		<div class="table-ctrl">
			<!-- 
			<a class="btn" id="lose"  href="javascript:valid(0);" style="display:none"><i class="icon icon-trash"></i><span>失效</span></a>
   			<a class="btn" id="revert"  href="javascript:valid(1);" style="display:none"><i class="icon icon-retweet"></i><span>恢复</span></a>
   			 -->
   			<a class="btn" id="save" href="javascript:save(1);" style="display:none"><i class="icon icon-check"></i><span>保存</span></a>
			<a class="btn" id="audit" href="javascript:save(0);" style="display:none"><i class="icon icon-ok-circle"></i><span>审核发布</span></a>
			<a class="btn" id="cancel" href="javascript:save(1);" style="display:none"><i class="icon icon-ban-circle"></i><span>取消发布</span></a>
			<a class="btn" href="javascript:back();"><i class="icon icon-share-alt"></i><span>返回列表</span></a>
		</div>
	</div>
	<div class="table-wrapper">
		<form action="recruitment/saveOrUpdateCarousel.do" id="myForm"  class="form" method="post">
			<input type="hidden" id="carouselguid" name="carouselguid" value=""/>
			<input type="hidden" id="valid" name="valid" value="1"/>
			<input id="isaudited" name="isaudited" type="hidden" value="1"/>
			<input id="modtime" name="modtime" type="hidden" value=""/>
			<input id="titlepic" name="titlepic" type="hidden" value=""/>
			<input id="pubuser" name="pubuser" type="hidden" value="${userid}"/>
			<input id="audituser" name="audituser" type="hidden"/>
			<fieldset>
				<legend>基本信息</legend>
				<ul>
					<li>
						<span><em class="red">* </em>排序号：</span>
						<input id="dorder" name="dorder" class="{required:true,maxlength:38,number:true} inputstyle"  />
					</li>
				</ul>
				<ul>
					<li>
					    <span><em class="red">* </em>标题：</span>
					    <input id="title" name="title" class="{required:true,maxlength:100} inputstyle" style="width:570px;"/>
					</li>
				</ul>
				<ul>
					<li>
						<span>描述：</span>
						<textarea id="rmk" name="rmk" rows="5" style="width:570px;" class="{maxlength:500} areastyle"></textarea>
					</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>轮播图片</legend>
				<div style="border:1px solid #b5b8c8;">
					<div style="padding-top:15px;">
						<img id="upload_button" alt="照片" src="skins/images/nopic.jpg" style="height:300px;">
					</div>
					<a href="javascript:clearPhoto();" id="clearPhoto" style="display:none;float:right;">X&nbsp;</a>
				</div>
			</fieldset>
			<fieldset>
				<legend><em class="red">* </em>正文</legend>
				<div style="height:650px">
					<textarea id="carouselcontent" name="carouselcontent.carouselcontent" style="display:none;"></textarea>
				</div>
			</fieldset>
		</form>
	</div>
</div>



</body>
</html>
