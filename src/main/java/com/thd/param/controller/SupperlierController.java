package com.thd.param.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thd.base.page.PageData;
import com.thd.base.page.PageParam;
import com.thd.param.model.Param;
import com.thd.param.model.Supplier;
import com.thd.param.model.Param.ParamType;
import com.thd.param.service.ParamService;

@Controller
@RequestMapping("/supplier/*")
public class SupperlierController {

	@Autowired
	private ParamService paramService;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
	}

	/**
	 * 转到供应商查询页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/supplierListForm")
	public ModelAndView supplierListForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/system/supplier/supplierListForm");
		List<String> supClassifyList = new ArrayList<String>();
		List<Param> list = paramService.getParamList(ParamType.SUPPLIER_CLASSIFY, Boolean.TRUE);
		for (Param param : list) {
			supClassifyList.add(param.getText());
		}
		modelAndView.addObject("supClassifyList", supClassifyList);
		return modelAndView;
	}

	/**
	 * 分页查询供应商列表信息
	 * @param pageParam
	 * @param supplier
	 * @return
	 */
	@RequestMapping(value = "/supplierList")
	@ResponseBody
	public PageData<Supplier> supplierList(PageParam pageParam, Supplier supplier) {
		PageData<Supplier> result = paramService.queryPageSupplierList(pageParam, supplier);
		return result;
	}

	/**
	 * 转到供应商新增页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/supplierAddForm")
	public ModelAndView supplierAddForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/system/supplier/supplierAddForm");
		List<String> supClassifyList = new ArrayList<String>();
		List<Param> list = paramService.getParamList(ParamType.SUPPLIER_CLASSIFY, Boolean.TRUE);
		for (Param param : list) {
			supClassifyList.add(param.getText());
		}
		modelAndView.addObject("supClassifyList", supClassifyList);
		return modelAndView;
	}

	/**
	 * 保存供应商信息
	 * @param sup
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveSupplier")
	@ResponseBody
	public ModelMap saveSupplier(Supplier sup) throws Exception {

		ModelMap modelMap = new ModelMap();
		paramService.saveSupplier(sup);
		modelMap.put("successMsg", "保存成功！");
		return modelMap;

	}

	/**
	 * 转到供应商修改页面
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/supplierUpdForm")
	public ModelAndView supplierUpdForm(Long id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView = new ModelAndView("/system/supplier/supplierUpdForm");// 返回用户修改页面
		Supplier supplier = paramService.getSupplierById(id);
		List<String> supClassifyList = new ArrayList<String>();
		List<Param> list = paramService.getParamList(ParamType.SUPPLIER_CLASSIFY, Boolean.TRUE);
		for (Param param : list) {
			supClassifyList.add(param.getText());
		}
		modelAndView.addObject("supClassifyList", supClassifyList);
		modelAndView.addObject("id", id);
		modelAndView.addObject("supplier", supplier);
		// 返回用户修改页面ModelAndView
		return modelAndView;
	}

	/**
	 * 修改供应商
	 * @param supplier
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateSupplier")
	@ResponseBody
	public ModelMap updateSupplier(Supplier supplier) throws Exception {
		ModelMap modelMap = new ModelMap();
		paramService.updateSupplier(supplier);
		modelMap.put("successFlg", "0");
		modelMap.put("successMsg", "保存成功！");
		return modelMap;
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteSupplier")
	@ResponseBody
	public ModelMap deleteSupplier(Long id) throws Exception {
		paramService.deleteSupplier(id);
		ModelMap modelMap = new ModelMap();
		modelMap.put("successMsg", "删除成功！");
		return modelMap;
	}
}
