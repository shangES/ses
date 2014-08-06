
var form1;
//简历批量导入
function impHTMLList(){
	var y=150;
	var $g = $(document.body);
	var x=$g.width() / 2 - 570/2;
	$("#uploadManyWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:570,
		height:400,
		position:[x,y],
		buttons: {
			"保存": function() {
				if(filelist.length>0){
						//数据
						$.ajax({  
					        url: "resume/resumeImport.do",  
					        contentType: "application/json; charset=utf-8",  
					        type: "POST",  
					        dataType: "json",  
					        data: JSON.stringify({list:filelist}),
					        success: function(msg) { 
					        	if(msg!=null&&msg!=''){
					        		alert(msg);
					        		return;
					        	}
					        	 mygrid.reload();
					        	 $("#uploadManyWindow").dialog("close");
					        	 $(".uploadTR").remove();
					        }
						});
				}
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			filelist=new Array();
		},close:function(){
			$(".uploadTR").remove();
		}
	});
}


//
var swfu;
function initManyUpload(basepath){
	SWFUpload.onload = function () {
		var settings = {
			flash_url : basepath+"plugin/swfupload/js/swfupload.swf",
			upload_url: basepath+"uploadFileList.do",
			post_params: {
				"table" : "tmp"
			},
			file_size_limit : "10240000",
			file_types : "",
			file_types_description : "",
			file_upload_limit : 100,
			file_queue_limit : 0,
			custom_settings : {
				progressTarget : "fsUploadProgress",
				cancelButtonId : "btnCancel",
				uploadButtonId : "btnUpload",
				myFileListTarget : "idFileList"
			},
			debug: false,
			auto_upload:false,

			// Button Settings
			button_image_url : "plugin/swfupload/images/XPButtonUploadText_61x22.png",	// Relative to the SWF file
			button_placeholder_id : "spanButtonPlaceholder",
			button_width: 61,
			button_height: 22,

			// The event handler functions are defined in handlers.js
			swfupload_loaded_handler : swfUploadLoaded,
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,
			queue_complete_handler : queueComplete,	// Queue plugin event
			
			// SWFObject settings
			minimum_flash_version : "11.0.28",
			swfupload_pre_load_handler : swfUploadPreLoad,
			swfupload_load_failed_handler : swfUploadLoadFailed
		};

		swfu = new SWFUpload(settings);
	};
}

//回调
var filelist=null;
function callUploadSuccess(file){
	filelist.push(file);
}

