var backBtn =null;
var saveBtn = null;
var roleCode = null;
var roleName = null;
$(function (){
	backBtn = $("#back");
	saveBtn = $("#save");
	roleCode = $("#roleCode");
	roleName = $("#roleName");

    //页面数据验证
	roleCode.on("focus",function(){
		validateTip(roleCode.next(),{"color":"#666666"},"* 请输入角色编码",false);
	}).on("blur",function(){
		if(roleCode.val() != null && roleCode.val() != ""){
			validateTip(roleCode.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(roleCode.next(),{"color":"red"},imgNo+" 角色编码不能为空，请重新输入",false);
		}

	});

	roleName.on("focus",function(){
		validateTip(roleName.next(),{"color":"#666666"},"* 请输入角色名称",false);
	}).on("blur",function(){
		if(roleName.val() != null && roleName.val() != "" ){
			validateTip(roleName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(roleName.next(),{"color":"red"},imgNo+" 角色名称不能为空,请重新输入",false);
		}

	});
	
	saveBtn.bind("click",function(){
		if(roleCode.attr("validateStatus") != "true"){
			roleCode.blur();
		}else if(roleName.attr("validateStatus") != "true"){
			roleName.blur();
		}else{
			if(confirm("是否确认提交数据")){
				$("#roleForm").submit();
			}
		}
	});






    //返回按钮
	backBtn.on("click",function(){
		if(referer != undefined 
				&& null != referer 
				&& "" != referer
				&& "null" != referer
				&& referer.length > 4){
			window.location.href = referer;
		}else{
			history.back(-1);
		}
	});
});





