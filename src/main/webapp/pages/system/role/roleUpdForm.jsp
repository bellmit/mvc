<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="${rc.contextPath}/js/system/role/roleUpdForm.js"></script>
</head>
<body>
<%@include file="../../pageloading.html" %>
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
				<input name="id" value="${id}" type="hidden"/>
			</form>
			<form id="submitForm" action="${rc.contextPath}/system/role/updateRole" method="post">
				<input name="id" value="${id}" type="hidden"/>
				<input name="roleName" value="" type="hidden"/>
				<input name="roleMemo" value="" type="hidden"/>
			</form>
		</div>
	</div>
</div>
</body>
</html>