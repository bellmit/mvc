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
    },
    ajaxSubmit: function () {
        login.openLoginDiv();
    },
    openLoginDiv: function () {
        var htl = "<div id='loginFormDiv'><span class='span_error' id='login_error'></span><a href='javascript:void(0);' onclick='login.closeDiv();'>关闭窗口</a><form action='##' method='post' id='ajaxForm'><table>"
            + "<tr><td colspan='2'><span style='color:#FF0000;' id='login_error'></span></td></tr>"
            + "<tr><td class='td_name'>公司：</td><td class='td_text'><input type='text' name='corpCode' id='j_corpCode' /></td></tr>"
            + "<tr><td class='td_name'>用户：</td><td class='td_text'><input type='text' name='j_username' id='j_username' /></td></tr>"
            + "<tr><td class='td_name'>密码：</td><td class='td_text'><input type='password' name='j_password' id='j_password' /></td></tr>"
            + "<tr><td colspan='2' class='td_but'><input type='button' id='login_button' onclick='login.doLogin();' value='登录' /></td></tr>"
            + "</table></form></div><div id='bg' class='bg' style='display: none;'></div>" +
            "<iframe id='popIframe' class='popIframe' frameborder='0'></iframe>";

        if (!$(".loginDiv").hasClass(
            "loginDiv")) {
            //alert(htl);
            // 动态写一个div弹出层
            $("<div>", {
                "class": "loginDiv",
                "id": "loginDiv"
            }).append(htl).appendTo("body");
        }
        document.getElementById('popIframe').style.display = 'block';
        document.getElementById('bg').style.display = 'block';
    },
// 关闭窗口
    closeDiv: function () {
        $("#loginDiv").remove();
        document.getElementById('bg').style.display = 'none';
        document.getElementById('popIframe').style.display = 'none';
        window.location.reload(true);
    },
    //登录操作
    doLogin: function () {
        var ajaxLoginForm = $('#ajaxLoginForm');
        var url = ajaxLoginForm.attr('action');
        var j_username = $("#j_username").val();
        var j_password = $("#j_password").val();
        var corpCode = $("#j_corpCode").val();
        var redirectURL = location.href;
        document.getElementById("checkLoginMsg").innerHTML = '';
        var checkLoginForm = $('#checkLoginForm');
        var loginUrl = checkLoginForm.attr('action');
        $.post(loginUrl, $('#ajaxForm').serialize(), function (data) {
            if (data.success) {
                document.getElementById("login_error").innerHTML = '';
                $.ajax({
                    type: "POST",
                    url: url + "/spring_security_login",
                    data: "j_username="
                        + j_username
                        + "&j_password="
                        + j_password
                        + "&corpCode="
                        + corpCode
                        + "&ajax=ajax" + "&redirectURL=" + redirectURL,
                    success: function (msg) {
                        alert(msg);
                        if (msg == "success") {
                            // 如果登录成功，则跳转到。
                            alert("登录成功");
                            window.location.reload(true);
                            //closeDiv();
                            //alert(111);
                        } else {
                            // 写入登录失败信息
                            var errors = "对不起，用户名或密码不正确！";
                            $("#login_error").html(errors);
                        }
                    }
                });
            } else {
                document.getElementById("login_error").innerHTML = data.msg;
            }
        });
    }
};
