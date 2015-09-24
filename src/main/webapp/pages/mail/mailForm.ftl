<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]/>
<#assign selector=JspTaglibs["/WEB-INF/taglib/showParamSelector.tld"]/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<head>
<@headMacro.headMacro/>
    <script type="text/javascript" src="${rc.contextPath}/js/mail/mailForm.js"></script>
    <script language="javascript">
        function changeColor() {
            var color = "#f00|#0f0|#00f|#880|#808|#088|yellow|green|blue|gray";
            color = color.split("|");
            document.getElementById("blink").style.color = color[parseInt(Math.random() * color.length)];
        }
        setInterval("changeColor()", 200);
    </script>
</head>
<body>
<#include "/pageloading.html"/>
<div class="maincont_div">
    <div class="m_cont">
        <div class="form_div">
            <form id="mail" action="${rc.contextPath}" method="post">
                <table align="center" width="90%">
                    <tr>
                        <td>邮件内容:</td>
                        <td><textarea id="mailContents" name="mailContents" style="width: 95%"></textarea></td>
                    </tr>
                    <tr>
                        <td>收件人:</td>
                        <td><input id="mailReceiver" type="text" name="mailReceiver">
                    </tr>
                </table>
            </form>
            <!--按钮部分开始-->
            <div class="buttonArea">
                <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="mail.sendMail();">发送</a>
            </div>
            <div id="blink">闪烁的文字</div>
        </div>
    </div>
</div>
</body>
</html>