/**
 * 提供打印功能,设置用户页眉页脚为空,并有打印预览功能 利用WebBrowser实现打印功能
 * 
 */
var printHtml = {
	hkey_root : "HKEY_CURRENT_USER",
	hkey_path : "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\",
	hkey_key : null,

	// 设置网页打印的页眉页脚为空
	pagesetup_null : function() {
		try {
			var regWsh = new ActiveXObject("WScript.Shell");
			hkey_key = "header";
			RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "")
			hkey_key = "footer"
			RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "")
		} catch (e) {
		}
	},

	// 设置网页打印的页眉页脚为默认值
	pagesetup_default : function() {
		try {
			var RegWsh = new ActiveXObject("WScript.Shell")
			hkey_key = "header"
			RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "&w&b页码，&p/&P")
			hkey_key = "footer"
			RegWsh.RegWrite(hkey_root + hkey_path + hkey_key, "&u&b&d")
		} catch (e) {
		}
	},

	setdivhidden : function(id) {// 把指定id以外的层统统隐藏
		var divs = document.getElementsByTagName("DIV");
		for ( var i = 0; i < divs.length; i++) {
			if (divs.item(i).id != id)
				divs.item(i).style.display = "none";
		}
	},

	setdivvisible : function(id) {// 把指定id以外的层统统显示
		var divs = document.getElementsByTagName("DIV");
		for ( var i = 0; i < divs.length; i++) {
			if (divs.item(i).id != id)
				divs.item(i).style.display = "block";
		}
	},

	// 预览函数
	printpr : function(id) {
		printHtml.pagesetup_null();// 预览之前去掉页眉，页脚

		var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';
		document.body.insertAdjacentHTML('beforeEnd', WebBrowser);// 在body标签内加入html（WebBrowser
		// activeX控件）
		WebBrowser1.ExecWB(7, 1);// 打印预览
		WebBrowser1.outerHTML = "";// 从代码中清除插入的html代码
		printHtml.pagesetup_default();// 预览结束后页眉页脚恢复默认值
		setdivvisible(id);// 预览结束后显示按钮
	},

	// 打印函数
	print : function(id) {
		printHtml.pagesetup_null();// 打印之前去掉页眉，页脚
		printHtml.setdivhidden(id); // 打印之前先隐藏不想打印输出的元素

		var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';
		document.body.insertAdjacentHTML('beforeEnd', WebBrowser);// 在body标签内加入html（WebBrowser
		// activeX控件）
		WebBrowser1.ExecWB(6, 1);// 打印
		// WebBrowser1.ExecWB(8, 1);//打印设置
		WebBrowser1.outerHTML = "";// 从代码中清除插入的html代码
		printHtml.pagesetup_default();// 打印结束后页眉页脚恢复默认值
	},

	// 设置
	printset : function() {
		printHtml.pagesetup_null();// 预览之前去掉页眉，页脚
		var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';
		document.body.insertAdjacentHTML('beforeEnd', WebBrowser);// 在body标签内加入html（WebBrowser
		// activeX控件）
		WebBrowser.ExecWB(8, 1);// 打印预览
		WebBrowser.outerHTML = "";// 从代码中清除插入的html代码
		// printHtml.pagesetup_default();//预览结束后页眉页脚恢复默认值
	}
};

/**
 * js 原生打印, 1、首先在head中添加 <style type="text/css" media="screen">
 * 
 * @media print{ .print {display:block;} .notPrint {display:none;} } </style>
 *        2、再添加<!--startprint-->和<!--endprint-->用于要打印的代码
 */
var print = {
	preview : function(id) {
		// var win=window.open("about:blank"); //打开一个空页面
		// win.moveTo(100,100); //移动到指定位置

		printHtml.pagesetup_null();// 打印之前去掉页眉，页脚
		printHtml.setdivhidden(id); // 打印之前先隐藏不想打印输出的元素

		bdhtml = window.document.body.innerHTML;
		sprnstr = "<!--startprint-->";
		eprnstr = "<!--endprint-->";
		prnhtml = bdhtml.substr(bdhtml.indexOf(sprnstr) + 17);
		prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));
		window.document.body.innerHTML = prnhtml;
		window.print();
		printHtml.pagesetup_default();// 打印结束后页眉页脚恢复默认值
	}

};

var printFrame = {
	// id-str 内容中的id
	printPart : function(id_str) {
		var el = document.getElementById(id_str);
		var iframe = document.createElement('IFRAME');
		var doc = null;
		iframe
				.setAttribute('style',
						'position:absolute;width:0px;height:0px;left:-500px;top:-500px;');
		document.body.appendChild(iframe);
		doc = iframe.contentWindow.document;
		doc.write('<div>' + el.innerHTML + '</div>');
		doc.close();
		iframe.contentWindow.focus();
		iframe.contentWindow.print();
		if (navigator.userAgent.indexOf("MSIE") > 0) {
			document.body.removeChild(iframe);
		}
	},
	
	
};