$(document).ready(function() {
	complaintCaseUpdForm.init();
});

var complaintCaseUpdForm = {
	//formHtml:'',
	//functionMap:[],
	init:function(){
		//operatorUpdForm.functionMap = new TabMap();
		//operatorUpdForm.formHtml = $("#submitForm").html();
		//$("#checkForm [name='complainantName']").val('');
		//$("#checkForm [name='plateNum']").val('');
		//$("#checkForm [name='operMail']").val('');	
//		$("#checkForm [name='operGender']").val('');	
//		$("#checkForm [name='operType']").val('');		
	},
	//重置
	reset:function(){
		$("#caseUpForm")[0].reset();
		complaintCaseUpdForm.init();
	},
	
	
	
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
	
	
	onclickGender:function(){
		if($("#checkForm [name=operGender]").get(0).checked){
			$("#submitForm input[name='operGender']").val('MAN');//用户性别(男)
		}else{
			$("#submitForm input[name='operGender']").val('WOMEN');//用户性别(女)
		}
	},
	onclickType:function(){
		if($("#checkForm [name=operType]").get(1).checked){
			$("#submitForm input[name='operType']").val('NORMAL');//用户类型(普通员工)
		}else{
			$("#submitForm input[name='operType']").val('MANAGER');//用户性别(主管)
		}
	}
	
}