<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <title>太平洋保险-投诉管理平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body class="login_bg">
<hr>
<table border="0" cellpadding="0" cellspacing="0">
    <tr>
        <th colspan="2">${mailVo.mailSubject!}</th>
    </tr>
    <tr>
        <th>发起人:</th>
        <td>${mailVo.mailSender!}</td>
    </tr>
    <tr>
        <th>接收人:</th>
        <td>
        <#if mailVo.mailReceivers??>
            <#list mailVo.mailReceivers as username>
                <#if username_index==0>
                ${username!}
                <#else>
                    、${username!}
                </#if>

            </#list>
        </#if>
        </td>
    </tr>
    <tr>
        <th>案件编号:</th>
        <td>${mailVo.caseCode!}</td>
    </tr>
    <tr>
        <th>描述:</th>
        <td>${mailVo.mailContents!}</td>
    </tr>
<#if mailVo.mailHref??>
    <tr>
        <th>处理连接:</th>
        <td><a href="${mailVo.mailHref!}">${mailVo.mailHref!}</a></td>
    </tr>
</#if>
</table>
</body>

</html>
