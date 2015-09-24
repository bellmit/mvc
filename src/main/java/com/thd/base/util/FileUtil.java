package com.thd.base.util;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtil {
    Logger logger = LoggerUtil.getLogger();

    @Value("#{appProps['upload-file-path']}")
    private String contextUploadFilePath;

    public FileUtil() {
        super();
    }

    /**
     * 上传文件
     */
    public String uploadFile(MultipartFile file) {
        String uploadPath = null;
        if (file != null && !file.isEmpty()) {
            try {
                uploadPath = outputFile(file);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                uploadPath = null;
            }
        }

        return uploadPath;
    }

    /**
     * 删除文件
     */
    public Boolean deleteFile(String uploadPath) {
        File file = new File(contextUploadFilePath + "/" + uploadPath);
        return !file.exists() || file.delete();
    }

    /**
     * 下载文件
     *
     * @param fileName         服务器端保存的文件名称
     * @param originalFileName 上传是文件的原名
     */
    public void downloadFile(String fileName, String originalFileName, HttpServletRequest request,
                             HttpServletResponse response) {

        BufferedInputStream bis = null;
        //BufferedOutputStream bos = null;
        try {
            String downloadPath = contextUploadFilePath + "/" + fileName;
            logger.debug("downloadPath====================" + downloadPath);

            long fileLength = new File(downloadPath).length();

            response.setContentType("application/octet-stream; charset=GBK");
            response.setHeader("Accept-Ranges", "bytes");
            response.addHeader("Content-Disposition", "attachment; filename="
                    + new String(originalFileName.getBytes("GB2312"), "ISO-8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(downloadPath));
            IOUtils.copy(bis, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            //do nothing
        } finally {
            IOUtils.closeQuietly(bis);
        }
    }

    /**
     * 输出文件
     *
     * @param multipartFile
     * @return fileName
     */
    private String outputFile(MultipartFile multipartFile) throws IOException {
        String filePath;
        String timeDir = new SimpleDateFormat(Constant.DATE_PATTERN_yyyyMMdd).format(new Date());
        File dirPath = new File(contextUploadFilePath + "/" + timeDir);
        if (!dirPath.exists()) {
            dirPath.mkdir();
        }

        filePath = timeDir + "/" + new SimpleDateFormat(Constant.DATE_PATTERN_yyyy_MM_dd_HHmmss_SSS).format(new Date());
        File file = new File(contextUploadFilePath + "/" + filePath);
        file.setWritable(true);
        FileCopyUtils.copy(multipartFile.getBytes(), file);

        return filePath;
    }

}
