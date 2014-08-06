Date.prototype.addMonth=function (num)
 {
 var tempDate=this.getDate();
 this.setMonth(this.getMonth()+num);
 if(tempDate!=this.getDate()) this.setDate(0);
 return this.format("yyyy-MM");
 };


 






//将要过生日的员工
function getEmployeeBirthdayTodo(){
	var pam={parameters:{}};
	$.ajax({  
		url: "todo/getEmployeeBirthdayTodo.do",  
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			var htm='';
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr>';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td>';
					htm+='<a href="">';
					htm+=obj.name;
					htm+='</a>';
					htm+='</td>';
					htm+='<td align="center">';
					htm+=obj.birthday;
					htm+='</td>';							
					htm+='<td align="center">';
					htm+=obj.msg+"天";
					htm+='</td>';							
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#toBirthday").html(htm);
			$("#toBirthdayCount").html(data.length);
			
			
			//计算高度
			_autoHeight();
		}
	});
}








//异动待生效的员工
function getEmployeePosationTodo(){
	var pam={parameters:{}};
	$.ajax({  
      url: "todo/getEmployeePosationTodo.do",  
      contentType: "application/json; charset=utf-8",  
      type: "POST",  
      dataType: "json",  
      data: JSON.stringify(pam),
      success: function(data) { 
    	  var htm='';
    	  htm+='<div class="portlet">';
    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>异动待生效的员工('+data.length+')</div>';
    	  htm+='<div class="portlet-content">';
    	  htm+='<table class="static-table">';
    	  htm+='<thead>';
    	  htm+='<tr>';
    	  htm+='<th width="50px">';
    	  htm+='</th>';
    	  htm+='<th>';
    	  htm+='员工姓名';
    	  htm+='</th>';
    	  htm+='<th width="100px">';
    	  htm+='异动时间';
    	  htm+='</th>';								
    	  htm+='<th width="100px">';
    	  htm+='操作';
    	  htm+='</th>';							
    	  htm+='</tr>';
    	  htm+='</thead>';
    	  htm+='<tbody id="column1">';
						
    	  
    	  if(data!=null&&data.length>0){
    		  for(var i=0;i<data.length;i++){
        		  var obj=data[i];
        		  htm+='<tr title="电话：'+obj.mobile+'" id="'+obj.postionguid+'">';
        		  htm+='<td align="center" class="index">';
				  htm+=(i+1);
				  htm+='</td>';
        		  htm+='<td>';
        		  htm+='<a href="javascript:convertView(\'employee.do?page=tab&id='+obj.employeeid+'&postionguid='+obj.postionguid+'\');">';
				  htm+=obj.name;
				  htm+='</a>';
        		  htm+='</td>';
        		  htm+='<td align="center">';
        		  htm+=obj.startdate;
        		  htm+='</td>';							
        		  htm+='<td align="center">';
        		  htm+='<a href="javascript:convertView(\'employee.do?page=tab&id='+obj.employeeid+'&postionguid='+obj.postionguid+'\');">生效</a>';
        		  htm+='&nbsp';
        		  htm+='&nbsp';
        		  htm+='&nbsp';
        		  htm+='<a href="javascript:cancel(\''+obj.postionguid+'\');">撤销</a>';
        		  htm+='</td>';							
        		  htm+='</tr>';
        	  }
    	  }else{
    		  htm+='<tr>';
    		  htm+='<td colspan="4" align="center">';
    		  htm+='无';
    		  htm+='</td>';
    		  htm+='</tr>';
    	  }
    	  htm+='</tbody>';
    	  htm+='</table>';
    	  htm+='</div>';
    	  htm+='</div>';
    	  $("#employeePosation").html(htm);
    	  
    	  
    	//计算高度
  		_autoHeight();
      }
  });
}




