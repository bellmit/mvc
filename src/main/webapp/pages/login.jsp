<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登录</title>
<link rel="stylesheet" media="all" type="text/css" href="${rc.contextPath}/css/themes/default/easyui.css" id="easyuiTheme"/>
<link rel="stylesheet" media="all" type="text/css" href="${rc.contextPath}/css/themes/icon.css"/>
<script type="text/javascript" src="${rc.contextPath}/js/jquery/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/jquery/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/jquery/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${rc.contextPath}/js/login.js"></script>
</head>
<body style="background-image: url('${rc.contextPath}/images/thdbg.jpg')">
<img style="position:absolute;left:0px;top:0px;width:100px;height:150px;cursor:hand;" src="${rc.contextPath}/images/thdbg.jpg" />
 <!-- <img style="position:absolute;left:50%;top:0px;width:50%;height:100%;cursor:hand;" src="${rc.contextPath}/images/thdbg.jpg" />-->
<div  class="m_cont" align="center" width="100%">
    <form id="loginForm" action="${rc.contextPath}/spring_security_login" method="post">
      	<table align="center">
			<tr>
				<td>用户:</td>
				<td><input type='text' name='j_username'></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type='password' name='j_password'></td>
			</tr>
		</table>
    </form>
    <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:login.submit();">提交</a>
 </div>
</body>
</html>
