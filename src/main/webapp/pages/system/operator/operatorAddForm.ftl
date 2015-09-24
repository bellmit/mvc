<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<title>用户管理页面</title>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/operator/operatorAddForm.js"></script>
</head>
<body>
<#include "/pageloading.html"/>
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
						<input name="operPhone" type="text" class="inp_text easyui-validatebox" data-options="required:true,validType:'phone'"/>
					</td>
				</tr>
				<tr>
					<td class="title"><span class="txt-impt">*</span>&nbsp;性别：</td>
					<td >
						<input name="operGender" type="radio"  checked value="0" onclick="operatorAddForm.onclickGender()"/>男
						<input name="operGender" type="radio"  value="1" onclick="operatorAddForm.onclickGender()"/>女
					</td>
					<td class="title"><span class="txt-impt">*</span>&nbsp;用户类型：</td>
					<td >
						<input name="operType" type="radio"  value="0" onclick="operatorAddForm.onclickType()" />主管
						<input name="operType" type="radio"  checked value="1" onclick="operatorAddForm.onclickType()"/>普通职员
					</td>
				</tr>
				<tr>
					<td class="title"><span class="txt-impt">*</span>&nbsp;邮箱：</td>
					<td colspan="3">
						<input name="operMail" type="text" class="inp_text easyui-validatebox" data-options="required:true,validType:'email'"/>
					</td>
				</tr>
			</table>
			
			<table class="inputTable">
				<thead>
					<tr><td><span class="steps_3_on"></span>选择用户机构</td></tr>
				</thead>
			</table>
			<table id="organizationGrid"  style="height:300px;">
			</table>
			<table class="inputTable">
				<thead>
					<tr><td><span class="steps_4_on"></span>选择用户角色</td></tr>
				</thead>
			</table>
			<table id="roleGrid"  style="height:300px;">
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