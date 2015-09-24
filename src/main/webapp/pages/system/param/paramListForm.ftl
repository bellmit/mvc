<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<title>参数管理页面</title>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/system/param/paramListForm.js"></script>
</head>
<body>
<#include "/pageloading.html"/>
<div class="maincont_div">
	<div class="m_cont">
		<div class="form_div">
		<form id="paramListForm" action="${rc.contextPath}/param/paramList" method="post"/>
			<table class="queryTable">
				<thead>
					<tr>
						<td colspan="4">选择参数类型</td>
					</tr>
				</thead>
				<tr>
					<td class="title">参数类型：</td>
					<td >
						<select  name="type" type="" class="inp_text easyui-validatebox"/>
							<#list typeList as type>
		              	  		<option value="${type[0]}"<#if type[0]="COMPLAINT_INSURANCE">selected</#if> >${type[1]}</option>
		              	  	</#list>
						</select>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="paramListForm.query();">查询</a>
			</div>
			<table id="paramGrid"  style="width:auto;height:390px" title="参数列表"></table>
		</form>
		
			
			<div id="gridToolbar" style="padding:5px;height:auto">
				<div style="margin-bottom:5px">
					<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="paramListForm.addParam();">新增参数</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="paramListForm.updParam();">修改参数</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="paramListForm.delParam();">删除参数</a>
				</div>
			</div>
			<div id="paramDialog" class="easyui-dialog" style="width:600px;height:350px;padding:10px 20px" closed="true" buttons="#paramDialog-buttons">
				<div class="ftitle">参数信息</div>
				<div class="form_div">
					<form id="paramSubmitForm" action="${rc.contextPath}" method="post">
					<input id="parentId" name="parentParam.id" value="" type="hidden"/>
					<input name="id" value="" type="hidden"/>
					
					<input id="typeId" name="type" value="" type="hidden"/>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tr>
							<th>父节点参数：</th>
							<td id="parentParamName"></td>
						</tr>
						<tr>
							<th>参数类型：</th>
							<td id="typeName"></td>
						</tr>
						
						<tr id="showText" style= "display:none">
							<th><span class="txt-impt">*</span>系统参数显示：</th>
							<td>
								<input name="text" class="easyui-validatebox" required="true"/>
							</td>
						</tr>
						<tr id="showValue" style= "display:block">
							<th><span class="txt-impt">*</span>&nbsp;系统参数值：</th>
							<td>
								<input id="value" name="value" class="easyui-validatebox"  required="true"/>
							</td>
						</tr>
						<tr>
							<th><span class="txt-impt">*</span>系统参数排序：</th>
							<td>
								<input name="sortNo" class="easyui-validatebox" data-options="required:true,validType:'number'"/>
							</td>
						</tr>
					</table>
					</form>
				</div>
			</div>
			<div id="paramDialog-buttons">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="paramListForm.dialogSave();">保存</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="paramListForm.dialogCancel();">取消</a>
			</div>
		</div>
			
	</div>
</div>

</body>
</html>
