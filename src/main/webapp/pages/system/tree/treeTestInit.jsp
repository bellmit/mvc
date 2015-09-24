<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<head>
    <title>投诉系统</title>
	<link rel="stylesheet" media="all" type="text/css" href="${rc.contextPath}/css/themes/default/easyui.css" id="easyuiTheme"/>
	<link rel="stylesheet" media="all" type="text/css" href="${rc.contextPath}/css/themes/icon.css"/>
	<link rel="stylesheet" media="all" type="text/css" href="${rc.contextPath}/css/main.css"/>
	<script type="text/javascript" src="${rc.contextPath}/js/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/js/jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/js/jquery/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/js/base/tabMap.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/js/base/tabs.js"></script>
	<script type="text/javascript" src="${rc.contextPath}/js/base/base.js"></script>
	<script>	
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