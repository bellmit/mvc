$(document).ready(function() {
	paramListForm.query();//初始化默认查询“投诉险种”
	$('#paramGrid').resizeDataGrid();
	base.fitGrid('paramGrid');//调节列表在页面中的自适应
});

var paramListForm={

	doFlg:'',//用于标识是参数修改还是参数新增
	typeNameFlg:'',//用于记录参数的类型
	initTreeGrid:function(){
		var queryForm = $('#paramListForm');
		var param = base.decodeURI(queryForm.serialize());
		$('#paramGrid').treegrid({
			url:queryForm.attr("action"),
			queryParams:base.strToJson(param),//带条件查询此列表，要序列表条件字段
			idField:'id',//显示子节点的信息
			treeField:'text',//在text列显示父子关系，并且可以隐藏
			rownumbers:true,
			fitColumns:true,//列表自适应屏幕的宽度
			singleSelect:true,
			toolbar:'#gridToolbar',//将“新增、删除、修改”放在列表的上面
			columns:[[
				{title:'系统参数显示',
				field:'text',
				width:300,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.text;
				}},
				{title:'系统参数值',
				field:'value',
				width:200,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.value;
				}},
				{title:'参数类型',
				field:'type',
				width:200,
				formatter:function(value,rowData,rowIndex){
					$("#typeName").html(rowData.obj.paramTypeText);//新增、修改时，将类型显示在页面中
					paramListForm.typeNameFlg=rowData.obj.type;//用全局变量typeNameFlg来存放参数的类型
					return rowData.obj.paramTypeText;
					
				}},
				{title:'系统参数排序',
				field:'sortNo',
				width:200,
				formatter:function(value,rowData,rowIndex){
					return rowData.obj.sortNo;
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
		paramListForm.initTreeGrid();
	},
	
	//新增参数
	addParam:function(){
		paramListForm.clear();
		var node = $('#paramGrid').treegrid("getSelected");
		
		if(node){
			$("#parentId").val(node.id);
			$("#parentParamName").html(node.obj.value);
			$("#typeId").val(node.obj.type);
			
		}else{//若不选父节点，则取type的方式不同
			$("#typeId").val(paramListForm.typeNameFlg);//调用全局变量记录的参数类型，放入页面中的typeId，

		}
		
		//如果为AREA_CODE(地区编号)或者是SYSTEM_PATH(系统路径)，则text和value都显示，其他只显示value
		if($('#typeId').val()=='AREA_CODE' || $('#typeId').val()=='SYSTEM_PATH'){
			$('#showText').css("display", "block");
			$('#showValue').css("display", "block");
		}else {
			$('#showText').css("display", "none");
			$('#showValue').css("display", "block");
		}
		
		$('#paramDialog').dialog('open').dialog('setTitle','新增参数');
		paramListForm.doFlg='NEW';
	},
	
	//修改参数
	updParam:function(){
		paramListForm.clear();
		var node = $('#paramGrid').treegrid("getSelected");
		if(!node){
			alert("请先选择参数！");
			return;
		}
		$("#typeId").val(node.obj.type);//为了将值传到后台
		
		if($('#typeId').val()=='AREA_CODE'){
			$('#showText').css("display", "block");
			$('#showValue').css("display", "block");
		}else if($('#typeId').val()=='SYSTEM_PATH'){
			$('#showText').css("display", "block");
			$('#showValue').css("display", "none");
		}else{
			$('#showText').css("display", "none");
			$('#showValue').css("display", "block");
		}
		
		
		
		$("#paramSubmitForm [name='id']").val(node.id);
		$("#paramSubmitForm [name='type']").val(node.obj.type);
		$("#paramSubmitForm [name='value']").val(node.obj.value);
		$("#paramSubmitForm [name='text']").val(node.obj.text);
		$("#paramSubmitForm [name='sortNo']").val(node.obj.sortNo);
		
		var parentNode = $('#paramGrid').treegrid("getParent",node.id);
		if(parentNode){
			$("#parentId").val(parentNode.id);
			$("#parentParamName").html(parentNode.obj.text);
		}
		$('#paramDialog').dialog('open').dialog('setTitle','修改参数');
		paramListForm.doFlg='UPDATE';
	},
	
	clear:function(){
		$("#paramSubmitForm [name='id']").val('');
		$("#paramSubmitForm [name='type']").val('');
		$("#paramSubmitForm [name='value']").val('');
		$("#paramSubmitForm [name='text']").val('');
		$("#paramSubmitForm [name='sortNo']").val('');
		$("#parentId").val('');
		$("#parentParamName").html('');
	},
	
	dialogSave:function(){
		var submitForm = $("#paramSubmitForm");
		if($('#typeId').val()!='AREA_CODE' && $('#typeId').val()!='SYSTEM_PATH'){
			$("#paramSubmitForm [name='text']").val($('#value').val());
		}
		
		if(!submitForm.form('validate')){
			return;
		}
		
		var url;
		if(paramListForm.doFlg=='NEW'){
			url = submitForm.attr("action") + "/param/saveParam";
			
		} else {
			url = submitForm.attr("action") + "/param/updateParam";
		}
		
		
		
		$.post(url,submitForm.serialize(),function(data){

			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			// 关闭Dialog
			paramListForm.dialogCancel();
			// 刷新列表
			paramListForm.initTreeGrid();
		});
	},
	
	//删除参数
	delParam:function(){
		paramListForm.clear();
		var node = $('#paramGrid').treegrid("getSelected");
		if(!node){
			alert("请先选择参数！");
			return;
		}
		if(!confirm("确定要删除该参数吗？")){
			return;
		}
		var submitForm = $("#paramSubmitForm");
		var url = submitForm.attr("action") + "/param/deleteParam";
		var param = {"id":node.id};
		$.post(url,param,function(data){
			alert(data.successMsg);
			// 关闭滚动条
			base.closepageloading();
			// 刷新列表
			paramListForm.initTreeGrid();
		});
	},
	// 关闭Dialog
	dialogCancel:function(){
		$('#paramDialog').dialog('close');
	}
}