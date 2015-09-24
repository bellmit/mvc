<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" media="all" type="text/css" href="${rc.contextPath}/css/themes/default/easyui.css"
          id="easyuiTheme"/>
    <link rel="stylesheet" media="all" type="text/css" href="${rc.contextPath}/css/themes/icon.css"/>
    <script type="text/javascript" src="${rc.contextPath}/js/jquery/jquery-1.6.3.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/js/jquery/jquery.easyui.min-bak.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/js/jquery/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/js/login.js"></script>
    <script language="javascript" type="text/javascript">
        $(function () {
            $('#username').validatebox({
                missingMessage: "请输入用户名",
                required: true
            });

            $('#password').validatebox({
                missingMessage: "请输入密码",
                required: true
            });

            document.onkeydown = function (e) {
                var ev = document.all ? window.event : e;
                if (ev.keyCode == 13) {
                    var loginForm = $('#loginForm');
                    if (!loginForm.form('validate')) {
                        return;
                    }
                    loginForm.submit();
                }
            };

            var elements = document.forms[0].elements;
            for(var i=0;i < elements.length;i++){
                var item = elements[i];
                if(item.type != 'hidden'){
                    item.focus();
                    return;
                }
            }

        });
    </script>

    <style type="text/css">
        .m_cont {
            margin: 0 auto;
            position: relative;
            margin-top: 10%;
        }
    </style>
</head>
<body style="background-image: url('${rc.contextPath}/images/thdbg.jpg');background-size: 50%">
<img style="position:absolute;left:0;top:0;width:100px;height:150px;cursor:hand;"
     src="${rc.contextPath}/images/thdbg.jpg" alt="拉登"/>

<div class="m_cont" align="center" width="100%">
    <form id="loginForm" name="formName" action="${rc.contextPath}/spring_security_login" method="post">
        <table align="center">
            <tr>
                <td>用户:</td>
                <td><label>
                    <input id="username" type='text' name='j_username'/>
                </label></td>
            </tr>
            <tr>
                <td>密码:</td>
                <td><label>
                    <input id="password" type='password' name='j_password'/>
                </label></td>
            </tr>
        </table>
    </form>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok"
       onclick="login.submit();">登陆</a>
</div>
</body>

</html>
