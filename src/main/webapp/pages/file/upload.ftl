<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<#assign c=JspTaglibs["http://java.sun.com/jsp/jstl/core"]/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<#import "/base/macro/headMacro.ftl" as headMacro>
<#setting number_format="#">
<head>
<@headMacro.headMacro/>
    <script type="text/javascript">
        var upload = {
            fileuploadLoad: function () {
                var form = $('#uploadForm');
                form.submit();
                alert('上传成功!');
                base.closeTab("toUpload");
            },
            reset: function () {
                document.getElementById('uploadForm').reset();//清除附件
            }
        }

    </script>
    <script type="text/javascript">
        var fileChk = {
            fileTypes: [".jpg", ".png", ".rar", ".txt", ".zip", ".doc", ".ppt", ".xls", ".pdf", ".docx", ".xlsx"],
            fileMaxSize: 1024 * 1024 * 20,//单位bytes,大小20M
            errInfo: {
                fileNotExists: "您选择的文件不存在!",
                fileSizeNull: "您选择的文件为空文件!",
                fileOutSize: "您选择的文件大小超过上限值(20M)",
                fileTypeError: "目前仅支持如下类型文件[.jpg,.png,.rar,.txt,.zip,.doc,.ppt,.xls,.pdf,.docx,.xlsx]"
            }
        };
        //判断元素是否为空
        function isNotNull(obj) {
            if (obj == null || obj == '' || obj == undefined) {
                return false;
            } else {
                return true;
            }
        }

        //校验文件
        function validateFile() {
            var file = $("#fileId")[0].files[0];
            //文件空校验
            if (!isNotNull(file)) {
                alert(fileChk.errInfo.fileNotExists);
                document.getElementById('uploadForm').reset();//清除附件
                return fileChk.errInfo.fileNotExists;
            } else {
                var filename = file.name;//文件名称
                var fileSize = file.size; //文件大小
                var fileTypes = fileChk.fileTypes;
                var fileMaxSize = fileChk.fileMaxSize;
                //文件类型校验
                if (isNotNull(filename)) {
                    var isFlag = false;
                    //获取文件扩展名
                    var extensionName = filename.substring(filename.lastIndexOf("."), filename.length).toLowerCase();
                    if (isNotNull(fileTypes) && fileTypes.length > 0) {
                        for (var t in fileTypes) {
                            if (fileTypes[t] == extensionName) {
                                isFlag = true;
                                break;
                            }
                        }
                    }
                    if (!isFlag) {
                        alert(fileChk.errInfo.fileTypeError);
                        document.getElementById('uploadForm').reset();//清除附件
                        return fileChk.errInfo.fileTypeError;
                    }
                }

                //文件大小校验
                if (fileSize <= 0) {
                    alert(fileChk.errInfo.fileSizeNull);
                    document.getElementById('uploadForm').reset();//清除附件
                    return fileChk.errInfo.fileSizeNull;
                } else if (fileSize > fileMaxSize) {
                    alert(fileChk.errInfo.fileOutSize);
                    document.getElementById('uploadForm').reset();//清除附件
                    return fileChk.errInfo.fileOutSize;
                }
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

                <form id="uploadForm" action="${rc.contextPath}/common/uploadFile" method="post"
                      enctype="multipart/form-data">
                    文件 <input type="file" id="fileId" name="file" onchange="validateFile()"><br>
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