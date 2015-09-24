$(document).ready(function() {
	operatorAddForm.init();
	
});

var operatorAddForm={
	
	formHtml:'',
	functionMap:[],
	roleMap:[],
	
	//机构列表的初始化
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
					return '<input id="'+rowData.id+'" type="checkbox" onclick="operatorAddForm.onchecked(this.checked,this.id);" />';
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
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});	
	},
	//角色列表的初始化
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
			fitColumns:true,//列表自适应屏幕的宽度
			collapsible:true,//列表可收缩，需加title
			columns:[[
				{title:'选择',
				field:'id',
				formatter:function(value,rowData,rowIndex){
					return '<input id="'+rowData.id+'" type="checkbox" onclick="operatorAddForm.oncheckedRole(this.checked,this.id);" />';
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
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});
	},
	
	//选中角色
	oncheckedRole:function(value,id){
		if(value){
			operatorAddForm.roleMap.put(id,id);
		}else{
			operatorUpdForm.roleMap.remove(id);
		}
	},	
	//选中的机构
	onchecked:function(value,id){
		if(value){
			operatorAddForm.functionMap.put(id,id);
		} else {
			operatorAddForm.functionMap.remove(id);
		}
	},
	
	//初始化方法	
	init:function(){
		operatorAddForm.initOrgTreeGrid();
		operatorAddForm.initRoleGrid();
		operatorAddForm.functionMap = new TabMap();
		operatorAddForm.roleMap = new TabMap();
		
		operatorAddForm.formHtml = $("#submitForm").html();
		
		$("#checkForm [name='operAccountNo']").val('');
		$("#checkForm [name='operPassword']").val('');
		$("#checkForm [name='operName']").val('');
		$("#checkForm [name='operPhone']").val('');
		$("#checkForm [name='operMail']").val('');
		$("#checkForm [name=operGender]").get(0).checked = true;//用户性别，默认选中男
		$("#checkForm [name=operGender]").get(1).checked = false;
		$("#submitForm input[name='operGender']").val('MAN');//用户性别(默认为0：男)
		$("#checkForm [name=operType]").get(1).checked = true;//用户类型，默认选中普通员工
		$("#checkForm [name=operType]").get(0).checked = false;
		$("#submitForm input[name='operType']").val('NORMAL');//用户类型(默认为0：普通员工)
		$("#checkForm [name='positionType']").val('');
	},
	
	//新增用户---提交方法
	submit:function(){
		var checkForm = $("#checkForm");
		if(!checkForm.form('validate')){
			return;
		}
		if(operatorAddForm.functionMap.size() == 0){
			alert("请至少选择一个机构！");
			return;
		}
		if(operatorAddForm.roleMap.size() == 0){
			alert("请至少选择一个角色！");
			return;
		}
		
		$.each(operatorAddForm.functionMap.keys(),function(i,key){
			var inputName = "orgList["+i+"].id";
			var inputValue = key;
			$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
		});
		$.each(operatorAddForm.roleMap.keys(),function(i,key){
			var inputName = "roleList["+i+"].id";
			var inputValue = key;
			$("<input/>").attr("type","hidden").attr("name",inputName).attr("value",inputValue).appendTo("#submitForm");
		});
		
		$("#submitForm input[name='operAccountNo']").val($("#checkForm [name='operAccountNo']").val());
		$("#submitForm input[name='operPassword']").val($("#checkForm [name='operPassword']").val());
		$("#submitForm input[name='operName']").val($("#checkForm [name='operName']").val());
		$("#submitForm input[name='operPhone']").val($("#checkForm [name='operPhone']").val());		
		$("#submitForm input[name='operMail']").val($("#checkForm [name='operMail']").val());
		$("#submitForm input[name='positionType']").val($("#checkForm [name='positionType']").val());
		
		var submitForm = $("#submitForm");
	
		$.post(submitForm.attr("action"), submitForm.serialize(), function(data){
			if(data.accountNoExit=='1'){
				alert('已经存在相同的登录账号，请重新输入登录账号！');
				base.closepageloading();
				return;
			}
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			base.reloadTab('operatorListForm');
			base.closeTab('addOperatorForm');
		});
		submitForm.html(roleAddForm.formHtml);
	},
	//重置方法
	reset:function(){
		operatorAddForm.init();
	},
	
	//选中性别
	onclickGender:function(){
		if($("#checkForm [name=operGender]").get(0).checked){
			$("#submitForm input[name='operGender']").val('MAN');//用户性别(男)
		}else{
			$("#submitForm input[name='operGender']").val('WOMEN');//用户性别(女)
		}
	},
	//选中类型
	onclickType:function(){
		if($("#checkForm [name=operType]").get(1).checked){
			$("#submitForm input[name='operType']").val('NORMAL');//用户类型(普通员工)
		}else{
			$("#submitForm input[name='operType']").val('MANAGER');//用户性别(主管)
		}
	}
	
}