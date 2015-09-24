/**
 * 设置写日志的间隔
 */
var __setWirteLog = function() {
	window.setTimeout( '__wirteLog()', 200) ;
};

/**
 * 具体将日志写到新的页面中
 */
var __wirteLog = function() {
	if (__pfLogger.messageArray.length > 0 ) {
		var sMessage = __pfLogger.messageArray.shift();
		try{
			__pfLogger._GetWindow().output(sMessage);
		} catch(e) {
			//因为使用IFrame以及页面跳转，所以导致最终页面尚未加载完毕就执行此方法，
			//此时output方法还不能调用，因此添加一个__logErrorCount变量，为了防止一直循环而提供一个上限。
			if(!window.__logErrorCount) {
				window.__logErrorCount = 0;
			} else {
				window.__logErrorCount++;
			}
			if(window.__logErrorCount < 999) {
				var oTempArray = [sMessage];
				for (var i = 0; i < __pfLogger.messageArray.length; i++) {
					oTempArray[i + 1] = __pfLogger.messageArray[i];
				}
				__pfLogger.messageArray = oTempArray;
				__setWirteLog();
			} else {
				alert('__writeLog error:' + e.name + ',' + e.message);
				window.__logErrorCount = 0;
			}
			return;
		}
	}
};

/**
 * Logger类
 */
function Logger() {
	this._GetWindow();
	this.messageArray = new Array();
};

Logger.prototype.output = function(message){
	this.messageArray[this.messageArray.length] = message;
	__setWirteLog();
};

Logger.prototype._GetWindow = function() {
	if ( !this.debugWindow || this.debugWindow.closed ) {
		this.debugWindow = window.open('../pages/debugTransfer.html', 'Logger');
		
	}
	return this.debugWindow ;
};