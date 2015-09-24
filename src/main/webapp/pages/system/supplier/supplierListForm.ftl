<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#assign selector=JspTaglibs["/WEB-INF/taglib/showParamSelector.tld"]/>
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<title>供应商维护页面</title>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/supplier/supplierListForm.js"></script>
</head>
<body>
<#include "/pageloading.html"/>
<div class="maincont_div">
	<div class="m_cont">
		<div class="form_div">
			<form id="querySupplierListForm" action="${rc.contextPath}/supplier/supplierList" method="post">
				<table class="queryTable">
					<thead>
						<tr>
							<td colspan="4">填写查询信息</td>
						</tr>
					</thead>
					<tr>
						<td class="title">供应商编号：</td>
						<td >
							<input name="supCode" type="text" class="inp_text easyui-validatebox"/>
						</td>
						<td class="title">供应商名称：</td>
						<td >
							<input name="supName" type="text" class="inp_text easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td class="title">供应商分类：</td>
						<td >
							<@selector.option itemId="supClassify" itemName="supClassify" required=true paramType=6/>
						</td>
						<td class="title">供应商地址：</td>
						<td >
							<input name="supAddress" type="text" class="inp_text easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td class="title">机构名称：</td>
						<td colspan="3">
							<input id="orgText" class="inp_text validatebox-text" readonly="readonly"/>
							<a id="" class="l-btn l-btn-plain" href="javascript:void(0);" onclick="supplierListForm.addOrg();">
								<span class="l-btn-left">
									<span class="l-btn-text icon-search l-btn-icon-left">选择</span>
								</span>
							</a>
							<input id="orgId" name="orgId" type="hidden">
						</td>
					</tr>
				</table>
				<div class="buttonArea">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="supplierListForm.query();">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="supplierListForm.reset();">重置</a>
				</div>
				<table id="supplierGrid"  style="width:auto;height:425px" title="供应商列表">
				</table>
			</form>
		</div>
		<div id="gridToolbar" style="height:auto">
			<div style="margin-bottom:5px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="supplierListForm.addSupplier()">新增供应商</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="supplierListForm.updateSupplier()">修改供应商</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="supplierListForm.deleteSupplier()">删除供应商</a>
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

<form id="supplierAddForm" action="${rc.contextPath}/supplier/supplierAddForm" method="post"></form>
<form id="supplierUpdForm" action="${rc.contextPath}/supplier/supplierUpdForm" method="post"></form>
<form id="supplierDelForm" action="${rc.contextPath}/supplier/deleteSupplier" method="post"></form>

</body>
</html>
