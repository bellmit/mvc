<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="${rc.contextPath}/js/system/function/functionListForm.js"></script>
</head>
<body>
<%@include file="../../pageloading.html" %>
<div class="maincont_div">
	<div class="m_cont">
	
		<div class="form_div">
			<table id="funGrid" class="easyui-treegrid" style="width:auto;height:450px" title="权限列表"></table>
		</div>
			
	</div>
</div>
<form id="funListForm" action="${rc.contextPath}/system/function/functionList" method="post"></form>
</body>
</html>