//招聘计划审批
function getRecruitprogramTodo(){
	var pam={parameters:{}};
	$.ajax({  
		url: "todo/getRecruitprogramTodo.do",  
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			  var htm='';
	    	  htm+='<div class="portlet">';
	    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>招聘计划审批('+data.length+')</div>';
	    	  htm+='<div class="portlet-content">';
	    	  htm+='<table class="static-table">';
	    	  htm+='<thead>';
	    	  htm+='<tr>';
	    	  htm+='<th width="50px">';
	    	  htm+='</th>';
	    	  htm+='<th>';
	    	  htm+='计划编号';
	    	  htm+='</th>';
	    	  htm+='<th width="100px">';
	    	  htm+='计划招聘人数';
	    	  htm+='</th>';								
	    	  htm+='<th width="100px">';
	    	  htm+='提交时间';
	    	  htm+='</th>';							
	    	  htm+='</tr>';
	    	  htm+='</thead>';
	    	  htm+='<tbody id="column1">';
			

			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr>';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td>';
					htm+='<a href="javascript:convertView(\'recruitprogram.do?page=tab&id='+obj.recruitprogramguid+'&taskid='+obj.taskid+'\');">';
					htm+=obj.recruitprogramcode;
					htm+='</a>';
					htm+='</td>';
					htm+='<td align="center">';
					htm+=obj.postnum;
					htm+='</td>';							
					htm+='<td align="center">';
					htm+=obj.taskcreatetime;
					htm+='</td>';							
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#recruitprogram").html(htm);
			
			
			//计算高度
			_autoHeight();
		}
	});
}



//待确认面试的简历
function getAffirmMyCandidatesTodo(){
	var pam={parameters:{userid:userid}};
	$.ajax({  
		url: "todo/getAffirmMyCandidatesTodo.do",  
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			var htm='';
	    	  htm+='<div class="portlet">';
	    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>待确认面试的简历('+data.length+')</div>';
	    	  htm+='<div class="portlet-content">';
	    	  htm+='<table class="static-table">';
	    	  htm+='<thead>';
	    	  htm+='<tr>';
	    	  htm+='<th width="50px">';
	    	  htm+='</th>';
	    	  htm+='<th>';
	    	  htm+='应聘者';
	    	  htm+='</th>';
	    	  htm+='<th width="100px">';
	    	  htm+='推荐时间';
	    	  htm+='</th>';								
	    	  htm+='<th width="100px">';
	    	  htm+='失效时间';
	    	  htm+='</th>';							
	    	  htm+='</tr>';
	    	  htm+='</thead>';
	    	  htm+='<tbody id="column1">';
	
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr>';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td>';
					htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&candidatesstate='+obj.candidatesstate+'&resumeeamilguid='+obj.resumeeamilguid+'&recommendguid='+obj.recommendguid+'\');">';
				    htm+=obj.name;
					htm+='</a>';
					htm+='</td>';
					htm+='<td align="center">';
					htm+=obj.timeformat;
					htm+='</td>';							
					htm+='<td align="center">';
					htm+=obj.msg;
					htm+='</td>';							
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#affirm").html(htm);
			
			
			//计算高度
			_autoHeight();
		}
	});
}




//待安排的面试
function getInterviewTodo(){
	var pam={parameters:{userid:userid}};
	$.ajax({  
		url: "todo/getInterviewTodo.do",  
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			  var htm='';
			  htm+='<div class="portlet">';
	    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>待安排的面试('+data.length+')</div>';
	    	  htm+='<div class="portlet-content">';
	    	  htm+='<table class="static-table">';
	    	  htm+='<thead>';
	    	  htm+='<tr>';
	    	  htm+='<th width="50px">';
	    	  htm+='</th>';
	    	  htm+='<th>';
	    	  htm+='应聘者';
	    	  htm+='</th>';
	    	  htm+='<th width="100px" align="center">';
	    	  htm+='认定时间';
	    	  htm+='</th>';								
	    	  htm+='<th width="100px">';
	    	  htm+='操作';
	    	  htm+='</th>';							
	    	  htm+='</tr>';
	    	  htm+='</thead>';
	    	  htm+='<tbody id="column1">';
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr>';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td align="left">';				 
					if(obj.candidatesstate==6){
						htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&candidatesstate='+obj.candidatesstate+'&resumeeamilguid='+obj.resumeeamilguid+'&auditiontime='+obj.auditiontime+'&arrangement=true\');">';
						htm+=obj.name+"(复试)";
						htm+='</a>';
					}else{
						htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&candidatesstate='+obj.candidatesstate+'&resumeeamilguid='+obj.resumeeamilguid+'&auditiontime='+obj.auditiontime+'&arrangement=true\');">';
						htm+=obj.name+"(初试)";
						htm+='</a>';
					}
					htm+='</td>';	
					htm+='<td align="center">';
					htm+=obj.timeformat;
					htm+='</td>';
					htm+='<td align="center">';
					htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&candidatesstate='+obj.candidatesstate+'&resumeeamilguid='+obj.resumeeamilguid+'&auditiontime='+obj.auditiontime+'&arrangement=true\');">安排面试</a>';
					htm+='</td>';							
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#interview").html(htm);
			
			
			//计算高度
			_autoHeight();
		}
	});
}







