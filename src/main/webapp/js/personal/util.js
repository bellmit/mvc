
/**
  * 自定义easyUI验证规则，重写 $.fn.validatebox.defaults.rules 
  *@auther zt
  *@date 2013-3-13 16:43
  */
$.extend($.fn.validatebox.defaults.rules, {
	safepass: {   
        validator: function (value, param) {   
            return /^[0-9a-zA-Z]{6,}$/.test(value);   
        },   
        message: '密码由字母和数字组成，至少6位'   
    },   
    equalTo: {   
        validator: function (value, param) {   
            return value == $(param[0]).val();   
        },   
        message: '两次输入的密码不一致'   
    },   
    mobile: {   
        validator: function (value, param) {   
            return /^((\(\d{2,3}\))|(\d{3}\-))?13\d{9}$/.test(value);   
        },   
        message: '手机号码不正确'   
    },   
    uname: {   
        validator: function (value, param) {   
            return /^[\u0391-\uFFE5\w]+$/.test(value);   
        },   
        message: '姓名称只允许汉字、英文字母、数字及下划线'   
    },     
    email:{
    	validator:function(value,param){
    		return /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/.test(value);
    	},
    	message:"邮箱格式错误"
    }
}); 