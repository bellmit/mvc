<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="${rc.contextPath}/js/system/operator/operatorUpdForm.js"></script>
</head>
<body>
<%@include file="../../pageloading.html" %>
<div>
	<div class="m_cont">
		<div class="form_div">
			<form id="orgListForm" action="${rc.contextPath}/system/organization/organizationList" method="post"></form>
			<form id="roleListForm" action="${rc.contextPath}/system/role/roleList" method="post"></form>
			<form id="checkForm" action="${rc.contextPath}" method="post">
				<table class="inputTable">
					<thead>
						<tr>
							<td colspan="4"><span class="steps_1_on"></span>修改用户信息</td>
						</tr>
					</thead>
					<tr>
						<td class="title"><span class="txt-impt">*</span>&nbsp;姓名：</td>
						<td >
							<input name="operName" type="text" class="inp_text easyui-validatebox" required="true" />
						</td>
						<td class="title"><span class="txt-impt">*</span>&nbsp;联系电话：</td>
						<td >
							<input name="operPhone" type="text" class="inp_text easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td class="title"><span class="txt-impt">*</span>&nbsp;性别：</td>
						<td >
							<input name="operGender" type="radio"  style="vertical-align: middle;" value="0" <c:if test="${operator.operGender=='MAN'}">checked</c:if> onclick="operatorUpdForm.onclickGender()"/>男
							<input name="operGender" type="radio"  style="vertical-align: middle;" value="1" <c:if test="${operator.operGender=='WOMEN'}">checked</c:if> onclick="operatorUpdForm.onclickGender()"/>女
						</td>
						<td class="title"><span class="txt-impt">*</span>&nbsp;用户类型：</td>
						<td >
							<input name="operType" type="radio" value="0" <c:if test="${operator.operType=='MANAGER'}">checked</c:if> onclick="operatorUpdForm.onclickType()" />主管
							<input name="operType" type="radio" value="1" <c:if test="${operator.operType=='NORMAL'}">checked</c:if> onclick="operatorUpdForm.onclickType()"/>普通职员
						</td>
					</tr>
					<tr>
						<td class="title"><span class="txt-impt">*</span>&nbsp;用户职位：</td>
						<td >
							<select name="positionType" class="inp_text easyui-validatebox" required="true" >
			              	  	<c:forEach items="${positionTypeList}" var="positionType">
			              	  		<option value="${positionType[0]}">${positionType[1]}</option>
			              	  	</c:forEach>
			              	</select>
						</td>
						<td class="title"><span class="txt-impt">*</span>&nbsp;邮箱：</td>
						<td>
							<input name="operMail" type="text" class="inp_text easyui-validatebox" required="true" />
						</td>
						
					</tr>
				</table>
				
				<table class="inputTable">
					<thead>
						<tr><td><span class="steps_2_on"></span>修改用户机构</td></tr>
					</thead>
				</table>
				<table id="organizationGrid" class="easyui-treegrid"  style="height:300px;">
				</table>
				<table class="inputTable">
					<thead>
						<tr><td><span class="steps_3_on"></span>修改用户角色</td></tr>
					</thead>
				</table>
				<table id="roleGrid" class="easyui-datagrid"  style="height:300px;">
				</table>			
			</form>
			
			<div class="buttonArea">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="operatorUpdForm.submit();">提交</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="operatorUpdForm.reset();">重置</a>
			</div>
			
			<form id="operatorDetailForm" action="${rc.contextPath}/system/operator/getOperatorDetail" method="post">
				<input name="id" value="${id}" type="hidden"/>
			</form>
			
			<form id="submitForm" action="${rc.contextPath}/system/operator/upadteOperator" method="post">
				<input name="id" value="${id}" type="hidden"/>
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