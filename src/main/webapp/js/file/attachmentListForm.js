$(document).ready(function() {
			attachmentList.initGrid();
			base.fitGrid('caseGrid');
		});

var attachmentList = {
	// 查询页面datagrid
	initGrid : function() {
		var attachmentListForm = $("#attachmentListForm");
		var url = attachmentListForm.attr("action");
		$("#attachmentGrid").datagrid({
			url : url,
			queryParams : null,
			rownumbers : true,
			singleSelect : true,
			autoRowHeight : true,
			pagination : true,
			pageSize : base.pageSize,
			fitColumns : true,
			pageNumber : 1,// 带条件查询时，用于显示在第一页
			remoteSort : true,// 设置到服务器端排序
			toolbar : '#gridToolbar',
			columns : [[{
						title : "附件名称",
						field : "originalFileName",
						width : 150,
						sortable : true
					}, {
						title : '操作',
						field : 'operate',
						width : 100,
						formatter : function(colValue, rowData, rowIndex) {
							var buttons = "";
							buttons += '<a name="handelBtn" href="javascript:void(0);" onClick="attachmentList.fileDownLoad(\''
									+ rowData.attachmentPath
									+ '\',\''
									+ rowData.originalFileName
									+ '\')">下载附件</a>';
							buttons += '<a name="cancelBtn" href="javascript:void(0);" onClick="attachmentList.deleteFile(\''
									+ rowData.id
									+ '\')">删除附件</a>';
							return buttons;
						}
					}]],
			onLoadSuccess : function(data) {
				$('a[name="handelBtn"]').linkbutton({
							plain : true,
							iconCls : "icon-edit"
						});
				$('a[name="cancelBtn"]').linkbutton({
							plain : true,
							iconCls : "icon-redo"
						});
				base.ajaxSuccess(data);
			},
			onLoadError : function(XMLHttpRequest, textStatus, event) {
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});
	},
	fileDownLoad : function(attachmentPath, originalFileName) {
		var url = $('#file').attr('action')+'/common/downloadFile';
		var form = $("<form>");
		form.attr('method', 'post');
		form.attr('action', url);

		var input1 = $('<input>');
		input1.attr('type', 'hidden');
		input1.attr('name', 'attachmentPath');
		input1.attr('value', attachmentPath);

		var input2 = $('<input>');
		input2.attr('type', 'hidden');
		input2.attr('name', 'originalFileName');
		input2.attr('value', originalFileName);

		$('body').append(form);
		form.append(input1);
		form.append(input2);

		form.submit();
		form.remove();
	},
	deleteFile:function(id){
		if(!confirm("确定要删除该附件吗！")){
			return;
		}
		var url = $('#file').attr('action')+'/common/deleteFile';
		var param = {"id":id};
		$.post(url,param,function(data){
			alert(data.msg);
			attachmentList.initGrid();
			// 关闭滚动条
			base.closepageloading();
		});
	},

	// 跳转到处理的详情页面
	dealForm : function(caseCode, subCaseCode, caseStatus, nodeType) {
		var url = $('#dealForm').attr("action")
				+ '/panel/personalDealForm?caseCode=' + caseCode
				+ '&subCaseCode=' + subCaseCode + '&caseStatus=' + caseStatus
				+ '&nodeType=' + nodeType + '&tabId=personalDealForm';
		parent.tabs.add("personalDealForm", "个人案件处理", url, true);
	}

}