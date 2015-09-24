<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<title>用户详细页面</title>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/operator/operatorDetailForm.js"></script>
</head>
<body>
<#include "/pageloading.html"/>
<div>
	<div class="m_cont">
		<div class="form_div">
			<form id="orgListForm" action="${rc.contextPath}/system/organization/organizationList" method="post"></form>
			<form id="roleListForm" action="${rc.contextPath}/system/role/roleList" method="post"></form>
			<form id="checkForm" action="${rc.contextPath}" method="post">				
				<table class="inputTable">
					<thead>
						<tr>
							<td colspan="4">用户信息</td>
						</tr>
					</thead>
					<tr>
						<td class="title">&nbsp;登录名：</td>							
						<td >
							${operator.operAccountNo!}
						</td>
						<td class="title">&nbsp;姓名：</td>
						<td >
							${operator.operName!}
						</td>
					</tr>
					<tr>
						<td class="title">&nbsp;性别：</td>
						<td >
							<#if operator.operGender=='MAN'>男<#else>女</#if>
						</td>
						<td class="title">&nbsp;用户类型：</td>
						<td >
							<#if operator.operType=='MANAGER'>主管<#else>普通职员</#if>
						</td>
					</tr>
					<tr>
						<td class="title">&nbsp;联系电话：</td>
						<td >
							${operator.operPhone!}
						</td>
						<td class="title">&nbsp;邮箱：</td>
						<td>
							${operator.operMail!}
						</td>
					</tr>
					<tr>
						<td class="title">&nbsp;所属机构：</td>							
						<td colspan="3">
							${orgsString!}
						</td>
					</tr>
					<tr>
						<td class="title">&nbsp;拥有角色：</td>							
						<td colspan="3">
							${roleString!}
						</td>
					</tr>
				</table>
				
			</form>
			
			<div class="buttonArea">
				<a href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="operatorDetailForm.closeTab();">关闭</a>
				
			</div>
			
			<form id="operatorDetailForm" action="${rc.contextPath}/system/operator/getOperatorDetail" method="post">
				<input name="id" value="${id!}" type="hidden"/>
			</form>
						
		</div>
	</div>
</div>
</body>
</html>
