<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<form action="person/saveOrUpdateTmpTrain.do" method="post" id="myTmpTrain_${ordernum}" >
	<input id="trainid_${ordernum }" name="trainid" type="hidden" value=""/>
	<input id="mycandidatesguid_${ordernum }" name="mycandidatesguid" type="hidden" value="${mycandidatesguid }"/>
			
	<div id="" class="formDiv">
		<ul>
			 <li>
	             <span><em class="red">* </em>培训机构：</span>
	             <input id="trainorg_${ordernum }" name="trainorg" class="{required:true,maxlength:50} inputstyle"/>
	         </li>
	         <li>
			    <span><em class="red">* </em>培训时间：</span>
			    <input id="Mtraindate_${ordernum }" name="traindate" class="{required:true,dateISO:true} inputselectstyle datepicker"/>
			    <div class="date-trigger" onclick="$('#Mstartdate_${ordernum }').focus();"/>
			 </li>
	         
		</ul>
		<ul>
			<li>
			    <span><em class="red">* </em>培训周期（天）：</span>
			    <input id="trainlength_${ordernum }" name="trainlength" class="{required:true,maxlength:50} inputstyle"/>
			</li>
		    
		</ul>
		<ul>
		    <li>
	           <span><em class="red">* </em>培训成果：</span>
	           <textarea id="result_${ordernum }" name="result"  rows="2" style="width:570px;" class="{required:true,maxlength:500} areastyle"></textarea>
	        </li>
		</ul>
		<ul>
            <li>
                 <span><em class="red">* </em>培训内容：</span>
                 <textarea id="content_${ordernum }" name="content"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle"></textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editTmpTrain('#myTmpTrain_${ordernum }');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveTmpTrain('#myTmpTrain_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delTmpTrain('#myTmpTrain_${ordernum }',${ordernum });"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>


