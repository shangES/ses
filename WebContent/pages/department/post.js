

//打开添加post的窗口 保存
var addpost=false;
var postguid;
var postForm=null;
function saveOrUpdatePost(state,postguid){
	addpost=state;
	formDisabled(false,"#postForm");
	$("#postWindow").dialog({
		autoOpen: true,
		modal: true,
		resizable:false,
		width:500,
		buttons: {
			"确定": function() {
				//验证保存的岗位名是否重复
				verificationPostname();
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},open:function(){
			
			$("#postForm").clearForm();
			
			if(addpost){
				$("#postForm #deptid").val(selectNode.id);
				$("#postForm #companyid").val(selectNode.ename);
				$("#postForm #state").val(1);
			}else{
				loadPostById(postguid);
			}
			
			$("#postForm").validate();
			 postForm=$('#postForm').ajaxForm(function(data) {
					//加载岗位信息
		    		mygrid.reload();
					$("#postWindow").dialog("close");
					addpost=false;
			 });
		}
	});
}






//验证保存的岗位名是否重复
function verificationPostname(){
	var postid=$("#postForm #postid").val();
	var postname=$("#postForm #postname").val();
	$.post("department/verificationPostname.do",{postid:postid,deptid:selectNode.id,postname:postname},function(msg){
		if(msg!=''&&msg!=null){
			alert(msg);
			$("#postForm #postname").val(null);
		}else{
			$("#postForm").submit();
		}
	});
}




//输入input的时候也要校验
function checkPostname(){
	var postid=$("#postForm #postid").val();
	var postname=$("#postForm #postname").val();
	
	$.post("department/verificationPostname.do",{postid:postid,deptid:selectNode.id,postname:postname},function(msg){
		if(msg!=''&&msg!=null){
			alert(msg);
			$("#postForm #postname").val(null);
		}
	});
}







//通过岗位id加载岗位信息
function loadPostById(postguid){
	$.getJSON("department/getPostByPostId.do",{id:postguid},function(data){
		for(var key in data){
			if($('#postForm #'+key)){
				$('#postForm #'+key).val(data[key]);
			}
		}
	});
}








//删除岗位列表的数据
function delPostById(){
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.postid);
	}
	
	if(array.length<=0){
		alert('请选择要删除的数据！');
		return;
	}
	if(!confirm('确认要删除选中数据吗？')){
		return;
	}
   	$.post("department/delPostById.do",{ids:array.toString()}, function() {
		mygrid.reload();
    });
}









//失效or还原岗位列表的数据
function invalidOrReductionPostById(state){
	
	var array=new Array();
	var cords=mygrid.getSelectedRecords();
	for(var i=0;i<cords.length;i++){
		var obj=cords[i];
		array.push(obj.postid);
	}
	if(array.length<=0){
		alert('请选择要'+(state==0?'失效':'恢复')+'的数据！');
		return;
	}
	if(!confirm("确认要"+(state==0?'失效':'恢复')+"选中数据吗？")){
		return;
	}
   	$.post("department/invalidOrReductionPostById.do",{ids:array.toString(),state:state}, function() {
		mygrid.reload();
    });
}









//岗位信息的单个的失效或者还原生效
function recoverPost(id,valid){
	var array=new Array();
	array.push(id);
	
	if(!confirm('确认要'+(valid==0?'失效':'恢复')+'选中数据吗？')){
		return;
	}
	
	$.post("department/invalidOrReductionPostById.do",{ids:array.toString(),state:valid}, function() {
		mygrid.reload();
  });
}







//导出
function expPost(){
	$("#dialog_exp").dialog({
		autoOpen: true,
		modal: true,
		model:true,
		resizable:false,
		width:400,
		height:130,
		buttons: {
			"确定": function() {
				mygrid.exportGrid('xls');
				$(this).dialog("close");
			},
			"取消": function() {
				$(this).dialog("close");
			}
		},
		close: function() {
		}
	});
}