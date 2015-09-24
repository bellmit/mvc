$(document).ready(function() {
	supplierUpdForm.initOrgTreeGrid();
	$('#organizationGrid').resizeDataGrid();
	base.fitGrid('organizationGrid');//调节列表在页面中的自适应
});

var supplierUpdForm={
	functionMap:[],
	//机构列表的初始化
	initOrgTreeGrid:function(){
		
		supplierUpdForm.functionMap = new TabMap();//初始化
		
		var queryForm = $('#orgListForm');
		$('#organizationGrid').treegrid({
			title:'机构列表',
			url:queryForm.attr("action"),
			idField:'id',
			treeField:'orgName',
			rownumbers:true,
			fitColumns:true,
			singleSelect:true,//设置成单选
			collapsible:true,//设置列表可收缩，同时要加个title属性
			columns:[[
				{title:'选择',
				field:'id',
				formatter:function(value,rowData,rowIndex){
					return '<input id="'+rowData.id+'" type="checkbox" onclick="supplierUpdForm.onchecked(this.checked,this.id);" />';
				}},
				{title:'机构名称',
				field:'orgName',
				width:200,
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
				width:200,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgAddress;
				}}
			]],
			onLoadSuccess:function(data){
				base.ajaxSuccess(data);
				var organizationId=$("#organizationId").val();
//				$('#organizationGrid').treegrid("select",organizationId);//回显选中机构id
				$("#"+organizationId).attr("checked","checked");
				supplierUpdForm.functionMap.put(organizationId,organizationId);
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});	
	},
	
	//选中的机构
	onchecked:function(value,id){
		if(value){
			supplierUpdForm.functionMap.put(id,id);
		} else {
			supplierUpdForm.functionMap.remove(id);
		}
	},
	
	//修改供应商---提交方法
	updateSupplier:function(){
		var checkForm = $("#checkForm");
		if(!checkForm.form('validate')){
			return;
		}
//		var node = $('#organizationGrid').treegrid("getSelected");
//		if(!node){
//			alert("请选择机构！");
//			return;
//		}
//		$("#checkForm [name='orgId']").val(node.id);

		if(supplierUpdForm.functionMap.size() == 0){
			alert("请选择一个机构！");
			return;
		}
		if(supplierUpdForm.functionMap.size() > 1){
			alert("只能选择一个机构！");
			return;
		}
		var inputValue ="";
		$.each(supplierUpdForm.functionMap.keys(),function(i,key){
			 inputValue = key;
		});
		$("#checkForm [name='orgId']").val(inputValue);
		
		$.post(checkForm.attr("action"), checkForm.serialize(), function(data){
			
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			base.reloadTab('supplierListForm');//刷新列表页签
			base.closeTab('updSupplierForm');//关闭修改页签
		});
		
	},
	//重置
	reset:function(){
		$("#checkForm")[0].reset();
		supplierUpdForm.initOrgTreeGrid();
	}
}