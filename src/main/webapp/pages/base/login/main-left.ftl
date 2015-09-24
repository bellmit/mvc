 <!--左部开始-->
<div class="easyui-panel" data-options="fit:true,border:false">
	<table id="main-task" class="easyui-propertygrid">
 	</table>
</div>
<script type="text/javascript">
$('#main-task').propertygrid({
	showGroup:true,
	fit:true,
	border:false,
	scrollbarSize: 0,
	data:[{"name":"超过48小时未更新","value":"1","group":"超期未结案投诉"},
		{"name":"媒体投诉","value":"1","group":"超期未结案投诉"},
		{"name":"监管投诉","value":"1","group":"超期未结案投诉"},
		{"name":"催办投诉","value":"1","group":"超期未结案投诉"},
		{"name":"加急投诉","value":"1","group":"超期未结案投诉"},
		{"name":"超过48小时未更新","value":"1","group":"总投诉数"},
		{"name":"媒体投诉","value":"1","group":"总投诉数"},
		{"name":"监管投诉","value":"1","group":"总投诉数"},
		{"name":"催办投诉","value":"1","group":"总投诉数"},
		{"name":"加急投诉","value":"1","group":"总投诉数"},
		{"name":"当前未结投诉","value":"100","group":"总计"},
		{"name":"超期未处理投诉","value":"100","group":"总计"}],
	columns:[[{field:"name",
			title:"状态/来源",
			width:145,
			styler:function(value,row,index){
				return 'font-weight:bold;color:#00397b;';
			}},
			{field:"value",
			title:"个数",
			width:145}]]
});
</script>
 <!--左部结束--> 