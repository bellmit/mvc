<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="${rc.contextPath}/js/sample/grid/gridList.js"></script>
</head>
<body>
<%@include file="../../pageloading.html" %>
<div class="maincont_div">
	<div class="m_cont">
	
		<!-- 标题开始 -->
		<div class="tit_box2"><h2 class="tit_icon_dot">列表</h2></div>
		<!-- 标题结束 -->
	
		<div class="form_div">
			<table id="tt" class="easyui-datagrid" style="width:auto;height:390px" title="翻页列表" iconCls="icon-save">
				<thead>
					<tr>
						<th field="itemid" width="80" sortable="true">Item ID</th>
						<th field="productid" width="100" sortable="true">Product ID</th>
						<th field="listprice" width="80" align="right" sortable="true">List Price</th>
						<th field="unitcost" width="80" align="right" sortable="true">Unit Cost</th>
						<th field="attr1" width="220">Attribute</th>
						<th field="status" width="60" align="center">Stauts</th>
					</tr>
				</thead>
			</table>
		</div>
			
	</div>
</div>
<form id="sampleForm" action="${rc.contextPath}/sample/gridData1" method="post"></form>
</body>
</html>