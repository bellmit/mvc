$(document).ready(function () {
    $.messager.show({
        title: '登录页面欢迎您!',
        msg: '登录页面欢迎您!'
    });
});

var login = {
    submit: function () {
        var loginForm = $('#loginForm');
        if (!loginForm.form('validate')) {
            return;
        }

        document.getElementById("checkLoginMsg").innerHTML = '';
        var checkLoginForm = $('#checkLoginForm');
        var url = checkLoginForm.attr('action');
        $.post(url, loginForm.serialize(), function (data) {
            if (data.success) {
                if (!loginForm.form('validate')) {
                    return;
                }

                document.getElementById("checkLoginMsg").innerHTML = '';
                loginForm.submit();
            } else {
                document.getElementById("checkLoginMsg").innerHTML = data.msg;
            }
        });
    }
};