<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" media="all" type="text/css" href="${rc.contextPath}/css/jquery/operamasks-ui.min.css" />
	<link rel="stylesheet" media="all" type="text/css" href="${rc.contextPath}/css/jquery/main.css" />
	<script type="text/javascript" src="${rc.contextPath}/js/jquery_op/jquery-1.6.3.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/js/jquery_op/operamasks-ui.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/js/jquery_op/base/logger.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/js/jquery_op/base/base.js"></script>	
	<script type="text/javascript" src="${rc.contextPath}/js/jquery_op/base/map.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/js/jquery_op/base/tabMap.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		alert(1);
		  $('#file0').omFileUpload({
		    action : '${rc.contextPath}/file/upload2',
		    //swf : '../../../ui/om-fileupload.swf',
		    onComplete : function(event,ID,fileObj,response,data){
		    	//var jsonData = eval("("+response+")");
		    	//$('#response').html('已上传文件至此：<a target="_blank" href="'+jsonData.fileUrl+'">点击浏览</a>');
		    }
		  });
		});
	</script>
</head>
<body>
<%@include file="../pageloading.html" %>
	<input id="file0" name="file0" type="file"/>
	<!--<input id="file0" name="file0" type="file" />
    --><button value="上传" onclick="$('#file0').omFileUpload('upload')">上传</button>
    <div id="response" style="font-weight: bold;color: red;"></div>
</body>
</html>