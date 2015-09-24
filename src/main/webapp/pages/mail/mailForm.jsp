<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../head.jsp"/>
<script type="text/javascript" src="${rc.contextPath}/js/mail/mailForm.js"></script>
<script language="javascript"> 
	function changeColor(){ 
		var color="#f00|#0f0|#00f|#880|#808|#088|yellow|green|blue|gray"; 
		color=color.split("|"); 
		document.getElementById("blink").style.color=color[parseInt(Math.random() * color.length)]; 
	} 
	setInterval("changeColor()",200); 
</script> 
</head>
<body>
<%@include file="../pageloading.html" %>
<div class="maincont_div">
	<div class="m_cont">
		<div class="form_div">
			<form id="mail" action="${rc.contextPath}" method="post">
				<table align="center" width="90%">
					<tr>
						<td>邮件内容:</td>
						<td><textarea id="mailContents" name="mailContents" style="width: 95%"></textarea></td>
					</tr>
					<tr>
						<td>收件人:</td>
						<td><input id="mailReceiver" type="text" name="mailReceiver">
					</tr>
				</table>
			</form>
			<!--按钮部分开始-->
		  	<div class="buttonArea">
	 	 		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="mail.sendMail();">发送</a>
	 	 	</div>
	 	 	<div id="blink">闪烁的文字</div>
		</div>
	</div>
</div>
</body>
</html>