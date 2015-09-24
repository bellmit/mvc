$(document).ready(function() {
	operatorUpdForm.init();
});

var operatorUpdForm = {
	formHtml:'',
	functionMap:[],
	roleMap:[],
	
	//初始化方法
	init:function(){
		operatorUpdForm.functionMap = new TabMap();
		operatorUpdForm.roleMap = new TabMap();
		operatorUpdForm.formHtml = $("#submitForm").html();
		
		operatorUpdForm.initOrgTreeGrid();
		operatorUpdForm.initRoleGrid();
		$("#checkForm [name='operName']").val('');
		$("#checkForm [name='operPhone']").val('');
		$("#checkForm [name='operMail']").val('');	
		$("#checkForm [name='positionType']").val('');	

	},
	//重置
	reset:function(){
		$("#checkForm")[0].reset();
		operatorUpdForm.init();
	},
	
	initPageData:function(){
		var operatorDetailForm = $("#operatorDetailForm");
		$.post(operatorDetailForm.attr("action"), operatorDetailForm.serialize(), function(data){
			// 关闭滚动条
			base.closepageloading();
			
			$("#checkForm [name='operName']").val(data.operName);
			$("#checkForm [name='operPhone']").val(data.operPhone);
			$("#checkForm [name='operMail']").val(data.operMail);
			$("#checkForm [name='positionType']").val(data.positionType);
			if(data.operGender=="MAN"){
				$("#submitForm input[name='operGender']").val('MAN');
			}else{
				$("#submitForm input[name='operGender']").val('WOMEN');
			}
			if(data.operType=='MANAGER'){
				$("#submitForm input[name='operType']").val('MANAGER');
			}else{
				$("#submitForm input[name='operType']").val('NORMAL');
			}		
			
			//选中的打勾
			if(data.organizations != null && data.organizations.length > 0){
				$.each(data.organizations,function(i,org){
					$("#"+org.id).attr("checked","checked");
					operatorUpdForm.functionMap.put(org.id,org.id);
				});
			}
			
		});
	},
	initRoleData:function(){
		var operatorDetailForm = $("#operatorDetailForm");
		$.post(operatorDetailForm.attr("action"), operatorDetailForm.serialize(), function(data){
			// 关闭滚动条
			base.closepageloading();
			if(data.roles != null && data.roles.length > 0){
				$.each(data.roles,function(i,role){
					$("#"+role.id).attr("checked","checked");
					operatorUpdForm.roleMap.put(role.id,role.id);
				});
			}
		});
	},
	//提交表单
		submit:function(){
		var checkForm = $("#checkForm");
		if(!checkForm.form('validate')){
			return;
		}
		if(operatorUpdForm.functionMap.size() == 0){
			alert("请至少选择一个机构！");
			return;
		}
		if(operatorUpdForm.roleMap.size() == 0){
			alert("请至少选择一个角色！");
			return;
		}
		
		$.each(operatorUpdForm.functionMap.keys(),function(i,key){
			var inputName = "orgList["+i+"].id";
			var inputValue = key;
			$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
		});
		$.each(operatorUpdForm.roleMap.keys(),function(i,key){
			var inputName = "roleList["+i+"].id";
			var inputValue = key;
			$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
		});
		
		$("#submitForm input[name='operName']").val($("#checkForm [name='operName']").val());
		$("#submitForm input[name='operPhone']").val($("#checkForm [name='operPhone']").val());		
		$("#submitForm input[name='operMail']").val($("#checkForm [name='operMail']").val());
		$("#submitForm input[name='positionType']").val($("#checkForm [name='positionType']").val());
		
		var submitForm = $("#submitForm");
		
		$.post(submitForm.attr("action"), submitForm.serialize(), function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			base.reloadTab('operatorListForm');
			base.closeTab('addOperatorForm');
		});
		submitForm.html(operatorUpdForm.formHtml);
	},
	
	initOrgTreeGrid:function(){
		var queryForm = $('#orgListForm');
		$('#organizationGrid').treegrid({
			title:'机构列表',
			url:queryForm.attr("action"),
			idField:'id',
			treeField:'orgName',
			rownumbers:true,
			fitColumns:true,
			singleSelect:false,//设置成多选
			collapsible:true,
			columns:[[
				{title:'选择',
				field:'id',
				formatter:function(value,rowData,rowIndex){
					return '<input id="'+rowData.id+'" type="checkbox" onclick="operatorUpdForm.onchecked(this.checked,this.id);" />';
				}},
				{title:'机构名称',
				field:'orgName',
				width:200,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.orgName;
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
				operatorUpdForm.initPageData();
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});	
	},
	
	initRoleGrid:function(){
		var queryForm = $('#roleListForm');
		var param = base.decodeURI(queryForm.serialize());	
		$('#roleGrid').datagrid({
			title:'角色列表',
			url:queryForm.attr('action'),
//			queryParams:base.strToJson(param),
			rownumbers:true,
			singleSelect:false,//设置成多选
			autoRowHeight:false,
			pagination:false,
			pageSize:base.pageSize,
			fitColumns:true,
			collapsible:true,//列表可收缩，需加title
			columns:[[
				{title:'选择',
				field:'id',
				formatter:function(value,rowData,rowIndex){
					return '<input id="'+rowData.id+'" type="checkbox" onclick="operatorUpdForm.oncheckedRole(this.checked,this.id);" />';
				}},
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
				operatorUpdForm.initRoleData();
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});
	},
	
	//选中的机构
	onchecked:function(value,id){
		if(value){
			operatorUpdForm.functionMap.put(id,id);
		} else {
			operatorUpdForm.functionMap.remove(id);
		}
	},
	//选中角色
	oncheckedRole:function(value,id){
		if(value){
			operatorUpdForm.roleMap.put(id,id);
		}else{
			operatorUpdForm.roleMap.remove(id);
		}
	},
	
	onclickGender:function(){
		if($("#checkForm [name=operGender]").get(0).checked){
			$("#submitForm input[name='operGender']").val('MAN');//用户性别(男)
		}else{
			$("#submitForm input[name='operGender']").val('WOMEN');//用户性别(女)
		}
	},
	onclickType:function(){
		if($("#checkForm [name=operType]").get(1).checked){
			$("#submitForm input[name='operType']").val('NORMAL');//用户类型(普通员工)
		}else{
			$("#submitForm input[name='operType']").val('MANAGER');//用户性别(主管)
		}
	}
	
}