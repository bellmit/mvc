
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
		base.log('add tab_' + tabId);
		var tab = $('#center-tab').omTabs('getAlter', 'tab_' + tabId);
		if (tab) {
			$('#center-tab').omTabs('activate', tab);
			var frame = document.getElementById(tabId);
			base.log(frame);
			// var url = frame.contentWindow.location;
			frame.contentWindow.location = url;
		} else {
			base.log('add tab_' + tabId);
			var ifh = $('#center-tab').height()
					- $('#center-tab').find(".om-tabs-headers").outerHeight()
					- 4;// 为了照顾apusic皮肤，apusic没有2px的padding，只有边框，所以多减去2px
			base.log('center-tab高度：' + $('#center-tab').height()
					+ ', om-tabs-headers高度：'
					+ $('#center-tab').find(".om-tabs-headers").outerHeight());
			base.log('高度为：' + ifh);
			$('#center-tab').omTabs('add', {
				title : titleName,
				tabId : 'tab_' + tabId,
				content : "<iframe id='" + tabId
						+ "' border=0 frameBorder='no' name='tab_" + tabId
						+ "' src='" + url + "' height='" + ifh
						+ "' width='100%' scrolling='auto'></iframe>",
				closable : closable
			});
			
		}
		//存放tabId对应的url
		map.put(tabId, url);
	},
	addTab : function(tabId, titleName, url, closable) {
		parent.window.tabs.add(tabId, titleName, url, closable);
	},
	addNewTab : function(tabId, titleName, url, closable) {
		parent.window.tabs.addTab(tabId, titleName, url, closable);

	},
	closeTab : function(tabId) {
		var tab = $('#center-tab').omTabs('getAlter', 'tab_' + tabId);
		if (tab) {
			$('#center-tab').omTabs('close', tab);
		}
	},
	closeTabs : function(tabIds) {
		if (tabIds) {
			for (var i=0; i < tabIds.length; i++) {
				var tab = $('#center-tab').omTabs('getAlter', 'tab_' + tabIds[i]);
				if (tab) {
					$('#center-tab').omTabs('close', tab);
				}
			}
		}
	},
	reloadTab : function(tabId) {
		var tab = $('#center-tab').omTabs('getAlter', 'tab_' + tabId);
		if (tab) {
			//$('#center-tab').omTabs('reload', tab, url);
			var url = map.get(tabId);
			var frame = document.getElementById(tabId);
			frame.contentWindow.location = url;
		}
	}
}