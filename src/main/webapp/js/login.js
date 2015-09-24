$(document).ready(function () {
    $.messager.show({
        title: '登录页面欢迎您!',
        msg: '登录页面欢迎您!'
    });
});

var login = {
    submit: function () {
        var loginForm = $('#loginForm');
        if(!loginForm.form('validate')){
            return;
        }
        loginForm.submit();
    }
};