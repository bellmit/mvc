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

import com.thd.base.page.TreeData;
import com.thd.base.page.TreeRow;
import com.thd.param.model.Param;
import com.thd.param.model.Param.ParamType;
import com.thd.param.service.ParamService;

@Controller
@RequestMapping("/param/*")
public class ParamController {
	@Autowired
	private ParamService paramService;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
	}

	/**
	 * 跳转到参数管理初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/paramListForm")
	public ModelAndView paramListForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/system/param/paramListForm");
		List<String[]> paramTypeList = new ArrayList<String[]>();
		for (ParamType type : ParamType.values()) {
			paramTypeList.add(new String[] { type.toString(), type.getText() });
		}
		modelAndView.addObject("typeList", paramTypeList);
		return modelAndView;
	}

	/**
	 * 
	 * @param param
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/paramList")
	@ResponseBody
	public TreeData<Param> paramList(Param param, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TreeData<Param> treeData = new TreeData<Param>();
		List<Param> paramList = paramService.getParamList(param.getType(), Boolean.FALSE);
		if (paramList != null && paramList.size() > 0) {
			List<TreeRow<Param>> rows = new ArrayList<TreeRow<Param>>();
			for (Param paramVo : paramList) {
				TreeRow<Param> treeRow = new TreeRow<Param>();
				treeRow.setId(paramVo.getId());
				if (paramVo.getParentParam() != null && paramVo.getParentParam().getId() != null) {
					treeRow.set_parentId(paramVo.getParentParam().getId());
				}
				Param par = new Param();
				par.setId(paramVo.getId());
				par.setText(paramVo.getText());
				par.setType(paramVo.getType());
				par.setValue(paramVo.getValue());
				par.setSortNo(paramVo.getSortNo());
				treeRow.setObj(par);
				rows.add(treeRow);
			}
			treeData.setRows(rows);
			treeData.setTotal(paramList.size());
		}
		return treeData;

	}

	/**
	 * 保存参数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveParam")
	@ResponseBody
	public ModelMap saveParam(Param param) throws Exception {

		ModelMap modelMap = new ModelMap();
		paramService.saveParam(param);
		modelMap.put("successMsg", "保存成功！");
		return modelMap;

	}

	/**
	 * 修改参数
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateParam")
	@ResponseBody
	public ModelMap updateParam(Param param) throws Exception {

		ModelMap modelMap = new ModelMap();
		paramService.updateParam(param);
		modelMap.put("successMsg", "修改成功！");
		return modelMap;

	}

	/**
	 * 删除参数
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteParam")
	@ResponseBody
	public ModelMap deleteParam(Long id) throws Exception {
		paramService.deleteParam(id);
		ModelMap modelMap = new ModelMap();
		modelMap.put("successMsg", "删除成功！");
		return modelMap;
	}
}
