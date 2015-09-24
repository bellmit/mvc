<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="${rc.contextPath}/js/base/login/main.js"></script>
</head>
<body>
	 <div id="main_layout" class="easyui-layout" style="width:100%;height:100%">
		 <!--中部开始--> 
		 <div data-options="region:'center'" style="width:100%;height:100%">
		 	<div id="main-tabs" class="easyui-tabs" data-options="fit:true,border:false,tools:'#main-tabs-tools'">
		 		<div title="首页" style="padding:10px;">
		 			<div class="waiwei">
		 				<div class="biaoti_1">
		 					<div class="biaoti_2">我的工作</div>
		 				</div>
		 			</div>
		 			<div class="xuanxiang_1">
		 				<div class="liebiao_1">
		 					<a href="#"><img src="${rc.contextPath}/images/btn01.jpg" />我负责的未处理投诉任务</a>
		 					<a href="#"><img src="${rc.contextPath}/images/btn04.jpg" />我经办的投诉</a>
		 					<a href="#"><img src="${rc.contextPath}/images/btn02.jpg" />抄送给我的投诉</a>
		 					<a href="#"><img src="${rc.contextPath}/images/btn05.jpg" />被退回的投诉</a>
		 					<a href="#"><img src="${rc.contextPath}/images/btn03.jpg" />我关注的案件</a>
		 				</div>
		 			</div>
		 		</div>
		 	</div>
		 	<div id="main-tabs-tools">
		 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="main.addLayout()"></a>
		 		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="main.removeLayout()"></a>
		 	</div>
		 </div> 
		 <!--中部结束--> 
	 </div> 
	 <form id="contextPathForm" action="${rc.contextPath}" method="post"></form>
</body>
</html>