//面试人员列表
function getAuditionTodo(){
	var pam={parameters:{}};
	$.ajax({  
		url: "todo/getAuditionTodo.do",  
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			var htm='';
			  htm+='<div class="portlet">';
	    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>面试人员列表('+data.length+')</div>';
	    	  htm+='<div class="portlet-content">';
	    	  htm+='<table class="static-table">';
	    	  htm+='<thead>';
	    	  htm+='<tr>';
	    	  htm+='<th width="50px">';
	    	  htm+='</th>';  
	    	  htm+='<th>';
	    	  htm+='应聘者';
	    	  htm+='</th>';
	    	  htm+='<th width="100px">';
	    	  htm+='面试时间';
	    	  htm+='</th>';								
	    	  htm+='<th width="100px">';
	    	  htm+='操作';
	    	  htm+='</th>';
	    	  htm+='</tr>';
	    	  htm+='</thead>';
	    	  htm+='<tbody id="column2">';
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr>';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td align="left">';
					htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&resumeeamilguid='+obj.resumeeamilguid+'\');">';
					htm+=obj.name;
					htm+='</a>';
					htm+='</td>';
					htm+='<td align="center" title="'+('【面试官：'+obj.maininterviewerguidname+'】'+obj.auditionaddress)+'">';
					htm+=obj.timeformat;
					htm+='</td>';
					htm+='<td  align="center">';
					htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&resumeeamilguid='+obj.resumeeamilguid+'\');">';
					htm+='查看';
					htm+='</a>';
					htm+='</td>';
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#audition").html(htm);
			
			
			//计算高度
			_autoHeight();
		}
	});
}





//面試結果
function getAffirmAuditionResultsTodo(){
	var pam={parameters:{userid:userid}};
	$.ajax({  
		url: "todo/getAffirmAuditionResultsTodo.do",  
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			var htm='';
			  htm+='<div class="portlet">';
	    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>面试结果待反馈('+data.length+')</div>';
	    	  htm+='<div class="portlet-content">';
	    	  htm+='<table class="static-table">';
	    	  htm+='<thead>';
	    	  htm+='<tr>';
	    	  htm+='<th width="50px">';
	    	  htm+='</th>';
	    	  htm+='<th>';
	    	  htm+='应聘者';
	    	  htm+='</th>';
	    	  htm+='<th width="100px">';
	    	  htm+='面试时间';
	    	  htm+='</th>';								
	    	  htm+='<th width="100px">';
	    	  htm+='操作';
	    	  htm+='</th>';							
	    	  htm+='</tr>';
	    	  htm+='</thead>';
	    	  htm+='<tbody id="column1">';
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr>';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td>';
					htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&candidatesstate='+obj.candidatesstate+'&resumeeamilguid='+obj.resumeeamilguid+'\');">';
				    htm+=obj.webusername;
				    htm+='</a>';
					htm+='</td>';
					htm+='<td align="center">';
					htm+=obj.timeformat;
					htm+='</td>';							
					htm+='<td align="center">';
					htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&candidatesstate='+obj.candidatesstate+'&resumeeamilguid='+obj.resumeeamilguid+'\');">';
					htm+='结果反馈';
					htm+='</a>';
					htm+='</td>';							
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#result").html(htm);
			
			
			//计算高度
			_autoHeight();
		}
	});
}



