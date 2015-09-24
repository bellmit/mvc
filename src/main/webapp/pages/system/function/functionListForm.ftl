<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<title>权限管理页面</title>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/function/functionListForm.js"></script>
</head>
<body>
<#include "/pageloading.html"/>
<div class="maincont_div">
	<div class="m_cont">
	
		<div class="form_div">
			<table id="funGrid"  style="width:auto;height:450px" title="权限列表"></table>
		</div>
			
	</div>
</div>
<form id="funListForm" action="${rc.contextPath}/system/function/functionList" method="post"/>
</body>
</html>
