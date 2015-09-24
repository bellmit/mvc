package com.thd.system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.thd.base.page.TreeData;
import com.thd.base.page.TreeRow;
import com.thd.system.model.Function;
import com.thd.system.service.SystemService;

@Controller
@RequestMapping("/system/function/*")
public class FunctionController {

	@Autowired
	private SystemService systemService;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
			throws Exception {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
	}

	/**
	 * 跳到权限管理初始页面
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/functionListForm")
	public ModelAndView functionListForm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 返回权限管理初始页面ModelAndView
		return new ModelAndView("/system/function/functionListForm");
	}

	/**
	 * 获得权限列表
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	@RequestMapping("/functionList")
	@ResponseBody
	public TreeData<Function> functionList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TreeData<Function> treeData = new TreeData<Function>();
		List<Function> funList = systemService.getFunctionList();
		if (funList != null && funList.size() > 0) {
			List<TreeRow<Function>> rows = new ArrayList<TreeRow<Function>>();
			for (Function fun : funList) {
				TreeRow<Function> treeRow = new TreeRow<Function>();
				treeRow.setId(fun.getId());
				if (fun.getParentFunction() != null
						&& fun.getParentFunction().getId() != null) {
					treeRow.set_parentId(fun.getParentFunction().getId());
				}
				Function function = new Function();
				function.setId(fun.getId());
				function.setFunName(fun.getFunName());
				function.setFunMemo(fun.getFunMemo());
				function.setFunOrder(fun.getFunOrder());
				function.setFunUrl(fun.getFunUrl());
				treeRow.setObj(function);
				rows.add(treeRow);
			}
			treeData.setRows(rows);
			treeData.setTotal(funList.size());
		}

		return treeData;
	}

}