//待安排的体检
function getExaminationTodo(){
	var pam={parameters:{userid:userid}};
	$.ajax({  
		url: "todo/getExaminationTodo.do",  
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			var htm='';
			  htm+='<div class="portlet">';
	    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>待安排的体检('+data.length+')</div>';
	    	  htm+='<div class="portlet-content">';
	    	  htm+='<table class="static-table">';
	    	  htm+='<thead>';
	    	  htm+='<tr>';
	    	  htm+='<th width="50px">';
	    	  htm+='</th>';
	    	  htm+='<th>';
	    	  htm+='应聘者';
	    	  htm+='</th>';
	    	  htm+='<th width="100px">';
	    	  htm+='面试通过';
	    	  htm+='</th>';								
	    	  htm+='<th width="100px">';
	    	  htm+='操作';
	    	  htm+='</th>';							
	    	  htm+='</tr>';
	    	  htm+='</thead>';
	    	  htm+='<tbody id="column2">';
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr>';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td align="left">';
					htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&candidatesstate='+obj.candidatesstate+'&resumeeamilguid='+obj.resumeeamilguid+'&advise=true\');">';
					htm+=obj.name;
					htm+='</a>';
					htm+='</td>';
					htm+='<td align="center">';
					htm+=obj.timeformat;
					//htm+='&nbsp;';
					htm+='</td>';
					htm+='<td align="center">';
					htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&candidatesstate='+obj.candidatesstate+'&resumeeamilguid='+obj.resumeeamilguid+'&advise=true\');">安排体检</a>';
					htm+='</td>';							
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#examination").html(htm);
			
			
			//计算高度
			_autoHeight();
		}
	});
}


//待入职的应聘者
function getEntryOkTodo(){
	var pam={parameters:{userid:userid}};
	$.ajax({  
		url: "todo/getEntryOkTodo.do",  
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			  var htm='';
			  htm+='<div class="portlet">';
	    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>待入职的应聘者('+data.length+')</div>';
	    	  htm+='<div class="portlet-content">';
	    	  htm+='<table class="static-table">';
	    	  htm+='<thead>';
	    	  htm+='<tr>';
	    	  htm+='<th width="50px">';
	    	  htm+='</th>';
	    	  htm+='<th>';
	    	  htm+='应聘者';
	    	  htm+='</th>';
	    	  htm+='<th width="100px" align="center">';
	    	  htm+='体检通过';
	    	  htm+='</th>';								
	    	  htm+='<th width="100px">';
	    	  htm+='操作';
	    	  htm+='</th>';							
	    	  htm+='</tr>';
	    	  htm+='</thead>';
	    	  htm+='<tbody id="column2">';
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr>';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td align="left">';	
					htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&candidatesstate='+obj.candidatesstate+'&resumeeamilguid='+obj.resumeeamilguid+'&entry=true\');">';
					htm+=obj.name;
					htm+='</a>';
					htm+='</td>';	
					htm+='<td align="center">';
					htm+=obj.timeformat;
					htm+='</td>';
					htm+='<td align="center">';
					htm+='<a href="javascript:convertView(\'mycandidates.do?page=form&id='+obj.webuserguid+'&recruitpostguid='+obj.recruitpostguid+'&mycandidatesguid='+obj.mycandidatesguid+'&candidatesstate='+obj.candidatesstate+'&resumeeamilguid='+obj.resumeeamilguid+'&entry=true\');">入职信息</a>';
					htm+='</td>';							
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#entryOk").html(htm);
			
			
			//计算高度
			_autoHeight();
		}
	});
}

