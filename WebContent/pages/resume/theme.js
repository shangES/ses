
//=============================================
//==============日期选择=======================
//=============================================
function initDatePicker(){
    $(".datepicker").datepicker({
        dateFormat: 'yy-mm-dd',
       	beforeShow: function(input, inst) {
   			if($(input).attr("readonly"))
   				inst.inline=true;
   		}
    });
}






//=============================================
//==============工作经历=======================
//=============================================
var workexperienceForm=null;
var workexperienceNum=0;

//加载
function loadWorkExperience(){
	var webuserguid=$("#webuserguid").val();
	if(webuserguid!=null&&webuserguid!=''&&webuserguid!='null'){
		$.post("resume/getWorkExperienceListHtml.do",{webuserguid:webuserguid},function(data){
			//添加表單
			$("#myWorkExperiences").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}


//添加
function addWorkExperience(){
	var webuserguid=$("#webuserguid").val();
	if(webuserguid!=null&&webuserguid!=''&&webuserguid!='null'){
		$.post("resume/getWorkExperienceHtml.do",{webuserguid:webuserguid,ordernum:workexperienceNum},function(data){

			//添加表單
			$("#myWorkExperiences").append(data);
			$("#myWorkExperience_"+workexperienceNum).fadeIn();
			$("#myWorkExperience_"+workexperienceNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#myWorkExperience_"+workexperienceForm+" input[id=modtime_"+workexperienceForm+"]").val(data.modtime);
						$("#workexperienceguid_"+workexperienceForm).val(data.workexperienceguid);
						//formDisabled(true,"#myWorkExperience_"+workexperienceForm);
					});
				}
			});
			 
			//日期选择
			initDatePicker();
			
			//计算高度
			_autoHeight();
			
			//表单数量
			workexperienceNum++;
			
		},"html");
	}
}



//修改
function editWorkExperience(id){
	formDisabled(false,id);
}



//保存
function saveWorkExperience(id,num){
	workexperienceForm=num;
	$(id).submit();
}


//删除
function delWorkExperience(id,num){
	var workexperienceguid=$("#workexperienceguid_"+num).val();
	if(workexperienceguid!=null&&workexperienceguid!=''&&workexperienceguid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(workexperienceguid);
	   	$.post("resume/delWorkExperienceById.do",{ids:array.toString()}, function() {
	   		$(id).fadeOut(function(){
	   			$(id).remove();
	   	 	});
	    });
	}else{
		$(id).fadeOut(function(){
   			$(id).remove();
   	 	});
	}
}








//=============================================
//==============项目经历=======================
//=============================================
var projectexperienceForm=null;
var projectexperienceNum=0;


//加载
function loadProjectExperience(){
	var webuserguid=$("#webuserguid").val();
	if(webuserguid!=null&&webuserguid!=''&&webuserguid!='null'){
		$.post("resume/getProjectExperienceListHtml.do",{webuserguid:webuserguid},function(data){
			//添加表單
			$("#myProjectExperiences").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}


//新增
function addProjectExperience(){
	var webuserguid=$("#webuserguid").val();
	if(webuserguid!=null&&webuserguid!=''&&webuserguid!='null'){
		$.post("resume/getProjectExperienceHtml.do",{webuserguid:webuserguid,ordernum:projectexperienceNum},function(data){

			//添加表單
			$("#myProjectExperiences").append(data);
			$("#myProjectExperience_"+projectexperienceNum).fadeIn();
			
			
			$("#myProjectExperience_"+projectexperienceNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#myProjectExperience_"+projectexperienceForm+" input[id=modtime_"+projectexperienceForm+"]").val(data.modtime);
						$("#projectexperienceguid_"+projectexperienceForm).val(data.projectexperienceguid);
						//formDisabled(true,"#myProjectExperience_"+projectexperienceForm);
					});
				}
			});
			 
			//日期选择
			initDatePicker();
			
			//计算高度
			_autoHeight();
			
			//表单数量
			projectexperienceNum++;
			
		},"html");
	}
}



//修改
function editProjectExperience(id){
	formDisabled(false,id);
}



