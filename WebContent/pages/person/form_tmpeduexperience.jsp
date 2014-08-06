<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="person/saveOrUpdateTmpEduExperience.do" method="post" id="myEducationExperience_${ordernum}" >
	<input id="eduexperienceid_${ordernum }" name="eduexperienceid" type="hidden" value=""/>
	<input type="hidden" id="mycandidatesguid" name="mycandidatesguid" value="${mycandidatesguid}"/>	
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>教育机构：</span>
	             <input id="eduorg_${ordernum }" name="eduorg" class="{required:true,maxlength:50} inputstyle" />
	         </li>
	         <li>
			    <span><em class="red">* </em>学历：</span>
			    <input id="culture_${ordernum }" name="culture" type="hidden" value="" />
				<input id="culturename_${ordernum}" name="culturename" class="{required:true} inputselectstyle"  onclick="chooseOptionTree('#culture_${ordernum}','#culturename_${ordernum}','CULTURE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#culture_${ordernum}','#culturename_${ordernum}','CULTURE');"/>
			</li>
		</ul>
	    <ul>
			<li>
			    <span><em class="red">* </em>起始日期：</span>
			    <input id="Estartdate_${ordernum }" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Wstartdate_${ordernum }').focus();"/>
			</li>
		    <li>
			    <span>终止日期：</span>
			    <input id="Eenddate_${ordernum }" name="enddate" class="{dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Wenddate_${ordernum }').focus();"/>
			</li>
		</ul>
		<ul>
			<li>
	             <span><em class="red">* </em>学习专业：</span>
	             <input id="professional_${ordernum }" name="professional" class="{required:true,maxlength:50} inputstyle" />
	         </li>
	         <li>
			    <span><em class="red">* </em>学历证明：</span>
			    <input id="degree_${ordernum }" name="degree" class="{required:true,maxlength:12} inputstyle" />
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>学习形式：</span>
			    <input id="learningtype_${ordernum }" name="learningtype" type="hidden" value="" />
				<input id="learningtypename_${ordernum}" name="learningtypename" class="{required:true} inputselectstyle"  onclick="chooseOptionTree('#learningtype_${ordernum}','#learningtypename_${ordernum}','LEARNINGTYPE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#learningtype_${ordernum}','#learningtypename_${ordernum}','LEARNINGTYPE');"/>
			</li>
		</ul>
		<ul>
            <li>
                 <span><em class="red">* </em>描述：</span>
                 <textarea id="description_${ordernum }" name="description"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle"></textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editEducationExperience('#myEducationExperience_${ordernum }');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveEducationExperience('#myEducationExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delEducationExperience('#myEducationExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>


