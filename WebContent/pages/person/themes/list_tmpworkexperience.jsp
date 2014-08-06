<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${tmpworkexperiencelist }" var="item" varStatus="idx">
<form action="person/saveOrUpdateTmpWorkExperience.do" method="post" id="myWorkExperience_${idx.index}" class="myWorkExperience">
	<input id="workexperienceid_${idx.index}" name="workexperienceid" type="hidden" value="${item.workexperienceid }"/>
	<input id="mycandidatesguid_${idx.index}" name="mycandidatesguid" type="hidden" value="${item.mycandidatesguid }"/>		
	<div id="" class="formDiv">
			<ul>
			<li>
	             <span><em class="red">* </em>单位：</span>
	             <input id="workunit_${idx.index}" name="workunit" class="{required:true,maxlength:50} inputstyle" value="${item.workunit }"/>
	         </li>
	         <li>
			    <span><em class="red">* </em>担任职务：</span>
			    <input id="job_${idx.index}" name="job" class="{required:true,maxlength:12} inputstyle" value="${item.job }"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>起始日期：</span>
			    <input id="Wstartdate_${idx.index}" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker" value="${item.startdate }"/>
			    <div class="date-trigger" onclick="$('#Wstartdate_${idx.index}').focus();"/>
			</li>
		    <li>
			    <span>终止日期：</span>
			    <input id="Wenddate_${idx.index}" name="enddate" class="{dateISO:true} inputselectstyle datepicker" value="${item.enddate }"/>
			    <div class="date-trigger" onclick="$('#Wenddate_${idx.index}').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
	             <span><em class="red">* </em>汇报人：</span>
	             <input id="reporter_${idx.index}" name="reporter" class="{required:true,maxlength:50} inputstyle"  value="${item.reporter }"/>
	         </li>
	          <li>
				<span>汇报人邮箱：</span>
				<input id="email_${idx.index}" name="email"  value="${item.email }" class="{maxlength:50,email:true} inputstyle"/>
			</li>
		</ul>
		<ul>
			 <li>
			    <span><em class="red">* </em>汇报人电话：</span>
			    <input id="reporterphone_${idx.index}" name="reporterphone" class="{required:true,number:true,maxlength:50} inputstyle"  value="${item.reporterphone }"/>
			</li>
			<li>
				 <span>薪资：</span>
				 <input id="money_${idx.index}" name="money" class="{maxlength:50} inputstyle"  value="${item.money }"/>
			</li>
		</ul>
		<ul>
			<li>
	             <span>离职原因：</span>
	             <input id="resignationreason_${idx.index}" name="resignationreason" type="hidden" value="${item.resignationreason }" />
				<input id="resignationreasonname_${idx.index}"  value="${item.resignationreasonname }"  name="resignationreasonname" class="inputselectstyle"  onclick="chooseOptionTree('#resignationreason_${idx.index}','#resignationreasonname_${idx.index}','RESIGNATIONREASON');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#resignationreason_${idx.index}','#resignationreasonname_${idx.index}','RESIGNATIONREASON');"/>
	         </li>
		</ul>
		<ul>
            <li>
                 <span><em class="red">* </em>任职经历描述：</span>
                 <textarea id="description_${idx.index}" name="description"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle">${item.description }</textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editWorkExperience('#myWorkExperience_${idx.index}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveWorkExperience('#myWorkExperience_${idx.index}',${idx.index });"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delWorkExperience('#myWorkExperience_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>

<script type="text/javascript">
workexperienceNum++;

$("#myWorkExperience_${idx.index}").validate({debug:true,submitHandler: function(form) {
		$(form).ajaxSubmit(function(data) {
			alert("操作成功!");
			//formDisabled(true,"#myWorkExperience_"+workexperienceForm);
		});
	}
});

//不可修改
//formDisabled(true,"#myWorkExperience_${idx.index}");
//日期选择
initDatePicker();
</script>
</c:forEach>


