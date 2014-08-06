<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="/META-INF/c.tld" %>
<c:forEach items="${tmptrainlist }" var="item" varStatus="idx">
<form action="person/saveOrUpdateTmpTrain.do" method="post" id="myTmpTrain_${idx.index}" >
	<input id="trainid_${idx.index}" name="trainid" type="hidden" value="${item.trainid }"/>
	<input id="mycandidatesguid_${idx.index}" name="mycandidatesguid" type="hidden" value="${item.mycandidatesguid }"/>	
		
	<div id="" class="formDiv">
		<ul>
			<li>
			    <span><em class="red">* </em>培训时间：</span>
			    <input id="Ttraindate_${idx.index}" name="traindate" class="{required:true,dateISO:true} inputselectstyle datepicker" value="${item.traindate }"/>
			    <div class="date-trigger" onclick="$('#Ttraindate_${idx.index}').focus();"/>
			</li>
		    <li>
	             <span><em class="red">* </em>培训周期（天）：</span>
	             <input id="trainlength_${idx.index}" name="trainlength" class="{required:true,maxlength:50} inputstyle" value="${item.trainlength }"/>
	        </li>
		</ul>
		<ul>
			<li>
	             <span><em class="red">* </em>培训机构：</span>
	             <input id="trainorg_${idx.index}" name="trainorg" class="{required:true,maxlength:50} inputstyle" value="${item.trainorg }"/>
	         </li>
	         
		</ul>
		<ul>
		    <li>
               <span><em class="red">* </em>培训成果：</span>
               <textarea id="result_${idx.index}" name="result"  rows="2" style="width:570px;" class="{required:true,maxlength:500} areastyle">${item.result }</textarea>
            </li> 
		</ul>
		<ul>
            <li>
                 <span><em class="red">* </em>培训内容：</span>
                 <textarea id="content_${idx.index}" name="content"  rows="5" style="width:570px;" class="{required:true,maxlength:500} areastyle">${item.content }</textarea>
            </li>
        </ul>
        <div class="formpanel">
			<div class="formpanel_line">
				<div class="right">
					<!--  <a class="btn" href="javascript:editTmpTrain('#myTmpTrain_${idx.index}');"><i class="icon icon-pencil"></i><span>修改</span></a>-->
					<a class="btn" href="javascript:saveTmpTrain('#myTmpTrain_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>保存</span></a>
					<a class="btn" href="javascript:delTmpTrain('#myTmpTrain_${idx.index}',${idx.index});"><i class="icon icon-check"></i><span>删除</span></a>
				</div>
			</div>
		</div>
	</div>
</form>
<script type="text/javascript">
trainNum++;

$("#myTmpTrain_${idx.index}").validate({debug:true,submitHandler: function(form) {
		$(form).ajaxSubmit(function(data) {
			alert("操作成功!");
			//formDisabled(true,"#myTmpTrain_"+trainForm);
		});
	}
});

//不可修改
//formDisabled(true,"#myTmpTrain_${idx.index}");

//日期选择
initDatePicker();
</script>

</c:forEach>

