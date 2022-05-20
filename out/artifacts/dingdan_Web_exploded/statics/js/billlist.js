var billObj;

//订单管理页面上点击删除按钮弹出删除框(billlist.jsp)
function deleteBill(obj){
	$.ajax({
		type:"GET",
		url:path+"/bill/deletebillbyid/"+obj.attr("billid"),
		dataType:"json",
		success:function(data){
			alert(data.delResult);
			if(data.delResult == "true"){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//删除失败
				//alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
				changeDLGContent("对不起，删除订单【"+obj.attr("billcc")+"】失败");
			}else if(data.delResult == "notexist"){
				//alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
				changeDLGContent("对不起，订单【"+obj.attr("billcc")+"】不存在");
			}
		},
		error:function(data){
			alert("对不起，删除失败");
		}
	});
}

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeBi').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeBi').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

 var provider = $("#queryProviderId");
	$(function(){
		//查询所有的供应商信息
		$.ajax({
			type:"GET",
			url:path+"/provider/providerlist",
			dataType:"json",
			success:function(data){//data：返回数据（json对象）
				if(data.providerlist != null){
					provider.html("");
					var options = "<option value=\"0\">请选择</option>";
					for(var i = 0; i < data.providerlist.length; i++){
							options += "<option  value=\""+data.providerlist[i].id+"\" >"+data.providerlist[i].proName+"</option>";
					}
					provider.html(options);
				}
			},
			error:function(data){//当访问时候，404，500 等非200的错误状态码
				validateTip(provider.next(),{"color":"red"},imgNo+" 获取供应商列表error",false);
			}
		});
		
    //显示订单信息
	$(".viewBill").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href=path+"/bill/view/"+ obj.attr("billid");
	});
	
	$(".modifyBill").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/bill/updatebill/"+ obj.attr("billid");
	});
	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteBill(billObj);
	});

	$(".deleteBill").on("click",function(){
		billObj = $(this);
		changeDLGContent("你确定要删除订单【"+billObj.attr("billcc")+"】吗？");
		openYesOrNoDLG();
	});
	 
	/*$(".deleteBill").on("click",function(){
		var obj = $(this);
		if(confirm("你确定要删除订单【"+obj.attr("billcc")+"】吗？")){
			$.ajax({
				type:"GET",
				url:path+"/bill.do",
				data:{method:"delbill",billid:obj.attr("billid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//删除成功：移除删除行
						alert("删除成功");
						obj.parents("tr").remove();
					}else if(data.delResult == "false"){//删除失败
						alert("对不起，删除订单【"+obj.attr("billcc")+"】失败");
					}else if(data.delResult == "notexist"){
						alert("对不起，订单【"+obj.attr("billcc")+"】不存在");
					}
				},
				error:function(data){
					alert("对不起，删除失败");
				}
			});
		}
	});*/
});
