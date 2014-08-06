<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${educationexperiences }" var="item" varStatus="idx">
<form action="resume/saveOrUpdateEducationExperience.do" method="post" id="myEducationExperience_${idx.index}" >
	<input id="educationexperienceguid_${idx.index}" name="educationexperienceguid" type="hidden" value="${item.educationexperienceguid}"/>
	<input id="webuserguid_${idx.index}" name="webuserguid" type="hidden" value="${item.webuserguid }"/>
	<input id="modtime_${idx.index}" name="modtime" type="hidden" value="${item.modtime}"/>
	
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>学校：</span>
	             <input id="school_${idx.index}" name="school" class="{required:true,maxlength:50} inputstyle" value="${item.school}"/>
	         </li>
	         <li>
			    <span><em class="red">* </em>学历情况：</span>
			    <input id="culture_${idx.index}" name="culture" type="hidden" value="${item.culture}"/>
			    <input id="culturename_${idx.index}" name="culturename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#culture_${idx.index}','#culturename_${idx.index}','CULTURE');" value="${item.culturename}"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#culture_${idx.index}','#culturename_${idx.index}','CULTURE');"/>
			</li>
		</ul>
		<ul>
			<li>
	             <span><em class="red">* </em>专业：</span>
	             <input id="specialty_${idx.index}" name="specialty" class="{required:true,maxlength:50} inputstyle" style="width:570px;" value="${item.specialty}"/>
	         </li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="Estartdate_${idx.index}" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker" value="${item.startdate}"/>
			    <div class="date-trigger" onclick="$('#Estartdate_${idx.index}').focus();"/>
			</li>
		    <li>
			    <span>结束时间：</span>
			    <input id="Eenddate_${idx.index}" name="enddate" class="{required:true,dateISO:true} inputselectstyle datepicker" value="${item.enddate}"/>
			    <div class="date-trigger" onclick="$('#Eenddate_${idx.index}').focus();"/>
			</li>
		</ul>
		
		<ul>
            <li>
                 <span><em class="red">* </em>专业描述：</span>
                 <textarea id="majordescription_${idx.index}" name="majordescription"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle">${item.majordescription}</textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editEducationExperience('#myEducationExperience_${idx.index}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveEducationExperience('#myEducationExperience_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delEducationExperience('#myEducationExperience_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>

<script type="text/javascript">
educationexperienceNum++;

$("#myEducationExperience_${idx.index}").validate({debug:true,submitHandler: function(form) {
		$(form).ajaxSubmit(function(data) {
			alert("操作成功!");
			//formDisabled(true,"#myEducationExperience_"+educationexperienceForm);
		});
	}
});

//不可修改
//formDisabled(true,"#myEducationExperience_${idx.index}");
//日期选择
initDatePicker();
</script>

</c:forEach>


