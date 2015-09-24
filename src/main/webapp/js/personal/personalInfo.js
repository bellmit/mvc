
/**
  *个人信息修改
  *@auther zt
  *@date 2013-3-13 15:00
  */
var personalInfoForm = {
		
		//修改个人信息
		updatePersonalInfo:function(){
			var submitForm = $("#personalInfoForm");
			if(!submitForm.form("validate")){
				return;
			}
			var url = submitForm.attr("action")+"/personalController/personalInfoChanges";
			
			//序列化表单
			var form = submitForm.serialize();
			//ajax提交
			$.ajax({
				type : "post",
				dataType : 'json',
				url : url,
				cache:false,//禁用缓存 
				data:form,
				success : function(data) {
					// 关闭滚动条
					base.closepageloading();
					$.messager.show({  
                        title: '个人信息修改',  
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