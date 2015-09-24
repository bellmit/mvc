<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<@headMacro.headMacro/>
    <script type="text/javascript" src="${rc.contextPath}/js/file/attachmentListForm.js"></script>
    <script type="text/javascript">
        var attachment = {
            fileDownLoad: function (url, attachmentPath, originalFileName) {
                var form = $("<form>");
                form.attr('method', 'post');
                form.attr('action', url);

                var input1 = $('<input>');
                input1.attr('type', 'hidden');
                input1.attr('name', 'attachmentPath');
                input1.attr('value', attachmentPath);

                var input2 = $('<input>');
                input2.attr('type', 'hidden');
                input2.attr('name', 'originalFileName');
                input2.attr('value', originalFileName);

                $('body').append(form);
                form.append(input1);
                form.append(input2);

                form.submit();
                form.remove();
            }
        }

    </script>
</head>
<body>
<#include "/pageloading.html"/>
<div class="maincont_div">
    <div class="m_cont">
        <div class="form_div">
            <form id="attachmentListForm" action="${rc.contextPath}/common/attachmentList2" method="post">
                <table id="attachmentGrid" style="width:auto;height:360px" title="附件列表"></table>
            </form>
            <table>
            <#list attachmentList as attachment>
                <a href="#"
                   onclick="attachment.fileDownLoad('${rc.contextPath}/common/downloadFile','${attachment.attachmentPath}','${attachment.originalFileName}');">${attachment.originalFileName}</a><br/>
            </#list>
            <#--<@c.forEach items="${attachmentList}" var="attachment">
                 <a href="#" onclick="attachment.fileDownLoad('${rc.contextPath}/common/downloadFile','${attachment.attachmentPath}','${attachment.originalFileName}');">${attachment.originalFileName}</a><br/>
               </@c.forEach>-->
            </table>
        </div>
    </div>
</div>
<form id="file" action="${rc.contextPath}" method="post"></form>
</body>
</html>