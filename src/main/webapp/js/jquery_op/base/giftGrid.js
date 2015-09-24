;(function($) {
	$.omWidget('om.omGiftGrid',$.om.omGrid, {
		_buildTableHead:function(){
            var op=this.options,
                el=this.element,
                grid = el.closest('.om-grid'),
                cms=this._getColModel(),
                allColsWidth = 0, //colModel的宽度
                indexWidth = 0, //colModel的宽度
                checkboxWidth = 0, //colModel的宽度
                autoExpandColIndex = -1;
                thead=$('<thead></thead>');
                tr=$('<tr></tr>').appendTo(thead);
            //渲染序号列
            if(op.showIndex){
                var cell=$('<th></th>').attr({axis:'indexCol',align:'center'}).addClass('indexCol').append($('<div class="indexheader" style="text-align:center;width:25px;"></div>'));
                tr.append(cell);
                indexWidth=25;
            }
            //渲染checkbox列
            if(!op.singleSelect){
                var cell=$('<th></th>').attr({axis:'checkboxCol',align:'center'}).addClass('checkboxCol').append($('<div class="checkboxheader" style="text-align:center;width:17px;"></div>'));
                tr.append(cell);
                checkboxWidth=17;
            }
            //渲染colModel各列
            for (var i=0,len=cms.length;i<len;i++) {
                var cm=cms[i],cmWidth = cm.width || 60,cmAlign=cm.align || 'center';
                if(cmWidth == 'autoExpand'){
                    cmWidth = 0;
                    autoExpandColIndex = i;
                }
                var thCell=$('<div></div>').html(cm.header).css({'text-align':cmAlign,width:cmWidth});
                cm.wrap && thCell.addClass('wrap');
                var th=$('<th></th>').attr('axis', 'col' + i).addClass('col' + i).append(thCell);
                if(cm.name) {
                    th.attr('abbr', cm.name);
                }
                if(cm.align) {
                    th.attr('align',cm.align);
                }
                //var _div=$('<div></div>').html(cm.header).attr('width', cmWidth);
                allColsWidth += cmWidth;
                tr.append(th);
            }
            //tr.append($('<th></th'));
            el.prepend(thead);
            
            
            $('table',this.hDiv).append(thead);
            this._fixHeaderWidth(autoExpandColIndex , allColsWidth);
            this.thead=thead;
            thead = null;
        },
		//绑定行选择/行反选/行单击/行双击等事件监听
        _bindSelectAndClickEnvent:function(){
            var self=this;
            this.tbody.unbind();
            //如果有checkbox列则绑定事件
            if(!this.options.singleSelect){ //可以多选
                // 全选/反选,不需要刷新headerChekcbox的选择状态
//                $('th.checkboxCol span.checkbox',this.thead).click(function(){
//                    var thCheckbox=$(this),trSize=self._getTrs().size();
//                    if(thCheckbox.hasClass('selected')){ //说明是要全部取消选择
//                        thCheckbox.removeClass('selected');
//                        for(var i=0;i<trSize;i++){
//                            self._rowDeSelect(i);
//                        }
//                    }else{ //说明是要全选
//                        thCheckbox.addClass('selected');
//                        for(var i=0;i<trSize;i++){
//                            self._rowSelect(i);
//                        }
//                    }
//                });
//                //行单击,需要刷新headerChekcbox的选择状态
//                this.tbody.delegate('tr.om-grid-row','click',function(event){
//                    var row=$(this),index=self._getRowIndex(row);
//                    alert(index);
//                    if(row.hasClass('om-state-highlight')){ //已选择
//                        self._rowDeSelect(index);
//                    }else{
//                        self._rowSelect(index);
//                    }
//                    self._refreshHeaderCheckBox();
//                    self._trigger("onRowClick",event,index,self._getRowData(index));
//                });
                //checkbox单击,需要刷新headerChekcbox的选择状态
                this.tbody.delegate('td.checkboxCol span.checkbox','click',function(event){
                	
                	var tdCheckbox=$(this),index=self._getRowIndexByCheckBox(tdCheckbox);
                	if(tdCheckbox.hasClass('selected')){
						self._rowDeSelect(index);
                    }else{
    					self._rowSelect(index);
                    }
                    self._refreshHeaderCheckBox();
                    self._trigger("onRowClick",event,index,self._getRowData(index));
                });
                //行双击
//                this.tbody.delegate('tr.om-grid-row','dblclick',function(event){
//                    var row=$(this),index=self._getRowIndex(row);
//                    if(row.hasClass('om-state-highlight')){ //已选择
//                        //do nothing
//                    }else{
//                        self._rowSelect(index);
//                        self._refreshHeaderCheckBox();
//                    }
//                    self._trigger("onRowDblClick",event,index,self._getRowData(index));
//                });
            }else{ //不可多选
                //行单击
                this.tbody.delegate('tr.om-grid-row','click',function(event){
                    var row=$(this),index=self._getRowIndex(row);
                    if(row.hasClass('om-state-highlight')){ //已选择
                        // no need to deselect another row and select this row
                    }else{
                        var lastSelectedIndex = self._getRowIndex(self.tbody.find('tr.om-state-highlight:not(:hidden)'));
                        (lastSelectedIndex != -1) && self._rowDeSelect(lastSelectedIndex);
                        self._rowSelect(index);
                    }
                    self._trigger("onRowClick",event,index,self._getRowData(index));
                });
                
                //行双击,因为双击一定会先触发单击，所以对于单选表格双击时这一行一定是选中的，所以不需要强制双击前选中
                this.tbody.delegate('tr.om-grid-row','dblclick',function(event){
                    var index = self._getRowIndex(this);
                    self._trigger("onRowDblClick",event,index,self._getRowData(index));
                });
            }
        },
        //单独抽出这个方法是为了更好整合其它grid插件，因为很多插件会对tr进行操作，比如行编辑插件会对tr进行隐藏，所以这里获取行索引要注意不与插件冲突。
        _getRowIndexByCheckBox:function(checkbox){
        	return this._getTdCheckBox().index(checkbox);
        },
        //获取所有真正的checkbox，此方法一样可以兼容其它插件。
        _getTdCheckBox:function(){
        	return this.tbody.find("td.checkboxCol span.checkbox");
        }
	});
})(jQuery);