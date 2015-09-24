$(document).ready(function() {
	roleListForm.initGrid();
});
var roleListForm = {
	initGrid:function(){
		var queryForm = $('#queryRoleListForm');
		var param = base.decodeURI(queryForm.serialize());
		$('#roleGrid').datagrid({
			url:queryForm.attr('action'),
			queryParams:base.strToJson(param),
			rownumbers:true,
			singleSelect:true,
			autoRowHeight:false,
			pagination:true,
			pageSize:base.pageSize,
			fitColumns:true,
			toolbar:'#gridToolbar',
			columns:[[
				{title:'角色名称',
				field:'roleName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.roleName;
				}},
				{title:'角色描述',
				field:'roleMemo',
				width:600,
				formatter:function(value,rowData,rowIndex){
					return rowData.roleMemo;
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
	addRole:function(){
		base.addTab('addRoleForm', '角色新增', $('#roleAddForm').attr('action'), true);
	},
	updateRole:function(){
		var row = $('#roleGrid').datagrid('getSelected');
		if(!row){
			alert("请选择角色！");
			return;
		}
		base.addTab('updRoleForm', '角色修改', $('#roleUpdForm').attr('action')+"?id="+row.id, true);
	},
	deleteRole:function(){
		var row = $('#roleGrid').datagrid('getSelected');
		if(!row){
			alert("请选择角色！");
			return;
		}
		if(!confirm("确定要删除该角色吗？")){
			return;
		}
		var roleDelForm = $("#roleDelForm");
		var url = roleDelForm.attr("action");
		var param = {"id":row.id};
		$.post(url,param,function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			// 刷新列表
			roleListForm.initGrid();
		});
	},
	query:function(){
		roleListForm.initGrid();
	},
	reset:function(){
		$("#queryRoleListForm [name='roleName']").val('');
	}
}