//保存
function saveProjectExperience(id,num){
	projectexperienceForm=num;
	$(id).submit();
}


//删除
function delProjectExperience(id,num){
	var projectexperienceguid=$("#projectexperienceguid_"+num).val();
	if(projectexperienceguid!=null&&projectexperienceguid!=''&&projectexperienceguid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(projectexperienceguid);
	   	$.post("resume/delProjectExperienceById.do",{ids:array.toString()}, function() {
	   		$(id).fadeOut(function(){
	   			$(id).remove();
	   	 	});
	    });
	}else{
		$(id).fadeOut(function(){
   			$(id).remove();
   	 	});
	}
}






//=============================================
//==============教育经历=======================
//=============================================
var educationexperienceForm=null;
var educationexperienceNum=0;



//加载
function loadEducationExperience(){
	var webuserguid=$("#webuserguid").val();
	if(webuserguid!=null&&webuserguid!=''&&webuserguid!='null'){
		$.post("resume/getEducationExperienceListHtml.do",{webuserguid:webuserguid},function(data){
			//添加表單
			$("#myEducationExperiences").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}



//新增
function addEducationExperience(){
	var webuserguid=$("#webuserguid").val();
	if(webuserguid!=null&&webuserguid!=''&&webuserguid!='null'){
		$.post("resume/getEducationExperienceHtml.do",{webuserguid:webuserguid,ordernum:educationexperienceNum},function(data){

			//添加表單
			$("#myEducationExperiences").append(data);
			$("#myEducationExperience_"+educationexperienceNum).fadeIn();
			$("#myEducationExperience_"+educationexperienceNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#educationexperienceguid_"+educationexperienceForm).val(data.educationexperienceguid);
						$("#myEducationExperience_"+educationexperienceForm+" input[id=modtime_"+educationexperienceForm+"]").val(data.modtime);
						//formDisabled(true,"#myEducationExperience_"+educationexperienceForm);
					});
				}
			});
			 
			//日期选择
			initDatePicker();
			
			//计算高度
			_autoHeight();
			
			//表单数量
			educationexperienceNum++;
			
		},"html");
	}
}



//修改
function editEducationExperience(id){
	formDisabled(false,id);
}



//保存
function saveEducationExperience(id,num){
	educationexperienceForm=num;
	$(id).submit();
}


//删除
function delEducationExperience(id,num){
	var educationexperienceguid=$("#educationexperienceguid_"+num).val();
	if(educationexperienceguid!=null&&educationexperienceguid!=''&&educationexperienceguid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(educationexperienceguid);
	   	$.post("resume/delEducationExperienceById.do",{ids:array.toString()}, function() {
	   		$(id).fadeOut(function(){
	   			$(id).remove();
	   	 	});
	    });
	}else{
		$(id).fadeOut(function(){
 			$(id).remove();
 	 	});
	}
}








//=============================================
//==============培训经历=======================
//=============================================
var trainingexperienceForm=null;
var trainingexperienceNum=0;



//加载
function loadTrainingExperience(){
	var webuserguid=$("#webuserguid").val();
	if(webuserguid!=null&&webuserguid!=''&&webuserguid!='null'){
		$.post("resume/getTrainingExperienceListHtml.do",{webuserguid:webuserguid},function(data){
			//添加表單
			$("#myTrainingExperiences").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}





//新增
function addTrainingExperience(){
	var webuserguid=$("#webuserguid").val();
	if(webuserguid!=null&&webuserguid!=''&&webuserguid!='null'){
		$.post("resume/getTrainingExperienceHtml.do",{webuserguid:webuserguid,ordernum:trainingexperienceNum},function(data){

			//添加表單
			$("#myTrainingExperiences").append(data);
			$("#myTrainingExperience_"+trainingexperienceNum).fadeIn();
			$("#myTrainingExperience_"+trainingexperienceNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#trainingexperienceguid_"+trainingexperienceForm).val(data.trainingexperienceguid);
						$("#myTrainingExperience_"+trainingexperienceForm+" input[id=modtime_"+trainingexperienceForm+"]").val(data.modtime);
						//formDisabled(true,"#myTrainingExperience_"+trainingexperienceForm);
					});
				}
			});
			 
			//日期选择
			initDatePicker();
			
			//计算高度
			_autoHeight();
			
			//表单数量
			trainingexperienceNum++;
			
		},"html");
	}
}



