<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<title>用户管理页面</title>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/operator/operatorListForm.js"></script>
</head>
<body>
<#include "/pageloading.html"/>
<div class="maincont_div">
	<div class="m_cont">
		<div class="form_div">
			<form id="queryOperatorListForm" action="${rc.contextPath}/system/operator/operatorList" method="post">
				<table class="queryTable">
					<thead>
						<tr>
							<td colspan="4">填写查询信息</td>
						</tr>
					</thead>
					<tr>
						<td class="title">用户登录名：</td>
						<td >
							<input name="operAccountNo" type="text" class="inp_text easyui-validatebox"/>
						</td>
						<td class="title">姓名：</td>
						<td >
							<input name="operName" type="text" class="inp_text easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td class="title">用户类型：</td>
						<td >
							<select  name="operType" type="" class="inp_text easyui-validatebox"/>
							<option value="">--请选择--</option>
							<option value="MANAGER">主管</option>
							<option value="NORMAL">普通员工</option>
							</select>
						</td>
						<td class="title">邮箱：</td>
						<td >
							<input name="operMail" type="text" class="inp_text easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td class="title">机构名称：</td>
						<td colspan="3">
							<input id="orgText" class="inp_text validatebox-text" readonly="readonly"/>
							<a id="" class="l-btn l-btn-plain" href="javascript:void(0);" onclick="operatorListForm.addOrg();">
								<span class="l-btn-left">
									<span class="l-btn-text icon-search l-btn-icon-left">选择</span>
								</span>
							</a>
							<input id="orgId" name="orgId" type="hidden">
						</td>
					</tr>
				</table>
				<div class="buttonArea">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="operatorListForm.query();">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="operatorListForm.reset();">重置</a>
				</div>
				<table id="operatorGrid"  style="width:auto;height:425px" title="用户列表">
				</table>
			</form>
		</div>
		<div id="gridToolbar" style="height:auto">
			<div style="margin-bottom:5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="operatorListForm.addOperator()">新增用户</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="operatorListForm.updateOperator()">修改用户</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="operatorListForm.deleteOperator()">删除用户</a>
			</div>
		</div>
		<!--机构信息部分开始-->
		<div id="orgDialog" class="easyui-dialog" style="width:1000px;height:500px;padding:10px 20px" closed="true" buttons="#orgDialog-buttons">
			<table id="orgGrid"  style="width:auto;height:390px" title="机构列表"></table>
			<div id="orgGridToolbar">
				<div>
					<a id="orgDialogBtn" href="#" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="">确定</a>
				</div>
			</div>
		</div>
		<form id="orgListForm" action="${rc.contextPath}/common/organizationList" method="post"></form>
		<!--机构信息部分结束-->
	</div>
</div>

<form id="operatorAddForm" action="${rc.contextPath}/system/operator/operatorAddForm" method="post"></form>
<form id="operatorUpdForm" action="${rc.contextPath}/system/operator/operatorUpdForm" method="post"></form>
<form id="operatorDelForm" action="${rc.contextPath}/system/operator/deleteOperator" method="post"></form>
<form id="operatorDetailForm" action="${rc.contextPath}/system/operator/operatorDetailForm" method="post"></form>
</body>
</html>