//待确定入职的员工
function getEntryTodo(){
	var pam={parameters:{userid:userid}};
	$.ajax({  
		url: "todo/getEntryTodo.do",
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			var htm='';
			  htm+='<div class="portlet">';
	    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>待确定入职的员工('+data.length+')</div>';
	    	  htm+='<div class="portlet-content">';
	    	  htm+='<table class="static-table">';
	    	  htm+='<thead>';
	    	  htm+='<tr>';
	    	  htm+='<th width="50px">';
	    	  htm+='</th>';
	    	  htm+='<th>';
	    	  htm+='应聘者';
	    	  htm+='</th>';
	    	  htm+='<th width="100px">';
	    	  htm+='报到时间';
	    	  htm+='</th>';								
	    	  htm+='<th width="100px">';
	    	  htm+='操作';
	    	  htm+='</th>';							
	    	  htm+='</tr>';
	    	  htm+='</thead>';
	    	  htm+='<tbody id="column2">';
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr>';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td align="left">';
					htm+='<a href="javascript:convertView(\'person.do?page=tab&id='+obj.mycandidatesguid+'\');">';
					htm+=obj.name;
					htm+='</a>';
					htm+='</td>';
					htm+='<td align="center">';
					htm+=obj.timeformat;
					htm+='</td>';
					htm+='<td align="center">';
					htm+='<a href="javascript:convertView(\'person.do?page=tab&id='+obj.mycandidatesguid+'\');">安排入职</a>';
					htm+='</td>';							
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#entry").html(htm);
			
			
			//计算高度
			_autoHeight();
		}
	});
}











//合同将要到期的员工
function getEmployeeContractTodo(){
	var pam={parameters:{}};
	$.ajax({  
		url: "todo/getEmployeeContractTodo.do",  
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			var htm='';
			  htm+='<div class="portlet">';
	    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>合同即将到期的员工('+data.length+')</div>';
	    	  htm+='<div class="portlet-content">';
	    	  htm+='<table class="static-table">';
	    	  htm+='<thead>';
	    	  htm+='<tr>';
	    	  htm+='<th width="50px">';
	    	  htm+='</th>';
	    	  htm+='<th>';
	    	  htm+='员工姓名';
	    	  htm+='</th>';
	    	  htm+='<th width="100px">';
	    	  htm+='合同终止日期';
	    	  htm+='</th>';								
	    	  htm+='<th width="100px">';
	    	  htm+='到期天数';
	    	  htm+='</th>';							
	    	  htm+='</tr>';
	    	  htm+='</thead>';
	    	  htm+='<tbody id="column1">';
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr title="电话：'+obj.mobile+'">';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td>';
					htm+='<a href="javascript:convertView(\'employee.do?page=tab&id='+obj.employeeid+'&postionguid='+obj.postionguid+'\');">';
					htm+=obj.name;
					htm+='</a>';
					htm+='</td>';
					htm+='<td align="center">';
					htm+=obj.enddate;
					htm+='</td>';							
					htm+='<td align="center">';
					if(obj.msg<=0){
						htm+='<font color="#ff0000">'+obj.msg+'天</font>';
					}else
						htm+=obj.msg+"天";
					htm+='</td>';							
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#employeeContract").html(htm);
			
			
			//计算高度
			_autoHeight();
		}
	});
}

//异动撤销
function cancel(postionguid){
	if(!confirm('确认要撤销吗？')){
		return;
	}
 	$.post("employee/delPositionById.do",{ids:postionguid}, function() {
 		pageReload();
  	});
}







