//修改
function editTrainingExperience(id){
	formDisabled(false,id);
}



//保存
function saveTrainingExperience(id,num){
	trainingexperienceForm=num;
	$(id).submit();
}


//删除
function delTrainingExperience(id,num){
	var trainingexperienceguid=$("#trainingexperienceguid_"+num).val();
	if(trainingexperienceguid!=null&&trainingexperienceguid!=''&&trainingexperienceguid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(trainingexperienceguid);
	   	$.post("resume/delTrainingExperienceById.do",{ids:array.toString()}, function() {
	   		$(id).fadeOut(function(){
	   			$(id).remove();
	   	 	});
	    });
	}else{
		$(id).fadeOut(function(){
			$(id).remove();
	 	});
	}
}














//=============================================
//==============附件上传=======================
//=============================================
var resumefileForm=null;
var resumefileFormNum=0;




//加载
function loadResumeFile(){
	var webuserguid=$("#webuserguid").val();
	if(webuserguid!=null&&webuserguid!=''&&webuserguid!='null'){
		$.post("resume/getResumeFileListHtml.do",{webuserguid:webuserguid},function(data){
			//添加表單
			$("#myResumeFiles").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}



//新增
function addResumeFile(){
	var webuserguid=$("#webuserguid").val();
	if(webuserguid!=null&&webuserguid!=''&&webuserguid!='null'){
		$.post("resume/getResumeFileHtml.do",{webuserguid:webuserguid,ordernum:resumefileFormNum},function(data){

			//添加表單
			$("#myResumeFiles").append(data);
			$("#myResumeFile_"+resumefileFormNum).fadeIn();
			$("#myResumeFile_"+resumefileFormNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#download_"+resumefileForm).show();
						$("#resumefileguid_"+resumefileForm).val(data.resumefileguid);
						$("#myResumeFile_"+resumefileForm+" input[id=modtime_"+resumefileForm+"]").val(data.modtime);
						//formDisabled(true,"#myResumeFile_"+resumefileForm);
					});
				}
			});
			 
			//上传控件
			initAjaxFileUpload(resumefileFormNum);
			
			//计算高度
			_autoHeight();
			
			//表单数量
			resumefileFormNum++;
			
		},"html");
	}
}


//下载
function downLoadFile(num,baseurl){
	var resumefileguid=$("#resumefileguid_"+num).val();
	window.open(baseurl+"downloadDocument.do?id="+resumefileguid);
}



//修改
function editResumeFile(id){
	formDisabled(false,id);
}



//保存
function saveResumeFile(id,num){
	resumefileForm=num;
	$(id).submit();
}


//删除
function delResumeFile(id,num){
	var resumefileguid=$("#resumefileguid_"+num).val();
	if(resumefileguid!=null&&resumefileguid!=''&&resumefileguid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		
		var array=new Array();
		array.push(resumefileguid);
	   	$.post("resume/delResumeFileById.do",{ids:array.toString()}, function() {
	   		$(id).fadeOut(function(){
	   			$(id).remove();
	   	 	});
	    });
	}else{
		$(id).fadeOut(function(){
			$(id).remove();
	 	});
	}
}

//上传
function initAjaxFileUpload(num){
	 new AjaxUpload('#resumefilepath_'+num,{
	  	id:'upFile',
	  	action: 'uploadFile.do',
	  	name:'file1',
	  	autoSubmit:true,
	  	responseType: 'json',
	  	data: {table:'j_resume_file'},
	  	onSubmit: function(file, ext){
	  		$("#resumefilepath_"+num+"_statu").html('<img src="skins/images/ajax-loader-small.gif"/>');
	  	},  
	  	onComplete: function(filename,obj) {
	  		$('#resumefilepath_'+num+'_statu').html(null);
	  		$('#resumefilepath_'+num).val(obj.filepath);
	  		$('#resumefilename_'+num).val(obj.name);
	  	}
	});
}
