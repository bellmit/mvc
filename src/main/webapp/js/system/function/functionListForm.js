$(document).ready(function() {
	functionListForm.initTreeGrid();
});
var functionListForm = {
	initTreeGrid:function(){
		var queryForm = $('#funListForm');
		$('#funGrid').treegrid({
			url:queryForm.attr("action"),
			idField:'id',
			treeField:'funName',
			rownumbers:true,
			fitColumns:true,//列表自适应屏幕的宽度
			singleSelect:true,
			columns:[[
				{title:'功能名称',
				field:'funName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.funName;
				}},
				{title:'功能描述',
				field:'funMemo',
				width:500,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.funMemo;
				}},
				{title:'功能URL',
				field:'funUrl',
				width:350,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.funUrl;
				}},
				{title:'功能排序',
				field:'funOrder',
				width:100,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.funOrder;
				}}
			]],
			onLoadSuccess:function(data){
				base.ajaxSuccess(data);
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});	
	}
}