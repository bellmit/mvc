$(document).ready(function() {
	roleAddForm.init();
});
var roleAddForm = {
	formHtml:'',
	functionMap:[],
	init:function(){
		roleAddForm.initTreeGrid();
		roleAddForm.functionMap = new TabMap();
		
		roleAddForm.formHtml = $("#submitForm").html();
		$("#checkForm [name='roleName']").val('');
		$("#checkForm [name='roleMemo']").val('');
	},
	reset:function(){
		roleAddForm.init();
	},
	submit:function(){
		var checkForm = $("#checkForm");
		if(!checkForm.form('validate')){
			return;
		}
		if(roleAddForm.functionMap.size() == 0){
			alert("请至少选择一个权限！");
			return;
		}
		
		$.each(roleAddForm.functionMap.keys(),function(i,key){
			var inputName = "functionList["+i+"].id";
			var inputValue = key;
			$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
		});
		$("#submitForm input[name='roleName']").val($("#checkForm [name='roleName']").val());
		$("#submitForm input[name='roleMemo']").val($("#checkForm [name='roleMemo']").val());
		
		var submitForm = $("#submitForm");
		$.post(submitForm.attr("action"), submitForm.serialize(), function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			base.reloadTab('roleListForm');
			base.closeTab('addRoleForm');
		});
		submitForm.html(roleAddForm.formHtml);
	},
	initTreeGrid:function(){
		var queryForm = $('#funListForm');
		$('#funGrid').treegrid({
			title:'权限列表',
			url:queryForm.attr("action"),
			idField:'id',
			treeField:'funName',
			rownumbers:true,
			fitColumns:true,
			singleSelect:true,
			collapsible:true,
			columns:[[
				{title:'选择',
				field:'id',
				formatter:function(value,rowData,rowIndex){
					return '<input id="'+rowData.id+'" type="checkbox" onclick="roleAddForm.onchecked(this.checked,this.id);" />';
				}},
				{title:'功能名称',
				field:'funName',
				width:200,
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
	},
	onchecked:function(value,id){
		if(value){
			roleAddForm.functionMap.put(id,id);
			roleAddForm.cascadeCheck(id);
			roleAddForm.cascadeCheckParent(id);
		} else {
			roleAddForm.functionMap.remove(id);
			roleAddForm.cascadeUnCheck(id);
			roleAddForm.cascadeUnCheckParent(id);
		}
	},
	cascadeCheckParent:function(id){
		var parentNode = $('#funGrid').treegrid("getParent",id);
		if(parentNode){
			$("#"+parentNode.id).attr("checked","checked");
			roleAddForm.functionMap.put(parentNode.id,parentNode.id);
			roleAddForm.cascadeCheckParent(parentNode.id);
		}
	},
	cascadeCheck:function(id){
		var childrenNodes = $('#funGrid').treegrid("getChildren",id);
		$.each(childrenNodes,function(i,childrenNode){
			roleAddForm.cascadeCheck(childrenNode.id);
			$("#"+childrenNode.id).attr("checked","checked");
			roleAddForm.functionMap.put(childrenNode.id,childrenNode.id);
		});
	},
	cascadeUnCheckParent:function(id){
		var checkedFlag = false;
		var parentNode = $('#funGrid').treegrid("getParent",id);
		if(parentNode){
			var childrenNodes = $('#funGrid').treegrid("getChildren",parentNode.id);
			$.each(childrenNodes,function(i,childrenNode){
				var checked = $("#"+childrenNode.id).attr("checked");
				if(checked){
					checkedFlag = true;
					return false;
				}
			});
			if(!checkedFlag){
				$("#"+parentNode.id).removeAttr("checked");
				roleAddForm.functionMap.remove(parentNode.id);
			}
			roleAddForm.cascadeUnCheckParent(parentNode.id);
		}
	},
	cascadeUnCheck:function(id){
		var childrenNodes = $('#funGrid').treegrid("getChildren",id);
		$.each(childrenNodes,function(i,childrenNode){
			roleAddForm.cascadeUnCheck(childrenNode.id);
			$("#"+childrenNode.id).removeAttr("checked");
			roleAddForm.functionMap.remove(childrenNode.id);
		});
	}
}