function graphTrace(pid) {

    // 获取图片资源
    var imageUrl ="bpmn/loadByProcessInstance.do?processInstanceId=" + pid + "&resourceType=image";
    $.getJSON('bpmn/traceProcess.do?processInstanceId=' + pid, function(infos) {

        var positionHtml = "";

        // 生成图片
        var varsArray = new Array();
        $.each(infos, function(i, v) {
            var $positionDiv = $('<div />', {
                'class': 'activity-attr'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 2),
                height: (v.height - 2),
                backgroundColor: 'black',
                opacity: 0,
                zIndex: 88
            });

            // 节点边框
            var $border = $('<div/>', {
                'class': 'activity-attr-border'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 4),
                height: (v.height - 3),
                zIndex: 888
            });

            if (v.currentActiviti) {
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid red'
                });
            }
            
            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
            varsArray[varsArray.length] = v.vars;
        });
        
        

        if ($('#workflowTraceDialog').length == 0) {
            $('<div/>', {
                id: 'workflowTraceDialog',
                title: '查看流程（按ESC键可以关闭）',
                html: "<div><img src='" + imageUrl + "' style='position:absolute; left:0px; top:0px;' />" +
                "<div id='processImageBorder'>" +
                positionHtml +
                "</div>" +
                "</div>"
            }).appendTo('body');
        } else {
            $('#workflowTraceDialog img').attr('src', imageUrl);
            $('#workflowTraceDialog #processImageBorder').html(positionHtml);
        }

        // 设置每个节点的data
        $('#workflowTraceDialog .activity-attr-border').each(function(i, v) {
        	 var vars=varsArray[i];
       	     var tipContent = "<table class='need-border'>";
             $.each(vars, function(varKey, varValue) {
                 if (varValue) {
                     tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
                 }
             });
             tipContent += "</table>";
             
             //彈出
             $(this).poshytip({
     			showTimeout:100,
     			alignTo: 'target',
     			alignX: 'right',
     			alignY: 'center',
     			offsetX: 5,
     			content:function(){
     				return tipContent;
     			}
         	});
        });
        
      
        

        // 打开对话框
        $('#workflowTraceDialog').dialog({
            modal: true,
            resizable: true,
            dragable: true,
            width:800,
			height:480,
            open: function() {
                $('#workflowTraceDialog').dialog('option', 'title', '查看流程（按ESC键可以关闭）');
                $('#workflowTraceDialog').css('padding', '0.2em');
                $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);
            },
            close: function() {
                $('#workflowTraceDialog').remove();
            }
        });

    });
}






















/**
 * 渲染流程信息
 * @param obj
 */
function renderProcessInfo(data){
	$("#myProcess").html(null);
	if(data==null||data.length<=0)
		return;
	
	var htm='';
	htm+='<fieldset>';
	htm+='<legend>流程信息</legend>';
	htm+='<ul>';
	htm+='<li>';
	htm+='<span>&nbsp;</span>';
	htm+='<div>';
	htm+='<table cellpadding="0" cellspacing="0" border="0" class="static-table" style="width:578px;">';
	htm+='<thead>';
	htm+='<tr class="ui-state-default">';
	htm+='<th>公司</th>';
	htm+='<th>部门</th>';
	htm+='<th>用户</th>';
	htm+='<th width="140px">时间</th>';
	htm+='<th>结果</th>';
	htm+='</tr>';
	htm+='</thead>';
	htm+='<tbody>';
	
	
	for(var i=0;i<data.length;i++){
		var obj=data[i];
		htm+='<tr>';
		htm+='<td>';
		htm+=obj.applycompanyname;
		htm+='</td>';
		htm+='<td>';
		htm+=obj.applydeptname;
		htm+='</td>';
		htm+='<td>';
		htm+=obj.applyusername;
		htm+='</td>';
		htm+='<td align="center">';
		htm+=obj.excutedate;
		htm+='</td>';
		htm+='<td>';
		htm+=obj.opinion;
		htm+='</td>';
		htm+='</tr>';
	}
	
	
	
	
	htm+='</tbody>';
	htm+='</table>';
	htm+='</div>';
	htm+='</li>';
	htm+='</ul>';
	htm+='</fieldset>';
	htm+='<br />';
	$("#myProcess").html(htm);
	
	//计算高度
	_autoHeight();
}
