<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="resume/saveOrUpdateEducationExperience.do" method="post" id="myEducationExperience_${ordernum}" >
	<input id="educationexperienceguid_${ordernum }" name="educationexperienceguid" type="hidden" value=""/>
	<input id="webuserguid_${ordernum }" name="webuserguid" type="hidden" value="${webuserguid }"/>
	<input id="modtime_${ordernum }" name="modtime" type="hidden" value=""/>
	
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>学校：</span>
	             <input id="school_${ordernum }" name="school" class="{required:true,maxlength:50} inputstyle"/>
	         </li>
	         <li>
			    <span><em class="red">* </em>学历情况：</span>
			    <input id="culture_${ordernum }" name="culture" type="hidden" value=""/>
			    <input id="culturename_${ordernum }" name="culturename" class="{required:true} inputselectstyle" onclick="chooseOptionTree('#culture_${ordernum }','#culturename_${ordernum }','CULTURE');"/>
			    <div class="select-trigger" onclick="chooseOptionTree('#culture_${ordernum }','#culturename_${ordernum }','CULTURE');"/>
			</li>
		</ul>
		<ul>
			<li>
	             <span><em class="red">* </em>专业：</span>
	             <input id="specialty_${ordernum }" name="specialty" class="{required:true,maxlength:50} inputstyle" style="width:570px;"/>
	         </li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="Estartdate_${ordernum }" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Estartdate_${ordernum }').focus();"/>
			</li>
		    <li>
			    <span>结束时间：</span>
			    <input id="Eenddate_${ordernum }" name="enddate" class="{dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Eenddate_${ordernum }').focus();"/>
			</li>
		</ul>
		
		<ul>
            <li>
                 <span><em class="red">* </em>专业描述：</span>
                 <textarea id="majordescription_${ordernum }" name="majordescription"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle"></textarea>
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


