<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${projectexperiences }" var="item" varStatus="idx">
<form action="resume/saveOrUpdateProjectExperience.do" method="post" id="myProjectExperience_${idx.index}" >
	<input id="projectexperienceguid_${idx.index}" name="projectexperienceguid" type="hidden" value="${item.projectexperienceguid }"/>
	<input id="webuserguid_${idx.index}" name="webuserguid" type="hidden" value="${item.webuserguid }"/>
	<input id="modtime_${idx.index}" name="modtime" type="hidden" value="${item.modtime }"/>
	
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>项目名称：</span>
	             <input id="itemname_${idx.index}" name="itemname" class="{required:true,maxlength:50} inputstyle" style="width:570px;" value="${item.itemname }"/>
	         </li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="Pstartdate_${idx.index}" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker" value="${item.startdate }"/>
			    <div class="date-trigger" onclick="$('#Pstartdate_${idx.index}').focus();"/>
			</li>
		    <li>
			    <span>结束时间：</span>
			    <input id="Penddate_${idx.index}" name="enddate" class="{dateISO:true} inputselectstyle datepicker" value="${item.enddate }"/>
			    <div class="date-trigger" onclick="$('#Penddate_${idx.index}').focus();"/>
			</li>
		</ul>
		
		<ul>
            <li>
                 <span><em class="red">* </em>职责描述：</span>
                 <textarea id="jobdescription_${idx.index}" name="jobdescription"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle">${item.jobdescription }</textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editProjectExperience('#myProjectExperience_${idx.index}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveProjectExperience('#myProjectExperience_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delProjectExperience('#myProjectExperience_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>


<script type="text/javascript">
projectexperienceNum++;

$("#myProjectExperience_${idx.index}").validate({debug:true,submitHandler: function(form) {
		$(form).ajaxSubmit(function(data) {
			alert("操作成功!");
			//formDisabled(true,"#myProjectExperience_"+projectexperienceForm);
		});
	}
});

//不可修改
//formDisabled(true,"#myProjectExperience_${idx.index}");
//日期选择
initDatePicker();
</script>

</c:forEach>


