
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
    //日期
    $(".timepicker").datetimepicker({
		dateFormat:'yy-mm-dd',
		timeFormat: 'hh:mm:ss',
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
function loadTmpWorkExperience(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("person/getTmpWorkExperienceListHtml.do",{mycandidatesguid:mycandidatesguid},function(data){
			//添加表單
			$("#myWorkExperiences").html(data);
			
			//计算高度
			_autoHeight();
			
		},"html");
	}
}


//添加
function addTmpWorkExperience(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("person/getTmpWorkExperienceHtml.do",{mycandidatesguid:mycandidatesguid,ordernum:workexperienceNum},function(data){

			//添加表單
			$("#myWorkExperiences").append(data);
			$("#myWorkExperience_"+workexperienceNum).fadeIn();
			$("#myWorkExperience_"+workexperienceNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#workexperienceid_"+workexperienceForm).val(data.workexperienceid);
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
	var workexperienceid=$("#workexperienceid_"+num).val();
	if(workexperienceid!=null&&workexperienceid!=''&&workexperienceid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(workexperienceid);
	   	$.post("person/delTmpWorkExperienceById.do",{ids:array.toString()}, function() {
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
function loadTmpEduExperience(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("person/getTmpEduExperienceListHtml.do",{mycandidatesguid:mycandidatesguid},function(data){
			//添加表單
			$("#myEducationExperiences").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}



//新增
function addTmpEduExperience(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("person/getTmpEduExperienceHtml.do",{mycandidatesguid:mycandidatesguid,ordernum:educationexperienceNum},function(data){

			//添加表單
			$("#myEducationExperiences").append(data);
			$("#myEducationExperience_"+educationexperienceNum).fadeIn();
			$("#myEducationExperience_"+educationexperienceNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#eduexperienceid_"+educationexperienceForm).val(data.eduexperienceid);
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
	var eduexperienceid=$("#eduexperienceid_"+num).val();
	if(eduexperienceid!=null&&eduexperienceid!=''&&eduexperienceid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(eduexperienceid);
	   	$.post("person/delTmpEduExperienceById.do",{ids:array.toString()}, function() {
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
var trainForm=null;
var trainNum=0;



//加载
function loadTmpTrainExperience(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("person/getTmpTrainListHtml.do",{mycandidatesguid:mycandidatesguid},function(data){
			//添加表單
			$("#myTrains").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}





//新增
function addTrainExperience(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("person/getTmpTrainHtml.do",{mycandidatesguid:mycandidatesguid,ordernum:trainNum},function(data){

			//添加表單
			$("#myTrains").append(data);
			$("#myTmpTrain_"+trainNum).fadeIn();
			$("#myTmpTrain_"+trainNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#trainid_"+trainForm).val(data.trainid);
						//formDisabled(true,"#myTmpTrain_"+trainForm);
					});
				}
			});
			 
			//日期选择
			initDatePicker();
			
			//计算高度
			_autoHeight();
			
			//表单数量
			trainNum++;
			
		},"html");
	}
}



//修改
function editTmpTrain(id){
	formDisabled(false,id);
}



//保存
function saveTmpTrain(id,num){
	trainForm=num;
	$(id).submit();
}


//删除
function delTmpTrain(id,num){
	var trainid=$("#trainid_"+num).val();
	if(trainid!=null&&trainid!=''&&trainid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(trainid);
	   	$.post("person/delTmpTrainByTrainId.do",{ids:array.toString()}, function() {
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
//==============家庭状况======================
//=============================================
var familyForm=null;
var familyNum=0;



//加载
function loadTmpFamily(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("myperson/getTmpFamilyListHtml.do",{mycandidatesguid:mycandidatesguid},function(data){
			//添加表單
			$("#myFamilys").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}





//新增
function addTmpFamily(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("myperson/getTmpFamilyHtml.do",{mycandidatesguid:mycandidatesguid,ordernum:familyNum},function(data){

			//添加表單
			$("#myFamilys").append(data);
			$("#myTmpFamily_"+familyNum).fadeIn();
			$("#myTmpFamily_"+familyNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#familyid_"+familyForm).val(data.familyid);
						//formDisabled(true,"#myTmpFamily_"+familyForm);
					});
				}
			});
			 
			//计算高度
			_autoHeight();
			
			//表单数量
			familyNum++;
			
		},"html");
	}
	
}



//修改
function editTmpFamily(id){
	formDisabled(false,id);
}



//保存
function saveTmpFamily(id,num){
	familyForm=num;
	$(id).submit();
}


//删除
function delTmpFamily(id,num){
	var familyid=$("#familyid_"+num).val();
	if(familyid!=null&&familyid!=''&&familyid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(familyid);
	   	$.post("myperson/delTmpFamilyById.do",{ids:array.toString()}, function() {
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
//==============公司亲属表======================
//=============================================
var relativesForm=null;
var relativesNum=0;



//加载
function loadTmpRelatives(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("myperson/getTmpRelativesListHtml.do",{mycandidatesguid:mycandidatesguid},function(data){
			//添加表單
			$("#myRelatives").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}





//新增
function addRelatives(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("myperson/getTmpRelativesHtml.do",{mycandidatesguid:mycandidatesguid,ordernum:relativesNum},function(data){
			//添加表單
			
			$("#myRelatives").append(data);
			$("#myTmpRelatives_"+relativesNum).fadeIn();
			$("#myTmpRelatives_"+relativesNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#relativesguid_"+relativesForm).val(data.relativesguid);
						//formDisabled(true,"#myTmpRelatives_"+relativesForm);
					});
				}
			});
			 
			//计算高度
			_autoHeight();
			//表单数量
			relativesNum++;
			
		},"html");
	}
	
}



//修改
function editTmpRelatives(id){
	formDisabled(false,id);
}



//保存
function saveTmpRelatives(id,num){
	relativesForm=num;
	$(id).submit();
}


//删除
function delTmpRelatives(id,num){
	var relativesguid=$("#relativesguid_"+num).val();
	if(relativesguid!=null&&relativesguid!=''&&relativesguid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(relativesguid);
	   	$.post("myperson/delTmpRelativesById.do",{ids:array.toString()}, function() {
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
var proexperienceForm=null;
var proexperienceNum=0;


//加载
function loadTmpProExperience(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("myperson/getTmpProjectExperienceListHtml.do",{mycandidatesguid:mycandidatesguid},function(data){
			//添加表單
			$("#myPros").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}





//新增
function addProExperience(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("myperson/getTmpProjectExperienceHtml.do",{mycandidatesguid:mycandidatesguid,ordernum:proexperienceNum},function(data){

			//添加表單
			$("#myPros").append(data);
			$("#myProExperience_"+proexperienceNum).fadeIn();
			$("#myProExperience_"+proexperienceNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#myProExperience_"+proexperienceForm+" input[id=modtime_"+proexperienceForm+"]").val(data.modtime);
						$("#projectexperienceguid_"+proexperienceForm).val(data.projectexperienceguid);
						//formDisabled(true,"#myProExperience_"+proexperienceForm);
					});
				}
			});
			 
			//日期选择
			initDatePicker();
			
			//计算高度
			_autoHeight();
			
			//表单数量
			proexperienceNum++;
			
		},"html");
	}
}



//修改
function editProExperience(id){
	formDisabled(false,id);
}



//保存
function saveProExperience(id,num){
	proexperienceForm=num;
	$(id).submit();
}


//删除
function delProExperience(id,num){
	var projectexperienceguid=$("#projectexperienceguid_"+num).val();
	if(projectexperienceguid!=null&&projectexperienceguid!=''&&projectexperienceguid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(projectexperienceguid);
	   	$.post("myperson/delTmpProjectExperienceById.do",{ids:array.toString()}, function() {
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
//==============推荐信息表======================
//=============================================
var recommendForm=null;
var recommendNum=0;



//加载
function loadTmpRecommend(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("myperson/getTmpRecommendListHtml.do",{mycandidatesguid:mycandidatesguid},function(data){
			//添加表單
			$("#myRecommends").html(data);
			
			//计算高度
			_autoHeight();
		},"html");
	}
}





//新增
function addTmpRecommend(){
	var mycandidatesguid=$("#mycandidatesguid").val();
	if(mycandidatesguid!=null&&mycandidatesguid!=''&&mycandidatesguid!='null'){
		$.post("myperson/getTmpRecommendHtml.do",{mycandidatesguid:mycandidatesguid,ordernum:recommendNum},function(data){
			//添加表單
			
			$("#myRecommends").append(data);
			$("#myTmpRecommend_"+recommendNum).fadeIn();
			$("#myTmpRecommend_"+recommendNum).validate({debug:true,submitHandler: function(form) {
					$(form).ajaxSubmit(function(data) {
						alert("操作成功!");
						$("#recommendguid_"+recommendForm).val(data.recommendguid);
						//formDisabled(true,"#myTmpRecommend_"+recommendForm);
					});
				}
			});
			 
			//计算高度
			_autoHeight();
			
			//表单数量
			recommendNum++;
			
		},"html");
	}
	
}



//修改
function editTmpRecommend(id){
	formDisabled(false,id);
}



//保存
function saveTmpRecommend(id,num){
	recommendForm=num;
	$(id).submit();
}


//删除
function delTmpRecommend(id,num){
	var recommendguid=$("#recommendguid_"+num).val();
	if(recommendguid!=null&&recommendguid!=''&&recommendguid!='null'){
		if(!confirm('确认要删除选中数据吗？')){
			return;
		}
		var array=new Array();
		array.push(recommendguid);
	   	$.post("myperson/delTmpRecommendById.do",{ids:array.toString()}, function() {
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







