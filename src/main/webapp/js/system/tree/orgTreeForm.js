$(document).ready(function() {
	$.messager.show({  
        title: '机构树页面欢迎您!',  
        msg:'机构树页面欢迎您!'
    });
	var url = $('#jsonForm').attr('action')+'/system/organization/orgTreeJson';
	$('#combotree').combotree({  
	    /**
		 * 获取数据源
		 */ 
	    url : url,
	    /**
		 * 级联显示多选
		 */
	    // cascadeCheck:true,
	    /**
		 * 选择树节点触发事件
		 */
	    onSelect : function(node) {
	        // 返回树对象
	        var tree = $(this).tree;
	        // 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            // 清除选中
	        	alert('不能选择机构1!');
	            $('#combotree').combotree('clear'); 
	        }
	    }  
	}); 
	
	$('#multiple_combotree').combotree( { 
	    /**
		 * 获取数据源
		 */ 
	    url : url,
	    /**
		 * 级联多选 默认为true
		 */
	    cascadeCheck:false,
	    /**
		 * true为多选 默认为单选
		 */
	    multiple:true,
	    /**
		 * 选择树节点触发事件
		 */
	    onCheck: function(node) {
	        // 返回树对象
	        var tree = $(this).tree;
	        // 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
	        var isLeaf = tree('isLeaf', node.target);  
	        if (!isLeaf) {  
	            // 清除选中
	            $('#multiple_combotree').combotree('clear');
	            // 等价于$('#multiple_combotree').combotree('setValues', '');多选可以用
	        }
	    }  
	});
	// 多选赋值
	$('#multiple_combotree').combotree('setValues', ['orgid2','orgid4']);
	stuList.initTree();
	// 单选赋值
	$('#combotree_1').combotree('setValue', 'orgid4');
	
//	var treeUrl = $('#jsonForm').attr('action')+'/system/getTree?Id=';
//   	alert(treeUrl);
//   	$('#tree').tree({  
//	  checkbox: false,
//	  url:treeUrl+0,
//	  onBeforeExpand:function(node,param){
//		  $('#tree').tree('options').url = treeUrl + node.id;                
//		  }
//	}); 
			                                              
	
});

var stuList={
		/**
		 * 数据源需要的json数据
		 */
		json:function(){
			[{
				"id":1,
				"text":"My Documents",
				"children":[{
					"id":11,
					"text":"Photos",
					"state":"closed",
					"children":[{
						"id":111,
						"text":"Friend"
					},{
						"id":112,
						"text":"Wife"
					},{
						"id":113,
						"text":"Company"
					}]
				},{
					"id":12,
					"text":"Program Files",
					"children":[{
						"id":121,
						"text":"Intel"
					},{
						"id":122,
						"text":"Java",
						"attributes":{
							"p1":"Custom Attribute1",
							"p2":"Custom Attribute2"
						}
					},{
						"id":123,
						"text":"Microsoft Office"
					},{
						"id":124,
						"text":"Games",
						"checked":true
					}]
				},{
					"id":13,
					"text":"index.html"
				},{
					"id":14,
					"text":"about.html"
				},{
					"id":15,
					"text":"welcome.html"
				}]
			}]
		},
		show:function(){
			/**
			 * 获得单选的值
			 */
			var val = $('#combotree').combotree('getValue');  
            alert(val);
		},
		show3:function(){
			/**
			 * 获得多选的值
			 */
			var val = $('#multiple_combotree').combotree('getValues');  
            alert(val);
		},
		show4:function(){
			/**
			 * 获得级联的值
			 */
			var val = $('#complaintGist2').val();  
            alert(val);
		},
		show_1:function(){
			/**
			 * 获得多选的值
			 */
			var val = $('#combotree_1').combotree('getValues');  
            alert(val);
		},
		initTree:function(){
			var url2 = $('#jsonForm').attr('action')+'/system/organization/orgTreeJson';
			$('#combotree_1').combotree( { 
			    /**
				 * 获取数据源
				 */ 
			    url : url2,
			    /**
				 * 级联多选 默认为true
				 */
			    cascadeCheck:true,
			    /**
				 * true为多选 默认为单选
				 */
			    multiple:true,
			    state:closed,
			    /**
				 * 选择树节点触发事件
				 */
			    onClick: function(node) {
			        // 返回树对象
			        var tree = $(this).tree;
			        // 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
			        var isLeaf = tree('isLeaf', node.target);
			        alert(node.id);
			        if (!isLeaf) {  
			            // 清除选中
			            $('#combotree_1').combotree('clear');
			            // 等价于$('#multiple_combotree').combotree('setValues',
						// '');多选可以用
			        }
			    }
			}); 
		}
};