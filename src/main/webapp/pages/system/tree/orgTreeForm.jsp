<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="selector" uri="/WEB-INF/taglib/showParamSelector.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../../head.jsp"/>
<script type="text/javascript" src="${rc.contextPath}/js/system/tree/orgTreeForm.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$.post('${rc.contextPath}/common/getComplaintGist', {}, function(data){
		createComplaintGist(data.complaintGistList);
		// 关闭滚动条
		base.closepageloading();
	});

	var treeUrl = $('#jsonForm').attr('action')+'/system/organization/getTree?Id=';
   	$('#tree').tree({  
	  checkbox: false,
	  url:treeUrl+0,
	  onBeforeExpand:function(node,param){
		  $('#tree').tree('options').url = treeUrl + node.id;                
		  }
	});
});

/**
 * 创建投诉要点
 * */
function createComplaintGist(complaintGistList){
	var selectGist1 = $('#complaintGist1');
	var defaultOption = $('<option value="">---请选择---</option>').appendTo(selectGist1);
	if(complaintGistList){
		$.each(complaintGistList,function(i,param){
			$('<option value="'+param.value+'">'+param.text+'</option>').appendTo(selectGist1);
		});
	}
	var selectGist2 = $('#complaintGist2');
	$('<option value="">---请选择---</option>').appendTo(selectGist2);
	
	selectGist1.bind('change',function(){
		selectGist2.html('');
		$('<option value="">---请选择---</option>').appendTo(selectGist2);
		$.each(complaintGistList,function(i,param){
			if(param.value == selectGist1.val()){
				$.each(param.childParams,function(j,childParam){
					$('<option value="'+param.value+'/'+childParam.value+'">'+childParam.text+'</option>').appendTo(selectGist2);
				});
				return false;
			}
		});
	});
}
</script>
</head>
<body>
<ul id="tree"></ul><br> 
<div class="m_cont" border="1" align="center" width="98%">
	<form id="jsonForm" action="${rc.contextPath}"></form>
	<h2>Basic ComboTree</h2>  
    <div class="demo-info">  
        <div class="demo-tip icon-tip"></div>  
        <div>Click the right arrow button to show the tree panel.</div>  
    </div>  
    <div style="margin:10px 0"></div> 
    <input id="combotree_1" data-options="required:true" style="width:200px;"><br>
    <input type="button" value="查看选中的数据" onclick="stuList.show_1()"/><br>
    <input id="combotree" data-options="required:true" style="width:200px;"><br>
	<input type="button" value="查看选中的数据" onclick="stuList.show()"/><br>
	<input id="multiple_combotree" data-options="required:true" style="width:200px;"><br>
	<input type="button" value="查看选中的数据" onclick="stuList.show3()"/><br>
	<selector:option itemId="tt" itemName="customeFeedback" required="true" paramType="0"/><br>
	<hr>
	投诉要点：<br>
    <select id="complaintGist1" name="complaintGist1"></select>
	&nbsp;&nbsp;
	<select id="complaintGist2" name="complaintGist2"></select><br>
	<input type="button" value="查看选中的数据" onclick="stuList.show4()"/><br>
	
</div>
</body>
</html>