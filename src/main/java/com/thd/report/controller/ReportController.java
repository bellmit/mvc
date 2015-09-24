package com.thd.report.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thd.base.page.PageData;
import com.thd.report.service.ReportService;
import com.thd.report.util.ReportUtil;
import com.thd.report.vo.ReportOperatorVo;
import com.thd.system.model.Operator;
import com.thd.system.model.Operator.GenderType;
import com.thd.system.model.Operator.OperatorType;

@Controller
@RequestMapping("/report/*")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@RequestMapping("/toOperatorListReport")
	public ModelAndView toOperatorListReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/report/operatorListReport");
		return modelAndView;
	}

	/**
	 * 查询数据 使用easyui dategrid 显示 需要返回json数据：两个参数  total(总条数)，rows(list) 
	 */
	@RequestMapping("/reportOpeData")
	public @ResponseBody
	PageData<ReportOperatorVo> reportOpeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageData<ReportOperatorVo> pageData = new PageData<ReportOperatorVo>();
		List<ReportOperatorVo> list = reportService.getOperatorList();
		int total = list.size();
		pageData.setRows(list);
		pageData.setTotal(total);
		return pageData;
	}

	/**
	 * 导出用户表
	 */
	@RequestMapping("operatorReportExport")
	public void employeeReportExport(HttpServletRequest request, HttpServletResponse response) throws IOException,
			InvocationTargetException {
		List<ReportOperatorVo> list = reportService.getOperatorList();
		String filename = "用户报表.xls";
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			filename = new String(filename.getBytes("utf-8"), "iso8859-1");//firefox浏览器
		} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			filename = URLEncoder.encode(filename, "UTF-8");//IE浏览器 解决文件名乱码
		}

		response.setContentType("application/ vnd.ms-excel;");
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		OutputStream out = response.getOutputStream();
		String[] header = { "姓名", "邮箱" };//头列么要与VO类字段对应  否则顺序会乱
		ReportUtil<ReportOperatorVo> r = new ReportUtil<ReportOperatorVo>();
		r.exportExcel("用户报表", header, list, out, "yyyy-mm-dd");
	}

	/**
	 * 去测试饼图页面
	 */
	@RequestMapping("/toTestReport")
	public ModelAndView toTestReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/report/testReport");
		return modelAndView;
	}

	/**
	 * 去用户性别饼图页面
	 */
	@RequestMapping("/toOperatorSexReport")
	public ModelAndView toOperatorSexReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/report/operatorSexReport");
		return modelAndView;
	}

	/**
	 * 返回用户性别饼图数据源
	 * @return
	 */
	@RequestMapping("/getOperatorSex")
	@ResponseBody
	public ModelMap getOperatorSex() {
		ModelMap modelMap = new ModelMap();
		List<Operator> list = reportService.getOperatorSexList();
		Double x = 0.0, y = 0.0;
		if (list.size() > 0) {
			for (Operator operator : list) {
				if (operator.getOperGender().equals(GenderType.MAN)) {
					x += 1;
				} else {
					y += 1;
				}
			}
		}
		x /= list.size();
		y /= list.size();
		modelMap.put("x", x);
		modelMap.put("y", y);
		return modelMap;
	}

	/**
	 * 去测试柱状图页面
	 */
	@RequestMapping("/toColumn_basictReport")
	public ModelAndView toColumn_basictReport() {
		ModelAndView modelAndView = new ModelAndView("/report/column_basictReport");
		return modelAndView;
	}

	/**
	 * 去用户性别柱状图页面
	 */
	@RequestMapping("/toOperatorSexColumnReport")
	public ModelAndView toOperatorSexColumnReport(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView("/report/operatorSexColumnReport");
		return modelAndView;
	}

	/**
	 * 返回用户性别饼图数据源
	 * @return
	 */
	@RequestMapping("/getClumnSex")
	@ResponseBody
	public ModelMap getClumnSex() {
		ModelMap modelMap = new ModelMap();
		List<Operator> list = reportService.getOperatorSexList();
		Double x = 0.0, y = 0.0, a = 0.0, b = 0.0;
		if (list.size() > 0) {
			for (Operator operator : list) {
				if (operator.getOperGender().equals(GenderType.MAN)) {
					x += 1;
					if (operator.getOperType().equals(OperatorType.MANAGER)) {
						a += 1;
					}
				} else {
					y += 1;
					if (operator.getOperType().equals(OperatorType.MANAGER)) {
						b += 1;
					}
				}
			}
		}
		modelMap.put("x", x);
		modelMap.put("y", y);
		modelMap.put("a", a);
		modelMap.put("b", b);
		modelMap.put("totle", list.size());
		return modelMap;
	}
}
