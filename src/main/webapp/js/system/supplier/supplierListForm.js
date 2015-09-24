$(document).ready(function() {
	supplierListForm.initGrid();
	$('#supplierGrid').resizeDataGrid();
	base.fitGrid('supplierGrid');//调节列表在页面中的自适应
	$('#orgDialogBtn').bind('click',function(){
		supplierListForm.orgDialogBtnClick();
	});
});


var supplierListForm={
	
	initGrid:function(){
		var queryForm = $('#querySupplierListForm');
		var param = base.decodeURI(queryForm.serialize());
		$('#supplierGrid').datagrid({
			url:queryForm.attr('action'),
			queryParams:base.strToJson(param),
			rownumbers:true,
			singleSelect:true,
			autoRowHeight:false,
			pagination:true,
			pageSize:base.pageSize,
			fitColumns:true,
			pageNumber:1,//带条件查询时，用于显示在第一页
			toolbar:'#gridToolbar',
			columns:[[
				{title:'编号',
				field:'supCode',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.supCode;
				}},
				{title:'供应商名称',
				field:'supName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.supName;
				}},
				{title:'所属机构',
				field:'orgText',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.orgText;
				}},			
				{title:'分类',
				field:'supClassify',
				width:200,
				formatter:function(value,rowData,rowIndex){
					return rowData.supClassify;
				}},
				{title:'地址',
				field:'supAddress',
				width:400,
				formatter:function(value,rowData,rowIndex){
					return rowData.supAddress;
				}},	
				{title:'联系电话',
				field:'supPhone',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.supPhone;
				}},	
				{title:'邮编',
				field:'supPostCode',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.supPostCode;
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
		supplierListForm.initGrid();
	},
	
	//跳转到新增供应商的页面
	addSupplier:function(){
		base.addTab('addSupplierForm', '供应商新增', $('#supplierAddForm').attr('action'), true);
	},
	
	//跳转到修改供应商的页面
	updateSupplier:function(){
		var row = $('#supplierGrid').datagrid('getSelected');
		if(!row){
			alert("请选择供应商！");
			return;
		}
		base.addTab('updSupplierForm', '供应商修改', $('#supplierUpdForm').attr('action')+"?id="+row.id, true);
	},
	
	//删除供应商
	deleteSupplier:function(){
		var row = $('#supplierGrid').datagrid('getSelected');
		if(!row){
			alert("请选择供应商！");
			return;
		}
		if(!confirm("确定要删除该供应商吗？")){
			return;
		}
		var supplierDelForm = $("#supplierDelForm");
		var url = supplierDelForm.attr("action");
		var param = {"id":row.id};
		$.post(url,param,function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			// 刷新列表
			supplierListForm.initGrid();
		});
	},
	//重置
	reset:function(){
		$("#querySupplierListForm")[0].reset();
		$("#orgId").val('');
	},
	
	//打开机构列表
	addOrg:function(){
		supplierListForm.initOrgGrid();
		$('#orgDialog').dialog('open').dialog('setTitle','机构列表');
	},
	
	orgDialogBtnClick:function(){
		var node = $('#orgGrid').treegrid("getSelected");
		if(!node){
			alert("请选择机构！");
			return;
		}
		$('#orgId').val(node.id);
		$('#orgText').val(node.obj.orgName);
		$('#orgDialog').dialog('close');
	},
	
	//机构列表初始化
	initOrgGrid:function(){
		var queryForm = $('#orgListForm');
		$('#orgGrid').treegrid({
			url:queryForm.attr("action"),
			idField:'id',
			treeField:'orgName',
			rownumbers:true,
			fitColumns:true,//列表自适应屏幕的宽度
			singleSelect:true,
			toolbar:'#orgGridToolbar',
			columns:[[
				{title:'机构名称',
				field:'orgName',
				width:250,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgName;
				}},
				{title:'机构代码',
				field:'orgCode',
				width:200,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgCode;
				}},
				{title:'机构说明',
				field:'orgMemo',
				width:600,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgMemo;
				}},
				{title:'机构地址',
				field:'orgAddress',
				width:350,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgAddress;
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