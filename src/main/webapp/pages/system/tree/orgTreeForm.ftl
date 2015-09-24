<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]/>
<#assign selector=JspTaglibs["/WEB-INF/taglib/showParamSelector.tld"]/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">

<#import "/base/macro/headMacro.ftl" as headMacro>
<head>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/tree/orgTreeForm.js"></script>
</head>
<body>
<ul id="tree"></ul><br>
<div class="m_cont" border="1" align="center" width="98%">
	<form id="jsonForm" action="${rc.contextPath}"></form>
	<h2>Basic ComboTree</h2>
    <div class="demo-info">
        <div class="demo-tip icon-tip"></div>
        <div>Click the right arrow button to show the tree panel.</div>
    </div>
    <div style="margin:10px 0"></div>
    <input id="combotree_1" data-options="required:true" style="width:200px;"><br>
    <input type="button" value="查看选中的数据" onclick="stuList.show_1()"/><br>
    <input id="combotree" data-options="required:true" style="width:200px;"><br>
    <input type="button" value="查看选中的数据" onclick="stuList.show()"/><br>
    <input id="multiple_combotree" data-options="required:true" style="width:200px;"><br>
    <input type="button" value="查看选中的数据" onclick="stuList.show3()"/><br>
   <#-- <@selector.option itemId="tt" itemName="customeFeedback" required="true" paramType="0"/><br>-->
    <hr>
    投诉要点：<br>
    <select id="complaintGist1" name="complaintGist1"></select>
    &nbsp;&nbsp;
    <select id="complaintGist2" name="complaintGist2"></select><br>
    <input type="button" value="查看选中的数据" onclick="stuList.show4()"/><br>

</div>
</body>
</html>