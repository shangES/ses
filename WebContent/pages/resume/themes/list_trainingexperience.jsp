<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${trainingexperiences }" var="item" varStatus="idx">
<form action="resume/saveOrUpdateTrainingExperience.do" method="post" id="myTrainingExperience_${idx.index}" >
	<input id="trainingexperienceguid_${idx.index}" name="trainingexperienceguid" type="hidden" value="${item.trainingexperienceguid }"/>
	<input id="webuserguid_${idx.index}" name="webuserguid" type="hidden" value="${item.webuserguid }"/>
	<input id="modtime_${idx.index}" name="modtime" type="hidden" value="${item.modtime }"/>
	
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>培训机构：</span>
	             <input id="traininginstitutions_${idx.index}" name="traininginstitutions" class="{required:true,maxlength:50} inputstyle" value="${item.traininginstitutions }"/>
	         </li>
	         <li>
			    <span><em class="red">* </em>证书：</span>
			    <input id="certificate_${idx.index}" name="certificate" class="{required:true,maxlength:50} inputstyle" value="${item.certificate }"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="Tstartdate_${idx.index}" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker" value="${item.startdate }"/>
			    <div class="date-trigger" onclick="$('#Tstartdate_${idx.index}').focus();"/>
			</li>
		    <li>
			    <span>结束时间：</span>
			    <input id="Tenddate_${idx.index}" name="enddate" class="{dateISO:true} inputselectstyle datepicker" value="${item.enddate }"/>
			    <div class="date-trigger" onclick="$('#Tenddate_${idx.index}').focus();"/>
			</li>
		</ul>
		
		<ul>
            <li>
                 <span><em class="red">* </em>培训内容：</span>
                 <textarea id="trainingcontent_${idx.index}" name="trainingcontent"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle">${item.trainingcontent }</textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editTrainingExperience('#myTrainingExperience_${idx.index}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveTrainingExperience('#myTrainingExperience_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delTrainingExperience('#myTrainingExperience_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
trainingexperienceNum++;

$("#myTrainingExperience_${idx.index}").validate({debug:true,submitHandler: function(form) {
		$(form).ajaxSubmit(function(data) {
			alert("操作成功!");
			formDisabled(true,"#myTrainingExperience_"+trainingexperienceForm);
		});
	}
});

//不可修改
//formDisabled(true,"#myTrainingExperience_${idx.index}");

//日期选择
initDatePicker();
</script>

</c:forEach>

