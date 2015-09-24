<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="${rc.contextPath}/js/system/operator/operatorListForm.js"></script>
</head>
<body>
<%@include file="../../pageloading.html" %>
<div class="maincont_div">
	<div class="m_cont">
		<div class="form_div">
			<form id="queryOperatorListForm" action="${rc.contextPath}/system/operator/operatorList" method="post">
				<table class="queryTable">
					<thead>
						<tr>
							<td colspan="4">填写查询信息</td>
						</tr>
					</thead>
					<tr>
						<td class="title">用户登录名：</td>
						<td >
							<input name="operAccountNo" type="text" class="inp_text easyui-validatebox"/>
						</td>
						<td class="title">姓名：</td>
						<td >
							<input name="operName" type="text" class="inp_text easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td class="title">用户类型：</td>
						<td >
							<select  name="operType" type="" class="inp_text easyui-validatebox"/>
							<option value="">--请选择--</option>
							<option value="MANAGER">主管</option>
							<option value="NORMAL">普通员工</option>
							</select>
						</td>
						<td class="title">邮箱：</td>
						<td >
							<input name="operMail" type="text" class="inp_text easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td class="title">用户职位：</td>
						<td colspan="3">
							<select  name="positionType" class="inp_text easyui-validatebox"/>
								<option value="">--请选择--</option>
								<c:forEach items="${positionTypeList}" var="positionType">
			              	  		<option value="${positionType[0]}">${positionType[1]}</option>
			              	  	</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<div class="buttonArea">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="operatorListForm.query();">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="operatorListForm.reset();">重置</a>
				</div>
				<table id="operatorGrid" class="easyui-datagrid" style="width:auto;height:425px" title="用户列表">
				</table>
			</form>
		</div>
		<div id="gridToolbar" style="height:auto">
			<div style="margin-bottom:5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="operatorListForm.addOperator()">新增用户</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="operatorListForm.updateOperator()">修改用户</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="operatorListForm.deleteOperator()">删除用户</a>
			</div>
		</div>
	</div>
</div>
<form id="operatorAddForm" action="${rc.contextPath}/system/operator/operatorAddForm" method="post"></form>
<form id="operatorUpdForm" action="${rc.contextPath}/system/operator/operatorUpdForm" method="post"></form>
<form id="operatorDelForm" action="${rc.contextPath}/system/operator/deleteOperator" method="post"></form>
</body>
</html>