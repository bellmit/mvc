<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="${rc.contextPath}/js/system/role/roleListForm.js"></script>
</head>
<body>
<%@include file="../../pageloading.html" %>
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