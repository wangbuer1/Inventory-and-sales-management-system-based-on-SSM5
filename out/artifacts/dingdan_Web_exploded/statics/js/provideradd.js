var proCode = null;
var proName = null;
var proContact = null;
var proPhone = null;
var addBtn = null;
var backBtn = null;

var errorinfo = null;
var a_companyLicPicPath = null;
var errorinfo_oc=null;
var a_orgCodePicPath = null;

$(function(){
	proCode = $("#proCode");
	proName = $("#proName");
	proContact = $("#proContact");
	proPhone = $("#proPhone");
	
	errorinfo = $("#errorinfo");
	a_companyLicPicPath =$("#a_companyLicPicPath");
	errorinfo_oc=$("#errorinfo_oc");
	a_orgCodePicPath = $("#a_orgCodePicPath");
	
	addBtn = $("#add");
	backBtn = $("#back");
	//初始化的时候，要把所有的提示信息变为：* 以提示必填项，更灵活，不要写在页面上
	proCode.next().html("*");
	proName.next().html("*");
	proContact.next().html("*");
	proPhone.next().html("*");
	if(errorinfo.val()==null||errorinfo.val()==""){
		a_companyLicPicPath.next().html("* 上传大小不能超过500KB * 上传文件类型必须为：jpg、jpeg、png、pneg");
	}else{
		a_companyLicPicPath.next().html(errorinfo.val());
	}
	if(errorinfo_oc.val()==null||errorinfo_oc.val()==""){
		a_orgCodePicPath.next().html("* 上传大小不能超过500KB * 上传文件类型必须为：jpg、jpeg、png、pneg");
	}else{
		a_orgCodePicPath.next().html(errorinfo_oc.val());
	}
	/*
	 * 验证
	 * 失焦\获焦
	 * jquery的方法传递
	 */
	proCode.on("blur",function(){
		if(proCode.val() != null && proCode.val() != ""){
			validateTip(proCode.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(proCode.next(),{"color":"red"},imgNo+" 编码不能为空，请重新输入",false);
		}
	}).on("focus",function(){
		//显示友情提示
		validateTip(proCode.next(),{"color":"#666666"},"* 请输入供应商编码",false);
	}).focus();
	
	proName.on("focus",function(){
		validateTip(proName.next(),{"color":"#666666"},"* 请输入供应商名称",false);
	}).on("blur",function(){
		if(proName.val() != null && proName.val() != ""){
			validateTip(proName.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(proName.next(),{"color":"red"},imgNo+" 供应商名称不能为空，请重新输入",false);
		}
		
	});
	
	proContact.on("focus",function(){
		validateTip(proContact.next(),{"color":"#666666"},"* 请输入联系人",false);
	}).on("blur",function(){
		if(proContact.val() != null && proContact.val() != ""){
			validateTip(proContact.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(proContact.next(),{"color":"red"},imgNo+" 联系人不能为空，请重新输入",false);
		}
		
	});
	
	proPhone.on("focus",function(){
		validateTip(proPhone.next(),{"color":"#666666"},"* 请输入手机号",false);
	}).on("blur",function(){
		var patrn=/^(13[0-9]|15[0-9]|18[0-9])\d{8}$/;
		if(proPhone.val().match(patrn)){
			validateTip(proPhone.next(),{"color":"green"},imgYes,true);
		}else{
			validateTip(proPhone.next(),{"color":"red"},imgNo + " 您输入的手机号格式不正确",false);
		}
	});
	
	addBtn.bind("click",function(){
		if(proCode.attr("validateStatus") != "true"){
			proCode.blur();
		}else if(proName.attr("validateStatus") != "true"){
			proName.blur();
		}else if(proContact.attr("validateStatus") != "true"){
			proContact.blur();
		}else if(proPhone.attr("validateStatus") != "true"){
			proPhone.blur();
		}else{
			if(confirm("是否确认提交数据")){
				$("#providerForm").submit();
			}
		}
	});
	
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