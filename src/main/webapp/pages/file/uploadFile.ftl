<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<@headMacro.headMacro/>
	<script type="text/javascript">
	$(document).ready(function() {
		  $('#file0').omFileUpload({
		    action : '${rc.contextPath}/file/upload2',
		    onComplete : function(event,ID,fileObj,response,data){
		    }
		  });
		});
	</script>
</head>
<body>
<#include "/pageloading.html"/>
	<input id="file0" name="file0" type="file"/>
	<button value="上传" onclick="$('#file0').omFileUpload('upload')">上传</button>
    <div id="response" style="font-weight: bold;color: red;"></div>
</body>
</html>