$(document).ready(function() {
	roleUpdForm.init();
});
var roleUpdForm = {
	formHtml:'',
	functionMap:[],
	init:function(){
		roleUpdForm.functionMap = new TabMap();
		roleUpdForm.formHtml = $("#submitForm").html();
		
		roleUpdForm.initTreeGrid();
		$("#checkForm [name='roleName']").val('');
		$("#checkForm [name='roleMemo']").val('');
		
	},
	reset:function(){
		roleUpdForm.init();
	},
	initPageData:function(){
		var roleDetailForm = $("#roleDetailForm");
		$.post(roleDetailForm.attr("action"), roleDetailForm.serialize(), function(data){
			// 关闭滚动条
			base.closepageloading();
			$("#checkForm [name='roleName']").val(data.roleName);
			$("#checkForm [name='roleMemo']").val(data.roleMemo);
			
			if(data.functions != null && data.functions.length > 0){
				$.each(data.functions,function(i,fun){
					$("#"+fun.id).attr("checked","checked");
					roleUpdForm.functionMap.put(fun.id,fun.id);
				});
			}
		});
	},
	submit:function(){
		var checkForm = $("#checkForm");
		if(!checkForm.form('validate')){
			return;
		}
		if(roleUpdForm.functionMap.size() == 0){
			alert("请至少选择一个权限！");
			return;
		}
		
		$.each(roleUpdForm.functionMap.keys(),function(i,key){
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
			singleSelect:true,
			collapsible:true,
			fitColumns:true,
			columns:[[
				{title:'选择',
				field:'id',
				formatter:function(value,rowData,rowIndex){
					return '<input id="'+rowData.id+'" type="checkbox" onclick="roleUpdForm.onchecked(this.checked,this.id);" />';
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
				roleUpdForm.initPageData();
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});	
	},
	onchecked:function(value,id){
		if(value){
			roleUpdForm.functionMap.put(id,id);
			roleUpdForm.cascadeCheck(id);
			roleUpdForm.cascadeCheckParent(id);
		} else {
			roleUpdForm.functionMap.remove(id);
			roleUpdForm.cascadeUnCheck(id);
			roleUpdForm.cascadeUnCheckParent(id);
		}
	},
	cascadeCheckParent:function(id){
		var parentNode = $('#funGrid').treegrid("getParent",id);
		if(parentNode){
			$("#"+parentNode.id).attr("checked","checked");
			roleUpdForm.functionMap.put(parentNode.id,parentNode.id);
			roleUpdForm.cascadeCheckParent(parentNode.id);
		}
	},
	cascadeCheck:function(id){
		var childrenNodes = $('#funGrid').treegrid("getChildren",id);
		$.each(childrenNodes,function(i,childrenNode){
			roleUpdForm.cascadeCheck(childrenNode.id);
			$("#"+childrenNode.id).attr("checked","checked");
			roleUpdForm.functionMap.put(childrenNode.id,childrenNode.id);
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
				roleUpdForm.functionMap.remove(parentNode.id);
			}
			roleUpdForm.cascadeUnCheckParent(parentNode.id);
		}
	},
	cascadeUnCheck:function(id){
		var childrenNodes = $('#funGrid').treegrid("getChildren",id);
		$.each(childrenNodes,function(i,childrenNode){
			roleUpdForm.cascadeUnCheck(childrenNode.id);
			$("#"+childrenNode.id).removeAttr("checked");
			roleUpdForm.functionMap.remove(childrenNode.id);
		});
	}
}