//待转正的员工
function getEmployeeZhuZhengTodo(){
	var pam={parameters:{}};
	$.ajax({  
		url: "todo/getEmployeeZhuZhengTodo.do",  
		contentType: "application/json; charset=utf-8",  
		type: "POST",  
		dataType: "json",  
		data: JSON.stringify(pam),
		success: function(data) { 
			 var htm='';
			  htm+='<div class="portlet">';
	    	  htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>试用期将要到期的员工('+data.length+')</div>';
	    	  htm+='<div class="portlet-content">';
	    	  htm+='<table class="static-table">';
	    	  htm+='<thead>';
	    	  htm+='<tr>';
	    	  htm+='<th width="50px">';
	    	  htm+='</th>';
	    	  htm+='<th>';
	    	  htm+='员工姓名';
	    	  htm+='</th>';
	    	  htm+='<th width="100px">';
	    	  htm+='计划转正时间';
	    	  htm+='</th>';								
	    	  htm+='<th width="100px">';
	    	  htm+='到期天数';
	    	  htm+='</th>';							
	    	  htm+='</tr>';
	    	  htm+='</thead>';
	    	  htm+='<tbody id="column2">';
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					var obj=data[i];
					htm+='<tr title="电话：'+obj.mobile+'">';
					htm+='<td align="center" class="index">';
					htm+=(i+1);
					htm+='</td>';
					htm+='<td>';
					htm+='<a href="javascript:convertView(\'employee.do?page=tab&id='+obj.employeeid+'&postionguid='+obj.postionguid+'\');">';
					htm+=obj.name;
					htm+='</a>';
					htm+='</td>';
					htm+='<td align="center">';
					htm+=obj.officialdateplan;
					htm+='</td>';							
					htm+='<td align="center">';
					if(obj.msg<=0){
						htm+='<font color="#ff0000">'+obj.msg+'天</font>';
					}else
					htm+=obj.msg+"天";
					htm+='</td>';							
					htm+='</tr>';
				}
			}else{
				htm+='<tr>';
				htm+='<td colspan="4" align="center">';
				htm+='无';
				htm+='</td>';
				htm+='</tr>';
			}
			$("#employeezhuzheng").html(htm);
			
			
			//计算高度
			_autoHeight();
		}
	});
}



//我的应聘情况统计图表
var chart=null;
function loadMyCandidatesChart(){
	if(chart==null){
		var htm='';
		htm+='<div class="portlet">';
		htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>我的应聘情况统计图</div>';
		htm+='<div class="portlet-content">';
		htm+='<table class="static-table" cellpadding="0" cellspacing="0" border="0">';
		htm+='<tr>';
		htm+='<td id="myChart" style="padding:0px;">';
		
		htm+='</td>';
		htm+='</tr>';
		htm+='</table>';
		htm+='</div>';
		htm+='</div>';
		$("#mycandidatesstate").html(htm);
	}
	
	var url='todo/loadCountMyCandidatesChart.do?s3d=false&type=Bar&cylinder=Cylinder&legendEnabled=false';
	if(chart==null){
		chart = new AnyChart('swf/AnyChart.swf', 'swf/Preloader.swf');
		chart.width = '100%';
		chart.height = 300;
		chart.setXMLFile(url);
		chart.write('myChart');
	}else{
		chart.setXMLFile(url);
		chart.refresh();
	}
}





//来源渠道统计图表
var typeChart=null;
function loadMyCandidatesTypeChart(){
	if(typeChart==null){
		var htm='';
		htm+='<div class="portlet">';
		htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>来源渠道统计图</div>';
		htm+='<div class="portlet-content">';
		htm+='<table class="static-table">';
		htm+='<tr>';
		htm+='<td id="myTypeChart" style="padding:0px;">';
		
		htm+='</td>';
		htm+='</tr>';
		htm+='</table>';
		htm+='</div>';
		htm+='</div>';
		$("#mycandidatestype").html(htm);
	}
	var url='todo/loadCountMyCandidatesTypeChart.do?s3d=false&type=Pie&cylinder=Cylinder&legendEnabled=false';
	if(typeChart==null){
		typeChart = new AnyChart('swf/AnyChart.swf', 'swf/Preloader.swf');
		typeChart.width = '100%';
		typeChart.height = 300;
		typeChart.setXMLFile(url);
		typeChart.write('myTypeChart');
	}else{
		typeChart.setXMLFile(url);
		typeChart.refresh();
	}
}




var chartNum = 1;
function changeChart(obj){
	chartNum = obj.value;
	 loadBZQKChart(chartNum);
}



