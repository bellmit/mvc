$(document).ready(function() {
	
});

var complaintCaseUpdForm = {
	
	//提交表单
		submit:function(){
		var checkForm = $("#caseUpForm");
		if(!checkForm.form('validate')){
			return;
		}
		var url=checkForm.attr("action")
		
		$.post(url, checkForm.serialize(), function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
		
		});
	},
	 
	submit2:function(){
		var checkForm = $("#acceptanceNode");
		if(!checkForm.form('validate')){
			return;
		}
		var url=checkForm.attr("action")
		
		$.post(url, checkForm.serialize(), function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
		
		});
	},
	
	
	
	
}