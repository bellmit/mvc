<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<title>机构管理页面</title>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/organization/organizationListForm.js"></script>
</head>
<body>
<#include "/pageloading.html"/>
<div class="maincont_div">
	<div class="m_cont">

		<div class="form_div">
			<table id="orgGrid" class="easyui-treegrid" style="width:auto;height:390px" title="机构列表"></table>
			<div id="gridToolbar" style="padding:5px;height:auto">
				<div style="margin-bottom:5px">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="organizationListForm.addOrg();">新增机构</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="organizationListForm.updOrg();">修改机构</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="organizationListForm.delOrg();">删除机构</a>
				</div>
			</div>
			<div id="orgDialog" class="easyui-dialog" style="width:600px;height:350px;padding:10px 20px" closed="true" buttons="#orgDialog-buttons">
				<div class="ftitle">机构信息</div>
				<div class="form_div">
					<form id="orgSubmitForm" action="${rc.contextPath}" method="post">
					<input id="parentId" name="parentOrganization.id" value="" type="hidden"/>
					<input name="id" value="" type="hidden"/>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<th>所属机构：</th>
							<td id="parentOrgName"></td>
						</tr>
						<tr>
							<th><span class="txt-impt">*</span>&nbsp;机构名称：</th>
							<td>
								<input name="orgName" class="easyui-validatebox" required="true"/>
							</td>
						</tr>
						<tr>
							<th>机构地址：</th>
							<td>
								<input name="orgAddress" class="easyui-validatebox"/>
							</td>
						</tr>
						<tr>
							<th>机构描述：</th>
							<td>
								<textarea name="orgMemo" class="easyui-validatebox" style="width:96%"></textarea>
							</td>
						</tr>
					</table>
					</form>
				</div>
			</div>
			<div id="orgDialog-buttons">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="organizationListForm.dialogSave();">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="organizationListForm.dialogCancel();">取消</a>
			</div>
		</div>
			
	</div>
</div>
<form id="orgListForm" action="${rc.contextPath}/system/organization/organizationList" method="post"/>
</body>
</html>
