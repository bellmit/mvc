<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]/>
<#assign selector=JspTaglibs["/WEB-INF/taglib/showParamSelector.tld"]/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<head>
<@headMacro.headMacro/>
    <link rel="stylesheet" href="${rc.contextPath}/css/moveTree.css" type="text/css"/>
    <script type="text/javascript" src="${rc.contextPath}/js/thd/moveTree.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/js/item/categoryManager.js"></script>
</head>
<body>
<#--<#include "/pageloading.html"/>-->
<form id="jsonForm" action="${rc.contextPath}"></form>
<div class="m_cont" border="1" align="center" width="98%">
    <div id="tt"></div>
    <hr>
    <div id="moveTreeId"></div>
</div>

<div id="mm" class="easyui-menu" style="width:120px;">
    <div onclick="category.addCategory()" data-options="iconCls:'icon-add'">新增</div>
    <div onclick="category.removeCategory()" data-options="iconCls:'icon-remove'">删除</div>
    <div class="menu-sep"></div>
    <div onclick="expand()">Expand</div>
    <div onclick="collapse()">Collapse</div>
</div>

<div id="addCategory" class="easyui-dialog" closed="true" title="新增类别" data-options="iconCls:'icon-save'"
     style="width:350px;height:150px;padding:10px" buttons="#addCategoryButtons">
    <div class="form_div">
        <div class="form-cell">
            <label>类别名称</label>
            <input id="categoryName" type="text" labelWidth="68" required="true" class="easyui-validatebox" sizeWidth="138" verWidth="400" name="categoryName"/>
            <span class="verification-panel"><p class="input-verification-empty">asdfas</p></span>
            <input type="hidden" name="examBaseCategory.parentCategoryId" value="${id!}"/>
        </div>
    </div>
</div>
<div id="addCategoryButtons">
    <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="organizationListForm.dialogSave();">保存</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="category.dialogCancel('addCategory');">取消</a>
</div>
</body>
</html>