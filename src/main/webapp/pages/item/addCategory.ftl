<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]/>
<#assign selector=JspTaglibs["/WEB-INF/taglib/showParamSelector.tld"]/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<head>
<@headMacro.headMacro/>
</head>
<body>
<form id="ems_baseCategory_add_panel" class="padding10">
    <div class="form-cell">
        <label>类别名称</label>
        <input id="categoryName" type="text" labelWidth="68" required="true" class="easyui-validatebox" sizeWidth="138" verWidth="400" name="examBaseCategory.categoryName"
               validType="remote['${ctx!}/html/exambase/exambase.checkBaseCategoryName.do','examBaseCategory.categoryName']"/>
        <input type="hidden" name="examBaseCategory.parentCategoryId" value="${id!}"/>
    </div>
    <div style="padding-left:68px;">
        <div class="form-cell-left">
            <input type="button" btnsize="big" style="color: blue" value="保存"/>
            <input type="button" btnsize="big" value='取消' class="ems_itempool_cancel"/>
        </div>
        <div class="clear"></div>
    </div>
</form>
</body>
</html>