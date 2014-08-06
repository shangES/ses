<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${tmpeduexperiencelist }" var="item" varStatus="idx">
<form action="person/saveOrUpdateTmpEduExperience.do" method="post" id="myEducationExperience_${idx.index}" class="myEducationExperience">
	<input id="eduexperienceid_${idx.index}" name="eduexperienceid" type="hidden" value="${item.eduexperienceid }"/>
	<input id="mycandidatesguid_${idx.index}" name="mycandidatesguid" type="hidden" value="${item.mycandidatesguid }"/>		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>教育机构：</span>
	             <input id="eduorg_${idx.index}" name="eduorg" class="{required:true,maxlength:50} inputstyle" value="${item.eduorg }"/>
	         </li>
	          <li>
			    <span><em class="red">* </em>学历：</span>
			    <input id="culture_${idx.index }" name="culture" type="hidden" value="${item.culture }" />
				<input id="culturename_${idx.index}" name="culturename" value="${item.culturename }" class="{required:true} inputselectstyle"  onclick="chooseOptionTree('#culture_${idx.index}','#culturename_${idx.index}','CULTURE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#culture_${idx.index}','#culturename_${idx.index}','CULTURE');"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>起始时间：</span>
			    <input id="Estartdate_${idx.index}" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker" value="${item.startdate }"/>
			    <div class="date-trigger" onclick="$('#Estartdate_${idx.index}').focus();"/>
			</li>
		    <li>
			    <span>结束时间：</span>
			    <input id="Eenddate_${idx.index}" name="enddate" class="{dateISO:true} inputselectstyle datepicker" value="${item.enddate }"/>
			    <div class="date-trigger" onclick="$('#Eenddate_${idx.index}').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
	             <span><em class="red">* </em>学习专业：</span>
	             <input id="professional_${idx.index}" name="professional" class="{required:true,maxlength:50} inputstyle" value="${item.professional }"/>
	         </li>
	         <li>
			    <span><em class="red">* </em>学历证明：</span>
			    <input id="degree_${idx.index}" name="degree" class="{required:true,maxlength:12} inputstyle" value="${item.degree }"/>
			</li>
		</ul>
		<ul>
			 <li>
			    <span><em class="red">* </em>学习形式：</span>
			    <input id="learningtype_${idx.index}" name="learningtype" type="hidden" value="${item.learningtype }"/>
				<input id="learningtypename_${idx.index}" name="learningtypename" class="{required:true} inputselectstyle" value="${item.learningtypename }" onclick="chooseOptionTree('#learningtype_${idx.index}','#learningtypename_${idx.index}','LEARNINGTYPE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#learningtype_${idx.index}','#learningtypename_${idx.index}','LEARNINGTYPE');"/>
			</li>
		</ul>
		<ul>
            <li>
                 <span><em class="red">* </em>描述：</span>
                 <textarea id="description_${idx.index}" name="description"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle">${item.description }</textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editEducationExperience('#myEducationExperience_${idx.index}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveEducationExperience('#myEducationExperience_${idx.index}',${idx.index });"><i class="icon icon-check"></i><span>保存</span></a>
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


