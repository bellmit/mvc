<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<@headMacro.headMacro/>
<script type="text/javascript">
var upload={
	fileuploadLoad:function(){
		var form = $('#uploadForm');
		form.submit();
		alert('上传成功!');
		base.closeTab("toUpload");
	},
	reset:function(){
		document.getElementById('uploadForm').reset();//清除附件
	}
}

</script>
</head>
<body>
<#include "/pageloading.html"/>
<div class="maincont_div">
	<div class="m_cont">
		<div class="form_div">
			<div align="center">
				<h2>恭喜你成功啦!</h2>
				<form id="uploadForm" action="${rc.contextPath}/common/uploadFile" method="post" enctype="multipart/form-data">
				文件 <input type="file" name="file"><br>
				<input type="submit" value="上传">
				</form>
			</div>
			<div class="buttonArea">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="upload.fileuploadLoad();">上传</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="upload.reset();">重置</a>
			</div>
		</div>
	</div>
</div>
</body>
</html>