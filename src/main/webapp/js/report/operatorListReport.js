 $(document).ready(function() {
	 
	 var toolbar=[{
		 
		 text:'导出excel',  
         iconCls:'icon-save',  
         handler:function(){
        	 	var url=$('#reportOperatorFrom').attr('action');
        	 	window.location.href = url;
        	 }  
	 }
	 ];
	 
	 var url=$("#path").attr('action')+'/report/reportOpeData';
	 $('#reportOperator').datagrid({
			title:'用户报表',
			height:500,
			nowrap: false,
			striped: true,
			collapsible:true,
			toolbar:toolbar,
			fitColumns:true,
			rownumbers:true,//设置序列号
			url:url,
			columns:[[
			    {title:'姓名',field:'name',width:150, align:'center'},
				{title:'邮箱',field:'emil',width:150,align:'center'}
			]]
		});

 });