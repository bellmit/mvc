<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<head>
<@headMacro.headMacro/>
<script type="text/javascript" src="${rc.contextPath}/js/sample/grid/gridList.js"></script>
</head>
<body>
<div class="maincont_div">
	<div class="m_cont">
	
		<!-- 标题开始 -->
		<div class="tit_box2"><h2 class="tit_icon_dot">列表</h2></div>
		<!-- 标题结束 -->
	
		<div class="form_div">
			<table id="tt" class="easyui-datagrid" style="width:auto;height:390px" title="翻页列表" iconCls="icon-save">
				<thead>
					<tr>
						<th field="itemid" width="80" sortable="true">Item ID</th>
						<th field="productid" width="100" sortable="true">Product ID</th>
						<th field="listprice" width="80" align="right" sortable="true">List Price</th>
						<th field="unitcost" width="80" align="right" sortable="true">Unit Cost</th>
						<th field="attr1" width="220">Attribute</th>
						<th field="status" width="60" align="center">Stauts</th>
					</tr>
				</thead>
			</table>
		</div>
			
	</div>
</div>
<form id="sampleForm" action="${rc.contextPath}/sample/gridData1" method="post"></form>
</body>
</html>