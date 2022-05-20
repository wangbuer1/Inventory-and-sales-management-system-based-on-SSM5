$(function(){
	
	$(".deleteRole").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		if(confirm("你确定要删除订单【"+obj.attr("roleid")+"】吗？")){
			$.ajax({
				type:"GET",
				url:path+"/role/delrole.html",
				data:{roleid:obj.attr("roleid")},
				dataType:"json",
				success:function(data){
					data = JSON.parse(data);
					if(data.delResult == "true"){//删除成功：移除删除行
						alert("删除成功");
						obj.parents("tr").remove();
					}else if(data.delResult == "false"){//删除失败
						alert("对不起，删除订单【"+obj.attr("roleid")+"】失败");
					}else if(data.delResult == "notexist"){
						alert("对不起，订单【"+obj.attr("roleid")+"】不存在");
					}else{
						alert("对不起，该供应商【"+obj.attr("roleid")+"】下有【"+data.delResult+"】条订单，不能删除");
					}
				},
				error:function(data){
					alert("对不起，删除失败");
				}
			});
		}
	});
	
	$(".modifyRole").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/role/updaterole.html/"+ obj.attr("roleid");
	});
	
	
});