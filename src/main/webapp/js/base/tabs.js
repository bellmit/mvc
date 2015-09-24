
/*
 * $(document).ready(function() { alert('ddd'); var content_h =
 * (document.body.clientHeight - 32 - 28) + 'px'; var tabElement =
 * $('#center-tab').omTabs({ height : content_h });
 * 
 * var ifh = tabElement.height() -
 * tabElement.find(".om-tabs-headers").outerHeight() - 4;
 * //为了照顾apusic皮肤，apusic没有2px的padding，只有边框，所以多减去2px $('#3Dbox').height(ifh); var
 * navData = []; $("#navTree").omTree({ dataSource : navData, simpleDataModel:
 * true, onClick : function(nodeData , event){ if(nodeData.url){ var tabId =
 * tabElement.omTabs('getAlter', 'tab_'+nodeData.id); if(tabId){
 * tabElement.omTabs('activate', tabId); }else{ tabElement.omTabs('add',{ title :
 * nodeData.text, tabId : 'tab_'+nodeData.id, content : "<iframe
 * id='"+nodeData.id+"' border=0 frameBorder='no' name='inner-frame'
 * src='"+nodeData.url+"' height='"+ifh+"' width='100%'></iframe>", closable :
 * true }); } } } }); });
 */

/**
 * 
 */
var map = new TabMap();//存放tabId，url
var tabs = {
	add : function(tabId, titleName, url, closable) {
		var tab = $('#main-tabs').tabs('getTab',titleName);
		if(tab){
			$('#main-tabs').tabs('select',titleName);
			var frame = document.getElementById(tabId);
			frame.contentWindow.location = url;
			tab = $('#main-tabs').tabs('getSelected');
		} else {
			base.log('add tab_' + tabId);
			var ifh = $('#main-tabs').height()
			- $('#main-tabs').find(".tabs-header").outerHeight()
			- 4;
			$('#main-tabs').tabs('add',{
				title:titleName,
				content:"<iframe id='" + tabId
				+ "' border=0 frameBorder='no' name='tab_" + tabId
				+ "' src='" + url + "' height='"+ifh+"' width='100%' scrolling='auto'></iframe>",
				closable:closable
			});
			tab = $('#main-tabs').tabs('getSelected');
		}
		
		map.put(tabId, {'index':$('#main-tabs').tabs('getTabIndex',tab),'url':url});
	},
	addTab : function(tabId, titleName, url, closable) {
		parent.window.tabs.add(tabId, titleName, url, closable);
	},
	addNewTab : function(tabId, titleName, url, closable) {
		parent.window.tabs.addTab(tabId, titleName, url, closable);

	},
	closeTab : function(tabId) {
		if(map.containsKey(tabId)){
			$('#main-tabs').tabs('close',map.get(tabId).index);
		}
	},
	closeTabs : function(tabIds) {
		if (tabIds) {
			for (var i=0; i < tabIds.length; i++) {
				if(map.containsKey(tabIds[i])){
					$('#main-tabs').tabs('close',map.get(tabIds[i]).index);
				}
			}
		}
	},
	reloadTab : function(tabId) {
		if(map.containsKey(tabId)){
			$('#main-tabs').tabs('select',map.get(tabId).index);
			var url = map.get(tabId).url;
			var frame = document.getElementById(tabId);
			frame.contentWindow.location = url;
		}
	},
	tabResize : function(){
		var ifh = $('#main-tabs').height()
			- $('#main-tabs').find(".tabs-header").outerHeight()
			- 4;
		for(var i=0; i < map.keys().length; i++){
			var key = map.keys()[i];
			var frame = document.getElementById(key);
			frame.height = ifh;
		}
	}
}