//编制情况统计图表
var myBZQKChartBody=null;
function loadBZQKChart(chartNum){
	var htm='';
	if(chartNum==1 || chartNum==null){
		myBZQKChartBody=null;
		if(myBZQKChartBody==null){
			htm+='<div class="portlet-content">';
			htm+='<table class="static-table">';
			htm+='<tr>';
			htm+='<td id="myBZQKChartBody" style="padding:0px;">';
			
			htm+='</td>';
			htm+='</tr>';
			htm+='</table>';
			htm+='</div>';
			$("#BZQKContent").html(htm);
		}
		var url = 'todo/myBZQKChartBody.do?s3d=false&type=line&cylinder=Cylinder&legendEnabled=false';
		if(myBZQKChartBody==null){
			myBZQKChartBody = new AnyChart('swf/AnyChart.swf', 'swf/Preloader.swf');
			myBZQKChartBody.width = '100%';
			myBZQKChartBody.height = 300;
			myBZQKChartBody.setXMLFile(url);
			myBZQKChartBody.write('myBZQKChartBody');
		}else{
			myBZQKChartBody.setXMLFile(url);
			myBZQKChartBody.refresh();
		}
	}else if(chartNum==2){
		var pam={parameters:{companyid:companyid}};
		$.ajax({
			url: "todo/myRecuritprogramChartBody.do",
			contentType: "application/json; charset=utf-8",  
			type: "POST",  
			dataType: "json",  
			data: JSON.stringify(pam),
			success: function(data) {
				var date=new Date();
				htm+='<div class="portlet-content">';
				htm+='<table class="static-table">';
				htm+='<thead><tr style="text-align:center;">';
				htm+='<th style="width:120px;">体系</th>';
				htm+='<th>';
				htm+=date.addMonth(-11);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(-10);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(-9);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(-8);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(-7);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(-6);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(-5);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(-4);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(-3);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(-2);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(-1);
				htm+='</th>';
				htm+='<th>';
				 date=new Date();
				htm+=date.addMonth(0);
				htm+='</th>';
				htm+='<th>';
				htm+='当月完成率';
				htm+='</th>';
				htm+='<th>';
				htm+='累计完成率';
				htm+='</th>';
				htm+='</tr></thead>';
				if(data!=null&&data.length>0){
					for(var i=0;i<data.length;i++){
						var obj=data[i];
						htm+='<tr>';
						htm+='<td>';
						htm+=obj.name;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_11;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_10;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_9;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_8;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_7;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_6;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_5;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_4;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_3;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_2;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_1;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.sumnumber_0;
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.monthrate;
						htm+='%';
						htm+='</td>';
						htm+='<td align="center">';
						htm+=obj.yearrate;
						htm+='%';
						htm+='</td>';
						htm+='</tr>';
					}
				}
				htm+='</table>';
				htm+='</div>';
				$("#BZQKContent").html(htm);
			}
		});
	}
}









//当日投递情况
var deliverChart=null;
function getDeliverTodo(){
	if(deliverChart==null){
		var htm='';
		htm+='<div class="portlet">';
		htm+='<div class="portlet-header ui-widget-header"><span class="icon icon-bookmark"></span>投递情况统计图</div>';
		htm+='<div class="portlet-content">';
		htm+='<table class="static-table">';
		htm+='<tr>';
		htm+='<td id="myDeliverChartBody" style="padding:0px;">';
		
		htm+='</td>';
		htm+='</tr>';
		htm+='</table>';
		htm+='</div>';
		htm+='</div>';
		$("#deliver").html(htm);
	}
	var url='todo/myDeliverChartBody.do?s3d=false&type=Pie&cylinder=Cylinder&legendEnabled=false';
	if(deliverChart==null){
		deliverChart = new AnyChart('swf/AnyChart.swf', 'swf/Preloader.swf');
		deliverChart.width = '100%';
		deliverChart.height = 300;
		deliverChart.setXMLFile(url);
		deliverChart.write('myDeliverChartBody');
	}else{
		deliverChart.setXMLFile(url);
		deliverChart.refresh();
	}
}

