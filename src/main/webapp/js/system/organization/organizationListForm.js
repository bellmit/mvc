$(document).ready(function() {
	organizationListForm.initTreeGrid();
	base.fitGrid('orgGrid');
});
var organizationListForm = {
	doFlg:'',
	initTreeGrid:function(){
		var queryForm = $('#orgListForm');
		$('#orgGrid').treegrid({
			url:queryForm.attr("action"),
			idField:'id',
			treeField:'orgName',
			rownumbers:true,
			fitColumns:true,//列表自适应屏幕的宽度
			singleSelect:true,
			toolbar:'#gridToolbar',
			columns:[[
				{title:'机构名称',
				field:'orgName',
				width:250,
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
	},
	
	addOrg:function(){
		organizationListForm.clear();
		var node = $('#orgGrid').treegrid("getSelected");
		if(node){
			$("#parentId").val(node.id);
			$("#parentOrgName").html(node.obj.orgName);
		}
		$('#orgDialog').dialog('open').dialog('setTitle','新增机构');
		organizationListForm.doFlg='NEW';
	},
	updOrg:function(){
		organizationListForm.clear();
		var node = $('#orgGrid').treegrid("getSelected");
		if(!node){
			alert("请先选择机构！");
			return;
		}
		
		$("#orgSubmitForm [name='id']").val(node.id);
		$("#orgSubmitForm [name='orgName']").val(node.obj.orgName);
		$("#orgSubmitForm [name='orgAddress']").val(node.obj.orgAddress);
		$("#orgSubmitForm [name='orgMemo']").val(node.obj.orgMemo);
		var parentNode = $('#orgGrid').treegrid("getParent",node.id);
		if(parentNode){
			$("#parentId").val(parentNode.id);
			$("#parentOrgName").html(parentNode.obj.orgName);
		}
		$('#orgDialog').dialog('open').dialog('setTitle','修改机构');
		organizationListForm.doFlg='UPDATE';
	},
	delOrg:function(){
		organizationListForm.clear();
		var node = $('#orgGrid').treegrid("getSelected");
		if(!node){
			alert("请先选择机构！");
			return;
		}
		if(!confirm("确定要删除该机构吗？")){
			return;
		}
		var submitForm = $("#orgSubmitForm");
		var url = submitForm.attr("action") + "/system/organization/deleteOrganization";
		var param = {"id":node.id};
		$.post(url,param,function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			// 刷新列表
			organizationListForm.initTreeGrid();
		});
	},
	dialogSave:function(){
		var submitForm = $("#orgSubmitForm");
		if(!submitForm.form('validate')){
			return;
		}
		
		var url;
		if(organizationListForm.doFlg=='NEW'){
			url = submitForm.attr("action") + "/system/organization/saveOrganization";
		} else {
			url = submitForm.attr("action") + "/system/organization/updateOrganization";
		}
		
		$.post(url,submitForm.serialize(),function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			// 关闭Dialog
			organizationListForm.dialogCancel();
			// 刷新列表
			organizationListForm.initTreeGrid();
		});
	},
	dialogCancel:function(){
		$('#orgDialog').dialog('close');
	},
	clear:function(){
		$("#orgSubmitForm [name='id']").val('');
		$("#orgSubmitForm [name='orgName']").val('');
		$("#orgSubmitForm [name='orgAddress']").val('');
		$("#orgSubmitForm [name='orgMemo']").val('');
		$("#parentId").val('');
		$("#parentOrgName").html('');
	}
}