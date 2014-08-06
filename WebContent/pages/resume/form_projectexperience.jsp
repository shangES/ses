<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="resume/saveOrUpdateProjectExperience.do" method="post" id="myProjectExperience_${ordernum}" >
	<input id="projectexperienceguid_${ordernum }" name="projectexperienceguid" type="hidden" value=""/>
	<input id="webuserguid_${ordernum }" name="webuserguid" type="hidden" value="${webuserguid }"/>
	<input id="modtime_${ordernum }" name="modtime" type="hidden" value=""/>
	
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>项目名称：</span>
	             <input id="itemname_${ordernum }" name="itemname" class="{required:true,maxlength:50} inputstyle" style="width:570px;"/>
	         </li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="Pstartdate_${ordernum }" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Pstartdate_${ordernum }').focus();"/>
			</li>
		    <li>
			    <span>结束时间：</span>
			    <input id="Penddate_${ordernum }" name="enddate" class="{dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Penddate_${ordernum }').focus();"/>
			</li>
		</ul>
		
		<ul>
            <li>
                 <span><em class="red">* </em>职责描述：</span>
                 <textarea id="jobdescription_${ordernum }" name="jobdescription"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle"></textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editProjectExperience('#myProjectExperience_${ordernum }');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveProjectExperience('#myProjectExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delProjectExperience('#myProjectExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>


