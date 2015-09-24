<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<title>角色管理页面</title>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/role/roleListForm.js"></script>
</head>
<body>
<#include "/pageloading.html"/>
<div class="maincont_div">
	<div class="m_cont">
		<div class="form_div">
			<form id="queryRoleListForm" action="${rc.contextPath}/system/role/roleList" method="post">
			<table class="queryTable">
				<thead>
					<tr>
						<td colspan="2">填写查询信息</td>
					</tr>
				</thead>
				<tr>
					<td class="title">角色名称：</td>
					<td >
						<input name="roleName" type="text" class="inp_text easyui-validatebox"/>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="roleListForm.query();">查询</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="roleListForm.reset();">重置</a>
			</div>
			<table id="roleGrid" class="easyui-datagrid" style="width:auto;height:425px" title="角色列表">
			</table>
			</form>
		</div>
		<div id="gridToolbar" style="height:auto">
			<div style="margin-bottom:5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="roleListForm.addRole();">新增角色</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="roleListForm.updateRole();">修改角色</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="roleListForm.deleteRole();">删除角色</a>
			</div>
		</div>
	</div>
</div>
<form id="roleAddForm" action="${rc.contextPath}/system/role/roleAddForm" method="post"></form>
<form id="roleUpdForm" action="${rc.contextPath}/system/role/roleUpdForm" method="post"></form>
<form id="roleDelForm" action="${rc.contextPath}/system/role/deleteRole" method="post"></form>
</body>
</html>
