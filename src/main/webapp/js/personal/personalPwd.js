
/**
  *个人密码修改
  *@auther zt
  *@date 2013-3-13 15:00
  */
var personalPwdForm = {
		
		updatePersonalPwd:function(){
			var submitForm = $("#personalPwdForm");
			if(!submitForm.form("validate"))
				return;
			
			var url = submitForm.attr("action")+"/personalController/personalPwdChanges";
			$.ajax({
				type : "post",
				dataType : 'json',
				url : url,
				cache:false,//禁用缓存 
				data:submitForm.serialize(),
				success : function(data) {
					// 关闭滚动条
					base.closepageloading();
						$("#expwd").val("");
						$("#operPassword").val("");
						$("#operPassword2").val("");
						$.messager.show({  
	                        title: '个人密码修改',  
	                        msg:data.message
	                    }); 
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// 通常情况下textStatus和errorThown只有其中一个有值 
					// 关闭滚动条
					base.closepageloading();
					$.messager.show({  
                        title: 'Error',  
                        msg:"服务器异常,请联系管理员!"
                    }); 
				}
			});
		}
}