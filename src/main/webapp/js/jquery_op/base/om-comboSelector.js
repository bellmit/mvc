(function($) {
  $.omWidget('om.comboTreeSelector', {
        options: {
			/**
             * 树节点是否可拖拽。
             * @type Boolean
             * @default false
             * @example
             * $("#mytree").omTree({draggable:true});
             */
			
			isSelectBranch:true,
			isUnselectLevel:0,
			isCheckUserStatus:true,
			isMulti:false,
			isTree:false,
			tree:{
				simpleDataModel:true,
				showIcon:false,
				showCheckbox:false,
				cascadeCheck:false
			},
			dialog:{
				title:'选择窗口',
				autoOpen: false,
				height: 500,
				width: 500,
				modal: false
			},
			branch:'branch',
			user:'user',
			userStatus:'0',
			multiSeparator : ',',
			selectedIndex:-1
         },
        _create:function(){
           //和属性等无关的初始化动作
           this.element.data("nodes", []).data("selectedDatas",[]).data("init_dataSource",[]).data("selectedData",{});
           var valueEl = this.element;
           valueEl.css({width:"220px",border:"1px solid #888888"});
           var span = $('<span class="om-combo om-widget om-state-default"></span>').insertAfter(valueEl).wrapInner(valueEl);
           span.css({position:"relative"});
           this.textInput = valueEl.clone().removeAttr("id").removeAttr("name").appendTo(span);
           valueEl.hide();
           var dropList = $('<div class="comboTreeDiv1"></div>').insertAfter(span);
           dropList.attr("id",valueEl.attr("id") + "dropList");
           dropList.hide();
           this.dropList = dropList;//存放匹配数据
           var treeDialogDiv = $('<div></div>');
           treeDialogDiv.css("background","#FFFFFF");
           treeDialogDiv.hide();
           this.treeDialog = treeDialogDiv;//弹出窗口
                                
//           var titleDiv = $('<div class="titleDiv"><div style="float:left;margin:3px 0 3px 3px;">已选择</div></div>').insertBefore(selectedDiv);
		   var titleDiv = $('<div class="titleDiv"><div style="float:left;margin:3px 0 3px 3px;">已选择</div></div>');
           titleDiv.css({width:span.css("width"),height:"20px"});
           
//           var button = $('<button class="comboTreeButton"></button>').appendTo(titleDiv);
		   var button = $('<button class="comboTreeButton"></button>').appendTo(span);
           this.button = button;//dialog按钮
           
           //           var selectedDiv = $('<div class="representation"></div>').insertBefore(span);
		   var selectedDiv = $('<div class="representation"></div>').appendTo(span);
		   selectedDiv.css({width:span.css("width")});
           this.selectedDiv = selectedDiv;
           
           
//           var selectedUL = $('<ul class="items"><li style="text-align:center;">请选择</li></ul>').appendTo(selectedDiv);
		   var selectedUL = $('<ul></ul>').appendTo(selectedDiv);
//           var selectedUL = $('<ul><li style="text-align:center;">请选择</li></ul>');
//           var selectedUL = $('<div></div>').appendTo(selectedDiv);
           this.selectedUL = selectedUL;
        },
        _init:function(){
           	//初始化，包括数据的获取，事件的绑定等。
           	var options = this.options,
		   		inputEl = this.textInput,
		   		valueEl = this.element,
		   		source = options.dataSource,
		   		button = this.button
		   		values = options.value,
		   		selectedDiv = this.selectedDiv;
		   	
		   	$.each(source,function(i){
				var a = source[i];
				if(a.type == options.user){
					a.id = "00" + a.id;
					a.hasChildren = false;
					a.isHasChildren = false;
				}
			});
			
			options.dataSource = source;
		   	if(options.isTree){
				this._loadDialogData(source);
		   	} else {
		   		this.button.hide();
		   	}
		   	
		   	
		   	if(options.isMulti){
		   		selectedDiv.css({height:"50px"});
		   	} else {
		   		selectedDiv.css({height:"25px"});
		   	}
		   	
		   	if(values!=null && values.length > 0){
		   		if(options.isMulti){
			   		var dataArray = $.grep(source,function(n,i){
		   				return $.inArray(n.id,values) > -1;
					});
					valueEl.data('selectedDatas',dataArray);	
		   		} else {
		   			var index = $.inArray(values,source);
		   			if(index > -1 && index < source.length){
		   				var node = source[index];
		   				valueEl.data('selectedData',node);
		   			}
		   		}
		   		this._setSelected();
		   		this._checkNodes();
		   		this._setSelectedItem(this);
		   	}
		   	
		   	var unusable = options.disabled || options.readOnly;
		   	if (unusable) {
				inputEl.attr("disabled","disabled");
				button.attr("disabled","disabled");
            } else {
                this._bindEvent();
            }
        },
        _loadDialogData:function(records){
        	var options = this.options,
        	treeDialog = this.treeDialog,
        	self = this,
        	inputEl = this.textInput,
        	valueEl = this.element;
 
        	//初始化Dialog
        	if(options.dialog){
        		treeDialog.omDialog(options.dialog);
        	}
        	
        	//初始化树
        	options.tree.onBeforeExpand = function(node){
        		if(node.nid == null){
        			return;
        		}
        		var nodeDom = $("#"+node.nid);
        		if(nodeDom.hasClass("hasChildren")){
        			nodeDom.removeClass("hasChildren");
        			var treeDatas = self._getChildrenNodes(self,node.id);
        			self.tree.omTree("insert", treeDatas, node);
        			
        			if(options.isMulti){
        				var selectedDatas = valueEl.data('selectedDatas');
        				if(selectedDatas != null){
        					$.each(selectedDatas,function(i){
        						var data = selectedDatas[i];
        						var target = self.tree.omTree("findNode", "id", data.id);
        						if(target != null){
        							self.tree.omTree('check',target);
        						}
        					});
        				}
        			}
        		}
        		return true;
        	};
        	if(options.isMulti){
        		//多选
        		options.tree.showCheckbox = true;
        		options.tree.onCheck = function(node,event){
        			var isChecked = self.tree.omTree('isCheck',node);
        			if(isChecked){
	        			if(!options.isSelectBranch){
	        				if(node.type == options.branch){
	        					alert("不能选择机构【"+node.text+"】！");
	        					var target = self.tree.omTree("findNode", "id", node.id);
	        					self.tree.omTree('uncheck',target);
	        					return;
	        				}
	        			}
		        		if(options.isCheckUserStatus){
	        				if(node.type == options.user){
	        					if(node.userStatus == options.userStatus){
	        						alert("您选择的用户【"+node.text+"】，当前状态为 Out Of Office ！");
	        					}
	        				}
	        			}
	        			
	        			// 级联的场合
	        			self._setValue(node);
	        			if(options.tree.cascadeCheck){
	        				if(node.isHasChildren){
	        					self._setCascadeChildrenNodes(self,node);
	        				}
	        				self._setCascadeParentNodes(self,node);
	        			} 
	        			self._setSelectedItem(self);
        			} else {
        				self._removeValue(node);
        				// 级联的场合
	        			if(options.tree.cascadeCheck){
	        				if(node.isHasChildren){
	        					self._removeCascadeChildrenNodes(self,node);
	        				}
	        				self._removeCascadeParentNodes(self,node);
	        			}
        				self._setSelectedItem(self);
        			}
        			
        		};
        	} else {
        		//单选
        		options.tree.onSelect = function(node,event){
        			if(!options.isSelectBranch){
        				if(node.type == options.branch){
        					alert("不能选择机构【"+node.text+"】！");
        					return;
        				}
        			}
        			if(options.isCheckUserStatus){
        				if(node.type == options.user){
        					if(node.userStatus == options.userStatus){
        						alert("您选择的用户【"+node.text+"】，当前状态为 Out Of Office ！");
        					}
        				}
        			}
        			
        			self._setValue(node);
        			self._setSelectedItem(self);
        		};
        	}
                
        	options.tree.dataSource = self._getPidList(records);
        	self.tree=$('<ul></ul>');
			self.tree.appendTo(treeDialog)
			self.tree.omTree(options.tree);
        	
        },
        _setCascadeChildrenNodes:function(self,node){
        	var childrenNodes = self._getChildrenNodes(self,node.id);
        	if(childrenNodes){
        		if(childrenNodes != null && childrenNodes.length > 0){
        			$.each(childrenNodes,function(i,childrenNode){
        				self._setValue(childrenNode);
        				self._setCascadeChildrenNodes(self,childrenNode);
        			});
        		}
        	}
        },
        _removeCascadeChildrenNodes:function(self,node){
        	var childrenNodes = self._getChildrenNodes(self,node.id);
        	if(childrenNodes){
        		if(childrenNodes != null && childrenNodes.length > 0){
        			$.each(childrenNodes,function(i,childrenNode){
        				self._removeValue(childrenNode);
        				self._removeCascadeChildrenNodes(self,childrenNode);
        			});
        		}
        	}
        },
        _setCascadeParentNodes:function(self,node){
        	var parentNode = self.tree.omTree("getParent", node);
        	if(parentNode != null){
        		var isChecked = self.tree.omTree('isCheck',parentNode);
        		if(isChecked){
	        		self._setValue(parentNode);
        		}
        		self._setCascadeParentNodes(self,parentNode);
        	} else {
        		var parentNode = self._getParentNode(self,node.pid);
        		if(parentNode != null && parentNode.length > 0){
        			var isChecked = self._checkParentNodeChecked(self,parentNode[0]);
	        		if(isChecked){
		        		self._setValue(parentNode[0]);
	        		}
	        		self._setCascadeParentNodes(self,parentNode[0]);
        		}
        	}
        },
        _checkParentNodeChecked:function(self,node){
        	var valueEl = self.element;
        	var result = true;
        	var childrenNodes = self._getChildrenNodes(self,node.id);
        	var selectedDatas = valueEl.data('selectedDatas');
        	$.each(childrenNodes,function(i,childrenNode){
        		var nodes = $.grep(selectedDatas,function(n,i){
	    			return childrenNode.id == n.id;
	    		});
	    		if(nodes == null || nodes.length <= 0){
	    			result = false;
	    			return false;
	    		}
        	});
        	return result;
        },
        _removeCascadeParentNodes:function(self,node){
        	var parentNode = self._getParentNode(self,node.pid);
        	if(parentNode != null && parentNode.length > 0){
        		self._removeValue(parentNode[0]);
        		self._removeCascadeParentNodes(self,parentNode[0]);
        	}
        },
        _setData:function(self,node){
        	var valueEl = self.element;
        	var options = this.options;
        	var dataArray = new Array();
        	
        	var addFlag = true;
        	var input = [];
    		$.each(valueEl.data('selectedDatas'),function(i,n){
    			input.push(n.id);
    			if(n.id == node.id){
    				addFlag = false;
    				return false;
    			}
    		});
    		if(addFlag){
    			input.push(node.id);
    			valueEl.data('selectedDatas').push(node);
    		}
    		options.value = input;
        },
        _getChildrenNodes:function(self,id){
        	var options = self.options;
        	if(options.dataSource != null){
	   		    var childrenNodes = $.grep(options.dataSource,function(n,i){
	    			return id == n.pid;
	    		});
	    		return childrenNodes;
        	} else {
        		return [];
        	}
        },
        _getParentNode:function(self,id){
        	var options = self.options;
        	if(options.dataSource != null&&id){
        		var parentNode = $.grep(options.dataSource,function(n,i){
	    			return id == n.id;
	    		});
	    		return parentNode;
        	} else {
        		return null;
        	}
        },
        _getPidList:function(datas){
        	var pidList = new Array();
        	if(datas != null){
        		if(datas[0].pid == "0"){
        			pidList.push(datas[0]);
        		} else {
        			$.each(datas,function(i,data){
        				var list =  $.grep(datas,function(n,j){
			    			return data.pid == n.id;
			    		});
			    		if(list == null || list.length <= 0){
			    			pidList.push(data);
			    		}
        			});
        		}
        	}
        	return pidList;
        },
        _bindEvent:function(){
        	var self = this,
        	options = self.options,
        	button = self.button,
        	inputEl = self.textInput,
        	valueEl = self.element,
        	dropList = self.dropList,
        	treeDialog = self.treeDialog;
        	
        	//Dialog事件
        	if(options.isTree){
        		button.click(function(){
        			treeDialog.omDialog('open');
        		});
        	}
        	
        	//下拉匹配Div事件
        	options.selectedIndex = -1;
        	inputEl.blur(function(){
    			self.dropList.hide();
    			self._setSelected();
    			self._setSelectedItem(self);
        	});
        	inputEl.keyup(function(event){
        		var ie = (document.all)? true:false;
        		if (ie){
        			var keyCode=event.keyCode;
        			if (keyCode==40||keyCode==38){ //下上
        				var isUp=false;
        				if(keyCode==40) isUp=true ;
        				self._chageSelection(isUp,self);
        			}else if (keyCode==13){//回车
        				self._outSelection(self);
        			}else{
						self._checkAndShow(self);
        			}
        		} else {
					self._checkAndShow(self);
        		}
				self._divPosition(self);
        	});
        	inputEl.focus(function(){
        		self._checkAndShow(self);
        	});
        },
        _chageSelection:function(isUp,self){
        	var options = self.options,
	        	dropList = self.dropList;
	        	
	        if(dropList.is(":hidden")){
	        	dropList.show();
	        } else {
		        if (isUp)
					options.selectedIndex++;
				else
					options.selectedIndex--;
	        }
	        
	        var maxIndex = dropList.children().length-1;
	        if (options.selectedIndex<0){options.selectedIndex=0;}
	        if (options.selectedIndex>maxIndex) {options.selectedIndex=maxIndex;}
	        var intTmp; //循环用的:)
	        for (intTmp=0;intTmp<=maxIndex;intTmp++){
	        	if (intTmp==options.selectedIndex){
	        		dropList.children()[intTmp].className = "sman_selectedStyle";
	        	} else {
	        		dropList.children()[intTmp].className = "";
	        	}
	        }
        },
        _outSelection:function(self){
        	var options = self.options,
	        	dropList = self.dropList;
	        
	        if(options.selectedIndex != -1){
	        	dropList.children()[options.selectedIndex].fireEvent("onmousedown");
	        }
	        dropList.hide();
        },
        _checkAndShow:function(self){
        	var strInput = "";
        	var options = self.options,
        	inputEl = self.textInput,
        	dropList = self.dropList;
			
			strInput = inputEl.val();
//        	var input = [];
//		    input = $.grep(inputIds.split(options.multiSeparator),function(n,i){
//    			return n != '';
//    		});
//    		if(input!=null && input.length > 0){
//    			strInput = $.trim(input[input.length - 1]);
//    		}
    		
    		if (strInput!=""){
    			self._divPosition(self);
    			options.selectedIndex = -1;
    			dropList.html('');
    			var dataArray = $.grep(options.dataSource,function(n,i){
    				if(n.text.substr(0,strInput.length).toUpperCase()==strInput.toUpperCase()
    				|| (n.mail!=null&&n.mail.substr(0,strInput.length).toUpperCase()==strInput.toUpperCase())
    				|| (n.type == options.user&&n.pname!=null&&n.pname.substr(0,strInput.length).toUpperCase()==strInput.toUpperCase())){
    					return true;
    				}
    				return false;
    			});
    			
    			$.each(dataArray,function(i,dataDetail){
    				var keyw;
    				if(dataDetail.id){
    					var text,mail,pname;
    					if(dataDetail.text.substr(0,strInput.length).toUpperCase()==strInput.toUpperCase()){
    						text = "<b>" + dataDetail.text.substr(0,strInput.length) + "</b>" + dataDetail.text.substr(strInput.length,dataDetail.text.length);
    					} else {
    						text = dataDetail.text;
    					}
	    				if(dataDetail.mail!=null&&dataDetail.mail.substr(0,strInput.length).toUpperCase()==strInput.toUpperCase()){
							mail = "<b>" + dataDetail.mail.substr(0,strInput.length) + "</b>" + dataDetail.mail.substr(strInput.length,dataDetail.mail.length);
						} else {
							mail = dataDetail.mail;
						}
						if(dataDetail.type == comboTreeSelector.USER&&dataDetail.pname!=null&&dataDetail.pname.substr(0,strInput.length).toUpperCase()==strInput.toUpperCase()){
							pname = "<b>" + dataDetail.pname.substr(0,strInput.length) + "</b>" + dataDetail.pname.substr(strInput.length,dataDetail.pname.length);
						} else {
							pname = dataDetail.pname;
						}
						if(dataDetail.type == options.user){
				    		keyw = text + " - " + mail + "（"+pname+"）";
				    	} else {
				    		keyw = text;
				    	}
				    	self._addOption(keyw,self,dataDetail);
    				}
    			});
    			dropList.show();
    		} else {
    			dropList.hide();
    		}
        },
        _addOption:function(keyw,self,dataDetail){
        	var dropList = self.dropList;
        	var divItem = $("<div><div/>");
        	divItem.html(keyw);
        	divItem.css("Line-height","30pt");
	        divItem.bind("mouseover",function(){
				this.className = "sman_selectedStyle";
			});
			divItem.bind("mouseout",function(){
				this.className = "";
			});
			divItem.bind("mousedown",function(){
				self._onMouseDownFunction(dataDetail);
			});
			divItem.appendTo(dropList);
        },
        _onMouseDownFunction:function(node){
        	var inputEl = this.textInput,
        	options = this.options;
	       	if(!options.isSelectBranch){
				if(node.type == options.branch){
					alert("不能选择机构【"+node.text+"】！");
					this._setSelected();
					this.dropList.hide();
					this._setSelectedItem(this);
					return;
				}
			}
			if(options.isCheckUserStatus){
				if(node.type == options.user){
					if(node.userStatus == options.userStatus){
						alert("您选择的用户【"+node.text+"】，当前状态为 Out Of Office ！");
					}
				}
			}
			
			inputEl.val('');
			this._setValue(node);
	        // 级联的场合
			if(options.tree.cascadeCheck){
				if(node.isHasChildren){
					this._setCascadeChildrenNodes(this,node);
				}
				this._setCascadeParentNodes(this,node);
			} 
			this._setSelectedItem(this);
			this._checkNodes();
			this.dropList.hide();
        },
        _divPosition:function(self){
	       	var inputEl = self.textInput,
	        	dropList = self.dropList;
	        var cityOffset = inputEl.offset();
	        var topnum = cityOffset.top + inputEl.outerHeight();
		    if($.browser.msie&&($.browser.version == "6.0"||$.browser.version == "7.0")){
	    		topnum = topnum + 2;
	    	}
	    	dropList.css({left: cityOffset.left + "px",top: topnum +"px"});
	    	var divId = self.element.attr("id") + "dropList";
	    	$("body").bind("mousedown", function(event){
	    		if (!(event.target.id == divId || $(event.target).parents("#" + divId).length>0)) {
	    			self.dropList.hide();
	    			self._setSelected();
	    			self._setSelectedItem(self);
	    			$("body").unbind("mousedown");
	    		}
	    	});
        },
        _setValue:function(node){
        	var inputEl = this.textInput,valueEl = this.element;
        	var options = this.options;
        	var inputIds = valueEl.val();
        	var inputTexts = inputEl.val();
        	
        	var input = [],value = [];
        	if(node != ''){
	        	if(options.isMulti){
	        		//多选
	        		var addFlag = true;
//	        		var selectedDatas = valueEl.data('selectedDatas');
//					var dataArray = new Array();
	        		$.each(valueEl.data('selectedDatas'),function(i,n){
	        			input.push(n.id);
	        			value.push(n.text);
	        			if(n.id == node.id){
	        				addFlag = false;
	        				return false;
	        			}
	        		});
	        		if(addFlag){
	        			input.push(node.id);
	        			value.push(node.text);
	        			valueEl.data('selectedDatas').push(node);
//	        			valueEl.data('selectedDatas',selectedDatas);
	        		}
//        			inputEl.val(value.join(options.multiSeparator));
	        		valueEl.val(input.join(options.multiSeparator));

//	        		this._setSelectedItem(this);
	        		
	        	} else {
	        		//单选
	        		var index = inputIds.indexOf(node.id);
	        		if(index < 0){
		        		input.push(node.id);
		        		value.push(node.text);
		        		valueEl.data('selectedData',node);
//		        		inputEl.val(value.join(''));
		        		valueEl.val(input.join(''));
//		        		this._setSelectedItem(this);
	        		}
	        	}
        	} else {
        		valueEl.val('');
        		inputEl.val('');
        		valueEl.data('selectedDatas',[]);
        		valueEl.data('selectedData',{});
        		options.value = [];
        	}
   
			options.value = input;
        },
        _removeValue:function(node){
        	var inputEl = this.textInput,valueEl = this.element;
        	var options = this.options;
        	var inputIds = valueEl.val();
        	var inputTexts = inputEl.val();
        	var tree = this.tree;
        	
        	if(node != ''){
        		if(options.isMulti){
        			var selectedDatas = valueEl.data('selectedDatas');
	        		var input = [],value=[];
	    			$.each(selectedDatas,function(i,n){
	        			input.push(n.id);
	        			value.push(n.text);
	        		});
		    		var ids = $.grep(input,function(n,i){
	        			return n != node.id;
	        		});
	        		var values = $.grep(value,function(n,i){
	        			return n != node.text;
	        		});
		    		var nodes = $.grep(selectedDatas,function(n,i){
	        			return n.id != node.id;
	        		});
		    		input = ids;
		    		value = values;
				    valueEl.data('selectedDatas',nodes);
//	    			inputEl.val(value.join(options.multiSeparator));
	        		valueEl.val(input.join(options.multiSeparator));
	        		options.value = input;
        		} else {
        			valueEl.data('selectedData',{});
        			options.value = {};
//        			inputEl.val('');
	        		valueEl.val('');
        		}
        	}
//        	this._setSelectedItem(this);
        },
        _setSelected:function(){
        	var inputEl = this.textInput,valueEl = this.element;
        	var options = this.options;
        	
        	var input = [],value=[];
        	if(options.isMulti){
        		//多选
        		var selectedDatas = valueEl.data('selectedDatas');
        		$.each(selectedDatas,function(i,n){
        			input.push(n.id);
        			value.push(n.text);
        		});
//    			inputEl.val(value.join(options.multiSeparator));
        		valueEl.val(input.join(options.multiSeparator));
        		this._setSelectedItem(this);
        	} else {
        		//单选
        		var selectedData = valueEl.data('selectedData');
    			input.push(selectedData.id);
        		value.push(selectedData.text);
//        		inputEl.val(value.join(''));
	        	valueEl.val(input.join(''));
	        	this._setSelectedItem(this);
        	}
        },
        _setSelectedItem:function(self){
        	var valueEl = self.element;
        	var options = self.options;
        	var selectedUL = self.selectedUL;
        	selectedUL.html('');

        	if(options.isMulti){
	        	var selectedDatas = valueEl.data('selectedDatas');
	        	$.each(selectedDatas,function(i,n){
	        		self._createSelectedItem(n);
	        	});
        	} else {
        		var selectedData = valueEl.data('selectedData');
        		self._createSelectedItem(selectedData);
        	}

        },
        _createSelectedItem:function(node){
        	var self = this;
        	var selectedUL = this.selectedUL;
        	var selectedDiv = this.selectedDiv;
        	var options = self.options;
        	if(!options.isMulti){
        		if(node.id==null||node.id==''){
        			return;
        		}
        	}
        	var selectedLI = $('<li class="selectItem"></li>').appendTo(selectedUL);
        	var span = $('<span></span>').appendTo(selectedLI);
        	span.html(node.text);
        	var button = $('<button class="itemClose"></button>').insertAfter(span);
        	button.click(function(){
        		self._removeValue(node);
        		if(options.isMulti){
        			// 级联的场合
					if(options.tree.cascadeCheck){
						if(node.isHasChildren){
							self._removeCascadeChildrenNodes(self,node);
						}
						self._removeCascadeParentNodes(self,node);
					}
        			self._checkNodes();
        		}
        		self._setSelectedItem(self);
        	});
        },
        _checkNodes:function(){
        	var options = this.options,
        	tree = this.tree,
        	valueEl = this.element;
        	
        	this.tree.omTree("checkAll",false);
        	var selectedDatas = valueEl.data('selectedDatas');
        	$.each(selectedDatas,function(i,n){
        		var target = tree.omTree("findNode", "id", n.id);
        		if(target != null){
        			tree.omTree('check',target);
        		}
        	});
        },
        getSelectedData:function(){
        	var options = this.options,valueEl = this.element;
        	
        	var node;
        	if(options.isMulti){
        		node = valueEl.data('selectedDatas');
        		if(node == null||node.length <= 0){
        			node = null;
        		}
        	} else {
        		node = valueEl.data('selectedData');
        		if(node.id==null || node.id==''){
        			node = null;
        		}
        	}
        	
        	return node;
        },
        clear:function(){
        	var inputEl = this.textInput,valueEl = this.element;
        	var options = this.options;
        	
    	    valueEl.val('');
    		inputEl.val('');
    		valueEl.data('selectedDatas',[]);
    		valueEl.data('selectedData',{});
    		options.value = [];
        }
    });
})(jQuery);