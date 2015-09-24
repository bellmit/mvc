$(document).ready(function() {
	gridList.initGrid();
});
var gridList = {
	initGrid:function(){
		var queryForm = $('#sampleForm');
		$('#tt').datagrid({
			url:queryForm.attr('action'),
			rownumbers:true,
			singleSelect:true,
			autoRowHeight:false,
			pagination:true,
			pageSize:10,
			onLoadSuccess:function(data){
				base.ajaxSuccess(data);
			},
			onLoadError:function(XMLHttpRequest, textStatus, event){
				base.ajaxError(XMLHttpRequest, textStatus, event);
			}
		});
		
	}
}