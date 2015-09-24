/**
 * @see 平台基础工具：JS调试工具等
 */
 
/**
 * 全局Ajax操作默认设置处理，beforeSend、error、success，如有其它请在此处添加
 */
$.ajaxSetup({
		global : true,
		cache : false,
		beforeSend : function(XMLHttpRequest) {
			base.openpageloading();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			base.ajaxError(XMLHttpRequest, textStatus, errorThrown);
		},
		success : function(data) {
			base.ajaxSuccess(data);
		}
	})

var base = {
	ajaxSuccess : function(data) {
		base.closepageloading();
	},
	ajaxError : function(XMLHttpRequest, textStatus, errorThrown) {
		base.closepageloading();
		if (textStatus == "timeout") {
			alert('请求超时,请重试!');
		} else if (textStatus == "error") {
			if (XMLHttpRequest.status == '403') {
				alert("您没权限访问");
				// window.location.href = contextPath + '/login.jsp';
			} else if (XMLHttpRequest.status == '500') {
				alert('服务器端发生错误！');
			} else {
				alert('系统异常,请联系系统管理员!');
			}
		}
	},
	/**
	 * 系统分页每页显示条数常量
	 */
	pageSize : 10,
	pageStart : 0,
	/**
	 * 打开遮罩层
	 */
	 openpageloading : function() {
		var pageloadid = null;
		try {
			pageloadid = document.getElementById("pageloadingdiv");
		} catch (e) {
			pageloadid = null;
		};
		if (pageloadid != undefined && pageloadid != null) {
			pageloadid.style.display = "block";
		}
	},
	/**
	 * 关闭遮罩层
	 */
	closepageloading : function() {
		var pageloadid = null;
		try {
			pageloadid = document.getElementById("pageloadingdiv");
		} catch (e) {
			pageloadid = null;
		};
		if (pageloadid != undefined && pageloadid != null) {
			pageloadid.style.display = "none";
		}
	},
	/**
	 * 日志记录，可以将所有日志都记录在同一个页面中 FAN
	 * 
	 * @param {}
	 *            param
	 */
	log : function(param) {
		var loged = false;
		if (loged) {
			param = param + "";
			try {
				if (typeof window.__pfLogger == 'undefined') {
					window.__pfLogger = new Logger();
				}
				window.__pfLogger.output(param);
			} catch (e) {
				alert('base.log error! ' + e.name + ':' + e.message);
			}
		}
	},
	strToJson : function(str) {
		str = str.replace(/&/g, "','");
		str = str.replace(/=/g, "':'");
		str = "({'" + str + "'})";
		obj = eval(str);
		return obj;
	},
	addTab : function(tabId, titleName, url, closable) {
		parent.tabs.add(tabId, titleName, url, closable);
	},
	closeTab : function(tabId) {
		parent.tabs.closeTab(tabId);
	},
	closeTabs : function(tabIds) {
		parent.tabs.closeTabs(tabIds);
	},
	reloadTab : function(tabId) {
		parent.tabs.reloadTab(tabId);
	},
	/**
	 * JavaScript的转码方法
	 */
	encodeURI : function(param) {
		return encodeURIComponent(param);
	},
	/**
	 * javaScript 的转码,由于要encodeURI两次服务器端才能decode
	 */
	encodeURItoUTf8 : function(param) {
		return encodeURI(base.encodeURI(param));
	},
	decodeURI : function(param) {
		param = param.replace(/\+/g," ");
		return decodeURIComponent(param);
	},
	//当页面大小变化时，列表随页面大小变化
	fitGrid:function(datagridId) {  
	    $(window).resize(function() {   
	        $('#' + datagridId).resizeDataGrid();   
	    });   
	}
}

$.fn.extend({   
    /**  
     * 修改DataGrid对象的默认大小，以适应页面宽度。  
     *   
     * @param heightMargin  
     *            高度对页内边距的距离。  
     * @param widthMargin  
     *            宽度对页内边距的距离。  
     * @param minHeight  
     *            最小高度。  
     * @param minWidth  
     *            最小宽度。  
     *   
     */   
    resizeDataGrid : function(heightMargin, widthMargin, minHeight, minWidth) {   
	        var height = $(document.body).height() - heightMargin;   
	        var width = $(document.body).width() - widthMargin;   
	   
	        height = height < minHeight ? minHeight : height;   
	        width = width < minWidth ? minWidth : width;   
	   
	        $(this).datagrid('resize', {   
	            height : height,   
	            width : width   
	        });   
    	}   
	}); 