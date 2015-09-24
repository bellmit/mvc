<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<title>角色修改页面</title>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/role/roleUpdForm.js"></script>
</head>
<body>
<#include "/pageloading.html"/>
<div>
	<div class="m_cont">
		<div class="form_div">
			
			<form id="funListForm" action="${rc.contextPath}/system/function/functionList" method="post"></form>
			<form id="checkForm" action="${rc.contextPath}" method="post">
			<table class="inputTable">
				<thead>
					<tr>
						<td colspan="2"><span class="steps_1_on"></span>填写角色信息</td>
					</tr>
				</thead>
				<tr>
					<td class="title"><span class="txt-impt">*</span>&nbsp;角色名称：</td>
					<td >
						<input name="roleName" type="text" class="inp_text easyui-validatebox" required="true"/>
					</td>
				</tr>
				<tr>
					<td class="title">角色描述：</td>
					<td >
						<textarea name="roleMemo" style="width:60%"></textarea>
					</td>
				</tr>
			</table>
			<table class="inputTable">
				<thead>
					<tr><td><span class="steps_2_on"></span>选择角色权限</td></tr>
				</thead>
			</table>
			<table id="funGrid" class="easyui-treegrid"  style="height:300px;">
			</table>
			</form>
			<div class="buttonArea">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="roleUpdForm.submit();">提交</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="roleUpdForm.reset();">重置</a>
			</div>
			<form id="roleDetailForm" action="${rc.contextPath}/system/role/getRoleDetail" method="post">
				<input name="id" value="${id!}" type="hidden"/>
			</form>
			<form id="submitForm" action="${rc.contextPath}/system/role/updateRole" method="post">
				<input name="id" value="${id!}" type="hidden"/>
				<input name="roleName" value="" type="hidden"/>
				<input name="roleMemo" value="" type="hidden"/>
			</form>
		</div>
	</div>
</div>
</body>
</html>
