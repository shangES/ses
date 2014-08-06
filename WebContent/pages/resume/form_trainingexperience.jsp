<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="resume/saveOrUpdateTrainingExperience.do" method="post" id="myTrainingExperience_${ordernum}" >
	<input id="trainingexperienceguid_${ordernum }" name="trainingexperienceguid" type="hidden" value=""/>
	<input id="webuserguid_${ordernum }" name="webuserguid" type="hidden" value="${webuserguid }"/>
	<input id="modtime_${ordernum }" name="modtime" type="hidden" value=""/>
	
		
	<div id="" class="formDiv">
		<ul>
			<li>
	             <span><em class="red">* </em>培训机构：</span>
	             <input id="traininginstitutions_${ordernum }" name="traininginstitutions" class="{required:true,maxlength:50} inputstyle"/>
	         </li>
	         <li>
			    <span><em class="red">* </em>证书：</span>
			    <input id="certificate_${ordernum }" name="certificate" class="{required:true,maxlength:50} inputstyle"/>
			</li>
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>开始时间：</span>
			    <input id="Tstartdate_${ordernum }" name="startdate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Tstartdate_${ordernum }').focus();"/>
			</li>
		    <li>
			    <span>结束时间：</span>
			    <input id="Tenddate_${ordernum }" name="enddate" class="{dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Tenddate_${ordernum }').focus();"/>
			</li>
		</ul>
		
		<ul>
            <li>
                 <span><em class="red">* </em>培训内容：</span>
                 <textarea id="trainingcontent_${ordernum }" name="trainingcontent"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle"></textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editTrainingExperience('#myTrainingExperience_${ordernum }');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveTrainingExperience('#myTrainingExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delTrainingExperience('#myTrainingExperience_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>


