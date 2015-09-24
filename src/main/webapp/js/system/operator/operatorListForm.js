$(document).ready(function() {
	operatorListForm.initGrid();
	
});

var operatorListForm={

	initGrid:function(){
		var queryForm = $('#queryOperatorListForm');
		var param = base.decodeURI(queryForm.serialize());
		$('#operatorGrid').datagrid({
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
				{title:'登录名',
				field:'operAccountNo',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.operAccountNo;
				}},
				{title:'姓名',
				field:'operName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.operName;
				}},
				{title:'姓别',
				field:'operGender',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.operGenderText;
				}},	
				{title:'用户类型',
				field:'operType',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.operTypeText;
				}},	
				{title:'用户职位',
				field:'positionType',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.positionTypeText;
				}},	
				{title:'联系电话',
				field:'operPhone',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.operPhone;
				}},			
				{title:'邮箱',
				field:'operMail',
				width:600,
				formatter:function(value,rowData,rowIndex){
					return rowData.operMail;
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
	//查询列表
	query:function(){
		operatorListForm.initGrid();
	},
	//跳转到新增用户
	addOperator:function(){
		base.addTab('addOperatorForm', '用户新增', $('#operatorAddForm').attr('action'), true);
	},
	
	//修改用户
	updateOperator:function(){
		var row = $('#operatorGrid').datagrid('getSelected');
		if(!row){
			alert("请选择用户！");
			return;
		}
		base.addTab('updOperatorForm', '用户修改', $('#operatorUpdForm').attr('action')+"?id="+row.id, true);
	},
	//删除用户
	deleteOperator:function(){
		var row = $('#operatorGrid').datagrid('getSelected');
		if(!row){
			alert("请选择用户！");
			return;
		}
		if(!confirm("确定要删除该用户吗？")){
			return;
		}
		var operatorDelForm = $("#operatorDelForm");
		var url = operatorDelForm.attr("action");
		var param = {"id":row.id};
		$.post(url,param,function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			// 刷新列表
			operatorListForm.initGrid();
		});
	},
	reset:function(){
		$("#queryOperatorListForm")[0].reset();
	}
	
}

