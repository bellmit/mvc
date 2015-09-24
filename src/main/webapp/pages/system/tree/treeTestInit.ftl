<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html xmlns="http://www.w3.org/1999/xhtml">
<#import "/base/macro/headMacro.ftl" as headMacro>
<head>
<@headMacro.headMacro/>
	<script type="text/javascript">
		 $(document).ready(function() {
			 var url = '${rc.contextPath}/system/organization/orgTreeJson';
				$('#qwe').combotree({  
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
			 
			 $('#tt').tree({
				 checkbox:false,
				 url:'${rc.contextPath}/system/organization/getTree',
				 onBeforeExpand:function(node,param){
					 $('#tt').tree('options').url = '${rc.contextPath}/system/organization/getTree?Id='+ node.id;
				 }
			 });
			                                             
		}); 
	</script>
  </head>
<body>
	<input id="qwe">
	<ui id="tt"></ui> 
</body>
</html>