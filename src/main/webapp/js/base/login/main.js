$(document).ready(function() {
	main.showMainHead();
	main.showMainLeft();
});
var main = {
	showMainHead:function(){
		var contextPathForm = $('#contextPathForm');
		var options = {
			region:'north',
			height:100
		};
		$('#main_layout').layout('add',options);
		$('#main_layout').layout('panel','north').panel('open').panel('refresh',contextPathForm.attr('action')+"/basic/mainHead");
	},
	showMainLeft:function(){
		var contextPathForm = $('#contextPathForm');
		var options = {
			region:'west',
			width:289,
			title:'案件提示',
			split:true
		};
		$('#main_layout').layout('add',options);
		$('#main_layout').layout('panel','west').panel('open').panel('refresh',contextPathForm.attr('action')+"/basic/mainLeft");
	},
	showMenu:function(i){
		$("#droplist_div_" + i).addClass("droplist_div");
		$("#droplist_div_" + i).removeClass("droplisthide_div");
	},
	hideMenu:function(i){
		$("#droplist_div_" + i).addClass("droplisthide_div");
		$("#droplist_div_" + i).removeClass("droplist_div");
	},
	addLayout:function(){
		main.showMainHead();
		main.showMainLeft();
		tabs.tabResize();
	},
	removeLayout:function(){
		$('#main_layout').layout('remove', 'north');
		$('#main_layout').layout('remove', 'west');
		tabs.tabResize();
	}
}