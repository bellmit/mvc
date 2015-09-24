/**
 * 投诉受理js
 * @author zt
 */
 
 $(document).ready(function() {
	acceptance.initGrid();
 	$('#complaintGrid').resizeDataGrid();
	base.fitGrid('complaintGrid');
});

var acceptance = {
	/**
	 * 案件列表初始化
	 * */
	initGrid:function(){
		var queryForm = $('#acceptanceListForm');
		var param = base.decodeURI(queryForm.serialize());
		$('#complaintGrid').datagrid({
			url:queryForm.attr('action'),
			queryParams:base.strToJson(param),
			rownumbers:true,
			singleSelect:true,
			autoRowHeight:true,
			pagination:true,
			pageSize:base.pageSize,
			fitColumns:true,
			pageNumber:1,
			remoteSort:true,// 设置到服务器端排序
			toolbar:'#gridToolbar',
			columns:[[
				{title:'投诉人',
				field:'cplName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.cplName;
				}},
				{title:'受理人',
				field:'acceptOper',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.acceptOper;
				}},
				{title:'受理时间',
				field:'acceptTime',
				width:250,
				sortable:true,// 设置可以排序
				formatter:function(value,rowData,rowIndex){
					return rowData.acceptTime;
				}}		
			]],
			
			onLoadSuccess:function(data){
				base.ajaxSuccess(data);
			}, 
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
			
		});
	},	
	/**
	 * 查询按钮事件
	 * */
	query:function(){
		acceptance.initGrid();
	},
	/**
	 * 重置按钮事件
	 * */
	reset:function(){
		$('#cplName').val('');
		$('#acceptOper').val('');
		$('#beginDate').datebox('setValue','');
		$('#endDate').datebox('setValue','');
	},
	/**
	 * 新增案件按钮事件
	 * */
	addAcceptanceForm:function(){
		base.addTab('complaintCaseAddForm', '案件新增', $('#complaintCaseAddForm').attr('action'), true);
	},		
	/**
	 * 修改暂存案件按钮事件
	 * */
	upComplaintCase:function(){
		var row = $('#complaintGrid').datagrid('getSelected');
		if(!row){
			alert("请选择案件！");
			return;
		}
		base.addTab('complaintCaseUpdForm', '暂存案件修改', $('#complaintCaseUpdForm').attr('action')+"?id="+row.id, true);
	}
}