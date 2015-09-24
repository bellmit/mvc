$(document).ready(function() {
	
});
var mail = {
	sendMail:function(){
		var submitForm = $("#mail");
//		if(!submitForm.form("validate")){
//			//return;
//		}
//		var mailContents = $('#mailContents').val();
//		if(mailContents == null || mailContents == ""){
//			alert('邮件内容不可为空!');
//			return;
//		}
//		var mailReceiver = $('#mailReceiver').val();
//		if(mailReceiver == null || mailReceiver == ""){
//			alert('收件人不可为空!');
//			return;
//		}
		var url = submitForm.attr("action")+'/mail/sendMail';
		//序列化表单
		var form = submitForm.serialize();
		var param = base.decodeURI(form);
		var params = base.strToJson(param);
		//ajax提交
		$.ajax({
			type : "post",
			dataType : 'json',
			url : url,
			cache:false,
			data:params,
			success : function(data) {
				// 关闭滚动条
				base.closepageloading();
				alert(data.msg);
				base.closeTab("toSendMailForm");
			}
		});
	}
}