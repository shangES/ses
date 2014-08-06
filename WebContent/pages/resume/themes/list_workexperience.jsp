<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${workexperiencelist }" var="item" varStatus="idx">
<form action="resume/saveOrUpdateWorkExperience.do" method="post" id="myWorkExperience_${idx.index}" class="myWorkExperience">
	<input id="workexperienceguid_${idx.index}" name="workexperienceguid" type="hidden" value="${item.workexperienceguid }"/>
	<input id="webuserguid_${idx.index}" name="webuserguid" type="hidden" value="${item.webuserguid }"/>
	<input id="modtime_${idx.index}" name="modtime" type="hidden" value="${item.modtime }"/>
	<input id="rmk_${idx.index}" name="rmk" type="hidden" value="${item.rmk }"/>
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>最近工作单位：</span>
	             <input id="workunit_${idx.index}" name="workunit" class="{required:true,maxlength:50} inputstyle" value="${item.workunit }"/>
	         </li>
	         <li>
			    <span><em class="red">* </em>职位：</span>
			    <input id="posation_${idx.index}" name="posation" class="{required:true,maxlength:12} inputstyle" value="${item.posation }"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="Wstartdate_${idx.index}" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker" value="${item.startdate }"/>
			    <div class="date-trigger" onclick="$('#Wstartdate_${idx.index}').focus();"/>
			</li>
		    <li>
			    <span>结束时间：</span>
			    <input id="Wenddate_${idx.index}" name="Wenddate" class="{dateISO:true} inputselectstyle datepicker" value="${item.enddate }"/>
			    <div class="date-trigger" onclick="$('#Wenddate_${idx.index}').focus();"/>
			</li>
		</ul>
		
		<ul>
			<li>
	             <span>证明人：</span>
	             <input id="references_${idx.index}" name="references" class="{maxlength:12} inputstyle"  value="${item.references }"/>
	         </li>
	         <li>
			    <span>证明人电话：</span>
			    <input id="referencesphone_${idx.index}" name="referencesphone" class="{maxlength:25} inputstyle"  value="${item.referencesphone }"/>
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


