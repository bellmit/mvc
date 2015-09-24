package com.thd.spring.start.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/file/*")
public class UploadController {

	@RequestMapping("/toUpload")
	public ModelAndView toUpload() {
		return new ModelAndView("/file/upload");
	}

	@RequestMapping("/toUpload2")
	public String toUpload2(HttpServletRequest request) {
		return "/file/uploadFile";
	}

	@RequestMapping("/upload")
    @ResponseBody
	public String upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request)
			throws IOException {
		System.out.println("=======file 名字=======" + file.getOriginalFilename());
		if (!file.isEmpty()) {
			try {
				FileOutputStream os = new FileOutputStream("D:/" + new Date().getTime() + file.getOriginalFilename());
				InputStream in = file.getInputStream();
				int b = 0;
				while ((b = in.read()) != -1) {
					os.write(b);
				}
				os.flush();
				os.close();
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "/start";
	}

	@RequestMapping("/upload2")
	public String upload2(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException,
			IOException {
		System.out.println("=======upload2 运行=======");
		//解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
				.getServletContext());
		//判断request是不是文件类型
		if (multipartResolver.isMultipart(request)) {
			//转换成springmvc封装好的类型
			MultipartHttpServletRequest muRequest = (MultipartHttpServletRequest) request;
			//迭代拿文件
			Iterator<String> iter = muRequest.getFileNames();
			while (iter.hasNext()) {
				//拿文件
				MultipartFile file = muRequest.getFile(iter.next());
				if (file != null) {
					//写文件
					String fileName = "demoUpload" + file.getOriginalFilename();
					String path = "D:/" + fileName;
					File localFile = new File(path);
					//讲上传文件写到服务器上指定文件
					file.transferTo(localFile);
				}
			}

		}
		return "/start";
	}

}
