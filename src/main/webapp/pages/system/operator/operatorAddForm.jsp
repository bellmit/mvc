<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="${rc.contextPath}/js/system/operator/operatorAddForm.js"></script>
</head>
<body>
<%@include file="../../pageloading.html" %>
<div>
	<div class="m_cont">
		<div class="form_div">
			<form id="roleAddForm" action="${rc.contextPath}/system/role/roleAddForm" method="post"></form>
			<form id="orgListForm" action="${rc.contextPath}/system/organization/organizationList" method="post"></form>
			<form id="roleListForm" action="${rc.contextPath}/system/role/roleList" method="post"></form>
			<form id="checkForm" action="${rc.contextPath}" method="post">
			<table class="inputTable">
				<thead>
					<tr>
						<td colspan="4"><span class="steps_1_on"></span>填写账号信息</td>
					</tr>
				</thead>
				<tr>
					<td class="title"><span class="txt-impt">*</span>&nbsp;登录账号：</td>
					<td >
						<input name="operAccountNo" type="text" class="inp_text easyui-validatebox" required="true"/>
					</td>
					<td class="title"><span class="txt-impt">*</span>&nbsp;密码：</td>
					<td >
						<input name="operPassword" type="password" class="inp_text easyui-validatebox" required="true"/>
					</td>
				</tr>
			</table>
			
			<table class="inputTable">
				<thead>
					<tr>
						<td colspan="4"><span class="steps_2_on"></span>填写用户信息</td>
					</tr>
				</thead>
				<tr>
					<td class="title"><span class="txt-impt">*</span>&nbsp;姓名：</td>
					<td >
						<input name="operName" type="text" class="inp_text easyui-validatebox" required="true"/>
					</td>
					<td class="title"><span class="txt-impt">*</span>&nbsp;联系电话：</td>
					<td >
						<input name="operPhone" type="text" class="inp_text easyui-validatebox" required="true"/>
					</td>
				</tr>
				<tr>
					<td class="title"><span class="txt-impt">*</span>&nbsp;性别：</td>
					<td >
						<input name="operGender" type="radio"  style="vertical-align: middle;" checked value="0" onclick="operatorAddForm.onclickGender()"/>男
						<input name="operGender" type="radio"  style="vertical-align: middle;" value="1" onclick="operatorAddForm.onclickGender()"/>女
					</td>
					<td class="title"><span class="txt-impt">*</span>&nbsp;用户类型：</td>
					<td >
						<input name="operType" type="radio"  value="0" onclick="operatorAddForm.onclickType()" />主管
						<input name="operType" type="radio"  checked value="1" onclick="operatorAddForm.onclickType()"/>普通职员
					</td>
				</tr>
				<tr>
					<td class="title"><span class="txt-impt">*</span>&nbsp;用户职位：</td>
					<td >
						<select name="positionType" class="inp_text easyui-validatebox" required="true">
					  		<option value="">--请选择--</option>
					  		<c:forEach items="${positionTypeList}" var="positionType">
			              	  <option value="${positionType[0]}">${positionType[1]}</option>
			              	</c:forEach>
		              	</select>
					</td>
					<td class="title"><span class="txt-impt">*</span>&nbsp;邮箱：</td>
					<td >
						<input name="operMail" type="text" class="inp_text easyui-validatebox" data-options="required:true,validType:'email'"/>
					</td>
					
				</tr>
			</table>
			
			<table class="inputTable">
				<thead>
					<tr><td><span class="steps_3_on"></span>选择用户机构</td></tr>
				</thead>
			</table>
			<table id="organizationGrid" class="easyui-treegrid"  style="height:300px;">
			</table>
			<table class="inputTable">
				<thead>
					<tr><td><span class="steps_4_on"></span>选择用户角色</td></tr>
				</thead>
			</table>
			<table id="roleGrid" class="easyui-datagrid"  style="height:300px;">
			</table>
			
			</form>
			<div class="buttonArea">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="operatorAddForm.submit();">提交</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="operatorAddForm.reset();">重置</a>
			</div>
			<form id="submitForm" action="${rc.contextPath}/system/operator/saveOperator" method="post">
				<input name="operAccountNo" value="" type="hidden"/>
				<input name="operPassword" value="" type="hidden"/>
				<input name="operName" value="" type="hidden"/>
				<input name="operPhone" value="" type="hidden"/>
				<input name="operGender" value="" type="hidden"/>
				<input name="operType" value="" type="hidden"/>
				<input name="operMail" value="" type="hidden"/>
				<input name="positionType" value="" type="hidden"/>
			</form>
		</div>
	</div>
</div>
</body>
</html>