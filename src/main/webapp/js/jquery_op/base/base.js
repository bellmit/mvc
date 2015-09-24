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
var custType = [{'text':'10','value':'VIP'},{'text':'25','value':'行领导'},{'text':'100','value':'贵宾客户专享'}];
		
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

	pageStart : 0,

	/**
	 * 检查输入的分页数合法性
	 */
	checkPageNum : function(inputNum, i, maxNum) {
		var pageNum = $('#' + inputNum).val();
		if (isNaN(pageNum) || (pageNum <= 0 && pageNum != '')
				|| pageNum.indexOf('.') > 0) {
			alert('只能输入整数！');
			$('#' + inputNum).focus();
		} else if (pageNum > maxNum) {
			alert('不能超过最大页数！');
			$('#' + inputNum).focus();
		}
		base.pageStart = i;

	},

	/**
	 * 输入页码后的跳转
	 */
	pagingFun : function(pageFun) {
		pageFun(base.pageStart);
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

	/**
	 * 封装了OMUI的validate方法，供系统表单验证
	 * 
	 * @param {}
	 *            formId 表单的form id
	 * @param {}
	 *            rule 表单上需要验证的表单项验证规则
	 * @param {}
	 *            message 验证后的提示信息 参考调用示例见js/complaint/entering.js
	 */
	formValidate : function(formId, rule, message) {
		$('#' + formId).validate({
			rules : rule,
			messages : message,
			/**
			 * 显示校验信息的容器，本示例使用<span class="errorMsg"
			 * />做为容器，建议使用容器来避免和其它组件的dom元素交叉的问题
			 * 比如使用omCombo的时候如果不使用容器将会导致样式错乱，根本原因是combo是在input外面包裹一层span再添加样式组成，而校验
			 * 框架默认会再input后面加label标签从而导致combo组件样式混乱。
			 * 
			 * @param {}
			 *            error
			 * @param {}
			 *            element
			 */
			errorPlacement : function(error, element) {
				if (error.html()) {
					$(element).parents().map(function() {
						var attentionElement = $(this).children().eq(2);
						if (this.tagName.toLowerCase() == 'td') {
							attentionElement.html(error);
							attentionElement.css('display', 'none'); // 覆盖默认显示方法，先隐藏消息，等鼠标移动上去再显示
							attentionElement.prev().prev().children("input")
									.addClass("x-form-invalid");
							if (attentionElement.prev().prev().children().length <= 0)
								attentionElement.prev().prev()
										.addClass("x-form-invalid");
						}
					});
				}
			},

			/**
			 * 控制错误显示隐藏的方法，当自定义了显示方式之后一定要在这里做处理。
			 * 
			 * @param {}
			 *            errorMap
			 * @param {}
			 *            errorList
			 */
			showErrors : function(errorMap, errorList) {
				if (errorList && errorList.length > 0) {
					$.each(errorList, function(index, obj) {
						var msg = this.message;
						$(obj.element).parents().map(function() {
							if (this.tagName.toLowerCase() == 'td') {
								var attentionElement = $(this).children().eq(2);
								attentionElement.prev().css("display",
										"inline-block");
								attentionElement.html(msg);
							}
						});
					});
				} else {
					$(this.currentElements).parents().map(function() {
						$(this).children("input").removeClass("x-form-invalid");
						// 获取errorImg图标，如果不是则不执行hide操作
						var errorImg = $(this).children().eq(1);
						if (errorImg.hasClass("errorImg")) {
							errorImg.hide();
						}
					});
				}
				this.defaultShowErrors();
			}
		})
		$('.errorImg').bind('mouseover', function(e) {
			// 要有错误才显示
			var errorMsg = $(this).next();
			if (errorMsg.html().length > 0
					&& errorMsg.find("label").html().length > 0)
				errorMsg.css('display', 'inline').css({
							'top' : e.pageY + 10,
							'left' : e.pageX + 5
						});
		}).bind('mouseout', function() {
					$(this).next().css('display', 'none');
				});
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
	/**
	 * 二级联动下拉列表
	 * level1SelectId为一级的id  level2SelectId为二级的id
	 */
	cascadeSelector : function(level1SelectId,level2SelectId,jsonData,level1Value, level2Value) {
		var level1Json = [];
		$.each(jsonData,function(i){
			if(jsonData[i].parentId == null){
				level1Json.push(jsonData[i]);
			}
		});
		$('#'+level1SelectId).omCombo({
			dataSource : level1Json,
			valueField : 'text',
			optionField : 'text',
			onValueChange : function(target, newValue, oldValue) {
				var childJson = new Array();
				$.each(level1Json,function(i){
					
					if(level1Json[i].text==newValue){
						childJson = level1Json[i].childList;
					}
				});
				$('#'+level2SelectId).val('').omCombo('setData', childJson);
				base.closepageloading();
			}
		});
		$('#'+level2SelectId).omCombo({
			editable : false,
			dataSource : [],
			valueField : 'text',
			optionField : 'text',
			onValueChange : function(target, newValue, oldValue) {
			}
		});
		if (level1Value) {
			$('#'+level1SelectId).val('').omCombo('value', level1Value);
		}
		if (level2Value) {
			$('#'+level2SelectId).val('').omCombo('value', level2Value);
		}
	},
	
	/**
	 * 二级联动下拉列表
	 * 投诉来源带出投诉等级
	 */
	
		cascadeSelectorSource : function(level1SelectId,level2SelectId,jsonData,level1Value, level2Value) {
			var childJson = [];
			$.each(jsonData,function(i){
					if(jsonData[i].parentId != null){
					childJson.push(jsonData[i]);
				}
			});
			$('#'+level2SelectId).omCombo({
				dataSource : childJson,
				valueField : 'text',
				optionField : 'text',
				onValueChange : function(target, newValue, oldValue) {
					var level1Json =[];
					var parentId;
					$.each(childJson,function(i){
					if(childJson[i].text == newValue){
						parentId=	childJson[i].parentId;
						}
					});
					$.each(jsonData,function(i){
						if(jsonData[i].id==parentId){
							$('#'+level1SelectId).val( jsonData[i].text);
						}
					});
				}
			});
			if (level1Value) {
			$('#'+level1SelectId).val('').omCombo('value', level1Value);
			}
			if (level2Value) {
				$('#'+level2SelectId).val('').omCombo('value', level2Value);
			}
		},
		
		/**
		 * 投诉来源多选
		 */
		caseSelectorSourceMulti : function(level1SelectId,level2SelectId,jsonData,level1Value, level2Value) {
			var childJson = [];
			$.each(jsonData,function(i){
					if(jsonData[i].parentId != null){
					childJson.push(jsonData[i]);
				}
			});
			$('#'+level2SelectId).omCombo({
				dataSource : childJson,
				valueField : 'text',
				optionField : 'text',
				multi : true,//多选
				onValueChange : function(target, newValue, oldValue) {
					var level1Json =[];
					var parentId;
					$.each(childJson,function(i){
					if(childJson[i].text == newValue){
						parentId=	childJson[i].parentId;
						}
					});
					$.each(jsonData,function(i){
						if(jsonData[i].id==parentId){
							$('#'+level1SelectId).val( jsonData[i].text);
						}
					});
				}
			});
			if (level1Value) {
			$('#'+level1SelectId).val('').omCombo('value', level1Value);
			}
			if (level2Value) {
				$('#'+level2SelectId).val('').omCombo('value', level2Value);
			}
		},
		
	/**
	 * 涉及单位多选下拉列表
	 */
		caseSelectorDepament : function(level1SelectId,level2SelectId,jsonData,level1Value, level2Value) {
		var level1Json = [];
		$.each(jsonData,function(i){
			if(jsonData[i].parentId == null){
				level1Json.push(jsonData[i]);
			}
		});
		$('#'+level1SelectId).omCombo({
			dataSource : level1Json,
			valueField : 'text',
			optionField : 'text',
			multi : true,//多选
			onValueChange : function(target, newValue, oldValue) {
				var childJson = new Array();
				$.each(level1Json,function(i){
					
					if(level1Json[i].text==newValue){
						childJson = level1Json[i].childList;
					}
				});
				$('#'+level2SelectId).val('').omCombo('setData', childJson);
				base.closepageloading();
			}
		});
		$('#'+level2SelectId).omCombo({
			editable : false,
			dataSource : [],
			valueField : 'text',
			optionField : 'text',
			onValueChange : function(target, newValue, oldValue) {
			}
		});
		if (level1Value) {
			$('#'+level1SelectId).val('').omCombo('value', level1Value);
		}
		if (level2Value) {
			$('#'+level2SelectId).val('').omCombo('value', level2Value);
		}
	}
}

$(document).ready(function() {
	// 屏蔽退格键
	document.documentElement.onkeydown = function() {
		if (window.event.keyCode == 8)// 屏蔽退格键
		{
			var type = window.event.srcElement.type;// 获取触发事件的对象类型
			// var tagName=window.event.srcElement.tagName;
			var reflag = window.event.srcElement.readOnly;// 获取触发事件的对象是否只读
			var disflag = window.event.srcElement.disabled;// 获取触发事件的对象是否可用
			if (type == "text" || type == "textarea")// 触发该事件的对象是文本框或者文本域
			{
				if (reflag || disflag)// 只读或者不可用
				{
					// window.event.stopPropagation();
					window.event.returnValue = false;// 阻止浏览器默认动作的执行
				}
			} else {
				window.event.returnValue = false;// 阻止浏览器默认动作的执行
			}
		}

	}
});

