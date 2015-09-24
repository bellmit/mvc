package com.thd.base.common.controller;

import com.thd.base.common.model.Attachment;
import com.thd.base.common.service.CommonService;
import com.thd.base.page.PageData;
import com.thd.base.page.PageParam;
import com.thd.base.util.FileUtil;
import com.thd.base.util.LoggerUtil;
import com.thd.param.model.Param;
import com.thd.param.model.Param.ParamType;
import com.thd.param.service.ParamService;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.fop.svg.PDFTranscoder;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common/*")
public class CommonController {

	Logger logger = LoggerUtil.getLogger();
	@Resource
	CommonService commonService;
	@Resource
	ParamService paramService;
	@Resource
	FileUtil fileUtil;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
	}

	/**
	 * 报表图片导出功能
	 */
	@RequestMapping("exportSvg")
	public @ResponseBody
	void exportSvg(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.setCharacterEncoding("utf-8");//注意编码 
		String type = request.getParameter("type");
		String svg = request.getParameter("svg");
		//String width = request.getParameter("width");
		String filename = request.getParameter("filename");
		filename = filename == null ? "chart" : filename;
		ServletOutputStream out = response.getOutputStream();
		if (null != type && null != svg) {
			svg = svg.replaceAll(":rect", "rect");
			String ext = "";
			Transcoder t = null;
			if (type.equals("image/png")) {
				ext = "png";
				try {
					t = new PNGTranscoder();
				} catch (Throwable e) {
					logger.debug(e.getMessage(), e);
				}
			} else if (type.equals("image/jpeg")) {
				ext = "jpg";
				t = new JPEGTranscoder();

			} else if (type.equals("application/pdf")) {
				ext = "pdf";
				t = new PDFTranscoder();

			} else if (type.equals("image/svg+xml")) {
				ext = "svg";
			}

			response.addHeader("Content-Disposition", "attachment; filename=" + filename + "-chart." + ext);
			response.addHeader("Content-Type", type);
			svg = svg.replace("-1", "0");
			if (null != t) {
				TranscoderInput transInput = new TranscoderInput();
				transInput.setReader(new StringReader(svg));
				TranscoderOutput output = new TranscoderOutput(out);
				try {
					t.transcode(transInput, output);
				} catch (TranscoderException e) {
					logger.debug(e.getMessage(), e);
				}
				/**
				 * svg中文导出必须用writer 用out会报错
				 */
			} else if (ext.equals("svg")) {
				/* 导出中文的svg图片 */
				OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
				writer.append(svg);
				writer.close();
				/*
				 * 导出英文的svg图片 //out.print(svg);
				 */
			} else {
				out.print("Invalid type: " + type);
			}
		} else {
			response.addHeader("Content-Type", "text/html");
			out
					.println("Usage:\n\tParameter [svg]: The DOM Element to be converted.\n\tParameter [type]: The destination MIME type for the elment to be transcoded.");

		}
		out.flush();
		out.close();
	}

	/**
	 * 获得案件投诉要点
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/getComplaintGist")
	@ResponseBody
	public ModelMap getComplaintGist(HttpServletRequest request, HttpServletResponse response) {
		ModelMap modelMap = new ModelMap();
		// 投诉要点
		List<Param> complaintGistList = new ArrayList<Param>();
		List<Param> gistList = paramService.getParamList(ParamType.COMPLAINT_POINT, true);
		if (gistList != null && gistList.size() > 0) {
			for (Param param : gistList) {
				Param p = new Param();
				p.setText(param.getText());
				p.setValue(param.getValue());
				if (param.getChildParams() != null && param.getChildParams().size() > 0) {
					List<Param> childParamList = new ArrayList<Param>();
					for (Param childParam : param.getChildParams()) {
						Param childP = new Param();
						childP.setText(childParam.getText());
						childP.setValue(childParam.getValue());
						childParamList.add(childP);
					}
					p.setChildParams(childParamList);
				}
				complaintGistList.add(p);
			}
		}
		modelMap.put("complaintGistList", complaintGistList);
		return modelMap;
	}

	/**
	 * 上传附件
	 */
	@RequestMapping("/uploadFile")
	@ResponseBody
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartServletRequest = (MultipartHttpServletRequest) request;

        List<Attachment> attachmentVos = new ArrayList<Attachment>();
        Map<String, MultipartFile> files = multipartServletRequest.getFileMap();
        Iterator<String> fileNames = multipartServletRequest.getFileNames();
        while (fileNames.hasNext()) {
            String fileName = fileNames.next();
            MultipartFile multipartFile = files.get(fileName);
            String attachmentPath = fileUtil.uploadFile(multipartFile);
            if (attachmentPath != null && !"".equals(attachmentPath)) {
                Attachment attachment = new Attachment();
                attachment.setOriginalFileName(multipartFile.getOriginalFilename());
                attachment.setAttachmentPath(attachmentPath);
                commonService.saveAttachment(attachment);
            }
        }


//		//解析器
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
//				.getServletContext());
//		//判断request是不是文件类型
//		if (multipartResolver.isMultipart(request)) {
//			//转换成springmvc封装好的类型
//			MultipartHttpServletRequest muRequest = (MultipartHttpServletRequest) request;
//			//迭代拿文件
//			Iterator<String> iter = muRequest.getFileNames();
//			while (iter.hasNext()) {
//				//拿文件
//				MultipartFile file = muRequest.getFile(iter.next());
//				if (file != null) {
//					Attachment attachment = new Attachment();
//					attachment.setOriginalFileName(file.getOriginalFilename());
//					String path = fileUtil.uploadFile(muRequest, file);
//					attachment.setAttachmentPath(path);
//					commonService.saveAttachment(attachment);
//				}
//			}
//		}
	}

	/**
	 * 文件下载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/downloadFile")
	public void downloadFile(Attachment attachment, HttpServletRequest request, HttpServletResponse response) {
        if (attachment != null) {
            fileUtil.downloadFile(attachment.getAttachmentPath(), attachment.getOriginalFileName(), request, response);
        }
	}

	@RequestMapping("/attachmentList")
	public ModelAndView attachmentList() {
		ModelAndView modelAndView = new ModelAndView("/file/attachmentListForm");
		List<Attachment> list = commonService.getAttachmentList();
		modelAndView.addObject("attachmentList", list);
		return modelAndView;
	}

	/**
	 * 分页查询附件列表
	 */
	@RequestMapping(value="/attachmentList2",produces="application/json;charset=UTF-8")
	@ResponseBody
	public PageData<Attachment> attachmentList2(PageParam pageParam) {
		Attachment attachment = new Attachment();
		PageData<Attachment> result = commonService.queryPageAttachmentList(pageParam, attachment);
		return result;
	}

	/**
	 * 删除上传文件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteFile")
	@ResponseBody
	public ModelMap deleteFile(String id) {
		Attachment attachment = commonService.getAttachmentById(id);

		// 删除附件表数据
		commonService.deleteAttachment(id);

		// 删除上传文件
		if (attachment != null) {
			fileUtil.deleteFile(attachment.getAttachmentPath());
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put("msg", "删除成功!");
		return modelMap;
	}
}
