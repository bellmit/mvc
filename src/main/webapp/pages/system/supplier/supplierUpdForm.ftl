<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#assign selector=JspTaglibs["/WEB-INF/taglib/showParamSelector.tld"]/>
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<title>供应商管理页面</title>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/supplier/supplierUpdForm.js"></script>
</head>
<body>
<#include "/pageloading.html"/>
<div>
	<div class="m_cont">
		<div class="form_div">
			<form id="orgListForm" action="${rc.contextPath}/system/organization/organizationList" method="post"></form>
			<form id="checkForm" action="${rc.contextPath}/supplier/updateSupplier" method="post">
				<table class="inputTable">
					<thead>
						<tr>
							<td colspan="4"><span class="steps_1_on"></span>供应商信息</td>
						</tr>
					</thead>
					<tr>
						<td class="title"><span class="txt-impt">*</span>&nbsp;供应商编号：</td>
						<td >
							<input name="supCode" type="text" class="inp_text easyui-validatebox" required="true" value="${supplier.supCode!}"/>
						</td>
						<td class="title"><span class="txt-impt">*</span>&nbsp;供应商分类：</td>
						<td >
			              	<@selector.option itemId="supClassify" itemName="supClassify" required=true paramType=6 selectedValue="${supplier.supClassify!}"/>
						</td>
					</tr>
					<tr>
						<td class="title"><span class="txt-impt">*</span>&nbsp;供应商名称：</td>
						<td >
							<input name="supName" type="text" class="inp_text easyui-validatebox" required="true" value="${supplier.supName!}"/>
						</td>
						<td class="title"><span class="txt-impt">*</span>&nbsp;供应商地址：</td>
						<td >
							<input name="supAddress" type="text" class="inp_text easyui-validatebox" data-options="required:true" value="${supplier.supAddress!}"/>
						</td>
					</tr>
					<tr>
						<td class="title"><span class="txt-impt">*</span>&nbsp;联系电话：</td>
						<td >
							<input name="supPhone" type="text" class="inp_text easyui-validatebox" required="true" value="${supplier.supPhone!}"/>
						</td>
						<td class="title"><span class="txt-impt">*</span>&nbsp;供应商邮编：</td>
						<td >
							<input name="supPostCode" type="text" class="inp_text easyui-validatebox" data-options="required:true" value="${supplier.supPostCode!}"/>
						</td>
					</tr>
				</table>
				<input  id="organizationId" value="${supplier.orgId!}" type="hidden"/>
				<input  name="id" value="${supplier.id!}" type="hidden"/>
				<input  name="orgId" value="" type="hidden"/>
				<table class="inputTable">
					<thead>
						<tr><td><span class="steps_3_on"></span>选择所属机构</td></tr>
					</thead>
				</table>
				<table id="organizationGrid"   style="height:300px;">
				</table>
				
			</form>
			<div class="buttonArea">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="supplierUpdForm.updateSupplier();">提交</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="supplierUpdForm.reset();">重置</a>
			</div>
			<form id="orgIdForm" action="${rc.contextPath}/supplier/getOrgIdBySupId?orgId=${supplier.orgId}" method="post"></form>
		</div>
	</div>
</div>
</body>
</html>