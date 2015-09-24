/**
 * 下拉菜单树
 * 
 */

var comboTreeSelector = {
	comboControllers:[],
	BRANCH:"branch",
	USER:"user",
	
	/**
	 * 树初始化函数
	 * 
	 * @param idname 页面DIV的ID
	 * @param dataSource 数据
	 * @param isMulti 是否为多选
	 * @param isSelectBranch 是否可以选择机构
	 */
	initComboTree:function(idname,dataSource,isMulti,isSelectBranch,isUnselectLevel,cascadeCheck,isCheckUserStatus){
		
		var newData = jQuery.extend(true, {}, dataSource);

		$.each(newData,function(i){
			var a = newData[i];
			if(a.type == comboTreeSelector.USER){
				a.id = "00" + a.id;
				a.hasChildren = false;
				a.isHasChildren = false;
			}
		});
		
		var comboController = {"idname":idname,
		"isMulti":isMulti,
		"isSelectBranch":isSelectBranch,
		"isCheckUserStatus":isCheckUserStatus,
		"isUnselectLevel":isUnselectLevel==null?0:isUnselectLevel,
		"nodeData":null,
		"dataSource":newData};
		
		var controllerArray = new Array();
		$.each(comboTreeSelector.comboControllers,function(i){
			controllerArray.push(comboTreeSelector.comboControllers[i].idname); 
		});
	
		if($.inArray(idname, controllerArray)== -1){
			comboTreeSelector.comboControllers.push(comboController);
		} else {
			var i = $.inArray(idname, controllerArray);
			if(i != -1){
				comboTreeSelector.comboControllers[i] = comboController;
			}
		}
		
		$("#" + idname + "choose").attr("myvalue",idname);
		$("#" + idname + "position").attr("myvalue",idname);
		
		var cascadeCheckFlg = true;
		if(cascadeCheck ==null){
			cascadeCheckFlg = false;
		} else {
			cascadeCheckFlg = cascadeCheck;
		}
		
		var treeData = new Array();
		var pidList = comboTreeSelector.getPidList(newData);
		$.each(pidList,function(i){
			var pid = pidList[i];
			var datas = comboTreeSelector.getChildrenNodes(newData,pid);
			$.each(datas,function(j){
				var data = datas[j];
				if($.inArray(data, treeData)== -1){
					treeData.push(data);
				}
			});
		});

		//树初始化
		$("#" + idname + "comboTree").omTree({
			dataSource:treeData,
			simpleDataModel:true,
			showIcon:false,
			showCheckbox: isMulti,
			cascadeCheck:cascadeCheckFlg,
			onBeforeExpand:function(node){
				var nodeDom = $("#"+node.nid);
				if(nodeDom.hasClass("hasChildren")){
					nodeDom.removeClass("hasChildren");
					
					var controller = comboTreeSelector.getMyController(idname);
					var treeData = comboTreeSelector.getChildrenNodes(controller.dataSource,node.id);
					$("#" + idname + "comboTree").omTree("insert", treeData, node);

					if(controller.isMulti){
						if(controller.nodeData != null){
							$.each(controller.nodeData,function(i){
								var data1 = controller.nodeData[i];
								var target = $("#" + idname + "comboTree").omTree("findNode", "id", data1.id);
								if(target != null){
									$("#" + idname + "comboTree").omTree('check',target);
								}
							});	
						}
					}
				}
				return true;
			},
			onSelect:function(nodedata){
				if(!isMulti) {
					//单选
					var isReturnFlag = false;
					$.each(comboTreeSelector.comboControllers,function(i){
						var node = comboTreeSelector.comboControllers[i];
						if(idname == node.idname){
							if(!node.isSelectBranch){
								if(nodedata.type == comboTreeSelector.BRANCH){
									alert("不能选择机构！");
									isReturnFlag = true;
									return false;
								}
							}
							if(nodedata.type == comboTreeSelector.BRANCH){
								var level = comboTreeSelector.getNodeLevel($("#" + idname + "comboTree"), nodedata, 1);
								if(level <= node.isUnselectLevel){
									alert("不能选择该级别的机构！");
									isReturnFlag = true;
									return false;
								}	
							}
							if(node.isCheckUserStatus){
								if(nodedata.type == comboTreeSelector.USER){
									if(nodedata.userStatus == "0"){
										alert("您选择的用户【"+nodedata.text+"】，当前状态为 Out Of Office ！");
//										if(!confirm("该用户当前状态为Out Of Office，是否继续选择？")){
//											isReturnFlag = true;
//											return false;
//										}
									}
								}
							}


							node.nodeData = nodedata;
						}
					});
					
					if(isReturnFlag){
						return;
					}
					
					$("#" + idname + "position").val(nodedata.text);
					comboTreeSelector.hideDropList($("#" + idname + "position").attr("myvalue"));
				}
			},
			onCheck:function(node,event){
				var checkedNodes = [];//选中节点
				var checkedText = "";
				var controller = comboTreeSelector.getMyController(idname);
				
				var target = $("#" + idname + "comboTree").omTree("findNode", "id", node.id);
				var isChecked = $("#" + idname + "comboTree").omTree('isCheck',node);
				if(isChecked){
					if(!controller.isSelectBranch){
						if(node.type == comboTreeSelector.BRANCH){
							alert("不能选择机构！");
							$("#" + idname + "comboTree").omTree('uncheck',target);
							isReturnFlag = true;
							return false;
						}
					}
					if(node.type == comboTreeSelector.BRANCH){
						var level = comboTreeSelector.getNodeLevel($("#" + idname + "comboTree"), node, 1);
						if(level <= controller.isUnselectLevel){
							alert("不能选择该级别的机构！");
							$("#" + idname + "comboTree").omTree('uncheck',target);
							isReturnFlag = true;
							return false;
						}
					}
					if(controller.isCheckUserStatus){
						if(node.type == comboTreeSelector.USER){
							if(node.userStatus == "0"){
								alert("您选择的用户【"+node.text+"】，当前状态为 Out Of Office ！");
//								if(!confirm("该用户当前状态为Out Of Office，是否继续选择？")){
//									$("#" + idname + "comboTree").omTree('uncheck',target);
//									isReturnFlag = true;
//									return false;
//								}
							}
						}
					}

										
					if(controller.nodeData != null){
						var selectNode = node.id;
						var dataArray = new Array();
						$.each(controller.nodeData,function(i){
							dataArray.push(controller.nodeData[i].id);
						});
						if($.inArray(selectNode, dataArray)== -1){
							controller.nodeData.push(node);
						}
           				
					} else {
						controller.nodeData = new Array();
						controller.nodeData.push(node);
					}
					
					//是否为下级联动选择
					if(cascadeCheckFlg){
						//是否有下级节点
						if(node.isHasChildren){
							comboTreeSelector.setSelectNodesAsChildren(idname,controller,node);
						}
						
						comboTreeSelector.setSelectNodesAsParent(idname,controller,node);
					}
				} else {
					if(!controller.isSelectBranch){
						if(node.type == comboTreeSelector.BRANCH){
							return false;
						}
					}
					if(node.type == comboTreeSelector.BRANCH){
						var level = comboTreeSelector.getNodeLevel($("#" + idname + "comboTree"), node, 1);
						if(level <= controller.isUnselectLevel){
							return false;
						}
					}
//					if(controller.isCheckUserStatus){
//						if(node.type == comboTreeSelector.USER){
//							if(node.userStatus == "0"){
//								return false;
//							}
//						}
//					}
					var selectNode = node.id;
					var dataArray = new Array();
					$.each(controller.nodeData,function(i){
						dataArray.push(controller.nodeData[i].id);
					});
					var i = $.inArray(selectNode, dataArray);
    				controller.nodeData.splice(i,1);
    				
    				//是否为下级联动选择
					if(cascadeCheckFlg){
						//是否有下级节点
						if(node.isHasChildren){
							comboTreeSelector.setSelectNodesAsChildren(idname,controller,node);
						}

						comboTreeSelector.setSelectNodesAsParent(idname,controller,node);
					}
				}
				
				$.each(controller.nodeData,function(i){
					var node = controller.nodeData[i];
					if(checkedText == "") {
						checkedText = node.text;
					} else {
						// 拼成“node.key|node.key|node.key......”的形式
						checkedText = checkedText + "，" + node.text;
					}
				});
				$("#" + idname + "position").val(checkedText);

			}
		});
		$("#" + idname + "comboTree").omTree("collapseAll");
		//点击下拉按钮显示下拉列表
		
		$("#" + idname + "choose").click(function(){
			comboTreeSelector.showDropList($(this).attr("myvalue"));
		});
		//点击输入框显示下拉列表
		$("#" + idname + "position").click(function(){
			comboTreeSelector.showDropList($(this).attr("myvalue"));
		});
	},
	/**
	 * 显示下拉树
	 * 
	 */
	showDropList:function(idname){
		var cityInput = $("#" + idname + "position");
		var cityOffset = cityInput.offset();
		var topnum = cityOffset.top+cityInput.outerHeight();
		if($.browser.msie&&($.browser.version == "6.0"||$.browser.version == "7.0")){
    		topnum = topnum + 2;
    	}
    	$("#" + idname + "comboTreeDiv").css({left: cityOffset.left + "px",top: topnum +"px"}).show();
    	//body绑定mousedown事件，当事件对象非下拉框、下拉按钮等下拉列表隐藏。
    	$("body").bind("mousedown", {"idname":idname}, comboTreeSelector.onBodyDown);
	},
	
	/**
	 * 隐藏下拉树
	 * 
	 */
	hideDropList:function(idname){
		$("#" + idname + "comboTreeDiv").hide();
   		$("body").unbind("mousedown", {"idname":idname}, comboTreeSelector.onBodyDown);
	},
	
	/**
	 * 鼠标按下事件
	 * 
	 */
	onBodyDown:function(event){
		if (!(event.target.id == (event.data.idname + "choose") || event.target.id == (event.data.idname + "comboTreeDiv") || $(event.target).parents("#" + event.data.idname + "comboTreeDiv").length>0)) {
    		comboTreeSelector.hideDropList(event.data.idname);
    	}
	},
	
	/**
	 * 获得选中节点
	 * 
	 */
	getSelectedNodes:function(idname){
		var controller = comboTreeSelector.getMyController(idname);
		
		if(controller.isMulti){
			//多选的场合
			return controller.nodeData;
		} else {
			//单选的场合
			return controller.nodeData;
		}
	},
	
	/**
	 * 设置选中节点
	 * 
	 */
	setSelectedNodes:function(idname,idList){	
		var controller = comboTreeSelector.getMyController(idname);
				
		if(controller.isMulti){
			//多选的场合
			if(idList) {
				var checkedText = "";
				// 勾选设置				
				var nodes = comboTreeSelector.getNodes(controller.dataSource,idList);
				
				controller.nodeData = nodes;
				$.each(nodes,function(i){
					var data = nodes[i];
					var target = $("#" + idname + "comboTree").omTree("findNode", "id", data.id);
					if(target != null){
						$("#" + idname + "comboTree").omTree('check',target);
					}
					
					if(checkedText == "") {
						checkedText = data.text;
					} else {
						// 拼成“node.key|node.key|node.key......”的形式
						checkedText = checkedText + "，" + data.text;
					}
				});
				
				$("#" + idname + "position").val(checkedText);
			} else {
				controller.nodeData = null;
				$("#" + idname + "comboTree").omTree("checkAll",false);
				$("#" + idname + "position").val("");
			}

		} else {
			//单选的场合
			if(idList) {
				var ids = new Array();
				ids.push(idList)
				var nodes = comboTreeSelector.getNodes(controller.dataSource,ids);
				
				if(nodes){
					if(nodes != null && nodes.length > 0){
						controller.nodeData = nodes[0];
						$("#" + idname + "position").val(nodes[0].text);
					}		
				}

			} else {
				controller.nodeData = null;
				$("#" + idname + "position").val("");
			}

		}
	},	
	/**
	 * 根据人的accountNo回显fullname
	 */
	setSelectedAccountNoNodes:function(idname,accountNo){
		var controller = comboTreeSelector.getMyController(idname);
	  	if(accountNo) {
	  		var node = comboTreeSelector.getNodeByAccount(controller.dataSource,accountNo);
			controller.nodeData = node;
			$("#" + idname + "position").val(controller.nodeData.text);
		} else {
			controller.nodeData = null;
			$("#" + idname + "position").val("");
		}
	},
	/**
	 * 获得选中借点的层级
	 */
	getNodeLevel:function(treeContrl,node, level){
		var parentNode = treeContrl.omTree('getParent',node);
		if(parentNode == null){
			return level;
		} else {
			level = level + 1;
			return comboTreeSelector.getNodeLevel(treeContrl, parentNode, level);
		}
	},
	
	/**
	 * 设置选中节点，和孩子节点
	 */
	setSelectNodesAsChildren:function(idname,controller,node){
		var nodes = comboTreeSelector.getChildrenNodes(controller.dataSource,node.id);
		var isChecked = $("#" + idname + "comboTree").omTree('isCheck',node);
		if(nodes){
			if(nodes != null && nodes.length > 0){
				$.each(nodes,function(i){
					var data = nodes[i];
					var selectNode = data.id;
					var dataArray = new Array();
					$.each(controller.nodeData,function(j){
						dataArray.push(controller.nodeData[j].id);
					});
					if(isChecked){
						if($.inArray(selectNode, dataArray)== -1){
							controller.nodeData.push(data);
						}
					}else{
						var i = $.inArray(selectNode, dataArray);
						if(i != -1){
							controller.nodeData.splice(i,1);
						}
					}
	
					comboTreeSelector.setSelectNodesAsChildren(idname,controller,data);
				});
			}
		}
	},
	
	/**
	 * 设置选中节点，和父节点
	 */
	setSelectNodesAsParent:function(idname,controller,node){
		var parentNode = $("#" + idname + "comboTree").omTree('getParent',node);
		if(parentNode != null){
			var isChecked = $("#" + idname + "comboTree").omTree('isCheck',parentNode);
			var selectNode = parentNode.id;
			var dataArray = new Array();
			$.each(controller.nodeData,function(i){
				dataArray.push(controller.nodeData[i].id);
			});
			if(isChecked){
				if($.inArray(selectNode, dataArray)== -1){
					controller.nodeData.push(parentNode);
				}
			}else{
				var i = $.inArray(selectNode, dataArray);
				if(i != -1){
					controller.nodeData.splice(i,1);
				}
			}
			comboTreeSelector.setSelectNodesAsParent(idname,controller,parentNode);
		}
	},
	
	/**
	 * 根据父ID得到子节点
	 */
	getChildrenNodes:function(dataSource,pid){
		var childrenNodes = new Array();
		if(dataSource != null){
			$.each(dataSource,function(i){
				var node = dataSource[i];
				if(node.pid == pid){
					childrenNodes.push(node);
				}
			});
		}

		return childrenNodes;
	},
	
	/**
	 * 根据idList得到节点
	 */
	getNodes:function(dataSource,idList){
		var nodes = new Array();
		if(idList != null){
			$.each(idList,function(i){
				var id = idList[i];
				if(dataSource != null){
					$.each(dataSource,function(j){
						var node = dataSource[j];
						if(node.id == id){
							nodes.push(node);
							return false;
						}
					});
				}
			});
		}
		return nodes;
	},
	
	/**
	 * 根据account得到节点
	 */
	getNodeByAccount:function(dataSource,account){
		var result = {};
		if(account){
			if(dataSource != null){
				$.each(dataSource,function(i){
					var node = dataSource[i];
					if(node.account == account){
						result = node;
						return false;
					}
				});
			}
		}
		return result;
	},
	
	/**
	 * 获得当前控件
	 */
	getMyController:function(idname){
		var controller = {};
		$.each(comboTreeSelector.comboControllers,function(i){
			var node = comboTreeSelector.comboControllers[i];
			if(idname == node.idname){
				controller = node;
			}
		});
		return controller;
	},
	
	getPidList:function(datas){
		var pidList = new Array();
		if(datas){
			if(datas[0].pid == "0"){
				pidList.push("0");
			} else {
				$.each(datas,function(i){
					var data1 = datas[i];
					var setFlag = true;
					$.each(datas,function(j){
						var data2 = datas[j];
						if(data1.pid == data2.id){
							setFlag = false;
							return false;
						}
					});
					if(setFlag){
						pidList.push(data1.pid);
					}
				});
			}
		}
		return pidList;
	}
}