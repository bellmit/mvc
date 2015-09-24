package com.thd.sample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thd.base.util.LoggerUtil;
import com.thd.sample.model.SampleTest1;
import com.thd.sample.vo.SampleGridParamVo;
import com.thd.sample.vo.SampleGridVo;

@Controller
@RequestMapping("/sample/*")
public class SampleController {
	Logger logger = LoggerUtil.getLogger();

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
	}

	@RequestMapping("/sampleGridList")
	public ModelAndView sampleGridList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/sample/grid/gridList");
		return modelAndView;
	}

	/**
	 * 未登录，跳转登录页面
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping("/gridData1")
	@ResponseBody
	public SampleGridVo gridData1(SampleGridParamVo pageVo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SampleGridVo sampleGridVo = new SampleGridVo();
		sampleGridVo.setTotal(14);

		int satrt = 0;
		int end = 0;
		if (pageVo.getPage() * pageVo.getRows() <= 14) {
			satrt = (pageVo.getPage() - 1) * pageVo.getRows();
			end = satrt + pageVo.getRows();
		} else {
			satrt = (pageVo.getPage() - 1) * pageVo.getRows();
			end = 14;
		}
		while (satrt < end) {
			SampleTest1 sampleTest1 = new SampleTest1();
			sampleTest1.setItemid("ITEM-" + satrt);
			sampleTest1.setProductid("FI-SW-" + satrt);
			sampleTest1.setListprice(16.50);
			sampleTest1.setUnitcost(10.00);
			sampleTest1.setAttr1("Large");
			sampleTest1.setStatus("P");
			sampleGridVo.addRows(sampleTest1);
			satrt++;
		}
		return sampleGridVo;
	}
}
