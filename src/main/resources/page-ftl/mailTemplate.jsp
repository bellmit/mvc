<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="javascript"> 
	function changeColor(){ 
		var color="#f00|#0f0|#00f|#880|#808|#088|yellow|green|blue|gray"; 
		color=color.split("|"); 
		document.getElementById("blink").style.color=color[parseInt(Math.random() * color.length)]; 
	} 
	setInterval("changeColor()",200); 
</script> 
</head>
<body class="login_bg">
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
         <th colspan="2">${mailVo.mailSubject}</th>
       </tr>
       <tr>
         <th>发起人:</th>
         <td>${mailVo.mailSender}</td>
       </tr>
       <tr>
         <th>接收人:</th>
         <td>
         <c:forEach items="${mailVo.mailReceivers}" var="username">
         	<c:choose>
         		<c:when test="username_index==0">${username}</c:when>
         		<c:otherwise>、${username}</c:otherwise>
         	</c:choose>
       	 </c:forEach>
         </td>
       </tr>
       <tr>
         <th>案件编号:</th>
         <td>${mailVo.caseCode}</td>
       </tr>
       <tr>
         <th>描述:</th>
         <td><div id="blink">闪烁的文字</div>${mailVo.mailContents}</td>
       </tr>
       <c:if test="mailVo.mailHref">
       <tr>
         <th>处理连接:</th>
         <td><a href="${mailVo.mailHref!}">${mailVo.mailHref!}</a></td>
       </tr>
       </c:if>
    </table>
</body>

</html>
